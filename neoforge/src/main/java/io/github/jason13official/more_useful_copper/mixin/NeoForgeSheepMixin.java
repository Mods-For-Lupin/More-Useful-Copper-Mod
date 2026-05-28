package io.github.jason13official.more_useful_copper.mixin;

import io.github.jason13official.more_useful_copper.impl.common.util.CommonSheepMixinLogic;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sheep.class)
public class NeoForgeSheepMixin {

  @Inject(at = @At("HEAD"), method = "mobInteract", cancellable = true)
  private void muc$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
    Sheep self = (Sheep) (Object) this;
    CommonSheepMixinLogic.injectedMobInteract(self, player, hand, cir);
  }
}
