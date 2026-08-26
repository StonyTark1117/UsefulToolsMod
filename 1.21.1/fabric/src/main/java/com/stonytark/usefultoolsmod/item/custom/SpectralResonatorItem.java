package com.stonytark.usefultoolsmod.item.custom;

import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Comparator;

public class SpectralResonatorItem extends Item {
    private static final double RANGE = 96.0D;
    public SpectralResonatorItem(Settings settings) { super(settings); }

    @Override public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!com.stonytark.usefultoolsmod.Config.spectralResonatorEnabled)
            return TypedActionResult.pass(stack);
        LivingEntity nearest = world.getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(RANGE),
                e -> e instanceof GhostEntity || e instanceof WraithEntity).stream()
                .min(Comparator.comparingDouble(player::squaredDistanceTo)).orElse(null);
        if (!world.isClient) {
            if (nearest != null && player instanceof net.minecraft.server.network.ServerPlayerEntity serverPlayer)
                com.stonytark.usefultoolsmod.util.ModAdvancements.award(serverPlayer, "spectral/locate_spirit");
            if (nearest == null) player.sendMessage(Text.translatable("message.usefultoolsmod.resonator.none"), true);
            else {
                Vec3d delta = nearest.getPos().subtract(player.getPos());
                player.sendMessage(Text.translatable("message.usefultoolsmod.resonator.found", nearest.getDisplayName(),
                        direction(delta, player.getYaw()), (int)Math.round(Math.sqrt(player.squaredDistanceTo(nearest)))), true);
            }
            stack.damage(1, player, hand == Hand.MAIN_HAND
                    ? net.minecraft.entity.EquipmentSlot.MAINHAND : net.minecraft.entity.EquipmentSlot.OFFHAND);
            player.getItemCooldownManager().set(this, 200);
        } else if (nearest != null && com.stonytark.usefultoolsmod.client.SpectralClientConfig.particlesEnabled) {
            Vec3d step = nearest.getPos().subtract(player.getEyePos()).normalize().multiply(0.65D);
            Vec3d point = player.getEyePos();
            for (int i = 0; i < 12; i++) { point = point.add(step); world.addParticle(
                    net.minecraft.particle.ParticleTypes.SOUL_FIRE_FLAME, point.x, point.y, point.z, 0, 0, 0); }
        }
        return TypedActionResult.success(stack, world.isClient);
    }

    private static String direction(Vec3d delta, float yaw) {
        double targetYaw = Math.toDegrees(Math.atan2(-delta.x, delta.z));
        double relative = (targetYaw - yaw + 540.0D) % 360.0D - 180.0D;
        if (Math.abs(relative) < 22.5D) return "ahead";
        if (Math.abs(relative) > 157.5D) return "behind";
        if (relative < -67.5D) return "left";
        if (relative > 67.5D) return "right";
        return relative < 0 ? "ahead-left" : "ahead-right";
    }
}
