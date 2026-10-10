The two spectator captures were checked against the pinned Forge 26.2-65.1.2
`injected-sources.jar` (SHA-256
`2362a1796c00bbe4c36a6d3200776f1cf108cdff90e35dcd8a3deba9aa99fe08`),
`net/minecraft/client/renderer/entity/LivingEntityRenderer.java`.
`submit` computes `forceTransparent = !isBodyVisible && !state.isInvisibleToPlayer`.
`getRenderType` then selects `RenderTypes.entityTranslucentCullItemTarget(texture)`
when `forceTransparent` is true; otherwise an invisible, non-glowing entity
gets no body render type. The model tint for this branch uses the vanilla
`654311423` translucent color.

In this run, both `effect give` invisibility commands and `gamemode spectator`
commands were acknowledged. `ghost-invisible.png` and `wraith-invisible.png`
show the entities absent for the ordinary viewer. In spectator mode,
`ghost-spectator-invisible.png` and `wraith-spectator-invisible.png` show faint
models. `ghost-spectator-white-invisible.png` and
`wraith-spectator-white-invisible.png` add a bright control backdrop; the Wraith
model is pale and translucent there. The Wraith's dark appearance against the
black wall is consistent with its dark texture under vanilla spectator
translucency. These observations exercise the target-native invisibility and
spectator paths; they do not prove shader entity-gbuffer coverage.
