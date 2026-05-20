package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class EctoPickaxeItem extends PickaxeItem {

    public EctoPickaxeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        EctoplasmInfusionHelper.setInfused(stack, true);
        return stack;
    }
}
