package com.stonytark.usefultoolsmod.trigger;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Registers all custom criterion triggers for UsefulToolsMod advancements.
 */
public class ModTriggers {

    public static final GhostNearbyTrigger GHOST_NEARBY =
            Registry.register(Registries.CRITERION, Identifier.of(UsefultoolsMod.MOD_ID, "ghost_nearby"), new GhostNearbyTrigger());

    public static final CoalToolIgnitedTrigger COAL_TOOL_IGNITED =
            Registry.register(Registries.CRITERION, Identifier.of(UsefultoolsMod.MOD_ID, "coal_tool_ignited"), new CoalToolIgnitedTrigger());

    public static void register() {}
}
