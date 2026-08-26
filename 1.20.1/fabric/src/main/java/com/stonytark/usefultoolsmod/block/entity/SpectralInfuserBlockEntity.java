package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.block.custom.SpectralInfuserBlock;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SpectralInfuserBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private int progress = 0;
    private int maxProgress = 200;
    private int fuelUses = 0;

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> maxProgress;
                case 2 -> fuelUses;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> progress = value;
                case 1 -> maxProgress = value;
                case 2 -> fuelUses = value;
            }
        }

        @Override
        public int size() {
            return 3;
        }
    };

    public SpectralInfuserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.SPECTRAL_INFUSER, pos, state);
    }

    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    public PropertyDelegate getPropertyDelegate() {
        return propertyDelegate;
    }

    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SpectralInfuserMenu(syncId, playerInventory, this, propertyDelegate);
    }

    public Text getDisplayName() {
        return Text.translatable("block.usefultoolsmod.spectral_infuser");
    }

    public boolean canPlayerUseInverted(PlayerEntity player) {
        return this.canPlayerUse(player);
    }

    private boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.getPos()) != this) {
            return false;
        }
        return player.squaredDistanceTo((double)this.getPos().getX() + 0.5, (double)this.getPos().getY() + 0.5, (double)this.getPos().getZ() + 0.5) <= 64.0;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.getPos());
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        Inventories.readNbt(tag, inventory);
        progress = tag.getInt("progress");
        fuelUses = tag.getInt("fuelUses");
    }

    @Override
    protected void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        Inventories.writeNbt(tag, inventory);
        tag.putInt("progress", progress);
        tag.putInt("fuelUses", fuelUses);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound tag = new NbtCompound();
        writeNbt(tag);
        return tag;
    }

    public static void tick(World world, BlockPos pos, BlockState state,
                            SpectralInfuserBlockEntity be) {
        if (!Config.spectralInfuserEnabled) {
            if (be.progress > 0) be.resetProgress();
            if (state.get(SpectralInfuserBlock.LIT))
                world.setBlockState(pos, state.with(SpectralInfuserBlock.LIT, false), 3);
            return;
        }
        boolean powered = world.isReceivingRedstonePower(pos);

        if (!powered && be.hasRecipe()) {
            be.progress++;
            if (!state.get(SpectralInfuserBlock.LIT)) {
                world.setBlockState(pos, state.with(SpectralInfuserBlock.LIT, true), 3);
            }
            be.markDirty();
            if (be.progress >= be.maxProgress) {
                be.craftItem();
                be.resetProgress();
            }
        } else {
            if (be.progress > 0) {
                be.resetProgress();
            }
            if (state.get(SpectralInfuserBlock.LIT)) {
                world.setBlockState(pos, state.with(SpectralInfuserBlock.LIT, false), 3);
            }
        }
    }

    private boolean hasRecipe() {
        ItemStack input = inventory.get(0);
        ItemStack fuel = inventory.get(1);
        ItemStack output = inventory.get(2);
        boolean hasFuel = fuelUses > 0 || fuel.isOf(ModItems.ECTOPLASM) || fuel.isOf(ModItems.CONDENSED_ECTOPLASM);
        return isInfusable(input) && hasFuel && output.isEmpty();
    }

    private void craftItem() {
        ItemStack input = inventory.get(0);
        ItemStack result;

        if (input.isOf(Items.EGG)) {
            result = new ItemStack(ModItems.GHOST_SPAWN_EGG);
        } else {
            result = input.copy();
            result.setCount(1);
            result.setDamage(0);
            EctoplasmInfusionHelper.setInfused(result, true);
        }

        inventory.set(2, result);
        inventory.set(0, ItemStack.EMPTY);
        if (fuelUses <= 0) {
            ItemStack fuel = inventory.get(1); fuelUses = fuel.isOf(ModItems.CONDENSED_ECTOPLASM) ? 8 : 1; fuel.decrement(1);
        }
        fuelUses--; markDirty();
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
}
