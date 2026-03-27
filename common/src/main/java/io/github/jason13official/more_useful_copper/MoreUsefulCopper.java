package io.github.jason13official.more_useful_copper;

import net.minecraft.resources.ResourceLocation;

public class MoreUsefulCopper {

  public static void init() {
  }

  public static ResourceLocation identifier(String path) {
    
    return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
  }
}