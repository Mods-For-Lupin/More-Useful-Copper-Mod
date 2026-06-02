package io.github.jason13official.more_useful_copper.impl.common.registry;

import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperBellBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperButtonBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperChainBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperComparatorBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperLeverBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperPressurePlateBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperRedstoneDustBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperRedstoneTorchBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperRepeaterBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperWallRedstoneTorchBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.GardenStakeBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedButtonBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedChainBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedComparatorBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedLeverBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedPressurePlateBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedRedstoneDustBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedRedstoneTorchBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedRepeaterBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedWallRedstoneTorchBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.sparkstone.SparkstoneRelayBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.sparkstone.SparkstoneTorchBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.sparkstone.SparkstoneWallTorchBlock;
import java.util.function.BiConsumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
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

  public static CopperPressurePlateBlock COPPER_PRESSURE_PLATE;
  public static CopperPressurePlateBlock EXPOSED_COPPER_PRESSURE_PLATE;
  public static CopperPressurePlateBlock WEATHERED_COPPER_PRESSURE_PLATE;
  public static CopperPressurePlateBlock OXIDIZED_COPPER_PRESSURE_PLATE;

  public static Block WAXED_COPPER_PRESSURE_PLATE;
  public static Block WAXED_EXPOSED_COPPER_PRESSURE_PLATE;
  public static Block WAXED_WEATHERED_COPPER_PRESSURE_PLATE;
  public static Block WAXED_OXIDIZED_COPPER_PRESSURE_PLATE;

  // public static CopperChainBlock COPPER_CHAIN;
  // public static CopperChainBlock EXPOSED_COPPER_CHAIN;
  // public static CopperChainBlock WEATHERED_COPPER_CHAIN;
  // public static CopperChainBlock OXIDIZED_COPPER_CHAIN;

  // public static Block WAXED_COPPER_CHAIN;
  // public static Block WAXED_EXPOSED_COPPER_CHAIN;
  // public static Block WAXED_WEATHERED_COPPER_CHAIN;
  // public static Block WAXED_OXIDIZED_COPPER_CHAIN;

  public static Block GARDEN_STAKE;

  public static SparkstoneTorchBlock SPARKSTONE_TORCH;
  public static SparkstoneWallTorchBlock SPARKSTONE_WALL_TORCH;
  public static SparkstoneRelayBlock SPARKSTONE_RELAY;

  public static void register(BiConsumer<Block, Identifier> consumer) {

    // COPPER_CHAIN = copperChain(WeatherState.UNAFFECTED, MoreUsefulCopper.identifier("copper_chain"));
    // EXPOSED_COPPER_CHAIN = copperChain(WeatherState.EXPOSED, MoreUsefulCopper.identifier("exposed_copper_chain"));
    // WEATHERED_COPPER_CHAIN = copperChain(WeatherState.WEATHERED, MoreUsefulCopper.identifier("weathered_copper_chain"));
    // OXIDIZED_COPPER_CHAIN = copperChain(WeatherState.OXIDIZED, MoreUsefulCopper.identifier("oxidized_copper_chain"));

    // WAXED_COPPER_CHAIN = waxedCopperChain(MoreUsefulCopper.identifier("waxed_copper_chain"));
    // WAXED_EXPOSED_COPPER_CHAIN = waxedCopperChain(MoreUsefulCopper.identifier("waxed_exposed_copper_chain"));
    // WAXED_WEATHERED_COPPER_CHAIN = waxedCopperChain(MoreUsefulCopper.identifier("waxed_weathered_copper_chain"));
    // WAXED_OXIDIZED_COPPER_CHAIN = waxedCopperChain(MoreUsefulCopper.identifier("waxed_oxidized_copper_chain"));

    // consumer.accept(COPPER_CHAIN, MoreUsefulCopper.identifier("copper_chain"));
    // consumer.accept(EXPOSED_COPPER_CHAIN, MoreUsefulCopper.identifier("exposed_copper_chain"));
    // consumer.accept(WEATHERED_COPPER_CHAIN, MoreUsefulCopper.identifier("weathered_copper_chain"));
    // consumer.accept(OXIDIZED_COPPER_CHAIN, MoreUsefulCopper.identifier("oxidized_copper_chain"));
    // consumer.accept(WAXED_COPPER_CHAIN, MoreUsefulCopper.identifier("waxed_copper_chain"));
    // consumer.accept(WAXED_EXPOSED_COPPER_CHAIN, MoreUsefulCopper.identifier("waxed_exposed_copper_chain"));
    // consumer.accept(WAXED_WEATHERED_COPPER_CHAIN, MoreUsefulCopper.identifier("waxed_weathered_copper_chain"));
    // consumer.accept(WAXED_OXIDIZED_COPPER_CHAIN, MoreUsefulCopper.identifier("waxed_oxidized_copper_chain"));

    COPPER_PRESSURE_PLATE = copperPressurePlate(WeatherState.UNAFFECTED, MoreUsefulCopper.identifier("copper_pressure_plate"));
    EXPOSED_COPPER_PRESSURE_PLATE = copperPressurePlate(WeatherState.EXPOSED, MoreUsefulCopper.identifier("exposed_copper_pressure_plate"));
    WEATHERED_COPPER_PRESSURE_PLATE = copperPressurePlate(WeatherState.WEATHERED, MoreUsefulCopper.identifier("weathered_copper_pressure_plate"));
    OXIDIZED_COPPER_PRESSURE_PLATE = copperPressurePlate(WeatherState.OXIDIZED, MoreUsefulCopper.identifier("oxidized_copper_pressure_plate"));

    WAXED_COPPER_PRESSURE_PLATE = waxedCopperPressurePlate(MoreUsefulCopper.identifier("waxed_copper_pressure_plate"));
    WAXED_EXPOSED_COPPER_PRESSURE_PLATE = waxedCopperPressurePlate(MoreUsefulCopper.identifier("waxed_exposed_copper_pressure_plate"));
    WAXED_WEATHERED_COPPER_PRESSURE_PLATE = waxedCopperPressurePlate(MoreUsefulCopper.identifier("waxed_weathered_copper_pressure_plate"));
    WAXED_OXIDIZED_COPPER_PRESSURE_PLATE = waxedCopperPressurePlate(MoreUsefulCopper.identifier("waxed_oxidized_copper_pressure_plate"));

    consumer.accept(COPPER_PRESSURE_PLATE, MoreUsefulCopper.identifier("copper_pressure_plate"));
    consumer.accept(EXPOSED_COPPER_PRESSURE_PLATE, MoreUsefulCopper.identifier("exposed_copper_pressure_plate"));
    consumer.accept(WEATHERED_COPPER_PRESSURE_PLATE, MoreUsefulCopper.identifier("weathered_copper_pressure_plate"));
    consumer.accept(OXIDIZED_COPPER_PRESSURE_PLATE, MoreUsefulCopper.identifier("oxidized_copper_pressure_plate"));
    consumer.accept(WAXED_COPPER_PRESSURE_PLATE, MoreUsefulCopper.identifier("waxed_copper_pressure_plate"));
    consumer.accept(WAXED_EXPOSED_COPPER_PRESSURE_PLATE, MoreUsefulCopper.identifier("waxed_exposed_copper_pressure_plate"));
    consumer.accept(WAXED_WEATHERED_COPPER_PRESSURE_PLATE, MoreUsefulCopper.identifier("waxed_weathered_copper_pressure_plate"));
    consumer.accept(WAXED_OXIDIZED_COPPER_PRESSURE_PLATE, MoreUsefulCopper.identifier("waxed_oxidized_copper_pressure_plate"));

    GARDEN_STAKE = new GardenStakeBlock(Properties.of()
        .setId(ResourceKey.create(Registries.BLOCK, MoreUsefulCopper.identifier("garden_stake")))
        .randomTicks().noOcclusion().noCollision().lightLevel(s -> s.getValue(GardenStakeBlock.LIT) ? 7 : 2));
    consumer.accept(GARDEN_STAKE, MoreUsefulCopper.identifier("garden_stake"));

    SPARKSTONE_TORCH = new SparkstoneTorchBlock(
        Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, MoreUsefulCopper.identifier("sparkstone_torch")))
            .noCollision().instabreak()
            .lightLevel(s -> s.getValue(SparkstoneTorchBlock.LIT) ? 10 : 0)
            .sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY));
    SPARKSTONE_WALL_TORCH = new SparkstoneWallTorchBlock(
        Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, MoreUsefulCopper.identifier("sparkstone_wall_torch")))
            .noCollision().instabreak()
            .lightLevel(s -> s.getValue(SparkstoneWallTorchBlock.LIT) ? 10 : 0)
            .sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY));
    SPARKSTONE_RELAY = new SparkstoneRelayBlock(
        Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, MoreUsefulCopper.identifier("sparkstone_relay")))
            .instabreak().pushReaction(PushReaction.DESTROY));

    consumer.accept(SPARKSTONE_TORCH, MoreUsefulCopper.identifier("sparkstone_torch"));
    consumer.accept(SPARKSTONE_WALL_TORCH, MoreUsefulCopper.identifier("sparkstone_wall_torch"));
    consumer.accept(SPARKSTONE_RELAY, MoreUsefulCopper.identifier("sparkstone_relay"));

    COPPER_BELL = new CopperBellBlock(
        Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, MoreUsefulCopper.identifier("copper_bell")))
            .mapColor(MapColor.GOLD).forceSolidOn().requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.DESTROY).noOcclusion());
    consumer.accept(COPPER_BELL, MoreUsefulCopper.identifier("copper_bell"));

    COPPER_BUTTON = weatheringCopperButton(WeatherState.UNAFFECTED, CopperButtonBlock.UNAFFECTED_PRESSED_TICKS, MoreUsefulCopper.identifier("copper_button"));
    EXPOSED_COPPER_BUTTON = weatheringCopperButton(WeatherState.EXPOSED, CopperButtonBlock.EXPOSED_PRESSED_TICKS, MoreUsefulCopper.identifier("exposed_copper_button"));
    WEATHERED_COPPER_BUTTON = weatheringCopperButton(WeatherState.WEATHERED, CopperButtonBlock.WEATHERED_PRESSED_TICKS, MoreUsefulCopper.identifier("weathered_copper_button"));
    OXIDIZED_COPPER_BUTTON = weatheringCopperButton(WeatherState.OXIDIZED, CopperButtonBlock.OXIDIZED_PRESSED_TICKS, MoreUsefulCopper.identifier("oxidized_copper_button"));

    WAXED_COPPER_BUTTON = waxedCopperButton(CopperButtonBlock.UNAFFECTED_PRESSED_TICKS, MoreUsefulCopper.identifier("waxed_copper_button"));
    WAXED_EXPOSED_COPPER_BUTTON = waxedCopperButton(CopperButtonBlock.EXPOSED_PRESSED_TICKS, MoreUsefulCopper.identifier("waxed_exposed_copper_button"));
    WAXED_WEATHERED_COPPER_BUTTON = waxedCopperButton(CopperButtonBlock.WEATHERED_PRESSED_TICKS, MoreUsefulCopper.identifier("waxed_weathered_copper_button"));
    WAXED_OXIDIZED_COPPER_BUTTON = waxedCopperButton(CopperButtonBlock.OXIDIZED_PRESSED_TICKS, MoreUsefulCopper.identifier("waxed_oxidized_copper_button"));

    COPPER_COMPARATOR = weatheringCopperComparator(WeatherState.UNAFFECTED, MoreUsefulCopper.identifier("copper_comparator"));
    EXPOSED_COPPER_COMPARATOR = weatheringCopperComparator(WeatherState.EXPOSED, MoreUsefulCopper.identifier("exposed_copper_comparator"));
    WEATHERED_COPPER_COMPARATOR = weatheringCopperComparator(WeatherState.WEATHERED, MoreUsefulCopper.identifier("weathered_copper_comparator"));
    OXIDIZED_COPPER_COMPARATOR = weatheringCopperComparator(WeatherState.OXIDIZED, MoreUsefulCopper.identifier("oxidized_copper_comparator"));

    WAXED_COPPER_COMPARATOR = waxedCopperComparator(MoreUsefulCopper.identifier("waxed_copper_comparator"));
    WAXED_EXPOSED_COPPER_COMPARATOR = waxedCopperComparator(MoreUsefulCopper.identifier("waxed_exposed_copper_comparator"));
    WAXED_WEATHERED_COPPER_COMPARATOR = waxedCopperComparator(MoreUsefulCopper.identifier("waxed_weathered_copper_comparator"));
    WAXED_OXIDIZED_COPPER_COMPARATOR = waxedCopperComparator(MoreUsefulCopper.identifier("waxed_oxidized_copper_comparator"));

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

    COPPER_REDSTONE_DUST = weatheringCopperRedstoneDust(WeatherState.UNAFFECTED, MoreUsefulCopper.identifier("copper_redstone_dust"));
    EXPOSED_COPPER_REDSTONE_DUST = weatheringCopperRedstoneDust(WeatherState.EXPOSED, MoreUsefulCopper.identifier("exposed_copper_redstone_dust"));
    WEATHERED_COPPER_REDSTONE_DUST = weatheringCopperRedstoneDust(WeatherState.WEATHERED, MoreUsefulCopper.identifier("weathered_copper_redstone_dust"));
    OXIDIZED_COPPER_REDSTONE_DUST = weatheringCopperRedstoneDust(WeatherState.OXIDIZED, MoreUsefulCopper.identifier("oxidized_copper_redstone_dust"));

    WAXED_COPPER_REDSTONE_DUST = waxedCopperRedstoneDust(WeatherState.UNAFFECTED, MoreUsefulCopper.identifier("waxed_copper_redstone_dust"));
    WAXED_EXPOSED_COPPER_REDSTONE_DUST = waxedCopperRedstoneDust(WeatherState.EXPOSED, MoreUsefulCopper.identifier("waxed_exposed_copper_redstone_dust"));
    WAXED_WEATHERED_COPPER_REDSTONE_DUST = waxedCopperRedstoneDust(WeatherState.WEATHERED, MoreUsefulCopper.identifier("waxed_weathered_copper_redstone_dust"));
    WAXED_OXIDIZED_COPPER_REDSTONE_DUST = waxedCopperRedstoneDust(WeatherState.OXIDIZED, MoreUsefulCopper.identifier("waxed_oxidized_copper_redstone_dust"));

    consumer.accept(COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("copper_redstone_dust"));
    consumer.accept(EXPOSED_COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("exposed_copper_redstone_dust"));
    consumer.accept(WEATHERED_COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("weathered_copper_redstone_dust"));
    consumer.accept(OXIDIZED_COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("oxidized_copper_redstone_dust"));
    consumer.accept(WAXED_COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("waxed_copper_redstone_dust"));
    consumer.accept(WAXED_EXPOSED_COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("waxed_exposed_copper_redstone_dust"));
    consumer.accept(WAXED_WEATHERED_COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("waxed_weathered_copper_redstone_dust"));
    consumer.accept(WAXED_OXIDIZED_COPPER_REDSTONE_DUST, MoreUsefulCopper.identifier("waxed_oxidized_copper_redstone_dust"));

    COPPER_LEVER = weatheringCopperLever(WeatherState.UNAFFECTED, MoreUsefulCopper.identifier("copper_lever"));
    EXPOSED_COPPER_LEVER = weatheringCopperLever(WeatherState.EXPOSED, MoreUsefulCopper.identifier("exposed_copper_lever"));
    WEATHERED_COPPER_LEVER = weatheringCopperLever(WeatherState.WEATHERED, MoreUsefulCopper.identifier("weathered_copper_lever"));
    OXIDIZED_COPPER_LEVER = weatheringCopperLever(WeatherState.OXIDIZED, MoreUsefulCopper.identifier("oxidized_copper_lever"));

    WAXED_COPPER_LEVER = waxedCopperLever(MoreUsefulCopper.identifier("waxed_copper_lever"));
    WAXED_EXPOSED_COPPER_LEVER = waxedCopperLever(MoreUsefulCopper.identifier("waxed_exposed_copper_lever"));
    WAXED_WEATHERED_COPPER_LEVER = waxedCopperLever(MoreUsefulCopper.identifier("waxed_weathered_copper_lever"));
    WAXED_OXIDIZED_COPPER_LEVER = waxedCopperLever(MoreUsefulCopper.identifier("waxed_oxidized_copper_lever"));

    consumer.accept(COPPER_LEVER, MoreUsefulCopper.identifier("copper_lever"));
    consumer.accept(EXPOSED_COPPER_LEVER, MoreUsefulCopper.identifier("exposed_copper_lever"));
    consumer.accept(WEATHERED_COPPER_LEVER, MoreUsefulCopper.identifier("weathered_copper_lever"));
    consumer.accept(OXIDIZED_COPPER_LEVER, MoreUsefulCopper.identifier("oxidized_copper_lever"));
    consumer.accept(WAXED_COPPER_LEVER, MoreUsefulCopper.identifier("waxed_copper_lever"));
    consumer.accept(WAXED_EXPOSED_COPPER_LEVER, MoreUsefulCopper.identifier("waxed_exposed_copper_lever"));
    consumer.accept(WAXED_WEATHERED_COPPER_LEVER, MoreUsefulCopper.identifier("waxed_weathered_copper_lever"));
    consumer.accept(WAXED_OXIDIZED_COPPER_LEVER, MoreUsefulCopper.identifier("waxed_oxidized_copper_lever"));

    COPPER_REDSTONE_TORCH = copperRedstoneTorch(WeatherState.UNAFFECTED, MoreUsefulCopper.identifier("copper_redstone_torch"));
    EXPOSED_COPPER_REDSTONE_TORCH = copperRedstoneTorch(WeatherState.EXPOSED, MoreUsefulCopper.identifier("exposed_copper_redstone_torch"));
    WEATHERED_COPPER_REDSTONE_TORCH = copperRedstoneTorch(WeatherState.WEATHERED, MoreUsefulCopper.identifier("weathered_copper_redstone_torch"));
    OXIDIZED_COPPER_REDSTONE_TORCH = copperRedstoneTorch(WeatherState.OXIDIZED, MoreUsefulCopper.identifier("oxidized_copper_redstone_torch"));

    WAXED_COPPER_REDSTONE_TORCH = waxedCopperRedstoneTorch(MoreUsefulCopper.identifier("waxed_copper_redstone_torch"));
    WAXED_EXPOSED_COPPER_REDSTONE_TORCH = waxedCopperRedstoneTorch(MoreUsefulCopper.identifier("waxed_exposed_copper_redstone_torch"));
    WAXED_WEATHERED_COPPER_REDSTONE_TORCH = waxedCopperRedstoneTorch(MoreUsefulCopper.identifier("waxed_weathered_copper_redstone_torch"));
    WAXED_OXIDIZED_COPPER_REDSTONE_TORCH = waxedCopperRedstoneTorch(MoreUsefulCopper.identifier("waxed_oxidized_copper_redstone_torch"));

    COPPER_WALL_REDSTONE_TORCH = copperWallRedstoneTorch(WeatherState.UNAFFECTED, MoreUsefulCopper.identifier("copper_wall_redstone_torch"));
    EXPOSED_COPPER_WALL_REDSTONE_TORCH = copperWallRedstoneTorch(WeatherState.EXPOSED, MoreUsefulCopper.identifier("exposed_copper_wall_redstone_torch"));
    WEATHERED_COPPER_WALL_REDSTONE_TORCH = copperWallRedstoneTorch(WeatherState.WEATHERED, MoreUsefulCopper.identifier("weathered_copper_wall_redstone_torch"));
    OXIDIZED_COPPER_WALL_REDSTONE_TORCH = copperWallRedstoneTorch(WeatherState.OXIDIZED, MoreUsefulCopper.identifier("oxidized_copper_wall_redstone_torch"));

    WAXED_COPPER_WALL_REDSTONE_TORCH = waxedCopperWallRedstoneTorch(MoreUsefulCopper.identifier("waxed_copper_wall_redstone_torch"));
    WAXED_EXPOSED_COPPER_WALL_REDSTONE_TORCH = waxedCopperWallRedstoneTorch(MoreUsefulCopper.identifier("waxed_exposed_copper_wall_redstone_torch"));
    WAXED_WEATHERED_COPPER_WALL_REDSTONE_TORCH = waxedCopperWallRedstoneTorch(MoreUsefulCopper.identifier("waxed_weathered_copper_wall_redstone_torch"));
    WAXED_OXIDIZED_COPPER_WALL_REDSTONE_TORCH = waxedCopperWallRedstoneTorch(MoreUsefulCopper.identifier("waxed_oxidized_copper_wall_redstone_torch"));

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

    COPPER_REPEATER = copperRepeater(WeatherState.UNAFFECTED, MoreUsefulCopper.identifier("copper_repeater"));
    EXPOSED_COPPER_REPEATER = copperRepeater(WeatherState.EXPOSED, MoreUsefulCopper.identifier("exposed_copper_repeater"));
    WEATHERED_COPPER_REPEATER = copperRepeater(WeatherState.WEATHERED, MoreUsefulCopper.identifier("weathered_copper_repeater"));
    OXIDIZED_COPPER_REPEATER = copperRepeater(WeatherState.OXIDIZED, MoreUsefulCopper.identifier("oxidized_copper_repeater"));

    WAXED_COPPER_REPEATER = waxedCopperRepeater(MoreUsefulCopper.identifier("waxed_copper_repeater"));
    WAXED_EXPOSED_COPPER_REPEATER = waxedCopperRepeater(MoreUsefulCopper.identifier("waxed_exposed_copper_repeater"));
    WAXED_WEATHERED_COPPER_REPEATER = waxedCopperRepeater(MoreUsefulCopper.identifier("waxed_weathered_copper_repeater"));
    WAXED_OXIDIZED_COPPER_REPEATER = waxedCopperRepeater(MoreUsefulCopper.identifier("waxed_oxidized_copper_repeater"));

    consumer.accept(COPPER_REPEATER, MoreUsefulCopper.identifier("copper_repeater"));
    consumer.accept(EXPOSED_COPPER_REPEATER, MoreUsefulCopper.identifier("exposed_copper_repeater"));
    consumer.accept(WEATHERED_COPPER_REPEATER, MoreUsefulCopper.identifier("weathered_copper_repeater"));
    consumer.accept(OXIDIZED_COPPER_REPEATER, MoreUsefulCopper.identifier("oxidized_copper_repeater"));
    consumer.accept(WAXED_COPPER_REPEATER, MoreUsefulCopper.identifier("waxed_copper_repeater"));
    consumer.accept(WAXED_EXPOSED_COPPER_REPEATER, MoreUsefulCopper.identifier("waxed_exposed_copper_repeater"));
    consumer.accept(WAXED_WEATHERED_COPPER_REPEATER, MoreUsefulCopper.identifier("waxed_weathered_copper_repeater"));
    consumer.accept(WAXED_OXIDIZED_COPPER_REPEATER, MoreUsefulCopper.identifier("waxed_oxidized_copper_repeater"));
  }

  private static CopperButtonBlock weatheringCopperButton(WeatherState weatherState, int ticksToStayPressed, Identifier id) {
    return new CopperButtonBlock(Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)).noCollision().strength(0.5F).pushReaction(PushReaction.DESTROY), weatherState, BlockSetType.STONE, ticksToStayPressed, true);
  }

  private static WaxedButtonBlock waxedCopperButton(int ticksToStayPressed, Identifier id) {
    return new WaxedButtonBlock(Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)).noCollision().strength(0.5F).pushReaction(PushReaction.DESTROY), BlockSetType.STONE, ticksToStayPressed, true);
  }

  private static CopperComparatorBlock weatheringCopperComparator(WeatherState weatherState, Identifier id) {
    return new CopperComparatorBlock(Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)).instabreak().pushReaction(PushReaction.DESTROY), weatherState);
  }

  private static WaxedComparatorBlock waxedCopperComparator(Identifier id) {
    return new WaxedComparatorBlock(Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)).instabreak().pushReaction(PushReaction.DESTROY));
  }

  private static CopperRedstoneDustBlock weatheringCopperRedstoneDust(WeatherState weatherState, Identifier id) {
    return new CopperRedstoneDustBlock(
        Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)).noCollision().instabreak().pushReaction(PushReaction.DESTROY), weatherState);
  }

  private static WaxedRedstoneDustBlock waxedCopperRedstoneDust(WeatherState weatherState, Identifier id) {
    return new WaxedRedstoneDustBlock(
        Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)).noCollision().instabreak().pushReaction(PushReaction.DESTROY), weatherState);
  }

  private static CopperLeverBlock weatheringCopperLever(WeatherState weatherState, Identifier id) {
    return new CopperLeverBlock(Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)).noCollision().strength(0.5F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY), weatherState);
  }

  private static WaxedLeverBlock waxedCopperLever(Identifier id) {
    return new WaxedLeverBlock(Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)).noCollision().strength(0.5F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
  }

  private static CopperRedstoneTorchBlock copperRedstoneTorch(WeatherState weatherState, Identifier id) {
    return new CopperRedstoneTorchBlock(
        Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)).noCollision().instabreak().lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 7 : 0).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY),
        weatherState);
  }

  private static WaxedRedstoneTorchBlock waxedCopperRedstoneTorch(Identifier id) {
    return new WaxedRedstoneTorchBlock(
        Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)).noCollision().instabreak().lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 7 : 0).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
  }

  private static CopperWallRedstoneTorchBlock copperWallRedstoneTorch(WeatherState weatherState, Identifier id) {
    return new CopperWallRedstoneTorchBlock(
        Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)).noCollision().instabreak().lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 7 : 0).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY),
        weatherState);
  }

  private static WaxedWallRedstoneTorchBlock waxedCopperWallRedstoneTorch(Identifier id) {
    return new WaxedWallRedstoneTorchBlock(
        Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)).noCollision().instabreak().lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 7 : 0).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
  }

  private static CopperRepeaterBlock copperRepeater(WeatherState weatherState, Identifier id) {
    return new CopperRepeaterBlock(Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)).instabreak().pushReaction(PushReaction.DESTROY), weatherState);
  }

  private static WaxedRepeaterBlock waxedCopperRepeater(Identifier id) {
    return new WaxedRepeaterBlock(Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)).instabreak().pushReaction(PushReaction.DESTROY));
  }

  private static CopperChainBlock copperChain(WeatherState weatherState, Identifier id) {
    return new CopperChainBlock(Properties.ofFullCopy(Blocks.IRON_CHAIN).setId(ResourceKey.create(Registries.BLOCK, id)), weatherState);
  }

  private static WaxedChainBlock waxedCopperChain(Identifier id) {
    return new WaxedChainBlock(Properties.ofFullCopy(Blocks.IRON_CHAIN).setId(ResourceKey.create(Registries.BLOCK, id)));
  }

  private static CopperPressurePlateBlock copperPressurePlate(WeatherState weatherState, Identifier id) {
    return new CopperPressurePlateBlock(150, Properties.ofFullCopy(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE).setId(ResourceKey.create(Registries.BLOCK, id)), BlockSetType.IRON, weatherState);
  }

  private static WaxedPressurePlateBlock waxedCopperPressurePlate(Identifier id) {
    return new WaxedPressurePlateBlock(150, Properties.ofFullCopy(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE).setId(ResourceKey.create(Registries.BLOCK, id)), BlockSetType.IRON);
  }
}
