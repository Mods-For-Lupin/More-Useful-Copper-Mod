package io.github.jason13official.more_useful_copper.impl.common.entity.ai.goal;

import io.github.jason13official.more_useful_copper.impl.common.entity.CopperGolem;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class DefendPlayerTargetGoal extends TargetGoal {

    private final CopperGolem golem;

    @Nullable private LivingEntity potentialTarget;
    private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0);

    public DefendPlayerTargetGoal(CopperGolem golem) {
        super(golem, false, true);
        this.golem = golem;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public void start() {
        this.golem.setTarget(this.potentialTarget);
        super.start();
    }

    @Override
    public boolean canUse() {

        AABB golemBoundingBox = this.golem.getBoundingBox().inflate(10.0, 8.0, 10.0);
        List<Player> playersNearby = this.golem.level().getNearbyPlayers(this.attackTargeting, this.golem, golemBoundingBox);

        for (Player player : playersNearby) {
            LivingEntity lastAttacker = player.getLastAttacker() == null ? player.getLastAttacker() : null;

            if (lastAttacker != null && lastAttacker.isDeadOrDying()) {
                this.potentialTarget = lastAttacker;
            }
        }

        if (this.potentialTarget == null) {
            return false;
        }

        if (!(this.potentialTarget instanceof Player player) || !this.potentialTarget.isSpectator() && !player.isCreative()) {
            return true;
        }

        return false;
    }
}
