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
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.player.RemotePlayer;
/** Paired native adapter timing and client FPS observations; never distributed. */
public final class PerformanceLightingQa {
    private static int phase,ticks,samples,fpsSamples,fpsTotal;
    private static long cpu;
    private static LivingEntity a;
    private static double x,y,z;
    private static final String[] NAMES={"baseline","ordinary_radius_15","hardened_radius_30","hardened_moving_radius_30"};
    private static ItemStack item(String name){return new ItemStack(BuiltInRegistries.ITEM.get(new net.minecraft.resources.ResourceLocation(name)));}
    public static boolean tick(Minecraft mc){
        if(a==null){
            mc.options.framerateLimit().set(260);mc.options.enableVsync().set(false);
            a=new RemotePlayer(mc.level,new GameProfile(UUID.randomUUID(),"LightingPerf"));a.setId(2000100);a.setNoGravity(true);
            x=mc.player.getX()+8;y=mc.player.getY();z=mc.player.getZ();a.setPos(x,y,z);mc.level.addEntity(a);
        }
        if(ticks==0)a.setItemSlot(EquipmentSlot.MAINHAND,phase==0?ItemStack.EMPTY:item(phase==1?"minecraft:glowstone":"usefultoolsmod:hglow"));
        if(phase==3)a.setPos(x+Math.sin(ticks*.13)*4,y,z);
        long start=System.nanoTime();ClientLighting.tick();long elapsed=System.nanoTime()-start;
        if(ticks>=40){cpu+=elapsed;samples++;if(ticks%20==0){fpsTotal+=mc.getFps();fpsSamples++;}}
        if(++ticks<140)return false;
        com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_PERF phase="+NAMES[phase]+" adapter_us="+(cpu/1000.0/samples)+" mean_fps="+(fpsTotal/(double)fpsSamples)+" tick_samples="+samples+" fps_samples="+fpsSamples+" fps_cap=260 vsync=false");
        ticks=samples=fpsSamples=fpsTotal=0;cpu=0;
        if(++phase<4)return false;
        mc.level.removeEntity(a.getId(),Entity.RemovalReason.DISCARDED);return true;
    }
}
