package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock;
import com.stonytark.usefultoolsmod.util.ControlledExplosion;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class MiningChargeBlockEntity extends BlockEntity {
    private int fuse = -1;
    private int channel;
    private UUID owner;
    public MiningChargeBlockEntity(BlockPos pos, BlockState state) { super(ModBlockEntityTypes.MINING_CHARGE, pos, state); }

    public void prime(ServerPlayerEntity player) {
        if (fuse >= 0) return; if (player != null) owner = player.getUuid(); fuse = 80;
        if (world != null) world.setBlockState(pos, getCachedState().with(MiningChargeBlock.LIT, true), 3); markDirty();
    }
    public boolean link(ServerPlayerEntity player, int selectedChannel) {
        if (owner != null && !owner.equals(player.getUuid())) return false;
        owner = player.getUuid(); channel = selectedChannel; markDirty(); return true;
    }
    public boolean detonate(ServerPlayerEntity player, int selectedChannel) {
        if (owner == null || !owner.equals(player.getUuid()) || channel != selectedChannel) return false;
        fuse = 1; markDirty(); return true;
    }
    public UUID owner() { return owner; }
    public int channel() { return channel; }

    public static void tick(World world, BlockPos pos, BlockState state, MiningChargeBlockEntity charge) {
        if (charge.fuse < 0) return; if (--charge.fuse > 0) { charge.markDirty(); return; }
        ServerWorld server = (ServerWorld)world;
        ServerPlayerEntity owner = charge.owner == null ? null : server.getServer().getPlayerManager().getPlayer(charge.owner);
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        ControlledExplosion.excavate(server, pos, state.get(MiningChargeBlock.FACING), owner);
        if (owner != null) com.stonytark.usefultoolsmod.util.ModAdvancements.award(owner, "explosives/precision_excavation");
    }

    @Override protected void readNbt(NbtCompound tag, RegistryWrapper.WrapperLookup lookup) {
        super.readNbt(tag, lookup); fuse = tag.contains("Fuse") ? tag.getInt("Fuse") : -1; channel = tag.getInt("Channel");
        owner = tag.containsUuid("Owner") ? tag.getUuid("Owner") : null;
    }
    @Override protected void writeNbt(NbtCompound tag, RegistryWrapper.WrapperLookup lookup) {
        super.writeNbt(tag, lookup); tag.putInt("Fuse", fuse); tag.putInt("Channel", channel); if (owner != null) tag.putUuid("Owner", owner);
    }
}
