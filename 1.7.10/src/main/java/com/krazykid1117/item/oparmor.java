/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemArmor$ArmorMaterial
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.world.World
 */
package com.krazykid1117.item;

import com.krazykid1117.item.MItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class oparmor
extends ItemArmor {
    public oparmor(ItemArmor.ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_) {
        super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (stack.getItem() == MItems.ophelm || stack.getItem() == MItems.opchest || stack.getItem() == MItems.opboots) {
            return "usefultoolsmod:textures/models/armor/oparmor_1.png";
        }
        if (stack.getItem() == MItems.oppants) {
            return "usefultoolsmod:textures/models/armor/oparmor_2.png";
        }
        return null;
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (itemStack.getItem().equals(MItems.opboots)) {
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 40, 2));
        }
        if (itemStack.getItem().equals(MItems.oppants)) {
            player.addPotionEffect(new PotionEffect(Potion.jump.id, 40, 2));
        }
        if (itemStack.getItem().equals(MItems.opchest)) {
            player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 40, 1));
        }
        if (itemStack.getItem().equals(MItems.ophelm)) {
            player.addPotionEffect(new PotionEffect(Potion.resistance.id, 40));
        }
    }
}
