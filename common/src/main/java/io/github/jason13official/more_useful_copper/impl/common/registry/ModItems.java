package io.github.jason13official.more_useful_copper.impl.common.registry;

import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.common.item.WaxScraperItem;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class ModItems {

  public static Item WAX_SCRAPER;

  public static void register(BiConsumer<Item, ResourceLocation> consumer) {
    WAX_SCRAPER = new WaxScraperItem(new Properties().durability(64));
    consumer.accept(WAX_SCRAPER, MoreUsefulCopper.identifier("wax_scraper"));
    consumer.accept(new BlockItem(ModBlocks.COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("copper_button"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("exposed_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("weathered_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("oxidized_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("waxed_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("waxed_exposed_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("waxed_weathered_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("waxed_oxidized_copper_button"));
  }
}
