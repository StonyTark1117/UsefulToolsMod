package com.stonytark.usefultoolsmod.util;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

/** Server-side awards for feature advancements whose criteria are intentionally event driven. */
public final class ModAdvancements {
    private ModAdvancements() {}

    public static void award(ServerPlayerEntity player, String path) {
        var advancement = player.server.getAdvancementLoader().get(
                new Identifier("usefultoolsmod", path));
        if (advancement != null) player.getAdvancementTracker().grantCriterion(advancement, "trigger");
    }
}
