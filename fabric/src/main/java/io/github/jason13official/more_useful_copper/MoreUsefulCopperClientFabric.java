package io.github.jason13official.more_useful_copper;

import io.github.jason13official.more_useful_copper.impl.client.model.CopperBottomBoatModel;
import io.github.jason13official.more_useful_copper.impl.client.model.CopperGolemModel;
import io.github.jason13official.more_useful_copper.impl.client.model.CreeperStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.SkeletonStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.SpiderStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.ZombieStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.geom.ModModelLayers;
import io.github.jason13official.more_useful_copper.impl.client.renderer.CopperStatueItemRenderer;
import io.github.jason13official.more_useful_copper.impl.client.renderer.blockentity.CopperBellRenderer;
import io.github.jason13official.more_useful_copper.impl.client.renderer.entity.CopperBottomBoatRenderer;
import io.github.jason13official.more_useful_copper.impl.client.renderer.entity.CopperStatueRenderer;
import io.github.jason13official.more_useful_copper.impl.client.renderer.entity.CopperGolemRenderer;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperRedstoneDustBlock;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue.Type;
import io.github.jason13official.more_useful_copper.impl.common.item.MoistureCompassItem;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModTiles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;

public class MoreUsefulCopperClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    MoreUsefulCopperClient.init();

    // fabric-specific, Forge handles this via `"render_type": "minecraft:cutout` added to block model files
    this.registerBlockRenderTypes();

    this.registerItemProperties();

    this.registerItemRenderers();

    this.registerEntityRenderers();

    this.registerTileRenderers();

    this.registerEntityModels();

    this.registerBlockColorHandlers();
  }

  private void registerBlockColorHandlers() {
    ColorProviderRegistry.BLOCK.register((state, level, pos, tint) -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.UNAFFECTED),
        ModBlocks.COPPER_REDSTONE_DUST, ModBlocks.WAXED_COPPER_REDSTONE_DUST);
    ColorProviderRegistry.BLOCK.register((state, level, pos, tint) -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.EXPOSED),
        ModBlocks.EXPOSED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_DUST);
    ColorProviderRegistry.BLOCK.register((state, level, pos, tint) -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.WEATHERED),
        ModBlocks.WEATHERED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_DUST);
    ColorProviderRegistry.BLOCK.register((state, level, pos, tint) -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.OXIDIZED),
        ModBlocks.OXIDIZED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_DUST);
  }

  private void registerEntityModels() {
    EntityModelLayerRegistry.registerModelLayer(CopperGolemModel.LAYER_LOCATION, CopperGolemModel::createBodyLayer);
    EntityModelLayerRegistry.registerModelLayer(CopperBottomBoatModel.LAYER_LOCATION, CopperBottomBoatModel::createBodyModel);

    for (Type type : Type.values()) {
      switch (type) {
        case CREEPER -> EntityModelLayerRegistry.registerModelLayer(ModModelLayers.createBoatModelName(type), CreeperStatueModel::createBodyLayer);
        case SKELETON -> EntityModelLayerRegistry.registerModelLayer(ModModelLayers.createBoatModelName(type), SkeletonStatueModel::createBodyLayer);
        case SPIDER -> EntityModelLayerRegistry.registerModelLayer(ModModelLayers.createBoatModelName(type), SpiderStatueModel::createBodyLayer);
        case ZOMBIE -> EntityModelLayerRegistry.registerModelLayer(ModModelLayers.createBoatModelName(type), ZombieStatueModel::createBodyLayer);
      }
    }
  }

  private void registerTileRenderers() {
    BlockEntityRenderers.register(ModTiles.COPPER_BELL, CopperBellRenderer::new);
  }

  private void registerEntityRenderers() {
    EntityRendererRegistry.register(ModEntities.COPPER_STATUE, CopperStatueRenderer::new);
    EntityRendererRegistry.register(ModEntities.LIGHTNING_BOTTLE, ThrownItemRenderer::new);
    EntityRendererRegistry.register(ModEntities.COPPER_GOLEM, CopperGolemRenderer::new);
    EntityRendererRegistry.register(ModEntities.COPPER_BOTTOM_BOAT, CopperBottomBoatRenderer::new);
  }

  private void registerItemRenderers() {
    BuiltinItemRendererRegistry.INSTANCE.register(ModItems.COPPER_STATUE_CREEPER, CopperStatueItemRenderer.INSTANCE::renderByItem);
    BuiltinItemRendererRegistry.INSTANCE.register(ModItems.COPPER_STATUE_SKELETON, CopperStatueItemRenderer.INSTANCE::renderByItem);
    BuiltinItemRendererRegistry.INSTANCE.register(ModItems.COPPER_STATUE_SPIDER, CopperStatueItemRenderer.INSTANCE::renderByItem);
    BuiltinItemRendererRegistry.INSTANCE.register(ModItems.COPPER_STATUE_ZOMBIE, CopperStatueItemRenderer.INSTANCE::renderByItem);
  }

  private void registerItemProperties() {
    ItemProperties.register(ModItems.MOISTURE_COMPASS, new ResourceLocation("angle"), new CompassItemPropertyFunction((clientLevel, itemStack, entity) -> {
      return !MoistureCompassItem.isMoistureCompass(itemStack) ? CompassItem.getSpawnPosition(clientLevel) : MoistureCompassItem.getMoisturePosition(itemStack.getOrCreateTag());
    }));
  }

  private void registerBlockRenderTypes() {
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GARDEN_STAKE, RenderType.cutout());

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
  }
}
