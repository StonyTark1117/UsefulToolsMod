package com.stonytark.usefultoolsmod.util;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public final class ControlledExplosion {
    public static final TagKey<Block> IMMUNE = TagKey.of(RegistryKeys.BLOCK,
            Identifier.of(UsefultoolsMod.MOD_ID, "controlled_explosion_immune"));
    private ControlledExplosion() {}

    public static int excavate(ServerWorld world, BlockPos chargePos, Direction inward, Entity owner) {
        int broken = 0;
        Direction.Axis first = inward.getAxis() == Direction.Axis.Y ? Direction.Axis.X
                : inward.getAxis() == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        Direction.Axis second = inward.getAxis() == Direction.Axis.Y ? Direction.Axis.Z : Direction.Axis.Y;
        for (int depth = 1; depth <= 4; depth++) {
            BlockPos center = chargePos.offset(inward, depth);
            for (int a = -1; a <= 1; a++) for (int b = -1; b <= 1; b++) {
                BlockPos target = offset(center, first, a, second, b);
                if (breakSafely(world, target, owner)) broken++;
            }
        }
        damageEntities(world, Vec3d.ofCenter(chargePos.offset(inward, 2)), 4.5D, owner);
        return broken;
    }

    public static int burst(ServerWorld world, Vec3d center, double radius, Entity owner) {
        int broken = 0; BlockPos origin = BlockPos.ofFloored(center); int r = (int)Math.ceil(radius);
        for (BlockPos cursor : BlockPos.iterate(origin.add(-r, -r, -r), origin.add(r, r, r))) {
            if (Vec3d.ofCenter(cursor).distanceTo(center) <= radius && breakSafely(world, cursor, owner)) broken++;
        }
        damageEntities(world, center, radius, owner); return broken;
    }

    private static BlockPos offset(BlockPos pos, Direction.Axis first, int a, Direction.Axis second, int b) {
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        if (first == Direction.Axis.X) x += a; else if (first == Direction.Axis.Y) y += a; else z += a;
        if (second == Direction.Axis.X) x += b; else if (second == Direction.Axis.Y) y += b; else z += b;
        return new BlockPos(x, y, z);
    }

    private static boolean breakSafely(ServerWorld world, BlockPos pos, Entity owner) {
        BlockState state = world.getBlockState(pos);
        if (state.isAir() || state.isIn(IMMUNE) || state.isOf(Blocks.BEDROCK)) return false;
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null || state.getHardness(world, pos) < 0) return false;
        if (Config.controlledDrops) Block.dropStacks(state, world, pos, null, owner, ItemStack.EMPTY);
        return world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
    }

    private static void damageEntities(ServerWorld world, Vec3d center, double radius, Entity owner) {
        for (LivingEntity living : world.getEntitiesByClass(LivingEntity.class, new Box(center, center).expand(radius), e -> true)) {
            double distance = living.getPos().distanceTo(center); if (distance > radius) continue;
            double configuredCap = Math.min(8.0D, Config.controlledEntityDamage);
            float damage = (float)Math.min(configuredCap, configuredCap * (1.0D - distance / radius));
            if (damage > 0) living.damage(world.getDamageSources().explosion(null, owner), damage);
        }
    }
}
