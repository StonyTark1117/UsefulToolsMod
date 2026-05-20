package com.stonytark.usefultoolsmod.datagen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.registry.RegistryWrapper;
import java.util.concurrent.CompletableFuture;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;

/**
 * Fabric 1.20.1 Recipe Data Generator
 * Generates ALL crafting recipes for blocks and items
 */
public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        // =====================================================================
        // Material crafting recipes
        // =====================================================================

        // Reinforced Lapis: 8 iron nuggets around lapis lazuli
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RLAPIS)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .input('A', Items.IRON_NUGGET).input('B', Items.LAPIS_LAZULI)
                .criterion(hasItem(Items.LAPIS_LAZULI), conditionsFromItem(Items.LAPIS_LAZULI)).offerTo(exporter);

        // Hardened Redstone: 4 clay balls in + around redstone
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.HRED)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .input('A', Items.CLAY_BALL).input('B', Items.REDSTONE)
                .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE)).offerTo(exporter);

        // Hardened Glowstone: 4 glowstone dust in + around a blaze rod
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.HGLOW)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .input('A', Items.GLOWSTONE_DUST).input('B', Items.BLAZE_ROD)
                .criterion(hasItem(Items.GLOWSTONE_DUST), conditionsFromItem(Items.GLOWSTONE_DUST)).offerTo(exporter);

        // Super Emerald (SEM): 4 iron ingots in + around emerald
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SEM)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .input('A', Items.IRON_INGOT).input('B', Items.EMERALD)
                .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE)).offerTo(exporter);

        // Obsidian Ingot: 4 iron ingots in + around obsidian shard
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.OBINGOT)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .input('A', Items.IRON_INGOT).input('B', ModItems.OBSHARD)
                .criterion(hasItem(ModItems.OBSHARD), conditionsFromItem(ModItems.OBSHARD)).offerTo(exporter);

        // Ferrous Gold: 8 iron nuggets around gold ingot
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RGOLD)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .input('A', Items.IRON_NUGGET).input('B', Items.GOLD_INGOT)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT)).offerTo(exporter);

        // Obsidian -> 3 Obsidian Shards
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.OBSHARD, 3)
                .input(Items.OBSIDIAN)
                .criterion(hasItem(Items.OBSIDIAN), conditionsFromItem(Items.OBSIDIAN))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "obshard_from_block"));

        // Ectoplasm from phantom membrane
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ECTOPLASM, 2)
                .input(Items.PHANTOM_MEMBRANE)
                .input(Items.GLOWSTONE_DUST)
                .criterion(hasItem(Items.PHANTOM_MEMBRANE), conditionsFromItem(Items.PHANTOM_MEMBRANE))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "ectoplasm_from_membrane"));

        // Refined Ectoplasm: 4 ectoplasm (cross) + 1 diamond (center)
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.REFINED_ECTOPLASM)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .input('A', ModItems.ECTOPLASM)
                .input('B', Items.DIAMOND)
                .criterion(hasItem(ModItems.ECTOPLASM), conditionsFromItem(ModItems.ECTOPLASM))
                .offerTo(exporter);

        // 1 Coal -> 4 Coal Dust
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.COAL_DUST, 4)
                .input(Items.COAL)
                .criterion(hasItem(Items.COAL), conditionsFromItem(Items.COAL))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "coal_dust_from_coal"));

        // 1 Charcoal -> 4 Coal Dust
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.COAL_DUST, 4)
                .input(Items.CHARCOAL)
                .criterion(hasItem(Items.CHARCOAL), conditionsFromItem(Items.CHARCOAL))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "coal_dust_from_charcoal"));

        // Hardened Coal: Coal Dust surrounded by Clay Balls
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.HARDENED_COAL)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .input('A', Items.CLAY_BALL)
                .input('B', ModItems.COAL_DUST)
                .criterion(hasItem(ModItems.COAL_DUST), conditionsFromItem(ModItems.COAL_DUST))
                .offerTo(exporter);

        // Calcified Amethyst: + pattern with amethyst shards around calcite
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CALCIFIED_AMETHYST)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .input('A', Items.AMETHYST_SHARD)
                .input('B', Blocks.CALCITE.asItem())
                .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter);

        // Glacial Shard: packed ice surrounding blue ice
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.GLACIAL_SHARD)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .input('A', Items.PACKED_ICE)
                .input('B', Items.BLUE_ICE)
                .criterion(hasItem(Items.PACKED_ICE), conditionsFromItem(Items.PACKED_ICE))
                .offerTo(exporter);

        // Polished Quartz: + pattern with quartz around smooth quartz block
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.POLISHED_QUARTZ)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .input('A', Items.QUARTZ)
                .input('B', Blocks.SMOOTH_QUARTZ.asItem())
                .criterion(hasItem(Items.QUARTZ), conditionsFromItem(Items.QUARTZ))
                .offerTo(exporter);

        // Polished Prismarine: + pattern with prismarine shards around prismarine crystals
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.POLISHED_PRISMARINE)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .input('A', Items.PRISMARINE_SHARD)
                .input('B', Items.PRISMARINE_CRYSTALS)
                .criterion(hasItem(Items.PRISMARINE_SHARD), conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);

        // =====================================================================
        // Storage block recipes (9 items -> 1 block)
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.RGOLDBLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.RGOLD)
                .criterion(hasItem(ModItems.RGOLD), conditionsFromItem(ModItems.RGOLD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.HRBLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.HRED)
                .criterion(hasItem(ModItems.HRED), conditionsFromItem(ModItems.HRED)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.SEMBLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.SEM)
                .criterion(hasItem(ModItems.SEM), conditionsFromItem(ModItems.SEM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.SOBLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.OBINGOT)
                .criterion(hasItem(ModItems.OBINGOT), conditionsFromItem(ModItems.OBINGOT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.LBLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.RLAPIS)
                .criterion(hasItem(ModItems.RLAPIS), conditionsFromItem(ModItems.RLAPIS)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.HGLOW_BLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.HGLOW)
                .criterion(hasItem(ModItems.HGLOW), conditionsFromItem(ModItems.HGLOW)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.RAW_RGOLD_BLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.RAW_RGOLD)
                .criterion(hasItem(ModItems.RAW_RGOLD), conditionsFromItem(ModItems.RAW_RGOLD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ECTOPLASM_BLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.ECTOPLASM)
                .criterion(hasItem(ModItems.ECTOPLASM), conditionsFromItem(ModItems.ECTOPLASM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.REFINED_ECTOPLASM_BLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.REFINED_ECTOPLASM)
                .criterion(hasItem(ModItems.REFINED_ECTOPLASM), conditionsFromItem(ModItems.REFINED_ECTOPLASM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.HARDENED_COAL_BLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.HARDENED_COAL)
                .criterion(hasItem(ModItems.HARDENED_COAL), conditionsFromItem(ModItems.HARDENED_COAL)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.COAL_DUST_BLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.COAL_DUST)
                .criterion(hasItem(ModItems.COAL_DUST), conditionsFromItem(ModItems.COAL_DUST)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.OBSHARD_BLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.OBSHARD)
                .criterion(hasItem(ModItems.OBSHARD), conditionsFromItem(ModItems.OBSHARD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.CALCIFIED_AMETHYST_BLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.CALCIFIED_AMETHYST)
                .criterion(hasItem(ModItems.CALCIFIED_AMETHYST), conditionsFromItem(ModItems.CALCIFIED_AMETHYST)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.GLACIAL_SHARD_BLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.GLACIAL_SHARD)
                .criterion(hasItem(ModItems.GLACIAL_SHARD), conditionsFromItem(ModItems.GLACIAL_SHARD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.POLISHED_QUARTZ_BLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.POLISHED_QUARTZ)
                .criterion(hasItem(ModItems.POLISHED_QUARTZ), conditionsFromItem(ModItems.POLISHED_QUARTZ)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.POLISHED_PRISMARINE_BLOCK)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .input('A', ModItems.POLISHED_PRISMARINE)
                .criterion(hasItem(ModItems.POLISHED_PRISMARINE), conditionsFromItem(ModItems.POLISHED_PRISMARINE)).offerTo(exporter);

        // =====================================================================
        // Reverse storage block recipes (1 block -> 9 items)
        // =====================================================================

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RGOLD, 9)
                .input(ModBlocks.RGOLDBLOCK)
                .criterion(hasItem(ModBlocks.RGOLDBLOCK), conditionsFromItem(ModBlocks.RGOLDBLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "rgold_from_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.HRED, 9)
                .input(ModBlocks.HRBLOCK)
                .criterion(hasItem(ModBlocks.HRBLOCK), conditionsFromItem(ModBlocks.HRBLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "hred_from_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.OBINGOT, 9)
                .input(ModBlocks.SOBLOCK)
                .criterion(hasItem(ModBlocks.SOBLOCK), conditionsFromItem(ModBlocks.SOBLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "sobingot_from_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RLAPIS, 9)
                .input(ModBlocks.LBLOCK)
                .criterion(hasItem(ModBlocks.LBLOCK), conditionsFromItem(ModBlocks.LBLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "rlapis_from_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SEM, 9)
                .input(ModBlocks.SEMBLOCK)
                .criterion(hasItem(ModBlocks.SEMBLOCK), conditionsFromItem(ModBlocks.SEMBLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "sem_from_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.HGLOW, 9)
                .input(ModBlocks.HGLOW_BLOCK)
                .criterion(hasItem(ModBlocks.HGLOW_BLOCK), conditionsFromItem(ModBlocks.HGLOW_BLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "hglow_from_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_RGOLD, 9)
                .input(ModBlocks.RAW_RGOLD_BLOCK)
                .criterion(hasItem(ModBlocks.RAW_RGOLD_BLOCK), conditionsFromItem(ModBlocks.RAW_RGOLD_BLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "raw_rgold_from_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ECTOPLASM, 9)
                .input(ModBlocks.ECTOPLASM_BLOCK)
                .criterion(hasItem(ModBlocks.ECTOPLASM_BLOCK), conditionsFromItem(ModBlocks.ECTOPLASM_BLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "ectoplasm_from_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.REFINED_ECTOPLASM, 9)
                .input(ModBlocks.REFINED_ECTOPLASM_BLOCK)
                .criterion(hasItem(ModBlocks.REFINED_ECTOPLASM_BLOCK), conditionsFromItem(ModBlocks.REFINED_ECTOPLASM_BLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "refined_ectoplasm_from_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.HARDENED_COAL, 9)
                .input(ModBlocks.HARDENED_COAL_BLOCK)
                .criterion(hasItem(ModBlocks.HARDENED_COAL_BLOCK), conditionsFromItem(ModBlocks.HARDENED_COAL_BLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "hardened_coal_from_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.COAL_DUST, 9)
                .input(ModBlocks.COAL_DUST_BLOCK)
                .criterion(hasItem(ModBlocks.COAL_DUST_BLOCK), conditionsFromItem(ModBlocks.COAL_DUST_BLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "coal_dust_from_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.OBSHARD, 9)
                .input(ModBlocks.OBSHARD_BLOCK)
                .criterion(hasItem(ModBlocks.OBSHARD_BLOCK), conditionsFromItem(ModBlocks.OBSHARD_BLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "obshard_from_obshard_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CALCIFIED_AMETHYST, 9)
                .input(ModBlocks.CALCIFIED_AMETHYST_BLOCK)
                .criterion(hasItem(ModBlocks.CALCIFIED_AMETHYST_BLOCK), conditionsFromItem(ModBlocks.CALCIFIED_AMETHYST_BLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "calcified_amethyst_from_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.GLACIAL_SHARD, 9)
                .input(ModBlocks.GLACIAL_SHARD_BLOCK)
                .criterion(hasItem(ModBlocks.GLACIAL_SHARD_BLOCK), conditionsFromItem(ModBlocks.GLACIAL_SHARD_BLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "glacial_shard_from_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.POLISHED_QUARTZ, 9)
                .input(ModBlocks.POLISHED_QUARTZ_BLOCK)
                .criterion(hasItem(ModBlocks.POLISHED_QUARTZ_BLOCK), conditionsFromItem(ModBlocks.POLISHED_QUARTZ_BLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "polished_quartz_from_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.POLISHED_PRISMARINE, 9)
                .input(ModBlocks.POLISHED_PRISMARINE_BLOCK)
                .criterion(hasItem(ModBlocks.POLISHED_PRISMARINE_BLOCK), conditionsFromItem(ModBlocks.POLISHED_PRISMARINE_BLOCK))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "polished_prismarine_from_block"));

        // =====================================================================
        // Ore recipes (reverse crafting + smelting/blasting)
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.RGOLDORE)
                .pattern("AAA").pattern("ABA").pattern("AAA")
                .input('A', Items.STONE).input('B', ModItems.RGOLD)
                .criterion(hasItem(ModItems.RGOLD), conditionsFromItem(ModItems.RGOLD))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "reverse_rgoldore"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.RGOLD_END_ORE)
                .pattern("AAA").pattern("ABA").pattern("AAA")
                .input('A', Items.END_STONE).input('B', ModItems.RGOLD)
                .criterion(hasItem(ModItems.RGOLD), conditionsFromItem(ModItems.RGOLD))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "reverse_rgold_end_ore"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.RGOLD_NETHER_ORE)
                .pattern("AAA").pattern("ABA").pattern("AAA")
                .input('A', Items.NETHERRACK).input('B', ModItems.RGOLD)
                .criterion(hasItem(ModItems.RGOLD), conditionsFromItem(ModItems.RGOLD))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "reverse_rgold_nether_ore"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.RGOLD_DEEPSLATE_ORE)
                .pattern("AAA").pattern("ABA").pattern("AAA")
                .input('A', Items.COBBLED_DEEPSLATE).input('B', ModItems.RGOLD)
                .criterion(hasItem(ModItems.RGOLD), conditionsFromItem(ModItems.RGOLD))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "reverse_rgold_deepslate_ore"));

        // Ore smelting and blasting
        List<ItemConvertible> RGOLD_SMELTABLES = List.of(ModItems.RAW_RGOLD, ModBlocks.RGOLDORE, ModBlocks.RGOLD_END_ORE, ModBlocks.RGOLD_NETHER_ORE, ModBlocks.RGOLD_DEEPSLATE_ORE);

        for (ItemConvertible input : RGOLD_SMELTABLES) {
            CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(input), RecipeCategory.MISC, ModItems.RGOLD, 0.25f, 200)
                    .group("rgold")
                    .criterion(hasItem(input), conditionsFromItem(input))
                    .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, getItemPath(ModItems.RGOLD) + "_from_smelting_" + getItemPath(input)));

            CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(input), RecipeCategory.MISC, ModItems.RGOLD, 0.25f, 100)
                    .group("rgold")
                    .criterion(hasItem(input), conditionsFromItem(input))
                    .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, getItemPath(ModItems.RGOLD) + "_from_blasting_" + getItemPath(input)));
        }

        // =====================================================================
        // Ice crafting recipe
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Blocks.ICE)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .input('S', Items.SNOWBALL)
                .criterion(hasItem(Items.SNOWBALL), conditionsFromItem(Items.SNOWBALL)).offerTo(exporter);

        // Blue Ice -> 9 Packed Ice
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.PACKED_ICE, 9)
                .input(Items.BLUE_ICE)
                .criterion(hasItem(Items.BLUE_ICE), conditionsFromItem(Items.BLUE_ICE))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "packed_ice_from_blue_ice"));

        // Packed Ice -> 9 Ice
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.ICE, 9)
                .input(Items.PACKED_ICE)
                .criterion(hasItem(Items.PACKED_ICE), conditionsFromItem(Items.PACKED_ICE))
                .offerTo(exporter, Identifier.of(UsefultoolsMod.MOD_ID, "ice_from_packed_ice"));

        // =====================================================================
        // Spectral Infuser block recipe
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.SPECTRAL_INFUSER)
                .pattern("EEE")
                .pattern("EBE")
                .pattern("SSS")
                .input('E', ModItems.ECTOPLASM)
                .input('B', Items.BLAZE_ROD)
                .input('S', Blocks.SMOOTH_STONE)
                .criterion(hasItem(ModItems.ECTOPLASM), conditionsFromItem(ModItems.ECTOPLASM))
                .offerTo(exporter);

        // =====================================================================
        // Emerald tool recipes (raw emerald - remerald)
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.REMERALD_HOE)
                .pattern("AA ").pattern(" B ").pattern(" B ")
                .input('A', Items.EMERALD).input('B', Items.STICK)
                .criterion(hasItem(Items.EMERALD), conditionsFromItem(Items.EMERALD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.REMERALD_AXE)
                .pattern("AA ").pattern("AB ").pattern(" B ")
                .input('A', Items.EMERALD).input('B', Items.STICK)
                .criterion(hasItem(Items.EMERALD), conditionsFromItem(Items.EMERALD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.REMERALD_SHOVEL)
                .pattern(" A ").pattern(" B ").pattern(" B ")
                .input('A', Items.EMERALD).input('B', Items.STICK)
                .criterion(hasItem(Items.EMERALD), conditionsFromItem(Items.EMERALD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.REMERALD_PICKAXE)
                .pattern("AAA").pattern(" B ").pattern(" B ")
                .input('A', Items.EMERALD).input('B', Items.STICK)
                .criterion(hasItem(Items.EMERALD), conditionsFromItem(Items.EMERALD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.REMERALD_SWORD)
                .pattern(" A ").pattern(" A ").pattern(" B ")
                .input('A', Items.EMERALD).input('B', Items.STICK)
                .criterion(hasItem(Items.EMERALD), conditionsFromItem(Items.EMERALD)).offerTo(exporter);

        // =====================================================================
        // Super Emerald (pemerald) tools
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PEMERALD_HOE)
                .pattern("AA ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.SEM).input('B', Items.STICK)
                .criterion(hasItem(ModItems.SEM), conditionsFromItem(ModItems.SEM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PEMERALD_AXE)
                .pattern("AA ").pattern("AB ").pattern(" B ")
                .input('A', ModItems.SEM).input('B', Items.STICK)
                .criterion(hasItem(ModItems.SEM), conditionsFromItem(ModItems.SEM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PEMERALD_SHOVEL)
                .pattern(" A ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.SEM).input('B', Items.STICK)
                .criterion(hasItem(ModItems.SEM), conditionsFromItem(ModItems.SEM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PEMERALD_PICKAXE)
                .pattern("AAA").pattern(" B ").pattern(" B ")
                .input('A', ModItems.SEM).input('B', Items.STICK)
                .criterion(hasItem(ModItems.SEM), conditionsFromItem(ModItems.SEM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PEMERALD_SWORD)
                .pattern(" A ").pattern(" A ").pattern(" B ")
                .input('A', ModItems.SEM).input('B', Items.STICK)
                .criterion(hasItem(ModItems.SEM), conditionsFromItem(ModItems.SEM)).offerTo(exporter);

        // =====================================================================
        // Polished Obsidian (pobsidian) tools
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.POBSIDIAN_HOE)
                .pattern("AA ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.OBINGOT).input('B', Items.STICK)
                .criterion(hasItem(ModItems.OBINGOT), conditionsFromItem(ModItems.OBINGOT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.POBSIDIAN_AXE)
                .pattern("AA ").pattern("AB ").pattern(" B ")
                .input('A', ModItems.OBINGOT).input('B', Items.STICK)
                .criterion(hasItem(ModItems.OBINGOT), conditionsFromItem(ModItems.OBINGOT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.POBSIDIAN_SHOVEL)
                .pattern(" A ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.OBINGOT).input('B', Items.STICK)
                .criterion(hasItem(ModItems.OBINGOT), conditionsFromItem(ModItems.OBINGOT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.POBSIDIAN_PICKAXE)
                .pattern("AAA").pattern(" B ").pattern(" B ")
                .input('A', ModItems.OBINGOT).input('B', Items.STICK)
                .criterion(hasItem(ModItems.OBINGOT), conditionsFromItem(ModItems.OBINGOT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.POBSIDIAN_SWORD)
                .pattern(" A ").pattern(" A ").pattern(" B ")
                .input('A', ModItems.OBINGOT).input('B', Items.STICK)
                .criterion(hasItem(ModItems.OBINGOT), conditionsFromItem(ModItems.OBINGOT)).offerTo(exporter);

        // =====================================================================
        // Rough Obsidian (robsidian) tools
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ROBSIDIAN_HOE)
                .pattern("AA ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.OBSHARD).input('B', Items.STICK)
                .criterion(hasItem(ModItems.OBSHARD), conditionsFromItem(ModItems.OBSHARD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ROBSIDIAN_AXE)
                .pattern("AA ").pattern("AB ").pattern(" B ")
                .input('A', ModItems.OBSHARD).input('B', Items.STICK)
                .criterion(hasItem(ModItems.OBSHARD), conditionsFromItem(ModItems.OBSHARD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ROBSIDIAN_SHOVEL)
                .pattern(" A ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.OBSHARD).input('B', Items.STICK)
                .criterion(hasItem(ModItems.OBSHARD), conditionsFromItem(ModItems.OBSHARD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ROBSIDIAN_PICKAXE)
                .pattern("AAA").pattern(" B ").pattern(" B ")
                .input('A', ModItems.OBSHARD).input('B', Items.STICK)
                .criterion(hasItem(ModItems.OBSHARD), conditionsFromItem(ModItems.OBSHARD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ROBSIDIAN_SWORD)
                .pattern(" A ").pattern(" A ").pattern(" B ")
                .input('A', ModItems.OBSHARD).input('B', Items.STICK)
                .criterion(hasItem(ModItems.OBSHARD), conditionsFromItem(ModItems.OBSHARD)).offerTo(exporter);

        // =====================================================================
        // Overpower tools
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.OVERPOWER_AXE)
                .pattern("AAE").pattern("DBD").pattern("CB ")
                .input('C', ModBlocks.SOBLOCK).input('B', Items.STICK).input('A', Blocks.DIAMOND_BLOCK).input('D', ModItems.RGOLD).input('E', ModItems.SEM)
                .criterion(hasItem(ModBlocks.SOBLOCK), conditionsFromItem(ModBlocks.SOBLOCK)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.OVERPOWER_SHOVEL)
                .pattern("EAE").pattern("DBD").pattern("CB ")
                .input('C', ModBlocks.SOBLOCK).input('B', Items.STICK).input('A', Blocks.DIAMOND_BLOCK).input('D', ModItems.RGOLD).input('E', ModItems.SEM)
                .criterion(hasItem(ModBlocks.SOBLOCK), conditionsFromItem(ModBlocks.SOBLOCK)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.OVERPOWER_PICKAXE)
                .pattern("AAA").pattern("DBE").pattern("CB ")
                .input('C', ModBlocks.SOBLOCK).input('B', Items.STICK).input('A', Blocks.DIAMOND_BLOCK).input('D', ModItems.RGOLD).input('E', ModItems.SEM)
                .criterion(hasItem(ModBlocks.SOBLOCK), conditionsFromItem(ModBlocks.SOBLOCK)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.OVERPOWER_SWORD)
                .pattern("EAE").pattern("DAD").pattern("CB ")
                .input('C', ModBlocks.SOBLOCK).input('B', Items.STICK).input('A', Blocks.DIAMOND_BLOCK).input('D', ModItems.RGOLD).input('E', ModItems.SEM)
                .criterion(hasItem(ModBlocks.SOBLOCK), conditionsFromItem(ModBlocks.SOBLOCK)).offerTo(exporter);

        // =====================================================================
        // Hardened Redstone tools
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.HREDSTONE_HOE)
                .pattern("AA ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.HRED).input('B', Items.STICK)
                .criterion(hasItem(ModItems.HRED), conditionsFromItem(ModItems.HRED)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.HREDSTONE_AXE)
                .pattern("AA ").pattern("AB ").pattern(" B ")
                .input('A', ModItems.HRED).input('B', Items.STICK)
                .criterion(hasItem(ModItems.HRED), conditionsFromItem(ModItems.HRED)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.HREDSTONE_SHOVEL)
                .pattern(" A ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.HRED).input('B', Items.STICK)
                .criterion(hasItem(ModItems.HRED), conditionsFromItem(ModItems.HRED)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.HREDSTONE_PICKAXE)
                .pattern("AAA").pattern(" B ").pattern(" B ")
                .input('A', ModItems.HRED).input('B', Items.STICK)
                .criterion(hasItem(ModItems.HRED), conditionsFromItem(ModItems.HRED)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.HREDSTONE_SWORD)
                .pattern(" A ").pattern(" A ").pattern(" B ")
                .input('A', ModItems.HRED).input('B', Items.STICK)
                .criterion(hasItem(ModItems.HRED), conditionsFromItem(ModItems.HRED)).offerTo(exporter);

        // =====================================================================
        // Hardened Glowstone tools
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.HGLOWSTONE_HOE)
                .pattern("AA ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.HGLOW).input('B', Items.STICK)
                .criterion(hasItem(ModItems.HGLOW), conditionsFromItem(ModItems.HGLOW)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.HGLOWSTONE_AXE)
                .pattern("AA ").pattern("AB ").pattern(" B ")
                .input('A', ModItems.HGLOW).input('B', Items.STICK)
                .criterion(hasItem(ModItems.HGLOW), conditionsFromItem(ModItems.HGLOW)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.HGLOWSTONE_SHOVEL)
                .pattern(" A ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.HGLOW).input('B', Items.STICK)
                .criterion(hasItem(ModItems.HGLOW), conditionsFromItem(ModItems.HGLOW)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.HGLOWSTONE_PICKAXE)
                .pattern("AAA").pattern(" B ").pattern(" B ")
                .input('A', ModItems.HGLOW).input('B', Items.STICK)
                .criterion(hasItem(ModItems.HGLOW), conditionsFromItem(ModItems.HGLOW)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.HGLOWSTONE_SWORD)
                .pattern(" A ").pattern(" A ").pattern(" B ")
                .input('A', ModItems.HGLOW).input('B', Items.STICK)
                .criterion(hasItem(ModItems.HGLOW), conditionsFromItem(ModItems.HGLOW)).offerTo(exporter);

        // =====================================================================
        // Ferrous Gold tools
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RGOLD_HOE)
                .pattern("AA ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.RGOLD).input('B', Items.STICK)
                .criterion(hasItem(ModItems.RGOLD), conditionsFromItem(ModItems.RGOLD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RGOLD_AXE)
                .pattern("AA ").pattern("AB ").pattern(" B ")
                .input('A', ModItems.RGOLD).input('B', Items.STICK)
                .criterion(hasItem(ModItems.RGOLD), conditionsFromItem(ModItems.RGOLD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RGOLD_SHOVEL)
                .pattern(" A ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.RGOLD).input('B', Items.STICK)
                .criterion(hasItem(ModItems.RGOLD), conditionsFromItem(ModItems.RGOLD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RGOLD_PICKAXE)
                .pattern("AAA").pattern(" B ").pattern(" B ")
                .input('A', ModItems.RGOLD).input('B', Items.STICK)
                .criterion(hasItem(ModItems.RGOLD), conditionsFromItem(ModItems.RGOLD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RGOLD_SWORD)
                .pattern(" A ").pattern(" A ").pattern(" B ")
                .input('A', ModItems.RGOLD).input('B', Items.STICK)
                .criterion(hasItem(ModItems.RGOLD), conditionsFromItem(ModItems.RGOLD)).offerTo(exporter);

        // =====================================================================
        // Reinforced Lapis tools
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RLAPIS_HOE)
                .pattern("AA ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.RLAPIS).input('B', Items.STICK)
                .criterion(hasItem(ModItems.RLAPIS), conditionsFromItem(ModItems.RLAPIS)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RLAPIS_AXE)
                .pattern("AA ").pattern("AB ").pattern(" B ")
                .input('A', ModItems.RLAPIS).input('B', Items.STICK)
                .criterion(hasItem(ModItems.RLAPIS), conditionsFromItem(ModItems.RLAPIS)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RLAPIS_SHOVEL)
                .pattern(" A ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.RLAPIS).input('B', Items.STICK)
                .criterion(hasItem(ModItems.RLAPIS), conditionsFromItem(ModItems.RLAPIS)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RLAPIS_PICKAXE)
                .pattern("AAA").pattern(" B ").pattern(" B ")
                .input('A', ModItems.RLAPIS).input('B', Items.STICK)
                .criterion(hasItem(ModItems.RLAPIS), conditionsFromItem(ModItems.RLAPIS)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RLAPIS_SWORD)
                .pattern(" A ").pattern(" A ").pattern(" B ")
                .input('A', ModItems.RLAPIS).input('B', Items.STICK)
                .criterion(hasItem(ModItems.RLAPIS), conditionsFromItem(ModItems.RLAPIS)).offerTo(exporter);

        // =====================================================================
        // Rough Ecto tools (raw ectoplasm)
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RECTO_SWORD)
                .pattern(" A ").pattern(" A ").pattern(" B ")
                .input('A', ModItems.ECTOPLASM).input('B', Items.STICK)
                .criterion(hasItem(ModItems.ECTOPLASM), conditionsFromItem(ModItems.ECTOPLASM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RECTO_PICKAXE)
                .pattern("AAA").pattern(" B ").pattern(" B ")
                .input('A', ModItems.ECTOPLASM).input('B', Items.STICK)
                .criterion(hasItem(ModItems.ECTOPLASM), conditionsFromItem(ModItems.ECTOPLASM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RECTO_AXE)
                .pattern("AA ").pattern("AB ").pattern(" B ")
                .input('A', ModItems.ECTOPLASM).input('B', Items.STICK)
                .criterion(hasItem(ModItems.ECTOPLASM), conditionsFromItem(ModItems.ECTOPLASM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RECTO_SHOVEL)
                .pattern(" A ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.ECTOPLASM).input('B', Items.STICK)
                .criterion(hasItem(ModItems.ECTOPLASM), conditionsFromItem(ModItems.ECTOPLASM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RECTO_HOE)
                .pattern("AA ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.ECTOPLASM).input('B', Items.STICK)
                .criterion(hasItem(ModItems.ECTOPLASM), conditionsFromItem(ModItems.ECTOPLASM)).offerTo(exporter);

        // =====================================================================
        // Ecto tools (refined ectoplasm)
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ECTO_SWORD)
                .pattern(" A ").pattern(" A ").pattern(" B ")
                .input('A', ModItems.REFINED_ECTOPLASM).input('B', Items.STICK)
                .criterion(hasItem(ModItems.REFINED_ECTOPLASM), conditionsFromItem(ModItems.REFINED_ECTOPLASM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ECTO_PICKAXE)
                .pattern("AAA").pattern(" B ").pattern(" B ")
                .input('A', ModItems.REFINED_ECTOPLASM).input('B', Items.STICK)
                .criterion(hasItem(ModItems.REFINED_ECTOPLASM), conditionsFromItem(ModItems.REFINED_ECTOPLASM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ECTO_AXE)
                .pattern("AA ").pattern("AB ").pattern(" B ")
                .input('A', ModItems.REFINED_ECTOPLASM).input('B', Items.STICK)
                .criterion(hasItem(ModItems.REFINED_ECTOPLASM), conditionsFromItem(ModItems.REFINED_ECTOPLASM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ECTO_SHOVEL)
                .pattern(" A ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.REFINED_ECTOPLASM).input('B', Items.STICK)
                .criterion(hasItem(ModItems.REFINED_ECTOPLASM), conditionsFromItem(ModItems.REFINED_ECTOPLASM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ECTO_HOE)
                .pattern("AA ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.REFINED_ECTOPLASM).input('B', Items.STICK)
                .criterion(hasItem(ModItems.REFINED_ECTOPLASM), conditionsFromItem(ModItems.REFINED_ECTOPLASM)).offerTo(exporter);

        // =====================================================================
        // Coal tools
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.COAL_SWORD)
                .pattern(" A ").pattern(" A ").pattern(" B ")
                .input('A', ModItems.HARDENED_COAL).input('B', Items.STICK)
                .criterion(hasItem(ModItems.HARDENED_COAL), conditionsFromItem(ModItems.HARDENED_COAL)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.COAL_PICKAXE)
                .pattern("AAA").pattern(" B ").pattern(" B ")
                .input('A', ModItems.HARDENED_COAL).input('B', Items.STICK)
                .criterion(hasItem(ModItems.HARDENED_COAL), conditionsFromItem(ModItems.HARDENED_COAL)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.COAL_SHOVEL)
                .pattern(" A ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.HARDENED_COAL).input('B', Items.STICK)
                .criterion(hasItem(ModItems.HARDENED_COAL), conditionsFromItem(ModItems.HARDENED_COAL)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.COAL_AXE)
                .pattern("AA ").pattern("AB ").pattern(" B ")
                .input('A', ModItems.HARDENED_COAL).input('B', Items.STICK)
                .criterion(hasItem(ModItems.HARDENED_COAL), conditionsFromItem(ModItems.HARDENED_COAL)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.COAL_HOE)
                .pattern("AA ").pattern(" B ").pattern(" B ")
                .input('A', ModItems.HARDENED_COAL).input('B', Items.STICK)
                .criterion(hasItem(ModItems.HARDENED_COAL), conditionsFromItem(ModItems.HARDENED_COAL)).offerTo(exporter);

        // =====================================================================
        // Leather tools
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.LEATHER_SWORD)
                .pattern(" A ").pattern(" A ").pattern(" B ")
                .input('A', Items.LEATHER).input('B', Items.STICK)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.LEATHER_PICKAXE)
                .pattern("AAA").pattern(" B ").pattern(" B ")
                .input('A', Items.LEATHER).input('B', Items.STICK)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.LEATHER_SHOVEL)
                .pattern(" A ").pattern(" B ").pattern(" B ")
                .input('A', Items.LEATHER).input('B', Items.STICK)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.LEATHER_AXE)
                .pattern("AA ").pattern("AB ").pattern(" B ")
                .input('A', Items.LEATHER).input('B', Items.STICK)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.LEATHER_HOE)
                .pattern("AA ").pattern(" B ").pattern(" B ")
                .input('A', Items.LEATHER).input('B', Items.STICK)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER)).offerTo(exporter);

        // =====================================================================
        // Cake tools + armor
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CAKE_SWORD)
                .pattern(" A ").pattern(" A ").pattern(" B ")
                .input('A', Items.CAKE).input('B', Items.STICK)
                .criterion(hasItem(Items.CAKE), conditionsFromItem(Items.CAKE)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CAKE_PICKAXE)
                .pattern("AAA").pattern(" B ").pattern(" B ")
                .input('A', Items.CAKE).input('B', Items.STICK)
                .criterion(hasItem(Items.CAKE), conditionsFromItem(Items.CAKE)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CAKE_SHOVEL)
                .pattern(" A ").pattern(" B ").pattern(" B ")
                .input('A', Items.CAKE).input('B', Items.STICK)
                .criterion(hasItem(Items.CAKE), conditionsFromItem(Items.CAKE)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CAKE_AXE)
                .pattern("AA ").pattern("AB ").pattern(" B ")
                .input('A', Items.CAKE).input('B', Items.STICK)
                .criterion(hasItem(Items.CAKE), conditionsFromItem(Items.CAKE)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CAKE_HOE)
                .pattern("AA ").pattern(" B ").pattern(" B ")
                .input('A', Items.CAKE).input('B', Items.STICK)
                .criterion(hasItem(Items.CAKE), conditionsFromItem(Items.CAKE)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CAKE_HELMET)
                .pattern("AAA").pattern("A A").pattern("   ")
                .input('A', Items.CAKE)
                .criterion(hasItem(Items.CAKE), conditionsFromItem(Items.CAKE)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CAKE_CHESTPLATE)
                .pattern("A A").pattern("AAA").pattern("AAA")
                .input('A', Items.CAKE)
                .criterion(hasItem(Items.CAKE), conditionsFromItem(Items.CAKE)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CAKE_LEGGINGS)
                .pattern("AAA").pattern("A A").pattern("A A")
                .input('A', Items.CAKE)
                .criterion(hasItem(Items.CAKE), conditionsFromItem(Items.CAKE)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CAKE_BOOTS)
                .pattern("   ").pattern("A A").pattern("A A")
                .input('A', Items.CAKE)
                .criterion(hasItem(Items.CAKE), conditionsFromItem(Items.CAKE)).offerTo(exporter);

        // =====================================================================
        // Raw metal rough tool recipes
        // =====================================================================

        // Rough Raw Gold tools
        stoneVariantTools(exporter, ModItems.RRAW_GOLD_SWORD, ModItems.RRAW_GOLD_PICKAXE, ModItems.RRAW_GOLD_SHOVEL, ModItems.RRAW_GOLD_AXE, ModItems.RRAW_GOLD_HOE, Items.RAW_GOLD);

        // Rough Raw Copper tools
        stoneVariantTools(exporter, ModItems.RRAW_COPPER_SWORD, ModItems.RRAW_COPPER_PICKAXE, ModItems.RRAW_COPPER_SHOVEL, ModItems.RRAW_COPPER_AXE, ModItems.RRAW_COPPER_HOE, Items.RAW_COPPER);

        // Rough Raw Iron tools
        stoneVariantTools(exporter, ModItems.RRAW_IRON_SWORD, ModItems.RRAW_IRON_PICKAXE, ModItems.RRAW_IRON_SHOVEL, ModItems.RRAW_IRON_AXE, ModItems.RRAW_IRON_HOE, Items.RAW_IRON);

        // Rough Raw Ferrous Gold tools
        stoneVariantTools(exporter, ModItems.RRAW_RGOLD_SWORD, ModItems.RRAW_RGOLD_PICKAXE, ModItems.RRAW_RGOLD_SHOVEL, ModItems.RRAW_RGOLD_AXE, ModItems.RRAW_RGOLD_HOE, ModItems.RAW_RGOLD);

        // Rough Netherite Scrap tools
        stoneVariantTools(exporter, ModItems.RSCRAP_SWORD, ModItems.RSCRAP_PICKAXE, ModItems.RSCRAP_SHOVEL, ModItems.RSCRAP_AXE, ModItems.RSCRAP_HOE, Items.NETHERITE_SCRAP);

        // =====================================================================
        // Rough Amethyst tools
        // =====================================================================

        stoneVariantTools(exporter, ModItems.RAMETHYST_SWORD, ModItems.RAMETHYST_PICKAXE, ModItems.RAMETHYST_SHOVEL, ModItems.RAMETHYST_AXE, ModItems.RAMETHYST_HOE, Items.AMETHYST_SHARD);

        // =====================================================================
        // Snow tools
        // =====================================================================

        stoneVariantTools(exporter, ModItems.SNOW_SWORD, ModItems.SNOW_PICKAXE, ModItems.SNOW_SHOVEL, ModItems.SNOW_AXE, ModItems.SNOW_HOE, Items.SNOWBALL);

        // =====================================================================
        // Rough Quartz tools
        // =====================================================================

        stoneVariantTools(exporter, ModItems.RQUARTZ_SWORD, ModItems.RQUARTZ_PICKAXE, ModItems.RQUARTZ_SHOVEL, ModItems.RQUARTZ_AXE, ModItems.RQUARTZ_HOE, Items.QUARTZ);

        // =====================================================================
        // Rough Prismarine tools
        // =====================================================================

        stoneVariantTools(exporter, ModItems.RPRISM_SWORD, ModItems.RPRISM_PICKAXE, ModItems.RPRISM_SHOVEL, ModItems.RPRISM_AXE, ModItems.RPRISM_HOE, Items.PRISMARINE_SHARD);

        // =====================================================================
        // Calcified Amethyst tools + armor
        // =====================================================================

        buildFullSet(exporter, ModItems.CALCIFIED_AMETHYST,
                ModItems.CAMETHYST_SWORD, ModItems.CAMETHYST_PICKAXE, ModItems.CAMETHYST_SHOVEL,
                ModItems.CAMETHYST_AXE, ModItems.CAMETHYST_HOE,
                ModItems.CAMETHYST_HELMET, ModItems.CAMETHYST_CHESTPLATE,
                ModItems.CAMETHYST_LEGGINGS, ModItems.CAMETHYST_BOOTS);

        // =====================================================================
        // Ice (Glacial) tools + armor
        // =====================================================================

        buildFullSet(exporter, ModItems.GLACIAL_SHARD,
                ModItems.ICE_SWORD, ModItems.ICE_PICKAXE, ModItems.ICE_SHOVEL,
                ModItems.ICE_AXE, ModItems.ICE_HOE,
                ModItems.ICE_HELMET, ModItems.ICE_CHESTPLATE,
                ModItems.ICE_LEGGINGS, ModItems.ICE_BOOTS);

        // =====================================================================
        // Polished Quartz tools + armor
        // =====================================================================

        buildFullSet(exporter, ModItems.POLISHED_QUARTZ,
                ModItems.PQUARTZ_SWORD, ModItems.PQUARTZ_PICKAXE, ModItems.PQUARTZ_SHOVEL,
                ModItems.PQUARTZ_AXE, ModItems.PQUARTZ_HOE,
                ModItems.PQUARTZ_HELMET, ModItems.PQUARTZ_CHESTPLATE,
                ModItems.PQUARTZ_LEGGINGS, ModItems.PQUARTZ_BOOTS);

        // =====================================================================
        // Polished Prismarine tools + armor
        // =====================================================================

        buildFullSet(exporter, ModItems.POLISHED_PRISMARINE,
                ModItems.PPRISM_SWORD, ModItems.PPRISM_PICKAXE, ModItems.PPRISM_SHOVEL,
                ModItems.PPRISM_AXE, ModItems.PPRISM_HOE,
                ModItems.PPRISM_HELMET, ModItems.PPRISM_CHESTPLATE,
                ModItems.PPRISM_LEGGINGS, ModItems.PPRISM_BOOTS);

        // =====================================================================
        // All armor sets
        // =====================================================================

        // Emerald armor
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.EMERALD_CHESTPLATE)
                .pattern("A A").pattern("AAA").pattern("AAA")
                .input('A', ModItems.SEM)
                .criterion(hasItem(ModItems.SEM), conditionsFromItem(ModItems.SEM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.EMERALD_BOOTS)
                .pattern("   ").pattern("A A").pattern("A A")
                .input('A', ModItems.SEM)
                .criterion(hasItem(ModItems.SEM), conditionsFromItem(ModItems.SEM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.EMERALD_LEGGINGS)
                .pattern("AAA").pattern("A A").pattern("A A")
                .input('A', ModItems.SEM)
                .criterion(hasItem(ModItems.SEM), conditionsFromItem(ModItems.SEM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.EMERALD_HELMET)
                .pattern("AAA").pattern("A A").pattern("   ")
                .input('A', ModItems.SEM)
                .criterion(hasItem(ModItems.SEM), conditionsFromItem(ModItems.SEM)).offerTo(exporter);

        // Hardened Redstone armor
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.HRED_CHESTPLATE)
                .pattern("A A").pattern("AAA").pattern("AAA")
                .input('A', ModItems.HRED)
                .criterion(hasItem(ModItems.HRED), conditionsFromItem(ModItems.HRED)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.HRED_BOOTS)
                .pattern("   ").pattern("A A").pattern("A A")
                .input('A', ModItems.HRED)
                .criterion(hasItem(ModItems.HRED), conditionsFromItem(ModItems.HRED)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.HRED_LEGGINGS)
                .pattern("AAA").pattern("A A").pattern("A A")
                .input('A', ModItems.HRED)
                .criterion(hasItem(ModItems.HRED), conditionsFromItem(ModItems.HRED)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.HRED_HELMET)
                .pattern("AAA").pattern("A A").pattern("   ")
                .input('A', ModItems.HRED)
                .criterion(hasItem(ModItems.HRED), conditionsFromItem(ModItems.HRED)).offerTo(exporter);

        // Hardened Glowstone armor
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.HGLOW_CHESTPLATE)
                .pattern("A A").pattern("AAA").pattern("AAA")
                .input('A', ModItems.HGLOW)
                .criterion(hasItem(ModItems.HGLOW), conditionsFromItem(ModItems.HGLOW)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.HGLOW_BOOTS)
                .pattern("   ").pattern("A A").pattern("A A")
                .input('A', ModItems.HGLOW)
                .criterion(hasItem(ModItems.HGLOW), conditionsFromItem(ModItems.HGLOW)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.HGLOW_LEGGINGS)
                .pattern("AAA").pattern("A A").pattern("A A")
                .input('A', ModItems.HGLOW)
                .criterion(hasItem(ModItems.HGLOW), conditionsFromItem(ModItems.HGLOW)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.HGLOW_HELMET)
                .pattern("AAA").pattern("A A").pattern("   ")
                .input('A', ModItems.HGLOW)
                .criterion(hasItem(ModItems.HGLOW), conditionsFromItem(ModItems.HGLOW)).offerTo(exporter);

        // Obsidian armor
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.OBSIDIAN_CHESTPLATE)
                .pattern("A A").pattern("AAA").pattern("AAA")
                .input('A', ModItems.OBINGOT)
                .criterion(hasItem(ModItems.OBINGOT), conditionsFromItem(ModItems.OBINGOT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.OBSIDIAN_BOOTS)
                .pattern("   ").pattern("A A").pattern("A A")
                .input('A', ModItems.OBINGOT)
                .criterion(hasItem(ModItems.OBINGOT), conditionsFromItem(ModItems.OBINGOT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.OBSIDIAN_LEGGINGS)
                .pattern("AAA").pattern("A A").pattern("A A")
                .input('A', ModItems.OBINGOT)
                .criterion(hasItem(ModItems.OBINGOT), conditionsFromItem(ModItems.OBINGOT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.OBSIDIAN_HELMET)
                .pattern("AAA").pattern("A A").pattern("   ")
                .input('A', ModItems.OBINGOT)
                .criterion(hasItem(ModItems.OBINGOT), conditionsFromItem(ModItems.OBINGOT)).offerTo(exporter);

        // Ferrous Gold armor
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RGOLD_CHESTPLATE)
                .pattern("A A").pattern("AAA").pattern("AAA")
                .input('A', ModItems.RGOLD)
                .criterion(hasItem(ModItems.RGOLD), conditionsFromItem(ModItems.RGOLD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RGOLD_BOOTS)
                .pattern("   ").pattern("A A").pattern("A A")
                .input('A', ModItems.RGOLD)
                .criterion(hasItem(ModItems.RGOLD), conditionsFromItem(ModItems.RGOLD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RGOLD_LEGGINGS)
                .pattern("AAA").pattern("A A").pattern("A A")
                .input('A', ModItems.RGOLD)
                .criterion(hasItem(ModItems.RGOLD), conditionsFromItem(ModItems.RGOLD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RGOLD_HELMET)
                .pattern("AAA").pattern("A A").pattern("   ")
                .input('A', ModItems.RGOLD)
                .criterion(hasItem(ModItems.RGOLD), conditionsFromItem(ModItems.RGOLD)).offerTo(exporter);

        // Reinforced Lapis armor
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RLAPIS_CHESTPLATE)
                .pattern("A A").pattern("AAA").pattern("AAA")
                .input('A', ModItems.RLAPIS)
                .criterion(hasItem(ModItems.RLAPIS), conditionsFromItem(ModItems.RLAPIS)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RLAPIS_BOOTS)
                .pattern("   ").pattern("A A").pattern("A A")
                .input('A', ModItems.RLAPIS)
                .criterion(hasItem(ModItems.RLAPIS), conditionsFromItem(ModItems.RLAPIS)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RLAPIS_LEGGINGS)
                .pattern("AAA").pattern("A A").pattern("A A")
                .input('A', ModItems.RLAPIS)
                .criterion(hasItem(ModItems.RLAPIS), conditionsFromItem(ModItems.RLAPIS)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RLAPIS_HELMET)
                .pattern("AAA").pattern("A A").pattern("   ")
                .input('A', ModItems.RLAPIS)
                .criterion(hasItem(ModItems.RLAPIS), conditionsFromItem(ModItems.RLAPIS)).offerTo(exporter);

        // Overpower armor
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.OVERPOWER_CHESTPLATE)
                .pattern("ACA").pattern("ABA").pattern("ADB")
                .input('A', Items.DIAMOND_BLOCK).input('B', ModItems.OBINGOT).input('C', ModItems.SEM).input('D', ModItems.RGOLD)
                .criterion(hasItem(Items.DIAMOND_BLOCK), conditionsFromItem(Items.DIAMOND_BLOCK)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.OVERPOWER_BOOTS)
                .pattern("ACA").pattern("ABA").pattern(" D ")
                .input('A', Items.DIAMOND_BLOCK).input('B', ModItems.OBINGOT).input('C', ModItems.SEM).input('D', ModItems.RGOLD)
                .criterion(hasItem(Items.DIAMOND_BLOCK), conditionsFromItem(Items.DIAMOND_BLOCK)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.OVERPOWER_LEGGINGS)
                .pattern("AAA").pattern("ABA").pattern("ACA")
                .input('A', Items.DIAMOND_BLOCK).input('B', ModItems.OBINGOT).input('C', ModItems.SEM)
                .criterion(hasItem(Items.DIAMOND_BLOCK), conditionsFromItem(Items.DIAMOND_BLOCK)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.OVERPOWER_HELMET)
                .pattern("AAA").pattern("ABA").pattern("CDC")
                .input('A', Items.DIAMOND_BLOCK).input('B', ModItems.OBINGOT).input('C', ModItems.SEM).input('D', ModItems.RGOLD)
                .criterion(hasItem(Items.DIAMOND_BLOCK), conditionsFromItem(Items.DIAMOND_BLOCK)).offerTo(exporter);

        // Ecto armor
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.ECTO_HELMET)
                .pattern("AAA").pattern("A A").pattern("   ")
                .input('A', ModItems.REFINED_ECTOPLASM)
                .criterion(hasItem(ModItems.REFINED_ECTOPLASM), conditionsFromItem(ModItems.REFINED_ECTOPLASM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.ECTO_CHESTPLATE)
                .pattern("A A").pattern("AAA").pattern("AAA")
                .input('A', ModItems.REFINED_ECTOPLASM)
                .criterion(hasItem(ModItems.REFINED_ECTOPLASM), conditionsFromItem(ModItems.REFINED_ECTOPLASM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.ECTO_LEGGINGS)
                .pattern("AAA").pattern("A A").pattern("A A")
                .input('A', ModItems.REFINED_ECTOPLASM)
                .criterion(hasItem(ModItems.REFINED_ECTOPLASM), conditionsFromItem(ModItems.REFINED_ECTOPLASM)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.ECTO_BOOTS)
                .pattern("   ").pattern("A A").pattern("A A")
                .input('A', ModItems.REFINED_ECTOPLASM)
                .criterion(hasItem(ModItems.REFINED_ECTOPLASM), conditionsFromItem(ModItems.REFINED_ECTOPLASM)).offerTo(exporter);

        // Coal armor
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.COAL_HELMET)
                .pattern("AAA").pattern("A A").pattern("   ")
                .input('A', ModItems.HARDENED_COAL)
                .criterion(hasItem(ModItems.HARDENED_COAL), conditionsFromItem(ModItems.HARDENED_COAL)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.COAL_CHESTPLATE)
                .pattern("A A").pattern("AAA").pattern("AAA")
                .input('A', ModItems.HARDENED_COAL)
                .criterion(hasItem(ModItems.HARDENED_COAL), conditionsFromItem(ModItems.HARDENED_COAL)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.COAL_LEGGINGS)
                .pattern("AAA").pattern("A A").pattern("A A")
                .input('A', ModItems.HARDENED_COAL)
                .criterion(hasItem(ModItems.HARDENED_COAL), conditionsFromItem(ModItems.HARDENED_COAL)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.COAL_BOOTS)
                .pattern("   ").pattern("A A").pattern("A A")
                .input('A', ModItems.HARDENED_COAL)
                .criterion(hasItem(ModItems.HARDENED_COAL), conditionsFromItem(ModItems.HARDENED_COAL)).offerTo(exporter);

        // =====================================================================
        // Food tool + armor sets (11 sets)
        // =====================================================================

        buildFullSet(exporter, Items.BREAD,
                ModItems.BREAD_SWORD, ModItems.BREAD_PICKAXE, ModItems.BREAD_SHOVEL,
                ModItems.BREAD_AXE, ModItems.BREAD_HOE,
                ModItems.BREAD_HELMET, ModItems.BREAD_CHESTPLATE,
                ModItems.BREAD_LEGGINGS, ModItems.BREAD_BOOTS);

        buildFullSet(exporter, Items.DRIED_KELP,
                ModItems.DRIED_KELP_SWORD, ModItems.DRIED_KELP_PICKAXE, ModItems.DRIED_KELP_SHOVEL,
                ModItems.DRIED_KELP_AXE, ModItems.DRIED_KELP_HOE,
                ModItems.DRIED_KELP_HELMET, ModItems.DRIED_KELP_CHESTPLATE,
                ModItems.DRIED_KELP_LEGGINGS, ModItems.DRIED_KELP_BOOTS);

        buildFullSet(exporter, Items.ROTTEN_FLESH,
                ModItems.ROTTEN_FLESH_SWORD, ModItems.ROTTEN_FLESH_PICKAXE, ModItems.ROTTEN_FLESH_SHOVEL,
                ModItems.ROTTEN_FLESH_AXE, ModItems.ROTTEN_FLESH_HOE,
                ModItems.ROTTEN_FLESH_HELMET, ModItems.ROTTEN_FLESH_CHESTPLATE,
                ModItems.ROTTEN_FLESH_LEGGINGS, ModItems.ROTTEN_FLESH_BOOTS);

        buildFullSet(exporter, Items.MELON_SLICE,
                ModItems.MELON_SWORD, ModItems.MELON_PICKAXE, ModItems.MELON_SHOVEL,
                ModItems.MELON_AXE, ModItems.MELON_HOE,
                ModItems.MELON_HELMET, ModItems.MELON_CHESTPLATE,
                ModItems.MELON_LEGGINGS, ModItems.MELON_BOOTS);

        buildFullSet(exporter, Items.SWEET_BERRIES,
                ModItems.SWEET_BERRY_SWORD, ModItems.SWEET_BERRY_PICKAXE, ModItems.SWEET_BERRY_SHOVEL,
                ModItems.SWEET_BERRY_AXE, ModItems.SWEET_BERRY_HOE,
                ModItems.SWEET_BERRY_HELMET, ModItems.SWEET_BERRY_CHESTPLATE,
                ModItems.SWEET_BERRY_LEGGINGS, ModItems.SWEET_BERRY_BOOTS);

        buildFullSet(exporter, Items.PUMPKIN_PIE,
                ModItems.PUMPKIN_PIE_SWORD, ModItems.PUMPKIN_PIE_PICKAXE, ModItems.PUMPKIN_PIE_SHOVEL,
                ModItems.PUMPKIN_PIE_AXE, ModItems.PUMPKIN_PIE_HOE,
                ModItems.PUMPKIN_PIE_HELMET, ModItems.PUMPKIN_PIE_CHESTPLATE,
                ModItems.PUMPKIN_PIE_LEGGINGS, ModItems.PUMPKIN_PIE_BOOTS);

        buildFullSet(exporter, Items.RED_MUSHROOM,
                ModItems.MUSHROOM_SWORD, ModItems.MUSHROOM_PICKAXE, ModItems.MUSHROOM_SHOVEL,
                ModItems.MUSHROOM_AXE, ModItems.MUSHROOM_HOE,
                ModItems.MUSHROOM_HELMET, ModItems.MUSHROOM_CHESTPLATE,
                ModItems.MUSHROOM_LEGGINGS, ModItems.MUSHROOM_BOOTS);

        buildFullSet(exporter, Items.PUFFERFISH,
                ModItems.PUFFERFISH_SWORD, ModItems.PUFFERFISH_PICKAXE, ModItems.PUFFERFISH_SHOVEL,
                ModItems.PUFFERFISH_AXE, ModItems.PUFFERFISH_HOE,
                ModItems.PUFFERFISH_HELMET, ModItems.PUFFERFISH_CHESTPLATE,
                ModItems.PUFFERFISH_LEGGINGS, ModItems.PUFFERFISH_BOOTS);

        buildFullSet(exporter, Items.HONEY_BOTTLE,
                ModItems.HONEY_SWORD, ModItems.HONEY_PICKAXE, ModItems.HONEY_SHOVEL,
                ModItems.HONEY_AXE, ModItems.HONEY_HOE,
                ModItems.HONEY_HELMET, ModItems.HONEY_CHESTPLATE,
                ModItems.HONEY_LEGGINGS, ModItems.HONEY_BOOTS);

        buildFullSet(exporter, Items.CHORUS_FRUIT,
                ModItems.CHORUS_FRUIT_SWORD, ModItems.CHORUS_FRUIT_PICKAXE, ModItems.CHORUS_FRUIT_SHOVEL,
                ModItems.CHORUS_FRUIT_AXE, ModItems.CHORUS_FRUIT_HOE,
                ModItems.CHORUS_FRUIT_HELMET, ModItems.CHORUS_FRUIT_CHESTPLATE,
                ModItems.CHORUS_FRUIT_LEGGINGS, ModItems.CHORUS_FRUIT_BOOTS);

        buildFullSet(exporter, Items.GOLDEN_APPLE,
                ModItems.GOLDEN_APPLE_SWORD, ModItems.GOLDEN_APPLE_PICKAXE, ModItems.GOLDEN_APPLE_SHOVEL,
                ModItems.GOLDEN_APPLE_AXE, ModItems.GOLDEN_APPLE_HOE,
                ModItems.GOLDEN_APPLE_HELMET, ModItems.GOLDEN_APPLE_CHESTPLATE,
                ModItems.GOLDEN_APPLE_LEGGINGS, ModItems.GOLDEN_APPLE_BOOTS);

        // =====================================================================
        // Vanilla material tool+armor sets (tools only where indicated)
        // =====================================================================

        // Tools-only sets
        stoneVariantTools(exporter, ModItems.PAPER_SWORD, ModItems.PAPER_PICKAXE, ModItems.PAPER_SHOVEL, ModItems.PAPER_AXE, ModItems.PAPER_HOE, Items.PAPER);
        stoneVariantTools(exporter, ModItems.FEATHER_SWORD, ModItems.FEATHER_PICKAXE, ModItems.FEATHER_SHOVEL, ModItems.FEATHER_AXE, ModItems.FEATHER_HOE, Items.FEATHER);
        stoneVariantTools(exporter, ModItems.GLASS_SWORD, ModItems.GLASS_PICKAXE, ModItems.GLASS_SHOVEL, ModItems.GLASS_AXE, ModItems.GLASS_HOE, Items.GLASS_PANE);
        stoneVariantTools(exporter, ModItems.SPONGE_SWORD, ModItems.SPONGE_PICKAXE, ModItems.SPONGE_SHOVEL, ModItems.SPONGE_AXE, ModItems.SPONGE_HOE, Items.SPONGE);
        stoneVariantTools(exporter, ModItems.NETHER_WART_SWORD, ModItems.NETHER_WART_PICKAXE, ModItems.NETHER_WART_SHOVEL, ModItems.NETHER_WART_AXE, ModItems.NETHER_WART_HOE, Items.NETHER_WART);
        stoneVariantTools(exporter, ModItems.POINTED_DRIPSTONE_SWORD, ModItems.POINTED_DRIPSTONE_PICKAXE, ModItems.POINTED_DRIPSTONE_SHOVEL, ModItems.POINTED_DRIPSTONE_AXE, ModItems.POINTED_DRIPSTONE_HOE, Items.POINTED_DRIPSTONE);

        // Armor-only sets: Rabbit Hide
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RABBIT_HIDE_HELMET)
                .pattern("AAA").pattern("A A").pattern("   ")
                .input('A', Items.RABBIT_HIDE)
                .criterion(hasItem(Items.RABBIT_HIDE), conditionsFromItem(Items.RABBIT_HIDE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RABBIT_HIDE_CHESTPLATE)
                .pattern("A A").pattern("AAA").pattern("AAA")
                .input('A', Items.RABBIT_HIDE)
                .criterion(hasItem(Items.RABBIT_HIDE), conditionsFromItem(Items.RABBIT_HIDE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RABBIT_HIDE_LEGGINGS)
                .pattern("AAA").pattern("A A").pattern("A A")
                .input('A', Items.RABBIT_HIDE)
                .criterion(hasItem(Items.RABBIT_HIDE), conditionsFromItem(Items.RABBIT_HIDE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RABBIT_HIDE_BOOTS)
                .pattern("   ").pattern("A A").pattern("A A")
                .input('A', Items.RABBIT_HIDE)
                .criterion(hasItem(Items.RABBIT_HIDE), conditionsFromItem(Items.RABBIT_HIDE)).offerTo(exporter);

        // Armor-only sets: Turtle Scute
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TURTLE_SCUTE_HELMET)
                .pattern("AAA").pattern("A A").pattern("   ")
                .input('A', Items.TURTLE_SCUTE)
                .criterion(hasItem(Items.TURTLE_SCUTE), conditionsFromItem(Items.TURTLE_SCUTE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TURTLE_SCUTE_CHESTPLATE)
                .pattern("A A").pattern("AAA").pattern("AAA")
                .input('A', Items.TURTLE_SCUTE)
                .criterion(hasItem(Items.TURTLE_SCUTE), conditionsFromItem(Items.TURTLE_SCUTE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TURTLE_SCUTE_LEGGINGS)
                .pattern("AAA").pattern("A A").pattern("A A")
                .input('A', Items.TURTLE_SCUTE)
                .criterion(hasItem(Items.TURTLE_SCUTE), conditionsFromItem(Items.TURTLE_SCUTE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TURTLE_SCUTE_BOOTS)
                .pattern("   ").pattern("A A").pattern("A A")
                .input('A', Items.TURTLE_SCUTE)
                .criterion(hasItem(Items.TURTLE_SCUTE), conditionsFromItem(Items.TURTLE_SCUTE)).offerTo(exporter);

        // Tools+Armor sets using vanilla materials
        buildFullSet(exporter, Items.CACTUS,
                ModItems.CACTUS_SWORD, ModItems.CACTUS_PICKAXE, ModItems.CACTUS_SHOVEL,
                ModItems.CACTUS_AXE, ModItems.CACTUS_HOE,
                ModItems.CACTUS_HELMET, ModItems.CACTUS_CHESTPLATE,
                ModItems.CACTUS_LEGGINGS, ModItems.CACTUS_BOOTS);

        buildFullSet(exporter, Items.BONE,
                ModItems.BONE_SWORD, ModItems.BONE_PICKAXE, ModItems.BONE_SHOVEL,
                ModItems.BONE_AXE, ModItems.BONE_HOE,
                ModItems.BONE_HELMET, ModItems.BONE_CHESTPLATE,
                ModItems.BONE_LEGGINGS, ModItems.BONE_BOOTS);

        buildFullSet(exporter, Items.CLAY_BALL,
                ModItems.CLAY_SWORD, ModItems.CLAY_PICKAXE, ModItems.CLAY_SHOVEL,
                ModItems.CLAY_AXE, ModItems.CLAY_HOE,
                ModItems.CLAY_HELMET, ModItems.CLAY_CHESTPLATE,
                ModItems.CLAY_LEGGINGS, ModItems.CLAY_BOOTS);

        buildFullSet(exporter, Items.BRICK,
                ModItems.BRICK_SWORD, ModItems.BRICK_PICKAXE, ModItems.BRICK_SHOVEL,
                ModItems.BRICK_AXE, ModItems.BRICK_HOE,
                ModItems.BRICK_HELMET, ModItems.BRICK_CHESTPLATE,
                ModItems.BRICK_LEGGINGS, ModItems.BRICK_BOOTS);

        buildFullSet(exporter, Items.NETHER_BRICK,
                ModItems.NETHER_BRICK_SWORD, ModItems.NETHER_BRICK_PICKAXE, ModItems.NETHER_BRICK_SHOVEL,
                ModItems.NETHER_BRICK_AXE, ModItems.NETHER_BRICK_HOE,
                ModItems.NETHER_BRICK_HELMET, ModItems.NETHER_BRICK_CHESTPLATE,
                ModItems.NETHER_BRICK_LEGGINGS, ModItems.NETHER_BRICK_BOOTS);

        buildFullSet(exporter, Items.COPPER_INGOT,
                ModItems.COPPER_SWORD, ModItems.COPPER_PICKAXE, ModItems.COPPER_SHOVEL,
                ModItems.COPPER_AXE, ModItems.COPPER_HOE,
                ModItems.COPPER_HELMET, ModItems.COPPER_CHESTPLATE,
                ModItems.COPPER_LEGGINGS, ModItems.COPPER_BOOTS);

        buildFullSet(exporter, Items.PHANTOM_MEMBRANE,
                ModItems.PHANTOM_SWORD, ModItems.PHANTOM_PICKAXE, ModItems.PHANTOM_SHOVEL,
                ModItems.PHANTOM_AXE, ModItems.PHANTOM_HOE,
                ModItems.PHANTOM_HELMET, ModItems.PHANTOM_CHESTPLATE,
                ModItems.PHANTOM_LEGGINGS, ModItems.PHANTOM_BOOTS);

        buildFullSet(exporter, Items.MAGMA_CREAM,
                ModItems.MAGMA_CREAM_SWORD, ModItems.MAGMA_CREAM_PICKAXE, ModItems.MAGMA_CREAM_SHOVEL,
                ModItems.MAGMA_CREAM_AXE, ModItems.MAGMA_CREAM_HOE,
                ModItems.MAGMA_CREAM_HELMET, ModItems.MAGMA_CREAM_CHESTPLATE,
                ModItems.MAGMA_CREAM_LEGGINGS, ModItems.MAGMA_CREAM_BOOTS);

        buildFullSet(exporter, Items.SLIME_BALL,
                ModItems.SLIME_SWORD, ModItems.SLIME_PICKAXE, ModItems.SLIME_SHOVEL,
                ModItems.SLIME_AXE, ModItems.SLIME_HOE,
                ModItems.SLIME_HELMET, ModItems.SLIME_CHESTPLATE,
                ModItems.SLIME_LEGGINGS, ModItems.SLIME_BOOTS);

        buildFullSet(exporter, Items.BLAZE_ROD,
                ModItems.BLAZE_SWORD, ModItems.BLAZE_PICKAXE, ModItems.BLAZE_SHOVEL,
                ModItems.BLAZE_AXE, ModItems.BLAZE_HOE,
                ModItems.BLAZE_HELMET, ModItems.BLAZE_CHESTPLATE,
                ModItems.BLAZE_LEGGINGS, ModItems.BLAZE_BOOTS);

        buildFullSet(exporter, Items.NAUTILUS_SHELL,
                ModItems.NAUTILUS_SWORD, ModItems.NAUTILUS_PICKAXE, ModItems.NAUTILUS_SHOVEL,
                ModItems.NAUTILUS_AXE, ModItems.NAUTILUS_HOE,
                ModItems.NAUTILUS_HELMET, ModItems.NAUTILUS_CHESTPLATE,
                ModItems.NAUTILUS_LEGGINGS, ModItems.NAUTILUS_BOOTS);

        buildFullSet(exporter, Items.POPPED_CHORUS_FRUIT,
                ModItems.PURPUR_SWORD, ModItems.PURPUR_PICKAXE, ModItems.PURPUR_SHOVEL,
                ModItems.PURPUR_AXE, ModItems.PURPUR_HOE,
                ModItems.PURPUR_HELMET, ModItems.PURPUR_CHESTPLATE,
                ModItems.PURPUR_LEGGINGS, ModItems.PURPUR_BOOTS);

        buildFullSet(exporter, Items.GHAST_TEAR,
                ModItems.GHAST_TEAR_SWORD, ModItems.GHAST_TEAR_PICKAXE, ModItems.GHAST_TEAR_SHOVEL,
                ModItems.GHAST_TEAR_AXE, ModItems.GHAST_TEAR_HOE,
                ModItems.GHAST_TEAR_HELMET, ModItems.GHAST_TEAR_CHESTPLATE,
                ModItems.GHAST_TEAR_LEGGINGS, ModItems.GHAST_TEAR_BOOTS);

        buildFullSet(exporter, Items.ENDER_EYE,
                ModItems.EYE_OF_ENDER_SWORD, ModItems.EYE_OF_ENDER_PICKAXE, ModItems.EYE_OF_ENDER_SHOVEL,
                ModItems.EYE_OF_ENDER_AXE, ModItems.EYE_OF_ENDER_HOE,
                ModItems.EYE_OF_ENDER_HELMET, ModItems.EYE_OF_ENDER_CHESTPLATE,
                ModItems.EYE_OF_ENDER_LEGGINGS, ModItems.EYE_OF_ENDER_BOOTS);

        buildFullSet(exporter, Items.SHULKER_SHELL,
                ModItems.SHULKER_SWORD, ModItems.SHULKER_PICKAXE, ModItems.SHULKER_SHOVEL,
                ModItems.SHULKER_AXE, ModItems.SHULKER_HOE,
                ModItems.SHULKER_HELMET, ModItems.SHULKER_CHESTPLATE,
                ModItems.SHULKER_LEGGINGS, ModItems.SHULKER_BOOTS);

        buildFullSet(exporter, Items.ECHO_SHARD,
                ModItems.ECHO_SHARD_SWORD, ModItems.ECHO_SHARD_PICKAXE, ModItems.ECHO_SHARD_SHOVEL,
                ModItems.ECHO_SHARD_AXE, ModItems.ECHO_SHARD_HOE,
                ModItems.ECHO_SHARD_HELMET, ModItems.ECHO_SHARD_CHESTPLATE,
                ModItems.ECHO_SHARD_LEGGINGS, ModItems.ECHO_SHARD_BOOTS);

        buildFullSet(exporter, Items.DRAGON_BREATH,
                ModItems.DRAGON_BREATH_SWORD, ModItems.DRAGON_BREATH_PICKAXE, ModItems.DRAGON_BREATH_SHOVEL,
                ModItems.DRAGON_BREATH_AXE, ModItems.DRAGON_BREATH_HOE,
                ModItems.DRAGON_BREATH_HELMET, ModItems.DRAGON_BREATH_CHESTPLATE,
                ModItems.DRAGON_BREATH_LEGGINGS, ModItems.DRAGON_BREATH_BOOTS);

        // =====================================================================
        // Flint Tools (rough)
        // =====================================================================

        stoneVariantTools(exporter, ModItems.RFLINT_SWORD, ModItems.RFLINT_PICKAXE, ModItems.RFLINT_SHOVEL, ModItems.RFLINT_AXE, ModItems.RFLINT_HOE, Items.FLINT);

        // =====================================================================
        // Flint-Iron (FNI) Tools
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.FNI_SWORD)
                .pattern(" F ").pattern(" I ").pattern(" S ")
                .input('F', Items.FLINT).input('I', Items.IRON_INGOT).input('S', Items.STICK)
                .criterion(hasItem(Items.FLINT), conditionsFromItem(Items.FLINT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.FNI_PICKAXE)
                .pattern("IFI").pattern(" S ").pattern(" S ")
                .input('F', Items.FLINT).input('I', Items.IRON_INGOT).input('S', Items.STICK)
                .criterion(hasItem(Items.FLINT), conditionsFromItem(Items.FLINT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.FNI_SHOVEL)
                .pattern(" I ").pattern(" F ").pattern(" S ")
                .input('F', Items.FLINT).input('I', Items.IRON_INGOT).input('S', Items.STICK)
                .criterion(hasItem(Items.FLINT), conditionsFromItem(Items.FLINT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.FNI_AXE)
                .pattern("FI ").pattern("FS ").pattern(" S ")
                .input('F', Items.FLINT).input('I', Items.IRON_INGOT).input('S', Items.STICK)
                .criterion(hasItem(Items.FLINT), conditionsFromItem(Items.FLINT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.FNI_HOE)
                .pattern("FI ").pattern(" S ").pattern(" S ")
                .input('F', Items.FLINT).input('I', Items.IRON_INGOT).input('S', Items.STICK)
                .criterion(hasItem(Items.FLINT), conditionsFromItem(Items.FLINT)).offerTo(exporter);

        // Flint-Iron (FNI) Armor
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.FNI_HELMET)
                .pattern("IFI").pattern("I I").pattern("   ")
                .input('F', Items.FLINT).input('I', Items.IRON_INGOT)
                .criterion(hasItem(Items.FLINT), conditionsFromItem(Items.FLINT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.FNI_CHESTPLATE)
                .pattern("I I").pattern("IFI").pattern("III")
                .input('F', Items.FLINT).input('I', Items.IRON_INGOT)
                .criterion(hasItem(Items.FLINT), conditionsFromItem(Items.FLINT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.FNI_LEGGINGS)
                .pattern("IFI").pattern("I I").pattern("I I")
                .input('F', Items.FLINT).input('I', Items.IRON_INGOT)
                .criterion(hasItem(Items.FLINT), conditionsFromItem(Items.FLINT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.FNI_BOOTS)
                .pattern("   ").pattern("IFI").pattern("I I")
                .input('F', Items.FLINT).input('I', Items.IRON_INGOT)
                .criterion(hasItem(Items.FLINT), conditionsFromItem(Items.FLINT)).offerTo(exporter);

        // =====================================================================
        // Stone Rock Variant Tools
        // =====================================================================

        stoneVariantTools(exporter, ModItems.ANDESITE_SWORD, ModItems.ANDESITE_PICKAXE, ModItems.ANDESITE_SHOVEL, ModItems.ANDESITE_AXE, ModItems.ANDESITE_HOE, Items.ANDESITE);
        stoneVariantTools(exporter, ModItems.BASALT_SWORD, ModItems.BASALT_PICKAXE, ModItems.BASALT_SHOVEL, ModItems.BASALT_AXE, ModItems.BASALT_HOE, Items.BASALT);
        stoneVariantTools(exporter, ModItems.BLACKSTONE_SWORD, ModItems.BLACKSTONE_PICKAXE, ModItems.BLACKSTONE_SHOVEL, ModItems.BLACKSTONE_AXE, ModItems.BLACKSTONE_HOE, Items.BLACKSTONE);
        stoneVariantTools(exporter, ModItems.CALCITE_SWORD, ModItems.CALCITE_PICKAXE, ModItems.CALCITE_SHOVEL, ModItems.CALCITE_AXE, ModItems.CALCITE_HOE, Items.CALCITE);
        stoneVariantTools(exporter, ModItems.DEEPSLATE_SWORD, ModItems.DEEPSLATE_PICKAXE, ModItems.DEEPSLATE_SHOVEL, ModItems.DEEPSLATE_AXE, ModItems.DEEPSLATE_HOE, Items.COBBLED_DEEPSLATE);
        stoneVariantTools(exporter, ModItems.DIORITE_SWORD, ModItems.DIORITE_PICKAXE, ModItems.DIORITE_SHOVEL, ModItems.DIORITE_AXE, ModItems.DIORITE_HOE, Items.DIORITE);
        stoneVariantTools(exporter, ModItems.END_STONE_SWORD, ModItems.END_STONE_PICKAXE, ModItems.END_STONE_SHOVEL, ModItems.END_STONE_AXE, ModItems.END_STONE_HOE, Items.END_STONE);
        stoneVariantTools(exporter, ModItems.GRANITE_SWORD, ModItems.GRANITE_PICKAXE, ModItems.GRANITE_SHOVEL, ModItems.GRANITE_AXE, ModItems.GRANITE_HOE, Items.GRANITE);
        stoneVariantTools(exporter, ModItems.NETHERRACK_SWORD, ModItems.NETHERRACK_PICKAXE, ModItems.NETHERRACK_SHOVEL, ModItems.NETHERRACK_AXE, ModItems.NETHERRACK_HOE, Items.NETHERRACK);
        stoneVariantTools(exporter, ModItems.SANDSTONE_SWORD, ModItems.SANDSTONE_PICKAXE, ModItems.SANDSTONE_SHOVEL, ModItems.SANDSTONE_AXE, ModItems.SANDSTONE_HOE, Items.SANDSTONE);
        stoneVariantTools(exporter, ModItems.SMOOTH_BASALT_SWORD, ModItems.SMOOTH_BASALT_PICKAXE, ModItems.SMOOTH_BASALT_SHOVEL, ModItems.SMOOTH_BASALT_AXE, ModItems.SMOOTH_BASALT_HOE, Items.SMOOTH_BASALT);
        stoneVariantTools(exporter, ModItems.TERRACOTTA_SWORD, ModItems.TERRACOTTA_PICKAXE, ModItems.TERRACOTTA_SHOVEL, ModItems.TERRACOTTA_AXE, ModItems.TERRACOTTA_HOE, Items.TERRACOTTA);
        stoneVariantTools(exporter, ModItems.TUFF_SWORD, ModItems.TUFF_PICKAXE, ModItems.TUFF_SHOVEL, ModItems.TUFF_AXE, ModItems.TUFF_HOE, Items.TUFF);

        // =====================================================================
        // Wood Variant Tools
        // =====================================================================

        stoneVariantTools(exporter, ModItems.OAK_SWORD, ModItems.OAK_PICKAXE, ModItems.OAK_SHOVEL, ModItems.OAK_AXE, ModItems.OAK_HOE, Items.OAK_PLANKS);
        stoneVariantTools(exporter, ModItems.SPRUCE_SWORD, ModItems.SPRUCE_PICKAXE, ModItems.SPRUCE_SHOVEL, ModItems.SPRUCE_AXE, ModItems.SPRUCE_HOE, Items.SPRUCE_PLANKS);
        stoneVariantTools(exporter, ModItems.BIRCH_SWORD, ModItems.BIRCH_PICKAXE, ModItems.BIRCH_SHOVEL, ModItems.BIRCH_AXE, ModItems.BIRCH_HOE, Items.BIRCH_PLANKS);
        stoneVariantTools(exporter, ModItems.JUNGLE_SWORD, ModItems.JUNGLE_PICKAXE, ModItems.JUNGLE_SHOVEL, ModItems.JUNGLE_AXE, ModItems.JUNGLE_HOE, Items.JUNGLE_PLANKS);
        stoneVariantTools(exporter, ModItems.ACACIA_SWORD, ModItems.ACACIA_PICKAXE, ModItems.ACACIA_SHOVEL, ModItems.ACACIA_AXE, ModItems.ACACIA_HOE, Items.ACACIA_PLANKS);
        stoneVariantTools(exporter, ModItems.DARK_OAK_SWORD, ModItems.DARK_OAK_PICKAXE, ModItems.DARK_OAK_SHOVEL, ModItems.DARK_OAK_AXE, ModItems.DARK_OAK_HOE, Items.DARK_OAK_PLANKS);
        stoneVariantTools(exporter, ModItems.MANGROVE_SWORD, ModItems.MANGROVE_PICKAXE, ModItems.MANGROVE_SHOVEL, ModItems.MANGROVE_AXE, ModItems.MANGROVE_HOE, Items.MANGROVE_PLANKS);
        stoneVariantTools(exporter, ModItems.CHERRY_SWORD, ModItems.CHERRY_PICKAXE, ModItems.CHERRY_SHOVEL, ModItems.CHERRY_AXE, ModItems.CHERRY_HOE, Items.CHERRY_PLANKS);
        stoneVariantTools(exporter, ModItems.BAMBOO_SWORD, ModItems.BAMBOO_PICKAXE, ModItems.BAMBOO_SHOVEL, ModItems.BAMBOO_AXE, ModItems.BAMBOO_HOE, Items.BAMBOO_PLANKS);
        stoneVariantTools(exporter, ModItems.CRIMSON_SWORD, ModItems.CRIMSON_PICKAXE, ModItems.CRIMSON_SHOVEL, ModItems.CRIMSON_AXE, ModItems.CRIMSON_HOE, Items.CRIMSON_PLANKS);
        stoneVariantTools(exporter, ModItems.WARPED_SWORD, ModItems.WARPED_PICKAXE, ModItems.WARPED_SHOVEL, ModItems.WARPED_AXE, ModItems.WARPED_HOE, Items.WARPED_PLANKS);

        // =====================================================================
        // Grenade & Dynamite
        // =====================================================================

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.GRENADE)
                .pattern("ACA").pattern("ABA").pattern("ADA")
                .input('A', Items.IRON_INGOT)
                .input('B', Items.GUNPOWDER)
                .input('C', Items.REDSTONE)
                .input('D', Items.PAPER)
                .criterion(hasItem(Items.GUNPOWDER), conditionsFromItem(Items.GUNPOWDER)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.DYNAMITE)
                .pattern(" B ").pattern("CBC").pattern(" A ")
                .input('A', Items.PAPER)
                .input('B', Items.STRING)
                .input('C', ModItems.GRENADE)
                .criterion(hasItem(ModItems.GRENADE), conditionsFromItem(ModItems.GRENADE)).offerTo(exporter);
    }

    // =========================================================================
    // Helper: standard 5-tool set (sword, pickaxe, shovel, axe, hoe)
    // =========================================================================
    private static void stoneVariantTools(RecipeExporter out,
                                          ItemConvertible sword, ItemConvertible pickaxe,
                                          ItemConvertible shovel, ItemConvertible axe,
                                          ItemConvertible hoe, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, sword)
                .pattern(" A ").pattern(" A ").pattern(" B ")
                .input('A', material).input('B', Items.STICK)
                .criterion(hasItem(material), conditionsFromItem(material)).offerTo(out);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, pickaxe)
                .pattern("AAA").pattern(" B ").pattern(" B ")
                .input('A', material).input('B', Items.STICK)
                .criterion(hasItem(material), conditionsFromItem(material)).offerTo(out);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, shovel)
                .pattern(" A ").pattern(" B ").pattern(" B ")
                .input('A', material).input('B', Items.STICK)
                .criterion(hasItem(material), conditionsFromItem(material)).offerTo(out);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, axe)
                .pattern("AA ").pattern("AB ").pattern(" B ")
                .input('A', material).input('B', Items.STICK)
                .criterion(hasItem(material), conditionsFromItem(material)).offerTo(out);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, hoe)
                .pattern("AA ").pattern(" B ").pattern(" B ")
                .input('A', material).input('B', Items.STICK)
                .criterion(hasItem(material), conditionsFromItem(material)).offerTo(out);
    }

    // =========================================================================
    // Helper: full set (5 tools + 4 armor)
    // =========================================================================
    private static void buildFullSet(RecipeExporter out, ItemConvertible material,
                                     ItemConvertible sword, ItemConvertible pickaxe,
                                     ItemConvertible shovel, ItemConvertible axe, ItemConvertible hoe,
                                     ItemConvertible helmet, ItemConvertible chestplate,
                                     ItemConvertible leggings, ItemConvertible boots) {
        // Tools
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, sword)
                .pattern(" A ").pattern(" A ").pattern(" B ")
                .input('A', material).input('B', Items.STICK)
                .criterion(hasItem(material), conditionsFromItem(material)).offerTo(out);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, pickaxe)
                .pattern("AAA").pattern(" B ").pattern(" B ")
                .input('A', material).input('B', Items.STICK)
                .criterion(hasItem(material), conditionsFromItem(material)).offerTo(out);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, shovel)
                .pattern(" A ").pattern(" B ").pattern(" B ")
                .input('A', material).input('B', Items.STICK)
                .criterion(hasItem(material), conditionsFromItem(material)).offerTo(out);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, axe)
                .pattern("AA ").pattern("AB ").pattern(" B ")
                .input('A', material).input('B', Items.STICK)
                .criterion(hasItem(material), conditionsFromItem(material)).offerTo(out);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, hoe)
                .pattern("AA ").pattern(" B ").pattern(" B ")
                .input('A', material).input('B', Items.STICK)
                .criterion(hasItem(material), conditionsFromItem(material)).offerTo(out);
        // Armor
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, helmet)
                .pattern("AAA").pattern("A A").pattern("   ")
                .input('A', material)
                .criterion(hasItem(material), conditionsFromItem(material)).offerTo(out);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, chestplate)
                .pattern("A A").pattern("AAA").pattern("AAA")
                .input('A', material)
                .criterion(hasItem(material), conditionsFromItem(material)).offerTo(out);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, leggings)
                .pattern("AAA").pattern("A A").pattern("A A")
                .input('A', material)
                .criterion(hasItem(material), conditionsFromItem(material)).offerTo(out);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, boots)
                .pattern("   ").pattern("A A").pattern("A A")
                .input('A', material)
                .criterion(hasItem(material), conditionsFromItem(material)).offerTo(out);
    }

    // =========================================================================
    // Helper: get item registry path for recipe naming
    // =========================================================================
    // getItemPath is inherited from RecipeProvider
}
