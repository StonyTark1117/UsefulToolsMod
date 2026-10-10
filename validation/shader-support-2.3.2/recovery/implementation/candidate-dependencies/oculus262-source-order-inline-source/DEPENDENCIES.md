# Dependencies

## Pinned build and runtime inputs

| Component | Version | Scope | Source |
|---|---:|---|---|
| Minecraft Java Edition | 26.2 | runtime | Mojang/Forge Maven |
| Minecraft Forge | 65.1.0 | runtime/compile | `net.minecraftforge:forge:26.2-65.1.0` |
| Java | 25 | build/runtime | Mojang manifest and Forge MDK |
| Gradle wrapper | 9.5.0 | build | official Forge 65.1.0 MDK |
| ForgeGradle | 7.0.31 | build plugin | official Forge Maven |
| EventBus validator | 7.0.5 | annotation processor | official Forge Maven |
| JUnit | 6.1.2 | tests only | Maven Central |

Repositories are limited to Forge's supported Minecraft/Forge repositories and
Maven Central. Versions are properties in `gradle.properties`; dynamic
rendering-mod versions and CurseMaven file IDs are absent.

The official Forge 65.1.0 MDK ZIP used to establish the build had SHA-256:

```text
fb9ca9d22ef5837e0b6d1778677a1b99358efe6969ef83ac5726710c107f754b
```

The Java runtime used for verification was Eclipse Temurin
25.0.4+7-LTS, SHA-256:

```text
7caab7db43bf4b94a2e6252c699e70d90084f9aa7c943cd3414761fd540937ae
```

## Renderer dependency decision

### Rubidium

The supplied 1.16.5 project used Rubidium 0.2.13. It is obsolete for the target
and is not retained.

### Embeddium

The latest official Embeddium Forge architecture inspected was branch
`20.1/forge`, commit
`dee0ebde6aea8b158613cd3b8ea5cdf0c1f6a9bf`. Official Forge builds stop at
Minecraft 1.20.1. No authoritative Forge 26.2 artifact was found, so no
Embeddium dependency is declared or fabricated.

### Iris and Sodium

Iris branch `26.2`, commit
`ff6f5aa1174f3bfcb540e405a9c259d29ff42b21`, is the closest mapping and rendering
reference. Its core terrain path depends on Sodium 0.9.1 and its current loader
module targets NeoForge rather than Forge.

Current Sodium source is licensed under PolyForm Shield 1.0.0. This repository
does not copy, adapt, bundle, or relicense Sodium implementation code. A NeoForge
binary is not declared as a Forge mod dependency.

### Consequence

There is no legally and technically suitable external terrain renderer for
Forge 26.2 in the researched sources. The current full-screen pipeline is
therefore independently integrated through Forge's frame graph and Minecraft's
GPU API. There is no required renderer-mod dependency.

A complete terrain shader pipeline still requires an independently implemented
Forge terrain bridge or a future authoritative compatible renderer release.
Vanilla terrain remains intact when shaders are disabled, and unsupported pack
features fail explicitly instead of being supplied fake values.

## Bundling

The binary JAR contains only this project's classes and resources. It does not
shade or relocate ANTLR, JCPP, `glsl-transformer`, Rubidium, Embeddium, Sodium,
or another mod. Development and test dependencies are not copied into the JAR.

Shader compilation uses the GLSL compiler exposed by Minecraft's active OpenGL
`GpuDevice`; no native compiler library is bundled.

## References inspected

- Oculus `1.20.1-new`:
  `b3b278134f719afe32ba8b6b5d3a93f052175afc`
- Iris `26.2`:
  `ff6f5aa1174f3bfcb540e405a9c259d29ff42b21`
- Embeddium `20.1/forge`:
  `dee0ebde6aea8b158613cd3b8ea5cdf0c1f6a9bf`

See `LICENSE_NOTES.md` for license treatment.
