# Known Issues

## Critical compatibility gaps

- Terrain/chunk, entity, block-entity, particle, hand/overlay, sky, cloud,
  weather, water, and translucent gbuffer programs are not routed through pack
  shaders.
- Shadow-map world rendering is not implemented.
- Only the main color target and main depth target are exposed. Additional
  `colortex`/`depthtex`, normal/specular, custom image, noise, and history
  targets are unsupported.
- The complete ShadersMod/OptiFine/Iris directive, property, profile, expression,
  and preprocessor model is not present.
- This means the beta does not satisfy the promise that every existing
  ShadersMod/OptiFine pack works unmodified. Lightweight full-screen packs and
  compatible final/composite stages are the current test surface.

## Renderer dependencies

Rubidium is obsolete and intentionally absent. Official Embeddium Forge support
ends at Minecraft 1.20.1, while current Iris/Sodium 26.2 targets are not a legal
or technical Forge drop-in. No renderer dependency version is fabricated and no
Sodium implementation is bundled.

The result is correct Forge/vanilla integration but no optimization-mod-specific
terrain bridge yet. Performance with a future Forge 26.2 optimization mod is
unverified.

## Graphics and depth

- Vulkan shader rendering is unsupported. It is detected and shaders are
  disabled before OpenGL-style pack code runs.
- Reversed depth is translated for recognized post-process depth texture
  sampling. Terrain/shadow comparisons and projection transformations are still
  future work.
- Legacy fragment output is limited to `gl_FragColor` or `gl_FragData[0]`.
- Unknown samplers, uniforms, attributes, built-ins, or additional outputs
  intentionally fail activation and fall back to vanilla.

## Lifecycle and validation gaps

- Automated source preparation and live AMD iGPU runs cover Complementary
  Reimagined r5.7.1 and BSL v10.1.1, but not a diverse pack matrix.
- A standalone launcher installation containing only the binary JAR has not been
  run; runtime verification used ForgeGradle's client.
- In-session F11 fullscreen toggling, dimension travel, resource-pack changes,
  repeated shader switching, malformed-pack UI flows, and a long leak test
  remain unverified.
- Vulkan startup was not available on the test machine.
- The minimap, UI, content-mod, and resource-pack compatibility matrix remains
  unverified because compatible candidates were not installed.
- Linux wrapper and Linux graphics startup were not run in the Windows test
  environment.
- No Forge-only CPU/GPU performance baseline, RenderDoc capture, or 60-minute
  resource-leak profile has been collected.

## Input handling

- Directory symlink containment exists, but Windows symlink creation is not in
  the portable automated suite.
- Archive declared-size and per-read limits are enforced. The current pack
  session does not track one cumulative actual-decompressed-byte budget across
  every later read.
- Conditional and function-macro preprocessing uses the bounded, relocated JCPP
  implementation after secure include expansion.

## Unrelated test-environment messages

The development client logged a Mojang Realms authentication error and Forge
userdev `union:` asset warnings. A test world also emitted vanilla worldgen
unsafe-terrain-read diagnostics. None originated from an Oculus class or shader
pass.
