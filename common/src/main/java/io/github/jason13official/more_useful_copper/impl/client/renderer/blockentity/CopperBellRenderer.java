package io.github.jason13official.more_useful_copper.impl.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.client.renderer.blockentity.state.CopperBellRenderState;
import io.github.jason13official.more_useful_copper.impl.common.block.entity.CopperBellBlockEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class CopperBellRenderer implements BlockEntityRenderer<CopperBellBlockEntity, CopperBellRenderState> {

  public static final Identifier COPPER_BELL_TEXTURE = MoreUsefulCopper.identifier("textures/entity/bell/bell_body.png");
  public static final Identifier EXPOSED_COPPER_BELL_TEXTURE = MoreUsefulCopper.identifier("textures/entity/bell/bell_body1.png");
  public static final Identifier WEATHERED_COPPER_BELL_TEXTURE = MoreUsefulCopper.identifier("textures/entity/bell/bell_body2.png");
  public static final Identifier OXIDIZED_COPPER_BELL_TEXTURE = MoreUsefulCopper.identifier("textures/entity/bell/bell_body3.png");
  private static final String BELL_BODY = "bell_body";
  private final ModelPart bellBody;

  public CopperBellRenderer(BlockEntityRendererProvider.Context context) {
    ModelPart modelpart = context.bakeLayer(ModelLayers.BELL);
    this.bellBody = modelpart.getChild(BELL_BODY);
  }

  public static LayerDefinition createBodyLayer() {
    MeshDefinition meshdefinition = new MeshDefinition();
    PartDefinition partdefinition = meshdefinition.getRoot();
    PartDefinition partdefinition1 = partdefinition.addOrReplaceChild(BELL_BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 7.0F, 6.0F),
        PartPose.offset(8.0F, 12.0F, 8.0F));
    partdefinition1.addOrReplaceChild("bell_base", CubeListBuilder.create().texOffs(0, 13).addBox(4.0F, 4.0F, 4.0F, 8.0F, 2.0F, 8.0F), PartPose.offset(-8.0F, -12.0F, -8.0F));
    return LayerDefinition.create(meshdefinition, 32, 32);
  }

  @Override
  public CopperBellRenderState createRenderState() {
    return new CopperBellRenderState();
  }

  @Override
  public void extractRenderState(CopperBellBlockEntity blockEntity, CopperBellRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
    BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
    state.ticks = blockEntity.ticks + partialTicks;
    state.shakeDirection = blockEntity.shaking ? blockEntity.clickDirection : null;
    state.oxidization = blockEntity.oxidization;
  }

  @Override
  public void submit(CopperBellRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
    float f1 = 0.0F;
    float f2 = 0.0F;
    Direction shakeDir = state.shakeDirection;
    if (shakeDir != null) {
      float f3 = Mth.sin(state.ticks / (float) Math.PI) / (4.0F + state.ticks / 3.0F);
      if (shakeDir == Direction.NORTH) {
        f1 = -f3;
      } else if (shakeDir == Direction.SOUTH) {
        f1 = f3;
      } else if (shakeDir == Direction.EAST) {
        f2 = -f3;
      } else if (shakeDir == Direction.WEST) {
        f2 = f3;
      }
    }

    this.bellBody.xRot = f1;
    this.bellBody.zRot = f2;

    Identifier texture = switch (state.oxidization) {
      case 1 -> EXPOSED_COPPER_BELL_TEXTURE;
      case 2 -> WEATHERED_COPPER_BELL_TEXTURE;
      case 3 -> OXIDIZED_COPPER_BELL_TEXTURE;
      default -> COPPER_BELL_TEXTURE;
    };

    submitNodeCollector.submitModelPart(this.bellBody, poseStack, RenderTypes.entityCutout(texture), state.lightCoords, OverlayTexture.NO_OVERLAY, null);
  }
}
