# Compatibility and migration

## Target matrix

| Minecraft | Java | Forge | Fabric | NeoForge | Quilt |
|---|---:|:---:|:---:|:---:|:---:|
| 1.7.10 | 8 | yes | - | - | - |
| 1.20.1 | 17 | yes | yes | yes | Fabric jar passes on Quilt 0.30.0 |
| 1.20.2 | 17 | yes | yes | yes | Fabric jar passes on Quilt 0.30.0 |
| 1.21.1 | 21 | yes | yes | yes | Fabric jar passes on Quilt 0.30.0 |
| 26.1.2 | 25 | yes | yes | yes | Fabric jar passes on Quilt 0.30.0 |
| 26.2 | 25 | yes | yes | yes | Fabric jar passes on Quilt 0.30.0 |

The 1.20.1 NeoForge line uses NeoForge's transitional `net.neoforged:forge`
artifact and Forge-compatible API namespace. It remains a distinct
NeoForge-built artifact. Its build uses ModDevGradle's legacy-Forge adapter;
the older NeoGradle UserDev adapter misapplies the transitional artifact's
coremods in a named development runtime.

## Stable data

- Namespace: `usefultoolsmod`
- Logical release: `2.3.0`
- Canonical catalog: 638 direct item definitions, 23 block items/blocks, four entities, 87 tool materials, 45 armor materials, 688 recipe definitions, 854 advancements, and 126 config descriptors.
- Renamed config keys migrate from `pointedDripstoneEnabled` and `pointedDripstoneEffects` to `dripstoneEnabled` and `dripstoneEffects` where the persistence format supports aliases.
- Existing registry IDs are retained and documented in [CLASSIC_MIGRATION.md](CLASSIC_MIGRATION.md); prefix-free variants are explicitly remapped. An independently captured published-v1.6 world passes the migration gate with zero missing mappings.

## Minecraft 1.7.10 substitutions

The classic adapter retains the original v1.6 registrations and adds the complete lowercase 2.3.0 catalog. Its generated recipes use documented version-native ingredients rather than requiring content mods or backporting unrelated vanilla systems. The authoritative mapping is [`catalog/classic_1_7_substitutions.json`](catalog/classic_1_7_substitutions.json). Examples include quartz for amethyst, stone/cobblestone for post-1.7 stone families, iron for copper, leather for rabbit hide and phantom membrane, End-themed pearls for chorus/shulker/echo materials, and the original Useful Tools iron nugget for modern iron-nugget recipes. Modern blasting recipes share their equivalent 1.7 furnace recipe.

RetroFuturaGradle `1.4.0` was requested in the original plan but has no published plugin marker in the upstream repository. The classic build therefore pins the final available 1.x release, `1.4.9`, with Gradle 8.8; Gradle runs on Java 21 and the Minecraft/mod toolchain remains Java 8.

## Optional integrations

JEI, WTHIT/Jade, REI, JER, Magnetization, and Mod Menu are declared or compiled only on lines where compatible upstream APIs exist. Their absence must not prevent the base mod from loading. Mod Menu is only a route to Fabric's built-in config screen; it is not required for configuration persistence.

Create: Ore Excavation support is data-driven on 1.20.1 Fabric and 1.21.1
NeoForge. 1.20.1 Forge is not runtime-certified in this release because the
Forge development profile has not yet been aligned with Create 6's supported
Forge mapping/runtime: the exact published Forge pair reaches Create's mixin
stage but fails refmap translation (`m_21211_` versus `getUseItem`). This
integration is intentionally untested and harmlessly data-only; no Create or
Ore Excavation dependency is required to load Useful Tools.

NeoForge 1.20.1 has no compatible REI runtime: REI 12 publishes a Forge jar
whose Architectury mixins target fields absent from NeoForge 47.1. Useful Tools
therefore keeps the REI and Architectury APIs compile-only on that one line and
offers no incompatible runtime opt-in. Jade 11.13.3 is the supported overlay
profile.

Useful Tools itself has no Architectury runtime dependency. Minecraft 1.21.1
uses three independent native projects; the 1.20.x Forge/NeoForge source sets
retain compile-only Architectury solely because their optional REI APIs expose
Architectury types. Base runtime profiles and packaged metadata exclude it.

## Release status

The runtime, GameTest, datagen-reproducibility, Quilt, visual, integration,
world-upgrade, and full 1.7.10 semantic-backport gates in
[FUTURE_WORK.md](1.21.1/FUTURE_WORK.md) are complete. Version 2.3.0 remains
unreleased only because tagging and publication were explicitly excluded from
this work.
