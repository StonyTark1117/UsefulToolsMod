package com.stonytark.usefultoolsmod.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/**
 * Fires the first time a coal tool in a player's hand catches fire.
 * Called from ModEvents.handleCoalToolBurning() on the server.
 */
public class CoalToolIgnitedTrigger extends SimpleCriterionTrigger<CoalToolIgnitedTrigger.TriggerInstance> {

    private static final ResourceLocation ID = new ResourceLocation("usefultoolsmod", "coal_tool_ignited");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pPlayer,
                                             DeserializationContext pDeserializationContext) {
        return new TriggerInstance(pPlayer);
    }

    /** Call this from game code when a held coal tool first ignites. */
    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public static final class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ContextAwarePredicate player) {
            super(ID, player);
        }
    }
}
