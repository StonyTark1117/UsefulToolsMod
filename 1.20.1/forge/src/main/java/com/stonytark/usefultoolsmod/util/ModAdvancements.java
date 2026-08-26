package com.stonytark.usefultoolsmod.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/** Server-side awards for feature advancements whose criteria are intentionally event driven. */
public final class ModAdvancements {
    private ModAdvancements() {}

    public static void award(ServerPlayer player, String path) {
        var advancement = player.server.getAdvancements().getAdvancement(
                ResourceLocation.fromNamespaceAndPath("usefultoolsmod", path));
        if (advancement != null) player.getAdvancements().award(advancement, "trigger");
    }
}
