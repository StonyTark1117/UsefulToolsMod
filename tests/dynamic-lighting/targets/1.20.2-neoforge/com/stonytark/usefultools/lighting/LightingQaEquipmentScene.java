package com.stonytark.usefultools.lighting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.core.registries.BuiltInRegistries;
import java.nio.file.*;
import java.util.*;
public final class LightingQaEquipmentScene {
 private static String acknowledged="",scheduled="",commandScheduled="";
 private static boolean ready;
 private static final EquipmentSlot[] SLOTS={EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET};
 private static void commands(Minecraft mc) {
  if(!ready){ready=true;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_NATIVE_COMMAND_CONTROLLER_READY");}
  String file=System.getProperty("usefulToolsLightingQaNativeCommands");if(file==null||mc.player==null||mc.level==null)return;
  try{Path path=Path.of(file);if(!Files.isRegularFile(path))return;List<String> lines=Files.readAllLines(path);if(lines.size()!=2||lines.get(0).equals(commandScheduled))return;String token=lines.get(0),command=lines.get(1);commandScheduled=token;var server=mc.getSingleplayerServer();if(server==null)throw new AssertionError("Scene command requires integrated server");server.execute(()->{try{var player=server.getPlayerList().getPlayer(mc.player.getUUID());if(player==null)throw new AssertionError("Missing scene command player");int result=server.getCommands().getDispatcher().execute(command,player.createCommandSourceStack().withPermission(4));com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_NATIVE_COMMAND_ACK token="+token+" result="+result);}catch(Exception e){com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_NATIVE_COMMAND_FAIL token="+token,e);}});}catch(Exception e){throw new IllegalStateException("Native scene command queue",e);}
 }
 public static void tick(Minecraft mc) {
  commands(mc);
  String file=System.getProperty("usefulToolsLightingQaEquipmentScene");if(file==null||mc.player==null||mc.level==null)return;
  try {
   Path path=Path.of(file);if(!Files.isRegularFile(path))return;List<String> lines=Files.readAllLines(path);if(lines.size()!=3||lines.get(0).equals(acknowledged))return;
   String token=lines.get(0),slot=lines.get(1),source=lines.get(2);int target=switch(slot){case "held"->0;case "head"->2;case "chest"->3;case "legs"->4;case "feet"->5;default->throw new AssertionError("Scene slot "+slot);};
   if(!token.equals(scheduled)) {
    var server=mc.getSingleplayerServer();if(server==null)throw new AssertionError("Equipment scene requires native integrated server");scheduled=token;
    server.execute(()->{var player=server.getPlayerList().getPlayer(mc.player.getUUID());if(player==null)throw new AssertionError("Missing scene player");player.getInventory().clearContent();for(EquipmentSlot equipment:SLOTS)player.setItemSlot(equipment,ItemStack.EMPTY);player.setItemSlot(SLOTS[target],new ItemStack(BuiltInRegistries.ITEM.get(new net.minecraft.resources.ResourceLocation(source))));player.containerMenu.broadcastChanges();});return;
   }
   for(int i=0;i<SLOTS.length;i++) {ItemStack stack=mc.player.getItemBySlot(SLOTS[i]);String actual=stack.isEmpty()?"minecraft:air":BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();if(!actual.equals(i==target?source:"minecraft:air"))return;}
   double x=Math.rint(mc.player.getX()*8)/8,y=Math.rint((mc.player.getY()+mc.player.getEyeHeight()*.5)*8)/8,z=Math.rint(mc.player.getZ()*8)/8;int level=SourcePolicy.level(source,target>=2);if(Math.abs(ClientLighting.FIELD.light(x,y,z)-level)>1e-6)return;
   acknowledged=token;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_SCENE_EQUIPMENT_ACK token="+token+" slot="+slot+" source="+source+" level="+level+" all_other_slots_empty=true");
  }catch(Exception e){throw new IllegalStateException("Native equipment scene acknowledgement",e);}
 }
}
