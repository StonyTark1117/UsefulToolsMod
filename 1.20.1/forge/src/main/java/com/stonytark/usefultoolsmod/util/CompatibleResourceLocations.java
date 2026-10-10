package com.stonytark.usefultoolsmod.util;

import net.minecraft.resources.ResourceLocation;

/** Uses the API retained by OptiFine's replacement of the 1.20.1 class. */
public final class CompatibleResourceLocations {
    private CompatibleResourceLocations() {
    }

    @SuppressWarnings({"deprecation", "removal"})
    public static ResourceLocation fromNamespaceAndPath(String namespace, String path) {
        return new ResourceLocation(namespace, path);
    }

    @SuppressWarnings({"deprecation", "removal"})
    public static ResourceLocation parse(String location) {
        return new ResourceLocation(location);
    }
}
