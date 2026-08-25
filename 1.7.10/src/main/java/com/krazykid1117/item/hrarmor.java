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

public class hrarmor
extends ItemArmor {
    public hrarmor(ItemArmor.ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_) {
        super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (stack.getItem() == MItems.hrhelm || stack.getItem() == MItems.hrchest || stack.getItem() == MItems.hrboots) {
            return "usefultoolsmod:textures/models/armor/harmor_1.png";
        }
        if (stack.getItem() == MItems.hrpants) {
            return "usefultoolsmod:textures/models/armor/harmor_2.png";
        }
        return null;
    }
}
