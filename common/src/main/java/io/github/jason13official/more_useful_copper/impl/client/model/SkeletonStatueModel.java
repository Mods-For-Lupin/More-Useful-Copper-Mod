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

public class SkeletonStatueModel extends EntityModel<EntityRenderState> {

  private final ModelPart skeleton_statue;
  private final ModelPart legs;
  private final ModelPart RightLeg;
  private final ModelPart LeftLeg;
  private final ModelPart arms;
  private final ModelPart LeftArm;
  private final ModelPart leftItem;
  private final ModelPart RightArm;
  private final ModelPart Head;
  private final ModelPart Body;

  public SkeletonStatueModel(final ModelPart root) {
    super(root);

    this.skeleton_statue = root.getChild("skeleton_statue");
    this.legs = this.skeleton_statue.getChild("legs");
    this.RightLeg = this.legs.getChild("RightLeg");
    this.LeftLeg = this.legs.getChild("LeftLeg");
    this.arms = this.skeleton_statue.getChild("arms");
    this.LeftArm = this.arms.getChild("LeftArm");
    this.leftItem = this.LeftArm.getChild("leftItem");
    this.RightArm = this.arms.getChild("RightArm");
    this.Head = this.skeleton_statue.getChild("Head");
    this.Body = this.skeleton_statue.getChild("Body");
  }

  public static LayerDefinition createBodyLayer() {
    MeshDefinition meshdefinition = new MeshDefinition();
    PartDefinition partdefinition = meshdefinition.getRoot();

    PartDefinition skeleton_statue = partdefinition.addOrReplaceChild("skeleton_statue", CubeListBuilder.create(), PartPose.offset(-2.0F, 12.0F, 0.0F));

    PartDefinition legs = skeleton_statue.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

    PartDefinition RightLeg = legs.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(24, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
        PartPose.offset(0.0F, 0.0F, 0.0F));

    PartDefinition LeftLeg = legs.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(24, 30).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
        PartPose.offset(4.0F, 0.0F, 0.0F));

    PartDefinition arms = skeleton_statue.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(7.0F, -10.0F, 0.0F));

    PartDefinition LeftArm = arms.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
        PartPose.offset(0.0F, 0.0F, 0.0F));

    PartDefinition leftItem = LeftArm.addOrReplaceChild("leftItem", CubeListBuilder.create(), PartPose.offset(1.0F, 7.0F, 1.0F));

    PartDefinition RightArm = arms.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
        PartPose.offset(-10.0F, 0.0F, 0.0F));

    PartDefinition Head = skeleton_statue.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
        PartPose.offset(2.0F, -12.0F, 0.0F));

    PartDefinition Body = skeleton_statue.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
        PartPose.offset(2.0F, -12.0F, 0.0F));

    return LayerDefinition.create(meshdefinition, 64, 64);
  }

  @Override
  public void setupAnim(EntityRenderState state) {
    // no-op
  }
}
