package com.stonytark.usefultoolsmod.datagen;

import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

/** Loader-neutral item-tag provider for the Architectury targets. */
public final class ModItemTagProvider extends IntrinsicHolderTagsProvider<Item> {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, Registries.ITEM, lookup,
                item -> BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow());
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(ItemTags.TRIMMABLE_ARMOR).add(
                ModItems.EMERALD_HELMET.get(), ModItems.EMERALD_CHESTPLATE.get(),
                ModItems.EMERALD_LEGGINGS.get(), ModItems.EMERALD_BOOTS.get(),
                ModItems.HRED_HELMET.get(), ModItems.HRED_CHESTPLATE.get(),
                ModItems.HRED_LEGGINGS.get(), ModItems.HRED_BOOTS.get(),
                ModItems.HGLOW_HELMET.get(), ModItems.HGLOW_CHESTPLATE.get(),
                ModItems.HGLOW_LEGGINGS.get(), ModItems.HGLOW_BOOTS.get(),
                ModItems.OBSIDIAN_HELMET.get(), ModItems.OBSIDIAN_CHESTPLATE.get(),
                ModItems.OBSIDIAN_LEGGINGS.get(), ModItems.OBSIDIAN_BOOTS.get(),
                ModItems.RGOLD_HELMET.get(), ModItems.RGOLD_CHESTPLATE.get(),
                ModItems.RGOLD_LEGGINGS.get(), ModItems.RGOLD_BOOTS.get(),
                ModItems.OVERPOWER_HELMET.get(), ModItems.OVERPOWER_CHESTPLATE.get(),
                ModItems.OVERPOWER_LEGGINGS.get(), ModItems.OVERPOWER_BOOTS.get(),
                ModItems.RLAPIS_HELMET.get(), ModItems.RLAPIS_CHESTPLATE.get(),
                ModItems.RLAPIS_LEGGINGS.get(), ModItems.RLAPIS_BOOTS.get(),
                ModItems.COAL_HELMET.get(), ModItems.COAL_CHESTPLATE.get(),
                ModItems.COAL_LEGGINGS.get(), ModItems.COAL_BOOTS.get(),
                ModItems.FNI_HELMET.get(), ModItems.FNI_CHESTPLATE.get(),
                ModItems.FNI_LEGGINGS.get(), ModItems.FNI_BOOTS.get(),
                ModItems.ECTO_HELMET.get(), ModItems.ECTO_CHESTPLATE.get(),
                ModItems.ECTO_LEGGINGS.get(), ModItems.ECTO_BOOTS.get());

        tag(ModTags.Items.MAGNETIZATION_FERROMAGNETIC).add(
                ModItems.RGOLD.get(), ModItems.RAW_RGOLD.get(), ModItems.OBINGOT.get(),
                ModItems.RLAPIS.get(), ModItems.SEM.get());
        tag(ModTags.Items.MAGNETIZATION_METAL_ARMOR).add(
                ModItems.COPPER_HELMET.get(), ModItems.COPPER_CHESTPLATE.get(),
                ModItems.COPPER_LEGGINGS.get(), ModItems.COPPER_BOOTS.get(),
                ModItems.RGOLD_HELMET.get(), ModItems.RGOLD_CHESTPLATE.get(),
                ModItems.RGOLD_LEGGINGS.get(), ModItems.RGOLD_BOOTS.get(),
                ModItems.OBSIDIAN_HELMET.get(), ModItems.OBSIDIAN_CHESTPLATE.get(),
                ModItems.OBSIDIAN_LEGGINGS.get(), ModItems.OBSIDIAN_BOOTS.get(),
                ModItems.FNI_HELMET.get(), ModItems.FNI_CHESTPLATE.get(),
                ModItems.FNI_LEGGINGS.get(), ModItems.FNI_BOOTS.get(),
                ModItems.RLAPIS_HELMET.get(), ModItems.RLAPIS_CHESTPLATE.get(),
                ModItems.RLAPIS_LEGGINGS.get(), ModItems.RLAPIS_BOOTS.get(),
                ModItems.EMERALD_HELMET.get(), ModItems.EMERALD_CHESTPLATE.get(),
                ModItems.EMERALD_LEGGINGS.get(), ModItems.EMERALD_BOOTS.get(),
                ModItems.OVERPOWER_HELMET.get(), ModItems.OVERPOWER_CHESTPLATE.get(),
                ModItems.OVERPOWER_LEGGINGS.get(), ModItems.OVERPOWER_BOOTS.get());
        tag(ModTags.Items.MAGNETIZATION_METAL_TOOLS).add(
                ModItems.COPPER_SWORD.get(), ModItems.COPPER_PICKAXE.get(), ModItems.COPPER_AXE.get(),
                ModItems.COPPER_SHOVEL.get(), ModItems.COPPER_HOE.get(),
                ModItems.RGOLD_SWORD.get(), ModItems.RGOLD_PICKAXE.get(), ModItems.RGOLD_AXE.get(),
                ModItems.RGOLD_SHOVEL.get(), ModItems.RGOLD_HOE.get(),
                ModItems.POBSIDIAN_SWORD.get(), ModItems.POBSIDIAN_PICKAXE.get(), ModItems.POBSIDIAN_AXE.get(),
                ModItems.POBSIDIAN_SHOVEL.get(), ModItems.POBSIDIAN_HOE.get(),
                ModItems.FNI_SWORD.get(), ModItems.FNI_PICKAXE.get(), ModItems.FNI_AXE.get(),
                ModItems.FNI_SHOVEL.get(), ModItems.FNI_HOE.get(),
                ModItems.RRAW_GOLD_SWORD.get(), ModItems.RRAW_GOLD_PICKAXE.get(), ModItems.RRAW_GOLD_AXE.get(),
                ModItems.RRAW_GOLD_SHOVEL.get(), ModItems.RRAW_GOLD_HOE.get(),
                ModItems.RRAW_COPPER_SWORD.get(), ModItems.RRAW_COPPER_PICKAXE.get(), ModItems.RRAW_COPPER_AXE.get(),
                ModItems.RRAW_COPPER_SHOVEL.get(), ModItems.RRAW_COPPER_HOE.get(),
                ModItems.RRAW_IRON_SWORD.get(), ModItems.RRAW_IRON_PICKAXE.get(), ModItems.RRAW_IRON_AXE.get(),
                ModItems.RRAW_IRON_SHOVEL.get(), ModItems.RRAW_IRON_HOE.get(),
                ModItems.RRAW_RGOLD_SWORD.get(), ModItems.RRAW_RGOLD_PICKAXE.get(), ModItems.RRAW_RGOLD_AXE.get(),
                ModItems.RRAW_RGOLD_SHOVEL.get(), ModItems.RRAW_RGOLD_HOE.get(),
                ModItems.RSCRAP_SWORD.get(), ModItems.RSCRAP_PICKAXE.get(), ModItems.RSCRAP_AXE.get(),
                ModItems.RSCRAP_SHOVEL.get(), ModItems.RSCRAP_HOE.get(),
                ModItems.RLAPIS_SWORD.get(), ModItems.RLAPIS_PICKAXE.get(), ModItems.RLAPIS_AXE.get(),
                ModItems.RLAPIS_SHOVEL.get(), ModItems.RLAPIS_HOE.get(),
                ModItems.PEMERALD_SWORD.get(), ModItems.PEMERALD_PICKAXE.get(), ModItems.PEMERALD_AXE.get(),
                ModItems.PEMERALD_SHOVEL.get(), ModItems.PEMERALD_HOE.get(),
                ModItems.OVERPOWER_SWORD.get(), ModItems.OVERPOWER_PICKAXE.get(),
                ModItems.OVERPOWER_AXE.get(), ModItems.OVERPOWER_SHOVEL.get());
    }
}
