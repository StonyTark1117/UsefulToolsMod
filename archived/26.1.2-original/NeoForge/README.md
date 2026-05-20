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

- **Minecraft:** 26.1.2
- **Loader:** NeoForge 26.1.2.55-beta+ (built and tested against this version; older
  26.1.0.x betas don't satisfy JEI 29.5.0.28's lower bound)
- **Java:** 25 (toolchain configured automatically via foojay-resolver)

### Optional integrations

The mod does not require any of these — it detects them at runtime and adds the integration only if they're present.

- **JEI** 29.5.0.28+ — Spectral Infuser shows up as its own recipe category with every supported tool/armor input listed
- **WTHIT (waila)** 19.0.1+ — Ghosts display ectoplasm armor info
- **Cloth Config** 26.1.154+ — Adds an in-game config screen accessible from the mod list
- **Just Enough Resources (JER)** — registers ore distributions in JER's WorldGen tab. *Currently disabled in `build.gradle`; will be enabled once JER publishes a 26.1 build on Modrinth.*

## Building

```
./gradlew build
```

The output jar lands at `build/libs/usefultoolsmod-2.2.3-26.1.2-neoforge.jar`.

> If your default JDK is newer than Java 25 (e.g. Java 26), Gradle's embedded Groovy
> may reject the build script. Either install Java 25 or run with an explicit override:
> `JAVA_HOME=/usr/lib/jvm/java-21-openjdk ./gradlew build` (Gradle itself runs on the
> JAVA_HOME JDK; the mod is still compiled against Java 25 via the toolchain).

## License

CC0-1.0 — see [LICENSE](LICENSE).
