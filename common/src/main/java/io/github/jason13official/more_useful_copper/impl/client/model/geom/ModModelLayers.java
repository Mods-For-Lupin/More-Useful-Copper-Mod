package io.github.jason13official.more_useful_copper.impl.client.model.geom;

import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModModelLayers {

  public static ModelLayerLocation createBoatModelName(CopperStatue.Type type) {
    return createLocation("copper_statue/" + type.getName(), "main");
  }

  private static ModelLayerLocation createLocation(String path, String model) {
    return new ModelLayerLocation(MoreUsefulCopper.identifier(path), model);
  }
}
