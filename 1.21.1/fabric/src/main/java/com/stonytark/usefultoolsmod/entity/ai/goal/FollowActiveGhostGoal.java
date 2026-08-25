package com.stonytark.usefultoolsmod.entity.ai.goal;

import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.EnumSet;
import java.util.List;

/**
 * Ghost AI goal: trail behind another ghost that is itself actively navigating
 * toward a real target (player or animal).
 *
 * <p>This produces emergent group movement — ghosts drift together toward the
 * same subject of interest — while breaking the deadlock loop where two idle
 * ghosts would otherwise target each other in {@link ObserveFriendlyMobGoal}.
 * An idle/wandering ghost has no active navigation path and therefore cannot
 * be selected as a lead, so circular chasing is impossible.
 *
 * <p>When trailing ghosts arrive close enough to the lead ghost's destination,
 * their own higher-priority goals ({@link FollowPlayerGoal} or
 * {@link ObserveFriendlyMobGoal}) will fire and they independently observe
 * the same target — producing the natural small-group behaviour.
 */
public class FollowActiveGhostGoal extends Goal {

    private final MobEntity mob;
    private GhostEntity leadGhost;
    private final double speedModifier;
    private final double followRange;
    /** Randomised each activation so ghosts spread out rather than stacking. */
    private double stopDistance;

    public FollowActiveGhostGoal(MobEntity mob, double speedModifier, double followRange) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.followRange = followRange;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (mob.isBaby()) return false;
        // Don't steal MOVE/LOOK from higher-priority player-following
        if (hasVisiblePlayer()) return false;

        List<GhostEntity> activeGhosts = mob.getWorld().getEntitiesByClass(
                GhostEntity.class,
                mob.getBoundingBox().expand(followRange),
                g -> g != mob
                        && g.isAlive()
                        && g.getNavigation().isFollowingPath()
        );

        if (activeGhosts.isEmpty()) return false;

        // Prefer the closest actively-navigating ghost
        GhostEntity closest = null;
        double closestDist = Double.MAX_VALUE;
        for (GhostEntity g : activeGhosts) {
            double d = mob.squaredDistanceTo(g);
            if (d < closestDist) {
                closestDist = d;
                closest = g;
            }
        }

        if (closest != null) {
            this.leadGhost = closest;
            // 2–5 blocks so trailing ghosts form a loose cluster, not a conga line
            this.stopDistance = 2.0 + mob.getRandom().nextDouble() * 3.0;
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        return leadGhost != null
                && leadGhost.isAlive()
                && leadGhost.getNavigation().isFollowingPath()
                && mob.squaredDistanceTo(leadGhost) < followRange * followRange;
    }

    @Override
    public void stop() {
        leadGhost = null;
        mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (leadGhost == null) return;
        mob.getLookControl().lookAt(leadGhost, 30.0F, 30.0F);
        if (mob.squaredDistanceTo(leadGhost) > stopDistance * stopDistance) {
            mob.getNavigation().startMovingTo(leadGhost, speedModifier);
        }
    }

    private boolean hasVisiblePlayer() {
        return !mob.getWorld().getEntitiesByClass(
                PlayerEntity.class,
                mob.getBoundingBox().expand(followRange),
                p -> p.isAlive() && !p.isSpectator() && mob.canSee(p)
        ).isEmpty();
    }
}
