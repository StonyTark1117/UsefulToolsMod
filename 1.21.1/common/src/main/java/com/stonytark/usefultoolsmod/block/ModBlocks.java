package com.stonytark.usefultoolsmod.block;

import com.stonytark.usefultoolsmod.generated.GeneratedRegistrationIds;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.custom.SpectralInfuserBlock;
import com.stonytark.usefultoolsmod.item.ModItems;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(UsefultoolsMod.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> RGOLDBLOCK = registerBlock(GeneratedRegistrationIds.Blocks.RGOLDBLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final RegistrySupplier<Block> HRBLOCK = registerBlock(GeneratedRegistrationIds.Blocks.HRBLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS)));

    public static final RegistrySupplier<Block> RGOLDORE = registerBlock(GeneratedRegistrationIds.Blocks.RGOLDORE,
            () -> new DropExperienceBlock(UniformInt.of(2,4), BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistrySupplier<Block> RGOLD_NETHER_ORE = registerBlock(GeneratedRegistrationIds.Blocks.RGOLD_NETHER_ORE,
            () -> new DropExperienceBlock(UniformInt.of(2,4), BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.NETHER_ORE)));

    public static final RegistrySupplier<Block> RGOLD_END_ORE = registerBlock(GeneratedRegistrationIds.Blocks.RGOLD_END_ORE,
            () -> new DropExperienceBlock(UniformInt.of(2,4), BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.ROOTED_DIRT)));

    public static final RegistrySupplier<Block> RGOLD_DEEPSLATE_ORE = registerBlock(GeneratedRegistrationIds.Blocks.RGOLD_DEEPSLATE_ORE,
            () -> new DropExperienceBlock(UniformInt.of(2,4), BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> SEMBLOCK = registerBlock(GeneratedRegistrationIds.Blocks.SEMBLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final RegistrySupplier<Block> SOBLOCK = registerBlock(GeneratedRegistrationIds.Blocks.SOBLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final RegistrySupplier<Block> LBLOCK = registerBlock(GeneratedRegistrationIds.Blocks.LBLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final RegistrySupplier<Block> HGLOW_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.HGLOW_BLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final RegistrySupplier<Block> RAW_RGOLD_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.RAW_RGOLD_BLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistrySupplier<Block> ECTOPLASM_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.ECTOPLASM_BLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2.5f).requiresCorrectToolForDrops().sound(SoundType.SLIME_BLOCK)));

    public static final RegistrySupplier<Block> REFINED_ECTOPLASM_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.REFINED_ECTOPLASM_BLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.SLIME_BLOCK)));

    public static final RegistrySupplier<Block> HARDENED_COAL_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.HARDENED_COAL_BLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistrySupplier<Block> COAL_DUST_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.COAL_DUST_BLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2f).requiresCorrectToolForDrops().sound(SoundType.SAND)));

    public static final RegistrySupplier<Block> OBSHARD_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.OBSHARD_BLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final RegistrySupplier<Block> CALCIFIED_AMETHYST_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.CALCIFIED_AMETHYST_BLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3.5f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final RegistrySupplier<Block> GLACIAL_SHARD_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.GLACIAL_SHARD_BLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.GLASS)));

    public static final RegistrySupplier<Block> POLISHED_QUARTZ_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.POLISHED_QUARTZ_BLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistrySupplier<Block> POLISHED_PRISMARINE_BLOCK = registerBlock(GeneratedRegistrationIds.Blocks.POLISHED_PRISMARINE_BLOCK,
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3.5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistrySupplier<Block> SPECTRAL_INFUSER = registerBlock(GeneratedRegistrationIds.Blocks.SPECTRAL_INFUSER,
            () -> new SpectralInfuserBlock(BlockBehaviour.Properties.of()
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .lightLevel(state -> state.getValue(SpectralInfuserBlock.LIT) ? 13 : 0)));

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistrySupplier<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register() {
        BLOCKS.register();
    }
}
