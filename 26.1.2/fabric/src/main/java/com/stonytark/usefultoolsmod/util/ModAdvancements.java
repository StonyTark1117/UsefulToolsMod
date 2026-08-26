package com.stonytark.usefultoolsmod.util;

import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

/** Server-side awards for feature advancements whose criteria are intentionally event driven. */
public final class ModAdvancements {
    private ModAdvancements() {}

    public static void award(ServerPlayer player, String path) {
        var advancement = ((net.minecraft.server.level.ServerLevel) player.level()).getServer().getAdvancements().get(
                Identifier.fromNamespaceAndPath("usefultoolsmod", path));
        if (advancement != null) player.getAdvancements().award(advancement, "trigger");
    }
}
