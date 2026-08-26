package com.stonytark.usefultoolsmod.classic;

import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ClassicRemoteDetonator extends Item {
    public ClassicRemoteDetonator() { setMaxStackSize(1); }

    @Override public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z,
                                       int side, float hitX, float hitY, float hitZ) {
        if (!ClassicGeneratedConfig.booleanValue("explosivesEnabled")
                || !ClassicGeneratedConfig.booleanValue("remoteDetonationEnabled")) return false;
        TileEntity value = world.getTileEntity(x, y, z); if (!(value instanceof TileMiningCharge)) return false;
        if (world.isRemote) return true;
        TileMiningCharge tile = (TileMiningCharge)value; String owner = player.getUniqueID().toString();
        if (tile.owner.length() > 0 && !tile.owner.equals(owner)) { player.addChatMessage(new ChatComponentText("That charge belongs to another player.")); return true; }
        tile.owner = owner; tile.channel = channel(stack);
        NBTTagCompound root = tag(stack); NBTTagList old = root.getTagList("Links", 10), links = new NBTTagList();
        for (int i = 0; i < old.tagCount() && links.tagCount() < 15; i++) {
            NBTTagCompound link = old.getCompoundTagAt(i);
            if (!(link.getInteger("Dim") == world.provider.dimensionId && link.getInteger("X") == x
                    && link.getInteger("Y") == y && link.getInteger("Z") == z)) links.appendTag(link.copy());
        }
        NBTTagCompound link = new NBTTagCompound(); link.setInteger("Dim", world.provider.dimensionId); link.setInteger("X", x);
        link.setInteger("Y", y); link.setInteger("Z", z); link.setInteger("Channel", tile.channel); links.appendTag(link);
        root.setTag("Links", links); tile.markDirty(); player.addChatMessage(new ChatComponentText("Linked charge on channel " + (tile.channel + 1) + "."));
        return true;
    }

    @Override public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!ClassicGeneratedConfig.booleanValue("explosivesEnabled")
                || !ClassicGeneratedConfig.booleanValue("remoteDetonationEnabled") || world.isRemote) return stack;
        NBTTagCompound root = tag(stack);
        if (player.isSneaking()) {
            int next = (channel(stack) + 1) & 7; root.setInteger("Channel", next);
            player.addChatMessage(new ChatComponentText("Detonator channel " + (next + 1) + ".")); return stack;
        }
        NBTTagList old = root.getTagList("Links", 10), keep = new NBTTagList(); int fired = 0;
        for (int i = 0; i < old.tagCount(); i++) {
            NBTTagCompound link = old.getCompoundTagAt(i);
            if (link.getInteger("Dim") != world.provider.dimensionId) continue;
            int x = link.getInteger("X"), y = link.getInteger("Y"), z = link.getInteger("Z");
            if (!world.blockExists(x, y, z)) continue;
            TileEntity value = world.getTileEntity(x, y, z);
            if (!(value instanceof TileMiningCharge)) continue;
            TileMiningCharge tile = (TileMiningCharge)value;
            boolean owner = player.getUniqueID().toString().equals(tile.owner);
            double remoteRange = ClassicGeneratedConfig.doubleValue("remoteRange");
            boolean range = withinRemoteRange(player.getDistanceSq(x + .5, y + .5, z + .5), remoteRange);
            if (!owner || !range) continue;
            if (tile.channel == channel(stack)) { tile.ignite(); fired++; }
            else keep.appendTag(link.copy());
        }
        root.setTag("Links", keep);
        if (fired > 0) player.addStat(ClassicAchievements.remoteFire, 1);
        player.addChatMessage(new ChatComponentText("Detonated " + fired + " linked charge" + (fired == 1 ? "." : "s.")));
        return stack;
    }

    private static int channel(ItemStack stack) { return normalizeChannel(tag(stack).getInteger("Channel")); }
    static int normalizeChannel(int channel) { return channel & 7; }
    static boolean withinRemoteRange(double distanceSquared, double range) {
        return distanceSquared <= range * range;
    }
    private static NBTTagCompound tag(ItemStack stack) {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound()); return stack.getTagCompound();
    }
}
