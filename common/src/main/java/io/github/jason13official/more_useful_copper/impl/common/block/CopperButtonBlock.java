package io.github.jason13official.more_useful_copper.impl.common.block;

import io.github.jason13official.more_useful_copper.api.common.block.IOxidizableBlock;
import io.github.jason13official.more_useful_copper.api.common.util.ButtonShapes;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CopperButtonBlock extends FaceAttachedHorizontalDirectionalBlock implements IOxidizableBlock {

  public static final int UNAFFECTED_PRESSED_TICKS = 30;
  public static final int EXPOSED_PRESSED_TICKS = 45;
  public static final int WEATHERED_PRESSED_TICKS = 60;
  public static final int OXIDIZED_PRESSED_TICKS = 90;

  public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

  private final WeatheringCopper.WeatherState weatherState;

  private final BlockSetType type;
  private final int ticksToStayPressed;
  private final boolean arrowsCanPress;

  public CopperButtonBlock(Properties properties, WeatherState weatherState, BlockSetType type, int ticksToStayPressed, boolean arrowsCanPress) {
    super(properties);
    this.weatherState = weatherState;
    this.type = type;
    this.ticksToStayPressed = ticksToStayPressed;
    this.arrowsCanPress = arrowsCanPress;

    this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(FACE, AttachFace.WALL));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(FACING, POWERED, FACE);
  }

  @Override
  public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    this.onRandomTick(state, level, pos, random);
  }

  @Override
  public boolean isRandomlyTicking(BlockState state) {
    return IOxidizableBlock.getNext(state.getBlock()).isPresent();
  }

  @Override
  public WeatheringCopper.WeatherState getAge() {
    return this.weatherState;
  }

  @Override
  public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    if (state.getValue(POWERED)) {
      return InteractionResult.CONSUME;
    } else {
      this.press(state, level, pos);
      this.playSound(player, level, pos, true);
      level.gameEvent(player, GameEvent.BLOCK_ACTIVATE, pos);
      return InteractionResult.sidedSuccess(level.isClientSide);
    }
  }

  public void press(BlockState state, Level level, BlockPos pos) {
    level.setBlock(pos, state.setValue(POWERED, true), 3);
    this.updateNeighbours(state, level, pos);
    level.scheduleTick(pos, this, this.ticksToStayPressed);
  }

  protected void playSound(@Nullable Player player, LevelAccessor level, BlockPos pos, boolean hitByArrow) {
    level.playSound(hitByArrow ? player : null, pos, this.getSound(hitByArrow), SoundSource.BLOCKS);
  }

  protected SoundEvent getSound(boolean isOn) {
    return isOn ? this.type.buttonClickOn() : this.type.buttonClickOff();
  }

  @Override
  public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
    if (!isMoving && !state.is(newState.getBlock())) {
      if (state.getValue(POWERED)) {
        this.updateNeighbours(state, level, pos);
      }

      super.onRemove(state, level, pos, newState, isMoving);
    }

  }

  @Override
  public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
    return blockState.getValue(POWERED) ? 15 : 0;
  }

  @Override
  public int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
    return blockState.getValue(POWERED) && getConnectedDirection(blockState) == side ? 15 : 0;
  }

  @Override
  public boolean isSignalSource(BlockState state) {
    return true;
  }

  @Override
  public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    if (state.getValue(POWERED)) {
      this.checkPressed(state, level, pos);
    }

  }

  @Override
  public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
    if (!level.isClientSide && this.arrowsCanPress && !(Boolean) state.getValue(POWERED)) {
      this.checkPressed(state, level, pos);
    }

  }

  protected void checkPressed(BlockState state, Level level, BlockPos pos) {
    AbstractArrow abstractarrow = this.arrowsCanPress ? level.getEntitiesOfClass(AbstractArrow.class, state.getShape(level, pos).bounds().move(pos)).stream().findFirst().orElse(null) : null;
    boolean flag = abstractarrow != null;
    boolean flag1 = state.getValue(POWERED);
    if (flag != flag1) {
      level.setBlock(pos, state.setValue(POWERED, flag), 3);
      this.updateNeighbours(state, level, pos);
      this.playSound(null, level, pos, flag);
      level.gameEvent(abstractarrow, flag ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
    }

    if (flag) {
      level.scheduleTick(new BlockPos(pos), this, this.ticksToStayPressed);
    }

  }

  private void updateNeighbours(BlockState state, Level level, BlockPos pos) {
    level.updateNeighborsAt(pos, this);
    level.updateNeighborsAt(pos.relative(getConnectedDirection(state).getOpposite()), this);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    Direction direction = state.getValue(FACING);
    boolean flag = state.getValue(POWERED);
    switch (state.getValue(FACE)) {
      case FLOOR:
        if (direction.getAxis() == Axis.X) {
          return flag ? ButtonShapes.PRESSED_FLOOR_AABB_X : ButtonShapes.FLOOR_AABB_X;
        }

        return flag ? ButtonShapes.PRESSED_FLOOR_AABB_Z : ButtonShapes.FLOOR_AABB_Z;
      case WALL:
        VoxelShape voxelshape = switch (direction) {
          case EAST -> flag ? ButtonShapes.PRESSED_EAST_AABB : ButtonShapes.EAST_AABB;
          case WEST -> flag ? ButtonShapes.PRESSED_WEST_AABB : ButtonShapes.WEST_AABB;
          case SOUTH -> flag ? ButtonShapes.PRESSED_SOUTH_AABB : ButtonShapes.SOUTH_AABB;
          case NORTH, UP, DOWN -> flag ? ButtonShapes.PRESSED_NORTH_AABB : ButtonShapes.NORTH_AABB;
        };

        return voxelshape;
      case CEILING:
      default:
        if (direction.getAxis() == Axis.X) {
          return flag ? ButtonShapes.PRESSED_CEILING_AABB_X : ButtonShapes.CEILING_AABB_X;
        } else {
          return flag ? ButtonShapes.PRESSED_CEILING_AABB_Z : ButtonShapes.CEILING_AABB_Z;
        }
    }
  }
}
