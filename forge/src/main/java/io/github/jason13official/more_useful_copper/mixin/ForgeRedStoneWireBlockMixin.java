package io.github.jason13official.more_useful_copper.mixin;

import io.github.jason13official.more_useful_copper.impl.common.block.CopperRedstoneDustBlock;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedStoneWireBlock.class)
public class ForgeRedStoneWireBlockMixin {

  // Vanilla wire connects TO copper wire
  @Inject(
      method = "shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z",
      at = @At("HEAD"),
      cancellable = true
  )
  private static void muc$shouldConnectTo(BlockState state, Direction direction, CallbackInfoReturnable<Boolean> cir) {
    if (state.is(ModBlockTags.COPPER_REDSTONE_WIRE)) {
      cir.setReturnValue(true);
    }
  }

  // Vanilla wire reads power FROM copper wire neighbors
  @Inject(method = "getWireSignal", at = @At("HEAD"), cancellable = true)
  private void muc$getWireSignal(BlockState state, CallbackInfoReturnable<Integer> cir) {
    if (state.is(ModBlockTags.COPPER_REDSTONE_WIRE)) {
      cir.setReturnValue(state.getValue(RedStoneWireBlock.POWER));
    }
  }

  // Set the cross-type "all wires silent" flag for the duration of vanilla wire's
  // getBestNeighborSignal call, so copper wire getSignal returns 0 during that window.
  // This fixes the reverse-direction attenuation bug (copper→vanilla boundary).
  @Inject(method = "calculateTargetStrength", at = @At("HEAD"))
  private void muc$calcHead(Level level, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
    CopperRedstoneDustBlock.isAnyWireCalculating = true;
  }

  @Inject(method = "calculateTargetStrength", at = @At("RETURN"))
  private void muc$calcReturn(Level level, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
    CopperRedstoneDustBlock.isAnyWireCalculating = false;
  }

  // Suppress vanilla wire's own getSignal when any wire type is calculating.
  // This fixes the vanilla→copper boundary bug: vanilla wire's signal must not appear
  // in getBestNeighborSignal (only in the j=getWireSignal path, which attenuates by -1).
  @Inject(method = "getSignal", at = @At("HEAD"), cancellable = true)
  private void muc$suppressDuringCalc(BlockState state, BlockGetter level, BlockPos pos,
      Direction direction, CallbackInfoReturnable<Integer> cir) {
    if (CopperRedstoneDustBlock.isAnyWireCalculating) {
      cir.setReturnValue(0);
    }
  }
}
