# Dynamite and Grenade models for 2.3.2

Regular Dynamite now has a solid bundle of red sticks, two paper bands, and a
fuse. Grenade has a segmented olive body, neck, metal lever, and pin ring.
The geometry is shared across the fifteen modern targets and Forge 1.7.10.
Both canonical and legacy classic item registrations use the new renderer.

Regular Dynamite retains instant detonation at the player, as requested. It
has no thrown entity. Grenade uses its new geometry when held and when thrown.
Explosive strengths, recipes, registry IDs, and configuration are unchanged.
The feature is recorded under **2.3.2 — Unreleased** in the repository changelog.

## Verification

The final audit passed for **16/16 packaged targets**. The three client runs
below passed world join, clean exit, and process cleanup, and their final
held/thrown captures were visually reviewed.

- `scripts/sync-2.3.2-explosive-models.py --check` checks all sixteen source targets
  against the shared model definition.
- `scripts/audit-explosive-models.py` checks solid cuboid bounds, all six faces,
  opaque texture samples, both hand transforms, packaged models and textures,
  classic renderer classes, and the newer item-model dispatchers. Its current
  result is saved in [asset-audit.json](asset-audit.json).
- The catalog audit verifies the canonical content and generated registration
  tables remain consistent.
- Client captures come from actual isolated Minecraft runs. The thrown Grenade
  is launched through normal item use. Modern `/tick freeze` and `/tick step 2`
  make its in-flight model inspectable before impact. Each client report records
  world join, clean exit, and exact process cleanup.

| Client | Held Dynamite | Held Grenade | Thrown Grenade | Report |
| --- | --- | --- | --- | --- |
| 1.21.1 NeoForge | [right](1.21.1-neoforge/dynamite-firstperson-right.png), [left](1.21.1-neoforge/dynamite-firstperson-left.png), [third person](1.21.1-neoforge/dynamite-thirdperson.png) | [right](1.21.1-neoforge/grenade-firstperson-right.png), [left](1.21.1-neoforge/grenade-firstperson-left.png), [third person](1.21.1-neoforge/grenade-thirdperson.png) | [flight](1.21.1-neoforge/grenade-thrown.png) | [JSON](1.21.1-neoforge/1.21.1-neoforge.json) |
| 26.2 Fabric | [right](26.2-fabric/dynamite-firstperson-right.png), [left](26.2-fabric/dynamite-firstperson-left.png), [third person](26.2-fabric/dynamite-thirdperson.png) | [right](26.2-fabric/grenade-firstperson-right.png), [left](26.2-fabric/grenade-firstperson-left.png), [third person](26.2-fabric/grenade-thirdperson.png) | [flight](26.2-fabric/grenade-thrown.png) | [JSON](26.2-fabric/26.2-fabric.json) |
| 1.7.10 Forge | [right](1.7.10-forge/dynamite-firstperson-right.png) | [right](1.7.10-forge/grenade-firstperson-right.png), [third person](1.7.10-forge/grenade-thirdperson.png) | [flight](1.7.10-forge/grenade-thrown.png) | [JSON](1.7.10-forge/1.7.10-forge.json) |

The modern runs also include [front](1.21.1-neoforge/geometry-front.png) and
[side](1.21.1-neoforge/geometry-side.png) views of enlarged item displays.
Visual acceptance on these clients demonstrates the legacy renderer, the
older baked-item renderer, and the newer item-model system. Other maintained
targets receive source and packaged-resource checks; these captures are not a
claim that every loader/version or shader pack was visually tested.

Repeat a capture with `python3 scripts/capture-explosive-models.py --target
1.21.1-neoforge --offline`, substituting `26.2-fabric` or `1.7.10-forge` as needed.
Use an online run when its Minecraft assets or build dependencies are uncached.
Use `python3 scripts/audit-explosive-models.py --assemble` to rebuild and inspect
all target JARs.

## Other items worth upgrading

| Priority | Item | Current appearance | Recommended upgrade |
| --- | --- | --- | --- |
| 1 | Sticky Dynamite | Generated flat sprite, also used by its thrown entity | Reuse the dynamite bundle with visible green adhesive, keeping its sticking behavior |
| 2 | Remote Detonator | Flat handheld sprite | A small housing, button, and antenna to match the explosive set |
| 3 | Spectral Resonator | Flat handheld sprite | A physical frame and ectoplasm core so the spirit detector reads as a device |
| 4 | Mining Charge | Placed model is already 3D; modern held item uses a flat generated sprite | Reuse the existing charge geometry in hand and as a dropped item |
| 4 | Ectoplasm Lantern | Placed model is already 3D; modern held item uses a flat generated sprite | Reuse the lantern frame and core in hand and as a dropped item |

The ordinary material tools already follow Minecraft's familiar handheld
silhouette. A wholesale 3D conversion of those families is lower priority than
the distinctive devices and explosives above. These recommendations are a
review only; their models are not changed by this feature.
