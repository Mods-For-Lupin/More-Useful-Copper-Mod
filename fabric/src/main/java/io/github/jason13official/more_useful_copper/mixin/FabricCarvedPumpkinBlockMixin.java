package io.github.jason13official.more_useful_copper.mixin;

import io.github.jason13official.more_useful_copper.impl.common.util.CommonCarvedPumpkinBlockMixinLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CarvedPumpkinBlock.class)
public class FabricCarvedPumpkinBlockMixin {

  @Inject(method = "trySpawnGolem", at = @At("HEAD"))
  private void muc$onTrySpawnGolem(Level level, BlockPos blockPos, CallbackInfo ci) {
    CommonCarvedPumpkinBlockMixinLogic.injectedTrySpawnGolem(level, blockPos, ci);
  }
}
