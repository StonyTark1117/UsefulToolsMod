package com.stonytark.usefultoolsmod.item.custom;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.custom.StickyDynamiteEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class StickyDynamiteItem extends Item {
    public StickyDynamiteItem(Settings settings) { super(settings); }
    @Override public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!Config.explosivesEnabled || !Config.stickyDynamiteEnabled) return TypedActionResult.fail(stack);
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_TNT_PRIMED,
                SoundCategory.PLAYERS, 0.7F, 1.05F);
        if (!world.isClient) {
            StickyDynamiteEntity entity = new StickyDynamiteEntity(ModEntities.STICKY_DYNAMITE, world, player);
            entity.setVelocity(player, player.getPitch(), player.getYaw(), 0, 1.1F, 0.5F); world.spawnEntity(entity);
        }
        if (!player.getAbilities().creativeMode) stack.decrement(1);
        return TypedActionResult.success(stack, world.isClient);
    }
}
