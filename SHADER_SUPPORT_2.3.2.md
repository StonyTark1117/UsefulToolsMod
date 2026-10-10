# Proposed shader support for 2.3.2

## Gate reached: Ghost and Wraith can render under shaders

The initial Ghost/Wraith regression fix has been implemented on modern targets
that forced an emissive translucent entity pass. With an Iris-compatible shader
pack active, the entities use the normal lit path and a modest minimum block
light value; without an active pack, they retain their existing emissive look.
An isolated 1.21.1 NeoForge client run with Iris, Sodium, and BSL confirmed
recognizable eyes, mouths, and silhouettes by day and at night. The matching
vanilla run confirmed the original emissive appearance. The saved captures,
smoke reports, and checks are in
[validation/ghost-shader-qa](validation/ghost-shader-qa/README.md).

This proves an initial path is viable. It does not establish compatibility
with every shader pack or every loader and Minecraft target. Solas could not
finish terrain loading in the software-rendered test client; a GPU-backed run
is needed for that pack.

## Make the spectral entities look better

1. Keep their bodies and faces on the shader-aware lit path so the texture's
   shading, eyes, and mouth remain visible. Preserve the no-shader path as a
   visual reference.
2. Trial a separate, low-intensity emissive mask for selected details such as
   the Ghost's eye edges, the Wraith's eyes, and sparse particles. Avoid an
   emissive pass over the whole body. Keep depth testing and normal entity
   occlusion so the glow does not turn into a flat overlay.
3. Compare mask strength in BSL and at least one other pack at noon, midnight,
   in rain, and against both dark and bright backgrounds. Tune from captured
   images with the eyes and mouth still legible. If a pack blooms the mask too
   aggressively, ship the readable lit path without the extra glow.
4. Check movement, damage flash, invisibility, team outline, and spectator
   rendering before enabling an additional pass. These states are easy to
   miss in a static scene.

## Whole-mod 2.3.2 scope

- Audit every client render surface: the two spectral entities, other entity
  effects, Ectoplasm Lantern and Mining Charge, translucent or emissive block
  and item textures, particles, enchantment glints, and relevant GUI previews.
  Record the render type, alpha channel, light behavior, and expected vanilla
  appearance for each one.
- Keep shader-loader detection optional and queried at render time, so toggling
  a pack in game changes the path without requiring a restart. Keep each
  loader's integration small; do not add Iris as a required mod dependency.
- Extend the isolated capture scene to cover the audited surfaces. For each
  supported Minecraft/loader line, compile the client sources. Visually run a
  representative loader and Minecraft version matrix with no shader loader,
  a loader present but shaders disabled, and at least two active packs. Include
  day/night and indoor light, and record the exact pack and loader versions.
- Set a release gate: no washed-out or missing entity features, no unexpected
  invisibility or bloom, the vanilla look retained where intended, no startup
  dependency on a shader loader, and no client crashes when packs are toggled.
  Compare frame time in a repeatable scene before and after any extra passes.

The first 2.3.2 change should be the proven Ghost/Wraith regression fix. The
extra glow and broader audit can follow as measured additions; each needs its
own captures before being included in the release.
