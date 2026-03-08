package io.github.jason13official.more_useful_copper.mixin;

import io.github.jason13official.more_useful_copper.impl.common.tags.ModBlockTags;
import net.minecraft.core.Direction;
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
}
