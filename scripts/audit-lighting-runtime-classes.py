#!/usr/bin/env python3
"""Match every production companion class to the private package actually tested."""
import argparse,hashlib,json,zipfile
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1];OUT=ROOT/'validation/dynamic-lighting-2.3.2'
p=argparse.ArgumentParser(description=__doc__);p.add_argument('profiles',nargs='+');a=p.parse_args()
audit={row['target']:row for row in json.loads((OUT/'artifact-audit.json').read_text())}
destination=OUT/'reviewed-runtime-class-equivalence.json';rows=json.loads(destination.read_text())
def sha(path):return hashlib.sha256(path.read_bytes()).hexdigest()
for profile in a.profiles:
 folder=OUT/'packaged'/profile;report=json.loads((folder/'report.json').read_text())
 assert report['status']=='captures_complete' and report['clean_client_exit']
 version=next(c['version'] for c in report['components']['components'] if c['uid']=='net.minecraft')
 loader={'net.fabricmc.fabric-loader':'fabric','net.minecraftforge':'forge','net.neoforged':'neoforge'}[report['loader_uid']];target=version+'-'+loader
 release=ROOT/audit[target]['companion'];assert sha(release)==audit[target]['companion_sha256'],'Release artifact changed since audit'
 with zipfile.ZipFile(release) as jar:classes={name:jar.read(name) for name in jar.namelist() if name.endswith('.class')}
 assert classes and 'com/stonytark/usefultools/lighting/LightField.class' in classes
 work=Path(report['work_dir']);roots=[work,Path.home()/'.cache/useful-tools-lighting-completed-runs'/work.name]
 packages=[]
 for root in roots:
  for package in (root/'instances/spectral/minecraft/mods').glob('*.jar'):
   with zipfile.ZipFile(package) as jar:
    if all(name in jar.namelist() for name in classes):packages.append(package)
 if not packages:
  for name,expected in report['artifacts'].items():
   package=Path(name)
   if not package.is_file():
    saved=report.get('preserved_inputs',{}).get(name,{})
    if saved.get('sha256')==expected:package=Path(saved['path'])
   if not package.is_file() or sha(package)!=expected:continue
   with zipfile.ZipFile(package) as jar:
    if all(name in jar.namelist() for name in classes):packages.append(package)
 assert len(packages)==1,('Expected one preserved companion input identified by the actual native report',profile,packages)
 package=packages[0];assert sha(package) in report['artifacts'].values(),'Preserved package not identified in native report'
 with zipfile.ZipFile(package) as jar:changed=[name for name,data in classes.items() if jar.read(name)!=data]
 assert not changed,('Production companion bytecode differs',profile,changed)
 shader=('bsl' if profile.startswith('bsl') else 'complementary' if profile.startswith('complementary') else None)
 row=dict(target=target,shader=shader,profile=profile,report_sha256=sha(folder/'report.json'),observed_native_package=str(package),observed_package_sha256=sha(package),current_release_sha256=sha(release),different_production_classes=changed,meaning='Every production companion class in the current release jar is byte-identical to the preserved package loaded by this native run. Acceptance-only classes are separate additions. Material-only runs separately disclose their intentional in-memory renderer instrumentation.')
 rows=[previous for previous in rows if previous['profile']!=profile];rows.append(row);print(target,profile,'production classes match')
 destination.write_text(json.dumps(rows,indent=2)+'\n')
destination.write_text(json.dumps(rows,indent=2)+'\n')
