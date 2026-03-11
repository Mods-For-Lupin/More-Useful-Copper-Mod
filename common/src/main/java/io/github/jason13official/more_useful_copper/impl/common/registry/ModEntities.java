package io.github.jason13official.more_useful_copper.impl.common.registry;

import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {

  public static EntityType<CopperStatue> COPPER_STATUE;

  public static void register(BiConsumer<EntityType<?>, ResourceLocation> consumer) {

    COPPER_STATUE = EntityType.Builder.<CopperStatue>of(CopperStatue::new, MobCategory.MISC).sized(0.5F, 1.975F).clientTrackingRange(10).build(MoreUsefulCopper.identifier("copper_statue").toString());
    consumer.accept(COPPER_STATUE, MoreUsefulCopper.identifier("copper_statue"));
  }
}
