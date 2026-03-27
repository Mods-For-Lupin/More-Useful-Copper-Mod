package io.github.jason13official.more_useful_copper;


import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class MoreUsefulCopperNeoForge {

  public static IEventBus EVENT_BUS;

  public MoreUsefulCopperNeoForge(IEventBus modEventBus, Dist dist) {

    EVENT_BUS = modEventBus;

    // after game object registration
    MoreUsefulCopper.init();

    // on to client init
    if (dist == Dist.CLIENT) {
      new MoreUsefulCopperClientNeoForge(EVENT_BUS);
    }
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