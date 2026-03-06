package io.github.jason13official.more_useful_copper;

import io.github.jason13official.more_useful_copper.api.common.block.IOxidizableBlock;
import io.github.jason13official.more_useful_copper.api.common.block.WaxableRegistry;
import net.minecraft.resources.ResourceLocation;

public class MoreUsefulCopper {

  public static void init() {
    IOxidizableBlock.init();
    WaxableRegistry.init();
  }

  public static ResourceLocation identifier(String path) {
    
    return new ResourceLocation(Constants.MOD_ID, path);
  }
}