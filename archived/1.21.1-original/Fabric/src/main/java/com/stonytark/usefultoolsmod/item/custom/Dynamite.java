package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class Dynamite extends Item {
    private static final float EXPLOSION_RADIUS = 20.0F;

    public Dynamite(Settings properties) {
        super(properties);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.PLAYERS,
                0.7F, 0.8F + world.random.nextFloat() * 0.2F);

        if (world.isClient) {
            for (int i = 0; i < 150; i++) {
                double offsetX = (world.random.nextDouble() - 0.5) * 2.0;
                double offsetY = world.random.nextDouble() * 1.5;
                double offsetZ = (world.random.nextDouble() - 0.5) * 2.0;
                world.addParticle(
                        ParticleTypes.LARGE_SMOKE,
                        player.getX() + offsetX,
                        player.getY() + 1.0 + offsetY,
                        player.getZ() + offsetZ,
                        0.0, 0.0, 0.0
                );
            }
        }

        if (!world.isClient) {
            world.createExplosion(null, player.getX(), player.getY(), player.getZ(),
                    EXPLOSION_RADIUS, World.ExplosionSourceType.TNT);
        }

        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }

        return TypedActionResult.success(stack, world.isClient());
    }
}
