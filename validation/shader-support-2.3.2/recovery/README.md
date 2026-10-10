# Ghost/Wraith recovery audit — October 4, 2026

The project-wide shader goal remains **incomplete**. The quoted correction describes partial coverage; it does not meet the repository's completion contract. `acceptance-matrix.json` retains all 16 maintained artifacts and five Fabric-on-Quilt rows. No target, stack or acceptance requirement was waived. Several gaps were recoverable harness/research problems, as the fresh results below demonstrate.

The [matrix expansion audit](implementation/research/acceptance-matrix-explicit-stacks-20261008.json)
retains all 47 prior stack checks and adds 13 explicit, incomplete checks for
the named NeoForge/Forge OptiFine and Zenkai configurations. The matrix now has
60 stack checks across the same 21 target rows; the added checks record
requirements, not completed validation.

## Current checkout update — October 8, 2026

The pinned 26.2 Forge 65.1.2 / original OptiFine K2 preview / BSL 10.1.2
[private-bridge trial](implementation/stack-revisit/26.2-forge-optifine-publisher-bridge-active-bsl-outline-v5/visual-review.json)
completed menu entry, both entity scene/control sequences, shader off/on toggles,
surface captures, and clean shutdown after narrowly repairing the publisher's
inert Shaders button, missing GUI blit overload, and block-outline render-state
constructor in the diagnostic bridge. **It fails visual acceptance:** all twelve
active-pack Ghost/Wraith scene PNGs are solid black, while shader-off frames are
visible. The active pack logged OpenGL uniform errors. The private bridge is not
an exact publisher-stack pass; the report's `captures_complete` status only
describes harness flow. Earlier failed/crashed trials remain failed.
That trial used artifact `957094d6…`, while the current 26.2 Forge main artifact
is `a7c6ee9e…`; the [entry comparison](implementation/build/forge262-main-payload-comparison-20261008.json)
shows a changed Wraith renderer and added shader texture. Its black-frame
finding is diagnostic for the older artifact and cannot certify current-artifact
behavior.
The [single-scene current-artifact retry](implementation/stack-revisit/26.2-forge-optifine-current-a7c6-bsl-indoor-diagnostic-v1/report.json)
used `a7c6ee9e…`, the durable VirtualGL and dependency copies, pinned Forge
65.1.2, the original OptiFine K2 preview, BSL 10.1.2, and the private bridge.
It entered the world, logged active shaders, accepted 21 commands, and shut down
cleanly, but its fresh Wraith indoor image is fully black. The new harness
returned `visual_failed` and exit code 1. This is current-artifact failure
evidence for one scene, not a full configuration run or a publisher pass.

The packaged harness now marks future completed runs `visual_failed` when a
required Ghost/Wraith scene PNG is fully black. The focused check detects all
twelve black scenes in this saved 26.2 run and none in the saved 26.1.2 Forge
BSL private-bridge run; it does not replace visual review for other defects.

The [1.20.2 Forge private outline candidate's paired frame-time trial](implementation/research/optifine1202-outline-paired-performance-v3.json)
has three 1800-frame repeats per state with warm-up and uncapped FPS. Its raw
files and report hashes are present. Outlined repeat medians increased by
0.41–0.45 ms with the trial enabled, while paired captures darkened Wraith's
interior, so that trial was rejected. This does not resolve the original
Complementary outline requirement or establish a 1.21.1 result.

All 16 maintained renderers, including classic Forge, now select a separate Wraith
UV texture only while a shader pack is active. The original no-shader texture is
unchanged; the [source/PNG audit](implementation/build/shader-texture-source-audit-20261008.json)
records both hashes and the 890 newly visible UV pixels per target. The
[warning-gated build matrix and frozen artifact hashes](implementation/build/matrix-shader-texture-20261008-provenance.json)
and [16 dedicated-server smoke checks](implementation/server-shader-texture-20261008/provenance.json)
passed on this checkout. Those checks establish build and side separation, not
client visual acceptance on all targets. Earlier captures of changed artifacts
cannot certify this rebuild.

Two 26.2 Forge lighting-companion files changed after that matrix build and two
source files were added. The affected [pinned warning-gated rebuild](implementation/build/forge262-current-companion-20261008.log)
and [isolated server smoke](implementation/server-forge262-current-companion-20261008/26.2-forge.json)
passed; [new hashes and source provenance](implementation/build/forge262-current-companion-20261008-provenance.json)
identify the current companion. The main mod JAR has the same SHA-256 and entry
payloads as the matrix artifact. The [current all-target source audit](implementation/durable-inputs/current-all-target-provenance-audit.json)
found no source-file or artifact-hash mismatches across all sixteen targets.

[Durable, byte-verified input copies](implementation/durable-inputs/manifest.json)
now retain the VirtualGL 3.1.5 runtime, publisher/pack inputs, QA probes,
artifacts, and world fixtures outside the temporary cache. The
[preflight path audit](implementation/durable-inputs/preflight-path-audit.json)
maps 34 cache references in eleven prepared commands to these copies; it is a
path/provenance check, not a runtime qualification.

On pinned Forge 64.0.8, the rebuilt artifact completed isolated, active
[BSL 10.1.2](implementation/stack-revisit/26.1.2-forge-optifine-k1-source-shader-texture-bsl-scenes-toggle-v50/scoped-audit.json)
and [Complementary Reimagined 5.7.1](implementation/stack-revisit/26.1.2-forge-optifine-k1-source-shader-texture-complementary-scenes-toggle-v54/scoped-audit.json)
trials with OptiFine K1 pre2 **plus a private admission bridge and donor renderer**.
Both entities have visually reviewed noon, midnight, rain, dark/bright backdrop,
and indoor captures; both off/on toggles, accepted scene/removal commands, live
shader state, and clean game shutdown are recorded. Wraith's body remains dark
and readable at the provisional floor 13; disabling the pack restores the
original silhouette. These are scoped private-stack results, not qualification
of bare publisher OptiFine. Entity-state controls, materials/GUI, lower-floor
comparison, other stacks, and the entire acceptance matrix remain open. Failed
intermediate menu-OCR runs remain failed with their saved frames and analyses.

The rebuilt 1.20.2 NeoForge artifact also completed serial active-pack runs
with a **private** native OptiFine compatibility candidate: [BSL 10.1.2](implementation/stack-revisit/1.20.2-neoforge-optifine-native-http-defaults-BSL-shader-texture-current-v1/visual-review.json)
and [Complementary Reimagined 5.9.3](implementation/stack-revisit/1.20.2-neoforge-optifine-native-http-defaults-Complementary-shader-texture-current-v1/visual-review.json).
Both captured the requested entity scenes, toggles, native states and material/GUI
fixtures with accepted commands and clean game exit. [Paired current-artifact
evidence](implementation/research/neoforge1202-optifine-outline-cross-pack-shader-texture-20261008.json)
shows BSL's native Glowing outline for both entities, but no visible outline
under Complementary despite accepted commands. The private candidate is not the
original publisher integration; the exact stack and failed Complementary state
control remain open.
[An isolated one-line Complementary diagnostic](implementation/research/neoforge1202-complementary-outline-alpha-diagnostic-result.json)
did not restore the missing outline. With the [untouched pack and a vanilla
sheep](implementation/research/neoforge1202-complementary-original-vanilla-outline-control-20261008.json),
the outline is absent while shaders are active and reappears after disabling
them. The defect is therefore broader than either mod entity renderer in this
private stack; no shader-pack or production change was promoted.
The [matched BSL and Complementary vanilla sheep controls](implementation/research/neoforge1202-optifine-vanilla-outline-pack-pair-20261008.json)
show that BSL retains the active outline on the same artifact and private
integration. This narrows the failure to the pack-dependent active rendering
path, but does not establish a production repair.
An [original Complementary 5.7.1 sheep control](implementation/research/neoforge1202-complementary-571-original-vanilla-outline-control-20261008.json)
also lacks the active outline and restores it when disabled, so that older
pack version does not resolve the captured defect.

## Reproduction and isolation

The current implementation and validation attempts are under [`implementation/`](implementation/).
The sections below retain the earlier recovery history; its base-only snapshots
do not describe the newly built checkout. All 16 actual checkout builds, with
the companion configuration retained and repository warning gates, passed in
[`matrix-floor13.log`](implementation/build/matrix-floor13.log). All 16 server
smoke checks passed in [`server-floor13/results.json`](implementation/server-floor13/results.json).
Subsequent classic/Fabric 1.20.2 companion changes were rebuilt and server-tested;
[`current-companion-payload-comparison.json`](implementation/build/current-companion-payload-comparison.json)
records identical main-mod entry payloads and the new archive hashes.

Wraith now has independent shader lighting on all 16 artifacts, including the
previously missing Fabric renderer hooks. The default floor is provisionally 13;
Ghost retains its established floor and native no-shader paths. Live state checks
cover Iris/Oculus, OptiFine, and the community port's actual service API. The
restrained mask trials were rejected visually; no extra pass was added to release
code. Their captures and repeated 1800-frame comparisons remain under
`implementation/mask-sole-egl-1800-*`; failed native shutdown attempts remain failures.

VirtualGL EGL rendering now presents fresh framebuffers to owned Xvfb, with
verified device/interposer routing and null audio. The current serial runs use
[`precipitation-control-configurations.json`](implementation/precipitation-control-configurations.json).
Every target remains present. Per-capture reviews and the
[`coverage audit`](implementation/precipitation-evidence-coverage.json) retain missing
checks, including both lantern registry entries and native inventory previews.
Earlier six-block captures do not certify the expanded lantern fixture.

The exact upstream Oculus 1.20.2 source was adapted privately to NeoForge
20.2.93 with native Embeddium 0.2.10. Disabled, BSL, and Complementary packaged
runs completed automation and clean shutdown. Visual coverage remains partial.
[`Source/patch provenance`](implementation/research/oculus-1202-neoforge-qa2-artifact.json)
and the [rebuild script](../../../scripts/build-spectral-oculus1202-neoforge.py)
retain the experiment; its fresh reproduction has identical jar entry payloads.
This is not a publisher release. OptiFine 26.x and Forge 26.1.2 stack investigation,
classic model parity, remaining shader/state/surface checks, and final matrix
reconciliation remain unfinished.

[`scripts/validate-packaged-spectral.py`](../../../scripts/validate-packaged-spectral.py) copies a QA profile and world into a private Prism launcher root, allocates owned Xvfb at 1280x720x24, removes inherited Wayland routing, and sets `ALSOFT_DRIVERS=null`. Input targets that verified client/display. `--gpu-zink` uses the Radeon GPU through Mesa Zink with Kopper presentation disabled; `glxinfo.txt` records each actual renderer. Fresh Minecraft F2 files supply the post-shader framebuffer, since the X11 surface can stay black. Sound was not tested. Reports save exact components, SHA-256 hashes of the mod/dependencies/pack, acknowledged commands, captures, and shutdown/cleanup results. Launcher logs and credentials remain private outside this evidence directory.

For 1.21.1 Forge, use `--menu-world`, then enter Singleplayer, select the copied New World, and click Play Selected World. Inspect a fresh F2 frame before each input. If the experimental-world warning appears, select “I know what I'm doing!” only on the owned virtual display. At the tested 854x480 window these controls were Singleplayer (426,237), world entry (390,132), Play (270,396), and confirmation (586,274). Coordinates are reproduction details, not permission to skip verifying the current page/window. 1.20.1 OptiFine was also entered through these menus; it did not need experimental confirmation.

The disposable sources for pinned Forge base builds are `/tmp/ghost-validation-source-20261004/{1.20.1,1.21.1}/forge`. Templates are the private copies of `codex-shader-1201-neoforge-oculus`, `codex-shader-1201-forge-optifine-47218`, `codex-shader-1211-forge-optifine-52016`, `codex-shader-1201-quilt` (Minecraft/intermediary/LWJGL changed to 1.21.1; Java 21 and version-matched QA world), `codex-shader-2612-fabric`, and `codex-shader-262-fabric`. The 26.1.2 NeoForge run used the 26.2 NeoForge template with Minecraft changed to 26.1.2 and the version-matched Fabric QA world. These template names do not describe the resulting loader version: each report's components do.

## Code/build findings

The working tree is shared with a separate optional dynamic-lighting companion implementation. Its Gradle script initially failed configuration. To avoid changing that work, the pinned Forge snapshots omit only the companion apply line. Strict build logs and per-file source hashes identify the base-only code tested. These builds do not certify the companion or the complete current checkout.

Initially, restoring native `ResourceLocation` factories eliminated strict compilation warnings on pinned 1.20.1 Forge. The subsequent pinned OptiFine I6 test exposed `NoSuchMethodError: ResourceLocation.fromNamespaceAndPath`: OptiFine replaces that class with one without Forge's newer factories. `CompatibleResourceLocations` now uses the retained constructors, with narrowly scoped deprecation/removal suppression explaining that binary compatibility requirement. Its call sites preserve the same IDs. `pinned-1201-compatible-strict.log` and `1.20.1-base-source-hashes-compatible.json` identify the final rebuilt snapshot. Earlier 1.20.1 captures certify their recorded artifact hashes, not this changed artifact.

On pinned 1.21.1 Forge, Quick Play world entry left `ForgeInternalHandler`'s loot modifier manager uninitialized. The initial entity-removal command then threw `IllegalStateException: Can not retrieve LootModifierManager until resources have loaded once`. Exact Forge sources/signatures and private JVM exception logging established the failing path. Normal menu entry initializes it successfully: both OptiFine and ForgeOculus then remove the test entities without that error. ForgeOculus's initial null-dimension crash was also absent with normal menu entry. The experimental reflection workaround was removed from the snapshot before the successful runs. `pinned-1211-normal-menu-strict.log` and `1.21.1-base-source-hashes-normal-menu.json` identify that build.

The Quilt harness was corrected to update Intermediary and LWJGL along with Minecraft. A false crash assertion that matched the module name “Fabric Crash Report Info” was narrowed to actual crash-report messages. Concurrent Xvfb allocation later collided; one affected run failed before scene captures. Those attempts are retained. The harness now reserves display numbers atomically and verifies the Xvfb lock PID and framebuffer before launching a client. Runs with `-reserved` in their name use that repair.

## Fresh evidence

Each completed run below has `report.json`, `client.log`, `glxinfo.txt`, Xvfb logs and actual screenshots. Visual reviews are separate in `visual-review.json`; a green harness status means commands/captures/exit succeeded, not automatic acceptance of readability or all requirements.

- [Forge 47.4.10 + Oculus 1.8.0/Embeddium 0.3.31 + BSL 10.1.2](1.20.1-forge-47410-oculus180-bsl1012-final/report.json): 12 scenes and clean exit, reviewed. Predates the constructor compatibility helper.
- [Same pinned stack + Complementary Reimagined 5.9.3](1.20.1-forge-47410-oculus180-complementary593-toggle/report.json): 12 scenes plus four shader toggle captures and clean exit, reviewed. Predates the helper.
- [Forge 52.1.10 + OptiFine J1 + BSL 10.1.2](1.21.1-forge-52110-optifineJ1-normal-menu-bsl1012/report.json): 12 scenes, entity removal and clean exit, reviewed.
- [Forge 52.1.10 + ForgeOculus 1.8.8/Beddium 1.0.11 + BSL 10.1.2](1.21.1-forge-52110-forgeoculus188-normal-menu-bsl1012/report.json): 12 scenes, four toggle captures, entity removal and clean exit, reviewed, with no experimental dimension workaround.
- [NeoForge 26.1.2.59-beta + Iris 1.11.4/Sodium 0.9.2 + Complementary 5.7.1](26.1.2-neoforge-pinned-complementary571-scenes/report.json): 12 scenes, entity removal and clean exit, reviewed. The prior development-runtime sampler crash did not recur in this packaged-client test.
- [1.21.1 Quilt 0.30.0 + Iris 1.8.8/Sodium 0.6.13 + BSL 10.1.2](1.21.1-quilt030-iris188-sodium0613-bsl1012-retry/report.json): 12 scenes, four toggle captures and clean exit, reviewed.
- [26.1.2 Quilt 0.30.0 + Iris 1.11.4/Sodium 0.9.2 + BSL 10.1.2](26.1.2-quilt030-iris1114-sodium092-bsl1012/report.json): 12 scenes and clean exit, reviewed.
- [26.2 Quilt 0.30.0 + Iris 1.11.4/Sodium 0.9.2 + BSL 10.1.2](26.2-quilt030-iris1114-sodium092-bsl1012/report.json): 12 scenes, four toggle captures and clean exit, reviewed.

- [Rebuilt Forge 47.4.10 + OptiFine I6 + BSL 10.1.2](1.20.1-forge-47410-optifineI6-compatible-bsl1012/report.json): constructor helper included, 12 scenes and clean exit, reviewed.
- [Rebuilt Forge 47.4.10 + Oculus/Embeddium + BSL 10.1.2](1.20.1-forge-47410-oculus180-compatible-bsl1012-reserved/report.json): helper included, atomic display reservation, 12 scenes plus four toggle captures and clean exit, reviewed.

- [Rebuilt Oculus/Complementary reserved-display attempt](1.20.1-forge-47410-oculus180-compatible-complementary593-reserved/report.json): 12 scenes/four toggles and clean exit, but the reviewed Ghost bright-background frame has unexpected colored artifacts. This is runtime evidence with an unresolved visual defect, not a clean visual pass. The single-client repeat below did not reproduce those artifacts; their cause has not been established.

- [Rebuilt Oculus/Complementary single-client repeat](1.20.1-forge-47410-oculus180-compatible-complementary593-single/report.json): 12 scenes, four toggle captures and clean exit, reviewed. The earlier colored floor/particle artifacts did not recur; this does not establish their cause.

Ghost's features remain visible in these reviewed scenes. Wraith's face and separated arms are visible, but it is markedly dimmer at night and indoors, especially the 1.21.1 Quilt night frame. This remains an open readability acceptance concern; static captures are not a blanket release pass.

## Remaining scope

Every target still needs complete evidence against the original acceptance criteria for every required exact stack: baseline, installed-disabled, both packs, toggles, all scenes, other audited mod surfaces, code provenance and readability. The original plan's extra-pass trials and conditional movement/damage/invisibility/outline/spectator and frame-time checks also require an evidence-based disposition. A static entity capture does not establish those behaviors. Newer or older captures cannot certify another artifact/hash.

1.20.2 Forge/NeoForge and 26.1.2 Forge still require version-matched shader-stack repair and full testing. The 26.2 Forge publisher community port has now been exercised on pinned Forge 65.1.2: [current-artifact BSL 10.1.1](implementation/stack-revisit/26.2-forge-community-publisher-companion-current-bsl-full-v1/scoped-audit.json), [Complementary 5.7.1](implementation/stack-revisit/26.2-forge-community-publisher-companion-interpolation-complementary-full-v2/scoped-audit.json), the [no-loader baseline](implementation/stack-revisit/26.2-forge-community-current-baseline-full-v1/scoped-audit.json), and [installed-disabled mode](implementation/stack-revisit/26.2-forge-community-publisher-companion-current-disabled-full-v1/scoped-audit.json) each pass their scoped audits. The optional source-maintained lighting companion makes the original Complementary pack's final stage activate. These pack runs demonstrate a full-screen stage, not pack-driven terrain/entity shader-pipeline coverage. Lack of a located stack is not an authorized scope reduction or an established external blocker. No unfinished row was marked not applicable or complete.

## Version-matched primary sources

- [ForgeOculus](https://www.curseforge.com/minecraft/mc-mods/forgeoculus) specifies Beddium. [Archived Beddium builds](https://github.com/peaksuperior885/Thorbedium/releases) are linked by its publisher; release metadata is saved in `research/thorbedium.json`.
- [Oculus Community Port source](https://github.com/Khaliiid0/Oculus-Community-Port) and [published 26.2 beta](https://www.curseforge.com/minecraft/mc-mods/oculus-community-port/files/8661452) describe limited full-screen shader processing, not the complete Iris world/entity pipeline. Its existence requires evaluation and does not establish an entity visual pass.
- Modrinth publisher metadata for Iris, Sodium, Oculus and Embeddium is retained in `research/*.json`. Missing Quilt tags alone did not establish incompatibility, as the fresh runtime evidence demonstrates.
- Quilt's official loader/game metadata is saved with source URLs in `research/quilt-loader.json` and `research/quilt-game.json`; Prism's current Quilt beta descriptor is also saved. The successful compatibility runs use the contract's Quilt 0.30.0, not that alternative beta.
