# Oculus Community Port to Minecraft Forge 26.2

Status: implementation plan and repository audit  
Target branch: `port/forge-26.2`  
Target coordinate: `net.minecraftforge:forge:26.2-65.1.0`  
Target Java: Java 25  
Planned artifact: `oculus-forge-26.2-<version>.jar`

This document was produced before implementation changes. It describes an unofficial
community port. It is not an official Oculus, Iris, Forge, Sodium, Embeddium, or
Mojang release.

## Implementation checkpoint - 0.2.0-alpha.1

The reproducible build, secure pack core, Forge client lifecycle, configuration
UI, backend safety policy, and a functional OpenGL full-screen frame-graph
pipeline are now implemented. Compatible `deferred*`, `composite*`, and `final`
programs compile and render in a Forge 26.2 world. Common uniforms, main color
and depth input, reversed-depth sample conversion, resizing, reload activation,
and failure-to-vanilla behavior are present.

The terrain/entity gbuffer bridge, shadows, additional targets and textures, and
the full OptiFine/Iris semantics remain open. Accordingly this checkpoint is a
functional alpha, not completion of the production plan.

## 1. Verified target facts

- Mojang's 26.2 version manifest declares `java-runtime-epsilon`, Java major version
  25.
- The official Forge 65.1.0 MDK targets Java 25, uses ForgeGradle
  `[7.0.17,8)`, and ships Gradle 9.5.0.
- Forge Maven contains `net.minecraftforge:forge:26.2-65.1.0`. The official
  65.1.0 changelog identifies it as the 26.2 recommended build 1.
- Minecraft 26.2 supports OpenGL and an experimental Vulkan backend.
  `RenderSystem.getDevice().getDeviceInfo().backendName()` is the supported runtime
  backend identifier.
- Minecraft 26.2 uses reversed depth. The default depth clear is `0.0`, and the
  default depth comparison is `GREATER_THAN_OR_EQUAL`.
- World rendering is assembled through `FrameGraphBuilder`. Forge 65.1.0 exposes
  `AddFramePassEvent` and `FramePassManager.PassDefinition` for additional frame
  passes.

## 2. Repository audit

### 2.1 Input

The supplied `Oculus-1.16.5.zip` contains 672 files:

- 613 Java source files
- 37 JSON files
- 3 JARs, including the Gradle wrapper
- no `src/test` source set
- one main source module plus a `glsl-relocated` Gradle subproject

The source tree is based on Oculus 1.16.5 and Iris-era packages under
`net.coderbot.iris`.

### 2.2 Build and metadata

Original build:

- Architectury Loom `0.10.0-SNAPSHOT`
- Shadow `7.1.2`
- Gradle 7.3.3
- Java 8
- Forge `1.16.5-36.2.29`
- Mojang mappings through Loom
- Rubidium `0.2.13` as compile-only
- `glsl-transformer`, ANTLR, and JCPP bundled/relocated

Original Forge metadata:

- `javafml` loader range `[36,)`
- mod id `oculus`
- LGPL-3.0-only
- no explicit Minecraft, Forge, Rubidium/Embeddium, or OptiFine dependency clauses

Original transformation surface:

- 10 mixin configurations
- over 100 mixin target classes
- one SRG-named access transformer with direct access to old `GlStateManager`,
  `WorldRenderer`, old matrix fields, `NativeImage`, and texture stitching internals

The original access transformer and every old mixin target must be considered
invalid until independently mapped to an existing 26.2 type and method.

### 2.3 Source subsystem counts

| Subsystem | Java files | Approx. lines | Notes |
|---|---:|---:|---|
| Shader packs | 78 | 6,431 | discovery, includes, options, properties, program sets |
| Pipelines | 24 | 3,780 | deferred/fixed pipelines, hand, shadow, transforms |
| Render targets | 9 | 587 | depth, noise, single-color, target collections |
| GL abstraction | 64 | 3,659 | programs, samplers, framebuffers, uniforms, state |
| Shadows | 12 | 757 | targets, culling, frusta |
| Uniforms | 17 | 1,490 | camera, celestial, fog, time, matrices |
| Textures/PBR | 26 | 1,339 | normal/specular atlases, mipmaps, loaders |
| GUI | 24 | 2,370 | selection screen, option widgets |
| Sodium/Rubidium compatibility | 58 | 2,611 | terrain pipeline, vertices, options, shadows |
| Batched entity rendering | 36 | 1,410 | buffers, ordering, render-type wrappers |
| Public API | 4 | 166 | shader state and item-light API |
| Vendored digraph | 22 | 2,495 | Apache-2.0 source headers |
| Vendored expression parser | 28 | 994 | no complete top-level notice in input |

### 2.4 Shader-pack layer

The input contains:

- shader-pack directory and ZIP discovery
- include graph construction and absolute pack paths
- JCPP preprocessing
- shader property preprocessing
- boolean and enum-like string options
- option profiles and option-menu parsing
- program arrays, fallback resolution, and program sets
- block/material maps and legacy IDs
- custom textures and pack directives

Security gaps to close:

- explicit archive entry and uncompressed-size limits
- compression-ratio limits
- canonical virtual-path validation
- bounded include depth and include count
- bounded shader text size and total expanded size
- strict malformed UTF-8 handling
- deterministic errors for cycles and missing includes

### 2.5 Rendering layer

The input directly manages OpenGL framebuffer objects, shader/program objects,
texture objects, samplers, blending, fixed-function state interception, and a
Rubidium chunk shader override. It assumes:

- OpenGL is always active
- forward depth with old clear/comparison conventions
- pre-frame-graph world rendering
- old immediate buffer builders and render types
- direct interception of `GlStateManager`
- Rubidium's 1.16 chunk renderer and vertex formats

None of those assumptions can be carried forward unchanged.

### 2.6 Hooks and coverage

The old mixins hook:

- main world rendering and terrain layers
- entities and block entities
- particles and translucent ordering
- hand, held items, and screen overlays
- sky, sun, moon, stars, clouds, fog, and weather
- render targets and post chains
- textures, atlases, PBR animation, and mipmaps
- GL state tracking
- resource reload and pipeline teardown
- video settings and shader selection UI
- Rubidium chunk building, chunk shaders, shadow chunks, and options
- compatibility for Epic Fight and Pixelmon

### 2.7 Bundled and vendored code

- Project license: LGPL-3.0.
- `de.odysseus.ithaka.digraph`: Apache-2.0 headers.
- vendored JOML subset: MIT headers.
- ANTLR runtime: BSD-3-Clause, but the input JAR does not embed its license.
- JCPP: Apache-2.0 dependency.
- `glsl-transformer`: AGPL-3.0. The modern Iris repository explicitly warns that
  this may affect distribution. It will not be copied or bundled in this port.
- The input's prebuilt `glsl-transformer` and ANTLR JARs will not be reused.
- Any source lacking clear provenance will be removed, replaced, or documented
  before distribution.

## 3. Modern reference implementations

References are for study and selectively adapted code only. Adapted code must retain
copyright and license notices.

| Project | Ref | Commit inspected | License/use decision |
|---|---|---|---|
| Oculus | `1.20.1-new` | `b3b278134f719afe32ba8b6b5d3a93f052175afc` | LGPL-3.0; useful for later Forge entry points and Embeddium integration patterns |
| Iris | `26.2` | `ff6f5aa1174f3bfcb540e405a9c259d29ff42b21` | LGPL-3.0; primary reference for Minecraft 26.2 shader behavior and mappings |
| Embeddium | `20.1/forge` | `dee0ebde6aea8b158613cd3b8ea5cdf0c1f6a9bf` | LGPL-3.0; last official Forge architecture reference, not a 26.2 dependency |

Iris 26.2 has 729 common Java files and only 17 NeoForge-specific Java files.
However, its terrain path requires Sodium 0.9.1. Current Sodium is PolyForm Shield
1.0.0, so its implementation will not be copied, modified, bundled, or represented
as an LGPL-compatible Forge renderer.

Embeddium officially supports Forge only through Minecraft 1.20.1. No authoritative
Forge 26.2 Embeddium artifact was found. Therefore:

1. Rubidium will be removed.
2. No nonexistent Embeddium version will be declared.
3. Sodium-NeoForge will not be declared as a Forge dependency; NeoForge mods are not
   Forge mods.
4. The port must use Forge/vanilla 26.2 chunk rendering integration implemented
   locally, or wait for a legally and technically compatible external Forge renderer.

## 4. Subsystem migration matrix

| Subsystem | Original implementation | 26.2/Forge replacement | Difficulty | Risk | Verification |
|---|---|---|---|---|---|
| Build | Loom + Shadow, Java 8 | ForgeGradle 7.0.17+, Gradle 9.5, Java 25, official mappings | Medium | Medium | clean Windows/Linux wrapper builds |
| Metadata | minimal `mods.toml` | Forge 65 metadata, client-only, exact Minecraft/Forge ranges, OptiFine incompatibility | Low | Low | inspect JAR and Forge discovery log |
| Discovery | filesystem/ZIP scan | bounded virtual pack filesystem; never extract ZIPs | Medium | High | unit tests for malformed and hostile archives |
| Includes | include graph | normalized virtual paths, cycles/depth/size bounds | Medium | High | graph and traversal tests |
| Macros | JCPP plus listeners | Apache-2.0 JCPP only or local bounded preprocessor | High | High | macro corpus and error-location tests |
| Options | legacy parsers | port LGPL parser logic without AGPL transformer | Medium | Medium | option/profile unit tests |
| GLSL transforms | AGPL transformer | independently implemented targeted text/AST transforms using compatible code only | Very high | Critical | shader corpus compile tests |
| Program compilation | direct LWJGL OpenGL | backend-gated OpenGL compiler; no calls on Vulkan | Very high | Critical | compile-error recovery and real GPU tests |
| Backend detection | absent | `DeviceInfo.backendName()` | Low | Low | OpenGL and Vulkan startup tests |
| Vulkan | unsupported | disable shader-pack renderer, preserve vanilla, show clear message | Medium | High | Vulkan startup/world tests |
| Reversed depth | forward-depth assumptions | clear `0.0`, greater comparisons, reversed projection/depth sampling formulas | Very high | Critical | depth visualization packs and occlusion tests |
| Render graph | direct world hooks | Forge `AddFramePassEvent`, frame resources, narrow verified hooks | Very high | Critical | pass ordering and target lifetime tests |
| Main targets | raw FBO lifecycle | `GpuTexture`, `GpuTextureView`, `RenderTargetDescriptor`, frame resources | Very high | Critical | resize/reload leak tests |
| Terrain | Rubidium shader override | local Forge/vanilla `ChunkSectionsToRender` and render-pipeline integration | Very high | Critical | opaque/cutout/translucent terrain matrix |
| Entities | batched buffer mixins | 26.2 feature-renderer/pipeline integration plus minimal hooks | Very high | High | vanilla and modded entity corpus |
| Block entities | dispatcher mixins | 26.2 feature states and Forge events/hooks | High | High | vanilla and modded block entities |
| Particles | particle-engine mixins | 26.2 particle feature pipeline and target selection | High | High | particle stress and translucent tests |
| Hand/overlays | game-renderer mixins | verified 26.2 hand/overlay frame stages | High | High | both hands, maps, fire/water overlays |
| Sky/weather | LevelRenderer hooks | dedicated 26.2 sky/cloud/weather render passes | High | High | toggle and ordering matrix |
| Shadows | second world render | reversed-depth-aware shadow targets and bounded culling | Very high | Critical | shadow distance, off/on, entities, terrain |
| Composite/deferred | direct fullscreen passes | render-graph passes with declared dependencies | Very high | Critical | multi-target shader packs |
| Uniforms | cached GL locations | render-thread cache tied to program lifetime; no per-frame allocation | High | High | capture and shader debug checks |
| Textures/PBR | atlas mixins/raw textures | `GpuTexture` lifecycle and current atlas APIs | Very high | High | normal/specular on/off and reload |
| Resize | FBO recreation hooks | render-thread target descriptors and transactional rebuild | Medium | High | window/fullscreen/GUI scale loops |
| Reload | old resource hooks | `RegisterClientReloadListenersEvent`; prepare/apply phases | Medium | High | repeated F3+T and pack changes |
| World lifecycle | pipeline manager hooks | login/logout, level unload, dimension key transitions | Medium | High | repeated dimensions/worlds |
| GUI | old widgets/Sodium page | native 26.2 `Screen` plus Forge config-screen registration | High | Medium | keyboard, mouse, narration, scaling |
| Compatibility | hard-coded mod mixins | guarded integrations only when a verified API exists | High | High | Forge mod-list matrix |
| Diagnostics | patched shader printer | structured stages, source paths/lines, recoverable failures | Medium | Medium | invalid shader corpus |

## 5. Dependency plan

Pinned build dependencies:

- `net.minecraftforge:forge:26.2-65.1.0`
- ForgeGradle in the official MDK range `[7.0.17,8)`
- Gradle 9.5.0 wrapper
- Java toolchain 25
- JUnit Jupiter, pinned, for pure-Java tests

Runtime:

- no Rubidium
- no fabricated Embeddium coordinate
- no Sodium/NeoForge dependency
- no OptiFine; metadata and runtime checks must report incompatibility
- no AGPL `glsl-transformer`

Potential bundled dependencies must be license-audited, pinned, checksum-verifiable,
and included through supported Jar-in-Jar only when necessary. Relocation is allowed
only where split-package/class collisions are proven.

## 6. Implementation phases and gates

### Phase A: reproducible shell

- Start from the official Forge 65.1.0 MDK.
- Preserve LGPL license and add notices.
- Establish branch `port/forge-26.2`.
- Add deterministic archive names, sources JAR, tests, and artifact verification.

Gate: `gradlew.bat clean build` and `./gradlew clean build` configurations are valid;
the production JAR contains correct metadata and no duplicate classes.

### Phase B: secure shader-pack core

- Port discovery, virtual directory/ZIP access, includes, options, profiles, and
  configuration.
- Add hostile-input tests and bounded error reporting.
- Do not touch rendering until this layer is deterministic.

Gate: unit tests cover traversal, cycles, encoding, oversized data, malformed ZIPs,
and serialization.

### Phase C: loader/UI lifecycle

- Register the Forge config screen and reload listener.
- Add backend detection and Vulkan-safe disable behavior.
- Add world/session lifecycle state without GPU ownership.

Gate: Forge reaches the title screen with no mixins and vanilla rendering unchanged.

### Phase D: OpenGL pipeline

- Implement compatible shader transformations without AGPL code.
- Compile/link on the render thread.
- Own programs, samplers, targets, views, buffers, and teardown transactionally.
- Incorporate reversed-depth conventions.

Gate: invalid shaders recover; a minimal internal shader renders without leaks.

### Phase E: render graph and scene coverage

- Register declared frame passes.
- Integrate terrain, entities, block entities, particles, hand/overlays, sky,
  clouds, weather, shadow, deferred, and composite stages.
- Add PBR textures and target resizing.

Gate: shader-disabled baseline is correct; the full manual matrix is exercised.

### Phase F: compatibility/performance/package

- Test common mods and resource/shader packs.
- Profile hot paths and repeated lifecycle operations.
- Produce production/sources JARs and SHA-256 files.

Gate: every acceptance criterion has direct evidence. Compilation alone is never
sufficient.

## 7. Planned automated verification

- semantic version/range handling
- shader option parsing and merging
- profile parsing
- normalized include resolution
- include cycle/depth/count/size limits
- macro expansion and diagnostics
- directory and ZIP discovery
- ZIP traversal, absolute paths, malformed archives, duplicate entries
- compression ratio and total uncompressed-size limits
- strict UTF-8 behavior
- configuration serialization and atomic persistence
- backend policy
- resource/JAR presence
- duplicate class detection
- no development-only dependencies in production

## 8. Manual verification gates

The manual plan must cover:

- OpenGL and Vulkan startup
- title screen and mods screen
- world creation/load/unload and second startup
- shader enable/disable/switch/reload
- dimensions and resource reloads
- multiple sizes, fullscreen, GUI scale, render distance
- shadows, normal maps, specular maps
- terrain, water, translucency, entities, block entities, particles, sky, clouds,
  weather, hands, and overlays
- invalid shaders and missing/recursive includes
- clean shutdown and log inspection
- Forge-only compatibility matrix and explicit OptiFine rejection

## 9. Stop/acceptance policy

The project must not be called complete while any renderer path is a placeholder,
while no shader pack has rendered in a real world, or while only compilation has
been tested. If a legally usable Forge 26.2 terrain renderer does not exist and the
local replacement is unfinished, the output is an incomplete engineering build,
not a production Oculus port. `KNOWN_ISSUES.md` and `TEST_REPORT.md` must say so
unambiguously.
