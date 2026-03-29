package io.github.jason13official.more_useful_copper.mixin;

import io.github.jason13official.more_useful_copper.impl.common.util.CommonLightningRodBlockMixinLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningRodBlock.class)
public class NeoForgeLightningRodBlockMixin {

  @Inject(at = @At("TAIL"), method = "onLightningStrike")
  private void more_useful_copper$onLightningStrike(BlockState state, Level level, BlockPos pos, CallbackInfo ci) {
    CommonLightningRodBlockMixinLogic.onLightningStrike(level, pos);
  }
}
