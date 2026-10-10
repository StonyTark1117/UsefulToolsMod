package com.stonytark.usefultools.lighting.classic;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

/** Native Java 8 acceptance fixture. Runs only inside a disposable integrated client. */
public final class AdvancedLightingQa {
    private static final ConcurrentLinkedQueue<Runnable> SERVER = new ConcurrentLinkedQueue<Runnable>();
    private static boolean preparing;
    private static volatile boolean ready, checked, failed;
    private static int preparationTicks, step, ticks, px, py, pz, bx, by, bz;
    private static double x,y,z,oldX;
    private static byte[] baseline;
    private static boolean spawnBaseline;
    private static WorldClient originalWorld;
    private static EntityLivingBase a,b,mob;
    public static void serverTick() { Runnable task; while((task=SERVER.poll())!=null)task.run(); }
    private static WorldServer serverWorld() { return Minecraft.getMinecraft().getIntegratedServer().worldServerForDimension(0); }
    private static ItemStack item(String name) { return new ItemStack((Item)Item.itemRegistry.getObject("usefultoolsmod:"+name)); }
    private static void log(String value) { System.out.println("USEFUL_TOOLS_LIGHTING_QA_ADVANCED_PASS "+value); }
    private static byte[] bytes() {
        ExtendedBlockStorage section=serverWorld().getChunkFromBlockCoords(px,pz).getBlockStorageArray()[py>>4];
        return section==null?new byte[2048]:section.getBlocklightArray().data.clone();
    }
    private static boolean spawn() {
        EntityZombie zombie=new EntityZombie(serverWorld());zombie.setPosition(px+.5,py,pz+.5);zombie.getRNG().setSeed(123);
        return zombie.getCanSpawnHere();
    }
    public static boolean prepare(final Minecraft mc) {
        if(!preparing) {
            preparing=true;px=(int)Math.floor(mc.thePlayer.posX)+8;py=Math.max(5,(int)Math.floor(mc.thePlayer.posY)-8);pz=(int)Math.floor(mc.thePlayer.posZ);
            SERVER.add(new Runnable(){public void run(){
                mc.gameSettings.difficulty=EnumDifficulty.NORMAL;
                mc.getIntegratedServer().func_147139_a(EnumDifficulty.NORMAL);
                for(int dx=-2;dx<=2;dx++)for(int dy=-1;dy<=3;dy++)for(int dz=-2;dz<=2;dz++)
                    serverWorld().setBlock(px+dx,py+dy,pz+dz,Math.abs(dx)==2||Math.abs(dz)==2||dy==-1||dy==3?Blocks.stone:Blocks.air,0,3);
            }});
        }
        if(failed)throw new AssertionError("Server fixture failed");
        if(ready)return true;
        if(++preparationTicks==80)SERVER.add(new Runnable(){public void run(){
            baseline=bytes();spawnBaseline=spawn();
            if(serverWorld().getSavedLightValue(EnumSkyBlock.Block,px,py,pz)!=0||!spawnBaseline){failed=true;System.err.println("USEFUL_TOOLS_LIGHTING_QA_FAIL invalid classic spawn control block="+serverWorld().getSavedLightValue(EnumSkyBlock.Block,px,py,pz)+" sky="+serverWorld().getSavedLightValue(EnumSkyBlock.Sky,px,py,pz)+" eligible="+spawnBaseline+" difficulty="+serverWorld().difficultySetting);}
            else ready=true;
        }});
        return false;
    }
    public static boolean serverInvariant() {
        if(failed)throw new AssertionError("Server-light/saved-array/spawn invariant failed");
        if(checked)return true;
        SERVER.add(new Runnable(){public void run(){
            if(checked||failed)return;
            if(!java.util.Arrays.equals(baseline,bytes())||serverWorld().getSavedLightValue(EnumSkyBlock.Block,px,py,pz)!=0||spawnBaseline!=spawn()
                    ||ClassicClientLighting.FIELD.light(px+.5,py+.5,pz+.5)<=0){failed=true;System.err.println("USEFUL_TOOLS_LIGHTING_QA_FAIL classic server-light/saved-array/spawn invariant");}
            else{checked=true;log("server block-light bytes and spawn eligibility unchanged inside visual field");}
        }});return false;
    }
    private static double sy(EntityLivingBase entity){return Math.rint((entity.posY+entity.getEyeHeight()*.5)*8)/8;}
    private static void expect(double px,double py,double pz,double want,String description){double value=ClassicClientLighting.FIELD.light(px,py,pz);if(Math.abs(value-want)>1e-6)throw new AssertionError(description+" expected="+want+" actual="+value);}
    private static void dimension(final Minecraft mc,final int destination,final double tx,final double ty,final double tz){SERVER.add(new Runnable(){public void run(){
        EntityPlayerMP player=mc.getIntegratedServer().getConfigurationManager().func_152612_a(mc.thePlayer.getCommandSenderName());
        mc.getIntegratedServer().getConfigurationManager().transferPlayerToDimension(player,destination);
        player.playerNetServerHandler.setPlayerLocation(tx,ty,tz,0,0);
    }});}
    public static boolean tick(final Minecraft mc){
        if(++ticks<20)return false;ticks=0;
        if(step==0){
            originalWorld=mc.theWorld;x=Math.rint(mc.thePlayer.posX*8)/8;y=Math.min(220,Math.rint(mc.thePlayer.posY*8)/8+32);z=Math.rint(mc.thePlayer.posZ*8)/8;
            a=new EntityOtherPlayerMP(mc.theWorld,new GameProfile(UUID.randomUUID(),"LightingQaA"));b=new EntityOtherPlayerMP(mc.theWorld,new GameProfile(UUID.randomUUID(),"LightingQaB"));mob=new EntityZombie(mc.theWorld);
            a.setPosition(x+64,y,z);b.setPosition(x-64,y,z);mob.setPosition(x,y,z+64);
            mc.theWorld.setBlock((int)Math.floor(x),(int)Math.floor(y)-1,(int)Math.floor(z)+64,Blocks.stone);
            mc.theWorld.setBlock((int)Math.floor(x),(int)Math.floor(y)+2,(int)Math.floor(z)+64,Blocks.stone);
            a.setCurrentItemOrArmor(0,item("ectoplasm_lantern"));b.setCurrentItemOrArmor(0,item("hglow"));mob.setCurrentItemOrArmor(1,item("hglow_boots"));
            mc.theWorld.addEntityToWorld(2000001,a);mc.theWorld.addEntityToWorld(2000002,b);mc.theWorld.addEntityToWorld(2000003,mob);
        }else if(step==1){
            expect(a.posX,sy(a),a.posZ,12,"remote main hand");expect(b.posX,sy(b),b.posZ,15,"second remote main hand");expect(Math.rint(mob.posX*8)/8,sy(mob),Math.rint(mob.posZ*8)/8,15,"mob boots");log("two visible remote players and equipped mob");
            for(int dx=-1;dx<=1;dx++)for(int dy=0;dy<=1;dy++)for(int dz=-1;dz<=1;dz++)mc.theWorld.setBlock((int)Math.floor(x)+dx,(int)Math.floor(y)+dy,(int)Math.floor(z)+64+dz,Blocks.water);
        }else if(step==2){
            if(!mob.isInWater())throw new AssertionError("Classic water control did not immerse mob");
            expect(Math.rint(mob.posX*8)/8,sy(mob),Math.rint(mob.posZ*8)/8,15,"underwater boots");log("underwater worn emission retained");oldX=a.posX;a.setPosition(oldX+48,y,z);
        }else if(step==3){expect(oldX,sy(a),z,0,"former source position");expect(a.posX,sy(a),z,12,"moved source");log("movement removes former illumination");mob.setCurrentItemOrArmor(1,null);
        }else if(step==4){expect(Math.rint(mob.posX*8)/8,sy(mob),Math.rint(mob.posZ*8)/8,0,"mob unequip");log("mob unequip removes illumination");
            mc.theWorld.removeEntityFromWorld(a.getEntityId());mc.theWorld.removeEntityFromWorld(b.getEntityId());mc.theWorld.removeEntityFromWorld(mob.getEntityId());
            bx=(int)Math.floor(x)+16;by=(int)Math.floor(mc.thePlayer.posY)+8;bz=(int)Math.floor(z);
            SERVER.add(new Runnable(){public void run(){serverWorld().setBlock(bx,by,bz,(net.minecraft.block.Block)net.minecraft.block.Block.blockRegistry.getObject("usefultoolsmod:hglow_block"),0,3);}});
        }else if(step==5){
            if(mc.theWorld.getBlock(bx,by,bz).getLightValue(mc.theWorld,bx,by,bz)!=15)return false;
            expect(bx+.5,by+.5,bz+.5,15,"network placed source");SERVER.add(new Runnable(){public void run(){if(serverWorld().getSavedLightValue(EnumSkyBlock.Block,bx,by,bz)!=15){failed=true;System.err.println("USEFUL_TOOLS_LIGHTING_QA_FAIL classic placed block light !=15");}else log("placed Hardened Glowstone actual server block light 15");}});
            log("network block placement indexed and removed entities cleared");dimension(mc,-1,0,100,0);
        }else if(step==6){if(mc.theWorld==originalWorld)return false;if(ClassicClientLighting.FIELD.size()!=0)throw new AssertionError("Dimension retained old sources");log("dimension change clears old field");dimension(mc,0,x,y-32,z);
        }else if(step==7){if(mc.theWorld.provider.dimensionId!=0)return false;if(ClassicClientLighting.FIELD.light(bx+.5,by+.5,bz+.5)<15)return false;log("return reload indexes retained placed light");SERVER.add(new Runnable(){public void run(){serverWorld().setBlockToAir(bx,by,bz);}});
        }else if(step==8){if(mc.theWorld.getBlock(bx,by,bz)!=Blocks.air)return false;expect(bx+.5,by+.5,bz+.5,0,"network block removal");log("network block removal clears field");
        }else if(step==9){if(failed)throw new AssertionError("Server fixture failed");SERVER.add(new Runnable(){public void run(){serverWorld().setBlock(bx,by,bz,(net.minecraft.block.Block)net.minecraft.block.Block.blockRegistry.getObject("usefultoolsmod:hglow_block"),0,3);}});
        }else if(step==10){if(mc.theWorld.getBlock(bx,by,bz).getLightValue(mc.theWorld,bx,by,bz)!=15||ClassicClientLighting.FIELD.light(bx+.5,by+.5,bz+.5)<15)return false;return true;}
        step++;return false;
    }
    private static int reloadStep;
    private static int reloadTicks;
    private static volatile boolean reloadChecked;
    private static boolean reloadAnnounced;
    public static void reopened(Minecraft mc){
        if(!reloadAnnounced){reloadAnnounced=true;System.out.println("USEFUL_TOOLS_LIGHTING_QA_REOPEN_READY");}
        if(reloadStep==0){
            if(mc.theWorld.getBlock(bx,by,bz).getLightValue(mc.theWorld,bx,by,bz)!=15||ClassicClientLighting.FIELD.light(bx+.5,by+.5,bz+.5)<15)return;
            log("saved world reopen restores placed emission and visual index");SERVER.add(new Runnable(){public void run(){serverWorld().setBlockToAir(bx,by,bz);}});reloadStep=1;
        }else if(reloadStep==1&&mc.theWorld.getBlock(bx,by,bz)==Blocks.air&&ClassicClientLighting.FIELD.light(bx+.5,by+.5,bz+.5)==0){
            reloadStep=2;log("saved world removal clears visual index");
        }else if(reloadStep==2){
            if(++reloadTicks==80)SERVER.add(new Runnable(){public void run(){boolean equal=java.util.Arrays.equals(baseline,bytes());int block=serverWorld().getSavedLightValue(EnumSkyBlock.Block,px,py,pz);boolean eligible=spawn();if(!equal||block!=0||spawnBaseline!=eligible){failed=true;System.err.println("USEFUL_TOOLS_LIGHTING_QA_FAIL saved-world light/spawn invariant bytes_equal="+equal+" block="+block+" spawn="+eligible);}else{reloadChecked=true;log("saved world block-light bytes and spawn eligibility unchanged");}}});
            if(reloadChecked&&PerformanceLightingQa.tick(mc)){reloadStep=3;System.out.println("USEFUL_TOOLS_LIGHTING_QA_REOPEN_PASS");}
        }
    }
}
