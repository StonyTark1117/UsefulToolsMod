# Useful Tools — Fabric 26.1.2

This is the Fabric 26.1.2 target for the unpublished Useful Tools `2.3.1` fix candidate.
It contains the full modern catalog: 633 direct items, 21 blocks/block items,
two entities, 682 recipes across all namespaces, 848 advancements, the Spectral
Infuser, Ghosts, explosives, effects, and reinforced-gold world generation.

## Requirements

- Minecraft 26.1.2
- Java 25
- Fabric Loader and Fabric API versions pinned in `gradle.properties`

JEI, WTHIT, and Mod Menu are optional. The config UI uses Minecraft's vanilla
widgets; Cloth Config is not required. Mod Menu supplies the optional route to
open that built-in screen. The ordinary Fabric artifact is also the candidate
for Quilt testing; no separate Quilt jar is built.

Build from the repository root with:

```bash
scripts/build-matrix.sh 26.1.2-fabric
```

See the root `README.md`, `COMPATIBILITY.md`, and `1.21.1/FUTURE_WORK.md` for
the release matrix and remaining runtime gates. `PORT_HANDOFF.md` and
`CHANGELOG.md` are historical port records and may describe superseded 2.2.x
dependencies or validation.

License: CC0-1.0.
