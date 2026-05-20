package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class EctoHoeItem extends HoeItem {

    public EctoHoeItem(ToolMaterial material, Item.Settings settings) {
        super(material, settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        EctoplasmInfusionHelper.setInfused(stack, true);
        return stack;
    }
}
