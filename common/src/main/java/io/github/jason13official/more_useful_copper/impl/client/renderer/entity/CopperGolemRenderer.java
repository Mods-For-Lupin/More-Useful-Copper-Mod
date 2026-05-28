package io.github.jason13official.more_useful_copper.impl.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.client.model.CopperGolemModel;
import io.github.jason13official.more_useful_copper.impl.client.renderer.entity.layers.CopperGolemCrackinessLayer;
import io.github.jason13official.more_useful_copper.impl.client.renderer.entity.state.CopperGolemRenderState;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperGolem;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class CopperGolemRenderer extends MobRenderer<CopperGolem, CopperGolemRenderState, CopperGolemModel> {

  private static final Identifier TEXTURE_LOCATION = MoreUsefulCopper.identifier("textures/entity/copper_golem/copper_golem.png");

  public CopperGolemRenderer(EntityRendererProvider.Context context) {
    super(context, new CopperGolemModel(context.bakeLayer(CopperGolemModel.LAYER_LOCATION)), 0.7F);
    this.addLayer(new CopperGolemCrackinessLayer(this));
  }

  @Override
  public CopperGolemRenderState createRenderState() {
    return new CopperGolemRenderState();
  }

  @Override
  public void extractRenderState(CopperGolem entity, CopperGolemRenderState state, float partialTicks) {
    super.extractRenderState(entity, state, partialTicks);
    state.attackTicksRemaining = entity.getAttackAnimationTick() > 0 ? entity.getAttackAnimationTick() - partialTicks : 0.0F;
    state.crackiness = entity.getCrackiness();
  }

  @Override
  public Identifier getTextureLocation(CopperGolemRenderState state) {
    return TEXTURE_LOCATION;
  }

  @Override
  protected void setupRotations(CopperGolemRenderState state, PoseStack poseStack, float bodyRot, float entityScale) {
    super.setupRotations(state, poseStack, bodyRot, entityScale);
    if (!(state.walkAnimationSpeed < 0.01)) {
      float wp = state.walkAnimationPos + 6.0F;
      float triangleWave = (Math.abs(wp % 13.0F - 6.5F) - 3.25F) / 3.25F;
      poseStack.mulPose(Axis.ZP.rotationDegrees(6.5F * triangleWave));
    }
  }
}
