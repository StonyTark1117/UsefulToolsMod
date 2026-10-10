#!/usr/bin/env python3
"""Native client equipment acceptance fixtures, kept outside all distributed artifacts."""
import json
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1];pkg='com.stonytark.usefultools.lighting'
rows=json.loads((ROOT/'lighting/sources.json').read_text())['sources']
for v in ['1.20.1','1.20.2','1.21.1','26.1.2','26.2']:
 for loader in ['forge','fabric','neoforge']:
  yarn=loader=='fabric' and not v.startswith('26.');new=v.startswith('26.');newest=v=='26.2';root=ROOT/v/loader
  source=(root/'src/lighting/java'/pkg.replace('.','/')/'ClientLighting.java').read_text()
  imports='\n'.join(l for l in source.splitlines() if l.startswith('import net.minecraft'))
  slots='EquipmentSlot';mc='MinecraftClient' if yarn else 'Minecraft'
  itemRegistry='Registries.ITEM' if yarn else ('ForgeRegistries.ITEMS' if loader=='forge' or v=='1.20.1' and loader=='neoforge' else 'BuiltInRegistries.ITEM')
  itemLookup='getValue' if new or itemRegistry.startswith('Forge') else 'get'
  ident='net.minecraft.util.Identifier.of' if yarn and v=='1.21.1' else ('new net.minecraft.util.Identifier' if yarn else ('net.minecraft.resources.Identifier.parse' if new else ('net.minecraft.resources.ResourceLocation.parse' if v=='1.21.1' else 'new net.minecraft.resources.ResourceLocation')))
  equipment='equipStack' if yarn else 'setItemSlot';readEquipment='getEquippedStack' if yarn else 'getItemBySlot';empty='ItemStack.EMPTY'
  getworld='world' if yarn else 'level';eye='getStandingEyeHeight' if yarn else 'getEyeHeight'
  nativeSample='net.minecraft.client.render.WorldRenderer.getLightmapCoordinates' if yarn else ('net.minecraft.util.LightCoordsUtil.getLightCoords' if newest else 'net.minecraft.client.renderer.LevelRenderer.'+('getLightCoords' if new else 'getLightColor'))
  lambLookup='''Object receiver=lamb.getMethod("itemLightSourceManager").invoke(lamb.getMethod("get").invoke(null));
            var method=Class.forName("dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager").getMethod("getLuminance",ItemStack.class,boolean.class);''' if new else '''Object receiver=null;
            var method=lamb.getMethod("getLuminanceFromItemStack",ItemStack.class,boolean.class);'''
  scene=f'''package {pkg};
{imports}
import java.nio.file.*;
import java.util.*;
public final class LightingQaEquipmentScene {{
 private static String acknowledged="",scheduled="",commandScheduled="";
 private static boolean ready;
 private static final EquipmentSlot[] SLOTS={{EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET}};
 private static void commands({mc} mc) {{
  if(!ready){{ready=true;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_NATIVE_COMMAND_CONTROLLER_READY");}}
  String file=System.getProperty("usefulToolsLightingQaNativeCommands");if(file==null||mc.player==null||mc.{getworld}==null)return;
  try{{Path path=Path.of(file);if(!Files.isRegularFile(path))return;List<String> lines=Files.readAllLines(path);if(lines.size()!=2||lines.get(0).equals(commandScheduled))return;String token=lines.get(0),command=lines.get(1);commandScheduled=token;var server=mc.{'getServer' if yarn else 'getSingleplayerServer'}();if(server==null)throw new AssertionError("Scene command requires integrated server");server.execute(()->{{try{{var player=server.{'getPlayerManager().getPlayer' if yarn else 'getPlayerList().getPlayer'}(mc.player.{'getUuid' if yarn else 'getUUID'}());if(player==null)throw new AssertionError("Missing scene command player");int result=server.{'getCommandManager' if yarn else 'getCommands'}().getDispatcher().execute(command,player.{'getCommandSource().withLevel(4)' if yarn else 'createCommandSourceStack().withPermission(net.minecraft.server.permissions.PermissionSet.ALL_PERMISSIONS)' if new else 'createCommandSourceStack().withPermission(4)'});com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_NATIVE_COMMAND_ACK token="+token+" result="+result);}}catch(Exception e){{com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_NATIVE_COMMAND_FAIL token="+token,e);}}}});}}catch(Exception e){{throw new IllegalStateException("Native scene command queue",e);}}
 }}
 public static void tick({mc} mc) {{
  commands(mc);
  String file=System.getProperty("usefulToolsLightingQaEquipmentScene");if(file==null||mc.player==null||mc.{getworld}==null)return;
  try {{
   Path path=Path.of(file);if(!Files.isRegularFile(path))return;List<String> lines=Files.readAllLines(path);if(lines.size()!=3||lines.get(0).equals(acknowledged))return;
   String token=lines.get(0),slot=lines.get(1),source=lines.get(2);int target=switch(slot){{case "held"->0;case "head"->2;case "chest"->3;case "legs"->4;case "feet"->5;default->throw new AssertionError("Scene slot "+slot);}};
   if(!token.equals(scheduled)) {{
    var server=mc.{'getServer' if yarn else 'getSingleplayerServer'}();if(server==null)throw new AssertionError("Equipment scene requires native integrated server");scheduled=token;
    server.execute(()->{{var player=server.{'getPlayerManager().getPlayer' if yarn else 'getPlayerList().getPlayer'}(mc.player.{'getUuid' if yarn else 'getUUID'}());if(player==null)throw new AssertionError("Missing scene player");player.getInventory().{'clear' if yarn else 'clearContent'}();for(EquipmentSlot equipment:SLOTS)player.{equipment}(equipment,ItemStack.EMPTY);player.{equipment}(SLOTS[target],new ItemStack({itemRegistry}.{itemLookup}({ident}(source))));player.{'currentScreenHandler.sendContentUpdates' if yarn else 'containerMenu.broadcastChanges'}();}});return;
   }}
   for(int i=0;i<SLOTS.length;i++) {{ItemStack stack=mc.player.{readEquipment}(SLOTS[i]);String actual=stack.isEmpty()?"minecraft:air":{itemRegistry}.{'getId' if yarn else 'getKey'}(stack.getItem()).toString();if(!actual.equals(i==target?source:"minecraft:air"))return;}}
   double x=Math.rint(mc.player.getX()*8)/8,y=Math.rint((mc.player.getY()+mc.player.{eye}()*.5)*8)/8,z=Math.rint(mc.player.getZ()*8)/8;int level=SourcePolicy.level(source,target>=2);if(Math.abs(ClientLighting.FIELD.light(x,y,z)-level)>1e-6)return;
   acknowledged=token;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_SCENE_EQUIPMENT_ACK token="+token+" slot="+slot+" source="+source+" level="+level+" all_other_slots_empty=true");
  }}catch(Exception e){{throw new IllegalStateException("Native equipment scene acknowledgement",e);}}
 }}
}}
'''
  scene_path=ROOT/'tests/dynamic-lighting/targets'/f'{v}-{loader}'/pkg.replace('.','/')/'LightingQaEquipmentScene.java';scene_path.parent.mkdir(parents=True,exist_ok=True);scene_path.write_text(scene)
  code=f'''package {pkg}.mixin;
{imports}
import {pkg}.ClientLighting;
import {pkg}.SourcePolicy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin({mc}.class)
public abstract class LightingQaMixin {{
    private static final EquipmentSlot[] SLOTS = {{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}};
    private static final String[] ITEMS = {{@ITEMS@}};
    private static final EquipmentSlot[] CASE_SLOTS = {{@SLOTS@}};
    private static int stage = -1, ticks;
    private static ItemStack[] original;
    private static boolean done, announced, disconnected;
    private static void clear({mc} mc) {{ for (EquipmentSlot slot : SLOTS) mc.player.{equipment}(slot, ItemStack.EMPTY); }}
    private static ItemStack item(String name) {{ ItemStack result=new ItemStack({itemRegistry}.{itemLookup}({ident}(name))); if(result.isEmpty())throw new AssertionError("Missing registered fixture item "+name);return result; }}
    private static void backends({mc} mc, BlockPos pos, String source) throws Exception {{
        boolean sodiumChecked=false;
        for(String prefix:new String[]{{"net.caffeinemc.mods.sodium","me.jellysquid.mods.sodium","org.embeddedt.embeddium.impl"}}) {{
            Class<?> cache;String dataPackage=prefix+(prefix.startsWith("org.embeddedt.")?".model.light.data":".client.model.light.data");
            try {{ cache=Class.forName(dataPackage+".HashLightDataCache"); }} catch(ClassNotFoundException absent) {{
                try {{cache=Class.forName(dataPackage+".SingleBlockLightDataCache");}} catch(ClassNotFoundException missing) {{
                    try {{Class.forName(dataPackage+".LightDataAccess");throw new AssertionError("Installed backend has no exercised native cache "+prefix);}} catch(ClassNotFoundException absentBackend) {{continue;}}
                }}
            }}
            Object instance;
            if(cache.getSimpleName().equals("SingleBlockLightDataCache")) {{
                instance=cache.getConstructor().newInstance();
                var reset=java.util.Arrays.stream(cache.getMethods()).filter(m->m.getName().equals("reset")&&m.getParameterCount()==2).findFirst().orElseThrow();
                reset.invoke(instance,pos,mc.{getworld});
            }} else instance=cache.getConstructors()[0].newInstance(mc.{getworld});
            sodiumChecked=true;
            int sampled=((Number)cache.getMethod("get",int.class,int.class,int.class).invoke(instance,pos.getX(),pos.getY(),pos.getZ())).intValue();
            int expected=(int)Math.ceil(ClientLighting.FIELD.light(pos.getX()+.5,pos.getY()+.5,pos.getZ()+.5)-1e-9);
            if((sampled&15)<expected)throw new AssertionError("Sodium's actual mesh cache missed visual light");
            com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_BACKEND_PASS Sodium cache source="+source);
        }}
        try {{Class.forName("net.caffeinemc.mods.sodium.client.model.light.data.LightDataAccess"); if(!sodiumChecked)throw new AssertionError("Installed Sodium has no exercised native light cache");}} catch(ClassNotFoundException absent) {{}}
        if(source.startsWith("usefultoolsmod:")&&SourcePolicy.level(source,false)>0) {{
            Class<?> lamb;
            try {{lamb=Class.forName("dev.lambdaurora.lambdynlights.LambDynLights");}} catch(ClassNotFoundException absent) {{return;}}
            {lambLookup}
            for(boolean water:new boolean[]{{false,true}}) if(((Number)method.invoke(receiver,item(source),water)).intValue()!=0)throw new AssertionError("Lamb duplicated companion-owned source "+source);
            com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_BACKEND_PASS Lamb yields owned source="+source);
        }}
    }}
    @Inject(method="tick", at=@At("TAIL"))
    private void usefulToolsLighting$qa(CallbackInfo ci) {{
        if (!announced) {{ announced=true; com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_LOADED"); }}
        {mc} mc = {mc}.getInstance();
        com.stonytark.usefultools.lighting.LightingQaMenu.capture(mc);
        com.stonytark.usefultools.lighting.LightingQaEquipmentScene.tick(mc);
        if(System.getProperty("usefulToolsLightingQaMultiplayer")!=null) {{ com.stonytark.usefultools.lighting.MultiplayerLightingQa.tick(mc); return; }}
        if (done) {{
            if(!disconnected&&mc.{getworld}==null) {{
                disconnected=true;
                if(ClientLighting.FIELD.size()!=0)throw new AssertionError("Disconnect retained sources");
                com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_ADVANCED_PASS disconnect clears field");
            }}
            if(disconnected&&mc.{getworld}!=null&&mc.player!=null) com.stonytark.usefultools.lighting.AdvancedLightingQa.reopened(mc);
            return;
        }}
        if (mc.{getworld} == null || mc.player == null || mc.player.getY() < 0) return;
        try {{
            if (stage == -1) {{
                if (++ticks < 100) return;
                if (!com.stonytark.usefultools.lighting.AdvancedLightingQa.prepare(mc)) return;
                original = new ItemStack[SLOTS.length]; for(int i=0;i<SLOTS.length;i++) original[i]=mc.player.{readEquipment}(SLOTS[i]).copy();
                stage=0; clear(mc); mc.player.{equipment}(CASE_SLOTS[0],item(ITEMS[0])); ticks=0; return;
            }}
            if(stage>ITEMS.length+1) {{
                if(com.stonytark.usefultools.lighting.AdvancedLightingQa.tick(mc)) {{
                    for(int i=0;i<SLOTS.length;i++)mc.player.{equipment}(SLOTS[i],original[i]);
                    done=true;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_PASS cases="+stage+" advanced=10");
                }}
                return;
            }}
            if (++ticks < 12) return;
            double x=Math.rint(mc.player.getX()*8)/8, y=Math.rint((mc.player.getY()+mc.player.{eye}()*.5)*8)/8, z=Math.rint(mc.player.getZ()*8)/8;
            int expected=stage<ITEMS.length ? SourcePolicy.level(ITEMS[stage], CASE_SLOTS[stage]!=EquipmentSlot.MAINHAND && CASE_SLOTS[stage]!=EquipmentSlot.OFFHAND) : stage==ITEMS.length ? 15 : 0;
            if(expected==15&&!com.stonytark.usefultools.lighting.AdvancedLightingQa.serverInvariant(mc))return;
            double value=ClientLighting.FIELD.light(x,y,z);
            if (Math.abs(value-expected)>1e-6) throw new AssertionError("stage="+stage+" source="+(stage<ITEMS.length?ITEMS[stage]:"mixed/removal")+" expected="+expected+" actual="+value+" held="+mc.player.{readEquipment}(EquipmentSlot.MAINHAND)+" entities="+ClientLighting.FIELD.size());
            if (expected>0) {{
                int radius=stage<ITEMS.length?SourcePolicy.radius(ITEMS[stage],false):30;
                for(int distance:new int[]{{10,15,20,29}}) {{
                    double want=Math.max(0,expected*(1-distance/(double)radius));
                    if(Math.abs(ClientLighting.FIELD.light(x+distance,y,z)-want)>1e-6) throw new AssertionError("radius "+distance);
                }}
                BlockPos pos=new BlockPos((int)Math.floor(x+20),(int)Math.floor(y),(int)Math.floor(z));
                int sampled={nativeSample}(mc.{getworld},pos);
                int required=(int)(ClientLighting.FIELD.light(pos.getX()+.5,pos.getY()+.5,pos.getZ()+.5)*16);
                if((sampled&255)<required) throw new AssertionError("Native renderer hook did not sample companion light");
                backends(mc,pos,stage<ITEMS.length?ITEMS[stage]:"mixed");
            }}
            com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_CASE_PASS stage="+stage+" source="+(stage<ITEMS.length?ITEMS[stage]:"mixed/removal")+" slot="+(stage<ITEMS.length?CASE_SLOTS[stage]:"mixed/removal")+" level="+value);
            stage++; ticks=0; clear(mc);
            if(stage<ITEMS.length) mc.player.{equipment}(CASE_SLOTS[stage],item(ITEMS[stage]));
            else if(stage==ITEMS.length) {{ mc.player.{equipment}(EquipmentSlot.MAINHAND,item("usefultoolsmod:ectoplasm_lantern")); mc.player.{equipment}(EquipmentSlot.OFFHAND,item("usefultoolsmod:hglow")); for(EquipmentSlot slot:new EquipmentSlot[]{{EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET}}) mc.player.{equipment}(slot,item("usefultoolsmod:hglow_"+(slot==EquipmentSlot.HEAD?"helmet":slot==EquipmentSlot.CHEST?"chestplate":slot==EquipmentSlot.LEGS?"leggings":"boots"))); }}
            else if(stage>ITEMS.length+1) com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_EQUIPMENT_PASS cases="+stage);
        }} catch(Throwable error) {{ done=true; com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_QA_FAIL "+error); error.printStackTrace(); }}
    }}
}}
'''
  cases=[]
  for row in rows:
   for hand in ['MAINHAND','OFFHAND']:cases.append(('usefultoolsmod:'+row['id'],hand))
   if row['worn']: cases.append(('usefultoolsmod:'+row['id'], {'helmet':'HEAD','chestplate':'CHEST','leggings':'LEGS','boots':'FEET'}[row['id'].split('_')[1]]))
  cases += [('minecraft:glowstone','MAINHAND'),('minecraft:glowstone','OFFHAND'),('usefultoolsmod:calcified_amethyst','MAINHAND'),('usefultoolsmod:ectoplasm','MAINHAND')]
  code=code.replace('@ITEMS@',','.join('"'+name+'"' for name,slot in cases)).replace('@SLOTS@',','.join('EquipmentSlot.'+slot for name,slot in cases))
  path=ROOT/'tests/dynamic-lighting/targets'/(v+'-'+loader)/pkg.replace('.','/')/'mixin/LightingQaMixin.java';path.parent.mkdir(parents=True,exist_ok=True);path.write_text(code)
  widget='net.minecraft.client.gui.widget.ClickableWidget' if yarn else 'net.minecraft.client.gui.components.AbstractWidget'
  screen='currentScreen' if yarn else 'gui.screen()' if newest else 'screen'
  scale='getScaleFactor' if yarn else 'getGuiScale'
  menu=f'''package {pkg};
/** Fresh native control bounds supplement pixel-font OCR in acceptance clients only. */
public final class LightingQaMenu {{
    private static long last;
    private LightingQaMenu() {{}}
    public static void capture({('net.minecraft.client.MinecraftClient' if yarn else 'net.minecraft.client.Minecraft')} mc) {{
        String destination=System.getProperty("usefulToolsLightingQaMenu");
        long now=System.currentTimeMillis();
        if(destination==null||mc.{screen}==null||now-last<250)return;
        last=now;
        StringBuilder text=new StringBuilder().append(now).append('\\n');
        double scale=mc.getWindow().{scale}();
        for(var control:mc.{screen}.children()) if(control instanceof {widget} button && button.visible && button.active) {{
            String label=java.util.Base64.getEncoder().encodeToString(button.getMessage().getString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
            text.append(label).append('\\t').append((button.getX()+button.getWidth()*.5)*scale).append('\\t').append((button.getY()+button.getHeight()*.5)*scale).append('\\n');
        }}
        com.stonytark.usefultools.lighting.qa.OptifineLightingQaMenuSnapshot.append(mc.{screen},scale,text);
        try {{java.nio.file.Files.write(java.nio.file.Paths.get(destination),text.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));}}
        catch(java.io.IOException error) {{throw new IllegalStateException("Cannot publish acceptance menu state",error);}}
    }}
}}
'''
  (path.parent.parent/'LightingQaMenu.java').write_text(menu)
print('Generated native client fixtures for 15 modern targets; classical FML fixture maintained separately.')

import runpy
runpy.run_path(str(ROOT/"scripts/generate-lighting-advanced-qa.py"), run_name="__main__")
runpy.run_path(str(ROOT/"scripts/generate-lighting-multiplayer-qa.py"), run_name="__main__")
