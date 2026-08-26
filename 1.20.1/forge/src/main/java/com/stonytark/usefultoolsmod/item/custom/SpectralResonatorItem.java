package com.stonytark.usefultoolsmod.item.custom;

import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;

public class SpectralResonatorItem extends Item {
    private static final double RANGE = 96.0D;

    public SpectralResonatorItem(Properties properties) { super(properties); }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!com.stonytark.usefultoolsmod.Config.spectralResonatorEnabled)
            return InteractionResultHolder.pass(stack);
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            LivingEntity nearest = level.getEntitiesOfClass(LivingEntity.class,
                            player.getBoundingBox().inflate(RANGE),
                            e -> e instanceof GhostEntity || e instanceof WraithEntity)
                    .stream().min(Comparator.comparingDouble(player::distanceToSqr)).orElse(null);
            if (nearest != null)
                com.stonytark.usefultoolsmod.util.ModAdvancements.award(serverPlayer, "spectral/locate_spirit");
            if (nearest == null) {
                player.displayClientMessage(Component.translatable("message.usefultoolsmod.resonator.none"), true);
            } else {
                Vec3 delta = nearest.position().subtract(player.position());
                String direction = direction(delta, player.getYRot());
                int distance = (int)Math.round(Math.sqrt(player.distanceToSqr(nearest)));
                player.displayClientMessage(Component.translatable("message.usefultoolsmod.resonator.found",
                        nearest.getDisplayName(), direction, distance), true);
            }
            stack.hurtAndBreak(1, serverPlayer, p -> p.broadcastBreakEvent(hand));
            player.getCooldowns().addCooldown(this, 200);
        } else if (level.isClientSide && com.stonytark.usefultoolsmod.client.SpectralClientConfig.particlesEnabled) {
            LivingEntity nearest = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(RANGE),
                    e -> e instanceof GhostEntity || e instanceof WraithEntity).stream()
                    .min(Comparator.comparingDouble(player::distanceToSqr)).orElse(null);
            if (nearest != null) {
                Vec3 step = nearest.position().subtract(player.getEyePosition()).normalize().scale(0.65D);
                Vec3 point = player.getEyePosition();
                for (int i = 0; i < 12; i++) {
                    point = point.add(step);
                    level.addParticle(net.minecraft.core.particles.ParticleTypes.SOUL_FIRE_FLAME,
                            point.x, point.y, point.z, 0, 0, 0);
                }
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private static String direction(Vec3 delta, float playerYaw) {
        double targetYaw = Math.toDegrees(Math.atan2(-delta.x, delta.z));
        double relative = (targetYaw - playerYaw + 540.0D) % 360.0D - 180.0D;
        if (Math.abs(relative) < 22.5D) return "ahead";
        if (Math.abs(relative) > 157.5D) return "behind";
        if (relative < -67.5D) return "left";
        if (relative > 67.5D) return "right";
        return relative < 0 ? "ahead-left" : "ahead-right";
    }
}
