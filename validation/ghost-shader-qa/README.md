# Ghost and Wraith shader validation

This is a captured regression check for the 1.21.1 NeoForge client. The client
ran in an isolated Xvfb display and disposable game directory, with Iris
1.8.14 beta 1, Sodium 0.8.13, and BSL v10.1.8 for the shader runs. The scene
places one entity at a fixed position and takes day and night captures. The
smoke log must confirm the shader pack loaded, all `/summon` and `/time`
commands succeeded, and the client exited cleanly.

| Capture | Ghost | Wraith | Outcome |
| --- | --- | --- | --- |
| No shader, original emissive path | [night](vanilla-adaptive/1.21.1-neoforge-ghost-night.png) | [night](vanilla-adaptive/1.21.1-neoforge-wraith-night.png) | Original glow retained |
| BSL, shader-aware lit path | [day](bsl-adaptive/1.21.1-neoforge-ghost-day.png), [night](bsl-adaptive/1.21.1-neoforge-ghost-night.png) | [day](bsl-adaptive/1.21.1-neoforge-wraith-day.png), [night](bsl-adaptive/1.21.1-neoforge-wraith-night.png) | Eyes, mouths, and silhouettes remain distinguishable |

The `*-cutout` directories show a rejected first fix: using the lit path even
without shaders darkened the vanilla night appearance, and under BSL the
Wraith was too dim. The adaptive implementation keeps the original path in
vanilla and raises minimum block light only when a shader pack is active.

Run `python validation/ghost-shader-qa/check-captures.py` to verify the four
saved smoke reports and the four day/night pairs. It checks face brightness
and internal contrast against the plain lit pass. The saved captures passed
this check. Review the images as well: pixel statistics alone cannot tell
whether a shader's bloom hides the eyes or mouth.

The BSL and vanilla runs passed the complete client smoke. Solas V3.7b did
report as loaded, but the software-rendered test client stayed on the terrain
loading screen, so its attempted run is **not** evidence of entity rendering.
Its log and failure report are saved in `solas-adaptive/` for diagnosis. Other
shader packs, loaders, GPUs, and Minecraft targets still need visual runs
before claiming broad shader compatibility.

To repeat the BSL run, use `scripts/smoke-client.py 1.21.1-neoforge` with
`--spectral-render-check`, `--client-mod` for Iris and Sodium, `--shader-pack`
for BSL, and a new `--output-dir`. The harness also accepts `--offline`,
`--skip-asset-download`, and `--asset-cache-source` when the development
runtime already has Minecraft assets cached.
