package com.stonytark.usefultoolsmod.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;

/**
 * Fires the first time a Ghost locks onto a player to follow them.
 * Called from FollowPlayerGoal.canUse() on the server.
 */
public class GhostNearbyTrigger extends AbstractCriterion<GhostNearbyTrigger.Conditions> {

    @Override
    public Codec<Conditions> getConditionsCodec() {
        return Conditions.CODEC;
    }

    /** Call this from game code whenever a ghost first targets a player. */
    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, conditions -> true);
    }

    public record Conditions(Optional<LootContextPredicate> player) implements AbstractCriterion.Conditions {

        public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(inst ->
                inst.group(
                        EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player")
                                .forGetter(Conditions::player)
                ).apply(inst, Conditions::new));
    }
}
