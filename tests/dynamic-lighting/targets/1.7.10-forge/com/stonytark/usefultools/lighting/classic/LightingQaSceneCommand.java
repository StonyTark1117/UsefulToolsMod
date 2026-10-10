package com.stonytark.usefultools.lighting.classic;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;

/** Acceptance-only, native 1.7.10 equivalent of the modern comparison corridor. */
public final class LightingQaSceneCommand extends CommandBase {
    private static volatile String expectedSource, expectedSlot;
    private static String acknowledgedSource, acknowledgedSlot;
    public static void verifyClient(net.minecraft.client.Minecraft mc) {
        String source = expectedSource, slot = expectedSlot;
        if (source == null || slot == null || mc.thePlayer == null) return;
        if (source.equals(acknowledgedSource) && slot.equals(acknowledgedSlot)) return;
        int index = "held".equals(slot) ? 0 : "head".equals(slot) ? 4 : "chest".equals(slot) ? 3 : "legs".equals(slot) ? 2 : 1;
        ItemStack stack = mc.thePlayer.getEquipmentInSlot(index);
        if (stack == null || !source.equals(Item.itemRegistry.getNameForObject(stack.getItem()))) return;
        for (int other = 0; other <= 4; other++) if (other != index && mc.thePlayer.getEquipmentInSlot(other) != null) return;
        int expected = com.stonytark.usefultools.lighting.SourcePolicy.level(source, index != 0);
        double x = Math.rint(mc.thePlayer.posX * 8) / 8, z = Math.rint(mc.thePlayer.posZ * 8) / 8;
        double y = Math.rint((mc.thePlayer.posY + mc.thePlayer.getEyeHeight() * .5) * 8) / 8;
        if (Math.abs(ClassicClientLighting.FIELD.light(x,y,z) - expected) > 1e-6) return;
        acknowledgedSource = source; acknowledgedSlot = slot;
        System.out.println("USEFUL_TOOLS_LIGHTING_SCENE_EQUIPMENT_ACK slot="+slot+" source="+source+" level="+expected);
    }
    @Override public String getCommandName() { return "lightingqa"; }
    @Override public String getCommandUsage(ICommandSender sender) { return "/lightingqa prepare|ordinary|hardened|view <distance>"; }
    @Override public void processCommand(ICommandSender sender, String[] arguments) {
        if (!(sender instanceof EntityPlayerMP) || arguments.length == 0) throw new IllegalArgumentException(getCommandUsage(sender));
        EntityPlayerMP player = (EntityPlayerMP)sender;
        WorldServer world = (WorldServer)player.worldObj;
        String scene = arguments[0];
        world.getGameRules().setOrCreateGameRule("doMobSpawning", "false");
        if ("prepare".equals(scene) || "equip".equals(scene)) {
            for (Object candidate : new java.util.ArrayList<Object>(world.loadedEntityList)) {
                if (candidate instanceof net.minecraft.entity.EntityLivingBase && !(candidate instanceof net.minecraft.entity.player.EntityPlayer)) ((net.minecraft.entity.Entity)candidate).setDead();
            }
        }
        if ("prepare".equals(scene)) {
            player.setGameType(WorldSettings.GameType.CREATIVE);
            player.inventory.clearInventory(null, -1);
            player.sendContainerToPlayer(player.inventoryContainer);
            world.setWorldTime(18000);
            world.getWorldInfo().setRaining(false);
            world.getWorldInfo().setThundering(false);
            for (int x=495; x<=535; x++) for (int y=199; y<=205; y++) for (int z=-5; z<=5; z++) {
                boolean wall = x==495 || x==535 || y==199 || y==205 || z==-5 || z==5;
                world.setBlock(x,y,z,wall ? Blocks.stone : Blocks.air,0,3);
            }
            player.playerNetServerHandler.setPlayerLocation(503.5,201,.5,90,18);
        } else if ("ordinary".equals(scene) || "hardened".equals(scene)) {
            Block block = "ordinary".equals(scene) ? Blocks.glowstone : Block.getBlockFromName("usefultoolsmod:hglow_block");
            if (block == null || block == Blocks.air) throw new IllegalStateException("Missing Hardened Glowstone registration");
            world.setBlock(500,200,0,block,0,3);
        } else if ("equip".equals(scene) && arguments.length==3) {
            player.inventory.clearInventory(null, -1);
            java.util.Arrays.fill(player.inventory.armorInventory, null);
            world.setBlock(500,200,0,Blocks.air,0,3);
            Item item = (Item)Item.itemRegistry.getObject(arguments[2]);
            if (item == null) throw new IllegalArgumentException("Unknown equipment source");
            ItemStack stack = new ItemStack(item);
            String slot = arguments[1];
            if ("held".equals(slot)) {
                player.inventory.currentItem = 0;
                player.inventory.mainInventory[0] = stack;
            } else {
                int index = "head".equals(slot) ? 3 : "chest".equals(slot) ? 2 : "legs".equals(slot) ? 1 : "feet".equals(slot) ? 0 : -1;
                if (index < 0) throw new IllegalArgumentException("Unknown armor slot");
                player.inventory.armorInventory[index] = stack;
            }
            player.sendContainerToPlayer(player.inventoryContainer);
            player.playerNetServerHandler.sendPacket(new S09PacketHeldItemChange(0));
            for (int index=0; index<=4; index++) player.playerNetServerHandler.sendPacket(new S04PacketEntityEquipment(player.getEntityId(),index,player.getEquipmentInSlot(index)));
            player.playerNetServerHandler.setPlayerLocation(503.5,201,.5,90,0);
            expectedSlot = slot; expectedSource = arguments[2];
        } else if ("view".equals(scene) && arguments.length==2) {
            int distance = Integer.parseInt(arguments[1]);
            if (distance!=3 && distance!=10 && distance!=15 && distance!=20 && distance!=29) throw new IllegalArgumentException("Invalid comparison distance");
            player.playerNetServerHandler.setPlayerLocation(500+distance+.5,201,.5,90,18);
        } else throw new IllegalArgumentException(getCommandUsage(sender));
        sender.addChatMessage(new ChatComponentText("USEFUL_TOOLS_LIGHTING_SCENE_ACCEPTED "+scene));
    }
}
