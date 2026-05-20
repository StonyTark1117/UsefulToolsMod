# 1.21.1 — remaining work

The Architectury multi-loader pilot is **feature-complete for gameplay**.
Items, blocks, entities, recipes, events, attributes, renderers, worldgen,
Spectral Infuser, JEI/WTHIT/JER integrations, all platform helpers (damage
mutation, target gating, FireBlock burnOdds), and JSON config persistence
all ship in both Fabric and NeoForge jars (~1.97 MB each).

What remains is **UI polish** and **operational tooling** — both nice-to-haves
that don't block shipping.

## Phase 3.5 full — Cloth Config UI

`Config.java` persists to `<gamedir>/config/usefultoolsmod.json` via reflection
on the 116 public-static fields. There's no in-game screen — users edit the
JSON file directly.

To add the UI:

1. **Cloth Config dep** is already declared (`cloth_config_version=15.0.140`)
   but unwired. Add to `common/build.gradle`:
   ```groovy
   modCompileOnly "me.shedaniel.cloth:cloth-config:${rootProject.cloth_config_version}"
   ```
   Each loader subproject:
   ```groovy
   // fabric/build.gradle
   modImplementation "me.shedaniel.cloth:cloth-config-fabric:${rootProject.cloth_config_version}"
   // neoforge/build.gradle
   modImplementation "me.shedaniel.cloth:cloth-config-neoforge:${rootProject.cloth_config_version}"
   ```

2. **Write `common/ClothConfigScreen.java`** that builds the screen via
   reflection on `Config` fields. Two paths:
   - Manual: iterate public-static fields, build a `ConfigBuilder` with one
     `BooleanToggleBuilder` / `DoubleSliderBuilder` per field. Lots of
     boilerplate but full control over categorization and tooltips.
   - AutoConfig: convert `Config` to an `@Config`-annotated class. Lose
     the JSON-by-reflection persistence; gain auto-generated screen and
     migration tooling. Cleaner long-term.

3. **Fabric ModMenu integration**: add `modmenu` entrypoint pointing at
   `UsefulToolsModMenuPlugin implements ModMenuApi`. The plugin returns
   `parent -> ClothConfigScreen.create(parent)`.

4. **NeoForge integration**: in `UsefultoolsModNeoForge`'s `@Mod` ctor,
   register a config screen factory:
   ```java
   ModLoadingContext.get().registerExtensionPoint(
       IConfigScreenFactory.class,
       () -> (screen, parent) -> ClothConfigScreen.create(parent)
   );
   ```

5. **Migration**: if you adopt AutoConfig, write a one-shot loader that
   reads the existing `usefultoolsmod.json` and writes to Cloth's
   `usefultoolsmod.toml` (or wherever AutoConfig persists).

Estimated effort: 2-3 focused hours.

## Phase 5b full — datagen TBDs

Recipe + Advancement providers are in common and wired through fabric +
neoforge datagen entrypoints. The rest of the providers diverge meaningfully:

- **LootTable provider bridging** — Fabric's `SimpleFabricLootTableProvider`
  expects `BiConsumer<ResourceKey<LootTable>, LootTable.Builder>` direct
  emission while NeoForge wraps in `LootTableProvider.SubProviderEntry`.
  `ModBlockLootTableProvider` is in common but unwired.

- **BlockTag + ItemTag providers** — both extend their loader's
  `TagsProvider` subclass (`net.neoforged.neoforge.common.data.BlockTagsProvider`
  vs `net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider`).
  Each loader subproject needs a thin subclass that bridges to the common
  tag-building logic.

- **BlockStateProvider + ItemModelProvider** — NeoForge-only DSL with no
  vanilla or Fabric equivalent. Either:
  - Rewrite using vanilla `net.minecraft.data.models.ModelProvider` (more
    verbose but cross-loader)
  - Keep the pre-generated JSON in `common/resources/data/` authoritative
    and accept manual authoring for new items

Estimated effort: 1 focused day for full coverage; or accept the lite scope
and stick with the shipped JSON for everything except recipes and advancements.

## Phase 7 — test pipeline

See `_scaffolding/INSTRUCTIONS.md` for the multi-loader adaptation steps.
Requires:

1. Choose a host loader subproject (probably `1.21.1/neoforge/` since
   NeoForge GameTests have more mature tooling)
2. Copy `_scaffolding/test-pipeline/` under it; replace loader-detection
   with a hardcoded `LOADER=neoforge`
3. Place the `UsefulToolsGameTests.java` (from `_scaffolding/templates/neoforge/`)
   under `1.21.1/common/src/main/java/...` so both loader runs exercise the
   same tests
4. Wire `runs { gameTestServer { ... } }` in `neoforge/build.gradle`
5. Interactive: launch `:neoforge:runClient`, open a creative world with
   cheats, run `/test create empty_3x3 3 3 3` to generate the test structure
   NBT, commit it
6. Verify: `1.21.1/neoforge/test-pipeline/run.sh` should pass Phase 1
   (GameTest) at minimum

Estimated effort: 1-2 hours of focused work plus the interactive NBT
generation step.

## Already done in earlier sessions

Everything above the deferral line. Recap:

| Phase | Status | Commit |
|---|---|---|
| Baseline — archive prior trees | Done | `50b076e` |
| 1 — Architectury scaffold | Done | `961ae8f` |
| 2 — gameplay code + assets to common | Done | `312450b` |
| 3 — Architectury registries + Config stub | Done | `81dce68` |
| 4a — wire init() + verified build | Done | `59c7178` |
| 4b — events to Architectury | Done | `4315947` |
| 4c — attributes + renderers + worldgen | Done | `650bb2f` |
| 5a — SpectralInfuser via vanilla Container | Done | `56ff512` |
| 6 — JEI + WTHIT + JER compat | Done | `be7ed1f` |
| 3.5 lite — JSON config persistence | Done | `a5eb4b3` |
| 8 — platform helpers + Fabric mixins | Done | `30e418b` |
| 5b lite — Recipe + Advancement datagen | Done | `4ded6a1` |
