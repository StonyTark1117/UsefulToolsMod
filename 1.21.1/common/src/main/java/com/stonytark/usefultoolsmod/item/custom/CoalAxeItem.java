package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class CoalAxeItem extends AxeItem {

    public CoalAxeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return CoalBurningHelper.isBurning(stack) || super.isFoil(stack);
    }
}
