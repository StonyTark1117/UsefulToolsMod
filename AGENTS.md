# Useful Tools agent instructions

Before changing code, creating test profiles, or launching clients, read this
file and the task's referenced plans and acceptance criteria. Establish the
required configurations, validation coverage, and display/audio isolation
before setup. Temporary scripts, ad hoc experiments, and packaged launchers
must follow the same instructions as repository harnesses.

## Supported versions are part of the contract

All maintained targets are first-class and receive the requested features,
fixes, research, and validation. This applies equally to 1.7.10, 1.20.1,
1.20.2, 1.21.1, 26.1.2, and 26.2 on every supported loader. No version's
explicit mention gives permission to neglect another version or loader.
Minecraft 1.7.10 Forge is a first-class supported target, not an archived,
optional, maintenance-only, or best-effort port. Its age does not reduce scope.
Do not argue for dropping it or repeatedly ask whether it is included.
The extra 1.7.10 guidance addresses previous omissions; it adds no priority or
exclusivity and does not replace any obligation to newer targets. The research
and persistence requirements below apply to unfamiliar APIs on every version.

The current 16 artifact targets are:

| Minecraft | Loaders | Source root |
|---|---|---|
| 1.7.10 | Forge | `1.7.10/` |
| 1.20.1 | Forge, Fabric, NeoForge | `1.20.1/<loader>/` |
| 1.20.2 | Forge, Fabric, NeoForge | `1.20.2/<loader>/` |
| 1.21.1 | Forge, Fabric, NeoForge | `1.21.1/<loader>/` |
| 26.1.2 | Forge, Fabric, NeoForge | `26.1.2/<loader>/` |
| 26.2 | Forge, Fabric, NeoForge | `26.2/<loader>/` |

Honor the Fabric-on-Quilt compatibility contract described in `README.md` and
`COMPATIBILITY.md` when affected. `archived/` contains historical snapshots,
not additional maintained targets. Recheck the current support matrix at the
start of work; this table must not exclude a subsequently supported target.

Unless the user explicitly narrows the task, project-wide work includes every
supported target. Inventory affected behavior and acceptance checks per target
before implementation. Keep that scope through retries, handoffs, and context
compaction. A plan, skill default, test limitation, or agent-written document
cannot silently narrow the user's requirements. Only the user can authorize a
reduction in supported-version scope or acceptance criteria.

A correction calling out one neglected target is additional work within the
original goal, not a replacement goal. After fixing that target, resume all
remaining requirements. Do not interpret "finish previously refused work" or
similar follow-ups as permission to forget the original acceptance criteria.

## Implement against each target's actual APIs

Inspect each target's build files, dependencies, source, and resource formats.
Skills centered on modern Minecraft supply examples, not authority to exclude
older versions or upgrade their APIs. Preserve the requested player-visible
behavior through version-native implementations. A registered item, generic
adapter, placeholder, or generated catalog entry does not substitute for its
required gameplay, rendering, persistence, or networking behavior.

For 1.7.10, use Forge 10.13.4.1614 and the project's RetroFuturaGradle setup.
`1.7.10/build.gradle.kts` uses RFG 1.4.9 with a Java 8 mod toolchain; the matrix
launcher runs Gradle on Java 21. Do not confuse the Gradle JVM with the game's
or compiled artifact's Java requirements. Preserve existing registry IDs,
world migration, and side separation.

When an API is unfamiliar, research it before treating it as an obstacle:

1. Read working implementations in this target and the relevant build setup.
2. Inspect the exact resolved Forge/Minecraft sources, mappings, and dependency
   signatures. Locate or obtain matching source artifacts when absent; use
   bytecode/signature inspection if source is unavailable.
3. Consult version-matched upstream sources/documentation and established
   projects using that version. Record the relevant source locations or URLs;
   modern API documentation alone does not establish a 1.7.10 limitation.
4. Resolve remaining uncertainty with a small compilation or runtime experiment
   in the actual target, then implement and validate the required behavior.

Missing modern APIs, sparse documentation, unfamiliar names, and an initial
failed attempt require investigation or an equivalent implementation. They do
not establish that a feature is impossible or out of scope. Existing recipe
substitutions are in `catalog/classic_1_7_substitutions.json`; do not invent new
exceptions to feature parity without user authorization.

Before claiming a compatible dependency or shader stack is unavailable, check
current publisher releases, exact game/loader metadata, required dependencies,
and relevant maintained ports or forks. Record the sources and search scope.
Distinguish "not found in this search" from demonstrated incompatibility; an
older attempted release or a missing familiar dependency name does not rule out
a documented replacement. Investigate its actual dependencies and compatibility.

Keep alternate-runtime experiments isolated from maintained configurations.
Do not change repository pins or introduce APIs deprecated on the pinned target
merely to make an alternate profile pass. After relevant code or build changes,
rerun affected repository-pinned builds with their required warning gates,
including `scripts/strict-warnings.gradle` where applicable. Captures of an
earlier artifact cannot certify the rebuilt code, and an alternate-profile pass
cannot excuse a broken maintained build.

## Keep GUI and audio testing off the user's desktop

All agent-run client, GUI, rendering, screenshot, and interactive tests must use
an isolated Xvfb virtual display. This applies to every supported version,
including 1.7.10, and to ad hoc experiments as well as scripted validation.
Do not launch test windows on the user's desktop, take its focus, move its mouse,
send it keyboard input, resize its windows, change its resolution, or capture it.
Authorization to test the mod does not authorize controlling the user's desktop.

Use `scripts/smoke-client.py <target>` with its default Xvfb launch. Do not pass
`--display` to reuse the user's display. An existing display may be reused only
after verifying that it belongs to an isolated, agent-owned Xvfb test session.
For custom harnesses, allocate a free display and a bounded framebuffer (the
existing harness uses `1280x720x24`), wait for readiness, and explicitly propagate
its `DISPLAY` to the game and every input, screenshot, and window-control tool.
Do not inherit the host display or let tools fall back to it. Unset inherited
Wayland routing for X11 tests so it cannot bypass the virtual display. Verify
the destination display and test window before sending input or capturing.

Audio must also remain isolated from the user's speakers, headphones, microphone,
and active audio sessions. The current harness sets `ALSOFT_DRIVERS=null`; retain
that null backend for tests that do not validate sound. For sound behavior tests,
use an isolated audio server with a virtual/null sink and monitor capture, or an
equivalent isolated loopback backend. Explicitly route the game and recorder to
that backend, verify the selected route, and save the captured output. Do not
change the user's default devices, mute their audio, capture their microphone,
or allow fallback to physical devices. Null output without a recording does not
prove audible behavior; clearing `ALSOFT_DRIVERS=null` alone is not isolation.

Record the virtual display, framebuffer dimensions, audio backend/routing, and
relevant captures with the validation evidence. Give concurrent runs separate
displays, audio routes, and output paths. Clean up only resources owned by that
run, including its processes and virtual audio server/sinks; never kill shared
desktop or audio services.

If isolation fails, stop the affected launch/input operation, investigate and
repair the virtual environment, and continue other authorized work. Never fall
back to the main desktop or physical audio. Large host screens, mouse contention,
and convenience are not reasons to bypass isolation or skip required testing.
If Xvfb rendering cannot establish a required GPU/shader behavior, report that
specific validation gap with evidence and investigate an isolated rendering
solution; do not claim it passed or take over the user's desktop. Only a direct
user instruction explicitly authorizing a particular host-desktop interaction
can create an exception; an agent's plan or tooling preference cannot. The
CurseForge author-management exception below is already authorized by the user
and does not permit host-desktop mod testing.

## CurseForge author operations: author API first, browser fallback

For user-requested CurseForge publishing or project/file management, first
investigate and attempt the **author/upload API**, using its current official
documentation and available author credentials. This is distinct from the
public discovery/download API. Do not attempt author edits through the public
API, use a public API key as an author token, or treat its lack of write
operations as evidence that the requested author task is blocked.

Author API reference:
https://support.curseforge.com/support/solutions/articles/9000197321-curseforge-api
It documents author-token authentication with `X-Api-Token` and project file
upload/management operations. Verify the currently documented site/base URL,
operation, and payload rather than guessing endpoints or capabilities. Keep
credentials out of logs, command output, and documents.

For each requested operation, check author API support and safely available
credentials first. Use the documented author endpoint when supported and
authenticated. Record the operation attempted and its result without secrets.
If no corresponding author endpoint exists, document that specific capability
gap; do not issue a made-up write request merely to count as an attempt. If
author credentials are unavailable or a valid author operation fails, record
the concrete limitation and proceed to the browser fallback when available.
Do not repeatedly probe the public download API or stop at an API limitation.

The user authorizes control of the real, logged-in browser, including the host
desktop when needed, as a fallback for these CurseForge author controls. This
standing authorization applies within the requested publishing/editing scope;
do not ask again solely because the fallback uses the desktop. Prefer browser
controls that target the specific tab. When desktop input is necessary, verify
the active CurseForge author page before acting and keep input/capture limited
to that workflow. Do not interact with unrelated windows or tabs. This exception
does not authorize unrelated publication, deletion, account/security changes,
or launching Minecraft tests on the host desktop.

Use the existing authenticated browser session and the author dashboard's actual
controls. Verify the saved project/file state after submission and retain
non-secret evidence. If login, human verification, missing project permissions,
or unavailable browser-control tools prevent progress, identify that exact
external requirement. An author API capability gap alone is not a blocker while
the authorized browser path remains available. Never claim a save or publication
succeeded based only on preparing a payload or clicking a button.

## Validate the full requested scope

Give every affected target the same required depth of behavioral validation,
including both 1.7.10 and newer versions. Different tools are acceptable;
weaker acceptance coverage because a target is older, newer, or harder to test
is not. If a modern harness is unavailable, implement an equivalent target-native
test or run a reproducible in-game scenario. Startup alone does not prove a
feature works. Representative-target tests do not prove the entire matrix.

Define validation rows from the original requirements, not from whichever runs
succeeded. For loader/integration/shader work, distinguish Minecraft version,
loader and exact pinned version, integration/shader-loader version, shader pack
and version, and required scenarios. A pass on one loader does not cover another
loader on the same Minecraft version; a pass on one pack or stack does not cover
another required pack or stack. A different loader runtime cannot certify the
repository-pinned runtime. Diagnostic runs on alternate versions remain
diagnostic evidence until the required configuration is tested successfully.

Research missing compatible stacks for the exact target rather than treating
"none identified" as "none exists", using the research procedure above.
A crashed run, missing capture, or untested combination remains failed or
unverified even if another stack passed. Do not
label a required row "not applicable" merely because it is difficult or because
no compatible dependency has yet been found. Establish genuine incompatibility
with version-specific evidence and retain the unresolved requirement for the
user's scope decision.

Classify failures by the layer actually reached: launcher, test automation,
development mappings, dependency, mod, or shader. Production dependencies that
cannot load in a named development runtime require a correctly configured
packaged-client attempt, not a mod compatibility verdict. Fix rejected commands,
world permissions, confirmation screens, and other UI-flow failures before
drawing conclusions about rendering. A timeout must be investigated against the
actual process, logs, and screen state; it is not automatically incompatibility.

For behavioral captures, verify scene commands were accepted, the required
shader state is active, and the intended entities and scene are present. Require
a newly generated capture from the current run before copying or naming it;
never reuse the latest pre-existing screenshot when capture fails. A helper's
green exit code or a screenshot filename does not establish the scene passed.
Visually inspect required captures and link them to the actual tested artifact
and configuration. Preserve rejected commands and failed captures as failures.

Use the repository tooling after checking its actual coverage:

- `scripts/build-matrix.sh 1.7.10-forge` builds the classic target;
  `scripts/build-matrix.sh` builds all 16 targets.
- `scripts/smoke-server.py 1.7.10-forge` and
  `scripts/smoke-client.py 1.7.10-forge` provide runtime smoke harnesses.
  Inspect their options and assertions; extend coverage or run additional
  scenarios for the requested feature.
- `scripts/generate_classic_catalog.py` updates classic generated content after
  canonical changes. Inspect its resulting diff, including runtime behavior.
- `scripts/audit_catalog.py` checks catalog consistency and modern registration
  tables; it is not proof of classic gameplay parity.

Keep a per-target record of required behavior, implementation status, validation
status, commands/scenarios, results, and evidence paths. Include the tested
revision and relevant working-tree changes or artifact hashes so the evidence
identifies the code actually tested. Preserve logs, failure output, and runtime
captures when applicable. Stale evidence must not certify changed behavior.

Never fabricate runs, results, screenshots, or approval. Distinguish implemented,
build-verified, runtime-verified, failed, and not run. A skipped, timed-out,
unavailable, or unverified required check remains incomplete. Do not mark an
all-target goal complete while any required target or scenario lacks evidence.

## Blockers must establish an external obstruction

An unresolved coding problem is unfinished work, not automatically a blocker.
Before reporting that progress requires user input or an external change,
provide:

- The exact target, required behavior, and failing operation.
- Reproduction steps and the concrete error or observed result.
- The version-specific sources investigated and approaches actually attempted.
- Why available alternatives cannot satisfy the requirement, and the specific
  missing access, input, dependency, or external change needed to proceed.

"Old version", "unsupported by the skill", "no modern GameTest", "could not
find docs", and "API unfamiliar" are not sufficient blocker explanations.
Investigate recoverable tooling failures and continue independent authorized
work. A genuine external obstruction must remain visible as an incomplete
requirement; it does not authorize dropping the target or declaring success.

## Documentation and completion must match evidence

Do not edit support promises, acceptance checklists, tests, manifests, or audit
criteria to make incomplete work appear complete. Improve tests to cover the
original requirement; do not remove assertions or target rows to obtain a pass.
Document real limitations explicitly rather than relabeling them as success.

Before updating a checklist, changelog, validation report, or final summary,
check each claim against the implementation and saved evidence. Separate a
proposed plan from work performed. Report exactly which targets and scenarios
passed and which remain unfinished. If existing documents contain unsupported
claims, investigate and correct them without claiming unperformed validation.

After retracting a completion claim, restore the full original acceptance
checklist, reopen unsupported passes, and continue the remaining authorized work.
An explanation, apology, or corrected document does not complete the task.
Status questions and user corrections steer the existing objective; they do not
cancel or replace it unless the user explicitly says so. Answer briefly and
resume useful work without making the user restate the original requirements.

At completion, review every original requirement against every required target.
Recheck the entire acceptance matrix after resolving any singled-out gap.
Neither "one successful capture per Minecraft version" nor "1.7.10 now passes"
establishes completion of an all-loader/all-stack goal. Correcting an overstated
report is necessary, but does not complete the implementation or validation;
continue working through the outstanding rows.
Verify evidence coverage, not just green exit codes or an agent-written report.
Instructions alone are not an enforcement mechanism: do not claim this file
creates CI protection or guarantees agent compliance.
