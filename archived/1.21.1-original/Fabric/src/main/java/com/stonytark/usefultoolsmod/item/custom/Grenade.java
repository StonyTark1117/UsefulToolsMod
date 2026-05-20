package com.stonytark.usefultoolsmod.item.custom;

import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.custom.GrenadeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Grenade extends Item {
    public Grenade(Settings properties) {
        super(properties);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.PLAYERS,
                0.7F, 0.8F + world.random.nextFloat() * 0.2F);

        if (!world.isClient) {
            GrenadeEntity grenade = new GrenadeEntity(ModEntities.GRENADE, world, player);
            grenade.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 1.1F, 1.0F);
            world.spawnEntity(grenade);
        }

        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }

        return TypedActionResult.success(stack, world.isClient());
    }
}
