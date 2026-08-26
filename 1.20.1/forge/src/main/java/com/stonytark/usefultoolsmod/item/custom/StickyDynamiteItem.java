package com.stonytark.usefultoolsmod.item.custom;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.custom.StickyDynamiteEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class StickyDynamiteItem extends Item {
    public StickyDynamiteItem(Properties properties) { super(properties); }

    @Override public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!Config.explosivesEnabled || !Config.stickyDynamiteEnabled) return InteractionResultHolder.fail(stack);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TNT_PRIMED,
                SoundSource.PLAYERS, 0.7F, 1.05F);
        if (!level.isClientSide) {
            StickyDynamiteEntity entity = new StickyDynamiteEntity(ModEntities.STICKY_DYNAMITE.get(), level, player);
            entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.1F, 0.5F);
            level.addFreshEntity(entity);
        }
        if (!player.getAbilities().instabuild) stack.shrink(1);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
