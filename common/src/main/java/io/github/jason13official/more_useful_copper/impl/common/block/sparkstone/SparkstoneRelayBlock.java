package io.github.jason13official.more_useful_copper.impl.common.block.sparkstone;

import io.github.jason13official.more_useful_copper.api.common.mixin.DispenserAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/// Directional pulse router. Accepts a rising-edge signal on any non-FACING face and re-emits a 1-tick pulse in the FACING direction. Right-click to rotate FACING clockwise.
public class SparkstoneRelayBlock extends Block {

  public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
  public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
  public static final EnumProperty<SparkstonePeriod> PERIOD = EnumProperty.create("period", SparkstonePeriod.class);

  /// mimicking shape from BasePressurePlateBlock
  protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 1.0, 15.0);

  public SparkstoneRelayBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any()
        .setValue(FACING, Direction.NORTH)
        .setValue(PERIOD, SparkstonePeriod.MEDIUM)
        .setValue(POWERED, false));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(FACING, POWERED, PERIOD);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }

  // --- Placement & survival ---

  @Override
  public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
    BlockPos below = pos.below();
    return level.getBlockState(below).isFaceSturdy(level, below, Direction.UP);
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {

    if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
      return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
  }

  @Override
  protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
    if (!canSurvive(state, level, pos)) {
      ticks.scheduleTick(pos, this, 1);
    }
    return super.updateShape(state, level, ticks, pos, direction, neighborPos, neighborState, random);
  }

  // --- Pulse routing ---

  @Override
  public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block,
      @Nullable Orientation orientation, boolean isMoving) {
    if (!canSurvive(state, level, pos)) {
      level.destroyBlock(pos, true);
      return;
    }
    // Only schedule — don't power on or notify neighbors yet.
    // The tick handles the rising edge so downstream relays fire one tick later,
    // producing a cascade wave instead of simultaneous triggering.
    if (!level.isClientSide() && !state.getValue(POWERED)
        && hasSignalOnNonFacingFaces(level, pos, state)
        && !((ServerLevel) level).getBlockTicks().hasScheduledTick(pos, this)) {
      // level.scheduleTick(pos, this, 1);
      level.scheduleTick(pos, this, state.getValue(PERIOD).period);
    }
  }

  @Override
  public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    if (!state.getValue(POWERED)) {
      // Rising edge: power on and notify downstream so the next relay schedules its tick.
      level.setBlock(pos, state.setValue(POWERED, true), Block.UPDATE_ALL);
      level.scheduleTick(pos, this, 1);
      level.updateNeighborsAt(pos, this);
    } else {

      Direction facing = state.getValue(FACING);
      BlockPos relative = pos.relative(facing);

      BlockState relativeState = level.getBlockState(relative);
      if (relativeState.getBlock() instanceof DispenserBlock dispenser) {
        ((DispenserAccessor) dispenser).more_useful_copper$doDispense(level, relativeState, relative);
      }

      // Falling edge: power off and notify downstream.
      level.setBlock(pos, state.setValue(POWERED, false), Block.UPDATE_ALL);
      level.updateNeighborsAt(pos, this);
    }
  }

  /// Returns `true` if any face other than FACING (or from below) has a signal.
  ///
  /// Uses `dir.getOpposite()` so we ask each neighbor what it emits *toward* this block. Relay A facing EAST has `getSignal(..., EAST) = 15`; relay B to relay A's east queries with dir=WEST →
  /// `getSignal(relayAPos, EAST)` correctly detects the output.
  private boolean hasSignalOnNonFacingFaces(Level level, BlockPos pos, BlockState state) {
    Direction facing = state.getValue(FACING);
    for (Direction dir : Direction.Plane.HORIZONTAL) {
      if (dir == facing) {
        continue;
      }
      if (level.getSignal(pos.relative(dir), dir.getOpposite()) > 0) {
        return true;
      }
    }
    return level.getSignal(pos.below(), Direction.UP) > 0;
  }

  // --- Redstone signal output ---

  @Override
  public boolean isSignalSource(BlockState state) {
    return true;
  }

  @Override
  public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
    return state.getValue(POWERED) && state.getValue(FACING) == direction ? 15 : 0;
  }

  @Override
  public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
    return getSignal(state, level, pos, direction);
  }

  // --- Interaction ---

  @Override
  protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
    if (!level.isClientSide()) {
      Direction next = state.getValue(FACING).getClockWise();
      level.setBlock(pos, state.setValue(FACING, next), Block.UPDATE_ALL);
      level.updateNeighborsAt(pos, this);
      level.playSound(null, pos, SoundEvents.STONE_BUTTON_CLICK_ON, SoundSource.BLOCKS, 0.3f, 0.6f);
    }
    return InteractionResult.SUCCESS;
  }
}
