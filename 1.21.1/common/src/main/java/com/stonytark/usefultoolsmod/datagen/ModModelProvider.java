package com.stonytark.usefultoolsmod.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.item.custom.EdibleArmorItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Registry-driven model provider shared by Fabric and NeoForge.
 *
 * <p>The item catalog follows four model rules: tools are handheld, armor is
 * trim-aware except for consumable armor, the Ghost egg uses the vanilla egg
 * template, and all remaining direct items use the generated parent. Block
 * items delegate to their matching block model.</p>
 */
public final class ModModelProvider implements DataProvider {
    private static final Map<String, Float> TRIMS = new LinkedHashMap<>();
    static {
        TRIMS.put("quartz", 0.1F);
        TRIMS.put("iron", 0.2F);
        TRIMS.put("netherite", 0.3F);
        TRIMS.put("redstone", 0.4F);
        TRIMS.put("copper", 0.5F);
        TRIMS.put("gold", 0.6F);
        TRIMS.put("emerald", 0.7F);
        TRIMS.put("diamond", 0.8F);
        TRIMS.put("lapis", 0.9F);
        TRIMS.put("amethyst", 1.0F);
    }

    private final PackOutput.PathProvider blockStates;
    private final PackOutput.PathProvider blockModels;
    private final PackOutput.PathProvider itemModels;

    public ModModelProvider(PackOutput output) {
        blockStates = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        blockModels = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/block");
        itemModels = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/item");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        List<CompletableFuture<?>> writes = new ArrayList<>();
        for (Block block : BuiltInRegistries.BLOCK) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
            if (UsefultoolsMod.MOD_ID.equals(id.getNamespace())) {
                writeBlock(output, writes, id);
            }
        }
        for (Item item : BuiltInRegistries.ITEM) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
            if (UsefultoolsMod.MOD_ID.equals(id.getNamespace())) {
                writeItem(output, writes, id, item);
            }
        }
        return CompletableFuture.allOf(writes.toArray(CompletableFuture[]::new));
    }

    private void writeBlock(CachedOutput output, List<CompletableFuture<?>> writes, ResourceLocation id) {
        if ("spectral_infuser".equals(id.getPath())) {
            writes.add(DataProvider.saveStable(output, spectralBlockState(), blockStates.json(id)));
            writes.add(DataProvider.saveStable(output, spectralModel(false), blockModels.json(id)));
            ResourceLocation on = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), id.getPath() + "_on");
            writes.add(DataProvider.saveStable(output, spectralModel(true), blockModels.json(on)));
        } else {
            JsonObject state = new JsonObject();
            JsonObject variants = new JsonObject();
            JsonObject normal = new JsonObject();
            normal.addProperty("model", id.getNamespace() + ":block/" + id.getPath());
            variants.add("", normal);
            state.add("variants", variants);
            writes.add(DataProvider.saveStable(output, state, blockStates.json(id)));

            JsonObject model = new JsonObject();
            model.addProperty("parent", "minecraft:block/cube_all");
            JsonObject textures = new JsonObject();
            textures.addProperty("all", id.getNamespace() + ":block/" + id.getPath());
            model.add("textures", textures);
            writes.add(DataProvider.saveStable(output, model, blockModels.json(id)));
        }
    }

    private void writeItem(CachedOutput output, List<CompletableFuture<?>> writes,
                           ResourceLocation id, Item item) {
        if (item instanceof BlockItem) {
            writes.add(DataProvider.saveStable(output,
                    parent(id.getNamespace() + ":block/" + id.getPath()), itemModels.json(id)));
        } else if (item instanceof SpawnEggItem) {
            writes.add(DataProvider.saveStable(output, parent("minecraft:item/template_spawn_egg"), itemModels.json(id)));
        } else if (item instanceof ArmorItem armor && !(item instanceof EdibleArmorItem)) {
            writeTrimmedArmor(output, writes, id, armor);
        } else if (item instanceof TieredItem) {
            writes.add(DataProvider.saveStable(output, texturedItem(id, "minecraft:item/handheld"), itemModels.json(id)));
        } else {
            writes.add(DataProvider.saveStable(output, texturedItem(id, "minecraft:item/generated"), itemModels.json(id)));
        }
    }

    private void writeTrimmedArmor(CachedOutput output, List<CompletableFuture<?>> writes,
                                   ResourceLocation id, ArmorItem armor) {
        JsonObject base = texturedItem(id, "minecraft:item/generated");
        JsonArray overrides = new JsonArray();
        for (Map.Entry<String, Float> trim : TRIMS.entrySet()) {
            String trimName = id.getPath() + "_" + trim.getKey() + "_trim";
            JsonObject predicate = new JsonObject();
            predicate.addProperty("minecraft:trim_type", trim.getValue());
            JsonObject override = new JsonObject();
            override.add("predicate", predicate);
            override.addProperty("model", id.getNamespace() + ":item/" + trimName);
            overrides.add(override);

            JsonObject trimModel = texturedItem(id, "minecraft:item/generated");
            trimModel.getAsJsonObject("textures").addProperty("layer1",
                    "minecraft:trims/items/" + armorSlot(armor) + "_trim_" + trim.getKey());
            ResourceLocation trimId = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), trimName);
            writes.add(DataProvider.saveStable(output, trimModel, itemModels.json(trimId)));
        }
        base.add("overrides", overrides);
        writes.add(DataProvider.saveStable(output, base, itemModels.json(id)));
    }

    private static String armorSlot(ArmorItem armor) {
        return switch (armor.getType()) {
            case HELMET -> "helmet";
            case CHESTPLATE -> "chestplate";
            case LEGGINGS -> "leggings";
            case BOOTS -> "boots";
            default -> throw new IllegalArgumentException("Unsupported armor slot: " + armor.getType());
        };
    }

    private static JsonObject texturedItem(ResourceLocation id, String parent) {
        JsonObject model = parent(parent);
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", id.getNamespace() + ":item/" + id.getPath());
        model.add("textures", textures);
        return model;
    }

    private static JsonObject parent(String parent) {
        JsonObject model = new JsonObject();
        model.addProperty("parent", parent);
        return model;
    }

    private static JsonObject spectralModel(boolean lit) {
        JsonObject model = parent("minecraft:block/orientable_with_bottom");
        JsonObject textures = new JsonObject();
        textures.addProperty("bottom", "usefultoolsmod:block/spectral_infuser_side");
        textures.addProperty("front", "usefultoolsmod:block/spectral_infuser_front" + (lit ? "_on" : ""));
        textures.addProperty("side", "usefultoolsmod:block/spectral_infuser_side");
        textures.addProperty("top", "usefultoolsmod:block/spectral_infuser_top");
        model.add("textures", textures);
        return model;
    }

    private static JsonObject spectralBlockState() {
        JsonObject state = new JsonObject();
        JsonObject variants = new JsonObject();
        addSpectralVariant(variants, "east", false, 90);
        addSpectralVariant(variants, "east", true, 90);
        addSpectralVariant(variants, "north", false, 0);
        addSpectralVariant(variants, "north", true, 0);
        addSpectralVariant(variants, "south", false, 180);
        addSpectralVariant(variants, "south", true, 180);
        addSpectralVariant(variants, "west", false, 270);
        addSpectralVariant(variants, "west", true, 270);
        state.add("variants", variants);
        return state;
    }

    private static void addSpectralVariant(JsonObject variants, String facing, boolean lit, int rotation) {
        JsonObject variant = new JsonObject();
        variant.addProperty("model", "usefultoolsmod:block/spectral_infuser" + (lit ? "_on" : ""));
        if (rotation != 0) {
            variant.addProperty("y", rotation);
        }
        variants.add("facing=" + facing + ",lit=" + lit, variant);
    }

    @Override
    public String getName() {
        return "Useful Tools registry-driven models";
    }
}
