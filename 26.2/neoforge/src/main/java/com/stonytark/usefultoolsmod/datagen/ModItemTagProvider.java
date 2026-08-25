package com.stonytark.usefultoolsmod.datagen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

/**
 * Item tag provider. 26.1 dropped the NeoForge {@code ItemTagsProvider} that took an
 * {@code ExistingFileHelper} plus a block-tag lookup; we extend the vanilla
 * {@link IntrinsicHolderTagsProvider} directly. The {@code TagLookup<Block>} parameter is
 * passed in so item tags can copy entries from sibling block tags (delivered via
 * {@code GatherDataEvent#createBlockAndItemTags}).
 */
public class ModItemTagProvider extends TagsProvider<Item> {
    public ModItemTagProvider(PackOutput packOutput,
                              CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagsProvider.TagLookup<Block>> blockTagLookup) {
        super(packOutput, Registries.ITEM, lookupProvider, UsefultoolsMod.MOD_ID);
        // blockTagLookup is unused; this mod doesn't copy block tags into item tags. The
        // signature still accepts it so GatherDataEvent#createBlockAndItemTags can hand it in.
    }



    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.EMERALD_HELMET.getKey())
                .add(ModItems.EMERALD_CHESTPLATE.getKey())
                .add(ModItems.EMERALD_LEGGINGS.getKey())
                .add(ModItems.EMERALD_BOOTS.getKey())
                .add(ModItems.HRED_HELMET.getKey())
                .add(ModItems.HRED_CHESTPLATE.getKey())
                .add(ModItems.HRED_LEGGINGS.getKey())
                .add(ModItems.HRED_BOOTS.getKey())
                .add(ModItems.HGLOW_HELMET.getKey())
                .add(ModItems.HGLOW_CHESTPLATE.getKey())
                .add(ModItems.HGLOW_LEGGINGS.getKey())
                .add(ModItems.HGLOW_BOOTS.getKey())
                .add(ModItems.OBSIDIAN_HELMET.getKey())
                .add(ModItems.OBSIDIAN_CHESTPLATE.getKey())
                .add(ModItems.OBSIDIAN_LEGGINGS.getKey())
                .add(ModItems.OBSIDIAN_BOOTS.getKey())
                .add(ModItems.RGOLD_HELMET.getKey())
                .add(ModItems.RGOLD_CHESTPLATE.getKey())
                .add(ModItems.RGOLD_LEGGINGS.getKey())
                .add(ModItems.RGOLD_BOOTS.getKey())
                .add(ModItems.OVERPOWER_HELMET.getKey())
                .add(ModItems.OVERPOWER_CHESTPLATE.getKey())
                .add(ModItems.OVERPOWER_LEGGINGS.getKey())
                .add(ModItems.OVERPOWER_BOOTS.getKey())
                .add(ModItems.RLAPIS_HELMET.getKey())
                .add(ModItems.RLAPIS_CHESTPLATE.getKey())
                .add(ModItems.RLAPIS_LEGGINGS.getKey())
                .add(ModItems.RLAPIS_BOOTS.getKey())
                .add(ModItems.COAL_HELMET.getKey())
                .add(ModItems.COAL_CHESTPLATE.getKey())
                .add(ModItems.COAL_LEGGINGS.getKey())
                .add(ModItems.COAL_BOOTS.getKey())
                .add(ModItems.FNI_HELMET.getKey())
                .add(ModItems.FNI_CHESTPLATE.getKey())
                .add(ModItems.FNI_LEGGINGS.getKey())
                .add(ModItems.FNI_BOOTS.getKey())
                .add(ModItems.ECTO_HELMET.getKey())
                .add(ModItems.ECTO_CHESTPLATE.getKey())
                .add(ModItems.ECTO_LEGGINGS.getKey())
                .add(ModItems.ECTO_BOOTS.getKey());

        // ----------------------------------------------------------------
        // Magnetization addon compatibility. Tag entries reference an
        // external namespace; if the magnetization mod isn't installed the
        // tags simply don't resolve. Inclusion rule: any set whose recipe
        // chain transitively contains iron — directly or via composite
        // ingots (RGOLD/RLAPIS/SEM/OBINGOT all use iron nuggets/ingots).
        // ----------------------------------------------------------------

        // Ingots/raw materials that should be pulled by attractive fields
        // when dropped on the ground. RLAPIS = 8 iron nuggets + 1 lapis;
        // SEM = 4 iron ingots + 1 emerald; OBINGOT = 4 iron ingots + 1
        // obshard; RGOLD = 8 iron nuggets + 1 gold.
        tag(ModTags.Items.MAGNETIZATION_FERROMAGNETIC)
                .add(ModItems.RGOLD.getKey())
                .add(ModItems.RAW_RGOLD.getKey())
                .add(ModItems.OBINGOT.getKey())
                .add(ModItems.RLAPIS.getKey())
                .add(ModItems.SEM.getKey());

        // Metal armor — adds magnetic susceptibility to the wearer.
        tag(ModTags.Items.MAGNETIZATION_METAL_ARMOR)
                .add(ModItems.COPPER_HELMET.getKey())
                .add(ModItems.COPPER_CHESTPLATE.getKey())
                .add(ModItems.COPPER_LEGGINGS.getKey())
                .add(ModItems.COPPER_BOOTS.getKey())
                .add(ModItems.RGOLD_HELMET.getKey())
                .add(ModItems.RGOLD_CHESTPLATE.getKey())
                .add(ModItems.RGOLD_LEGGINGS.getKey())
                .add(ModItems.RGOLD_BOOTS.getKey())
                .add(ModItems.OBSIDIAN_HELMET.getKey())
                .add(ModItems.OBSIDIAN_CHESTPLATE.getKey())
                .add(ModItems.OBSIDIAN_LEGGINGS.getKey())
                .add(ModItems.OBSIDIAN_BOOTS.getKey())
                .add(ModItems.FNI_HELMET.getKey())
                .add(ModItems.FNI_CHESTPLATE.getKey())
                .add(ModItems.FNI_LEGGINGS.getKey())
                .add(ModItems.FNI_BOOTS.getKey())
                // Iron-bearing composite armors:
                .add(ModItems.RLAPIS_HELMET.getKey())
                .add(ModItems.RLAPIS_CHESTPLATE.getKey())
                .add(ModItems.RLAPIS_LEGGINGS.getKey())
                .add(ModItems.RLAPIS_BOOTS.getKey())
                .add(ModItems.EMERALD_HELMET.getKey())
                .add(ModItems.EMERALD_CHESTPLATE.getKey())
                .add(ModItems.EMERALD_LEGGINGS.getKey())
                .add(ModItems.EMERALD_BOOTS.getKey())
                .add(ModItems.OVERPOWER_HELMET.getKey())
                .add(ModItems.OVERPOWER_CHESTPLATE.getKey())
                .add(ModItems.OVERPOWER_LEGGINGS.getKey())
                .add(ModItems.OVERPOWER_BOOTS.getKey());

        // Metal tools/weapons — eligible for the electromagnet's
        // personal-magnet enchantment.
        tag(ModTags.Items.MAGNETIZATION_METAL_TOOLS)
                .add(ModItems.COPPER_SWORD.getKey())
                .add(ModItems.COPPER_PICKAXE.getKey())
                .add(ModItems.COPPER_AXE.getKey())
                .add(ModItems.COPPER_SHOVEL.getKey())
                .add(ModItems.COPPER_HOE.getKey())
                .add(ModItems.RGOLD_SWORD.getKey())
                .add(ModItems.RGOLD_PICKAXE.getKey())
                .add(ModItems.RGOLD_AXE.getKey())
                .add(ModItems.RGOLD_SHOVEL.getKey())
                .add(ModItems.RGOLD_HOE.getKey())
                .add(ModItems.POBSIDIAN_SWORD.getKey())
                .add(ModItems.POBSIDIAN_PICKAXE.getKey())
                .add(ModItems.POBSIDIAN_AXE.getKey())
                .add(ModItems.POBSIDIAN_SHOVEL.getKey())
                .add(ModItems.POBSIDIAN_HOE.getKey())
                .add(ModItems.FNI_SWORD.getKey())
                .add(ModItems.FNI_PICKAXE.getKey())
                .add(ModItems.FNI_AXE.getKey())
                .add(ModItems.FNI_SHOVEL.getKey())
                .add(ModItems.FNI_HOE.getKey())
                .add(ModItems.RRAW_GOLD_SWORD.getKey())
                .add(ModItems.RRAW_GOLD_PICKAXE.getKey())
                .add(ModItems.RRAW_GOLD_AXE.getKey())
                .add(ModItems.RRAW_GOLD_SHOVEL.getKey())
                .add(ModItems.RRAW_GOLD_HOE.getKey())
                .add(ModItems.RRAW_COPPER_SWORD.getKey())
                .add(ModItems.RRAW_COPPER_PICKAXE.getKey())
                .add(ModItems.RRAW_COPPER_AXE.getKey())
                .add(ModItems.RRAW_COPPER_SHOVEL.getKey())
                .add(ModItems.RRAW_COPPER_HOE.getKey())
                .add(ModItems.RRAW_IRON_SWORD.getKey())
                .add(ModItems.RRAW_IRON_PICKAXE.getKey())
                .add(ModItems.RRAW_IRON_AXE.getKey())
                .add(ModItems.RRAW_IRON_SHOVEL.getKey())
                .add(ModItems.RRAW_IRON_HOE.getKey())
                .add(ModItems.RRAW_RGOLD_SWORD.getKey())
                .add(ModItems.RRAW_RGOLD_PICKAXE.getKey())
                .add(ModItems.RRAW_RGOLD_AXE.getKey())
                .add(ModItems.RRAW_RGOLD_SHOVEL.getKey())
                .add(ModItems.RRAW_RGOLD_HOE.getKey())
                .add(ModItems.RSCRAP_SWORD.getKey())
                .add(ModItems.RSCRAP_PICKAXE.getKey())
                .add(ModItems.RSCRAP_AXE.getKey())
                .add(ModItems.RSCRAP_SHOVEL.getKey())
                .add(ModItems.RSCRAP_HOE.getKey())
                // Iron-bearing composite tools:
                .add(ModItems.RLAPIS_SWORD.getKey())
                .add(ModItems.RLAPIS_PICKAXE.getKey())
                .add(ModItems.RLAPIS_AXE.getKey())
                .add(ModItems.RLAPIS_SHOVEL.getKey())
                .add(ModItems.RLAPIS_HOE.getKey())
                .add(ModItems.PEMERALD_SWORD.getKey())
                .add(ModItems.PEMERALD_PICKAXE.getKey())
                .add(ModItems.PEMERALD_AXE.getKey())
                .add(ModItems.PEMERALD_SHOVEL.getKey())
                .add(ModItems.PEMERALD_HOE.getKey())
                .add(ModItems.OVERPOWER_SWORD.getKey())
                .add(ModItems.OVERPOWER_PICKAXE.getKey())
                .add(ModItems.OVERPOWER_AXE.getKey())
                .add(ModItems.OVERPOWER_SHOVEL.getKey());
    }
}
