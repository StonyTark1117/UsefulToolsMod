package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock;
import com.stonytark.usefultoolsmod.util.ControlledExplosion;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class MiningChargeBlockEntity extends BlockEntity {
    private int fuse = -1;
    private int channel;
    private UUID owner;

    public MiningChargeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.MINING_CHARGE.get(), pos, state);
    }

    public void prime(ServerPlayer player) {
        if (fuse >= 0) return;
        if (player != null) owner = player.getUUID();
        fuse = 80;
        if (level != null) level.setBlock(worldPosition, getBlockState().setValue(MiningChargeBlock.LIT, true), 3);
        setChanged();
    }

    public boolean link(ServerPlayer player, int selectedChannel) {
        if (owner != null && !owner.equals(player.getUUID())) return false;
        owner = player.getUUID(); channel = selectedChannel; setChanged(); return true;
    }

    public boolean detonate(ServerPlayer player, int selectedChannel) {
        if (owner == null || !owner.equals(player.getUUID()) || channel != selectedChannel) return false;
        fuse = 1; setChanged(); return true;
    }

    public UUID owner() { return owner; }
    public int channel() { return channel; }

    public static void tick(Level level, BlockPos pos, BlockState state, MiningChargeBlockEntity charge) {
        if (charge.fuse < 0) return;
        if (--charge.fuse > 0) { charge.setChanged(); return; }
        ServerLevel server = (ServerLevel)level;
        ServerPlayer owner = charge.owner == null ? null : server.getServer().getPlayerList().getPlayer(charge.owner);
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        ControlledExplosion.excavate(server, pos, state.getValue(MiningChargeBlock.FACING), owner);
        if (owner != null) com.stonytark.usefultoolsmod.util.ModAdvancements.award(owner, "explosives/precision_excavation");
    }

    @Override protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag); tag.putInt("Fuse", fuse); tag.putInt("Channel", channel);
        if (owner != null) tag.putUUID("Owner", owner);
    }
    @Override public void load(CompoundTag tag) {
        super.load(tag); fuse = tag.contains("Fuse") ? tag.getInt("Fuse") : -1; channel = tag.getInt("Channel");
        owner = tag.hasUUID("Owner") ? tag.getUUID("Owner") : null;
    }
}
