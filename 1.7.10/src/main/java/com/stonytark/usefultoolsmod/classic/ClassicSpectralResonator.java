package com.stonytark.usefultoolsmod.classic;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ClassicSpectralResonator extends Item {
    public ClassicSpectralResonator() { setMaxDamage(128); setMaxStackSize(1); }

    @Override public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!ClassicGeneratedConfig.booleanValue("spectralResonatorEnabled")) return stack;
        long ready = stack.hasTagCompound() ? stack.getTagCompound().getLong("ReadyAt") : 0;
        if (world.getTotalWorldTime() < ready) return stack;
        Entity nearest = null; double best = 96D * 96D;
        List entities = world.loadedEntityList;
        for (Object value : entities) if (value instanceof EntityGhost || value instanceof EntityWraith) {
            Entity entity = (Entity)value; double distance = player.getDistanceSqToEntity(entity);
            if (distance < best) { nearest = entity; best = distance; }
        }
        if (!world.isRemote) {
            if (nearest == null) player.addChatMessage(new ChatComponentText("No spectral presence within 96 blocks."));
            else {
                player.addStat(ClassicAchievements.locateSpirit, 1);
                double dx = nearest.posX - player.posX, dz = nearest.posZ - player.posZ;
                String ns = Math.abs(dz) > 2 ? (dz < 0 ? "north" : "south") : "";
                String ew = Math.abs(dx) > 2 ? (dx < 0 ? "west" : "east") : "";
                String direction = ns.length() > 0 && ew.length() > 0 ? ns + "-" + ew : ns + ew;
                player.addChatMessage(new ChatComponentText("Spectral presence: " + direction + ", "
                        + (int)Math.round(Math.sqrt(best)) + " blocks."));
            }
            if (!player.capabilities.isCreativeMode) stack.damageItem(1, player);
        } else if (nearest != null && ClassicGeneratedConfig.booleanValue("spectralParticlesEnabled")) {
            for (int i = 1; i <= 12; i++) {
                double t = i / 13D;
                world.spawnParticle("portal", player.posX + (nearest.posX - player.posX) * t,
                        player.posY + 1 + (nearest.posY - player.posY) * t,
                        player.posZ + (nearest.posZ - player.posZ) * t, 0, .01, 0);
            }
        }
        if (!stack.hasTagCompound()) stack.setTagCompound(new net.minecraft.nbt.NBTTagCompound());
        stack.getTagCompound().setLong("ReadyAt", world.getTotalWorldTime() + 200);
        return stack;
    }
}
