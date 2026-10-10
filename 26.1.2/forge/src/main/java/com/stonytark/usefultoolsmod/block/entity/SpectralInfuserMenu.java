package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.item.ModItems;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpectralInfuserMenu extends AbstractContainerMenu {
    private final SpectralInfuserBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    // Slot indices
    private static final int PLAYER_INV_START = 0;
    private static final int PLAYER_INV_END = 27;
    private static final int PLAYER_HOTBAR_END = 36;
    private static final int INPUT_SLOT = 36;
    private static final int FUEL_SLOT = 37;
    private static final int OUTPUT_SLOT = 38;

    // Server-side constructor
    public SpectralInfuserMenu(int containerId, Inventory inv,
                               SpectralInfuserBlockEntity be, ContainerData data) {
        this(containerId, inv, be, data, be);
    }

    private SpectralInfuserMenu(int containerId, Inventory inv,
                               SpectralInfuserBlockEntity be, ContainerData data, Container contents) {
        super(ModMenuTypes.SPECTRAL_INFUSER_MENU.get(), containerId);
        this.blockEntity = be;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        // Server slots use the persisted block entity; client slots receive native menu packets.
        this.addSlot(new Slot(contents, 0, 56, 17) {                                  // input (top)
            @Override
            public boolean mayPlace(ItemStack stack) {
                return SpectralInfuserBlockEntity.isInfusable(stack);
            }
        });
        this.addSlot(new Slot(contents, 1, 56, 53) {                                  // ectoplasm (bottom)
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItems.ECTOPLASM.get()) || stack.is(ModItems.CONDENSED_ECTOPLASM.get());
            }
        });
        this.addSlot(new Slot(contents, 2, 116, 35) {                                 // output (right)
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false; // output-only
            }
        });

        addDataSlots(data);
    }

    // The native menu-open and contents packets share Minecraft's packet queue.
    // Client rendering needs synchronized slots, not a block-entity position.
    public SpectralInfuserMenu(int containerId, Inventory inv) {
        this(containerId, inv, null, new SimpleContainerData(3), new SimpleContainer(3));
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getFuelUses() { return data.get(2); }

    public int getScaledProgress() {
        int progress = data.get(0);
        int maxProgress = data.get(1);
        int arrowPixelWidth = 24;
        return maxProgress != 0 ? progress * arrowPixelWidth / maxProgress : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack original = slot.getItem();
            newStack = original.copy();

            if (index == OUTPUT_SLOT) {
                // Output → player inventory
                if (!this.moveItemStackTo(original, PLAYER_INV_START, PLAYER_HOTBAR_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(original, newStack);
            } else if (index >= INPUT_SLOT) {
                // Input/fuel → player inventory
                if (!this.moveItemStackTo(original, PLAYER_INV_START, PLAYER_HOTBAR_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Player inventory → block entity slots
                if (SpectralInfuserBlockEntity.isInfusable(original)) {
                    if (!this.moveItemStackTo(original, INPUT_SLOT, INPUT_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (original.is(ModItems.ECTOPLASM.get()) || original.is(ModItems.CONDENSED_ECTOPLASM.get())) {
                    if (!this.moveItemStackTo(original, FUEL_SLOT, FUEL_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < PLAYER_INV_END) {
                    // Main inventory → hotbar
                    if (!this.moveItemStackTo(original, PLAYER_INV_END, PLAYER_HOTBAR_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    // Hotbar → main inventory
                    if (!this.moveItemStackTo(original, PLAYER_INV_START, PLAYER_INV_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (original.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (original.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, original);
        }
        return newStack;
    }

    @Override
    public boolean stillValid(Player player) {
        if (blockEntity == null) return level.isClientSide();
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.SPECTRAL_INFUSER.get());
    }

    private void addPlayerInventory(Inventory inv) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inv, col, 8 + col * 18, 142));
        }
    }
}
