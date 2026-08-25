package com.stonytark.usefultoolsmod.entity.ai.goal;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmArmorHelper;
import com.stonytark.usefultoolsmod.trigger.ModTriggers;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.List;

public class FollowPlayerGoal extends Goal {

    private final MobEntity mob;
    private PlayerEntity targetPlayer;
    private final double speedModifier;
    private final double followRange;
    private final double minStopDistance;
    private final double maxStopDistance;
    private double variableStopDist;

    public FollowPlayerGoal(MobEntity mob, double speedModifier, double followRange, double minStopDistance) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.followRange = followRange;
        this.minStopDistance = minStopDistance;
        this.maxStopDistance = 5.0;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (mob.isBaby()) return false;
        if (mob instanceof AnimalEntity a && a.isInLove()) return false;

        List<PlayerEntity> players = mob.getWorld().getEntitiesByClass(
                PlayerEntity.class,
                mob.getBoundingBox().expand(followRange),
                player -> player.isAlive() && !player.isSpectator()
        );

        double closestDist = Double.MAX_VALUE;
        PlayerEntity closestPlayer = null;

        for (PlayerEntity player : players) {
            if (Config.ectoplasmGhostAvoidance && EctoplasmArmorHelper.isGhostInvisible(player)) {
                continue;
            }
            double dist = mob.squaredDistanceTo(player);
            if (dist < closestDist && mob.canSee(player)) {
                closestDist = dist;
                closestPlayer = player;
            }
        }

        if (closestPlayer != null) {
            this.targetPlayer = closestPlayer;
            this.variableStopDist = minStopDistance
                    + mob.getRandom().nextDouble() * (maxStopDistance - minStopDistance);
            if (closestPlayer instanceof ServerPlayerEntity serverPlayer) {
                ModTriggers.GHOST_NEARBY.trigger(serverPlayer);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        if (targetPlayer != null && Config.ectoplasmGhostAvoidance
                && EctoplasmArmorHelper.isGhostInvisible(targetPlayer)) {
            return false;
        }
        return targetPlayer != null
                && targetPlayer.isAlive()
                && mob.squaredDistanceTo(targetPlayer) > variableStopDist * variableStopDist
                && mob.canSee(targetPlayer);
    }

    @Override
    public void stop() {
        targetPlayer = null;
        mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (targetPlayer == null) return;
        mob.getLookControl().lookAt(targetPlayer, 30.0F, 30.0F);
        Vec3d targetPos = targetPlayer.getPos();
        mob.getNavigation().startMovingTo(targetPos.x, targetPos.y + 1.0D, targetPos.z, speedModifier);
    }
}
