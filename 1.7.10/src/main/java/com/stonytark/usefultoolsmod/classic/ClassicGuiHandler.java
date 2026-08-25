package com.stonytark.usefultoolsmod.classic;

import com.stonytark.usefultoolsmod.classic.client.GuiSpectralInfuser;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ClassicGuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id != ClassicSpectralInfuserBlock.GUI_ID) return null;
        TileSpectralInfuser tile = (TileSpectralInfuser) world.getTileEntity(x, y, z);
        return tile == null ? null : new ContainerSpectralInfuser(player.inventory, tile);
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id != ClassicSpectralInfuserBlock.GUI_ID) return null;
        TileSpectralInfuser tile = (TileSpectralInfuser) world.getTileEntity(x, y, z);
        return tile == null ? null : new GuiSpectralInfuser(player.inventory, tile);
    }
}
