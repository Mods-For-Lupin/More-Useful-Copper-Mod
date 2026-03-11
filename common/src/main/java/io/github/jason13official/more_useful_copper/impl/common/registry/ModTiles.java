package io.github.jason13official.more_useful_copper.impl.common.registry;

import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.common.block.entity.CopperBellBlockEntity;
import io.github.jason13official.more_useful_copper.impl.common.block.entity.CopperComparatorBlockEntity;
import io.github.jason13official.more_useful_copper.platform.Services;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModTiles {

  public static BlockEntityType<CopperComparatorBlockEntity> COPPER_COMPARATOR;
  public static BlockEntityType<CopperBellBlockEntity> COPPER_BELL;

  public static void register(BiConsumer<BlockEntityType<?>, ResourceLocation> consumer) {
    COPPER_COMPARATOR = Services.PLATFORM.tileBuilder(
        CopperComparatorBlockEntity::new,
        ModBlocks.COPPER_COMPARATOR,
        ModBlocks.EXPOSED_COPPER_COMPARATOR,
        ModBlocks.WEATHERED_COPPER_COMPARATOR,
        ModBlocks.OXIDIZED_COPPER_COMPARATOR,
        ModBlocks.WAXED_COPPER_COMPARATOR,
        ModBlocks.WAXED_EXPOSED_COPPER_COMPARATOR,
        ModBlocks.WAXED_WEATHERED_COPPER_COMPARATOR,
        ModBlocks.WAXED_OXIDIZED_COPPER_COMPARATOR
    ).build(null);

    COPPER_BELL = Services.PLATFORM.tileBuilder(CopperBellBlockEntity::new, ModBlocks.COPPER_BELL).build(null);

    consumer.accept(COPPER_COMPARATOR, MoreUsefulCopper.identifier("copper_comparator"));
    consumer.accept(COPPER_BELL, MoreUsefulCopper.identifier("copper_bell"));
  }
}
