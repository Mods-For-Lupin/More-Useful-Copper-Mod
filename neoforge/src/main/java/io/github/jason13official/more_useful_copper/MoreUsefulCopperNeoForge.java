package io.github.jason13official.more_useful_copper;


import io.github.jason13official.more_useful_copper.impl.common.entity.CopperGolem;
import io.github.jason13official.more_useful_copper.impl.common.registry.ForgeModLootTableModifiers;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModTabs;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModTiles;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class MoreUsefulCopperNeoForge {

  public static IEventBus EVENT_BUS;

  public MoreUsefulCopperNeoForge(IEventBus modEventBus, Dist dist) {

    EVENT_BUS = modEventBus;

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

    // on to client init
    if (FMLLoader.getDist() == Dist.CLIENT) {
      new MoreUsefulCopperClientNeoForge(EVENT_BUS);
    }
  }

  /// Mimicking Botania's registration
  public <T> void bind(ResourceKey<Registry<T>> registryKey, Consumer<BiConsumer<T, Identifier>> source) {

//    source.accept((t, rl) -> Registry.register(registry, rl, t));
    EVENT_BUS.addListener((Consumer<RegisterEvent>) event -> {
      if (registryKey.equals(event.getRegistryKey())) {
        source.accept((t, rl) -> event.register(registryKey, rl, () -> t));
      }
    });
  }
}