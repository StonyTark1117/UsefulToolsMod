# 2.3.0 visual acceptance

Date: 2026-08-25

## Result

PASS. The visual gate combines live client captures with direct inspection of
the underlying pixel assets. All captures below are repository evidence from
isolated development runs; no image is a mock-up.

## Matrix configuration screens

`validation/client-smoke/config-matrix-montage.png` contains the config screen
captured by each of the 16 release targets. The screens are readable, use the
expected version-native presentation, expose category navigation, and have no
overlapping or clipped controls. Their individual source captures and JSON
results remain under `validation/client-smoke/`; the matrix summary records
16/16 successful config opens, world joins, clean exits, and process cleanup.

## Spectral Infuser and Ghost

The 1.7.10 live captures include the placed Spectral Infuser in daylight and at
night, its actual three-slot GUI, and the rendered Ghost from front, side, and
third-person views. These are collected in
`validation/classic-all-montage.png`. The Ghost is stable from each observed
angle and retains its intended translucent white texture.

For the shared modern implementation, `validation/modern-asset-montage.png`
includes the complete Infuser GUI texture, the Ghost texture, both ectoplasm
armor layers, and representative armor item families. The live modern client
loaded the block, armor, GUI, entity, and JEI atlases without missing-texture,
missing-model, or renderer exceptions. Loader-native GameTests separately
exercise the Infuser menu/block entity and Ghost construction on all 15 modern
targets, so the asset inspection is paired with live registration evidence.

## Armor and representative content families

The asset montage samples spectral, emissive, mineral, food, mob-drop, and
overpowered armor families and the two equipped ectoplasm armor layouts. No
sample has a corrupt decode, unintended opaque background, or missing layer.
The catalog unit gate additionally decodes and requires every generated item
and armor texture, rather than only the visual sample.

`validation/visual-client/1.21.1-neoforge-inventory.png` is a live JEI inventory
view filtered to Useful Tools. It shows the representative tool/material
families and validates that their item models populate the recipe-viewer list.

## JEI and WTHIT

The representative 1.21.1 NeoForge client loaded JEI 19.27.0.340 and WTHIT
12.10.1 together. `validation/visual-client/1.21.1-neoforge-world.png` shows the
live WTHIT overlay and the inventory capture shows the live JEI list. Both
Useful Tools WTHIT plugins and the Useful Tools JEI recipe/category plugin
initialized in `validation/visual-client/1.21.1-neoforge.log`.

This client check found one project-owned interoperability defect that the
server matrix could not expose: the custom SOB incorrect-mining tag added
bedrock outside the vanilla tier hierarchy, causing WTHIT comparison errors.
The tag now inherits only `minecraft:incorrect_for_diamond_tool` on 1.21.1 and
26.x. The final rerun has no tier-comparison, missing-texture, missing-model, or
integration error, joins a world, captures both views, saves every dimension,
exits cleanly, and leaves no owned process.
