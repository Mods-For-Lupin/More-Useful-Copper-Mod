package io.github.jason13official.more_useful_copper;

import io.github.jason13official.more_useful_copper.impl.client.model.CreeperStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.SkeletonStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.SpiderStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.ZombieStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.geom.ModModelLayers;
import io.github.jason13official.more_useful_copper.impl.client.renderer.blockentity.CopperBellRenderer;
import io.github.jason13official.more_useful_copper.impl.client.renderer.entity.CopperStatueRenderer;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperRedstoneDustBlock;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue.Type;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModTiles;
import java.util.function.Consumer;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;

public class MoreUsefulCopperClientForge {

  public MoreUsefulCopperClientForge(final IEventBus modEventBus) {

    modEventBus.addListener((Consumer<RegisterColorHandlersEvent.Block>) event -> {
      event.register(
          (state, level, pos, tint) -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.UNAFFECTED),
          ModBlocks.COPPER_REDSTONE_DUST, ModBlocks.WAXED_COPPER_REDSTONE_DUST);
      event.register(
          (state, level, pos, tint) -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.EXPOSED),
          ModBlocks.EXPOSED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_DUST);
      event.register(
          (state, level, pos, tint) -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.WEATHERED),
          ModBlocks.WEATHERED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_DUST);
      event.register(
          (state, level, pos, tint) -> CopperRedstoneDustBlock.getColorForPower(state.getValue(CopperRedstoneDustBlock.POWER), WeatherState.OXIDIZED),
          ModBlocks.OXIDIZED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_DUST);
    });

    modEventBus.addListener((Consumer<EntityRenderersEvent.RegisterRenderers>) event -> {
      event.registerBlockEntityRenderer(ModTiles.COPPER_BELL, CopperBellRenderer::new);
      event.registerEntityRenderer(ModEntities.COPPER_STATUE, CopperStatueRenderer::new);
    });

    modEventBus.addListener((Consumer<EntityRenderersEvent.RegisterLayerDefinitions>) event -> {
      for (Type type : Type.values()) {
        switch (type) {
          case CREEPER -> event.registerLayerDefinition(ModModelLayers.createBoatModelName(type), CreeperStatueModel::createBodyLayer);
          case SKELETON -> event.registerLayerDefinition(ModModelLayers.createBoatModelName(type), SkeletonStatueModel::createBodyLayer);
          case SPIDER -> event.registerLayerDefinition(ModModelLayers.createBoatModelName(type), SpiderStatueModel::createBodyLayer);
          case ZOMBIE -> event.registerLayerDefinition(ModModelLayers.createBoatModelName(type), ZombieStatueModel::createBodyLayer);
        }
      }
    });
  }
}
