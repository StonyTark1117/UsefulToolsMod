#!/usr/bin/env python3
"""Collect already-built jars and create a checksummed local release manifest."""

from __future__ import annotations

import hashlib
import json
import shutil
import subprocess
import sys
import tomllib
import zipfile
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
RELEASE = ROOT / "release/2.3.0"

TARGETS = [
    ("1.7.10", "forge", "8", "1.7.10/build/libs"),
    ("1.20.1", "forge", "17", "1.20.1/forge/build/libs"),
    ("1.20.1", "fabric", "17", "1.20.1/fabric/build/libs"),
    ("1.20.1", "neoforge", "17", "1.20.1/neoforge/build/libs"),
    ("1.20.2", "forge", "17", "1.20.2/forge/build/libs"),
    ("1.20.2", "fabric", "17", "1.20.2/fabric/build/libs"),
    ("1.20.2", "neoforge", "17", "1.20.2/neoforge/build/libs"),
    ("1.21.1", "forge", "21", "1.21.1/forge-standalone/build/libs"),
    ("1.21.1", "fabric", "21", "1.21.1/fabric/build/libs"),
    ("1.21.1", "neoforge", "21", "1.21.1/neoforge/build/libs"),
    ("26.1.2", "forge", "25", "26.1.2/forge/build/libs"),
    ("26.1.2", "fabric", "25", "26.1.2/fabric/build/libs"),
    ("26.1.2", "neoforge", "25", "26.1.2/neoforge/build/libs"),
    ("26.2", "forge", "25", "26.2/forge/build/libs"),
    ("26.2", "fabric", "25", "26.2/fabric/build/libs"),
    ("26.2", "neoforge", "25", "26.2/neoforge/build/libs"),
]


def choose(directory: Path, sources: bool) -> Path:
    jars = [p for p in directory.glob("*.jar") if ("sources" in p.name) == sources
            and not any(token in p.name for token in ("-dev", "javadoc", "-all"))]
    if not jars:
        raise SystemExit(f"missing {'source' if sources else 'binary'} jar in {directory}")
    return max(jars, key=lambda p: p.stat().st_mtime)


def digest(path: Path) -> str:
    h = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            h.update(chunk)
    return h.hexdigest()


def metadata_name(minecraft: str, loader: str) -> str:
    if minecraft == "1.7.10":
        return "mcmod.info"
    if loader == "fabric":
        return "fabric.mod.json"
    if loader == "neoforge" and minecraft not in {"1.20.1", "1.20.2"}:
        return "META-INF/neoforge.mods.toml"
    return "META-INF/mods.toml"


def dependency_bounds(path: Path, minecraft: str, loader: str) -> dict[str, dict[str, object]]:
    """Read the bounds actually shipped in the binary, not Gradle's inputs."""
    with zipfile.ZipFile(path) as jar:
        raw = jar.read(metadata_name(minecraft, loader)).decode("utf-8")

    required: dict[str, object] = {}
    optional: dict[str, object] = {}
    if minecraft == "1.7.10":
        metadata = json.loads(raw)[0]
        required = {dependency: "*" for dependency in metadata.get("dependencies", [])}
    elif loader == "fabric":
        metadata = json.loads(raw)
        required = dict(metadata.get("depends", {}))
        optional = {
            **metadata.get("recommends", {}),
            **metadata.get("suggests", {}),
        }
    else:
        metadata = tomllib.loads(raw)
        for dependency in metadata.get("dependencies", {}).get("usefultoolsmod", []):
            dependency_id = dependency["modId"]
            bound = dependency.get("versionRange", "*")
            is_required = dependency.get("mandatory", dependency.get("type") != "optional")
            (required if is_required else optional)[dependency_id] = bound
    return {"required": required, "optional": optional}


def main() -> None:
    if RELEASE.exists():
        shutil.rmtree(RELEASE)
    RELEASE.mkdir(parents=True, exist_ok=True)
    entries = []
    for minecraft, loader, java, relative in TARGETS:
        source_dir = ROOT / relative
        binary = choose(source_dir, False)
        sources = choose(source_dir, True)
        bounds = dependency_bounds(binary, minecraft, loader)
        base = f"usefultoolsmod-2.3.0-{minecraft}-{loader}"
        for source, suffix, kind in ((binary, ".jar", "binary"), (sources, "-sources.jar", "sources")):
            destination = RELEASE / f"{base}{suffix}"
            shutil.copy2(source, destination)
            if not zipfile.is_zipfile(destination):
                raise SystemExit(f"invalid jar: {destination}")
            entries.append({
                "minecraft": minecraft,
                "loader": loader,
                "java": int(java),
                "dependency_bounds": bounds,
                "kind": kind,
                "file": destination.name,
                "bytes": destination.stat().st_size,
                "sha256": digest(destination),
            })
    (RELEASE / "artifacts.json").write_text(json.dumps({"version": "2.3.0", "artifacts": entries}, indent=2) + "\n")
    (RELEASE / "SHA256SUMS").write_text("".join(f"{e['sha256']}  {e['file']}\n" for e in entries))
    print(f"assembled {len(entries)} files in {RELEASE}")
    subprocess.run([sys.executable, str(ROOT / "scripts/audit-release.py")], check=True)


if __name__ == "__main__":
    main()
