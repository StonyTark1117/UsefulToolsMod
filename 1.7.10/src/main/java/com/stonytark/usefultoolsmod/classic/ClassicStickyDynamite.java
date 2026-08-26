package com.stonytark.usefultoolsmod.classic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ClassicStickyDynamite extends Item {
    public ClassicStickyDynamite() { setMaxStackSize(16); }
    @Override public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!ClassicGeneratedConfig.booleanValue("explosivesEnabled")
                || !ClassicGeneratedConfig.booleanValue("stickyDynamiteEnabled")) return stack;
        world.playSoundAtEntity(player, "random.bow", .5F, .5F);
        if (!world.isRemote) world.spawnEntityInWorld(new EntityStickyDynamite(world, player));
        if (!player.capabilities.isCreativeMode) stack.stackSize--; return stack;
    }
}
