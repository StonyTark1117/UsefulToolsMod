#!/usr/bin/env python3
"""Reconcile explicit configuration requirements without dropping failed rows."""
import argparse
import hashlib
import importlib.util
import json
import re
from pathlib import Path

spec=importlib.util.spec_from_file_location('spectral_evidence',Path(__file__).with_name('audit-spectral-evidence.py'))
evidence=importlib.util.module_from_spec(spec);spec.loader.exec_module(evidence)
DURABLE_INPUTS=(Path(__file__).resolve().parents[1]/'validation/shader-support-2.3.2'
                /'recovery/implementation/durable-inputs/restored-inputs')
LEGACY_INPUTS=Path.home()/'.cache/useful-tools-spectral-recovery/restored-inputs'
OCULUS_262_PUBLISHER_SHA256='0592d342653804260aa4c8047102ff3fc9eef2e0a8fb3bec26820f95c49fb227'
BSL_10_1_1_SHA256='131ca6b56b100e60577ffdb0dd51bfab2ee6e334564eeee324e5422026891668'
COMPLEMENTARY_5_7_1_SHA256='24a20634a7832d422d3cd5023be16829f26840e25c69404dd418306ea79f63f0'
OCULUS_262_GBUFFER_PACK_SHA256S={BSL_10_1_1_SHA256,COMPLEMENTARY_5_7_1_SHA256}
OCULUS_262_GBUFFER_BRIDGE_SHA256='f0eb770c263b879e44960ae79c2fd0315c0a181381da20089b0f7428b02b7743'
OCULUS_262_GBUFFER_TRANSFORMS={
    'dev/oculusforge/render/program/LegacyFinalShaderAdapter':('a38310c597c0b03d7e2fec3737ed3f5ceb6db520e388ff11502819ace6fc306d','a53f461cee4134d4d5147d830d17d6259e2ccb6eff1cef326603a9249b81ddba'),
    'dev/oculusforge/render/program/PackProgramCompiler':('c72fbbf4e6f0d320c14600f36241cd9716e57a5876890812ccfee49fae99ac3a','e2d50346be8421804d60965881d0861018db0c7b3db0522c48b98a4d26f64f09'),
    'dev/oculusforge/render/program/PreparedPackPipeline':('c8e6ca7f3c7ee1c2a569c2421445d51885c65cb88a35e00017d05e96f7fd829b','f25ab1fb5959d739c7cde745cab0ad3b1dc5bdcf30dcb11ce6cf3a53fec22cc6'),
    'dev/oculusforge/render/program/PackShaderPipeline':('8d27d0de070039e3ee3f1ba76a057a3d14f76e690a83d6747ec0f93384ef9fa8','45875cdd0a5b91bfa726d0088ab0b712d8c6cb59c289fb8be754a0cf285af5fa'),
    'com/mojang/blaze3d/opengl/GlRenderPass':('7ad9eed06cc24d3a5c8db45a57d9bb6691cf354b07774036316046cb3acc10fa','e2de6aa0f24780fcc8d73c0784d0623773ad5d29dfd5b5c8dd042d2e24cc6ed6')
}

def resolve_input(path,durable_root=DURABLE_INPUTS):
    """Resolve an exact historical input from repository-owned hash-keyed storage."""
    path=Path(path)
    if path.is_file():return path
    candidate=durable_root/path.parent.name/path.name
    if candidate.is_file():return candidate
    legacy=LEGACY_INPUTS/path.parent.name/path.name
    return legacy if legacy.is_file() else path

def native_damage_gaps(report):
    """Require fresh client observation for each synchronized damage capture."""
    checks=report.get('native_damage_checks',[])
    gaps=[]
    for entity in ('ghost','wraith'):
        proof=next((c for c in checks if c.get('entity')==entity),{})
        if (not re.fullmatch(r'[0-9a-f]{32}',str(proof.get('request_token','')))
                or not isinstance(proof.get('time_ms'),int)
                or not isinstance(proof.get('hurt_ticks'),int)
                or not 5<=proof['hurt_ticks']<=10
                or not str(proof.get('entity_class','')).endswith('.'+entity.title()+'Entity')):
            gaps.append('native client hurt-state proof missing or invalid: '+entity)
    return gaps

def community_gbuffer_gaps(directory,report,expected):
    """Require causal proof for the private 26.2 Oculus gbuffer bridge.

    A pack activation or a fullscreen-stage log is deliberately insufficient.
    The current bridge records real native pipeline draws, but its adapted
    vertex/fragment programs contain only pack source hashes as comments. Its
    draw evidence therefore cannot certify that pack gbuffer code rendered.
    """
    proof=report.get('community_gbuffer_proof')
    if not isinstance(proof,dict):return ['Oculus community terrain/entity gbuffer proof missing']
    gaps=[]
    expected_pack=(expected.get('shader_pack') or {}).get('sha256')
    expected_artifacts=set(expected.get('artifacts',{}).values())
    if proof.get('schema')!=1:gaps.append('Oculus community gbuffer proof schema missing or wrong')
    if proof.get('publisher_sha256')!=OCULUS_262_PUBLISHER_SHA256 or OCULUS_262_PUBLISHER_SHA256 not in expected_artifacts:
        gaps.append('Oculus community gbuffer proof publisher hash missing or wrong')
    if (proof.get('shader_pack_sha256')!=expected_pack
            or expected_pack not in OCULUS_262_GBUFFER_PACK_SHA256S):
        gaps.append('Oculus community gbuffer proof shader-pack hash missing or wrong')
    run_token=proof.get('run_token')
    if not re.fullmatch(r'[0-9a-f]{32}',str(run_token or '')) or report.get('run_id')!=run_token:
        gaps.append('Oculus community gbuffer proof run token missing, wrong, or stale')
    for field in ('bridge','transform_log','event_log'):
        binding=proof.get(field)
        if not isinstance(binding,dict):
            gaps.append('Oculus community gbuffer '+field.replace('_',' ')+' binding missing')
            continue
        path=Path(str(binding.get('path','')))
        digest=str(binding.get('sha256',''))
        if not path.is_file() or not re.fullmatch(r'[0-9a-f]{64}',digest) or hashlib.sha256(path.read_bytes()).hexdigest()!=digest:
            gaps.append('Oculus community gbuffer '+field.replace('_',' ')+' missing or changed')
    bridge_binding=proof.get('bridge',{})
    if isinstance(bridge_binding,dict) and bridge_binding.get('sha256')!=OCULUS_262_GBUFFER_BRIDGE_SHA256:
        gaps.append('Oculus community gbuffer bridge is not the audited deterministic build')
    if isinstance(bridge_binding,dict) and bridge_binding.get('sha256')==OCULUS_262_GBUFFER_BRIDGE_SHA256:
        gaps.append('Oculus community terrain/entity pack gbuffer sources were hashed but not compiled into the rendered pipelines')
    transform_binding=proof.get('transform_log',{})
    transform_path=Path(str(transform_binding.get('path',''))) if isinstance(transform_binding,dict) else Path()
    if transform_path.is_file():
        transform_text=transform_path.read_text(errors='replace')
        ready=r'^OCULUS_262_GBUFFER_BRIDGE ready targets=5 run_token='+re.escape(str(run_token))+r' event_log='
        if not re.search(ready,transform_text,re.M):
            gaps.append('Oculus community gbuffer transform log run binding missing or stale')
        if 'OCULUS_262_GBUFFER_BRIDGE failure=' in transform_text:
            gaps.append('Oculus community gbuffer transform failure was recorded')
        for name,(before,after) in OCULUS_262_GBUFFER_TRANSFORMS.items():
            line='OCULUS_262_GBUFFER_BRIDGE transformed='+name+' before='+before+' after='+after
            if transform_text.splitlines().count(line)!=1:
                gaps.append('Oculus community exact gbuffer transform missing or duplicated: '+name)
    event_binding=proof.get('event_log',{})
    event_path=Path(str(event_binding.get('path',''))) if isinstance(event_binding,dict) else Path()
    events=[]
    if event_path.is_file():
        try:
            events=[json.loads(line) for line in event_path.read_text().splitlines() if line.strip()]
        except (OSError,ValueError,TypeError):
            gaps.append('Oculus community gbuffer event log is not valid JSON lines')
    exact_events=proof.get('events')
    if not isinstance(exact_events,list) or events!=exact_events:
        gaps.append('Oculus community gbuffer embedded events missing, changed, or reordered')
        exact_events=[]
    allowed_pipelines={
        'terrain':re.compile(r'^minecraft:pipeline/(?:solid|cutout|translucent)_terrain$'),
        'entity':re.compile(r'^minecraft:pipeline/(?:entity|armor)_[a-z0-9_]+$')
    }
    for category,pattern in allowed_pipelines.items():
        category_events=[(i,e) for i,e in enumerate(exact_events)
                         if isinstance(e,dict) and e.get('category')==category and e.get('run_token')==run_token]
        prepared_pair=next(((i,e) for i,e in category_events if e.get('kind')=='prepared'
                            and re.fullmatch(r'[0-9a-f]{64}',str(e.get('program_sha256','')))),None)
        prepared=prepared_pair[1] if prepared_pair else None
        if not prepared or not all(re.fullmatch(r'[0-9a-f]{64}',str(prepared.get(k,''))) for k in
                                   ('pack_vertex_sha256','pack_fragment_sha256','adapted_vertex_sha256','adapted_fragment_sha256')):
            gaps.append('Oculus community '+category+' gbuffer preparation proof missing or malformed')
        prepared_index=prepared_pair[0] if prepared_pair else -1
        compiled_pair=next(((i,e) for i,e in category_events if i>prepared_index and e.get('kind')=='compiled'
                            and pattern.fullmatch(str(e.get('pipeline',''))) and e.get('valid') is True and prepared
                            and e.get('program_sha256')==prepared.get('program_sha256')),None)
        compiled=compiled_pair[1] if compiled_pair else None
        if not compiled:gaps.append('Oculus community '+category+' gbuffer OpenGL compilation proof missing or malformed')
        compiled_index=compiled_pair[0] if compiled_pair else -1
        bound_pair=next(((i,e) for i,e in category_events if i>compiled_index and e.get('kind')=='bound'
                         and pattern.fullmatch(str(e.get('pipeline',''))) and compiled
                         and e.get('program_sha256')==compiled.get('program_sha256')),None)
        bound=bound_pair[1] if bound_pair else None
        if not bound:gaps.append('Oculus community '+category+' gbuffer render-pass binding proof missing or malformed')
        bound_index=bound_pair[0] if bound_pair else -1
        rendered=next((e for i,e in category_events if i>bound_index and e.get('kind')=='rendered'
                       and pattern.fullmatch(str(e.get('pipeline',''))) and bound
                       and e.get('program_sha256')==bound.get('program_sha256')
                       and isinstance(e.get('draw_count'),int) and e['draw_count']>0),None)
        if not rendered:gaps.append('Oculus community '+category+' gbuffer draw proof missing or malformed')
    return gaps

def requirements(row):
    command=row['command']
    def value(flag):return command[command.index(flag)+1]
    classic=value('--minecraft-version')=='1.7.10'
    mode=value('--mode')
    files=[Path(value('--artifact'))]+[Path(command[i+1]) for i,x in enumerate(command) if x=='--client-mod']
    captures=[entity+'-'+scene+'.png' for entity in ('ghost','wraith') for scene in
              ('day','night','rain','dark-background','bright-background','indoor')]
    if '--entity-states' in command:
        states=('movement','damage','invisible') if classic else ('movement-before','movement-after','damage','invisible','spectator-invisible','outline')
        captures += [entity+'-'+state+'.png' for entity in ('ghost','wraith') for state in states]
    if '--surfaces' in command:
        captures += ['surfaces-blocks-'+state+'.png' for state in (('night','lit','day') if classic else ('night-unlit','night-lit','day'))]
        captures += ['surfaces-'+state+'.png' for state in ('worn-armor-and-glint','inventory-previews','projectiles','infuser-gui')]
    toggles='--toggle-shaders' in command
    if toggles:captures += [entity+'-shaders-'+state+'.png' for entity in ('ghost','wraith') for state in ('off','restored')]
    expected=dict(loader_uid=value('--loader-uid'),loader_version=value('--loader-version'),mode=mode,
                  artifacts={str(f.resolve()):hashlib.sha256(resolve_input(f).read_bytes()).hexdigest() for f in files},
                  captures=captures,toggles=toggles,full_framebuffer=True,per_capture_review=True)
    if '--artifact-provenance' in command:
        expected['source_sha256']=json.loads(resolve_input(Path(value('--artifact-provenance'))).read_text())['source_sha256']
    if mode=='active':
        pack=Path(value('--shader-pack'));expected['shader_pack']={'name':pack.name,'sha256':hashlib.sha256(resolve_input(pack).read_bytes()).hexdigest()}
    return Path(value('--output')),expected

def audit_matrix(rows,targets):
    results=[]
    for row in rows:
        gaps=[]
        if 'command' not in row:gaps.append(row.get('reason','unresolved configuration'))
        else:
            try:
                directory,expected=requirements(row)
                gaps=evidence.audit(directory,expected)
                command=row['command']
                integration=command[command.index('--shader-integration')+1]
                if (directory/'report.json').exists():
                    report=json.loads((directory/'report.json').read_text())
                    if row['target']=='26.2-forge' and integration=='community' and expected['mode']=='active':
                        gaps.extend(community_gbuffer_gaps(directory,report,expected))
                    if '--native-damage-probe' in command:gaps.extend(native_damage_gaps(report))
                    if not report.get('shutdown_acknowledgement'):gaps.append('native shutdown acknowledgement missing')
                    if report.get('runtime_loader_pin_acknowledged')!=expected['loader_version']:gaps.append('native runtime loader acknowledgement missing or wrong')
                    if 'ghost-rain.png' in expected['captures']:
                        fixture=report.get('precipitation_fixture',{})
                        if fixture.get('biome')!='minecraft:plains' or fixture.get('acknowledged') is not True:
                            gaps.append('precipitation-capable scene biome not verified')
                    if row['target']=='1.7.10-forge' and '--entity-states' in row['command']:
                        native=report.get('classic_native_state_api_evidence',{})
                        path=Path(native.get('path',''))
                        if not path.is_file() or hashlib.sha256(path.read_bytes()).hexdigest()!=native.get('sha256'):
                            gaps.append('classic native outline/spectator API evidence missing or changed')
                        dispositions=report.get('classic_native_state_dispositions',{})
                        if not all(dispositions.get(state) for state in ('outline','spectator')):
                            gaps.append('classic outline/spectator dispositions missing')
                    if '--surfaces' in row['command']:
                        required_blocks={'ectoplasm_lantern','soul_lantern','mining_charge','spectral_infuser','ectoplasm_block','refined_ectoplasm_block','hglow_block'}
                        if not required_blocks.issubset(set(report.get('surface_registry_fixture',[]))):
                            gaps.append('required block/lantern registry surface coverage missing')
                        inventory=report.get('inventory_preview_fixture',{})
                        if inventory.get('native_survival_inventory') is not True or inventory.get('armor_slots')!=4:
                            gaps.append('native equipped armor inventory preview not verified')
                        glint=report.get('glint_fixture',{})
                        if row['target']!='1.7.10-forge':
                            import re
                            log=(directory/'client.log').read_text(errors='replace')
                            if ('cannot support that enchantment' in log
                                    or not (glint.get('native_serialization_acknowledged') is True
                                            or re.search(r'\[CHAT\].*(?:Enchanted |Applied enchantment )',log))):
                                gaps.append('native enchantment glint fixture not verified')
                    if not any('kill ' in c.get('command','') or 'spectralqa remove' in c.get('command','') for c in report.get('commands',[])):gaps.append('entity removal acknowledgement missing')
            except (OSError,KeyError,ValueError) as error:gaps.append('requirements unavailable: '+str(error))
        results.append({'name':row['name'],'target':row['target'],'complete':not gaps,'gaps':gaps})
    present={r['target'] for r in rows}
    missing=sorted(set(targets)-present)
    return {'complete':not missing and bool(results) and all(r['complete'] for r in results),
            'missing_targets':missing,'unexpected_targets':sorted(present-set(targets)),
            'required_targets':len(targets),'configurations':results}

if __name__=='__main__':
    parser=argparse.ArgumentParser(description=__doc__)
    parser.add_argument('manifest',type=Path);parser.add_argument('acceptance_matrix',type=Path);parser.add_argument('--output',type=Path,required=True)
    args=parser.parse_args()
    result=audit_matrix(json.loads(args.manifest.read_text()),[r['target'] for r in json.loads(args.acceptance_matrix.read_text())['rows']])
    args.output.write_text(json.dumps(result,indent=2)+'\n')
    print(json.dumps({'complete':result['complete'],'required_targets':result['required_targets'],'missing_targets':result['missing_targets'],'complete_configurations':sum(r['complete'] for r in result['configurations']),'total_configurations':len(result['configurations'])}))
    raise SystemExit(not result['complete'])
