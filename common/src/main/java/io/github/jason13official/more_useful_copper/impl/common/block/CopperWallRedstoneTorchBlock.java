package io.github.jason13official.more_useful_copper.impl.common.block;

import io.github.jason13official.more_useful_copper.api.common.block.IOxidizableBlock;
import io.github.jason13official.more_useful_copper.api.common.block.WaxableRegistry;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneWallTorchBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public class CopperWallRedstoneTorchBlock extends RedstoneWallTorchBlock implements IOxidizableBlock, SimpleWaterloggedBlock {

  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  private final WeatherState weatherState;

  public CopperWallRedstoneTorchBlock(Properties properties, WeatherState weatherState) {
    super(properties);
    this.weatherState = weatherState;
    this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(WATERLOGGED);
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    BlockState state = super.getStateForPlacement(context);
    if (state == null) {
      return null;
    }
    FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
    return state.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
  }

  @Override
  protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
    if (state.getValue(WATERLOGGED)) {
      ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
    }
    return super.updateShape(state, level, ticks, pos, direction, neighborPos, neighborState, random);
  }

  @Override
  public FluidState getFluidState(BlockState state) {
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  @Override
  protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    if (stack.is(ModItemTags.MANUAL_OXIDIZER) && this.weatherState != WeatherState.OXIDIZED) {
      return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    if (stack.is(ModItemTags.WAX_SCRAPER)) {
      return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    InteractionResult waxResult = WaxableRegistry.tryWaxing(state, level, pos, player, stack);
    if (waxResult != null) {
      return waxResult == InteractionResult.PASS ? InteractionResult.TRY_WITH_EMPTY_HAND : InteractionResult.SUCCESS;
    }

    return InteractionResult.TRY_WITH_EMPTY_HAND;
  }

  @Override
  public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    this.changeOverTime(state, level, pos, random);
  }

  @Override
  public boolean isRandomlyTicking(BlockState state) {
    return IOxidizableBlock.getNext(state.getBlock()).isPresent();
  }

  @Override
  public WeatherState getAge() {
    return this.weatherState;
  }
}
