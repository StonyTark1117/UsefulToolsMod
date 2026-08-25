package com.stonytark.usefultoolsmod.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/**
 * Fires the first time a Ghost locks onto a player to follow them.
 * Called from FollowPlayerGoal.canUse() on the server.
 */
public class GhostNearbyTrigger extends SimpleCriterionTrigger<GhostNearbyTrigger.TriggerInstance> {

    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("usefultoolsmod", "ghost_nearby");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pPlayer,
                                             DeserializationContext pDeserializationContext) {
        return new TriggerInstance(pPlayer);
    }

    /** Call this from game code whenever a ghost first targets a player. */
    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public static final class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ContextAwarePredicate player) {
            super(ID, player);
        }
    }
}
