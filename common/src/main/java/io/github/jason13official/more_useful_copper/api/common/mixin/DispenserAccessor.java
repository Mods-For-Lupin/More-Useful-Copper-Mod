package io.github.jason13official.more_useful_copper.api.common.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public interface DispenserAccessor {

  void more_useful_copper$doDispense(ServerLevel level, BlockPos relative);
}
