#!/usr/bin/env python3
"""Freeze a private candidate without the unreferenced HTTP callback stub."""
import hashlib
import json
from pathlib import Path
import subprocess
import tempfile
import zipfile

WORKSPACE = Path('/home/braydon/Useful-Tools')
CACHE = Path('/home/braydon/.cache/useful-tools-spectral-recovery')
IMPLEMENTATION = WORKSPACE/'validation/shader-support-2.3.2/recovery/implementation'
BASE_SHA = 'd4ed26c44f6ff02c6fdeac7e87425e41354a71966ed81f3b11148f00d7662f4a'
base = IMPLEMENTATION/'candidate-dependencies'/BASE_SHA/'native-video-defaults-loader.jar'
owner = 'net/minecraft/client/renderer/texture/HttpTexture'
donor_entry = 'optifineoforge/donors/'+owner+'.class'
plan_entry = 'optifineoforge/member-restores.txt'
removed = 'M '+owner+' lambda$load$2 (Lnet/minecraft/server/packs/resources/ResourceManager;)V'
source = WORKSPACE/'tests/spectral-qa/probe/OptifineHttpDefaultPrune.java'
publisher_sha = '0b9fea83d7d66e0c1cf8d2445a4c6c4db74563389045512f8ed466743820d854'
publisher = IMPLEMENTATION/'candidate-dependencies'/publisher_sha/'optifine-patched.jar'
native = Path('/home/braydon/.local/share/PrismLauncher/libraries/net/neoforged/neoforge/20.2.93/neoforge-20.2.93-client.jar')
asm = (
    Path('/home/braydon/.gradle/caches/modules-2/files-2.1/org.ow2.asm/asm/9.10.1/ada2141c0cc52ee8f5c48cd5fa4ce0e794f22236/asm-9.10.1.jar'),
    Path('/home/braydon/.gradle/caches/modules-2/files-2.1/org.ow2.asm/asm-tree/9.10.1/e244332a17564c1d1572449399a842de35881be2/asm-tree-9.10.1.jar'),
    Path('/home/braydon/.gradle/caches/modules-2/files-2.1/org.ow2.asm/asm-analysis/9.10.1/8d49f14d51f632cb1d87c88d1ceaf50db0d8af1b/asm-analysis-9.10.1.jar'),
)

def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()

if sha(base) != BASE_SHA:
    raise RuntimeError('Base private candidate hash changed')
for path in (source, publisher, native, *asm):
    if not path.is_file():
        raise FileNotFoundError(path)
if sha(publisher) != publisher_sha:
    raise RuntimeError('Publisher input hash changed')
with tempfile.TemporaryDirectory(prefix='native-http-defaults-') as temp:
    temp = Path(temp)
    donor_class = temp/'donor'/(owner+'.class')
    publisher_class = temp/'publisher'/(owner+'.class')
    class_file = temp/'output'/(owner+'.class')
    for path in (donor_class, publisher_class):
        path.parent.mkdir(parents=True)
    with zipfile.ZipFile(base) as original:
        donor_class.write_bytes(original.read(donor_entry))
    with zipfile.ZipFile(publisher) as published:
        publisher_class.write_bytes(published.read('srg/'+owner+'.class'))
    classpath = ':'.join(str(path) for path in asm)
    subprocess.run(['/usr/lib/jvm/java-17-openjdk/bin/javac', '--release', '17', '-Xlint:all', '-Werror',
                    '-cp', classpath, '-d', str(temp), str(source)], check=True)
    subprocess.run(['/usr/lib/jvm/java-17-openjdk/bin/java', '-cp', str(temp)+':'+classpath,
                    'com.stonytark.usefultoolsmod.spectralqa.OptifineHttpDefaultPrune',
                    str(donor_class), str(publisher_class), str(class_file)], check=True)
    with zipfile.ZipFile(base) as original:
        lines = original.read(plan_entry).decode().splitlines()
        if lines.count(removed) != 1:
            raise RuntimeError('Expected exact member-plan row is missing or duplicated')
        plan = ('\n'.join(line for line in lines if line != removed)+'\n').encode()
        staged = CACHE/'native-http-texture-build/native-http-defaults-loader.jar'
        with zipfile.ZipFile(staged, 'w') as output:
            seen = set()
            for item in original.infolist():
                if item.filename in seen:
                    raise RuntimeError('Duplicate candidate entry: '+item.filename)
                seen.add(item.filename)
                data = class_file.read_bytes() if item.filename == donor_entry else plan if item.filename == plan_entry else original.read(item)
                output.writestr(item, data)
            if not {donor_entry, plan_entry} <= seen:
                raise RuntimeError('Expected donor/plan entries are missing')
digest = sha(staged)
durable = IMPLEMENTATION/'candidate-dependencies'/digest/'native-http-defaults-loader.jar'
durable.parent.mkdir(parents=True, exist_ok=True)
durable.write_bytes(staged.read_bytes())
if sha(durable) != digest:
    raise RuntimeError('Durable candidate hash mismatch')
record = {
    'candidate': str(durable), 'candidate_sha256': digest,
    'base_candidate': str(base), 'base_candidate_sha256': BASE_SHA,
    'changed_entries': [donor_entry, plan_entry],
    'removed_plan_row': removed,
    'checker_source': str(source), 'checker_source_sha256': sha(source),
    'publisher_sha256': sha(publisher), 'native_sha256': sha(native),
    'qualification': 'private bytecode candidate only; fresh packaged runtime validation is required'
}
out = IMPLEMENTATION/'research/neoforge1202-native-http-default-candidate.json'
out.write_text(json.dumps(record, indent=2)+'\n')
print(json.dumps(record, indent=2))
