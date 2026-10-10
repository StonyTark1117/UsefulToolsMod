package com.stonytark.usefultools.lighting.classic;
import com.stonytark.usefultools.lighting.LightField;
import com.stonytark.usefultools.lighting.SourcePolicy;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.init.Blocks;
import net.minecraft.world.WorldSettings;
/** Real TCP clients; FML's native server tick owns every fixture world/equipment change. */
public final class ClassicMultiplayerLightingQa {
    private static final String[] ITEMS={"ectoplasm_lantern","soul_lantern","hglow_block","hglow","hglowstone_axe","hglowstone_hoe","hglowstone_pickaxe","hglowstone_shovel","hglowstone_sword","hglow_helmet","hglow_helmet","hglow_chestplate","hglow_chestplate","hglow_leggings","hglow_leggings","hglow_boots","hglow_boots"};
    private static final int[] SLOTS={0,0,0,0,0,0,0,0,0,0,4,0,3,0,2,0,1};
    private static volatile boolean opened,scheduled,ready,done,failed;
    private static int next,seen=-1,stable,logged=-1,maintenance;
    private static boolean announced,disconnectLogged;
    private static Path directory(){return Paths.get(System.getProperty("usefulToolsLightingQaMultiplayer"));}
    private static void write(String file,String text) throws Exception {Path tmp=directory().resolve(file+".tmp"+Thread.currentThread().getId());Files.write(tmp,text.getBytes(StandardCharsets.UTF_8));Files.move(tmp,directory().resolve(file),StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);}
    private static String read(String file)throws Exception{return new String(Files.readAllBytes(directory().resolve(file)),StandardCharsets.UTF_8);}
    private static ItemStack item(String id){return new ItemStack((Item)Item.itemRegistry.getObject("usefultoolsmod:"+id));}
    private static String id(ItemStack stack){return stack==null?"empty":String.valueOf(Item.itemRegistry.getNameForObject(stack.getItem()));}
    private static void fail(Throwable error){failed=true;try{write("failure",error.toString());}catch(Exception ignored){}System.err.println("USEFUL_TOOLS_LIGHTING_QA_FAIL real multiplayer "+error);error.printStackTrace();}
    private static void position(EntityPlayerMP player,double x){player.motionX=player.motionY=player.motionZ=0;player.playerNetServerHandler.setPlayerLocation(x,201,.5,0,0);}
    public static void serverTick(){
        if(System.getProperty("usefulToolsLightingQaMultiplayer")==null||done||failed)return;
        MinecraftServer server=MinecraftServer.getServer();if(server==null)return;
        try {
            if(!opened){int port=Integer.getInteger("usefulToolsLightingQaPort",25839);server.setOnlineMode(false);server.func_147137_ag().addLanEndpoint(java.net.InetAddress.getLoopbackAddress(),port);write("port",Integer.toString(port));opened=true;}
            List<EntityPlayerMP> players=new ArrayList<EntityPlayerMP>();for(Object p:server.getConfigurationManager().playerEntityList)players.add((EntityPlayerMP)p);
            if(players.size()!=2)return;
            EntityPlayerMP host=null,guest=null;for(EntityPlayerMP player:players){if(player.getCommandSenderName().equals("LightingHost"))host=player;if(player.getCommandSenderName().equals("LightingGuest"))guest=player;}
            if(host==null||guest==null)return;
            if(!scheduled){
                scheduled=true;ready=false;
                for(EntityPlayerMP player:players){player.theItemInWorldManager.setGameType(WorldSettings.GameType.CREATIVE);player.capabilities.isFlying=true;player.capabilities.disableDamage=true;player.sendPlayerAbilities();for(int i=0;i<5;i++)player.setCurrentItemOrArmor(i,null);}
                if(next==0)for(int x=996;x<=1044;x++)for(int z=-3;z<=3;z++)host.worldObj.setBlock(x,200,z,Blocks.stone,0,3);
                position(host,next==ITEMS.length+1?1004.5:1000.5);position(guest,1012.5);
                if(next<ITEMS.length){host.setCurrentItemOrArmor(SLOTS[next],item(ITEMS[next]));guest.setCurrentItemOrArmor(0,item("ectoplasm_lantern"));}
                else if(next<ITEMS.length+3){host.setCurrentItemOrArmor(0,item("ectoplasm_lantern"));host.setCurrentItemOrArmor(4,item("hglow_helmet"));guest.setCurrentItemOrArmor(0,item("hglowstone_sword"));}
                if(next==ITEMS.length+2||next==ITEMS.length+3){for(int x=1000;x<=1006;x++)for(int y=201;y<=203;y++)for(int z=-1;z<=1;z++)host.worldObj.setBlock(x,y,z,next==ITEMS.length+2?Blocks.water:Blocks.air,0,3);if(next==ITEMS.length+2)position(host,1004.5);}
                StringBuilder request=new StringBuilder().append(next).append('\n');
                for(EntityPlayerMP player:players){player.inventoryContainer.detectAndSendChanges();request.append(player.getCommandSenderName()).append('|').append(player.posX).append('|').append(player.posY).append('|').append(player.posZ);for(int slot=0;slot<5;slot++)request.append('|').append(id(player.getEquipmentInSlot(slot)));request.append('\n');}
                write("request",request.toString());ready=true;
            }
            if(++maintenance%40==0){position(host,next==ITEMS.length+1||next==ITEMS.length+2?1004.5:1000.5);position(guest,1012.5);}
            String stage=Integer.toString(next);
            if(ready&&Files.exists(directory().resolve("LightingHost.ack"))&&Files.exists(directory().resolve("LightingGuest.ack"))&&read("LightingHost.ack").equals(stage)&&read("LightingGuest.ack").equals(stage)){
                if(next==ITEMS.length+3){write("complete","two actual classic Minecraft clients verified "+(next+1)+" network stages");done=true;System.out.println("USEFUL_TOOLS_LIGHTING_QA_NETWORK_PASS");}else{next++;scheduled=false;}
            }
        }catch(Throwable error){fail(error);}
    }
    public static void tick(Minecraft mc){
        if(failed)return;
        if(done){if(!disconnectLogged&&mc.theWorld==null){if(ClassicClientLighting.FIELD.size()!=0)throw new AssertionError("Network disconnect retained light");disconnectLogged=true;System.out.println("USEFUL_TOOLS_LIGHTING_QA_NETWORK_DISCONNECT_PASS");}return;}
        if(mc.theWorld==null||mc.thePlayer==null)return;
        if(!announced){announced=true;System.out.println("USEFUL_TOOLS_LIGHTING_QA_NETWORK_JOINED client="+mc.thePlayer.getCommandSenderName());}
        try {
            if(!Files.exists(directory().resolve("request")))return;
            List<String> lines=Files.readAllLines(directory().resolve("request"),StandardCharsets.UTF_8);int stage=Integer.parseInt(lines.get(0));if(stage!=seen){seen=stage;stable=0;}if(++stable<20)return;
            LightField expected=new LightField();Map<String,LightField.Source> sources=new HashMap<String,LightField.Source>();
            for(String line:lines.subList(1,lines.size())){
                String[] values=line.split("\\|");EntityLivingBase actor=mc.thePlayer.getCommandSenderName().equals(values[0])?mc.thePlayer:null;
                for(Object obj:mc.theWorld.loadedEntityList){Entity entity=(Entity)obj;if(entity instanceof EntityLivingBase&&entity.getCommandSenderName().equals(values[0]))actor=(EntityLivingBase)entity;}
                if(actor==null)return;
                if(Math.abs(actor.posX-Double.parseDouble(values[1]))>.2||Math.abs(actor.posY-Double.parseDouble(values[2]))>2||Math.abs(actor.posZ-Double.parseDouble(values[3]))>.2)return;
                int level=0,radius=0;for(int slot=0;slot<5;slot++){String actual=id(actor.getEquipmentInSlot(slot));if(!actual.equals(values[slot+4]))return;int candidate=SourcePolicy.level(actual,slot!=0);if(candidate>level){level=candidate;radius=SourcePolicy.radius(actual,slot!=0);}}
                if(level>0)sources.put(values[0],new LightField.Source(Math.rint(actor.posX*8)/8,Math.rint((actor.posY+actor.getEyeHeight()*.5)*8)/8,Math.rint(actor.posZ*8)/8,level,radius));
            }
            expected.update(sources);
            for(int x=998;x<=1042;x+=2){double want=expected.light(x+.5,201.5,.5);if(Math.abs(ClassicClientLighting.FIELD.light(x+.5,201.5,.5)-want)>1e-6)throw new AssertionError("Network field stage="+stage+" x="+x);int sampled=mc.theWorld.getBlock(x,201,0).getMixedBrightnessForBlock(mc.theWorld,x,201,0);if((sampled&255)<(int)(want*16))throw new AssertionError("Network renderer sample");}
            String role=mc.thePlayer.getCommandSenderName();write(role+".ack",Integer.toString(stage));if(logged!=stage){logged=stage;System.out.println("USEFUL_TOOLS_LIGHTING_QA_NETWORK_CASE_PASS stage="+stage+" client="+role+" remote_equipment=true");}
            if(mc.getIntegratedServer()==null&&Files.exists(directory().resolve("complete"))){done=true;System.out.println("USEFUL_TOOLS_LIGHTING_QA_NETWORK_PASS");}
        }catch(Throwable error){fail(error);}
    }
}
