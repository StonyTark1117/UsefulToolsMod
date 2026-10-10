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
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.block.Blocks;
/** Runs only in a disposable acceptance world; never packaged with the mod. */
public final class AdvancedLightingQa {
    private static int step, ticks;
    private static ClientLevel originalWorld;
    private static LivingEntity a, b, mob;
    private static double x,y,z,oldX;
    private static BlockPos placed;
    private static volatile boolean serverReady, serverChecked, serverFailed;
    private static BlockPos spawnProbe;
    private static byte[] initialLight;
    private static boolean initialSpawn;
    private static boolean preparing;
    private static int preparationTicks;
    private static ItemStack item(String name) { return new ItemStack(BuiltInRegistries.ITEM.get(new net.minecraft.resources.ResourceLocation(name))); }
    private static void log(String message) { com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_ADVANCED_PASS " + message); }
    private static void command(Minecraft mc, String text) {
        var server=mc.getSingleplayerServer();
        server.execute(() -> server.getCommands().performPrefixedCommand(server.createCommandSourceStack(),text));
    }
    private static byte[] bytes(net.minecraft.server.level.ServerLevel level) {
        var data=level.getLightEngine().getLayerListener( net.minecraft.world.level.LightLayer.BLOCK).getDataLayerData(net.minecraft.core.SectionPos.of(spawnProbe));
        return data==null?new byte[2048]:data.getData().clone();
    }
    private static boolean spawn(net.minecraft.server.level.ServerLevel level) {
        return net.minecraft.world.entity.monster.Monster.checkMonsterSpawnRules(net.minecraft.world.entity.EntityType.ZOMBIE,level,net.minecraft.world.entity.MobSpawnType.NATURAL,spawnProbe,net.minecraft.util.RandomSource.create(123));
    }
    public static boolean prepare(Minecraft mc) {
        if (!preparing) {
            preparing=true;command(mc,"difficulty normal");
            spawnProbe=new BlockPos((int)Math.floor(mc.player.getX())+8,(int)Math.floor(mc.player.getY())-8,(int)Math.floor(mc.player.getZ()));
            mc.getSingleplayerServer().execute(() -> {
                var level=mc.getSingleplayerServer().overworld();
                // Protect the fixture from a random world's hostile mobs;
                // difficulty and native spawn eligibility remain unchanged.
                for (var player : mc.getSingleplayerServer().getPlayerList().getPlayers()) player.getAbilities().invulnerable=true;
                for(int bx=-2;bx<=2;bx++)for(int by=-1;by<=3;by++)for(int bz=-2;bz<=2;bz++) {
                    boolean wall=Math.abs(bx)==2||Math.abs(bz)==2||by==-1||by==3;
                    level.setBlock(spawnProbe.offset(bx,by,bz),(wall?Blocks.STONE:Blocks.AIR).defaultBlockState(),3);
                }
            });
        }
        if(serverFailed)throw new AssertionError("Server acceptance fixture failed");
        if(serverReady)return true;
        if(++preparationTicks==80)mc.getSingleplayerServer().execute(() -> {
            var level=mc.getSingleplayerServer().overworld();
            initialLight=bytes(level);initialSpawn=spawn(level);
            if(level.getBrightness(net.minecraft.world.level.LightLayer.BLOCK,spawnProbe)!=0||!initialSpawn) {
                serverFailed=true;com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_QA_FAIL expected dark spawn-eligible control room block="+level.getBrightness(net.minecraft.world.level.LightLayer.BLOCK,spawnProbe)+" sky="+level.getBrightness(net.minecraft.world.level.LightLayer.SKY,spawnProbe)+" eligible="+initialSpawn+" difficulty="+level.getDifficulty()+" floor="+level.getBlockState(spawnProbe.offset(0,-1,0)) );
            } else serverReady=true;
        });
        return false;
    }
    public static boolean serverInvariant(Minecraft mc) {
        if(serverFailed)throw new AssertionError("Server-light/saved-array/spawn invariant failed");
        if(serverChecked)return true;
        mc.getSingleplayerServer().execute(() -> {
            if(serverChecked||serverFailed)return;
            var level=mc.getSingleplayerServer().overworld();
            if(!java.util.Arrays.equals(initialLight,bytes(level))||level.getBrightness(net.minecraft.world.level.LightLayer.BLOCK,spawnProbe)!=0||initialSpawn!=spawn(level)
                    ||ClientLighting.FIELD.light(spawnProbe.getX()+.5,spawnProbe.getY()+.5,spawnProbe.getZ()+.5)<=0) {
                serverFailed=true;com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_QA_FAIL server-light/saved-array/spawn invariant");
            } else {serverChecked=true;log("server block-light bytes and spawn eligibility unchanged inside the visual field");}
        });
        return false;
    }
    private static void position(LivingEntity entity,double px,double py,double pz) { entity.setPos(px,py,pz);entity.setNoGravity(true); }
    private static double sy(LivingEntity entity) { return Math.rint((entity.getY()+entity.getEyeHeight()*.5)*8)/8; }
    private static void expect(double px,double py,double pz,double wanted,String description) {
        double got=ClientLighting.FIELD.light(px,py,pz);
        if(Math.abs(got-wanted)>1e-6)throw new AssertionError(description+" expected="+wanted+" actual="+got);
    }
    public static boolean tick(Minecraft mc) {
        if(++ticks<20)return false;ticks=0;
        if(step==0) {
            originalWorld=mc.level;x=Math.rint(mc.player.getX()*8)/8;y=Math.rint(mc.player.getY()*8)/8+32;z=Math.rint(mc.player.getZ()*8)/8;
            a=new RemotePlayer(mc.level,new GameProfile(UUID.randomUUID(),"LightingQaA"));
            b=new RemotePlayer(mc.level,new GameProfile(UUID.randomUUID(),"LightingQaB"));
            mob=new Zombie(mc.level);
            a.setId(2000001);b.setId(2000002);mob.setId(2000003);
            position(a,x+64,y,z);position(b,x-64,y,z);position(mob,x,y,z+64);
            a.setItemSlot(EquipmentSlot.MAINHAND,item("usefultoolsmod:ectoplasm_lantern"));
            b.setItemSlot(EquipmentSlot.OFFHAND,item("usefultoolsmod:hglow"));
            mob.setItemSlot(EquipmentSlot.FEET,item("usefultoolsmod:hglow_boots"));
            mc.level.addEntity(a);mc.level.addEntity(b);mc.level.addEntity(mob);
        } else if(step==1) {
            expect(a.getX(),sy(a),a.getZ(),12,"remote player main hand");
            expect(b.getX(),sy(b),b.getZ(),15,"second remote player off hand");
            expect(mob.getX(),sy(mob),mob.getZ(),15,"mob boots");log("two visible remote players and equipped mob");
            for(int bx=-1;bx<=1;bx++)for(int by=0;by<=3;by++)for(int bz=-1;bz<=1;bz++)
                mc.level.setBlock(new BlockPos((int)Math.floor(mob.getX())+bx,(int)Math.floor(mob.getY())+by,(int)Math.floor(mob.getZ())+bz),Blocks.WATER.defaultBlockState(),3);
        } else if(step==2) {
            if(!mob.isInWater())throw new AssertionError("water control did not immerse mob");
            expect(mob.getX(),sy(mob),mob.getZ(),15,"underwater worn source");log("underwater emission retained");
            oldX=a.getX();position(a,a.getX()+48,y,z);
        } else if(step==3) {
            expect(oldX,sy(a),z,0,"former moved source");expect(a.getX(),sy(a),z,12,"moved source");log("movement removes former illumination");
            mob.setItemSlot(EquipmentSlot.FEET,ItemStack.EMPTY);
        } else if(step==4) {
            expect(mob.getX(),sy(mob),mob.getZ(),0,"unequipped mob");log("mob unequip removes illumination");
            mc.level.removeEntity(a.getId(),Entity.RemovalReason.DISCARDED);mc.level.removeEntity(b.getId(),Entity.RemovalReason.DISCARDED);
            mc.level.removeEntity(mob.getId(),Entity.RemovalReason.DISCARDED);
            placed=new BlockPos((int)Math.floor(x)+16,(int)Math.floor(y)-24,(int)Math.floor(z));
            command(mc,"setblock "+placed.getX()+" "+placed.getY()+" "+placed.getZ()+" usefultoolsmod:hglow_block");
        } else if(step==5) {
            if(mc.level.getBlockState(placed).getLightEmission(mc.level,placed)!=15)return false;
            expect(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5,15,"network placed source");
            mc.getSingleplayerServer().execute(() -> {
                if(mc.getSingleplayerServer().overworld().getBrightness(net.minecraft.world.level.LightLayer.BLOCK,placed)!=15) {
                    serverFailed=true;com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_QA_FAIL placed actual block light !=15");
                } else log("placed Hardened Glowstone actual server block light 15");
            });
            log("network block placement indexed and removed entities cleared");
            command(mc,"execute in minecraft:the_nether run tp @a 0 100 0");
        } else if(step==6) {
            if(mc.level==originalWorld)return false;
            if(ClientLighting.FIELD.size()!=0)throw new AssertionError("dimension retained old sources");log("dimension change clears old field");
            command(mc,"execute in minecraft:overworld run tp @a "+x+" "+(y-32)+" "+z);
        } else if(step==7) {
            if(!mc.level.dimension().location().toString().equals("minecraft:overworld"))return false;
            if(mc.level.getBlockState(placed).getLightEmission(mc.level,placed)!=15)return false;
            if(ClientLighting.FIELD.light(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5)<15)return false;
            log("return reload indexes retained placed light");command(mc,"setblock "+placed.getX()+" "+placed.getY()+" "+placed.getZ()+" air");
        } else if(step==8) {
            if(!mc.level.getBlockState(placed).isAir())return false;
            expect(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5,0,"removed network block");log("network block removal clears field");
        } else if(step==9) {
            if(serverFailed)throw new AssertionError("Server acceptance fixture failed");
            command(mc,"setblock "+placed.getX()+" "+placed.getY()+" "+placed.getZ()+" usefultoolsmod:hglow_block");
        } else if(step==10) {
            if(mc.level.getBlockState(placed).getLightEmission(mc.level,placed)!=15||ClientLighting.FIELD.light(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5)<15)return false;
            return true;
        }
        step++;return false;
    }
    private static int reloadStep;
    private static int reloadTicks;
    private static volatile boolean reloadChecked;
    private static boolean reloadAnnounced;
    public static void reopened(Minecraft mc) {
        if(!reloadAnnounced){reloadAnnounced=true;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_REOPEN_READY");}
        if(reloadStep==0) {
            if(mc.level.getBlockState(placed).getLightEmission(mc.level,placed)!=15||ClientLighting.FIELD.light(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5)<15)return;
            log("saved world reopen restores placed emission and visual index");
            command(mc,"setblock "+placed.getX()+" "+placed.getY()+" "+placed.getZ()+" air");reloadStep=1;
        } else if(reloadStep==1&&mc.level.getBlockState(placed).isAir()&&ClientLighting.FIELD.light(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5)==0) {
            reloadStep=2;log("saved world removal clears visual index");
        } else if(reloadStep==2) {
            // Remove the fixture's real world-light source and allow native
            // relighting to settle before comparing the saved light arrays.
            if(++reloadTicks==80)mc.getSingleplayerServer().execute(() -> {
                var level=mc.getSingleplayerServer().overworld();
                boolean equal=java.util.Arrays.equals(initialLight,bytes(level));int block=level.getBrightness(net.minecraft.world.level.LightLayer.BLOCK,spawnProbe);boolean eligible=spawn(level);
                if(!equal||block!=0||initialSpawn!=eligible) {
                    serverFailed=true;com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_QA_FAIL saved-world light/spawn invariant bytes_equal="+equal+" block="+block+" spawn="+eligible+" difficulty="+level.getDifficulty());
                } else {reloadChecked=true;log("saved world block-light bytes and spawn eligibility unchanged");}
            });
            if(reloadChecked&&PerformanceLightingQa.tick(mc)) {
                reloadStep=3;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_REOPEN_PASS");
            }
        }
    }
}
