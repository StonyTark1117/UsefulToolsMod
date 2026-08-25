package com.stonytark.usefultoolsmod.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class SpectralInfuserReiDisplay extends BasicDisplay {

    public SpectralInfuserReiDisplay(ItemStack weaponInput, ItemStack ectoplasmFuel, ItemStack output) {
        super(
                List.of(EntryIngredients.of(weaponInput), EntryIngredients.of(ectoplasmFuel)),
                List.of(EntryIngredients.of(output)),
                Optional.empty()
        );
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SpectralInfuserReiCategory.ID;
    }
}
