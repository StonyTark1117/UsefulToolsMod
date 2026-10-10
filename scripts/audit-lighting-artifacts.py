#!/usr/bin/env python3
"""Verify release separation and exact LabPBR payloads in every built target pair."""
import hashlib,json,zipfile
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1];OUT=ROOT/'validation/dynamic-lighting-2.3.2'
builds=json.loads((OUT/'build/results.json').read_text());assets=json.loads((OUT/'labpbr-assets.json').read_text())['assets'];result=[]
for build in builds:
 target=build['target'];assert build['exit_code']==0,target
 version,loader=target.rsplit('-',1);directory=ROOT/version if version=='1.7.10' else ROOT/version/loader
 candidates=directory/'build/libs'
 base=next(p for p in candidates.glob('usefultoolsmod-*.jar') if not p.name.endswith(('-dev.jar','-sources.jar')))
 companion=next(p for p in candidates.glob('usefultools-lighting-2*.jar'))
 row=dict(target=target,base=str(base.relative_to(ROOT)),companion=str(companion.relative_to(ROOT)))
 for kind,path in [('base',base),('companion',companion)]:
  digest=hashlib.sha256(path.read_bytes()).hexdigest();assert build['artifacts'][str(path.relative_to(ROOT))]==digest,'Build evidence no longer matches '+str(path)
  row[kind+'_sha256']=digest
  with zipfile.ZipFile(path) as jar:
   names=jar.namelist();assert not any('LightingQa' in n or 'ClassicQaAudioPlugin' in n for n in names),'Acceptance code in '+str(path)
   if kind=='base':
    assert not any(n.endswith('.class') and '/usefultools/lighting/' in n for n in names),'Base contains companion classes'
    count=0
    for asset in assets:
     resource=ROOT/asset['specular']
     if not resource.is_relative_to(directory):continue
     internal=resource.relative_to(directory/'src/main/resources').as_posix()
     assert hashlib.sha256(jar.read(internal)).hexdigest()==asset['specular_sha256'],internal
     count+=1
    assert count==13,(target,count);row['emission_maps_verified']=count
   elif loader=='fabric' and version=='1.20.2':assert 'dynamiclights' in json.loads(jar.read('fabric.mod.json'))['entrypoints']
   elif target in {'1.20.1-forge','1.20.1-neoforge','1.20.2-forge'}:
    refmap=json.loads(jar.read('usefultools_lighting.refmap.json'))['mappings']
    tick=refmap['com/stonytark/usefultools/lighting/mixin/ClientTickMixin']['tick']
    assert tick=='Lnet/minecraft/client/Minecraft;m_91398_()V', 'Native SRG tick selector missing from packaged Mixin refmap'
    assert all('/' in owner and '.' not in owner for owner in refmap), 'Mixin reference maps require internal class names'
    row['native_srg_refmap_verified']=True
 row.update(base_has_no_companion_classes=True,companion_has_no_acceptance_classes=True);result.append(row)
assert len(result)==16
(OUT/'artifact-audit.json').write_text(json.dumps(sorted(result,key=lambda r:r['target']),indent=2)+'\n')
print('PASS:',len(result),'base/companion pairs;',sum(r['emission_maps_verified'] for r in result),'exact emission maps; no acceptance code in release jars.')
