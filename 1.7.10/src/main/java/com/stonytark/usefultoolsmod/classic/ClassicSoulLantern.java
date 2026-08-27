package com.stonytark.usefultoolsmod.classic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ClassicSoulLantern extends Block {
    public static final int WARD_RADIUS = 12;

    public ClassicSoulLantern(Material material) {
        super(material);
        setBlockBounds(.25F, 0F, .25F, .75F, 1F, .75F);
    }

    @Override public boolean isOpaqueCube() { return false; }
    @Override public boolean renderAsNormalBlock() { return false; }

    public static boolean activeWithin(World world, double x, double y, double z, int radius) {
        if (!ClassicGeneratedConfig.booleanValue("ectoplasmLanternEnabled")) return false;
        int minX = (int)Math.floor(x) - radius, minY = Math.max(0, (int)Math.floor(y) - radius);
        int minZ = (int)Math.floor(z) - radius, maxY = Math.min(255, (int)Math.floor(y) + radius);
        for (int bx = minX; bx <= x + radius; bx++) for (int by = minY; by <= maxY; by++)
            for (int bz = minZ; bz <= z + radius; bz++)
                if ((world.getBlock(bx, by, bz) == ClassicFeatureRegistry.ectoplasmLantern
                        || world.getBlock(bx, by, bz) == ClassicFeatureRegistry.legacySoulLantern)
                        && !world.isBlockIndirectlyGettingPowered(bx, by, bz)) return true;
        return false;
    }

    public static int[] nearest(World world, double x, double y, double z, int radius) {
        int[] result = null; double best = radius * radius;
        int ox = (int)Math.floor(x), oy = (int)Math.floor(y), oz = (int)Math.floor(z);
        for (int bx = ox - radius; bx <= ox + radius; bx++) for (int by = Math.max(0, oy - radius); by <= Math.min(255, oy + radius); by++)
            for (int bz = oz - radius; bz <= oz + radius; bz++) if ((world.getBlock(bx, by, bz) == ClassicFeatureRegistry.ectoplasmLantern
                    || world.getBlock(bx, by, bz) == ClassicFeatureRegistry.legacySoulLantern)
                    && !world.isBlockIndirectlyGettingPowered(bx, by, bz)) {
                double distance = (bx + .5 - x) * (bx + .5 - x) + (by + .5 - y) * (by + .5 - y) + (bz + .5 - z) * (bz + .5 - z);
                if (distance < best) { best = distance; result = new int[] {bx, by, bz}; }
            }
        return result;
    }

    @Override public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityGhost) ((EntityGhost) entity).pauseLifetime();
    }

    @Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x + .25, y, z + .25, x + .75, y + 1, z + .75);
    }
}
