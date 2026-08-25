/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  cpw.mods.fml.common.eventhandler.SubscribeEvent
 *  cpw.mods.fml.common.gameevent.PlayerEvent$ItemPickupEvent
 *  net.minecraft.item.ItemStack
 *  net.minecraft.stats.StatBase
 */
package com.krazykid1117.achievements;

import com.krazykid1117.achievements.MAchievements;
import com.krazykid1117.blocks.MBlocks;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;

public class OnMineEvent {
    @SubscribeEvent
    public void whenIGetArgoldIngot(PlayerEvent.ItemPickupEvent e) {
        if (e.pickedUp.getEntityItem().isItemEqual(new ItemStack(MBlocks.rGoldore))) {
            e.player.addStat((StatBase)MAchievements.achievenrgold, 1);
        }
    }
}
