package com.stonytark.usefultools.lighting.classic;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/** Paired native timing and FPS observations. Never packaged for distribution. */
public final class PerformanceLightingQa {
    private static int phase,ticks,samples,fpsSamples,fpsTotal;
    private static long cpu;
    private static EntityOtherPlayerMP actor;
    private static double x,y,z;
    private static final String[] NAMES={"baseline","ordinary_radius_15","hardened_radius_30","hardened_moving_radius_30"};
    private static final ClassicClientLighting ADAPTER=new ClassicClientLighting();
    private static final TickEvent.ClientTickEvent EVENT=new TickEvent.ClientTickEvent(TickEvent.Phase.END);
    private static ItemStack item(String name){return new ItemStack((Item)Item.itemRegistry.getObject(name));}
    public static boolean tick(Minecraft mc){
        if(actor==null){
            mc.gameSettings.limitFramerate=260;mc.gameSettings.enableVsync=false;
            org.lwjgl.opengl.Display.setVSyncEnabled(false);
            actor=new EntityOtherPlayerMP(mc.theWorld,new GameProfile(UUID.randomUUID(),"LightingPerf"));
            x=mc.thePlayer.posX+8;y=mc.thePlayer.posY;z=mc.thePlayer.posZ;
            actor.setPosition(x,y,z);mc.theWorld.addEntityToWorld(2000100,actor);
        }
        if(ticks==0)actor.setCurrentItemOrArmor(0,phase==0?null:item(phase==1?"minecraft:glowstone":"usefultoolsmod:hglow"));
        if(phase==3)actor.setPosition(x+Math.sin(ticks*.13)*4,y,z);
        long start=System.nanoTime();ADAPTER.tick(EVENT);long elapsed=System.nanoTime()-start;
        if(ticks>=40){
            cpu+=elapsed;samples++;
            if(ticks%20==0){fpsTotal+=Integer.parseInt(mc.debug.split(" ")[0]);fpsSamples++;}
        }
        if(++ticks<140)return false;
        System.out.println("USEFUL_TOOLS_LIGHTING_QA_PERF phase="+NAMES[phase]+" adapter_us="+(cpu/1000.0/samples)+" mean_fps="+(fpsTotal/(double)fpsSamples)+" tick_samples="+samples+" fps_samples="+fpsSamples+" fps_cap=260 vsync=false");
        ticks=samples=fpsSamples=fpsTotal=0;cpu=0;
        if(++phase<4)return false;
        mc.theWorld.removeEntityFromWorld(actor.getEntityId());return true;
    }
}
