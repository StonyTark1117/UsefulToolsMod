package com.stonytark.usefultoolsmod.block;


import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
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

import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(BuiltInRegistries.BLOCK, UsefultoolsMod.MOD_ID);

    public static final DeferredHolder<Block, Block> RGOLDBLOCK = registerBlock("rgoldblock",
            props -> new Block(props
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final DeferredHolder<Block, Block> HRBLOCK = registerBlock("hrblock",
            props -> new Block(props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS)));

    public static final DeferredHolder<Block, Block> RGOLDORE = registerBlock("rgoldore",
            props -> new DropExperienceBlock(UniformInt.of(2,4), props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredHolder<Block, Block> RGOLD_NETHER_ORE = registerBlock("rgold_nether_ore",
            props -> new DropExperienceBlock(UniformInt.of(2,4), props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.NETHER_ORE)));

    public static final DeferredHolder<Block, Block> RGOLD_END_ORE = registerBlock("rgold_end_ore",
            props -> new DropExperienceBlock(UniformInt.of(2,4), props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.ROOTED_DIRT)));

    public static final DeferredHolder<Block, Block> RGOLD_DEEPSLATE_ORE = registerBlock("rgold_deepslate_ore",
            props -> new DropExperienceBlock(UniformInt.of(2,4), props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final DeferredHolder<Block, Block> SEMBLOCK = registerBlock("semblock",
            props -> new Block(props
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final DeferredHolder<Block, Block> SOBLOCK = registerBlock("soblock",
            props -> new Block(props
                    .strength(5f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final DeferredHolder<Block, Block> LBLOCK = registerBlock("lblock",
            props -> new Block(props
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    // Storage blocks
    public static final DeferredHolder<Block, Block> HGLOW_BLOCK = registerBlock("hglow_block",
            props -> new Block(props
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final DeferredHolder<Block, Block> RAW_RGOLD_BLOCK = registerBlock("raw_rgold_block",
            props -> new Block(props
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredHolder<Block, Block> ECTOPLASM_BLOCK = registerBlock("ectoplasm_block",
            props -> new Block(props
                    .strength(2.5f).requiresCorrectToolForDrops().sound(SoundType.SLIME_BLOCK)));

    public static final DeferredHolder<Block, Block> REFINED_ECTOPLASM_BLOCK = registerBlock("refined_ectoplasm_block",
            props -> new Block(props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.SLIME_BLOCK)));

    public static final DeferredHolder<Block, Block> HARDENED_COAL_BLOCK = registerBlock("hardened_coal_block",
            props -> new Block(props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredHolder<Block, Block> COAL_DUST_BLOCK = registerBlock("coal_dust_block",
            props -> new Block(props
                    .strength(2f).requiresCorrectToolForDrops().sound(SoundType.SAND)));

    public static final DeferredHolder<Block, Block> OBSHARD_BLOCK = registerBlock("obshard_block",
            props -> new Block(props
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final DeferredHolder<Block, Block> CALCIFIED_AMETHYST_BLOCK = registerBlock("calcified_amethyst_block",
            props -> new Block(props
                    .strength(3.5f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final DeferredHolder<Block, Block> GLACIAL_SHARD_BLOCK = registerBlock("glacial_shard_block",
            props -> new Block(props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.GLASS)));

    public static final DeferredHolder<Block, Block> POLISHED_QUARTZ_BLOCK = registerBlock("polished_quartz_block",
            props -> new Block(props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredHolder<Block, Block> POLISHED_PRISMARINE_BLOCK = registerBlock("polished_prismarine_block",
            props -> new Block(props
                    .strength(3.5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredHolder<Block, Block> SPECTRAL_INFUSER = registerBlock("spectral_infuser",
            props -> new SpectralInfuserBlock(props
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .lightLevel(state -> state.getValue(SpectralInfuserBlock.LIT) ? 13 : 0)));


    private static <T extends Block> DeferredHolder<Block, T> registerBlock(String name, Function<BlockBehaviour.Properties, T> factory){
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK,
                Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, name));
        DeferredHolder<Block, T> toReturn = BLOCKS.register(name,
                () -> factory.apply(BlockBehaviour.Properties.of().setId(blockKey)));
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredHolder<Block, T> block) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM,
                Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, name));
        // useBlockDescriptionPrefix(): in 26.1 Item.Properties defaults its descriptionId to
        // "item.<modid>.<path>". BlockItems need the "block." prefix to pick up the block's
        // lang entry — otherwise the inventory tooltip falls back to the raw key.
        ModItems.ITEMS.register(name, () ->
                new BlockItem(block.get(),
                        new Item.Properties().useBlockDescriptionPrefix().setId(itemKey)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
