# Changelog

## 2.2.2-1.21.1-forge

### Fixed
- **Spectral / Spectral-Infused armor mix-and-match** — wearing a mix of native Spectral armor and ectoplasm-infused pieces now correctly hides the player from Ghosts. Previously the check was either-or (4 native or 4 infused), so any mixed loadout failed both branches and Ghosts could still see the player. (`EctoplasmArmorHelper.isGhostInvisible`)
- **Spectral-Infused armor tooltip** — infused armor incorrectly showed "Can damage Ghosts". Armor doesn't damage Ghosts; tools/weapons do. Infused armor now reads "Full Set: Hides you from Ghosts"; the damage line stays on tools and weapons. (new lang key `tooltip.usefultoolsmod.ecto_armor_invisibility`)
- **Ghost spawn egg / `/summon` ignored config rate** — `MobSpawnEvent.FinalizeSpawn` fires for every spawn type, so the rate gate was rolling against `Config.ghostSpawnChance` for spawn eggs, breeding, and dispensers. The rate now only applies to natural-style spawns (NATURAL, CHUNK_GENERATION, SPAWNER, STRUCTURE, PATROL, TRIAL_SPAWNER); spawn eggs and commands always succeed. The `ghostEnabled` kill-switch still gates all spawn types.
- **Spectral / Infused armor tooltip clarity** — both tooltips now lead with "Full Set:" and use DARK_GREEN styling (matching other full-set lines), making it obvious the ghost-hiding effect requires a complete 4-piece set (any mix of native Spectral and Spectral-Infused).
