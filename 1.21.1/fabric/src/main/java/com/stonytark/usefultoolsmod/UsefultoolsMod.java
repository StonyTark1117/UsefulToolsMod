package com.stonytark.usefultoolsmod;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.block.entity.ModBlockEntityTypes;
import com.stonytark.usefultoolsmod.block.entity.ModMenuTypes;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.event.ModEvents;
import com.stonytark.usefultoolsmod.item.ModArmorMaterials;
import com.stonytark.usefultoolsmod.item.ModCreativeModeTabs;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.trigger.ModTriggers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.GenerationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsefultoolsMod implements ModInitializer {
    public static final String MOD_ID = "usefultoolsmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        Config.load();
        LOGGER.info("Initializing Useful Tools Mod (Fabric)");

        // Register in correct dependency order
        ModEntities.register();
        ModArmorMaterials.register();
        ModBlocks.register();
        ModItems.register();
        ModCreativeModeTabs.register();

        // Register block entity and menu types
        ModBlockEntityTypes.register();
        ModMenuTypes.register();

        // Register triggers
        ModTriggers.register();

        // Register entity attributes
        FabricDefaultAttributeRegistry.register(ModEntities.GHOST, GhostEntity.createAttributes());

        // Register spawn placements — UNRESTRICTED allows Ghosts to spawn floating
        // (they fly with NoGravity), matching the 1.20-Fabric behavior.
        SpawnRestriction.register(ModEntities.GHOST,
                SpawnLocationTypes.UNRESTRICTED,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                GhostEntity::checkGhostSpawnRules);

        // Register events
        ModEvents.register();

        // Register worldgen (biome modifications)
        registerWorldgen();
    }

    private void registerWorldgen() {
        // Overworld rgold ore
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE,
                        Identifier.of(MOD_ID, "rgold_ore_placed")));

        // Nether rgold ore
        BiomeModifications.addFeature(
                BiomeSelectors.foundInTheNether(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE,
                        Identifier.of(MOD_ID, "nether_rgold_ore_placed")));

        // End rgold ore
        BiomeModifications.addFeature(
                BiomeSelectors.foundInTheEnd(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE,
                        Identifier.of(MOD_ID, "end_rgold_ore_placed")));

        // Ghost spawn (overworld)
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                SpawnGroup.MONSTER,
                ModEntities.GHOST,
                5, 1, 3);
    }
}
