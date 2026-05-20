package com.stonytark.usefultoolsmod.compat.jei;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SpectralInfuserRecipeCategory implements IRecipeCategory<SpectralInfuserRecipe> {

    public static final RecipeType<SpectralInfuserRecipe> TYPE =
            new RecipeType<>(Identifier.of(UsefultoolsMod.MOD_ID, "spectral_infusion"),
                    SpectralInfuserRecipe.class);

    private static final Identifier TEXTURE =
            Identifier.of(UsefultoolsMod.MOD_ID, "textures/gui/spectral_infuser.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;

    public SpectralInfuserRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 43, 14, 92, 62);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(ModBlocks.SPECTRAL_INFUSER));

        IDrawableStatic arrowStatic = guiHelper.createDrawable(TEXTURE, 176, 0, 24, 16);
        this.arrow = guiHelper.createAnimatedDrawable(arrowStatic, 200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public RecipeType<SpectralInfuserRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("block.usefultoolsmod.spectral_infuser");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(SpectralInfuserRecipe recipe, IRecipeSlotsView recipeSlotsView, DrawContext drawContext,
                     double mouseX, double mouseY) {
        arrow.draw(drawContext, 36, 21);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SpectralInfuserRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 13, 3)
                .addItemStack(recipe.weaponInput());

        builder.addSlot(RecipeIngredientRole.INPUT, 13, 39)
                .addItemStack(recipe.ectoplasmFuel());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 73, 21)
                .addItemStack(recipe.output());
    }
}
