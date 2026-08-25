/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.creativetab.CreativeTabs
 */
package com.krazykid1117.CreativeTabs;

import com.krazykid1117.CreativeTabs.CreativeTabToolsMod;
import net.minecraft.creativetab.CreativeTabs;

public class MCreativeTabs {
    public static CreativeTabs tabToolsMod;

    public static void initialiseTabs() {
        tabToolsMod = new CreativeTabToolsMod("BlockTab");
    }
}
