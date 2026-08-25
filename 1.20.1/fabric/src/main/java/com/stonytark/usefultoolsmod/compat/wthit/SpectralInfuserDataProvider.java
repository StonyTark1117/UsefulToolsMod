package com.stonytark.usefultoolsmod.compat.wthit;

import com.stonytark.usefultoolsmod.block.entity.SpectralInfuserBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class SpectralInfuserDataProvider implements IDataProvider<SpectralInfuserBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<SpectralInfuserBlockEntity> accessor,
                           IPluginConfig config) {
        SpectralInfuserBlockEntity be = accessor.getTarget();
        DefaultedList<ItemStack> inv = be.getInventory();
        var containerData = be.getPropertyDelegate();

        ItemStack input = inv.get(0);
        ItemStack fuel = inv.get(1);
        ItemStack output = inv.get(2);

        NbtCompound tag = data.raw();
        tag.putInt("utm_progress", containerData.get(0));
        tag.putInt("utm_maxProgress", containerData.get(1));
        tag.putBoolean("utm_hasFuel", !fuel.isEmpty());

        if (!input.isEmpty()) {
            tag.putString("utm_inputName", input.getName().getString());
        }
        if (!output.isEmpty()) {
            tag.putString("utm_outputName", output.getName().getString());
        }
    }
}
