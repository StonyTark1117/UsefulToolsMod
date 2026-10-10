# Changelog

## 0.3.0-beta.1 - 2026-08-16

First public beta and renderer-hardening release.

### Improved

- replace active shader generations transactionally; after resource reload, a
  failed replacement recompiles the last-known-good prepared source into a fresh
  GPU generation and reports the degraded state in the config screen
- ping-pong multi-stage rendering between the main and temporary targets,
  reducing full-screen texture copies from one per stage to zero for even stage
  counts or one for odd stage counts
- reuse one command encoder across the ordered full-screen stage sequence
- enforce file-count, total-size, per-file, and symbolic-link policy while
  discovering directory packs without first materializing the whole file list
- enforce a cumulative decompressed-read budget for ZIP pack sessions

### Verified

- 16 compatibility and hostile-pack regression tests on each Minecraft target
- Java compilation with all lint checks and Gradle 9.6 with all warnings enabled
- real-pack preparation remains compatible with Complementary Reimagined r5.7.1
  and BSL v10.1.1

### Release status

This is a beta of the safe full-screen compatibility renderer. Stable 1.0 still
requires terrain/entity gbuffers, shadow rendering, the full attachment/texture
model, a broader pack matrix, and long-duration frametime/leak qualification.


## 0.2.0-alpha.2 - 2026-08-15

Shader-pack discovery and stability update.

### Fixed

- discover active programs under `shaders/world0`, with the root `shaders`
  layout retained for compatibility packs
- preprocess pack conditionals and expanded includes before GLSL adaptation
- accept comma-separated uniform declarations and ignore unused declarations
- support current legacy color/depth attachment aliases and common final-pass
  uniforms used by Complementary Reimagined and BSL
- use Forge's non-deprecated three-argument frame-pass extraction API
- map legacy uniform buffers before opening the GPU render pass, as required by
  Forge 61's command encoder
- enable native access explicitly on Java run/test tasks

### Verified

- Gradle 9.6.0 build with `--warning-mode all` and no deprecation warnings
- six focused shader compatibility/security regression tests
- source preparation of Complementary Reimagined r5.7.1 and BSL v10.1.1
- reproducible production JAR with a relocated, bundled JCPP preprocessor

## 0.2.0-alpha.1 - 2026-07-28

Functional full-screen shader milestone; not a complete Oculus port.

### Added

- Forge 65 `AddFramePassEvent` world-frame integration
- ordered `deferred*`, `composite*`, and `final` full-screen programs
- render-thread program compilation through Minecraft's GPU device
- legacy fragment and fixed-function full-screen vertex adapters
- main color/depth samplers and reversed-depth sample conversion
- common frame, time, world, weather, view, camera, fog, and sky uniforms
- multi-pass temporary target management and window-size-aware descriptors
- live config-driven enable, disable, selection, and resource reload
- recoverable source, compile, and runtime failure handling
- mapped uniform buffer cleanup at replacement and shutdown
- adapter/compiler tests and live Forge world-rendering evidence
- original shader-prism project icon replacing the upstream Oculus logo

### Changed

- OpenGL backend policy now admits the implemented full-screen pipeline
- shader selection UI reports active and failed state
- documentation now distinguishes working full-screen support from missing
  gbuffer/shadow/full-pack support

### Verified

- OpenGL Forge startup and world load
- one-stage and two-stage shader execution
- legacy `final.vsh` compilation
- shaders-disabled vanilla fallback
- 876x536 and 1920x1080 targets
- clean shutdown and repeated startup

## 0.1.0-alpha.1 - 2026-07-28

Secure build and shader-pack input foundation.

### Added

- Java 25, Gradle 9.5.0, ForgeGradle 7.0.31, and Forge 65.1.0 build
- modern Forge metadata, lifecycle, resource reload, and config screen
- OpenGL/Vulkan/unknown detection and OptiFine rejection
- bounded directory/ZIP discovery, include resolution, and source diagnostics
- traversal, archive, decompression, size, cycle, and malformed UTF-8 defenses
- shader option parser, object macro helper, SemVer, and atomic config
- automated tests, artifact checks, sources JARs, and SHA-256 generation

### Removed

- obsolete Loom, Java 8, Forge 1.16.5, Rubidium, CurseMaven IDs, mixins, SRG
  access transformer, bundled JARs, and AGPL shader-transformer dependency
