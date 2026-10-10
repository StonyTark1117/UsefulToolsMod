# Forge 1.7.10 lighting parity — 2.3.2

Scope: the two 1.7.10 light-emission gaps reported in this thread. Modern
versions already define the required levels and were not changed by this fix.
Hardened Glowstone's lack of emission is shared across versions and is outside
this version-specific parity repair.

| Requirement | Implementation | Runtime evidence |
| --- | --- | --- |
| Ectoplasm Lantern emits 12 | Native block light level | Placement source 12; adjacent block 11 |
| Legacy Soul Lantern also emits 12 | Same block subclass | Alias source 12; adjacent block 11 |
| Redstone disables the ward without extinguishing lantern light | Constant emission | Powered lantern remains 12; ward query false |
| Infuser emits 13 only while processing | Metadata bit 8; native light query | Processing source 13; adjacent block 12 |
| Idle, redstone-paused, disabled, unfueled, or output-blocked infuser emits 0 | Processing tick updates metadata and block lighting | Each stop condition checked against real world block-light storage |
| Inventory and crafting survive transitions | Metadata updates avoid block replacement; client tile refresh checks block identity | Input consumed; output infused; exactly one fuel consumed; same server tile retained |
| Lit state and inventory survive saves | Native Anvil chunk metadata and existing tile NBT | Actual chunk writer/reader round trip checks metadata, stored light, progress, and fuel |
| Existing infusers with metadata 0 recover | First processing tick sets bit 8 | Legacy metadata reset and next-tick source 13 checked |
| Nearby light clears on removal | Vanilla block-light propagation | Source and adjacent light become 0 after both removals |
| Client receives the same light transitions | Vanilla block-update packets | All nine stages confirm emitted, stored-source, and adjacent light on a real client |

The client scenario waits for each stage's light checks before advancing. In
particular, redstone stays applied until the client observes the off state.
The test mod is compiled into a separate source set and is absent from all
release artifacts. Both runs use disposable game directories; the client runs
on a separate Xvfb display.

Reproduce from the repository root:

```bash
(cd 1.7.10 && JAVA_HOME=/usr/lib/jvm/java-21-openjdk ./gradlew --no-daemon --max-workers=1 build)
PYTHONDONTWRITEBYTECODE=1 python scripts/audit-classic-lighting.py
PYTHONDONTWRITEBYTECODE=1 python scripts/capture-classic-lighting.py
```

Evidence:

- `build.log`, `final-build.log`: successful Forge build and existing 10 Java semantic tests.
- `runtime-checks.json`, `1.7.10-forge.log`, `1.7.10-forge.json`: 14 native server checks, shutdown, and process cleanup.
- `client/native-client.log`, `client/lighting-checks.json`, `client/1.7.10-forge.json`: native integrated-server/client assertions and cleanup.
- `artifact-audit.json`: tested revision, working-source/artifact hashes, packaged-source equality, and absence of QA classes.

Earlier harness attempts are retained as failure logs. They exposed test setup
issues (classpath duplication, RFG working-directory timing, separate Anvil tile
loading, and a redstone scenario that removed power too early). Those logs do
not certify completion; the final runtime reports do.
