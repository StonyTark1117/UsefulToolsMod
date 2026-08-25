package com.stonytark.usefultoolsmod.trigger;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Registers all custom criterion triggers for UsefulToolsMod advancements.
 * Uses DeferredRegister so registration happens during Forge's registration
 * phase, which is guaranteed to precede data generation.
 */
public class ModTriggers {

    public static final DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPES =
            DeferredRegister.create(Registries.TRIGGER_TYPE, UsefultoolsMod.MOD_ID);

    public static final DeferredHolder<CriterionTrigger<?>, GhostNearbyTrigger> GHOST_NEARBY =
            TRIGGER_TYPES.register("ghost_nearby", GhostNearbyTrigger::new);

    public static final DeferredHolder<CriterionTrigger<?>, CoalToolIgnitedTrigger> COAL_TOOL_IGNITED =
            TRIGGER_TYPES.register("coal_tool_ignited", CoalToolIgnitedTrigger::new);

    public static void register(IEventBus modEventBus) {
        TRIGGER_TYPES.register(modEventBus);
    }
}
