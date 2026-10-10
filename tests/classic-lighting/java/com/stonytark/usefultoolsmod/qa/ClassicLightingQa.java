package com.stonytark.usefultoolsmod.qa;

import com.stonytark.usefultoolsmod.classic.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import java.io.File;
import java.lang.reflect.Method;

@Mod(modid = "classiclightingqa", name = "Classic lighting runtime QA", version = "1",
     dependencies = "required-after:usefultoolsmod")
public class ClassicLightingQa {
    public static volatile int x, z, stage = -1;
    public static volatile int clientStage = -1;
    public static final int Y = 180;
    private int ticks;
    private TileSpectralInfuser tile;
    private Block infuser;
    private boolean failed;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws Exception {
        FMLCommonHandler.instance().bus().register(this);
        if (event.getSide().isClient())
            Class.forName("com.stonytark.usefultoolsmod.qa.ClassicLightingClientQa")
                    .getMethod("register").invoke(null);
    }

    @SubscribeEvent
    public void tick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote
                || event.world.provider.dimensionId != 0 || failed || stage == 9) return;
        World world = event.world;
        if (MinecraftServer.getServer().isSinglePlayer() && world.playerEntities.isEmpty()) return;
        if (++ticks % 60 != 0) return;
        if (MinecraftServer.getServer().isSinglePlayer() && stage >= 0 && clientStage < stage) return;
        try {
            switch (++stage) {
                case 0:
                    x = world.getSpawnPoint().posX; z = world.getSpawnPoint().posZ;
                    for (int cx = (x >> 4) - 2; cx <= (x >> 4) + 2; cx++)
                        for (int cz = (z >> 4) - 2; cz <= (z >> 4) + 2; cz++)
                            world.getChunkFromChunkCoords(cx, cz);
                    for (int dx = -2; dx <= 2; dx++) for (int dy = -1; dy <= 1; dy++)
                        for (int dz = -2; dz <= 2; dz++) world.setBlockToAir(x + dx, Y + dy, z + dz);
                    world.setBlock(x, Y, z, ClassicFeatureRegistry.ectoplasmLantern);
                    light(world, 12, 11); pass("lantern-placement-12-adjacent-11");
                    break;
                case 1:
                    world.setBlock(x + 1, Y, z, Blocks.redstone_block);
                    light(world, 12, -1);
                    check(!ClassicSoulLantern.activeWithin(world, x, Y, z, 2), "powered ward disabled");
                    pass("powered-lantern-still-12-ward-disabled");
                    world.setBlockToAir(x + 1, Y, z);
                    world.setBlock(x, Y, z, ClassicFeatureRegistry.legacySoulLantern);
                    light(world, 12, 11); pass("legacy-alias-12-adjacent-11");
                    break;
                case 2:
                    world.setBlockToAir(x, Y, z);
                    light(world, 0, 0); pass("lantern-removal-clears-light");
                    infuser = ClassicGeneratedCatalog.BLOCKS.get("spectral_infuser");
                    world.setBlock(x, Y, z, infuser, 2, 3);
                    tile = (TileSpectralInfuser) world.getTileEntity(x, Y, z);
                    light(world, 0, 0); pass("idle-infuser-0");
                    break;
                case 3:
                    tile.setInventorySlotContents(0, new ItemStack(Items.iron_sword));
                    tile.setInventorySlotContents(1, new ItemStack(ClassicGeneratedCatalog.ITEMS.get("ectoplasm"), 4));
                    tile.updateEntity();
                    light(world, 13, 12);
                    check(world.getBlockMetadata(x, Y, z) == 10, "other metadata preserved");
                    check(tile == world.getTileEntity(x, Y, z), "tile identity retained");
                    check(!tile.shouldRefresh(infuser, infuser, 2, 10, world, x, Y, z), "client metadata preserves tile");
                    check(tile.shouldRefresh(infuser, Blocks.air, 10, 0, world, x, Y, z), "removal refreshes tile");
                    roundTrip(world); pass("processing-13-adjacent-12-tile-and-save-preserved");
                    break;
                case 4:
                    world.setBlock(x + 1, Y, z, Blocks.redstone_block);
                    tile.updateEntity(); light(world, 0, -1);
                    check(tile.getProgress() == 0, "redstone resets progress");
                    pass("redstone-stops-infusion-and-light");
                    break;
                case 5:
                    world.setBlockToAir(x + 1, Y, z); light(world, 0, 0);
                    tile.updateEntity(); light(world, 13, 12); pass("redstone-release-resumes-light");
                    setEnabled(false);
                    tile.updateEntity(); light(world, 0, 0);
                    check(tile.getProgress() == 0, "config resets progress");
                    pass("disabled-infuser-0");
                    break;
                case 6:
                    setEnabled(true);
                    tile.updateEntity(); light(world, 13, 12);
                    tile.setInventorySlotContents(1, null);
                    tile.updateEntity(); light(world, 0, 0); pass("fuel-removal-clears-light");
                    tile.setInventorySlotContents(1, new ItemStack(ClassicGeneratedCatalog.ITEMS.get("ectoplasm"), 4));
                    tile.updateEntity(); light(world, 13, 12);
                    break;
                case 7:
                    for (int i = 0; i < 200; i++) tile.updateEntity();
                    check(tile.getStackInSlot(0) == null, "input consumed");
                    check(tile.getStackInSlot(2) != null && ClassicInfusion.isInfused(tile.getStackInSlot(2)), "output infused");
                    check(tile.getStackInSlot(1).stackSize == 3, "one fuel consumed");
                    light(world, 0, 0); pass("craft-completion-blocked-output-0-inventory-intact");
                    // Old worlds have metadata 0 even if processing was saved.
                    tile.setInventorySlotContents(2, null);
                    tile.setInventorySlotContents(0, new ItemStack(Items.iron_sword));
                    world.setBlockMetadataWithNotify(x, Y, z, 0, 3);
                    tile.updateEntity(); light(world, 13, 12); pass("old-metadata-zero-recovers-lit-on-tick");
                    break;
                case 8:
                    tile.setInventorySlotContents(0, null);
                    tile.updateEntity(); light(world, 0, 0); pass("input-removal-clears-light");
                    world.setBlockToAir(x, Y, z); light(world, 0, 0); pass("infuser-removal-clears-light");
                    break;
                case 9:
                    System.out.println("CLASSIC_LIGHTING_SERVER_PASS");
                    break;
                default: throw new AssertionError("bad stage");
            }
        } catch (Throwable problem) {
            failed = true;
            System.out.println("CLASSIC_LIGHTING_FAIL stage=" + stage);
            problem.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static void setEnabled(boolean enabled) throws Exception {
        java.lang.reflect.Field values = ClassicGeneratedConfig.class.getDeclaredField("VALUES");
        values.setAccessible(true);
        ((java.util.Map<String, Object>) values.get(null)).put("spectralInfuserEnabled", enabled);
    }

    private void roundTrip(World world) throws Exception {
        NBTTagCompound tag = new NBTTagCompound();
        AnvilChunkLoader loader = new AnvilChunkLoader(new File(System.getProperty("java.io.tmpdir")));
        Method write = AnvilChunkLoader.class.getDeclaredMethod("writeChunkToNBT", Chunk.class, World.class, NBTTagCompound.class);
        Method read = AnvilChunkLoader.class.getDeclaredMethod("readChunkFromNBT", World.class, NBTTagCompound.class);
        write.setAccessible(true); read.setAccessible(true);
        write.invoke(loader, world.getChunkFromBlockCoords(x, z), world, tag);
        Chunk restored = (Chunk) read.invoke(loader, world, tag);
        check(restored.getBlockMetadata(x & 15, Y, z & 15) == 10, "Anvil lit metadata retained");
        check(restored.getSavedLightValue(EnumSkyBlock.Block, x & 15, Y, z & 15) == 13, "Anvil light retained");
        loader.loadEntities(world, tag, restored);
        TileSpectralInfuser saved = (TileSpectralInfuser) restored.func_150806_e(x & 15, Y, z & 15);
        check(saved.getProgress() == tile.getProgress(), "saved progress retained");
        check(saved.getStackInSlot(1).stackSize == 4, "saved fuel retained");
    }

    private static void light(World world, int emitted, int adjacent) {
        check(world.getBlock(x, Y, z).getLightValue(world, x, Y, z) == emitted, "emission " + emitted);
        check(world.getSavedLightValue(EnumSkyBlock.Block, x, Y, z) == emitted, "propagated source " + emitted);
        if (adjacent >= 0) check(world.getSavedLightValue(EnumSkyBlock.Block, x + 1, Y, z) == adjacent, "adjacent " + adjacent);
    }
    private static void check(boolean condition, String name) {
        if (!condition) throw new AssertionError(name);
    }
    private static void pass(String name) { System.out.println("CLASSIC_LIGHTING_CHECK " + name); }
}
