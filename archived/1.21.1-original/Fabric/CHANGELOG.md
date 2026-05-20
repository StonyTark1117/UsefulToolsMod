# Changelog

## 2.2.2-1.21.1-fabric

### Fixed
- **Spectral / Spectral-Infused armor mix-and-match** — wearing a mix of native Spectral armor and ectoplasm-infused pieces now correctly hides the player from Ghosts. Previously the check was either-or (4 native or 4 infused), so any mixed loadout failed both branches and Ghosts could still see the player. (`EctoplasmArmorHelper.isGhostInvisible`)
- **Spectral-Infused armor tooltip** — infused armor incorrectly showed "Can damage Ghosts". Armor doesn't damage Ghosts; tools/weapons do. Infused armor now reads "Hides you from Ghosts"; the damage line stays on tools and weapons. (new lang key `tooltip.usefultoolsmod.ecto_armor_invisibility`)
- **Ghost spawn egg ignores config rate** — using a Ghost Spawn Egg (or `/summon`, breeding, etc.) only succeeded ~15% of the time because `ServerEntityEvents.ENTITY_LOAD` was discarding any Ghost that failed `Config.ghostSpawnChance`, regardless of how it spawned. The rate gate has been moved into the `SpawnRestriction` predicate (`GhostEntity::checkGhostSpawnRules`), which Minecraft only consults during natural spawning. Spawn eggs and commands now always succeed; the kill-switch (`ghostEnabled = false`) still discards on load. Matches the 1.20-Fabric behavior.
- **Ghost spawn location restored to mid-air** — `SpawnRestriction` was registered with `SpawnLocationTypes.ON_GROUND`, requiring solid blocks beneath spawn candidates. Ghosts fly with `noGravity = true`, so the 1.20-Fabric port used `NO_RESTRICTIONS` to allow floating spawns. Switched to `SpawnLocationTypes.UNRESTRICTED` (the 1.21.1 equivalent) so natural spawns can occur in open air again.
- **Overpowered armor effects ignored config flags** — `ModArmorItem.inventoryTick` was applying the OP-armor status effects (Jump Boost III, etc.) regardless of `Config.overpowerEnabled` and `Config.opArmorEffectsEnabled`. Now both flags are honored, matching 1.20-Fabric.
- **Overpowered armor aura particles** — the client tick was looking for a *full* OP armor set (4/4) before drawing the aura, and didn't gate on config. 1.20-Fabric draws an aura per OP piece worn (so 1–4 stacking auras) and respects both config flags. Restored that behavior, including the radial cos/sin spawn pattern with a 50/50 mix of `SOUL_FIRE_FLAME` and `ENCHANT` particles instead of plain soul-flame spread.
- Removed two orphan advancement files (`rgold_from_blasting_rgold`, `rgold_from_smelting_rgold`) that referenced non-existent recipes — leftovers from the original 1.20-Forge JSON migration.
- **Datagen completeness**: `ModBlockTagProvider` and `ModRecipeProvider` were missing 14 block tags (3 `needs_*` + 11 `incorrect_*`) and the grenade/dynamite recipes. The static resources covered them at jar build time, but a fresh `runDatagen` would have lost them. Backfilled both providers.
- **Spectral / Infused armor tooltip clarity** — both tooltips now lead with "Full Set:" and use the same DARK_GREEN styling as other full-set lines, making it obvious the ghost-hiding effect requires a complete 4-piece set (any mix of native Spectral and Spectral-Infused). Was: "Ghosts will avoid you" / "Hides you from Ghosts" in plain gray.

## 2.2.1-1.21.1-fabric

First public release of the 1.21.1 Fabric port.

### Added
- Full feature parity with the 1.20 Fabric edition: ~640 items, 21 blocks, 1972 recipes, 2247 advancements.
- **Spectral Infuser** block, block entity, GUI, and recipe — combine Spectral armor with ectoplasm for Ghost-neutralizing infused gear.
- **Ghost** mob with full AI rewrite: hide-from-adults, mate, follow-player, follow-active-ghost, fly, observe-friendly-mob goals; ecto-weapon-only damage model; ectoplasm-based breeding and taming.
- **Vanilla-material tool & armor effects** — 19 on-hit effects and 7 full-set armor effects (counter-attacks, ignite, levitate, teleport-dodge, darkness, wither, etc.).
- **Edible food-themed armor and tools** with proper `FoodComponent` attached.
- **JEI plugin** — Spectral Infuser recipe category, Egg + Ectoplasm → Ghost Spawn Egg recipe.
- **WTHIT plugin** — server + client split for Fabric 12.10.2 API.
- **Datagen pipeline** — 7 providers covering recipes, advancements, item models, blockstates, item tags, block tags, block loot tables.
- Custom advancement triggers: `ghost_nearby`, `coal_tool_ignited`.
- Config file (`config/usefultoolsmod.json`) controlling Ghost spawning, ectoplasm avoidance, and creative tab gating.

### Notes
- Resource pack format `48` (1.21.1 data pack format), `supported_formats: [34, 48]`.
- Built against Fabric Loom 1.15, Fabric API 0.116.9+1.21.1, Java 21.
