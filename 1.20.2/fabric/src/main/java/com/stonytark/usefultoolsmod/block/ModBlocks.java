package com.stonytark.usefultoolsmod.block;

import com.stonytark.usefultoolsmod.generated.GeneratedRegistrationIds;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.custom.SpectralInfuserBlock;
import com.stonytark.usefultoolsmod.block.custom.EctoplasmLanternBlock;
import com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock;
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

    // Storage blocks
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

    // Spectral Infuser
    public static Block SPECTRAL_INFUSER;
    public static Block ECTOPLASM_LANTERN;
    /** Legacy registry alias for worlds created before the Ectoplasm Lantern rename. */
    public static Block LEGACY_SOUL_LANTERN;
    public static Block MINING_CHARGE;

    public static void register() {
        RGOLDBLOCK = registerBlock(GeneratedRegistrationIds.Blocks.RGOLDBLOCK,
                new Block(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.METAL)));
        HRBLOCK = registerBlock(GeneratedRegistrationIds.Blocks.HRBLOCK,
                new Block(AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.NETHER_BRICKS)));
        RGOLDORE = registerBlock(GeneratedRegistrationIds.Blocks.RGOLDORE,
                new ExperienceDroppingBlock(AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.STONE),
                        UniformIntProvider.create(2, 4)));
        RGOLD_NETHER_ORE = registerBlock(GeneratedRegistrationIds.Blocks.RGOLD_NETHER_ORE,
                new ExperienceDroppingBlock(AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.NETHER_ORE),
                        UniformIntProvider.create(2, 4)));
        RGOLD_END_ORE = registerBlock(GeneratedRegistrationIds.Blocks.RGOLD_END_ORE,
                new ExperienceDroppingBlock(AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.ROOTED_DIRT),
                        UniformIntProvider.create(2, 4)));
        RGOLD_DEEPSLATE_ORE = registerBlock(GeneratedRegistrationIds.Blocks.RGOLD_DEEPSLATE_ORE,
                new ExperienceDroppingBlock(AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE),
                        UniformIntProvider.create(2, 4)));
        SEMBLOCK = registerBlock(GeneratedRegistrationIds.Blocks.SEMBLOCK,
                new Block(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.AMETHYST_BLOCK)));
        SOBLOCK = registerBlock(GeneratedRegistrationIds.Blocks.SOBLOCK,
                new Block(AbstractBlock.Settings.create().strength(5f).requiresTool().sounds(BlockSoundGroup.AMETHYST_BLOCK)));
        LBLOCK = registerBlock(GeneratedRegistrationIds.Blocks.LBLOCK,
                new Block(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.METAL)));

        // Storage blocks
        HGLOW_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.HGLOW_BLOCK,
                new Block(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.METAL)));
        RAW_RGOLD_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.RAW_RGOLD_BLOCK,
                new Block(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.STONE)));
        ECTOPLASM_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.ECTOPLASM_BLOCK,
                new Block(AbstractBlock.Settings.create().strength(2.5f).requiresTool().sounds(BlockSoundGroup.SLIME)));
        REFINED_ECTOPLASM_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.REFINED_ECTOPLASM_BLOCK,
                new Block(AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.SLIME)));
        HARDENED_COAL_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.HARDENED_COAL_BLOCK,
                new Block(AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.STONE)));
        COAL_DUST_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.COAL_DUST_BLOCK,
                new Block(AbstractBlock.Settings.create().strength(2f).requiresTool().sounds(BlockSoundGroup.SAND)));
        OBSHARD_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.OBSHARD_BLOCK,
                new Block(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.AMETHYST_BLOCK)));
        CALCIFIED_AMETHYST_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.CALCIFIED_AMETHYST_BLOCK,
                new Block(AbstractBlock.Settings.create().strength(3.5f).requiresTool().sounds(BlockSoundGroup.AMETHYST_BLOCK)));
        GLACIAL_SHARD_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.GLACIAL_SHARD_BLOCK,
                new Block(AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.GLASS)));
        POLISHED_QUARTZ_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.POLISHED_QUARTZ_BLOCK,
                new Block(AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.STONE)));
        POLISHED_PRISMARINE_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.POLISHED_PRISMARINE_BLOCK,
                new Block(AbstractBlock.Settings.create().strength(3.5f).requiresTool().sounds(BlockSoundGroup.STONE)));

        // Spectral Infuser
        SPECTRAL_INFUSER = registerBlock(GeneratedRegistrationIds.Blocks.SPECTRAL_INFUSER,
                new SpectralInfuserBlock(AbstractBlock.Settings.create()
                        .strength(3.5f)
                        .requiresTool()
                        .sounds(BlockSoundGroup.STONE)
                        .luminance(state -> state.get(SpectralInfuserBlock.LIT) ? 13 : 0)));
        ECTOPLASM_LANTERN = registerBlock(GeneratedRegistrationIds.Blocks.ECTOPLASM_LANTERN, new EctoplasmLanternBlock(AbstractBlock.Settings.create()
                .strength(3.0F).sounds(BlockSoundGroup.LANTERN).luminance(state -> 12).nonOpaque()));
        LEGACY_SOUL_LANTERN = registerBlock("soul_lantern", new EctoplasmLanternBlock(AbstractBlock.Settings.create()
                .strength(3.0F).sounds(BlockSoundGroup.LANTERN).luminance(state -> 12).nonOpaque()));
        MINING_CHARGE = registerBlock(GeneratedRegistrationIds.Blocks.MINING_CHARGE, new MiningChargeBlock(AbstractBlock.Settings.create()
                .strength(1.0F).sounds(BlockSoundGroup.WOOL).nonOpaque()));
    }

    private static <T extends Block> T registerBlock(String name, T block) {
        Registry.register(Registries.BLOCK, new Identifier(UsefultoolsMod.MOD_ID, name), block);
        Registry.register(Registries.ITEM, new Identifier(UsefultoolsMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
        return block;
    }
}
