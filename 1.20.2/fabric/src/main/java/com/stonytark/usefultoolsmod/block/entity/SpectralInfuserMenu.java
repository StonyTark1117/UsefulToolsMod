package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

public class SpectralInfuserMenu extends ScreenHandler {
    private final SpectralInfuserBlockEntity blockEntity;
    private final PropertyDelegate propertyDelegate;

    // Slot indices
    private static final int PLAYER_INV_START = 0;
    private static final int PLAYER_INV_END = 27;
    private static final int PLAYER_HOTBAR_END = 36;
    private static final int INPUT_SLOT = 36;
    private static final int FUEL_SLOT = 37;
    private static final int OUTPUT_SLOT = 38;

    public SpectralInfuserMenu(int syncId, PlayerInventory playerInventory,
                               SpectralInfuserBlockEntity blockEntity, PropertyDelegate propertyDelegate) {
        super(ModMenuTypes.SPECTRAL_INFUSER, syncId);
        this.blockEntity = blockEntity;
        this.propertyDelegate = propertyDelegate;

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        // Block entity slots
        DefaultedList<ItemStack> inv = blockEntity.getInventory();
        this.addSlot(new Slot(new SimpleInventory(inv), 0, 56, 17));   // input (top)
        this.addSlot(new Slot(new SimpleInventory(inv), 1, 56, 53));   // ectoplasm (bottom)
        this.addSlot(new Slot(new SimpleInventory(inv), 2, 116, 35) {   // output (right)
            @Override
            public boolean canInsert(ItemStack stack) {
                return false; // output-only
            }
        });

        this.addProperties(propertyDelegate);
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = propertyDelegate.get(0);
        int maxProgress = propertyDelegate.get(1);
        int arrowPixelWidth = 24;
        return maxProgress != 0 ? progress * arrowPixelWidth / maxProgress : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack original = slot.getStack().copy();
            newStack = original;

            if (index == OUTPUT_SLOT) {
                // Output → player inventory
                if (!this.insertItem(original, PLAYER_INV_START, PLAYER_HOTBAR_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(original, newStack);
            } else if (index >= INPUT_SLOT) {
                // Input/fuel → player inventory
                if (!this.insertItem(original, PLAYER_INV_START, PLAYER_HOTBAR_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Player inventory → block entity slots
                if (SpectralInfuserBlockEntity.isInfusable(original)) {
                    if (!this.insertItem(original, INPUT_SLOT, INPUT_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (original.isOf(ModItems.ECTOPLASM)) {
                    if (!this.insertItem(original, FUEL_SLOT, FUEL_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < PLAYER_INV_END) {
                    // Main inventory → hotbar
                    if (!this.insertItem(original, PLAYER_INV_END, PLAYER_HOTBAR_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    // Hotbar → main inventory
                    if (!this.insertItem(original, PLAYER_INV_START, PLAYER_INV_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (original.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return blockEntity.canPlayerUseInverted(player);
    }

    private void addPlayerInventory(PlayerInventory inv) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory inv) {
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inv, col, 8 + col * 18, 142));
        }
    }

    // Simple wrapper to expose inventory
    private static class SimpleInventory implements Inventory {
        private final DefaultedList<ItemStack> stacks;

        public SimpleInventory(DefaultedList<ItemStack> stacks) {
            this.stacks = stacks;
        }

        @Override
        public int size() {
            return stacks.size();
        }

        @Override
        public boolean isEmpty() {
            for (ItemStack stack : stacks) {
                if (!stack.isEmpty()) return false;
            }
            return true;
        }

        @Override
        public ItemStack getStack(int slot) {
            return stacks.get(slot);
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            return stacks.get(slot).split(amount);
        }

        @Override
        public ItemStack removeStack(int slot) {
            ItemStack stack = stacks.get(slot);
            stacks.set(slot, ItemStack.EMPTY);
            return stack;
        }

        @Override
        public void setStack(int slot, ItemStack stack) {
            stacks.set(slot, stack);
        }

        @Override
        public void markDirty() {
        }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return true;
        }

        @Override
        public void clear() {
            stacks.clear();
        }
    }
}
