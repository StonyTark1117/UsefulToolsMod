#!/usr/bin/env python3
"""Make a private shader control with zero emission alpha and identical specular RGB/diffuse assets."""
import argparse,hashlib,io,json,zipfile
from pathlib import Path
from PIL import Image
p=argparse.ArgumentParser(description=__doc__)
p.add_argument('--artifact',type=Path,required=True)
p.add_argument('--output',type=Path,required=True)
p.add_argument('--control-artifact',type=Path,help='Also make a private base JAR changing only these 13 maps, avoiding resource-pack ordering')
p.add_argument('--provenance',type=Path,help='Original immutable build provenance for the private derived control JAR')
a=p.parse_args();maps={};records={}
with zipfile.ZipFile(a.artifact) as jar:
    if 'pack.mcmeta' in jar.namelist():metadata=jar.read('pack.mcmeta')
    else:
        info=json.loads(jar.read('mcmod.info'))
        assert any(row.get('mcversion')=='1.7.10' for row in info),'Missing metadata in a non-Classic artifact'
        metadata=json.dumps({'pack':{'pack_format':1,'description':'Private Classic emission-alpha control'}}).encode()
    for name in sorted(jar.namelist()):
        if name.startswith('assets/usefultoolsmod/textures/') and name.endswith('_s.png') and 'hglow' in name:
            before=jar.read(name);image=Image.open(io.BytesIO(before)).convert('RGBA');rgb=image.convert('RGB').tobytes()
            image.putalpha(0);buffer=io.BytesIO();image.save(buffer,format='PNG');after=buffer.getvalue()
            assert Image.open(io.BytesIO(after)).convert('RGB').tobytes()==rgb
            maps[name]=after;records[name]={'original_sha256':hashlib.sha256(before).hexdigest(),'control_sha256':hashlib.sha256(after).hexdigest(),'specular_rgb_unchanged':True,'emission_alpha':0}
assert len(maps)==13,('Expected block, ten item/tool/armor icons and two equipped armor maps',len(maps))
a.output.parent.mkdir(parents=True,exist_ok=True)
with zipfile.ZipFile(a.output,'w',zipfile.ZIP_DEFLATED) as pack:
    pack.writestr('pack.mcmeta',metadata)
    for name,data in maps.items():pack.writestr(name,data)
report={'artifact':str(a.artifact.resolve()),'artifact_sha256':hashlib.sha256(a.artifact.read_bytes()).hexdigest(),'control_pack':str(a.output.resolve()),'control_pack_sha256':hashlib.sha256(a.output.read_bytes()).hexdigest(),'purpose':'Private A/B shader emission control; no diffuse images or world state changed','maps':records}
if a.control_artifact:
    if not a.provenance:p.error('A private control artifact requires original provenance')
    provenance=json.loads(a.provenance.read_text());assert provenance['artifact_sha256']==report['artifact_sha256']
    a.control_artifact.parent.mkdir(parents=True,exist_ok=True)
    with zipfile.ZipFile(a.artifact) as before,zipfile.ZipFile(a.control_artifact,'w',zipfile.ZIP_DEFLATED) as after:
        for entry in before.infolist():after.writestr(entry,maps.get(entry.filename,before.read(entry)))
    with zipfile.ZipFile(a.artifact) as before,zipfile.ZipFile(a.control_artifact) as after:
        changed=[n for n in before.namelist() if before.read(n)!=after.read(n)]
        assert set(changed)==set(maps)
    provenance.update(artifact=str(a.control_artifact.resolve()),artifact_sha256=hashlib.sha256(a.control_artifact.read_bytes()).hexdigest(),private_emission_control=True,original_artifact=str(a.artifact.resolve()),original_artifact_sha256=report['artifact_sha256'],unchanged_production_classes=True,changed_resource_paths=changed,control_map_records=records)
    provenance['source_sha256']=dict(provenance['source_sha256'],**{'private-emission-control:'+n:r['control_sha256'] for n,r in records.items()})
    a.control_artifact.with_suffix('.json').write_text(json.dumps(provenance,indent=2)+'\n')
    report['derived_control_artifact']=str(a.control_artifact.resolve());report['derived_control_artifact_sha256']=provenance['artifact_sha256']
a.output.with_suffix('.json').write_text(json.dumps(report,indent=2)+'\n');print(json.dumps({k:v for k,v in report.items() if k!='maps'},indent=2))
