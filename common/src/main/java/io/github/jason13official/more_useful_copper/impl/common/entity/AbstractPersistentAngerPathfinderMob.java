package io.github.jason13official.more_useful_copper.impl.common.entity;

import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractPersistentAngerPathfinderMob extends PathfinderMob implements NeutralMob {

  /// mimicking [IronGolem] persistent anger
  private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

  private long persistentAngerEndTime;

  @Nullable
  private EntityReference<LivingEntity> persistentAngerTarget;

  public AbstractPersistentAngerPathfinderMob(EntityType<? extends AbstractPersistentAngerPathfinderMob> entityType, Level level) {
    super(entityType, level);
  }

  @Override
  public void startPersistentAngerTimer() {
    this.setTimeToRemainAngry(PERSISTENT_ANGER_TIME.sample(this.random));
  }

  @Override
  public long getPersistentAngerEndTime() {
    return this.persistentAngerEndTime;
  }

  @Override
  public void setPersistentAngerEndTime(long endTime) {
    this.persistentAngerEndTime = endTime;
  }

  @Override
  public @Nullable EntityReference<LivingEntity> getPersistentAngerTarget() {
    return this.persistentAngerTarget;
  }

  @Override
  public void setPersistentAngerTarget(@Nullable EntityReference<LivingEntity> target) {
    this.persistentAngerTarget = target;
  }
}
