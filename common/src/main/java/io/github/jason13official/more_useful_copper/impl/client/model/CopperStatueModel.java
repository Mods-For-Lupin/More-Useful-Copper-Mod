package io.github.jason13official.more_useful_copper.impl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.jason13official.more_useful_copper.Constants;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public class CopperStatueModel extends EntityModel<CopperStatue> {

  public static final ModelLayerLocation CREEPER_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "creeper"), "main");

  public CopperStatueModel(final ModelPart root) {
  }

  @Override
  public void setupAnim(CopperStatue copperStatue, float v, float v1, float v2, float v3, float v4) {

  }

  @Override
  public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int color) {

  }
}
