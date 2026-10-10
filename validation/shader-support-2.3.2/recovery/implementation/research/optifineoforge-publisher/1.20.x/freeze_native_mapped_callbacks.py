#!/usr/bin/env python3
"""Freeze exact pinned NeoForge donor bodies after SRG member-reference checks."""
import hashlib
import json
from pathlib import Path
import zipfile

WORKSPACE = Path('/home/braydon/Useful-Tools')
CACHE = Path('/home/braydon/.cache/useful-tools-spectral-recovery')
IMPLEMENTATION = WORKSPACE/'validation/shader-support-2.3.2/recovery/implementation'
BASE_SHA = '6b9847049a08339a7bdc856e58d41c0cf97fc704dca31f662c6917ea6c35737b'
base = IMPLEMENTATION/'candidate-dependencies'/BASE_SHA/'native-gl-bbox-loader.jar'
native = Path('/home/braydon/.local/share/PrismLauncher/libraries/net/neoforged/neoforge/20.2.93/neoforge-20.2.93-client.jar')
publisher = CACHE/'neo1202-payload/optifine-patched.jar'
mappings = Path('/home/braydon/.gradle/caches/neoformruntime/intermediate_results/createMappings_9fe271ac4d3dc80a91f4395462ead5ddacfd6398_officialToSrg.tsrg')
classes = {
    'net/minecraft/client/gui/screens/TitleScreen': CACHE/'native-title-actions-mapped-build/output',
    'net/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection$RebuildTask': CACHE/'native-chunk-callbacks-build/output',
    'net/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection$ResortTransparencyTask': CACHE/'native-chunk-callbacks-build/output',
    'net/minecraft/client/server/IntegratedServer': CACHE/'native-server-halt-mapped-build/output',
}
sources = [WORKSPACE/'tests/spectral-qa/probe'/name for name in (
    'OptifineNativeTitleActions.java', 'OptifineNativeChunkCallbacks.java',
    'OptifineNativeServerHalt.java')]

def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()

if sha(base) != BASE_SHA:
    raise RuntimeError('Base private candidate hash changed')
for path in (native, publisher, mappings, *sources):
    if not path.is_file():
        raise FileNotFoundError(path)
replacements = {}
for owner, directory in classes.items():
    class_file = directory/(owner+'.class')
    if not class_file.is_file():
        raise FileNotFoundError(class_file)
    replacements['optifineoforge/donors/'+owner+'.class'] = class_file.read_bytes()
staged = CACHE/'native-server-halt-mapped-build/native-mapped-callbacks-loader.jar'
with zipfile.ZipFile(base) as original, zipfile.ZipFile(staged, 'w') as output:
    seen = set()
    for item in original.infolist():
        if item.filename in seen:
            raise RuntimeError('Duplicate candidate entry: '+item.filename)
        seen.add(item.filename)
        output.writestr(item, replacements.get(item.filename, original.read(item)))
    if not replacements.keys() <= seen:
        raise RuntimeError('Expected donor entries are missing')
digest = sha(staged)
durable = IMPLEMENTATION/'candidate-dependencies'/digest/'native-mapped-callbacks-loader.jar'
durable.parent.mkdir(parents=True, exist_ok=True)
durable.write_bytes(staged.read_bytes())
if sha(durable) != digest:
    raise RuntimeError('Durable candidate hash mismatch')
record = {
    'candidate': str(durable), 'candidate_sha256': digest,
    'base_candidate': str(base), 'base_candidate_sha256': BASE_SHA,
    'replaced_entries': sorted(replacements),
    'replaced_class_sha256': {entry: hashlib.sha256(data).hexdigest() for entry,data in replacements.items()},
    'checker_sources_sha256': {str(path): sha(path) for path in sources},
    'native_input': str(native), 'native_sha256': sha(native),
    'publisher_input': str(publisher), 'publisher_sha256': sha(publisher),
    'official_to_srg_mappings': str(mappings), 'mappings_sha256': sha(mappings),
    'restored_exact_native_bodies': {'title_actions': 6, 'chunk_callbacks': 2, 'integrated_server_halt': 1},
    'qualification': 'private bytecode candidate only; fresh packaged runtime, pack and full behavior validation required'
}
out = IMPLEMENTATION/'research/neoforge1202-native-mapped-callbacks-candidate.json'
out.write_text(json.dumps(record, indent=2)+'\n')
print(json.dumps(record, indent=2))
