package io.github.jason13official.more_useful_copper.impl.client.model;

import io.github.jason13official.more_useful_copper.Constants;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;

public class CopperStatueModel extends EntityModel<EntityRenderState> {

  public static final ModelLayerLocation CREEPER_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "creeper"), "main");

  public CopperStatueModel(final ModelPart root) {
    super(root);
  }

  @Override
  public void setupAnim(EntityRenderState state) {
  }
}
