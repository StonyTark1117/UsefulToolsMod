package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class CoalPickaxeItem extends PickaxeItem {

    public CoalPickaxeItem(ToolMaterial material, Item.Settings settings) {
        super(material, settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return CoalBurningHelper.isBurning(stack) || super.hasGlint(stack);
    }
}
