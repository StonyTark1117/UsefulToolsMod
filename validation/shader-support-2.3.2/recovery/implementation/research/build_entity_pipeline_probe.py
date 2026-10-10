#!/usr/bin/env python3
"""Freeze a private BSL pack that visibly marks the entity gbuffer path."""
import hashlib
import json
from pathlib import Path
import zipfile

ROOT = Path('/home/braydon/Useful-Tools')
IMPLEMENTATION = ROOT/'validation/shader-support-2.3.2/recovery/implementation'
SOURCE_SHA = '934a1f8fbec9ccec408df783e74ab4d2fdc09f3d4d797de245a07ca92376dde7'
SOURCE = Path('/home/braydon/.cache/useful-tools-spectral-recovery/packs')/SOURCE_SHA/'BSL_v10.1.2.zip'
ENTRY = 'shaders/program/gbuffers_entities.glsl'
OLD = (b'gl_FragData[0] = albedo;', b'gl_FragData[1] = vec4(vlAlbedo, 1.0);')
NEW = (b'gl_FragData[0] = vec4(1.0, 0.0, 0.0, albedo.a);',
       b'gl_FragData[1] = vec4(1.0, 0.0, 0.0, 1.0);')

def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()

if digest(SOURCE) != SOURCE_SHA:
    raise RuntimeError('BSL source hash changed')
staged = Path('/home/braydon/.cache/useful-tools-spectral-recovery/entity-pipeline-probe.zip')
with zipfile.ZipFile(SOURCE) as original, zipfile.ZipFile(staged, 'w') as output:
    seen = set()
    for entry in original.infolist():
        if entry.filename in seen:
            raise RuntimeError('Duplicate source entry: '+entry.filename)
        seen.add(entry.filename)
        data = original.read(entry)
        if entry.filename == ENTRY:
            for old, new in zip(OLD, NEW):
                if data.count(old) != 1:
                    raise RuntimeError('Expected exact entity shader assignment missing or duplicated')
                data = data.replace(old, new)
        output.writestr(entry, data)
    if ENTRY not in seen:
        raise RuntimeError('Entity shader entry missing')
with zipfile.ZipFile(staged) as archive:
    if archive.testzip() is not None:
        raise RuntimeError('Probe pack archive failed integrity check')
probe_sha = digest(staged)
durable = IMPLEMENTATION/'candidate-dependencies'/probe_sha/'BSL_v10.1.2_entity-pipeline-probe.zip'
durable.parent.mkdir(parents=True, exist_ok=True)
durable.write_bytes(staged.read_bytes())
if digest(durable) != probe_sha:
    raise RuntimeError('Durable probe hash mismatch')
alias = durable.with_name('BSLprobe.zip')
alias.write_bytes(durable.read_bytes())
if digest(alias) != probe_sha:
    raise RuntimeError('Short-label probe alias hash mismatch')
record = {
    'source': str(SOURCE), 'source_sha256': SOURCE_SHA,
    'probe_pack': str(durable), 'probe_pack_sha256': probe_sha,
    'short_label_alias': str(alias),
    'modified_entry': ENTRY,
    'changed_assignments': [old.decode()+' -> '+new.decode() for old, new in zip(OLD, NEW)],
    'purpose': 'Private diagnostic only: an active entity gbuffer must turn entity pixels red while terrain stays unchanged. A full-screen-only port must not be treated as entity-pipeline coverage.',
    'qualification': 'Requires isolated packaged runs on exact Forge 65.1.2 with fresh controls and visual comparison; this pack is not a primary acceptance pack.'
}
out = IMPLEMENTATION/'research/entity-pipeline-probe-pack.json'
out.write_text(json.dumps(record, indent=2)+'\n')
print(json.dumps(record, indent=2))
