package com.stonytark.usefultoolsmod.entity.client;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.BooleanSupplier;
import java.util.Optional;

/** Keeps the vanilla spectral glow unless Iris, Oculus, or OptiFine is rendering a shader pack. */
final class SpectralShaderSupport {
    private static final BooleanSupplier IRIS_SHADERS_ACTIVE = findIrisShaderState();
    private static final BooleanSupplier OPTIFINE_SHADERS_ACTIVE = findOptiFineShaderState();
    private static final BooleanSupplier COMMUNITY_SHADERS_ACTIVE = findCommunityShaderState();
    // Separate Wraith tuning keeps Ghost lighting and the no-shader appearance unchanged.
    private static final int WRAITH_LIGHT_FLOOR = Math.max(10, Math.min(15,
            Integer.getInteger("usefultools.wraithShaderLight", 13)));

    private SpectralShaderSupport() {}

    static boolean isShaderPackInUse() {
        return IRIS_SHADERS_ACTIVE.getAsBoolean() || OPTIFINE_SHADERS_ACTIVE.getAsBoolean()
                || COMMUNITY_SHADERS_ACTIVE.getAsBoolean();
    }

    static boolean shouldUseVanillaEmissive(boolean visible, boolean translucent, boolean glowing) {
        // Keep Minecraft's normal invisible/translucent/team-outline handling intact.
        return !isShaderPackInUse() && visible && !translucent && !glowing;
    }

    static int blockLightLevel(int normal) {
        return isShaderPackInUse() ? Math.max(normal, 10) : normal;
    }

    static int wraithBlockLightLevel(int normal) {
        return isShaderPackInUse() ? Math.max(normal, WRAITH_LIGHT_FLOOR) : normal;
    }

    private static BooleanSupplier findCommunityShaderState() {
        // 0.3.0-beta.1 has no Iris API. Use its live service state, not its configured flag:
        // compilation/backend failure can disable a selected pack. Resolve the mod instance
        // lazily because renderer classes can load before client setup finishes.
        try {
            Class<?> mod = Class.forName("dev.oculusforge.OculusForge", false,
                    SpectralShaderSupport.class.getClassLoader());
            Field service = mod.getDeclaredField("clientService");
            service.setAccessible(true);
            Method active = Class.forName("dev.oculusforge.client.OculusClientService")
                    .getMethod("shadersActive");
            Method lookup = Class.forName("net.minecraftforge.fml.ModList")
                    .getMethod("getModObjectById", String.class);
            return () -> {
                try {
                    Object value = lookup.invoke(null, "oculus");
                    if (!(value instanceof Optional<?>)) return false;
                    Object instance = ((Optional<?>) value).orElse(null);
                    return instance != null && Boolean.TRUE.equals(active.invoke(service.get(instance)));
                } catch (ReflectiveOperationException | LinkageError | RuntimeException exception) {
                    return false;
                }
            };
        } catch (ReflectiveOperationException | LinkageError | RuntimeException exception) {
            return () -> false;
        }
    }

    private static BooleanSupplier findIrisShaderState() {
        try {
            Class<?> api = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
            Object instance = api.getMethod("getInstance").invoke(null);
            Method active = api.getMethod("isShaderPackInUse");
            return () -> {
                try {
                    return Boolean.TRUE.equals(active.invoke(instance));
                } catch (ReflectiveOperationException | LinkageError | RuntimeException exception) {
                    return false;
                }
            };
        } catch (ReflectiveOperationException | LinkageError | RuntimeException exception) {
            return () -> false;
        }
    }

    private static BooleanSupplier findOptiFineShaderState() {
        try {
            Class<?> shaders = Class.forName("net.optifine.shaders.Shaders");
            Field shaderPackLoaded = shaders.getField("shaderPackLoaded");
            return () -> {
                try {
                    return shaderPackLoaded.getBoolean(null);
                } catch (IllegalAccessException | LinkageError | RuntimeException exception) {
                    return false;
                }
            };
        } catch (ReflectiveOperationException | LinkageError | RuntimeException exception) {
            return () -> false;
        }
    }
}
