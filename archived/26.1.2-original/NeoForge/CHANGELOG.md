# Changelog

## 2.2.3-26.1.2-neoforge

Port from Minecraft 1.21.1 to 26.1.2 (NeoForge 21.1.219 → 26.1.0.1-beta). No new
gameplay features — this release exists purely to make the 2.2.3 content available on
26.1.

### Build / toolchain
- **Java 25** required (was 21). Use the foojay toolchain resolver, or set `JAVA_HOME`
  to a Java 21 install for Gradle while the toolchain compiles the mod against 25.
- **Gradle 9.1.0** (was 8.13), **NeoGradle 7.1.27** (was 7.0.+),
  **foojay-resolver-convention 1.0.0** (the older 0.10.0 has a stale `IBM_SEMERU`
  reference that breaks under Gradle 9).
- Two run-types replace the old single `data`: `clientData` and `serverData`.

### API migration
- All custom tools/armor moved from the removed `SwordItem`/`PickaxeItem`/`ArmorItem`
  subclasses to plain `Item` with `Item.Properties#sword(...)`/`#humanoidArmor(...)`.
- `ModToolTiers` rebuilt as `ToolMaterial` records. `ModArmorMaterials` rebuilt as
  `ArmorMaterial` records carrying `ResourceKey<EquipmentAsset>` keys.
- 45 equipment-asset JSONs at `assets/usefultoolsmod/equipment/<material>.json`; armor
  textures copied to the new `textures/entity/equipment/humanoid/{,_leggings/}` paths.
- `GuiGraphics` → `GuiGraphicsExtractor`; `SpectralInfuserScreen` rewritten against the
  1.21.6 extract-pass pipeline using `RenderPipelines.GUI_TEXTURED`.
- `GhostRenderer` migrated to the 1.21.2 `RenderState` pattern (new `GhostRenderState`);
  `GhostModel` animations baked once and applied per-frame via `KeyframeAnimation`.
- `Block#use` → `Block#useWithoutItem` on `SpectralInfuserBlock`.
- `BlockEntity` save data: `CompoundTag` → `ValueInput`/`ValueOutput`.
- `Entity#hurt` is `final`; mobs override `hurtServer(ServerLevel, DamageSource, float)`.
- Project-wide renames: `ResourceLocation` → `Identifier`, `MobSpawnType` →
  `EntitySpawnReason`, `ArmorItem.Type` → `ArmorType`, `Util` → `util.Util`, advancements
  `critereon` → `criterion`, `FMLEnvironment.dist` field → `getDist()` method, plus
  several package moves (`Zombie`/`Drowned`/`AbstractSkeleton`/`Equippable`/`GameRules`/
  `ThrowableItemProjectile` all moved to sub-packages).
- `MobEffects` constants renamed (`DAMAGE_BOOST` → `STRENGTH`, `MOVEMENT_SPEED` → `SPEED`,
  etc.); `GameRules.RULE_KEEPINVENTORY` → `KEEP_INVENTORY`.
- `DeferredSpawnEggItem` retired in favor of vanilla `SpawnEggItem` + `Properties#spawnEgg`.

### Known gaps
- `compat/jer/**` is excluded from compile until JER publishes a 26.1 build on Modrinth.
  Plugin source is checked in and ready.

### Datagen
- `datagen/**` has been rewritten against vanilla's 26.1 `ModelProvider` / `BlockModelGenerators`
  / `ItemModelGenerators` system (NeoForge dropped its `client.model.generators` package in
  26.1). Both `./gradlew runClientData` and `./gradlew runServerData` work — the old single
  `data` run was replaced by these two so client and server outputs land in sibling
  subdirs of `src/generated/resources/` and don't wipe each other's HashCache.
- `ModBlockStateProvider` deliberately elides `SPECTRAL_INFUSER` from its known-blocks
  stream because the block needs a directional + lit multivariant blockstate that the
  vanilla cube helper can't express. Its blockstate and both `orientable_with_bottom`
  models (`spectral_infuser` + `spectral_infuser_on`) live hand-written under
  `src/main/resources/assets/usefultoolsmod/` so re-runs can't clobber them.

See `MIGRATION_CHEATSHEET.md` (1073 lines) for the per-primer change catalog and
`PORT_HANDOFF.md` for the in-progress notes.

## 2.2.3-1.21.1-neoforge

### Added
- **Magnetization addon compatibility** — when the optional Magnetization addon is installed, this mod's iron-bearing items, ores, armor, and tools now participate in its magnetic field interactions via the `magnetization:ferromagnetic`, `magnetization:metal_armor`, `magnetization:metal_tools`, and `magnetization:ferromagnetic_blocks` tags. Inclusion criterion: any set whose recipe chain transitively contains iron — including composite ingots like `RGOLD`, `RLAPIS`, `SEM`, and `OBINGOT` which all use iron nuggets or ingots.
  - Ferromagnetic items: `rgold`, `raw_rgold`, `obingot`, `rlapis`, `sem`
  - Metal armor: copper, rgold, obsidian, fni (Flint-Iron), rlapis, emerald, overpower full sets
  - Metal tools: copper, rgold, pobsidian, fni, rraw_iron, rraw_copper, rraw_gold, rraw_rgold, rscrap, rlapis, pemerald full tool sets, plus overpower sword/pickaxe/axe/shovel
  - Ferromagnetic blocks: all four `rgold` ore variants (overworld/deepslate/nether/end), `rgoldblock`, `raw_rgold_block`, `lblock`, `semblock`, `soblock`
  - Tag JSONs are emitted unconditionally; they're inert when Magnetization isn't installed, so no runtime gate is needed. Magnetization is listed as an `optional` dependency in `neoforge.mods.toml` for metadata clarity.

## 2.2.2-1.21.1-neoforge

Parity sync with the 2.2.2 Fabric release. All fixes mirror the Fabric variant.

### Fixed
- **Spectral / Spectral-Infused armor mix-and-match** — wearing a mix of native Spectral armor and ectoplasm-infused pieces now correctly hides the player from Ghosts. Previously the check was either-or (4 native or 4 infused), so any mixed loadout failed both branches and Ghosts could still see the player. (`EctoplasmArmorHelper.isGhostInvisible`)
- **Spectral-Infused armor tooltip** — infused armor incorrectly showed "Can damage Ghosts". Armor doesn't damage Ghosts; tools/weapons do. Infused armor now reads "Hides you from Ghosts"; the damage line stays on tools and weapons. (new lang key `tooltip.usefultoolsmod.ecto_armor_invisibility`)
- **Ghost spawn egg ignores config rate** — using a Ghost Spawn Egg (or `/summon`, breeding, etc.) only succeeded ~15% of the time because `FinalizeSpawnEvent` was discarding any Ghost that failed `Config.ghostSpawnChance`, regardless of how it spawned. The rate gate has been moved into the spawn predicate (`GhostEntity.checkGhostSpawnRules`), which Minecraft only consults during natural spawning. Spawn eggs and commands now always succeed; the kill-switch (`ghostEnabled = false`) still discards on finalize.
- **Datagen completeness**: `ModBlockTagProvider` and `ModRecipeProvider` were missing the `needs/incorrect_recto_tool` block tags and the grenade/dynamite recipes (the static resources covered them at jar build time, but a fresh `runData` would have lost them). The grenade/dynamite static JSON copies were removed in favor of datagen ownership.

### Already in place from the initial NeoForge port
The following 2.2.2-Fabric fixes were already shipped in the 2.2.1-neoforge initial release and required no changes for parity:
- Overpowered armor effects honor `Config.overpowerEnabled` and `Config.opArmorEffectsEnabled` (`ModArmorItem.inventoryTick`).
- Overpowered armor aura particles drawn per-piece worn (1–4 stacking), gated on config flags, with radial cos/sin pattern and 50/50 SOUL_FIRE_FLAME / ENCHANT mix.
- Ghost spawn placement uses `SpawnPlacementTypes.NO_RESTRICTIONS` (NeoForge equivalent of Fabric's `UNRESTRICTED`) so ghosts can spawn floating.
- No orphan `rgold_from_blasting_rgold` / `rgold_from_smelting_rgold` advancements present.

## 2.2.1-1.21.1-neoforge

Initial NeoForge 1.21.1 release, ported from the Forge 1.21.1 source. Feature parity with the Forge variant.

### Compatibility

- Minecraft 1.21.1
- NeoForge 21.1.219+

### Optional integrations

- JEI 19.27.0.340+ — Spectral Infuser recipe category
- WTHIT (waila) 12.10.1+ — Ghost ectoplasm armor info
- Cloth Config 15.0.140+ — In-game config screen

### Port notes

- Migrated from Forge APIs to NeoForge equivalents (`net.minecraftforge.*` → `net.neoforged.neoforge.*`, `RegistryObject` → `DeferredHolder`, `ForgeTier` → `SimpleTier`, etc.)
- Tick events updated to NeoForge's split `PlayerTickEvent.Post` / `LevelTickEvent.Post`
- `LivingHurtEvent` → `LivingIncomingDamageEvent`, `MobSpawnEvent.FinalizeSpawn` → `FinalizeSpawnEvent`
- Menu screen registration moved to `RegisterMenuScreensEvent`
- Biome modifier JSONs relocated to the `neoforge:` namespace, fixing a silent ore-generation failure in pre-release builds
