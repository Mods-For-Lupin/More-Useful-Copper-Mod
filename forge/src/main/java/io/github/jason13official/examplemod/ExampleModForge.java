package io.github.jason13official.examplemod;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class ExampleModForge {

  public static IEventBus EVENT_BUS;

  public ExampleModForge(final FMLJavaModLoadingContext context) {

    EVENT_BUS = context.getModEventBus();

    // after game object registration
    ExampleMod.init();

    // on to client init
    if (FMLLoader.getDist() == Dist.CLIENT) {
      new ExampleModClientForge(EVENT_BUS);
    }
  }

  public ExampleModForge() {
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