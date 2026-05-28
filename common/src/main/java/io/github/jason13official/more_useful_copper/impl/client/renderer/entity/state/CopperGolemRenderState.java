package io.github.jason13official.more_useful_copper.impl.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Crackiness;

public class CopperGolemRenderState extends LivingEntityRenderState {

  public float attackTicksRemaining;
  public Crackiness.Level crackiness = Crackiness.Level.NONE;
}
