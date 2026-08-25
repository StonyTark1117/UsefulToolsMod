/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.GameRegistry
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 */
package com.krazykid1117.Main;

import com.krazykid1117.blocks.MBlocks;
import com.krazykid1117.item.MItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CraftingManager {
    public static void mainRegistry() {
        CraftingManager.addCraftingRec();
        CraftingManager.addSmeltingRec();
    }

    public static void addCraftingRec() {
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rGold, 1), (Object[])new Object[]{"XXX", "XCX", "XXX", Character.valueOf('C'), Items.gold_ingot, Character.valueOf('X'), MItems.INugget});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MBlocks.rGoldBlock, 1), (Object[])new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), MItems.rGold});
        GameRegistry.addRecipe((ItemStack)new ItemStack(Items.iron_ingot, 1), (Object[])new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), MItems.INugget});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MBlocks.rGoldore, 1), (Object[])new Object[]{"XXX", "XCX", "XBX", Character.valueOf('X'), Blocks.stone, Character.valueOf('C'), Items.gold_nugget, Character.valueOf('B'), MItems.INugget});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.emSword, 1), (Object[])new Object[]{" X ", " X ", " B ", Character.valueOf('X'), Items.emerald, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rGSword, 1), (Object[])new Object[]{" X ", " X ", " B ", Character.valueOf('X'), MItems.rGold, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rGHoe, 1), (Object[])new Object[]{"XX ", " B ", " B ", Character.valueOf('X'), MItems.rGold, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.emHoe, 1), (Object[])new Object[]{"XX ", " B ", " B ", Character.valueOf('X'), Items.emerald, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.emAxe, 1), (Object[])new Object[]{"XX ", "XB ", " B ", Character.valueOf('X'), Items.emerald, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rGAxe, 1), (Object[])new Object[]{"XX ", "XB ", " B ", Character.valueOf('X'), MItems.rGold, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.emPick, 1), (Object[])new Object[]{"XXX", " B ", " B ", Character.valueOf('X'), Items.emerald, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rGPick, 1), (Object[])new Object[]{"XXX", " B ", " B ", Character.valueOf('X'), MItems.rGold, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.emShovel, 1), (Object[])new Object[]{" X ", " B ", " B ", Character.valueOf('X'), Items.emerald, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rGShovel, 1), (Object[])new Object[]{" X ", " B ", " B ", Character.valueOf('X'), MItems.rGold, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(Blocks.obsidian, 1), (Object[])new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), MItems.ObShard});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.sEm, 1), (Object[])new Object[]{" X ", "XEX", " X ", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('E'), Items.emerald});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.semSword, 1), (Object[])new Object[]{" X ", " X ", " B ", Character.valueOf('X'), MItems.sEm, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.semHoe, 1), (Object[])new Object[]{"XX ", " B ", " B ", Character.valueOf('X'), MItems.sEm, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.semAxe, 1), (Object[])new Object[]{"XX ", "XB ", " B ", Character.valueOf('X'), MItems.sEm, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.semPick, 1), (Object[])new Object[]{"XXX", " B ", " B ", Character.valueOf('X'), MItems.sEm, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.semShovel, 1), (Object[])new Object[]{" X ", " B ", " B ", Character.valueOf('X'), MItems.sEm, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.joSword, 1), (Object[])new Object[]{" X ", " X ", " B ", Character.valueOf('X'), MItems.ObShard, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.joHoe, 1), (Object[])new Object[]{"XX ", " B ", " B ", Character.valueOf('X'), MItems.ObShard, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.joAxe, 1), (Object[])new Object[]{" XX", " BX", " B ", Character.valueOf('X'), MItems.ObShard, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.joPick, 1), (Object[])new Object[]{"XXX", " B ", " B ", Character.valueOf('X'), MItems.ObShard, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.joShovel, 1), (Object[])new Object[]{" X ", " B ", " B ", Character.valueOf('X'), MItems.ObShard, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MBlocks.semBlock, 1), (Object[])new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), MItems.sEm});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.ObIngot, 1), (Object[])new Object[]{" X ", "XEX", " X ", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('E'), MItems.ObShard});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.oSword, 1), (Object[])new Object[]{" X ", " X ", " B ", Character.valueOf('X'), MItems.ObIngot, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.oHoe, 1), (Object[])new Object[]{"XX ", " B ", " B ", Character.valueOf('X'), MItems.ObIngot, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.oAxe, 1), (Object[])new Object[]{" XX", " BX", " B ", Character.valueOf('X'), MItems.ObIngot, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.oPick, 1), (Object[])new Object[]{"XXX", " B ", " B ", Character.valueOf('X'), MItems.ObIngot, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.oShovel, 1), (Object[])new Object[]{" X ", " B ", " B ", Character.valueOf('X'), MItems.ObIngot, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MBlocks.sOblock, 1), (Object[])new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), MItems.ObIngot});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rgboots, 1), (Object[])new Object[]{"X X", "X X", "   ", Character.valueOf('X'), MItems.rGold});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rgboots, 1), (Object[])new Object[]{"   ", "X X", "X X", Character.valueOf('X'), MItems.rGold});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rghelm, 1), (Object[])new Object[]{"XXX", "X X", "   ", Character.valueOf('X'), MItems.rGold});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rghelm, 1), (Object[])new Object[]{"   ", "XXX", "X X", Character.valueOf('X'), MItems.rGold});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rgchest, 1), (Object[])new Object[]{"X X", "XXX", "XXX", Character.valueOf('X'), MItems.rGold});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rgpants, 1), (Object[])new Object[]{"XXX", "X X", "X X", Character.valueOf('X'), MItems.rGold});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.obboots, 1), (Object[])new Object[]{"X X", "X X", "   ", Character.valueOf('X'), MItems.ObIngot});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.obboots, 1), (Object[])new Object[]{"   ", "X X", "X X", Character.valueOf('X'), MItems.ObIngot});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.obhelm, 1), (Object[])new Object[]{"XXX", "X X", "   ", Character.valueOf('X'), MItems.ObIngot});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.obhelm, 1), (Object[])new Object[]{"   ", "XXX", "X X", Character.valueOf('X'), MItems.ObIngot});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.obchest, 1), (Object[])new Object[]{"X X", "XXX", "XXX", Character.valueOf('X'), MItems.ObIngot});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.obpants, 1), (Object[])new Object[]{"XXX", "X X", "X X", Character.valueOf('X'), MItems.ObIngot});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.opaxe, 1), (Object[])new Object[]{"XXO", "YIY", "KI ", Character.valueOf('X'), Blocks.diamond_block, Character.valueOf('O'), MItems.sEm, Character.valueOf('I'), Items.stick, Character.valueOf('K'), MBlocks.sOblock, Character.valueOf('Y'), MItems.rGold});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.oppick, 1), (Object[])new Object[]{"XXX", "YIO", "KI ", Character.valueOf('X'), Blocks.diamond_block, Character.valueOf('O'), MItems.sEm, Character.valueOf('I'), Items.stick, Character.valueOf('K'), MBlocks.sOblock, Character.valueOf('Y'), MItems.rGold});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.opshovel, 1), (Object[])new Object[]{"OXO", "YIY", "KI ", Character.valueOf('X'), Blocks.diamond_block, Character.valueOf('O'), MItems.sEm, Character.valueOf('I'), Items.stick, Character.valueOf('K'), MBlocks.sOblock, Character.valueOf('Y'), MItems.rGold});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.opsword, 1), (Object[])new Object[]{"OXO", "YXY", "KI ", Character.valueOf('X'), Blocks.diamond_block, Character.valueOf('O'), MItems.sEm, Character.valueOf('I'), Items.stick, Character.valueOf('K'), MBlocks.sOblock, Character.valueOf('Y'), MItems.rGold});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.Grenade, 1), (Object[])new Object[]{"XOX", "XYX", "XZX", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('O'), Items.redstone, Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), Items.paper});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.Dynamite, 1), (Object[])new Object[]{" S ", "XSX", " Z ", Character.valueOf('X'), MItems.Grenade, Character.valueOf('S'), Items.string, Character.valueOf('Z'), Items.paper});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.opboots, 1), (Object[])new Object[]{"XOX", "XZX", " Y ", Character.valueOf('X'), Blocks.diamond_block, Character.valueOf('O'), MItems.sEm, Character.valueOf('Z'), MItems.ObIngot, Character.valueOf('Y'), MItems.rGold});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.opchest, 1), (Object[])new Object[]{"XOX", "XZX", "XYZ", Character.valueOf('X'), Blocks.diamond_block, Character.valueOf('O'), MItems.sEm, Character.valueOf('Z'), MItems.ObIngot, Character.valueOf('Y'), MItems.rGold});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.ophelm, 1), (Object[])new Object[]{"XXX", "XZX", "OYO", Character.valueOf('X'), Blocks.diamond_block, Character.valueOf('O'), MItems.sEm, Character.valueOf('Z'), MItems.ObIngot, Character.valueOf('Y'), MItems.rGold});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.oppants, 1), (Object[])new Object[]{"XXX", "XYX", "XOX", Character.valueOf('X'), Blocks.diamond_block, Character.valueOf('O'), MItems.sEm, Character.valueOf('Y'), MItems.ObIngot});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.emboots, 1), (Object[])new Object[]{"X X", "X X", "   ", Character.valueOf('X'), MItems.sEm});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.emboots, 1), (Object[])new Object[]{"   ", "X X", "X X", Character.valueOf('X'), MItems.sEm});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.emhelm, 1), (Object[])new Object[]{"XXX", "X X", "   ", Character.valueOf('X'), MItems.sEm});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.emhelm, 1), (Object[])new Object[]{"   ", "XXX", "X X", Character.valueOf('X'), MItems.sEm});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.emchest, 1), (Object[])new Object[]{"X X", "XXX", "XXX", Character.valueOf('X'), MItems.sEm});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.empants, 1), (Object[])new Object[]{"XXX", "X X", "X X", Character.valueOf('X'), MItems.sEm});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.hred, 1), (Object[])new Object[]{" X ", "XOX", " X ", Character.valueOf('X'), Items.clay_ball, Character.valueOf('O'), Items.redstone});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rSword, 1), (Object[])new Object[]{" X ", " X ", " B ", Character.valueOf('X'), MItems.hred, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rHoe, 1), (Object[])new Object[]{"XX ", " B ", " B ", Character.valueOf('X'), MItems.hred, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rAxe, 1), (Object[])new Object[]{" XX", " BX", " B ", Character.valueOf('X'), MItems.hred, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rPick, 1), (Object[])new Object[]{"XXX", " B ", " B ", Character.valueOf('X'), MItems.hred, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.rShovel, 1), (Object[])new Object[]{" X ", " B ", " B ", Character.valueOf('X'), MItems.hred, Character.valueOf('B'), Items.stick});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MBlocks.hrblock, 1), (Object[])new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), MItems.hred});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.hrchest, 1), (Object[])new Object[]{"X X", "XXX", "XXX", Character.valueOf('X'), MItems.hred});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.hrpants, 1), (Object[])new Object[]{"XXX", "X X", "X X", Character.valueOf('X'), MItems.hred});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.hrboots, 1), (Object[])new Object[]{"X X", "X X", "   ", Character.valueOf('X'), MItems.hred});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.hrboots, 1), (Object[])new Object[]{"   ", "X X", "X X", Character.valueOf('X'), MItems.hred});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.hrhelm, 1), (Object[])new Object[]{"XXX", "X X", "   ", Character.valueOf('X'), MItems.hred});
        GameRegistry.addRecipe((ItemStack)new ItemStack(MItems.hrhelm, 1), (Object[])new Object[]{"   ", "XXX", "X X", Character.valueOf('X'), MItems.hred});
        GameRegistry.addShapelessRecipe((ItemStack)new ItemStack(MItems.hred, 9), (Object[])new Object[]{new ItemStack(MBlocks.hrblock)});
        GameRegistry.addShapelessRecipe((ItemStack)new ItemStack(MItems.ObIngot, 9), (Object[])new Object[]{new ItemStack(MBlocks.sOblock)});
        GameRegistry.addShapelessRecipe((ItemStack)new ItemStack(MItems.sEm, 9), (Object[])new Object[]{new ItemStack(MBlocks.semBlock)});
        GameRegistry.addShapelessRecipe((ItemStack)new ItemStack(MItems.ObShard, 9), (Object[])new Object[]{new ItemStack(Blocks.obsidian)});
        GameRegistry.addShapelessRecipe((ItemStack)new ItemStack(MItems.INugget, 9), (Object[])new Object[]{new ItemStack(Items.iron_ingot)});
        GameRegistry.addShapelessRecipe((ItemStack)new ItemStack(MItems.rGold, 9), (Object[])new Object[]{new ItemStack(MBlocks.rGoldBlock)});
    }

    public static void addSmeltingRec() {
        GameRegistry.addSmelting((Block)MBlocks.rGoldBlock, (ItemStack)new ItemStack(Items.gold_ingot, 9), (float)20.0f);
        GameRegistry.addSmelting((Block)MBlocks.rGoldore, (ItemStack)new ItemStack(MItems.rGold, 1), (float)20.0f);
        GameRegistry.addSmelting((Block)Blocks.obsidian, (ItemStack)new ItemStack(MItems.ObShard, 1), (float)30.0f);
    }
}
