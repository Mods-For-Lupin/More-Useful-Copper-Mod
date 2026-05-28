package io.github.jason13official.more_useful_copper.impl.common.block;

import io.github.jason13official.more_useful_copper.api.common.util.BellShapes;
import io.github.jason13official.more_useful_copper.impl.common.block.entity.CopperBellBlockEntity;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModTiles;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModItemTags;
import org.jetbrains.annotations.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BellAttachType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CopperBellBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {

  public static final int EVENT_BELL_RING = 1;

  public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
  public static final EnumProperty<BellAttachType> ATTACHMENT = BlockStateProperties.BELL_ATTACHMENT;
  public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
  public static final BooleanProperty WAXED = BooleanProperty.create("waxed");
  public static final IntegerProperty OXIDIZATION = IntegerProperty.create("oxidization", 0, 3);

  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  public CopperBellBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(
        this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(ATTACHMENT, BellAttachType.FLOOR).setValue(POWERED, false).setValue(WAXED, false).setValue(OXIDIZATION, 0)
            .setValue(WATERLOGGED, false));
  }

  private static Direction getConnectedDirection(BlockState state) {
    switch (state.getValue(ATTACHMENT)) {
      case FLOOR -> {
        return Direction.UP;
      }
      case CEILING -> {
        return Direction.DOWN;
      }
      default -> {
        return state.getValue(FACING).getOpposite();
      }
    }
  }

  @Nullable
  @SuppressWarnings("unchecked")
  protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> serverType, BlockEntityType<E> clientType,
      BlockEntityTicker<? super E> ticker) {
    return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
  }

  public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
    boolean flag = level.hasNeighborSignal(pos);
    if (flag != state.getValue(POWERED)) {
      if (flag) {
        this.attemptToRing(level, pos, null);
      }

      level.setBlock(pos, state.setValue(POWERED, flag), 3);
    }

  }

  public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
    Entity entity = projectile.getOwner();
    Player player = entity instanceof Player ? (Player) entity : null;
    this.onHit(level, state, hit, player, true);
  }

  @Override
  protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    EquipmentSlot slot = hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;

    if (stack.is(ModItemTags.WAX_SCRAPER)) {
      if (state.getValue(WAXED)) {
        if (!level.isClientSide()) {
          level.playSound(null, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
          level.levelEvent(null, LevelEvent.PARTICLES_WAX_OFF, pos, 0);
          level.setBlock(pos, state.setValue(WAXED, false), Block.UPDATE_ALL_IMMEDIATE);
          level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
          if (!player.getAbilities().instabuild) {
            stack.hurtAndBreak(1, player, slot);
          }
        }
        return InteractionResult.SUCCESS;
      } else if (state.getValue(OXIDIZATION) > 0) {
        if (!level.isClientSide()) {
          BlockState newState = state.setValue(OXIDIZATION, state.getValue(OXIDIZATION) - 1);
          level.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);
          level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
          level.levelEvent(null, LevelEvent.PARTICLES_SCRAPE, pos, 0);
          level.playSound(null, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
          if (!player.getAbilities().instabuild) {
            stack.hurtAndBreak(1, player, slot);
          }
        }
        return InteractionResult.SUCCESS;
      }
      return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    if (stack.is(ModItemTags.MANUAL_OXIDIZER) && !state.getValue(WAXED) && state.getValue(OXIDIZATION) < 3) {
      if (!level.isClientSide()) {
        BlockState newState = state.setValue(OXIDIZATION, state.getValue(OXIDIZATION) + 1);
        level.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
        level.levelEvent(null, LevelEvent.PARTICLES_SCRAPE, pos, 0);
        level.playSound(null, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (!player.getAbilities().instabuild) {
          stack.hurtAndBreak(1, player, slot);
        }
      }
      return InteractionResult.SUCCESS;
    }

    if (stack.is(Items.HONEYCOMB) && !state.getValue(WAXED)) {
      if (!level.isClientSide()) {
        BlockState newState = state.setValue(WAXED, true);
        level.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
        level.levelEvent(null, LevelEvent.PARTICLES_AND_SOUND_WAX_ON, pos, 0);
        if (!player.getAbilities().instabuild) {
          stack.shrink(1);
        }
      }
      return InteractionResult.SUCCESS;
    }

    return InteractionResult.TRY_WITH_EMPTY_HAND;
  }

  @Override
  protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
    return this.onHit(level, state, hit, player, true) ? InteractionResult.SUCCESS : InteractionResult.PASS;
  }

  public boolean onHit(Level level, BlockState state, BlockHitResult result, Player player, boolean canRingBell) {
    Direction direction = result.getDirection();
    BlockPos blockpos = result.getBlockPos();
    boolean flag = !canRingBell || this.isProperHit(state, direction, result.getLocation().y - (double) blockpos.getY());
    if (flag) {
      boolean flag1 = this.attemptToRing(player, level, blockpos, direction);
      if (flag1 && player != null) {
        player.awardStat(Stats.BELL_RING);
      }

      return true;
    } else {
      return false;
    }
  }

  private boolean isProperHit(BlockState pos, Direction p_direction, double distanceY) {
    if (p_direction.getAxis() != Axis.Y && !(distanceY > (double) 0.8124F)) {
      Direction direction = pos.getValue(FACING);
      BellAttachType bellattachtype = pos.getValue(ATTACHMENT);
      switch (bellattachtype) {
        case FLOOR:
          return direction.getAxis() == p_direction.getAxis();
        case SINGLE_WALL:
        case DOUBLE_WALL:
          return direction.getAxis() != p_direction.getAxis();
        case CEILING:
          return true;
        default:
          return false;
      }
    } else {
      return false;
    }
  }

  public boolean attemptToRing(Level level, BlockPos pos, Direction direction) {
    return this.attemptToRing(null, level, pos, direction);
  }

  public boolean attemptToRing(Entity entity, Level level, BlockPos pos, Direction direction) {
    BlockEntity blockentity = level.getBlockEntity(pos);
    if (!level.isClientSide() && blockentity instanceof CopperBellBlockEntity) {
      if (direction == null) {
        direction = level.getBlockState(pos).getValue(FACING);
      }

      ((CopperBellBlockEntity) blockentity).onHit(direction);
      level.playSound(null, pos, SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 2.0F, 1.0F);
      level.gameEvent(entity, GameEvent.BLOCK_CHANGE, pos);
      return true;
    } else {
      return false;
    }
  }

  private VoxelShape getVoxelShape(BlockState state) {
    Direction direction = state.getValue(FACING);
    BellAttachType bellattachtype = state.getValue(ATTACHMENT);
    if (bellattachtype == BellAttachType.FLOOR) {
      return direction != Direction.NORTH && direction != Direction.SOUTH ? BellShapes.EAST_WEST_FLOOR_SHAPE : BellShapes.NORTH_SOUTH_FLOOR_SHAPE;
    } else if (bellattachtype == BellAttachType.CEILING) {
      return BellShapes.CEILING_SHAPE;
    } else if (bellattachtype != BellAttachType.DOUBLE_WALL) {
      if (direction == Direction.NORTH) {
        return BellShapes.TO_NORTH;
      } else if (direction == Direction.SOUTH) {
        return BellShapes.TO_SOUTH;
      } else {
        return direction == Direction.EAST ? BellShapes.TO_EAST : BellShapes.TO_WEST;
      }
    } else {
      return direction != Direction.NORTH && direction != Direction.SOUTH ? BellShapes.EAST_WEST_BETWEEN : BellShapes.NORTH_SOUTH_BETWEEN;
    }
  }

  public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return this.getVoxelShape(state);
  }

  public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return this.getVoxelShape(state);
  }

  @SuppressWarnings("deprecation")
  public RenderShape getRenderShape(BlockState state) {
    return RenderShape.MODEL;
  }

  public BlockState getStateForPlacement(BlockPlaceContext context) {
    Direction direction = context.getClickedFace();
    BlockPos blockpos = context.getClickedPos();
    Level level = context.getLevel();
    Axis direction$axis = direction.getAxis();
    boolean waterlogged = level.getFluidState(blockpos).getType() == Fluids.WATER;
    if (direction$axis == Axis.Y) {
      BlockState blockstate = this.defaultBlockState().setValue(ATTACHMENT, direction == Direction.DOWN ? BellAttachType.CEILING : BellAttachType.FLOOR)
          .setValue(FACING, context.getHorizontalDirection()).setValue(WATERLOGGED, waterlogged);
      if (blockstate.canSurvive(context.getLevel(), blockpos)) {
        return blockstate;
      }
    } else {
      boolean flag = direction$axis == Axis.X && level.getBlockState(blockpos.west()).isFaceSturdy(level, blockpos.west(), Direction.EAST) && level.getBlockState(blockpos.east())
          .isFaceSturdy(level, blockpos.east(), Direction.WEST) || direction$axis == Axis.Z && level.getBlockState(blockpos.north()).isFaceSturdy(level, blockpos.north(), Direction.SOUTH)
          && level.getBlockState(blockpos.south()).isFaceSturdy(level, blockpos.south(), Direction.NORTH);
      BlockState blockstate1 = this.defaultBlockState().setValue(FACING, direction.getOpposite()).setValue(ATTACHMENT, flag ? BellAttachType.DOUBLE_WALL : BellAttachType.SINGLE_WALL)
          .setValue(WATERLOGGED, waterlogged);
      if (blockstate1.canSurvive(context.getLevel(), context.getClickedPos())) {
        return blockstate1;
      }

      boolean flag1 = level.getBlockState(blockpos.below()).isFaceSturdy(level, blockpos.below(), Direction.UP);
      blockstate1 = blockstate1.setValue(ATTACHMENT, flag1 ? BellAttachType.FLOOR : BellAttachType.CEILING);
      if (blockstate1.canSurvive(context.getLevel(), context.getClickedPos())) {
        return blockstate1;
      }
    }

    return null;
  }

  @Override
  protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
    if (state.getValue(WATERLOGGED)) {
      ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
    }

    BellAttachType bellattachtype = state.getValue(ATTACHMENT);
    Direction direction = getConnectedDirection(state).getOpposite();
    if (direction == directionToNeighbour && !state.canSurvive(level, pos) && bellattachtype != BellAttachType.DOUBLE_WALL) {
      return Blocks.AIR.defaultBlockState();
    } else {
      if (directionToNeighbour.getAxis() == state.getValue(FACING).getAxis()) {
        if (bellattachtype == BellAttachType.DOUBLE_WALL && !neighbourState.isFaceSturdy(level, neighbourPos, directionToNeighbour)) {
          return state.setValue(ATTACHMENT, BellAttachType.SINGLE_WALL).setValue(FACING, directionToNeighbour.getOpposite());
        }

        if (bellattachtype == BellAttachType.SINGLE_WALL && direction.getOpposite() == directionToNeighbour && neighbourState.isFaceSturdy(level, neighbourPos, state.getValue(FACING))) {
          return state.setValue(ATTACHMENT, BellAttachType.DOUBLE_WALL);
        }
      }

      return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }
  }

  public FluidState getFluidState(BlockState state) {
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
    Direction direction = getConnectedDirection(state).getOpposite();
    return direction == Direction.UP ? Block.canSupportCenter(level, pos.above(), Direction.DOWN) : FaceAttachedHorizontalDirectionalBlock.canAttach(level, pos, direction);
  }

  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(FACING, ATTACHMENT, POWERED, WAXED, OXIDIZATION, WATERLOGGED);
  }

  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new CopperBellBlockEntity(pos, state);
  }

  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
    return createTickerHelper(blockEntityType, ModTiles.COPPER_BELL, level.isClientSide() ? CopperBellBlockEntity::clientTick : CopperBellBlockEntity::serverTick);
  }

  public boolean isRandomlyTicking(BlockState state) {
    return !state.getValue(WAXED) && state.getValue(OXIDIZATION) < 3;
  }

  public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    if (random.nextFloat() < 0.05688889F) {
      this.applyOxidation(state, level, pos, random);
    }
  }

  private void applyOxidation(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    int currentAge = state.getValue(OXIDIZATION);
    int moreOxidized = 0;
    int sameOrLess = 0;

    for (BlockPos nearPos : BlockPos.withinManhattan(pos, 4, 4, 4)) {
      if (nearPos.distManhattan(pos) > 4) {
        break;
      }
      if (nearPos.equals(pos)) {
        continue;
      }
      BlockState nearState = level.getBlockState(nearPos);
      Block nearBlock = nearState.getBlock();
      if (nearBlock instanceof CopperBellBlock nearBell) {
        int nearAge = nearState.getValue(OXIDIZATION);
        if (nearAge < currentAge) {
          return;
        }
        if (nearAge > currentAge) {
          moreOxidized++;
        } else {
          sameOrLess++;
        }
      } else if (nearBlock instanceof ChangeOverTimeBlock<?> ctb) {
        int nearOrdinal = ctb.getAge().ordinal();
        if (nearOrdinal < currentAge) {
          return;
        }
        if (nearOrdinal > currentAge) {
          moreOxidized++;
        } else {
          sameOrLess++;
        }
      }
    }

    float f = (float) (moreOxidized + 1) / (float) (moreOxidized + sameOrLess + 1);
    float chance = f * f * (currentAge == 0 ? 0.75F : 1.0F);
    if (random.nextFloat() < chance) {
      level.setBlock(pos, state.setValue(OXIDIZATION, currentAge + 1), Block.UPDATE_ALL_IMMEDIATE);
    }
  }

  public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
    super.triggerEvent(state, level, pos, id, param);
    BlockEntity blockEntity = level.getBlockEntity(pos);
    return blockEntity != null && blockEntity.triggerEvent(id, param);
  }

  @Override
  protected boolean isPathfindable(BlockState state, PathComputationType type) {
    return false;
  }
}
