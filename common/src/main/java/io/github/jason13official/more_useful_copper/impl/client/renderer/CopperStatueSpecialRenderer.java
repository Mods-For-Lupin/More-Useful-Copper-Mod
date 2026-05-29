package io.github.jason13official.more_useful_copper.impl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.client.model.geom.ModModelLayers;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import java.util.function.Consumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import org.joml.Vector3fc;

public class CopperStatueSpecialRenderer implements NoDataSpecialModelRenderer {

  private final ModelPart root;
  private final RenderType renderType;

  CopperStatueSpecialRenderer(SpecialModelRenderer.BakingContext context, CopperStatue.Type type) {
    this.root = context.entityModelSet().bakeLayer(ModModelLayers.createBoatModelName(type));
    this.renderType = RenderTypes.entityCutout(
        MoreUsefulCopper.identifier("textures/entity/copper_statue/" + type.getName() + ".png")
    );
  }

  @Override
  public void submit(PoseStack poseStack, SubmitNodeCollector collector,
      int light, int overlay, boolean hasFoil, int outlineColor) {
    poseStack.pushPose();
    poseStack.scale(-1.0F, -1.0F, 1.0F);
    poseStack.translate(0.0F, -1.501F, 0.0F);
    collector.submitModelPart(root, poseStack, renderType, light, overlay, null, false, hasFoil, -1, null, outlineColor);
    poseStack.popPose();
  }

  @Override
  public void getExtents(Consumer<Vector3fc> output) {
    root.getExtentsForGui(new PoseStack(), output);
  }

  public enum Unbaked implements NoDataSpecialModelRenderer.Unbaked {
    CREEPER(CopperStatue.Type.CREEPER,   "copper_statue_creeper"),
    SKELETON(CopperStatue.Type.SKELETON, "copper_statue_skeleton"),
    SPIDER(CopperStatue.Type.SPIDER,     "copper_statue_spider"),
    ZOMBIE(CopperStatue.Type.ZOMBIE,     "copper_statue_zombie");

    public final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(this);
    public final String id;
    private final CopperStatue.Type type;

    Unbaked(CopperStatue.Type type, String id) {
      this.type = type;
      this.id = id;
    }

    @Override
    public MapCodec<? extends NoDataSpecialModelRenderer.Unbaked> type() {
      return MAP_CODEC;
    }

    @Override
    public CopperStatueSpecialRenderer bake(SpecialModelRenderer.BakingContext context) {
      return new CopperStatueSpecialRenderer(context, type);
    }
  }
}
