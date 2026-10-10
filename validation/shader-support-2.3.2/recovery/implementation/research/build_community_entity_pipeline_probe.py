#!/usr/bin/env python3
"""Freeze an entity-gbuffer marker from the community port's tested BSL pack."""
import hashlib
import json
from pathlib import Path
import zipfile

ROOT = Path('/home/braydon/Useful-Tools')
IMPLEMENTATION = ROOT/'validation/shader-support-2.3.2/recovery/implementation'
SOURCE_SHA = '131ca6b56b100e60577ffdb0dd51bfab2ee6e334564eeee324e5422026891668'
SOURCE = Path('/home/braydon/.cache/useful-tools-spectral-recovery/restored-inputs')/SOURCE_SHA/'BSL_v10.1.1.zip'
ENTRY = 'shaders/program/gbuffers_entities.glsl'
OLD = b'gl_FragData[0] = albedo;'
NEW = b'gl_FragData[0] = vec4(1.0, 0.0, 0.0, albedo.a);'

def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()

if digest(SOURCE) != SOURCE_SHA:
    raise RuntimeError('Community BSL source hash changed')
staged = Path('/home/braydon/.cache/useful-tools-spectral-recovery/community-entity-pipeline-probe.zip')
with zipfile.ZipFile(SOURCE) as original, zipfile.ZipFile(staged, 'w') as output:
    seen = set()
    for entry in original.infolist():
        if entry.filename in seen:
            raise RuntimeError('Duplicate source entry: '+entry.filename)
        seen.add(entry.filename)
        data = original.read(entry)
        if entry.filename == ENTRY:
            if data.count(OLD) != 1:
                raise RuntimeError('Expected exact entity color assignment missing or duplicated')
            data = data.replace(OLD, NEW)
        output.writestr(entry, data)
    if ENTRY not in seen:
        raise RuntimeError('Entity shader entry missing')
with zipfile.ZipFile(staged) as archive:
    if archive.testzip() is not None:
        raise RuntimeError('Probe archive failed integrity check')
probe_sha = digest(staged)
durable = IMPLEMENTATION/'candidate-dependencies'/probe_sha/'BSLprobe.zip'
durable.parent.mkdir(parents=True, exist_ok=True)
durable.write_bytes(staged.read_bytes())
if digest(durable) != probe_sha:
    raise RuntimeError('Durable probe hash mismatch')
record = {
    'source': str(SOURCE), 'source_sha256': SOURCE_SHA,
    'probe_pack': str(durable), 'probe_pack_sha256': probe_sha,
    'modified_entry': ENTRY,
    'changed_assignment': OLD.decode()+' -> '+NEW.decode(),
    'purpose': 'Private diagnostic only: test whether the pinned Forge community port routes entity pixels through gbuffers_entities; compare with the original BSL 10.1.1 scene.',
    'qualification': 'The primary pack and complete acceptance scenarios remain separate requirements.'
}
out = IMPLEMENTATION/'research/community-entity-pipeline-probe-pack.json'
out.write_text(json.dumps(record, indent=2)+'\n')
print(json.dumps(record, indent=2))
