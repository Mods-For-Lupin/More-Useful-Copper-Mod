package io.github.jason13official.more_useful_copper.impl.common.block;

import io.github.jason13official.more_useful_copper.api.common.block.IOxidizableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class CopperLeverBlock extends LeverBlock implements IOxidizableBlock {

  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  private final WeatherState weatherState;

  public CopperLeverBlock(Properties properties, WeatherState weatherState) {
    super(properties);
    this.weatherState = weatherState;
    this.registerDefaultState(
        this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(FACE, AttachFace.WALL).setValue(WATERLOGGED, false));
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(WATERLOGGED);
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

  @Override
  public FluidState getFluidState(BlockState state) {
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    this.onRandomTick(state, level, pos, random);
  }

  public boolean isRandomlyTicking(BlockState state) {
    return IOxidizableBlock.getNext(state.getBlock()).isPresent();
  }

  public WeatheringCopper.WeatherState getAge() {
    return this.weatherState;
  }
}
