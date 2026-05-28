package io.github.jason13official.more_useful_copper;

import io.github.jason13official.more_useful_copper.api.common.block.IOxidizableBlock;
import io.github.jason13official.more_useful_copper.api.common.block.WaxableRegistry;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModBlockTags;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModItemTags;
import net.minecraft.resources.Identifier;

public class MoreUsefulCopper {

  public static void init() {
    IOxidizableBlock.init();
    WaxableRegistry.init();

    ModBlockTags.init();
    ModItemTags.init();
  }

  public static Identifier identifier(String path) {

    return Identifier.fromNamespaceAndPath(Constants.MOD_ID, path);
  }
}