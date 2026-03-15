package io.github.jason13official.more_useful_copper.impl.common.registry;

import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperBottomBoat;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperGolem;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import io.github.jason13official.more_useful_copper.impl.common.entity.ThrownLightningBottle;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {

  public static EntityType<CopperStatue> COPPER_STATUE;

  public static EntityType<CopperBottomBoat> COPPER_BOTTOM_BOAT;

  public static EntityType<ThrownLightningBottle> LIGHTNING_BOTTLE;

  public static EntityType<CopperGolem> COPPER_GOLEM;

  public static void register(BiConsumer<EntityType<?>, ResourceLocation> consumer) {

    COPPER_STATUE = EntityType.Builder.<CopperStatue>of(CopperStatue::new, MobCategory.MISC).sized(0.5F, 1.975F).clientTrackingRange(10).build(MoreUsefulCopper.identifier("copper_statue").toString());
    consumer.accept(COPPER_STATUE, MoreUsefulCopper.identifier("copper_statue"));

    COPPER_GOLEM = EntityType.Builder.of(CopperGolem::new, MobCategory.MISC).sized(1.4F, 2.7F).clientTrackingRange(10).build(MoreUsefulCopper.identifier("copper_golem").toString());
    consumer.accept(COPPER_GOLEM, MoreUsefulCopper.identifier("copper_golem"));

    COPPER_BOTTOM_BOAT = EntityType.Builder.<CopperBottomBoat>of(CopperBottomBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10)
        .build(MoreUsefulCopper.identifier("copper_bottom_boat").toString());
    consumer.accept(COPPER_BOTTOM_BOAT, MoreUsefulCopper.identifier("copper_bottom_boat"));

    LIGHTNING_BOTTLE = EntityType.Builder.<ThrownLightningBottle>of(ThrownLightningBottle::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
        .build(MoreUsefulCopper.identifier("lightning_bottle").toString());
    consumer.accept(LIGHTNING_BOTTLE, MoreUsefulCopper.identifier("lightning_bottle"));
  }
}
