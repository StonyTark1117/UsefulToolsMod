#!/usr/bin/env python3
"""Generate real-network acceptance fixtures; neither actor is injected client-side."""
from pathlib import Path
import json
ROOT=Path(__file__).resolve().parents[1];pkg='com.stonytark.usefultools.lighting'
rows=json.loads((ROOT/'lighting/sources.json').read_text())['sources']
for v in ['1.20.1','1.20.2','1.21.1','26.1.2','26.2']:
 for loader in ['forge','fabric','neoforge']:
  yarn=loader=='fabric' and not v.startswith('26.');new=v.startswith('26.')
  root=ROOT/v/loader;mc='MinecraftClient' if yarn else 'Minecraft'
  imports='\n'.join(l for l in (root/'src/lighting/java'/pkg.replace('.','/')/'ClientLighting.java').read_text().splitlines() if l.startswith('import net.minecraft'))
  itemreg='Registries.ITEM' if yarn else ('ForgeRegistries.ITEMS' if loader=='forge' or v=='1.20.1' and loader=='neoforge' else 'BuiltInRegistries.ITEM')
  ident='net.minecraft.util.Identifier.of' if yarn and v=='1.21.1' else ('new net.minecraft.util.Identifier' if yarn else ('net.minecraft.resources.Identifier.parse' if new else ('net.minecraft.resources.ResourceLocation.parse' if v=='1.21.1' else 'new net.minecraft.resources.ResourceLocation')))
  cases=[]
  for row in rows:
   for slot in ['MAINHAND','OFFHAND']:cases.append((row['id'],slot))
   if row['worn']:cases.append((row['id'],dict(helmet='HEAD',chestplate='CHEST',leggings='LEGS',boots='FEET')[row['id'].split('_')[1]]))
  n=dict(KEY='getId' if yarn else 'getKey',PKG=pkg,IMPORTS=imports,MC=mc,SERVER='getServer' if yarn else 'getSingleplayerServer',PLAYER_LIST='getPlayerManager().getPlayerList()' if yarn else 'getPlayerList().getPlayers()',NETWORK='getNetworkIo().bind' if yarn else 'getConnection().startTcpServerListener',ONLINE='setOnlineMode' if yarn else 'setUsesAuthentication',COMMANDS='getCommandManager' if yarn else 'getCommands',EXECUTE='executeWithPrefix' if yarn else 'performPrefixedCommand',SOURCE='getCommandSource' if yarn else 'createCommandSourceStack',EQUIP='equipStack' if yarn else 'setItemSlot',READ='getEquippedStack' if yarn else 'getItemBySlot',REG=itemreg,LOOKUP='getValue' if new or itemreg.startswith('Forge') else 'get',ID=ident,WORLD='world' if yarn else 'level',ENTITIES='getEntities' if yarn else 'entitiesForRendering',EYE='getStandingEyeHeight' if yarn else 'getEyeHeight',SYNC='currentScreenHandler.sendContentUpdates' if yarn else 'containerMenu.broadcastChanges',ABILITIES='sendAbilitiesUpdate' if yarn else 'onUpdateAbilities',CASES=','.join('"'+a+','+b+'"' for a,b in cases),SAMPLE='net.minecraft.client.render.WorldRenderer.getLightmapCoordinates' if yarn else 'net.minecraft.util.LightCoordsUtil.getLightCoords' if v=='26.2' else 'net.minecraft.client.renderer.LevelRenderer.'+('getLightCoords' if new else 'getLightColor'))
  code='''package @PKG@;
@IMPORTS@
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
/** Uses two real Minecraft processes and the integrated server's loopback TCP connection. */
public final class MultiplayerLightingQa {
    private static final String[] CASES={@CASES@};
    private static final EquipmentSlot[] SLOTS={EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET};
    private static int next,seen=-1,stable,logged=-1,maintenance;
    private static volatile boolean opening,opened,scheduled,done;
    private static boolean announced,disconnectLogged;
    private static volatile boolean ready;
    private static Path directory(){return Paths.get(System.getProperty("usefulToolsLightingQaMultiplayer"));}
    private static void write(String file,String text) throws Exception {
        Path tmp=directory().resolve(file+".tmp"+Thread.currentThread().@THREAD_ID@());Files.write(tmp,text.getBytes(StandardCharsets.UTF_8));
        Files.move(tmp,directory().resolve(file),StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);
    }
    private static String id(ItemStack stack){return stack.isEmpty()?"empty":@REG@.@KEY@(stack.getItem()).toString();}
    private static ItemStack item(String id){return new ItemStack(@REG@.@LOOKUP@(@ID@("usefultoolsmod:"+id)));}
    private static void command(@MC@ mc,String command){var server=mc.@SERVER@();server.@COMMANDS@().@EXECUTE@(server.@SOURCE@(),command);}
    private static void install(@MC@ mc,int stage) {
        try {
            var players=mc.@SERVER@().@PLAYER_LIST@;
            if(players.size()!=2){scheduled=false;return;}
            var host=players.stream().filter(p->p.getName().getString().equals("LightingHost")).findFirst().orElseThrow();
            var guest=players.stream().filter(p->p.getName().getString().equals("LightingGuest")).findFirst().orElseThrow();
            command(mc,"gamemode creative @a");
            if(stage==0)command(mc,"fill 996 200 -3 1044 200 3 minecraft:stone");
            for(var player:players){for(var slot:SLOTS)player.@EQUIP@(slot,ItemStack.EMPTY);player.setNoGravity(true);player.getAbilities().flying=true;player.getAbilities().invulnerable=true;player.@ABILITIES@();}
            int x=stage==CASES.length+1?1004:1000;
            command(mc,"tp LightingHost "+x+" 201 0");command(mc,"tp LightingGuest 1012 201 0");
            if(stage<CASES.length){String[] c=CASES[stage].split(",");host.@EQUIP@(EquipmentSlot.valueOf(c[1]),item(c[0]));guest.@EQUIP@(EquipmentSlot.MAINHAND,item("ectoplasm_lantern"));}
            else if(stage<CASES.length+3){host.@EQUIP@(EquipmentSlot.MAINHAND,item("ectoplasm_lantern"));host.@EQUIP@(EquipmentSlot.HEAD,item("hglow_helmet"));guest.@EQUIP@(EquipmentSlot.OFFHAND,item("hglowstone_sword"));}
            if(stage==CASES.length+2){command(mc,"fill 1000 201 -1 1006 203 1 minecraft:water");command(mc,"tp LightingHost 1004 201 0");}
            if(stage==CASES.length+3){command(mc,"fill 1000 201 -1 1006 203 1 minecraft:air");}
            for(var player:players)player.@SYNC@();
            StringBuilder request=new StringBuilder().append(stage).append('\\n');
            for(var player:players){request.append(player.getName().getString()).append('|').append(player.getX()).append('|').append(player.getY()).append('|').append(player.getZ());for(var slot:SLOTS)request.append('|').append(id(player.@READ@(slot)));request.append('\\n');}
            write("request",request.toString());ready=true;
        }catch(Throwable e){fail(e);}
    }
    private static void fail(Throwable error){try{write("failure",error.toString());}catch(Exception ignored){}com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_QA_FAIL real multiplayer",error);done=true;}
    public static void tick(@MC@ mc){
        if(done){if(!disconnectLogged&&mc.@WORLD@==null){if(ClientLighting.FIELD.size()!=0)throw new AssertionError("Network disconnect retained light");disconnectLogged=true;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_NETWORK_DISCONNECT_PASS");}return;}
        if(mc.@WORLD@==null||mc.player==null)return;
        if(!announced){announced=true;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_NETWORK_JOINED client="+mc.player.getName().getString());}
        try {
            boolean host=mc.@SERVER@()!=null;
            if(host&&!opening){opening=true;mc.@SERVER@().execute(()->{try{int port=Integer.getInteger("usefulToolsLightingQaPort",25839);mc.@SERVER@().@ONLINE@(false);mc.@SERVER@().@NETWORK@(java.net.InetAddress.getByAddress(new byte[]{127,0,0,1}),port);write("port",Integer.toString(port));opened=true;}catch(Throwable e){fail(e);}});}
            if(host&&opened&&!scheduled){scheduled=true;ready=false;mc.@SERVER@().execute(()->install(mc,next));}
            if(host&&ready&&++maintenance%40==0)mc.@SERVER@().execute(()->{command(mc,"tp LightingHost "+(next==CASES.length+1||next==CASES.length+2?1004:1000)+" 201 0");command(mc,"tp LightingGuest 1012 201 0");});
            Path request=directory().resolve("request");if(!Files.exists(request))return;
            List<String> lines=Files.readAllLines(request,StandardCharsets.UTF_8);int stage=Integer.parseInt(lines.get(0));
            if(stage!=seen){seen=stage;stable=0;}
            if(++stable<20)return;
            LightField expected=new LightField();Map<String,LightField.Source> sources=new HashMap<>();
            for(String line:lines.subList(1,lines.size())) {
                String[] values=line.split("\\\\|");LivingEntity actor=mc.player.getName().getString().equals(values[0])?mc.player:null;
                for(Entity entity:mc.@WORLD@.@ENTITIES@())if(entity instanceof LivingEntity living&&entity.getName().getString().equals(values[0]))actor=living;
                if(actor==null){if(stable%100==0)write(mc.player.getName().getString()+".waiting","missing actor "+values[0]);return;}
                if(Math.abs(actor.getX()-Double.parseDouble(values[1]))>.2||Math.abs(actor.getY()-Double.parseDouble(values[2]))>.2||Math.abs(actor.getZ()-Double.parseDouble(values[3]))>.2){if(stable%100==0)write(mc.player.getName().getString()+".waiting","position "+values[0]+" actual="+actor.getX()+","+actor.getY()+","+actor.getZ()+" expected="+line);return;}
                int level=0,radius=0;
                for(int i=0;i<SLOTS.length;i++){String actual=id(actor.@READ@(SLOTS[i]));if(!actual.equals(values[i+4])){if(stable%100==0)write(mc.player.getName().getString()+".waiting","equipment "+values[0]+" slot="+SLOTS[i]+" actual="+actual+" expected="+values[i+4]);return;}boolean worn=i>=2;int candidate=SourcePolicy.level(actual,worn);if(candidate>level){level=candidate;radius=SourcePolicy.radius(actual,worn);}}
                if(level>0)sources.put(values[0],new LightField.Source(Math.rint(actor.getX()*8)/8,Math.rint((actor.getY()+actor.@EYE@()*.5)*8)/8,Math.rint(actor.getZ()*8)/8,level,radius));
            }
            expected.update(sources);
            for(int x=998;x<=1042;x+=2){double want=expected.light(x+.5,201.5,.5);if(Math.abs(ClientLighting.FIELD.light(x+.5,201.5,.5)-want)>1e-6)throw new AssertionError("Network field stage="+stage+" x="+x+" expected="+want+" actual="+ClientLighting.FIELD.light(x+.5,201.5,.5));BlockPos pos=new BlockPos(x,201,0);if((@SAMPLE@(mc.@WORLD@,pos)&255)<(int)(want*16))throw new AssertionError("Network renderer sample");}
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
'''
  n['THREAD_ID']='getId' if v.startswith('1.20.') else 'threadId'
  for k,value in n.items():code=code.replace('@'+k+'@',value)
  path=ROOT/'tests/dynamic-lighting/targets'/(v+'-'+loader)/pkg.replace('.','/')/'MultiplayerLightingQa.java';path.write_text(code)
print('Generated real-network acceptance fixtures for 15 modern targets.')
