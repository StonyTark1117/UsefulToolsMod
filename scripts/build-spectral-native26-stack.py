#!/usr/bin/env python3
"""Rebuild a hash-recorded, isolated native Forge 26.x shader-stack experiment."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import subprocess
import tarfile
import zipfile

ROOT = Path(__file__).resolve().parents[1]


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def payload(path):
    with zipfile.ZipFile(path) as archive:
        return {name: hashlib.sha256(archive.read(name)).hexdigest()
                for name in archive.namelist() if not name.endswith('/')}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('provenance', type=Path)
    parser.add_argument('--work-dir', type=Path, required=True)
    parser.add_argument('--java-home', type=Path, required=True)
    parser.add_argument('--prepare-only', action='store_true')
    args = parser.parse_args()
    manifest = json.loads(args.provenance.read_text())
    archive = ROOT / manifest['source_archive']
    if digest(archive) != manifest['source_archive_sha256']:
        raise SystemExit('Source archive hash mismatch')
    work = args.work_dir.resolve()
    if work.exists():
        raise SystemExit('Work directory already exists: ' + str(work))
    work.mkdir(parents=True)
    with tarfile.open(archive) as source:
        source.extractall(work, filter='data')
    for name, expected in manifest['source_sha256'].items():
        if digest(work / name) != expected:
            raise SystemExit('Extracted source mismatch: ' + name)
    # Private compile dependencies remain immutable and are verified before use.
    for name, expected in manifest.get('compile_dependency_sha256', {}).items():
        if digest(Path(name)) != expected:
            raise SystemExit('Compile dependency mismatch: ' + name)
    record = {
        'minecraft': manifest['minecraft'], 'forge': manifest['forge'],
        'upstream_commit': manifest['upstream_commit'],
        'source_archive_sha256': digest(archive),
        'verified_source_count': len(manifest['source_sha256']),
        'java_home': str(args.java_home.resolve()),
        'status': 'prepared; build not run',
        'scope': 'Private optional stack experiment; maintained pins unchanged',
    }
    output = work / 'reproduction.json'
    if not args.prepare_only:
        env = os.environ.copy()
        env['JAVA_HOME'] = str(args.java_home.resolve())
        command = ['bash', './gradlew', '--no-daemon', '--max-workers=1',
                   '-Dorg.gradle.internal.http.connectionTimeout=10000',
                   '-Dorg.gradle.internal.http.socketTimeout=15000', 'jar']
        with (work / 'reproduction-build.log').open('w') as log:
            result = subprocess.run(command, cwd=work, env=env, stdout=log,
                                    stderr=subprocess.STDOUT)
        record.update(command=command, exit_code=result.returncode,
                      status='build successful' if result.returncode == 0 else 'build failed')
        if result.returncode == 0:
            jars = list((work / 'build/libs').glob('*.jar'))
            record['artifacts'] = {str(p): digest(p) for p in jars}
            recorded = Path(manifest['artifact'])
            record['recorded_artifact_available'] = recorded.is_file()
            record['exact_recorded_hash_reproduced'] = (
                len(jars) == 1 and digest(jars[0]) == manifest['sha256'])
            record['payload_identical_to_recorded_artifact'] = (
                len(jars) == 1 and recorded.is_file()
                and digest(recorded) == manifest['sha256']
                and payload(jars[0]) == payload(recorded))
        output.write_text(json.dumps(record, indent=2) + '\n')
        print(output)
        raise SystemExit(result.returncode)
    output.write_text(json.dumps(record, indent=2) + '\n')
    print(output)


if __name__ == '__main__':
    main()
