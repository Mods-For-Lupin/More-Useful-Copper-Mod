package io.github.jason13official.more_useful_copper.impl.common.util;

import io.github.jason13official.more_useful_copper.impl.common.block.CopperRedstoneDustBlock;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class CommonDiodeBlockMixinLogic {

  /// Mixin injection: lets repeaters and comparators read copper wire power on their front input.
  /// Only overrides the return value when the existing result is less than 15 and the
  /// directly-faced neighbor is a copper wire.
  ///
  /// @param cir set to the higher of the existing result and the copper wire's power level
  public static void injectedGetInputSignal(Level level, BlockPos pos, BlockState state,
      CallbackInfoReturnable<Integer> cir) {
    int result = cir.getReturnValue();
    if (result < 15) {
      Direction dir = state.getValue(HorizontalDirectionalBlock.FACING);
      BlockState adj = level.getBlockState(pos.relative(dir));
      if (adj.is(ModBlockTags.COPPER_REDSTONE_WIRE)) {
        cir.setReturnValue(Math.max(result, adj.getValue(CopperRedstoneDustBlock.POWER)));
      }
    }
  }

  /// Mixin injection: lets comparators read copper wire power from their side inputs.
  /// Skipped when `sideInputDiodesOnly` is `true` (repeater mode) to prevent copper wire
  /// from incorrectly locking a repeater — only diode side-inputs should do that.
  ///
  /// @param sideInputDiodesOnly `true` for repeaters; early-exit without modifying `cir`
  /// @param cir                 set to the highest side-input power including any copper wire
  public static void injectedGetAlternateSignal(SignalGetter level, BlockPos pos, BlockState state,
      boolean sideInputDiodesOnly, CallbackInfoReturnable<Integer> cir) {
    if (sideInputDiodesOnly) return;
    Direction dir = state.getValue(HorizontalDirectionalBlock.FACING);
    Direction left = dir.getClockWise();
    Direction right = dir.getCounterClockWise();
    int result = cir.getReturnValue();
    int extra = result;
    BlockState leftState = level.getBlockState(pos.relative(left));
    BlockState rightState = level.getBlockState(pos.relative(right));
    if (leftState.is(ModBlockTags.COPPER_REDSTONE_WIRE)) {
      extra = Math.max(extra, leftState.getValue(CopperRedstoneDustBlock.POWER));
    }
    if (rightState.is(ModBlockTags.COPPER_REDSTONE_WIRE)) {
      extra = Math.max(extra, rightState.getValue(CopperRedstoneDustBlock.POWER));
    }
    if (extra != result) {
      cir.setReturnValue(extra);
    }
  }
}
