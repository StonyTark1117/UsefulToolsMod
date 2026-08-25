package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;

public class CakeArmorItem extends EdibleArmorItem {
    public CakeArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Item.Settings settings) {
        super(material, type, settings);
    }
}
