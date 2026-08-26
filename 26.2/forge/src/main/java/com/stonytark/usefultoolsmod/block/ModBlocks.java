package com.stonytark.usefultoolsmod.block;

import com.stonytark.usefultoolsmod.generated.GeneratedRegistrationIds;


import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.custom.SpectralInfuserBlock;
import com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock;
import com.stonytark.usefultoolsmod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, UsefultoolsMod.MOD_ID);

    public static final RegistryObject<Block> RGOLDBLOCK = registerBlock(GeneratedRegistrationIds.Blocks.RGOLDBLOCK,
            props -> new Block(props
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final RegistryObject<Block> HRBLOCK = registerBlock(GeneratedRegistrationIds.Blocks.HRBLOCK,
            props -> new Block(props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS)));

    public static final RegistryObject<Block> RGOLDORE = registerBlock(GeneratedRegistrationIds.Blocks.RGOLDORE,
            props -> new DropExperienceBlock(UniformInt.of(2,4), props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> RGOLD_NETHER_ORE = registerBlock(GeneratedRegistrationIds.Blocks.RGOLD_NETHER_ORE,
            props -> new DropExperienceBlock(UniformInt.of(2,4), props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.NETHER_ORE)));

    public static final RegistryObject<Block> RGOLD_END_ORE = registerBlock(GeneratedRegistrationIds.Blocks.RGOLD_END_ORE,
            props -> new DropExperienceBlock(UniformInt.of(2,4), props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.ROOTED_DIRT)));

    public static final RegistryObject<Block> RGOLD_DEEPSLATE_ORE = registerBlock(GeneratedRegistrationIds.Blocks.RGOLD_DEEPSLATE_ORE,
            props -> new DropExperienceBlock(UniformInt.of(2,4), props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<Block> SEMBLOCK = registerBlock(GeneratedRegistrationIds.Blocks.SEMBLOCK,
            props -> new Block(props
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> SOBLOCK = registerBlock(GeneratedRegistrationIds.Blocks.SOBLOCK,
            props -> new Block(props
                    .strength(5f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> LBLOCK = registerBlock(GeneratedRegistrationIds.Blocks.LBLOCK,
            props -> new Block(props
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    // Storage blocks
    public static final RegistryObject<Block> HGLOW_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.HGLOW_BLOCK,
            props -> new Block(props
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final RegistryObject<Block> RAW_RGOLD_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.RAW_RGOLD_BLOCK,
            props -> new Block(props
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> ECTOPLASM_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.ECTOPLASM_BLOCK,
            props -> new Block(props
                    .strength(2.5f).requiresCorrectToolForDrops().sound(SoundType.SLIME_BLOCK)));

    public static final RegistryObject<Block> REFINED_ECTOPLASM_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.REFINED_ECTOPLASM_BLOCK,
            props -> new Block(props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.SLIME_BLOCK)));

    public static final RegistryObject<Block> HARDENED_COAL_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.HARDENED_COAL_BLOCK,
            props -> new Block(props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> COAL_DUST_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.COAL_DUST_BLOCK,
            props -> new Block(props
                    .strength(2f).requiresCorrectToolForDrops().sound(SoundType.SAND)));

    public static final RegistryObject<Block> OBSHARD_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.OBSHARD_BLOCK,
            props -> new Block(props
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> CALCIFIED_AMETHYST_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.CALCIFIED_AMETHYST_BLOCK,
            props -> new Block(props
                    .strength(3.5f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> GLACIAL_SHARD_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.GLACIAL_SHARD_BLOCK,
            props -> new Block(props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.GLASS)));

    public static final RegistryObject<Block> POLISHED_QUARTZ_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.POLISHED_QUARTZ_BLOCK,
            props -> new Block(props
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> POLISHED_PRISMARINE_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.POLISHED_PRISMARINE_BLOCK,
            props -> new Block(props
                    .strength(3.5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> SPECTRAL_INFUSER = registerBlock(GeneratedRegistrationIds.Blocks.SPECTRAL_INFUSER,
            props -> new SpectralInfuserBlock(props
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .lightLevel(state -> state.getValue(SpectralInfuserBlock.LIT) ? 13 : 0)));
    public static final RegistryObject<Block> SOUL_LANTERN = registerBlock(GeneratedRegistrationIds.Blocks.SOUL_LANTERN,
            props -> new Block(props.strength(3.0F).sound(SoundType.LANTERN).lightLevel(state -> 12)));
    public static final RegistryObject<Block> MINING_CHARGE = registerBlock(GeneratedRegistrationIds.Blocks.MINING_CHARGE,
            props -> new MiningChargeBlock(props.strength(1.0F).sound(SoundType.WOOL).noOcclusion()));


    private static <T extends Block> RegistryObject<Block> registerBlock(String name, Function<BlockBehaviour.Properties, T> factory){
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK,
                Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, name));
        RegistryObject<Block> toReturn = BLOCKS.register(name,
                () -> factory.apply(BlockBehaviour.Properties.of().setId(blockKey)));
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<Block> block) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM,
                Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, name));
        // useBlockDescriptionPrefix(): in 26.1 Item.Properties defaults its descriptionId to
        // "item.<modid>.<path>". BlockItems need the "block." prefix to pick up the block's
        // lang entry — otherwise the inventory tooltip falls back to the raw key.
        ModItems.ITEMS.register(name, () ->
                new BlockItem(block.get(),
                        new Item.Properties().useBlockDescriptionPrefix().setId(itemKey)));
    }

    public static void register(BusGroup busGroup) {
        BLOCKS.register(busGroup);
    }
}
