"""Freeze a private UI repair; preserve the publisher's real inherited click dispatch."""
from pathlib import Path
import hashlib
import json
import shutil
import subprocess
import zipfile

root = Path.home()/'.cache/useful-tools-spectral-recovery'
expected = '00eef5fbf0b4006cecca46a7e691fe2dbf1563007957408bc3a09244fd676425'
source = root/'qa'/expected/'OptifiNeoforge-2.0.0-mc1202-native-20293-candidate.jar'
assert hashlib.sha256(source.read_bytes()).hexdigest() == expected
build = root/'native-gui-inheritance-build'
build.mkdir(exist_ok=True)
with zipfile.ZipFile(source) as archive:
    entries = {name:archive.read(name) for name in archive.namelist() if not name.endswith('/')}
key = 'optifineoforge/donors/net/minecraft/client/gui/screens/VideoSettingsScreen.class'
(build/'donor.class').write_bytes(entries[key])
runtime_hash = '4d6abc90a4aaa4159dfeb7882b2adb684251c78f40193281c1e97b062477c1e4'
runtime = root/'qa'/runtime_hash/'optifine-native-game-layer-classpath.jar'
assert hashlib.sha256(runtime.read_bytes()).hexdigest() == runtime_hash
with zipfile.ZipFile(runtime) as archive:
    parent = archive.read('srg/net/optifine/gui/GuiScreenOF.class')
(build/'parent.class').write_bytes(parent)
asm = Path.home()/'.gradle/caches/modules-2/files-2.1/org.ow2.asm'
jars = [next((asm/name/'9.10.1').glob('*/'+name+'-9.10.1.jar')) for name in ('asm','asm-tree')]
cp = ':'.join(map(str,jars))
java = '/usr/lib/jvm/java-17-openjdk/bin/'
helper = Path('tests/spectral-qa/probe/OptifineNativeGuiInheritance.java')
commands = [[java+'javac','--release','17','-Xlint:all','-Werror','-cp',cp,'-d',str(build),str(helper)],
            [java+'java','-cp',str(build)+':'+cp,'com.stonytark.usefultoolsmod.spectralqa.OptifineNativeGuiInheritance',str(build/'donor.class'),str(build/'parent.class'),str(build/'fixed.class')]]
for command in commands:
    subprocess.run(command,check=True)
entries[key] = (build/'fixed.class').read_bytes()
plan = 'optifineoforge/member-restores.txt'
line = 'M net/minecraft/client/gui/screens/VideoSettingsScreen mouseClicked (DDI)Z'
lines = entries[plan].decode().splitlines()
assert lines.count(line) == 1
entries[plan] = ('\n'.join(item for item in lines if item != line)+'\n').encode()
target = build/'native-inherited-click-loader.jar'
with zipfile.ZipFile(target,'w',zipfile.ZIP_DEFLATED) as archive:
    for name,data in sorted(entries.items()):
        info = zipfile.ZipInfo(name,(1980,1,1,0,0,0));info.compress_type=zipfile.ZIP_DEFLATED
        archive.writestr(info,data)
sha = hashlib.sha256(target.read_bytes()).hexdigest()
destination = root/'qa'/sha
destination.mkdir(exist_ok=True)
shutil.copy2(target,destination/target.name)
shutil.copy2(helper,destination/helper.name)
record = {'input':str(source),'input_sha256':expected,'output':str(destination/target.name),'sha256':sha,
          'changed_entries':[key,plan],'commands':commands,'runtime_qualified':False,
          'source_sha256':hashlib.sha256(helper.read_bytes()).hexdigest(),
          'actual_parent_sha256':hashlib.sha256(parent).hexdigest(),
          'runtime_input':str(runtime),'runtime_input_sha256':runtime_hash,
          'behavior':'Remove donor default-return override so the inspected real publisher GuiScreenOF click/action dispatch is inherited. No simulated click or screen replacement.'}
Path(__file__).with_name('native-gui-inheritance-candidate.json').write_text(json.dumps(record,indent=2)+'\n')
print(json.dumps(record,indent=2))
