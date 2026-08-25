package com.stonytark.usefultoolsmod.classic;

import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/** Accepts early v1.6 saves that omitted the unlocalized-name item./tile. prefix. */
public final class ClassicMissingMappings {
    private ClassicMissingMappings() {}

    public static void remap(FMLMissingMappingsEvent event) {
        for (FMLMissingMappingsEvent.MissingMapping mapping : event.get()) {
            if (mapping.name.contains(":") && !mapping.name.startsWith("usefultoolsmod:")) continue;
            String path = mapping.name.contains(":")
                    ? mapping.name.substring(mapping.name.indexOf(':') + 1) : mapping.name;
            if (mapping.type == GameRegistry.Type.ITEM) {
                Item target = GameRegistry.findItem("usefultoolsmod", path);
                if (target == null && !path.startsWith("item."))
                    target = GameRegistry.findItem("usefultoolsmod", "item." + path);
                if (target != null) mapping.remap(target);
            } else {
                Block target = GameRegistry.findBlock("usefultoolsmod", path);
                if (target == null && !path.startsWith("tile."))
                    target = GameRegistry.findBlock("usefultoolsmod", "tile." + path);
                if (target != null) mapping.remap(target);
            }
        }
    }
}
