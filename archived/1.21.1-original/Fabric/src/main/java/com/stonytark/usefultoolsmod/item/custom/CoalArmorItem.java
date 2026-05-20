package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;

public class CoalArmorItem extends ArmorItem {

    public CoalArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Item.Settings settings) {
        super(material, type, settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return CoalBurningHelper.isBurning(stack) || super.hasGlint(stack);
    }
}
