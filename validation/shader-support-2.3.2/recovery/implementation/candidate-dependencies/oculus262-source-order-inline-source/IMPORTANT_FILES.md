# Important Files

## Build and metadata

- `build.gradle` - Forge build, run configs, sources/checksum packaging, and
  artifact verification
- `gradle.properties` - pinned Minecraft, Forge, ForgeGradle, Java-facing, test,
  and mod versions
- `src/main/resources/META-INF/mods.toml` - Forge metadata and dependency ranges
- `src/main/resources/pack.mcmeta` - Minecraft 26.2 resource metadata
- `src/main/resources/assets/oculus/lang/en_us.json` - UI and diagnostics

## Client lifecycle

- `OculusForge.java` - mod entry, client events, frame-pass registration, shutdown
- `OculusClientService.java` - backend policy, config, pack preparation, activation
- `ShaderPackReloadListener.java` - asynchronous prepare/render-thread apply
- `ShaderPackScreen.java` - Forge configuration and live reload controls
- `GraphicsApi.java`, `BackendPolicy.java` - backend classification and safety

## Shader pipeline

- `render/program/PackProgramCompiler.java` - stage discovery and GPU compilation
- `render/program/LegacyFinalShaderAdapter.java` - bounded legacy fragment bridge
- `render/program/LegacyFinalVertexAdapter.java` - legacy full-screen vertex bridge
- `render/program/PreparedPackPipeline.java` - immutable CPU-prepared generation
- `render/program/PackFinalProgram.java` - compiled program and uniform resources
- `render/program/PackShaderPipeline.java` - ordered live program generation
- `render/pipeline/ShaderPipelineManager.java` - activation, failure, teardown
- `render/pipeline/OculusCompositePass.java` - Forge frame-graph execution

## Pack safety

- `VirtualPath.java` - normalized virtual paths and include resolution
- `DirectoryPackFileSystem.java` - contained real-path directory reads
- `ZipPackFileSystem.java` - bounded non-extracting archive reads
- `ShaderPackRepository.java` - discovery and rejection diagnostics
- `ShaderSourceLoader.java` - includes, source maps, and expansion limits
- `ShaderOptionParser.java`, `ShaderMacroExpander.java` - preprocessing helpers
- `OculusConfig.java`, `OculusConfigStore.java` - validated atomic config

## Tests and evidence

Tests under `src/test/java/dev/oculusforge` cover versions, configuration,
backend policy, pack discovery, hostile paths/ZIPs, includes, macros, options,
and legacy source adaptation.

- `TEST_REPORT.md` - exact automated and runtime results
- `MANUAL_TEST_PLAN.md` - remaining acceptance matrix
- `KNOWN_ISSUES.md` - unsupported features and unverified areas
- `PORTING_PLAN.md` - original audit and migration matrix
- `ARCHITECTURE.md` - current boundaries and next renderer components

## Legal

- `LICENSE` - LGPL-3.0-only
- `LICENSE_NOTES.md` - provenance and exclusions
- `NOTICE` - unofficial-port attribution
- `DEPENDENCIES.md` - pinned inputs and renderer-dependency decision

## Publishing kit

- `publishing/PUBLISHING_COPY.md` - ready-to-paste listing fields, description,
  changelog, and platform metadata
- `publishing/FULL_MOD_DESCRIPTION.md` - expanded Markdown project description
  with features, installation, compatibility, limitations, and attribution
- `publishing/LEGAL_PUBLISHING_CHECKLIST.md` - LGPL, attribution, trademark,
  moderation, and source-availability checklist
- `publishing/curseforge-icon-400.png` - original CurseForge avatar
- `publishing/modrinth-icon-512.png` - optimized Modrinth icon
- `publishing/ICON_README.md` - icon provenance, generation prompt, and disclosure
- `publishing/RELEASE_SHA256.txt` - artifact and icon checksums
