# Useful Tools 2.3.1 acceptance gate

Version 2.3.1 is a fix candidate for all 16 published targets. It must not be
uploaded to GitHub, Modrinth, or CurseForge until every automated gate below is
green and the maintainer explicitly approves the visual captures.

## Fixed in source

- [x] Replace transparent `cube_all` Ectoplasm Lantern models with bounded frame,
  backed core, and non-occluding block behavior on all 15 modern targets.
- [x] Replace Mining Charge `cube_all` models with bounded solid-backed
  geometry, all six placement directions, and distinct unlit/lit states.
- [x] Use separate generated inventory models rather than inheriting placed
  block geometry.
- [x] Add equivalent 1.7.10 bounded blocks, opaque block-face textures, and
  separate original inventory sprites.
- [x] Rename Soul Lantern to Ectoplasm Lantern, use
  `usefultoolsmod:ectoplasm_lantern` as the canonical ID, and retain
  `usefultoolsmod:soul_lantern` as a deprecated compatibility alias.
- [x] Restore bounded selection/collision shapes without changing registry IDs,
  block entities, recipes, redstone behavior, or save data.
- [x] Add a Wraith WTHIT provider on every supported WTHIT target. It reports
  hostile identity, live Ectoplasm Lantern suppression, and held-weapon eligibility.
- [x] Keep WTHIT optional and keep 26.2 Forge free of an unavailable WTHIT API.
- [x] Audit Wraith spawning/config gating, targets, flight/lunge, ward response,
  damage immunity, loot, sound, advancement, renderer, and model. No missing
  advertised behavior was found beyond WTHIT observability.
- [x] Add `scripts/audit-2.3.1-fixes.py` and extend the packaged-JAR audit.

## Automated verification

- [x] `scripts/audit-2.3.1-fixes.py` passes 16/16 from a clean checkout.
- [x] Canonical catalog, generated registration/config tables, and classic
  generation are reproducible with zero unintended diff.
- [x] All 16 binary and source builds complete under their pinned Java versions.
- [x] All 15 loader-native GameTest profiles pass and leave no owned process.
  The 1.21.1 Forge Gradle launcher requires bounded post-pass termination.
- [x] All 15 modern datagen profiles reproduce the committed resource trees.
- [x] Optional integrations pass absent profiles and all supported present
  profiles, including WTHIT Wraith tooltips.
- [x] All five Fabric jars pass unchanged under the supported Quilt loader.
- [x] All 16 server and client smoke profiles reach readiness and cleanly release
  owned processes and ports.
- [x] `scripts/assemble-release.py` produces 16 binaries and 16 source jars in
  `release/2.3.1/`; `scripts/audit-release.py` verifies exact hashes, metadata,
  dependency bounds, assets, repaired models, and Java class levels.

## Required visual evidence

- [x] Ectoplasm Lantern placed over solid and transparent/non-solid floors, viewed
  from the top, bottom, and all four sides with no world holes or z-fighting.
- [x] Mining Charge placed in all six directions in both unlit and lit states,
  viewed over solid and transparent/non-solid neighbors.
- [x] Ectoplasm Lantern and Mining Charge inventory representations remain readable
  and do not reuse the world model.
- [x] Ghost and Wraith render/model captures are correct.
- [x] WTHIT installed: Wraith hunting, ward-suppressed, ordinary-weapon, and
  infused-weapon tooltip states are captured and correct.
- [x] WTHIT absent: the same build reaches a world with no optional-API error.

Store the reports and captures in the versioned `validation/*-2.3.1/`
directories and summarize them in `validation/2.3.1-remediation.md`.

## Git and publication hold

- [ ] Review the final diff and generated files for unrelated changes.
- [ ] Commit the verified fix as a focused 2.3.1 commit on `main`.
- [ ] Push `main`, create and push the 2.3.1 tag at that exact commit, then
  verify remote ancestry, tag target, and a clean worktree.
- [ ] Provide the commit, artifact manifest, captures, test results, and
  issue-by-issue remediation report to the maintainer.
- [ ] Obtain explicit maintainer approval of the visuals and fixes.

Do not create or upload a GitHub release, Modrinth version, or CurseForge file
before that final approval. A pushed commit/tag and local artifacts are review
inputs, not publication authorization.
