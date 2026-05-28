package io.github.jason13official.more_useful_copper.impl.common.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class FollowPlayerGoal extends Goal {

  private final PathfinderMob mob;
  private final double speedModifier;

  private int calmDown;
  @Nullable
  private Player player;

  private boolean isRunning;

  public FollowPlayerGoal(PathfinderMob mob, double speedModifier) {
    this.mob = mob;
    this.speedModifier = speedModifier;
    this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
  }

  @Override
  public boolean canUse() {

    if (this.calmDown > 0) {
      --this.calmDown;
      return false;
    } else {
      this.player = this.mob.level().getNearestPlayer(this.mob, 10.0);
      return this.player != null;
    }
  }

  @Override
  public boolean canContinueToUse() {
    return this.canUse();
  }

  @Override
  public void start() {
    // super.start();
    this.isRunning = true;
  }

  @Override
  public void stop() {
    this.player = null;
    this.mob.getNavigation().stop();
    this.calmDown = Goal.reducedTickDelay(100);
    this.isRunning = false;
    // super.stop();
  }

  @Override
  public void tick() {

    // super.tick();

    this.mob.getLookControl().setLookAt(this.player, (float) (this.mob.getMaxHeadYRot() + 20), (float) this.mob.getMaxHeadXRot());

    if (this.mob.distanceToSqr(this.player) < 8.0D) {
      this.mob.getNavigation().stop();
    } else {
      this.mob.getNavigation().moveTo(this.player, this.speedModifier);
    }
  }
}
