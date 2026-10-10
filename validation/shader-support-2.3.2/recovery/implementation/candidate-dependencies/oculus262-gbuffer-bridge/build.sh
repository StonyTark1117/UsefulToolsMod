#!/usr/bin/env bash
set -euo pipefail

here=$(cd -- "$(dirname -- "$0")" && pwd)
source_root="$here/../oculus262-source-order-inline-source"
jdk=/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2
agent_classes="$here/agent-classes"
stage="$here/stage-v1"

JAVA_HOME="$jdk" PATH="$jdk/bin:$PATH" bash "$source_root/gradlew" \
  -p "$source_root" -Pforge_version=65.1.2 compileJava compileGbufferDonorJava test --no-daemon --warning-mode all

mkdir -p "$agent_classes" "$stage/com/stonytark/usefultoolsmod/spectralqa" \
  "$stage/donor/dev/oculusforge/render/program" "$stage/donor/com/mojang/blaze3d/opengl"
"$jdk/bin/javac" --release 25 -Xlint:all -d "$agent_classes" \
  "$here/src/com/stonytark/usefultoolsmod/spectralqa/Oculus262GbufferBridge.java"
install -m 0644 "$agent_classes/com/stonytark/usefultoolsmod/spectralqa/Oculus262GbufferBridge.class" \
  "$stage/com/stonytark/usefultoolsmod/spectralqa/Oculus262GbufferBridge.class"
install -m 0644 "$agent_classes/com/stonytark/usefultoolsmod/spectralqa/Oculus262GbufferBridge\$Fingerprints.class" \
  "$stage/com/stonytark/usefultoolsmod/spectralqa/Oculus262GbufferBridge\$Fingerprints.class"
install -m 0644 "$agent_classes/com/stonytark/usefultoolsmod/spectralqa/Oculus262GbufferBridge\$1.class" \
  "$stage/com/stonytark/usefultoolsmod/spectralqa/Oculus262GbufferBridge\$1.class"

main_classes="$source_root/build/sourceSets/main"
donor_classes="$source_root/build/sourceSets/gbufferDonor"
for class_name in LegacyFinalShaderAdapter PackProgramCompiler PreparedPackPipeline PackShaderPipeline; do
  install -m 0644 "$main_classes/dev/oculusforge/render/program/$class_name.class" \
    "$stage/donor/dev/oculusforge/render/program/$class_name.class"
done
install -m 0644 "$donor_classes/com/mojang/blaze3d/opengl/GlRenderPass.class" \
  "$stage/donor/com/mojang/blaze3d/opengl/GlRenderPass.class"

"$jdk/bin/jar" --create --file "$here/bridge-v1.jar" --manifest "$here/MANIFEST.MF" \
  --date=2026-10-07T00:00:00Z \
  -C "$stage" com/stonytark/usefultoolsmod/spectralqa/Oculus262GbufferBridge.class \
  -C "$stage" 'com/stonytark/usefultoolsmod/spectralqa/Oculus262GbufferBridge$Fingerprints.class' \
  -C "$stage" 'com/stonytark/usefultoolsmod/spectralqa/Oculus262GbufferBridge$1.class' \
  -C "$stage" donor/dev/oculusforge/render/program/LegacyFinalShaderAdapter.class \
  -C "$stage" donor/dev/oculusforge/render/program/PackProgramCompiler.class \
  -C "$stage" donor/dev/oculusforge/render/program/PreparedPackPipeline.class \
  -C "$stage" donor/dev/oculusforge/render/program/PackShaderPipeline.class \
  -C "$stage" donor/com/mojang/blaze3d/opengl/GlRenderPass.class
sha256sum "$here/bridge-v1.jar"
