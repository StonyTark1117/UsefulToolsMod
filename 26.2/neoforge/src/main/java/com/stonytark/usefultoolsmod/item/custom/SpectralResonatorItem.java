package com.stonytark.usefultoolsmod.item.custom;

import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;

public class SpectralResonatorItem extends Item {
    private static final double RANGE = 96;
    public SpectralResonatorItem(Properties properties) { super(properties); }
    @Override public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!com.stonytark.usefultoolsmod.Config.spectralResonatorEnabled) return InteractionResult.PASS;
        if (!level.isClientSide()) {
            LivingEntity nearest = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(RANGE),
                    e -> e instanceof GhostEntity || e instanceof WraithEntity).stream()
                    .min(Comparator.comparingDouble(player::distanceToSqr)).orElse(null);
            if (nearest != null && player instanceof ServerPlayer serverPlayer)
                com.stonytark.usefultoolsmod.util.ModAdvancements.award(serverPlayer, "spectral/locate_spirit");
            if (nearest == null && player instanceof ServerPlayer serverPlayer) serverPlayer.connection.send(
                    new ClientboundSetActionBarTextPacket(Component.translatable("message.usefultoolsmod.resonator.none")));
            else { Vec3 delta = nearest.position().subtract(player.position());
                if (player instanceof ServerPlayer serverPlayer) serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(
                        Component.translatable("message.usefultoolsmod.resonator.found", nearest.getDisplayName(),
                        direction(delta, player.getYRot()), (int)Math.round(Math.sqrt(player.distanceToSqr(nearest)))))); }
            stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            player.getCooldowns().addCooldown(stack, 200);
        } else if (com.stonytark.usefultoolsmod.client.SpectralClientConfig.particlesEnabled) {
            LivingEntity nearest = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(RANGE),
                    e -> e instanceof GhostEntity || e instanceof WraithEntity).stream()
                    .min(Comparator.comparingDouble(player::distanceToSqr)).orElse(null);
            if (nearest != null) {
                Vec3 point = player.getEyePosition();
                Vec3 step = nearest.position().subtract(point).normalize().scale(.65D);
                for (int i = 0; i < 12; i++) {
                    point = point.add(step);
                    level.addParticle(net.minecraft.core.particles.ParticleTypes.SOUL_FIRE_FLAME,
                            point.x, point.y, point.z, 0, 0, 0);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
    private static String direction(Vec3 delta, float yaw) {
        double targetYaw = Math.toDegrees(Math.atan2(-delta.x, delta.z)); double relative = (targetYaw - yaw + 540) % 360 - 180;
        if (Math.abs(relative) < 22.5) return "ahead"; if (Math.abs(relative) > 157.5) return "behind";
        if (relative < -67.5) return "left"; if (relative > 67.5) return "right"; return relative < 0 ? "ahead-left" : "ahead-right";
    }
}
