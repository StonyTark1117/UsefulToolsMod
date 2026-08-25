package com.stonytark.usefultoolsmod.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

/**
 * Fires the first time a Ghost locks onto a player to follow them.
 * Called from FollowPlayerGoal.canUse() on the server.
 */
public class GhostNearbyTrigger extends AbstractCriterion<GhostNearbyTrigger.Conditions> {

    public static final Identifier ID = new Identifier("usefultoolsmod", "ghost_nearby");

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, LootContextPredicate playerPredicate,
                                            AdvancementEntityPredicateDeserializer deserializer) {
        return new Conditions(ID, playerPredicate);
    }

    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, conditions -> true);
    }

    public static class Conditions extends AbstractCriterionConditions {
        public Conditions(Identifier id, LootContextPredicate playerPredicate) {
            super(id, playerPredicate);
        }
    }
}
