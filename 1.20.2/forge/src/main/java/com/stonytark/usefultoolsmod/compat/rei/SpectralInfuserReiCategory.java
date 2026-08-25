package com.stonytark.usefultoolsmod.compat.rei;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class SpectralInfuserReiCategory implements DisplayCategory<SpectralInfuserReiDisplay> {

    public static final CategoryIdentifier<SpectralInfuserReiDisplay> ID =
            CategoryIdentifier.of(new ResourceLocation(UsefultoolsMod.MOD_ID, "spectral_infusion"));

    @Override
    public CategoryIdentifier<? extends SpectralInfuserReiDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.usefultoolsmod.spectral_infuser");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.SPECTRAL_INFUSER.get());
    }

    @Override
    public int getDisplayHeight() {
        return 66;
    }

    @Override
    public List<Widget> setupDisplay(SpectralInfuserReiDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        Point origin = new Point(bounds.getCenterX() - 46, bounds.y + 4);

        widgets.add(Widgets.createRecipeBase(bounds));

        // Input slot (weapon/armor)
        List<EntryIngredient> inputs = display.getInputEntries();
        widgets.add(Widgets.createSlot(new Point(origin.x, origin.y))
                .entries(inputs.get(0))
                .markInput());

        // Fuel slot (ectoplasm)
        widgets.add(Widgets.createSlot(new Point(origin.x, origin.y + 36))
                .entries(inputs.get(1))
                .markInput());

        // Arrow
        widgets.add(Widgets.createArrow(new Point(origin.x + 28, origin.y + 16)));

        // Output slot
        List<EntryIngredient> outputs = display.getOutputEntries();
        widgets.add(Widgets.createSlot(new Point(origin.x + 64, origin.y + 16))
                .entries(outputs.get(0))
                .markOutput());

        return widgets;
    }
}
