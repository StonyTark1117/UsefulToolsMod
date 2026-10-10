#!/usr/bin/env python3
"""Freeze the two verified pinned-NeoForge block-entity bounds into a private donor."""
import hashlib
import json
from pathlib import Path
import zipfile

D = Path('/home/braydon/.cache/useful-tools-spectral-recovery')
ROOT = Path(__file__).parents[3]
old = D/'qa/1dc419d8704c99665380fd89dfe5ac59c1d4b465d2af976c68eaafb08da1b6a4/native-real-gl-backup-loader.jar'
patch = D/'native-bbox-classes/output'
tmp = D/'native-bbox-classes/native-gl-bbox-loader.jar'
owners = ('net/minecraft/client/renderer/blockentity/SignRenderer',
          'net/minecraft/client/renderer/blockentity/SkullBlockRenderer')
replacements = {'optifineoforge/donors/'+owner+'.class':(patch/(owner+'.class')).read_bytes()
                for owner in owners}
with zipfile.ZipFile(old) as src, zipfile.ZipFile(tmp, 'w') as dst:
    seen = set()
    for info in src.infolist():
        name = info.filename
        if name in seen:
            raise RuntimeError('Duplicate jar entry: '+name)
        seen.add(name)
        dst.writestr(info, replacements.get(name, src.read(info)))
    if not replacements.keys() <= seen:
        raise RuntimeError('Native donor entries missing')
sha = hashlib.sha256(tmp.read_bytes()).hexdigest()
output = D/'qa'/sha/'native-gl-bbox-loader.jar'
output.parent.mkdir(parents=True, exist_ok=True)
output.write_bytes(tmp.read_bytes())
assert hashlib.sha256(output.read_bytes()).hexdigest() == sha
durable = ROOT/'candidate-dependencies'/sha/output.name
durable.parent.mkdir(parents=True, exist_ok=True)
durable.write_bytes(output.read_bytes())
assert hashlib.sha256(durable.read_bytes()).hexdigest() == sha
record = {'input':str(old),'input_sha256':hashlib.sha256(old.read_bytes()).hexdigest(),
          'output':str(output),'durable_output':str(durable),'sha256':sha,
          'changed_entries':sorted(replacements),
          'source_sha256':hashlib.sha256((Path('/home/braydon/Useful-Tools')/'tests/spectral-qa/probe/OptifineNativeBoundingBoxes.java').read_bytes()).hexdigest(),
          'runtime_qualified':False,
          'note':'Two null-return culling bounds replaced with exact pinned native bodies; separate bytecode and publisher ABI verification is recorded in neoforge1202-native-bbox-body-check.json.'}
(ROOT/'research/neoforge1202-native-gl-bbox-candidate.json').write_text(json.dumps(record,indent=2)+'\n')
print(json.dumps(record,indent=2))
