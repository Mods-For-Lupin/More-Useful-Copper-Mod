package io.github.jason13official.more_useful_copper.mixin;

import io.github.jason13official.more_useful_copper.mixin.logic.CommonAxeItemMixinLogic;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public class ForgeAxeItemMixin {

  @Inject(at = @At("HEAD"), method = "useOn", cancellable = true)
  private void more_useful_copper$useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
    CommonAxeItemMixinLogic.injectedUseAxeOnBlockLogic(context, cir);
  }
}
