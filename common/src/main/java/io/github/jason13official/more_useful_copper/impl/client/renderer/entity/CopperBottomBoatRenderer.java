package io.github.jason13official.more_useful_copper.impl.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.client.model.CopperBottomBoatModel;
import io.github.jason13official.more_useful_copper.impl.client.renderer.entity.state.CopperBottomBoatRenderState;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperBottomBoat;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

public class CopperBottomBoatRenderer extends EntityRenderer<CopperBottomBoat, CopperBottomBoatRenderState> {

  private static final Identifier COPPER_TEXTURE = MoreUsefulCopper.identifier("textures/entity/boat/copper_bottom_boat.png");
  private static final Identifier EXPOSED_TEXTURE = MoreUsefulCopper.identifier("textures/entity/boat/copper_bottom_boat1.png");
  private static final Identifier WEATHERED_TEXTURE = MoreUsefulCopper.identifier("textures/entity/boat/copper_bottom_boat2.png");
  private static final Identifier OXIDIZED_TEXTURE = MoreUsefulCopper.identifier("textures/entity/boat/copper_bottom_boat3.png");

  private final EntityModel<BoatRenderState> model;

  public CopperBottomBoatRenderer(Context context) {
    super(context);
    this.shadowRadius = 0.8F;
    this.model = new CopperBottomBoatModel(context.bakeLayer(CopperBottomBoatModel.LAYER_LOCATION));
  }

  @Override
  public CopperBottomBoatRenderState createRenderState() {
    return new CopperBottomBoatRenderState();
  }

  @Override
  public void extractRenderState(CopperBottomBoat entity, CopperBottomBoatRenderState state, float partialTicks) {
    super.extractRenderState(entity, state, partialTicks);
    state.yRot = entity.getYRot(partialTicks);
    state.hurtTime = entity.getHurtTime() - partialTicks;
    state.hurtDir = entity.getHurtDir();
    state.damageTime = Math.max(entity.getDamage() - partialTicks, 0.0F);
    state.bubbleAngle = entity.getBubbleAngle(partialTicks);
    state.isUnderWater = entity.isUnderWater();
    state.rowingTimeLeft = entity.getRowingTime(0, partialTicks);
    state.rowingTimeRight = entity.getRowingTime(1, partialTicks);
    state.oxidizationLevel = entity.getOxidizationLevel();
  }

  @Override
  public void submit(CopperBottomBoatRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
    Identifier texture = switch (state.oxidizationLevel) {
      case 1 -> EXPOSED_TEXTURE;
      case 2 -> WEATHERED_TEXTURE;
      case 3 -> OXIDIZED_TEXTURE;
      default -> COPPER_TEXTURE;
    };

    poseStack.pushPose();
    poseStack.translate(0.0F, 0.375F, 0.0F);
    poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - state.yRot));
    float hurt = state.hurtTime;
    if (hurt > 0.0F) {
      poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(hurt) * hurt * state.damageTime / 10.0F * state.hurtDir));
    }
    if (!state.isUnderWater && !Mth.equal(state.bubbleAngle, 0.0F)) {
      poseStack.mulPose(new Quaternionf().setAngleAxis(state.bubbleAngle * (float) (Math.PI / 180.0), 1.0F, 0.0F, 1.0F));
    }
    poseStack.scale(-1.0F, -1.0F, 1.0F);
    poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
    submitNodeCollector.submitModel(this.model, state, poseStack, texture, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
    poseStack.popPose();
    super.submit(state, poseStack, submitNodeCollector, camera);
  }
}
