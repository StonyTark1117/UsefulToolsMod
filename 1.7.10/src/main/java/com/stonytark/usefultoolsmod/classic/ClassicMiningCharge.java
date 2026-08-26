package com.stonytark.usefultoolsmod.classic;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ClassicMiningCharge extends BlockContainer {
    public ClassicMiningCharge(Material material) { super(material); }
    @Override public TileEntity createNewTileEntity(World world, int meta) { return new TileMiningCharge(); }

    @Override public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        int facing = MathHelper.floor_double(placer.rotationYaw * 4F / 360F + .5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, facing, 2);
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileMiningCharge && placer instanceof EntityPlayer)
            ((TileMiningCharge)tile).owner = ((EntityPlayer)placer).getUniqueID().toString();
    }

    @Override public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
                                               int side, float hitX, float hitY, float hitZ) {
        if (!ClassicGeneratedConfig.booleanValue("explosivesEnabled")
                || !ClassicGeneratedConfig.booleanValue("miningChargeEnabled")) return false;
        ItemStack held = player.getHeldItem();
        if (held != null && held.getItem() == Items.flint_and_steel) {
            ((TileMiningCharge)world.getTileEntity(x, y, z)).ignite(); held.damageItem(1, player); return true;
        }
        return false;
    }

    @Override public void onNeighborBlockChange(World world, int x, int y, int z, net.minecraft.block.Block neighbor) {
        if (ClassicGeneratedConfig.booleanValue("explosivesEnabled")
                && ClassicGeneratedConfig.booleanValue("miningChargeEnabled")
                && world.isBlockIndirectlyGettingPowered(x, y, z)) {
            TileEntity tile = world.getTileEntity(x, y, z); if (tile instanceof TileMiningCharge) ((TileMiningCharge)tile).ignite();
        }
    }
}
