package io.github.jason13official.more_useful_copper;

import io.github.jason13official.more_useful_copper.impl.common.ModConfig;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperGolem;
import io.github.jason13official.more_useful_copper.impl.common.registry.ForgeModLootTableModifiers;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModTabs;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModTiles;
import io.github.jason13official.more_useful_copper.platform.Services;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class MoreUsefulCopperForge {

  public static IEventBus EVENT_BUS;

  public MoreUsefulCopperForge(final FMLJavaModLoadingContext context) {

    EVENT_BUS = context.getModEventBus();

    bind(Registries.BLOCK, ModBlocks::register);
    bind(Registries.BLOCK_ENTITY_TYPE, ModTiles::register);
    bind(Registries.ENTITY_TYPE, ModEntities::register);
    bind(Registries.ITEM, ModItems::register);
    bind(Registries.CREATIVE_MODE_TAB, ModTabs::register);

    // after all RegisterEvents have fired, blocks/game object fields are guaranteed to be populated
    EVENT_BUS.addListener((Consumer<FMLCommonSetupEvent>) event -> MoreUsefulCopper.init());

    // forge-specific, registers loot modifier types used in data files under `loot_modifiers`
    ForgeModLootTableModifiers.register(EVENT_BUS);

    EVENT_BUS.addListener((Consumer<EntityAttributeCreationEvent>) event -> {
      event.put(ModEntities.COPPER_STATUE, LivingEntity.createLivingAttributes().build());
      event.put(ModEntities.COPPER_GOLEM, CopperGolem.createAttributes().build());
    });

    MinecraftForge.EVENT_BUS.addListener((Consumer<AddReloadListenerEvent>) event -> {
      event.addListener(new ResourceReloadListener());
    });

    // on to client init
    if (FMLLoader.getDist() == Dist.CLIENT) {
      new MoreUsefulCopperClientForge(EVENT_BUS);
    }
  }

  public MoreUsefulCopperForge() {
    this(FMLJavaModLoadingContext.get());
  }

  /// Mimicking Botania's registration
  public <T> void bind(ResourceKey<Registry<T>> registryKey, Consumer<BiConsumer<T, ResourceLocation>> source) {

//    source.accept((t, rl) -> Registry.register(registry, rl, t));
    EVENT_BUS.addListener((Consumer<RegisterEvent>) event -> {
      if (registryKey.equals(event.getRegistryKey())) {
        source.accept((t, rl) -> event.register(registryKey, rl, () -> t));
      }
    });
  }

  public static class ResourceReloadListener extends SimplePreparableReloadListener<Void> {

    @Override
    protected Void prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
      return null;
    }

    @Override
    protected void apply(Void unused, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
      ModConfig.load(Services.PLATFORM.getConfigDirectory());
    }

    @Override
    public String getName() {
      return MoreUsefulCopper.identifier(Constants.MOD_ID).toString();
    }
  }
}