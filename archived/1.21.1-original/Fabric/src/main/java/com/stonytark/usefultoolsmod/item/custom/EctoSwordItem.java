package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class EctoSwordItem extends SwordItem {

    public EctoSwordItem(ToolMaterial material, Item.Settings settings) {
        super(material, settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        EctoplasmInfusionHelper.setInfused(stack, true);
        return stack;
    }
}
