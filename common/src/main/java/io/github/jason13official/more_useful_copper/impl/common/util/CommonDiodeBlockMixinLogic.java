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

  // Repeaters and comparators facing copper wire read its power on the front input
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

  // Comparator side-input reads power from copper wire.
  // Repeaters use sideInputDiodesOnly() = true, so copper wire must NOT contribute to
  // getAlternateSignal for them — that would incorrectly allow copper wire to lock a repeater.
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
