# Useful Tools 2.3.0 release gate

Version 2.3.0 is release-ready locally but remains unreleased. This file records
the completed acceptance gate. Release tooling stops at local artifacts; it
must not publish to CurseForge, Modrinth, or GitHub.

## Target matrix

| Minecraft | Java | Required artifacts | Source status |
|---|---:|---|---|
| 1.7.10 | 8 | Forge | builds; classic v1.6 baseline recovered |
| 1.20.1 | 17 | Forge, Fabric, NeoForge | all active roots compile |
| 1.20.2 | 17 | Forge, Fabric, NeoForge | all active roots compile |
| 1.21.1 | 21 | Forge, Fabric, NeoForge | all active roots compile/build |
| 26.1.2 | 25 | Forge, Fabric, NeoForge | all active roots compile/build |
| 26.2 | 25 | Forge, Fabric, NeoForge | all active roots build |

Include Quilt support where possible, using existing Fabric builds/no separate Quilt Jars.

Logical version is `2.3.0`. Loader metadata uses `2.3.0+mc.<minecraft>.<loader>`. `scripts/assemble-release.py` produces the canonical `usefultoolsmod-2.3.0-<minecraft>-<loader>.jar` names and matching source jars.

## Implemented

- [x] Active source roots for all 16 target artifacts, without imported caches/run/IDE state.
- [x] Exact plugin/dependency pins in active build files; no snapshot or `+` selectors.
- [x] Per-line Java toolchains and a sequential root build matrix.
- [x] Binary and complete source-jar tasks on every target.
- [x] Generated namespace-aware canonical catalog with 633 direct items, 21 block items/blocks, two entities, 87 tier definitions, 682 recipes, 848 advancements, and 116 config controls.
- [x] Catalog uniqueness/count audit and CI matrix.
- [x] Dependency-free unit tests for catalog source parsing, namespace/resource discovery, config/tier extraction, canonical output, and audit failure modes.
- [x] Matrix-wide static config contract tests: all active modern config implementations expose the canonical 116 options, JSON formats retain atomic/bounded alias migration, and spec formats bound ghost spawning.
- [x] 1.21.1 Fabric JSON config hardening: atomic writes, malformed-value handling, ghost-spawn bounds, and `pointedDripstone*` aliases.
- [x] Dependency-free vanilla-widget config screens for every modern loader; Cloth Config is no longer required.
- [x] Cake config gating fix and removal of stale 1.21.1 no-op event handlers.
- [x] Replace the 1.21.1 Architectury layer and `@ExpectPlatform` adapters with independent loader-native projects and keep client registration out of dedicated-server initialization.
- [x] Path-independent 1.21.1 JSON config tests for legacy aliases, malformed per-key values, disk-level bounds, complete default creation, and atomic temp-file cleanup.
- [x] Loader-native GameTests on all 15 modern targets (96 required invocations), including exact registry counts, representative recipe/tier/worldgen registration, live Spectral Infuser processing, Ghost/Grenade construction, Ghost breeding/feeding/immunity/infused-armor/spawn gating, exactly-once representative damage mutation, and disabled-explosives behavior.
- [x] Remove 1.21.1 Gradle/NeoForge deprecations under project control; document the intentional version-native Holder and fire-table compatibility calls.
- [x] Loader metadata/version normalization and optional dependency declarations.
- [x] Version-native WTHIT descriptors on 1.20.x Fabric (split common/client API for WTHIT 8; single initializer API for WTHIT 9), with WTHIT 8 server registration verified live.
- [x] Local artifact collection, JAR validation, packaged dependency-bound and byte-size manifest, and SHA-256 generation.
- [x] Clean build gate for every target plus a 32-artifact release audit covering ZIP integrity, metadata versions, byte sizes, hashes, Java class levels, assets/data, exact 682/848 recipe/advancement payloads, and non-empty source jars.
- [x] Bounded 16-target dedicated-server smoke runner with readiness detection, JSON/log evidence, thread dumps on timeout, TCP cleanup checks, and exact environment-marked process cleanup.
- [x] Modern-matrix datagen audit runner with hash drift reports, independent 1.21.1 loader targets, and exact process cleanup.
- [x] Complete native 1.21.1 datagen coverage for loot, block/item tags, configured/placed worldgen, registry-driven blockstates/models, recipes, and advancements.
- [x] Root CC0 license, changelog, compatibility guide, contribution guide, and corrected README.
- [x] Published 1.7.10 v1.6 source recovery into a reproducible Forge 10.13.4.1614/Java 8 RetroFuturaGradle 1.4.9 build. The originally requested 1.4.0 plugin marker was never published; 1.4.9 is the final resolvable 1.x release and runs under pinned Gradle 8.8/Java 21 while emitting Java 8 classes.

## Completed release requirements

### Shared behavior and data

- [x] Replace duplicated loader/version registration ID tables with catalog-generated tables. All 15 unique modern source roots consume the same generated 633 item, 21 block, and two entity constants; loader-native factory expressions remain hand-written. The catalog audit rejects stale tables, missing/extra references, or reintroduced ID literals.
- [x] Normalize config descriptors across all native modern roots, including tooltips and reload-required labels. A catalog-driven generator emits the same 116 runtime descriptors into all 15 roots; every built-in screen consumes those labels, categories, tooltips, and reload metadata.
- [x] Audit every effect and bridge for exactly-once damage mutation, target suppression, burn odds, spawn control, and cancellation. The audit found and fixed 1.20.x Fabric's extra-hit damage emulation, incorrect dripstone math, delayed-only targeting suppression, and the ignored `cakeHungerEffects` toggle across 13 roots. Contract tests now require all 50 effect controls to reach behavior code and every modern line to expose its damage, targeting, spawn, and burn bridge; the 1.21.1 GameTests exercise representative mutation/cancellation paths live.
- [x] Verify Spectral Infuser inventory/menu/screen transforms, Ghost AI/spawning/breeding/feeding/damage rules, explosives, tiers, all effects, config gating, and worldgen on every modern loader. Static behavior/config contracts cover every source root and all 50 effect controls; 96 loader-native GameTest invocations plus 16 client scenarios cover representative live behavior. (Ghosts are breedable animals in the recovered implementations, not tameable pets.)
- [x] Run clean datagen for every modern loader and require a zero-diff committed resource tree. All 15 modern targets pass the registered-provider matrix with zero file drift; 26.x Forge uses isolated client/server outputs so one HashCache cannot delete the other side.
- [x] Reconciled the earlier 682/848 audit: the extra pair is the intentional `minecraft:ice` snowball recipe and its recipe advancement, previously omitted by the mod-namespace-only catalog scan.
- [x] Verify optional integrations absent and present. All 15 absent profiles and 25 supported present profiles pass independently (40/40 total), including JEI, WTHIT, Jade, REI, JER, and a joint 1.21.1 NeoForge startup with Magnetization 1.4.3. WTHIT 19/20's broken self-declared JEI entrypoint is not treated as a valid combined upstream profile.
- [x] Add data-driven Create: Ore Excavation support for 1.21.1 NeoForge. The r-gold overworld vein and drilling recipe live in the optional `createoreexcavation` namespace, so the base mod remains loadable without Create or the addon.

### Full classic backport

- [x] Finish backporting the modern player-facing mechanics to 1.7.10. The complete catalog, reactive/aura combat effects, ore generation, material lifecycle effects, ectoplasm phasing, config-gated explosives, Ghost lifecycle, and Spectral Infuser are implemented and pass semantic, server, client, and published-world acceptance.
- [x] Finish the remaining material-effect edge-case comparison. Eight canonical achievements supplement the nine recovered achievements; generated catalog/migration tests and eight Java-8 semantic tests cover recipes, infusion, damage ordering, effect/config mapping, achievements, native-dimension ore rules, and phasing.
- [x] Preserve mixed-case classic registry IDs and remap the prefix-free early-v1.6 variants.
- [x] Generate the exhaustive 67-object classic ID migration table. All post-1.7 ingredient substitutions are machine-readable in `catalog/classic_1_7_substitutions.json`; the generated adapter registers 672 crafting recipes and five furnace transformations (the five duplicate blasting intents collapse into their furnace equivalents).
- [x] Upgrade a representative independently-created published-v1.6 world with zero missing mappings. All five published block IDs survived at their original coordinates; the exact artifact hashes, steps, logs, shutdown, and known Forge-only warnings are recorded in `validation/classic-v1.6-upgrade.md`.

### Automated/runtime acceptance

- [x] Unit tests: 60 dependency-free tests cover catalog generation/audit, Architectury removal, every persistence/config contract, damage and target bridges, recipe generation, classic adapters/migration, runner ownership, integration profiles, RCON framing, and the WTHIT tier-order regression. The classic Gradle suite adds eight Java-8 semantic tests.
- [x] GameTests on every modern loader for representative tiers, Spectral Infuser processing, Ghost lifecycle/armor, explosives, config, effects, and worldgen. All 15 targets pass 96 required invocations; published 2.2.2 legacy-world loading is covered by separate Forge/Fabric/NeoForge upgrade acceptance.
- [x] Complete 1.7.10 automated Forge integration. The reusable matrix runners cover the Java-8 dedicated server and Xvfb client; the client joins a world, opens the 116-option config GUI and Infuser, renders a stable Ghost, and exits cleanly. Eight Java-8 semantic tests cover the behavior paths unavailable to GameTest.
- [x] For all 16 binaries: applicable zero-drift datagen, dedicated-server boot, client boot/world join, config-screen smoke, and confirmed shutdown/port cleanup. The strict clean build and JAR/source audit also pass.
- [x] Run each ordinary Fabric jar under Quilt Loader 0.30.0; all five reach dedicated-server readiness and shut down cleanly without creating Quilt artifacts. The 26.2 metadata runtime floor was corrected from Fabric Loader 0.19.3 to the compatible 0.19.2 exposed by Quilt.
- [x] Visual checks for Spectral Infuser, Ghost renderer/animation, armor textures, all 16 config screens, JEI/WTHIT views, and representative creative/inventory families. Reviewed captures and the interoperability fix they exposed are recorded in `validation/visual-audit.md`.
- [x] Upgrade representative published 2.2.2 Forge/Fabric/NeoForge worlds/configs with zero missing mappings, model/texture/recipe errors, mod-load exceptions, or unexplained project warnings. Evidence is in `validation/modern-2.2-upgrade.md`.
- [x] Remove or explain project-controlled deprecation/unchecked/optional-API annotation warnings. The 26.x sources use loader-aware flammability hooks, server-side damage entrypoints, current collision and NightConfig APIs, and current JEI recipe APIs. Forge's only remaining source-level deprecation is narrowly suppressed and documented because Forge 64/65 deprecates the vanilla advancement provider without shipping the NeoForge-only replacement. Launcher/Gradle native-access notices, old Netty reflection, optional third-party probes, and Minecraft's missing data-fixer notices are external runtime diagnostics rather than project source warnings.

Live results are recorded in `validation/2.3.0-runtime.md` and its linked JSON,
logs, screenshots, and migration reports. The final isolated matrix passes all
16 server targets and all 16 client targets with clean game-layer shutdown,
dimension saves, listener release, and exact launcher cleanup. Optional
integrations pass 40/40 profiles; all five Fabric jars pass unchanged on Quilt
Loader 0.30.0.

## Completion condition

Satisfied locally on 2026-08-25: all 16 binary jars and all 16 source jars pass
their applicable gates, and `scripts/assemble-release.py` produced the audited
32-file set under `release/2.3.0/`. Do not tag or publish as part of this work.
