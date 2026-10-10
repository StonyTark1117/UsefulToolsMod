#!/usr/bin/env python3
"""Freeze pinned build artifacts and retain every recovery target in explicit runs.

Missing shader candidates remain unresolved records; they are never removed or
converted into passes. Publisher candidates still require packaged validation.
"""
import argparse
import hashlib
import json
from pathlib import Path
import shutil

ROOT=Path(__file__).resolve().parents[1]
CACHE=Path('/tmp/usefultools-shaders-2.3.2')
INSTANCES=Path.home()/'.local/share/PrismLauncher/instances'
parser=argparse.ArgumentParser(description=__doc__)
parser.add_argument('--build-record',type=Path,required=True)
parser.add_argument('--output',type=Path,required=True)
args=parser.parse_args()
build=json.loads(args.build_record.read_text())
built={r['target']:r for r in build['artifacts']}
original=json.loads((ROOT/'validation/shader-support-2.3.2/recovery/acceptance-matrix.json').read_text())
pins={
 '1.7.10':{'forge':'10.13.4.1614'},
 '1.20.1':{'forge':'47.4.10','fabric':'0.18.4','neoforge':'47.1.106'},
 '1.20.2':{'forge':'48.1.0','fabric':'0.18.4','neoforge':'20.2.93'},
 '1.21.1':{'forge':'52.1.10','fabric':'0.18.4','neoforge':'21.1.219'},
 '26.1.2':{'forge':'64.0.8','fabric':'0.19.2','neoforge':'26.1.2.59-beta'},
 '26.2':{'forge':'65.1.2','fabric':'0.19.3','neoforge':'26.2.0.67'}}
uids={'forge':'net.minecraftforge','fabric':'net.fabricmc.fabric-loader',
      'neoforge':'net.neoforged','quilt':'org.quiltmc.quilt-loader'}
templates={'1.7.10':'codex-shader-1710-forge-no-loader','1.20.1':'codex-shader-1201-quilt',
           '1.20.2':'codex-shader-1202-quilt','1.21.1':'codex-shader-1201-quilt',
           '26.1.2':'codex-shader-2612-fabric','26.2':'codex-shader-262-fabric'}
worlds={'1.21.1':Path('/tmp/spectral-prism-qcq1k1xm/instances/spectral/minecraft/saves')}
api={'1.20.1':INSTANCES/'codex-shader-1201-fabric/minecraft/mods/fabric-api-0.92.11+1.20.1.jar',
     '1.20.2':CACHE/'mods/fabric-api-0.91.6+1.20.2.jar',
     '1.21.1':CACHE/'mods/fabric-api-0.116.9+1.21.1.jar',
     '26.1.2':INSTANCES/'codex-shader-2612-fabric/minecraft/mods/fabric-api-0.155.3+26.1.2.jar',
     '26.2':INSTANCES/'codex-shader-262-fabric/minecraft/mods/fabric-api-0.158.0+26.2.jar'}
fabric_stacks={
 '1.20.1':['iris-1.7.6+mc1.20.1.jar','sodium-fabric-0.5.11+mc1.20.1.jar'],
 '1.20.2':['iris-mc1.20.2-1.6.14.jar','sodium-fabric-mc1.20.2-0.5.5.jar'],
 '1.21.1':['iris-fabric-1.8.8+mc1.21.1.jar','sodium-fabric-0.6.13+mc1.21.1.jar'],
 '26.1.2':['iris-fabric-1.11.4+mc26.1.2.jar','sodium-fabric-0.9.2+mc26.1.2.jar'],
 '26.2':['iris-fabric-1.11.4+mc26.2.jar','sodium-fabric-0.9.2+mc26.2.jar']}
classic_qa=json.loads((ROOT/'validation/shader-support-2.3.2/recovery/implementation/classic-qa-lantern-inventory-artifact.json').read_text())['artifact']
def mods(names):return [CACHE/'mods'/name for name in names]
def stacks(version,loader):
 if loader in ('fabric','quilt'):return [('iris',mods(fabric_stacks[version]))]
 if version=='1.7.10':return [('optifine',[INSTANCES/'codex-shader-1710-forge-optifine/minecraft/mods/OptiFine_1.7.10_HD_U_E7.jar'])]
 if version=='1.20.1':
  result=[('iris',mods(['oculus-mc1.20.1-1.8.0.jar','embeddium-0.3.31+mc1.20.1.jar']))]
  if loader=='forge':result.append(('optifine',[INSTANCES/'codex-shader-1201-forge-optifine-47218/minecraft/mods/OptiFine_1.20.1_HD_U_I6.jar']))
  return result
 if version=='1.20.2':
  candidates=[('iris',mods(['oculus-mc1.20.2-1.8.0.jar','embeddium-'+loader+'-0.2.10+mc1.20.2.jar']))]
  if loader=='neoforge':
   native_port=json.loads((ROOT/'validation/shader-support-2.3.2/recovery/implementation/research/oculus-1202-neoforge-qa2-artifact.json').read_text())
   artifact=Path(native_port['artifact'])
   assert hashlib.sha256(artifact.read_bytes()).hexdigest()==native_port['artifact_sha256'],'Native Oculus experiment changed'
   candidates.append(('iris',[artifact,CACHE/'mods/embeddium-neoforge-0.2.10+mc1.20.2.jar']))
  return candidates
 if version=='1.21.1':
  if loader=='forge':return [('iris',mods(['oculus-mc1.21.1-1.8.8.jar','beddium-1.0.11-mc1.21.1.jar'])),('optifine',[INSTANCES/'codex-shader-1211-forge-optifine/minecraft/mods/OptiFine_1.21.1_HD_U_J1.jar'])]
  return [('iris',mods(['iris-neoforge-1.8.12+mc1.21.1.jar','sodium-neoforge-0.6.13+mc1.21.1.jar']))]
 if version.startswith('26.') and loader=='neoforge':return [('iris',mods(['iris-neoforge-1.11.4+mc'+version+'.jar','sodium-neoforge-0.9.2+mc'+version+'.jar']))]
 if version=='26.2' and loader=='forge':return [('community',mods(['oculus-forge-26.2-0.3.0-beta.1.jar']))]
 return []
rows=[]
for required in original['rows']:
 target=required['target'];version,loader=target.rsplit('-',1)
 artifact_target=version+'-'+('fabric' if loader=='quilt' else loader)
 record=built[artifact_target];artifact=Path(record['artifact'])
 assert hashlib.sha256(artifact.read_bytes()).hexdigest()==record['artifact_sha256'],target+' was rebuilt after build record'
 frozen=CACHE/'matrix-artifacts'/record['artifact_sha256'];frozen.mkdir(parents=True,exist_ok=True)
 shutil.copy2(artifact,frozen/artifact.name)
 provenance=frozen/'provenance.json';provenance.write_text(json.dumps(record,indent=2)+'\n')
 template=INSTANCES/templates[version];world=worlds.get(version,template/'minecraft/saves')
 java='/usr/lib/jvm/java-'+('8' if version=='1.7.10' else '17' if version.startswith('1.20.') else '21')+'-openjdk/bin/java'
 if version.startswith('26.'):java='/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2/bin/java'
 native=[Path(classic_qa)] if version=='1.7.10' else [api[version]] if loader in ('fabric','quilt') else []
 base=['python3',str(ROOT/'scripts/validate-packaged-spectral.py'),'--template',str(template),
       '--artifact',str(frozen/artifact.name),'--artifact-provenance',str(provenance),
       '--minecraft-version',version,'--world-source',str(world),'--java-path',java,
       '--loader-uid',uids[loader],'--loader-version','0.30.0' if loader=='quilt' else pins[version][loader],
       '--gpu-zink','--menu-world','--entity-states','--surfaces']
 if version=='1.7.10':base+=['--classic-qa']
 for dependency in native:base+=['--client-mod',str(dependency)]
 def add(mode,integration,dependencies,pack=None,index=0):
  name=target+'-'+mode+'-'+integration+str(index)+(('-'+pack.stem) if pack else '')
  command=base+['--mode',mode,'--shader-integration',integration,'--output',str(args.output.parent/'matrix-runs'/name)]
  for dependency in dependencies:command+=['--client-mod',str(dependency)]
  if pack:command+=['--shader-pack',str(pack),'--toggle-shaders']
  for value in [template,world,artifact,*native,*dependencies,*([pack] if pack else [])]:assert value.exists(),str(value)
  rows.append({'name':name,'target':target,'build_pin':pins[version].get('fabric' if loader=='quilt' else loader),
               'runtime_pin':'0.30.0' if loader=='quilt' else pins[version][loader],'command':command})
 add('baseline','optifine' if version=='1.7.10' else 'iris',[])
 candidates=stacks(version,loader)
 if not candidates:rows.append({'name':target+'-missing-shader-stack','target':target,'status':'unresolved',
                                'reason':'Exact compatible shader runtime still under investigation; both primary packs and disabled/toggle controls remain required.'})
 for i,(integration,dependencies) in enumerate(candidates):
  add('disabled',integration,dependencies,index=i)
  pair=['BSL_v10.1.1.zip','ComplementaryReimagined_r5.7.1.zip'] if integration=='community' else ['BSL_v10.1.2.zip','ComplementaryReimagined_r5.7.1.zip' if version.startswith('26.') else 'ComplementaryReimagined_r5.9.3.zip']
  for pack in pair:add('active',integration,dependencies,CACHE/pack,i)
  if version=='1.7.10':add('active',integration,dependencies,INSTANCES/"codex-shader-1710-forge-optifine/minecraft/shaderpacks/Sildur's Enhanced Default v1.14 Fast.zip",i)
assert {r['target'] for r in rows}=={r['target'] for r in original['rows']}
rows.sort(key=lambda r:0 if '--mode' in r.get('command',[]) and r['command'][r['command'].index('--mode')+1]=='baseline' else 1)
args.output.write_text(json.dumps(rows,indent=2)+'\n')
print(json.dumps({'targets':len(original['rows']),'runnable':sum('command' in r for r in rows),'unresolved':sum('command' not in r for r in rows)}))
