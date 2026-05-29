package io.github.jason13official.more_useful_copper.impl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.serialization.MapCodec;
import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.client.model.CreeperStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.SkeletonStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.SpiderStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.ZombieStatueModel;
import io.github.jason13official.more_useful_copper.impl.client.model.geom.ModModelLayers;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import java.util.function.Consumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.joml.Vector3fc;

public class CopperStatueSpecialRenderer implements NoDataSpecialModelRenderer {

  private static final EntityRenderState DUMMY_STATE = new EntityRenderState();

  private final ModelPart root;
  private final EntityModel<EntityRenderState> model;
  private final RenderType renderType;
  private final float scale;

  CopperStatueSpecialRenderer(SpecialModelRenderer.BakingContext context, CopperStatue.Type type, float scale) {
    this.root = context.entityModelSet().bakeLayer(ModModelLayers.createBoatModelName(type));
    this.model = switch (type) {
      case CREEPER  -> new CreeperStatueModel(root);
      case SKELETON -> new SkeletonStatueModel(root);
      case SPIDER   -> new SpiderStatueModel(root);
      case ZOMBIE   -> new ZombieStatueModel(root);
    };
    this.renderType = RenderTypes.entityCutout(
        MoreUsefulCopper.identifier("textures/entity/copper_statue/" + type.getName() + ".png")
    );
    this.scale = scale;
  }

  @Override
  public void submit(PoseStack poseStack, SubmitNodeCollector collector,
      int light, int overlay, boolean hasFoil, int outlineColor) {
    poseStack.pushPose();
    poseStack.translate(0.5F, 0.125F, 0.5F);
    poseStack.scale(scale, scale, scale);
    poseStack.scale(-1.0F, -1.0F, 1.0F);
    poseStack.translate(0.0F, -1.501F, 0.0F);
    extracted(poseStack);
    collector.submitModel(model, DUMMY_STATE, poseStack, renderType, light, OverlayTexture.NO_OVERLAY, outlineColor, null);
    poseStack.popPose();
  }

  private static void extracted(PoseStack poseStack) {
    poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
  }

  @Override
  public void getExtents(Consumer<Vector3fc> output) {
    root.getExtentsForGui(new PoseStack(), output);
  }

  public enum Unbaked implements NoDataSpecialModelRenderer.Unbaked {
    CREEPER (CopperStatue.Type.CREEPER,  "copper_statue_creeper",  0.31F + 0.2f),
    SKELETON(CopperStatue.Type.SKELETON, "copper_statue_skeleton", 0.25F + 0.2f),
    SPIDER  (CopperStatue.Type.SPIDER,   "copper_statue_spider",   0.40F + 0.2f),
    ZOMBIE  (CopperStatue.Type.ZOMBIE,   "copper_statue_zombie",   0.25F + 0.2f);

    public final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(this);
    public final String id;
    private final CopperStatue.Type type;
    private final float scale;

    Unbaked(CopperStatue.Type type, String id, float scale) {
      this.type = type;
      this.id = id;
      this.scale = scale;
    }

    @Override
    public MapCodec<? extends NoDataSpecialModelRenderer.Unbaked> type() {
      return MAP_CODEC;
    }

    @Override
    public CopperStatueSpecialRenderer bake(SpecialModelRenderer.BakingContext context) {
      return new CopperStatueSpecialRenderer(context, type, scale);
    }
  }
}
