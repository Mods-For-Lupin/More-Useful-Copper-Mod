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
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/// Oscillating pulse source. Emits 1-tick redstone bursts at a configurable period. Right-click to cycle the oscillation period. Pauses when the support block is powered.
public class SparkstoneTorchBlock extends Block {

  public static final EnumProperty<SparkstonePeriod> PERIOD = EnumProperty.create("period", SparkstonePeriod.class);
  public static final BooleanProperty LIT = BlockStateProperties.LIT;

  protected static final VoxelShape SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);

  public SparkstoneTorchBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any()
        .setValue(PERIOD, SparkstonePeriod.MEDIUM)
        .setValue(LIT, false));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(PERIOD, LIT);
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
    return this.defaultBlockState();
  }

  @Override
  protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
    if (!canSurvive(state, level, pos)) {
      return Blocks.AIR.defaultBlockState();
    }
    return super.updateShape(state, level, ticks, pos, direction, neighborPos, neighborState, random);
  }

  // --- Oscillation lifecycle ---

  @Override
  public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
    if (!level.isClientSide()) {
      ((ServerLevel) level).scheduleTick(pos, this, state.getValue(PERIOD).period);
    }
  }

  @Override
  public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    if (isAttachedBlockPowered(level, pos)) {
      // Paused — ensure unlit
      if (state.getValue(LIT)) {
        level.setBlock(pos, state.setValue(LIT, false), Block.UPDATE_ALL);
        level.updateNeighborsAt(pos, this);
      }
      return;
    }

    int period = state.getValue(PERIOD).period;

    if (state.getValue(LIT)) {
      // Turn off, schedule turn-on after (period - 1) ticks
      level.setBlock(pos, state.setValue(LIT, false), Block.UPDATE_ALL);
      level.updateNeighborsAt(pos, this);
      level.scheduleTick(pos, this, period - 1);
    } else {
      // Turn on, schedule turn-off after 1 tick
      level.setBlock(pos, state.setValue(LIT, true), Block.UPDATE_ALL);
      level.updateNeighborsAt(pos, this);
      level.scheduleTick(pos, this, 1);
    }
  }

  @Override
  public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block,
      @Nullable Orientation orientation, boolean isMoving) {
    if (!canSurvive(state, level, pos)) {
      level.destroyBlock(pos, true);
      return;
    }
    if (!level.isClientSide() && !isAttachedBlockPowered(level, pos)
        && !((ServerLevel) level).getBlockTicks().hasScheduledTick(pos, this)) {
      ((ServerLevel) level).scheduleTick(pos, this, state.getValue(PERIOD).period);
    }
  }

  /// Returns `true` if the block directly below is receiving a neighbor signal (redstone pause).
  protected boolean isAttachedBlockPowered(Level level, BlockPos pos) {
    return level.hasNeighborSignal(pos.below());
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
    return direction == Direction.DOWN ? getSignal(state, level, pos, direction) : 0;
  }

  // --- Interaction ---

  @Override
  protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
    if (!level.isClientSide()) {
      SparkstonePeriod next = state.getValue(PERIOD).next();
      level.setBlock(pos, state.setValue(PERIOD, next), Block.UPDATE_ALL);
      level.playSound(null, pos, SoundEvents.STONE_BUTTON_CLICK_ON, SoundSource.BLOCKS, 0.3f, 0.6f);
    }
    return InteractionResult.SUCCESS;
  }

  // --- Visual ---

  @Override
  public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
    if (!state.getValue(LIT)) {
      return;
    }
    double x = pos.getX() + 0.5;
    double y = pos.getY() + 0.7;
    double z = pos.getZ() + 0.5;
    level.addParticle(ParticleTypes.END_ROD, x, y, z, 0.0, 0.0, 0.0);
  }
}
