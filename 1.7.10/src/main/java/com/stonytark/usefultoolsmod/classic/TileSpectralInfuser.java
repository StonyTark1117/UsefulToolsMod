package com.stonytark.usefultoolsmod.classic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileSpectralInfuser extends TileEntity implements IInventory {
    private final ItemStack[] inventory = new ItemStack[3];
    private int progress;
    private int fuelUses;
    private static final int MAX_PROGRESS = 200;

    @Override
    public void updateEntity() {
        if (worldObj.isRemote) return;
        if (!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && hasRecipe()) {
            if (++progress >= MAX_PROGRESS) craft();
        } else if (progress != 0) {
            progress = 0;
        }
        markDirty();
    }

    private boolean hasRecipe() {
        return isInfusable(inventory[0])
                && (fuelUses > 0 || inventory[1] != null && (inventory[1].getItem() == ClassicGeneratedCatalog.ITEMS.get("ectoplasm")
                || inventory[1].getItem() == ClassicFeatureRegistry.condensedEctoplasm))
                && inventory[2] == null;
    }

    private void craft() {
        ItemStack input = inventory[0];
        ItemStack result = createResult(input, ClassicGeneratedCatalog.ITEMS.get("ghost_spawn_egg"), Items.egg);
        inventory[2] = result;
        inventory[0] = null;
        if (fuelUses <= 0) {
            fuelUses = inventory[1].getItem() == ClassicFeatureRegistry.condensedEctoplasm ? 8 : 1;
            if (--inventory[1].stackSize <= 0) inventory[1] = null;
        }
        fuelUses--;
        progress = 0;
        markDirty();
    }

    public static ItemStack createResult(ItemStack input, Item ghostSpawnEgg) {
        return createResult(input, ghostSpawnEgg, Items.egg);
    }

    static ItemStack createResult(ItemStack input, Item ghostSpawnEgg, Item egg) {
        if (input == null) return null;
        if (input.getItem() == egg) return new ItemStack(ghostSpawnEgg);
        ItemStack result = ClassicInfusion.infuse(input);
        result.setItemDamage(0);
        return result;
    }

    public static boolean isInfusable(ItemStack stack) {
        return isInfusable(stack, Items.egg);
    }

    static boolean isInfusable(ItemStack stack, Item egg) {
        if (stack == null) return false;
        Item item = stack.getItem();
        return item == egg || item instanceof ItemSword || item instanceof ItemPickaxe
                || item instanceof ItemAxe || item instanceof ItemSpade || item instanceof ItemHoe
                || item instanceof ItemArmor;
    }

    public int getProgress() { return progress; }
    public int getMaxProgress() { return MAX_PROGRESS; }
    public int getFuelUses() { return fuelUses; }
    public void setProgress(int value) { progress = value; }

    @Override public int getSizeInventory() { return inventory.length; }
    @Override public ItemStack getStackInSlot(int slot) { return inventory[slot]; }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack stack = inventory[slot];
        if (stack == null) return null;
        if (stack.stackSize <= amount) {
            inventory[slot] = null;
            return stack;
        }
        ItemStack split = stack.splitStack(amount);
        if (stack.stackSize == 0) inventory[slot] = null;
        return split;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = inventory[slot];
        inventory[slot] = null;
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) stack.stackSize = getInventoryStackLimit();
        markDirty();
    }

    @Override public String getInventoryName() { return "container.usefultoolsmod.spectral_infuser"; }
    @Override public boolean hasCustomInventoryName() { return false; }
    @Override public int getInventoryStackLimit() { return 64; }
    @Override public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
                && player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
    }
    @Override public void openInventory() {}
    @Override public void closeInventory() {}
    @Override public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot == 0) return isInfusable(stack);
        if (slot == 1) return stack != null && (stack.getItem() == ClassicGeneratedCatalog.ITEMS.get("ectoplasm")
                || stack.getItem() == ClassicFeatureRegistry.condensedEctoplasm);
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        NBTTagList list = new NBTTagList();
        for (int slot = 0; slot < inventory.length; slot++) {
            if (inventory[slot] == null) continue;
            NBTTagCompound item = new NBTTagCompound();
            item.setByte("Slot", (byte) slot);
            inventory[slot].writeToNBT(item);
            list.appendTag(item);
        }
        tag.setTag("Items", list);
        tag.setInteger("Progress", progress);
        tag.setInteger("FuelUses", fuelUses);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        NBTTagList list = tag.getTagList("Items", 10);
        for (int index = 0; index < list.tagCount(); index++) {
            NBTTagCompound item = list.getCompoundTagAt(index);
            int slot = item.getByte("Slot") & 255;
            if (slot < inventory.length) inventory[slot] = ItemStack.loadItemStackFromNBT(item);
        }
        progress = tag.getInteger("Progress");
        fuelUses = tag.getInteger("FuelUses");
    }
}
