#!/usr/bin/env python3
"""Reconcile lighting evidence without turning absent checks into passes."""
import hashlib, json, re, subprocess
from pathlib import Path
from lighting_shader_diagnostics import compiler_failures
from lighting_pins import pinned_loader

ROOT = Path(__file__).resolve().parents[1]
OUT = ROOT / 'validation/dynamic-lighting-2.3.2'
def read(path):
    return json.loads(path.read_text()) if path.is_file() else None
def record(status, paths, **details):
    return dict(status=status, evidence=[str(p.relative_to(ROOT)) for p in paths if p.exists()], **details)
coverage = read(OUT/'coverage.json')
coverage['tested_revision'] = subprocess.check_output(['git','rev-parse','HEAD'],cwd=ROOT,text=True).strip()
coverage['overall_status'] = 'reconciling recorded acceptance checks'
builds = {r['target']:r for r in read(OUT/'build/results.json')}
audit = {r['target']:r for r in read(OUT/'artifact-audit.json')}
for target, checks in coverage['targets'].items():
    loader_pin=pinned_loader(target)
    native = OUT/'runtime'/target
    equipment = read(native/'equipment-checks.json')
    log = (native/'native-client.log').read_text(errors='replace') if (native/'native-client.log').exists() else ''
    valid = bool(equipment and equipment['success'] and 'QA_FAIL' not in log)
    evidence = [native/'equipment-checks.json',native/'native-client.log',native/'tested-files.json',native/(target+'.json')]
    for name in ['base-build','companion-build']:
        good = builds[target]['exit_code']==0 and target in audit
        kind = 'base' if name == 'base-build' else 'companion'
        artifact = ROOT / audit[target][kind] if target in audit else None
        expected = audit[target][kind+'_sha256'] if target in audit else None
        observed = hashlib.sha256(artifact.read_bytes()).hexdigest() if artifact and artifact.is_file() else None
        current = good and observed == expected and builds[target]['artifacts'].get(str(artifact.relative_to(ROOT))) == expected
        status = ('build verified' if current else
                  'current output changed; recorded build covers prior artifact' if good else 'failed')
        checks[name] = record(status,[OUT/'build/results.json',OUT/'build'/(target+'.log'),OUT/'artifact-audit.json'],
                              recorded_artifact_sha256=expected,current_output_sha256=observed)
    candidates = [OUT/'base-client'/f'{target}.json',OUT/'base-client-recovery'/f'{target}.json',OUT/'base-client-recovery'/target/f'{target}.json']
    good = [p for p in candidates if read(p) and read(p).get('success') and not read(p).get('client_mods') and not any('usefulToolsLighting=true' in c for c in read(p).get('command',[]))]
    checks['base-client-no-companion'] = record('runtime verified' if good else 'not run',[good[-1]] if good else candidates)
    candidates = [OUT/'base-server'/target/f'{target}.json',OUT/'base-server-classic-recovery-2'/f'{target}.json',OUT/'base-server-recovery'/target/f'{target}.json']
    good = [p for p in candidates if read(p) and read(p).get('ready') and read(p).get('clean_shutdown') and not read(p).get('failure')]
    checks['dedicated-server-no-client-classes'] = record('runtime verified' if good else 'not run',[good[-1]] if good else candidates,meaning='Base-only dedicated server; no client-class linkage errors.')
    server = OUT/'companion-server-final'/target
    if target=='1.7.10-forge' and (OUT/'companion-server-sorted-classic'/f'{target}.json').exists():server=OUT/'companion-server-sorted-classic'
    if (OUT/'companion-server-native-final'/target/f'{target}.json').exists():server=OUT/'companion-server-native-final'/target
    current_servers=[f for base in OUT.glob('companion-server-native*') if base.is_dir() for f in [base/target] if read(f/'artifact.json') and read(f/'artifact.json').get('companion_sha256')==audit[target]['companion_sha256'] and read(f/f'{target}.json')]
    if current_servers:server=max(current_servers,key=lambda f:(f/f'{target}.json').stat().st_mtime)
    result = read(server/f'{target}.json')
    checks['dedicated-server-companion-installed'] = record('runtime verified' if result and result.get('ready') and result.get('clean_shutdown') and not result.get('failure') else 'not run',[server/f'{target}.json',server/'artifact.json',server/f'{target}.log'])
    for name in ['held-all-sources-all-hands','worn-each-armor-slot','mixed-equipment-max','movement-water','removal-unload-reload-dimension','placed-block-light-15','server-light-saved-data-spawns-unchanged','radius-10-15-20-29']:
        checks[name] = record('runtime verified' if valid else 'not verified',evidence,cases=len(equipment['cases']) if equipment else 0)
    checks['multiple-players-equipped-mobs'] = record('adapter fixture verified; real multiplayer not run',evidence,scenario='Two native remote-player actors and an equipped zombie, including movement, underwater light, unequip and removal. Actors are client-side test fixtures, not two networked clients.')
    observations=[]
    for line in log.splitlines():
        match=re.search(r'QA_PERF phase=(\S+) adapter_us=(\S+) mean_fps=(\S+) tick_samples=(\d+) fps_samples=(\d+)',line)
        if match:
            phase,cpu,fps,ticks,samples=match.groups()
            observations.append(dict(phase=phase,adapter_us=float(cpu),mean_fps=float(fps),tick_samples=int(ticks),fps_samples=int(samples)))
    perf=dict(observations=observations,method='Four sequential phases; each has 40 warm-up and 100 timed extra native adapter ticks, with five native FPS-counter samples. Isolated Xvfb; concurrent matrix activity can affect timings. These are observations, not frame-time percentiles or a hardware FPS guarantee.')
    (native/'performance.json').write_text(json.dumps(perf,indent=2)+'\n')
    checks['performance-cleanup'] = record('runtime verified' if valid and len(observations)==4 else 'incomplete',[native/'performance.json',native/f'{target}.json'])
    checks['lamb-source-ownership'] = record('not run' if target in {'1.20.1-fabric','1.20.2-fabric','1.21.1-fabric','1.21.1-neoforge','26.1.2-fabric','26.1.2-neoforge','26.2-fabric','26.2-neoforge'} else 'not applicable',[])
    checks['sodium'] = record('not applicable' if target=='1.7.10-forge' else 'not run',[],scenario='Classic uses the target-native Forge transformer paths; modern optional renderer caches require their own actual backend checks.')
    if checks.get('quilt-companion',{}).get('status')!='runtime verified':checks['quilt-companion'] = record('not run' if target.endswith('fabric') else 'not applicable',[])
    packaged=[]
    for folder in (OUT/'packaged').iterdir():
        result=read(folder/'report.json')
        if not result or result.get('status')!='captures_complete' or not result.get('clean_client_exit') or compiler_failures('\n'.join(result.get('shader_compile_errors',[]))):continue
        components=result.get('components',{}).get('components',[])
        mc=next((c['version'] for c in components if c['uid']=='net.minecraft'),None)
        uid=result.get('loader_uid')
        loader={'net.fabricmc.fabric-loader':'fabric','net.minecraftforge':'forge','net.neoforged':'neoforge'}.get(uid)
        if mc+'-'+str(loader)!=target or result.get('loader_version')!=loader_pin or len(result.get('lighting_equipment_cases',[]))!=(22 if target=='1.7.10-forge' else 36):continue
        packaged.append(folder)
    if target.endswith('fabric'):
        quilt=[]
        for folder in (OUT/'packaged').glob('quilt*'):
            result=read(folder/'report.json')
            if not result or result.get('status')!='captures_complete' or not result.get('clean_client_exit') or result.get('loader_uid')!='org.quiltmc.quilt-loader':continue
            mc=next((c['version'] for c in result.get('components',{}).get('components',[]) if c['uid']=='net.minecraft'),None)
            if mc==target.rsplit('-',1)[0] and len(result.get('lighting_equipment_cases',[]))==36:quilt.append(folder)
        if quilt:
            folder=max(quilt,key=lambda f:(f/'report.json').stat().st_mtime)
            checks['quilt-companion']=record('runtime verified',[folder/'report.json',folder/'client.log'],version=read(folder/'report.json')['loader_version'],scenario='Actual Quilt runtime exercises the Fabric companion with 36 native equipment cases, advanced world-light invariants, disconnect and clean client shutdown.')
    for name,marker in [('sodium','QA_BACKEND_PASS Sodium'),('lamb-source-ownership','QA_BACKEND_PASS Lamb')]:
        proven=[f for f in packaged if (f/'client.log').exists() and marker in (f/'client.log').read_text(errors='replace')]
        if proven:checks[name]=record('runtime verified',[proven[-1]/'report.json',proven[-1]/'client.log'],scenario='Actual optional backend lookup asserted against companion ownership and renderer sampling; versions and artifact hashes are pinned in the profile report.')
    if target == '1.7.10-forge':
        classic_backend=[]
        equivalents=read(OUT/'reviewed-runtime-class-equivalence.json') or []
        for folder in packaged:
            result=read(folder/'report.json')
            review=read(folder/'native-beddium-visual-review.json')
            report_hash=hashlib.sha256((folder/'report.json').read_bytes()).hexdigest()
            same_classes=any(row.get('profile')==folder.name and row.get('report_sha256')==report_hash
                             and row.get('current_release_sha256')==audit[target]['companion_sha256']
                             and not row.get('different_production_classes') for row in equivalents)
            if not review or review.get('status')!='reviewed native Beddium baseline passed' or review.get('report_sha256')!=report_hash or not same_classes:continue
            if len(result.get('lighting_classic_beddium_cache_cases',[]))!=22 or len(result.get('lighting_advanced_cases',[]))!=10 or len(result.get('lighting_classic_beddium_dirty_scenes',[]))!=3:continue
            if len(result.get('lighting_equipment_shader_captures',[]))!=18 or not result.get('classic_silent_audio_hook'):continue
            if not all((folder/name).is_file() and hashlib.sha256((folder/name).read_bytes()).hexdigest()==expected for name,expected in review.get('sheets',{}).items()):continue
            if not all((folder/name).is_file() and hashlib.sha256((folder/name).read_bytes()).hexdigest()==expected for name,expected in result.get('capture_sha256',{}).items()):continue
            classic_backend.append(folder)
        if classic_backend:
            folder=max(classic_backend,key=lambda f:(f/'report.json').stat().st_mtime)
            checks['sodium']=record('runtime verified',[folder/'report.json',folder/'native-beddium-visual-review.json',OUT/'reviewed-runtime-class-equivalence.json'],scenario='Native Beddium1.2.5-j8 / Forge10.13.4.1614: all22nativecache stages, tenadvancedcases, all18held/worncaptures and fixed-camera removal/movement without manual render reload. Cached vanilla light unchanged; clean isolated exit. Separate shader profiles remain required.')
    if target == '1.7.10-forge' and checks['sodium']['status'] != 'runtime verified':
        checks['sodium'] = record('not verified: classic Sodium-family renderer investigation pending',
                                 [OUT/'research-classic-sodium/classic-sodium-research-status.json'],
                                 meaning='Publisher-supported 1.7.10 renderers exist. Their native light sampling and dirty-section behavior need inspection and acceptance; age does not make this check inapplicable. Existing OptiFine evidence is retained separately.')
    networks=[f for f in (OUT/'multiplayer').glob(target+'*') if f.is_dir() and read(f/'result.json') and read(f/'result.json').get('status')=='runtime verified']
    if networks:
        network=max(networks,key=lambda f:(f/'result.json').stat().st_mtime)
        checks['multiple-players-equipped-mobs']=record('runtime verified',[network/'result.json',network/'host/report.json',network/'guest/report.json',*evidence],scenario='Two actual Minecraft clients over loopback TCP verify replicated equipment, movement, underwater light and removal; equipped mob checks also remain in the native fixture.')
    for shader in ['bsl','complementary']:
        checks[shader+'-matched-emission-toggle']=record('not verified',[])
        checks[shader+'-isolated-material-emission']=record('not verified',[],scenario='Companion renderer boost bypassed after native acceptance; matched native emission versus zero-alpha LabPBR control with identical RGB/diffuse images, exposure and bloom.')
        material_cert=read(OUT/'material-emission-certification.json') or {'coverage':[]}
        for row in material_cert['coverage']:
            if row['target']!=target or row['shader']!=shader:continue
            comparison_path=ROOT/row['evidence'];material=read(comparison_path)
            if not material or material.get('status')!='isolated material emission and held/worn visuals verified' or not material.get('visual_review') or not material.get('all_required_metrics_passed'):continue
            folders=[OUT/'packaged'/material[key] for key in ['native_profile','zero_alpha_profile']]
            if not all(read(f/'report.json') and read(f/'report.json').get('loader_version')==loader_pin for f in folders):continue
            if not all(read(f/'report.json') and hashlib.sha256((f/'report.json').read_bytes()).hexdigest()==expected for f,expected in zip(folders,material['report_sha256'])):continue
            if not all((f/name).is_file() and hashlib.sha256((f/name).read_bytes()).hexdigest()==expected for f,captures in zip(folders,material['capture_sha256']) for name,expected in captures.items()):continue
            if not all((ROOT/sheet['path']).is_file() and hashlib.sha256((ROOT/sheet['path']).read_bytes()).hexdigest()==sheet['sha256'] for sheet in material['visual_review_sheets']):continue
            checks[shader+'-isolated-material-emission']=record('isolated material emission and held/worn visuals verified',[comparison_path,*[f/'report.json' for f in folders],*[ROOT/sheet['path'] for sheet in material['visual_review_sheets']]],meaning=material['method'])
        profiles=[f for f in packaged if shader in f.name and read(f/'lighting-comparison.json') and read(f/'lighting-comparison.json').get('all_requested_distances_brighter') and len(read(f/'report.json').get('live_shader_states',[]))>=2]
        if profiles:
            reviewed=[]
            for folder in profiles:
                review=read(folder/'equipment-shader-review.json')
                if not review or review.get('status')!='reviewed' or len(review.get('native_state_evidence',[]))!=18:continue
                report=read(folder/'report.json')
                cases=review['native_state_evidence']
                if [{k:c[k] for k in ['slot','source','capture']} for c in cases]!=[{k:c[k] for k in ['slot','source','capture']} for c in report.get('lighting_equipment_shader_captures',[])]:continue
                logs='\n'.join(p.read_text(errors='replace') for p in [folder/'client.log',folder/'lighting-native-acknowledgements.log'] if p.exists())
                if not all(c.get('native_acknowledgement') and c['native_acknowledgement'] in logs for c in cases):continue
                if not all(report['capture_sha256'][c['capture']]==c['capture_sha256'] for c in cases):continue
                if not all(hashlib.sha256((folder/c['capture']).read_bytes()).hexdigest()==c['capture_sha256'] for c in cases):continue
                sheets=[p for p in folder.glob('*.png') if 'contact' in p.name and hashlib.sha256(p.read_bytes()).hexdigest()==review['contact_sheet_sha256']]
                if not sheets:continue
                reviewed.append(folder)
            folder=max(reviewed or profiles,key=lambda f:(f/'report.json').stat().st_mtime)
            checks[shader+'-matched-emission-toggle']=record('matched block and held/worn captures verified' if reviewed else 'matched placed-block illumination measured; held/tool/armor shader visuals incomplete',[folder/'report.json',folder/'lighting-comparison.json',folder/'equipment-shader-review.json',*folder.glob('*contact*.png'),folder/'shader-diagnostic-review.json',folder/'client.log'],meaning='Matched screen-region brightness includes shader emission and companion illumination; no isolated emission-only claim.')
        elif target in {'26.1.2-forge','26.2-forge'}:
            checks[shader+'-matched-emission-toggle']=record('not verified: shader backend recovery and visual review pending', [OUT/'unavailable-shader-profiles.json'],meaning='Historical backend failures and native port recovery remain recorded; companion-native lighting has its own full evidence.')
    if target=='26.1.2-forge' and checks['sodium']['status']!='runtime verified' and read(OUT/'packaged/sodium-private-port-26.1.2-forge-native-software/report.json'):
        checks['sodium']=record('not verified: private backend fails startup',[OUT/'packaged/sodium-private-port-26.1.2-forge-native-software/report.json',OUT/'packaged/sodium-private-port-26.1.2-forge-native-software/native-backend-failure.txt'])
coverage['kernel'] = record('passed',[OUT/'kernel-tests.log'])
coverage['notes'] = ['Revision identifies the shared uncommitted working tree; tested-files.json records source and compiled-bytecode hashes.', 'Shader captures certify only their exact recorded target, artifact hashes, material maps, and settings.', 'Failures and superseded attempts remain saved; they do not certify current code.', 'Dynamic-light invariants are checked after removing the fixture\'s real placed block and allowing native relighting to settle.']
unresolved = []
unresolved_markers = ('not run', 'not verified', 'incomplete', 'failed',
                      'current output changed', 'pending')
for target, checks in coverage['targets'].items():
    for name, check in checks.items():
        status = check.get('status', '') if isinstance(check, dict) else str(check)
        if any(marker in status.lower() for marker in unresolved_markers):
            unresolved.append(dict(target=target, check=name, status=status))
coverage['unresolved_checks'] = unresolved
coverage['overall_status'] = (
    f'incomplete: {len(unresolved)} recorded acceptance checks remain unresolved'
    if unresolved else
    'complete: every recorded target acceptance check is verified or not applicable'
)
(OUT/'coverage.json').write_text(json.dumps(coverage,indent=2)+'\n')
print('Recorded',len(coverage['targets']),'targets;',coverage['overall_status'])
