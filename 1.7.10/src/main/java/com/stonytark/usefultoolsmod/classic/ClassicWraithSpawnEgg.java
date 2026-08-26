package com.stonytark.usefultoolsmod.classic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ClassicWraithSpawnEgg extends ItemMonsterPlacer {
    @Override public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z,
                                       int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote || !ClassicGeneratedConfig.booleanValue("wraithEnabled")) return true;
        EntityWraith wraith = new EntityWraith(world);
        wraith.setLocationAndAngles(x + .5D, y + 1D, z + .5D, world.rand.nextFloat() * 360F, 0);
        world.spawnEntityInWorld(wraith); if (!player.capabilities.isCreativeMode) stack.stackSize--; return true;
    }
}
