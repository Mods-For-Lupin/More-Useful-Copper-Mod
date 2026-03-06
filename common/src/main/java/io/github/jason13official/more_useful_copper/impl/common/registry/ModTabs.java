package io.github.jason13official.more_useful_copper.impl.common.registry;

import io.github.jason13official.more_useful_copper.Constants;
import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.platform.Services;
import java.util.function.BiConsumer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModTabs {

  public static CreativeModeTab MORE_USEFUL_COPPER;

  public static void register(BiConsumer<CreativeModeTab, ResourceLocation> consumer) {

    MORE_USEFUL_COPPER = Services.PLATFORM.tabBuilder()
        .icon(() -> new ItemStack(Items.COPPER_BLOCK))
        .title(Component.translatable("itemGroup.moreUsefulCopper"))
        .displayItems((itemDisplayParameters, output) -> {
          output.accept(ModBlocks.COPPER_BUTTON);
          output.accept(ModBlocks.EXPOSED_COPPER_BUTTON);
          output.accept(ModBlocks.WEATHERED_COPPER_BUTTON);
          output.accept(ModBlocks.OXIDIZED_COPPER_BUTTON);
          output.accept(ModBlocks.WAXED_COPPER_BUTTON);
          output.accept(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON);
          output.accept(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON);
          output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON);
          output.accept(ModItems.WAX_SCRAPER);
          output.accept(ModItems.SPRAY_BOTTLE);
        })
        .build();

    consumer.accept(MORE_USEFUL_COPPER, MoreUsefulCopper.identifier(Constants.MOD_ID));
  }
}
