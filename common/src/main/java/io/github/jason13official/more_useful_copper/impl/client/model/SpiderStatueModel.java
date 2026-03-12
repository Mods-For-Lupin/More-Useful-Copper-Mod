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

public class SpiderStatueModel extends EntityModel<CopperStatue> {

  private final ModelPart spider_statue;
  private final ModelPart head;
  private final ModelPart body0;
  private final ModelPart back;
  private final ModelPart right_legs;
  private final ModelPart leg0;
  private final ModelPart leg2;
  private final ModelPart leg4;
  private final ModelPart leg6;
  private final ModelPart left_legs;
  private final ModelPart leg1;
  private final ModelPart leg3;
  private final ModelPart leg5;
  private final ModelPart leg7;

  public SpiderStatueModel(final ModelPart root) {
    super(RenderType::entityCutoutNoCull);

    this.spider_statue = root.getChild("spider_statue");
    this.head = this.spider_statue.getChild("head");
    this.body0 = this.spider_statue.getChild("body0");
    this.back = this.spider_statue.getChild("back");
    this.right_legs = this.spider_statue.getChild("right_legs");
    this.leg0 = this.right_legs.getChild("leg0");
    this.leg2 = this.right_legs.getChild("leg2");
    this.leg4 = this.right_legs.getChild("leg4");
    this.leg6 = this.right_legs.getChild("leg6");
    this.left_legs = this.spider_statue.getChild("left_legs");
    this.leg1 = this.left_legs.getChild("leg1");
    this.leg3 = this.left_legs.getChild("leg3");
    this.leg5 = this.left_legs.getChild("leg5");
    this.leg7 = this.left_legs.getChild("leg7");
  }

  public static LayerDefinition createBodyLayer() {
    MeshDefinition meshdefinition = new MeshDefinition();
    PartDefinition partdefinition = meshdefinition.getRoot();

    PartDefinition spider_statue = partdefinition.addOrReplaceChild("spider_statue", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, -3.0F));

    PartDefinition head = spider_statue.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 20).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
        PartPose.offset(0.0F, 0.0F, 0.0F));

    PartDefinition body0 = spider_statue.addOrReplaceChild("body0", CubeListBuilder.create().texOffs(32, 24).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
        PartPose.offset(0.0F, 0.0F, 3.0F));

    PartDefinition back = spider_statue.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)),
        PartPose.offset(0.0F, 0.0F, 12.0F));

    PartDefinition right_legs = spider_statue.addOrReplaceChild("right_legs", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, 5.0F));

    PartDefinition leg0 = right_legs.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(32, 20).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, -0.7854F));

    PartDefinition leg2 = right_legs.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(32, 20).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
        PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, 0.2618F, -0.6109F));

    PartDefinition leg4 = right_legs.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(32, 20).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
        PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, -0.2618F, -0.6109F));

    PartDefinition leg6 = right_legs.addOrReplaceChild("leg6", CubeListBuilder.create().texOffs(32, 20).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
        PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, 0.0F, -0.7854F, -0.7854F));

    PartDefinition left_legs = spider_statue.addOrReplaceChild("left_legs", CubeListBuilder.create(), PartPose.offset(4.0F, 0.0F, 5.0F));

    PartDefinition leg1 = left_legs.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(32, 20).addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.7854F));

    PartDefinition leg3 = left_legs.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(32, 20).addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
        PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, -0.2618F, 0.6109F));

    PartDefinition leg5 = left_legs.addOrReplaceChild("leg5", CubeListBuilder.create().texOffs(32, 20).addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
        PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, 0.2618F, 0.6109F));

    PartDefinition leg7 = left_legs.addOrReplaceChild("leg7", CubeListBuilder.create().texOffs(32, 20).addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
        PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, 0.0F, 0.7854F, 0.7854F));

    return LayerDefinition.create(meshdefinition, 128, 128);
  }

  @Override
  public void setupAnim(CopperStatue entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    // no-op
  }

  @Override
  public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
    spider_statue.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
  }
}
