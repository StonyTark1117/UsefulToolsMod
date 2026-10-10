"""Build isolated native Options-hook donors; does not launch or certify clients."""
from pathlib import Path
import hashlib
import json
import subprocess
import zipfile

research = Path(__file__).resolve().parent.parent
durable = Path('/home/braydon/.cache/useful-tools-spectral-recovery')
work = durable / 'native-options-hook-build'
work.mkdir(parents=True, exist_ok=True)
java = Path('/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2/bin')
libraries = [next((Path('/home/braydon/.gradle/caches/modules-2/files-2.1/org.ow2.asm')
                  / name / '9.10.1').glob('*/' + name + '-9.10.1.jar'))
             for name in ('asm', 'asm-tree')]
classpath = ':'.join(map(str, libraries))
source = Path('tests/spectral-qa/probe/OptifineNativeOptionsHooks.java')
subprocess.run([str(java / 'javac'), '--release', '17', '-proc:none', '-Xlint:all',
                '-Werror', '-cp', classpath, '-d', str(work), str(source)], check=True)
rows = []
for donor in json.loads((research / 'durable-optifine26-donors.json').read_text()):
    version, forge = donor['version'], donor['forge_pin']
    native = (Path('/home/braydon/.local/share/PrismLauncher/libraries/net/minecraftforge/forge')
              / (version + '-' + forge) / ('forge-' + version + '-' + forge + '-client.jar'))
    stage = work / version
    stage.mkdir(exist_ok=True)
    if hashlib.sha256(Path(donor['donor']).read_bytes()).hexdigest() != donor['sha256']:
        raise ValueError('Donor hash mismatch')
    with zipfile.ZipFile(donor['donor']) as archive:
        (stage / 'publisher.class').write_bytes(archive.read('srg/net/minecraft/client/Options.class'))
    with zipfile.ZipFile(native) as archive:
        (stage / 'native.class').write_bytes(archive.read('net/minecraft/client/Options.class'))
    command = [str(java / 'java'), '-cp', str(work) + ':' + classpath,
               'com.stonytark.usefultoolsmod.spectralqa.OptifineNativeOptionsHooks',
               str(stage / 'publisher.class'), str(stage / 'native.class'), str(stage / 'restored.class')]
    result = subprocess.run(command, capture_output=True, text=True)
    row = dict(minecraft=version, forge=forge, command=command, exit_code=result.returncode,
               stderr=result.stderr, input_donor_sha256=donor['sha256'],
               native_sha256=hashlib.sha256(native.read_bytes()).hexdigest())
    if result.returncode == 0:
        artifact = stage / 'options-hooks-donor.jar'
        with zipfile.ZipFile(donor['donor']) as original, zipfile.ZipFile(artifact, 'w') as output:
            for entry in original.infolist():
                body = ((stage / 'restored.class').read_bytes()
                        if entry.filename == 'srg/net/minecraft/client/Options.class'
                        else original.read(entry.filename))
                output.writestr(entry, body)
        digest = hashlib.sha256(artifact.read_bytes()).hexdigest()
        frozen = durable / 'qa' / digest / artifact.name
        frozen.parent.mkdir(parents=True, exist_ok=True)
        frozen.write_bytes(artifact.read_bytes())
        (frozen.parent / source.name).write_bytes(source.read_bytes())
        row.update(donor=str(frozen), sha256=digest)
    rows.append(row)
record = dict(source_sha256=hashlib.sha256(source.read_bytes()).hexdigest(), rows=rows,
              runtime_qualified=False,
              native_methods_restored=['broadcastOptions', 'processOptionsKeysOnly'],
              preserved='Publisher constructor, shader settings and all other methods retained; '
                        'native key loop operates on the same combined keyMappings array.')
(research / 'optifine-native-options-hook-trial-v3.json').write_text(json.dumps(record, indent=2) + '\n')
for row in rows:
    print(row['minecraft'], row['exit_code'], row.get('sha256', row['stderr']))
raise SystemExit(int(any(row['exit_code'] for row in rows)))
