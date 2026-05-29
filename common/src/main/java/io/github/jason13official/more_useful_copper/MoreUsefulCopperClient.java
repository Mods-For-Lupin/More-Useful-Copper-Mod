package io.github.jason13official.more_useful_copper;

import com.mojang.serialization.MapCodec;
import io.github.jason13official.more_useful_copper.impl.client.renderer.CopperStatueSpecialRenderer;
import java.util.function.BiConsumer;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.resources.Identifier;

public class MoreUsefulCopperClient {

  public static void init() {
  }

  public static void registerSpecialModelRenderers(
      BiConsumer<Identifier, MapCodec<? extends NoDataSpecialModelRenderer.Unbaked>> consumer) {
    for (CopperStatueSpecialRenderer.Unbaked u : CopperStatueSpecialRenderer.Unbaked.values()) {
      consumer.accept(MoreUsefulCopper.identifier(u.id), u.MAP_CODEC);
    }
  }
}
