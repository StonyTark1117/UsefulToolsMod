package com.stonytark.usefultoolsmod.fabric.datagen;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.concurrent.CompletableFuture;

/** Fabric's namespace-filtered adapter for the shared block loot definitions. */
public final class UsefulToolsBlockLootProvider extends FabricBlockLootTableProvider {
    public UsefulToolsBlockLootProvider(FabricDataOutput output,
                                        CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void generate() {
        dropSelfWithSequence(ModBlocks.RGOLDBLOCK.get());
        dropSelfWithSequence(ModBlocks.HRBLOCK.get());
        dropSelfWithSequence(ModBlocks.SEMBLOCK.get());
        dropSelfWithSequence(ModBlocks.SOBLOCK.get());
        dropSelfWithSequence(ModBlocks.LBLOCK.get());
        dropSelfWithSequence(ModBlocks.SPECTRAL_INFUSER.get());
        dropSelfWithSequence(ModBlocks.HGLOW_BLOCK.get());
        dropSelfWithSequence(ModBlocks.RAW_RGOLD_BLOCK.get());
        dropSelfWithSequence(ModBlocks.ECTOPLASM_BLOCK.get());
        dropSelfWithSequence(ModBlocks.REFINED_ECTOPLASM_BLOCK.get());
        dropSelfWithSequence(ModBlocks.HARDENED_COAL_BLOCK.get());
        dropSelfWithSequence(ModBlocks.COAL_DUST_BLOCK.get());
        dropSelfWithSequence(ModBlocks.OBSHARD_BLOCK.get());
        dropSelfWithSequence(ModBlocks.CALCIFIED_AMETHYST_BLOCK.get());
        dropSelfWithSequence(ModBlocks.GLACIAL_SHARD_BLOCK.get());
        dropSelfWithSequence(ModBlocks.POLISHED_QUARTZ_BLOCK.get());
        dropSelfWithSequence(ModBlocks.POLISHED_PRISMARINE_BLOCK.get());

        add(ModBlocks.RGOLDORE.get(), block ->
                withRandomSequence(block, createOreDrop(block, ModItems.RAW_RGOLD.get())));
        add(ModBlocks.RGOLD_NETHER_ORE.get(), block ->
                withRandomSequence(block, createOreDrop(block, ModItems.RAW_RGOLD.get())));
        add(ModBlocks.RGOLD_END_ORE.get(), block ->
                withRandomSequence(block, createOreDrop(block, ModItems.RAW_RGOLD.get())));
        add(ModBlocks.RGOLD_DEEPSLATE_ORE.get(), block ->
                withRandomSequence(block, createOreDrop(block, ModItems.RAW_RGOLD.get())));
    }

    private void dropSelfWithSequence(Block block) {
        add(block, withRandomSequence(block, createSingleItemTable(block)));
    }

    private static LootTable.Builder withRandomSequence(Block block, LootTable.Builder table) {
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
        return table.setRandomSequence(ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "blocks/" + id.getPath()));
    }
}
