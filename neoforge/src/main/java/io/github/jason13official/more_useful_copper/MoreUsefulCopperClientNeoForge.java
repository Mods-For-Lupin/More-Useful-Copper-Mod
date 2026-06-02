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
import java.util.function.Consumer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;

public class MoreUsefulCopperClientNeoForge {

  public MoreUsefulCopperClientNeoForge(final IEventBus modEventBus) {

    modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> {
      MoreUsefulCopperClient.init();
    });

    MoreUsefulCopperClient.registerSpecialModelRenderers((id, unbaked) ->
        modEventBus.addListener((RegisterSpecialModelRendererEvent e) -> e.register(id, unbaked))
    );

    modEventBus.addListener(this::registerBlockColorHandlers);

    modEventBus.addListener(this::registerTileRenderers);
    modEventBus.addListener(this::registerEntityRenderers);

    modEventBus.addListener(this::registerEntityModels);
  }

  private void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
    event.registerEntityRenderer(ModEntities.COPPER_STATUE, CopperStatueRenderer::new);
    event.registerEntityRenderer(ModEntities.COPPER_BOTTOM_BOAT, CopperBottomBoatRenderer::new);
    // event.registerEntityRenderer(ModEntities.COPPER_GOLEM, CopperGolemRenderer::new);
    event.registerEntityRenderer(ModEntities.LIGHTNING_BOTTLE, ThrownItemRenderer::new);
  }

  private void registerTileRenderers(EntityRenderersEvent.RegisterRenderers event) {
    event.registerBlockEntityRenderer(ModTiles.COPPER_BELL, CopperBellRenderer::new);
  }

  private void registerEntityModels(EntityRenderersEvent.RegisterLayerDefinitions event) {
    event.registerLayerDefinition(CopperGolemModel.LAYER_LOCATION, CopperGolemModel::createBodyLayer);
    event.registerLayerDefinition(CopperBottomBoatModel.LAYER_LOCATION, CopperBottomBoatModel::createBodyModel);

    for (Type type : Type.values()) {
      switch (type) {
        case CREEPER -> event.registerLayerDefinition(ModModelLayers.createBoatModelName(type), CreeperStatueModel::createBodyLayer);
        case SKELETON -> event.registerLayerDefinition(ModModelLayers.createBoatModelName(type), SkeletonStatueModel::createBodyLayer);
        case SPIDER -> event.registerLayerDefinition(ModModelLayers.createBoatModelName(type), SpiderStatueModel::createBodyLayer);
        case ZOMBIE -> event.registerLayerDefinition(ModModelLayers.createBoatModelName(type), ZombieStatueModel::createBodyLayer);
      }
    }
  }

  private void registerBlockColorHandlers(RegisterColorHandlersEvent.BlockTintSources event) {
    event.register(List.of(state -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.UNAFFECTED)),
        ModBlocks.COPPER_REDSTONE_DUST, ModBlocks.WAXED_COPPER_REDSTONE_DUST);
    event.register(List.of(state -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.EXPOSED)),
        ModBlocks.EXPOSED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_DUST);
    event.register(List.of(state -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.WEATHERED)),
        ModBlocks.WEATHERED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_DUST);
    event.register(List.of(state -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.OXIDIZED)),
        ModBlocks.OXIDIZED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_DUST);
  }
}
