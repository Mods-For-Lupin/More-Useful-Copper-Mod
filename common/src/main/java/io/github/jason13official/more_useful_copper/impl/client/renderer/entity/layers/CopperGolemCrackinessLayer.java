package io.github.jason13official.more_useful_copper.impl.client.renderer.entity.layers;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.jason13official.more_useful_copper.impl.client.model.CopperGolemModel;
import io.github.jason13official.more_useful_copper.impl.client.renderer.entity.state.CopperGolemRenderState;
import java.util.Map;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Crackiness;

public class CopperGolemCrackinessLayer extends RenderLayer<CopperGolemRenderState, CopperGolemModel> {

  private static final Map<Crackiness.Level, Identifier> resourceLocations = ImmutableMap.of(
    Crackiness.Level.LOW, Identifier.withDefaultNamespace("textures/entity/iron_golem/iron_golem_crackiness_low.png"),
    Crackiness.Level.MEDIUM, Identifier.withDefaultNamespace("textures/entity/iron_golem/iron_golem_crackiness_medium.png"),
    Crackiness.Level.HIGH, Identifier.withDefaultNamespace("textures/entity/iron_golem/iron_golem_crackiness_high.png")
  );

  public CopperGolemCrackinessLayer(RenderLayerParent<CopperGolemRenderState, CopperGolemModel> renderer) {
    super(renderer);
  }

  @Override
  public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, CopperGolemRenderState state, float yRot, float xRot) {
    if (!state.isInvisible) {
      Crackiness.Level crackiness = state.crackiness;
      if (crackiness != Crackiness.Level.NONE) {
        Identifier damageTexture = resourceLocations.get(crackiness);
        renderColoredCutoutModel(this.getParentModel(), damageTexture, poseStack, submitNodeCollector, lightCoords, state, -1, 1);
      }
    }
  }
}
