# 1.7.10 Forge + OptiFine E7 + Complementary Reimagined 5.9.3

- Minecraft 1.7.10, Forge 10.13.4.1614, OptiFine HD U E7 Ultra.
- Repository-built packaged mod: `usefultoolsmod-2.3.1+mc.1.7.10.forge.jar`, SHA-256 `fd5a0789d7667910a3e12949d9931a5c68b00e0d72e45e8f7364d766c789d8b6`. The staged jar had the same hash.
- Pack: `ComplementaryReimagined_r5.9.3.zip`, SHA-256 `fed6c879e732cf7fc7bc6570ebb1def43235aa7e96f62859fbdbdbfa0b484b38`.
- Isolated Xvfb `:77`, 1280x720x24 framebuffer; Minecraft window 854x480. Mesa llvmpipe OpenGL 4.6, Java 8. The game process had `DISPLAY=:77`, no `WAYLAND_DISPLAY`, and `ALSOFT_DRIVERS=null`. All input and capture commands explicitly targeted `:77`.
- `optionsshaders.txt` records the active pack. `fml-client-session.log.gz` records the client startup, pack load, and in-game toggle; `latest-session.log.gz` is the corresponding Minecraft log. Both are from the final packaged-jar session. The older `latest.log` is retained as an earlier attempt, not the final-session log.

Minecraft F2 captures from the final packaged-jar session:

| Scene | Ghost | Wraith |
| --- | --- | --- |
| Day | `ghost-day.png` | `wraith-day.png` |
| Night | `ghost-night.png` | `wraith-night.png` |
| Rain | `ghost-rain.png` | `wraith-rain.png` |
| Lit indoor room | `ghost-indoor.png` | `wraith-indoor.png` |

The Ghost's body, eyes, and mouth remain visible. The Wraith is dimmer at night and indoors, but its head, facial marks, and separated body remain recognizable in the captured frames. These are visual observations for this exact software-rendered OptiFine stack, not a claim about every pack or GPU.

OptiFine logs warnings about newer shader variable/biome names in this pack. The inspected frames rendered and the client did not crash; this check does not establish that every pack effect is available on Minecraft 1.7.10.

`wraith-shaders-off.png` and `wraith-shaders-retoggled.png` were taken in the same client session after changing OptiFine's Shaders menu from Complementary to OFF and back. `optionsshaders-off.txt` and `optionsshaders.txt` record the two states. The game continued rendering and exited through Save and Quit to Title, then Quit Game, without a crash.

The indoor scene is a disposable, lit stone-brick room prepared with `validation/shader-support-2.3.2/prepare-classic-room.py`; its world copy is outside the repository. A hanging glowstone lamp was placed with `/setblock -20 73 220 minecraft:glowstone`. The Wraith initially sank through terrain because the classic no-clip entity still received vanilla gravity; cancelling that gravity in `EntityWraith.onLivingUpdate` made its above-ground scene testable. The shader-dependent packed-light floor is queried on each render and falls back to vanilla light when OptiFine or an active pack is absent.
