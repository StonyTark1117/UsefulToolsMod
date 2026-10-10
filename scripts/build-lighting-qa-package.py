#!/usr/bin/env python3
"""Build one native acceptance package under the shared client/build admission gate."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import re
import shutil
import signal
import subprocess
import sys
import time
import zipfile

from lighting_client_lifecycle import wait_client
from lighting_pins import pinned_loader
from spectral_validation import acquire_client_admission

ROOT = Path(__file__).resolve().parents[1]
OUT = ROOT / 'validation/dynamic-lighting-2.3.2'


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def cancelled(signum, frame):
    raise KeyboardInterrupt()


def main():
    builds = {target:(directory, java) for target,directory,java in re.findall(
        r'run_build "([^"]+)"\s+"([^"]+)"\s+"([^"]+)"',
        (ROOT / 'scripts/build-matrix.sh').read_text())}
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('target', choices=sorted(builds))
    parser.add_argument('--wait-seconds',type=int,choices=range(61),default=0,
                        help='Wait at most 60 seconds for one admitted build; no profile or JVM starts while waiting')
    args = parser.parse_args()
    try:
        admission = acquire_client_admission(Path.home()/'.cache/useful-tools-spectral-client.lock',args.wait_seconds)
    except BlockingIOError:
        print('Client/build admission occupied; no package build started.', file=sys.stderr)
        return 75
    with admission:
        os.setpriority(os.PRIO_PROCESS, 0, max(10, os.getpriority(os.PRIO_PROCESS, 0)))
        if shutil.which('ionice'):
            subprocess.run(['ionice','-c','3','-p',str(os.getpid())],check=True)
        signal.signal(signal.SIGTERM, cancelled)
        rows = json.loads((OUT/'artifact-audit.json').read_text())
        row = next(row for row in rows if row['target'] == args.target)
        release = ROOT/row['companion']
        if digest(release) != row['companion_sha256']:
            raise ValueError('Release artifact changed; audit the current native build first')
        with zipfile.ZipFile(release) as jar:
            production = {name:hashlib.sha256(jar.read(name)).hexdigest()
                          for name in jar.namelist() if name.endswith('.class')}
        directory, java = builds[args.target]
        if not Path(java).is_dir():
            if args.target.startswith('26.') and args.target.endswith('fabric'):
                java = '/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2'
            if not Path(java).is_dir(): raise FileNotFoundError(java)
        task = ('remapLightingJar' if args.target.endswith('fabric') and not args.target.startswith('26.')
                else 'reobfLightingJar' if args.target in {'1.7.10-forge','1.20.1-forge',
                    '1.20.2-forge','1.20.1-neoforge'} else 'lightingJar')
        command = ['./gradlew','--no-daemon','--max-workers=1','--no-build-cache',
                   '-I',str(ROOT/'tests/dynamic-lighting/qa.gradle'),
                   '-PusefulToolsLightingQaPackage=true']
        if args.target != '1.7.10-forge':
            command += ['-I',str(ROOT/'scripts/strict-warnings.gradle')]
        command += [task]
        name = args.target+'-qa-build-'+str(time.time_ns())
        log_path = OUT/(name+'.log')
        receipt_path = OUT/(name+'.json')
        source_roots = [ROOT/'tests/dynamic-lighting/targets'/args.target,
                        ROOT/'tests/dynamic-lighting/menu',ROOT/directory/'src/lighting',
                        ROOT/'lighting/common/java']
        sources = {str(path.relative_to(ROOT)):digest(path) for root in source_roots
                   for path in root.rglob('*') if path.is_file()}
        sources['tests/dynamic-lighting/qa.gradle'] = digest(ROOT/'tests/dynamic-lighting/qa.gradle')
        receipt = {'target':args.target,'command':command,'java_home':java,
                   'loader_pin':pinned_loader(args.target),'release_jar':str(release),
                   'release_sha256':row['companion_sha256'],'production_class_sha256':production,
                   'source_sha256':sources,'status':'running','log':str(log_path),
                   'resource_policy':{'nice':os.getpriority(os.PRIO_PROCESS,0),
                       'active_processors':2,'ordinary_client_or_build_slots':1}}
        receipt_path.write_text(json.dumps(receipt,indent=2)+'\n')
        environment = {**os.environ,'JAVA_HOME':java}
        environment['JAVA_TOOL_OPTIONS'] = (environment.get('JAVA_TOOL_OPTIONS','')
                                           +' -XX:ActiveProcessorCount=2').strip()
        with log_path.open('w') as log:
            client = subprocess.Popen(command,cwd=ROOT/directory,env=environment,
                                      stdout=log,stderr=subprocess.STDOUT,start_new_session=True)
            try:
                result = wait_client(client)
            except KeyboardInterrupt:
                receipt.update(status='cancelled',exit_code=130)
                receipt_path.write_text(json.dumps(receipt,indent=2)+'\n')
                return 130
        receipt.update(exit_code=result,status='build failed' if result else 'checking package')
        receipt_path.write_text(json.dumps(receipt,indent=2)+'\n')
        if result:
            print(log_path.read_text()[-4000:]);return result
        candidates = list((ROOT/directory/'build/libs').glob('usefultools-lighting-acceptance-only*.jar'))
        if len(candidates) != 1: raise ValueError('Expected one native acceptance package: '+str(candidates))
        artifact = candidates[0]
        with zipfile.ZipFile(artifact) as jar:
            mismatches = [name for name,expected in production.items()
                          if name not in jar.namelist() or hashlib.sha256(jar.read(name)).hexdigest()!=expected]
            if mismatches:raise ValueError('Acceptance package changed production bytecode: '+str(mismatches))
            if not any('LightingQaMenuSnapshot.class' in name for name in jar.namelist()):
                raise ValueError('Shared native menu fixture absent from package')
            if args.target == '1.7.10-forge':
                manifest = jar.read('META-INF/MANIFEST.MF').decode().replace('\r\n ', '').replace('\n ', '')
                if ('FMLCorePlugin: com.stonytark.usefultools.lighting.classic.ClassicLightingQaPlugin'
                        not in manifest):
                    raise ValueError('Classic package must bootstrap its silent audio hook through native mod-JAR discovery')
                if 'com/stonytark/usefultools/lighting/classic/ClassicLightingQaPlugin.class' not in jar.namelist():
                    raise ValueError('Native classic acceptance bootstrap absent from package')
        if digest(release) != row['companion_sha256']:
            raise ValueError('Acceptance packaging modified the distributable companion')
        sha = digest(artifact)
        destination = Path.home()/'.cache/useful-tools-lighting-inputs'/sha/(args.target+'-acceptance-only.jar')
        destination.parent.mkdir(parents=True,exist_ok=True,mode=0o700)
        if destination.exists() and digest(destination)!=sha:raise ValueError('Immutable package collision')
        if not destination.exists():shutil.copy2(artifact,destination)
        receipt.update(status='native acceptance package built and production classes matched',
                       qa_package=str(destination),qa_sha256=sha,
                       distributable_artifact_unchanged=True,production_class_mismatches=[])
        receipt_path.write_text(json.dumps(receipt,indent=2)+'\n')
        print(json.dumps({'qa_package':str(destination),'sha256':sha,'receipt':str(receipt_path)}))
        return 0


if __name__ == '__main__':
    raise SystemExit(main())
