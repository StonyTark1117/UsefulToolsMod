package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.block.custom.SpectralInfuserBlock;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
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
import org.jetbrains.annotations.Nullable;

/**
 * Spectral Infuser block entity — a 3-slot container that infuses items
 * with ectoplasm. Slot 0 = input (tool/armor/egg), Slot 1 = ectoplasm fuel,
 * Slot 2 = output.
 *
 * Migrated from NeoForge ItemStackHandler to vanilla Container + NonNullList
 * for cross-loader compatibility on Architectury.
 */
public class SpectralInfuserBlockEntity extends BlockEntity implements MenuProvider, Container {
    private final NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

    private int progress = 0;
    private int maxProgress = 200;

    private final ContainerData data = new ContainerData() {
        @Override public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> maxProgress;
                default -> 0;
            };
        }
        @Override public void set(int index, int value) {
            switch (index) {
                case 0 -> progress = value;
                case 1 -> maxProgress = value;
            }
        }
        @Override public int getCount() { return 2; }
    };

    public SpectralInfuserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.SPECTRAL_INFUSER.get(), pos, state);
    }

    public ContainerData getData() { return data; }

    // ── Container implementation ─────────────────────────────────────────────

    @Override public int getContainerSize() { return items.size(); }

    @Override public boolean isEmpty() {
        for (ItemStack stack : items) if (!stack.isEmpty()) return false;
        return true;
    }

    @Override public ItemStack getItem(int slot) { return items.get(slot); }

    @Override public ItemStack removeItem(int slot, int amount) {
        ItemStack result = ContainerHelper.removeItem(items, slot, amount);
        if (!result.isEmpty()) setChanged();
        return result;
    }

    @Override public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) stack.setCount(getMaxStackSize());
        setChanged();
    }

    @Override public boolean canPlaceItem(int slot, ItemStack stack) {
        return switch (slot) {
            case 0 -> isInfusable(stack);
            case 1 -> stack.is(ModItems.ECTOPLASM.get());
            case 2 -> false; // output-only
            default -> false;
        };
    }

    @Override public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override public void clearContent() {
        items.clear();
    }

    // ── MenuProvider ─────────────────────────────────────────────────────────

    @Override public Component getDisplayName() {
        return Component.translatable("block.usefultoolsmod.spectral_infuser");
    }

    @Nullable
    @Override public AbstractContainerMenu createMenu(int containerId, Inventory inv, Player player) {
        return new SpectralInfuserMenu(containerId, inv, this, this.data);
    }

    // ── Serialization ────────────────────────────────────────────────────────

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        ContainerHelper.saveAllItems(tag, items, provider);
        tag.putInt("progress", progress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        items.clear();
        ContainerHelper.loadAllItems(tag, items, provider);
        progress = tag.getInt("progress");
    }

    // ── Tick / Processing ────────────────────────────────────────────────────

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
            if (be.progress > 0) be.resetProgress();
            if (state.getValue(SpectralInfuserBlock.LIT)) {
                level.setBlock(pos, state.setValue(SpectralInfuserBlock.LIT, false), 3);
            }
        }
    }

    private boolean hasRecipe() {
        ItemStack input = items.get(0);
        ItemStack fuel = items.get(1);
        ItemStack output = items.get(2);
        return isInfusable(input) && fuel.is(ModItems.ECTOPLASM.get())
                && !fuel.isEmpty() && output.isEmpty();
    }

    private void craftItem() {
        ItemStack input = items.get(0);
        ItemStack result;

        if (input.is(Items.EGG)) {
            result = new ItemStack(ModItems.GHOST_SPAWN_EGG.get());
        } else {
            result = input.copy();
            if (result.has(DataComponents.DAMAGE)) {
                result.set(DataComponents.DAMAGE, 0);
            }
            EctoplasmInfusionHelper.setInfused(result, true);
        }

        items.set(2, result);
        items.set(0, ItemStack.EMPTY);
        items.get(1).shrink(1);
        setChanged();
    }

    private void resetProgress() { progress = 0; }

    public static boolean isInfusable(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.getItem() instanceof TieredItem
                || stack.getItem() instanceof ArmorItem
                || stack.is(Items.EGG);
    }
}
