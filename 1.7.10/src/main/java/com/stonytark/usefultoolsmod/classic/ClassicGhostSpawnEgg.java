package com.stonytark.usefultoolsmod.classic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** Uses the vanilla two-layer spawn-egg renderer while spawning the custom Ghost. */
public class ClassicGhostSpawnEgg extends ItemMonsterPlacer {
    public ClassicGhostSpawnEgg() {
        setMaxStackSize(64);
    }

    @Override
    public ItemMonsterPlacer setTextureName(String ignored) {
        super.setTextureName("spawn_egg");
        return this;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        return renderPass == 0 ? 0xE8F7FF : 0xA8D8E8;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z,
                             int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return true;
        int spawnX = x + net.minecraft.util.Facing.offsetsXForSide[side];
        int spawnY = y + net.minecraft.util.Facing.offsetsYForSide[side];
        int spawnZ = z + net.minecraft.util.Facing.offsetsZForSide[side];
        EntityGhost ghost = new EntityGhost(world);
        ghost.setLocationAndAngles(spawnX + 0.5D, spawnY, spawnZ + 0.5D, player.rotationYaw, 0.0F);
        world.spawnEntityInWorld(ghost);
        if (!player.capabilities.isCreativeMode) --stack.stackSize;
        return true;
    }
}
