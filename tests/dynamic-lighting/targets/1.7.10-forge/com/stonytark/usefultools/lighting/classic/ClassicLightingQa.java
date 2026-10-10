package com.stonytark.usefultools.lighting.classic;
import com.stonytark.usefultools.lighting.SourcePolicy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
@Mod(modid="useful_tools_lighting_qa",name="Unpackaged Lighting Acceptance Fixture",version="1",acceptableRemoteVersions="*")
public final class ClassicLightingQa {
    private static final String[] ITEMS={"usefultoolsmod:ectoplasm_lantern","usefultoolsmod:soul_lantern","usefultoolsmod:hglow_block","usefultoolsmod:hglow","usefultoolsmod:hglowstone_axe","usefultoolsmod:hglowstone_hoe","usefultoolsmod:hglowstone_pickaxe","usefultoolsmod:hglowstone_shovel","usefultoolsmod:hglowstone_sword","usefultoolsmod:hglow_helmet","usefultoolsmod:hglow_helmet","usefultoolsmod:hglow_chestplate","usefultoolsmod:hglow_chestplate","usefultoolsmod:hglow_leggings","usefultoolsmod:hglow_leggings","usefultoolsmod:hglow_boots","usefultoolsmod:hglow_boots","minecraft:glowstone","usefultoolsmod:calcified_amethyst","usefultoolsmod:ectoplasm"};
    private static final int[] SLOTS={0,0,0,0,0,0,0,0,0,0,4,0,3,0,2,0,1,0,0,0};
    private int stage=-1,ticks;private boolean done,disconnected;private ItemStack[] original;
    @Mod.EventHandler public void initialize(FMLInitializationEvent event) { if(event.getSide().isClient()) FMLCommonHandler.instance().bus().register(this); }
    @Mod.EventHandler public void serverStarting(cpw.mods.fml.common.event.FMLServerStartingEvent event) { event.registerServerCommand(new LightingQaSceneCommand()); }
    private static ItemStack item(String id){return new ItemStack((Item)Item.itemRegistry.getObject(id));}
    private static void clear(Minecraft mc){for(int i=0;i<=4;i++)mc.thePlayer.setCurrentItemOrArmor(i,null);}
    @SubscribeEvent public void serverTick(TickEvent.ServerTickEvent event){if(event.phase==TickEvent.Phase.END){if(System.getProperty("usefulToolsLightingQaMultiplayer")!=null)ClassicMultiplayerLightingQa.serverTick();else AdvancedLightingQa.serverTick();}}
    @SubscribeEvent public void tick(TickEvent.ClientTickEvent event){
        if(event.phase!=TickEvent.Phase.END)return;
        if(System.getProperty("usefulToolsLightingQaMultiplayer")!=null){ClassicMultiplayerLightingQa.tick(Minecraft.getMinecraft());return;}
        LightingQaSceneCommand.verifyClient(Minecraft.getMinecraft());
        if(done){if(!disconnected&&Minecraft.getMinecraft().theWorld==null){disconnected=true;if(ClassicClientLighting.FIELD.size()!=0)throw new AssertionError("Disconnect retained field");System.out.println("USEFUL_TOOLS_LIGHTING_QA_ADVANCED_PASS disconnect clears field");}if(disconnected&&Minecraft.getMinecraft().theWorld!=null&&Minecraft.getMinecraft().thePlayer!=null)AdvancedLightingQa.reopened(Minecraft.getMinecraft());return;}
        Minecraft mc=Minecraft.getMinecraft();if(mc.theWorld==null||mc.thePlayer==null)return;
        try{
            if(stage<0){if(++ticks<100||!AdvancedLightingQa.prepare(mc))return;original=new ItemStack[5];for(int i=0;i<=4;i++){ItemStack st=mc.thePlayer.getEquipmentInSlot(i);original[i]=st==null?null:st.copy();}stage=0;clear(mc);mc.thePlayer.setCurrentItemOrArmor(SLOTS[0],item(ITEMS[0]));return;}
            if(stage>ITEMS.length+1){if(AdvancedLightingQa.tick(mc)){for(int i=0;i<=4;i++)mc.thePlayer.setCurrentItemOrArmor(i,original[i]);done=true;System.out.println("USEFUL_TOOLS_LIGHTING_QA_PASS cases="+stage+" advanced=10");}return;}
            if(++ticks<12)return;
            double x=Math.rint(mc.thePlayer.posX*8)/8,y=Math.rint((mc.thePlayer.posY+mc.thePlayer.getEyeHeight()*.5)*8)/8,z=Math.rint(mc.thePlayer.posZ*8)/8;
            int expected=stage<ITEMS.length?SourcePolicy.level(ITEMS[stage],SLOTS[stage]!=0):stage==ITEMS.length?15:0;
            if(expected==15&&!AdvancedLightingQa.serverInvariant())return;
            double actual=ClassicClientLighting.FIELD.light(x,y,z);if(Math.abs(actual-expected)>1e-6)throw new AssertionError("source "+stage+" expected="+expected+" actual="+actual);
            ClassicBeddiumLightingQa.sample(mc,(int)Math.floor(x+20),(int)Math.floor(y),(int)Math.floor(z),stage);
            if(expected>0){int radius=stage<ITEMS.length?SourcePolicy.radius(ITEMS[stage],false):30;for(int distance:new int[]{10,15,20,29}){double want=Math.max(0,expected*(1-distance/(double)radius));if(Math.abs(ClassicClientLighting.FIELD.light(x+distance,y,z)-want)>1e-6)throw new AssertionError("radius "+distance);}
                int bx=(int)Math.floor(x+20),by=(int)Math.floor(y),bz=(int)Math.floor(z);
                int sampled=mc.theWorld.getBlock(bx,by,bz).getMixedBrightnessForBlock(mc.theWorld,bx,by,bz);int required=(int)(ClassicClientLighting.FIELD.light(bx+.5,by+.5,bz+.5)*16);
                if((sampled&255)<required)throw new AssertionError("Native Forge transformer failed to sample field");
            }
            System.out.println("USEFUL_TOOLS_LIGHTING_QA_CASE_PASS stage="+stage+" source="+(stage<ITEMS.length?ITEMS[stage]:"mixed/removal")+" slot="+(stage<ITEMS.length?SLOTS[stage]:-1)+" level="+actual);
            clear(mc);stage++;ticks=0;
            if(stage<ITEMS.length)mc.thePlayer.setCurrentItemOrArmor(SLOTS[stage],item(ITEMS[stage]));
            else if(stage==ITEMS.length){mc.thePlayer.setCurrentItemOrArmor(0,item("usefultoolsmod:ectoplasm_lantern"));for(int i=1;i<=4;i++)mc.thePlayer.setCurrentItemOrArmor(i,item("usefultoolsmod:hglow_"+(i==4?"helmet":i==3?"chestplate":i==2?"leggings":"boots")));}
            else if(stage>ITEMS.length+1)System.out.println("USEFUL_TOOLS_LIGHTING_QA_EQUIPMENT_PASS cases="+stage);
        }catch(Throwable error){done=true;System.err.println("USEFUL_TOOLS_LIGHTING_QA_FAIL "+error);error.printStackTrace();}
    }
}
