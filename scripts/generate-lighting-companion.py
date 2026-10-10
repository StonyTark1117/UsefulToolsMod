#!/usr/bin/env python3
"""Generate native companion adapters from the shared source policy; never touch gameplay."""
import json
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
PACKAGE='com.stonytark.usefultools.lighting'
def write(root,name,text):
 p=root/name;p.parent.mkdir(parents=True,exist_ok=True);p.write_text(text)
policy=json.loads((ROOT/'lighting/sources.json').read_text())
placed_row=next(row for row in policy['sources'] if row['placed'])
# This policy has two radial profiles. Reject a new profile rather than silently
# generating an adapter with the wrong reach; extend the generator first.
assert all(row['radius']==row['level'] or (row['level'],row['radius'])==(placed_row['level'],placed_row['radius']) for row in policy['sources'])
source='package '+PACKAGE+';\n\n/** Generated from lighting/sources.json. */\npublic final class SourcePolicy {\n    private SourcePolicy() {}\n    public static int level(String id, boolean worn) {\n        if ("minecraft:glowstone".equals(id)) return worn ? 0 : 15;\n        if (id == null || !id.startsWith("usefultoolsmod:")) return 0;\n        String name = id.substring(15);\n        switch (name) {\n'
for row in policy['sources']:
 source+='            case "'+row['id']+'": return '+str(row['level'])+(';' if row['worn'] else ' * (worn ? 0 : 1);')+'\n'
source+='            default: return 0;\n        }\n    }\n    public static int radius(String id, boolean worn) { int level = level(id, worn); return level == '+str(placed_row['level'])+' && id.startsWith("usefultoolsmod:") ? '+str(placed_row['radius'])+' : level; }\n}\n'
write(ROOT/'lighting/common/java',PACKAGE.replace('.','/')+'/SourcePolicy.java',source)
for v in ['1.20.1','1.20.2','1.21.1','26.1.2','26.2']:
 for loader in ['forge','fabric','neoforge']:
  root=ROOT/v/loader
  yarn=loader=='fabric' and not v.startswith('26.')
  new=v.startswith('26.')
  newest=v=='26.2'
  code='''package @PKG@;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import @MC@;
import @WORLD@;
import @ENTITY@;
import @LIVING@;
import @STACK@;
import @SLOT@;
import @POS@;
import @CHUNK@;
import @SECTION@;
import @STATUS@;
import @REG@;

/** Native client adapter. All changes are confined to render-section scheduling and lightmap samples. */
public final class ClientLighting {
    public static final LightField FIELD = new LightField();
    private static @World@ world;
    private static final Map<Long, ChunkLights> chunks = new HashMap<>();
    private static final class ChunkLights {
        final @Chunk@ chunk;
        final int x, z;
        final Map<String, LightField.Source> sources = new HashMap<>();
        ChunkLights(@Chunk@ chunk, int x, int z) { this.chunk = chunk; this.x = x; this.z = z; }
    }
    private ClientLighting() {}
    private static long key(int x, int z) { return ((long)x << 32) ^ (z & 0xFFFFFFFFL); }
    private static String id(@Stack@ stack) { return stack.@empty@() ? null : @Registry@.@itemId@(stack.@item@()).toString(); }
    private static boolean hardened(@State@ state) { return "usefultoolsmod:hglow_block".equals(@Registry@.@blockId@(state.@block@()).toString()); }
    private static LightField.Source placed(int x, int y, int z) { return new LightField.Source(x + .5, y + .5, z + .5, @placedLevel@, @placedRadius@); }
    private static String blockKey(int x, int y, int z) { return "b:" + x + ":" + y + ":" + z; }
    public static void blockChanged(@Pos@ pos, @State@ state) {
        @Minecraft@ mc = @Minecraft@.@instance@();
        if (mc == null) return;
        if (world == null || world != mc.@worldField@) return;
        ChunkLights chunk = chunks.get(key(pos.@x@() >> 4, pos.@z@() >> 4));
        if (chunk == null) return; // The initial palette scan will include this update.
        String source = blockKey(pos.@x@(), pos.@y@(), pos.@z@());
        if (hardened(state)) chunk.sources.put(source, placed(pos.@x@(), pos.@y@(), pos.@z@()));
        else chunk.sources.remove(source);
    }
    public static void chunkChanged(@Chunk@ chunk) {
        if (chunk != null) chunks.remove(key(chunk.@chunkPos@().@chunkX@, chunk.@chunkPos@().@chunkZ@));
    }
    public static void tick() {
        @Minecraft@ mc = @Minecraft@.@instance@();
        if (mc == null) return;
        if (world != mc.@worldField@) { FIELD.clear(); chunks.clear(); world = mc.@worldField@; }
        if (world == null || mc.player == null) { FIELD.drainDirty(); return; }
        // Never request or generate missing chunks. Replaced or unloaded chunks lose their sources immediately.
        for (Iterator<ChunkLights> it = chunks.values().iterator(); it.hasNext();) {
            ChunkLights old = it.next();
            if (world.@chunkManager@().getChunk(old.x, old.z, @Status@.FULL, false) != old.chunk) it.remove();
        }
        int cx = mc.player.@blockPos@().@x@() >> 4, cz = mc.player.@blockPos@().@z@() >> 4;
        int distance = mc.options.@distance@().@optionValue@() + 3;
        int budget = 8;
        for (int ring = 0; ring <= distance; ring++) for (int dx = -ring; dx <= ring; dx++) for (int dz = -ring; dz <= ring; dz++) {
            if (Math.max(Math.abs(dx), Math.abs(dz)) != ring) continue;
            int x = cx + dx, z = cz + dz;
            @Chunk@ chunk = world.@chunkManager@().getChunk(x, z, @Status@.FULL, false);
            if (chunk == null || chunks.containsKey(key(x, z)) || budget == 0) continue;
            budget--;
            ChunkLights found = new ChunkLights(chunk, x, z);
            @Section@[] sections = chunk.@sections@();
            for (int i = 0; i < sections.length; i++) {
                @Section@ section = sections[i];
                if (section == null || section.@air@() || !section.@maybe@(ClientLighting::hardened)) continue;
                int baseY = world.@sectionY@(i) << 4;
                for (int bx = 0; bx < 16; bx++) for (int by = 0; by < 16; by++) for (int bz = 0; bz < 16; bz++) {
                    if (hardened(section.getBlockState(bx, by, bz))) {
                        int px = (x << 4) + bx, py = baseY + by, pz = (z << 4) + bz;
                        found.sources.put(blockKey(px, py, pz), placed(px, py, pz));
                    }
                }
            }
            chunks.put(key(x, z), found);
        }
        Map<String, LightField.Source> next = new HashMap<>();
        for (ChunkLights chunk : chunks.values()) next.putAll(chunk.sources);
        for (@Entity@ entity : world.@entities@()) if (entity instanceof @Living@ living && !entity.@removed@()) collect(next, living);
        // Track local equipment explicitly throughout the initial entity packet transition.
        if (!mc.player.@removed@()) collect(next, mc.player);
        FIELD.update(next);
        for (long section : FIELD.drainDirty()) mc.@renderer@.@dirty@(LightField.sectionX(section), LightField.sectionY(section), LightField.sectionZ(section));
    }
    private static void collect(Map<String, LightField.Source> next, @Living@ living) {
        int strongest = 0, radius = 0;
        for (@Slot@ slot : new @Slot@[] { @Slot@.MAINHAND, @Slot@.OFFHAND, @Slot@.HEAD, @Slot@.CHEST, @Slot@.LEGS, @Slot@.FEET }) {
            boolean worn = slot != @Slot@.MAINHAND && slot != @Slot@.OFFHAND;
            String itemId = id(living.@equipment@(slot));
            int candidate = SourcePolicy.level(itemId, worn), reach = SourcePolicy.radius(itemId, worn);
            if (candidate > strongest || candidate == strongest && reach > radius) { strongest = candidate; radius = reach; }
        }
        if (strongest > 0) next.put("e:" + living.@entityId@(), new LightField.Source(
            Math.rint(living.getX() * 8) / 8, Math.rint((living.getY() + living.@eyeHeight@() * .5) * 8) / 8,
            Math.rint(living.getZ() * 8) / 8, strongest, radius));
    }
    public static int sample(int original, @Pos@ pos) { return FIELD.packed(original, pos.@x@() + .5, pos.@y@() + .5, pos.@z@() + .5); }
}
'''
  n={'PKG':PACKAGE,'MC':'net.minecraft.client.'+('MinecraftClient' if yarn else 'Minecraft'),'WORLD':'net.minecraft.client.'+('world.ClientWorld' if yarn else 'multiplayer.ClientLevel'),
   'ENTITY':'net.minecraft.'+('entity.Entity' if yarn else 'world.entity.Entity'),'LIVING':'net.minecraft.'+('entity.LivingEntity' if yarn else 'world.entity.LivingEntity'),
   'STACK':'net.minecraft.'+('item.ItemStack' if yarn else 'world.item.ItemStack'),'SLOT':'net.minecraft.'+('entity.EquipmentSlot' if yarn else 'world.entity.EquipmentSlot'),
   'POS':'net.minecraft.'+('util.math.BlockPos' if yarn else 'core.BlockPos'),'CHUNK':'net.minecraft.world.'+('chunk.WorldChunk' if yarn else 'level.chunk.LevelChunk'),
   'SECTION':'net.minecraft.world.'+('chunk.ChunkSection' if yarn else 'level.chunk.LevelChunkSection'),
   'STATUS':'net.minecraft.world.'+('chunk.ChunkStatus' if yarn else 'level.chunk.'+('status.' if v not in ['1.20.1','1.20.2'] else '')+'ChunkStatus'),
   'REG':'net.minecraft.'+('registry.Registries' if yarn else 'core.registries.BuiltInRegistries'),
   'Minecraft':'MinecraftClient' if yarn else 'Minecraft','World':'ClientWorld' if yarn else 'ClientLevel','Entity':'Entity','Living':'LivingEntity','Stack':'ItemStack','Slot':'EquipmentSlot','Pos':'BlockPos','Chunk':'WorldChunk' if yarn else 'LevelChunk','Section':'ChunkSection' if yarn else 'LevelChunkSection','Status':'ChunkStatus',
   'State':'net.minecraft.'+('block.BlockState' if yarn else 'world.level.block.state.BlockState'),'empty':'isEmpty','item':'getItem','block':'getBlock','Registry':'Registries' if yarn else 'BuiltInRegistries',
   'itemId':'ITEM.getId' if yarn else 'ITEM.getKey','blockId':'BLOCK.getId' if yarn else 'BLOCK.getKey','instance':'getInstance','worldField':'world' if yarn else 'level','x':'getX','y':'getY','z':'getZ',
   'chunkPos':'getPos','chunkX':'x()' if new else 'x','chunkZ':'z()' if new else 'z','chunkManager':'getChunkManager' if yarn else 'getChunkSource','blockPos':'getBlockPos' if yarn else 'blockPosition','distance':'getViewDistance' if yarn else 'renderDistance',
   'optionValue':'getValue' if yarn else 'get','eyeHeight':'getStandingEyeHeight' if yarn else 'getEyeHeight','sections':'getSectionArray' if yarn else 'getSections','air':'isEmpty' if yarn else 'hasOnlyAir','maybe':'hasAny' if yarn else 'maybeHas','sectionY':'sectionIndexToCoord' if yarn else 'getSectionYFromSectionIndex',
   'entities':'getEntities' if yarn else 'entitiesForRendering','removed':'isRemoved','equipment':'getEquippedStack' if yarn else 'getItemBySlot','entityId':'getId','renderer':'worldRenderer' if yarn else ('levelExtractor' if newest else 'levelRenderer'),'dirty':'scheduleBlockRender' if yarn else 'setSectionDirty'}
  if loader=='forge' or v=='1.20.1' and loader=='neoforge':
   n.update(REG='net.minecraftforge.registries.ForgeRegistries',Registry='ForgeRegistries',itemId='ITEMS.getKey',blockId='BLOCKS.getKey')
  n.update(placedLevel=str(placed_row['level']),placedRadius=str(placed_row['radius']))
  for k,val in n.items(): code=code.replace('@'+k+'@',val)
  java=root/'src/lighting/java'/PACKAGE.replace('.','/')
  write(java,'ClientLighting.java',code)
  write(java/'mixin','ClientTickMixin.java',f'''package {PACKAGE}.mixin;
import {n['MC']};
import {PACKAGE}.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin({n['Minecraft']}.class)
public abstract class ClientTickMixin {{
    @Inject(method = "tick", at = @At("TAIL"))
    private void usefulToolsLighting$tick(CallbackInfo ci) {{ ClientLighting.tick(); }}
}}
''')
  sampleClass='net.minecraft.client.render.WorldRenderer' if yarn else ('net.minecraft.util.LightCoordsUtil' if newest else 'net.minecraft.client.renderer.LevelRenderer')
  sampleName='getLightmapCoordinates' if yarn else ('getLightCoords' if new else 'getLightColor')
  getter='net.minecraft.world.BlockRenderView' if yarn else ('net.minecraft.world.level.BlockAndLightGetter' if new else 'net.minecraft.world.level.BlockAndTintGetter')
  write(java/'mixin','LightmapMixin.java',f'''package {PACKAGE}.mixin;
import {sampleClass};
import {getter};
import {n['POS']};
import {PACKAGE}.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin({sampleClass.rsplit('.',1)[1]}.class)
public abstract class LightmapMixin {{
    @Inject(method = "{sampleName}(L{getter.replace('.','/')};L{n['POS'].replace('.','/')};)I", at = @At("RETURN"), cancellable = true)
    private static void usefulToolsLighting$sample({getter.rsplit('.',1)[1]} view, BlockPos pos, CallbackInfoReturnable<Integer> cir) {{ cir.setReturnValue(ClientLighting.sample(cir.getReturnValueI(), pos)); }}
}}
''')
  # The state-aware overload is also used directly for chunk meshes. Hook it, too.
  p=java/'mixin/LightmapMixin.java';s=p.read_text();args=(((('LightCoordsUtil' if newest else 'LevelRenderer') + '.BrightnessGetter brightness, ') if new else '')+getter.rsplit('.',1)[1]+' view, '+n['State']+' state, BlockPos pos')
  descriptor=(('Lnet/minecraft/util/LightCoordsUtil$BrightnessGetter;' if newest else 'Lnet/minecraft/client/renderer/LevelRenderer$BrightnessGetter;') if new else '')+'L'+getter.replace('.','/')+';L'+n['State'].replace('.','/')+';L'+n['POS'].replace('.','/')+';'
  s=s[:-2]+f'''    @Inject(method = "{sampleName}({descriptor})I", at = @At("RETURN"), cancellable = true)
    private static void usefulToolsLighting$stateSample({args}, CallbackInfoReturnable<Integer> cir) {{ cir.setReturnValue(ClientLighting.sample(cir.getReturnValueI(), pos)); }}
}}
''';p.write_text(s)
  write(java/'mixin','EntityLightMixin.java',f'''package {PACKAGE}.mixin;
import {'net.minecraft.client.render.entity.EntityRenderer' if yarn else 'net.minecraft.client.renderer.entity.EntityRenderer'};
import {n['ENTITY']};
import {PACKAGE}.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(EntityRenderer.class)
public abstract class EntityLightMixin {{
    @Inject(method = "{'getLight' if yarn else 'getPackedLightCoords'}", at = @At("RETURN"), cancellable = true)
    private void usefulToolsLighting$entity(Entity entity, float partialTicks, CallbackInfoReturnable<Integer> cir) {{ cir.setReturnValue(ClientLighting.FIELD.packed(cir.getReturnValueI(), entity.getX(), entity.getY() + entity.{'getStandingEyeHeight' if yarn else 'getEyeHeight'}(), entity.getZ())); }}
}}
''')
  write(java,'CompanionMixinPlugin.java',f'''package {PACKAGE};
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/** Allows an external, unpackaged acceptance fixture to attach to native client ticks. */
public final class CompanionMixinPlugin implements IMixinConfigPlugin {{
    public void onLoad(String mixinPackage) {{}}
    public String getRefMapperConfig() {{ return null; }}
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {{ return true; }}
    public void acceptTargets(Set<String> mine, Set<String> other) {{}}
    public List<String> getMixins() {{ return Boolean.getBoolean("usefulToolsLightingQa") ? Collections.singletonList("LightingQaMixin") : Collections.emptyList(); }}
    public void preApply(String target, ClassNode node, String name, IMixinInfo info) {{}}
    public void postApply(String target, ClassNode node, String name, IMixinInfo info) {{}}
}}
''')
  cache='net.minecraft.client.world.ClientChunkManager' if yarn else 'net.minecraft.client.multiplayer.ClientChunkCache'
  write(java/'mixin','ChunkDataMixin.java',f'''package {PACKAGE}.mixin;
import {cache};
import {n['CHUNK']};
import {PACKAGE}.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin({cache.rsplit('.',1)[1]}.class)
public abstract class ChunkDataMixin {{
    @Inject(method="{'loadChunkFromPacket' if yarn else 'replaceWithPacketData'}", at=@At("RETURN"))
    private void usefulToolsLighting$chunk(CallbackInfoReturnable<{n['Chunk']}> cir) {{ ClientLighting.chunkChanged(cir.getReturnValue()); }}
}}
''')
  # Client world block update path: no world brightness methods are modified.
  write(java/'mixin','BlockUpdateMixin.java',f'''package {PACKAGE}.mixin;
import {n['WORLD']};
import {n['POS']};
import {PACKAGE}.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin({n['World']}.class)
public abstract class BlockUpdateMixin {{
    @Inject(method = "{'updateListeners' if yarn else 'setBlocksDirty'}", at = @At("TAIL"))
    private void usefulToolsLighting$changed(BlockPos pos, {n['State']} oldState, {n['State']} newState{', int flags' if yarn else ''}, CallbackInfo ci) {{ ClientLighting.blockChanged(pos, newState); }}
}}
''')
  sodium_targets=[('SodiumLegacy','me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess'),('Sodium','net.caffeinemc.mods.sodium.client.model.light.data.LightDataAccess')]
  if v=='1.21.1' and loader=='forge':sodium_targets.append(('Embeddium','org.embeddedt.embeddium.impl.model.light.data.LightDataAccess'))
  for prefix,target in sodium_targets:
   write(java/'mixin',prefix+'Mixin.java',f'''package {PACKAGE}.mixin;
import {PACKAGE}.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Pseudo
@Mixin(targets = "{target}", remap = false)
public abstract class {prefix}Mixin {{
    @Inject(method = "compute", at = @At("RETURN"), cancellable = true, remap = false)
    private void usefulToolsLighting$sample(int x, int y, int z, CallbackInfoReturnable<Integer> cir) {{ cir.setReturnValue(ClientLighting.FIELD.sodium(cir.getReturnValueI(), x, y, z)); }}
}}
''')
  res=root/'src/lighting/resources'
  pack={"description":"Useful Tools Lighting resources"}
  if new:pack.update(min_format=88 if newest else 84,max_format=88 if newest else 84)
  else:pack["pack_format"]={"1.20.1":15,"1.20.2":18,"1.21.1":34}[v]
  write(res,"pack.mcmeta",json.dumps({"pack":pack},indent=2)+"\n")
  if v=='1.21.1' and loader=='forge':
   templates=ROOT/'lighting/compat/forgeoculus-1.21.1'
   write(java,'IrisPbrTrackerBridge.java',(templates/'IrisPbrTrackerBridge.java').read_text())
   write(java/'mixin','IrisPbrTextureMixin.java',(templates/'IrisPbrTextureMixin.java').read_text())
  write(res,'usefultools_lighting.mixins.json',json.dumps({'required':True,'minVersion':'0.8','package':PACKAGE+'.mixin','compatibilityLevel':'JAVA_17' if v.startswith('1.20.') else 'JAVA_21','plugin':PACKAGE+'.CompanionMixinPlugin','client':['ClientTickMixin','LightmapMixin','BlockUpdateMixin','EntityLightMixin','ChunkDataMixin','SodiumLegacyMixin','SodiumMixin']+(['EmbeddiumMixin','IrisPbrTextureMixin'] if v=='1.21.1' and loader=='forge' else []),'injectors':{'defaultRequire':1}},indent=2)+'\n')
  if loader=='forge' and v.startswith('1.20.') or loader=='neoforge' and v=='1.20.1':
   p=res/'usefultools_lighting.mixins.json'; config=json.loads(p.read_text()); config['refmap']='usefultools_lighting.refmap.json'; p.write_text(json.dumps(config,indent=2)+'\n')
  if loader=='fabric':
   write(res,'fabric.mod.json',json.dumps({'schemaVersion':1,'id':'usefultools_lighting','version':'${version}','name':'Useful Tools Lighting','description':'Optional render-only held, worn, and extended Hardened Glowstone lighting.','environment':'client','license':'CC0-1.0','mixins':['usefultools_lighting.mixins.json'],'depends':{'fabricloader':'>=0.16.0','minecraft':v,'usefultoolsmod':'*'}},indent=2)+'\n')
  else:
   forgePackage='net.minecraftforge.fml.common.Mod' if loader=='forge' or (loader=='neoforge' and v=='1.20.1') else 'net.neoforged.fml.common.Mod'
   write(java,'LightingMod.java',f'package {PACKAGE};\nimport {forgePackage};\n/** Empty on dedicated servers. Rendering is loaded only through client Mixins. */\n@Mod("usefultools_lighting")\npublic final class LightingMod {{}}\n')
   write(res,'META-INF/'+('mods.toml' if loader=='forge' or v in ['1.20.1','1.20.2'] else 'neoforge.mods.toml'),'''modLoader="javafml"
loaderVersion="[1,)"
license="CC0-1.0"
[[mods]]
modId="usefultools_lighting"
version="${version}"
displayName="Useful Tools Lighting"
description="Render-only held, worn, and extended Hardened Glowstone lighting."
[[mixins]]
config="usefultools_lighting.mixins.json"
''')
  if loader=='forge' or v=='1.20.1' and loader=='neoforge':
   meta=res/'META-INF/mods.toml'; text=meta.read_text().replace('displayName="Useful Tools Lighting"','displayName="Useful Tools Lighting"\ndisplayTest="IGNORE_SERVER_VERSION"');meta.write_text(text)
print('Generated shared policy and all 15 modern adapters. Classic adapter is maintained against native RFG APIs.')

# Keep optional API metadata synchronized whenever native sources are regenerated.
import runpy
runpy.run_path(str(ROOT/"scripts/generate-lighting-lamb.py"), run_name="__main__")
