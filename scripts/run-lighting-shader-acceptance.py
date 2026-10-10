#!/usr/bin/env python3
"""Bounded, native, isolated software-rendered shader acceptance profiles."""
import argparse,concurrent.futures,json,subprocess,time,zipfile
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1];OUT=ROOT/'validation/dynamic-lighting-2.3.2';MODS=Path('/tmp/usefultools-shaders-2.3.2/mods');QA=Path('/tmp/useful-tools-lighting-qa-packages');BACK=Path('/tmp/useful-tools-lighting-backends');PRISM=Path.home()/'.local/share/PrismLauncher/instances'
p=argparse.ArgumentParser();p.add_argument('targets',nargs='+');p.add_argument('--workers',type=int,default=2);p.add_argument('--suffix',default='native-equipment');p.add_argument('--experimental-backends',action='store_true');a=p.parse_args()
def client_stack(target):
 v,l=target.rsplit('-',1)
 if l=='fabric':
  sodium='sodium-fabric-'+('0.9.2+mc'+v if v.startswith('26') else '0.6.13+mc1.21.1' if v=='1.21.1' else 'mc1.20.2-0.5.5' if v=='1.20.2' else '0.5.13+mc1.20.1')+'.jar'
  iris='iris-fabric-1.11.4+mc'+v+'.jar' if v.startswith('26') else 'iris-fabric-1.8.8+mc1.21.1.jar' if v=='1.21.1' else 'iris-mc1.20.2-1.6.14.jar' if v=='1.20.2' else 'iris-1.7.6+mc1.20.1.jar'
  return 'iris',[MODS/sodium,MODS/iris,*sorted((BACK/target).glob('lambdynamiclights-*.jar'))]
 if l=='neoforge' and v.startswith('26'):return 'iris',[MODS/('sodium-neoforge-0.9.2+mc'+v+'.jar'),MODS/('iris-neoforge-1.11.4+mc'+v+'.jar'),*sorted((BACK/target).glob('lambdynamiclights-*.jar'))]
 if target=='1.20.1-neoforge':return 'iris',[MODS/'embeddium-0.3.31+mc1.20.1.jar',MODS/'oculus-mc1.20.1-1.8.0.jar']
 if target=='1.20.2-forge':return 'optifine',[MODS/'preview_OptiFine_1.20.2_HD_U_I7_pre1.jar']
 if target=='1.20.2-neoforge':
  oculus=MODS/'oculus-mc1.20.2-1.8.0.jar'
  if a.experimental_backends:
   import hashlib
   manifest=json.loads((ROOT/'validation/shader-support-2.3.2/recovery/implementation/research/oculus-1202-neoforge-qa2-artifact.json').read_text());oculus=Path(manifest['artifact']);assert hashlib.sha256(oculus.read_bytes()).hexdigest()==manifest['artifact_sha256']
  return 'iris',[MODS/'embeddium-neoforge-0.2.10+mc1.20.2.jar',oculus]
 if target=='1.21.1-forge':return 'iris',[MODS/'beddium-1.0.11-mc1.21.1.jar',MODS/'oculus-mc1.21.1-1.8.8.jar']
 if target=='1.21.1-neoforge':return 'iris',[MODS/'sodium-neoforge-0.8.13+mc1.21.1.jar',MODS/'iris-neoforge-1.8.14-beta.1+mc1.21.1.jar',*sorted((BACK/target).glob('lambdynamiclights-*.jar'))]
 if target=='1.20.1-forge':return 'optifine',[PRISM/'codex-shader-1201-forge-optifine-47218/minecraft/mods/OptiFine_1.20.1_HD_U_I6.jar']
 if l=='forge' and v.startswith('26'):
  if a.experimental_backends and v=='26.2':return 'community',[MODS/'oculus-forge-26.2-0.3.0-beta.1.jar']
  optifine=MODS/('preview_OptiFine_'+v+'_HD_U_'+('K1_pre2' if v=='26.1.2' else 'K2_pre1')+'.jar')
  if a.experimental_backends and v=='26.1.2':
   import hashlib
   manifest=json.loads((ROOT/'validation/shader-support-2.3.2/recovery/implementation/research/optifine2612-prepatched-artifact.json').read_text());optifine=Path(manifest['artifact']);assert hashlib.sha256(optifine.read_bytes()).hexdigest()==manifest['artifact_sha256']
  return 'optifine',[optifine]
 raise ValueError(target)
def run_target(target):
 profile=json.loads((OUT/'multiplayer/profiles'/f'{target}.json').read_text());base=profile['invocation'];base[0]=str(ROOT/'scripts/validate-packaged-spectral.py');base[base.index('--mode')+1]='active';integration,clients=client_stack(target)
 for f in clients:
  if not f.is_file():raise FileNotFoundError(f)
  base+=['--client-mod',str(f)]
 deadline=time.monotonic()+1200
 while True:
  with zipfile.ZipFile(QA/(target+'-acceptance-only.jar')) as z:
   native=any(b'USEFUL_TOOLS_LIGHTING_NATIVE_COMMAND_CONTROLLER_READY' in z.read(n) for n in z.namelist() if n.endswith('/LightingQaEquipmentScene.class'))
  if native:break
  if time.monotonic()>deadline:raise RuntimeError('Native scene package not ready: '+target)
  time.sleep(2)
 base+=['--shader-integration',integration,'--lighting-scenes','--lighting-equipment-scenes','--toggle-shaders']
 rows=[]
 for shader in ['bsl','complementary']:
  name=shader+'-software-'+target+'-'+a.suffix;cmd=base.copy();cmd[cmd.index('--output')+1]=str(OUT/'packaged'/name);version=target.rsplit('-',1)[0]
  pack='BSL_v10.1.5.zip' if shader=='bsl' and version.startswith('26') else 'BSL_v10.1.2.zip' if shader=='bsl' else 'ComplementaryReimagined_r5.9.3-mc262.zip' if version=='26.2' else 'ComplementaryReimagined_r5.9.3.zip';cmd+=['--shader-pack',str(MODS.parent/pack)]
  settings=['ADVANCED_MATERIALS=true','MATERIAL_FORMAT=1','EXPOSURE=0.00','AUTO_EXPOSURE=false','BLOOM_STRENGTH=1.00'] if shader=='bsl' else ['RP_MODE=3','TM_EXPOSURE=1.00','BLOOM_STRENGTH=0.12','CUSTOM_EMISSION_INTENSITY=100']
  for setting in settings:cmd+=['--shader-setting',setting]
  print('START',name,flush=True)
  with (OUT/(name+'.log')).open('w') as log:code=subprocess.call(['python',*cmd],cwd=ROOT,stdout=log,stderr=subprocess.STDOUT)
  rows.append(dict(target=target,profile=name,exit_code=code,command=cmd,renderer='Owned Xvfb software OpenGL; exact glxinfo saved per run'))
  print('END',name,code,flush=True)
 return rows
with concurrent.futures.ThreadPoolExecutor(max_workers=a.workers) as executor:
 rows=[]
 for future in concurrent.futures.as_completed([executor.submit(run_target,t) for t in a.targets]):
  rows+=future.result();(OUT/('software-shader-batch-'+a.suffix+'.json')).write_text(json.dumps(rows,indent=2)+'\n')
