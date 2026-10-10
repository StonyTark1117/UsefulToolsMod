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
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.block.Blocks;
/** Runs only in a disposable acceptance world; never packaged with the mod. */
public final class AdvancedLightingQa {
    private static int step, ticks;
    private static ClientWorld originalWorld;
    private static LivingEntity a, b, mob;
    private static double x,y,z,oldX;
    private static BlockPos placed;
    private static volatile boolean serverReady, serverChecked, serverFailed;
    private static BlockPos spawnProbe;
    private static byte[] initialLight;
    private static boolean initialSpawn;
    private static boolean preparing;
    private static int preparationTicks;
    private static ItemStack item(String name) { return new ItemStack(Registries.ITEM.get(new net.minecraft.util.Identifier(name))); }
    private static void log(String message) { com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_ADVANCED_PASS " + message); }
    private static void command(MinecraftClient mc, String text) {
        var server=mc.getServer();
        server.execute(() -> server.getCommandManager().executeWithPrefix(server.getCommandSource(),text));
    }
    private static byte[] bytes(net.minecraft.server.world.ServerWorld level) {
        var data=level.getLightingProvider().get( net.minecraft.world.LightType.BLOCK).getLightSection(net.minecraft.util.math.ChunkSectionPos.from(spawnProbe));
        return data==null?new byte[2048]:data.asByteArray().clone();
    }
    private static boolean spawn(net.minecraft.server.world.ServerWorld level) {
        return net.minecraft.entity.mob.HostileEntity.canSpawnInDark(net.minecraft.entity.EntityType.ZOMBIE,level,net.minecraft.entity.SpawnReason.NATURAL,spawnProbe,net.minecraft.util.math.random.Random.create(123));
    }
    public static boolean prepare(MinecraftClient mc) {
        if (!preparing) {
            preparing=true;command(mc,"difficulty normal");
            spawnProbe=new BlockPos((int)Math.floor(mc.player.getX())+8,(int)Math.floor(mc.player.getY())-8,(int)Math.floor(mc.player.getZ()));
            mc.getServer().execute(() -> {
                var level=mc.getServer().getOverworld();
                // Protect the fixture from a random world's hostile mobs;
                // difficulty and native spawn eligibility remain unchanged.
                for (var player : mc.getServer().getPlayerManager().getPlayerList()) player.getAbilities().invulnerable=true;
                for(int bx=-2;bx<=2;bx++)for(int by=-1;by<=3;by++)for(int bz=-2;bz<=2;bz++) {
                    boolean wall=Math.abs(bx)==2||Math.abs(bz)==2||by==-1||by==3;
                    level.setBlockState(spawnProbe.add(bx,by,bz),(wall?Blocks.STONE:Blocks.AIR).getDefaultState(),3);
                }
            });
        }
        if(serverFailed)throw new AssertionError("Server acceptance fixture failed");
        if(serverReady)return true;
        if(++preparationTicks==80)mc.getServer().execute(() -> {
            var level=mc.getServer().getOverworld();
            initialLight=bytes(level);initialSpawn=spawn(level);
            if(level.getLightLevel(net.minecraft.world.LightType.BLOCK,spawnProbe)!=0||!initialSpawn) {
                serverFailed=true;com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_QA_FAIL expected dark spawn-eligible control room block="+level.getLightLevel(net.minecraft.world.LightType.BLOCK,spawnProbe)+" sky="+level.getLightLevel(net.minecraft.world.LightType.SKY,spawnProbe)+" eligible="+initialSpawn+" difficulty="+level.getDifficulty()+" floor="+level.getBlockState(spawnProbe.add(0,-1,0)) );
            } else serverReady=true;
        });
        return false;
    }
    public static boolean serverInvariant(MinecraftClient mc) {
        if(serverFailed)throw new AssertionError("Server-light/saved-array/spawn invariant failed");
        if(serverChecked)return true;
        mc.getServer().execute(() -> {
            if(serverChecked||serverFailed)return;
            var level=mc.getServer().getOverworld();
            if(!java.util.Arrays.equals(initialLight,bytes(level))||level.getLightLevel(net.minecraft.world.LightType.BLOCK,spawnProbe)!=0||initialSpawn!=spawn(level)
                    ||ClientLighting.FIELD.light(spawnProbe.getX()+.5,spawnProbe.getY()+.5,spawnProbe.getZ()+.5)<=0) {
                serverFailed=true;com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_QA_FAIL server-light/saved-array/spawn invariant");
            } else {serverChecked=true;log("server block-light bytes and spawn eligibility unchanged inside the visual field");}
        });
        return false;
    }
    private static void position(LivingEntity entity,double px,double py,double pz) { entity.setPosition(px,py,pz);entity.setNoGravity(true); }
    private static double sy(LivingEntity entity) { return Math.rint((entity.getY()+entity.getStandingEyeHeight()*.5)*8)/8; }
    private static void expect(double px,double py,double pz,double wanted,String description) {
        double got=ClientLighting.FIELD.light(px,py,pz);
        if(Math.abs(got-wanted)>1e-6)throw new AssertionError(description+" expected="+wanted+" actual="+got);
    }
    public static boolean tick(MinecraftClient mc) {
        if(++ticks<20)return false;ticks=0;
        if(step==0) {
            originalWorld=mc.world;x=Math.rint(mc.player.getX()*8)/8;y=Math.rint(mc.player.getY()*8)/8+32;z=Math.rint(mc.player.getZ()*8)/8;
            a=new OtherClientPlayerEntity(mc.world,new GameProfile(UUID.randomUUID(),"LightingQaA"));
            b=new OtherClientPlayerEntity(mc.world,new GameProfile(UUID.randomUUID(),"LightingQaB"));
            mob=new ZombieEntity(mc.world);
            a.setId(2000001);b.setId(2000002);mob.setId(2000003);
            position(a,x+64,y,z);position(b,x-64,y,z);position(mob,x,y,z+64);
            a.equipStack(EquipmentSlot.MAINHAND,item("usefultoolsmod:ectoplasm_lantern"));
            b.equipStack(EquipmentSlot.OFFHAND,item("usefultoolsmod:hglow"));
            mob.equipStack(EquipmentSlot.FEET,item("usefultoolsmod:hglow_boots"));
            mc.world.addEntity(a);mc.world.addEntity(b);mc.world.addEntity(mob);
        } else if(step==1) {
            expect(a.getX(),sy(a),a.getZ(),12,"remote player main hand");
            expect(b.getX(),sy(b),b.getZ(),15,"second remote player off hand");
            expect(mob.getX(),sy(mob),mob.getZ(),15,"mob boots");log("two visible remote players and equipped mob");
            for(int bx=-1;bx<=1;bx++)for(int by=0;by<=3;by++)for(int bz=-1;bz<=1;bz++)
                mc.world.setBlockState(new BlockPos((int)Math.floor(mob.getX())+bx,(int)Math.floor(mob.getY())+by,(int)Math.floor(mob.getZ())+bz),Blocks.WATER.getDefaultState(),3);
        } else if(step==2) {
            if(!mob.isTouchingWater())throw new AssertionError("water control did not immerse mob");
            expect(mob.getX(),sy(mob),mob.getZ(),15,"underwater worn source");log("underwater emission retained");
            oldX=a.getX();position(a,a.getX()+48,y,z);
        } else if(step==3) {
            expect(oldX,sy(a),z,0,"former moved source");expect(a.getX(),sy(a),z,12,"moved source");log("movement removes former illumination");
            mob.equipStack(EquipmentSlot.FEET,ItemStack.EMPTY);
        } else if(step==4) {
            expect(mob.getX(),sy(mob),mob.getZ(),0,"unequipped mob");log("mob unequip removes illumination");
            mc.world.removeEntity(a.getId(),Entity.RemovalReason.DISCARDED);mc.world.removeEntity(b.getId(),Entity.RemovalReason.DISCARDED);
            mc.world.removeEntity(mob.getId(),Entity.RemovalReason.DISCARDED);
            placed=new BlockPos((int)Math.floor(x)+16,(int)Math.floor(y)-24,(int)Math.floor(z));
            command(mc,"setblock "+placed.getX()+" "+placed.getY()+" "+placed.getZ()+" usefultoolsmod:hglow_block");
        } else if(step==5) {
            if(mc.world.getBlockState(placed).getLuminance()!=15)return false;
            expect(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5,15,"network placed source");
            mc.getServer().execute(() -> {
                if(mc.getServer().getOverworld().getLightLevel(net.minecraft.world.LightType.BLOCK,placed)!=15) {
                    serverFailed=true;com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_QA_FAIL placed actual block light !=15");
                } else log("placed Hardened Glowstone actual server block light 15");
            });
            log("network block placement indexed and removed entities cleared");
            command(mc,"execute in minecraft:the_nether run tp @a 0 100 0");
        } else if(step==6) {
            if(mc.world==originalWorld)return false;
            if(ClientLighting.FIELD.size()!=0)throw new AssertionError("dimension retained old sources");log("dimension change clears old field");
            command(mc,"execute in minecraft:overworld run tp @a "+x+" "+(y-32)+" "+z);
        } else if(step==7) {
            if(!mc.world.getRegistryKey().getValue().toString().equals("minecraft:overworld"))return false;
            if(mc.world.getBlockState(placed).getLuminance()!=15)return false;
            if(ClientLighting.FIELD.light(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5)<15)return false;
            log("return reload indexes retained placed light");command(mc,"setblock "+placed.getX()+" "+placed.getY()+" "+placed.getZ()+" air");
        } else if(step==8) {
            if(!mc.world.getBlockState(placed).isAir())return false;
            expect(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5,0,"removed network block");log("network block removal clears field");
        } else if(step==9) {
            if(serverFailed)throw new AssertionError("Server acceptance fixture failed");
            command(mc,"setblock "+placed.getX()+" "+placed.getY()+" "+placed.getZ()+" usefultoolsmod:hglow_block");
        } else if(step==10) {
            if(mc.world.getBlockState(placed).getLuminance()!=15||ClientLighting.FIELD.light(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5)<15)return false;
            return true;
        }
        step++;return false;
    }
    private static int reloadStep;
    private static int reloadTicks;
    private static volatile boolean reloadChecked;
    private static boolean reloadAnnounced;
    public static void reopened(MinecraftClient mc) {
        if(!reloadAnnounced){reloadAnnounced=true;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_REOPEN_READY");}
        if(reloadStep==0) {
            if(mc.world.getBlockState(placed).getLuminance()!=15||ClientLighting.FIELD.light(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5)<15)return;
            log("saved world reopen restores placed emission and visual index");
            command(mc,"setblock "+placed.getX()+" "+placed.getY()+" "+placed.getZ()+" air");reloadStep=1;
        } else if(reloadStep==1&&mc.world.getBlockState(placed).isAir()&&ClientLighting.FIELD.light(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5)==0) {
            reloadStep=2;log("saved world removal clears visual index");
        } else if(reloadStep==2) {
            // Remove the fixture's real world-light source and allow native
            // relighting to settle before comparing the saved light arrays.
            if(++reloadTicks==80)mc.getServer().execute(() -> {
                var level=mc.getServer().getOverworld();
                boolean equal=java.util.Arrays.equals(initialLight,bytes(level));int block=level.getLightLevel(net.minecraft.world.LightType.BLOCK,spawnProbe);boolean eligible=spawn(level);
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
