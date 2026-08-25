# Complete Useful Tools Mod 2.3.0

## Audit Summary

- The clean main checkout currently contains only the 1.21.1 Architectury Fabric/NeoForge pilot. Published 1.20.x and 26.1.2 sources remain outside the active tree or under archived/; Forge 1.21.1, every 26.2 target, and buildable 1.7.10 source are absent.
- With Java 21 explicitly selected, 1.21.1 builds two approximately 2 MB 2.3.0 jars containing 633 registered items, 21 blocks, two entities, 682 recipes, and 848 advancements. The ambient Java 26 build fails, tests are NO-SOURCE, source jars omit common sources, and compilation produces over 100       
  warnings.

- “Gameplay complete” is not yet demonstrated. Damage and targeting bridges exist, but obsolete no-op/TODO code remains; full datagen, built-in config UI, runtime tests, CI, accurate optional-dependency metadata, root license/changelog, and release verification are missing.
- README.md, FUTURE_WORK.md, the published CurseForge matrix (https://www.curseforge.com/minecraft/mc-mods/the-useful-tools-mod), and the GitHub repositories (https://github.com/StonyTark1117?tab=repositories) disagree about supported versions. The live Modrinth project currently contains 26            
  historical releases through 26.1.2 but no 26.2 release.

- Architectury is now available for 26.1.2 (https://api.modrinth.com/v2/project/architectury-api/version?game_versions=%5B%2226.1.2%22%5D) and 26.2 (https://api.modrinth.com/v2/project/architectury-api/version?game_versions=%5B%2226.2%22%5D), invalidating the existing deferred-platform premise.

## Target Matrix and Versioning

Produce 16 release-ready binary jars plus complete source jars:

Minecraft    Java    Artifacts                                                                                                                                                                                                                                                                                 
━━━━━━━━━━━  ━━━━━━  ━━━━━━━━━━━━━━━━━━━━━━━━━                                                                                                                                                                                                                                                                  
1.7.10          8    Forge                                                                                                                                                                                                                                                                                     
───────────  ──────  ─────────────────────────                                                                                                                                                                                                                                                                  
1.20.1         17    Forge, Fabric, NeoForge                                                                                                                                                                                                                                                                   
───────────  ──────  ─────────────────────────                                                                                                                                                                                                                                                                  
1.20.2         17    Forge, Fabric, NeoForge                                                                                                                                                                                                                                                                   
───────────  ──────  ─────────────────────────                                                                                                                                                                                                                                                                  
1.21.1         21    Forge, Fabric, NeoForge                                                                                                                                                                                                                                                                   
───────────  ──────  ─────────────────────────                                                                                                                                                                                                                                                                  
26.1.2         25    Forge, Fabric, NeoForge                                                                                                                                                                                                                                                                   
───────────  ──────  ─────────────────────────                                                                                                                                                                                                                                                                  
26.2           25    Forge, Fabric, NeoForge

- Use logical release version 2.3.0 everywhere. Metadata uses SemVer-compatible build metadata such as 2.3.0+mc.1.21.1.fabric; files retain the familiar usefultoolsmod-2.3.0-<minecraft>-<loader>.jar pattern.
- Treat 2.3.0 as unreleased until all applicable gates pass. Preserve every published registry identifier and add migration aliases where historical names differ.
- Test ordinary Fabric jars under Quilt for every modern line. Record Quilt compatibility only where the unchanged Fabric artifact passes; do not create separate Quilt artifacts.
- Pin exact Gradle/plugins/dependencies—no snapshots or + selectors. Use toolchains per line. Build 1.7.10 with Forge 10.13.4.1614 and RetroFuturaGradle 1.4.0 rather than legacy ForgeGradle.

## Implementation Changes

1. Canonical content and repository structure
    - Add a version-neutral content manifest defining IDs, material statistics, 633 items, 21 blocks, entities, recipes, effects, config keys, tags, and advancement intent.
    - Generate loader/version registration tables and data from this manifest; keep hand-written code for behavior that cannot be described declaratively.
    - Create active roots for 1.7.10, 1.20.1, 1.20.2, 1.21.1, 26.1.2, and 26.2. Import only source/assets from legacy repositories—exclude their tracked caches, build outputs, run directories, and IDE state.
    - Use Architectury for supported loader families: Fabric/Forge on 1.20.1, all three loaders on 1.20.2, and Fabric/NeoForge on 1.21.1/26.x. Maintain thin standalone adapters where Architectury lacks that loader.
    - Add a root matrix runner and CI workflows that invoke each version’s pinned wrapper/toolchain independently.

2. Finish the shared modern feature baseline
    - Remove stale disabled-handler blocks and no-op damage placeholders after verifying the platform bridges cover damage mutation, target suppression, burn odds, spawn control, and cancellation exactly once.
    - Complete every datagen provider: recipes, advancements, loot, block/item tags, models, blockstates, worldgen, biome modifiers, and integration tags. Clean datagen must reproduce the committed resource tree with no diff.
    - Finish Spectral Infuser inventory/menu/screen behavior, Ghost AI/spawning/breeding/taming/damage rules, explosives, mining tiers, all material effects, and all 116 config controls.
    - Restore accurate Fabric/Forge/NeoForge metadata, project links, icons, dependency ranges, and optional integrations. Preserve the 1.21.1 NeoForge Magnetization behavior from 2.2.3; enable JEI, WTHIT, JER, and similar integrations only where compatible upstream artifacts exist.
    - Make source jars include common, generated, and loader-specific sources.

3. Configuration interface
    - Introduce one shared config descriptor containing stable keys, types, defaults, bounds, categories, labels, and tooltips.
    - Keep loader/version-native persistence formats to avoid breaking existing installations, with one-time migration for renamed keys such as pointedDripstone*/dripstone*. Validate malformed values and save atomically.
    - Generate built-in vanilla-widget screens for every loader. Register through Forge/NeoForge mod-list hooks and optional Mod Menu entrypoints on Fabric; Cloth Config is no longer required.
    - Apply saved values immediately where safe and clearly mark any option that requires a world or resource reload.

4. Full 1.7.10 backport
    - Recover v1.6 behavior and identifiers from the published classic jar, retaining mixed-case legacy registry IDs or explicit remaps so existing worlds load.
    - Backport the entire modern catalog and player-facing mechanics using Java 8/Forge equivalents: achievements replace advancements, Forge events replace modern callbacks, legacy containers/networking implement the Spectral Infuser, and 1.7 entity/render APIs implement Ghosts and explosives.
    - Use OreDictionary materials where available and documented version-native substitute recipes for post-1.7 ingredients. Do not backport unrelated vanilla systems or require third-party content mods.
    - Supply a migration table for every classic item/block ID and verify a v1.6 save upgrades without missing mappings.

5. Documentation and release assembly
    - Replace the contradictory README/FUTURE_WORK text with the exact matrix, feature catalog, optional-integration table, Quilt test results, classic substitution recipes, build instructions, and known limitations.
    - Add root CC0 license, 2.3.0 changelog, compatibility/migration guide, contribution instructions, and an artifact manifest containing game version, loader, Java requirement, dependency bounds, byte size, and SHA-256.
    - Stop at release-ready artifacts and documentation. Do not upload, publish, tag, or modify CurseForge, Modrinth, or GitHub releases.

## Test and Acceptance Gates

- Unit-test the content generator, config validation/migration, damage calculations, target predicates, recipe transformations, and classic semantic adapters.
- Run GameTests on every modern loader for registration, representative recipes and tiers, Spectral Infuser transformations, Ghost lifecycle and armor interactions, explosives, config gating, effects, worldgen, and legacy-world loading.
- Give 1.7.10 equivalent automated Forge integration tests plus dedicated-server and client scenarios where GameTest does not exist.
- For every artifact: clean build, clean datagen diff, jar-content audit, complete source-jar audit, dedicated-server boot, client boot/world join, config-screen smoke test, and shutdown/port cleanup.
- Test optional integrations both absent and present. Integration absence must never prevent startup.
- Run each Fabric jar under the matching Quilt loader and record pass/fail without creating a separate binary.
- Perform visual checks for the Spectral Infuser, Ghost renderer/animation, armor textures, config screens, JEI/WTHIT views, and representative creative-tab families.
- Upgrade representative 2.2.x worlds/configs and the published 1.7.10 v1.6 world. Acceptance requires zero missing registry mappings, recipe/model/texture errors, mod-load exceptions, or unexplained warnings.
- The completion gate is all 16 binaries and source jars passing their applicable matrix, with reproducible resources, checksums, and release documentation prepared locally.         