package io.github.jason13official.more_useful_copper.impl.common.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.jason13official.more_useful_copper.api.common.block.IOxidizableBlock;
import io.github.jason13official.more_useful_copper.api.common.block.WaxableRegistry;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModBlockTags;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModItemTags;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CopperRedstoneDustBlock extends Block implements IOxidizableBlock, SimpleWaterloggedBlock {

  // --- Block state properties (copied from RedStoneWireBlock) ---
  public static final EnumProperty<RedstoneSide> NORTH = BlockStateProperties.NORTH_REDSTONE;
  public static final EnumProperty<RedstoneSide> EAST = BlockStateProperties.EAST_REDSTONE;
  public static final EnumProperty<RedstoneSide> SOUTH = BlockStateProperties.SOUTH_REDSTONE;
  public static final EnumProperty<RedstoneSide> WEST = BlockStateProperties.WEST_REDSTONE;
  public static final IntegerProperty POWER = BlockStateProperties.POWER;
  public static final Map<Direction, EnumProperty<RedstoneSide>> PROPERTY_BY_DIRECTION = Maps.newEnumMap(
      ImmutableMap.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST)
  );
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  // --- Shape constants (copied from RedStoneWireBlock) ---
  private static final VoxelShape SHAPE_DOT = Block.box(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
  private static final Map<Direction, VoxelShape> SHAPES_FLOOR = Maps.newEnumMap(
      ImmutableMap.of(
          Direction.NORTH, Block.box(3.0, 0.0, 0.0, 13.0, 1.0, 13.0),
          Direction.SOUTH, Block.box(3.0, 0.0, 3.0, 13.0, 1.0, 16.0),
          Direction.EAST, Block.box(3.0, 0.0, 3.0, 16.0, 1.0, 13.0),
          Direction.WEST, Block.box(0.0, 0.0, 3.0, 13.0, 1.0, 13.0)
      )
  );
  private static final Map<Direction, VoxelShape> SHAPES_UP = Maps.newEnumMap(
      ImmutableMap.of(
          Direction.NORTH, Shapes.or(SHAPES_FLOOR.get(Direction.NORTH), Block.box(3.0, 0.0, 0.0, 13.0, 16.0, 1.0)),
          Direction.SOUTH, Shapes.or(SHAPES_FLOOR.get(Direction.SOUTH), Block.box(3.0, 0.0, 15.0, 13.0, 16.0, 16.0)),
          Direction.EAST, Shapes.or(SHAPES_FLOOR.get(Direction.EAST), Block.box(15.0, 0.0, 3.0, 16.0, 16.0, 13.0)),
          Direction.WEST, Shapes.or(SHAPES_FLOOR.get(Direction.WEST), Block.box(0.0, 0.0, 3.0, 1.0, 16.0, 13.0))
      )
  );
  // Shared static cache across all 8 copper wire block instances
  private static final Map<BlockState, VoxelShape> SHAPES_CACHE = Maps.newHashMap();
  // True while ANY wire type (vanilla or copper) is inside its getBestNeighborSignal call.
  // Mirrors the role of vanilla's shouldSignal=false, but works across all block instances.
  // Single-threaded redstone tick: no volatile needed.
  public static boolean isAnyWireCalculating = false;
  protected final BlockState crossState;
  private final WeatheringCopper.WeatherState weatherState;
  private boolean shouldSignal = true;
  public CopperRedstoneDustBlock(BlockBehaviour.Properties props, WeatherState weatherState) {
    super(props);
    this.weatherState = weatherState;
    this.registerDefaultState(
        this.stateDefinition.any()
            .setValue(NORTH, RedstoneSide.NONE)
            .setValue(EAST, RedstoneSide.NONE)
            .setValue(SOUTH, RedstoneSide.NONE)
            .setValue(WEST, RedstoneSide.NONE)
            .setValue(POWER, 0)
            .setValue(WATERLOGGED, false)
    );
    this.crossState = this.defaultBlockState()
        .setValue(NORTH, RedstoneSide.SIDE)
        .setValue(EAST, RedstoneSide.SIDE)
        .setValue(SOUTH, RedstoneSide.SIDE)
        .setValue(WEST, RedstoneSide.SIDE);

    for (BlockState blockState : this.getStateDefinition().getPossibleStates()) {
      if (blockState.getValue(POWER) == 0) {
        SHAPES_CACHE.put(blockState, this.calculateShape(blockState));
      }
    }
  }

  /**
   * Returns a packed RGB color for the given power level and oxidation state. Used by block color providers on both Fabric and Forge.
   * <p>
   * Color progressions (dim at power 0 → bright at power 15), derived from blurred reference images: UNAFFECTED  #C36E52  dim 40% → bright EXPOSED     #A37E69  dim 40% → bright WEATHERED   #6D9A70
   * dim 40% → bright OXIDIZED    #54A688  dim 40% → bright
   */
  public static int getColorForPower(int power, WeatherState weatherState) {
    float f = power / 15.0F;
    return switch (weatherState) {
      case EXPOSED -> Mth.color(f * 0.38F + 0.26F, f * 0.29F + 0.20F, f * 0.25F + 0.16F);
      case WEATHERED -> Mth.color(f * 0.26F + 0.17F, f * 0.36F + 0.24F, f * 0.26F + 0.18F);
      case OXIDIZED -> Mth.color(f * 0.20F + 0.13F, f * 0.39F + 0.26F, f * 0.32F + 0.21F);
      default -> Mth.color(f * 0.46F + 0.30F, f * 0.26F + 0.17F, f * 0.19F + 0.13F);
    };
  }

  protected static boolean isCross(BlockState state) {
    return state.getValue(NORTH).isConnected()
        && state.getValue(SOUTH).isConnected()
        && state.getValue(EAST).isConnected()
        && state.getValue(WEST).isConnected();
  }

  protected static boolean isDot(BlockState state) {
    return !state.getValue(NORTH).isConnected()
        && !state.getValue(SOUTH).isConnected()
        && !state.getValue(EAST).isConnected()
        && !state.getValue(WEST).isConnected();
  }

  // --- Shape ---

  protected static boolean shouldConnectTo(BlockState state) {
    return shouldConnectTo(state, null);
  }

  protected static boolean shouldConnectTo(BlockState state, @Nullable Direction direction) {
    if (state.is(Blocks.REDSTONE_WIRE) || state.is(ModBlockTags.COPPER_REDSTONE_WIRE)) {
      return true;
    } else if (state.is(Blocks.REPEATER)) {
      Direction facing = state.getValue(RepeaterBlock.FACING);
      return facing == direction || facing.getOpposite() == direction;
    } else {
      return state.is(Blocks.OBSERVER) ? direction == state.getValue(ObserverBlock.FACING) : state.isSignalSource() && direction != null;
    }
  }

  // --- Placement & shape updates ---

  @Nullable
  private static InteractionResult tryWaxing(BlockState state, Level level, BlockPos pos, Player player, ItemStack itemStack) {
    if (itemStack.getItem() instanceof HoneycombItem) {
      Optional<BlockState> waxedState = WaxableRegistry.getWaxed(state);
      if (waxedState.isPresent()) {
        if (!level.isClientSide) {
          BlockState blockstate = waxedState.get();
          if (player instanceof ServerPlayer sp) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(sp, pos, itemStack);
          }
          itemStack.shrink(1);
          level.setBlock(pos, blockstate, Block.UPDATE_ALL_IMMEDIATE);
          level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockstate));
          level.levelEvent(null, LevelEvent.PARTICLES_AND_SOUND_WAX_ON, pos, 0);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
      }
      return InteractionResult.PASS;
    }
    return null;
  }

  private Vec3 particleColor(int power) {
    float f = power / 15.0F;
    return switch (this.weatherState) {
      case EXPOSED -> new Vec3(f * 0.38 + 0.26, f * 0.29 + 0.20, f * 0.25 + 0.16);
      case WEATHERED -> new Vec3(f * 0.26 + 0.17, f * 0.36 + 0.24, f * 0.26 + 0.18);
      case OXIDIZED -> new Vec3(f * 0.20 + 0.13, f * 0.39 + 0.26, f * 0.32 + 0.21);
      default -> new Vec3(f * 0.46 + 0.30, f * 0.26 + 0.17, f * 0.19 + 0.13);
    };
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(NORTH, EAST, SOUTH, WEST, POWER, WATERLOGGED);
  }

  // --- Connection logic (copied from RedStoneWireBlock) ---

  private VoxelShape calculateShape(BlockState state) {
    VoxelShape shape = SHAPE_DOT;
    for (Direction dir : Direction.Plane.HORIZONTAL) {
      RedstoneSide side = state.getValue(PROPERTY_BY_DIRECTION.get(dir));
      if (side == RedstoneSide.SIDE) {
        shape = Shapes.or(shape, SHAPES_FLOOR.get(dir));
      } else if (side == RedstoneSide.UP) {
        shape = Shapes.or(shape, SHAPES_UP.get(dir));
      }
    }
    return shape;
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return SHAPES_CACHE.get(state.setValue(POWER, 0));
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
    return this.getConnectionState(context.getLevel(), this.crossState, context.getClickedPos())
        .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
  }

  @Override
  public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
    if (state.getValue(WATERLOGGED)) {
      level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
    }
    if (direction == Direction.DOWN) {
      return state;
    } else if (direction == Direction.UP) {
      return this.getConnectionState(level, state, pos);
    } else {
      RedstoneSide redstoneSide = this.getConnectingSide(level, pos, direction);
      return redstoneSide.isConnected() == state.getValue(PROPERTY_BY_DIRECTION.get(direction)).isConnected() && !isCross(state)
          ? state.setValue(PROPERTY_BY_DIRECTION.get(direction), redstoneSide)
          : this.getConnectionState(
              level,
              this.crossState.setValue(POWER, state.getValue(POWER)).setValue(PROPERTY_BY_DIRECTION.get(direction), redstoneSide),
              pos
          );
    }
  }

  @Override
  public void updateIndirectNeighbourShapes(BlockState state, LevelAccessor level, BlockPos pos, int flags, int recursionLeft) {
    BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
    for (Direction direction : Direction.Plane.HORIZONTAL) {
      RedstoneSide redstoneSide = state.getValue(PROPERTY_BY_DIRECTION.get(direction));
      if (redstoneSide != RedstoneSide.NONE && !level.getBlockState(mutableBlockPos.setWithOffset(pos, direction)).is(this)) {
        mutableBlockPos.move(Direction.DOWN);
        BlockState blockState = level.getBlockState(mutableBlockPos);
        if (blockState.is(this)) {
          BlockPos blockPos = mutableBlockPos.relative(direction.getOpposite());
          level.neighborShapeChanged(direction.getOpposite(), level.getBlockState(blockPos), mutableBlockPos, blockPos, flags, recursionLeft);
        }
        mutableBlockPos.setWithOffset(pos, direction).move(Direction.UP);
        BlockState blockState2 = level.getBlockState(mutableBlockPos);
        if (blockState2.is(this)) {
          BlockPos blockPos2 = mutableBlockPos.relative(direction.getOpposite());
          level.neighborShapeChanged(direction.getOpposite(), level.getBlockState(blockPos2), mutableBlockPos, blockPos2, flags, recursionLeft);
        }
      }
    }
  }

  protected BlockState getConnectionState(BlockGetter level, BlockState state, BlockPos pos) {
    boolean wasDot = isDot(state);
    state = this.getMissingConnections(level, this.defaultBlockState().setValue(POWER, state.getValue(POWER)), pos);
    if (wasDot && isDot(state)) {
      return state;
    }
    boolean n = state.getValue(NORTH).isConnected();
    boolean s = state.getValue(SOUTH).isConnected();
    boolean e = state.getValue(EAST).isConnected();
    boolean w = state.getValue(WEST).isConnected();
    boolean noNS = !n && !s;
    boolean noEW = !e && !w;
    if (!w && noNS) {
      state = state.setValue(WEST, RedstoneSide.SIDE);
    }
    if (!e && noNS) {
      state = state.setValue(EAST, RedstoneSide.SIDE);
    }
    if (!n && noEW) {
      state = state.setValue(NORTH, RedstoneSide.SIDE);
    }
    if (!s && noEW) {
      state = state.setValue(SOUTH, RedstoneSide.SIDE);
    }
    return state;
  }

  // --- Modified: recognizes both vanilla and copper wire ---

  private BlockState getMissingConnections(BlockGetter level, BlockState state, BlockPos pos) {
    boolean aboveIsNonConductor = !level.getBlockState(pos.above()).isRedstoneConductor(level, pos);
    for (Direction direction : Direction.Plane.HORIZONTAL) {
      if (!state.getValue(PROPERTY_BY_DIRECTION.get(direction)).isConnected()) {
        RedstoneSide redstoneSide = this.getConnectingSide(level, pos, direction, aboveIsNonConductor);
        state = state.setValue(PROPERTY_BY_DIRECTION.get(direction), redstoneSide);
      }
    }
    return state;
  }

  private RedstoneSide getConnectingSide(BlockGetter level, BlockPos pos, Direction direction) {
    return this.getConnectingSide(level, pos, direction, !level.getBlockState(pos.above()).isRedstoneConductor(level, pos));
  }

  private RedstoneSide getConnectingSide(BlockGetter level, BlockPos pos, Direction direction, boolean nonNormalCubeAbove) {
    BlockPos blockPos = pos.relative(direction);
    BlockState blockState = level.getBlockState(blockPos);
    if (nonNormalCubeAbove) {
      boolean canConnect = blockState.getBlock() instanceof TrapDoorBlock || this.canSurviveOn(level, blockPos, blockState);
      if (canConnect && shouldConnectTo(level.getBlockState(blockPos.above()))) {
        if (blockState.isFaceSturdy(level, blockPos, direction.getOpposite())) {
          return RedstoneSide.UP;
        }
        return RedstoneSide.SIDE;
      }
    }
    return !shouldConnectTo(blockState, direction)
        && (blockState.isRedstoneConductor(level, blockPos) || !shouldConnectTo(level.getBlockState(blockPos.below())))
        ? RedstoneSide.NONE
        : RedstoneSide.SIDE;
  }

  // --- Power propagation (copied from RedStoneWireBlock) ---

  // Modified: reads power from both vanilla wire and all copper wire variants
  private int getWireSignal(BlockState state) {
    if (state.is(ModBlockTags.COPPER_REDSTONE_WIRE)) {
      return state.getValue(POWER);
    }
    if (state.is(Blocks.REDSTONE_WIRE)) {
      return state.getValue(RedStoneWireBlock.POWER);
    }
    return 0;
  }

  private void updatePowerStrength(Level level, BlockPos pos, BlockState state) {
    int i = this.calculateTargetStrength(level, pos);
    if (state.getValue(POWER) != i) {
      if (level.getBlockState(pos) == state) {
        level.setBlock(pos, state.setValue(POWER, i), 2);
      }
      Set<BlockPos> set = Sets.newHashSet();
      set.add(pos);
      for (Direction direction : Direction.values()) {
        set.add(pos.relative(direction));
      }
      for (BlockPos blockPos : set) {
        level.updateNeighborsAt(blockPos, this);
      }
    }
  }

  private int calculateTargetStrength(Level level, BlockPos pos) {
    this.shouldSignal = false;
    isAnyWireCalculating = true;
    int i = level.getBestNeighborSignal(pos);
    isAnyWireCalculating = false;
    this.shouldSignal = true;
    int j = 0;
    if (i < 15) {
      for (Direction direction : Direction.Plane.HORIZONTAL) {
        BlockPos blockPos = pos.relative(direction);
        BlockState blockState = level.getBlockState(blockPos);
        j = Math.max(j, this.getWireSignal(blockState));
        BlockPos blockPos2 = pos.above();
        if (blockState.isRedstoneConductor(level, blockPos) && !level.getBlockState(blockPos2).isRedstoneConductor(level, blockPos2)) {
          j = Math.max(j, this.getWireSignal(level.getBlockState(blockPos.above())));
        } else if (!blockState.isRedstoneConductor(level, blockPos)) {
          j = Math.max(j, this.getWireSignal(level.getBlockState(blockPos.below())));
        }
      }
    }
    return Math.max(i, j - 1);
  }

  private void checkCornerChangeAt(Level level, BlockPos pos) {
    if (level.getBlockState(pos).is(this)) {
      level.updateNeighborsAt(pos, this);
      for (Direction direction : Direction.values()) {
        level.updateNeighborsAt(pos.relative(direction), this);
      }
    }
  }

  private void updateNeighborsOfNeighboringWires(Level level, BlockPos pos) {
    for (Direction direction : Direction.Plane.HORIZONTAL) {
      this.checkCornerChangeAt(level, pos.relative(direction));
    }
    for (Direction direction : Direction.Plane.HORIZONTAL) {
      BlockPos blockPos = pos.relative(direction);
      if (level.getBlockState(blockPos).isRedstoneConductor(level, blockPos)) {
        this.checkCornerChangeAt(level, blockPos.above());
      } else {
        this.checkCornerChangeAt(level, blockPos.below());
      }
    }
  }

  // --- Block event handlers ---

  protected void updatesOnShapeChange(Level level, BlockPos pos, BlockState oldState, BlockState newState) {
    for (Direction direction : Direction.Plane.HORIZONTAL) {
      BlockPos blockPos = pos.relative(direction);
      if (oldState.getValue(PROPERTY_BY_DIRECTION.get(direction)).isConnected()
          != newState.getValue(PROPERTY_BY_DIRECTION.get(direction)).isConnected()
          && level.getBlockState(blockPos).isRedstoneConductor(level, blockPos)) {
        level.updateNeighborsAtExceptFromFacing(blockPos, newState.getBlock(), direction.getOpposite());
      }
    }
  }

  @Override
  public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
    if (!oldState.is(state.getBlock()) && !level.isClientSide) {
      this.updatePowerStrength(level, pos, state);
      for (Direction direction : Direction.Plane.VERTICAL) {
        level.updateNeighborsAt(pos.relative(direction), this);
      }
      this.updateNeighborsOfNeighboringWires(level, pos);
    }
  }

  @Override
  public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
    if (!movedByPiston && !state.is(newState.getBlock())) {
      super.onRemove(state, level, pos, newState, movedByPiston);
      if (!level.isClientSide) {
        for (Direction direction : Direction.values()) {
          level.updateNeighborsAt(pos.relative(direction), this);
        }
        this.updatePowerStrength(level, pos, state);
        this.updateNeighborsOfNeighboringWires(level, pos);
      }
    }
  }

  // --- Signal output ---

  @Override
  public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
    if (!level.isClientSide) {
      if (state.canSurvive(level, pos)) {
        this.updatePowerStrength(level, pos, state);
      } else {
        dropResources(state, level, pos);
        level.removeBlock(pos, false);
      }
    }
  }

  @Override
  public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
    return !this.shouldSignal ? 0 : state.getSignal(level, pos, direction);
  }

  @Override
  public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
    if (!this.shouldSignal || isAnyWireCalculating || direction == Direction.DOWN) {
      return 0;
    }
    int i = state.getValue(POWER);
    if (i == 0) {
      return 0;
    }
    return direction != Direction.UP
        && !this.getConnectionState(level, state, pos).getValue(PROPERTY_BY_DIRECTION.get(direction.getOpposite())).isConnected()
        ? 0
        : i;
  }

  // --- Survival ---

  @Override
  public boolean isSignalSource(BlockState state) {
    return this.shouldSignal;
  }

  @Override
  public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
    BlockPos blockPos = pos.below();
    BlockState blockState = level.getBlockState(blockPos);
    return this.canSurviveOn(level, blockPos, blockState);
  }

  // --- Waterlogging ---

  private boolean canSurviveOn(BlockGetter level, BlockPos pos, BlockState state) {
    return state.isFaceSturdy(level, pos, Direction.UP) || state.is(Blocks.HOPPER);
  }

  // --- IOxidizableBlock ---

  @Override
  public FluidState getFluidState(BlockState state) {
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  @Override
  public WeatherState getAge() {
    return this.weatherState;
  }

  @Override
  public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    this.onRandomTick(state, level, pos, random);
  }

  // --- Waxing helper (copied from CopperComparatorBlock) ---

  @Override
  public boolean isRandomlyTicking(BlockState state) {
    return IOxidizableBlock.getNext(state.getBlock()).isPresent();
  }

  // --- Interaction ---

  @Override
  public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    if (!player.getAbilities().mayBuild) {
      return InteractionResult.PASS;
    }
    ItemStack stackInHand = player.getItemInHand(hand);

    // Oxidize passthrough
    if (stackInHand.is(ModItemTags.MANUAL_OXIDIZER) && this.weatherState != WeatherState.OXIDIZED) {
      return InteractionResult.PASS;
    }

    // Wax scraper passthrough
    if (stackInHand.is(ModItemTags.WAX_SCRAPER)) {
      return InteractionResult.PASS;
    }

    // Apply wax
    InteractionResult waxResult = tryWaxing(state, level, pos, player, stackInHand);
    if (waxResult != null) {
      return waxResult;
    }

    // Cross/dot toggle (same as vanilla RedStoneWireBlock)
    if (isCross(state) || isDot(state)) {
      BlockState newState = isCross(state) ? this.defaultBlockState() : this.crossState;
      newState = newState.setValue(POWER, state.getValue(POWER));
      newState = this.getConnectionState(level, newState, pos);
      if (newState != state) {
        level.setBlock(pos, newState, 3);
        this.updatesOnShapeChange(level, pos, state, newState);
        return InteractionResult.SUCCESS;
      }
    }
    return InteractionResult.PASS;
  }

  // --- Rotation / Mirror ---

  @Override
  public BlockState rotate(BlockState state, Rotation rotation) {
    return switch (rotation) {
      case CLOCKWISE_180 -> state
          .setValue(NORTH, state.getValue(SOUTH))
          .setValue(EAST, state.getValue(WEST))
          .setValue(SOUTH, state.getValue(NORTH))
          .setValue(WEST, state.getValue(EAST));
      case COUNTERCLOCKWISE_90 -> state
          .setValue(NORTH, state.getValue(EAST))
          .setValue(EAST, state.getValue(SOUTH))
          .setValue(SOUTH, state.getValue(WEST))
          .setValue(WEST, state.getValue(NORTH));
      case CLOCKWISE_90 -> state
          .setValue(NORTH, state.getValue(WEST))
          .setValue(EAST, state.getValue(NORTH))
          .setValue(SOUTH, state.getValue(EAST))
          .setValue(WEST, state.getValue(SOUTH));
      default -> state;
    };
  }

  @Override
  public BlockState mirror(BlockState state, Mirror mirror) {
    return switch (mirror) {
      case LEFT_RIGHT -> state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
      case FRONT_BACK -> state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
      default -> super.mirror(state, mirror);
    };
  }

  // --- Particles ---

  @Override
  public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
    int power = state.getValue(POWER);
    if (power != 0) {
      for (Direction direction : Direction.Plane.HORIZONTAL) {
        RedstoneSide redstoneSide = state.getValue(PROPERTY_BY_DIRECTION.get(direction));
        switch (redstoneSide) {
          case UP:
            this.spawnParticlesAlongLine(level, random, pos, this.particleColor(power), direction, Direction.UP, -0.5F, 0.5F);
          case SIDE:
            this.spawnParticlesAlongLine(level, random, pos, this.particleColor(power), Direction.DOWN, direction, 0.0F, 0.5F);
            break;
          default:
            this.spawnParticlesAlongLine(level, random, pos, this.particleColor(power), Direction.DOWN, direction, 0.0F, 0.3F);
        }
      }
    }
  }

  private void spawnParticlesAlongLine(Level level, RandomSource random, BlockPos pos, Vec3 particleVec,
      Direction xDirection, Direction zDirection, float min, float max) {
    float f = max - min;
    if (!(random.nextFloat() >= 0.2F * f)) {
      float g = 0.4375F;
      float h = min + f * random.nextFloat();
      double d = 0.5 + (double) (g * (float) xDirection.getStepX()) + (double) (h * (float) zDirection.getStepX());
      double e = 0.5 + (double) (g * (float) xDirection.getStepY()) + (double) (h * (float) zDirection.getStepY());
      double k = 0.5 + (double) (g * (float) xDirection.getStepZ()) + (double) (h * (float) zDirection.getStepZ());
      level.addParticle(
          new DustParticleOptions(particleVec.toVector3f(), 1.0F),
          (double) pos.getX() + d, (double) pos.getY() + e, (double) pos.getZ() + k,
          0.0, 0.0, 0.0
      );
    }
  }
}
