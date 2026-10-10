#!/usr/bin/env python3
"""Freeze existing exact matrix inputs and retain missing inputs explicitly."""
import argparse
import hashlib
import json
from pathlib import Path
import shutil


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('manifest', type=Path)
    parser.add_argument('--cache-root', type=Path, required=True)
    parser.add_argument('--output', type=Path, required=True)
    args = parser.parse_args()
    rows = json.loads(args.manifest.read_text())
    paths = {}
    flags = {'--artifact', '--artifact-provenance', '--client-mod', '--shader-pack', '--probe-agent'}
    for row in rows:
        command = row.get('command', [])
        for index, flag in enumerate(command[:-1]):
            if flag in flags:
                paths.setdefault(command[index + 1], set()).add(row['name'])
    frozen, missing = [], []
    for name, configurations in sorted(paths.items()):
        source = Path(name)
        if not source.is_file():
            missing.append(dict(path=name, configurations=sorted(configurations)))
            continue
        sha = digest(source)
        destination = args.cache_root / sha / source.name
        destination.parent.mkdir(parents=True, exist_ok=True)
        if destination.exists() and digest(destination) != sha:
            raise ValueError('Existing immutable cache entry changed: ' + str(destination))
        if not destination.exists():
            shutil.copyfile(source, destination)
        if digest(destination) != sha:
            raise ValueError('Copied input hash mismatch: ' + name)
        frozen.append(dict(path=name, durable=str(destination.resolve()), sha256=sha,
                           configurations=sorted(configurations)))
    record = dict(manifest=str(args.manifest.resolve()), manifest_sha256=digest(args.manifest),
                  frozen=frozen, missing=missing,
                  scope='Input preservation only; no build, runtime or visual qualification.')
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(json.dumps(record, indent=2) + '\n')
    print(json.dumps(dict(frozen=len(frozen), missing=len(missing))))
    return int(bool(missing))


if __name__ == '__main__':
    raise SystemExit(main())
