#!/usr/bin/env python3
"""Generate isolated, unpackaged entity and world-lifecycle acceptance fixtures."""
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1];pkg='com.stonytark.usefultools.lighting'
for v in ['1.20.1','1.20.2','1.21.1','26.1.2','26.2']:
 for loader in ['forge','fabric','neoforge']:
  yarn=loader=='fabric' and not v.startswith('26.');new=v.startswith('26.')
  mc='MinecraftClient' if yarn else 'Minecraft';world='ClientWorld' if yarn else 'ClientLevel';living='LivingEntity'
  code='''package @PKG@;
@IMPORTS@
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import @REMOTE@;
import @ZOMBIE@;
import @BLOCKS@;
/** Runs only in a disposable acceptance world; never packaged with the mod. */
public final class AdvancedLightingQa {
    private static int step, ticks;
    private static @World@ originalWorld;
    private static @Living@ a, b, mob;
    private static double x,y,z,oldX;
    private static BlockPos placed;
    private static volatile boolean serverReady, serverChecked, serverFailed;
    private static BlockPos spawnProbe;
    private static byte[] initialLight;
    private static boolean initialSpawn;
    private static boolean preparing;
    private static int preparationTicks;
    private static ItemStack item(String name) { return new ItemStack(@ITEM_REG@.@LOOKUP@(@ID@(name))); }
    private static void log(String message) { com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_ADVANCED_PASS " + message); }
    private static void command(@Minecraft@ mc, String text) {
        var server=mc.@SERVER@();
        server.execute(() -> server.@COMMANDS@().@EXECUTE@(server.@SOURCE@(),text));
    }
    private static byte[] bytes(@ServerWorld@ level) {
        var data=level.@ENGINE@().@LAYER@( @LIGHT@.BLOCK).@DATA@(@SECTION_POS@.@FROM@(spawnProbe));
        return data==null?new byte[2048]:data.@BYTES@().clone();
    }
    private static boolean spawn(@ServerWorld@ level) {
        return @MONSTER@.@SPAWN@(@ENTITYTYPE@.ZOMBIE,level,@REASON@.NATURAL,spawnProbe,@RANDOM@.@RANDOM_CREATE@(123));
    }
    public static boolean prepare(@Minecraft@ mc) {
        if (!preparing) {
            preparing=true;command(mc,"difficulty normal");
            spawnProbe=new BlockPos((int)Math.floor(mc.player.getX())+8,(int)Math.floor(mc.player.getY())-8,(int)Math.floor(mc.player.getZ()));
            mc.@SERVER@().execute(() -> {
                var level=mc.@SERVER@().@OVERWORLD@();
                // Protect the fixture from a random world's hostile mobs;
                // difficulty and native spawn eligibility remain unchanged.
                for (var player : mc.@SERVER@().@PLAYER_LIST@) player.getAbilities().invulnerable=true;
                for(int bx=-2;bx<=2;bx++)for(int by=-1;by<=3;by++)for(int bz=-2;bz<=2;bz++) {
                    boolean wall=Math.abs(bx)==2||Math.abs(bz)==2||by==-1||by==3;
                    level.@SET_BLOCK@(spawnProbe.@OFFSET@(bx,by,bz),(wall?Blocks.STONE:Blocks.AIR).@DEFAULT_STATE@(),3);
                }
            });
        }
        if(serverFailed)throw new AssertionError("Server acceptance fixture failed");
        if(serverReady)return true;
        if(++preparationTicks==80)mc.@SERVER@().execute(() -> {
            var level=mc.@SERVER@().@OVERWORLD@();
            initialLight=bytes(level);initialSpawn=spawn(level);
            if(level.@BRIGHTNESS@(@LIGHT@.BLOCK,spawnProbe)!=0||!initialSpawn) {
                serverFailed=true;com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_QA_FAIL expected dark spawn-eligible control room block="+level.@BRIGHTNESS@(@LIGHT@.BLOCK,spawnProbe)+" sky="+level.@BRIGHTNESS@(@LIGHT@.SKY,spawnProbe)+" eligible="+initialSpawn+" difficulty="+level.getDifficulty()+" floor="+level.getBlockState(spawnProbe.@OFFSET@(0,-1,0)) );
            } else serverReady=true;
        });
        return false;
    }
    public static boolean serverInvariant(@Minecraft@ mc) {
        if(serverFailed)throw new AssertionError("Server-light/saved-array/spawn invariant failed");
        if(serverChecked)return true;
        mc.@SERVER@().execute(() -> {
            if(serverChecked||serverFailed)return;
            var level=mc.@SERVER@().@OVERWORLD@();
            if(!java.util.Arrays.equals(initialLight,bytes(level))||level.@BRIGHTNESS@(@LIGHT@.BLOCK,spawnProbe)!=0||initialSpawn!=spawn(level)
                    ||ClientLighting.FIELD.light(spawnProbe.getX()+.5,spawnProbe.getY()+.5,spawnProbe.getZ()+.5)<=0) {
                serverFailed=true;com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_QA_FAIL server-light/saved-array/spawn invariant");
            } else {serverChecked=true;log("server block-light bytes and spawn eligibility unchanged inside the visual field");}
        });
        return false;
    }
    private static void position(@Living@ entity,double px,double py,double pz) { entity.@POSITION@(px,py,pz);entity.setNoGravity(true); }
    private static double sy(@Living@ entity) { return Math.rint((entity.getY()+entity.@EYE@()*.5)*8)/8; }
    private static void expect(double px,double py,double pz,double wanted,String description) {
        double got=ClientLighting.FIELD.light(px,py,pz);
        if(Math.abs(got-wanted)>1e-6)throw new AssertionError(description+" expected="+wanted+" actual="+got);
    }
    public static boolean tick(@Minecraft@ mc) {
        if(++ticks<20)return false;ticks=0;
        if(step==0) {
            originalWorld=mc.@WORLD_FIELD@;x=Math.rint(mc.player.getX()*8)/8;y=Math.rint(mc.player.getY()*8)/8+32;z=Math.rint(mc.player.getZ()*8)/8;
            a=new @Remote@(mc.@WORLD_FIELD@,new GameProfile(UUID.randomUUID(),"LightingQaA"));
            b=new @Remote@(mc.@WORLD_FIELD@,new GameProfile(UUID.randomUUID(),"LightingQaB"));
            mob=new @Zombie@(mc.@WORLD_FIELD@);
            a.setId(2000001);b.setId(2000002);mob.setId(2000003);
            position(a,x+64,y,z);position(b,x-64,y,z);position(mob,x,y,z+64);
            a.@EQUIP@(EquipmentSlot.MAINHAND,item("usefultoolsmod:ectoplasm_lantern"));
            b.@EQUIP@(EquipmentSlot.OFFHAND,item("usefultoolsmod:hglow"));
            mob.@EQUIP@(EquipmentSlot.FEET,item("usefultoolsmod:hglow_boots"));
            mc.@WORLD_FIELD@.@ADD_A@;mc.@WORLD_FIELD@.@ADD_B@;mc.@WORLD_FIELD@.@ADD_MOB@;
        } else if(step==1) {
            expect(a.getX(),sy(a),a.getZ(),12,"remote player main hand");
            expect(b.getX(),sy(b),b.getZ(),15,"second remote player off hand");
            expect(mob.getX(),sy(mob),mob.getZ(),15,"mob boots");log("two visible remote players and equipped mob");
            for(int bx=-1;bx<=1;bx++)for(int by=0;by<=3;by++)for(int bz=-1;bz<=1;bz++)
                mc.@WORLD_FIELD@.@SET_BLOCK@(new BlockPos((int)Math.floor(mob.getX())+bx,(int)Math.floor(mob.getY())+by,(int)Math.floor(mob.getZ())+bz),Blocks.WATER.@DEFAULT_STATE@(),3);
        } else if(step==2) {
            if(!mob.@WATER@())throw new AssertionError("water control did not immerse mob");
            expect(mob.getX(),sy(mob),mob.getZ(),15,"underwater worn source");log("underwater emission retained");
            oldX=a.getX();position(a,a.getX()+48,y,z);
        } else if(step==3) {
            expect(oldX,sy(a),z,0,"former moved source");expect(a.getX(),sy(a),z,12,"moved source");log("movement removes former illumination");
            mob.@EQUIP@(EquipmentSlot.FEET,ItemStack.EMPTY);
        } else if(step==4) {
            expect(mob.getX(),sy(mob),mob.getZ(),0,"unequipped mob");log("mob unequip removes illumination");
            mc.@WORLD_FIELD@.removeEntity(a.getId(),Entity.RemovalReason.DISCARDED);mc.@WORLD_FIELD@.removeEntity(b.getId(),Entity.RemovalReason.DISCARDED);
            mc.@WORLD_FIELD@.removeEntity(mob.getId(),Entity.RemovalReason.DISCARDED);
            placed=new BlockPos((int)Math.floor(x)+16,(int)Math.floor(y)-24,(int)Math.floor(z));
            command(mc,"setblock "+placed.getX()+" "+placed.getY()+" "+placed.getZ()+" usefultoolsmod:hglow_block");
        } else if(step==5) {
            if(mc.@WORLD_FIELD@.getBlockState(placed).@EMISSION@(@EMISSION_ARGS@)!=15)return false;
            expect(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5,15,"network placed source");
            mc.@SERVER@().execute(() -> {
                if(mc.@SERVER@().@OVERWORLD@().@BRIGHTNESS@(@LIGHT@.BLOCK,placed)!=15) {
                    serverFailed=true;com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_QA_FAIL placed actual block light !=15");
                } else log("placed Hardened Glowstone actual server block light 15");
            });
            log("network block placement indexed and removed entities cleared");
            command(mc,"execute in minecraft:the_nether run tp @a 0 100 0");
        } else if(step==6) {
            if(mc.@WORLD_FIELD@==originalWorld)return false;
            if(ClientLighting.FIELD.size()!=0)throw new AssertionError("dimension retained old sources");log("dimension change clears old field");
            command(mc,"execute in minecraft:overworld run tp @a "+x+" "+(y-32)+" "+z);
        } else if(step==7) {
            if(!mc.@WORLD_FIELD@.@DIMENSION@.toString().equals("minecraft:overworld"))return false;
            if(mc.@WORLD_FIELD@.getBlockState(placed).@EMISSION@(@EMISSION_ARGS@)!=15)return false;
            if(ClientLighting.FIELD.light(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5)<15)return false;
            log("return reload indexes retained placed light");command(mc,"setblock "+placed.getX()+" "+placed.getY()+" "+placed.getZ()+" air");
        } else if(step==8) {
            if(!mc.@WORLD_FIELD@.getBlockState(placed).isAir())return false;
            expect(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5,0,"removed network block");log("network block removal clears field");
        } else if(step==9) {
            if(serverFailed)throw new AssertionError("Server acceptance fixture failed");
            command(mc,"setblock "+placed.getX()+" "+placed.getY()+" "+placed.getZ()+" usefultoolsmod:hglow_block");
        } else if(step==10) {
            if(mc.@WORLD_FIELD@.getBlockState(placed).@EMISSION@(@EMISSION_ARGS@)!=15||ClientLighting.FIELD.light(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5)<15)return false;
            return true;
        }
        step++;return false;
    }
    private static int reloadStep;
    private static int reloadTicks;
    private static volatile boolean reloadChecked;
    private static boolean reloadAnnounced;
    public static void reopened(@Minecraft@ mc) {
        if(!reloadAnnounced){reloadAnnounced=true;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_REOPEN_READY");}
        if(reloadStep==0) {
            if(mc.@WORLD_FIELD@.getBlockState(placed).@EMISSION@(@EMISSION_ARGS@)!=15||ClientLighting.FIELD.light(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5)<15)return;
            log("saved world reopen restores placed emission and visual index");
            command(mc,"setblock "+placed.getX()+" "+placed.getY()+" "+placed.getZ()+" air");reloadStep=1;
        } else if(reloadStep==1&&mc.@WORLD_FIELD@.getBlockState(placed).isAir()&&ClientLighting.FIELD.light(placed.getX()+.5,placed.getY()+.5,placed.getZ()+.5)==0) {
            reloadStep=2;log("saved world removal clears visual index");
        } else if(reloadStep==2) {
            // Remove the fixture's real world-light source and allow native
            // relighting to settle before comparing the saved light arrays.
            if(++reloadTicks==80)mc.@SERVER@().execute(() -> {
                var level=mc.@SERVER@().@OVERWORLD@();
                boolean equal=java.util.Arrays.equals(initialLight,bytes(level));int block=level.@BRIGHTNESS@(@LIGHT@.BLOCK,spawnProbe);boolean eligible=spawn(level);
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
'''
  source=(ROOT/v/loader/'src/lighting/java'/pkg.replace('.','/')/'ClientLighting.java').read_text();imports='\n'.join(l for l in source.splitlines() if l.startswith('import net.minecraft'))
  itemreg='Registries.ITEM' if yarn else ('ForgeRegistries.ITEMS' if loader=='forge' or v=='1.20.1' and loader=='neoforge' else 'BuiltInRegistries.ITEM')
  ident='net.minecraft.util.Identifier.of' if yarn and v=='1.21.1' else ('new net.minecraft.util.Identifier' if yarn else ('net.minecraft.resources.Identifier.parse' if new else ('net.minecraft.resources.ResourceLocation.parse' if v=='1.21.1' else 'new net.minecraft.resources.ResourceLocation')))
  remote='net.minecraft.client.'+('network.OtherClientPlayerEntity' if yarn else 'player.RemotePlayer')
  zombie='net.minecraft.'+('entity.mob.ZombieEntity' if yarn else 'world.entity.monster.'+('zombie.' if new else '')+'Zombie')
  n=dict(WATER='isTouchingWater' if yarn else 'isInWater',PKG=pkg,IMPORTS=imports,Minecraft=mc,World=world,Living=living,REMOTE=remote,ZOMBIE=zombie,Remote=remote.rsplit('.',1)[1],Zombie=zombie.rsplit('.',1)[1],BLOCKS='net.minecraft.'+('block.Blocks' if yarn else 'world.level.block.Blocks'),ITEM_REG=itemreg,LOOKUP='getValue' if new or itemreg.startswith('Forge') else 'get',ID=ident,SERVER='getServer' if yarn else 'getSingleplayerServer',COMMANDS='getCommandManager' if yarn else 'getCommands',EXECUTE='executeWithPrefix' if yarn else 'performPrefixedCommand',SOURCE='getCommandSource' if yarn else 'createCommandSourceStack',ServerWorld='net.minecraft.server.'+('world.ServerWorld' if yarn else 'level.ServerLevel'),ENGINE='getLightingProvider' if yarn else 'getLightEngine',LAYER='get' if yarn else 'getLayerListener',LIGHT='net.minecraft.world.LightType' if yarn else 'net.minecraft.world.level.LightLayer',DATA='getLightSection' if yarn else 'getDataLayerData',SECTION_POS='net.minecraft.'+('util.math.ChunkSectionPos' if yarn else 'core.SectionPos'),FROM='from' if yarn else 'of',BYTES='asByteArray' if yarn else 'getData',MONSTER='net.minecraft.'+('entity.mob.HostileEntity' if yarn else 'world.entity.monster.Monster'),SPAWN='canSpawnInDark' if yarn else 'checkMonsterSpawnRules',ENTITYTYPE='net.minecraft.'+('entity.EntityType' if yarn else 'world.entity.'+('EntityTypes' if v=='26.2' else 'EntityType')),REASON='net.minecraft.entity.SpawnReason' if yarn else 'net.minecraft.world.entity.'+('EntitySpawnReason' if new else 'MobSpawnType'),RANDOM='net.minecraft.util.math.random.Random' if yarn else 'net.minecraft.util.RandomSource',RANDOM_CREATE='create',OVERWORLD='getOverworld' if yarn else 'overworld',SET_BLOCK='setBlockState' if yarn else 'setBlock',OFFSET='add' if yarn else 'offset',DEFAULT_STATE='getDefaultState' if yarn else 'defaultBlockState',BRIGHTNESS='getLightLevel' if yarn else 'getBrightness',POSITION='setPosition' if yarn else 'setPos',EYE='getStandingEyeHeight' if yarn else 'getEyeHeight',EQUIP='equipStack' if yarn else 'setItemSlot',WORLD_FIELD='world' if yarn else 'level',EMISSION='getLuminance' if yarn else 'getLightEmission',DIMENSION='getRegistryKey().getValue()' if yarn else 'dimension().'+('identifier()' if new else 'location()'))
  for ent,id in [('A','a'),('B','b'),('MOB','mob')]:n['ADD_'+ent]='add'+('Player' if ent!='MOB' else 'Entity')+'('+id+'.getId(),'+id+')' if yarn and v=='1.20.1' else 'addEntity('+id+')'
  if not yarn and v=='1.20.1':
   n['ADD_A']='addPlayer(a.getId(),(RemotePlayer)a)';n['ADD_B']='addPlayer(b.getId(),(RemotePlayer)b)';n['ADD_MOB']='putNonPlayerEntity(mob.getId(),mob)'
  elif yarn and v=='1.20.1':
   n['ADD_A']='addPlayer(a.getId(),(OtherClientPlayerEntity)a)';n['ADD_B']='addPlayer(b.getId(),(OtherClientPlayerEntity)b)'
  n['EMISSION_ARGS']='mc.level,placed' if not yarn and not new else ''
  n['PLAYER_LIST']='getPlayerManager().getPlayerList()' if yarn else 'getPlayerList().getPlayers()'
  for k,val in n.items():code=code.replace('@'+k+'@',val)
  path=ROOT/'tests/dynamic-lighting/targets'/f'{v}-{loader}'/pkg.replace('.','/')/'AdvancedLightingQa.java';path.parent.mkdir(parents=True,exist_ok=True);path.write_text(code)
  perf='''package @PKG@;
@IMPORTS@
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import @REMOTE@;
/** Paired native adapter timing and client FPS observations; never distributed. */
public final class PerformanceLightingQa {
    private static int phase,ticks,samples,fpsSamples,fpsTotal;
    private static long cpu;
    private static @Living@ a;
    private static double x,y,z;
    private static final String[] NAMES={"baseline","ordinary_radius_15","hardened_radius_30","hardened_moving_radius_30"};
    private static ItemStack item(String name){return new ItemStack(@ITEM_REG@.@LOOKUP@(@ID@(name)));}
    public static boolean tick(@Minecraft@ mc){
        if(a==null){
            mc.options.@FPS_LIMIT@().setValue(260);mc.options.@VSYNC@().setValue(false);
            a=new @Remote@(mc.@WORLD_FIELD@,new GameProfile(UUID.randomUUID(),"LightingPerf"));a.setId(2000100);a.setNoGravity(true);
            x=mc.player.getX()+8;y=mc.player.getY();z=mc.player.getZ();a.@POSITION@(x,y,z);mc.@WORLD_FIELD@.@ADD_A@;
        }
        if(ticks==0)a.@EQUIP@(EquipmentSlot.MAINHAND,phase==0?ItemStack.EMPTY:item(phase==1?"minecraft:glowstone":"usefultoolsmod:hglow"));
        if(phase==3)a.@POSITION@(x+Math.sin(ticks*.13)*4,y,z);
        long start=System.nanoTime();ClientLighting.tick();long elapsed=System.nanoTime()-start;
        if(ticks>=40){cpu+=elapsed;samples++;if(ticks%20==0){fpsTotal+=mc.@FPS@();fpsSamples++;}}
        if(++ticks<140)return false;
        com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_PERF phase="+NAMES[phase]+" adapter_us="+(cpu/1000.0/samples)+" mean_fps="+(fpsTotal/(double)fpsSamples)+" tick_samples="+samples+" fps_samples="+fpsSamples+" fps_cap=260 vsync=false");
        ticks=samples=fpsSamples=fpsTotal=0;cpu=0;
        if(++phase<4)return false;
        mc.@WORLD_FIELD@.removeEntity(a.getId(),Entity.RemovalReason.DISCARDED);return true;
    }
}
'''
  pn={**n,'FPS_LIMIT':'getMaxFps' if yarn else 'framerateLimit','VSYNC':'getEnableVsync' if yarn else 'enableVsync','FPS':'getCurrentFps' if yarn else 'getFps'}
  for k,val in pn.items():perf=perf.replace('@'+k+'@',val)
  if not yarn:perf=perf.replace('.setValue(','.set(')
  path.with_name('PerformanceLightingQa.java').write_text(perf)
print('Generated native advanced acceptance fixtures for all 15 modern targets.')
