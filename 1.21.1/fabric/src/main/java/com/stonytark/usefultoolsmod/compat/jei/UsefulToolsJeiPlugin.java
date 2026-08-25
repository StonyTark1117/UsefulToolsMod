package com.stonytark.usefultoolsmod.compat.jei;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class UsefulToolsJeiPlugin implements IModPlugin {

    @Override
    public Identifier getPluginUid() {
        return Identifier.of(UsefultoolsMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new SpectralInfuserRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<SpectralInfuserRecipe> recipes = new ArrayList<>();
        ItemStack ectoplasm = new ItemStack(ModItems.ECTOPLASM);

        Registries.ITEM.forEach(item -> {
            if (item instanceof ToolItem || item instanceof ArmorItem) {
                ItemStack input = new ItemStack(item);
                ItemStack output = input.copy();
                EctoplasmInfusionHelper.setInfused(output, true);
                recipes.add(new SpectralInfuserRecipe(input, ectoplasm, output));
            }
        });

        recipes.add(new SpectralInfuserRecipe(
                new ItemStack(Items.EGG),
                ectoplasm,
                new ItemStack(ModItems.GHOST_SPAWN_EGG)));

        registration.addRecipes(SpectralInfuserRecipeCategory.TYPE, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SPECTRAL_INFUSER),
                SpectralInfuserRecipeCategory.TYPE);
    }
}
