#!/usr/bin/env python3
"""Create the deterministic publisher-derived Oculus 1.20.2 NeoForge artifact."""
import argparse
import hashlib
import json
from pathlib import Path
import subprocess
from zipfile import ZIP_DEFLATED, ZipFile, ZipInfo

ROOT = Path(__file__).resolve().parents[1]
IMPL = ROOT / "validation/shader-support-2.3.2/recovery/implementation"
RESEARCH = IMPL / "research"
PUBLISHER_SHA = "e566bf7783cd66b90fdb25f87f611b7eacb1f62e0b2a1fd122d07a294ce08327"
NATIVE_SHA = "a402decb6cdb3e5dec0b2cdd164e4412b327bd86bb83b0aac1f611d03f46a42e"
SOURCE_SHA = "cdf61716d1706be75dfc46ee51e07fa51566dce6273fc6de340bf8aacc34868b"
PATCH_SHA = "3589f9c48b4172b78489b73f1d612589693fc4208825d5e9651d549293a9987d"
NEOFORGE_SHA = "14738ecbf2b5322536c78c0a1de678ba457b3244a891f31efb5bac89eadee172"
FML_SHA = "a335ee8d684ad57b028956a5350c85940e6e3dd16dd1884a2914d53fc6acee79"
OLD_VERSION = b'version="1.8.0-neoforge-qa2"'
NEW_VERSION = b'version="1.8.0"'
PROVENANCE_NAME = "META-INF/useful-tools/oculus-1202-neoforge-publisher-compat.json"


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def stable_json(value):
    return (json.dumps(value, sort_keys=True, separators=(",", ":")) + "\n").encode()


def write_entry(output, name, data, is_dir=False):
    info = ZipInfo(name, (1980, 1, 1, 0, 0, 0))
    info.create_system = 3
    info.external_attr = ((0o40755 if is_dir else 0o100644) << 16)
    info.compress_type = ZIP_DEFLATED
    output.writestr(info, data, compress_type=ZIP_DEFLATED, compresslevel=9)


def verify_runtime_abi(neoforge, fml):
    assert digest(neoforge) == NEOFORGE_SHA, "NeoForge 20.2.93 runtime changed"
    assert digest(fml) == FML_SHA, "FML loader 1.0.16 runtime changed"
    requirements = {
        neoforge: {
            "net/neoforged/neoforge/client/ChunkRenderTypeSet.class": (b"of", b"asList"),
            "net/neoforged/neoforge/client/model/data/ModelData.class": (b"EMPTY", b"builder"),
            "net/neoforged/neoforge/client/gui/overlay/ExtendedGui.class": (b"render", b"shouldDrawSurvivalElements"),
            "net/neoforged/neoforge/client/ConfigScreenHandler.class": (b"getScreenFactoryFor",),
            "net/neoforged/neoforge/common/NeoForge.class": (b"EVENT_BUS",),
        },
        fml: {
            "net/neoforged/fml/loading/LoadingModList.class": (b"get", b"getModFileById"),
            "net/neoforged/fml/loading/FMLLoader.class": (b"getDist", b"getLoadingModList", b"isProduction"),
            "net/neoforged/fml/loading/FMLPaths.class": (b"GAMEDIR", b"get"),
        },
    }
    for archive, classes in requirements.items():
        with ZipFile(archive) as runtime:
            for name, symbols in classes.items():
                data = runtime.read(name)
                for symbol in symbols:
                    assert symbol in data, "required runtime symbol missing: " + name + "!" + symbol.decode()
    exact = {
        "net.neoforged.neoforge.client.ChunkRenderTypeSet": [
            "public static net.neoforged.neoforge.client.ChunkRenderTypeSet of(net.minecraft.client.renderer.RenderType...);\n    descriptor: ([Lnet/minecraft/client/renderer/RenderType;)Lnet/neoforged/neoforge/client/ChunkRenderTypeSet;",
            "public java.util.List<net.minecraft.client.renderer.RenderType> asList();\n    descriptor: ()Ljava/util/List;"],
        "net.neoforged.neoforge.client.model.data.ModelData": [
            "public static final net.neoforged.neoforge.client.model.data.ModelData EMPTY;\n    descriptor: Lnet/neoforged/neoforge/client/model/data/ModelData;",
            "public static net.neoforged.neoforge.client.model.data.ModelData$Builder builder();\n    descriptor: ()Lnet/neoforged/neoforge/client/model/data/ModelData$Builder;"],
        "net.neoforged.neoforge.client.gui.overlay.ExtendedGui": [
            "public net.neoforged.neoforge.client.gui.overlay.ExtendedGui(net.minecraft.client.Minecraft);\n    descriptor: (Lnet/minecraft/client/Minecraft;)V",
            "public void render(net.minecraft.client.gui.GuiGraphics, float);\n    descriptor: (Lnet/minecraft/client/gui/GuiGraphics;F)V"],
        "net.neoforged.neoforge.client.ConfigScreenHandler": [
            "public static java.util.Optional<java.util.function.BiFunction<net.minecraft.client.Minecraft, net.minecraft.client.gui.screens.Screen, net.minecraft.client.gui.screens.Screen>> getScreenFactoryFor(net.neoforged.neoforgespi.language.IModInfo);\n    descriptor: (Lnet/neoforged/neoforgespi/language/IModInfo;)Ljava/util/Optional;"],
        "net.neoforged.neoforge.common.NeoForge": [
            "public static final net.neoforged.bus.api.IEventBus EVENT_BUS;\n    descriptor: Lnet/neoforged/bus/api/IEventBus;"],
        "net.neoforged.fml.loading.LoadingModList": [
            "public static net.neoforged.fml.loading.LoadingModList get();\n    descriptor: ()Lnet/neoforged/fml/loading/LoadingModList;",
            "public net.neoforged.fml.loading.moddiscovery.ModFileInfo getModFileById(java.lang.String);\n    descriptor: (Ljava/lang/String;)Lnet/neoforged/fml/loading/moddiscovery/ModFileInfo;"],
        "net.neoforged.fml.loading.FMLLoader": [
            "public static net.neoforged.api.distmarker.Dist getDist();\n    descriptor: ()Lnet/neoforged/api/distmarker/Dist;",
            "public static boolean isProduction();\n    descriptor: ()Z"],
        "net.neoforged.fml.loading.FMLPaths": [
            "public java.nio.file.Path get();\n    descriptor: ()Ljava/nio/file/Path;"],
    }
    classpath = str(neoforge) + ":" + str(fml)
    for class_name, signatures in exact.items():
        result = subprocess.run(["javap", "-classpath", classpath, "-public", "-s", class_name],
                                check=True, text=True, capture_output=True)
        normalized = "\n".join(line.rstrip() for line in result.stdout.splitlines())
        for signature in signatures:
            assert signature in normalized, "required exact ABI signature missing: " + class_name
    return {"neoforge_sha256": NEOFORGE_SHA, "fml_loader_sha256": FML_SHA,
            "verified_classes": sum(len(classes) for classes in requirements.values())}


def build(publisher, native, neoforge, fml, output):
    expected = json.loads((RESEARCH / "oculus-1202-neoforge-qa2-artifact.json").read_text())
    assert digest(publisher) == PUBLISHER_SHA, "publisher archive changed"
    assert digest(native) == NATIVE_SHA, "reviewed native build changed"
    assert digest(RESEARCH / "oculus-1202-source.tar.gz") == SOURCE_SHA, "source archive changed"
    assert digest(RESEARCH / expected["patch"]) == PATCH_SHA, "reviewed port patch changed"
    assert expected["source_archive_sha256"] == SOURCE_SHA
    assert expected["patch_sha256"] == PATCH_SHA
    runtime_abi = verify_runtime_abi(neoforge, fml)
    if output.exists():
        raise SystemExit("Output already exists: " + str(output))
    with ZipFile(native) as source:
        names = source.namelist()
        assert len(names) == len(set(names)), "duplicate native archive entries"
        payloads = {name: source.read(name) for name in names if not name.endswith("/")}
    assert PROVENANCE_NAME not in names
    assert sum(b"net/minecraftforge" in data for data in payloads.values()) == 0
    assert sum(b"net/neoforged" in data for data in payloads.values()) == 26
    metadata = payloads["META-INF/mods.toml"]
    assert metadata.count(OLD_VERSION) == 1 and NEW_VERSION not in metadata
    assert b'modId="oculus"' in metadata and b'modId="neoforge"' in metadata
    assert b'versionRange="[20.2.93]"' in metadata
    payloads["META-INF/mods.toml"] = metadata.replace(OLD_VERSION, NEW_VERSION)
    provenance = {
        "schema": 1,
        "identity": {"mod_id": "oculus", "publisher": "Zenkai", "publisher_version": "1.8.0"},
        "publisher_archive": {"name": publisher.name, "sha256": PUBLISHER_SHA},
        "source": {"commit": expected["source_commit"], "archive_sha256": SOURCE_SHA,
                   "url": expected["source_url"]},
        "neoforge_port": {"minecraft": "1.20.2", "neoforge": "20.2.93",
                          "patch_sha256": PATCH_SHA, "compiled_native_sha256": NATIVE_SHA,
                          "runtime_abi": runtime_abi},
        "disposition": "Publisher-derived source rebuild; not an unchanged publisher binary"
    }
    payloads[PROVENANCE_NAME] = stable_json(provenance)
    output.parent.mkdir(parents=True, exist_ok=True)
    with ZipFile(output, "w") as target:
        directories = sorted({part for name in payloads for part in
                              ["/".join(name.split("/")[:i]) + "/" for i in range(1, len(name.split("/")))]})
        for name in directories:
            write_entry(target, name, b"", True)
        for name in sorted(payloads):
            write_entry(target, name, payloads[name])
    result = {"artifact": str(output.resolve()), "sha256": digest(output),
              "publisher_sha256": PUBLISHER_SHA, "native_build_sha256": NATIVE_SHA,
              "source_sha256": SOURCE_SHA, "patch_sha256": PATCH_SHA,
              "runtime_abi": runtime_abi, "entries": len(payloads) + len(directories)}
    print(json.dumps(result, sort_keys=True))
    return result


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--publisher", type=Path, required=True)
    parser.add_argument("--native-build", type=Path, required=True)
    parser.add_argument("--neoforge", type=Path, required=True)
    parser.add_argument("--fml-loader", type=Path, required=True)
    parser.add_argument("--output", type=Path, required=True)
    args = parser.parse_args()
    build(args.publisher.resolve(), args.native_build.resolve(), args.neoforge.resolve(),
          args.fml_loader.resolve(), args.output.resolve())


if __name__ == "__main__":
    main()
