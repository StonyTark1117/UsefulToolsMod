package com.stonytark.usefultoolsmod.classic;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileMiningCharge extends TileEntity {
    public int fuse = -1;
    public int channel;
    public String owner = "";
    public void ignite() { if (fuse < 0) { fuse = 80; markDirty(); } }

    @Override public void updateEntity() {
        if (worldObj.isRemote || fuse < 0) return;
        if (--fuse <= 0) {
            int facing = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            worldObj.setBlockToAir(xCoord, yCoord, zCoord);
            ClassicControlledExplosion.miningCharge(worldObj, xCoord, yCoord, zCoord, facing);
            for (Object value : worldObj.playerEntities) if (value instanceof net.minecraft.entity.player.EntityPlayer) {
                net.minecraft.entity.player.EntityPlayer player = (net.minecraft.entity.player.EntityPlayer)value;
                if (player.getUniqueID().toString().equals(owner)) player.addStat(ClassicAchievements.precisionExcavation, 1);
            }
        } else if (fuse % 20 == 0) worldObj.playSoundEffect(xCoord + .5, yCoord + .5, zCoord + .5, "random.fuse", 1F, 1F);
    }

    @Override public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag); tag.setInteger("Fuse", fuse); tag.setInteger("Channel", channel); tag.setString("Owner", owner);
    }
    @Override public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag); fuse = tag.getInteger("Fuse"); channel = tag.getInteger("Channel"); owner = tag.getString("Owner");
    }
}
