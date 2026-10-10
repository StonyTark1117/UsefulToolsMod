package com.stonytark.usefultools.lighting;

/** Generated from lighting/sources.json. */
public final class SourcePolicy {
    private SourcePolicy() {}
    public static int level(String id, boolean worn) {
        if ("minecraft:glowstone".equals(id)) return worn ? 0 : 15;
        if (id == null || !id.startsWith("usefultoolsmod:")) return 0;
        String name = id.substring(15);
        switch (name) {
            case "ectoplasm_lantern": return 12 * (worn ? 0 : 1);
            case "soul_lantern": return 12 * (worn ? 0 : 1);
            case "hglow_block": return 15 * (worn ? 0 : 1);
            case "hglow": return 15 * (worn ? 0 : 1);
            case "hglowstone_axe": return 15 * (worn ? 0 : 1);
            case "hglowstone_hoe": return 15 * (worn ? 0 : 1);
            case "hglowstone_pickaxe": return 15 * (worn ? 0 : 1);
            case "hglowstone_shovel": return 15 * (worn ? 0 : 1);
            case "hglowstone_sword": return 15 * (worn ? 0 : 1);
            case "hglow_helmet": return 15;
            case "hglow_chestplate": return 15;
            case "hglow_leggings": return 15;
            case "hglow_boots": return 15;
            default: return 0;
        }
    }
    public static int radius(String id, boolean worn) { int level = level(id, worn); return level == 15 && id.startsWith("usefultoolsmod:") ? 30 : level; }
}
