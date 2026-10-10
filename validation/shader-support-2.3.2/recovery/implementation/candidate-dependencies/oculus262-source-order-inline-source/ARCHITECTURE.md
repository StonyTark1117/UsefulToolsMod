# Architecture

## Status boundary

This beta implements a real Forge 26.2 full-screen shader pipeline, but not the
complete Oculus world renderer. It deliberately rejects unsupported pack
features instead of installing compatibility stubs or suppressing failures.

No mixins or access transformers are shipped. The obsolete 1.16.5 injection
surface was removed; the current GPU integration uses supported Minecraft and
Forge APIs.

## Lifecycle

`OculusForge` creates one `OculusClientService`, registers the resource reload
listener and config screen, contributes `OculusCompositePass` through
`AddFramePassEvent`, and closes GPU resources at client shutdown.

Pack activation follows two thread domains:

1. Reload preparation opens the selected pack, resolves bounded includes, and
   produces immutable adapted source on a preparation executor.
2. Reload application compiles the prepared programs through `GpuDevice` on the
   render thread and atomically installs the resulting pipeline.

Disabling shaders triggers the same reload path and closes the active uniform
buffers. Replacement compilation is transactional: after resource reload,
preparation or compilation failure recompiles the last-known-good prepared
source into a fresh GPU generation and exposes an explicit UI error. A runtime
render failure disables the failed pipeline and restores vanilla.

## Pack trust boundary

```text
shaderpacks
  -> ShaderPackRepository
    -> DirectoryPackFileSystem / ZipPackFileSystem
      -> VirtualPath normalization and bounded strict-UTF-8 reads
        -> ShaderSourceLoader include graph
          -> PackProgramCompiler
            -> Legacy shader adapters
              -> immutable PreparedPackPipeline
```

ZIPs are never extracted. Protections include relative-path enforcement,
canonical directory containment, duplicate archive entry rejection, entry and
archive size limits, compression-ratio limits, include cycle/depth/count limits,
and expanded-source limits.

## GPU pipeline

`PackProgramCompiler` discovers supported stages in deterministic order:

1. `deferred`, `deferred1` through `deferred15`
2. `composite`, `composite1` through `composite15`
3. `final`

Each fragment stage can use its matching `.vsh`; otherwise the compiler supplies
a fixed full-screen vertex shader. Source fingerprints are included in shader
resource identifiers so Minecraft's pipeline cache cannot alias different pack
generations.

`OculusCompositePass` declares the main target to the Forge frame graph. For an
active pipeline, it leases a same-size temporary color target and ping-pongs
successive stages between main and temporary color. Even stage counts require no
full-screen copy; odd stage counts require one final copy back to main. The
current main depth texture is available to recognized legacy depth samplers.
Stable render-target descriptors are cached by size.

Programs are represented by:

- `PreparedFinalProgram` / `PreparedPackPipeline`: CPU-side immutable source
- `PackFinalProgram`: compiled pipeline, sampler bindings, and optional uniform
  buffer
- `PackShaderPipeline`: ordered live program generation
- `ShaderPipelineManager`: active generation, failure fallback, and teardown

Minecraft owns cached compiled `RenderPipeline` objects. This port owns and
closes the mapped uniform ring buffers it allocates.

## Legacy source adaptation

The fragment adapter converts a deliberately bounded subset of legacy GLSL to
GLSL 330:

- `varying` inputs
- `texture2D` and `texture2DLod`
- `gl_FragColor` or `gl_FragData[0]`
- known main-color and main-depth sampler aliases
- a defined set of common scalar, vector, camera, fog, weather, view, and time
  uniforms

The vertex adapter translates legacy full-screen use of `ftransform`,
`gl_Vertex`, and `gl_MultiTexCoord0` to a `gl_VertexID` full-screen triangle.
Unknown uniforms, custom attributes, unavailable color attachments, multiple
fragment outputs, and unsupported built-ins are hard errors.

Minecraft 26.2 stores reversed depth. Recognized depth texture samples are
adapted to the forward-depth value expected by legacy post-processing shaders.
This does not yet solve reversed depth for future terrain or shadow programs.

## Backend policy

The active API is read from `RenderSystem.getDevice().getDeviceInfo()`:

- OpenGL: full-screen pipeline may activate
- Vulkan: shaders automatically disabled with a clear message
- unknown backend: shaders automatically disabled
- OptiFine present: shaders automatically disabled as incompatible

OpenGL shader-pack code is never attempted on Vulkan.

## Allocation and threading rules

- no pack reads, preprocessing, or compilation per frame
- no synchronous pack disk I/O on the render thread
- immutable prepared generations cross the executor boundary
- GPU resources are created, used, and closed on the render thread
- sampler and uniform layouts are compiled per program
- uniform locations are represented by a fixed std140-compatible block
- target descriptors are reused while dimensions are stable

## Required next architecture

Full pack compatibility still needs independently implemented, Forge-compatible
components:

- terrain/chunk gbuffer routing
- entity, block entity, particle, hand, overlay, sky, cloud, weather, and water
  stages
- multiple color/depth targets and pack directives
- shadow world rendering
- normal/specular/custom textures
- complete profiles/options/preprocessor semantics
- dimension-aware matrices and the remaining Iris/OptiFine uniform model

Those components must preserve the current boundaries: supported Forge APIs
where possible, narrow verified hooks only where required, explicit unsupported
features, transactional GPU ownership, and no incompatible-license code.
