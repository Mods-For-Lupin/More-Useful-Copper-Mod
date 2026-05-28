package io.github.jason13official.more_useful_copper.api.common.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ToolMaterial;

public class ModTiers {

  public static final ToolMaterial COPPER = new ToolMaterial(
      BlockTags.INCORRECT_FOR_COPPER_TOOL,
      150,
      6.0F,
      1.0F,
      22,
      ItemTags.COPPER_TOOL_MATERIALS
  );
}
