package com.stonytark.usefultoolsmod.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

/**
 * Fires the first time a Ghost locks onto a player to follow them.
 * Called from FollowPlayerGoal.canUse() on the server.
 */
public class GhostNearbyTrigger extends SimpleCriterionTrigger<GhostNearbyTrigger.TriggerInstance> {

    @Override
    protected TriggerInstance createInstance(JsonObject pJson, Optional<ContextAwarePredicate> pPlayer,
                                             DeserializationContext pDeserializationContext) {
        return new TriggerInstance(pPlayer);
    }

    /** Call this from game code whenever a ghost first targets a player. */
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
