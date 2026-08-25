/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.item.Item
 */
package com.krazykid1117.CreativeTabs;

import com.krazykid1117.blocks.MBlocks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabToolsMod
extends CreativeTabs {
    public CreativeTabToolsMod(String lable) {
        super(lable);
    }

    public Item getTabIconItem() {
        return Item.getItemFromBlock((Block)MBlocks.rGoldBlock);
    }
}
