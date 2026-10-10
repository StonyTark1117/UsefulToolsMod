# Manual Test Plan

This plan defines remaining verification for the Forge 26.2 shader pipeline.
The current beta can execute compatible full-screen stages; the broader
world-rendering and real-pack matrix below is still required.

## Test setup

1. Install Java 25.
2. Create a clean Minecraft 26.2 Forge 65.1.0 profile.
3. Back up the instance.
4. Place the binary JAR in `mods`.
5. Do not install OptiFine.
6. Save `logs/latest.log`, crash reports, and screenshots/video for each failure.
7. Run OpenGL first. Run Vulkan only as a separate fallback test.
8. Use shader packs whose download terms permit testing and record name,
   version, URL, hash, license/terms, and selected options.

## Current-beta smoke test

1. Start with no `config/oculus.properties`.
2. Launch the client.
3. Confirm Forge lists Oculus as an unofficial community port.
4. Open Mods → Oculus → Config.
5. Confirm the active backend name is shown.
6. With shaders disabled, load a world and confirm vanilla rendering.
7. Put one directory pack and one ZIP pack under `shaderpacks`.
8. Click Refresh and confirm both appear.
9. Add malformed, traversal, oversized, and invalid UTF-8 test packs.
10. Confirm they are rejected with a useful `latest.log` entry and no crash.
11. Select a compatible full-screen test pack and enable shaders.
12. Confirm its effect appears, then disable it without restarting.
13. Save pack selection, close the screen, restart, and confirm persistence.

Automated development runs confirmed direct-config enabled and disabled world
paths. Config-screen interaction and live UI switching still require manual
execution.

## Renderer acceptance matrix

Run every row on a clean OpenGL instance after the pipeline exists:

| Scenario | Required observations |
|---|---|
| Shaders disabled | vanilla appearance; no custom GPU objects; baseline FPS |
| Internal fallback | correct terrain/entities/UI; no missing programs |
| Lightweight pack | terrain, sky, hand, water, shadows |
| Medium pack | deferred/composite order, normal/specular maps |
| Demanding pack | stable allocation, resize, long session |
| Shadows off/on | target creation/destruction and scene parity |
| Normal maps off/on | material sampling and fallback |
| Specular maps off/on | material sampling and fallback |
| Invalid source | structured error and last-known-good/vanilla recovery |
| Missing include | path/line diagnostic and recovery |
| Unsupported feature | explicit message; no silent defaults |

Use at least three shader packs from different implementation families and
complexity levels. Do not redistribute packs unless their terms allow it.

## Scene coverage

Create a reproducible test world containing:

- opaque, cutout, translucent, emissive, animated, and water/lava blocks
- entities with armor, nameplates, glowing, leashes, fire, and transparency
- several block entities, including animated and custom mod examples
- dense particles and weather
- first-person hand, maps, bows, tridents, spyglass, portals, fire, water, and
  powder-snow overlays
- overworld sky, sun, moon, stars, clouds, rain, snow, dawn/night transitions
- Nether and End transitions

Capture reference screenshots with shaders disabled and each selected pack.

## Lifecycle sequence

Perform in this order and inspect logs/GPU behavior after every step:

1. cold launch;
2. title screen;
3. create world;
4. load world;
5. enable shaders;
6. disable shaders;
7. switch packs repeatedly;
8. trigger resource reload;
9. change resource pack;
10. travel Overworld → Nether → Overworld → End;
11. change render distance low/high;
12. resize to several window dimensions;
13. toggle fullscreen repeatedly;
14. change GUI scale;
15. unload to title;
16. load a second world;
17. exit with the in-game Quit button;
18. start again using saved configuration.

Pass only if every superseded GPU generation is released, no stale world state
is reused, and vanilla fallback remains correct.

## Graphics backends

### OpenGL

- record backend, vendor, driver, extensions, clip-space convention;
- verify reversed depth clears/comparisons and projection;
- capture RenderDoc or equivalent if available;
- use driver debug output and treat high-severity messages as failures.

### Vulkan

- start Minecraft with experimental Vulkan selected;
- confirm Oculus detects `Vulkan`;
- confirm shader rendering automatically stays disabled;
- confirm no OpenGL calls, program compilation, or GL class initialization occur;
- confirm vanilla title screen and world still render;
- confirm the user-facing incompatibility message is clear.

Do not mark Vulkan shader-pack support passed; only safe fallback is expected.

## Compatibility matrix

Test one current Forge 26.2 mod in each group after authoritative compatible
versions exist:

- minimap;
- UI/options;
- content mod with custom entities and block entities;
- resource pack;
- any future chosen Forge terrain renderer dependency.

Record exact versions and hashes. Test Oculus last and first in mod ordering where
applicable. OptiFine must produce a clear conflict/disabled result.

## Performance and leaks

1. Capture Forge-only baseline with identical settings and scene.
2. Capture Oculus installed/shaders disabled.
3. Capture each shader class after warm-up.
4. Record average, 1% low, frame-time distribution, CPU/GPU utilization, VRAM,
   render distance, resolution, and driver.
5. Switch packs and resize at least 50 times while tracking live GPU objects.
6. Run a 60-minute world session with dimension transitions.
7. Verify no monotonically increasing program, texture, framebuffer, buffer, or
   native allocation count.

## Failure criteria

Any of these fails the run:

- fatal mixin/access transformer/mod-loading error;
- missing dependency or incorrect loader message;
- OpenGL calls on Vulkan;
- silent shader fallback;
- corrupt vanilla rendering after disable/failure;
- crash on malformed pack;
- path escape or unbounded pack input;
- shader program/target leak;
- wrong depth, translucent order, shadow, hand, sky, or entity stage;
- a claimed pass without saved evidence.
