package com.stonytark.usefultoolsmod.compat.rei;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;

@REIPluginClient
public class UsefulToolsReiPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new SpectralInfuserReiCategory());
        registry.addWorkstations(SpectralInfuserReiCategory.ID,
                EntryStacks.of(ModBlocks.SPECTRAL_INFUSER.get()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        ItemStack ectoplasm = new ItemStack(ModItems.ECTOPLASM.get());

        BuiltInRegistries.ITEM.forEach(item -> {
            if (item instanceof TieredItem || item instanceof ArmorItem) {
                ItemStack input = new ItemStack(item);
                ItemStack output = input.copy();
                EctoplasmInfusionHelper.setInfused(output, true);
                registry.add(new SpectralInfuserReiDisplay(input, ectoplasm, output));
            }
        });

        // Egg + Ectoplasm -> Ghost Spawn Egg
        registry.add(new SpectralInfuserReiDisplay(
                new ItemStack(Items.EGG),
                ectoplasm,
                new ItemStack(ModItems.GHOST_SPAWN_EGG.get())));
    }
}
