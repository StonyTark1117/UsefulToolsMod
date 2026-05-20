package com.stonytark.usefultoolsmod.datagen;


import net.minecraft.core.registries.BuiltInRegistries;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.LinkedHashMap;


public class ModItemModelProvider extends ItemModelProvider {

    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, UsefultoolsMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.RGOLD.get());
        basicItem(ModItems.RAW_RGOLD.get());
        basicItem(ModItems.OBSHARD.get());
        basicItem(ModItems.OBINGOT.get());
        basicItem(ModItems.HRED.get());
        basicItem(ModItems.HGLOW.get());
        basicItem(ModItems.SEM.get());
        basicItem(ModItems.RLAPIS.get());

        //simpleBlockItem(ModBlocks.RGOLDBLOCK);
        //simpleBlockItem(ModBlocks.HRBLOCK);
        //simpleBlockItem(ModBlocks.RGOLDORE);
        //simpleBlockItem(ModBlocks.SEMBLOCK);
        //simpleBlockItem(ModBlocks.SOBLOCK);

        handheldItem(ModItems.REMERALD_SWORD);
        handheldItem(ModItems.REMERALD_PICKAXE);
        handheldItem(ModItems.REMERALD_SHOVEL);
        handheldItem(ModItems.REMERALD_AXE);
        handheldItem(ModItems.REMERALD_HOE);

        handheldItem(ModItems.PEMERALD_SWORD);
        handheldItem(ModItems.PEMERALD_PICKAXE);
        handheldItem(ModItems.PEMERALD_SHOVEL);
        handheldItem(ModItems.PEMERALD_AXE);
        handheldItem(ModItems.PEMERALD_HOE);

        handheldItem(ModItems.ROBSIDIAN_SWORD);
        handheldItem(ModItems.ROBSIDIAN_PICKAXE);
        handheldItem(ModItems.ROBSIDIAN_SHOVEL);
        handheldItem(ModItems.ROBSIDIAN_AXE);
        handheldItem(ModItems.ROBSIDIAN_HOE);

        handheldItem(ModItems.POBSIDIAN_SWORD);
        handheldItem(ModItems.POBSIDIAN_PICKAXE);
        handheldItem(ModItems.POBSIDIAN_SHOVEL);
        handheldItem(ModItems.POBSIDIAN_AXE);
        handheldItem(ModItems.POBSIDIAN_HOE);

        handheldItem(ModItems.OVERPOWER_SWORD);
        handheldItem(ModItems.OVERPOWER_PICKAXE);
        handheldItem(ModItems.OVERPOWER_SHOVEL);
        handheldItem(ModItems.OVERPOWER_AXE);

        handheldItem(ModItems.HREDSTONE_SWORD);
        handheldItem(ModItems.HREDSTONE_PICKAXE);
        handheldItem(ModItems.HREDSTONE_SHOVEL);
        handheldItem(ModItems.HREDSTONE_AXE);
        handheldItem(ModItems.HREDSTONE_HOE);

        handheldItem(ModItems.HGLOWSTONE_SWORD);
        handheldItem(ModItems.HGLOWSTONE_PICKAXE);
        handheldItem(ModItems.HGLOWSTONE_SHOVEL);
        handheldItem(ModItems.HGLOWSTONE_AXE);
        handheldItem(ModItems.HGLOWSTONE_HOE);

        handheldItem(ModItems.RGOLD_SWORD);
        handheldItem(ModItems.RGOLD_PICKAXE);
        handheldItem(ModItems.RGOLD_SHOVEL);
        handheldItem(ModItems.RGOLD_AXE);
        handheldItem(ModItems.RGOLD_HOE);

        handheldItem(ModItems.RLAPIS_SWORD);
        handheldItem(ModItems.RLAPIS_PICKAXE);
        handheldItem(ModItems.RLAPIS_SHOVEL);
        handheldItem(ModItems.RLAPIS_AXE);
        handheldItem(ModItems.RLAPIS_HOE);

        trimmedArmorItem(ModItems.EMERALD_HELMET);
        trimmedArmorItem(ModItems.EMERALD_CHESTPLATE);
        trimmedArmorItem(ModItems.EMERALD_LEGGINGS);
        trimmedArmorItem(ModItems.EMERALD_BOOTS);

        trimmedArmorItem(ModItems.HRED_HELMET);
        trimmedArmorItem(ModItems.HRED_CHESTPLATE);
        trimmedArmorItem(ModItems.HRED_LEGGINGS);
        trimmedArmorItem(ModItems.HRED_BOOTS);

        trimmedArmorItem(ModItems.HGLOW_HELMET);
        trimmedArmorItem(ModItems.HGLOW_CHESTPLATE);
        trimmedArmorItem(ModItems.HGLOW_LEGGINGS);
        trimmedArmorItem(ModItems.HGLOW_BOOTS);

        trimmedArmorItem(ModItems.OBSIDIAN_HELMET);
        trimmedArmorItem(ModItems.OBSIDIAN_CHESTPLATE);
        trimmedArmorItem(ModItems.OBSIDIAN_LEGGINGS);
        trimmedArmorItem(ModItems.OBSIDIAN_BOOTS);

        trimmedArmorItem(ModItems.RGOLD_HELMET);
        trimmedArmorItem(ModItems.RGOLD_CHESTPLATE);
        trimmedArmorItem(ModItems.RGOLD_LEGGINGS);
        trimmedArmorItem(ModItems.RGOLD_BOOTS);

        trimmedArmorItem(ModItems.RLAPIS_HELMET);
        trimmedArmorItem(ModItems.RLAPIS_CHESTPLATE);
        trimmedArmorItem(ModItems.RLAPIS_LEGGINGS);
        trimmedArmorItem(ModItems.RLAPIS_BOOTS);

        trimmedArmorItem(ModItems.OVERPOWER_HELMET);
        trimmedArmorItem(ModItems.OVERPOWER_CHESTPLATE);
        trimmedArmorItem(ModItems.OVERPOWER_LEGGINGS);
        trimmedArmorItem(ModItems.OVERPOWER_BOOTS);

        withExistingParent(ModItems.GHOST_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        basicItem(ModItems.ECTOPLASM.get());
        basicItem(ModItems.REFINED_ECTOPLASM.get());

        // Rough Ectoplasm tools
        handheldItem(ModItems.RECTO_SWORD); handheldItem(ModItems.RECTO_PICKAXE); handheldItem(ModItems.RECTO_SHOVEL); handheldItem(ModItems.RECTO_AXE); handheldItem(ModItems.RECTO_HOE);

        // Ectoplasm tools + armor
        handheldItem(ModItems.ECTO_SWORD);
        handheldItem(ModItems.ECTO_PICKAXE);
        handheldItem(ModItems.ECTO_SHOVEL);
        handheldItem(ModItems.ECTO_AXE);
        handheldItem(ModItems.ECTO_HOE);
        trimmedArmorItem(ModItems.ECTO_HELMET);
        trimmedArmorItem(ModItems.ECTO_CHESTPLATE);
        trimmedArmorItem(ModItems.ECTO_LEGGINGS);
        trimmedArmorItem(ModItems.ECTO_BOOTS);

        // Coal material and tools
        basicItem(ModItems.COAL_DUST.get());
        basicItem(ModItems.HARDENED_COAL.get());

        handheldItem(ModItems.COAL_SWORD);
        handheldItem(ModItems.COAL_PICKAXE);
        handheldItem(ModItems.COAL_SHOVEL);
        handheldItem(ModItems.COAL_AXE);
        handheldItem(ModItems.COAL_HOE);

        trimmedArmorItem(ModItems.COAL_HELMET);
        trimmedArmorItem(ModItems.COAL_CHESTPLATE);
        trimmedArmorItem(ModItems.COAL_LEGGINGS);
        trimmedArmorItem(ModItems.COAL_BOOTS);

        handheldItem(ModItems.LEATHER_SWORD);
        handheldItem(ModItems.LEATHER_PICKAXE);
        handheldItem(ModItems.LEATHER_SHOVEL);
        handheldItem(ModItems.LEATHER_AXE);
        handheldItem(ModItems.LEATHER_HOE);

        handheldItem(ModItems.CAKE_SWORD);
        handheldItem(ModItems.CAKE_PICKAXE);
        handheldItem(ModItems.CAKE_SHOVEL);
        handheldItem(ModItems.CAKE_AXE);
        handheldItem(ModItems.CAKE_HOE);

        basicItem(ModItems.CAKE_HELMET.get());
        basicItem(ModItems.CAKE_CHESTPLATE.get());
        basicItem(ModItems.CAKE_LEGGINGS.get());
        basicItem(ModItems.CAKE_BOOTS.get());

        // Food set tools + armor (11 sets)
        handheldItem(ModItems.BREAD_SWORD); handheldItem(ModItems.BREAD_PICKAXE);
        handheldItem(ModItems.BREAD_SHOVEL); handheldItem(ModItems.BREAD_AXE); handheldItem(ModItems.BREAD_HOE);
        basicItem(ModItems.BREAD_HELMET.get()); basicItem(ModItems.BREAD_CHESTPLATE.get());
        basicItem(ModItems.BREAD_LEGGINGS.get()); basicItem(ModItems.BREAD_BOOTS.get());

        handheldItem(ModItems.DRIED_KELP_SWORD); handheldItem(ModItems.DRIED_KELP_PICKAXE);
        handheldItem(ModItems.DRIED_KELP_SHOVEL); handheldItem(ModItems.DRIED_KELP_AXE); handheldItem(ModItems.DRIED_KELP_HOE);
        basicItem(ModItems.DRIED_KELP_HELMET.get()); basicItem(ModItems.DRIED_KELP_CHESTPLATE.get());
        basicItem(ModItems.DRIED_KELP_LEGGINGS.get()); basicItem(ModItems.DRIED_KELP_BOOTS.get());

        handheldItem(ModItems.ROTTEN_FLESH_SWORD); handheldItem(ModItems.ROTTEN_FLESH_PICKAXE);
        handheldItem(ModItems.ROTTEN_FLESH_SHOVEL); handheldItem(ModItems.ROTTEN_FLESH_AXE); handheldItem(ModItems.ROTTEN_FLESH_HOE);
        basicItem(ModItems.ROTTEN_FLESH_HELMET.get()); basicItem(ModItems.ROTTEN_FLESH_CHESTPLATE.get());
        basicItem(ModItems.ROTTEN_FLESH_LEGGINGS.get()); basicItem(ModItems.ROTTEN_FLESH_BOOTS.get());

        handheldItem(ModItems.MELON_SWORD); handheldItem(ModItems.MELON_PICKAXE);
        handheldItem(ModItems.MELON_SHOVEL); handheldItem(ModItems.MELON_AXE); handheldItem(ModItems.MELON_HOE);
        basicItem(ModItems.MELON_HELMET.get()); basicItem(ModItems.MELON_CHESTPLATE.get());
        basicItem(ModItems.MELON_LEGGINGS.get()); basicItem(ModItems.MELON_BOOTS.get());

        handheldItem(ModItems.SWEET_BERRY_SWORD); handheldItem(ModItems.SWEET_BERRY_PICKAXE);
        handheldItem(ModItems.SWEET_BERRY_SHOVEL); handheldItem(ModItems.SWEET_BERRY_AXE); handheldItem(ModItems.SWEET_BERRY_HOE);
        basicItem(ModItems.SWEET_BERRY_HELMET.get()); basicItem(ModItems.SWEET_BERRY_CHESTPLATE.get());
        basicItem(ModItems.SWEET_BERRY_LEGGINGS.get()); basicItem(ModItems.SWEET_BERRY_BOOTS.get());

        handheldItem(ModItems.PUMPKIN_PIE_SWORD); handheldItem(ModItems.PUMPKIN_PIE_PICKAXE);
        handheldItem(ModItems.PUMPKIN_PIE_SHOVEL); handheldItem(ModItems.PUMPKIN_PIE_AXE); handheldItem(ModItems.PUMPKIN_PIE_HOE);
        basicItem(ModItems.PUMPKIN_PIE_HELMET.get()); basicItem(ModItems.PUMPKIN_PIE_CHESTPLATE.get());
        basicItem(ModItems.PUMPKIN_PIE_LEGGINGS.get()); basicItem(ModItems.PUMPKIN_PIE_BOOTS.get());

        handheldItem(ModItems.MUSHROOM_SWORD); handheldItem(ModItems.MUSHROOM_PICKAXE);
        handheldItem(ModItems.MUSHROOM_SHOVEL); handheldItem(ModItems.MUSHROOM_AXE); handheldItem(ModItems.MUSHROOM_HOE);
        basicItem(ModItems.MUSHROOM_HELMET.get()); basicItem(ModItems.MUSHROOM_CHESTPLATE.get());
        basicItem(ModItems.MUSHROOM_LEGGINGS.get()); basicItem(ModItems.MUSHROOM_BOOTS.get());

        handheldItem(ModItems.PUFFERFISH_SWORD); handheldItem(ModItems.PUFFERFISH_PICKAXE);
        handheldItem(ModItems.PUFFERFISH_SHOVEL); handheldItem(ModItems.PUFFERFISH_AXE); handheldItem(ModItems.PUFFERFISH_HOE);
        basicItem(ModItems.PUFFERFISH_HELMET.get()); basicItem(ModItems.PUFFERFISH_CHESTPLATE.get());
        basicItem(ModItems.PUFFERFISH_LEGGINGS.get()); basicItem(ModItems.PUFFERFISH_BOOTS.get());

        handheldItem(ModItems.HONEY_SWORD); handheldItem(ModItems.HONEY_PICKAXE);
        handheldItem(ModItems.HONEY_SHOVEL); handheldItem(ModItems.HONEY_AXE); handheldItem(ModItems.HONEY_HOE);
        basicItem(ModItems.HONEY_HELMET.get()); basicItem(ModItems.HONEY_CHESTPLATE.get());
        basicItem(ModItems.HONEY_LEGGINGS.get()); basicItem(ModItems.HONEY_BOOTS.get());

        handheldItem(ModItems.CHORUS_FRUIT_SWORD); handheldItem(ModItems.CHORUS_FRUIT_PICKAXE);
        handheldItem(ModItems.CHORUS_FRUIT_SHOVEL); handheldItem(ModItems.CHORUS_FRUIT_AXE); handheldItem(ModItems.CHORUS_FRUIT_HOE);
        basicItem(ModItems.CHORUS_FRUIT_HELMET.get()); basicItem(ModItems.CHORUS_FRUIT_CHESTPLATE.get());
        basicItem(ModItems.CHORUS_FRUIT_LEGGINGS.get()); basicItem(ModItems.CHORUS_FRUIT_BOOTS.get());

        handheldItem(ModItems.GOLDEN_APPLE_SWORD); handheldItem(ModItems.GOLDEN_APPLE_PICKAXE);
        handheldItem(ModItems.GOLDEN_APPLE_SHOVEL); handheldItem(ModItems.GOLDEN_APPLE_AXE); handheldItem(ModItems.GOLDEN_APPLE_HOE);
        basicItem(ModItems.GOLDEN_APPLE_HELMET.get()); basicItem(ModItems.GOLDEN_APPLE_CHESTPLATE.get());
        basicItem(ModItems.GOLDEN_APPLE_LEGGINGS.get()); basicItem(ModItems.GOLDEN_APPLE_BOOTS.get());

        // Raw metal rough tools
        handheldItem(ModItems.RRAW_GOLD_SWORD);
        handheldItem(ModItems.RRAW_GOLD_PICKAXE);
        handheldItem(ModItems.RRAW_GOLD_SHOVEL);
        handheldItem(ModItems.RRAW_GOLD_AXE);
        handheldItem(ModItems.RRAW_GOLD_HOE);

        handheldItem(ModItems.RRAW_COPPER_SWORD);
        handheldItem(ModItems.RRAW_COPPER_PICKAXE);
        handheldItem(ModItems.RRAW_COPPER_SHOVEL);
        handheldItem(ModItems.RRAW_COPPER_AXE);
        handheldItem(ModItems.RRAW_COPPER_HOE);

        handheldItem(ModItems.RRAW_IRON_SWORD);
        handheldItem(ModItems.RRAW_IRON_PICKAXE);
        handheldItem(ModItems.RRAW_IRON_SHOVEL);
        handheldItem(ModItems.RRAW_IRON_AXE);
        handheldItem(ModItems.RRAW_IRON_HOE);

        handheldItem(ModItems.RRAW_RGOLD_SWORD);
        handheldItem(ModItems.RRAW_RGOLD_PICKAXE);
        handheldItem(ModItems.RRAW_RGOLD_SHOVEL);
        handheldItem(ModItems.RRAW_RGOLD_AXE);
        handheldItem(ModItems.RRAW_RGOLD_HOE);

        handheldItem(ModItems.RSCRAP_SWORD);
        handheldItem(ModItems.RSCRAP_PICKAXE);
        handheldItem(ModItems.RSCRAP_SHOVEL);
        handheldItem(ModItems.RSCRAP_AXE);
        handheldItem(ModItems.RSCRAP_HOE);

        // Crystal / element material items
        basicItem(ModItems.CALCIFIED_AMETHYST.get());
        basicItem(ModItems.GLACIAL_SHARD.get());
        basicItem(ModItems.POLISHED_QUARTZ.get());
        basicItem(ModItems.POLISHED_PRISMARINE.get());

        // Rough crystal tools
        handheldItem(ModItems.RAMETHYST_SWORD);
        handheldItem(ModItems.RAMETHYST_PICKAXE);
        handheldItem(ModItems.RAMETHYST_SHOVEL);
        handheldItem(ModItems.RAMETHYST_AXE);
        handheldItem(ModItems.RAMETHYST_HOE);

        handheldItem(ModItems.SNOW_SWORD);
        handheldItem(ModItems.SNOW_PICKAXE);
        handheldItem(ModItems.SNOW_SHOVEL);
        handheldItem(ModItems.SNOW_AXE);
        handheldItem(ModItems.SNOW_HOE);

        handheldItem(ModItems.RQUARTZ_SWORD);
        handheldItem(ModItems.RQUARTZ_PICKAXE);
        handheldItem(ModItems.RQUARTZ_SHOVEL);
        handheldItem(ModItems.RQUARTZ_AXE);
        handheldItem(ModItems.RQUARTZ_HOE);

        handheldItem(ModItems.RPRISM_SWORD);
        handheldItem(ModItems.RPRISM_PICKAXE);
        handheldItem(ModItems.RPRISM_SHOVEL);
        handheldItem(ModItems.RPRISM_AXE);
        handheldItem(ModItems.RPRISM_HOE);

        // Polished crystal tools + armor
        handheldItem(ModItems.CAMETHYST_SWORD);
        handheldItem(ModItems.CAMETHYST_PICKAXE);
        handheldItem(ModItems.CAMETHYST_SHOVEL);
        handheldItem(ModItems.CAMETHYST_AXE);
        handheldItem(ModItems.CAMETHYST_HOE);
        trimmedArmorItem(ModItems.CAMETHYST_HELMET);
        trimmedArmorItem(ModItems.CAMETHYST_CHESTPLATE);
        trimmedArmorItem(ModItems.CAMETHYST_LEGGINGS);
        trimmedArmorItem(ModItems.CAMETHYST_BOOTS);

        handheldItem(ModItems.ICE_SWORD);
        handheldItem(ModItems.ICE_PICKAXE);
        handheldItem(ModItems.ICE_SHOVEL);
        handheldItem(ModItems.ICE_AXE);
        handheldItem(ModItems.ICE_HOE);
        trimmedArmorItem(ModItems.ICE_HELMET);
        trimmedArmorItem(ModItems.ICE_CHESTPLATE);
        trimmedArmorItem(ModItems.ICE_LEGGINGS);
        trimmedArmorItem(ModItems.ICE_BOOTS);

        handheldItem(ModItems.PQUARTZ_SWORD);
        handheldItem(ModItems.PQUARTZ_PICKAXE);
        handheldItem(ModItems.PQUARTZ_SHOVEL);
        handheldItem(ModItems.PQUARTZ_AXE);
        handheldItem(ModItems.PQUARTZ_HOE);
        trimmedArmorItem(ModItems.PQUARTZ_HELMET);
        trimmedArmorItem(ModItems.PQUARTZ_CHESTPLATE);
        trimmedArmorItem(ModItems.PQUARTZ_LEGGINGS);
        trimmedArmorItem(ModItems.PQUARTZ_BOOTS);

        handheldItem(ModItems.PPRISM_SWORD);
        handheldItem(ModItems.PPRISM_PICKAXE);
        handheldItem(ModItems.PPRISM_SHOVEL);
        handheldItem(ModItems.PPRISM_AXE);
        handheldItem(ModItems.PPRISM_HOE);
        trimmedArmorItem(ModItems.PPRISM_HELMET);
        trimmedArmorItem(ModItems.PPRISM_CHESTPLATE);
        trimmedArmorItem(ModItems.PPRISM_LEGGINGS);
        trimmedArmorItem(ModItems.PPRISM_BOOTS);

        // Flint and FNI
        handheldItem(ModItems.RFLINT_SWORD); handheldItem(ModItems.RFLINT_PICKAXE); handheldItem(ModItems.RFLINT_SHOVEL); handheldItem(ModItems.RFLINT_AXE); handheldItem(ModItems.RFLINT_HOE);
        handheldItem(ModItems.FNI_SWORD); handheldItem(ModItems.FNI_PICKAXE); handheldItem(ModItems.FNI_SHOVEL); handheldItem(ModItems.FNI_AXE); handheldItem(ModItems.FNI_HOE);
        trimmedArmorItem(ModItems.FNI_HELMET);
        trimmedArmorItem(ModItems.FNI_CHESTPLATE);
        trimmedArmorItem(ModItems.FNI_LEGGINGS);
        trimmedArmorItem(ModItems.FNI_BOOTS);

        // Vanilla Material Sets (25 sets)
        // ── Paper (tools only) ──
        handheldItem(ModItems.PAPER_SWORD); handheldItem(ModItems.PAPER_PICKAXE);
        handheldItem(ModItems.PAPER_SHOVEL); handheldItem(ModItems.PAPER_AXE); handheldItem(ModItems.PAPER_HOE);
        // ── Feather (tools only) ──
        handheldItem(ModItems.FEATHER_SWORD); handheldItem(ModItems.FEATHER_PICKAXE);
        handheldItem(ModItems.FEATHER_SHOVEL); handheldItem(ModItems.FEATHER_AXE); handheldItem(ModItems.FEATHER_HOE);
        // ── Glass (tools only) ──
        handheldItem(ModItems.GLASS_SWORD); handheldItem(ModItems.GLASS_PICKAXE);
        handheldItem(ModItems.GLASS_SHOVEL); handheldItem(ModItems.GLASS_AXE); handheldItem(ModItems.GLASS_HOE);
        // ── Rabbit Hide (armor only) ──
        trimmedArmorItem(ModItems.RABBIT_HIDE_HELMET); trimmedArmorItem(ModItems.RABBIT_HIDE_CHESTPLATE);
        trimmedArmorItem(ModItems.RABBIT_HIDE_LEGGINGS); trimmedArmorItem(ModItems.RABBIT_HIDE_BOOTS);
        // ── Cactus (tools + armor) ──
        handheldItem(ModItems.CACTUS_SWORD); handheldItem(ModItems.CACTUS_PICKAXE);
        handheldItem(ModItems.CACTUS_SHOVEL); handheldItem(ModItems.CACTUS_AXE); handheldItem(ModItems.CACTUS_HOE);
        trimmedArmorItem(ModItems.CACTUS_HELMET); trimmedArmorItem(ModItems.CACTUS_CHESTPLATE);
        trimmedArmorItem(ModItems.CACTUS_LEGGINGS); trimmedArmorItem(ModItems.CACTUS_BOOTS);
        // ── Sponge (tools only) ──
        handheldItem(ModItems.SPONGE_SWORD); handheldItem(ModItems.SPONGE_PICKAXE);
        handheldItem(ModItems.SPONGE_SHOVEL); handheldItem(ModItems.SPONGE_AXE); handheldItem(ModItems.SPONGE_HOE);
        // ── Bone (tools + armor) ──
        handheldItem(ModItems.BONE_SWORD); handheldItem(ModItems.BONE_PICKAXE);
        handheldItem(ModItems.BONE_SHOVEL); handheldItem(ModItems.BONE_AXE); handheldItem(ModItems.BONE_HOE);
        trimmedArmorItem(ModItems.BONE_HELMET); trimmedArmorItem(ModItems.BONE_CHESTPLATE);
        trimmedArmorItem(ModItems.BONE_LEGGINGS); trimmedArmorItem(ModItems.BONE_BOOTS);
        // ── Clay (tools + armor) ──
        handheldItem(ModItems.CLAY_SWORD); handheldItem(ModItems.CLAY_PICKAXE);
        handheldItem(ModItems.CLAY_SHOVEL); handheldItem(ModItems.CLAY_AXE); handheldItem(ModItems.CLAY_HOE);
        trimmedArmorItem(ModItems.CLAY_HELMET); trimmedArmorItem(ModItems.CLAY_CHESTPLATE);
        trimmedArmorItem(ModItems.CLAY_LEGGINGS); trimmedArmorItem(ModItems.CLAY_BOOTS);
        // ── Nether Wart (tools only) ──
        handheldItem(ModItems.NETHER_WART_SWORD); handheldItem(ModItems.NETHER_WART_PICKAXE);
        handheldItem(ModItems.NETHER_WART_SHOVEL); handheldItem(ModItems.NETHER_WART_AXE); handheldItem(ModItems.NETHER_WART_HOE);
        // ── Brick (tools + armor) ──
        handheldItem(ModItems.BRICK_SWORD); handheldItem(ModItems.BRICK_PICKAXE);
        handheldItem(ModItems.BRICK_SHOVEL); handheldItem(ModItems.BRICK_AXE); handheldItem(ModItems.BRICK_HOE);
        trimmedArmorItem(ModItems.BRICK_HELMET); trimmedArmorItem(ModItems.BRICK_CHESTPLATE);
        trimmedArmorItem(ModItems.BRICK_LEGGINGS); trimmedArmorItem(ModItems.BRICK_BOOTS);
        // ── Nether Brick (tools + armor) ──
        handheldItem(ModItems.NETHER_BRICK_SWORD); handheldItem(ModItems.NETHER_BRICK_PICKAXE);
        handheldItem(ModItems.NETHER_BRICK_SHOVEL); handheldItem(ModItems.NETHER_BRICK_AXE); handheldItem(ModItems.NETHER_BRICK_HOE);
        trimmedArmorItem(ModItems.NETHER_BRICK_HELMET); trimmedArmorItem(ModItems.NETHER_BRICK_CHESTPLATE);
        trimmedArmorItem(ModItems.NETHER_BRICK_LEGGINGS); trimmedArmorItem(ModItems.NETHER_BRICK_BOOTS);
        // ── Pointed Dripstone (tools only) ──
        handheldItem(ModItems.POINTED_DRIPSTONE_SWORD); handheldItem(ModItems.POINTED_DRIPSTONE_PICKAXE);
        handheldItem(ModItems.POINTED_DRIPSTONE_SHOVEL); handheldItem(ModItems.POINTED_DRIPSTONE_AXE); handheldItem(ModItems.POINTED_DRIPSTONE_HOE);
        // ── Copper (tools + armor) ──
        handheldItem(ModItems.COPPER_SWORD); handheldItem(ModItems.COPPER_PICKAXE);
        handheldItem(ModItems.COPPER_SHOVEL); handheldItem(ModItems.COPPER_AXE); handheldItem(ModItems.COPPER_HOE);
        trimmedArmorItem(ModItems.COPPER_HELMET); trimmedArmorItem(ModItems.COPPER_CHESTPLATE);
        trimmedArmorItem(ModItems.COPPER_LEGGINGS); trimmedArmorItem(ModItems.COPPER_BOOTS);
        // ── Phantom Membrane (tools + armor) ──
        handheldItem(ModItems.PHANTOM_SWORD); handheldItem(ModItems.PHANTOM_PICKAXE);
        handheldItem(ModItems.PHANTOM_SHOVEL); handheldItem(ModItems.PHANTOM_AXE); handheldItem(ModItems.PHANTOM_HOE);
        trimmedArmorItem(ModItems.PHANTOM_HELMET); trimmedArmorItem(ModItems.PHANTOM_CHESTPLATE);
        trimmedArmorItem(ModItems.PHANTOM_LEGGINGS); trimmedArmorItem(ModItems.PHANTOM_BOOTS);
        // ── Magma Cream (tools + armor) ──
        handheldItem(ModItems.MAGMA_CREAM_SWORD); handheldItem(ModItems.MAGMA_CREAM_PICKAXE);
        handheldItem(ModItems.MAGMA_CREAM_SHOVEL); handheldItem(ModItems.MAGMA_CREAM_AXE); handheldItem(ModItems.MAGMA_CREAM_HOE);
        trimmedArmorItem(ModItems.MAGMA_CREAM_HELMET); trimmedArmorItem(ModItems.MAGMA_CREAM_CHESTPLATE);
        trimmedArmorItem(ModItems.MAGMA_CREAM_LEGGINGS); trimmedArmorItem(ModItems.MAGMA_CREAM_BOOTS);
        // ── Slime (tools + armor) ──
        handheldItem(ModItems.SLIME_SWORD); handheldItem(ModItems.SLIME_PICKAXE);
        handheldItem(ModItems.SLIME_SHOVEL); handheldItem(ModItems.SLIME_AXE); handheldItem(ModItems.SLIME_HOE);
        trimmedArmorItem(ModItems.SLIME_HELMET); trimmedArmorItem(ModItems.SLIME_CHESTPLATE);
        trimmedArmorItem(ModItems.SLIME_LEGGINGS); trimmedArmorItem(ModItems.SLIME_BOOTS);
        // ── Blaze Rod (tools + armor) ──
        handheldItem(ModItems.BLAZE_SWORD); handheldItem(ModItems.BLAZE_PICKAXE);
        handheldItem(ModItems.BLAZE_SHOVEL); handheldItem(ModItems.BLAZE_AXE); handheldItem(ModItems.BLAZE_HOE);
        trimmedArmorItem(ModItems.BLAZE_HELMET); trimmedArmorItem(ModItems.BLAZE_CHESTPLATE);
        trimmedArmorItem(ModItems.BLAZE_LEGGINGS); trimmedArmorItem(ModItems.BLAZE_BOOTS);
        // ── Nautilus Shell (tools + armor) ──
        handheldItem(ModItems.NAUTILUS_SWORD); handheldItem(ModItems.NAUTILUS_PICKAXE);
        handheldItem(ModItems.NAUTILUS_SHOVEL); handheldItem(ModItems.NAUTILUS_AXE); handheldItem(ModItems.NAUTILUS_HOE);
        trimmedArmorItem(ModItems.NAUTILUS_HELMET); trimmedArmorItem(ModItems.NAUTILUS_CHESTPLATE);
        trimmedArmorItem(ModItems.NAUTILUS_LEGGINGS); trimmedArmorItem(ModItems.NAUTILUS_BOOTS);
        // ── Purpur (tools + armor) ──
        handheldItem(ModItems.PURPUR_SWORD); handheldItem(ModItems.PURPUR_PICKAXE);
        handheldItem(ModItems.PURPUR_SHOVEL); handheldItem(ModItems.PURPUR_AXE); handheldItem(ModItems.PURPUR_HOE);
        trimmedArmorItem(ModItems.PURPUR_HELMET); trimmedArmorItem(ModItems.PURPUR_CHESTPLATE);
        trimmedArmorItem(ModItems.PURPUR_LEGGINGS); trimmedArmorItem(ModItems.PURPUR_BOOTS);
        // ── Ghast Tear (tools + armor) ──
        handheldItem(ModItems.GHAST_TEAR_SWORD); handheldItem(ModItems.GHAST_TEAR_PICKAXE);
        handheldItem(ModItems.GHAST_TEAR_SHOVEL); handheldItem(ModItems.GHAST_TEAR_AXE); handheldItem(ModItems.GHAST_TEAR_HOE);
        trimmedArmorItem(ModItems.GHAST_TEAR_HELMET); trimmedArmorItem(ModItems.GHAST_TEAR_CHESTPLATE);
        trimmedArmorItem(ModItems.GHAST_TEAR_LEGGINGS); trimmedArmorItem(ModItems.GHAST_TEAR_BOOTS);
        // ── Eye of Ender (tools + armor) ──
        handheldItem(ModItems.EYE_OF_ENDER_SWORD); handheldItem(ModItems.EYE_OF_ENDER_PICKAXE);
        handheldItem(ModItems.EYE_OF_ENDER_SHOVEL); handheldItem(ModItems.EYE_OF_ENDER_AXE); handheldItem(ModItems.EYE_OF_ENDER_HOE);
        trimmedArmorItem(ModItems.EYE_OF_ENDER_HELMET); trimmedArmorItem(ModItems.EYE_OF_ENDER_CHESTPLATE);
        trimmedArmorItem(ModItems.EYE_OF_ENDER_LEGGINGS); trimmedArmorItem(ModItems.EYE_OF_ENDER_BOOTS);
        // ── Shulker Shell (tools + armor) ──
        handheldItem(ModItems.SHULKER_SWORD); handheldItem(ModItems.SHULKER_PICKAXE);
        handheldItem(ModItems.SHULKER_SHOVEL); handheldItem(ModItems.SHULKER_AXE); handheldItem(ModItems.SHULKER_HOE);
        trimmedArmorItem(ModItems.SHULKER_HELMET); trimmedArmorItem(ModItems.SHULKER_CHESTPLATE);
        trimmedArmorItem(ModItems.SHULKER_LEGGINGS); trimmedArmorItem(ModItems.SHULKER_BOOTS);
        // ── Turtle Scute (armor only) ──
        trimmedArmorItem(ModItems.TURTLE_SCUTE_HELMET); trimmedArmorItem(ModItems.TURTLE_SCUTE_CHESTPLATE);
        trimmedArmorItem(ModItems.TURTLE_SCUTE_LEGGINGS); trimmedArmorItem(ModItems.TURTLE_SCUTE_BOOTS);
        // ── Echo Shard (tools + armor) ──
        handheldItem(ModItems.ECHO_SHARD_SWORD); handheldItem(ModItems.ECHO_SHARD_PICKAXE);
        handheldItem(ModItems.ECHO_SHARD_SHOVEL); handheldItem(ModItems.ECHO_SHARD_AXE); handheldItem(ModItems.ECHO_SHARD_HOE);
        trimmedArmorItem(ModItems.ECHO_SHARD_HELMET); trimmedArmorItem(ModItems.ECHO_SHARD_CHESTPLATE);
        trimmedArmorItem(ModItems.ECHO_SHARD_LEGGINGS); trimmedArmorItem(ModItems.ECHO_SHARD_BOOTS);
        // ── Dragon's Breath (tools + armor) ──
        handheldItem(ModItems.DRAGON_BREATH_SWORD); handheldItem(ModItems.DRAGON_BREATH_PICKAXE);
        handheldItem(ModItems.DRAGON_BREATH_SHOVEL); handheldItem(ModItems.DRAGON_BREATH_AXE); handheldItem(ModItems.DRAGON_BREATH_HOE);
        trimmedArmorItem(ModItems.DRAGON_BREATH_HELMET); trimmedArmorItem(ModItems.DRAGON_BREATH_CHESTPLATE);
        trimmedArmorItem(ModItems.DRAGON_BREATH_LEGGINGS); trimmedArmorItem(ModItems.DRAGON_BREATH_BOOTS);

        // Stone Rock Variants
        handheldItem(ModItems.ANDESITE_SWORD); handheldItem(ModItems.ANDESITE_PICKAXE); handheldItem(ModItems.ANDESITE_SHOVEL); handheldItem(ModItems.ANDESITE_AXE); handheldItem(ModItems.ANDESITE_HOE);
        handheldItem(ModItems.BASALT_SWORD); handheldItem(ModItems.BASALT_PICKAXE); handheldItem(ModItems.BASALT_SHOVEL); handheldItem(ModItems.BASALT_AXE); handheldItem(ModItems.BASALT_HOE);
        handheldItem(ModItems.BLACKSTONE_SWORD); handheldItem(ModItems.BLACKSTONE_PICKAXE); handheldItem(ModItems.BLACKSTONE_SHOVEL); handheldItem(ModItems.BLACKSTONE_AXE); handheldItem(ModItems.BLACKSTONE_HOE);
        handheldItem(ModItems.CALCITE_SWORD); handheldItem(ModItems.CALCITE_PICKAXE); handheldItem(ModItems.CALCITE_SHOVEL); handheldItem(ModItems.CALCITE_AXE); handheldItem(ModItems.CALCITE_HOE);
        handheldItem(ModItems.DEEPSLATE_SWORD); handheldItem(ModItems.DEEPSLATE_PICKAXE); handheldItem(ModItems.DEEPSLATE_SHOVEL); handheldItem(ModItems.DEEPSLATE_AXE); handheldItem(ModItems.DEEPSLATE_HOE);
        handheldItem(ModItems.DIORITE_SWORD); handheldItem(ModItems.DIORITE_PICKAXE); handheldItem(ModItems.DIORITE_SHOVEL); handheldItem(ModItems.DIORITE_AXE); handheldItem(ModItems.DIORITE_HOE);
        handheldItem(ModItems.END_STONE_SWORD); handheldItem(ModItems.END_STONE_PICKAXE); handheldItem(ModItems.END_STONE_SHOVEL); handheldItem(ModItems.END_STONE_AXE); handheldItem(ModItems.END_STONE_HOE);
        handheldItem(ModItems.GRANITE_SWORD); handheldItem(ModItems.GRANITE_PICKAXE); handheldItem(ModItems.GRANITE_SHOVEL); handheldItem(ModItems.GRANITE_AXE); handheldItem(ModItems.GRANITE_HOE);
        handheldItem(ModItems.NETHERRACK_SWORD); handheldItem(ModItems.NETHERRACK_PICKAXE); handheldItem(ModItems.NETHERRACK_SHOVEL); handheldItem(ModItems.NETHERRACK_AXE); handheldItem(ModItems.NETHERRACK_HOE);
        handheldItem(ModItems.SANDSTONE_SWORD); handheldItem(ModItems.SANDSTONE_PICKAXE); handheldItem(ModItems.SANDSTONE_SHOVEL); handheldItem(ModItems.SANDSTONE_AXE); handheldItem(ModItems.SANDSTONE_HOE);
        handheldItem(ModItems.SMOOTH_BASALT_SWORD); handheldItem(ModItems.SMOOTH_BASALT_PICKAXE); handheldItem(ModItems.SMOOTH_BASALT_SHOVEL); handheldItem(ModItems.SMOOTH_BASALT_AXE); handheldItem(ModItems.SMOOTH_BASALT_HOE);
        handheldItem(ModItems.TERRACOTTA_SWORD); handheldItem(ModItems.TERRACOTTA_PICKAXE); handheldItem(ModItems.TERRACOTTA_SHOVEL); handheldItem(ModItems.TERRACOTTA_AXE); handheldItem(ModItems.TERRACOTTA_HOE);
        handheldItem(ModItems.TUFF_SWORD); handheldItem(ModItems.TUFF_PICKAXE); handheldItem(ModItems.TUFF_SHOVEL); handheldItem(ModItems.TUFF_AXE); handheldItem(ModItems.TUFF_HOE);

        // Wood Variants
        handheldItem(ModItems.OAK_SWORD); handheldItem(ModItems.OAK_PICKAXE); handheldItem(ModItems.OAK_SHOVEL); handheldItem(ModItems.OAK_AXE); handheldItem(ModItems.OAK_HOE);
        handheldItem(ModItems.SPRUCE_SWORD); handheldItem(ModItems.SPRUCE_PICKAXE); handheldItem(ModItems.SPRUCE_SHOVEL); handheldItem(ModItems.SPRUCE_AXE); handheldItem(ModItems.SPRUCE_HOE);
        handheldItem(ModItems.BIRCH_SWORD); handheldItem(ModItems.BIRCH_PICKAXE); handheldItem(ModItems.BIRCH_SHOVEL); handheldItem(ModItems.BIRCH_AXE); handheldItem(ModItems.BIRCH_HOE);
        handheldItem(ModItems.JUNGLE_SWORD); handheldItem(ModItems.JUNGLE_PICKAXE); handheldItem(ModItems.JUNGLE_SHOVEL); handheldItem(ModItems.JUNGLE_AXE); handheldItem(ModItems.JUNGLE_HOE);
        handheldItem(ModItems.ACACIA_SWORD); handheldItem(ModItems.ACACIA_PICKAXE); handheldItem(ModItems.ACACIA_SHOVEL); handheldItem(ModItems.ACACIA_AXE); handheldItem(ModItems.ACACIA_HOE);
        handheldItem(ModItems.DARK_OAK_SWORD); handheldItem(ModItems.DARK_OAK_PICKAXE); handheldItem(ModItems.DARK_OAK_SHOVEL); handheldItem(ModItems.DARK_OAK_AXE); handheldItem(ModItems.DARK_OAK_HOE);
        handheldItem(ModItems.MANGROVE_SWORD); handheldItem(ModItems.MANGROVE_PICKAXE); handheldItem(ModItems.MANGROVE_SHOVEL); handheldItem(ModItems.MANGROVE_AXE); handheldItem(ModItems.MANGROVE_HOE);
        handheldItem(ModItems.CHERRY_SWORD); handheldItem(ModItems.CHERRY_PICKAXE); handheldItem(ModItems.CHERRY_SHOVEL); handheldItem(ModItems.CHERRY_AXE); handheldItem(ModItems.CHERRY_HOE);
        handheldItem(ModItems.BAMBOO_SWORD); handheldItem(ModItems.BAMBOO_PICKAXE); handheldItem(ModItems.BAMBOO_SHOVEL); handheldItem(ModItems.BAMBOO_AXE); handheldItem(ModItems.BAMBOO_HOE);
        handheldItem(ModItems.CRIMSON_SWORD); handheldItem(ModItems.CRIMSON_PICKAXE); handheldItem(ModItems.CRIMSON_SHOVEL); handheldItem(ModItems.CRIMSON_AXE); handheldItem(ModItems.CRIMSON_HOE);
        handheldItem(ModItems.WARPED_SWORD); handheldItem(ModItems.WARPED_PICKAXE); handheldItem(ModItems.WARPED_SHOVEL); handheldItem(ModItems.WARPED_AXE); handheldItem(ModItems.WARPED_HOE);
    }

    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(DeferredHolder<Item, Item> itemRegistryObject) {
        final String MOD_ID = UsefultoolsMod.MOD_ID; // Change this to your mod id

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace()  + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()));
            });
        }
    }

    private ItemModelBuilder handheldItem(DeferredHolder<Item, Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(UsefultoolsMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    public void buttonItem(DeferredHolder<? extends Block, ? extends Block> block, DeferredHolder<Block, Block> baseBlock) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(UsefultoolsMod.MOD_ID,
                        "block/" + BuiltInRegistries.BLOCK.getKey(baseBlock.get()).getPath()));
    }

    public void fenceItem(DeferredHolder<? extends Block, ? extends Block> block, DeferredHolder<Block, Block> baseBlock) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(UsefultoolsMod.MOD_ID,
                        "block/" + BuiltInRegistries.BLOCK.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(DeferredHolder<? extends Block, ? extends Block> block, DeferredHolder<Block, Block> baseBlock) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(UsefultoolsMod.MOD_ID,
                        "block/" + BuiltInRegistries.BLOCK.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(DeferredHolder<? extends Block, ? extends Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(UsefultoolsMod.MOD_ID,"item/" + item.getId().getPath()));
    }
}