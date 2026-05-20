package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class EctoHoeItem extends HoeItem {

    public EctoHoeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        EctoplasmInfusionHelper.setInfused(stack, true);
        return stack;
    }
}
