package io.github.jason13official.more_useful_copper.mixin;

import io.github.jason13official.more_useful_copper.impl.common.util.CommonDiodeBlockMixinLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DiodeBlock.class)
public abstract class FabricDiodeBlockMixin {

  @Shadow protected abstract boolean sideInputDiodesOnly();

  @Inject(method = "getInputSignal", at = @At("RETURN"), cancellable = true)
  protected void muc$getInputSignal(Level level, BlockPos pos, BlockState state,
      CallbackInfoReturnable<Integer> cir) {
    CommonDiodeBlockMixinLogic.injectedGetInputSignal(level, pos, state, cir);
  }

  @Inject(method = "getAlternateSignal", at = @At("RETURN"), cancellable = true)
  protected void muc$getAlternateSignal(SignalGetter level, BlockPos pos, BlockState state,
      CallbackInfoReturnable<Integer> cir) {
    CommonDiodeBlockMixinLogic.injectedGetAlternateSignal(level, pos, state, this.sideInputDiodesOnly(), cir);
  }
}
