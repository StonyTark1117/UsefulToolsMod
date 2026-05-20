package com.stonytark.usefultoolsmod.datagen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture,
                              CompletableFuture<TagLookup<Block>> lookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, lookupCompletableFuture, UsefultoolsMod.MOD_ID, existingFileHelper);
    }



    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.EMERALD_HELMET.get())
                .add(ModItems.EMERALD_CHESTPLATE.get())
                .add(ModItems.EMERALD_LEGGINGS.get())
                .add(ModItems.EMERALD_BOOTS.get())
                .add(ModItems.HRED_HELMET.get())
                .add(ModItems.HRED_CHESTPLATE.get())
                .add(ModItems.HRED_LEGGINGS.get())
                .add(ModItems.HRED_BOOTS.get())
                .add(ModItems.HGLOW_HELMET.get())
                .add(ModItems.HGLOW_CHESTPLATE.get())
                .add(ModItems.HGLOW_LEGGINGS.get())
                .add(ModItems.HGLOW_BOOTS.get())
                .add(ModItems.OBSIDIAN_HELMET.get())
                .add(ModItems.OBSIDIAN_CHESTPLATE.get())
                .add(ModItems.OBSIDIAN_LEGGINGS.get())
                .add(ModItems.OBSIDIAN_BOOTS.get())
                .add(ModItems.RGOLD_HELMET.get())
                .add(ModItems.RGOLD_CHESTPLATE.get())
                .add(ModItems.RGOLD_LEGGINGS.get())
                .add(ModItems.RGOLD_BOOTS.get())
                .add(ModItems.OVERPOWER_HELMET.get())
                .add(ModItems.OVERPOWER_CHESTPLATE.get())
                .add(ModItems.OVERPOWER_LEGGINGS.get())
                .add(ModItems.OVERPOWER_BOOTS.get())
                .add(ModItems.RLAPIS_HELMET.get())
                .add(ModItems.RLAPIS_CHESTPLATE.get())
                .add(ModItems.RLAPIS_LEGGINGS.get())
                .add(ModItems.RLAPIS_BOOTS.get())
                .add(ModItems.COAL_HELMET.get())
                .add(ModItems.COAL_CHESTPLATE.get())
                .add(ModItems.COAL_LEGGINGS.get())
                .add(ModItems.COAL_BOOTS.get())
                .add(ModItems.FNI_HELMET.get())
                .add(ModItems.FNI_CHESTPLATE.get())
                .add(ModItems.FNI_LEGGINGS.get())
                .add(ModItems.FNI_BOOTS.get())
                .add(ModItems.ECTO_HELMET.get())
                .add(ModItems.ECTO_CHESTPLATE.get())
                .add(ModItems.ECTO_LEGGINGS.get())
                .add(ModItems.ECTO_BOOTS.get());

    }
}