#!/usr/bin/env python3
"""Rebuild the isolated, source-matched Oculus 1.20.2 NeoForge experiment."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import shutil
import signal
import subprocess
import tarfile
import sys

from lighting_client_lifecycle import wait_client
from spectral_validation import acquire_client_admission

ROOT = Path(__file__).resolve().parents[1]
RESEARCH = ROOT / 'validation/shader-support-2.3.2/recovery/implementation/research'


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def cancelled(signum, frame):
    raise KeyboardInterrupt()


def build_prepared(work, project, record, java_home):
    """Build one verified private source tree with the shared resource ceiling."""
    output = work / 'build-receipt.json'
    try:
        admission = acquire_client_admission(Path.home() / '.cache/useful-tools-spectral-client.lock')
    except BlockingIOError:
        print('Client/build admission occupied; no private build started.', file=sys.stderr)
        return 75
    with admission:
        os.setpriority(os.PRIO_PROCESS, 0, max(10, os.getpriority(os.PRIO_PROCESS, 0)))
        if shutil.which('ionice'):
            subprocess.run(['ionice', '-c', '3', '-p', str(os.getpid())], check=True)
        signal.signal(signal.SIGTERM, cancelled)
        env = os.environ.copy()
        env['JAVA_HOME'] = str(java_home.resolve())
        env['JAVA_TOOL_OPTIONS'] = (env.get('JAVA_TOOL_OPTIONS', '')
                                   + ' -XX:ActiveProcessorCount=2').strip()
        command = ['bash', './gradlew', '--no-daemon', '--max-workers=1',
                   '-Dorg.gradle.internal.http.connectionTimeout=10000',
                   '-Dorg.gradle.internal.http.socketTimeout=15000', 'build']
        record.update(command=command, status='building', resource_policy={
            'nice': os.getpriority(os.PRIO_PROCESS, 0), 'active_processors': 2,
            'ordinary_client_or_build_slots': 1})
        output.write_text(json.dumps(record, indent=2) + '\n')
        with (work / 'build.log').open('w') as log:
            process = subprocess.Popen(command, cwd=project, env=env, stdout=log,
                                       stderr=subprocess.STDOUT, start_new_session=True)
            try:
                result = wait_client(process)
            except KeyboardInterrupt:
                record.update(status='cancelled; private process cleanup finished', exit_code=130)
                output.write_text(json.dumps(record, indent=2) + '\n')
                return 130
        record.update(status='build successful' if result == 0 else 'build failed', exit_code=result)
        if result == 0:
            record['artifacts'] = {str(path): digest(path)
                                   for path in (project / 'build/libs').glob('*.jar')}
        output.write_text(json.dumps(record, indent=2) + '\n')
        print(str(output))
        return result


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--work-dir', type=Path, required=True,
                        help='New isolated directory; existing contents are never overwritten')
    parser.add_argument('--embeddium', type=Path, required=True)
    parser.add_argument('--java-home', type=Path, default=Path('/usr/lib/jvm/java-17-openjdk'))
    parser.add_argument('--prepare-only', action='store_true')
    parser.add_argument('--build-prepared', action='store_true',
                        help='Verify an existing protected source tree, then acquire the single build slot')
    args = parser.parse_args()
    if args.prepare_only and args.build_prepared:
        parser.error('--prepare-only and --build-prepared are mutually exclusive')
    manifest = json.loads((RESEARCH / 'oculus-1202-neoforge-qa2-artifact.json').read_text())
    archive = RESEARCH / 'oculus-1202-source.tar.gz'
    patch = RESEARCH / manifest['patch']
    assert digest(archive) == manifest['source_archive_sha256'], 'Upstream archive changed'
    assert digest(patch) == manifest['patch_sha256'], 'Reviewed native patch changed'
    dependencies = json.loads((RESEARCH / 'embeddium-1202-selection.json').read_text())
    native = next(row for row in dependencies if row['version']['loaders'] == ['neoforge'])
    dependency = args.embeddium.resolve()
    assert digest(dependency) == native['sha256'], 'Wrong native Embeddium artifact'
    work = args.work_dir.resolve()
    if args.build_prepared:
        record = json.loads((work / 'provenance.json').read_text())
        assert record['source_commit'] == manifest['source_commit'], 'Wrong source revision'
        assert record['source_archive_sha256'] == digest(archive), 'Wrong source archive'
        assert record['patch_sha256'] == digest(patch), 'Wrong native patch'
        assert record['minecraft'] == '1.20.2' and record['neoforge'] == '20.2.93', 'Wrong native pin'
        project = Path(record['project']).resolve()
        assert project.parent == work, 'Prepared project escaped its protected directory'
        assert set(record['prepared_source_sha256']) == set(manifest['changed_source_sha256'])
        for name, expected in record['prepared_source_sha256'].items():
            assert digest(project / name) == expected, 'Prepared source changed: ' + name
            if name != 'build.gradle':
                assert expected == manifest['changed_source_sha256'][name], 'Native patch source mismatch'
        old_build = '/tmp/usefultools-shaders-2.3.2/mods/embeddium-neoforge-0.2.10+mc1.20.2.jar'
        restored_build = (project / 'build.gradle').read_text().replace(
            'verified-dependencies/embeddium-neoforge.jar', old_build)
        assert hashlib.sha256(restored_build.encode()).hexdigest() == manifest['changed_source_sha256']['build.gradle']
        assert digest(project / 'verified-dependencies/embeddium-neoforge.jar') == native['sha256']
        # Verify untouched upstream files too. A source-matched private build
        # cannot accept an extra or silently modified implementation file.
        upstream_files = set()
        with tarfile.open(archive) as source:
            for member in source.getmembers():
                if not member.isfile():
                    continue
                name = str(Path(*Path(member.name).parts[1:]))
                upstream_files.add(name)
                if name in manifest['changed_source_sha256']:
                    continue
                upstream = source.extractfile(member)
                assert upstream is not None
                assert digest(project / name) == hashlib.sha256(upstream.read()).hexdigest(), 'Untouched upstream source changed: ' + name
        expected_sources = {name for name in upstream_files | set(manifest['changed_source_sha256'])
                            if name.startswith('src/')}
        actual_sources = {str(path.relative_to(project)) for path in (project / 'src').rglob('*')
                          if path.is_file()}
        assert actual_sources == expected_sources, 'Unexpected private implementation files'
        record['complete_upstream_file_integrity_verified'] = True
        raise SystemExit(build_prepared(work, project, record, args.java_home))
    if work.exists():
        raise SystemExit('Work directory already exists: ' + str(work))
    work.mkdir(parents=True)
    with tarfile.open(archive) as source:
        source.extractall(work, filter='data')
    children = list(work.iterdir())
    assert len(children) == 1 and children[0].is_dir(), 'Unexpected upstream archive layout'
    project = children[0]
    subprocess.run(['patch', '--batch', '--fuzz=0', '-p1', '-i', str(patch)],
                   cwd=project, check=True)
    for name, expected in manifest['changed_source_sha256'].items():
        assert digest(project / name) == expected, 'Patched source mismatch: ' + name
    # The preserved QA2 patch used a verified local dependency. Relocate only
    # that file reference after verifying every patched source byte above.
    private_dependency = project / 'verified-dependencies/embeddium-neoforge.jar'
    private_dependency.parent.mkdir()
    shutil.copy2(dependency, private_dependency)
    build_file = project / 'build.gradle'
    old = '/tmp/usefultools-shaders-2.3.2/mods/embeddium-neoforge-0.2.10+mc1.20.2.jar'
    text = build_file.read_text()
    assert text.count(old) == 1, 'Dependency reference changed'
    build_file.write_text(text.replace(old, 'verified-dependencies/embeddium-neoforge.jar'))
    record = {'source_commit': manifest['source_commit'],
              'source_archive_sha256': digest(archive), 'patch_sha256': digest(patch),
              'minecraft': '1.20.2', 'neoforge': '20.2.93',
              'embeddium_sha256': digest(private_dependency),
              'prepared_source_sha256': {name: digest(project / name)
                                        for name in manifest['changed_source_sha256']},
              'java_home': str(args.java_home), 'project': str(project),
              'scope': 'Private optional stack experiment; no maintained build pins changed',
              'status': 'prepared; build not run'}
    output = work / 'provenance.json'
    output.write_text(json.dumps(record, indent=2) + '\n')
    if args.prepare_only:
        print(str(output))
        return
    raise SystemExit(build_prepared(work, project, record, args.java_home))


if __name__ == '__main__':
    main()
