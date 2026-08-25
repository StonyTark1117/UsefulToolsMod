/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.GameRegistry
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 */
package com.krazykid1117.blocks;

import com.krazykid1117.CreativeTabs.MCreativeTabs;
import com.krazykid1117.blocks.hrBlock;
import com.krazykid1117.blocks.rGoldBlock;
import com.krazykid1117.blocks.rGoldore;
import com.krazykid1117.blocks.sOblock;
import com.krazykid1117.blocks.semBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class MBlocks {
    public static Block rGoldBlock;
    public static Block semBlock;
    public static Block sOblock;
    public static Block hrblock;
    public static Block rGoldore;

    public static void MainRegistry() {
        MBlocks.initializeBlock();
        MBlocks.initializeBlock1();
        MBlocks.registerItem();
        MBlocks.registerItem1();
    }

    public static void initializeBlock() {
        rGoldBlock = new rGoldBlock(Material.iron).setBlockName("rGoldBlock").setCreativeTab(MCreativeTabs.tabToolsMod).setBlockTextureName("usefultoolsmod:rGoldBlock");
        semBlock = new semBlock(Material.iron).setBlockName("semBlock").setCreativeTab(MCreativeTabs.tabToolsMod).setBlockTextureName("usefultoolsmod:semBlock");
        sOblock = new sOblock(Material.rock).setBlockName("sOblock").setCreativeTab(MCreativeTabs.tabToolsMod).setBlockTextureName("usefultoolsmod:sOblock");
        hrblock = new hrBlock(Material.clay).setBlockName("hrblock").setCreativeTab(MCreativeTabs.tabToolsMod).setBlockTextureName("usefultoolsmod:hrblock");
    }

    public static void registerItem() {
        GameRegistry.registerBlock((Block)rGoldBlock, (String)rGoldBlock.getUnlocalizedName());
        GameRegistry.registerBlock((Block)semBlock, (String)semBlock.getUnlocalizedName());
        GameRegistry.registerBlock((Block)sOblock, (String)sOblock.getUnlocalizedName());
        GameRegistry.registerBlock((Block)hrblock, (String)hrblock.getUnlocalizedName());
    }

    public static void initializeBlock1() {
        rGoldore = new rGoldore(Material.ground).setBlockName("rGoldore").setCreativeTab(MCreativeTabs.tabToolsMod).setBlockTextureName("usefultoolsmod:rGoldOre");
    }

    public static void registerItem1() {
        GameRegistry.registerBlock((Block)rGoldore, (String)rGoldore.getUnlocalizedName());
    }
}
