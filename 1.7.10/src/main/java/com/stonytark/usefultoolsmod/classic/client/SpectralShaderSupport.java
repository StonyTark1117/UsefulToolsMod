package com.stonytark.usefultoolsmod.classic.client;

import java.lang.reflect.Field;

/** Optional OptiFine shader state, read on every render so pack toggles apply immediately. */
public final class SpectralShaderSupport {
    private static final Field SHADER_PACK_LOADED = findShaderPackField();
    private static final int WRAITH_LIGHT_FLOOR = Math.max(10, Math.min(15,
            Integer.getInteger("usefultools.wraithShaderLight", 13)));

    private SpectralShaderSupport() {}

    public static int packedLight(int vanillaLight) {
        return packedLight(vanillaLight, 10);
    }

    public static int wraithPackedLight(int vanillaLight) {
        return packedLight(vanillaLight, WRAITH_LIGHT_FLOOR);
    }

    private static int packedLight(int vanillaLight, int floor) {
        if (!isShaderPackInUse()) return vanillaLight;
        int blockLight = Math.max(vanillaLight & 0xffff, floor << 4);
        return (vanillaLight & 0xffff0000) | blockLight;
    }

    public static boolean isShaderPackInUse() {
        return isShaderPackLoaded();
    }

    private static boolean isShaderPackLoaded() {
        if (SHADER_PACK_LOADED == null) return false;
        try {
            return SHADER_PACK_LOADED.getBoolean(null);
        } catch (IllegalAccessException | LinkageError ignored) {
            return false;
        }
    }

    private static Field findShaderPackField() {
        try {
            return Class.forName("shadersmod.client.Shaders").getField("shaderPackLoaded");
        } catch (ClassNotFoundException | NoSuchFieldException | LinkageError ignored) {
            return null;
        }
    }
}
