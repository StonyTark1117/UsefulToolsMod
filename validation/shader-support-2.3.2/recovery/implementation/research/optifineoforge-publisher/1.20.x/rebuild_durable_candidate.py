"""Recreate the private native 20.2.93 candidate from actual publisher/native inputs."""
from pathlib import Path
import zipfile, hashlib, json, subprocess
root=Path.home()/'.cache/useful-tools-spectral-recovery'
research=Path(__file__).resolve().parent
payload=root/'neo1202-payload'
src=root/'publisher-source/OptifiNeoforge-cb230d0328b38bf0f58622f08cf248e93ffc021a'
loader=src/'build/libs/OptifiNeoforge-2.0.0+mc1.20.2.jar'
original=root/'preview_OptiFine_1.20.2_HD_U_I7_pre1.jar'
asmroot=Path.home()/'.gradle/caches/modules-2/files-2.1/org.ow2.asm'
jars=[next((asmroot/n/'9.10.1').glob('*/'+n+'-9.10.1.jar')) for n in ['asm','asm-tree','asm-commons']]
cp=':'.join(map(str,jars)); java='/usr/lib/jvm/java-17-openjdk/bin/'
classes=payload/'tools';classes.mkdir(exist_ok=True)
subprocess.run([java+'javac','--release','17','-Xlint:all','-Werror','-cp',cp,'-d',str(classes),str(research/'NativeApiAlias.java'),str(research/'NativeFieldRelocation.java')],check=True)
subprocess.run([java+'javac','--release','17','-Xlint:all','-Werror','-cp',str(loader)+':'+cp,'-d',str(classes),str(research/'PrepareNativeRuntime.java')],check=True)
def write(path,entries):
 with zipfile.ZipFile(path,'w',zipfile.ZIP_DEFLATED) as z:
  for n,d in sorted(entries.items()):
   info=zipfile.ZipInfo(n,(1980,1,1,0,0,0)); info.compress_type=zipfile.ZIP_DEFLATED;z.writestr(info,d)
def read(path):
 with zipfile.ZipFile(path) as z:return {n:z.read(n) for n in z.namelist() if not n.endswith('/')}
game={};runtime={}
for n,d in read(payload/'optifine-official-patched.jar').items():
 if n.startswith('notch/'):continue
 stripped=n[4:] if n.startswith('srg/') else n
 if stripped.endswith('.class') and stripped.startswith(('net/minecraft/','com/mojang/')):game[stripped]=d
 elif not stripped.startswith(('optifine/Installer','optifine/Differ')):
  key='srg/'+stripped if stripped.startswith('net/optifine/') and stripped.endswith('.class') else stripped
  runtime[key]=d
# Real Patcher/json/xdelta bodies remain needed by the original runtime transformer.
for n,d in read(original).items():
 if n=='optifine/Patcher.class' or n.startswith(('optifine/json/','optifine/xdelta/')):runtime[n]=d
entries=read(loader)
entries.update({'optifineoforge/patched/'+n:d for n,d in game.items()})
entries['optifineoforge/patched-index.txt']=('\n'.join(n[:-6] for n in sorted(game))+'\n').encode()
entries['optifineoforge/member-restores.txt']=(payload/'member-restores.txt').read_bytes()
entries['optifineoforge/reparent.txt']=(payload/'reparents.txt').read_bytes()
for p in (payload/'donors').rglob('*.class'):entries['optifineoforge/donors/'+str(p.relative_to(payload/'donors'))]=p.read_bytes()
keep=json.loads((research/'prepared-native-candidate.json').read_text())['keep_runtime']
keep.append('net/minecraft/client/server/IntegratedServer\tinitServer\t()Z')
keep.extend(json.loads((research/'native-util-family-candidate.json').read_text())['added'])
entries['optifineoforge/keep-runtime.txt']=('\n'.join(keep)+'\n').encode()
# No synthetic class shims or missing-member default bodies are generated.
entries.pop('optifineoforge/stubs.txt',None)
write(payload/'loader-split.jar',entries);write(payload/'runtime-split.jar',runtime)
for stem in ['loader','runtime']:
 subprocess.run([java+'java','-cp',str(classes)+':'+cp,'NativeApiAlias',str(research/'aliases-face-data.properties'),str(payload/(stem+'-split.jar')),str(payload/(stem+'-alias.jar'))],check=True)
subprocess.run([java+'java','-cp',str(classes)+':'+cp,'NativeFieldRelocation',str(payload/'loader-alias.jar'),str(payload/'loader-final.jar')],check=True)
subprocess.run([java+'java','-cp',str(classes)+':'+str(loader)+':'+cp,'PrepareNativeRuntime',str(payload/'runtime-alias.jar'),str(payload/'runtime-prepared.jar')],check=True)
records=[]
for source,name in [(payload/'loader-final.jar','OptifiNeoforge-2.0.0-mc1202-native-20293-candidate.jar'),(payload/'runtime-prepared.jar','optifine-native-game-layer-classpath.jar')]:
 h=hashlib.sha256(source.read_bytes()).hexdigest();dest=root/'qa'/h;dest.mkdir(parents=True,exist_ok=True);out=dest/name;out.write_bytes(source.read_bytes());records.append({'path':str(out),'sha256':h})
(research/'durable-native-candidate.json').write_text(json.dumps({'candidate_not_certified':True,'runtime_pin':'20.2.93','synthetic_stubs_generated':False,'components':records,'builder_sha256':hashlib.sha256(Path(__file__).read_bytes()).hexdigest()},indent=2)+'\n')
print(json.dumps(records,indent=2))
