/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 */
package com.krazykid1117.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class rGoldore
extends Block {
    protected rGoldore(Material p_i45394_1_) {
        super(p_i45394_1_);
        this.setHarvestLevel("pickaxe", 2);
        this.setHardness(4.5f);
        this.setStepSound(soundTypeStone);
    }
}
