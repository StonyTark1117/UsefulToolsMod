package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.block.custom.SpectralInfuserBlock;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class SpectralInfuserBlockEntity extends BlockEntity implements MenuProvider, Container {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return switch (slot) {
                case 0 -> isInfusable(stack);
                case 1 -> stack.is(ModItems.ECTOPLASM.get());
                case 2 -> false; // output only
                default -> false;
            };
        }
    };

    private int progress = 0;
    private int maxProgress = 200;

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> progress = value;
                case 1 -> maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public SpectralInfuserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.SPECTRAL_INFUSER.get(), pos, state);
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public ContainerData getData() {
        return data;
    }

    // ── Container implementation (for Containers.dropContents) ──────────────

    @Override
    public int getContainerSize() {
        return itemHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return itemHandler.extractItem(slot, amount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = itemHandler.getStackInSlot(slot);
        itemHandler.setStackInSlot(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        itemHandler.setStackInSlot(slot, stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    // ── MenuProvider ────────────────────────────────────────────────────────

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.usefultoolsmod.spectral_infuser");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inv, Player player) {
        return new SpectralInfuserMenu(containerId, inv, this, this.data);
    }

    // ── Serialization ───────────────────────────────────────────────────────

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.put("inventory", itemHandler.serializeNBT(provider));
        tag.putInt("progress", progress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        itemHandler.deserializeNBT(provider, tag.getCompound("inventory"));
        progress = tag.getInt("progress");
    }

    // ── Tick / Processing ───────────────────────────────────────────────────

    public static void tick(Level level, BlockPos pos, BlockState state,
                            SpectralInfuserBlockEntity be) {
        boolean powered = level.hasNeighborSignal(pos);

        if (!powered && be.hasRecipe()) {
            be.progress++;
            if (!state.getValue(SpectralInfuserBlock.LIT)) {
                level.setBlock(pos, state.setValue(SpectralInfuserBlock.LIT, true), 3);
            }
            be.setChanged();
            if (be.progress >= be.maxProgress) {
                be.craftItem();
                be.resetProgress();
            }
        } else {
            if (be.progress > 0) {
                be.resetProgress();
            }
            if (state.getValue(SpectralInfuserBlock.LIT)) {
                level.setBlock(pos, state.setValue(SpectralInfuserBlock.LIT, false), 3);
            }
        }
    }

    private boolean hasRecipe() {
        ItemStack input = itemHandler.getStackInSlot(0);
        ItemStack fuel = itemHandler.getStackInSlot(1);
        ItemStack output = itemHandler.getStackInSlot(2);
        return isInfusable(input) && fuel.is(ModItems.ECTOPLASM.get())
                && !fuel.isEmpty() && output.isEmpty();
    }

    private void craftItem() {
        ItemStack input = itemHandler.getStackInSlot(0);
        ItemStack result;

        if (input.is(Items.EGG)) {
            // Egg + Ectoplasm → Ghost Spawn Egg
            result = new ItemStack(ModItems.GHOST_SPAWN_EGG.get());
        } else {
            result = input.copy();
            // Reset durability to full
            if (result.has(DataComponents.DAMAGE)) {
                result.set(DataComponents.DAMAGE, 0);
            }
            EctoplasmInfusionHelper.setInfused(result, true);
        }

        itemHandler.setStackInSlot(2, result);
        itemHandler.setStackInSlot(0, ItemStack.EMPTY);
        itemHandler.getStackInSlot(1).shrink(1);
    }

    private void resetProgress() {
        progress = 0;
    }

    public static boolean isInfusable(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.getItem() instanceof TieredItem
                || stack.getItem() instanceof ArmorItem
                || stack.is(Items.EGG);
    }
}
