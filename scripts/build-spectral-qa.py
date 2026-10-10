#!/usr/bin/env python3
"""Build separate acceptance helpers without adding them to release artifacts."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import subprocess

ROOT = Path(__file__).resolve().parents[1]
parser = argparse.ArgumentParser(description=__doc__)
parser.add_argument('helper', choices=('probe', 'classic', 'fabric1211'))
args = parser.parse_args()
env = os.environ.copy()
env['JAVA_HOME'] = '/usr/lib/jvm/java-21-openjdk'
if args.helper == 'probe':
    directory = ROOT / 'tests/spectral-qa/build'
    classes = directory / 'probe-classes'
    classes.mkdir(parents=True, exist_ok=True)
    source = ROOT / 'tests/spectral-qa/probe/SpectralRuntimeProbe.java'
    controller = source.with_name('SpectralCommandController.java')
    hurt = source.with_name('SpectralHurtObserver.java')
    subprocess.run([env['JAVA_HOME']+'/bin/javac', '--release', '8', '-Xlint:all',
                    '-Xlint:-options', '-Werror', '-d', str(classes), str(source), str(controller), str(hurt)], check=True)
    manifest = directory / 'probe-manifest.mf'
    manifest.write_text('Manifest-Version: 1.0\nPremain-Class: com.stonytark.usefultoolsmod.spectralqa.SpectralRuntimeProbe\n\n')
    artifact = directory / 'runtime-probe.jar'
    subprocess.run([env['JAVA_HOME']+'/bin/jar', 'cfm', str(artifact), str(manifest),
                    '-C', str(classes), '.'], check=True)
    (directory / 'probe-provenance.json').write_text(json.dumps({
        'source_sha256': hashlib.sha256(source.read_bytes()).hexdigest(),
        'controller_source_sha256': hashlib.sha256(controller.read_bytes()).hexdigest(),
        'hurt_observer_source_sha256': hashlib.sha256(hurt.read_bytes()).hexdigest(),
        'artifact_sha256': hashlib.sha256(artifact.read_bytes()).hexdigest(),
        'java_release': 8}, indent=2)+'\n')
else:
    target, script, task = ('1.7.10', 'classic.gradle', 'reobfSpectralQaJar') if args.helper == 'classic' else ('1.21.1/fabric', 'qa.gradle', 'remapSpectralQaJar')
    command = ['./gradlew', '--no-daemon', '--max-workers=1', '-I',
               str(ROOT/'tests/spectral-qa'/script)]
    if args.helper == 'fabric1211':
        command += ['-I', str(ROOT/'scripts/strict-warnings.gradle')]
    subprocess.run(command+[task], cwd=ROOT/target, env=env, check=True)
