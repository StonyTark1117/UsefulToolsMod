package com.stonytark.usefultools.lighting;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

/** Native client adapter. All changes are confined to render-section scheduling and lightmap samples. */
public final class ClientLighting {
    public static final LightField FIELD = new LightField();
    private static ClientWorld world;
    private static final Map<Long, ChunkLights> chunks = new HashMap<>();
    private static final class ChunkLights {
        final WorldChunk chunk;
        final int x, z;
        final Map<String, LightField.Source> sources = new HashMap<>();
        ChunkLights(WorldChunk chunk, int x, int z) { this.chunk = chunk; this.x = x; this.z = z; }
    }
    private ClientLighting() {}
    private static long key(int x, int z) { return ((long)x << 32) ^ (z & 0xFFFFFFFFL); }
    private static String id(ItemStack stack) { return stack.isEmpty() ? null : Registries.ITEM.getId(stack.getItem()).toString(); }
    private static boolean hardened(net.minecraft.block.BlockState state) { return "usefultoolsmod:hglow_block".equals(Registries.BLOCK.getId(state.getBlock()).toString()); }
    private static LightField.Source placed(int x, int y, int z) { return new LightField.Source(x + .5, y + .5, z + .5, 15, 30); }
    private static String blockKey(int x, int y, int z) { return "b:" + x + ":" + y + ":" + z; }
    public static void blockChanged(BlockPos pos, net.minecraft.block.BlockState state) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc == null) return;
        if (world == null || world != mc.world) return;
        ChunkLights chunk = chunks.get(key(pos.getX() >> 4, pos.getZ() >> 4));
        if (chunk == null) return; // The initial palette scan will include this update.
        String source = blockKey(pos.getX(), pos.getY(), pos.getZ());
        if (hardened(state)) chunk.sources.put(source, placed(pos.getX(), pos.getY(), pos.getZ()));
        else chunk.sources.remove(source);
    }
    public static void chunkChanged(WorldChunk chunk) {
        if (chunk != null) chunks.remove(key(chunk.getPos().x, chunk.getPos().z));
    }
    public static void tick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc == null) return;
        if (world != mc.world) { FIELD.clear(); chunks.clear(); world = mc.world; }
        if (world == null || mc.player == null) { FIELD.drainDirty(); return; }
        // Never request or generate missing chunks. Replaced or unloaded chunks lose their sources immediately.
        for (Iterator<ChunkLights> it = chunks.values().iterator(); it.hasNext();) {
            ChunkLights old = it.next();
            if (world.getChunkManager().getChunk(old.x, old.z, ChunkStatus.FULL, false) != old.chunk) it.remove();
        }
        int cx = mc.player.getBlockPos().getX() >> 4, cz = mc.player.getBlockPos().getZ() >> 4;
        int distance = mc.options.getViewDistance().getValue() + 3;
        int budget = 8;
        for (int ring = 0; ring <= distance; ring++) for (int dx = -ring; dx <= ring; dx++) for (int dz = -ring; dz <= ring; dz++) {
            if (Math.max(Math.abs(dx), Math.abs(dz)) != ring) continue;
            int x = cx + dx, z = cz + dz;
            WorldChunk chunk = world.getChunkManager().getChunk(x, z, ChunkStatus.FULL, false);
            if (chunk == null || chunks.containsKey(key(x, z)) || budget == 0) continue;
            budget--;
            ChunkLights found = new ChunkLights(chunk, x, z);
            ChunkSection[] sections = chunk.getSectionArray();
            for (int i = 0; i < sections.length; i++) {
                ChunkSection section = sections[i];
                if (section == null || section.isEmpty() || !section.hasAny(ClientLighting::hardened)) continue;
                int baseY = world.sectionIndexToCoord(i) << 4;
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
        for (Entity entity : world.getEntities()) if (entity instanceof LivingEntity living && !entity.isRemoved()) collect(next, living);
        // Track local equipment explicitly throughout the initial entity packet transition.
        if (!mc.player.isRemoved()) collect(next, mc.player);
        FIELD.update(next);
        for (long section : FIELD.drainDirty()) mc.worldRenderer.scheduleBlockRender(LightField.sectionX(section), LightField.sectionY(section), LightField.sectionZ(section));
    }
    private static void collect(Map<String, LightField.Source> next, LivingEntity living) {
        int strongest = 0, radius = 0;
        for (EquipmentSlot slot : new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET }) {
            boolean worn = slot != EquipmentSlot.MAINHAND && slot != EquipmentSlot.OFFHAND;
            String itemId = id(living.getEquippedStack(slot));
            int candidate = SourcePolicy.level(itemId, worn), reach = SourcePolicy.radius(itemId, worn);
            if (candidate > strongest || candidate == strongest && reach > radius) { strongest = candidate; radius = reach; }
        }
        if (strongest > 0) next.put("e:" + living.getId(), new LightField.Source(
            Math.rint(living.getX() * 8) / 8, Math.rint((living.getY() + living.getStandingEyeHeight() * .5) * 8) / 8,
            Math.rint(living.getZ() * 8) / 8, strongest, radius));
    }
    public static int sample(int original, BlockPos pos) { return FIELD.packed(original, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5); }
}
