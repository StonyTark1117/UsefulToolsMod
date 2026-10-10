#!/usr/bin/env python3
"""Pin native two-client profiles to immutable, hash-audited artifact copies."""
import json,hashlib,shutil
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1];OUT=ROOT/'validation/dynamic-lighting-2.3.2';PRISM=Path.home()/'.local/share/PrismLauncher/instances';STORE=Path('/tmp/useful-tools-lighting-reviewed-artifacts');STORE.mkdir(exist_ok=True)
profiles=OUT/'multiplayer/profiles';profiles.mkdir(parents=True,exist_ok=True)
for row in json.loads((OUT/'artifact-audit.json').read_text()):
 target=row['target']
 if target=='1.7.10-forge':continue
 version,loader=target.rsplit('-',1);root=ROOT/version/loader
 base=ROOT/row['base'];copy=STORE/row['base_sha256']/base.name;copy.parent.mkdir(parents=True,exist_ok=True)
 if not copy.exists():shutil.copy2(base,copy)
 assert hashlib.sha256(copy.read_bytes()).hexdigest()==row['base_sha256']
 record=dict(artifact=str(base),artifact_sha256=row['base_sha256'],source_sha256={str(p.relative_to(ROOT)):hashlib.sha256(p.read_bytes()).hexdigest() for p in (root/'src').rglob('*') if p.is_file()},build_evidence=str(OUT/'build/results.json'),note='Artifact hashes identify the actual tested bytecode; source files are observed at the immutable artifact copy. Shared working-tree changes after the build do not alter this copy.')
 provenance=profiles/(target+'-artifact-'+row['base_sha256']+'.json')
 if not provenance.exists():provenance.write_text(json.dumps(record,indent=2)+'\n')
 if version.startswith('26.'):
  template='codex-shader-'+('2612' if version=='26.1.2' else '262')+'-fabric' if loader=='fabric' else 'codex-shader-262-neoforge'
  world=PRISM/('codex-shader-'+('2612' if version=='26.1.2' else '262')+'-fabric')/'minecraft/saves'
 elif version=='1.20.1':
  template='codex-shader-1201-fabric' if loader=='fabric' else 'codex-shader-1201-neoforge-oculus' if loader=='neoforge' else 'codex-shader-1201-forge-optifine-47218';world=PRISM/'codex-shader-1201-fabric/minecraft/saves'
 elif version=='1.20.2':
  template='codex-shader-1202-quilt' if loader=='fabric' else 'codex-shader-1201-neoforge-oculus';world=Path('/tmp/useful-tools-lighting-native-worlds/1.20.2-forge/saves') if target=='1.20.2-forge' and Path('/tmp/useful-tools-lighting-native-worlds/1.20.2-forge/saves').is_dir() else PRISM/'codex-shader-1202-quilt/minecraft/saves'
 else:
  template='codex-shader-1211-forge-optifine-52016' if loader=='forge' else 'codex-shader-262-neoforge' if loader=='neoforge' else 'codex-shader-1201-fabric'
  world=Path('/tmp/spectral-prism-qcq1k1xm/instances/spectral/minecraft/saves')
 if loader=='fabric':uid='net.fabricmc.fabric-loader';pin='0.19.3' if version.startswith('26.') else '0.18.4'
 else:
  uid='net.minecraftforge' if loader=='forge' else 'net.neoforged';key='forge_version' if loader=='forge' else 'neo_version';props=dict(line.split('=',1) for line in (root/'gradle.properties').read_text().splitlines() if '=' in line and not line.lstrip().startswith('#'));pin=props[key]
 java='/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2/bin/java' if version.startswith('26.') else '/usr/lib/jvm/java-17-openjdk/bin/java' if version.startswith('1.20.') else '/usr/lib/jvm/java-21-openjdk/bin/java'
 clients=[Path('/tmp/useful-tools-lighting-qa-packages')/(target+'-acceptance-only.jar')]
 if loader=='fabric':
  if version.startswith('26.'):
   api='0.155.3+26.1.2' if version=='26.1.2' else '0.158.0+26.2'
   candidates=([Path('/tmp/useful-tools-lighting-backends/26.1.2-fabric/fabric-api-'+api+'.jar')] if version=='26.1.2' else list((Path.home()/'.gradle/caches/modules-2/files-2.1/net.fabricmc.fabric-api/fabric-api'/api).glob('*/*.jar')))
   clients.append(next(p for p in candidates if p.is_file() and not p.name.endswith('-sources.jar')))
  else:clients.append(Path('/tmp/usefultools-shaders-2.3.2/mods')/('fabric-api-0.91.6+1.20.2.jar' if version=='1.20.2' else 'fabric-api-0.116.9+1.21.1.jar' if version=='1.21.1' else 'fabric-api-0.92.11+1.20.1.jar'))
 if loader=='fabric' and version=='1.20.1':clients[-1]=PRISM/'codex-shader-1201-fabric/minecraft/mods/fabric-api-0.92.11+1.20.1.jar'
 for client in clients:assert client.is_file(),client
 args=['scripts/validate-packaged-spectral.py','--template',str(PRISM/template),'--artifact',str(copy),'--artifact-provenance',str(provenance),'--minecraft-version',version,'--java-path',java,'--loader-uid',uid,'--loader-version',pin,'--world-source',str(world),'--mode','baseline','--lighting-qa','--jvm-arg=-DusefulToolsLightingQa=true','--output',str(OUT/'multiplayer'/target)]
 args+=['--lighting-direct-client']
 if loader=='forge':args+=['--menu-world']
 for client in clients:args+=['--client-mod',str(client)]
 (profiles/(target+'.json')).write_text(json.dumps(dict(target=target,invocation=args,components=dict(components=[dict(uid='net.minecraft',version=version)])),indent=2)+'\n')
print('Prepared 15 immutable native profiles.')
