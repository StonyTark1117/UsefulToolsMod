# Published v1.6 world-upgrade acceptance

Date: 2026-08-25

The source world was created by the independently downloaded CurseForge release
`UsefulTools-v1.6-1.7.10.jar`, not by this checkout. Its SHA-256 was
`a1d8127563b1200f4d314f4cc3f560b638a1b78be200f02aae8597b69fc2a815` and the
JAR contained 164 ZIP entries. `scripts/audit-classic-release.py` independently
verified that its bytecode registers the same 62 item paths and five block paths
listed in `CLASSIC_MIGRATION.md`.

## Fixture and upgrade

1. Forge 10.13.4.1614 was installed into an isolated temporary server and run
   with Java 8 plus only the published v1.6 JAR.
2. A fresh flat world was saved with all five published blocks placed in one
   loaded spawn chunk: `tile.rGoldBlock`, `tile.semBlock`, `tile.sOblock`,
   `tile.hrblock`, and `tile.rGoldore`.
3. The published JAR was replaced with the locally built
   `usefultoolsmod-2.3.0+mc.1.7.10.forge.jar` (tested SHA-256
   `0397bf7f7bc1390f716b034297e81b0d5e66acedacb0f27edbfa69936c5c75bb`).
4. The same server and world were restarted without regenerating or editing the
   save.

Forge reported the source mod version as 1.6 and the destination as
`2.3.0+mc.1.7.10.forge`. Useful Tools registered the complete 633-item,
21-block catalog plus 672 crafting and five furnace recipes. The server reached
`Done (0.218s)!`.

All five `testforblock` checks succeeded at their original coordinates after
the upgrade. Neither the FML log nor the ordinary server log contained an FML
missing-mapping event, unidentified mapping, failed injection, or substitution
failure. The upgraded world saved all three dimensions, released TCP 25565,
and exited with status zero.

The only error-class log entry was Forge 1.7.10's unsigned universal-JAR notice;
the obsolete Forge version-check endpoint also produced its known non-JSON
exception. Neither originated in Useful Tools or affected the upgrade.
