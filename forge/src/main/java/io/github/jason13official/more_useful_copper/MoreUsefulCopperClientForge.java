package io.github.jason13official.more_useful_copper;

import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import java.util.function.Consumer;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;

public class MoreUsefulCopperClientForge {

  public MoreUsefulCopperClientForge(final IEventBus modEventBus) {

    modEventBus.addListener((Consumer<RegisterColorHandlersEvent.Block>) event -> {
      event.register((blockState, blockAndTintGetter, blockPos, tintIndex) -> 0xFFA500, ModBlocks.COPPER_REDSTONE_DUST);
    });
  }
}
