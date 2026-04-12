package io.github.jason13official.more_useful_copper;

import io.github.jason13official.more_useful_copper.api.common.block.IOxidizableBlock;
import io.github.jason13official.more_useful_copper.api.common.block.WaxableRegistry;
import io.github.jason13official.more_useful_copper.impl.common.ModConfig;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModBlockTags;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModItemTags;
import io.github.jason13official.more_useful_copper.platform.Services;
import net.minecraft.resources.ResourceLocation;

public class MoreUsefulCopper {

  public static void init() {
    ModConfig.load(Services.PLATFORM.getConfigDirectory());

    IOxidizableBlock.init();
    WaxableRegistry.init();

    ModBlockTags.init();
    ModItemTags.init();
  }

  public static ResourceLocation identifier(String path) {

    return new ResourceLocation(Constants.MOD_ID, path);
  }
}