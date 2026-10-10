#!/usr/bin/env python3
"""Compile and package optional companions using each base target's resolved toolchain."""
from pathlib import Path
import subprocess,re,os,json,hashlib,argparse,time
ROOT=Path(__file__).resolve().parents[1]
p=argparse.ArgumentParser();p.add_argument('targets',nargs='*');p.add_argument('--full',action='store_true');args=p.parse_args()
output=ROOT/'validation/dynamic-lighting-2.3.2/build';output.mkdir(parents=True,exist_ok=True)
results=json.loads((output/'results.json').read_text()) if (output/'results.json').exists() else []
for target,directory,jvm in re.findall(r'run_build "([^"]+)"\s+"([^"]+)"\s+"([^"]+)"',(ROOT/'scripts/build-matrix.sh').read_text()):
 if args.targets and target not in args.targets:continue
 if not Path(jvm).is_dir() and target.startswith('26.') and 'fabric' in target:jvm='/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2'
 command=['./gradlew','--no-daemon','--max-workers=1','--no-build-cache']
 command+=['clean','build'] if args.full else ['compileLightingJava','reobfLightingJar' if target in {'1.7.10-forge','1.20.1-neoforge'} else ('remapLightingJar' if 'fabric' in target and not target.startswith('26.') else 'lightingJar')]
 if target!='1.7.10-forge':command+=['-I',str(ROOT/'scripts/strict-warnings.gradle')]
 start=time.monotonic();print(target,flush=True)
 logpath=output/(target+'.log')
 if logpath.exists():logpath.rename(output/(target+'-previous-'+str(time.time_ns())+'.log'))
 with logpath.open('w') as log:
  result=subprocess.run(command,cwd=ROOT/directory,env={**os.environ,'JAVA_HOME':jvm},stdout=log,stderr=subprocess.STDOUT)
 artifacts={str(f.relative_to(ROOT)):hashlib.sha256(f.read_bytes()).hexdigest() for f in (ROOT/directory/'build/libs').glob('*.jar') if 'lighting' in f.name or args.full}
 results=[r for r in results if r['target']!=target]
 results.append({'target':target,'command':command,'exit_code':result.returncode,'java_home':jvm,'seconds':time.monotonic()-start,'artifacts':artifacts,'revision':subprocess.check_output(['git','rev-parse','HEAD'],cwd=ROOT,text=True).strip()})
 (output/'results.json').write_text(json.dumps(results,indent=2)+'\n')
 if result.returncode:print((output/(target+'.log')).read_text()[-4000:],flush=True);raise SystemExit(result.returncode)
