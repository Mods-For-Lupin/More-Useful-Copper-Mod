package io.github.jason13official.more_useful_copper.impl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class CreeperStatueModel extends EntityModel<CopperStatue> {

  private final ModelPart creeper_statue;
  private final ModelPart head;
  private final ModelPart legs;

  public CreeperStatueModel(final ModelPart root) {
    super(RenderType::entityCutoutNoCull);

    this.creeper_statue = root.getChild("creeper_statue");
    this.head = this.creeper_statue.getChild("head");
    this.legs = this.creeper_statue.getChild("legs");
  }

  public static LayerDefinition createBodyLayer() {
    MeshDefinition meshdefinition = new MeshDefinition();
    PartDefinition partdefinition = meshdefinition.getRoot();

    PartDefinition creeper_statue = partdefinition.addOrReplaceChild("creeper_statue",
        CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -18.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

    PartDefinition head = creeper_statue.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -26.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
        PartPose.offset(0.0F, 0.0F, 0.0F));

    PartDefinition legs = creeper_statue.addOrReplaceChild("legs", CubeListBuilder.create().texOffs(24, 16).addBox(-4.0F, -6.0F, 2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(24, 16).addBox(0.0F, -6.0F, 2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(24, 16).addBox(-4.0F, -6.0F, -6.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(24, 16).addBox(0.0F, -6.0F, -6.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

    return LayerDefinition.create(meshdefinition, 64, 64);
  }

  @Override
  public void setupAnim(CopperStatue entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    // no-op
  }

  @Override
  public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
    creeper_statue.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
  }
}
