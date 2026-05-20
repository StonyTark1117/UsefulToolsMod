package com.stonytark.usefultoolsmod.block;


import net.minecraft.core.registries.BuiltInRegistries;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.custom.SpectralInfuserBlock;
import com.stonytark.usefultoolsmod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(BuiltInRegistries.BLOCK, UsefultoolsMod.MOD_ID);

    public static final DeferredHolder<Block, Block> RGOLDBLOCK = registerBlock("rgoldblock",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final DeferredHolder<Block, Block> HRBLOCK = registerBlock("hrblock",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS)));

    public static final DeferredHolder<Block, Block> RGOLDORE = registerBlock("rgoldore",
            () -> new DropExperienceBlock(UniformInt.of(2,4), BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredHolder<Block, Block> RGOLD_NETHER_ORE = registerBlock("rgold_nether_ore",
            () -> new DropExperienceBlock(UniformInt.of(2,4), BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.NETHER_ORE)));

    public static final DeferredHolder<Block, Block> RGOLD_END_ORE = registerBlock("rgold_end_ore",
            () -> new DropExperienceBlock(UniformInt.of(2,4), BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.ROOTED_DIRT)));

    public static final DeferredHolder<Block, Block> RGOLD_DEEPSLATE_ORE = registerBlock("rgold_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(2,4), BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final DeferredHolder<Block, Block> SEMBLOCK = registerBlock("semblock",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final DeferredHolder<Block, Block> SOBLOCK = registerBlock("soblock",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final DeferredHolder<Block, Block> LBLOCK = registerBlock("lblock",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    // Storage blocks
    public static final DeferredHolder<Block, Block> HGLOW_BLOCK = registerBlock("hglow_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final DeferredHolder<Block, Block> RAW_RGOLD_BLOCK = registerBlock("raw_rgold_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredHolder<Block, Block> ECTOPLASM_BLOCK = registerBlock("ectoplasm_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2.5f).requiresCorrectToolForDrops().sound(SoundType.SLIME_BLOCK)));

    public static final DeferredHolder<Block, Block> REFINED_ECTOPLASM_BLOCK = registerBlock("refined_ectoplasm_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.SLIME_BLOCK)));

    public static final DeferredHolder<Block, Block> HARDENED_COAL_BLOCK = registerBlock("hardened_coal_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredHolder<Block, Block> COAL_DUST_BLOCK = registerBlock("coal_dust_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2f).requiresCorrectToolForDrops().sound(SoundType.SAND)));

    public static final DeferredHolder<Block, Block> OBSHARD_BLOCK = registerBlock("obshard_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final DeferredHolder<Block, Block> CALCIFIED_AMETHYST_BLOCK = registerBlock("calcified_amethyst_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3.5f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final DeferredHolder<Block, Block> GLACIAL_SHARD_BLOCK = registerBlock("glacial_shard_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.GLASS)));

    public static final DeferredHolder<Block, Block> POLISHED_QUARTZ_BLOCK = registerBlock("polished_quartz_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredHolder<Block, Block> POLISHED_PRISMARINE_BLOCK = registerBlock("polished_prismarine_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3.5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredHolder<Block, Block> SPECTRAL_INFUSER = registerBlock("spectral_infuser",
            () -> new SpectralInfuserBlock(BlockBehaviour.Properties.of()
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .lightLevel(state -> state.getValue(SpectralInfuserBlock.LIT) ? 13 : 0)));


    private static <T extends Block> DeferredHolder<Block, T> registerBlock(String name, Supplier<T> block){
        DeferredHolder<Block, T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredHolder<Block, T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
