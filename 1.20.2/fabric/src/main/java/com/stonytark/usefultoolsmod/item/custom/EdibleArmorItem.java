package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class EdibleArmorItem extends ArmorItem {

    public EdibleArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        FoodComponent food = stack.getItem().getFoodComponent();
        if (food != null && player.canConsume(food.isAlwaysEdible())) {
            player.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        }
        return super.use(world, player, hand);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        FoodComponent food = stack.getItem().getFoodComponent();
        if (food != null) {
            return food.isSnack() ? 16 : 32;
        }
        return super.getMaxUseTime(stack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        if (stack.getItem().getFoodComponent() != null) {
            return UseAction.EAT;
        }
        return super.getUseAction(stack);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            FoodComponent food = stack.getItem().getFoodComponent();
            if (food != null) {
                player.getHungerManager().eat(stack.getItem(), stack);
            }
        }
        stack.decrement(1);
        return stack;
    }
}
