package com.stonytark.usefultoolsmod.classic;

import com.krazykid1117.Main.Mainregistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ClassicSpectralInfuserBlock extends BlockContainer {
    public static final int GUI_ID = 230;

    public ClassicSpectralInfuserBlock() {
        super(Material.rock);
        setHardness(3.5F);
        setResistance(10.0F);
    }

    public static void registerTileEntity() {
        GameRegistry.registerTileEntity(TileSpectralInfuser.class, "usefultoolsmod:spectral_infuser");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileSpectralInfuser();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
                                    int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) player.openGui(Mainregistry.modInstance, GUI_ID, world, x, y, z);
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, net.minecraft.block.Block block, int metadata) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileSpectralInfuser) {
            TileSpectralInfuser infuser = (TileSpectralInfuser) tile;
            for (int slot = 0; slot < infuser.getSizeInventory(); slot++) {
                ItemStack stack = infuser.getStackInSlot(slot);
                if (stack != null) {
                    world.spawnEntityInWorld(new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, stack));
                }
            }
        }
        super.breakBlock(world, x, y, z, block, metadata);
    }
}
