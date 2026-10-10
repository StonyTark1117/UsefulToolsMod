#!/usr/bin/env python3
"""Freeze the verified pinned NeoForge title-action donor as a private candidate."""
import hashlib
import json
from pathlib import Path
import zipfile

WORKSPACE = Path('/home/braydon/Useful-Tools')
CACHE = Path('/home/braydon/.cache/useful-tools-spectral-recovery')
IMPLEMENTATION = WORKSPACE/'validation/shader-support-2.3.2/recovery/implementation'
PREVIOUS_SHA = '6b9847049a08339a7bdc856e58d41c0cf97fc704dca31f662c6917ea6c35737b'
previous = IMPLEMENTATION/'candidate-dependencies'/PREVIOUS_SHA/'native-gl-bbox-loader.jar'
class_file = CACHE/'native-title-actions-build/output/net/minecraft/client/gui/screens/TitleScreen.class'
source = WORKSPACE/'tests/spectral-qa/probe/OptifineNativeTitleActions.java'
HISTORICAL_SOURCE_SHA = '52c4457122c2408d04d468c21116a376228c2d27e437ed0740796a22c9d915ba'
native = Path('/home/braydon/.local/share/PrismLauncher/libraries/net/neoforged/neoforge/20.2.93/neoforge-20.2.93-client.jar')
publisher = CACHE/'neo1202-payload/optifine-patched.jar'
entry = 'optifineoforge/donors/net/minecraft/client/gui/screens/TitleScreen.class'

def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()

if sha(previous) != PREVIOUS_SHA:
    raise RuntimeError('Prior private candidate hash changed')
if sha(source) != HISTORICAL_SOURCE_SHA:
    raise RuntimeError('Historical five-action checker changed; use freeze_native_mapped_callbacks.py for the corrected SRG-aware candidate')
for path in (class_file, source, native, publisher):
    if not path.is_file():
        raise FileNotFoundError(path)
staged = CACHE/'native-title-actions-build/native-title-actions-loader.jar'
with zipfile.ZipFile(previous) as original, zipfile.ZipFile(staged, 'w') as output:
    seen = set()
    for item in original.infolist():
        if item.filename in seen:
            raise RuntimeError('Duplicate candidate entry: '+item.filename)
        seen.add(item.filename)
        output.writestr(item, class_file.read_bytes() if item.filename == entry else original.read(item))
    if entry not in seen:
        raise RuntimeError('Expected title donor entry is missing')
digest = sha(staged)
durable = IMPLEMENTATION/'candidate-dependencies'/digest/'native-title-actions-loader.jar'
durable.parent.mkdir(parents=True, exist_ok=True)
durable.write_bytes(staged.read_bytes())
if sha(durable) != digest:
    raise RuntimeError('Durable candidate hash mismatch')
record = {
    'candidate': str(durable), 'candidate_sha256': digest,
    'prior_candidate': str(previous), 'prior_candidate_sha256': PREVIOUS_SHA,
    'replaced_entry': entry, 'replaced_class_sha256': sha(class_file),
    'checker_source': str(source), 'checker_source_sha256': sha(source),
    'native_input': str(native), 'native_sha256': sha(native),
    'publisher_input': str(publisher), 'publisher_sha256': sha(publisher),
    'restored_exact_native_title_actions': 5,
    'unresolved_action': 'Historical raw-SRG comparison could not resolve confirmDemo and LOGGER; later officialToSrg mapping resolved both aliases',
    'qualification': 'private bytecode candidate only; requires fresh packaged runtime and full behavioral validation'
}
out = IMPLEMENTATION/'research/neoforge1202-native-title-actions-candidate.json'
out.write_text(json.dumps(record, indent=2)+'\n')
print(json.dumps(record, indent=2))
