# Modern 2.2.x upgrade acceptance

Date: 2026-08-25

## Result

PASS. Three worlds created by published 2.2.2 artifacts were reopened by the
matching 2.3.0 development target. All targets reached dedicated-server
readiness, preserved the selected world objects and non-default configuration,
saved all three dimensions, stopped their RCON/listener threads, and released
their TCP ports.

| Source artifact | 2.3.0 target | Published SHA-256 |
| --- | --- | --- |
| `usefultoolsmod-2.2.2-1.21.1-forge.jar` (CurseForge file 8066166) | 1.21.1 Forge | `5f0e9178b76ef728390e1919d2bf12bd932cf3eb3563e8b658cdcb04d0ac8498` |
| `usefultoolsmod-2.2.2-1.21.1-fabric.jar` (CurseForge file 8065517) | 1.21.1 Fabric | `68ba790c7caa01ca8c74094a5acfba1dd4b5dc44c67f14bba939636877c9325a` |
| `usefultoolsmod-2.2.2-1.20.2-neoforge.jar` (CurseForge file 8066107) | 1.20.2 NeoForge | `56ae289e7f0bb173908e0a9b60326154d366347bac822cd68ad62a102fdecc21` |

The Forge server used Forge 52.1.10 and Java 21. The Fabric server used Fabric
Loader 0.18.4, Fabric API 0.116.9+1.21.1, and Java 21. The NeoForge server used
NeoForge 20.2.93 and Java 17. The old artifacts came directly from the
project's CurseForge CDN URLs; the versions were cross-checked against the
author's `StonyTark1117` GitHub repositories.

## World assertions

Each old server created a fresh world, placed these five blocks, summoned a
persistent Ghost named `UpgradeGhost`, flushed the save, and shut down:

- `0 100 0`: `usefultoolsmod:spectral_infuser`
- `1 100 0`: `usefultoolsmod:rgoldblock`
- `2 100 0`: `usefultoolsmod:rgoldore`
- `3 100 0`: `usefultoolsmod:rgold_nether_ore`
- `4 100 0`: `usefultoolsmod:rgold_end_ore`

After replacement with 2.3.0, RCON `data get block 0 100 0` returned the
Spectral Infuser ID and its empty three-slot inventory data on all three
targets. `execute if block` returned `Test passed` for each of the four regular
blocks. `data get entity` returned a UUID for the named Ghost on every target.
This gives direct registry, block-entity NBT, and entity persistence evidence.

## Config assertions

Before copying each old installation to the 2.3.0 run, three values were set to
non-defaults:

- explosives enabled: `false`
- Ghost spawn chance: `0.42`
- pointed-dripstone effects: `false`

Forge retained these values in the world `serverconfig` TOML, NeoForge retained
them in its common TOML, and Fabric retained them in
`config/usefultoolsmod.json`. Fabric also accepted the historical
`pointedDripstone*` JSON keys through the alias migration path. No target reset,
discarded, or clamped any of these valid values.

## Log audit and shutdown-race correction

Final current logs are:

- `validation/modern-upgrade/forge-current.log`
- `validation/modern-upgrade/fabric-current.log`
- `validation/modern-upgrade/neoforge-current.log`

They contain no missing registry/mapping diagnostics, failed recipe loads,
missing model/texture diagnostics, mod-loading exceptions, crash markers, or
startup failures. The final Forge and NeoForge runs report `BUILD SUCCESSFUL`;
Fabric completed all dimension saves and released both ports before exact
cleanup of the known Architectury transformer launcher orphan.

The first Forge upgrade run exposed a project-owned shutdown race: a filesystem
reload event could arrive after Forge unloaded the config spec, and `Config`
would read unloaded values. All nine Forge/NeoForge spec implementations now
accept only loading/reloading events while `SPEC.isLoaded()` is true. A repeated
upgrade run no longer emits the exception.

Remaining diagnostics are loader/development-runtime output already covered by
the release warning audit: Forge/NeoForge userdev union-URL and duplicated OSHI
resource warnings, NeoForge 20.2 language-provider jars without `mods.toml`,
terminal capability notices, offline-mode/LAN notices, Netty reflective fast
path fallbacks, and Minecraft's expected missing data-fixer notices for custom
entities. Fabric's tag-convention warning is emitted by Fabric API for external
convention tags and does not identify a Useful Tools namespace tag. None is a
missing-content or upgrade failure.
