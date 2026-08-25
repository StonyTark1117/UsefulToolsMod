package com.stonytark.usefultoolsmod.trigger;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.advancements.CriteriaTriggers;

/**
 * Registers all custom criterion triggers for UsefulToolsMod advancements.
 * Registration must happen during FMLCommonSetupEvent via enqueueWork.
 */
public class ModTriggers {

    public static final GhostNearbyTrigger GHOST_NEARBY = new GhostNearbyTrigger();
    public static final CoalToolIgnitedTrigger COAL_TOOL_IGNITED = new CoalToolIgnitedTrigger();

    public static void register() {
        CriteriaTriggers.register(UsefultoolsMod.MOD_ID + ":ghost_nearby", GHOST_NEARBY);
        CriteriaTriggers.register(UsefultoolsMod.MOD_ID + ":coal_tool_ignited", COAL_TOOL_IGNITED);
    }
}
