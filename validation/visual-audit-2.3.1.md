# 2.3.1 visual acceptance candidate

Date: 2026-08-26

## Status

AUTOMATED CAPTURE PASS; MANUAL APPROVAL PENDING. These are live 1.21.1
NeoForge client captures from the 2.3.1 source tree with JEI 19.27.0.340 and
WTHIT 12.10.1 loaded. No 2.3.1 release may be published until the user reviews
and explicitly approves these visuals.

## Block geometry

`validation/visual-client-2.3.1/1.21.1-neoforge-blocks.png` is contextual proof
of the bounded Ectoplasm Lantern only; the nearby vanilla redstone block and
background charges are not Mining Charge acceptance evidence.

The dedicated Mining Charge evidence is:

- `1.21.1-neoforge-mining-charge-unlit.png`: six labeled FACING variants in
  the unlit state.
- `1.21.1-neoforge-mining-charge-lit.png`: the same six labeled variants with
  the exposed red armed-state plate.
- `1.21.1-neoforge-mining-charge-glass.png`: labeled unlit/lit downward-facing
  charges over glass, with the terrain visible through the support but no view
  through either charge's casing or interior.

These captures show bounded three-dimensional casing, visibly distinct armed
state, and no missing-model texture, unbounded item plane, z-fighting, exposed
block interior, or floor-view alpha leak.

The resolver audit separately checks every blockstate, model, texture, facing,
and lit variant in all 16 targets, including the Java 8 classic assets.

## WTHIT and entity rendering

`validation/visual-client-2.3.1/1.21.1-neoforge-wraith-wthit.png` shows the live
Wraith renderer and its Useful Tools WTHIT body: hostile identity, active
Ectoplasm Lantern repulsion, and the held infused weapon's effective status.

`validation/visual-client-2.3.1/1.21.1-neoforge-ghost-wthit.png` shows the live
Ghost renderer and existing player-facing visibility and weapon guidance. The
two captures demonstrate that Wraith coverage now matches the useful quality
of the pre-existing Ghost provider without copying Ghost biology.

## Config and item presentation

`validation/visual-client-2.3.1/1.21.1-neoforge-config.png` shows exactly one
Ectoplasm Lantern control. Seven option rows end above a separate navigation
row, so the previous option/button overlap is gone. The legacy
`soulLanternEnabled` migration key remains accepted internally but is
intentionally hidden from the player-facing screen.

`validation/visual-client-2.3.1/1.21.1-neoforge-inventory.png` shows the live JEI
item list filtered with `@usefultoolsmod`, including the repaired block items,
spawn eggs, tools, materials, and other Useful Tools families.

The client joined an isolated world, accepted the deterministic showcase
commands, captured every view, saved all dimensions, exited through the UI,
and left no owned process behind. The corresponding JSON report and complete
client log are in the same directory.
