"""Build and strictly verify the private Forge 64.0.8 / OptiFine K1 admission agent."""
from __future__ import annotations

import hashlib
import json
from pathlib import Path
import shutil
import subprocess
import zipfile


ROOT = Path("/home/braydon/Useful-Tools")
WORK = Path("/tmp/usefultools-optifine2612-k1-admission-offline")
JAVA = Path("/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2/bin")
NATIVE = Path("/home/braydon/.local/share/PrismLauncher/libraries/net/minecraftforge/forge/26.1.2-64.0.8/forge-26.1.2-64.0.8-client.jar")
DONOR = ROOT / "validation/shader-support-2.3.2/recovery/implementation/candidate-dependencies/89b6c8051570cf76259041ea13ca4ae5c0147b303c30d85c78d39a3f6305c633/native-options-quad-hooks-donor.jar"
OFFICIAL = ROOT / "validation/shader-support-2.3.2/recovery/implementation/candidate-dependencies/f8eb9026e4da2444e18d5601d3dede2bd19cf514d02095ffcdb0e101687c2172/preview_OptiFine_26.1.2_HD_U_K1_pre2.jar"
SOURCE = ROOT / "tests/spectral-qa/probe/Optifine2612K1AdmissionBridge.java"
CHECK = ROOT / "tests/spectral-qa/probe/Optifine2612K1AdmissionCheck.java"
OUT = ROOT / "validation/shader-support-2.3.2/recovery/implementation/research/optifine2612-k1-admission/offline-check.json"
ASM_ROOT = Path("/home/braydon/.gradle/caches/modules-2/files-2.1/org.ow2.asm")
ASM_NAMES = ("asm", "asm-tree", "asm-analysis", "asm-util")


def digest(path: Path) -> str:
    return hashlib.sha256(path.read_bytes()).hexdigest()


def asm_jar(name: str) -> Path:
    return next(path for path in (ASM_ROOT / name / "9.10.1").glob("*/*.jar")
                if path.name == f"{name}-9.10.1.jar")


def deterministic_entry(name: str) -> zipfile.ZipInfo:
    entry = zipfile.ZipInfo(name, (1980, 1, 1, 0, 0, 0))
    entry.compress_type = zipfile.ZIP_DEFLATED
    entry.external_attr = 0o100644 << 16
    return entry


def main() -> None:
    if WORK.exists():
        shutil.rmtree(WORK)
    classes = WORK / "classes"
    classes.mkdir(parents=True)
    libraries = [asm_jar(name) for name in ASM_NAMES]
    classpath = ":".join(map(str, libraries))
    compile_command = [str(JAVA / "javac"), "--release", "17", "-proc:none", "-Xlint:all",
                       "-Werror", "-cp", classpath, "-d", str(classes), str(SOURCE), str(CHECK)]
    compile_result = subprocess.run(compile_command, capture_output=True, text=True)
    if compile_result.returncode:
        raise SystemExit(compile_result.stderr)

    raw = WORK / "optifine2612-k1-admission-agent.jar"
    with zipfile.ZipFile(raw, "w", zipfile.ZIP_DEFLATED) as archive:
        archive.writestr(deterministic_entry("META-INF/MANIFEST.MF"), "Manifest-Version: 1.0\nPremain-Class: "
                         "com.stonytark.usefultoolsmod.spectralqa.Optifine2612K1AdmissionBridge\n\n")
        for path in sorted(classes.rglob("Optifine2612K1AdmissionBridge*.class")):
            name = str(path.relative_to(classes))
            archive.writestr(deterministic_entry(name), path.read_bytes())

    verify_command = [str(JAVA / "java"), "-cp", str(classes) + ":" + classpath,
                      "com.stonytark.usefultoolsmod.spectralqa.Optifine2612K1AdmissionCheck",
                      str(NATIVE), str(DONOR), str(OFFICIAL)]
    verify_result = subprocess.run(verify_command, capture_output=True, text=True)
    if verify_result.returncode:
        raise SystemExit(verify_result.stderr)

    agent_hash = digest(raw)
    frozen = ROOT / "validation/shader-support-2.3.2/recovery/implementation/candidate-dependencies" / agent_hash / "bridge.jar"
    frozen.parent.mkdir(parents=True, exist_ok=True)
    shutil.copy2(raw, frozen)
    output = ROOT / "validation/shader-support-2.3.2/recovery/implementation/stack-revisit/26.1.2-forge-optifine-k1-minimal-admission-disabled-v1"
    evidence = output / "k1-admission-bridge.log"
    prepared_runtime_command = [
        "python3", str(ROOT / "scripts/validate-packaged-spectral.py"),
        "--template", "/home/braydon/.local/share/PrismLauncher/instances/codex-shader-2612-fabric",
        "--artifact", str(ROOT / "validation/shader-support-2.3.2/recovery/implementation/candidate-dependencies/c0e94e10373ff3a4f2b70afb0ba5c1e9a2f7267a5c62e8de4c0f4bc0684ba6ba/usefultoolsmod-2.3.1+mc.26.1.2.forge.jar"),
        "--artifact-provenance", str(ROOT / "validation/shader-support-2.3.2/recovery/implementation/candidate-dependencies/c0e94e10373ff3a4f2b70afb0ba5c1e9a2f7267a5c62e8de4c0f4bc0684ba6ba/provenance.json"),
        "--minecraft-version", "26.1.2",
        "--world-source", "/home/braydon/.local/share/PrismLauncher/instances/codex-shader-2612-fabric/minecraft/saves",
        "--java-path", "/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2/bin/java",
        "--loader-uid", "net.minecraftforge", "--loader-version", "64.0.8",
        "--menu-world", "--mode", "disabled", "--shader-integration", "optifine",
        "--output", str(output),
        "--gpu-virtualgl", str(ROOT / "validation/shader-support-2.3.2/recovery/implementation/tooling/virtualgl-3.1.5"),
        "--client-mod", str(OFFICIAL), "--egl-device", "egl0",
        "--entities", "wraith", "--scenes", "indoor", "--client-admission-wait-seconds", "0",
        "--jvm-arg=-javaagent:" + str(frozen) + "=" + str(DONOR) + "!" + str(evidence),
    ]
    record = {
        "scope": "Private disabled-client admission diagnostic only; not a publisher or maintained integration pass.",
        "minecraft": "26.1.2",
        "forge": "64.0.8",
        "optifine": "K1_pre2",
        "compile_command": compile_command,
        "compile_exit_code": compile_result.returncode,
        "compile_stderr": compile_result.stderr,
        "verify_command": verify_command,
        "verify_exit_code": verify_result.returncode,
        "verify_stdout": verify_result.stdout,
        "verify_stderr": verify_result.stderr,
        "native_sha256": digest(NATIVE),
        "donor_sha256": digest(DONOR),
        "official_k1_sha256": digest(OFFICIAL),
        "agent_sha256": agent_hash,
        "agent": str(frozen),
        "sources": {str(path.relative_to(ROOT)): digest(path) for path in (SOURCE, CHECK)},
        "asm_libraries": {str(path): digest(path) for path in libraries},
        "selected_families": ["BlockState", "BossHealthOverlay", "Camera", "FluidRenderer", "FogRenderer", "Frustum", "GameRenderer", "GlCommandEncoder", "GlDevice", "GlStateManager", "GlTexture", "GpuDevice", "GpuTexture", "GuiGraphicsExtractor", "GuiRenderer", "Identifier", "ItemFrameRenderer", "LoadingOverlay", "ModelManager", "ModelSubmit", "Options", "QuadCollection", "QuadInstance", "RenderSectionRegion", "RenderTarget", "SectionCompiler", "SpriteContents", "TextureAtlas", "TextureAtlasSprite", "VertexFormat"],
        "prepared_runtime_command": prepared_runtime_command,
        "isolation_contract": {
            "display": "runner-owned unique Xvfb, 1280x720x24; inherited Wayland unset by harness",
            "audio": "ALSOFT_DRIVERS=null supplied by harness",
            "admission": "fail-fast ordinary-client lock; no profile on exit 75",
            "output": str(output),
        },
        "runtime_qualified": False,
    }
    OUT.write_text(json.dumps(record, indent=2) + "\n")
    print(json.dumps({"agent": str(frozen), "sha256": agent_hash,
                      "verified_classes": sum(line.startswith("verified_class=")
                                              for line in verify_result.stdout.splitlines())}, indent=2))


if __name__ == "__main__":
    main()
