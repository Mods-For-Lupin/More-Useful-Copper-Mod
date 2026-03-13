package io.github.jason13official.more_useful_copper.api.client.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

/**
 * This registry holds {@linkplain DynamicItemRenderer builtin item renderers} for items.
 */
public interface BuiltinItemRendererRegistry {
  /**
   * The singleton instance of the renderer registry.
   * Use this instance to call the methods in this interface.
   */
  BuiltinItemRendererRegistry INSTANCE = new BuiltinItemRendererRegistryImpl();

  /**
   * Registers the renderer for the item.
   *
   * <p>Note that the item's JSON model must also extend {@code minecraft:builtin/entity}.
   *
   * @param item     the item
   * @param renderer the renderer
   * @throws IllegalArgumentException if the item already has a registered renderer
   * @throws NullPointerException if either the item or the renderer is null
   */
  void register(ItemLike item, DynamicItemRenderer renderer);

  /**
   * Returns the renderer for the item, or {@code null} if the item has no renderer.
   */
  DynamicItemRenderer get(ItemLike item);

  /**
   * Dynamic item renderers render items with custom code.
   * They allow using non-model rendering, such as BERs, for items.
   *
   * <p>An item with a dynamic renderer must have a model extending {@code minecraft:builtin/entity}.
   * The renderers are registered with {@link BuiltinItemRendererRegistry#register(ItemLike, DynamicItemRenderer)}.
   */
  @FunctionalInterface
  interface DynamicItemRenderer {
    /**
     * Renders an item stack.
     *
     * @param stack           the rendered item stack
     * @param mode            the model transformation mode
     * @param matrices        the matrix stack
     * @param vertexConsumers the vertex consumer provider
     * @param light           packed lightmap coordinates
     * @param overlay         the overlay UV passed to {@link com.mojang.blaze3d.vertex.VertexConsumer#overlayCoords(int)}
     */
    void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay);
  }
}

