package io.github.jason13official.more_useful_copper.mixin;

import io.github.jason13official.more_useful_copper.impl.common.util.CommonRedStoneWireBlockMixinLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.RedstoneWireEvaluator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneWireEvaluator.class)
public class FabricRedstoneWireEvaluatorMixin {

  @Inject(method = "getWireSignal", at = @At("HEAD"), cancellable = true)
  private void muc$getWireSignal(BlockPos pos, BlockState state, CallbackInfoReturnable<Integer> cir) {
    CommonRedStoneWireBlockMixinLogic.injectedGetWireSignal(state, cir);
  }
}
