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
    private static final net.minecraft.entity.data.TrackedData<Integer> MODEL_FACE = net.minecraft.entity.data.DataTracker.registerData(StickyDynamiteEntity.class, net.minecraft.entity.data.TrackedDataHandlerRegistry.INTEGER);
    private static final net.minecraft.entity.data.TrackedData<Float> MODEL_RADIUS = net.minecraft.entity.data.DataTracker.registerData(StickyDynamiteEntity.class, net.minecraft.entity.data.TrackedDataHandlerRegistry.FLOAT);

    @Override protected void initDataTracker() {
        super.initDataTracker(); dataTracker.startTracking(MODEL_FACE, -1); dataTracker.startTracking(MODEL_RADIUS, .7F);
    }

    /** Visual displacement only; collision, attachment and explosion use the original position. */
    public Vec3d getModelOffset() {
        int face = dataTracker.get(MODEL_FACE);
        if (face < 0) return Vec3d.ZERO;
        net.minecraft.util.math.Direction direction = net.minecraft.util.math.Direction.byId(face);
        double radius = dataTracker.get(MODEL_RADIUS);
        return new Vec3d(direction.getOffsetX() * radius, direction.getOffsetY() * radius,
                direction.getOffsetZ() * radius);
    }

    private int fuse = 80; private boolean stuck; private BlockPos stuckPos; private UUID stuckEntity;
    public StickyDynamiteEntity(EntityType<? extends StickyDynamiteEntity> type, World world) { super(type, world); }
    public StickyDynamiteEntity(EntityType<? extends StickyDynamiteEntity> type, World world, LivingEntity owner) { super(type, owner, world); }

    @Override protected void onCollision(HitResult result) {
        if (stuck) return;
        if (result instanceof EntityHitResult hit) {
            Entity attached = hit.getEntity();
            stuckEntity = attached.getUuid();
            double dx = getX() - attached.getX(), dz = getZ() - attached.getZ();
            // Use the impacted side of the body; server attachment coordinates stay unchanged.
            dataTracker.set(MODEL_FACE, Math.abs(dx) > Math.abs(dz) ? (dx > 0 ? 5 : 4) : (dz > 0 ? 3 : 2));
            dataTracker.set(MODEL_RADIUS, Math.max(.7F, attached.getWidth() * .5F + .25F));
        }
        else if (result instanceof BlockHitResult hit) { stuckPos = hit.getBlockPos(); dataTracker.set(MODEL_FACE, hit.getSide().getId()); }
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
        super.writeCustomDataToNbt(tag); tag.putInt("ModelFace", dataTracker.get(MODEL_FACE)); tag.putFloat("ModelRadius", dataTracker.get(MODEL_RADIUS)); tag.putInt("Fuse", fuse); tag.putBoolean("Stuck", stuck);
        if (stuckPos != null) tag.putLong("StuckPos", stuckPos.asLong()); if (stuckEntity != null) tag.putUuid("StuckEntity", stuckEntity);
    }
    @Override public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag); dataTracker.set(MODEL_FACE, tag.contains("ModelFace") ? tag.getInt("ModelFace") : -1); dataTracker.set(MODEL_RADIUS, tag.contains("ModelRadius") ? tag.getFloat("ModelRadius") : .7F); fuse = tag.getInt("Fuse"); stuck = tag.getBoolean("Stuck");
        stuckPos = tag.contains("StuckPos") ? BlockPos.fromLong(tag.getLong("StuckPos")) : null;
        stuckEntity = tag.containsUuid("StuckEntity") ? tag.getUuid("StuckEntity") : null;
    }
}
