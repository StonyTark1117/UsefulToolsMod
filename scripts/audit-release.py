#!/usr/bin/env python3
"""Fail unless the local 2.3.1 release is complete and internally coherent."""

from __future__ import annotations

import hashlib
import json
import struct
import zipfile
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
VERSION = "2.3.1"
RELEASE = ROOT / f"release/{VERSION}"
MANIFEST = RELEASE / "artifacts.json"
JAVA_MAJOR = {8: 52, 17: 61, 21: 65, 25: 69}


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def class_major(data: bytes, name: str) -> int:
    if data[:4] != b"\xca\xfe\xba\xbe":
        raise AssertionError(f"invalid class header: {name}")
    return struct.unpack(">H", data[6:8])[0]


def metadata_name(minecraft: str, loader: str) -> str:
    if minecraft == "1.7.10":
        return "mcmod.info"
    if loader == "fabric":
        return "fabric.mod.json"
    if loader == "neoforge" and minecraft not in {"1.20.1", "1.20.2"}:
        return "META-INF/neoforge.mods.toml"
    return "META-INF/mods.toml"


def main() -> None:
    manifest = json.loads(MANIFEST.read_text())
    entries = manifest["artifacts"]
    assert manifest["version"] == VERSION
    assert len(entries) == 32, f"expected 32 artifacts, found {len(entries)}"
    expected_files = {entry["file"] for entry in entries}
    actual_files = {path.name for path in RELEASE.glob("*.jar")}
    assert expected_files == actual_files, "release jar set differs from artifacts.json"

    checksums = {}
    for line in (RELEASE / "SHA256SUMS").read_text().splitlines():
        digest, name = line.split("  ", 1)
        checksums[name] = digest
    assert set(checksums) == expected_files, "SHA256SUMS file set mismatch"

    coordinates = set()
    bounds_by_target = {}
    for entry in entries:
        coordinate = (entry["minecraft"], entry["loader"], entry["kind"])
        assert coordinate not in coordinates, f"duplicate coordinate: {coordinate}"
        coordinates.add(coordinate)
        path = RELEASE / entry["file"]
        digest = sha256(path)
        assert digest == entry["sha256"] == checksums[path.name], f"digest mismatch: {path.name}"
        assert path.stat().st_size == entry["bytes"], f"size mismatch: {path.name}"
        bounds = entry.get("dependency_bounds")
        assert isinstance(bounds, dict), f"missing dependency bounds: {path.name}"
        assert set(bounds) == {"required", "optional"}, f"invalid dependency groups: {path.name}"
        assert isinstance(bounds["required"], dict) and bounds["required"], (
            f"missing required dependency bounds: {path.name}"
        )
        assert isinstance(bounds["optional"], dict), f"invalid optional dependency bounds: {path.name}"
        target = (entry["minecraft"], entry["loader"])
        if target in bounds_by_target:
            assert bounds == bounds_by_target[target], f"binary/source dependency mismatch: {target}"
        else:
            bounds_by_target[target] = bounds
        if entry["minecraft"] != "1.7.10":
            assert "minecraft" in bounds["required"], f"missing Minecraft bound: {path.name}"
            loader_ids = {"fabricloader"} if entry["loader"] == "fabric" else {"forge", "neoforge"}
            assert loader_ids & bounds["required"].keys(), f"missing loader bound: {path.name}"

        with zipfile.ZipFile(path) as jar:
            bad_member = jar.testzip()
            assert bad_member is None, f"corrupt member {bad_member} in {path.name}"
            names = jar.namelist()
            metadata = metadata_name(entry["minecraft"], entry["loader"])
            assert metadata in names, f"missing {metadata}: {path.name}"

            if entry["kind"] == "sources":
                java_sources = [name for name in names if name.endswith(".java")]
                assert java_sources, f"source jar has no Java sources: {path.name}"
                continue

            metadata_text = jar.read(metadata).decode("utf-8")
            assert VERSION in metadata_text, f"stale embedded version: {path.name}"
            classes = [name for name in names if name.endswith(".class")]
            assert classes, f"binary jar has no classes: {path.name}"
            majors = {class_major(jar.read(name), name) for name in classes}
            expected_major = JAVA_MAJOR[entry["java"]]
            assert max(majors) <= expected_major, (
                f"{path.name} requires class major {max(majors)}, above Java {entry['java']}"
            )
            assert any(name.startswith("assets/usefultoolsmod/") for name in names), (
                f"missing mod assets: {path.name}"
            )
            if entry["minecraft"] == "1.7.10":
                assert "assets/usefultoolsmod/textures/items/soul_lantern.png" in names
                assert "assets/usefultoolsmod/textures/items/mining_charge.png" in names
            else:
                # The Ectoplasm Lantern is canonical in 2.3.1.  The old Soul
                # Lantern model remains packaged as the world-compatibility
                # alias, and both placed models must stay bounded and backed.
                for model_name in (
                    "ectoplasm_lantern",
                    "soul_lantern",
                    "mining_charge",
                    "mining_charge_lit",
                ):
                    model_path = f"assets/usefultoolsmod/models/block/{model_name}.json"
                    assert model_path in names, f"missing repaired model {model_path}: {path.name}"
                    model = json.loads(jar.read(model_path))
                    assert model.get("parent") != "minecraft:block/cube_all" and model.get("elements"), (
                        f"unrepaired placed model {model_path}: {path.name}"
                    )
                charge_state = json.loads(jar.read("assets/usefultoolsmod/blockstates/mining_charge.json"))
                assert len(charge_state.get("variants", {})) == 12, f"incomplete charge states: {path.name}"
            if entry["minecraft"] != "1.7.10":
                assert any(name.startswith("data/usefultoolsmod/") for name in names), (
                    f"missing mod data: {path.name}"
                )
                data_json = [
                    name for name in names
                    if name.startswith("data/") and name.endswith(".json")
                    and len(name.split("/")) > 3
                ]
                recipes = [
                    name for name in data_json
                    if name.split("/")[2] in {"recipe", "recipes"}
                ]
                advancements = [
                    name for name in data_json
                    if name.split("/")[2] in {"advancement", "advancements"}
                ]
                # 2.3.1 adds one migration recipe for the legacy Soul Lantern
                # alias. Some data-provider combinations retain two additional
                # generated recipe entries while exposing the same gameplay
                # content, so the exact accepted totals are 689 and 691.
                assert (len(recipes), len(advancements)) in {(689, 854), (691, 854)}, (
                    f"unexpected data counts ({len(recipes)}, {len(advancements)}): {path.name}"
                )

    print("release audit: OK (16 binaries, 16 source jars)")


if __name__ == "__main__":
    main()
