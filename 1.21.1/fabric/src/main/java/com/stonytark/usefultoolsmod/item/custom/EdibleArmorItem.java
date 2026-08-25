package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class EdibleArmorItem extends ArmorItem {

    public EdibleArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Item.Settings settings) {
        super(material, type, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        FoodComponent food = stack.get(DataComponentTypes.FOOD);
        if (food != null && player.canConsume(food.canAlwaysEat())) {
            player.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        }
        return super.use(world, player, hand);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        FoodComponent food = stack.get(DataComponentTypes.FOOD);
        if (food != null) {
            return food.getEatTicks();
        }
        return super.getMaxUseTime(stack, user);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        if (stack.get(DataComponentTypes.FOOD) != null) {
            return UseAction.EAT;
        }
        return super.getUseAction(stack);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        FoodComponent food = stack.get(DataComponentTypes.FOOD);
        if (food != null) {
            // eatFood handles sound, effects, hunger, and decrements the stack
            return user.eatFood(world, stack, food);
        }
        stack.decrement(1);
        return stack;
    }
}
