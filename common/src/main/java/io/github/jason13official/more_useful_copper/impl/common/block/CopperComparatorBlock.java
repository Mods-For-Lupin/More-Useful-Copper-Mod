package io.github.jason13official.more_useful_copper.impl.common.block;

import io.github.jason13official.more_useful_copper.api.common.block.IOxidizableBlock;
import io.github.jason13official.more_useful_copper.api.common.block.WaxableRegistry;
import io.github.jason13official.more_useful_copper.impl.common.block.entity.CopperComparatorBlockEntity;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModItemTags;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.ticks.TickPriority;
import org.jetbrains.annotations.Nullable;

public class CopperComparatorBlock extends DiodeBlock implements IOxidizableBlock, SimpleWaterloggedBlock, EntityBlock {

  public static final EnumProperty<ComparatorMode> MODE = BlockStateProperties.MODE_COMPARATOR;
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  private final WeatherState weatherState;

  public CopperComparatorBlock(Properties properties, WeatherState weatherState) {
    super(properties);
    this.weatherState = weatherState;
    this.registerDefaultState(
        this.stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(POWERED, false)
            .setValue(MODE, ComparatorMode.COMPARE)
            .setValue(WATERLOGGED, false)
    );
  }

  // --- IOxidizableBlock ---

  @Override
  public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    this.onRandomTick(state, level, pos, random);
  }

  @Override
  public boolean isRandomlyTicking(BlockState state) {
    return IOxidizableBlock.getNext(state.getBlock()).isPresent();
  }

  // --- Waterlogging ---

  @Override
  public WeatherState getAge() {
    return this.weatherState;
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
    return super.getStateForPlacement(context).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
  }

  @Override
  public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
    if (state.getValue(WATERLOGGED)) {
      level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
    }
    return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
  }

  // --- EntityBlock ---

  @Override
  public FluidState getFluidState(BlockState state) {
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  // --- Comparator logic (adapted from ComparatorBlock for CopperComparatorBlockEntity) ---

  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new CopperComparatorBlockEntity(pos, state);
  }

  @Override
  protected int getDelay(BlockState state) {
    return 2;
  }

  @Override
  protected int getOutputSignal(BlockGetter level, BlockPos pos, BlockState state) {
    BlockEntity blockEntity = level.getBlockEntity(pos);
    return blockEntity instanceof CopperComparatorBlockEntity be ? be.getOutputSignal() : 0;
  }

  private int calculateOutputSignal(Level level, BlockPos pos, BlockState state) {
    int i = this.getInputSignal(level, pos, state);
    if (i == 0) {
      return 0;
    }
    int j = this.getAlternateSignal(level, pos, state);
    if (j > i) {
      return 0;
    }
    return state.getValue(MODE) == ComparatorMode.SUBTRACT ? i - j : i;
  }

  @Override
  protected boolean shouldTurnOn(Level level, BlockPos pos, BlockState state) {
    int i = this.getInputSignal(level, pos, state);
    if (i == 0) {
      return false;
    }
    int j = this.getAlternateSignal(level, pos, state);
    return i > j || (i == j && state.getValue(MODE) == ComparatorMode.COMPARE);
  }

  @Override
  protected int getInputSignal(Level level, BlockPos pos, BlockState state) {
    int i = super.getInputSignal(level, pos, state);
    Direction direction = state.getValue(FACING);
    BlockPos blockPos = pos.relative(direction);
    BlockState blockState = level.getBlockState(blockPos);
    if (blockState.hasAnalogOutputSignal()) {
      i = blockState.getAnalogOutputSignal(level, blockPos);
    } else if (i < 15 && blockState.isRedstoneConductor(level, blockPos)) {
      blockPos = blockPos.relative(direction);
      blockState = level.getBlockState(blockPos);
      ItemFrame itemFrame = this.getItemFrame(level, direction, blockPos);
      int j = Math.max(
          itemFrame == null ? Integer.MIN_VALUE : itemFrame.getAnalogOutput(),
          blockState.hasAnalogOutputSignal() ? blockState.getAnalogOutputSignal(level, blockPos) : Integer.MIN_VALUE
      );
      if (j != Integer.MIN_VALUE) {
        i = j;
      }
    }
    return i;
  }

  @Nullable
  private ItemFrame getItemFrame(Level level, Direction facing, BlockPos pos) {
    List<ItemFrame> list = level.getEntitiesOfClass(
        ItemFrame.class,
        new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1),
        itemFrame -> itemFrame != null && itemFrame.getDirection() == facing
    );
    return list.size() == 1 ? list.get(0) : null;
  }

  @Override
  protected void checkTickOnNeighbor(Level level, BlockPos pos, BlockState state) {
    if (!level.getBlockTicks().willTickThisTick(pos, this)) {
      int i = this.calculateOutputSignal(level, pos, state);
      BlockEntity blockEntity = level.getBlockEntity(pos);
      int j = blockEntity instanceof CopperComparatorBlockEntity be ? be.getOutputSignal() : 0;
      if (i != j || state.getValue(POWERED) != this.shouldTurnOn(level, pos, state)) {
        TickPriority tickPriority = this.shouldPrioritize(level, pos, state) ? TickPriority.HIGH : TickPriority.NORMAL;
        level.scheduleTick(pos, this, 2, tickPriority);
      }
    }
  }

  protected void refreshOutputState(Level level, BlockPos pos, BlockState state) {
    int i = this.calculateOutputSignal(level, pos, state);
    BlockEntity blockEntity = level.getBlockEntity(pos);
    int j = 0;
    if (blockEntity instanceof CopperComparatorBlockEntity be) {
      j = be.getOutputSignal();
      be.setOutputSignal(i);
    }
    if (j != i || state.getValue(MODE) == ComparatorMode.COMPARE) {
      boolean bl = this.shouldTurnOn(level, pos, state);
      boolean bl2 = state.getValue(POWERED);
      if (bl2 && !bl) {
        level.setBlock(pos, state.setValue(POWERED, false), 2);
      } else if (!bl2 && bl) {
        level.setBlock(pos, state.setValue(POWERED, true), 2);
      }
      this.updateNeighborsInFront(level, pos, state);
    }
  }

  @Override
  public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    this.refreshOutputState(level, pos, state);
  }

  @Override
  public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
    super.triggerEvent(state, level, pos, id, param);
    BlockEntity blockEntity = level.getBlockEntity(pos);
    return blockEntity != null && blockEntity.triggerEvent(id, param);
  }

  // --- Interaction ---

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(FACING, MODE, POWERED, WATERLOGGED);
  }

  @Override
  public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    if (!player.getAbilities().mayBuild) {
      return InteractionResult.PASS;
    }

    ItemStack stackInHand = player.getItemInHand(hand);

    // oxidize
    if (stackInHand.is(ModItemTags.MANUAL_OXIDIZER) && this.weatherState != WeatherState.OXIDIZED) {
      return InteractionResult.PASS;
    }

    // remove wax / scrape oxidation
    if (stackInHand.is(ModItemTags.WAX_SCRAPER)) {
      return InteractionResult.PASS;
    }

    // apply wax
    InteractionResult waxResult = WaxableRegistry.tryWaxing(state, level, pos, player, stackInHand);
    if (waxResult != null) {
      return waxResult;
    }

    // cycle mode
    state = state.cycle(MODE);
    float f = state.getValue(MODE) == ComparatorMode.SUBTRACT ? 0.55F : 0.5F;
    level.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, f);
    level.setBlock(pos, state, 2);
    this.refreshOutputState(level, pos, state);
    return InteractionResult.sidedSuccess(level.isClientSide);
  }
}
