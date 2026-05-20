package com.stonytark.usefultoolsmod;

import com.stonytark.usefultoolsmod.platform.PlatformHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UsefultoolsMod {
    public static final String MOD_ID = "usefultoolsmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private UsefultoolsMod() {}

    public static void init() {
        LOGGER.info("Initialising {} on {}", MOD_ID, PlatformHelper.platformName());
    }
}
