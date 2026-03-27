package io.github.jason13official.more_useful_copper.impl.common.tags;

import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {

  public static TagKey<Block> COPPER_REDSTONE_WIRE;

  public static void init() {
    COPPER_REDSTONE_WIRE = TagKey.create(Registries.BLOCK, MoreUsefulCopper.identifier("copper_redstone_wire"));
  }
}
