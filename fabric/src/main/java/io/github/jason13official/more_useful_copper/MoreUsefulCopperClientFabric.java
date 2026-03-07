package io.github.jason13official.more_useful_copper;

import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class MoreUsefulCopperClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    MoreUsefulCopperClient.init();

    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COPPER_COMPARATOR, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXPOSED_COPPER_COMPARATOR, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEATHERED_COPPER_COMPARATOR, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OXIDIZED_COPPER_COMPARATOR, RenderType.cutout());

    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_COPPER_COMPARATOR, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_EXPOSED_COPPER_COMPARATOR, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_WEATHERED_COPPER_COMPARATOR, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_OXIDIZED_COPPER_COMPARATOR, RenderType.cutout());
  }
}
