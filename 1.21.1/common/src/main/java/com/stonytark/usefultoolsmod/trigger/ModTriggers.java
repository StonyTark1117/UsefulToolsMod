package com.stonytark.usefultoolsmod.trigger;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;

public class ModTriggers {

    public static final DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPES =
            DeferredRegister.create(UsefultoolsMod.MOD_ID, Registries.TRIGGER_TYPE);

    public static final RegistrySupplier<GhostNearbyTrigger> GHOST_NEARBY =
            TRIGGER_TYPES.register("ghost_nearby", GhostNearbyTrigger::new);

    public static final RegistrySupplier<CoalToolIgnitedTrigger> COAL_TOOL_IGNITED =
            TRIGGER_TYPES.register("coal_tool_ignited", CoalToolIgnitedTrigger::new);

    public static void register() {
        TRIGGER_TYPES.register();
    }
}
