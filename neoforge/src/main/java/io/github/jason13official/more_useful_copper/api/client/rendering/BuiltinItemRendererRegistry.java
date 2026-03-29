package io.github.jason13official.more_useful_copper.api.client.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

/// Holds [DynamicItemRenderer] instances for items that need custom BEWLR rendering.
public interface BuiltinItemRendererRegistry {

  /// The singleton instance.
  BuiltinItemRendererRegistry INSTANCE = new BuiltinItemRendererRegistryImpl();

  /// Registers a renderer for the given item.
  ///
  /// @throws IllegalArgumentException if the item already has a registered renderer
  void register(ItemLike item, DynamicItemRenderer renderer);

  /// Returns the renderer for the item, or `null` if none is registered.
  DynamicItemRenderer get(ItemLike item);

  /// Renders an item stack with custom code.
  @FunctionalInterface
  interface DynamicItemRenderer {
    void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices,
        MultiBufferSource vertexConsumers, int light, int overlay);
  }
}
