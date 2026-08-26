package com.stonytark.usefultoolsmod.classic;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public final class ClassicControlledExplosion {
    private static final DamageSource DAMAGE = new DamageSource("usefulMiningCharge").setExplosion();
    private ClassicControlledExplosion() {}

    public static void miningCharge(World world, int x, int y, int z, int facing) {
        if (!ClassicGeneratedConfig.booleanValue("explosivesEnabled")
                || !ClassicGeneratedConfig.booleanValue("miningChargeEnabled")) return;
        int dx = facing == 1 ? -1 : facing == 3 ? 1 : 0;
        int dz = facing == 0 ? 1 : facing == 2 ? -1 : 0;
        for (int depth = 1; depth <= 4; depth++) for (int a = -1; a <= 1; a++) for (int b = -1; b <= 1; b++) {
            int bx = x + dx * depth + (dx == 0 ? a : 0);
            int by = y + b;
            int bz = z + dz * depth + (dz == 0 ? a : 0);
            Block block = world.getBlock(bx, by, bz);
            if (block == Blocks.air || block == Blocks.bedrock || world.getTileEntity(bx, by, bz) != null) continue;
            if (block.getBlockHardness(world, bx, by, bz) < 0) continue;
            world.func_147480_a(bx, by, bz, ClassicGeneratedConfig.booleanValue("controlledDrops"));
        }
        List hit = world.getEntitiesWithinAABB(EntityLivingBase.class,
                AxisAlignedBB.getBoundingBox(x - 5, y - 4, z - 5, x + 5, y + 4, z + 5));
        for (Object value : hit) {
            Entity entity = (Entity)value; double distance = Math.sqrt(entity.getDistanceSq(x + .5, y + .5, z + .5));
            if (distance <= 5) entity.attackEntityFrom(DAMAGE, (float)damageAtDistance(
                    ClassicGeneratedConfig.doubleValue("controlledEntityDamage"), distance, 6D));
        }
        world.playSoundEffect(x + .5, y + .5, z + .5, "random.explode", 3F, 1F);
        world.spawnParticle("hugeexplosion", x + .5, y + .5, z + .5, 0, 0, 0);
    }

    /** Mining-safe spherical burst used by Sticky Dynamite. */
    public static void stickyBurst(World world, double x, double y, double z) {
        if (!ClassicGeneratedConfig.booleanValue("explosivesEnabled")
                || !ClassicGeneratedConfig.booleanValue("stickyDynamiteEnabled")) return;
        int radius = 4;
        int minX = (int)Math.floor(x - radius), maxX = (int)Math.floor(x + radius);
        int minY = (int)Math.floor(y - radius), maxY = (int)Math.floor(y + radius);
        int minZ = (int)Math.floor(z - radius), maxZ = (int)Math.floor(z + radius);
        for (int bx = minX; bx <= maxX; bx++) for (int by = minY; by <= maxY; by++)
            for (int bz = minZ; bz <= maxZ; bz++) {
                double dx = bx + .5D - x, dy = by + .5D - y, dz = bz + .5D - z;
                if (dx * dx + dy * dy + dz * dz > radius * radius) continue;
                Block block = world.getBlock(bx, by, bz);
                if (block == Blocks.air || block == Blocks.bedrock || world.getTileEntity(bx, by, bz) != null) continue;
                if (block.getBlockHardness(world, bx, by, bz) < 0) continue;
                world.func_147480_a(bx, by, bz, ClassicGeneratedConfig.booleanValue("controlledDrops"));
            }
        List hit = world.getEntitiesWithinAABB(EntityLivingBase.class,
                AxisAlignedBB.getBoundingBox(x - radius, y - radius, z - radius,
                        x + radius, y + radius, z + radius));
        for (Object value : hit) {
            Entity entity = (Entity)value;
            double distance = Math.sqrt(entity.getDistanceSq(x, y, z));
            if (distance <= radius) entity.attackEntityFrom(DAMAGE, (float)damageAtDistance(
                    ClassicGeneratedConfig.doubleValue("controlledEntityDamage"), distance, radius));
        }
        world.playSoundEffect(x, y, z, "random.explode", 3F, 1F);
        world.spawnParticle("hugeexplosion", x, y, z, 0, 0, 0);
    }

    static double damageAtDistance(double configuredDamage, double distance, double radius) {
        double cap = Math.max(0D, Math.min(8D, configuredDamage));
        return Math.max(0D, cap * (1D - distance / radius));
    }
}
