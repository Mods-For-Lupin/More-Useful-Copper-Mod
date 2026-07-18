package io.github.jason13official.more_useful_copper;

import io.github.jason13official.more_useful_copper.impl.client.model.CopperBottomBoatModel;
import io.github.jason13official.more_useful_copper.impl.client.model.CopperGolemModel;
import io.github.jason13official.more_useful_copper.impl.client.model.CreeperStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.SkeletonStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.SpiderStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.ZombieStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.geom.ModModelLayers;
import io.github.jason13official.more_useful_copper.impl.client.renderer.blockentity.CopperBellRenderer;
import io.github.jason13official.more_useful_copper.impl.client.renderer.entity.CopperBottomBoatRenderer;
import io.github.jason13official.more_useful_copper.impl.client.renderer.entity.CopperGolemRenderer;
import io.github.jason13official.more_useful_copper.impl.client.renderer.entity.CopperStatueRenderer;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperRedstoneDustBlock;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue.Type;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModTiles;
import java.util.List;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;

public class MoreUsefulCopperClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    MoreUsefulCopperClient.init();
    MoreUsefulCopperClient.registerSpecialModelRenderers(SpecialModelRenderers.ID_MAPPER::put);

    this.registerModelLayers();

    this.registerEntityRenderers();

    this.registerTileRenderers();

    this.registerBlockColorHandlers();
  }

  private void registerModelLayers() {
    ModelLayerRegistry.registerModelLayer(CopperGolemModel.LAYER_LOCATION, CopperGolemModel::createBodyLayer);
    ModelLayerRegistry.registerModelLayer(CopperBottomBoatModel.LAYER_LOCATION, CopperBottomBoatModel::createBodyModel);

    for (Type type : Type.values()) {
      switch (type) {
        case CREEPER -> ModelLayerRegistry.registerModelLayer(ModModelLayers.createBoatModelName(type), CreeperStatueModel::createBodyLayer);
        case SKELETON -> ModelLayerRegistry.registerModelLayer(ModModelLayers.createBoatModelName(type), SkeletonStatueModel::createBodyLayer);
        case SPIDER -> ModelLayerRegistry.registerModelLayer(ModModelLayers.createBoatModelName(type), SpiderStatueModel::createBodyLayer);
        case ZOMBIE -> ModelLayerRegistry.registerModelLayer(ModModelLayers.createBoatModelName(type), ZombieStatueModel::createBodyLayer);
      }
    }
  }

  private void registerBlockColorHandlers() {
    BlockColorRegistry.register(List.of(state -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.UNAFFECTED)),
        ModBlocks.COPPER_REDSTONE_DUST, ModBlocks.WAXED_COPPER_REDSTONE_DUST);
    BlockColorRegistry.register(List.of(state -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.EXPOSED)),
        ModBlocks.EXPOSED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_DUST);
    BlockColorRegistry.register(List.of(state -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.WEATHERED)),
        ModBlocks.WEATHERED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_DUST);
    BlockColorRegistry.register(List.of(state -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.OXIDIZED)),
        ModBlocks.OXIDIZED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_DUST);
  }

  private void registerTileRenderers() {
    BlockEntityRenderers.register(ModTiles.COPPER_BELL, CopperBellRenderer::new);
  }

  private void registerEntityRenderers() {

    EntityRenderers.register(ModEntities.COPPER_STATUE, CopperStatueRenderer::new);
    EntityRenderers.register(ModEntities.LIGHTNING_BOTTLE, ThrownItemRenderer::new);
    // EntityRenderers.register(ModEntities.COPPER_GOLEM, CopperGolemRenderer::new);
    EntityRenderers.register(ModEntities.COPPER_BOTTOM_BOAT, CopperBottomBoatRenderer::new);
  }
}
