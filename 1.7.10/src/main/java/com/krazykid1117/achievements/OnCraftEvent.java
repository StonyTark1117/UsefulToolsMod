/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  cpw.mods.fml.common.eventhandler.SubscribeEvent
 *  cpw.mods.fml.common.gameevent.PlayerEvent$ItemCraftedEvent
 *  net.minecraft.stats.StatBase
 */
package com.krazykid1117.achievements;

import com.krazykid1117.achievements.MAchievements;
import com.krazykid1117.item.MItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.stats.StatBase;

public class OnCraftEvent {
    @SubscribeEvent
    public void whenICraftSmoothEmerald(PlayerEvent.ItemCraftedEvent e) {
        if (e.crafting.getItem().equals(MItems.sEm)) {
            e.player.addStat((StatBase)MAchievements.achievesem, 1);
        }
    }

    @SubscribeEvent
    public void whenICraftrGold(PlayerEvent.ItemCraftedEvent c) {
        if (c.crafting.getItem().equals(MItems.rGold)) {
            c.player.addStat((StatBase)MAchievements.achievergold, 1);
        }
    }

    @SubscribeEvent
    public void whenICrafthred(PlayerEvent.ItemCraftedEvent r) {
        if (r.crafting.getItem().equals(MItems.hred)) {
            r.player.addStat((StatBase)MAchievements.achievehred, 1);
        }
    }

    @SubscribeEvent
    public void whenICraftrpick(PlayerEvent.ItemCraftedEvent p) {
        if (p.crafting.getItem().equals(MItems.rPick)) {
            p.player.addStat((StatBase)MAchievements.achieverpick, 1);
        }
    }

    @SubscribeEvent
    public void whenICraftempick(PlayerEvent.ItemCraftedEvent z) {
        if (z.crafting.getItem().equals(MItems.semPick)) {
            z.player.addStat((StatBase)MAchievements.achieveempick, 1);
        }
    }

    @SubscribeEvent
    public void whenICraftoppick(PlayerEvent.ItemCraftedEvent op) {
        if (op.crafting.getItem().equals(MItems.opsword)) {
            op.player.addStat((StatBase)MAchievements.achieveop, 1);
        }
    }

    @SubscribeEvent
    public void whenICraftobingot(PlayerEvent.ItemCraftedEvent o) {
        if (o.crafting.getItem().equals(MItems.ObIngot)) {
            o.player.addStat((StatBase)MAchievements.achieveobi, 1);
        }
    }

    @SubscribeEvent
    public void whenICraftopick(PlayerEvent.ItemCraftedEvent ob) {
        if (ob.crafting.getItem().equals(MItems.oPick)) {
            ob.player.addStat((StatBase)MAchievements.achieveopick, 1);
        }
    }
}
