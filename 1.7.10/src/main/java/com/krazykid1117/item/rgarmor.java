/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemArmor$ArmorMaterial
 *  net.minecraft.item.ItemStack
 */
package com.krazykid1117.item;

import com.krazykid1117.item.MItems;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class rgarmor
extends ItemArmor {
    public rgarmor(ItemArmor.ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_) {
        super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (stack.getItem() == MItems.rghelm || stack.getItem() == MItems.rgchest || stack.getItem() == MItems.rgboots) {
            return "usefultoolsmod:textures/models/armor/rgarmor_1.png";
        }
        if (stack.getItem() == MItems.rgpants) {
            return "usefultoolsmod:textures/models/armor/rgarmor_2.png";
        }
        return null;
    }
}
