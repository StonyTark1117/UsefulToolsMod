package com.stonytark.usefultoolsmod.compat.jade;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.entity.SpectralInfuserBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public class SpectralInfuserServerDataProvider implements IServerDataProvider<BlockAccessor> {

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(UsefultoolsMod.MOD_ID, "spectral_infuser_data");
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        if (!(accessor.getBlockEntity() instanceof SpectralInfuserBlockEntity be)) return;

        var handler = be.getItemHandler();
        var containerData = be.getData();

        ItemStack input = handler.getStackInSlot(0);
        ItemStack fuel = handler.getStackInSlot(1);
        ItemStack output = handler.getStackInSlot(2);

        data.putInt("utm_progress", containerData.get(0));
        data.putInt("utm_maxProgress", containerData.get(1));
        data.putBoolean("utm_hasFuel", !fuel.isEmpty());

        if (!input.isEmpty()) {
            data.putString("utm_inputName", input.getHoverName().getString());
        }
        if (!output.isEmpty()) {
            data.putString("utm_outputName", output.getHoverName().getString());
        }
    }
}
