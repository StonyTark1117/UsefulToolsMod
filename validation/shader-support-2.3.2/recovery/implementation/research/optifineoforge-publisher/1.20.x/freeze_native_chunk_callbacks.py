#!/usr/bin/env python3
"""Freeze verified pinned NeoForge chunk callbacks in the private OptiFine donor."""
import hashlib
import json
from pathlib import Path
import zipfile

WORKSPACE = Path('/home/braydon/Useful-Tools')
CACHE = Path('/home/braydon/.cache/useful-tools-spectral-recovery')
IMPLEMENTATION = WORKSPACE/'validation/shader-support-2.3.2/recovery/implementation'
PREVIOUS_SHA = '5c137c17604fb68951c5d7838037a3d25b23185c9480a082b88eaf70be758cfe'
previous = IMPLEMENTATION/'candidate-dependencies'/PREVIOUS_SHA/'native-title-actions-loader.jar'
output_classes = CACHE/'native-chunk-callbacks-build/output'
source = WORKSPACE/'tests/spectral-qa/probe/OptifineNativeChunkCallbacks.java'
native = Path('/home/braydon/.local/share/PrismLauncher/libraries/net/neoforged/neoforge/20.2.93/neoforge-20.2.93-client.jar')
publisher = CACHE/'neo1202-payload/optifine-patched.jar'
owners = [
    'net/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection$RebuildTask',
    'net/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection$ResortTransparencyTask',
]

def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()

if sha(previous) != PREVIOUS_SHA:
    raise RuntimeError('Prior private candidate hash changed')
replacements = {}
for owner in owners:
    class_file = output_classes/(owner+'.class')
    if not class_file.is_file():
        raise FileNotFoundError(class_file)
    replacements['optifineoforge/donors/'+owner+'.class'] = class_file.read_bytes()
for path in (source, native, publisher):
    if not path.is_file():
        raise FileNotFoundError(path)
staged = CACHE/'native-chunk-callbacks-build/native-chunk-callbacks-loader.jar'
with zipfile.ZipFile(previous) as original, zipfile.ZipFile(staged, 'w') as output:
    seen = set()
    for item in original.infolist():
        if item.filename in seen:
            raise RuntimeError('Duplicate candidate entry: '+item.filename)
        seen.add(item.filename)
        output.writestr(item, replacements.get(item.filename, original.read(item)))
    if not replacements.keys() <= seen:
        raise RuntimeError('Expected chunk donor entries are missing')
digest = sha(staged)
durable = IMPLEMENTATION/'candidate-dependencies'/digest/'native-chunk-callbacks-loader.jar'
durable.parent.mkdir(parents=True, exist_ok=True)
durable.write_bytes(staged.read_bytes())
if sha(durable) != digest:
    raise RuntimeError('Durable candidate hash mismatch')
record = {
    'candidate': str(durable), 'candidate_sha256': digest,
    'prior_candidate': str(previous), 'prior_candidate_sha256': PREVIOUS_SHA,
    'replaced_entries': sorted(replacements),
    'replaced_class_sha256': {entry: hashlib.sha256(data).hexdigest() for entry,data in replacements.items()},
    'checker_source': str(source), 'checker_source_sha256': sha(source),
    'native_input': str(native), 'native_sha256': sha(native),
    'publisher_input': str(publisher), 'publisher_sha256': sha(publisher),
    'restored_exact_native_chunk_callbacks': 2,
    'qualification': 'private bytecode candidate only; fresh packaged world/render validation required'
}
out = IMPLEMENTATION/'research/neoforge1202-native-chunk-callbacks-candidate.json'
out.write_text(json.dumps(record, indent=2)+'\n')
print(json.dumps(record, indent=2))
