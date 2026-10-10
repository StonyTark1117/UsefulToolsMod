import importlib.util
from pathlib import Path
import tempfile
import unittest
from unittest.mock import patch
import struct
import zlib
import json
import subprocess
import re

ROOT = Path(__file__).resolve().parents[1]
SPEC = importlib.util.spec_from_file_location('spectral_validation', ROOT / 'scripts/spectral_validation.py')
validation = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(validation)


class SpectralValidationTests(unittest.TestCase):
    def test_fully_black_required_scenes_are_rejected_without_flagging_controls(self):
        from PIL import Image

        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            Image.new('RGB', (4, 4), (0, 0, 0)).save(root / 'wraith-night.png')
            Image.new('RGB', (4, 4), (0, 0, 0)).save(root / 'shader-menu.png')
            visible = Image.new('RGB', (4, 4), (0, 0, 0))
            visible.putpixel((2, 2), (1, 0, 0))
            visible.save(root / 'ghost-day.png')
            self.assertEqual(['wraith-night.png'], validation.fully_black_entity_scenes(
                root, ['wraith-night.png', 'ghost-day.png', 'shader-menu.png']))

    def test_black_scene_marks_completed_shader_capture_as_visual_failure(self):
        report = {'status': 'captures_complete', 'clean_client_exit': True,
                  'visual_verdict': 'not reviewed'}
        validation.record_black_scene_verdict(report, ['wraith-night.png'], 'active')
        self.assertEqual('visual_failed', report['status'])
        self.assertEqual('shader', report['failure_layer'])
        self.assertEqual('fail', report['visual_verdict'])
        self.assertTrue(report['clean_client_exit'])

        already_failed = {'status': 'failed', 'error': 'world entry failed'}
        validation.record_black_scene_verdict(already_failed, ['ghost-day.png'], 'active')
        self.assertEqual('failed', already_failed['status'])
        self.assertEqual('world entry failed', already_failed['error'])

    def test_community_save_accepts_faint_exact_label_only_with_screen_context(self):
        words = [
            {'text':'ShaderPack: ComplementaryReimagined_r5.7.1.zip','confidence':.87,'x':425,'y':172},
            {'text':'Shaders: OFF','confidence':.81,'x':425,'y':224},
            {'text':'Save','confidence':.612,'x':425,'y':395},
            {'text':'Done','confidence':.66,'x':633,'y':396},
        ]
        self.assertEqual([words[2]],validation.shader_menu_control_candidates(words,r'^Save$'))
        self.assertEqual([],validation.shader_menu_control_candidates(words[:3],r'^Save$'))
        self.assertEqual([],validation.shader_menu_control_candidates(
            [*words,dict(words[2],x=320)],r'^Save$'))
        self.assertEqual([],validation.shader_menu_control_candidates(
            [*words[:2],dict(words[2],text='Saxe'),words[3]],r'^Save$'))

    def test_community_gbuffer_extractor_binds_bridge_events_to_current_run(self):
        with tempfile.TemporaryDirectory() as directory:
            root=Path(directory);bridge=root/'bridge.jar';bridge.write_bytes(b'bridge')
            events=root/'events.jsonl';token='a'*32
            event={'kind':'compiled','category':'terrain','run_token':token}
            events.write_text(json.dumps(event)+'\n')
            transform=root/'transform.log';transform.write_text(
                'OCULUS_262_GBUFFER_BRIDGE ready targets=5 run_token='+token+' event_log='+str(events)+'\n')
            proof=validation.extract_community_gbuffer_proof(
                ['-javaagent:'+str(bridge)+'='+str(transform)],token,'b'*64,'c'*64)
            self.assertEqual([event],proof['events'])
            self.assertEqual(token,proof['run_token'])
            with self.assertRaisesRegex(ValueError,'stale harness run'):
                validation.extract_community_gbuffer_proof(
                    ['-javaagent:'+str(bridge)+'='+str(transform)],'d'*32,'b'*64,'c'*64)

    def test_community_mod_control_accepts_only_versioned_clipped_pixel_aliases(self):
        for label in ('Oculus Forge 26.2 Communit', '0culus Forge 26.2 Communit',
                      'culus Forge 26.2 Communit'):
            self.assertIsNotNone(re.search(validation.COMMUNITY_MOD_CONTROL, label, re.I))
        for label in ('Oculus Forge 26.1 Communit', 'Forge 26.2 Communit',
                      'culus Forge 26.2 Other'):
            self.assertIsNone(re.search(validation.COMMUNITY_MOD_CONTROL, label, re.I))

    def test_community_shader_state_control_rejects_wrong_state(self):
        self.assertIsNotNone(re.fullmatch(validation.shader_state_control_pattern(True), 'Shaders: ON', re.I))
        self.assertIsNone(re.fullmatch(validation.shader_state_control_pattern(True), 'Shaders: OFF', re.I))
        self.assertIsNotNone(re.fullmatch(validation.shader_state_control_pattern(False), 'Shaders : OFF', re.I))
        self.assertIsNone(re.fullmatch(validation.shader_state_control_pattern(False), 'Shaders: ON', re.I))

    def test_owned_framebuffer_ocr_maps_pause_control_to_game_window(self):
        geometry={'X':'213','Y':'120','WIDTH':'854','HEIGHT':'480'}
        words=[{'text':'Options...','confidence':.89,'x':531.75,'y':408.25},
               {'text':'Unrelated window','confidence':.99,'x':1100,'y':690}]
        mapped=validation.map_root_ocr_to_window(words,geometry,(1280,720))
        self.assertEqual(len(mapped),1)
        self.assertEqual(mapped[0]['text'],'Options...')
        self.assertEqual((mapped[0]['x'],mapped[0]['y']),(318.75,288.25))
        self.assertEqual(mapped[0]['source'],'owned_xvfb_framebuffer')
        with self.assertRaisesRegex(ValueError,'outside the owned framebuffer'):
            validation.map_root_ocr_to_window(words,{**geometry,'X':'500'},(1280,720))

    def test_private_agent_log_preflight_rejects_stale_output_before_launch(self):
        with tempfile.TemporaryDirectory() as directory:
            output=Path(directory)/'current'
            bridge=Path(directory)/'bridge.jar';bridge.write_bytes(b'private test agent')
            donor=Path(directory)/'donor.jar';donor.write_bytes(b'private test donor')
            valid='--jvm-arg=-javaagent:'+str(bridge)+'='+str(donor)+'!'+str(output/'native-bridge.log')
            validation.validate_manifest_agent_logs([valid],output)
            stale=valid.replace(str(output),str(Path(directory)/'previous'))
            with self.assertRaisesRegex(ValueError,'parent is unavailable'):
                validation.validate_manifest_agent_logs([stale],output)
            with self.assertRaisesRegex(ValueError,'donor archive is unavailable'):
                validation.validate_manifest_agent_logs([
                    valid.replace(str(donor),'-javaagent:'+str(donor))],output)
            external=Path(directory)/'existing';external.mkdir()
            validation.validate_manifest_agent_logs([
                valid.replace(str(output),str(external))],output)

    def test_embedded_donor_agent_is_checked_before_launch(self):
        with tempfile.TemporaryDirectory() as directory:
            root=Path(directory);output=root/'current';output.mkdir()
            bridge=root/'bridge.jar';bridge.write_bytes(b'embedded exact donors')
            validation.validate_manifest_agent_logs(
                ['-javaagent:'+str(bridge)+'='+str(output/'transform.log')],output)
            with self.assertRaisesRegex(ValueError,'agent is unavailable'):
                validation.validate_manifest_agent_logs(
                    ['-javaagent:'+str(root/'missing.jar')+'='+str(output/'transform.log')],output)

    def test_packaged_client_rejects_bad_donor_before_profile_preparation(self):
        import os
        import sys
        with tempfile.TemporaryDirectory() as directory:
            root=Path(directory);output=root/'unprepared';bridge=root/'bridge.jar'
            bridge.write_bytes(b'private test agent')
            bad='-javaagent:'+str(bridge)+'='+str(root/'missing-donor.jar')+'!'+str(output/'native-bridge.log')
            environment={**os.environ,'HOME':str(root)}
            result=subprocess.run([sys.executable,str(ROOT/'scripts/validate-packaged-spectral.py'),
                '--template',str(root/'absent-template'),'--artifact',str(root/'absent-artifact.jar'),
                '--loader-uid','net.minecraftforge','--loader-version','65.1.2','--output',str(output),
                '--jvm-arg='+bad],capture_output=True,text=True,env=environment,timeout=10)
            self.assertEqual(2,result.returncode,result.stderr)
            self.assertIn('donor archive is unavailable',result.stderr)
            self.assertFalse(output.exists())

    def test_bounded_admission_wait_transfers_exclusive_ownership_and_times_out(self):
        import threading
        with tempfile.TemporaryDirectory() as directory:
            path=Path(directory)/'client.lock'
            first=validation.acquire_client_admission(path)
            release=threading.Timer(.05,first.close);release.start()
            try:
                second=validation.acquire_client_admission(path,wait_seconds=1)
                with self.assertRaises(BlockingIOError):
                    validation.acquire_client_admission(path,wait_seconds=.05)
                second.close()
                third=validation.acquire_client_admission(path);third.close()
            finally:
                release.join();first.close()

    def test_native_package_builder_rejects_busy_admission_before_build_or_evidence(self):
        import os
        import sys
        with tempfile.TemporaryDirectory() as directory:
            root=Path(directory)
            lock=validation.acquire_client_admission(root/'.cache/useful-tools-spectral-client.lock')
            try:
                environment={**os.environ,'HOME':str(root)}
                result=subprocess.run([sys.executable,str(ROOT/'scripts/build-lighting-qa-package.py'),
                    '1.20.2-forge'],capture_output=True,text=True,env=environment,timeout=10)
                self.assertEqual(75,result.returncode,result.stderr)
                self.assertIn('no package build started',result.stderr)
                self.assertEqual([],list(root.rglob('*.jar')))
            finally:lock.close()

    def test_classic_beddium_requires_every_stage_and_positive_native_samples(self):
        rows=['USEFUL_TOOLS_LIGHTING_QA_BACKEND_PASS beddium_cache stage='+str(stage)
              +' packed=240 required=80 vanilla_cache_unchanged=true' for stage in range(22)]
        result=validation.classic_beddium_observation('\n'.join(rows+rows))
        self.assertEqual(list(range(22)),[row['stage'] for row in result])
        for invalid in ('\n'.join(rows[:-1]),'\n'.join(rows).replace('required=80','required=0'),
                        '\n'.join(rows).replace('packed=240','packed=16'),
                        '\n'.join(rows+['USEFUL_TOOLS_LIGHTING_QA_BACKEND_PASS beddium_cache stage=0 packed=200 required=80 vanilla_cache_unchanged=true'])):
            with self.assertRaises(ValueError):validation.classic_beddium_observation(invalid)

    def test_shader_menu_retry_never_selects_folder_or_options_controls(self):
        for label in ('Shaders','Shaders...','Shaders ...','of.options.shaders'):
            self.assertIsNotNone(re.search(validation.SHADERS_MENU_CONTROL,label,re.I))
        for label in ('Shaders Folder','Shader Options...','Shaders: OFF'):
            self.assertIsNone(re.search(validation.SHADERS_MENU_CONTROL,label,re.I))
        for label in ('OFF','QFF','of.options.shaders.packNone','of.options.shaders.packhone',
                      'of.options.shaders.packone'):
            self.assertIsNotNone(re.search(validation.OPTIFINE_OFF_CONTROL,label,re.I))
        for label in ('of.options.shaders.packDefault','BSL_v10.1.2.zip'):
            self.assertIsNone(re.search(validation.OPTIFINE_OFF_CONTROL,label,re.I))

    def test_isolated_world_row_survives_unreadable_title_and_morld_ocr(self):
        words=[dict(text='Play Selected Morld',confidence=.81,x=268,y=395),
               dict(text='Survival Mode, Commands, Version: 1.2l.1',confidence=.86,x=429,y=153)]
        self.assertIsNone(validation.menu_action(words,'New World'))
        action,control=validation.menu_action(words,'New World',single_world=True)
        self.assertEqual('open_world',action)
        self.assertEqual(153,control['y'])
        words.append(dict(text='Creative Mode, Version: 1.21.1',confidence=.9,x=429,y=223))
        self.assertIsNone(validation.menu_action(words,'New World',single_world=True))

    def test_native_survival_row_pixel_font_confusions_require_one_isolated_world(self):
        words=[dict(text='Play Selected Morld',confidence=.83,x=270,y=396),
               dict(text='Surwival Mode. Cheats. Version: 1.20.2',confidence=.81,x=210,y=68)]
        self.assertIsNone(validation.menu_action(words,'New World'))
        action,control=validation.menu_action(words,'New World',single_world=True)
        self.assertEqual('open_world',action)
        self.assertEqual(68,control['y'])
        words.append(dict(text='Survival Mode, Cheats, Version: 1.20.2',confidence=.9,x=210,y=148))
        self.assertIsNone(validation.menu_action(words,'New World',single_world=True))

    def test_observed_forge_optifine_world_metadata_ocr_opens_only_isolated_world(self):
        button=dict(text='Play Selected Morld',confidence=.81,x=268,y=395)
        for label in ('Surival Mode. Commands., Version: 1.2i.1',
                      'Suryival Mode, Commands, Version: 1.2l.1'):
            metadata=dict(text=label,confidence=.77,x=429,y=153)
            self.assertIsNone(validation.menu_action([button,metadata],'New World'))
            self.assertEqual(('open_world',metadata),
                             validation.menu_action([button,metadata],'New World',single_world=True))

    def test_client_admission_fails_before_another_profile_is_prepared(self):
        with tempfile.TemporaryDirectory() as directory:
            path=Path(directory)/'client.lock'
            first=validation.acquire_client_admission(path)
            try:
                with self.assertRaises(BlockingIOError):validation.acquire_client_admission(path)
                self.assertEqual(0o600,path.stat().st_mode&0o777)
            finally:first.close()
            second=validation.acquire_client_admission(path)
            second.close()

    def test_packaged_entry_point_rejects_busy_admission_before_copy_or_output(self):
        import sys
        import os
        with tempfile.TemporaryDirectory() as directory:
            root=Path(directory);output=root/'not-prepared'
            lock=validation.acquire_client_admission(root/'.cache/useful-tools-spectral-client.lock')
            try:
                environment=os.environ.copy();environment['HOME']=str(root)
                result=subprocess.run([sys.executable,str(ROOT/'scripts/validate-packaged-spectral.py'),
                    '--template',str(root/'absent-template'),'--artifact',str(root/'absent-artifact.jar'),
                    '--loader-uid','net.minecraftforge','--loader-version','48.1.0','--output',str(output)],
                    capture_output=True,text=True,timeout=10,env=environment)
                self.assertEqual(75,result.returncode,result.stderr)
                self.assertIn('no test profile was created',result.stderr)
                self.assertFalse(output.exists())
            finally:lock.close()

    def test_invalid_build_provenance_fails_before_output_or_private_profile(self):
        import sys
        import os
        with tempfile.TemporaryDirectory() as directory:
            root=Path(directory);artifact=root/'artifact.jar';artifact.write_bytes(b'fixture')
            provenance=root/'provenance.json';provenance.write_text(json.dumps({'source_sha256':{}}))
            environment=os.environ.copy();environment['HOME']=str(root)
            result=subprocess.run([sys.executable,str(ROOT/'scripts/validate-packaged-spectral.py'),
                '--template',str(root/'absent-template'),'--artifact',str(artifact),
                '--artifact-provenance',str(provenance),'--loader-uid','net.minecraftforge',
                '--loader-version','48.1.0','--output',str(root/'not-prepared')],
                capture_output=True,text=True,timeout=10,env=environment)
            self.assertEqual(2,result.returncode,result.stderr)
            self.assertIn('Build provenance requires',result.stderr)
            self.assertFalse((root/'not-prepared').exists())
            self.assertFalse((root/'.cache/useful-tools-spectral-active-runs').exists())

    def test_matrix_reports_failure_after_retaining_all_attempted_rows(self):
        import sys
        with tempfile.TemporaryDirectory() as directory:
            root=Path(directory);manifest=root/'matrix.json';results=root/'results.json'
            rows=[{'name':name,'target':'target','command':[
                sys.executable,'-c','raise SystemExit('+str(code)+')',
                '--output',str(root/name)]} for name,code in [('failed',2),('passed',0)]]
            manifest.write_text(json.dumps(rows))
            command=[sys.executable,str(ROOT/'scripts/run-spectral-configurations.py'),
                     str(manifest),'--results',str(results)]
            result=subprocess.run(command,capture_output=True,text=True,timeout=10)
            self.assertEqual(1,result.returncode,result.stderr)
            retained=json.loads(results.read_text())
            self.assertEqual(['failed','passed'],[row['name'] for row in retained])
            self.assertEqual([2,0],[row['exit_code'] for row in retained])
            resumed=subprocess.run(command+['--resume'],capture_output=True,text=True,timeout=10)
            self.assertEqual(1,resumed.returncode,resumed.stderr)
            self.assertEqual(retained,json.loads(results.read_text()))

    def test_busy_client_queue_stops_and_can_resume_without_dropping_rows(self):
        import sys
        with tempfile.TemporaryDirectory() as directory:
            root=Path(directory);manifest=root/'matrix.json';results=root/'results.json'
            command=[sys.executable,'-c','raise SystemExit(75)','--output',str(root/'run')]
            manifest.write_text(json.dumps([{'name':'first','target':'target','command':command},
                                           {'name':'second','target':'target','command':command}]))
            invocation=[sys.executable,str(ROOT/'scripts/run-spectral-configurations.py'),str(manifest),'--results',str(results)]
            first=subprocess.run(invocation,capture_output=True,text=True)
            self.assertEqual(75,first.returncode)
            self.assertEqual(['first'],[row['name'] for row in json.loads(results.read_text())])
            self.assertFalse((root/'run').exists())
            manifest.write_text(json.dumps([{'name':'first','target':'target','status':'fixture_completed'},
                                           {'name':'second','target':'target','status':'fixture_completed'}]))
            resumed=subprocess.run(invocation+['--resume'],capture_output=True,text=True)
            self.assertEqual(0,resumed.returncode,resumed.stderr)
            self.assertEqual(['first','second'],[row['name'] for row in json.loads(results.read_text())])

    def test_duplicate_world_titles_cannot_select_an_uninstrumented_world(self):
        with tempfile.TemporaryDirectory() as directory:
            root=Path(directory);saves=root/'saves';saves.mkdir()
            for name in ('New World', 'New World (1)', 'New World (2)'):
                world=saves/name;world.mkdir();(world/'level.dat').write_bytes(b'identical menu title')
            (saves/'unrelated.txt').write_text('preserve')
            selected,moved=validation.isolate_world_selection(saves,root/'archive')
            self.assertEqual(2,len(moved))
            self.assertEqual([selected],[p.name for p in saves.iterdir() if p.is_dir()])
            self.assertEqual('preserve',(saves/'unrelated.txt').read_text())
            for name in moved:self.assertEqual(b'identical menu title',(root/'archive'/name/'level.dat').read_bytes())
            with self.assertRaises(ValueError):validation.isolate_world_selection(saves,saves/'bad')

    def test_damage_capture_requires_fresh_client_hurt_timer(self):
        token = 'a' * 32
        proof = token + '\nHURT\n1000\n9\nGhostEntity\n'
        self.assertEqual(9, validation.native_hurt_observation(proof, token, 1100)['hurt_ticks'])
        self.assertIsNone(validation.native_hurt_observation(proof, 'b'*32, 1100))
        self.assertIsNone(validation.native_hurt_observation(proof, token, 1200))
        self.assertIsNone(validation.native_hurt_observation(proof, token, 900))
        self.assertIsNone(validation.native_hurt_observation(proof.replace('\n9\n', '\n0\n'), token, 1100))
        with self.assertRaises(ValueError):
            validation.native_hurt_observation(token+'\nSENT\nmethod\n', token, 1100)
        with self.assertRaises(ValueError):
            validation.native_hurt_observation(token+'\nFAILED\nfield absent\n', token, 1100)

    def test_native_frame_trial_rejects_stale_partial_and_fabricated_samples(self):
        token='a'*32
        valid=token+'\nCOMPLETE\n'+json.dumps([1000]*1800)+'\n'
        self.assertEqual([1000]*1800,validation.native_frame_observation(valid,token))
        self.assertIsNone(validation.native_frame_observation(valid,'b'*32))
        for value in ([1000]*1799,[1000]*1799+[0],[True]*1800,[1.5]*1800,{'samples':1800}):
            with self.assertRaises(ValueError):validation.native_frame_observation(token+'\nCOMPLETE\n'+json.dumps(value),token)
        with self.assertRaises(ValueError):validation.native_frame_observation(token+'\nFAILED\ntimeout',token)

    def test_native_transport_never_substitutes_for_fresh_game_feedback(self):
        token = 'a' * 32
        self.assertEqual('SENT', validation.native_command_status(token+'\nSENT\npublic void sendCommand(String)\n', token))
        self.assertEqual('FAILED', validation.native_command_status(token+'\nFAILED\nplayer absent\n', token))
        self.assertIsNone(validation.native_command_status('b'*32+'\nSENT\nmethod\n', token))
        with self.assertRaises(ValueError):
            validation.native_command_status(token+'\nSENT\n', token)
        previous = ['Set the time to 6000']
        self.assertEqual('pending', validation.command_feedback(previous, 'Set the time to 6000', 'Set the time'))
        self.assertEqual('accepted', validation.command_feedback(previous, 'Set the time to 6000\nSet the time to 6000', 'Set the time'))
        self.assertEqual('rejected', validation.command_feedback([], 'Unknown entity\nSet the time to 6000', 'Set the time'))

    def test_infuser_client_inventory_rejects_observed_empty_and_stale_slots(self):
        header = '1000 com.stonytark.usefultoolsmod.block.entity.SpectralInfuserMenu\n'
        populated = header + '36 1 minecraft:iron_sword\n37 1 usefultoolsmod:ectoplasm\n'
        empty = header + '36 0 minecraft:air\n37 0 minecraft:air\n'
        self.assertTrue(validation.populated_infuser_client_slots(populated, 1500))
        for text, time_ms in [(empty, 1500), (populated, 6001), (populated, 999),
                              ('MENU_PROBE_ERROR reflection failed', 1500)]:
            self.assertFalse(validation.populated_infuser_client_slots(text, time_ms))

    def test_quota_failure_releases_display_but_preserves_existing_owner(self):
        with tempfile.TemporaryDirectory() as directory:
            reservation = Path(directory) / 'display.lock'
            with patch.object(validation.os, 'write', side_effect=OSError(122, 'Disk quota exceeded')):
                with self.assertRaises(OSError):
                    validation.reserve_display(reservation, 'failed-run')
            self.assertFalse(reservation.exists())
            self.assertTrue(validation.reserve_display(reservation, 'owned-run'))
            self.assertFalse(validation.reserve_display(reservation, 'other-run'))
            self.assertEqual('owned-run', reservation.read_text())

    def test_pack_label_omitted_spaces_preserves_exact_version(self):
        pattern = validation.pack_control_pattern("Sildur's Enhanced Default v1.14 Fast.zip")
        for label in ("Sildur's Enhanced Default v1.14 Fast.zip",
                      "Sildur'sEnhancedDefaultv1.14Fast.zip"):
            self.assertIsNotNone(re.search(pattern, label, re.I))
        for label in ("Sildur'sEnhancedDefaultv1.15Fast.zip", "Shader Options",
                      "Sildur'sEnhancedDefaultv1.14Fast.zip.disabled"):
            self.assertIsNone(re.search(pattern, label, re.I))

    def test_complementary_pixel_font_alias_retains_exact_pack_version(self):
        pattern = validation.pack_control_pattern('ComplementaryReimagined_r5.9.3.zip')
        for label in ('ComplementaryReimagined_r5.9.3.zip',
                      'Complementaryfieimagined_r5.9.3.zip',
                      'Complementaryfeimagined_r5.9.3.zip',
                      'Complementaryeinagined_r5.9.3.zip'):
            self.assertIsNotNone(re.search(pattern, label, re.I))
        for label in ('Complementaryfieimagined_r5.7.1.zip',
                      'Complementaryfieimagined_r5.9.3.zip.disabled',
                      'ComplementaryUnbound_r5.9.3.zip', 'Shader Options'):
            self.assertIsNone(re.search(pattern, label, re.I))

    def test_external_queue_requires_exact_command_and_never_claims_pass(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            external = root / 'external.json'
            manifest = root / 'matrix.json'
            results = root / 'results.json'
            command = ['unavailable-client-command', '--output', str(root / 'run')]
            external.write_text(json.dumps([{'command': command}]))
            manifest.write_text(json.dumps([{'name': 'baseline', 'target': 'target',
                'command': command, 'external_execution_manifest': str(external)}]))
            invocation = ['python3', str(ROOT / 'scripts/run-spectral-configurations.py'),
                          str(manifest), '--results', str(results)]
            subprocess.run(invocation, check=True, capture_output=True)
            record = json.loads(results.read_text())[0]
            self.assertEqual('external_execution', record['status'])
            self.assertIsNone(record['exit_code'])
            self.assertFalse((root / 'run/report.json').exists())
            external.write_text(json.dumps([{'command': command + ['different']}]))
            failed = subprocess.run(invocation, capture_output=True, text=True)
            self.assertNotEqual(0, failed.returncode)
            self.assertIn('External execution command mismatch', failed.stderr)

    def test_launcher_diagnostics_preserve_native_error_without_arguments_or_secrets(self):
        text = '\n'.join([
            'Minecraft arguments: --accessToken secret-value',
            'java.lang.IllegalArgumentException: invalid session token secret-value',
            'java.lang.NoSuchMethodError: GlStateManager.init(GLCapabilities)',
            'Caused by: java.lang.ClassNotFoundException: net.example.NativeClass',
            '    at optifine.OptiFineTransformer.transform(OptiFineTransformer.java:31)',
            'username=test-player',
        ])
        filtered = validation.native_console_errors(text)
        self.assertIn('NoSuchMethodError', filtered)
        self.assertIn('ClassNotFoundException', filtered)
        self.assertIn('at optifine.OptiFineTransformer', filtered)
        self.assertNotIn('secret-value', filtered)
        self.assertNotIn('username', filtered)

    def test_private_weather_predicate_uses_native_data_formats_and_paths(self):
        import json
        with tempfile.TemporaryDirectory() as directory:
            for version,folder in [('1.20.2','predicates'),('1.21.1','predicate'),('26.1.2','predicate')]:
                world=Path(directory)/version
                result=validation.install_weather_control(world,version)
                pack=world/'datapacks/spectral_recovery_weather'
                rain=pack/('data/spectral_recovery/'+folder+'/rain.json')
                self.assertEqual({'condition':'minecraft:weather_check','raining':True},json.loads(rain.read_text()))
                self.assertIn(str(rain.relative_to(pack)),result['files_sha256'])
            metadata=json.loads((pack/'pack.mcmeta').read_text())['pack']
            self.assertEqual([101,1],metadata['min_format'])
            with self.assertRaises(KeyError):validation.install_weather_control(world,'unverified-version')

    def test_requested_floor_uses_last_jvm_override_and_native_clamping(self):
        self.assertIsNone(validation.requested_wraith_floor([]))
        self.assertEqual(15, validation.requested_wraith_floor(['-Dusefultools.wraithShaderLight=99']))
        self.assertEqual(10, validation.requested_wraith_floor(['-Dusefultools.wraithShaderLight=15','-Dusefultools.wraithShaderLight=9']))

    def test_bsl_pixel_prefix_alias_preserves_exact_version_and_zip(self):
        pattern = validation.pack_control_pattern('BSL_v10.1.2.zip')
        self.assertRegex('BSL_v10.1.2.zip', pattern)
        self.assertRegex('BSL_410.1.2.zip', pattern)
        self.assertRegex('BSL_u1a.1.2.zip', pattern)
        for incorrect in ['BSL_410.1.1.zip', 'BSL_410.1.20.zip', 'BSL_410.1.2.zp', '6SL_10.1.2.zp']:
            self.assertIsNone(re.search(pattern, incorrect, re.I))

    def test_experimental_confirmation_observed_alias_requires_native_screen(self):
        button = dict(text="I Kinow WhatI'm Coing!", confidence=.8149, x=585, y=273.75)
        title = dict(text='Worlds using Experinental Settings are not supported', confidence=.8958, x=424, y=107)
        backup = dict(text='Create Backup and Load', confidence=.8104, x=267, y=273.5)
        self.assertIsNone(validation.menu_action([button], 'New World'))
        self.assertIsNone(validation.menu_action([title, button], 'New World'))
        self.assertIsNone(validation.menu_action([backup, button], 'New World'))
        action, observed = validation.menu_action([title, backup, button], 'New World')
        self.assertEqual('confirm_experimental', action)
        self.assertIs(button, observed)
        self.assertIsNone(validation.menu_action([title, backup, dict(button, confidence=.5)], 'New World'))
        self.assertIsNone(validation.menu_action([title, backup, button, dict(button, x=800)], 'New World'))

    def test_forge_warning_button_pixel_font_confusion(self):
        words=[dict(text='Warnings while loading mods', confidence=.9,x=400,y=20),dict(text='Froceed to main menu',confidence=.9,x=423,y=451)]
        self.assertEqual('acknowledge_loading_warnings', validation.menu_action(words,'New World')[0])

    def test_unknown_item_feedback_never_acknowledges_a_fixture(self):
        self.assertFalse(validation.acknowledged('Unknown item usefultoolsmod:hglow_sword; Replaced previously', 'Replaced'))
        self.assertIn('usefultoolsmod:hglowstone_sword[', validation.enchanted_sword_fixture('26.1.2'))

    def test_infuser_fixture_uses_target_native_inventory_serialization(self):
        for version in ('1.20.1','1.20.2','1.21.1','26.1.2','26.2'):
            for loader in ('net.minecraftforge','net.neoforged','net.fabricmc.fabric-loader','org.quiltmc.quilt-loader'):
                fixture=validation.infuser_fixture_nbt(version,loader)
                source_loader='fabric' if loader in ('net.fabricmc.fabric-loader','org.quiltmc.quilt-loader') else 'forge' if loader=='net.minecraftforge' else 'neoforge'
                source=(ROOT/version/source_loader/'src/main/java/com/stonytark/usefultoolsmod/block/entity/SpectralInfuserBlockEntity.java').read_text()
                self.assertEqual('tag.put("inventory"' in source,'inventory:{Size:3,' in fixture)
                self.assertEqual(version.startswith('1.20.'),'Count:1b' in fixture)
                self.assertIn('progress:-10000',fixture)

    def test_async_screenshot_requires_complete_chunks_and_crc(self):
        def chunk(kind, body):
            return struct.pack('>I', len(body))+kind+body+struct.pack('>I', zlib.crc32(kind+body)&0xffffffff)
        png=b'\x89PNG\r\n\x1a\n'+chunk(b'IHDR',b'header')+chunk(b'IDAT',b'pixels')+chunk(b'IEND',b'')
        self.assertTrue(validation.complete_png(png))
        for size in range(len(png)):
            self.assertFalse(validation.complete_png(png[:size]))
        damaged=bytearray(png);damaged[20]^=1
        self.assertFalse(validation.complete_png(damaged))

    def test_prism_launch_options_stay_in_general_section(self):
        original = '[General]\nJvmArgs=\nOverrideJavaArgs=false\n[UI]\ngeometry=abc\n'
        configured = validation.set_instance_option(original, 'JvmArgs', '-Dusefultools.wraithShaderLight=12')
        configured = validation.set_instance_option(configured, 'OverrideJavaArgs', 'true')
        self.assertEqual('[General]\nJvmArgs=-Dusefultools.wraithShaderLight=12\nOverrideJavaArgs=true\n[UI]\ngeometry=abc\n', configured)

    def test_native_disconnect_requires_title_and_return_control(self):
        words=[dict(text='Failed to connect to the server',confidence=.95),
               dict(text='Server sent an invalid packet',confidence=.95),
               dict(text='Back to Server List',confidence=.95)]
        self.assertIn('invalid packet', validation.menu_failure(words))
        self.assertIsNone(validation.menu_failure(words[:2]))
        self.assertIsNone(validation.menu_failure([dict(w,confidence=.2) for w in words]))
        self.assertIsNone(validation.menu_failure([dict(text='Failed to connect to the server in a guide',confidence=.99),words[2]]))
        words[0]['text']='Failedtoconnecttotheserver'
        self.assertIsNotNone(validation.menu_failure(words))

    def test_shutdown_requires_the_actual_pause_menu(self):
        self.assertIsNone(validation.shutdown_control([dict(text='一口',confidence=.84)]))
        control=dict(text='Save and Quit to Title',confidence=.95,x=426,y=307)
        self.assertEqual(control,validation.shutdown_control([control]))
        self.assertIsNone(validation.shutdown_control([dict(control,confidence=.2)]))
        self.assertIsNone(validation.shutdown_control([control],True))
        with self.assertRaises(ValueError):validation.shutdown_control([control,control])
        self.assertIsNotNone(validation.shutdown_control([dict(control,text='Disconnect')],True))

    def test_shutdown_waits_on_native_saving_world_menu(self):
        words=[dict(text='Game Menu',confidence=.80),
               dict(text='Saving world',confidence=.91)]
        self.assertTrue(validation.native_pause_menu_visible(words))
        self.assertIsNone(validation.shutdown_control(words))
        self.assertFalse(validation.native_pause_menu_visible([dict(text='Game Menu',confidence=.2)]))
        self.assertFalse(validation.native_pause_menu_visible([dict(text='Saved screenshot as 2026-10-08.png',confidence=.91)]))

    def test_menu_navigation_uses_only_recognized_controls(self):
        words = [{'text':'Singleplayer', 'confidence':.99, 'x':426, 'y':237}]
        self.assertEqual('singleplayer', validation.menu_action(words, 'New World')[0])
        self.assertIsNone(validation.menu_action([dict(words[0], confidence=.2)], 'New World'))
        self.assertIsNone(validation.menu_action([dict(words[0], text='Multiplayer')], 'New World'))
        world = [{'text':'Flay Selected World','confidence':.95,'x':268,'y':395},
                 {'text':'Hew Horld','confidence':.95,'x':278,'y':115},
                 {'text':'Create Hew Horld','confidence':.95,'x':580,'y':395}]
        self.assertEqual(('open_world', world[1]), validation.menu_action(world, 'New World'))
        world[1]['text'] = 'Heu Horld'
        self.assertEqual(('open_world', world[1]), validation.menu_action(world, 'New World'))
        self.assertIsNone(validation.menu_action(world, 'Different World'))
        # Fresh Forge 1.21.1 pixel-font OCR can omit every label space.
        for label in ("I know what I'm doing!", "IknowwhatI'mdoing!"):
            button = dict(words[0], text=label, x=586, y=274)
            self.assertEqual(('confirm_experimental', button),
                             validation.menu_action([button], 'New World'))
        self.assertIsNone(validation.menu_action(
            [dict(words[0], text='This guide explains how to know what you are doing')], 'New World'))

    def test_baseline_rejects_accidental_shader_dependencies(self):
        for name in ('iris-fabric.jar', 'oculus.jar', 'OptiFine.jar'):
            with self.assertRaises(ValueError):
                validation.validation_mode('baseline', None, False, [Path(name)])
        self.assertEqual('baseline', validation.validation_mode(None, None, False, [Path('fabric-api.jar')]))

    def test_disabled_requires_an_installed_loader(self):
        with self.assertRaises(ValueError):
            validation.validation_mode('disabled', None, True, [])
        self.assertEqual('disabled', validation.validation_mode(None, None, True, [Path('oculus.jar')]))

    def test_optifine_disabled_replaces_inherited_active_pack(self):
        with tempfile.TemporaryDirectory() as directory:
            game = Path(directory)
            path = game / 'optionsshaders.txt'
            path.write_text('shaderPack=Old.zip\nrenderResMul=1.0\n')
            validation.configure_shaders(game, 'optifine', 'disabled')
            self.assertEqual('renderResMul=1.0\nshaderPack=OFF\n', path.read_text())
            validation.configure_shaders(game, 'optifine', 'active', 'BSL.zip')
            self.assertIn('shaderPack=BSL.zip\n', path.read_text())

    def test_community_uses_its_actual_config_keys(self):
        with tempfile.TemporaryDirectory() as directory:
            game = Path(directory)
            validation.configure_shaders(game, 'community', 'active', 'BSL.zip')
            self.assertEqual('shaders.enabled=true\nshaderpack=BSL.zip\n',
                             (game / 'config/oculus.properties').read_text())

    def test_activation_does_not_accept_pack_discovery_or_fallback(self):
        self.assertFalse(validation.activation_reported('Found shaderpack: BSL.zip', 'iris', 'BSL.zip'))
        self.assertFalse(validation.activation_reported('Using shaderpack: Other.zip', 'iris', 'BSL.zip'))
        self.assertTrue(validation.activation_reported('Using shaderpack: BSL.zip', 'iris', 'BSL.zip'))
        self.assertTrue(validation.activation_reported("Activated shader pack 'BSL.zip' with 1 Forge frame-graph full-screen stage(s)", 'community', 'BSL.zip'))
        self.assertTrue(validation.activation_reported("Activated shader pack 'BSL' with 1 Forge frame-graph full-screen stage(s)", 'community', 'BSL.zip'))
        self.assertFalse(validation.activation_reported("Activated shader pack 'Other' with 1 Forge frame-graph full-screen stage(s)", 'community', 'BSL.zip'))
        self.assertFalse(validation.activation_reported("Executed shader pack 'BSL.zip'", 'community', 'BSL.zip'))

    def test_rejected_command_cannot_pass_on_another_matching_message(self):
        self.assertFalse(validation.acknowledged('Summoned entity\nUnknown entity', 'Summoned'))
        self.assertFalse(validation.acknowledged('Unknown or incomplete command', 'command'))
        self.assertTrue(validation.acknowledged('Summoned new Wraith', 'Summoned'))
        self.assertFalse(validation.acknowledged(
            'Hardened Glowstone Sword cannot support that enchantment', 'enchant'))
        self.assertTrue(validation.acknowledged('Enchanted Hardened Glowstone Sword', 'Enchanted'))

    def test_glint_fixture_uses_native_item_serialization(self):
        for version in ('1.20.1', '1.20.2'):
            self.assertIn('Enchantments:[', validation.enchanted_sword_fixture(version))
        for version in ('1.21.1', '26.1.2', '26.2'):
            self.assertIn('[minecraft:enchantments={"minecraft:unbreaking":1}]',
                          validation.enchanted_sword_fixture(version))


if __name__ == '__main__':
    unittest.main()
