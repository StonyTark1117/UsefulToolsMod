# UsefulToolsMod test pipeline — install + extend

**Multi-loader update (Architectury):** since the consolidation, the pipeline
needs to be invoked per loader subproject:

```bash
# Phase-1 GameTest only (no GUI):
./1.21.1/gradlew -p 1.21.1 :fabric:runGametest        # Fabric variant
./1.21.1/gradlew -p 1.21.1 :neoforge:runGameTestServer # NeoForge variant
```

The pipeline runner under `test-pipeline/run.sh` was written for the
pre-Architectury single-loader trees and still expects to live at the
project root. To re-enable it on the 1.21.1 multi-loader project:

1. Pick a loader (e.g. `1.21.1/neoforge/`) as the host for the pipeline.
2. Copy `test-pipeline/` under it: `cp -r _scaffolding/test-pipeline 1.21.1/neoforge/`
3. The runner's `build.gradle` grep won't match — replace the loader-detection
   block with a hardcoded `LOADER=neoforge` (or `fabric`) and the matching
   gametest task.
4. The GameTest class itself can live in `1.21.1/common/src/main/java/...`
   so both loader runs exercise it — but each subproject's `build.gradle`
   needs `loom { runs { server { defaultMods.add ... } } }` or the equivalent
   gametest-task configuration.
5. The test structure NBT (`src/main/resources/data/usefultoolsmod/structure/empty_3x3.nbt`)
   must be generated once via `./gradlew :neoforge:runClient`, a creative
   world with cheats, and `/test create empty_3x3 3 3 3`. Commit the resulting NBT.

Status as of consolidation: pipeline is **not yet wired into 1.21.1/**.
The above is the to-do for whoever picks it up.

The pre-Architectury single-loader instructions below are retained for
reference. Apply the loader-subproject prefix to gradlew calls in any
copy-paste from them.

---

You're a Claude session working on **either** the NeoForge or Fabric copy of
UsefulToolsMod. This scaffolding lives at `../_scaffolding/` relative to your
project. Goal: install the two-phase pipeline (GameTest → ydotool/grim visual)
and grow the visual scenarios as you find places it would catch regressions.

The two projects are kept in lockstep — same package, same `mod_id`, same
test method names. If you add a scenario or test method, ping the user so
the other session can mirror it.

## One-time host setup (skip if already done)

```bash
sudo pacman -S ydotool grim imagemagick jq
systemctl --user enable --now ydotool.service
```

## Install in this project

From the project root (`UsefulToolsMod-2.X-Neoforge` or `-Fabric`):

1. **Copy the pipeline runner** (not symlinked — the steps/ dir grows
   per-project, so copy is cleaner):

   ```bash
   cp -r ../_scaffolding/test-pipeline ./test-pipeline
   ```

2. **Apply the loader-specific patches** under `../_scaffolding/templates/`:

   - **NeoForge**: `templates/neoforge/build.gradle.patch` (one argument
     line + an optional task alias).
   - **Fabric**: `templates/fabric/build.gradle.patch` AND
     `templates/fabric/fabric.mod.json.patch`.

3. **Drop in the GameTest class**:

   ```bash
   mkdir -p src/main/java/com/stonytark/usefultoolsmod/gametest
   cp ../_scaffolding/templates/<loader>/UsefulToolsGameTests.java \
      src/main/java/com/stonytark/usefultoolsmod/gametest/
   ```

4. **Generate the test structure NBT once.** Start a dev client
   (`./gradlew runClient`), open a creative world with cheats, run:

   ```
   /test create empty_3x3 3 3 3
   ```

   This creates `src/main/resources/data/usefultoolsmod/structure/empty_3x3.nbt`.
   Commit it. You only do this once per project.

5. **Start from a clean save state.** Step 01 navigates from the main menu
   assuming no prior world exists. The harness wipes `runs/client/saves/`
   (NeoForge) and `run/saves/` (Fabric) at the top of every visual run, so
   you don't have to do this by hand — but if you've been clicking around
   in `runClient` manually, do one extra wipe before your *first* pipeline
   run so any half-baked world geometry is gone:

   ```bash
   rm -rf runs/client/saves run/saves   # whichever exists in this project
   ```

6. **Verify phase 1 passes**:

   ```bash
   ./test-pipeline/run.sh
   ```

   Phase 1 should report `smoke` (and the two TODO stubs) as passing. Phase 2
   will then launch a client and run the three starter steps. The first run
   has no baselines, so it just records `out/*.png` — review them, copy good
   ones into `expected/` to lock in the appearance.

## Growing the visual scenarios

The starter steps cover:

- `01_create_creative_world.sh` — bootstraps every later step
- `02_jei_spectral_infuser_renders.sh` — JEI search + recipe page
- `03_grenade_throw_no_crash.sh` — primary crash-bait scenario

Add more under `test-pipeline/steps/` whenever you touch code that the
GameTest framework can't easily exercise. Good candidates:

- Block UIs (the spectral infuser GUI itself, not just JEI's view of it)
- Custom HUD overlays from the WTHIT plugin
- Particle/sound on item use (visual-diff catches regressions GameTest won't)
- Config screen rendering (cloth-config) — opens via mod list
- Right-click behaviors that route through client-only code paths

Naming: `NN_short_description.sh`, where `NN` defines run order. Each step
runs in a subshell with `mc_key`, `mc_type`, `mc_click`, `mc_chat`, and
`MC_ADDR` already exported by `visual.sh`. Keep step scripts short; if logic
is complex, extract a helper into `test-pipeline/lib.sh` and source it.

## Growing the GameTest scenarios

Same file (`UsefulToolsGameTests.java`), one method per behavior. The
`empty_3x3` template is fine for most things — only build a new structure NBT
if you need pre-placed blocks. Mirror new methods between the two repos so
parity stays trivial to grep for:

```bash
diff <(grep -oE '@GameTest|public.*void [a-zA-Z]+\(' src/main/java/com/stonytark/usefultoolsmod/gametest/UsefulToolsGameTests.java) \
     <(ssh other-machine cat /path/to/other/UsefulToolsGameTests.java | grep -oE ...)
```

(In practice: when you add a method, tell the user; the other session adds
the matching method.)

## Sanity rules

- **Don't touch `_scaffolding/` from a project session.** That's the shared
  starter. Changes there should be intentional and coordinated.
- **Phase 2 only runs if phase 1 passes** — don't add a "skip gametest"
  flag; the gating is the point.
- **`out/` is gitignored**; **`expected/` is committed** (these are the
  approved baselines).
- If a visual diff fails, *look at the diff PNG before re-baselining*. A
  font-rendering hiccup is not the same as your mod's GUI moving.
- If `ydotool` calls hang, `ydotoold` isn't running — that's the #1
  failure mode.

## File map

```
_scaffolding/
├── INSTRUCTIONS.md                         # this file
├── test-pipeline/
│   ├── run.sh                              # phase 1 + dispatch to phase 2
│   ├── visual.sh                           # phase 2 driver
│   ├── .gitignore                          # ignores out/
│   └── steps/
│       ├── 01_create_creative_world.sh
│       ├── 02_jei_spectral_infuser_renders.sh
│       └── 03_grenade_throw_no_crash.sh
└── templates/
    ├── neoforge/
    │   ├── UsefulToolsGameTests.java
    │   └── build.gradle.patch
    └── fabric/
        ├── UsefulToolsGameTests.java
        ├── build.gradle.patch
        └── fabric.mod.json.patch
```
