# Oculus 1.20.2
This is an unofficial port of [Oculus](https://github.com/Asek3/Oculus) (the Forge fork of [Iris Shaders](https://github.com/IrisShaders/Iris)) to Minecraft **Forge 1.20.2**, a version no shader loader ever shipped for. Do not ask for support on the official Oculus or Iris GitHub/Discord, as they do not support this.

## Requirements
- Minecraft 1.20.2 with Forge 48.x
- [Embeddium 0.2.10+mc1.20.2](https://modrinth.com/mod/embeddium/version/0.2.10+mc1.20.2) (required)

## Notes
- Based on Oculus 1.8.0 (branch `1.20.1-new`), adapted to the 1.20.2 rendering internals (`SectionRenderDispatcher`, `renderSectionLayer`, `SectionBufferBuilderPack`) and to Embeddium 0.2.x internals (Sodium 0.5.3-era, single `VERTEX_DATA` chunk attribute).
- Tested with Complementary Reimagined r5.8.1.
- Licensed under LGPL-3.0, same as upstream. Source for the port lives in this repository.

## Known interactions
GeckoLib 4.3.1 (the last 1.20.2 build) lacks the shadow-pass re-render guard newer versions have; mods that drive per-render animation state (e.g. custom player models) may animate erratically with shaders enabled unless patched to skip processing during the Iris shadow pass.
