package io.github.jason13official.more_useful_copper.impl.common.tags;

import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {

  public static TagKey<Item> WAX_SCRAPER;

  public static void init() {
    WAX_SCRAPER = TagKey.create(Registries.ITEM, MoreUsefulCopper.identifier("wax_scraper"));
  }
}
