# Useful Tools 2.3.2 device model verification

The five requested upgrades are implemented across all 16 maintained targets.
The feature is recorded under **2.3.2 — Unreleased**. Local development JARs
retain their current 2.3.1 version labels; published releases are unchanged.

| Item | Appropriate 3D presentation |
| --- | --- |
| Sticky Dynamite | Adhesive-bound red bundle in hand, inventory, dropped form, flight, and attachment |
| Remote Detonator | Metal controller with antenna, channel display, status indicator, and raised red trigger |
| Mining Charge | Existing bounded placed silhouette reused in modern item contexts; matching native classic held silhouette |
| Ectoplasm Lantern | Existing framed, solid-backed placed model reused in modern item contexts; native opaque classic frame and cyan core |
| Spectral Resonator | Brass crystal cage, cyan crystal, handle, gauge, and side control |

Modern first-person transforms show both hands, with the devices' controls
facing the player. Classic uses native Forge item renderers; 1.7.10 has no
offhand item context. Existing modern placed block models and classic bounded
placed blocks, blockstates, collision shapes, and armed indicators are preserved.
The deprecated Soul Lantern item alias follows the Ectoplasm Lantern geometry.

Sticky Dynamite previously moved to the centre of its hit block or attached
entity. Its new renderer displaces only the visible model onto the impacted
surface, and samples light there instead of inside a solid block. Separate
`ModelFace` and `ModelRadius` visual metadata are synchronized and saved with
backward-compatible defaults. Classic already positions block attachments
outside the block; only its entity attachment needs an extra visual offset.
The original gameplay tick bodies are verified unchanged on all 16 targets.
Fuse timing, explosive strength, owner tracking, attachment coordinates, and
explosion centres remain unchanged. Regular Dynamite retains instant detonation
and has no thrown entity.

## Verification

- **16/16 builds and packaged-model audits:** solid cuboids and bounded UVs,
  opaque device material texels, resolved block parents, correct 26.x item
  dispatchers, renderer classes, and current renderer/entity source inputs.
  Loom's original development sources are checked before Yarn remapping.
- **6/6 26.x client datagen checks:** Forge, Fabric, and NeoForge on 26.1.2 and
  26.2 reproduce their checked-in generated resources without drift and preserve
  custom geometry. Removed generated flat device models cannot override it.
- **3 real isolated clients:** 1.21.1 NeoForge, 26.2 Fabric, and 1.7.10 Forge.
  The two modern clients cover both hands, third person, an actual throw, solid
  wall attachment, cow-body attachment, placed blocks, and front/side displays.
  Classic covers every held model, third person, and an actual throw.
- **Existing regression checks:** the 2.3.1 repair audit passes on 16 targets,
  the canonical catalog audit passes, and the original Dynamite/Grenade packaged
  audit still passes on all 16 targets.

Modern attachment command logs confirm `Stuck: 1b`, `Fuse: 70`, and the original
server attachment position after stepping ten ticks; screenshots show the
model visibly outside that position. Runtime reports confirm world joins,
clean exits, and no remaining owned processes. Captures require commands to be
enabled and reject invalid commands rather than accepting empty-hand scenes.

![Both hands in 26.2 Fabric](modern-held-review.png)

![Held items in Forge 1.7.10](classic-held-review.png)

![Sticky Dynamite attached to a solid wall](26.2-fabric/sticky_dynamite-attached-wall.png)

![Sticky Dynamite attached to a cow](26.2-fabric/sticky_dynamite-attached-entity.png)

The other 13 clients are covered by source/build/JAR checks rather than separate
visual sessions. Classic attachment offsets are source/build checked; live wall
and body attachment screenshots are from the two modern clients. Shader packs
and optional resource packs were not part of these device visual sessions.

## Reproduce

Run from the repository root:

```bash
PYTHONDONTWRITEBYTECODE=1 python scripts/sync-2.3.2-device-models.py --check
PYTHONDONTWRITEBYTECODE=1 python scripts/audit-device-models.py --assemble
PYTHONDONTWRITEBYTECODE=1 python scripts/audit-device-datagen.py
PYTHONDONTWRITEBYTECODE=1 python scripts/capture-device-models.py --target 1.21.1-neoforge --offline
PYTHONDONTWRITEBYTECODE=1 python scripts/capture-device-models.py --target 26.2-fabric --offline
PYTHONDONTWRITEBYTECODE=1 python scripts/capture-device-models.py --target 1.7.10-forge --offline
PYTHONDONTWRITEBYTECODE=1 python scripts/audit-2.3.1-fixes.py
PYTHONDONTWRITEBYTECODE=1 python scripts/audit_catalog.py
```

Inspect `asset-audit.json`, the six `datagen/*.json` reports, and each client
directory's runtime report, `capture-audit.json`, screenshots, and logs. Manual
visual review complements the automated image and command checks.

These complete the device upgrades recommended in the earlier
[Dynamite/Grenade review](../explosive-models-2.3.2/README.md). No further item
needs the same treatment for this update: ordinary tools, material sprites,
food, and spawn eggs suit their existing vanilla presentation, while the
Spectral Infuser already uses a 3D block model.
