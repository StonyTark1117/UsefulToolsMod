package com.stonytark.usefultools.lighting;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.minecraft.client.renderer.texture.AbstractTexture;

/** Keeps ForgeOculus 1.8.8's legacy tracker synchronized with its native PBR tracker. */
public final class IrisPbrTrackerBridge {
    private static final String TRACKER = "net.irisshaders.iris.pbr.TextureTracker";
    private static final Object INSTANCE;
    private static final Method TRACK;
    private static final Method DELETE;

    static {
        try {
            Class<?> tracker = Class.forName(TRACKER);
            INSTANCE = tracker.getField("INSTANCE").get(null);
            TRACK = tracker.getMethod("trackTexture", int.class, AbstractTexture.class);
            DELETE = tracker.getMethod("onDeleteTexture", int.class);
        } catch (ReflectiveOperationException error) {
            throw new ExceptionInInitializerError(error);
        }
    }

    private IrisPbrTrackerBridge() {}

    public static void track(int id, AbstractTexture texture) {
        invoke(TRACK, id, texture);
    }

    public static void delete(int id) {
        invoke(DELETE, id);
    }

    private static void invoke(Method method, Object... arguments) {
        try {
            method.invoke(INSTANCE, arguments);
        } catch (IllegalAccessException | InvocationTargetException error) {
            throw new IllegalStateException("ForgeOculus PBR texture tracking", error);
        }
    }
}
