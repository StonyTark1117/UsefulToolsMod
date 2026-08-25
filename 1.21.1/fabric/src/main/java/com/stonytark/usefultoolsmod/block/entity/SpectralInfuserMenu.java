package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class SpectralInfuserMenu extends ScreenHandler {
    private final Inventory blockEntity;
    private final PropertyDelegate data;

    // Slot indices
    private static final int PLAYER_INV_START = 0;
    private static final int PLAYER_INV_END = 27;
    private static final int PLAYER_HOTBAR_END = 36;
    private static final int INPUT_SLOT = 36;
    private static final int FUEL_SLOT = 37;
    private static final int OUTPUT_SLOT = 38;

    // Server-side constructor (with real block entity and property delegate)
    public SpectralInfuserMenu(int syncId, PlayerInventory playerInv, SpectralInfuserBlockEntity blockEntity, PropertyDelegate data) {
        super(ModMenuTypes.SPECTRAL_INFUSER_MENU, syncId);
        this.blockEntity = blockEntity;
        this.data = data;

        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);

        // Block entity slots
        addSlot(new Slot(blockEntity, 0, 56, 17) {    // input (top)
            @Override
            public boolean canInsert(ItemStack stack) {
                return SpectralInfuserBlockEntity.isInfusable(stack);
            }
        });
        addSlot(new Slot(blockEntity, 1, 56, 53) {    // ectoplasm (bottom)
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(ModItems.ECTOPLASM);
            }
        });
        addSlot(new Slot(blockEntity, 2, 116, 35) {   // output (right)
            @Override
            public boolean canInsert(ItemStack stack) {
                return false; // output-only
            }
        });

        addProperties(data);
    }

    // Client-side constructor (from network data: BlockPos)
    public SpectralInfuserMenu(int syncId, PlayerInventory playerInv, BlockPos pos) {
        super(ModMenuTypes.SPECTRAL_INFUSER_MENU, syncId);
        // On the client, try to get the real block entity if available
        var world = playerInv.player.getWorld();
        var be = world.getBlockEntity(pos);
        if (be instanceof SpectralInfuserBlockEntity sibe) {
            this.blockEntity = sibe;
            this.data = sibe.getPropertyDelegate();
        } else {
            this.blockEntity = new SimpleBlockEntityInventory();
            this.data = new ArrayPropertyDelegate(2);
        }

        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);

        // Block entity slots
        addSlot(new Slot(blockEntity, 0, 56, 17) {    // input (top)
            @Override
            public boolean canInsert(ItemStack stack) {
                return SpectralInfuserBlockEntity.isInfusable(stack);
            }
        });
        addSlot(new Slot(blockEntity, 1, 56, 53) {    // ectoplasm (bottom)
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(ModItems.ECTOPLASM);
            }
        });
        addSlot(new Slot(blockEntity, 2, 116, 35) {   // output (right)
            @Override
            public boolean canInsert(ItemStack stack) {
                return false; // output-only
            }
        });

        addProperties(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = data.get(0);
        int maxProgress = data.get(1);
        int arrowPixelWidth = 24;
        return maxProgress != 0 ? progress * arrowPixelWidth / maxProgress : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasStack()) {
            ItemStack original = slot.getStack();
            newStack = original.copy();

            if (index == OUTPUT_SLOT) {
                // Output -> player inventory
                if (!insertItem(original, PLAYER_INV_START, PLAYER_HOTBAR_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(original, newStack);
            } else if (index >= INPUT_SLOT) {
                // Input/fuel -> player inventory
                if (!insertItem(original, PLAYER_INV_START, PLAYER_HOTBAR_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Player inventory -> block entity slots
                if (SpectralInfuserBlockEntity.isInfusable(original)) {
                    if (!insertItem(original, INPUT_SLOT, INPUT_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (original.isOf(ModItems.ECTOPLASM)) {
                    if (!insertItem(original, FUEL_SLOT, FUEL_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < PLAYER_INV_END) {
                    // Main inventory -> hotbar
                    if (!insertItem(original, PLAYER_INV_END, PLAYER_HOTBAR_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    // Hotbar -> main inventory
                    if (!insertItem(original, PLAYER_INV_START, PLAYER_INV_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (original.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (original.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, original);
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return blockEntity.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory inv) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory inv) {
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(inv, col, 8 + col * 18, 142));
        }
    }

    // Helper inventory class for client-side fallback
    private static class SimpleBlockEntityInventory implements Inventory {
        private final DefaultedList<ItemStack> items = DefaultedList.ofSize(3, ItemStack.EMPTY);

        @Override
        public int size() {
            return 3;
        }

        @Override
        public boolean isEmpty() {
            for (ItemStack stack : items) {
                if (!stack.isEmpty()) return false;
            }
            return true;
        }

        @Override
        public ItemStack getStack(int slot) {
            return items.get(slot);
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            return items.get(slot).split(amount);
        }

        @Override
        public ItemStack removeStack(int slot) {
            ItemStack stack = items.get(slot);
            items.set(slot, ItemStack.EMPTY);
            return stack;
        }

        @Override
        public void setStack(int slot, ItemStack stack) {
            items.set(slot, stack);
        }

        @Override
        public void markDirty() {}

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return true;
        }

        @Override
        public void clear() {
            items.clear();
        }
    }
}
