package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Dynamite extends Item {
    private static final float EXPLOSION_RADIUS = 20.0F;

    public Dynamite(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // Play fuse sound
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.TNT_PRIMED, SoundSource.PLAYERS,
                0.7F, 0.8F + level.random.nextFloat() * 0.2F);

        // Spawn smoke particles (client-side only)
        if (level.isClientSide) {
            for (int i = 0; i < 150; i++) { // lots of smoke!
                double offsetX = (level.random.nextDouble() - 0.5) * 2.0;
                double offsetY = level.random.nextDouble() * 1.5;
                double offsetZ = (level.random.nextDouble() - 0.5) * 2.0;
                level.addParticle(
                        net.minecraft.core.particles.ParticleTypes.LARGE_SMOKE,
                        player.getX() + offsetX,
                        player.getY() + 1.0 + offsetY,
                        player.getZ() + offsetZ,
                        0.0, 0.0, 0.0
                );
            }
        }

        // Create explosion on server
        if (!level.isClientSide) {
            level.explode(null, player.getX(), player.getY(), player.getZ(),
                    20.0F, Level.ExplosionInteraction.TNT);
        }

        // Consume item unless in creative
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
