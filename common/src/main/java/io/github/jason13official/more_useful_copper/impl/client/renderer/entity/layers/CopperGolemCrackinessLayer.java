package io.github.jason13official.more_useful_copper.impl.client.renderer.entity.layers;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.jason13official.more_useful_copper.impl.client.model.CopperGolemModel;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperGolem;
import java.util.Map;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem.Crackiness;

public class CopperGolemCrackinessLayer extends RenderLayer<CopperGolem, CopperGolemModel<CopperGolem>> {

  private static final Map<Crackiness, ResourceLocation> resourceLocations = ImmutableMap.of(Crackiness.LOW, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_low.png"),
      Crackiness.MEDIUM, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_medium.png"), Crackiness.HIGH,
      new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_high.png"));

  public CopperGolemCrackinessLayer(RenderLayerParent<CopperGolem, CopperGolemModel<CopperGolem>> renderer) {
    super(renderer);
  }

  public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, CopperGolem livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
      float netHeadYaw, float headPitch) {
    if (!livingEntity.isInvisible()) {
      Crackiness crackiness = livingEntity.getCrackiness();
      if (crackiness != Crackiness.NONE) {
        ResourceLocation resourcelocation = resourceLocations.get(crackiness);
        renderColoredCutoutModel(this.getParentModel(), resourcelocation, poseStack, buffer, packedLight, livingEntity, 1.0F, 1.0F, 1.0F);
      }
    }
  }
}
