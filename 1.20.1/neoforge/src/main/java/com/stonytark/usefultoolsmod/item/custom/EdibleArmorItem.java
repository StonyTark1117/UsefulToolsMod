package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class EdibleArmorItem extends ArmorItem {

    public EdibleArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        FoodProperties food = stack.getFoodProperties(player);
        if (food != null && player.canEat(food.canAlwaysEat())) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }
        return super.use(level, player, hand);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        FoodProperties food = stack.getFoodProperties(null);
        if (food != null) {
            return food.isFastFood() ? 16 : 32;
        }
        return super.getUseDuration(stack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        if (stack.getFoodProperties(null) != null) {
            return UseAnim.EAT;
        }
        return super.getUseAnimation(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            FoodProperties food = stack.getFoodProperties(player);
            if (food != null) {
                player.getFoodData().eat(stack.getItem(), stack, player);
            }
        }
        stack.shrink(1);
        return stack;
    }
}
