import hashlib
import importlib.util
import json
from pathlib import Path
import tempfile
import unittest
from unittest.mock import patch

SPEC = importlib.util.spec_from_file_location('spectral_evidence', Path(__file__).resolve().parents[1] / 'scripts/audit-spectral-evidence.py')
evidence = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(evidence)
MATRIX_SPEC = importlib.util.spec_from_file_location('spectral_matrix', Path(__file__).resolve().parents[1] / 'scripts/audit-spectral-matrix.py')
matrix = importlib.util.module_from_spec(MATRIX_SPEC)
MATRIX_SPEC.loader.exec_module(matrix)


class EvidenceTests(unittest.TestCase):
    def test_remaining_exact_shader_configurations_stay_in_acceptance_matrix(self):
        path = Path(__file__).resolve().parents[1] / 'validation/shader-support-2.3.2/recovery/acceptance-matrix.json'
        rows = {row['target']: row for row in json.loads(path.read_text())['rows']}
        expected = {
            '1.20.2-neoforge': [
                ('Oculus 1.8.0/Zenkai', 'none (installed, disabled)'),
                ('Oculus 1.8.0/Zenkai', 'BSL 10.1.2'),
                ('Oculus 1.8.0/Zenkai', 'Complementary Reimagined 5.9.3'),
                ('OptiFine I7 pre1', 'none (installed, disabled)'),
                ('OptiFine I7 pre1', 'BSL 10.1.2'),
                ('OptiFine I7 pre1', 'Complementary Reimagined 5.9.3'),
            ],
            '26.1.2-forge': [
                ('OptiFine K1 pre2', 'none (installed, disabled)'),
                ('OptiFine K1 pre2', 'BSL 10.1.2'),
                ('OptiFine K1 pre2', 'Complementary Reimagined 5.7.1'),
            ],
            '26.2-forge': [
                ('OptiFine K2 pre1', 'none (installed, disabled)'),
                ('OptiFine K2 pre1', 'BSL 10.1.2'),
                ('OptiFine K2 pre1', 'Complementary Reimagined 5.7.1'),
                ('Oculus Community Port 0.3.0-beta.1', 'BSL 10.1.1'),
                ('Oculus Community Port 0.3.0-beta.1', 'Complementary 5.7.1'),
            ],
            '1.20.2-forge': [('OptiFine I7 pre1', 'Complementary Reimagined 5.9.3')],
            '1.21.1-forge': [('OptiFine J1', 'Complementary Reimagined')],
        }
        self.assertEqual(16, sum(map(len, expected.values())))
        for target, configurations in expected.items():
            checks = rows[target]['stack_checks']
            for integration, pack in configurations:
                matches = [check for check in checks
                           if check['integration'].startswith(integration) and check['pack'].startswith(pack)]
                self.assertEqual(1, len(matches), (target, integration, pack))
                self.assertEqual('incomplete', matches[0]['status'])

    def test_community_gbuffer_proof_requires_hash_bound_compile_bind_and_draw_events(self):
        with tempfile.TemporaryDirectory() as work:
            root=Path(work);token='1'*32
            bridge=root/'bridge.jar';bridge.write_bytes(b'exact bridge')
            transform=root/'transform.log';transform.write_text(
                'OCULUS_262_GBUFFER_BRIDGE ready targets=5 run_token='+token+' event_log='+str(root/'events.jsonl')+'\n'
                +''.join('OCULUS_262_GBUFFER_BRIDGE transformed='+name+' before='+before+' after='+after+'\n'
                         for name,(before,after) in matrix.OCULUS_262_GBUFFER_TRANSFORMS.items()))
            def digest(path):return hashlib.sha256(path.read_bytes()).hexdigest()
            events=[]
            for category,pipeline in (('terrain','minecraft:pipeline/solid_terrain'),('entity','minecraft:pipeline/entity_translucent')):
                common={'category':category,'run_token':token}
                events.extend([
                    {**common,'kind':'prepared','pack_vertex_sha256':'a'*64,'pack_fragment_sha256':'b'*64,
                     'adapted_vertex_sha256':'c'*64,'adapted_fragment_sha256':'d'*64,'program_sha256':'e'*64},
                    {**common,'kind':'compiled','pipeline':pipeline,'valid':True,'program_sha256':'e'*64},
                    {**common,'kind':'bound','pipeline':pipeline,'program_sha256':'e'*64},
                    {**common,'kind':'rendered','pipeline':pipeline,'program_sha256':'e'*64,'draw_count':1}
                ])
            event_log=root/'events.jsonl';event_log.write_text(''.join(json.dumps(e,separators=(',',':'))+'\n' for e in events))
            proof={'schema':1,'run_token':token,'publisher_sha256':matrix.OCULUS_262_PUBLISHER_SHA256,
                   'shader_pack_sha256':matrix.BSL_10_1_1_SHA256,'events':events,
                   'bridge':{'path':str(bridge),'sha256':digest(bridge)},
                   'transform_log':{'path':str(transform),'sha256':digest(transform)},
                   'event_log':{'path':str(event_log),'sha256':digest(event_log)}}
            report={'run_id':token,'community_gbuffer_proof':proof}
            expected={'artifacts':{'oculus.jar':matrix.OCULUS_262_PUBLISHER_SHA256},
                      'shader_pack':{'sha256':matrix.BSL_10_1_1_SHA256}}
            with patch.object(matrix,'OCULUS_262_GBUFFER_BRIDGE_SHA256',digest(bridge)):
                self.assertEqual(
                    ['Oculus community terrain/entity pack gbuffer sources were hashed but not compiled into the rendered pipelines'],
                    matrix.community_gbuffer_gaps(root,report,expected))

            original_transform=transform.read_text()
            transform.write_text(original_transform+'OCULUS_262_GBUFFER_BRIDGE failure=com/mojang/blaze3d/opengl/GlRenderPass reason=forged\n')
            proof['transform_log']['sha256']=digest(transform)
            with patch.object(matrix,'OCULUS_262_GBUFFER_BRIDGE_SHA256',digest(bridge)):
                self.assertIn('Oculus community gbuffer transform failure was recorded',
                              matrix.community_gbuffer_gaps(root,report,expected))
            transform.write_text(original_transform)
            proof['transform_log']['sha256']=digest(transform)

            reordered=[events[0],events[1],events[3],events[2],*events[4:]]
            event_log.write_text(''.join(json.dumps(e,separators=(',',':'))+'\n' for e in reordered))
            proof['events']=reordered
            proof['event_log']['sha256']=digest(event_log)
            with patch.object(matrix,'OCULUS_262_GBUFFER_BRIDGE_SHA256',digest(bridge)):
                self.assertIn('Oculus community terrain gbuffer draw proof missing or malformed',
                              matrix.community_gbuffer_gaps(root,report,expected))

            proof['events']=events
            event_log.write_text(''.join(json.dumps(e,separators=(',',':'))+'\n' for e in events))
            proof['event_log']['sha256']=digest(event_log)
            event_log.write_text(event_log.read_text()+'{}\n')
            with patch.object(matrix,'OCULUS_262_GBUFFER_BRIDGE_SHA256',digest(bridge)):
                self.assertIn('Oculus community gbuffer event log missing or changed',matrix.community_gbuffer_gaps(root,report,expected))

    def test_community_gbuffer_proof_accepts_exact_complementary_hash_only(self):
        with tempfile.TemporaryDirectory() as temporary:
            root=Path(temporary);token='4'*32
            bridge=root/'bridge.jar';bridge.write_bytes(b'bridge')
            transform=root/'transform.log'
            event_log=root/'events.jsonl'
            events=[]
            for category,pipeline,program in (('terrain','minecraft:pipeline/solid_terrain','1'*64),
                                               ('entity','minecraft:pipeline/entity_solid','2'*64)):
                events.extend([
                    {'kind':'prepared','category':category,'run_token':token,'program_sha256':program,
                     'pack_vertex_sha256':'a'*64,'pack_fragment_sha256':'b'*64,
                     'adapted_vertex_sha256':'c'*64,'adapted_fragment_sha256':'d'*64},
                    {'kind':'compiled','category':category,'run_token':token,'pipeline':pipeline,
                     'valid':True,'program_sha256':program},
                    {'kind':'bound','category':category,'run_token':token,'pipeline':pipeline,
                     'program_sha256':program},
                    {'kind':'rendered','category':category,'run_token':token,'pipeline':pipeline,
                     'program_sha256':program,'draw_count':1},
                ])
            event_log.write_text(''.join(json.dumps(event)+'\n' for event in events))
            transform.write_text(
                'OCULUS_262_GBUFFER_BRIDGE ready targets=5 run_token='+token+' event_log='+str(event_log)+'\n'+
                ''.join('OCULUS_262_GBUFFER_BRIDGE transformed='+name+' before='+before+' after='+after+'\n'
                        for name,(before,after) in matrix.OCULUS_262_GBUFFER_TRANSFORMS.items()))
            proof={'schema':1,'run_token':token,'publisher_sha256':matrix.OCULUS_262_PUBLISHER_SHA256,
                   'shader_pack_sha256':matrix.COMPLEMENTARY_5_7_1_SHA256,'events':events,
                   'bridge':{'path':str(bridge),'sha256':hashlib.sha256(bridge.read_bytes()).hexdigest()},
                   'transform_log':{'path':str(transform),'sha256':hashlib.sha256(transform.read_bytes()).hexdigest()},
                   'event_log':{'path':str(event_log),'sha256':hashlib.sha256(event_log.read_bytes()).hexdigest()}}
            report={'run_id':token,'community_gbuffer_proof':proof}
            expected={'artifacts':{'publisher':matrix.OCULUS_262_PUBLISHER_SHA256},
                      'shader_pack':{'sha256':matrix.COMPLEMENTARY_5_7_1_SHA256}}
            with patch.object(matrix,'OCULUS_262_GBUFFER_BRIDGE_SHA256',hashlib.sha256(bridge.read_bytes()).hexdigest()):
                self.assertEqual(
                    ['Oculus community terrain/entity pack gbuffer sources were hashed but not compiled into the rendered pipelines'],
                    matrix.community_gbuffer_gaps(root,report,expected))
                expected['shader_pack']['sha256']='0'*64
                self.assertIn('Oculus community gbuffer proof shader-pack hash missing or wrong',
                              matrix.community_gbuffer_gaps(root,report,expected))

    def test_community_gbuffer_proof_rejects_forged_missing_and_stale_claims(self):
        expected={'artifacts':{'oculus.jar':matrix.OCULUS_262_PUBLISHER_SHA256},
                  'shader_pack':{'sha256':matrix.BSL_10_1_1_SHA256}}
        self.assertEqual(['Oculus community terrain/entity gbuffer proof missing'],
                         matrix.community_gbuffer_gaps(Path('.'),{},expected))
        forged={'schema':1,'run_token':'2'*32,'publisher_sha256':matrix.OCULUS_262_PUBLISHER_SHA256,
                'shader_pack_sha256':matrix.BSL_10_1_1_SHA256,'events':[],
                'bridge':{},'transform_log':{},'event_log':{}}
        gaps=matrix.community_gbuffer_gaps(Path('.'),{'run_id':'3'*32,'community_gbuffer_proof':forged},expected)
        self.assertIn('Oculus community gbuffer proof run token missing, wrong, or stale',gaps)
        self.assertIn('Oculus community terrain gbuffer OpenGL compilation proof missing or malformed',gaps)
        self.assertIn('Oculus community entity gbuffer draw proof missing or malformed',gaps)

    def test_matrix_resolves_missing_hash_keyed_input_from_durable_cache(self):
        with tempfile.TemporaryDirectory() as work:
            root=Path(work)
            expected='a'*64
            historical=root/'deleted'/expected/'artifact.jar'
            durable=root/'durable'/expected/'artifact.jar'
            durable.parent.mkdir(parents=True)
            durable.write_bytes(b'exact archived bytes')
            self.assertEqual(durable,matrix.resolve_input(historical,root/'durable'))
            historical.parent.mkdir(parents=True)
            historical.write_bytes(b'current bytes take precedence')
            self.assertEqual(historical,matrix.resolve_input(historical,root/'durable'))

    def test_native_damage_proof_requires_both_actual_client_entities(self):
        checks=[dict(entity=e,request_token='a'*32,time_ms=1791190701345,hurt_ticks=10,
                     entity_class='example.'+e.title()+'Entity') for e in ('ghost','wraith')]
        self.assertEqual([],matrix.native_damage_gaps({'native_damage_checks':checks}))
        self.assertEqual(2,len(matrix.native_damage_gaps({})))
        for invalid in (0,4,11,'10',None):
            checks[0]['hurt_ticks']=invalid
            self.assertEqual(['native client hurt-state proof missing or invalid: ghost'],
                             matrix.native_damage_gaps({'native_damage_checks':checks}))
        checks[0]['hurt_ticks']=10
        checks[0]['entity_class']='example.WraithEntity'
        self.assertEqual(1,len(matrix.native_damage_gaps({'native_damage_checks':checks})))

    def test_matrix_retains_unresolved_and_omitted_targets(self):
        result = matrix.audit_matrix([{'name':'missing-stack','target':'1.20.2-forge','reason':'publisher candidate not validated'}],
                                     ['1.20.2-forge','26.1.2-forge'])
        self.assertFalse(result['complete'])
        self.assertEqual(['26.1.2-forge'],result['missing_targets'])
        self.assertEqual(['publisher candidate not validated'],result['configurations'][0]['gaps'])

    def test_green_run_needs_exact_artifact_and_fresh_reviewed_captures(self):
        required = dict(loader_uid='net.fabricmc.fabric-loader', loader_version='0.18.4',
                        mode='baseline', artifacts={'mod.jar': 'current'}, captures=['wraith-night.png'])
        report = dict(required, status='captures_complete', clean_client_exit=True,
                      framebuffer='1280x720x24', wayland_unset=True,
                      audio_backend='ALSOFT_DRIVERS=null', visual_verdict='pass',
                      commands=[{'accepted': True}], live_shader_states=[{'active': False}])
        with tempfile.TemporaryDirectory() as work:
            root = Path(work)
            def save(): (root / 'report.json').write_text(json.dumps(report))
            save()
            self.assertIn('missing fresh capture: wraith-night.png', evidence.audit(root, required))
            (root / 'wraith-night.png').write_bytes(b'fresh pixels')
            report['capture_sha256'] = {'wraith-night.png': hashlib.sha256(b'fresh pixels').hexdigest()}
            save()
            self.assertEqual([], evidence.audit(root, required))
            required['per_capture_review']=True
            self.assertIn('capture visual review missing or stale: wraith-night.png',evidence.audit(root,required))
            report['visual_reviews']={'wraith-night.png':{'verdict':'pass','capture_sha256':report['capture_sha256']['wraith-night.png'],'note':'Face and arms readable in fresh night scene.'}}
            save()
            self.assertEqual([],evidence.audit(root,required))
            report['shader_compile_errors']=['Error compiling vertex shader: shadow.vsh']
            save()
            self.assertIn('shader compilation errors remain',evidence.audit(root,required))
            report['shader_compile_errors']=[]
            report['artifacts'] = {'mod.jar': 'old'}
            report['visual_verdict'] = 'not reviewed'
            save()
            self.assertIn('artifact hash mismatch: mod.jar', evidence.audit(root, required))
            self.assertIn('visual review incomplete', evidence.audit(root, required))
            (root / 'wraith-night.png').write_bytes(b'replaced pixels')
            self.assertIn('capture hash mismatch: wraith-night.png', evidence.audit(root, required))


if __name__ == '__main__': unittest.main()
