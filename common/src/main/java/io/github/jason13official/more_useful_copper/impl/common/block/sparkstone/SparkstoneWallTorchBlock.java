package io.github.jason13official.more_useful_copper.impl.common.block.sparkstone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/// Wall-mounted variant of [SparkstoneTorchBlock]. Paired with [SparkstoneTorchBlock] via `StandingAndWallBlockItem` so both share one item.
public class SparkstoneWallTorchBlock extends Block {

  public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
  public static final EnumProperty<SparkstonePeriod> PERIOD = EnumProperty.create("period", SparkstonePeriod.class);
  public static final BooleanProperty LIT = BlockStateProperties.LIT;

  protected static final VoxelShape SHAPE_NORTH = Block.box(5.5, 3.0, 11.0, 10.5, 13.0, 16.0);
  protected static final VoxelShape SHAPE_SOUTH = Block.box(5.5, 3.0, 0.0, 10.5, 13.0, 5.0);
  protected static final VoxelShape SHAPE_WEST = Block.box(11.0, 3.0, 5.5, 16.0, 13.0, 10.5);
  protected static final VoxelShape SHAPE_EAST = Block.box(0.0, 3.0, 5.5, 5.0, 13.0, 10.5);

  public SparkstoneWallTorchBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any()
        .setValue(FACING, Direction.NORTH)
        .setValue(PERIOD, SparkstonePeriod.MEDIUM)
        .setValue(LIT, false));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(FACING, PERIOD, LIT);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return switch (state.getValue(FACING)) {
      case SOUTH -> SHAPE_SOUTH;
      case WEST -> SHAPE_WEST;
      case EAST -> SHAPE_EAST;
      default -> SHAPE_NORTH;
    };
  }

  // --- Placement & survival ---

  @Override
  public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
    Direction facing = state.getValue(FACING);
    BlockPos attachedPos = pos.relative(facing.getOpposite());
    return level.getBlockState(attachedPos).isFaceSturdy(level, attachedPos, facing);
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    Direction dir = context.getHorizontalDirection();
    BlockState state = this.defaultBlockState().setValue(FACING, dir);
    return canSurvive(state, context.getLevel(), context.getClickedPos()) ? state : null;
  }

  @Override
  public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
      LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
    if (!canSurvive(state, level, pos)) {
      level.destroyBlock(pos, true);
    }
    return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
  }

  // --- Oscillation lifecycle ---

  @Override
  public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
    if (!level.isClientSide) {
      level.scheduleTick(pos, this, state.getValue(PERIOD).period);
    }
  }

  @Override
  public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    if (isAttachedBlockPowered(level, pos, state)) {
      if (state.getValue(LIT)) {
        level.setBlock(pos, state.setValue(LIT, false), Block.UPDATE_ALL);
        level.updateNeighborsAt(pos, this);
      }
      return;
    }

    int period = state.getValue(PERIOD).period;

    if (state.getValue(LIT)) {
      level.setBlock(pos, state.setValue(LIT, false), Block.UPDATE_ALL);
      level.updateNeighborsAt(pos, this);
      level.scheduleTick(pos, this, period - 1);
    } else {
      level.setBlock(pos, state.setValue(LIT, true), Block.UPDATE_ALL);
      level.updateNeighborsAt(pos, this);
      level.scheduleTick(pos, this, 1);
    }
  }

  @Override
  public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block,
      BlockPos fromPos, boolean isMoving) {
    if (!canSurvive(state, level, pos)) {
      level.destroyBlock(pos, true);
      return;
    }
    if (!isAttachedBlockPowered(level, pos, state)
        && !level.getBlockTicks().hasScheduledTick(pos, this)) {
      level.scheduleTick(pos, this, state.getValue(PERIOD).period);
    }
  }

  /// Returns `true` if the wall block this torch is attached to is powered.
  private boolean isAttachedBlockPowered(Level level, BlockPos pos, BlockState state) {
    Direction facing = state.getValue(FACING);
    return level.hasNeighborSignal(pos.relative(facing.getOpposite()));
  }

  // --- Redstone signal output ---

  @Override
  public boolean isSignalSource(BlockState state) {
    return true;
  }

  @Override
  public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
    return state.getValue(LIT) && direction != Direction.UP ? 15 : 0;
  }

  @Override
  public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
    Direction facing = state.getValue(FACING);
    return direction == facing.getOpposite() ? getSignal(state, level, pos, direction) : 0;
  }

  // --- Interaction ---

  @Override
  protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
    if (!level.isClientSide) {
      SparkstonePeriod next = state.getValue(PERIOD).next();
      level.setBlock(pos, state.setValue(PERIOD, next), 3);
      level.playSound(null, pos, SoundEvents.STONE_BUTTON_CLICK_ON, SoundSource.BLOCKS, 0.3f, 0.6f);
    }
    return InteractionResult.sidedSuccess(level.isClientSide);
  }

  // --- Visual ---

  @Override
  public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
    if (!state.getValue(LIT)) {
      return;
    }
    Direction facing = state.getValue(FACING);
    double x = pos.getX() + 0.5 + 0.27 * facing.getStepX();
    double y = pos.getY() + 0.7;
    double z = pos.getZ() + 0.5 + 0.27 * facing.getStepZ();
    level.addParticle(ParticleTypes.END_ROD, x, y, z, 0.0, 0.0, 0.0);
  }
}
