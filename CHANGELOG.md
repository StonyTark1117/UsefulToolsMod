# Changelog

## 2.3.0 - Unreleased

- Consolidated active, buildable sources for Forge, Fabric, and NeoForge across Minecraft 1.20.1, 1.20.2, 1.21.1, 26.1.2, and 26.2, plus Forge 1.7.10.
- Replaced the 1.21.1 Architectury multiproject with independent native Forge, Fabric, and NeoForge builds. Useful Tools no longer requires Architectury at runtime; 1.20.x retains only the compile-time API needed by optional REI sources.
- Restored the full modern catalog, Spectral Infuser, Ghost, explosives, world generation, material effects, recipes, advancements, optional recipe/viewer integrations, and 116 configuration controls in the modern lines.
- Added dependency-free in-game config screens and removed the Cloth Config requirement. Fabric exposes them through optional, version-pinned Mod Menu entrypoints, including the repaired 1.20.x lines.
- Added atomic config persistence and migration aliases for `pointedDripstone*` to `dripstone*` in shared JSON-config lines. The 1.20.x Fabric loaders now recover malformed values per key and clamp disk-loaded ghost spawn chance.
- Restored the missing Dynamite/Grenade recipe advancements on the 1.20.x and standalone 1.21.1 Forge lines, and migrated 1.20.x Fabric WTHIT integration to each WTHIT generation's universal plugin descriptor.
- Added complete source jars, exact loader-qualified metadata versions, a namespace-aware canonical generated catalog, CI, local matrix/release tooling, and artifact manifests with packaged dependency bounds, byte sizes, and SHA-256 hashes.
- Added a strict 32-artifact audit (including exact 682-recipe/848-advancement payload checks) and disabled unsafe shared build-cache reuse in the matrix after detecting that ForgeGradle could report a cached compile while emitting a resource-only jar.
- Added 60 dependency-free contract/unit gates, a full modern datagen reproducibility gate, and loader-native GameTests on all 15 modern targets (96 required invocations) for registration, recipes, tiers, Spectral Infuser processing/config gating, Ghost lifecycle and armor interaction, explosives, effects, and worldgen.
- Verified every ordinary Fabric release JAR unchanged on Quilt Loader 0.30.0. The 26.2 metadata now distinguishes its 0.19.3 build dependency from the compatible 0.19.2 runtime floor.
- Recovered the published 1.7.10 v1.6 implementation into a reproducible Java 8/Forge 10.13.4.1614 build, then added generated adapters for all 633 canonical items, 21 blocks, 87 tool materials, 45 armor materials, 672 crafting recipes, five furnace transforms, and 116 native config entries. Added the classic Ghost, spawn egg/renderer, Spectral Infuser tile/container/GUI, infusion NBT, damage/target bridges, passive effects, substitution manifest, achievements, Java-8 semantic tests, automated server/client scenarios, and published-v1.6 world migration acceptance.
- Added 16/16 isolated server and client gates, 40 absent/present optional-integration profiles, representative 2.2.2 Forge/Fabric/NeoForge world/config upgrades, visual acceptance, and repeatable Quilt 0.30.0 certification for all five unchanged Fabric jars.
- Corrected late Forge/NeoForge config-reload callbacks after spec unload and removed bedrock from the 1.21+/26.x SOB incorrect-mining tag so WTHIT can compare the custom tier hierarchy without error.
- Replaced the 1.21.1 Forge metadata's ineffective `[0,)` Forge/JavaFML floors with the supported Forge 52 major range and verified the rebuilt target in a fresh isolated server world.

No release has been published by this repository work.
