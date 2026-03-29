package io.github.jason13official.more_useful_copper.impl.common.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.jason13official.more_useful_copper.api.common.block.IOxidizableBlock;
import io.github.jason13official.more_useful_copper.api.common.block.WaxableRegistry;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public class CopperPressurePlateBlock extends WeightedPressurePlateBlock implements IOxidizableBlock, SimpleWaterloggedBlock {

  public static final MapCodec<CopperPressurePlateBlock> CODEC = RecordCodecBuilder.mapCodec(
    instance -> instance.group(
      WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(CopperPressurePlateBlock::getAge),
      Codec.intRange(1, 1024).fieldOf("max_weight").forGetter(b -> b.maxWeight),
      BlockSetType.CODEC.fieldOf("block_set_type").forGetter(b -> b.type),
      propertiesCodec()
    ).apply(instance, (weatherState, maxWeight, type, props) -> new CopperPressurePlateBlock(maxWeight, props, type, weatherState))
  );

  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  private final WeatherState weatherState;
  private final int maxWeight;

  public CopperPressurePlateBlock(int maxWeight, Properties properties, BlockSetType type, WeatherState weatherState) {
    super(maxWeight, type, properties);
    this.weatherState = weatherState;
    this.maxWeight = maxWeight;
    this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.POWER, 0).setValue(WATERLOGGED, false));
  }

  @SuppressWarnings("unchecked")
  @Override
  public MapCodec<WeightedPressurePlateBlock> codec() {
    return (MapCodec<WeightedPressurePlateBlock>) (MapCodec<?>) CODEC;
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
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

  @Override
  protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    if (stack.is(ModItemTags.MANUAL_OXIDIZER) && this.weatherState != WeatherState.OXIDIZED) {
      return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    if (stack.is(ModItemTags.WAX_SCRAPER) && this.weatherState != WeatherState.UNAFFECTED) {
      return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    InteractionResult waxResult = WaxableRegistry.tryWaxing(state, level, pos, player, stack);
    if (waxResult != null) {
      return waxResult == InteractionResult.PASS ? ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION : ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
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
