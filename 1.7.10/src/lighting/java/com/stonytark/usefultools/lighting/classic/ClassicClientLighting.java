package com.stonytark.usefultools.lighting.classic;
import com.stonytark.usefultools.lighting.LightField;
import com.stonytark.usefultools.lighting.SourcePolicy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public final class ClassicClientLighting {
    public static final LightField FIELD = new LightField();
    private static WorldClient world;
    private static final Map<Long, ChunkLights> chunks = new HashMap<Long, ChunkLights>();
    private static final class ChunkLights {
        final Chunk chunk;
        final Map<String, LightField.Source> sources = new HashMap<String, LightField.Source>();
        ChunkLights(Chunk chunk) { this.chunk = chunk; }
    }
    public static void initialize() {
        ClassicClientLighting listener = new ClassicClientLighting();
        FMLCommonHandler.instance().bus().register(listener);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(listener);
    }
    @SubscribeEvent
    public void unload(net.minecraftforge.event.world.WorldEvent.Unload event) {
        if (event.world == world) { FIELD.clear(); chunks.clear(); world = null; }
    }
    private static long key(int x, int z) { return ((long)x << 32) ^ (z & 0xFFFFFFFFL); }
    private static String blockKey(int x, int y, int z) { return "b:" + x + ":" + y + ":" + z; }
    private static boolean hardened(Block block) { return "usefultoolsmod:hglow_block".equals(Block.blockRegistry.getNameForObject(block)); }
    private static int level(ItemStack stack, boolean worn) { return stack == null ? 0 : SourcePolicy.level(Item.itemRegistry.getNameForObject(stack.getItem()), worn); }
    public static void blockChanged(int x, int y, int z) {
        if (world == null) return;
        ChunkLights chunk = chunks.get(key(x >> 4, z >> 4));
        if (chunk == null) return;
        String id = blockKey(x, y, z);
        if (hardened(world.getBlock(x, y, z))) chunk.sources.put(id, new LightField.Source(x + .5, y + .5, z + .5, 15, 30));
        else chunk.sources.remove(id);
    }
    public static void chunkChanged(Object value) {
        Chunk chunk = (Chunk)value;
        if (chunk.worldObj.isRemote) chunks.remove(key(chunk.xPosition, chunk.zPosition));
    }
    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getMinecraft();
        if (world != mc.theWorld) { FIELD.clear(); chunks.clear(); world = mc.theWorld; }
        if (world == null || mc.thePlayer == null) { FIELD.drainDirty(); return; }
        for (Iterator<ChunkLights> it = chunks.values().iterator(); it.hasNext();) {
            Chunk chunk = it.next().chunk;
            if (!world.getChunkProvider().chunkExists(chunk.xPosition, chunk.zPosition) || world.getChunkFromChunkCoords(chunk.xPosition, chunk.zPosition) != chunk) it.remove();
        }
        int cx = ((int)Math.floor(mc.thePlayer.posX)) >> 4, cz = ((int)Math.floor(mc.thePlayer.posZ)) >> 4;
        int distance = mc.gameSettings.renderDistanceChunks + 3, budget = 8;
        for (int ring = 0; ring <= distance; ring++) for (int dx = -ring; dx <= ring; dx++) for (int dz = -ring; dz <= ring; dz++) {
            if (Math.max(Math.abs(dx), Math.abs(dz)) != ring) continue;
            int x = cx + dx, z = cz + dz;
            if (budget == 0 || chunks.containsKey(key(x, z)) || !world.getChunkProvider().chunkExists(x, z)) continue;
            budget--;
            Chunk chunk = world.getChunkFromChunkCoords(x, z); ChunkLights found = new ChunkLights(chunk);
            for (ExtendedBlockStorage section : chunk.getBlockStorageArray()) {
                if (section == null || section.isEmpty()) continue;
                for (int bx = 0; bx < 16; bx++) for (int by = 0; by < 16; by++) for (int bz = 0; bz < 16; bz++) if (hardened(section.getBlockByExtId(bx, by, bz))) {
                    int px = (x << 4) + bx, py = section.getYLocation() + by, pz = (z << 4) + bz;
                    found.sources.put(blockKey(px, py, pz), new LightField.Source(px + .5, py + .5, pz + .5, 15, 30));
                }
            }
            chunks.put(key(x, z), found);
        }
        Map<String, LightField.Source> next = new HashMap<String, LightField.Source>();
        for (ChunkLights chunk : chunks.values()) next.putAll(chunk.sources);
        for (Object value : world.loadedEntityList) if (value instanceof EntityLivingBase) {
            EntityLivingBase living = (EntityLivingBase)value;
            if (living.isDead) continue;
            int strongest = level(living.getHeldItem(), false);
            int radius = living.getHeldItem() == null ? 0 : SourcePolicy.radius(Item.itemRegistry.getNameForObject(living.getHeldItem().getItem()), false);
            for (int slot = 1; slot <= 4; slot++) { int candidate = level(living.getEquipmentInSlot(slot), true); if (candidate > 0 && candidate >= strongest) { strongest = candidate; radius = 30; } }
            if (strongest > 0) next.put("e:" + living.getEntityId(), new LightField.Source(Math.rint(living.posX * 8) / 8,
                Math.rint((living.posY + living.getEyeHeight() * .5) * 8) / 8, Math.rint(living.posZ * 8) / 8, strongest, radius));
        }
        FIELD.update(next);
        for (long section : FIELD.drainDirty()) {
            int x = LightField.sectionX(section) << 4, y = LightField.sectionY(section) << 4, z = LightField.sectionZ(section) << 4;
            mc.renderGlobal.markBlockRangeForRenderUpdate(x, y, z, x + 15, y + 15, z + 15);
        }
    }
    public static int sample(int original, int x, int y, int z) { return FIELD.packed(original, x + .5, y + .5, z + .5); }
    public static int sampleEntity(int original, Object value) {
        Entity entity = (Entity)value;
        return entity.worldObj == world ? FIELD.packed(original, entity.posX, entity.posY + entity.getEyeHeight() * .5, entity.posZ) : original;
    }
}
