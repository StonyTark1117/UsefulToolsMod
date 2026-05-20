package com.stonytark.usefultoolsmod;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.item.ModCreativeModeTabs;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.platform.PlatformHelper;
import com.stonytark.usefultoolsmod.trigger.ModTriggers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UsefultoolsMod {
    public static final String MOD_ID = "usefultoolsmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private UsefultoolsMod() {}

    public static void init() {
        LOGGER.info("Initialising {} on {}", MOD_ID, PlatformHelper.platformName());

        // Order matters:
        //   1. Blocks first — registering them adds matching BlockItems to ModItems.ITEMS
        //   2. Entities — SpawnEggItem ctors call ModEntities.GHOST.get() at item creation
        //   3. Items — flushes Item + BlockItem + SpawnEggItem entries
        //   4. Creative tab — its icon supplier references ModItems.RGOLD
        //   5. Triggers — independent ordering, last for clarity
        ModBlocks.register();
        ModEntities.register();
        ModItems.register();
        ModCreativeModeTabs.register();
        ModTriggers.register();
    }
}
