package com.stonytark.usefultoolsmod.entity.ai.goal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.EnumSet;
import java.util.List;

/**
 * Ghost AI goal: passively observe nearby friendly mobs (animals/villagers) when
 * no player is in direct line of sight. Lower priority than FollowPlayerGoal.
 *
 * <p>An optional {@code excluded} class prevents ghosts from targeting each other,
 * breaking the mutual-follow deadlock where two idle ghosts orbit one another.
 */
public class ObserveFriendlyMobGoal extends Goal {

    private final MobEntity mob;
    private MobEntity targetMob;
    private final double speedModifier;
    private final double followRange;
    private float variableStopDist;
    private final Class<? extends MobEntity> excluded;

    public ObserveFriendlyMobGoal(MobEntity mob, double speedModifier, double followRange, Class<? extends MobEntity> excluded) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.followRange = followRange;
        this.excluded = excluded;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (mob.isBaby()) return false;
        // Don't activate when a visible player is nearby — FollowPlayerGoal handles that
        if (hasVisiblePlayer()) return false;

        List<MobEntity> friendlyMobs = mob.getWorld().getEntitiesByClass(
                MobEntity.class,
                mob.getBoundingBox().expand(followRange),
                m -> (m instanceof AnimalEntity || m instanceof VillagerEntity)
                        && m.isAlive()
                        && m != mob
                        && !excluded.isInstance(m)
        );

        if (friendlyMobs.isEmpty()) return false;

        MobEntity closest = null;
        double closestDist = Double.MAX_VALUE;
        for (MobEntity m : friendlyMobs) {
            double d = mob.squaredDistanceTo(m);
            if (d < closestDist) {
                closestDist = d;
                closest = m;
            }
        }

        if (closest != null) {
            this.targetMob = closest;
            this.variableStopDist = 1.0f + mob.getRandom().nextFloat() * 4.0f;
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        if (hasVisiblePlayer()) return false;
        return targetMob != null
                && targetMob.isAlive()
                && mob.squaredDistanceTo(targetMob) > variableStopDist * variableStopDist;
    }

    @Override
    public void stop() {
        targetMob = null;
        mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (targetMob == null) return;
        mob.getLookControl().lookAt(targetMob, 30.0F, 30.0F);
        mob.getNavigation().startMovingTo(targetMob, speedModifier);
    }

    private boolean hasVisiblePlayer() {
        List<PlayerEntity> players = mob.getWorld().getEntitiesByClass(
                PlayerEntity.class,
                mob.getBoundingBox().expand(followRange),
                p -> p.isAlive() && !p.isSpectator() && mob.canSee(p)
        );
        return !players.isEmpty();
    }
}
