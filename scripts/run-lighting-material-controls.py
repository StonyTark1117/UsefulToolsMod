#!/usr/bin/env python3
"""Run separate native shader material A/B controls without changing production releases."""
import argparse,hashlib,json,subprocess,sys,zipfile,threading,shutil,signal
from pathlib import Path
from lighting_pins import pinned_loader
from lighting_client_lifecycle import wait_client
ROOT=Path(__file__).resolve().parents[1];OUT=ROOT/'validation/dynamic-lighting-2.3.2';PACKAGED=OUT/'packaged'
p=argparse.ArgumentParser(description=__doc__)
p.add_argument('targets',nargs='+')
p.add_argument('--shaders',nargs='+',choices=['bsl','complementary'],default=['bsl','complementary'])
p.add_argument('--modes',nargs='+',choices=['native','zero-alpha'],default=['native','zero-alpha'],
               help='Run only the named control modes; use one mode to keep each invocation to one client')
p.add_argument('--workers',type=int,choices=[1],default=1,
               help='Run one client at a time to avoid renderer contention')
p.add_argument('--suffix',default='frozen-clock-v1')
p.add_argument('--qa-package',type=Path,
               help='Current protected acceptance-only package to replace a stale historical QA path')
p.add_argument('--admission-wait-seconds',type=int,choices=range(61),default=0,
               help='Wait briefly for the single shared client slot without preparing a profile')
a=p.parse_args();control_lock=threading.Lock();records=json.loads((OUT/'shader-certification.json').read_text())['coverage'];rows=[]
def cancel_batch(signum,frame):
    raise KeyboardInterrupt('Material batch cancelled by signal '+str(signum))
signal.signal(signal.SIGTERM,cancel_batch)
def run(target,shader):
    choices=[]
    for row in records:
        if row['target']!=target or not row.get('equipment_review') or not row['profile'].startswith(shader):continue
        folder=PACKAGED/row['profile'];report=json.loads((folder/'report.json').read_text())
        if report['status']=='captures_complete' and report.get('clean_client_exit') and not report.get('lighting_material_control'):choices.append((folder,report))
    if not choices:return [dict(target=target,shader=shader,status='pending matching reviewed shader stack')]
    folder,original=max(choices,key=lambda row:(row[0]/'report.json').stat().st_mtime)
    base=original['invocation'].copy();base[0]=str(ROOT/'scripts/validate-packaged-spectral.py')
    filtered=[];index=0
    while index<len(base):
        if base[index]=='--client-mod':
            path=Path(base[index+1])
            if path.name.lower().startswith('lambdynamiclights'):
                index+=2;continue
            if path.name.endswith('-acceptance-only.jar') and a.qa_package:
                path=a.qa_package.resolve()
            elif not path.is_file():
                expected=original['artifacts'].get(str(path))
                recovered=(Path.home()/'.cache/useful-tools-lighting-inputs'/expected/path.name
                           if expected else None)
                if recovered and recovered.is_file():path=recovered
                else:raise FileNotFoundError('Missing protected client dependency: '+str(path))
            base[index+1]=str(path)
            with zipfile.ZipFile(path) as jar:
                if 'fabric.mod.json' in jar.namelist() and json.loads(jar.read('fabric.mod.json')).get('id') in {'lambdynlights','lambdynlights_api'}:index+=2;continue
        filtered.append(base[index]);index+=1
    base=filtered
    base[base.index('--loader-version')+1]=pinned_loader(target)
    shader_path=Path(base[base.index('--shader-pack')+1])
    if not shader_path.is_file():
        shader_hash=original['shader_pack']['sha256']
        recovered=Path.home()/'.cache/useful-tools-lighting-inputs'/shader_hash/shader_path.name
        if not recovered.is_file():
            raise FileNotFoundError('Missing protected shader pack: '+str(shader_path))
        base[base.index('--shader-pack')+1]=str(recovered)
    if '--ocr-python' in base:
        ocr_path=Path(base[base.index('--ocr-python')+1])
        if not ocr_path.is_file():
            recovered=Path.home()/'.cache/useful-tools-lighting-ocr/bin/python'
            if not recovered.is_file():raise FileNotFoundError('Missing OCR interpreter: '+str(ocr_path))
            base[base.index('--ocr-python')+1]=str(recovered)
    else:
        recovered=Path.home()/'.cache/useful-tools-lighting-ocr/bin/python'
        if not recovered.is_file():raise FileNotFoundError('Missing protected OCR interpreter')
        base+=['--ocr-python',str(recovered)]
    if '--lighting-direct-client' not in base and not target.startswith('1.7.10'):base.append('--lighting-direct-client')
    if a.admission_wait_seconds:
        base+=['--client-admission-wait-seconds',str(a.admission_wait_seconds)]
    base.append('--lighting-material-only')
    artifact=Path(base[base.index('--artifact')+1]).resolve()
    if not artifact.is_file():
        expected=original['artifacts'].get(str(artifact))
        recovered=(Path.home()/'.cache/useful-tools-lighting-inputs'/expected/artifact.name
                   if expected else None)
        if not recovered or not recovered.is_file():
            raise FileNotFoundError('Missing protected base artifact: '+str(artifact))
        artifact=recovered;base[base.index('--artifact')+1]=str(artifact)
    if '--artifact-provenance' not in base:
        expected=original['artifacts'][str(artifact)]
        immutable=Path('/tmp/useful-tools-lighting-reviewed-artifacts')/expected/artifact.name
        immutable.parent.mkdir(parents=True,exist_ok=True)
        if not immutable.exists():
            work=Path(original['work_dir'])
            candidates=[artifact,*[path for directory in [work,Path.home()/'.cache/useful-tools-lighting-completed-runs'/work.name] for path in (directory/'instances/spectral/minecraft/mods').glob('*.jar')]]
            matches=[path for path in candidates if path.exists() and hashlib.sha256(path.read_bytes()).hexdigest()==expected]
            if not matches:raise RuntimeError('Historical artifact changed; recover its exact saved hash before comparing '+target)
            shutil.copy2(matches[0],immutable)
        if hashlib.sha256(immutable.read_bytes()).hexdigest()!=expected:raise RuntimeError('Saved artifact hash mismatch')
        provenance=immutable.with_suffix('.material-provenance.json')
        provenance.write_text(json.dumps(dict(artifact=str(artifact),artifact_sha256=expected,source_sha256=original['source_sha256'],historical_profile=folder.name,note='Source observations retained from the original native report; artifact bytes verified against its saved SHA256.'),indent=2)+'\n')
        artifact=immutable;base[base.index('--artifact')+1]=str(artifact);base+=['--artifact-provenance',str(provenance)]
    else:provenance=Path(base[base.index('--artifact-provenance')+1])
    controls=Path('/tmp/useful-tools-lighting-emission-controls')/(target+'-'+a.suffix)/hashlib.sha256(artifact.read_bytes()).hexdigest();controls.mkdir(parents=True,exist_ok=True)
    control=controls/'base-zero-emission.jar';recipe=controls/'control-pack.zip'
    with control_lock:
        if not control.is_file():
            subprocess.run([sys.executable,str(ROOT/'scripts/build-lighting-emission-control.py'),'--artifact',str(artifact),'--output',str(recipe),'--control-artifact',str(control),'--provenance',str(provenance)],cwd=ROOT,check=True)
    results=[]
    for mode in a.modes:
        name=shader+'-material-'+mode+'-'+target+'-'+a.suffix;destination=PACKAGED/name;command=base.copy();command[command.index('--output')+1]=str(destination)
        if mode=='zero-alpha':
            command[command.index('--artifact')+1]=str(control);command[command.index('--artifact-provenance')+1]=str(control.with_suffix('.json'))
        if (destination/'report.json').is_file():
            report=json.loads((destination/'report.json').read_text());status='previous completed native control' if report['status']=='captures_complete' and report.get('clean_client_exit') and report.get('lighting_material_control',{}).get('acknowledgement') else 'previous failed profile retained; choose new suffix for retry'
            results.append(dict(target=target,shader=shader,mode=mode,profile=name,status=status))
            if status.startswith('previous failed'):return results
            continue
        print('START',name,flush=True)
        with (OUT/(name+'.log')).open('w') as log:
            client=subprocess.Popen([sys.executable,*command],cwd=ROOT,stdout=log,stderr=subprocess.STDOUT,start_new_session=True)
            try:code=wait_client(client)
            except KeyboardInterrupt:
                rows.extend(results)
                rows.append(dict(target=target,shader=shader,mode=mode,profile=name,status='cancelled; acceptance remains incomplete'))
                (OUT/('material-control-batch-'+a.suffix+'.json')).write_text(json.dumps(rows,indent=2)+'\n')
                raise SystemExit(130)
        status=('completed; visual review and material comparison pending' if code==0 else
                'deferred: another client owns admission; no profile created' if code==75 else 'failed')
        results.append(dict(target=target,shader=shader,mode=mode,profile=name,exit_code=code,command=command,original_profile=folder.name,status=status))
        print('END',name,code,flush=True)
        if code:return results
    return results
queue=[(target,shader) for target in a.targets for shader in a.shaders]
for index,(target,shader) in enumerate(queue):
    try:results=run(target,shader);rows.extend(results)
    except Exception as error:
        results=[];rows.append(dict(target=target,shader=shader,status='failed before client launch',error=str(error)));print('PREPARE FAILED',target,shader,error,flush=True)
    busy=any(row.get('exit_code')==75 for row in results)
    if busy:
        rows.extend(dict(target=t,shader=s,status='pending: batch stopped at busy client admission') for t,s in queue[index+1:])
    (OUT/('material-control-batch-'+a.suffix+'.json')).write_text(json.dumps(rows,indent=2)+'\n')
    if busy:raise SystemExit(75)
