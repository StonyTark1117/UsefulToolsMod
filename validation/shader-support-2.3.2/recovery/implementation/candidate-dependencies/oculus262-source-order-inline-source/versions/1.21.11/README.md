# Oculus Forge 1.21.11 Community Port

> Unofficial public beta for Minecraft 1.21.11 / Forge 61.2.0

This repository is an independent, LGPL-3.0-only community port. It is not an
official Oculus, Iris, Forge, Mojang, Microsoft, Sodium, or Embeddium release.

Version `0.3.0-beta.1` contains a hardened OpenGL frame-graph shader path. It can
load a shader-pack directory or ZIP, compile compatible legacy `deferred*`,
`composite*`, and `final` programs, execute them in order over the rendered
world, update common uniforms, and return safely to vanilla rendering when
disabled or when compilation fails.

It also discovers the dimension layout used by current packs (`shaders/world0`)
and preprocesses conditional GLSL before compatibility checks. Complementary
Reimagined r5.7.1 and BSL v10.1.1 pass the real-pack preparation audit through
their conservative `final` compatibility path.

This is a beta, not yet the complete Oculus compatibility renderer required for
a stable 1.0. Packs that require terrain/entity gbuffer programs, shadow
rendering, additional color attachments, normal/specular textures, custom
textures, or the full OptiFine/Iris directive language will not work yet. See
[KNOWN_ISSUES.md](KNOWN_ISSUES.md) before testing.

## Target

- Minecraft Java Edition 1.21.11
- Minecraft Forge 61.2.0 (`net.minecraftforge:forge:1.21.11-61.2.0`)
- Java 21
- Gradle 9.6.0 and ForgeGradle 7.0.31
- OpenGL only for shader-pack rendering
- Vulkan detected and rejected with vanilla fallback
- OptiFine detected and rejected

## Implemented

- live Forge 61 `AddFramePassEvent` integration without mixins or access
  transformers
- ordered `deferred*`, `composite*`, and `final` full-screen stages
- compatible legacy fragment and fixed-function full-screen vertex adaptation
- main color and depth sampling, including forward-depth emulation for legacy
  depth reads on Minecraft's reversed-depth target
- common time, frame, camera, fog, sky, weather, view, and world uniform updates
- asynchronous pack preparation and render-thread GPU compilation
- live enable/disable and transactional pack switching through resource reload
- last-known-good source recompilation when a replacement fails after resource reload
- ping-pong multi-stage execution with at most one full-screen texture copy
- cached pipeline descriptors and mapped uniform ring buffers
- safe fallback when source adaptation, compilation, or pass execution fails
- shader-pack directory and non-extracting ZIP discovery
- bounded includes, strict UTF-8, cycle/path traversal/decompression defenses,
  directory budgets, and cumulative ZIP-read limits
- Forge-native configuration screen and atomic configuration persistence
- production and sources JARs, checksums, duplicate-class checks, and required
  resource verification

## Performance and compatibility policy

Shaders-disabled rendering leaves vanilla world rendering intact and does not
compile or execute shader programs. Enabled rendering uses Minecraft's GPU
abstraction and Forge frame graph, keeps source and archive work off the render
thread, caches stable descriptors, and avoids per-frame pack I/O or compilation.

Rubidium is not used: it is obsolete for this target. No authoritative
Forge-compatible Embeddium/Sodium release for Minecraft 26.2 was found. This
beta therefore integrates with Forge/vanilla locally rather than fabricating a
dependency or copying incompatible Sodium code. Future renderer integrations
must be optional, API-guarded, and license-compatible.

The compatibility goal remains unmodified ShadersMod/OptiFine packs, but this
release only implements the full-screen portion of that model. Legacy color
attachments use the current frame color and `noisetex` uses a deterministic
nearest-filter fallback because Forge does not expose the complete Iris target
set. Unknown active samplers, outputs, and uniforms still fail with a diagnostic
and vanilla fallback.

## Build

Use a Java 21 JDK:

```powershell
.\gradlew.bat clean build
.\gradlew.bat packageRelease
```

Linux:

```bash
./gradlew clean build
./gradlew packageRelease
```

Artifacts:

- `build/libs/oculus-forge-1.21.11-0.3.0-beta.1.jar`
- `build/libs/oculus-forge-1.21.11-0.3.0-beta.1-sources.jar`
- matching `.sha256` files

The development branch is `port/forge-1.21.11`.

## Test

Place the binary JAR in a Forge 61.2.0 Minecraft 1.21.11 `mods` folder. Put shader
packs in the instance's `shaderpacks` directory and select them through
Mods -> Oculus -> Config.

The included runtime evidence covers OpenGL startup, title/world loading,
single- and two-stage shader execution, legacy `final.vsh`, shaders-disabled
fallback, window-size target recreation, repeated startup, and clean shutdown.
It does not cover a representative real-world shader-pack matrix or every
acceptance scenario. Exact evidence is in [TEST_REPORT.md](TEST_REPORT.md), with
remaining procedures in [MANUAL_TEST_PLAN.md](MANUAL_TEST_PLAN.md).

## License and attribution

The port is licensed under LGPL-3.0-only; see [LICENSE](LICENSE),
[LICENSE_NOTES.md](LICENSE_NOTES.md), and [NOTICE](NOTICE). Corresponding source
is this source tree and the generated sources JAR.
