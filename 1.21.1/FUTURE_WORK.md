# 1.21.1 — remaining work

The Architectury multi-loader pilot is functionally complete: items, blocks,
entities, recipes, events, attributes, renderers, worldgen, Spectral Infuser,
and JEI/WTHIT/JER integrations all ship in both Fabric and NeoForge jars
(~1.99 MB each). Config now persists to JSON.

What remains is feature *restoration* and *operational polish* — none of it
blocks runtime use, but each item is a piece of the legacy NeoForge tree
that didn't survive the Architectury translation 1:1.

## Phase 8 — platform helpers (restores 3 degraded features)

Architectury 13.0.8 doesn't expose:

1. **Damage-amount mutation** — `EntityEvent.LIVING_HURT` returns
   `EventResult` (cancel-only). The original tree mutated damage in ~10
   on-hit effects (Sweet Berry +1, Glass +2, Dripstone ×1.3, Phantom-at-night
   +2, etc.). These are tagged `// TODO(damage-mutation)` in
   `common/event/ModEvents.java` and stubbed to no-ops.

2. **LivingChangeTarget** — no cross-loader event for "this mob is about to
   set a player as its target." The original tree gated 8 effects on this
   (Rotten Flesh undead-neutral, Pumpkin Pie enderman avoidance, Bone reduced
   detection, etc.). The full handler is preserved as commented dead code in
   `common/event/ModEvents.java` under "Targeting events".

3. **`FireBlock.getBurnOdds(BlockState)`** — private in vanilla, exposed by
   NeoForge. The FNI boots fire-spawn check (handleFniBootsFire) now relies
   on the 4% random roll alone; fire may spawn on non-flammable surfaces.
   Tagged `// TODO(platform-helper)` in `common/event/ModEvents.java`.

**Restoration plan:**

```
common/platform/CombatHelper.java         (@ExpectPlatform)
  static float modifyOutgoingDamage(LivingEntity attacker, ItemStack weapon, float amount)
  static boolean isMobAllowedToTarget(Mob mob, LivingEntity target)
  static int getBurnOdds(BlockState state)

fabric/platform/CombatHelperImpl.java
  - modifyOutgoingDamage:   uses Fabric's ServerLivingEntityEvents.ALLOW_DAMAGE
                            wrapped in a mixin on LivingEntity#hurtServer to
                            allow mutation, not just cancellation.
  - isMobAllowedToTarget:   mixin on Mob#setTarget
  - getBurnOdds:            accessor mixin on FireBlock

neoforge/platform/CombatHelperImpl.java
  - modifyOutgoingDamage:   register LivingIncomingDamageEvent listener,
                            call event.setAmount(modified) before returning.
  - isMobAllowedToTarget:   register LivingChangeTargetEvent listener.
  - getBurnOdds:            direct call (NeoForge makes it public).
```

The Fabric implementations require setting up the mixin infrastructure:
- `fabric/src/main/resources/usefultoolsmod.mixins.json`
- Three mixin classes under `fabric/src/main/java/.../mixin/`
- Update `fabric/build.gradle` to register the mixins file
- Add the mixin entries to `fabric.mod.json`

Estimated effort: 1 focused session.

## Phase 3.5 (full) — Cloth Config UI

`Config` currently persists via reflection-driven JSON read/write. There's
no in-game config screen — users edit the JSON file and reload.

To wire a UI:

1. **Use Cloth Config AutoConfig.** Convert `Config.java` to a `@Config` /
   `@ConfigEntry`-annotated class. AutoConfig handles persistence and generates
   a screen by reflection on field types and `@Comment` annotations.
2. **Fabric integration**: depend on Mod Menu and Cloth Config. Add a
   `modmenu` entrypoint pointing at a `ModMenuApi` impl that opens
   `AutoConfig.getConfigScreen(...)`.
3. **NeoForge integration**: register `IConfigScreenFactory` to open the
   same Cloth screen from NeoForge's mod-list config button. Cloth Config
   for NeoForge handles this via `ClothConfigEntryBuilder` adapters.
4. **Migration**: Cloth Config writes to a different file format than our
   current `usefultoolsmod.json`. Either: rename ours to `usefultoolsmod.toml`
   and use Cloth's TOML serializer, or migrate the existing JSON values on
   first run.

Estimated effort: 1-2 focused sessions, depending on familiarity with
Cloth's API. Cloth Config dep is already declared in `gradle.properties`
(`cloth_config_version=15.0.140`) — just unwired.

## Phase 5b — datagen consolidation

Currently `common/src/main/resources/data/` ships NeoForge's pre-generated
output verbatim (3,618 files copied during Phase 2). Adding a new item
requires manually authoring its model/loot table/recipe JSON.

To wire datagen:

1. **Provider classes in common/datagen/** — port ModBlockStateProvider,
   ModItemModelProvider, ModBlockLootTableProvider, ModRecipeProvider,
   ModBlockTagProvider, ModItemTagProvider, ModAdvancementProvider,
   ModDatapackEntries from the archived NeoForge tree. Most use vanilla
   `net.minecraft.data.*` APIs that work cross-loader.

2. **Per-loader entrypoints**:
   - `neoforge/datagen/NeoForgeDataGen.java` — `@EventBusSubscriber(modid=MOD_ID, bus=MOD)`
     with `@SubscribeEvent` on `GatherDataEvent.Client` and `GatherDataEvent.Server`.
   - `fabric/datagen/FabricDataGen.java` — `DataGeneratorEntrypoint`, calls
     the common providers from `onInitializeDataGenerator`.

3. **Gradle tasks**: `:neoforge:runData` and `:fabric:runDatagen` should
   produce output identical to the currently-shipped `common/resources/data/`.
   Diff to verify, then delete the manually-copied data and let datagen own it.

Estimated effort: 1 focused session.

## Phase 7 — test pipeline

See `_scaffolding/INSTRUCTIONS.md` for the adaptation steps. Requires
interactive client launch to generate the test structure NBT.

## Spectral Infuser visual screen

`common/client/SpectralInfuserScreen.java` references a texture at
`textures/gui/spectral_infuser.png` which currently exists. The screen
should render correctly. If the texture path or atlas binding diverges
between Fabric and NeoForge, this is the first place to look.

## Misc Architectury 13.0.8 limitations (not blocking)

- `MenuRegistry.openExtendedMenu` writes the extra-data buffer once when
  the menu opens. If our menu protocol grows to need bidirectional sync,
  swap to Architectury's networking helpers.
- `EntityEvent.ADD` fires on entity spawn regardless of side; we already
  gate on `level.isClientSide()`.
- `BiomeModifications.addProperties` is invoked once at registration; it
  doesn't reflect runtime config-toggle changes. The current Ghost spawn
  is unconditional; if `Config.ghostEnabled=false` is set after the world
  is created, ghosts will still spawn until `LIVING_CHECK_SPAWN` cancels them
  (which happens — that path works).
