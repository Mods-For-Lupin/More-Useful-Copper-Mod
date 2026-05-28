package io.github.jason13official.more_useful_copper.impl.client.renderer.entity.state;

import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;

public class CopperStatueRenderState extends EntityRenderState {

  public CopperStatue.Type variant;
  public Identifier texture;
  public float yRot;
}
