package com.stonytark.usefultools.lighting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.registry.Registries;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
/** Uses two real Minecraft processes and the integrated server's loopback TCP connection. */
public final class MultiplayerLightingQa {
    private static final String[] CASES={"ectoplasm_lantern,MAINHAND","ectoplasm_lantern,OFFHAND","soul_lantern,MAINHAND","soul_lantern,OFFHAND","hglow_block,MAINHAND","hglow_block,OFFHAND","hglow,MAINHAND","hglow,OFFHAND","hglowstone_axe,MAINHAND","hglowstone_axe,OFFHAND","hglowstone_hoe,MAINHAND","hglowstone_hoe,OFFHAND","hglowstone_pickaxe,MAINHAND","hglowstone_pickaxe,OFFHAND","hglowstone_shovel,MAINHAND","hglowstone_shovel,OFFHAND","hglowstone_sword,MAINHAND","hglowstone_sword,OFFHAND","hglow_helmet,MAINHAND","hglow_helmet,OFFHAND","hglow_helmet,HEAD","hglow_chestplate,MAINHAND","hglow_chestplate,OFFHAND","hglow_chestplate,CHEST","hglow_leggings,MAINHAND","hglow_leggings,OFFHAND","hglow_leggings,LEGS","hglow_boots,MAINHAND","hglow_boots,OFFHAND","hglow_boots,FEET"};
    private static final EquipmentSlot[] SLOTS={EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET};
    private static int next,seen=-1,stable,logged=-1,maintenance;
    private static volatile boolean opening,opened,scheduled,done;
    private static boolean announced,disconnectLogged;
    private static volatile boolean ready;
    private static Path directory(){return Paths.get(System.getProperty("usefulToolsLightingQaMultiplayer"));}
    private static void write(String file,String text) throws Exception {
        Path tmp=directory().resolve(file+".tmp"+Thread.currentThread().getId());Files.write(tmp,text.getBytes(StandardCharsets.UTF_8));
        Files.move(tmp,directory().resolve(file),StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);
    }
    private static String id(ItemStack stack){return stack.isEmpty()?"empty":Registries.ITEM.getId(stack.getItem()).toString();}
    private static ItemStack item(String id){return new ItemStack(Registries.ITEM.get(new net.minecraft.util.Identifier("usefultoolsmod:"+id)));}
    private static void command(MinecraftClient mc,String command){var server=mc.getServer();server.getCommandManager().executeWithPrefix(server.getCommandSource(),command);}
    private static void install(MinecraftClient mc,int stage) {
        try {
            var players=mc.getServer().getPlayerManager().getPlayerList();
            if(players.size()!=2){scheduled=false;return;}
            var host=players.stream().filter(p->p.getName().getString().equals("LightingHost")).findFirst().orElseThrow();
            var guest=players.stream().filter(p->p.getName().getString().equals("LightingGuest")).findFirst().orElseThrow();
            command(mc,"gamemode creative @a");
            if(stage==0)command(mc,"fill 996 200 -3 1044 200 3 minecraft:stone");
            for(var player:players){for(var slot:SLOTS)player.equipStack(slot,ItemStack.EMPTY);player.setNoGravity(true);player.getAbilities().flying=true;player.getAbilities().invulnerable=true;player.sendAbilitiesUpdate();}
            int x=stage==CASES.length+1?1004:1000;
            command(mc,"tp LightingHost "+x+" 201 0");command(mc,"tp LightingGuest 1012 201 0");
            if(stage<CASES.length){String[] c=CASES[stage].split(",");host.equipStack(EquipmentSlot.valueOf(c[1]),item(c[0]));guest.equipStack(EquipmentSlot.MAINHAND,item("ectoplasm_lantern"));}
            else if(stage<CASES.length+3){host.equipStack(EquipmentSlot.MAINHAND,item("ectoplasm_lantern"));host.equipStack(EquipmentSlot.HEAD,item("hglow_helmet"));guest.equipStack(EquipmentSlot.OFFHAND,item("hglowstone_sword"));}
            if(stage==CASES.length+2){command(mc,"fill 1000 201 -1 1006 203 1 minecraft:water");command(mc,"tp LightingHost 1004 201 0");}
            if(stage==CASES.length+3){command(mc,"fill 1000 201 -1 1006 203 1 minecraft:air");}
            for(var player:players)player.currentScreenHandler.sendContentUpdates();
            StringBuilder request=new StringBuilder().append(stage).append('\n');
            for(var player:players){request.append(player.getName().getString()).append('|').append(player.getX()).append('|').append(player.getY()).append('|').append(player.getZ());for(var slot:SLOTS)request.append('|').append(id(player.getEquippedStack(slot)));request.append('\n');}
            write("request",request.toString());ready=true;
        }catch(Throwable e){fail(e);}
    }
    private static void fail(Throwable error){try{write("failure",error.toString());}catch(Exception ignored){}com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_QA_FAIL real multiplayer",error);done=true;}
    public static void tick(MinecraftClient mc){
        if(done){if(!disconnectLogged&&mc.world==null){if(ClientLighting.FIELD.size()!=0)throw new AssertionError("Network disconnect retained light");disconnectLogged=true;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_NETWORK_DISCONNECT_PASS");}return;}
        if(mc.world==null||mc.player==null)return;
        if(!announced){announced=true;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_NETWORK_JOINED client="+mc.player.getName().getString());}
        try {
            boolean host=mc.getServer()!=null;
            if(host&&!opening){opening=true;mc.getServer().execute(()->{try{int port=Integer.getInteger("usefulToolsLightingQaPort",25839);mc.getServer().setOnlineMode(false);mc.getServer().getNetworkIo().bind(java.net.InetAddress.getByAddress(new byte[]{127,0,0,1}),port);write("port",Integer.toString(port));opened=true;}catch(Throwable e){fail(e);}});}
            if(host&&opened&&!scheduled){scheduled=true;ready=false;mc.getServer().execute(()->install(mc,next));}
            if(host&&ready&&++maintenance%40==0)mc.getServer().execute(()->{command(mc,"tp LightingHost "+(next==CASES.length+1||next==CASES.length+2?1004:1000)+" 201 0");command(mc,"tp LightingGuest 1012 201 0");});
            Path request=directory().resolve("request");if(!Files.exists(request))return;
            List<String> lines=Files.readAllLines(request,StandardCharsets.UTF_8);int stage=Integer.parseInt(lines.get(0));
            if(stage!=seen){seen=stage;stable=0;}
            if(++stable<20)return;
            LightField expected=new LightField();Map<String,LightField.Source> sources=new HashMap<>();
            for(String line:lines.subList(1,lines.size())) {
                String[] values=line.split("\\|");LivingEntity actor=mc.player.getName().getString().equals(values[0])?mc.player:null;
                for(Entity entity:mc.world.getEntities())if(entity instanceof LivingEntity living&&entity.getName().getString().equals(values[0]))actor=living;
                if(actor==null){if(stable%100==0)write(mc.player.getName().getString()+".waiting","missing actor "+values[0]);return;}
                if(Math.abs(actor.getX()-Double.parseDouble(values[1]))>.2||Math.abs(actor.getY()-Double.parseDouble(values[2]))>.2||Math.abs(actor.getZ()-Double.parseDouble(values[3]))>.2){if(stable%100==0)write(mc.player.getName().getString()+".waiting","position "+values[0]+" actual="+actor.getX()+","+actor.getY()+","+actor.getZ()+" expected="+line);return;}
                int level=0,radius=0;
                for(int i=0;i<SLOTS.length;i++){String actual=id(actor.getEquippedStack(SLOTS[i]));if(!actual.equals(values[i+4])){if(stable%100==0)write(mc.player.getName().getString()+".waiting","equipment "+values[0]+" slot="+SLOTS[i]+" actual="+actual+" expected="+values[i+4]);return;}boolean worn=i>=2;int candidate=SourcePolicy.level(actual,worn);if(candidate>level){level=candidate;radius=SourcePolicy.radius(actual,worn);}}
                if(level>0)sources.put(values[0],new LightField.Source(Math.rint(actor.getX()*8)/8,Math.rint((actor.getY()+actor.getStandingEyeHeight()*.5)*8)/8,Math.rint(actor.getZ()*8)/8,level,radius));
            }
            expected.update(sources);
            for(int x=998;x<=1042;x+=2){double want=expected.light(x+.5,201.5,.5);if(Math.abs(ClientLighting.FIELD.light(x+.5,201.5,.5)-want)>1e-6)throw new AssertionError("Network field stage="+stage+" x="+x+" expected="+want+" actual="+ClientLighting.FIELD.light(x+.5,201.5,.5));BlockPos pos=new BlockPos(x,201,0);if((net.minecraft.client.render.WorldRenderer.getLightmapCoordinates(mc.world,pos)&255)<(int)(want*16))throw new AssertionError("Network renderer sample");}
            String role=mc.player.getName().getString();write(role+".ack",Integer.toString(stage));
            if(logged!=stage){logged=stage;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_NETWORK_CASE_PASS stage="+stage+" client="+role+" remote_equipment=true");}
            if(host&&ready&&Files.exists(directory().resolve("LightingHost.ack"))&&Files.exists(directory().resolve("LightingGuest.ack"))&&Files.readString(directory().resolve("LightingHost.ack")).equals(Integer.toString(stage))&&Files.readString(directory().resolve("LightingGuest.ack")).equals(Integer.toString(stage))){
                if(stage==CASES.length+3){write("complete","two actual Minecraft clients verified "+(stage+1)+" network stages");done=true;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_NETWORK_PASS");}
                else{next=stage+1;scheduled=false;}
            }
            if(!host&&Files.exists(directory().resolve("complete"))){done=true;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_NETWORK_PASS");}
        }catch(Throwable error){fail(error);}
    }
}
