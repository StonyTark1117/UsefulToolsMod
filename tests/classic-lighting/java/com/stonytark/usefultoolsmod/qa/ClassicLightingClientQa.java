package com.stonytark.usefultoolsmod.qa;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import java.util.HashSet;
import java.util.Set;

public class ClassicLightingClientQa {
    private final Set<Integer> verified = new HashSet<Integer>();
    private boolean done;
    public static void register() {
        FMLCommonHandler.instance().bus().register(new ClassicLightingClientQa());
    }
    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        if (done || event.phase != TickEvent.Phase.END) return;
        World world = Minecraft.getMinecraft().theWorld;
        if (world == null) return;
        int stage = ClassicLightingQa.stage, x = ClassicLightingQa.x, y = ClassicLightingQa.Y, z = ClassicLightingQa.z;
        if (stage < 0) return;
        int expected = stage <= 1 ? 12 : stage == 3 || stage == 6 || stage == 7 ? 13 : 0;
        if (world.getBlock(x, y, z).getLightValue(world, x, y, z) == expected
                && world.getSavedLightValue(EnumSkyBlock.Block, x, y, z) == expected
                && world.getSavedLightValue(EnumSkyBlock.Block, x + 1, y, z) == Math.max(0, expected - 1)) {
            ClassicLightingQa.clientStage = stage;
            if (verified.add(stage)) System.out.println("CLASSIC_LIGHTING_CLIENT_CHECK stage=" + stage + " light=" + expected);
        }
        if (stage >= 0 && !verified.contains(stage) && Minecraft.getMinecraft().thePlayer.ticksExisted % 100 == 0) {
            System.out.println("CLASSIC_LIGHTING_CLIENT_WAIT stage=" + stage
                    + " emitted=" + world.getBlock(x, y, z).getLightValue(world, x, y, z)
                    + " source=" + world.getSavedLightValue(EnumSkyBlock.Block, x, y, z)
                    + " adjacent=" + world.getSavedLightValue(EnumSkyBlock.Block, x + 1, y, z));
        }
        if (stage == 9) {
            done = true;
            for (int required : new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8}) {
                if (!verified.contains(required)) {
                    System.out.println("CLASSIC_LIGHTING_CLIENT_FAIL missing-stage=" + required);
                    return;
                }
            }
            System.out.println("CLASSIC_LIGHTING_CLIENT_PASS");
        }
    }
}
