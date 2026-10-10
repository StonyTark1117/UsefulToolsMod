#!/usr/bin/env python3
"""Freeze a private video-menu candidate without five placeholder overrides."""
import hashlib
import json
from pathlib import Path
import zipfile

WORKSPACE = Path('/home/braydon/Useful-Tools')
CACHE = Path('/home/braydon/.cache/useful-tools-spectral-recovery')
IMPLEMENTATION = WORKSPACE/'validation/shader-support-2.3.2/recovery/implementation'
BASE_SHA = '8a030fec935f05b7846a2f0ef3392a42de85a65704f79eade64b01a9ff00bbce'
base = IMPLEMENTATION/'candidate-dependencies'/BASE_SHA/'native-mapped-callbacks-loader.jar'
owner = 'net/minecraft/client/gui/screens/VideoSettingsScreen'
donor_entry = 'optifineoforge/donors/'+owner+'.class'
plan_entry = 'optifineoforge/member-restores.txt'
class_file = CACHE/'native-video-inheritance-build/output'/(owner+'.class')
source = WORKSPACE/'tests/spectral-qa/probe/OptifineVideoDefaultPrune.java'
publisher = CACHE/'neo1202-payload/optifine-patched.jar'
runtime = CACHE/'qa/4d6abc90a4aaa4159dfeb7882b2adb684251c78f40193281c1e97b062477c1e4/optifine-native-game-layer-classpath.jar'
native = Path('/home/braydon/.local/share/PrismLauncher/libraries/net/neoforged/neoforge/20.2.93/neoforge-20.2.93-client.jar')
methods = (
    'mouseScrolled (DDDD)Z',
    'renderBackground (Lnet/minecraft/client/gui/GuiGraphics;IIF)V',
    'lambda$mouseClicked$4 (Lnet/minecraft/client/gui/components/Button;)V',
    'lambda$mouseClicked$3 (Lnet/minecraft/client/gui/components/Button;)V',
    'lambda$init$2 (Lcom/mojang/blaze3d/platform/Window;Lnet/minecraft/client/gui/components/Button;)V',
)

def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()

if sha(base) != BASE_SHA:
    raise RuntimeError('Base private candidate hash changed')
for path in (class_file, source, publisher, runtime, native):
    if not path.is_file():
        raise FileNotFoundError(path)
with zipfile.ZipFile(base) as original:
    lines = original.read(plan_entry).decode().splitlines()
    removed = {'M '+owner+' '+method for method in methods}
    if any(lines.count(line) != 1 for line in removed):
        raise RuntimeError('Expected exact five member-plan rows are missing or duplicated')
    plan = ('\n'.join(line for line in lines if line not in removed)+'\n').encode()
    staged = CACHE/'native-video-inheritance-build/native-video-defaults-loader.jar'
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
durable = IMPLEMENTATION/'candidate-dependencies'/digest/'native-video-defaults-loader.jar'
durable.parent.mkdir(parents=True, exist_ok=True)
durable.write_bytes(staged.read_bytes())
if sha(durable) != digest:
    raise RuntimeError('Durable candidate hash mismatch')
record = {
    'candidate': str(durable), 'candidate_sha256': digest,
    'base_candidate': str(base), 'base_candidate_sha256': BASE_SHA,
    'changed_entries': [donor_entry, plan_entry],
    'removed_plan_rows': sorted(removed),
    'checker_source': str(source), 'checker_source_sha256': sha(source),
    'publisher_sha256': sha(publisher), 'runtime_sha256': sha(runtime),
    'native_sha256': sha(native),
    'qualification': 'private bytecode candidate only; inherited scrolling/background and menu controls require fresh packaged runtime validation'
}
out = IMPLEMENTATION/'research/neoforge1202-native-video-defaults-candidate.json'
out.write_text(json.dumps(record, indent=2)+'\n')
print(json.dumps(record, indent=2))
