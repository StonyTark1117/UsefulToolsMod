package com.stonytark.usefultoolsmod.util;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Mining-safe destruction shared by Mining Charges and Sticky Dynamite. */
public final class ControlledExplosion {
    public static final TagKey<Block> IMMUNE = BlockTags.create(
            ResourceLocation.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, "controlled_explosion_immune"));

    private ControlledExplosion() {}

    public static int excavate(ServerLevel level, BlockPos chargePos, Direction inward, Player owner) {
        int broken = 0;
        Direction.Axis horizontalAxis = inward.getAxis() == Direction.Axis.Y ? Direction.Axis.X
                : (inward.getAxis() == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X);
        Direction.Axis verticalAxis = inward.getAxis() == Direction.Axis.Y ? Direction.Axis.Z : Direction.Axis.Y;
        for (int depth = 1; depth <= 4; depth++) {
            BlockPos center = chargePos.relative(inward, depth);
            for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) {
                    BlockPos target = offset(center, horizontalAxis, a, verticalAxis, b);
                    if (breakSafely(level, target, owner)) broken++;
                }
            }
        }
        damageEntities(level, Vec3.atCenterOf(chargePos.relative(inward, 2)), 4.5D, owner);
        return broken;
    }

    public static int burst(ServerLevel level, Vec3 center, double radius, Entity owner) {
        int broken = 0;
        BlockPos origin = BlockPos.containing(center);
        int r = (int)Math.ceil(radius);
        for (BlockPos cursor : BlockPos.betweenClosed(origin.offset(-r, -r, -r), origin.offset(r, r, r))) {
            if (Vec3.atCenterOf(cursor).distanceTo(center) <= radius && breakSafely(level, cursor, owner)) broken++;
        }
        damageEntities(level, center, radius, owner);
        return broken;
    }

    private static BlockPos offset(BlockPos pos, Direction.Axis first, int a, Direction.Axis second, int b) {
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        if (first == Direction.Axis.X) x += a; else if (first == Direction.Axis.Y) y += a; else z += a;
        if (second == Direction.Axis.X) x += b; else if (second == Direction.Axis.Y) y += b; else z += b;
        return new BlockPos(x, y, z);
    }

    private static boolean breakSafely(ServerLevel level, BlockPos pos, Entity owner) {
        BlockState state = level.getBlockState(pos);
        if (state.isAir() || state.is(IMMUNE) || state.is(Blocks.BEDROCK)) return false;
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity != null || state.getDestroySpeed(level, pos) < 0.0F) return false;
        if (Config.controlledDrops) Block.dropResources(state, level, pos, null, owner, net.minecraft.world.item.ItemStack.EMPTY);
        return level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
    }

    private static void damageEntities(ServerLevel level, Vec3 center, double radius, Entity owner) {
        AABB bounds = new AABB(center, center).inflate(radius);
        for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, bounds)) {
            double distance = Math.sqrt(living.distanceToSqr(center));
            if (distance > radius) continue;
            double configuredCap = Math.min(8.0D, Config.controlledEntityDamage);
            float damage = (float)Math.min(configuredCap, configuredCap * (1.0D - distance / radius));
            if (damage > 0) living.hurt(level.damageSources().explosion(null, owner), damage);
        }
    }
}
