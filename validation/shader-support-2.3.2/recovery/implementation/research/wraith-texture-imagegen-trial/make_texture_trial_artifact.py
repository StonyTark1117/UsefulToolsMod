"""Freeze a private 26.1.2 Forge Wraith-UV visual experiment, never a release build."""

import hashlib
import json
from pathlib import Path
import shutil
import zipfile


ROOT = Path("/home/braydon/Useful-Tools")
BASE = ROOT / "validation/shader-support-2.3.2/recovery/implementation"
ORIGINAL = (BASE / "candidate-dependencies/c0e94e10373ff3a4f2b70afb0ba5c1e9a2f7267a5c62e8de4c0f4bc0684ba6ba"
            / "usefultoolsmod-2.3.1+mc.26.1.2.forge.jar")
ORIGINAL_PROVENANCE = ORIGINAL.parent / "provenance.json"
TEXTURE = Path(__file__).with_name("wraith-uv-fill-trial.png")
ENTRY = "assets/usefultoolsmod/textures/entity/ghost/wraith.png"


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def main():
    if digest(ORIGINAL) != "c0e94e10373ff3a4f2b70afb0ba5c1e9a2f7267a5c62e8de4c0f4bc0684ba6ba":
        raise SystemExit("Pinned private source artifact changed")
    if digest(TEXTURE) != "2dc1a7b1933cadd4a4608db762bccd6078c8f9dd9c41c41338c8f9c15a172c27":
        raise SystemExit("Trial texture changed")
    temporary = Path(__file__).with_name("trial-artifact.tmp.jar")
    with zipfile.ZipFile(ORIGINAL) as source, zipfile.ZipFile(temporary, "w") as target:
        names = source.namelist()
        if names.count(ENTRY) != 1:
            raise SystemExit("Expected exactly one Wraith texture in artifact")
        for member in source.infolist():
            data = TEXTURE.read_bytes() if member.filename == ENTRY else source.read(member)
            target.writestr(member, data)
    with zipfile.ZipFile(ORIGINAL) as source, zipfile.ZipFile(temporary) as trial:
        changed = [name for name in source.namelist() if source.read(name) != trial.read(name)]
        if changed != [ENTRY]:
            raise SystemExit(f"Unexpected private artifact payload changes: {changed}")
    artifact_hash = digest(temporary)
    destination = BASE / "candidate-dependencies" / artifact_hash
    destination.mkdir(parents=True, exist_ok=True)
    frozen = destination / "usefultoolsmod-2.3.1+mc.26.1.2.forge-wraith-uv-trial.jar"
    shutil.copy2(temporary, frozen)
    temporary.unlink()
    original_record = json.loads(ORIGINAL_PROVENANCE.read_text())
    sources = dict(original_record["source_sha256"])
    sources[str(TEXTURE.relative_to(ROOT))] = digest(TEXTURE)
    record = {
        "target": "26.1.2-forge-private-wraith-uv-trial",
        "scope": "Private texture-only visual experiment; not a pinned build or release artifact.",
        "artifact": str(frozen),
        "artifact_sha256": artifact_hash,
        "source_sha256": sources,
        "original_artifact": str(ORIGINAL),
        "original_artifact_sha256": digest(ORIGINAL),
        "changed_zip_entries": [ENTRY],
        "texture_sha256": digest(TEXTURE),
    }
    (destination / "provenance.json").write_text(json.dumps(record, indent=2) + "\n")
    print(json.dumps({"artifact": str(frozen), "sha256": artifact_hash,
                      "provenance": str(destination / "provenance.json")}, indent=2))


if __name__ == "__main__":
    main()
