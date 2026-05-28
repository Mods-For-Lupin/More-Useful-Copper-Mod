package io.github.jason13official.more_useful_copper.impl.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import org.jspecify.annotations.Nullable;

public class CopperBellRenderState extends BlockEntityRenderState {

  public @Nullable Direction shakeDirection;
  public float ticks;
  public int oxidization;
}
