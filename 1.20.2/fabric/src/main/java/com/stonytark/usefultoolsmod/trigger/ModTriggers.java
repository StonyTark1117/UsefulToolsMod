package com.stonytark.usefultoolsmod.trigger;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;

/**
 * Registers all custom criterion triggers for UsefulToolsMod advancements.
 */
public class ModTriggers {

    public static final GhostNearbyTrigger GHOST_NEARBY =
            (GhostNearbyTrigger) Criteria.register("usefultoolsmod:ghost_nearby", new GhostNearbyTrigger());

    public static final CoalToolIgnitedTrigger COAL_TOOL_IGNITED =
            (CoalToolIgnitedTrigger) Criteria.register("usefultoolsmod:coal_tool_ignited", new CoalToolIgnitedTrigger());

    public static void register() {
        // Registration happens via the static initializers above
    }
}
