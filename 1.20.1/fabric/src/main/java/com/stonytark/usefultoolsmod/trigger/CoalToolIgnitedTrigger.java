package com.stonytark.usefultoolsmod.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

/**
 * Fires the first time a coal tool in a player's hand catches fire.
 * Called from ModEvents.handleCoalToolBurning() on the server.
 */
public class CoalToolIgnitedTrigger extends AbstractCriterion<CoalToolIgnitedTrigger.Conditions> {

    public static final Identifier ID = new Identifier("usefultoolsmod", "coal_tool_ignited");

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
