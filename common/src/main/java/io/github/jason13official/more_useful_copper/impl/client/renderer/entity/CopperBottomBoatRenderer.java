package io.github.jason13official.more_useful_copper.impl.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.client.model.CopperBottomBoatModel;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperBottomBoat;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.WaterPatchModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

public class CopperBottomBoatRenderer extends EntityRenderer<CopperBottomBoat> {

  //  private final Map<CopperBottomBoat.Type, Pair<ResourceLocation, ListModel<CopperBottomBoat>>> boatResources;
  private final Pair<ResourceLocation, ListModel<CopperBottomBoat>> rlModelPair;

  public CopperBottomBoatRenderer(Context context) {
    super(context);
    this.shadowRadius = 0.8F;
//    this.boatResources = Stream.of(Type.values())
//        .collect(ImmutableMap.toImmutableMap((type) -> type, (type) -> Pair.of(MoreUsefulCopper.identifier(getTextureLocation()), this.createBoatModel(context))));
    this.rlModelPair = Pair.of(MoreUsefulCopper.identifier(getTextureLocation()), this.createBoatModel(context));
  }

  private static String getTextureLocation() {
    return "textures/entity/boat/copper_bottom_boat.png";
  }

  private ListModel<CopperBottomBoat> createBoatModel(Context context) {
    ModelLayerLocation modellayerlocation = CopperBottomBoatModel.LAYER_LOCATION;
    ModelPart modelpart = context.bakeLayer(modellayerlocation);
    return new CopperBottomBoatModel(modelpart);
  }

  public void render(CopperBottomBoat boat, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
    poseStack.pushPose();
    poseStack.translate(0.0F, 0.375F, 0.0F);
    poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
    float f = (float) boat.getHurtTime() - partialTicks;
    float f1 = boat.getDamage() - partialTicks;
    if (f1 < 0.0F) {
      f1 = 0.0F;
    }

    if (f > 0.0F) {
      poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float) boat.getHurtDir()));
    }

    float f2 = boat.getBubbleAngle(partialTicks);
    if (!Mth.equal(f2, 0.0F)) {
      poseStack.mulPose((new Quaternionf()).setAngleAxis(boat.getBubbleAngle(partialTicks) * ((float) Math.PI / 180F), 1.0F, 0.0F, 1.0F));
    }

    ResourceLocation resourcelocation = this.getTextureLocation(boat);
    ListModel<CopperBottomBoat> listmodel = this.rlModelPair.getSecond();
    poseStack.scale(-1.0F, -1.0F, 1.0F);
    poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
    listmodel.setupAnim(boat, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
    VertexConsumer vertexconsumer = buffer.getBuffer(listmodel.renderType(resourcelocation));
    listmodel.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    if (!boat.isUnderWater()) {
      VertexConsumer vertexconsumer1 = buffer.getBuffer(RenderType.waterMask());
      if (listmodel instanceof WaterPatchModel waterpatchmodel) {
        waterpatchmodel.waterPatch().render(poseStack, vertexconsumer1, packedLight, OverlayTexture.NO_OVERLAY);
      }
    }

    poseStack.popPose();
    super.render(boat, entityYaw, partialTicks, poseStack, buffer, packedLight);
  }

  public ResourceLocation getTextureLocation(CopperBottomBoat entity) {

    int oxi = entity.getOxidizationLevel();
    String oxiSuffix = oxi == 0 ? "" : String.valueOf(oxi);

    return MoreUsefulCopper.identifier("textures/entity/boat/copper_bottom_boat" + oxiSuffix + ".png");
  }
}

