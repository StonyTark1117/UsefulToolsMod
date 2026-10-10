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
- Current logical release: `2.3.1`
- Canonical catalog: 638 direct item definitions, 23 block items/blocks, four entities, 87 tool materials, 45 armor materials, 688 recipe definitions, 854 advancements, and 126 config descriptors.
- Renamed config keys migrate from `pointedDripstoneEnabled` and `pointedDripstoneEffects` to `dripstoneEnabled` and `dripstoneEffects` where the persistence format supports aliases.
- Existing registry IDs are retained and documented in [CLASSIC_MIGRATION.md](CLASSIC_MIGRATION.md); prefix-free variants are explicitly remapped. An independently captured published-v1.6 world passes the migration gate with zero missing mappings.

## Minecraft 1.7.10 substitutions

The classic adapter retains the original v1.6 registrations and adds the complete lowercase 2.3.1 catalog. Its generated recipes use documented version-native ingredients rather than requiring content mods or backporting unrelated vanilla systems. The authoritative mapping is [`catalog/classic_1_7_substitutions.json`](catalog/classic_1_7_substitutions.json). Examples include quartz for amethyst, stone/cobblestone for post-1.7 stone families, iron for copper, leather for rabbit hide and phantom membrane, End-themed pearls for chorus/shulker/echo materials, and the original Useful Tools iron nugget for modern iron-nugget recipes. Modern blasting recipes share their equivalent 1.7 furnace recipe.

RetroFuturaGradle `1.4.0` was requested in the original plan but has no published plugin marker in the upstream repository. The classic build therefore pins the final available 1.x release, `1.4.9`, with Gradle 8.8; Gradle runs on Java 21 and the Minecraft/mod toolchain remains Java 8.

## Optional integrations

On modern targets, Ghost and Wraith retain their original emissive translucent
render path when no shader pack is active. With an Iris-compatible shader pack
active, they use Minecraft's lit entity render path with a modest minimum block
light level. Their textures contain only opaque and transparent pixels, so this
avoids the washed-out emissive pass while keeping faces readable at night. The
shader integration is optional and has no hard Iris dependency. The 1.20.x and
1.21.1 Fabric renderers and the classic target already use their normal entity
render paths; they were not changed for this fix. Shader pack appearance still
depends on each pack's lighting and bloom settings. See
[the Ghost/Wraith visual validation](validation/ghost-shader-qa/README.md).

JEI, WTHIT/Jade, REI, JER, Magnetization, and Mod Menu are declared or compiled only on lines where compatible upstream APIs exist. Their absence must not prevent the base mod from loading. Mod Menu is only a route to Fabric's built-in config screen; it is not required for configuration persistence.

WTHIT is optional. On supported targets it reports both Ghost and Wraith combat
contracts; Wraith entries include live Ectoplasm Lantern suppression and whether the
player's held weapon satisfies the ectoplasm-infusion damage requirement. No
WTHIT API is packaged on 26.2 Forge because upstream does not publish that
loader/version combination.

Create: Ore Excavation support is data-driven on 1.20.1 Fabric and 1.21.1
NeoForge. The 1.20.1 Forge integration is expected to work in a packaged
runtime, but cannot be certified by the Forge development harness because
Create's mixin/refmap translation is blocked by Forge's dev-environment
limitation (`m_21211_` versus `getUseItem`). The integration remains harmlessly
data-only and no Create or Ore Excavation dependency is required to load Useful
Tools.

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

### Upcoming 2.3.2 lighting

The base mod gives placed Hardened Glowstone normal block light at level 15.
Install the optional **Useful Tools Lighting** companion in the client's `mods`
folder for held, worn, and extended visual lighting. Choose the jar matching
both Minecraft and the loader; keep Useful Tools installed as well. The
companion supplies its own lighting backend and does not require LambDynamicLights
or a shader loader. It does not need to be installed on a dedicated server.

| Source | Activation | Level | Visual radius |
|---|---|---:|---:|
| Ectoplasm Lantern and legacy Soul Lantern | Either available hand | 12 | 12 blocks |
| Hardened Glowstone block, material, and five tools | Either available hand | 15 | 30 blocks |
| Any Hardened Glowstone armor piece | Held or worn in its armor slot | 15 | 30 blocks |
| Placed Hardened Glowstone | Always | 15 | 30 blocks with the companion |
| Ordinary Glowstone reference | Held | 15 | 15 blocks |

Minecraft 1.7.10 has one native hand. Every target checks all of its native
armor slots. Equipment on visible players and living mobs uses its strongest
source; hands and armor do not add their brightness together. Visual light falls
off linearly and remains active underwater. Switching equipment, moving,
removing blocks, unloading chunks, leaving a world, and changing dimensions
update or clear the client field. Lantern and Infuser world lighting remains
unchanged. Spectral crystals, ectoplasm equipment, and idle Infuser items gain
no new emission.

The companion changes renderer light samples and schedules affected render
sections. It does not write server light, saved light arrays, or spawning rules.
The placed block's ordinary level-15 world light still has Minecraft's normal
gameplay effects. Its additional reach is visual.

Optional LambDynamicLights integration uses its documented registration API.
When both companions are present, Useful Tools Lighting owns these mod sources
and registers zero emission with LambDynamicLights to avoid duplicate handling.
The build pins are API 4.4.0 for Fabric 1.20.1, legacy API 2.3.3 for Fabric
1.20.2, API 4.8.0 for Fabric/NeoForge 1.21.1, API 4.11.2 for Fabric/NeoForge
26.1.2, and API 4.12.4 for Fabric/NeoForge 26.2. Runtime verification is recorded
separately from compilation in the
[lighting coverage record](validation/dynamic-lighting-2.3.2/coverage.json).
Fabric-on-Quilt companion checks pass on all five maintained Fabric versions
with Quilt Loader 0.31.0-beta.4, including native equipment, world-light
invariants, disconnect, and clean shutdown. Exact artifacts are recorded in
the coverage record.

Actual renderer-cache checks also pass with Embeddium 0.3.31 on Forge
1.20.1 and [Embeddium 0.2.10 on Forge 1.20.2](https://modrinth.com/mod/embeddium/version/0.2.10%2Bmc1.20.2).
The shader stacks below exercise their applicable Sodium/Embeddium/Beddium
cache paths as well. Repaired private Sodium Forge ports now pass native cache
acceptance on 26.1.2 and 26.2. The earlier startup failures remain recorded.
These private experimental ports are not public installation recommendations.
Classic uses native Forge transformer hooks. On Forge 10.13.4.1614,
Beddium 1.2.5-j8 with FalsePatternLib 1.12.2 and UniMixins 0.3.1 passes
22 native cache cases, ten advanced lighting checks, all 18 held/worn
capture scenes, and fixed-camera source movement/removal without a manual
renderer reload. The fresh [native Beddium review](validation/dynamic-lighting-2.3.2/packaged/beddium-native-cache-dirty-scenes-1.7.10-forge-durable-v2/native-beddium-visual-review.json)
records artifact equality, distance comparisons, isolation, and clean exit.
This baseline profile uses no shader pack; the classic OptiFine shader checks
use separate configurations. These results certify the recorded backend versions.

Hardened Glowstone block, item, tool, and armor textures include LabPBR 1.3
emission maps. Use a shader profile that reads LabPBR material textures. The
verified Fabric 1.21.1 profiles use Iris 1.8.8, Sodium 0.6.13, and
LambDynamicLights 4.8.11 with these matched settings:

| Shader pack | Material and comparison settings |
|---|---|
| BSL 8.4.02.2 / 10.1.2 / 10.1.5 | Advanced Materials on; Material Format 1 (LabPBR); Exposure 0.00; Auto Exposure off; Bloom Strength 1.00 |
| Complementary Reimagined 5.9.3 | Resource Pack Mode 3 (LabPBR); Exposure 1.00; Bloom Strength 0.12; Custom Emission Intensity 100 |

These settings stay constant between ordinary and Hardened Glowstone. The
following exact stacks have reviewed BSL and Complementary captures: placed
blocks at 10, 15, 20, and 29 blocks, every held source, each armor slot worn
individually, and shader toggling. Hardened Glowstone illuminates more of the
corridor; tool shapes, armor silhouettes, and the simple block texture remain
recognizable. Measurements combine shader emission and companion illumination;
they do not isolate material emission or measure physical radiance.

Separate BSL and Complementary material controls pass on all three 1.20.1
loaders, all three 1.20.2 loaders, all three 1.21.1 loaders, all three 26.1.2 loaders,
and all three 26.2 loaders. The 1.7.10 Forge BSL and Complementary controls also
pass with OptiFine E7 and the isolated software renderer.
The Forge 26.x results use the private renderer ports disclosed
below. In these controls,
the companion's renderer light boosts are bypassed after full native acceptance,
LambDynamicLights is absent, and only emission alpha is changed in the control
artifact. All original diffuse images and specular RGB channels remain identical.
Both profiles brighten the block face and every requested held and individually
worn Hardened Glowstone source. The remaining target comparisons are underway;
their status and reviewed images are recorded in the
[material emission certification record](validation/dynamic-lighting-2.3.2/material-emission-certification.json).

On Forge 1.21.1, the companion bridges the two texture trackers in published
Oculus 1.8.8 so its PBR lookup receives registered textures. Both current-artifact
shader profiles and both separate emission-map comparisons pass with the
original Oculus and Beddium jars and the current companion.

| Target | Verified shader backend | BSL version |
|---|---|---|
| 1.7.10 Forge | OptiFine E7 | 8.4.02.2 |
| 1.20.1 Forge | OptiFine I6 | 10.1.2 |
| 1.20.1 Fabric | Iris 1.7.6 + Sodium 0.5.13 | 10.1.2 |
| 1.20.1 NeoForge | Oculus 1.8.0 + Embeddium 0.3.31 | 10.1.2 |
| 1.20.2 Forge | OptiFine I7 pre1 | 10.1.2 |
| 1.20.2 Fabric | Iris 1.6.14 + Sodium 0.5.5 | 10.1.2 |
| 1.20.2 NeoForge | **Private experimental** Oculus 1.8.0 NeoForge QA2 + Embeddium 0.2.10 with native renderer-bounds repair | 10.1.2 |
| 1.21.1 Forge | Oculus 1.8.8 + Beddium 1.0.11 | 10.1.2 |
| 1.21.1 Fabric | Iris 1.8.8 + Sodium 0.6.13 | 10.1.2 |
| 1.21.1 NeoForge | Iris 1.8.14 beta1 + Sodium 0.8.13 | 10.1.2 |
| 26.1.2 Fabric | Iris 1.11.4 + Sodium 0.9.2 on pinned Loader 0.19.2; optional LambDynamicLights 4.11.0 | 10.1.5 |
| 26.2 Fabric | Iris 1.11.4 + Sodium 0.9.2 | 10.1.5 |
| 26.1.2 / 26.2 NeoForge | Iris 1.11.4 + Sodium 0.9.2, matching Minecraft version | 10.1.5 |
| 26.1.2 Forge | **Private experimental** native Iris/Sodium port; both profiles reviewed | 10.1.5 |
| 26.2 Forge | **Private experimental** native Iris/Sodium port; both profiles reviewed | 10.1.5 |

All reviewed Complementary runs use Reimagined 5.9.3; 26.2 uses its recorded
Minecraft 26.2 pack variant. NeoForge 1.20.2's private backend is a test result,
not a claim that the public Forge Oculus jar supports that target. OptiFine
1.20.x profiles retain startup OpenGL and shader-option warnings; their shader
programs load and the requested captures/toggle checks complete. Exact artifacts,
settings, acknowledged equipment, image hashes, visual reviews, and warnings are
recorded in the [shader certification record](validation/dynamic-lighting-2.3.2/shader-certification.json).

Forge 26.1.2's tested OptiFine K1 pre2 fails at `GlStateManager.init`; a full
prepatched experiment fails module resolution, and a bounded bridge experiment
fails native Options/Config initialization. Forge 26.2's OptiFine K2 pre1 fails
at `Identifier(String)`. The community Oculus 26.2 beta lacks terrain/entity
gbuffers, shadows, and LabPBR support; BSL fails native compilation and
Complementary rejects an unsupported uniform. Later private native Iris/Sodium
repairs preserve Forge's actual frustum and weather APIs and supply native pack
metadata. Both ports pass native lighting/cache acceptance. BSL
and Complementary on both Forge versions also complete the distance, equipment,
and shader-toggle checks with reviewed captures and exact native resource formats.
The remaining isolated material comparisons are underway.
The companion's held/worn and
30-block lighting passes on both Forge targets.
Retained attempts and native diagnostics are in the
[unavailable-profile record](validation/dynamic-lighting-2.3.2/unavailable-shader-profiles.json).

All 16 native adapters have passed equipment, movement, water, block placement
and removal, dimension, disconnect, saved-world reopening, server-light-array,
and spawn-eligibility checks in isolated Xvfb. Real two-client loopback sessions
also pass on all 16 targets: 21 stages on 1.7.10 Forge and 34 stages on each
modern target, including replicated equipment, movement, water, removal,
disconnect, and clean shutdown. The equipped-mob checks use native client
fixtures. All 16 companion jars also pass dedicated-server loading.
Per-target logs, captures, hashes, cleanup records, and sampled adapter CPU/FPS
comparisons are kept with the coverage record. These measurements use virtual
displays and do not establish a hardware-independent FPS guarantee. Shader
certification remains incomplete for the remaining isolated material comparisons; those checks remain visible in the
coverage record. LambDynamicLights 4.11.0 declares compatibility with its pinned
Fabric Loader 0.19.2; both pinned shader profiles pass native source ownership, equipment, cache, movement, and cleanup checks with that version.
Versions 4.11.1 and 4.11.2 require Loader 0.19.3, so those alternate-loader runs
do not certify the maintained Fabric 26.1.2 configuration.

Version 2.3.1 repairs the Ectoplasm Lantern and Mining Charge block rendering
and adds Wraith WTHIT coverage. Its matrix gates and manual visual review are
recorded in [FUTURE_WORK.md](1.21.1/FUTURE_WORK.md).
