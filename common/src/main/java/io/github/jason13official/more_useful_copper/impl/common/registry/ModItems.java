package io.github.jason13official.more_useful_copper.impl.common.registry;

import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.common.item.SprayBottleItem;
import io.github.jason13official.more_useful_copper.impl.common.item.WaxScraperItem;
import java.util.function.BiConsumer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.StandingAndWallBlockItem;

public class ModItems {

  public static Item WAX_SCRAPER;
  public static Item SPRAY_BOTTLE;

  public static void register(BiConsumer<Item, ResourceLocation> consumer) {
    WAX_SCRAPER = new WaxScraperItem(new Properties().durability(64));
    SPRAY_BOTTLE = new SprayBottleItem(new Properties().durability(64));
    consumer.accept(SPRAY_BOTTLE, MoreUsefulCopper.identifier("spray_bottle"));
    consumer.accept(WAX_SCRAPER, MoreUsefulCopper.identifier("wax_scraper"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_BELL, new Properties()), MoreUsefulCopper.identifier("copper_bell"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("copper_button"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("exposed_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("weathered_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("oxidized_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("waxed_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("waxed_exposed_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("waxed_weathered_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("waxed_oxidized_copper_button"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("exposed_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("weathered_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("oxidized_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("waxed_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("waxed_exposed_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("waxed_weathered_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("waxed_oxidized_copper_lever"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("exposed_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("weathered_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("oxidized_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("waxed_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("waxed_exposed_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("waxed_weathered_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("waxed_oxidized_copper_comparator"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("exposed_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("weathered_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("oxidized_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("waxed_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("waxed_exposed_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("waxed_weathered_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("waxed_oxidized_copper_redstone_dust"));

    // Torch items use StandingAndWallBlockItem so one item places both floor and wall variants
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.COPPER_REDSTONE_TORCH, ModBlocks.COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.EXPOSED_COPPER_REDSTONE_TORCH, ModBlocks.EXPOSED_COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("exposed_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.WEATHERED_COPPER_REDSTONE_TORCH, ModBlocks.WEATHERED_COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("weathered_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.OXIDIZED_COPPER_REDSTONE_TORCH, ModBlocks.OXIDIZED_COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("oxidized_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.WAXED_COPPER_REDSTONE_TORCH, ModBlocks.WAXED_COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("waxed_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_TORCH, ModBlocks.WAXED_EXPOSED_COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("waxed_exposed_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_TORCH, ModBlocks.WAXED_WEATHERED_COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("waxed_weathered_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_TORCH, ModBlocks.WAXED_OXIDIZED_COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("waxed_oxidized_copper_redstone_torch"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("exposed_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("weathered_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("oxidized_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("waxed_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("waxed_exposed_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("waxed_weathered_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("waxed_oxidized_copper_repeater"));
  }
}
