package com.stonytark.usefultoolsmod.classic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ClassicWraithSpawnEgg extends ItemMonsterPlacer {
    public ClassicWraithSpawnEgg() {
        setMaxStackSize(64);
    }

    /** Use vanilla's complete two-layer egg instead of a missing custom overlay. */
    @Override public ItemMonsterPlacer setTextureName(String ignored) {
        super.setTextureName("spawn_egg");
        return this;
    }

    @Override public int getColorFromItemStack(ItemStack stack, int renderPass) {
        return renderPass == 0 ? 0x2B2038 : 0x9B59B6;
    }

    @Override public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z,
                                       int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote || !ClassicGeneratedConfig.booleanValue("wraithEnabled")) return true;
        EntityWraith wraith = new EntityWraith(world);
        wraith.setLocationAndAngles(x + .5D, y + 1D, z + .5D, world.rand.nextFloat() * 360F, 0);
        world.spawnEntityInWorld(wraith); if (!player.capabilities.isCreativeMode) stack.stackSize--; return true;
    }
}
