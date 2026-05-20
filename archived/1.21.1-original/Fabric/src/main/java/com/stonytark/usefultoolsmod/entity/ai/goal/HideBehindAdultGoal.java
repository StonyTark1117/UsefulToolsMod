package com.stonytark.usefultoolsmod.entity.ai.goal;

import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

/**
 * Baby ghost AI goal: when any non-ghost entity is nearby, flee to safety.
 *
 * <p>Three-tier fallback in priority order:
 * <ol>
 *   <li><b>Adult ghost cover</b> — navigate to the far side of the nearest adult ghost
 *       relative to the threat, tucking the baby behind it.</li>
 *   <li><b>Block cover</b> — search outward in the flee direction for a solid block and
 *       navigate just past it so the block breaks line of sight.</li>
 *   <li><b>Pure flee</b> — if neither option is available, simply move 8 blocks away from
 *       the threat.</li>
 * </ol>
 *
 * <p>The hiding destination is cached for 20 ticks to avoid expensive recalculations
 * every tick.
 */
public class HideBehindAdultGoal extends Goal {

    private static final double THREAT_RANGE   = 10.0;
    private static final double ADULT_RANGE    = 16.0;
    private static final double HIDE_OFFSET    = 2.5;  // blocks behind adult
    private static final double COVER_OFFSET   = 1.5;  // blocks past solid cover
    private static final double FLEE_DISTANCE  = 8.0;
    private static final int    RECALC_INTERVAL = 20;  // ticks

    private final MobEntity mob;
    private final double speedModifier;

    private @Nullable LivingEntity threat;
    private @Nullable Vec3d hideTarget;
    private int recalcCooldown = 0;

    public HideBehindAdultGoal(MobEntity mob, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (!mob.isBaby()) return false;

        LivingEntity nearest = findNearestThreat();
        if (nearest == null) return false;

        this.threat = nearest;
        this.hideTarget = computeHideTarget(nearest);
        this.recalcCooldown = RECALC_INTERVAL;
        return hideTarget != null;
    }

    @Override
    public boolean shouldContinue() {
        return threat != null && threat.isAlive()
                && mob.squaredDistanceTo(threat) < THREAT_RANGE * THREAT_RANGE * 1.5;
    }

    @Override
    public void stop() {
        threat = null;
        hideTarget = null;
        mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (threat == null) return;

        // Look away from threat (toward safety)
        Vec3d awayDir = mob.getPos().subtract(threat.getPos()).normalize();
        Vec3d lookAt = mob.getPos().add(awayDir);
        mob.getLookControl().lookAt(lookAt.x, lookAt.y, lookAt.z, 30.0F, 30.0F);

        // Recalculate hiding destination periodically
        if (--recalcCooldown <= 0) {
            hideTarget = computeHideTarget(threat);
            recalcCooldown = RECALC_INTERVAL;
        }

        if (hideTarget != null) {
            mob.getNavigation().startMovingTo(hideTarget.x, hideTarget.y, hideTarget.z, speedModifier);
        }
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    /** Returns the nearest non-ghost living entity within {@link #THREAT_RANGE}. */
    private @Nullable LivingEntity findNearestThreat() {
        List<LivingEntity> threats = mob.getWorld().getEntitiesByClass(
                LivingEntity.class,
                mob.getBoundingBox().expand(THREAT_RANGE),
                e -> e.isAlive() && e != mob && !(e instanceof GhostEntity)
        );

        LivingEntity nearest = null;
        double nearestDist = Double.MAX_VALUE;
        for (LivingEntity e : threats) {
            double d = mob.squaredDistanceTo(e);
            if (d < nearestDist) {
                nearestDist = d;
                nearest = e;
            }
        }
        return nearest;
    }

    /**
     * Compute a safe destination, trying adult cover first, then block cover,
     * then a plain flee vector.
     */
    private @Nullable Vec3d computeHideTarget(LivingEntity threat) {
        Vec3d threatPos = threat.getPos();

        // --- Tier 1: hide behind an adult ghost ---
        GhostEntity adult = findNearestAdult();
        if (adult != null) {
            Vec3d adultPos = adult.getPos();
            Vec3d shieldDir = adultPos.subtract(threatPos).normalize();
            return adultPos.add(shieldDir.multiply(HIDE_OFFSET)).add(0, 0.5, 0);
        }

        // --- Tier 2: hide behind a solid block ---
        Vec3d fleeDir = mob.getPos().subtract(threatPos).normalize();
        Vec3d blockCover = findBlockCover(fleeDir);
        if (blockCover != null) return blockCover;

        // --- Tier 3: pure flee ---
        return mob.getPos().add(fleeDir.multiply(FLEE_DISTANCE));
    }

    /** Nearest adult {@link GhostEntity} within {@link #ADULT_RANGE}. */
    private @Nullable GhostEntity findNearestAdult() {
        List<GhostEntity> adults = mob.getWorld().getEntitiesByClass(
                GhostEntity.class,
                mob.getBoundingBox().expand(ADULT_RANGE),
                g -> g != mob && g.isAlive() && !g.isBaby()
        );

        GhostEntity nearest = null;
        double nearestDist = Double.MAX_VALUE;
        for (GhostEntity g : adults) {
            double d = mob.squaredDistanceTo(g);
            if (d < nearestDist) {
                nearestDist = d;
                nearest = g;
            }
        }
        return nearest;
    }

    /**
     * Walk {@code fleeDir} outward from the baby 2–10 blocks, checking y±1 for
     * a solid block the ghost can hide behind.  Returns a position 1.5 blocks
     * past the solid block so the block breaks line of sight.
     */
    private @Nullable Vec3d findBlockCover(Vec3d fleeDir) {
        World world = mob.getWorld();
        Vec3d origin = mob.getPos();

        for (int step = 2; step <= 10; step++) {
            Vec3d candidate = origin.add(fleeDir.multiply(step));
            BlockPos base = BlockPos.ofFloored(candidate);

            for (int dy = -1; dy <= 1; dy++) {
                BlockPos check = base.up(dy);
                if (world.getBlockState(check).isSolidBlock(world, check)) {
                    // Position 1.5 blocks past the solid block in the flee direction
                    Vec3d coverPos = Vec3d.ofCenter(check).add(fleeDir.multiply(COVER_OFFSET));
                    return coverPos;
                }
            }
        }
        return null;
    }
}
