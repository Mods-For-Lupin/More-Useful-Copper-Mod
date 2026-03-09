package io.github.jason13official.more_useful_copper.impl.common.util;

import io.github.jason13official.more_useful_copper.impl.common.block.CopperRedstoneDustBlock;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class CommonRedStoneWireBlockMixinLogic {

  // Vanilla wire connects TO copper wire
  public static void injectedShouldConnectTo(BlockState state, Direction direction,
      CallbackInfoReturnable<Boolean> cir) {
    if (state.is(ModBlockTags.COPPER_REDSTONE_WIRE)) {
      cir.setReturnValue(true);
    }
  }

  // Vanilla wire reads power FROM copper wire neighbors
  public static void injectedGetWireSignal(BlockState state, CallbackInfoReturnable<Integer> cir) {
    if (state.is(ModBlockTags.COPPER_REDSTONE_WIRE)) {
      cir.setReturnValue(state.getValue(RedStoneWireBlock.POWER));
    }
  }

  // Set the cross-type "all wires silent" flag for the duration of vanilla wire's
  // getBestNeighborSignal call, so copper wire getSignal returns 0 during that window.
  // This fixes the reverse-direction attenuation bug (copper→vanilla boundary).
  public static void injectedCalcHead() {
    CopperRedstoneDustBlock.isAnyWireCalculating = true;
  }

  public static void injectedCalcReturn() {
    CopperRedstoneDustBlock.isAnyWireCalculating = false;
  }

  // Suppress vanilla wire's own getSignal when any wire type is calculating.
  // This fixes the vanilla→copper boundary bug: vanilla wire's signal must not appear
  // in getBestNeighborSignal (only in the j=getWireSignal path, which attenuates by -1).
  public static void injectedSuppressDuringCalc(CallbackInfoReturnable<Integer> cir) {
    if (CopperRedstoneDustBlock.isAnyWireCalculating) {
      cir.setReturnValue(0);
    }
  }
}
