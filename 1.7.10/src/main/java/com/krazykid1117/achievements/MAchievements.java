/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.stats.Achievement
 *  net.minecraftforge.common.AchievementPage
 */
package com.krazykid1117.achievements;

import com.krazykid1117.blocks.MBlocks;
import com.krazykid1117.item.MItems;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class MAchievements {
    public static Achievement achievergold;
    public static Achievement achievesem;
    public static Achievement achievenrgold;
    public static Achievement achievehred;
    public static Achievement achieverpick;
    public static Achievement achieveempick;
    public static Achievement achieveop;
    public static Achievement achieveobi;
    public static Achievement achieveopick;

    public static void MainRegistry() {
        MAchievements.CreateAchievement();
        MAchievements.registerAchievementPage();
    }

    public static void CreateAchievement() {
        achievergold = new Achievement("achievement.craftrgold", "craftrgold", 0, 0, MItems.rGold, (Achievement)null).initIndependentStat().registerStat();
        achievesem = new Achievement("achievement.craftsem", "craftsem", 2, 1, MItems.sEm, (Achievement)null).initIndependentStat().registerStat();
        achievenrgold = new Achievement("achievement.minenrgold", "minenrgold", 3, 2, new ItemStack(MBlocks.rGoldore), (Achievement)null).initIndependentStat().registerStat();
        achievehred = new Achievement("achievement.crafthred", "crafthred", 4, 3, MItems.hred, (Achievement)null).initIndependentStat().registerStat();
        achieverpick = new Achievement("achievement.craftrpick", "craftrpick", 5, 4, MItems.rPick, achievehred).initIndependentStat().registerStat();
        achieveempick = new Achievement("achievement.craftempick", "craftempick", 6, 5, MItems.semPick, achievesem).initIndependentStat().registerStat();
        achieveop = new Achievement("achievement.craftopsword", "craftopsword", 7, 6, MItems.opsword, (Achievement)null).initIndependentStat().registerStat();
        achieveobi = new Achievement("achievement.craftobingot", "craftobingot", 8, 7, MItems.ObIngot, (Achievement)null).initIndependentStat().registerStat();
        achieveopick = new Achievement("achievement.craftopick", "craftopick", 9, 8, MItems.oPick, achieveobi).initIndependentStat().registerStat();
    }

    public static void registerAchievementPage() {
        AchievementPage.registerAchievementPage((AchievementPage)new AchievementPage("MAchievements", new Achievement[]{achievesem, achievergold, achievenrgold, achievehred, achieverpick, achieveempick, achieveop, achieveobi, achieveopick}));
    }
}
