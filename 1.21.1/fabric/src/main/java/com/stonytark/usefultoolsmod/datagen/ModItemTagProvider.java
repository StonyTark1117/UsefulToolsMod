package com.stonytark.usefultoolsmod.datagen;

import com.stonytark.usefultoolsmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModItems.REMERALD_SWORD)
                .add(ModItems.PEMERALD_SWORD)
                .add(ModItems.ROBSIDIAN_SWORD)
                .add(ModItems.POBSIDIAN_SWORD)
                .add(ModItems.OVERPOWER_SWORD)
                .add(ModItems.HREDSTONE_SWORD)
                .add(ModItems.HGLOWSTONE_SWORD)
                .add(ModItems.RGOLD_SWORD)
                .add(ModItems.RLAPIS_SWORD)
                .add(ModItems.RECTO_SWORD)
                .add(ModItems.ECTO_SWORD)
                .add(ModItems.COAL_SWORD);

        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.REMERALD_PICKAXE)
                .add(ModItems.PEMERALD_PICKAXE)
                .add(ModItems.ROBSIDIAN_PICKAXE)
                .add(ModItems.POBSIDIAN_PICKAXE)
                .add(ModItems.OVERPOWER_PICKAXE)
                .add(ModItems.HREDSTONE_PICKAXE)
                .add(ModItems.HGLOWSTONE_PICKAXE)
                .add(ModItems.RGOLD_PICKAXE)
                .add(ModItems.RLAPIS_PICKAXE)
                .add(ModItems.RECTO_PICKAXE)
                .add(ModItems.ECTO_PICKAXE)
                .add(ModItems.COAL_PICKAXE);

        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModItems.REMERALD_AXE)
                .add(ModItems.PEMERALD_AXE)
                .add(ModItems.ROBSIDIAN_AXE)
                .add(ModItems.POBSIDIAN_AXE)
                .add(ModItems.OVERPOWER_AXE)
                .add(ModItems.HREDSTONE_AXE)
                .add(ModItems.HGLOWSTONE_AXE)
                .add(ModItems.RGOLD_AXE)
                .add(ModItems.RLAPIS_AXE)
                .add(ModItems.RECTO_AXE)
                .add(ModItems.ECTO_AXE)
                .add(ModItems.COAL_AXE);

        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ModItems.REMERALD_SHOVEL)
                .add(ModItems.PEMERALD_SHOVEL)
                .add(ModItems.ROBSIDIAN_SHOVEL)
                .add(ModItems.POBSIDIAN_SHOVEL)
                .add(ModItems.OVERPOWER_SHOVEL)
                .add(ModItems.HREDSTONE_SHOVEL)
                .add(ModItems.HGLOWSTONE_SHOVEL)
                .add(ModItems.RGOLD_SHOVEL)
                .add(ModItems.RLAPIS_SHOVEL)
                .add(ModItems.RECTO_SHOVEL)
                .add(ModItems.ECTO_SHOVEL)
                .add(ModItems.COAL_SHOVEL);

        getOrCreateTagBuilder(ItemTags.HOES)
                .add(ModItems.REMERALD_HOE)
                .add(ModItems.PEMERALD_HOE)
                .add(ModItems.ROBSIDIAN_HOE)
                .add(ModItems.POBSIDIAN_HOE)
                .add(ModItems.HREDSTONE_HOE)
                .add(ModItems.HGLOWSTONE_HOE)
                .add(ModItems.RGOLD_HOE)
                .add(ModItems.RLAPIS_HOE)
                .add(ModItems.RECTO_HOE)
                .add(ModItems.ECTO_HOE)
                .add(ModItems.COAL_HOE);

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.EMERALD_HELMET)
                .add(ModItems.EMERALD_CHESTPLATE)
                .add(ModItems.EMERALD_LEGGINGS)
                .add(ModItems.EMERALD_BOOTS)
                .add(ModItems.HRED_HELMET)
                .add(ModItems.HRED_CHESTPLATE)
                .add(ModItems.HRED_LEGGINGS)
                .add(ModItems.HRED_BOOTS)
                .add(ModItems.OBSIDIAN_HELMET)
                .add(ModItems.OBSIDIAN_CHESTPLATE)
                .add(ModItems.OBSIDIAN_LEGGINGS)
                .add(ModItems.OBSIDIAN_BOOTS)
                .add(ModItems.RGOLD_HELMET)
                .add(ModItems.RGOLD_CHESTPLATE)
                .add(ModItems.RGOLD_LEGGINGS)
                .add(ModItems.RGOLD_BOOTS)
                .add(ModItems.OVERPOWER_HELMET)
                .add(ModItems.OVERPOWER_CHESTPLATE)
                .add(ModItems.OVERPOWER_LEGGINGS)
                .add(ModItems.OVERPOWER_BOOTS)
                .add(ModItems.RLAPIS_HELMET)
                .add(ModItems.RLAPIS_CHESTPLATE)
                .add(ModItems.RLAPIS_LEGGINGS)
                .add(ModItems.RLAPIS_BOOTS);
    }
}
