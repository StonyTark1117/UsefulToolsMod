package com.stonytark.usefultoolsmod.item.custom;

import com.stonytark.usefultoolsmod.entity.custom.GrenadeEntity;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class Grenade extends Item {
    public Grenade(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // Play the fuse sound
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.TNT_PRIMED, SoundSource.PLAYERS, 0.7F, 0.8F + level.random.nextFloat() * 0.2F);

        if (!level.isClientSide) {
            GrenadeEntity grenade = new GrenadeEntity(ModEntities.GRENADE.get(), level, player);
            grenade.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.1F, 1.0F);
            level.addFreshEntity(grenade);
        }

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
