package io.github.jason13official.more_useful_copper;

import io.github.jason13official.more_useful_copper.impl.common.ModConfig;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperGolem;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import io.github.jason13official.more_useful_copper.impl.common.registry.FabricModLootTableModifiers;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModTabs;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModTiles;
import io.github.jason13official.more_useful_copper.platform.Services;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.fabricmc.fabric.mixin.resource.loader.ReloadableResourceManagerImplMixin;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
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

    // fabric-specific, forge handles this via data files and a custom LootModifier
    FabricModLootTableModifiers.register();

    FabricDefaultAttributeRegistry.register(ModEntities.COPPER_STATUE, LivingEntity.createLivingAttributes());
    FabricDefaultAttributeRegistry.register(ModEntities.COPPER_GOLEM, CopperGolem.createAttributes());

    ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new ResourceReloadListener());
    // on to client init
  }

  /// Mimicking Botania's registration
  public <T> void bind(Registry<T> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {

    source.accept((t, rl) -> Registry.register(registry, rl, t));
  }

  public static class ResourceReloadListener implements SimpleSynchronousResourceReloadListener {

    @Override
    public ResourceLocation getFabricId() {
      return MoreUsefulCopper.identifier(Constants.MOD_ID);
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
      ModConfig.load(Services.PLATFORM.getConfigDirectory());
    }
  }
}
