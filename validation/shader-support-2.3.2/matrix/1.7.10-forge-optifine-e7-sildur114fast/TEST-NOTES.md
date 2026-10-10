# 1.7.10 Forge + OptiFine E7 + Sildur's Enhanced Default 1.14 Fast

- Same packaged Minecraft 1.7.10 / Forge 10.13.4.1614 / OptiFine HD U E7 Ultra client and mod jar as the Complementary run. Mod jar SHA-256: `fd5a0789d7667910a3e12949d9931a5c68b00e0d72e45e8f7364d766c789d8b6`.
- Shader archive: `Sildur's Enhanced Default v1.14 Fast.zip`, SHA-256 `8cd55ca8b38f04a7bc18f94f6d266b7570de46b4efcb65341027fe9351fb15f1`, downloaded from the [author's CurseForge file](https://www.curseforge.com/minecraft/shaders/sildurs-enhanced-default/files/3912672).
- The same isolated Xvfb `:77` (1280x720x24 framebuffer), 854x480 Minecraft window, Mesa llvmpipe OpenGL 4.6, Java 8, `DISPLAY=:77`, unset `WAYLAND_DISPLAY`, and `ALSOFT_DRIVERS=null` were used. Input and capture targeted `:77`.
- `optionsshaders.txt` records this pack. `fml-client-sildur-session.log` starts at its in-game load and records shader programs, including `shadow`, loading without a subsequent compile error. `latest.log` is the same client session's Minecraft log.

Minecraft F2 captures from the final packaged-jar session:

| Scene | Ghost | Wraith |
| --- | --- | --- |
| Day | `ghost-day.png` | `wraith-day.png` |
| Night | `ghost-night.png` | `wraith-night.png` |
| Rain | `ghost-rain.png` | `wraith-rain.png` |
| Lit indoor room | `ghost-indoor.png` | `wraith-indoor.png` |

Both models render in all four scene categories. The Ghost's pale form and face markings stay distinguishable outdoors, including at night. The Wraith remains visible but is dark against the night sky; indoors its head, eyes, mouth, and body separation are clearer under the glowstone. Some outdoor frames show the entities from the side or back because they move and rotate autonomously, so the Complementary frontal captures are the stronger facial-detail evidence. This result is limited to the exact software-rendered stack above.

Earlier attempts to use BSL 10.1.2 and BSL 7.1.04.1 in this client produced shader compile errors on Mesa llvmpipe. Those attempts are excluded from the passing pack count. The second passing pack here is Sildur's Enhanced Default.
