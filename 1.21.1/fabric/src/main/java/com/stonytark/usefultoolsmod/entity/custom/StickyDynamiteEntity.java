package com.stonytark.usefultoolsmod.entity.custom;

import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.util.ControlledExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.UUID;

public class StickyDynamiteEntity extends ThrownItemEntity {
    private int fuse = 80; private boolean stuck; private BlockPos stuckPos; private UUID stuckEntity;
    public StickyDynamiteEntity(EntityType<? extends StickyDynamiteEntity> type, World world) { super(type, world); }
    public StickyDynamiteEntity(EntityType<? extends StickyDynamiteEntity> type, World world, LivingEntity owner) { super(type, owner, world); }

    @Override protected void onCollision(HitResult result) {
        if (stuck) return;
        if (result instanceof EntityHitResult hit) stuckEntity = hit.getEntity().getUuid();
        else if (result instanceof BlockHitResult hit) stuckPos = hit.getBlockPos();
        stuck = true; setNoGravity(true); setVelocity(Vec3d.ZERO);
    }
    @Override public void tick() {
        if (!stuck) super.tick();
        else if (stuckEntity != null && getWorld() instanceof ServerWorld server) {
            Entity attached = server.getEntity(stuckEntity);
            if (attached != null) setPosition(attached.getX(), attached.getY() + attached.getHeight() * 0.5D, attached.getZ());
        } else if (stuckPos != null) setPosition(Vec3d.ofCenter(stuckPos));
        if (getWorld().isClient) getWorld().addParticle(ParticleTypes.SMOKE, getX(), getY() + 0.1D, getZ(), 0, 0.01D, 0);
        else if (--fuse <= 0) { ControlledExplosion.burst((ServerWorld)getWorld(), getPos(), 4.0D, getOwner()); discard(); }
    }
    @Override protected Item getDefaultItem() { return ModItems.STICKY_DYNAMITE; }
    @Override public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag); tag.putInt("Fuse", fuse); tag.putBoolean("Stuck", stuck);
        if (stuckPos != null) tag.putLong("StuckPos", stuckPos.asLong()); if (stuckEntity != null) tag.putUuid("StuckEntity", stuckEntity);
    }
    @Override public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag); fuse = tag.getInt("Fuse"); stuck = tag.getBoolean("Stuck");
        stuckPos = tag.contains("StuckPos") ? BlockPos.fromLong(tag.getLong("StuckPos")) : null;
        stuckEntity = tag.containsUuid("StuckEntity") ? tag.getUuid("StuckEntity") : null;
    }
}
