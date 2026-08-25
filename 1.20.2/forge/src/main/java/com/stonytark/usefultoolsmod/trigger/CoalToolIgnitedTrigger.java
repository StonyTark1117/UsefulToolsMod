package com.stonytark.usefultoolsmod.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

/**
 * Fires the first time a coal tool in a player's hand catches fire.
 * Called from ModEvents.handleCoalToolBurning() on the server.
 */
public class CoalToolIgnitedTrigger extends SimpleCriterionTrigger<CoalToolIgnitedTrigger.TriggerInstance> {

    @Override
    protected TriggerInstance createInstance(JsonObject pJson, Optional<ContextAwarePredicate> pPlayer,
                                             DeserializationContext pDeserializationContext) {
        return new TriggerInstance(pPlayer);
    }

    /** Call this from game code when a held coal tool first ignites. */
    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player)
            implements SimpleCriterionTrigger.SimpleInstance {

        @Override
        public Optional<ContextAwarePredicate> playerPredicate() {
            return player;
        }

        @Override
        public JsonObject serializeToJson() {
            return new JsonObject();
        }
    }
}
