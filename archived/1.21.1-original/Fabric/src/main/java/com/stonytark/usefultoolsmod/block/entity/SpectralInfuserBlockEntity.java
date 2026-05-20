package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.block.custom.SpectralInfuserBlock;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SpectralInfuserBlockEntity extends BlockEntity implements Inventory, ExtendedScreenHandlerFactory<BlockPos> {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private int progress = 0;
    private int maxProgress = 200;

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
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
        public int size() {
            return 2;
        }
    };

    public SpectralInfuserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.SPECTRAL_INFUSER_BE, pos, state);
    }

    public PropertyDelegate getPropertyDelegate() {
        return propertyDelegate;
    }

    // ── Inventory implementation ──────────────────────────────────────────────

    @Override
    public int size() {
        return items.size();
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
        ItemStack stack = Inventories.splitStack(items, slot, amount);
        markDirty();
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack stack = Inventories.removeStack(items, slot);
        markDirty();
        return stack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        items.set(slot, stack);
        markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public void clear() {
        items.clear();
    }

    // ── Getters for menu ──────────────────────────────────────────────────────

    public ItemStack getInputStack() {
        return items.get(0);
    }

    public ItemStack getFuelStack() {
        return items.get(1);
    }

    public ItemStack getOutputStack() {
        return items.get(2);
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    // ── NBT Serialization ─────────────────────────────────────────────────────

    @Override
    protected void writeNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(tag, registryLookup);
        Inventories.writeNbt(tag, items, registryLookup);
        tag.putInt("progress", progress);
    }

    @Override
    protected void readNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(tag, registryLookup);
        Inventories.readNbt(tag, items, registryLookup);
        progress = tag.getInt("progress");
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound tag = new NbtCompound();
        writeNbt(tag, registryLookup);
        return tag;
    }

    // ── Tick / Processing ─────────────────────────────────────────────────────

    public static void tick(World level, BlockPos pos, BlockState state, SpectralInfuserBlockEntity be) {
        boolean powered = level.isReceivingRedstonePower(pos);

        if (!powered && be.hasRecipe()) {
            be.progress++;
            if (!state.get(SpectralInfuserBlock.LIT)) {
                level.setBlockState(pos, state.with(SpectralInfuserBlock.LIT, true), 3);
            }
            be.markDirty();
            if (be.progress >= be.maxProgress) {
                be.craftItem();
                be.resetProgress();
                be.markDirty();
                if (level instanceof net.minecraft.server.world.ServerWorld sw) {
                    sw.getChunkManager().markForUpdate(pos);
                }
            }
        } else {
            if (be.progress > 0) {
                be.resetProgress();
            }
            if (state.get(SpectralInfuserBlock.LIT)) {
                level.setBlockState(pos, state.with(SpectralInfuserBlock.LIT, false), 3);
            }
        }
    }

    private boolean hasRecipe() {
        ItemStack input = items.get(0);
        ItemStack fuel = items.get(1);
        ItemStack output = items.get(2);
        return isInfusable(input) && fuel.isOf(ModItems.ECTOPLASM) && !fuel.isEmpty() && output.isEmpty();
    }

    private void craftItem() {
        ItemStack input = items.get(0);
        ItemStack result;

        if (input.isOf(Items.EGG)) {
            result = new ItemStack(ModItems.GHOST_SPAWN_EGG);
        } else {
            result = input.copy();
            result.setCount(1);
            result.setDamage(0);
            EctoplasmInfusionHelper.setInfused(result, true);
        }

        items.set(2, result);
        items.set(0, ItemStack.EMPTY);
        items.get(1).decrement(1);
    }

    private void resetProgress() {
        progress = 0;
    }

    public static boolean isInfusable(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.getItem() instanceof ToolItem
                || stack.getItem() instanceof ArmorItem
                || stack.isOf(Items.EGG);
    }

    // ── ExtendedScreenHandlerFactory<BlockPos> ───────────────────────────────

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.usefultoolsmod.spectral_infuser");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SpectralInfuserMenu(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return this.pos;
    }
}
