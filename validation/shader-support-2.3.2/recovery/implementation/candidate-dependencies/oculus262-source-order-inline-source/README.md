# Oculus Community Port

> Unofficial client-side Forge shader port for Minecraft 1.21.11 and 26.2.

This repository contains the complete corresponding source for every
`0.3.0-beta.1` binary published by the Oculus Community Port project. It is
licensed under `LGPL-3.0-only` and is not an official Oculus, Iris, Forge,
Mojang, Microsoft, Sodium, Embeddium, OptiFine, or shader-pack release.

## Corresponding source map

| Published binary | Minecraft / Forge / Java | Source directory |
|---|---|---|
| `oculus-forge-26.2-0.3.0-beta.1.jar` | 26.2 / 65.1.0 / Java 25 | Repository root |
| `oculus-forge-1.21.11-0.3.0-beta.1.jar` | 1.21.11 / 61.2.0 / Java 21 | [`versions/1.21.11`](versions/1.21.11) |

Each source directory contains its own Java sources, resources, tests, Gradle
build scripts, Gradle wrapper, dependency notices, license notices, and the
relocated JCPP license. The generated `-sources.jar` for each published binary
is also provided as an additional file with that version.

## Release status

Version `0.3.0-beta.1` is the first public beta. It provides a hardened OpenGL
frame-graph path for compatible full-screen shader-pack stages. It discovers
programs under both the root `shaders` layout and `shaders/world0`, preprocesses
conditional GLSL, updates common uniforms, replaces packs transactionally, and
returns safely to vanilla rendering when a pack cannot be compiled or executed.

Complementary Reimagined r5.7.1 and BSL v10.1.1 passed the real-pack preparation
and compatible `world0/final` activation tests on both supported Minecraft
targets.

This remains a beta. Terrain/entity gbuffers, complete shadow rendering, the
full attachment and custom-texture model, complete OptiFine/Iris semantics, and
Vulkan shader-pack rendering are not implemented. Pack activation therefore
does not imply complete Iris/Oculus visual parity. See
[`KNOWN_ISSUES.md`](KNOWN_ISSUES.md).

## Implemented

- client-only Forge integration through the current frame graph
- deterministic compatible `deferred*`, `composite*`, and `final` stages
- legacy full-screen vertex and fragment adaptation
- `shaders/world0` and root `shaders` program discovery
- bounded includes and conditional GLSL preprocessing
- common frame, time, camera, fog, sky, weather, view, and world uniforms
- asynchronous pack preparation and render-thread GPU compilation
- transactional pack switching with last-known-good source recovery
- ping-pong multi-stage rendering with at most one final texture copy
- mapped uniform ring buffers and cached render-target descriptors
- secure directory and non-extracting ZIP pack loading
- traversal, symlink, archive-size, compression, UTF-8, and include limits
- Forge-native configuration screen and atomic configuration persistence
- reproducible production and sources JARs with SHA-256 sidecars

## Build Minecraft 26.2

Use a Java 25 JDK from the repository root:

```powershell
.\gradlew.bat clean build --warning-mode all
.\gradlew.bat packageRelease
```

Linux or macOS:

```bash
./gradlew clean build --warning-mode all
./gradlew packageRelease
```

## Build Minecraft 1.21.11

Use a Java 21 JDK:

```powershell
cd versions\1.21.11
.\gradlew.bat clean build --warning-mode all
.\gradlew.bat packageRelease
```

Linux or macOS:

```bash
cd versions/1.21.11
./gradlew clean build --warning-mode all
./gradlew packageRelease
```

## Test

Run the focused compatibility and hostile-input test suite from either source
directory:

```powershell
.\gradlew.bat test --warning-mode all
```

The beta passed 16 automated tests on each Minecraft target and was live-tested
on a Ryzen 5 5625U with integrated AMD Radeon graphics and OpenGL 4.6. See
[`TEST_REPORT.md`](TEST_REPORT.md) and
[`versions/1.21.11/TEST_REPORT.md`](versions/1.21.11/TEST_REPORT.md).

## License and attribution

Oculus Community Port is distributed under GNU LGPL version 3 only. See
[`LICENSE`](LICENSE), [`LICENSE_NOTES.md`](LICENSE_NOTES.md), and
[`NOTICE`](NOTICE).

- Community port and corresponding source: this repository
- Original Oculus: https://github.com/Asek3/Oculus
- Iris: https://github.com/IrisShaders/Iris

The bundled and relocated JCPP 1.4.14 dependency is Apache-2.0 licensed; its
license is included under `third_party` and in the generated artifacts.

## Issues

Report community-port problems here:

https://github.com/Khaliiid0/Oculus-Community-Port/issues

Do not report community-port bugs to the original Oculus, Iris, or shader-pack
developers.
