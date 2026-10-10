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
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.OtherClientPlayerEntity;
/** Paired native adapter timing and client FPS observations; never distributed. */
public final class PerformanceLightingQa {
    private static int phase,ticks,samples,fpsSamples,fpsTotal;
    private static long cpu;
    private static LivingEntity a;
    private static double x,y,z;
    private static final String[] NAMES={"baseline","ordinary_radius_15","hardened_radius_30","hardened_moving_radius_30"};
    private static ItemStack item(String name){return new ItemStack(Registries.ITEM.get(new net.minecraft.util.Identifier(name)));}
    public static boolean tick(MinecraftClient mc){
        if(a==null){
            mc.options.getMaxFps().setValue(260);mc.options.getEnableVsync().setValue(false);
            a=new OtherClientPlayerEntity(mc.world,new GameProfile(UUID.randomUUID(),"LightingPerf"));a.setId(2000100);a.setNoGravity(true);
            x=mc.player.getX()+8;y=mc.player.getY();z=mc.player.getZ();a.setPosition(x,y,z);mc.world.addPlayer(a.getId(),(OtherClientPlayerEntity)a);
        }
        if(ticks==0)a.equipStack(EquipmentSlot.MAINHAND,phase==0?ItemStack.EMPTY:item(phase==1?"minecraft:glowstone":"usefultoolsmod:hglow"));
        if(phase==3)a.setPosition(x+Math.sin(ticks*.13)*4,y,z);
        long start=System.nanoTime();ClientLighting.tick();long elapsed=System.nanoTime()-start;
        if(ticks>=40){cpu+=elapsed;samples++;if(ticks%20==0){fpsTotal+=mc.getCurrentFps();fpsSamples++;}}
        if(++ticks<140)return false;
        com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_PERF phase="+NAMES[phase]+" adapter_us="+(cpu/1000.0/samples)+" mean_fps="+(fpsTotal/(double)fpsSamples)+" tick_samples="+samples+" fps_samples="+fpsSamples+" fps_cap=260 vsync=false");
        ticks=samples=fpsSamples=fpsTotal=0;cpu=0;
        if(++phase<4)return false;
        mc.world.removeEntity(a.getId(),Entity.RemovalReason.DISCARDED);return true;
    }
}
