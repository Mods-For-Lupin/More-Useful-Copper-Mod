package io.github.jason13official.more_useful_copper.mixin;

import io.github.jason13official.more_useful_copper.impl.common.util.CommonRedStoneWireBlockMixinLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedStoneWireBlock.class)
public class NeoForgeRedStoneWireBlockMixin {

  @Inject(
      method = "shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z",
      at = @At("HEAD"),
      cancellable = true
  )
  private static void muc$shouldConnectTo(BlockState state, Direction direction, CallbackInfoReturnable<Boolean> cir) {
    CommonRedStoneWireBlockMixinLogic.injectedShouldConnectTo(state, direction, cir);
  }

  @Inject(method = "getWireSignal", at = @At("HEAD"), cancellable = true)
  private void muc$getWireSignal(BlockState state, CallbackInfoReturnable<Integer> cir) {
    CommonRedStoneWireBlockMixinLogic.injectedGetWireSignal(state, cir);
  }

  @Inject(method = "calculateTargetStrength", at = @At("HEAD"))
  private void muc$calcHead(Level level, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
    CommonRedStoneWireBlockMixinLogic.injectedCalcHead();
  }

  @Inject(method = "calculateTargetStrength", at = @At("RETURN"))
  private void muc$calcReturn(Level level, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
    CommonRedStoneWireBlockMixinLogic.injectedCalcReturn();
  }

  @Inject(method = "getSignal", at = @At("HEAD"), cancellable = true)
  private void muc$suppressDuringCalc(BlockState state, BlockGetter level, BlockPos pos,
      Direction direction, CallbackInfoReturnable<Integer> cir) {
    CommonRedStoneWireBlockMixinLogic.injectedSuppressDuringCalc(cir);
  }
}
