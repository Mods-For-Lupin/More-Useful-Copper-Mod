package io.github.jason13official.more_useful_copper.impl.common.registry;

import io.github.jason13official.more_useful_copper.Constants;
import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.platform.Services;
import java.util.function.BiConsumer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModTabs {

  public static CreativeModeTab MORE_USEFUL_COPPER;

  public static void register(BiConsumer<CreativeModeTab, Identifier> consumer) {

    MORE_USEFUL_COPPER = Services.PLATFORM.tabBuilder()
        .icon(() -> new ItemStack(Items.COPPER_BLOCK))
        .title(Component.translatable("itemGroup.moreUsefulCopper"))
        .displayItems((itemDisplayParameters, output) -> {
          addItemsToTabOutput(output);
        }).build();

    consumer.accept(MORE_USEFUL_COPPER, MoreUsefulCopper.identifier(Constants.MOD_ID));
  }

  private static void addItemsToTabOutput(Output output) {
    // output.accept(ModItems.SPARKSTONE_TORCH);
    // output.accept(ModItems.SPARKSTONE_RELAY);

    output.accept(ModItems.WAX_SCRAPER);
    output.accept(ModItems.SPRAY_BOTTLE);
    output.accept(ModItems.COPPER_BOTTOM_BOAT);
    output.accept(ModItems.GARDEN_STAKE);
    output.accept(ModItems.LIGHTNING_BOTTLE);
    output.accept(ModItems.MOISTURE_COMPASS);

    output.accept(ModItems.COPPER_STATUE_CREEPER);
    output.accept(ModItems.COPPER_STATUE_SKELETON);
    output.accept(ModItems.COPPER_STATUE_SPIDER);
    output.accept(ModItems.COPPER_STATUE_ZOMBIE);

    output.accept(ModBlocks.COPPER_BELL);

    output.accept(ModItems.COPPER_NUGGET);
    output.accept(ModItems.COPPER_SHEARS);
    output.accept(ModItems.COPPER_PICKAXE);
    output.accept(ModItems.COPPER_AXE);
    output.accept(ModItems.COPPER_HOE);
    output.accept(ModItems.COPPER_SHOVEL);
    output.accept(ModItems.COPPER_SWORD);
    output.accept(ModItems.COPPER_HELMET);
    output.accept(ModItems.COPPER_CHESTPLATE);
    output.accept(ModItems.COPPER_LEGGINGS);
    output.accept(ModItems.COPPER_BOOTS);
    output.accept(ModItems.COPPER_CHAIN);
    output.accept(ModBlocks.EXPOSED_COPPER_CHAIN);
    output.accept(ModBlocks.WEATHERED_COPPER_CHAIN);
    output.accept(ModBlocks.OXIDIZED_COPPER_CHAIN);
    output.accept(ModBlocks.WAXED_COPPER_CHAIN);
    output.accept(ModBlocks.WAXED_EXPOSED_COPPER_CHAIN);
    output.accept(ModBlocks.WAXED_WEATHERED_COPPER_CHAIN);
    output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_CHAIN);
    output.accept(ModItems.COPPER_HORSE_ARMOR);

    // TODO create entity
    output.accept(ModItems.COPPER_GOLEM_SPAWN_EGG);

    output.accept(ModItems.COPPER_PRESSURE_PLATE);
    output.accept(ModBlocks.EXPOSED_COPPER_PRESSURE_PLATE);
    output.accept(ModBlocks.WEATHERED_COPPER_PRESSURE_PLATE);
    output.accept(ModBlocks.OXIDIZED_COPPER_PRESSURE_PLATE);
    output.accept(ModBlocks.WAXED_COPPER_PRESSURE_PLATE);
    output.accept(ModBlocks.WAXED_EXPOSED_COPPER_PRESSURE_PLATE);
    output.accept(ModBlocks.WAXED_WEATHERED_COPPER_PRESSURE_PLATE);
    output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_PRESSURE_PLATE);

    output.accept(ModBlocks.COPPER_BUTTON);
    output.accept(ModBlocks.EXPOSED_COPPER_BUTTON);
    output.accept(ModBlocks.WEATHERED_COPPER_BUTTON);
    output.accept(ModBlocks.OXIDIZED_COPPER_BUTTON);
    output.accept(ModBlocks.WAXED_COPPER_BUTTON);
    output.accept(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON);
    output.accept(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON);
    output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON);

    output.accept(ModBlocks.COPPER_LEVER);
    output.accept(ModBlocks.EXPOSED_COPPER_LEVER);
    output.accept(ModBlocks.WEATHERED_COPPER_LEVER);
    output.accept(ModBlocks.OXIDIZED_COPPER_LEVER);
    output.accept(ModBlocks.WAXED_COPPER_LEVER);
    output.accept(ModBlocks.WAXED_EXPOSED_COPPER_LEVER);
    output.accept(ModBlocks.WAXED_WEATHERED_COPPER_LEVER);
    output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_LEVER);

    output.accept(ModBlocks.COPPER_COMPARATOR);
    output.accept(ModBlocks.EXPOSED_COPPER_COMPARATOR);
    output.accept(ModBlocks.WEATHERED_COPPER_COMPARATOR);
    output.accept(ModBlocks.OXIDIZED_COPPER_COMPARATOR);
    output.accept(ModBlocks.WAXED_COPPER_COMPARATOR);
    output.accept(ModBlocks.WAXED_EXPOSED_COPPER_COMPARATOR);
    output.accept(ModBlocks.WAXED_WEATHERED_COPPER_COMPARATOR);
    output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_COMPARATOR);

    output.accept(ModBlocks.COPPER_REDSTONE_DUST);
    output.accept(ModBlocks.EXPOSED_COPPER_REDSTONE_DUST);
    output.accept(ModBlocks.WEATHERED_COPPER_REDSTONE_DUST);
    output.accept(ModBlocks.OXIDIZED_COPPER_REDSTONE_DUST);
    output.accept(ModBlocks.WAXED_COPPER_REDSTONE_DUST);
    output.accept(ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_DUST);
    output.accept(ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_DUST);
    output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_DUST);

    output.accept(ModBlocks.COPPER_REDSTONE_TORCH);
    output.accept(ModBlocks.EXPOSED_COPPER_REDSTONE_TORCH);
    output.accept(ModBlocks.WEATHERED_COPPER_REDSTONE_TORCH);
    output.accept(ModBlocks.OXIDIZED_COPPER_REDSTONE_TORCH);
    output.accept(ModBlocks.WAXED_COPPER_REDSTONE_TORCH);
    output.accept(ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_TORCH);
    output.accept(ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_TORCH);
    output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_TORCH);

    output.accept(ModBlocks.COPPER_REPEATER);
    output.accept(ModBlocks.EXPOSED_COPPER_REPEATER);
    output.accept(ModBlocks.WEATHERED_COPPER_REPEATER);
    output.accept(ModBlocks.OXIDIZED_COPPER_REPEATER);
    output.accept(ModBlocks.WAXED_COPPER_REPEATER);
    output.accept(ModBlocks.WAXED_EXPOSED_COPPER_REPEATER);
    output.accept(ModBlocks.WAXED_WEATHERED_COPPER_REPEATER);
    output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_REPEATER);
  }
}
