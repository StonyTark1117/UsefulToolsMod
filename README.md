# The Useful Tools Mod

Useful Tools adds a large catalog of material-based tools and armor, the Spectral Infuser, Ghosts, explosives, reinforced-gold ore across dimensions, decorative/storage blocks, advancements, and optional recipe/viewer integrations.

The former Soul Lantern feature is now the Ectoplasm Lantern, distinguishing it
from Minecraft's vanilla Soul Lantern. `usefultoolsmod:ectoplasm_lantern` is the
canonical ID; the old namespaced ID remains a legacy world-compatibility alias.

This repository is the consolidated home for every maintained source line. Version `2.3.0` is the current published release. Version `2.3.1` is a local fix candidate and remains on manual visual-review hold; its tooling writes only to `release/2.3.1/`.

## Target matrix

| Minecraft | Java | Loaders |
|---|---:|---|
| 1.7.10 | 8 | Forge |
| 1.20.1 | 17 | Forge, Fabric, NeoForge |
| 1.20.2 | 17 | Forge, Fabric, NeoForge |
| 1.21.1 | 21 | Forge, Fabric, NeoForge |
| 26.1.2 | 25 | Forge, Fabric, NeoForge |
| 26.2 | 25 | Forge, Fabric, NeoForge |

Every Fabric binary is tested unchanged under Quilt Loader 0.30.0; no separate Quilt jar is produced.

## Repository layout

- `catalog/` — authoritative schema-2 version-neutral content/config/material/recipe manifest plus classic substitution data.
- `1.7.10/` — Java 8 Forge 10.13.4.1614 build retaining the published classic implementation and generated 2.3.1 catalog adapters.
- `1.20.1/`, `1.20.2/`, `26.1.2/`, `26.2/` — loader-specific active roots.
- `1.21.1/` — independent loader-native `forge/`, `fabric/`, and `neoforge/` projects.
- `scripts/` — catalog audit, complete matrix runner, and local release assembler.
- `archived/` — historical source snapshots retained for provenance only.

The canonical modern catalog currently contains 638 direct item definitions, 23 block items/blocks, four entities, 87 tool tiers, 688 data recipes, 854 advancements, and 126 config controls. The totals include the intentional `minecraft:ice` snowball recipe and its advancement as well as the mod namespace. `scripts/generate_registration_tables.py` emits the ID tables used by every modern loader/version factory; run `scripts/audit_catalog.py` after registration or config changes.

## Configuration

Every modern loader has a built-in vanilla-widget configuration screen. Fabric exposes it through optional Mod Menu integration; Forge and NeoForge use their native mod-list hooks. Cloth Config is not required. Config persistence remains version/loader native where necessary, and shared JSON lines save atomically and migrate the historical `pointedDripstone*` keys.

Forge 1.7.10 exposes the same 126 descriptors through its native mod-list configuration GUI and `.cfg` persistence. Its complete catalog, material tables, language entries, textures, recipes, and config table are generated with `scripts/generate_classic_catalog.py`; rerun that command after changing the canonical manifest.

## Building

Build one target:

```bash
scripts/build-matrix.sh 1.20.2-forge
```

Build the complete matrix:

```bash
scripts/build-matrix.sh
```

After successful builds, collect canonically named binaries/source jars, generate packaged dependency bounds, byte sizes, and SHA-256 checksums, and run the strict 32-artifact audit:

```bash
scripts/assemble-release.py
```

This writes only to `release/2.3.1/`; it does not publish, tag, or upload anything. The audit checks ZIP integrity, embedded versions, class-file Java levels, loader metadata, repaired block models, assets/data, and source coverage.

Run a bounded dedicated-server smoke test with:

```bash
scripts/smoke-server.py 1.20.2-fabric
```

The smoke runner supports all 16 targets, creates the local EULA file, waits
for server readiness, sends `stop`, verifies process and TCP-port cleanup, and
writes its log, JSON result, and any timeout thread dump under
`validation/server-smoke/`. Every launched descendant carries a unique marker,
so forced cleanup is limited to that exact validation run even when a Gradle
launcher reparents its game JVM.

The equivalent client runner opens the built-in config screen, creates and
joins an isolated world, captures visual evidence, exits through the UI, and
verifies exact process cleanup:

```bash
scripts/smoke-client.py 1.21.1-neoforge
```

It also accepts repeatable `--gradle-property` values plus an optional
`--inventory-probe` for recipe-viewer visual profiles.

Run deterministic data generation for one modern artifact with:

```bash
scripts/audit-datagen.py 1.21.1-fabric
```

Use `all` in place of the target for the full modern matrix. In-place generator
roots must remain byte-for-byte stable. Each 1.21.1 loader now runs its own
native providers; the audit treats Fabric's omitted default recipe result count
and NeoForge's explicit `count: 1` as the same decoded JSON value.

Fresh GameTest, optional-integration, Java-warning, Quilt, and release evidence
is stored under `validation/`. The 2.3.1 candidate is not publishable until its
Ectoplasm Lantern, Mining Charge, and Wraith/WTHIT captures are manually approved.
After a full local build, `release/2.3.1/artifacts.json` records the candidate
artifact hashes and packaged dependency bounds.

## Compatibility and release status

See [COMPATIBILITY.md](COMPATIBILITY.md) for loader notes, migration rules, optional integrations, and known blockers. [CHANGELOG.md](CHANGELOG.md) tracks release changes, and [FUTURE_WORK.md](1.21.1/FUTURE_WORK.md) is the authoritative 2.3.1 acceptance checklist.

License: [CC0-1.0](LICENSE).
