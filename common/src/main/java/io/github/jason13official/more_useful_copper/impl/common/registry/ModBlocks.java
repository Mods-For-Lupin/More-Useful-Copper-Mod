package io.github.jason13official.more_useful_copper.impl.common.registry;

import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperButtonBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperComparatorBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperLeverBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperRedstoneDustBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedButtonBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedComparatorBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedLeverBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedRedstoneDustBlock;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {

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

  public static void register(BiConsumer<Block, ResourceLocation> consumer) {

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
  }

  private static CopperButtonBlock weatheringCopperButton(WeatherState weatherState, int ticksToStayPressed) {
    return new CopperButtonBlock(Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), weatherState, BlockSetType.STONE, ticksToStayPressed, true);
  }

  private static ButtonBlock waxedCopperButton(int ticksToStayPressed) {
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
}
