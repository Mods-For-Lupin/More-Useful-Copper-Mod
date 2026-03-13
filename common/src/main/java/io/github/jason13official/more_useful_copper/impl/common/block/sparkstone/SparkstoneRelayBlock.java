package io.github.jason13official.more_useful_copper.impl.common.block.sparkstone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

/// Directional pulse router. Accepts a rising-edge signal on any non-FACING face and
/// re-emits a 1-tick pulse in the FACING direction. Right-click to rotate FACING clockwise.
public class SparkstoneRelayBlock extends Block {

  public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
  public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
  public static final EnumProperty<SparkstonePeriod> PERIOD = EnumProperty.create("period", SparkstonePeriod.class);

  protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);

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
    return this.defaultBlockState()
        .setValue(FACING, context.getHorizontalDirection().getOpposite());
  }

  @Override
  public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
      LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
    if (!canSurvive(state, level, pos)) {
      level.destroyBlock(pos, true);
    }
    return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
  }

  // --- Pulse routing ---

  @Override
  public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block,
      BlockPos fromPos, boolean isMoving) {
    if (!canSurvive(state, level, pos)) {
      level.destroyBlock(pos, true);
      return;
    }
    if (!level.isClientSide && !state.getValue(POWERED)
        && hasSignalOnNonFacingFaces(level, pos, state)) {
      level.setBlock(pos, state.setValue(POWERED, true), Block.UPDATE_ALL);
      level.scheduleTick(pos, this, state.getValue(PERIOD).period);
      level.updateNeighborsAt(pos, this);
    }
  }

  @Override
  public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    if (state.getValue(POWERED)) {
      level.setBlock(pos, state.setValue(POWERED, false), Block.UPDATE_ALL);
      level.updateNeighborsAt(pos, this);
    }
  }

  /// Returns `true` if any face other than FACING (or from below) has a signal.
  private boolean hasSignalOnNonFacingFaces(Level level, BlockPos pos, BlockState state) {
    Direction facing = state.getValue(FACING);
    for (Direction dir : Direction.Plane.HORIZONTAL) {
      if (dir == facing) continue;
      if (level.getSignal(pos.relative(dir), dir) > 0) return true;
    }
    return level.getSignal(pos.below(), Direction.DOWN) > 0;
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
  public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
      InteractionHand hand, BlockHitResult hit) {
    if (!level.isClientSide) {
      Direction next = state.getValue(FACING).getClockWise();
      level.setBlock(pos, state.setValue(FACING, next), 3);
      level.updateNeighborsAt(pos, this);
      level.playSound(null, pos, SoundEvents.STONE_BUTTON_CLICK_ON, SoundSource.BLOCKS, 0.3f, 0.6f);
    }
    return InteractionResult.sidedSuccess(level.isClientSide);
  }
}
