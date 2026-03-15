package io.github.jason13official.more_useful_copper.impl.common.registry;

import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperBellBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperButtonBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperComparatorBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperLeverBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperRedstoneDustBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperRedstoneTorchBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperRepeaterBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperWallRedstoneTorchBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.GardenStakeBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedButtonBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedComparatorBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedLeverBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedRedstoneDustBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedRedstoneTorchBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedRepeaterBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedWallRedstoneTorchBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.sparkstone.SparkstoneRelayBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.sparkstone.SparkstoneTorchBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.sparkstone.SparkstoneWallTorchBlock;
import io.github.jason13official.more_useful_copper.platform.Services;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {

  public static CopperBellBlock COPPER_BELL;

  public static CopperButtonBlock COPPER_BUTTON;
  public static CopperButtonBlock EXPOSED_COPPER_BUTTON;
  public static CopperButtonBlock WEATHERED_COPPER_BUTTON;
  public static CopperButtonBlock OXIDIZED_COPPER_BUTTON;

  public static Block WAXED_COPPER_BUTTON;
  public static Block WAXED_EXPOSED_COPPER_BUTTON;
  public static Block WAXED_WEATHERED_COPPER_BUTTON;
  public static Block WAXED_OXIDIZED_COPPER_BUTTON;

  public static CopperComparatorBlock COPPER_COMPARATOR;
  public static CopperComparatorBlock EXPOSED_COPPER_COMPARATOR;
  public static CopperComparatorBlock WEATHERED_COPPER_COMPARATOR;
  public static CopperComparatorBlock OXIDIZED_COPPER_COMPARATOR;

  public static Block WAXED_COPPER_COMPARATOR;
  public static Block WAXED_EXPOSED_COPPER_COMPARATOR;
  public static Block WAXED_WEATHERED_COPPER_COMPARATOR;
  public static Block WAXED_OXIDIZED_COPPER_COMPARATOR;

  public static CopperRedstoneDustBlock COPPER_REDSTONE_DUST;
  public static CopperRedstoneDustBlock EXPOSED_COPPER_REDSTONE_DUST;
  public static CopperRedstoneDustBlock WEATHERED_COPPER_REDSTONE_DUST;
  public static CopperRedstoneDustBlock OXIDIZED_COPPER_REDSTONE_DUST;

  public static Block WAXED_COPPER_REDSTONE_DUST;
  public static Block WAXED_EXPOSED_COPPER_REDSTONE_DUST;
  public static Block WAXED_WEATHERED_COPPER_REDSTONE_DUST;
  public static Block WAXED_OXIDIZED_COPPER_REDSTONE_DUST;

  public static CopperLeverBlock COPPER_LEVER;
  public static CopperLeverBlock EXPOSED_COPPER_LEVER;
  public static CopperLeverBlock WEATHERED_COPPER_LEVER;
  public static CopperLeverBlock OXIDIZED_COPPER_LEVER;

  public static Block WAXED_COPPER_LEVER;
  public static Block WAXED_EXPOSED_COPPER_LEVER;
  public static Block WAXED_WEATHERED_COPPER_LEVER;
  public static Block WAXED_OXIDIZED_COPPER_LEVER;

  public static CopperRedstoneTorchBlock COPPER_REDSTONE_TORCH;
  public static CopperRedstoneTorchBlock EXPOSED_COPPER_REDSTONE_TORCH;
  public static CopperRedstoneTorchBlock WEATHERED_COPPER_REDSTONE_TORCH;
  public static CopperRedstoneTorchBlock OXIDIZED_COPPER_REDSTONE_TORCH;

  public static Block WAXED_COPPER_REDSTONE_TORCH;
  public static Block WAXED_EXPOSED_COPPER_REDSTONE_TORCH;
  public static Block WAXED_WEATHERED_COPPER_REDSTONE_TORCH;
  public static Block WAXED_OXIDIZED_COPPER_REDSTONE_TORCH;

  public static CopperWallRedstoneTorchBlock COPPER_WALL_REDSTONE_TORCH;
  public static CopperWallRedstoneTorchBlock EXPOSED_COPPER_WALL_REDSTONE_TORCH;
  public static CopperWallRedstoneTorchBlock WEATHERED_COPPER_WALL_REDSTONE_TORCH;
  public static CopperWallRedstoneTorchBlock OXIDIZED_COPPER_WALL_REDSTONE_TORCH;

  public static Block WAXED_COPPER_WALL_REDSTONE_TORCH;
  public static Block WAXED_EXPOSED_COPPER_WALL_REDSTONE_TORCH;
  public static Block WAXED_WEATHERED_COPPER_WALL_REDSTONE_TORCH;
  public static Block WAXED_OXIDIZED_COPPER_WALL_REDSTONE_TORCH;

  public static CopperRepeaterBlock COPPER_REPEATER;
  public static CopperRepeaterBlock EXPOSED_COPPER_REPEATER;
  public static CopperRepeaterBlock WEATHERED_COPPER_REPEATER;
  public static CopperRepeaterBlock OXIDIZED_COPPER_REPEATER;

  public static Block WAXED_COPPER_REPEATER;
  public static Block WAXED_EXPOSED_COPPER_REPEATER;
  public static Block WAXED_WEATHERED_COPPER_REPEATER;
  public static Block WAXED_OXIDIZED_COPPER_REPEATER;

  public static Block COPPER_PRESSURE_PLATE;
  public static Block COPPER_CHAIN;

  public static Block GARDEN_STAKE;

  public static SparkstoneTorchBlock SPARKSTONE_TORCH;
  public static SparkstoneWallTorchBlock SPARKSTONE_WALL_TORCH;
  public static SparkstoneRelayBlock SPARKSTONE_RELAY;

  public static void register(BiConsumer<Block, ResourceLocation> consumer) {

    COPPER_CHAIN = new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN));
    COPPER_PRESSURE_PLATE = Services.PLATFORM.createWeightedPressurePlateBlock(150, BlockBehaviour.Properties.copy(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE), BlockSetType.IRON);

    consumer.accept(COPPER_CHAIN, MoreUsefulCopper.identifier("copper_chain"));
    consumer.accept(COPPER_PRESSURE_PLATE, MoreUsefulCopper.identifier("copper_pressure_plate"));

    GARDEN_STAKE = new GardenStakeBlock(BlockBehaviour.Properties.of().randomTicks().noOcclusion().noCollission().lightLevel(s -> s.getValue(GardenStakeBlock.LIT) ? 7 : 2));
    consumer.accept(GARDEN_STAKE, MoreUsefulCopper.identifier("garden_stake"));

    SPARKSTONE_TORCH = new SparkstoneTorchBlock(
        Properties.of().noCollission().instabreak()
            .lightLevel(s -> s.getValue(SparkstoneTorchBlock.LIT) ? 10 : 0)
            .sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY));
    SPARKSTONE_WALL_TORCH = new SparkstoneWallTorchBlock(
        Properties.of().noCollission().instabreak()
            .lightLevel(s -> s.getValue(SparkstoneWallTorchBlock.LIT) ? 10 : 0)
            .sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY));
    SPARKSTONE_RELAY = new SparkstoneRelayBlock(
        Properties.of().instabreak().pushReaction(PushReaction.DESTROY));

    consumer.accept(SPARKSTONE_TORCH, MoreUsefulCopper.identifier("sparkstone_torch"));
    consumer.accept(SPARKSTONE_WALL_TORCH, MoreUsefulCopper.identifier("sparkstone_wall_torch"));
    consumer.accept(SPARKSTONE_RELAY, MoreUsefulCopper.identifier("sparkstone_relay"));

    COPPER_BELL = new CopperBellBlock(
        Properties.of().mapColor(MapColor.GOLD).forceSolidOn().requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.DESTROY).noOcclusion());
    consumer.accept(COPPER_BELL, MoreUsefulCopper.identifier("copper_bell"));

    COPPER_BUTTON = weatheringCopperButton(WeatherState.UNAFFECTED, CopperButtonBlock.UNAFFECTED_PRESSED_TICKS);
    EXPOSED_COPPER_BUTTON = weatheringCopperButton(WeatherState.EXPOSED, CopperButtonBlock.EXPOSED_PRESSED_TICKS);
    WEATHERED_COPPER_BUTTON = weatheringCopperButton(WeatherState.WEATHERED, CopperButtonBlock.WEATHERED_PRESSED_TICKS);
    OXIDIZED_COPPER_BUTTON = weatheringCopperButton(WeatherState.OXIDIZED, CopperButtonBlock.OXIDIZED_PRESSED_TICKS);

    WAXED_COPPER_BUTTON = waxedCopperButton(CopperButtonBlock.UNAFFECTED_PRESSED_TICKS);
    WAXED_EXPOSED_COPPER_BUTTON = waxedCopperButton(CopperButtonBlock.EXPOSED_PRESSED_TICKS);
    WAXED_WEATHERED_COPPER_BUTTON = waxedCopperButton(CopperButtonBlock.WEATHERED_PRESSED_TICKS);
    WAXED_OXIDIZED_COPPER_BUTTON = waxedCopperButton(CopperButtonBlock.OXIDIZED_PRESSED_TICKS);

    COPPER_COMPARATOR = weatheringCopperComparator(WeatherState.UNAFFECTED);
    EXPOSED_COPPER_COMPARATOR = weatheringCopperComparator(WeatherState.EXPOSED);
    WEATHERED_COPPER_COMPARATOR = weatheringCopperComparator(WeatherState.WEATHERED);
    OXIDIZED_COPPER_COMPARATOR = weatheringCopperComparator(WeatherState.OXIDIZED);

    WAXED_COPPER_COMPARATOR = waxedCopperComparator();
    WAXED_EXPOSED_COPPER_COMPARATOR = waxedCopperComparator();
    WAXED_WEATHERED_COPPER_COMPARATOR = waxedCopperComparator();
    WAXED_OXIDIZED_COPPER_COMPARATOR = waxedCopperComparator();

    consumer.accept(COPPER_BUTTON, MoreUsefulCopper.identifier("copper_button"));
    consumer.accept(EXPOSED_COPPER_BUTTON, MoreUsefulCopper.identifier("exposed_copper_button"));
    consumer.accept(WEATHERED_COPPER_BUTTON, MoreUsefulCopper.identifier("weathered_copper_button"));
    consumer.accept(OXIDIZED_COPPER_BUTTON, MoreUsefulCopper.identifier("oxidized_copper_button"));
    consumer.accept(WAXED_COPPER_BUTTON, MoreUsefulCopper.identifier("waxed_copper_button"));
    consumer.accept(WAXED_EXPOSED_COPPER_BUTTON, MoreUsefulCopper.identifier("waxed_exposed_copper_button"));
    consumer.accept(WAXED_WEATHERED_COPPER_BUTTON, MoreUsefulCopper.identifier("waxed_weathered_copper_button"));
    consumer.accept(WAXED_OXIDIZED_COPPER_BUTTON, MoreUsefulCopper.identifier("waxed_oxidized_copper_button"));

    consumer.accept(COPPER_COMPARATOR, MoreUsefulCopper.identifier("copper_comparator"));
    consumer.accept(EXPOSED_COPPER_COMPARATOR, MoreUsefulCopper.identifier("exposed_copper_comparator"));
    consumer.accept(WEATHERED_COPPER_COMPARATOR, MoreUsefulCopper.identifier("weathered_copper_comparator"));
    consumer.accept(OXIDIZED_COPPER_COMPARATOR, MoreUsefulCopper.identifier("oxidized_copper_comparator"));
    consumer.accept(WAXED_COPPER_COMPARATOR, MoreUsefulCopper.identifier("waxed_copper_comparator"));
    consumer.accept(WAXED_EXPOSED_COPPER_COMPARATOR, MoreUsefulCopper.identifier("waxed_exposed_copper_comparator"));
    consumer.accept(WAXED_WEATHERED_COPPER_COMPARATOR, MoreUsefulCopper.identifier("waxed_weathered_copper_comparator"));
    consumer.accept(WAXED_OXIDIZED_COPPER_COMPARATOR, MoreUsefulCopper.identifier("waxed_oxidized_copper_comparator"));

    COPPER_REDSTONE_DUST = weatheringCopperRedstoneDust(WeatherState.UNAFFECTED);
    EXPOSED_COPPER_REDSTONE_DUST = weatheringCopperRedstoneDust(WeatherState.EXPOSED);
    WEATHERED_COPPER_REDSTONE_DUST = weatheringCopperRedstoneDust(WeatherState.WEATHERED);
    OXIDIZED_COPPER_REDSTONE_DUST = weatheringCopperRedstoneDust(WeatherState.OXIDIZED);

    WAXED_COPPER_REDSTONE_DUST = waxedCopperRedstoneDust(WeatherState.UNAFFECTED);
    WAXED_EXPOSED_COPPER_REDSTONE_DUST = waxedCopperRedstoneDust(WeatherState.EXPOSED);
    WAXED_WEATHERED_COPPER_REDSTONE_DUST = waxedCopperRedstoneDust(WeatherState.WEATHERED);
    WAXED_OXIDIZED_COPPER_REDSTONE_DUST = waxedCopperRedstoneDust(WeatherState.OXIDIZED);

    consumer.accept(COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("copper_redstone_dust"));
    consumer.accept(EXPOSED_COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("exposed_copper_redstone_dust"));
    consumer.accept(WEATHERED_COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("weathered_copper_redstone_dust"));
    consumer.accept(OXIDIZED_COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("oxidized_copper_redstone_dust"));
    consumer.accept(WAXED_COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("waxed_copper_redstone_dust"));
    consumer.accept(WAXED_EXPOSED_COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("waxed_exposed_copper_redstone_dust"));
    consumer.accept(WAXED_WEATHERED_COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("waxed_weathered_copper_redstone_dust"));
    consumer.accept(WAXED_OXIDIZED_COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("waxed_oxidized_copper_redstone_dust"));

    COPPER_LEVER = weatheringCopperLever(WeatherState.UNAFFECTED);
    EXPOSED_COPPER_LEVER = weatheringCopperLever(WeatherState.EXPOSED);
    WEATHERED_COPPER_LEVER = weatheringCopperLever(WeatherState.WEATHERED);
    OXIDIZED_COPPER_LEVER = weatheringCopperLever(WeatherState.OXIDIZED);

    WAXED_COPPER_LEVER = waxedCopperLever();
    WAXED_EXPOSED_COPPER_LEVER = waxedCopperLever();
    WAXED_WEATHERED_COPPER_LEVER = waxedCopperLever();
    WAXED_OXIDIZED_COPPER_LEVER = waxedCopperLever();

    consumer.accept(COPPER_LEVER, MoreUsefulCopper.identifier("copper_lever"));
    consumer.accept(EXPOSED_COPPER_LEVER, MoreUsefulCopper.identifier("exposed_copper_lever"));
    consumer.accept(WEATHERED_COPPER_LEVER, MoreUsefulCopper.identifier("weathered_copper_lever"));
    consumer.accept(OXIDIZED_COPPER_LEVER, MoreUsefulCopper.identifier("oxidized_copper_lever"));
    consumer.accept(WAXED_COPPER_LEVER, MoreUsefulCopper.identifier("waxed_copper_lever"));
    consumer.accept(WAXED_EXPOSED_COPPER_LEVER, MoreUsefulCopper.identifier("waxed_exposed_copper_lever"));
    consumer.accept(WAXED_WEATHERED_COPPER_LEVER, MoreUsefulCopper.identifier("waxed_weathered_copper_lever"));
    consumer.accept(WAXED_OXIDIZED_COPPER_LEVER, MoreUsefulCopper.identifier("waxed_oxidized_copper_lever"));

    COPPER_REDSTONE_TORCH = copperRedstoneTorch(WeatherState.UNAFFECTED);
    EXPOSED_COPPER_REDSTONE_TORCH = copperRedstoneTorch(WeatherState.EXPOSED);
    WEATHERED_COPPER_REDSTONE_TORCH = copperRedstoneTorch(WeatherState.WEATHERED);
    OXIDIZED_COPPER_REDSTONE_TORCH = copperRedstoneTorch(WeatherState.OXIDIZED);

    WAXED_COPPER_REDSTONE_TORCH = waxedCopperRedstoneTorch();
    WAXED_EXPOSED_COPPER_REDSTONE_TORCH = waxedCopperRedstoneTorch();
    WAXED_WEATHERED_COPPER_REDSTONE_TORCH = waxedCopperRedstoneTorch();
    WAXED_OXIDIZED_COPPER_REDSTONE_TORCH = waxedCopperRedstoneTorch();

    COPPER_WALL_REDSTONE_TORCH = copperWallRedstoneTorch(WeatherState.UNAFFECTED);
    EXPOSED_COPPER_WALL_REDSTONE_TORCH = copperWallRedstoneTorch(WeatherState.EXPOSED);
    WEATHERED_COPPER_WALL_REDSTONE_TORCH = copperWallRedstoneTorch(WeatherState.WEATHERED);
    OXIDIZED_COPPER_WALL_REDSTONE_TORCH = copperWallRedstoneTorch(WeatherState.OXIDIZED);

    WAXED_COPPER_WALL_REDSTONE_TORCH = waxedCopperWallRedstoneTorch();
    WAXED_EXPOSED_COPPER_WALL_REDSTONE_TORCH = waxedCopperWallRedstoneTorch();
    WAXED_WEATHERED_COPPER_WALL_REDSTONE_TORCH = waxedCopperWallRedstoneTorch();
    WAXED_OXIDIZED_COPPER_WALL_REDSTONE_TORCH = waxedCopperWallRedstoneTorch();

    consumer.accept(COPPER_REDSTONE_TORCH, MoreUsefulCopper.identifier("copper_redstone_torch"));
    consumer.accept(EXPOSED_COPPER_REDSTONE_TORCH, MoreUsefulCopper.identifier("exposed_copper_redstone_torch"));
    consumer.accept(WEATHERED_COPPER_REDSTONE_TORCH, MoreUsefulCopper.identifier("weathered_copper_redstone_torch"));
    consumer.accept(OXIDIZED_COPPER_REDSTONE_TORCH, MoreUsefulCopper.identifier("oxidized_copper_redstone_torch"));
    consumer.accept(WAXED_COPPER_REDSTONE_TORCH, MoreUsefulCopper.identifier("waxed_copper_redstone_torch"));
    consumer.accept(WAXED_EXPOSED_COPPER_REDSTONE_TORCH, MoreUsefulCopper.identifier("waxed_exposed_copper_redstone_torch"));
    consumer.accept(WAXED_WEATHERED_COPPER_REDSTONE_TORCH, MoreUsefulCopper.identifier("waxed_weathered_copper_redstone_torch"));
    consumer.accept(WAXED_OXIDIZED_COPPER_REDSTONE_TORCH, MoreUsefulCopper.identifier("waxed_oxidized_copper_redstone_torch"));

    consumer.accept(COPPER_WALL_REDSTONE_TORCH, MoreUsefulCopper.identifier("copper_wall_redstone_torch"));
    consumer.accept(EXPOSED_COPPER_WALL_REDSTONE_TORCH, MoreUsefulCopper.identifier("exposed_copper_wall_redstone_torch"));
    consumer.accept(WEATHERED_COPPER_WALL_REDSTONE_TORCH, MoreUsefulCopper.identifier("weathered_copper_wall_redstone_torch"));
    consumer.accept(OXIDIZED_COPPER_WALL_REDSTONE_TORCH, MoreUsefulCopper.identifier("oxidized_copper_wall_redstone_torch"));
    consumer.accept(WAXED_COPPER_WALL_REDSTONE_TORCH, MoreUsefulCopper.identifier("waxed_copper_wall_redstone_torch"));
    consumer.accept(WAXED_EXPOSED_COPPER_WALL_REDSTONE_TORCH, MoreUsefulCopper.identifier("waxed_exposed_copper_wall_redstone_torch"));
    consumer.accept(WAXED_WEATHERED_COPPER_WALL_REDSTONE_TORCH, MoreUsefulCopper.identifier("waxed_weathered_copper_wall_redstone_torch"));
    consumer.accept(WAXED_OXIDIZED_COPPER_WALL_REDSTONE_TORCH, MoreUsefulCopper.identifier("waxed_oxidized_copper_wall_redstone_torch"));

    COPPER_REPEATER = copperRepeater(WeatherState.UNAFFECTED);
    EXPOSED_COPPER_REPEATER = copperRepeater(WeatherState.EXPOSED);
    WEATHERED_COPPER_REPEATER = copperRepeater(WeatherState.WEATHERED);
    OXIDIZED_COPPER_REPEATER = copperRepeater(WeatherState.OXIDIZED);

    WAXED_COPPER_REPEATER = waxedCopperRepeater();
    WAXED_EXPOSED_COPPER_REPEATER = waxedCopperRepeater();
    WAXED_WEATHERED_COPPER_REPEATER = waxedCopperRepeater();
    WAXED_OXIDIZED_COPPER_REPEATER = waxedCopperRepeater();

    consumer.accept(COPPER_REPEATER, MoreUsefulCopper.identifier("copper_repeater"));
    consumer.accept(EXPOSED_COPPER_REPEATER, MoreUsefulCopper.identifier("exposed_copper_repeater"));
    consumer.accept(WEATHERED_COPPER_REPEATER, MoreUsefulCopper.identifier("weathered_copper_repeater"));
    consumer.accept(OXIDIZED_COPPER_REPEATER, MoreUsefulCopper.identifier("oxidized_copper_repeater"));
    consumer.accept(WAXED_COPPER_REPEATER, MoreUsefulCopper.identifier("waxed_copper_repeater"));
    consumer.accept(WAXED_EXPOSED_COPPER_REPEATER, MoreUsefulCopper.identifier("waxed_exposed_copper_repeater"));
    consumer.accept(WAXED_WEATHERED_COPPER_REPEATER, MoreUsefulCopper.identifier("waxed_weathered_copper_repeater"));
    consumer.accept(WAXED_OXIDIZED_COPPER_REPEATER, MoreUsefulCopper.identifier("waxed_oxidized_copper_repeater"));
  }

  private static CopperButtonBlock weatheringCopperButton(WeatherState weatherState, int ticksToStayPressed) {
    return new CopperButtonBlock(Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), weatherState, BlockSetType.STONE, ticksToStayPressed, true);
  }

  private static WaxedButtonBlock waxedCopperButton(int ticksToStayPressed) {
    return new WaxedButtonBlock(Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), BlockSetType.STONE, ticksToStayPressed, true);
  }

  private static CopperComparatorBlock weatheringCopperComparator(WeatherState weatherState) {
    return new CopperComparatorBlock(Properties.of().instabreak().pushReaction(PushReaction.DESTROY), weatherState);
  }

  private static WaxedComparatorBlock waxedCopperComparator() {
    return new WaxedComparatorBlock(Properties.of().instabreak().pushReaction(PushReaction.DESTROY));
  }

  private static CopperRedstoneDustBlock weatheringCopperRedstoneDust(WeatherState weatherState) {
    return new CopperRedstoneDustBlock(
        Properties.of().noCollission().instabreak().pushReaction(PushReaction.DESTROY), weatherState);
  }

  private static WaxedRedstoneDustBlock waxedCopperRedstoneDust(WeatherState weatherState) {
    return new WaxedRedstoneDustBlock(
        Properties.of().noCollission().instabreak().pushReaction(PushReaction.DESTROY), weatherState);
  }

  private static CopperLeverBlock weatheringCopperLever(WeatherState weatherState) {
    return new CopperLeverBlock(Properties.of().noCollission().strength(0.5F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY), weatherState);
  }

  private static WaxedLeverBlock waxedCopperLever() {
    return new WaxedLeverBlock(Properties.of().noCollission().strength(0.5F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
  }

  private static CopperRedstoneTorchBlock copperRedstoneTorch(WeatherState weatherState) {
    return new CopperRedstoneTorchBlock(
        Properties.of().noCollission().instabreak().lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 7 : 0).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY),
        weatherState);
  }

  private static WaxedRedstoneTorchBlock waxedCopperRedstoneTorch() {
    return new WaxedRedstoneTorchBlock(
        Properties.of().noCollission().instabreak().lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 7 : 0).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
  }

  private static CopperWallRedstoneTorchBlock copperWallRedstoneTorch(WeatherState weatherState) {
    return new CopperWallRedstoneTorchBlock(
        Properties.of().noCollission().instabreak().lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 7 : 0).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY),
        weatherState);
  }

  private static WaxedWallRedstoneTorchBlock waxedCopperWallRedstoneTorch() {
    return new WaxedWallRedstoneTorchBlock(
        Properties.of().noCollission().instabreak().lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 7 : 0).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
  }

  private static CopperRepeaterBlock copperRepeater(WeatherState weatherState) {
    return new CopperRepeaterBlock(Properties.of().instabreak().pushReaction(PushReaction.DESTROY), weatherState);
  }

  private static WaxedRepeaterBlock waxedCopperRepeater() {
    return new WaxedRepeaterBlock(Properties.of().instabreak().pushReaction(PushReaction.DESTROY));
  }
}
