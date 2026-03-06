package io.github.jason13official.examplemod;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class ExampleModFabric implements ModInitializer {

  @Override
  public void onInitialize() {

    // after game object registration
    ExampleMod.init();

    // on to client init
  }

  /// Mimicking Botania's registration
  public <T> void bind(Registry<T> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {

    source.accept((t, rl) -> Registry.register(registry, rl, t));
  }
}
