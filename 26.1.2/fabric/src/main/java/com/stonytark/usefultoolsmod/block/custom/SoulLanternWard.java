package com.stonytark.usefultoolsmod.block.custom;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public final class SoulLanternWard {
    public static final int RADIUS = 12;
    private SoulLanternWard() {}

    public static boolean activeWithin(Entity entity, int radius) {
        return activeWithin(entity.level(), entity.blockPosition(), radius);
    }
    public static boolean activeWithin(LevelAccessor level, BlockPos origin, int radius) {
        if (!com.stonytark.usefultoolsmod.Config.ectoplasmLanternEnabled) return false;
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-radius, -radius, -radius), origin.offset(radius, radius, radius))) {
            if ((level.getBlockState(pos).is(ModBlocks.ECTOPLASM_LANTERN) || level.getBlockState(pos).is(ModBlocks.LEGACY_SOUL_LANTERN)) && !level.hasNeighborSignal(pos)) return true;
        }
        return false;
    }
    public static BlockPos nearest(Entity entity) {
        if (!com.stonytark.usefultoolsmod.Config.ectoplasmLanternEnabled) return null;
        BlockPos origin = entity.blockPosition(), best = null; double bestDistance = Double.MAX_VALUE;
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-RADIUS, -RADIUS, -RADIUS), origin.offset(RADIUS, RADIUS, RADIUS))) {
            if ((entity.level().getBlockState(pos).is(ModBlocks.ECTOPLASM_LANTERN) || entity.level().getBlockState(pos).is(ModBlocks.LEGACY_SOUL_LANTERN)) && !entity.level().hasNeighborSignal(pos)) {
                double distance = pos.distSqr(origin);
                if (distance < bestDistance) { bestDistance = distance; best = pos.immutable(); }
            }
        }
        return best;
    }
}
