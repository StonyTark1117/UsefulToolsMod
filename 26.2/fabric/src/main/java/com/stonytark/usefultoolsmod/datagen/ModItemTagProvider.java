package com.stonytark.usefultoolsmod.datagen;

import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

    private static ResourceKey<Item> key(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item)
                .orElseThrow(() -> new IllegalStateException("Unregistered item in tag provider: " + item));
    }

    public ModItemTagProvider(FabricPackOutput output,
                              CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        builder(ItemTags.TRIMMABLE_ARMOR)
                .add(key(ModItems.EMERALD_HELMET))
                .add(key(ModItems.EMERALD_CHESTPLATE))
                .add(key(ModItems.EMERALD_LEGGINGS))
                .add(key(ModItems.EMERALD_BOOTS))
                .add(key(ModItems.HRED_HELMET))
                .add(key(ModItems.HRED_CHESTPLATE))
                .add(key(ModItems.HRED_LEGGINGS))
                .add(key(ModItems.HRED_BOOTS))
                .add(key(ModItems.HGLOW_HELMET))
                .add(key(ModItems.HGLOW_CHESTPLATE))
                .add(key(ModItems.HGLOW_LEGGINGS))
                .add(key(ModItems.HGLOW_BOOTS))
                .add(key(ModItems.OBSIDIAN_HELMET))
                .add(key(ModItems.OBSIDIAN_CHESTPLATE))
                .add(key(ModItems.OBSIDIAN_LEGGINGS))
                .add(key(ModItems.OBSIDIAN_BOOTS))
                .add(key(ModItems.RGOLD_HELMET))
                .add(key(ModItems.RGOLD_CHESTPLATE))
                .add(key(ModItems.RGOLD_LEGGINGS))
                .add(key(ModItems.RGOLD_BOOTS))
                .add(key(ModItems.OVERPOWER_HELMET))
                .add(key(ModItems.OVERPOWER_CHESTPLATE))
                .add(key(ModItems.OVERPOWER_LEGGINGS))
                .add(key(ModItems.OVERPOWER_BOOTS))
                .add(key(ModItems.RLAPIS_HELMET))
                .add(key(ModItems.RLAPIS_CHESTPLATE))
                .add(key(ModItems.RLAPIS_LEGGINGS))
                .add(key(ModItems.RLAPIS_BOOTS))
                .add(key(ModItems.COAL_HELMET))
                .add(key(ModItems.COAL_CHESTPLATE))
                .add(key(ModItems.COAL_LEGGINGS))
                .add(key(ModItems.COAL_BOOTS))
                .add(key(ModItems.FNI_HELMET))
                .add(key(ModItems.FNI_CHESTPLATE))
                .add(key(ModItems.FNI_LEGGINGS))
                .add(key(ModItems.FNI_BOOTS))
                .add(key(ModItems.ECTO_HELMET))
                .add(key(ModItems.ECTO_CHESTPLATE))
                .add(key(ModItems.ECTO_LEGGINGS))
                .add(key(ModItems.ECTO_BOOTS));

        // Magnetization addon: tag references external namespace; if Magnetization
        // isn't installed the tags simply don't resolve.
        builder(ModTags.Items.MAGNETIZATION_FERROMAGNETIC)
                .add(key(ModItems.RGOLD))
                .add(key(ModItems.RAW_RGOLD))
                .add(key(ModItems.OBINGOT))
                .add(key(ModItems.RLAPIS))
                .add(key(ModItems.SEM));

        builder(ModTags.Items.MAGNETIZATION_METAL_ARMOR)
                .add(key(ModItems.COPPER_HELMET))
                .add(key(ModItems.COPPER_CHESTPLATE))
                .add(key(ModItems.COPPER_LEGGINGS))
                .add(key(ModItems.COPPER_BOOTS))
                .add(key(ModItems.RGOLD_HELMET))
                .add(key(ModItems.RGOLD_CHESTPLATE))
                .add(key(ModItems.RGOLD_LEGGINGS))
                .add(key(ModItems.RGOLD_BOOTS))
                .add(key(ModItems.OBSIDIAN_HELMET))
                .add(key(ModItems.OBSIDIAN_CHESTPLATE))
                .add(key(ModItems.OBSIDIAN_LEGGINGS))
                .add(key(ModItems.OBSIDIAN_BOOTS))
                .add(key(ModItems.FNI_HELMET))
                .add(key(ModItems.FNI_CHESTPLATE))
                .add(key(ModItems.FNI_LEGGINGS))
                .add(key(ModItems.FNI_BOOTS))
                .add(key(ModItems.RLAPIS_HELMET))
                .add(key(ModItems.RLAPIS_CHESTPLATE))
                .add(key(ModItems.RLAPIS_LEGGINGS))
                .add(key(ModItems.RLAPIS_BOOTS))
                .add(key(ModItems.EMERALD_HELMET))
                .add(key(ModItems.EMERALD_CHESTPLATE))
                .add(key(ModItems.EMERALD_LEGGINGS))
                .add(key(ModItems.EMERALD_BOOTS))
                .add(key(ModItems.OVERPOWER_HELMET))
                .add(key(ModItems.OVERPOWER_CHESTPLATE))
                .add(key(ModItems.OVERPOWER_LEGGINGS))
                .add(key(ModItems.OVERPOWER_BOOTS));

        builder(ModTags.Items.MAGNETIZATION_METAL_TOOLS)
                .add(key(ModItems.COPPER_SWORD))
                .add(key(ModItems.COPPER_PICKAXE))
                .add(key(ModItems.COPPER_AXE))
                .add(key(ModItems.COPPER_SHOVEL))
                .add(key(ModItems.COPPER_HOE))
                .add(key(ModItems.RGOLD_SWORD))
                .add(key(ModItems.RGOLD_PICKAXE))
                .add(key(ModItems.RGOLD_AXE))
                .add(key(ModItems.RGOLD_SHOVEL))
                .add(key(ModItems.RGOLD_HOE))
                .add(key(ModItems.POBSIDIAN_SWORD))
                .add(key(ModItems.POBSIDIAN_PICKAXE))
                .add(key(ModItems.POBSIDIAN_AXE))
                .add(key(ModItems.POBSIDIAN_SHOVEL))
                .add(key(ModItems.POBSIDIAN_HOE))
                .add(key(ModItems.FNI_SWORD))
                .add(key(ModItems.FNI_PICKAXE))
                .add(key(ModItems.FNI_AXE))
                .add(key(ModItems.FNI_SHOVEL))
                .add(key(ModItems.FNI_HOE))
                .add(key(ModItems.RRAW_GOLD_SWORD))
                .add(key(ModItems.RRAW_GOLD_PICKAXE))
                .add(key(ModItems.RRAW_GOLD_AXE))
                .add(key(ModItems.RRAW_GOLD_SHOVEL))
                .add(key(ModItems.RRAW_GOLD_HOE))
                .add(key(ModItems.RRAW_COPPER_SWORD))
                .add(key(ModItems.RRAW_COPPER_PICKAXE))
                .add(key(ModItems.RRAW_COPPER_AXE))
                .add(key(ModItems.RRAW_COPPER_SHOVEL))
                .add(key(ModItems.RRAW_COPPER_HOE))
                .add(key(ModItems.RRAW_IRON_SWORD))
                .add(key(ModItems.RRAW_IRON_PICKAXE))
                .add(key(ModItems.RRAW_IRON_AXE))
                .add(key(ModItems.RRAW_IRON_SHOVEL))
                .add(key(ModItems.RRAW_IRON_HOE))
                .add(key(ModItems.RRAW_RGOLD_SWORD))
                .add(key(ModItems.RRAW_RGOLD_PICKAXE))
                .add(key(ModItems.RRAW_RGOLD_AXE))
                .add(key(ModItems.RRAW_RGOLD_SHOVEL))
                .add(key(ModItems.RRAW_RGOLD_HOE))
                .add(key(ModItems.RSCRAP_SWORD))
                .add(key(ModItems.RSCRAP_PICKAXE))
                .add(key(ModItems.RSCRAP_AXE))
                .add(key(ModItems.RSCRAP_SHOVEL))
                .add(key(ModItems.RSCRAP_HOE))
                .add(key(ModItems.RLAPIS_SWORD))
                .add(key(ModItems.RLAPIS_PICKAXE))
                .add(key(ModItems.RLAPIS_AXE))
                .add(key(ModItems.RLAPIS_SHOVEL))
                .add(key(ModItems.RLAPIS_HOE))
                .add(key(ModItems.PEMERALD_SWORD))
                .add(key(ModItems.PEMERALD_PICKAXE))
                .add(key(ModItems.PEMERALD_AXE))
                .add(key(ModItems.PEMERALD_SHOVEL))
                .add(key(ModItems.PEMERALD_HOE))
                .add(key(ModItems.OVERPOWER_SWORD))
                .add(key(ModItems.OVERPOWER_PICKAXE))
                .add(key(ModItems.OVERPOWER_AXE))
                .add(key(ModItems.OVERPOWER_SHOVEL));
    }
}
