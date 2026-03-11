package io.github.jason13official.more_useful_copper.impl.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.client.model.CopperStatueModel;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue.Type;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import oshi.util.tuples.Pair;

public class CopperStatueRenderer extends EntityRenderer<CopperStatue> {

  private final Map<CopperStatue.Type, Pair<ResourceLocation, ListModel<CopperStatue>>> statueResources;

//  public CopperStatueRenderer(Context context, CopperStatueModel model, float shadowRadius) {
//    super(context, model, shadowRadius);
//  }

  public CopperStatueRenderer(Context context) {
    super(context);

    this.shadowRadius = 0.0f;
    this.statueResources = Stream.of(CopperStatue.Type.values()).collect(
        ImmutableMap.toImmutableMap(type -> type, type -> new Pair<ResourceLocation, ListModel<CopperStatue>>(MoreUsefulCopper.identifier(getTextureLocation(type)), this.createStatueModel(context, type)))
    );
  }

  private static String getTextureLocation(CopperStatue.Type type) {
    return "textures/entity/copper_statue/" + type.getName() + ".png";
  }

  private ListModel<CopperStatue> createStatueModel(Context context, Type type) {
    return null;
  }

  @Override
  public ResourceLocation getTextureLocation(CopperStatue statue) {
    return this.statueResources.get(statue.getVariant()).getA();
  }
}
