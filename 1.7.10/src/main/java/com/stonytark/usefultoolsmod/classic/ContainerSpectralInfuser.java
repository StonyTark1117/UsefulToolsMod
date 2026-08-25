package com.stonytark.usefultoolsmod.classic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSpectralInfuser extends Container {
    private final TileSpectralInfuser infuser;
    private int lastProgress;

    public ContainerSpectralInfuser(InventoryPlayer player, TileSpectralInfuser infuser) {
        this.infuser = infuser;
        addSlotToContainer(new Slot(infuser, 0, 44, 35));
        addSlotToContainer(new Slot(infuser, 1, 80, 35));
        addSlotToContainer(new Slot(infuser, 2, 134, 35) {
            @Override public boolean isItemValid(ItemStack stack) { return false; }
            @Override public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
                super.onPickupFromSlot(player, stack);
                player.addStat(ClassicAchievements.infusedItem, 1);
                if (stack != null && stack.getItem() == ClassicGeneratedCatalog.ITEMS.get("ghost_spawn_egg"))
                    player.addStat(ClassicAchievements.ghostEgg, 1);
            }
        });
        for (int row = 0; row < 3; row++) for (int column = 0; column < 9; column++)
            addSlotToContainer(new Slot(player, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
        for (int column = 0; column < 9; column++)
            addSlotToContainer(new Slot(player, column, 8 + column * 18, 142));
    }

    @Override public boolean canInteractWith(EntityPlayer player) { return infuser.isUseableByPlayer(player); }
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack original = null;
        Slot slot = (Slot) inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) return null;
        ItemStack stack = slot.getStack();
        original = stack.copy();
        if (index < 3) {
            if (!mergeItemStack(stack, 3, 39, true)) return null;
        } else if (infuser.isItemValidForSlot(1, stack)) {
            if (!mergeItemStack(stack, 1, 2, false)) return null;
        } else if (infuser.isItemValidForSlot(0, stack)) {
            if (!mergeItemStack(stack, 0, 1, false)) return null;
        } else if (index < 30) {
            if (!mergeItemStack(stack, 30, 39, false)) return null;
        } else if (!mergeItemStack(stack, 3, 30, false)) {
            return null;
        }
        if (stack.stackSize == 0) slot.putStack(null); else slot.onSlotChanged();
        if (stack.stackSize == original.stackSize) return null;
        slot.onPickupFromSlot(player, stack);
        return original;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (Object listener : crafters) {
            if (lastProgress != infuser.getProgress()) ((ICrafting) listener).sendProgressBarUpdate(this, 0, infuser.getProgress());
        }
        lastProgress = infuser.getProgress();
    }

    @Override public void updateProgressBar(int id, int value) {
        if (id == 0) infuser.setProgress(value);
    }

    public int scaledProgress(int width) {
        return infuser.getProgress() * width / infuser.getMaxProgress();
    }
}
