package com.stonytark.usefultoolsmod.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

public final class PlatformHelper {
    private PlatformHelper() {}

    @ExpectPlatform
    public static String platformName() {
        throw new AssertionError("ExpectPlatform implementation not found for PlatformHelper.platformName");
    }
}
