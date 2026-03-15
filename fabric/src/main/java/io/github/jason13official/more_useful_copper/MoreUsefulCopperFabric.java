package io.github.jason13official.more_useful_copper;

import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModTabs;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModTiles;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class MoreUsefulCopperFabric implements ModInitializer {

  @Override
  public void onInitialize() {

    bind(BuiltInRegistries.BLOCK, ModBlocks::register);
    bind(BuiltInRegistries.BLOCK_ENTITY_TYPE, ModTiles::register);
    bind(BuiltInRegistries.ENTITY_TYPE, ModEntities::register);
    bind(BuiltInRegistries.ITEM, ModItems::register);
    bind(BuiltInRegistries.CREATIVE_MODE_TAB, ModTabs::register);

    // after game object registration
    MoreUsefulCopper.init();

    FabricDefaultAttributeRegistry.register(ModEntities.COPPER_STATUE, LivingEntity.createLivingAttributes());

    // on to client init
  }

  /// Mimicking Botania's registration
  public <T> void bind(Registry<T> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {

    source.accept((t, rl) -> Registry.register(registry, rl, t));
  }
}
