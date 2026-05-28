package io.github.jason13official.more_useful_copper.impl.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.client.model.CreeperStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.SkeletonStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.SpiderStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.ZombieStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.geom.ModModelLayers;
import io.github.jason13official.more_useful_copper.impl.client.renderer.entity.state.CopperStatueRenderState;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue.Type;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class CopperStatueRenderer extends EntityRenderer<CopperStatue, CopperStatueRenderState> {

  private final Map<Type, Identifier> textures;
  private final Map<Type, EntityModel<EntityRenderState>> models;

  public CopperStatueRenderer(Context context) {
    super(context);
    this.shadowRadius = 0.0f;
    this.textures = Stream.of(Type.values()).collect(ImmutableMap.toImmutableMap(
        type -> type,
        type -> MoreUsefulCopper.identifier(getTextureLocation(type))
    ));
    this.models = Stream.of(Type.values()).collect(ImmutableMap.toImmutableMap(
        type -> type,
        type -> createStatueModel(context, type)
    ));
  }

  private static String getTextureLocation(Type type) {
    return "textures/entity/copper_statue/" + type.getName() + ".png";
  }

  @SuppressWarnings("unchecked")
  private EntityModel<EntityRenderState> createStatueModel(Context context, Type type) {
    ModelLayerLocation modelLayerLocation = ModModelLayers.createBoatModelName(type);
    ModelPart modelPart = context.bakeLayer(modelLayerLocation);
    return (EntityModel<EntityRenderState>) (EntityModel<?>) switch (type) {
      case CREEPER -> new CreeperStatueModel(modelPart);
      case SKELETON -> new SkeletonStatueModel(modelPart);
      case SPIDER -> new SpiderStatueModel(modelPart);
      case ZOMBIE -> new ZombieStatueModel(modelPart);
    };
  }

  @Override
  public CopperStatueRenderState createRenderState() {
    return new CopperStatueRenderState();
  }

  @Override
  public void extractRenderState(CopperStatue entity, CopperStatueRenderState state, float partialTicks) {
    super.extractRenderState(entity, state, partialTicks);
    state.variant = entity.getVariant();
    state.texture = this.textures.get(entity.getVariant());
    state.yRot = entity.getYRot(partialTicks);
  }

  @Override
  public void submit(CopperStatueRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
    poseStack.pushPose();
    poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - state.yRot));
    poseStack.scale(-1.0F, -1.0F, 1.0F);
    poseStack.translate(0.0F, -1.501F, 0.0F);

    EntityModel<EntityRenderState> model = this.models.get(state.variant);
    if (model != null && state.texture != null) {
      submitNodeCollector.submitModel(model, state, poseStack, state.texture, state.lightCoords, OverlayTexture.NO_OVERLAY, -1, null);
    }

    poseStack.popPose();
    super.submit(state, poseStack, submitNodeCollector, camera);
  }
}
