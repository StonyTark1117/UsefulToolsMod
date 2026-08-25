package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;

public class EdibleHoeItem extends HoeItem {
    public EdibleHoeItem(ToolMaterial material, Item.Settings settings) {
        super(material, settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack result = super.finishUsing(stack, world, user);
        result.decrement(1);
        return result;
    }
}
