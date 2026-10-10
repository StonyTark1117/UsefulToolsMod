# License notes

This unofficial community port is distributed under `LGPL-3.0-only`, matching
the supplied Oculus source and modern Iris reference source.

The full license is in `LICENSE`.

## Source provenance

- Supplied Oculus 1.16.5 source: LGPL-3.0.
- Oculus `1.20.1-new` was inspected for Forge integration patterns:
  commit `b3b278134f719afe32ba8b6b5d3a93f052175afc`.
- Iris `26.2` was inspected for shader preprocessing behavior, Minecraft 26.2
  rendering behavior, and mappings: commit
  `8f3a7a35d780fe80c8cd3c8517f3fa3c4df3f18a`.
- `GlslPreprocessor` adapts the marker/listener technique from Iris
  `JcppProcessor` and `GlslCollectingListener` at that commit (LGPL-3.0-only).
- JCPP 1.4.14 is bundled and relocated for GLSL conditional preprocessing;
  JCPP is Apache-2.0 licensed.
- Embeddium `20.1/forge` was inspected only as the latest Forge architecture
  reference:
  commit `dee0ebde6aea8b158613cd3b8ea5cdf0c1f6a9bf`.

## Excluded code and dependencies

- No Sodium implementation code is copied or bundled. Current Sodium is
  PolyForm Shield 1.0.0 and is not treated as an LGPL implementation source.
- No AGPL `glsl-transformer` code or binary is copied or bundled.
- The prebuilt JARs from the supplied archive are not redistributed.
- Rubidium is not retained.
- No NeoForge artifact is represented as a Forge dependency.

The shader-pack safety code, Forge frame-graph integration, GPU resource
management, common-uniform block, and bounded legacy source adapters in this
port were independently written for this port. The upstream projects were used
to understand pack semantics and current Minecraft behavior; no Sodium
implementation was copied. No source file in `render/program` or
`render/pipeline` is a verbatim copy from the inspected projects.

Any future code adaptation must be recorded here with its origin, commit, files,
and license.

The original Oculus logo is not redistributed. This port uses an original
AI-assisted abstract shader-prism icon created specifically for the community
port. It is branding only and does not depict in-game rendering. The source
generation and platform-sized PNGs are documented under `publishing/`.

## Corresponding source

Corresponding source for `0.3.0-beta.1` is published in this repository. The
Minecraft 26.2 project is at the repository root, and the complete Minecraft
1.21.11 project is under `versions/1.21.11`. Both trees contain their build
scripts, Gradle wrapper, Java source, resources, tests, and third-party notices.
The matching generated sources JAR is additionally published with each binary.
The binary JAR embeds `LICENSE-oculus-forge.txt`, this file, and `NOTICE`.

The original Oculus repository URL in mod metadata is attribution and upstream
source context. It does not designate the upstream project as the support or
issue tracker for this unofficial build.
