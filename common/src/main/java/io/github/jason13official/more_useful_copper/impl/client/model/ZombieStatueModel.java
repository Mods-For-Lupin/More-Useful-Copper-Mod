package io.github.jason13official.more_useful_copper.impl.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ZombieStatueModel extends EntityModel<EntityRenderState> {

  private final ModelPart zombie_statue;
  private final ModelPart Body;
  private final ModelPart Head;
  private final ModelPart arms;
  private final ModelPart RightArm;
  private final ModelPart LeftArm;
  private final ModelPart legs;
  private final ModelPart RightLeg;
  private final ModelPart LeftLeg;

  public ZombieStatueModel(final ModelPart root) {
    super(root);

    this.zombie_statue = root.getChild("zombie_statue");
    this.Body = this.zombie_statue.getChild("Body");
    this.Head = this.zombie_statue.getChild("Head");
    this.arms = this.zombie_statue.getChild("arms");
    this.RightArm = this.arms.getChild("RightArm");
    this.LeftArm = this.arms.getChild("LeftArm");
    this.legs = this.zombie_statue.getChild("legs");
    this.RightLeg = this.legs.getChild("RightLeg");
    this.LeftLeg = this.legs.getChild("LeftLeg");
  }

  public static LayerDefinition createBodyLayer() {
    MeshDefinition meshdefinition = new MeshDefinition();
    PartDefinition partdefinition = meshdefinition.getRoot();

    PartDefinition zombie_statue = partdefinition.addOrReplaceChild("zombie_statue", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

    PartDefinition Body = zombie_statue.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
        PartPose.offset(0.0F, 0.0F, 0.0F));

    PartDefinition Head = zombie_statue.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
        PartPose.offset(0.0F, 0.0F, 0.0F));

    PartDefinition arms = zombie_statue.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));

    PartDefinition RightArm = arms.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -2.0F, -10.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)),
        PartPose.offset(0.0F, 0.0F, 0.0F));

    PartDefinition LeftArm = arms.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, -2.0F, -10.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)),
        PartPose.offset(10.0F, 0.0F, 0.0F));

    PartDefinition legs = zombie_statue.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));

    PartDefinition RightLeg = legs.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(32, 0).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
        PartPose.offset(0.0F, 0.0F, 0.0F));

    PartDefinition LeftLeg = legs.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(16, 32).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
        PartPose.offset(3.8F, 0.0F, 0.0F));

    return LayerDefinition.create(meshdefinition, 64, 64);
  }

  @Override
  public void setupAnim(EntityRenderState state) {
    // no-op
  }
}
