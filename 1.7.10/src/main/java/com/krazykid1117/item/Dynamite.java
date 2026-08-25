/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package com.krazykid1117.item;

import com.stonytark.usefultoolsmod.classic.ClassicGeneratedConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Dynamite
extends Item {
    private static final float EXPLOSION_RADIUS = 20.0f;

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        if (!ClassicGeneratedConfig.booleanValue("explosivesEnabled")) return itemstack;
        if (!player.capabilities.isCreativeMode) {
            --itemstack.stackSize;
        }
        world.playSoundAtEntity((Entity)player, "random.fuse", 0.7f, 0.8f);
        if (!world.isRemote) {
            player.worldObj.createExplosion((Entity)null, player.posX, player.posY, player.posZ,
                    EXPLOSION_RADIUS, true);
        }
        return itemstack;
    }
}
