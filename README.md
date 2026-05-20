# The Useful Tools Mod — combined repository

A Minecraft mod adding reinforced tools and armor variants of common materials.

This repository consolidates what previously lived across six independent per-loader, per-version repos. Active development happens against the multi-loader (Architectury + Forgix) project; the legacy single-loader trees are preserved under `archived/` for reference.

## Layout

```
.
├── _scaffolding/              # Two-phase test pipeline (GameTest + visual)
└── archived/                  # Frozen copies of the prior single-loader trees
    ├── 1.21.1-original/
    │   ├── Forge/             # ex-UsefulToolsMod-2.X
    │   ├── NeoForge/          # ex-UsefulToolsMod-2.X-Neoforge
    │   └── Fabric/            # ex-UsefulToolsMod-2.X-Fabric
    └── 26.1.2-original/
        ├── Forge/             # ex-UsefulToolsMod-2.X-26.X-Forge
        ├── NeoForge/          # ex-UsefulToolsMod-2.X-26.X-Neoforge
        └── Fabric/            # ex-UsefulToolsMod-2.X-26.X-Fabric
```

The multi-loader project lands at `1.21.1/` (pilot) and, once Architectury ships for that line, `26.1.2/`.

## Backups of the archived trees

The frozen `archived/` copies are also pushed at their original standalone repos:

- https://github.com/StonyTark1117/UsefulToolsMod-2.X
- https://github.com/StonyTark1117/UsefulToolsMod-2.X-Neoforge
- https://github.com/StonyTark1117/UsefulToolsMod-2.X-Fabric
- https://github.com/StonyTark1117/UsefulToolsMod-2.X-26.X-Forge
- https://github.com/StonyTark1117/UsefulToolsMod-2.X-26.X-Neoforge
- https://github.com/StonyTark1117/UsefulToolsMod-2.X-26.X-Fabric

Those repos are intentionally left as-is for posterity. Future work happens here.

## Deprecated

Minecraft 1.20.x is no longer supported. The three 1.20 trees were deleted from this repo (still available at their standalone GitHub repos).
