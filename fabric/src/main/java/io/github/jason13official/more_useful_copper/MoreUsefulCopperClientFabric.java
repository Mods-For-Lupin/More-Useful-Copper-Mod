package io.github.jason13official.more_useful_copper;

import io.github.jason13official.more_useful_copper.impl.common.block.CopperRedstoneDustBlock;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;

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

    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COPPER_REDSTONE_DUST, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXPOSED_COPPER_REDSTONE_DUST, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEATHERED_COPPER_REDSTONE_DUST, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OXIDIZED_COPPER_REDSTONE_DUST, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_COPPER_REDSTONE_DUST, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_DUST, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_DUST, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_DUST, RenderType.cutout());

    ColorProviderRegistry.BLOCK.register(
        (state, level, pos, tint) -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.UNAFFECTED),
        ModBlocks.COPPER_REDSTONE_DUST, ModBlocks.WAXED_COPPER_REDSTONE_DUST);
    ColorProviderRegistry.BLOCK.register(
        (state, level, pos, tint) -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.EXPOSED),
        ModBlocks.EXPOSED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_DUST);
    ColorProviderRegistry.BLOCK.register(
        (state, level, pos, tint) -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.WEATHERED),
        ModBlocks.WEATHERED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_DUST);
    ColorProviderRegistry.BLOCK.register(
        (state, level, pos, tint) -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.OXIDIZED),
        ModBlocks.OXIDIZED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_DUST);
  }
}
