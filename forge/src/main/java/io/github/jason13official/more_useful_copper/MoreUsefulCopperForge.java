package io.github.jason13official.more_useful_copper;

import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModTabs;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModTiles;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
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

    EVENT_BUS.addListener((Consumer<EntityAttributeCreationEvent>) event -> {
      event.put(ModEntities.COPPER_STATUE, LivingEntity.createLivingAttributes().build());
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
}