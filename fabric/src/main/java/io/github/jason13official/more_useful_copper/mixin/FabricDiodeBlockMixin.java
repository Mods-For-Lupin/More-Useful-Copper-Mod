package io.github.jason13official.more_useful_copper.mixin;

import io.github.jason13official.more_useful_copper.impl.common.block.CopperRedstoneDustBlock;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DiodeBlock.class)
public abstract class FabricDiodeBlockMixin {

  // Repeaters and comparators facing copper wire read its power on the front input
  @Inject(method = "getInputSignal", at = @At("RETURN"), cancellable = true)
  protected void muc$getInputSignal(Level level, BlockPos pos, BlockState state,
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

  // Comparator side-input reads power from copper wire (replaces SignalGetter interface mixin)
  @Inject(method = "getAlternateSignal", at = @At("RETURN"), cancellable = true)
  protected void muc$getAlternateSignal(SignalGetter level, BlockPos pos, BlockState state,
      CallbackInfoReturnable<Integer> cir) {
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
