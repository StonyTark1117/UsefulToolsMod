/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemSpade
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.world.World
 */
package com.krazykid1117.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class opshovel
extends ItemSpade {
    public opshovel(Item.ToolMaterial p_i45353_1_) {
        super(p_i45353_1_);
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
        super.onUpdate(stack, world, entity, par4, par5);
        EntityPlayer player = (EntityPlayer)entity;
        ItemStack equipped = player.getCurrentEquippedItem();
        if (equipped == stack) {
            player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 5, 3));
            player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 5, 1));
            player.addPotionEffect(new PotionEffect(Potion.jump.id, 5, 5));
        }
    }
}
