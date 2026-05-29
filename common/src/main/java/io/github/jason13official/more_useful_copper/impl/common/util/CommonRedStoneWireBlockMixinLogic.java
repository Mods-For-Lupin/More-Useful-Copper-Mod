package io.github.jason13official.more_useful_copper.impl.common.util;

import io.github.jason13official.more_useful_copper.impl.common.block.CopperRedstoneDustBlock;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class CommonRedStoneWireBlockMixinLogic {

  /// Mixin injection: makes vanilla redstone wire connect to any copper wire variant.
  ///
  /// @param state     the neighbor block state being evaluated for connection
  /// @param direction the side being checked
  /// @param cir       mixin return-value callback; set to `true` if `state` is copper wire
  public static void injectedShouldConnectTo(BlockState state, Direction direction,
      CallbackInfoReturnable<Boolean> cir) {
    if (state.is(ModBlockTags.COPPER_REDSTONE_WIRE)) {
      cir.setReturnValue(true);
    }
  }

  /// Mixin injection: lets vanilla wire read the power level of an adjacent copper wire neighbor.
  ///
  /// @param state the neighbor block state
  /// @param cir   mixin return-value callback; set to the copper wire's power if applicable
  public static void injectedGetWireSignal(BlockState state, CallbackInfoReturnable<Integer> cir) {
    if (state.is(ModBlockTags.COPPER_REDSTONE_WIRE)) {
      cir.setReturnValue(state.getValue(RedStoneWireBlock.POWER));
    }
  }

  /// Mixin injection — HEAD of vanilla wire's power-calculation method. Sets CopperRedstoneDustBlock.isAnyWireCalculating so copper wires suppress their `getSignal` during `getBestNeighborSignal`,
  /// fixing the reverse-direction attenuation bug at copper→vanilla boundaries.
  public static void injectedCalcHead() {
    CopperRedstoneDustBlock.isAnyWireCalculating = true;
  }

  /// Mixin injection — RETURN of vanilla wire's power-calculation method. Clears CopperRedstoneDustBlock.isAnyWireCalculating.
  public static void injectedCalcReturn() {
    CopperRedstoneDustBlock.isAnyWireCalculating = false;
  }

  /// Mixin injection: suppresses vanilla wire's `getSignal` while any wire type is calculating. Without this, vanilla wire would contribute to `getBestNeighborSignal` directly instead of only through
  /// the attenuated `j = getWireSignal` path, breaking vanilla→copper boundaries.
  ///
  /// @param cir set to `0` when suppression is active
  public static void injectedSuppressDuringCalc(CallbackInfoReturnable<Integer> cir) {
    if (CopperRedstoneDustBlock.isAnyWireCalculating) {
      cir.setReturnValue(0);
    }
  }
}
