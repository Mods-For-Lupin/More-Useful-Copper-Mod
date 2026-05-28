package io.github.jason13official.more_useful_copper.impl.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.client.model.CopperGolemModel;
import io.github.jason13official.more_useful_copper.impl.client.renderer.entity.layers.CopperGolemCrackinessLayer;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperGolem;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class CopperGolemRenderer extends MobRenderer<CopperGolem, CopperGolemModel<CopperGolem>> {

  private static final Identifier TEXTURE_LOCATION = MoreUsefulCopper.identifier("textures/entity/copper_golem/copper_golem.png");

  public CopperGolemRenderer(EntityRendererProvider.Context context) {
    super(context, new CopperGolemModel<>(context.bakeLayer(CopperGolemModel.LAYER_LOCATION)), 0.7F);
    this.addLayer(new CopperGolemCrackinessLayer(this));
    // this.addLayer(new IronGolemFlowerLayer(this, context.getBlockRenderDispatcher()));
  }

  @Override
  public Identifier getTextureLocation(CopperGolem entity) {
    return TEXTURE_LOCATION;
  }

  @Override
  protected void setupRotations(CopperGolem golem, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
    super.setupRotations(golem, poseStack, ageInTicks, rotationYaw, partialTicks, scale);
    if (!((double) golem.walkAnimation.speed() < 0.01)) {
      float f = 13.0F;
      float g = golem.walkAnimation.position(partialTicks) + 6.0F;
      float h = (Math.abs(g % 13.0F - 6.5F) - 3.25F) / 3.25F;
      poseStack.mulPose(Axis.ZP.rotationDegrees(6.5F * h));
    }
  }
}
