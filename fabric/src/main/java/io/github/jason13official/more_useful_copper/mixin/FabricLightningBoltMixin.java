package io.github.jason13official.more_useful_copper.mixin;

import io.github.jason13official.more_useful_copper.impl.common.util.CommonLightningBoltMixinLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningBolt.class)
public abstract class FabricLightningBoltMixin {

  @Shadow
  private int life;

  @Shadow
  protected abstract BlockPos getStrikePosition();

  @Inject(at = @At("HEAD"), method = "tick")
  private void more_useful_copper$tick(CallbackInfo ci) {
    LightningBolt self = (LightningBolt) (Object) this;
    CommonLightningBoltMixinLogic.onLightningTick(self, this.life, this.getStrikePosition());
  }
}
