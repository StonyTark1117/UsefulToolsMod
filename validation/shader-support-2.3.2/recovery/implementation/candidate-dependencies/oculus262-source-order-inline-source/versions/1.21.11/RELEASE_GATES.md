# Release Gates

Release labels describe verified capability, not only artifact naming.

## Beta gate - met by 0.3.0-beta.1

- reproducible Forge production and sources JARs for both supported targets
- clean Java compilation with all lint checks and Gradle with all warnings enabled
- secure bounded directory/ZIP loading with hostile-input regression tests
- asynchronous source preparation and render-thread GPU ownership
- transactional replacement with last-known-good recovery
- runtime failure isolation back to vanilla
- real-pack preparation for Complementary Reimagined and BSL
- live OpenGL AMD iGPU activation, resize, soak, and clean shutdown evidence

## Release-candidate gate - not yet met

- repeat enable/disable and pack switching at least 50 times
- F11/fullscreen, resource-pack reload, world unload/reload, and all dimensions
- standalone launcher profiles using only the distributable JARs
- at least five shader packs from three implementation families
- controlled vanilla/disabled/enabled frametime measurements
- 60-minute resource and GPU-object leak qualification
- clean failure UI for malformed, unsupported, and compile-failing packs

## Stable 1.0 gate - not yet met

- terrain/chunk and translucent gbuffer routing
- entities, block entities, particles, hand, overlays, sky, clouds, weather, and water
- real shadow-map world rendering
- multiple color/depth/history targets and pack buffer directives
- normal, specular, noise, and pack custom textures
- dimension-aware matrices and the remaining OptiFine/Iris uniform semantics
- compatibility documentation that matches a broad, saved-evidence test matrix

Until all stable gates are met, the project must not claim complete Oculus/Iris
shader-pack compatibility.
