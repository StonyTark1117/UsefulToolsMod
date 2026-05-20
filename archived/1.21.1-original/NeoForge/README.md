# The Useful Tools Mod

A simple Minecraft mod adding various tools and armor based on reinforced versions of common materials, plus a few extras like a custom block, mob, and dimension-spanning ore generation.

## Features

- **Reinforced material set** — tools and armor (sword, pickaxe, axe, shovel, hoe, helmet, chestplate, leggings, boots) for reinforced gold and a handful of other materials
- **Reinforced gold ore** generates in the Overworld, Nether, and End
- **Spectral Infuser** — a custom block with its own GUI that turns vanilla tools/armor into spectral (ectoplasm-infused) versions, and turns eggs into ghost spawn eggs
- **Ghost** — a hostile mob that spawns at night in any dimension; carries ectoplasm
- **Ectoplasm armor set** — wearing the full set grants invisibility while crouching
- **Decorative blocks** — hardened coal, coal dust, calcified amethyst, glacial shard, polished quartz/prismarine, refined ectoplasm, and more

## Compatibility

- **Minecraft:** 1.21.1
- **Loader:** NeoForge 21.1.219+

### Optional integrations

The mod does not require any of these — it detects them at runtime and adds the integration only if they're present.

- **JEI** 19.27.0.340+ — Spectral Infuser shows up as its own recipe category with every supported tool/armor input listed
- **WTHIT (waila)** 12.10.1+ — Ghosts display ectoplasm armor info
- **Cloth Config** 15.0.140+ — Adds an in-game config screen accessible from the mod list

## Building

```
./gradlew build
```

The output jar lands at `build/libs/usefultoolsmod-2.2.1-1.21.1-neoforge.jar`.

## License

CC0-1.0 — see [LICENSE](LICENSE).
