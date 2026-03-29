package io.github.jason13official.more_useful_copper.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.jason13official.more_useful_copper.api.client.rendering.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityWithoutLevelRenderer.class)
public class NeoForgeBlockEntityWithoutLevelRendererMixin {

  @Inject(method = "renderByItem", at = @At("HEAD"), cancellable = true)
  private void more_useful_copper$renderByItem(ItemStack stack, ItemDisplayContext mode,
      PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, CallbackInfo ci) {
    BuiltinItemRendererRegistry.DynamicItemRenderer renderer = BuiltinItemRendererRegistry.INSTANCE.get(stack.getItem());

    if (renderer != null) {
      renderer.render(stack, mode, matrices, vertexConsumers, light, overlay);
      ci.cancel();
    }
  }
}
