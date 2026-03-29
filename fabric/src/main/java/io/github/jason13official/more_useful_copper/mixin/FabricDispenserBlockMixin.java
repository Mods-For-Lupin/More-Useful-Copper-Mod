package io.github.jason13official.more_useful_copper.mixin;

import io.github.jason13official.more_useful_copper.api.common.mixin.DispenserAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DispenserBlock.class)
public abstract class FabricDispenserBlockMixin implements DispenserAccessor {

  @Shadow
  protected abstract void dispenseFrom(ServerLevel level, BlockState state, BlockPos pos);

  @Override
  public void more_useful_copper$doDispense(ServerLevel level, BlockState state, BlockPos pos) {
    this.dispenseFrom(level, state, pos);
  }
}
