package io.github.jason13official.more_useful_copper.impl.common.entity;

import java.util.UUID;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AbstractPersistentAngerPathfinderMob extends PathfinderMob implements NeutralMob {

  /// mimicking [IronGolem] persistent anger
  private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

  /// mimicking [IronGolem] persistent anger
  private int remainingPersistentAngerTime;

  /// mimicking [IronGolem] persistent anger
  @Nullable
  private UUID persistentAngerTarget;

  public AbstractPersistentAngerPathfinderMob(EntityType<? extends AbstractPersistentAngerPathfinderMob> entityType, Level level) {
    super(entityType, level);
  }

  @Override
  public void startPersistentAngerTimer() {
    this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
  }

  @Override
  public int getRemainingPersistentAngerTime() {
    return this.remainingPersistentAngerTime;
  }

  @Override
  public void setRemainingPersistentAngerTime(int amount) {
    this.remainingPersistentAngerTime = amount;
  }

  @Override
  public UUID getPersistentAngerTarget() {
    return this.persistentAngerTarget;
  }

  @Override
  public void setPersistentAngerTarget(@Nullable UUID pTarget) {
    this.persistentAngerTarget = pTarget;
  }
}
