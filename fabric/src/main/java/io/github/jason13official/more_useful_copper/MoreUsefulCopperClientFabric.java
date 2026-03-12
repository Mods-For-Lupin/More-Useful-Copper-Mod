package io.github.jason13official.more_useful_copper;

import io.github.jason13official.more_useful_copper.impl.client.model.CreeperStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.SkeletonStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.SpiderStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.ZombieStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.geom.ModModelLayers;
import io.github.jason13official.more_useful_copper.impl.client.renderer.blockentity.CopperBellRenderer;
import io.github.jason13official.more_useful_copper.impl.client.renderer.entity.CopperStatueRenderer;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperRedstoneDustBlock;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue.Type;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModTiles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;

public class MoreUsefulCopperClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    MoreUsefulCopperClient.init();

    EntityRendererRegistry.register(ModEntities.COPPER_STATUE, CopperStatueRenderer::new);
    for (Type type : Type.values()) {
      switch (type) {
        case CREEPER -> EntityModelLayerRegistry.registerModelLayer(ModModelLayers.createBoatModelName(type), CreeperStatueModel::createBodyLayer);
        case SKELETON -> EntityModelLayerRegistry.registerModelLayer(ModModelLayers.createBoatModelName(type), SkeletonStatueModel::createBodyLayer);
        case SPIDER -> EntityModelLayerRegistry.registerModelLayer(ModModelLayers.createBoatModelName(type), SpiderStatueModel::createBodyLayer);
        case ZOMBIE -> EntityModelLayerRegistry.registerModelLayer(ModModelLayers.createBoatModelName(type), ZombieStatueModel::createBodyLayer);
      }
    }

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

    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COPPER_REDSTONE_TORCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXPOSED_COPPER_REDSTONE_TORCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEATHERED_COPPER_REDSTONE_TORCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OXIDIZED_COPPER_REDSTONE_TORCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_COPPER_REDSTONE_TORCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_TORCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_TORCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_TORCH, RenderType.cutout());

    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COPPER_WALL_REDSTONE_TORCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXPOSED_COPPER_WALL_REDSTONE_TORCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEATHERED_COPPER_WALL_REDSTONE_TORCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OXIDIZED_COPPER_WALL_REDSTONE_TORCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_COPPER_WALL_REDSTONE_TORCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_EXPOSED_COPPER_WALL_REDSTONE_TORCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_WEATHERED_COPPER_WALL_REDSTONE_TORCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_OXIDIZED_COPPER_WALL_REDSTONE_TORCH, RenderType.cutout());

    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COPPER_REPEATER, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXPOSED_COPPER_REPEATER, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEATHERED_COPPER_REPEATER, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OXIDIZED_COPPER_REPEATER, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_COPPER_REPEATER, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_EXPOSED_COPPER_REPEATER, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_WEATHERED_COPPER_REPEATER, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_OXIDIZED_COPPER_REPEATER, RenderType.cutout());

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

    BlockEntityRenderers.register(ModTiles.COPPER_BELL, CopperBellRenderer::new);
  }
}
