package io.github.jason13official.more_useful_copper.impl.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.client.model.CreeperStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.SkeletonStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.SpiderStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.ZombieStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.geom.ModModelLayers;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue.Type;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import oshi.util.tuples.Pair;

public class CopperStatueRenderer extends EntityRenderer<CopperStatue> {

  private final Map<Type, Pair<ResourceLocation, EntityModel<CopperStatue>>> statueResources;

//  public CopperStatueRenderer(Context context, CopperStatueModel model, float shadowRadius) {
//    super(context, model, shadowRadius);
//  }

  public CopperStatueRenderer(Context context) {
    super(context);

    this.shadowRadius = 0.0f;
    this.statueResources = Stream.of(Type.values()).collect(ImmutableMap.toImmutableMap(type -> type,
        type -> new Pair<ResourceLocation, EntityModel<CopperStatue>>(MoreUsefulCopper.identifier(getTextureLocation(type)), this.createStatueModel(context, type))));
  }

  private static String getTextureLocation(Type type) {
    return "textures/entity/copper_statue/" + type.getName() + ".png";
  }

  private EntityModel<CopperStatue> createStatueModel(Context context, Type type) {

    ModelLayerLocation modelLayerLocation = ModModelLayers.createBoatModelName(type);
    ModelPart modelPart = context.bakeLayer(modelLayerLocation);

    return switch (type) {
      case CREEPER -> new CreeperStatueModel(modelPart);
      case SKELETON -> new SkeletonStatueModel(modelPart);
      case SPIDER -> new SpiderStatueModel(modelPart);
      case ZOMBIE -> new ZombieStatueModel(modelPart);
    };
  }

  @Override
  public ResourceLocation getTextureLocation(CopperStatue statue) {
    return this.statueResources.get(statue.getVariant()).getA();
  }

  @Override
  public void render(CopperStatue entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

    poseStack.pushPose();

    // BoatRenderer: get the model out of the ground
    // poseStack.translate(0.0F, 0.375F, 0.0F);

    // flip the model vertically (bottom becomes top)
    poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));

    // invert the model on the x-axis and y-axis ???
    poseStack.scale(-1.0F, -1.0F, 1.0F);

    // LivingEntityRenderer: get the model out of the ground
    poseStack.translate(0.0F, -1.501F, 0.0F);

    Pair<ResourceLocation, EntityModel<CopperStatue>> pair = this.statueResources.get(entity.getVariant());
    ResourceLocation resourceLocation = pair.getA();
    EntityModel<CopperStatue> model = pair.getB();

    VertexConsumer vertexConsumer = buffer.getBuffer(model.renderType(resourceLocation));
    model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

    poseStack.popPose();

    super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
  }
}
