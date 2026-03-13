package io.github.jason13official.more_useful_copper.mixin;

import io.github.jason13official.more_useful_copper.api.common.mixin.DispenserAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.DispenserBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DispenserBlock.class)
public abstract class ForgeDispenserBlockMixin implements DispenserAccessor {

  @Shadow
  protected abstract void dispenseFrom(ServerLevel level, BlockPos pos);

  @Override
  public void more_useful_copper$doDispense(ServerLevel level, BlockPos relative) {
    this.dispenseFrom(level, relative);
  }
}
