package com.stonytark.usefultoolsmod.block;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.custom.SpectralInfuserBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {
    public static Block RGOLDBLOCK;
    public static Block HRBLOCK;
    public static Block RGOLDORE;
    public static Block RGOLD_NETHER_ORE;
    public static Block RGOLD_END_ORE;
    public static Block RGOLD_DEEPSLATE_ORE;
    public static Block SEMBLOCK;
    public static Block SOBLOCK;
    public static Block LBLOCK;
    public static Block HGLOW_BLOCK;
    public static Block RAW_RGOLD_BLOCK;
    public static Block ECTOPLASM_BLOCK;
    public static Block REFINED_ECTOPLASM_BLOCK;
    public static Block HARDENED_COAL_BLOCK;
    public static Block COAL_DUST_BLOCK;
    public static Block OBSHARD_BLOCK;
    public static Block CALCIFIED_AMETHYST_BLOCK;
    public static Block GLACIAL_SHARD_BLOCK;
    public static Block POLISHED_QUARTZ_BLOCK;
    public static Block POLISHED_PRISMARINE_BLOCK;
    public static Block SPECTRAL_INFUSER;

    public static void register() {
        RGOLDBLOCK = registerBlock("rgoldblock",
                new Block(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.METAL)));
        HRBLOCK = registerBlock("hrblock",
                new Block(AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.NETHER_BRICKS)));
        RGOLDORE = registerBlock("rgoldore",
                new ExperienceDroppingBlock(UniformIntProvider.create(2, 4),
                        AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.STONE)));
        RGOLD_NETHER_ORE = registerBlock("rgold_nether_ore",
                new ExperienceDroppingBlock(UniformIntProvider.create(2, 4),
                        AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.NETHER_ORE)));
        RGOLD_END_ORE = registerBlock("rgold_end_ore",
                new ExperienceDroppingBlock(UniformIntProvider.create(2, 4),
                        AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.ROOTED_DIRT)));
        RGOLD_DEEPSLATE_ORE = registerBlock("rgold_deepslate_ore",
                new ExperienceDroppingBlock(UniformIntProvider.create(2, 4),
                        AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE)));
        SEMBLOCK = registerBlock("semblock",
                new Block(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.AMETHYST_BLOCK)));
        SOBLOCK = registerBlock("soblock",
                new Block(AbstractBlock.Settings.create().strength(5f).requiresTool().sounds(BlockSoundGroup.AMETHYST_BLOCK)));
        LBLOCK = registerBlock("lblock",
                new Block(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.METAL)));

        // Storage blocks
        HGLOW_BLOCK = registerBlock("hglow_block",
                new Block(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.METAL)));
        RAW_RGOLD_BLOCK = registerBlock("raw_rgold_block",
                new Block(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.STONE)));
        ECTOPLASM_BLOCK = registerBlock("ectoplasm_block",
                new Block(AbstractBlock.Settings.create().strength(2.5f).requiresTool().sounds(BlockSoundGroup.SLIME)));
        REFINED_ECTOPLASM_BLOCK = registerBlock("refined_ectoplasm_block",
                new Block(AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.SLIME)));
        HARDENED_COAL_BLOCK = registerBlock("hardened_coal_block",
                new Block(AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.STONE)));
        COAL_DUST_BLOCK = registerBlock("coal_dust_block",
                new Block(AbstractBlock.Settings.create().strength(2f).requiresTool().sounds(BlockSoundGroup.SAND)));
        OBSHARD_BLOCK = registerBlock("obshard_block",
                new Block(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.AMETHYST_BLOCK)));
        CALCIFIED_AMETHYST_BLOCK = registerBlock("calcified_amethyst_block",
                new Block(AbstractBlock.Settings.create().strength(3.5f).requiresTool().sounds(BlockSoundGroup.AMETHYST_BLOCK)));
        GLACIAL_SHARD_BLOCK = registerBlock("glacial_shard_block",
                new Block(AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.GLASS)));
        POLISHED_QUARTZ_BLOCK = registerBlock("polished_quartz_block",
                new Block(AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.STONE)));
        POLISHED_PRISMARINE_BLOCK = registerBlock("polished_prismarine_block",
                new Block(AbstractBlock.Settings.create().strength(3.5f).requiresTool().sounds(BlockSoundGroup.STONE)));

        // Spectral Infuser
        SPECTRAL_INFUSER = registerBlock("spectral_infuser",
                new SpectralInfuserBlock(AbstractBlock.Settings.create()
                        .strength(3.5f)
                        .requiresTool()
                        .sounds(BlockSoundGroup.STONE)
                        .luminance(state -> state.get(SpectralInfuserBlock.LIT) ? 13 : 0)));
    }

    private static <T extends Block> T registerBlock(String name, T block) {
        Registry.register(Registries.BLOCK, Identifier.of(UsefultoolsMod.MOD_ID, name), block);
        Registry.register(Registries.ITEM, Identifier.of(UsefultoolsMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
        return block;
    }
}
