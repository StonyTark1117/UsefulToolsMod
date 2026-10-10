#!/usr/bin/env python3
"""Run pinned native shader/backend profiles; every result remains target-specific."""
import argparse, json, subprocess
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
OUT=ROOT/'validation/dynamic-lighting-2.3.2'
TMP=Path('/tmp/useful-tools-lighting-backends')
MODS=Path('/tmp/usefultools-shaders-2.3.2/mods')
PRISM=Path.home()/'.local/share/PrismLauncher/instances'
QA=Path('/tmp/useful-tools-lighting-qa-packages')
p=argparse.ArgumentParser();p.add_argument('targets',nargs='*');p.add_argument('--baseline',action='store_true');p.add_argument('--suffix',default='');p.add_argument('--equipment-scenes',action='store_true');opts=p.parse_args()
stacks={
 '1.20.2-fabric':('codex-shader-1202-quilt','0.18.4','iris-mc1.20.2-1.6.14.jar',MODS/'fabric-api-0.91.6+1.20.2.jar'),
 '1.21.1-neoforge':('codex-shader-262-neoforge','21.1.219',TMP/'1.21.1-neoforge/iris-neoforge-1.8.8+mc1.21.1.jar',None),
 '26.1.2-fabric':('codex-shader-2612-fabric','0.19.3','iris-fabric-1.11.4+mc26.1.2.jar',TMP/'26.1.2-fabric/fabric-api-0.155.3+26.1.2.jar'),
 '26.1.2-neoforge':('codex-shader-262-neoforge','26.1.2.59-beta','iris-neoforge-1.11.4+mc26.1.2.jar',None),
 '26.2-fabric':('codex-shader-262-fabric','0.19.3','iris-fabric-1.11.4+mc26.2.jar','0.158.0+26.2'),
 '26.2-neoforge':('codex-shader-262-neoforge','26.2.0.67','iris-neoforge-1.11.4+mc26.2.jar',None),
}
artifacts={r['target']:r for r in json.loads((OUT/'artifact-audit.json').read_text())}
results=[]
for target,(template,loader,iris,api) in stacks.items():
 if opts.targets and target not in opts.targets:continue
 version,platform=target.rsplit('-',1)
 if isinstance(api,str):
  candidates=list((Path.home()/'.gradle/caches/modules-2/files-2.1/net.fabricmc.fabric-api/fabric-api'/api).glob('*/*.jar'))
  api=next(f for f in candidates if not f.name.endswith('-sources.jar'))
 clients=[QA/(target+'-acceptance-only.jar'),*sorted((TMP/target).glob('sodium-*.jar')),*sorted((TMP/target).glob('lambdynamiclights-*.jar'))]
 if api:clients.append(api)
 java='/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2/bin/java' if version.startswith('26.') else '/usr/lib/jvm/java-17-openjdk/bin/java' if version.startswith('1.20.') else '/usr/lib/jvm/java-21-openjdk/bin/java'
 world=PRISM/('codex-shader-'+('2612' if version=='26.1.2' else '262')+'-fabric')/'minecraft/saves' if version.startswith('26.') else (PRISM/'codex-shader-1202-quilt/minecraft/saves' if version=='1.20.2' else Path('/tmp/spectral-prism-qcq1k1xm/instances/spectral/minecraft/saves'))
 if not opts.baseline:clients.append(iris if isinstance(iris,Path) else MODS/iris)
 for shader in (['baseline'] if opts.baseline else ['bsl','complementary']):
  name=shader+'-backend-'+target+opts.suffix
  command=['python',str(ROOT/'scripts/validate-packaged-spectral.py'),'--template',str(PRISM/template),'--artifact',str(ROOT/artifacts[target]['base']),'--minecraft-version',version,'--java-path',java,'--loader-uid','net.fabricmc.fabric-loader' if platform=='fabric' else 'net.neoforged','--loader-version',loader,'--world-source',str(world),'--mode','baseline' if opts.baseline else 'active','--lighting-qa','--lighting-direct-client','--jvm-arg=-DusefulToolsLightingQa=true','--output',str(OUT/'packaged'/name)]
  for client in clients:
   if not client.is_file():raise FileNotFoundError(client)
   command+=['--client-mod',str(client)]
  if not opts.baseline:
   command+=['--gpu-zink','--lighting-scenes','--toggle-shaders','--shader-pack','/tmp/usefultools-shaders-2.3.2/'+('BSL_v10.1.2.zip' if shader=='bsl' else 'ComplementaryReimagined_r5.9.3.zip')]
   if opts.equipment_scenes:command+=['--lighting-equipment-scenes']
   settings=['ADVANCED_MATERIALS=true','MATERIAL_FORMAT=1','EXPOSURE=0.00','AUTO_EXPOSURE=false','BLOOM_STRENGTH=1.00'] if shader=='bsl' else ['RP_MODE=3','TM_EXPOSURE=1.00','BLOOM_STRENGTH=0.12','CUSTOM_EMISSION_INTENSITY=100']
   for setting in settings:command+=['--shader-setting',setting]
  print('START '+name,flush=True)
  with (OUT/(name+'.log')).open('w') as log:code=subprocess.call(command,cwd=ROOT,stdout=log,stderr=subprocess.STDOUT)
  results.append(dict(profile=name,exit_code=code,command=command))
  (OUT/('backend-profile-batch'+('-baseline' if opts.baseline else '')+'.json')).write_text(json.dumps(results,indent=2)+'\n')
  print('END '+name+' '+str(code),flush=True)
