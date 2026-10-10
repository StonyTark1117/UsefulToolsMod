#!/usr/bin/env python3
"""Capture modern spectral scenes from a disposable packaged Prism client on owned Xvfb.

The report distinguishes command/capture success from the human visual verdict.
No existing display or launcher instance is ever controlled.
"""
import argparse
import base64
import fcntl
import hashlib
import gzip
import json
import os
from pathlib import Path
import re
import shutil
import struct
import shlex
import signal
import subprocess
import sys
import tempfile
import time
import uuid
import urllib.request
from collections import Counter

# Freeze the shared harness before parsing or creating a test profile. A run
# waiting for the GPU must keep the source that its evidence identifies.
if os.environ.get('USEFUL_TOOLS_SPECTRAL_FROZEN_RUN')!='1':
    repository=Path(__file__).resolve().parents[1]
    names=('validate-packaged-spectral.py','spectral_validation.py','spectral-menu-ocr.py','lighting_shader_diagnostics.py')
    sources={name:(repository/'scripts'/name).read_bytes() for name in names}
    for name,data in sources.items():
        assert data,'Empty harness source: '+name
        compile(data,str(repository/'scripts'/name),'exec')
    identity=hashlib.sha256(b''.join(name.encode()+sources[name] for name in names)).hexdigest()
    frozen=Path('/tmp/usefultools-shaders-2.3.2/harness')/identity/'scripts'
    frozen.mkdir(parents=True,exist_ok=True)
    for name,data in sources.items():
        path=frozen/name
        if path.exists():assert path.read_bytes()==data,'Frozen harness source changed'
        else:
            temporary=frozen/('.'+name+'-'+uuid.uuid4().hex)
            temporary.write_bytes(data);temporary.replace(path)
    frozen_env=dict(os.environ,USEFUL_TOOLS_SPECTRAL_FROZEN_RUN='1',USEFUL_TOOLS_SPECTRAL_REPOSITORY_ROOT=str(repository))
    os.execvpe(sys.executable,[sys.executable,str(frozen/'validate-packaged-spectral.py'),*sys.argv[1:]],frozen_env)
from spectral_validation import COMMUNITY_MOD_CONTROL, classic_beddium_observation, SHADERS_MENU_CONTROL, acquire_client_admission, native_frame_observation, isolate_world_selection, install_weather_control, requested_wraith_floor, validation_mode, configure_shaders, activation_reported, set_option, set_instance_option, menu_action, complete_png, infuser_fixture_nbt, native_console_errors, enchanted_sword_fixture, pack_control_pattern, reserve_display, populated_infuser_client_slots, native_command_status, command_feedback, native_hurt_observation, validate_manifest_agent_logs, map_root_ocr_to_window

ROOT = Path(os.environ.get('USEFUL_TOOLS_SPECTRAL_REPOSITORY_ROOT',str(Path(__file__).resolve().parents[1])))
OCR_SCRIPT=Path(__file__).with_name('spectral-menu-ocr.py')
p = argparse.ArgumentParser(description=__doc__)
p.add_argument('--template', type=Path, required=True)
p.add_argument('--fml-config-source', type=Path, help='Copy an exact saved Forge/NeoForge FML configuration into this private run before startup')
p.add_argument('--artifact', type=Path, required=True)
p.add_argument('--artifact-provenance', type=Path, help='Hash-linked source record for an immutable copy of a built artifact')
p.add_argument('--minecraft-version')
p.add_argument('--java-path', type=Path)
p.add_argument('--world-source', type=Path, help='A version-matched disposable QA saves directory')
p.add_argument('--menu-world', action='store_true', help='Enter the copied world through the normal menus instead of Quick Play')
p.add_argument('--ocr-python', type=Path, default=Path('/tmp/usefultools-spectral-ocr/bin/python'),
               help='Python environment with rapidocr-onnxruntime for verified normal-menu navigation')
p.add_argument('--loader-uid', required=True)
p.add_argument('--loader-version', required=True)
p.add_argument('--client-mod', type=Path, action='append', default=[])
p.add_argument('--shader-pack', type=Path)
p.add_argument('--disable-shaders', action='store_true')
p.add_argument('--mode', choices=('baseline','disabled','active'))
p.add_argument('--shader-integration', choices=('iris','optifine','community'), default='iris')
p.add_argument('--jvm-arg', action='append', default=[])
p.add_argument('--probe-agent', type=Path, default=ROOT/'tests/spectral-qa/build/runtime-probe.jar')
p.add_argument('--entities', nargs='+', choices=('ghost','wraith'), default=['ghost','wraith'])
p.add_argument('--scenes', nargs='+', choices=('day','night','rain','dark-background','bright-background','indoor'),
               default=['day','night','rain','dark-background','bright-background','indoor'])
p.add_argument('--classic-qa', action='store_true', help='Use the separately packaged target-native classic scene controller')
p.add_argument('--native-damage-probe', action='store_true', help='Require fresh native client hurt timer before capturing modern damage flashes')
p.add_argument('--native-spectral-commands', action='store_true', help='Send modern scene commands through the optional isolated native client connection; require fresh server feedback')
p.add_argument('--entity-states', action='store_true', help='Capture movement, damage, invisibility, outline and spectator controls')
p.add_argument('--vanilla-outline-control', action='store_true', help='Compare an acknowledged native sheep glowing state with shaders active and disabled')
p.add_argument('--outline-interior-controls', action='store_true', help='Capture each native entity immediately before enabling glowing in the identical outline scene')
p.add_argument('--spectator-diagnostics', action='store_true', help='Compare active Iris and native spectator translucency in the same acknowledged entity state')
p.add_argument('--surfaces', action='store_true', help='Capture the audited block, item, armor, projectile and GUI surfaces')
p.add_argument('--lighting-qa', action='store_true', help='Run the unpackaged lighting acceptance fixtures in a native packaged runtime')
p.add_argument('--client-admission-wait-seconds',type=int,choices=range(3601),default=0,
               help='Wait up to 3600 seconds for one client; no profile or JVM starts while waiting')
p.add_argument('--lighting-classic-beddium', action='store_true', help='Require all 22 stages to sample the active native Beddium renderer cache')
p.add_argument('--lighting-scenes', action='store_true', help='Compare ordinary and Hardened Glowstone in a matched dark corridor')
p.add_argument('--lighting-equipment-scenes', action='store_true', help='Capture actual held and individually worn materials under the selected shader')
p.add_argument('--lighting-direct-client', action='store_true', help='Launch the native modern client from checksum-verified component libraries without account refresh')
p.add_argument('--lighting-material-only', action='store_true', help='After normal acceptance, retain world light and isolate shader material emission from companion light samples')
p.add_argument('--lighting-material-agent', type=Path, default=ROOT/'tests/dynamic-lighting/material-agent/build/material-control-agent.jar')
p.add_argument('--lighting-emission-map-control', type=Path, help='Private identical-RGB LabPBR map override with emission alpha zero, for shader A/B proof')
p.add_argument('--lighting-startup-only', action='store_true', help='Diagnose base-only native client loading without acceptance fixtures')
p.add_argument('--lighting-multiplayer', type=Path, help='Private two-client acceptance coordination directory')
p.add_argument('--server-address', help='Loopback acceptance server; joins through native Prism Quick Play')
p.add_argument('--shader-setting', action='append', default=[], help='Repeatable pack option NAME=VALUE, saved with the evidence')
p.add_argument('--outline-frame-trials', action='store_true', help='Measure acknowledged native Glowing entities as well as ordinary entities, with the same repeated native frame observer')
p.add_argument('--uncapped', action='store_true', help='Remove the FPS cap for a separate performance run')
p.add_argument('--mask-trials', action='store_true', help='Requires the separate spectral QA mod; compare disabled and three eye-edge strengths')
p.add_argument('--native-frame-trials', action='store_true', help='Sample the private native frame observer after warm-up; requires uncapped FPS')
p.add_argument('--frame-trials', action='store_true', help='Requires spectral QA and --uncapped; repeat paired 1800-frame trials indoors')
p.add_argument('--toggle-shaders', action='store_true', help='Exercise the Iris/Oculus K toggle with both entities indoors')
p.add_argument('--rain-diagnostics', action='store_true', help='Capture shader-off/restored controls while native rain remains active')
p.add_argument('--output', type=Path, required=True)
p.add_argument('--timeout', type=int, default=360)
p.add_argument('--log-jvm-errors', action='store_true', help='Privately log thrown JVM exceptions to diagnose linkage errors')
p.add_argument('--offline-name', default='StonyTark17', help='Use the existing QA world owner name for integrated-server commands')
p.add_argument('--gpu-zink', action='store_true', help='Use Vulkan GPU rendering with Xvfb presentation disabled in Kopper')
p.add_argument('--gpu-virtualgl', type=Path, help='Extracted VirtualGL package; use an EGL device with owned Xvfb presentation')
p.add_argument('--egl-device', choices=('egl0','egl1'), default='egl1')
a = p.parse_args()
if not any(arg.startswith('-XX:ActiveProcessorCount=') for arg in a.jvm_arg):
    # Keep native renderer, compiler and chunk-worker pools bounded in private
    # validation clients. The supported Java 8/17/21/25 runtimes accept this flag.
    a.jvm_arg.append('-XX:ActiveProcessorCount=2')
if a.gpu_zink and a.gpu_virtualgl:p.error('Choose one GPU backend')
if a.surfaces and a.loader_uid=='net.minecraftforge' and (a.minecraft_version or '').startswith('26.'):
    a.jvm_arg.append('-DusefulToolsSpectralMenuProbe=true')
gpu_prefix=[]
if a.gpu_virtualgl:
    vgl=a.gpu_virtualgl.resolve()
    if not (vgl/'opt/VirtualGL/bin/vglrun').is_file() or not (vgl/'usr/lib/libvglfaker.so').is_file():p.error('Incomplete extracted VirtualGL package')
    gpu_prefix=[str(vgl/'opt/VirtualGL/bin/vglrun'),'-ld',str(vgl/'usr/lib'),'-d',a.egl_device,'-c','proxy']
try:
    mode = validation_mode(a.mode, a.shader_pack, a.disable_shaders, a.client_mod)
    if a.spectator_diagnostics and (mode!='active' or a.shader_integration!='iris' or not a.entity_states or a.classic_qa):
        p.error('Spectator diagnostics require modern active Iris and --entity-states')
except ValueError as error:
    p.error(str(error))
if a.vanilla_outline_control and (mode != 'active' or a.shader_integration != 'optifine' or not a.native_spectral_commands): p.error('Vanilla outline control requires active OptiFine and native scene commands')
if a.outline_interior_controls and (not a.entity_states or not a.native_spectral_commands or a.minecraft_version == '1.7.10'): p.error('Outline interior controls require native modern entity-state commands')
if a.toggle_shaders and mode != 'active': p.error('Toggles require an active shader pack')
if a.lighting_classic_beddium and (a.minecraft_version!='1.7.10' or not a.lighting_qa or not a.lighting_scenes or a.lighting_startup_only): p.error('Classic Beddium requires full native 1.7.10 lighting acceptance and source movement/removal scenes')
if a.rain_diagnostics and mode != 'active': p.error('Rain diagnostics require an active shader pack')
if a.lighting_material_only and (mode!='active' or not a.lighting_qa or not a.lighting_scenes or a.lighting_startup_only): p.error('Material-only control requires an active shader, full native lighting acceptance and comparison scenes')
if a.lighting_material_only and not a.lighting_material_agent.is_file(): p.error('Build the private material-control agent before running')
if a.lighting_emission_map_control and (not a.lighting_material_only or not a.lighting_emission_map_control.is_file()): p.error('Emission-map control requires material-only mode and an existing private control pack')
if a.menu_world and not a.ocr_python.is_file(): p.error('Normal-menu entry requires --ocr-python with rapidocr-onnxruntime')
if a.native_frame_trials and not a.uncapped: p.error('Native frame trials require --uncapped')
if a.outline_frame_trials and (not a.native_frame_trials or not a.native_spectral_commands or a.classic_qa or a.lighting_qa): p.error('Outline frame trials require native modern spectral commands and uncapped native frame trials')
if a.frame_trials and not a.uncapped: p.error('Frame trials require --uncapped')
if a.classic_qa and not a.menu_world: p.error('Classic packaged clients require verified normal-menu entry')
provenance = None
if a.artifact_provenance:
    try:
        provenance=json.loads(a.artifact_provenance.read_text())
        if not isinstance(provenance,dict) or not all(key in provenance for key in ('artifact','artifact_sha256','source_sha256')):
            raise ValueError('Build provenance requires artifact, artifact_sha256 and source_sha256')
        if not isinstance(provenance['source_sha256'],dict):
            raise ValueError('Build provenance source_sha256 must be a mapping')
        if provenance['artifact_sha256']!=hashlib.sha256(a.artifact.read_bytes()).hexdigest():
            raise ValueError('Artifact does not match supplied build provenance')
    except (OSError,ValueError,TypeError) as error:
        p.error(str(error))
# Admission precedes profile copying, metadata downloads and virtual displays.
# Coordinated multiplayer explicitly needs its existing bounded two-client slots.
client_admission = None
if not a.lighting_multiplayer:
    try:
        client_admission = acquire_client_admission(Path.home()/'.cache/useful-tools-spectral-client.lock',a.client_admission_wait_seconds)
    except BlockingIOError:
        print('Another ordinary client test owns the admission slot; no test profile was created.',file=sys.stderr)
        raise SystemExit(75)
try:
    validate_manifest_agent_logs(a.jvm_arg,a.output)
except ValueError as error:
    p.error(str(error))
# Give interactive desktop work priority over private validation. Children
# inherit these process-local settings; no user session or default is changed.
os.setpriority(os.PRIO_PROCESS,0,max(10,os.getpriority(os.PRIO_PROCESS,0)))
io_priority={'requested':'idle','applied':False}
if shutil.which('ionice'):
    priority_result=subprocess.run(['ionice','-c','3','-p',str(os.getpid())],text=True,capture_output=True)
    io_priority.update(applied=priority_result.returncode==0,error=priority_result.stderr.strip())
out = a.output.resolve(); out.mkdir(parents=True, exist_ok=True)
if (out/'report.json').exists(): raise SystemExit('Use a fresh evidence directory')
run_id = uuid.uuid4().hex
marker = 'USEFUL_TOOLS_SPECTRAL_RUN_ID'
env = os.environ.copy()
for key in ('DISPLAY', 'WAYLAND_DISPLAY', 'WAYLAND_SOCKET', 'LIBGL_ALWAYS_SOFTWARE', 'GALLIUM_DRIVER', 'LIBGL_KOPPER_DRI2', 'LIBGL_KOPPER_DISABLE'): env.pop(key, None)
env.update(ALSOFT_DRIVERS='null', QT_QPA_PLATFORM='xcb', **{marker:run_id})
if a.lighting_qa and not a.gpu_zink and not a.gpu_virtualgl:env['LP_NUM_THREADS']='2'
if a.gpu_zink: env.update(GALLIUM_DRIVER='zink', LIBGL_KOPPER_DRI2='true', LIBGL_KOPPER_DISABLE='true')
launcher = Path.home()/'.local/share/PrismLauncher'
lighting_run_roots=Path.home()/'.cache/useful-tools-lighting-active-runs' if a.lighting_qa else None
if lighting_run_roots:lighting_run_roots.mkdir(mode=0o700,parents=True,exist_ok=True)
spectral_run_roots = Path.home()/'.cache/useful-tools-spectral-active-runs'
if not lighting_run_roots:
    spectral_run_roots.mkdir(mode=0o700, parents=True, exist_ok=True)
work = Path(tempfile.mkdtemp(prefix='spectral-prism-',dir=lighting_run_roots or spectral_run_roots))
instance = work/'instances'/'spectral'; game = instance/'minecraft'
report = dict(status='not_run', revision=subprocess.check_output(['git','rev-parse','HEAD'],cwd=ROOT,text=True).strip(),
              run_id=run_id, invocation=sys.argv, mode=mode, shader_integration=a.shader_integration,
              loader_uid=a.loader_uid, loader_version=a.loader_version, work_dir=str(work),
              framebuffer='1280x720x24', audio_backend='ALSOFT_DRIVERS=null', wayland_unset=True, gpu_zink=a.gpu_zink,
              software_render_threads=env.get('LP_NUM_THREADS'),
              test_active_processors=next(arg.split('=',1)[1] for arg in a.jvm_arg if arg.startswith('-XX:ActiveProcessorCount=')),
              commands=[], captures=[], visual_verdict='not reviewed')
if gpu_prefix:report['gpu_virtualgl']={'path':str(vgl),'egl_device':a.egl_device,'transport':'proxy','wrapper_sha256':hashlib.sha256((vgl/'opt/VirtualGL/bin/vglrun').read_bytes()).hexdigest(),'interposer_sha256':hashlib.sha256((vgl/'usr/lib/libvglfaker.so').read_bytes()).hexdigest()}
xvfb = None; proc = None; display_reservation = None; gpu_lock = None; software_slot_lock = None
report['process_resource_priority']={'nice':os.getpriority(os.PRIO_PROCESS,0),'io':io_priority,
                                     'scope':'Private harness and inherited client/display children only'}

def digest(path): return hashlib.sha256(path.read_bytes()).hexdigest()
harness_sources = out/'harness-source'
harness_sources.mkdir(parents=True, exist_ok=True)
report['harness_sha256'] = {}
for name in ('validate-packaged-spectral.py','spectral_validation.py','spectral-menu-ocr.py','lighting_shader_diagnostics.py'):
    source = Path(__file__).with_name(name)
    frozen = harness_sources/name
    frozen.write_bytes(source.read_bytes())
    report['harness_sha256']['scripts/'+name] = digest(frozen)
report['harness_source_directory']=str(harness_sources.resolve())
report['harness_original_source_directory']=str(Path(__file__).parent)
# OCR is a subprocess on each menu capture. Keep its implementation immutable
# within this run even when another task edits the shared checkout.
OCR_SCRIPT = harness_sources/'spectral-menu-ocr.py'
report['working_tree_diff_sha256'] = hashlib.sha256(subprocess.check_output(
    ['git','diff','--binary','HEAD'], cwd=ROOT)).hexdigest()
artifact_root = a.artifact.resolve().parent.parent.parent
if a.artifact_provenance:
    report['source_sha256']=provenance['source_sha256']
    report['artifact_provenance_sha256']=digest(a.artifact_provenance)
    report['artifact_original_path']=provenance['artifact']
    if provenance.get('private_emission_control'):
        import io,zipfile
        from PIL import Image
        original=Path(provenance['original_artifact'])
        if not a.lighting_material_only or digest(original)!=provenance['original_artifact_sha256']:raise SystemExit('Private emission control requires a verified original artifact and material-only mode')
        with zipfile.ZipFile(original) as before,zipfile.ZipFile(a.artifact) as after:
            if set(before.namelist())!=set(after.namelist()):raise SystemExit('Private emission control changed archive entries')
            changed={name for name in before.namelist() if before.read(name)!=after.read(name)}
            expected={name for name in before.namelist() if name.startswith('assets/usefultoolsmod/textures/') and name.endswith('_s.png') and 'hglow' in name}
            if changed!=expected or len(changed)!=13:raise SystemExit('Private emission control changed content beyond 13 material maps')
            for name in changed:
                first=Image.open(io.BytesIO(before.read(name))).convert('RGBA');second=Image.open(io.BytesIO(after.read(name))).convert('RGBA')
                if first.size!=second.size or first.convert('RGB').tobytes()!=second.convert('RGB').tobytes() or second.getchannel('A').getextrema()!=(0,0):raise SystemExit('Invalid private material control: '+name)
        report['private_emission_control']={'original_artifact':str(original),'original_artifact_sha256':digest(original),'control_artifact_sha256':digest(a.artifact),'unchanged_production_classes_and_diffuse_assets':True,'changed_resource_paths':sorted(changed)}

if (artifact_root/'src').is_dir():
    report['source_sha256'] = {str(f.relative_to(ROOT)):digest(f) for f in
                             (artifact_root/'src').rglob('*') if f.is_file()}

def run(args, **kwargs):
    return subprocess.run(args, env=env, text=True, capture_output=True, **kwargs)
def game_log():
    path = game/'logs/latest.log'
    text=path.read_text(errors='replace') if path.exists() else ''
    if a.minecraft_version=='1.7.10':
        fml=game/'logs/fml-client-latest.log'
        if fml.exists():
            # Classic sends shader and fixture acknowledgements only to FML's
            # log. Preserve those controls without copying launcher arguments.
            text+='\n'+'\n'.join(line for line in fml.read_text(errors='replace').splitlines() if '[Shaders]' in line or 'USEFUL_TOOLS_LIGHTING' in line or 'SPECTRAL_QA' in line or re.search(r'joined the game|logged in with entity id',line,re.I) or re.match(r'^\d+:\d+.*error:',line))
    return text
def owned_java():
    found=[]
    for entry in Path('/proc').iterdir():
        if not entry.name.isdigit(): continue
        try:
            values=(entry/'environ').read_bytes().split(b'\0')
            cmd=(entry/'cmdline').read_bytes()
        except (OSError, ProcessLookupError): continue
        if f'{marker}={run_id}'.encode() in values and b'java' in cmd:
            assert f'DISPLAY={env["DISPLAY"]}'.encode() in values
            assert b'ALSOFT_DRIVERS=null' in values
            assert not any(v.startswith(b'WAYLAND_DISPLAY=') for v in values)
            if a.gpu_virtualgl:
                assert ('VGL_DISPLAY='+a.egl_device).encode() in values, 'Game lost requested EGL route'
                try:loaded_maps=(entry/'maps').read_bytes()
                except (OSError,ProcessLookupError):continue
                if not loaded_maps:continue  # The JVM can exit between /proc reads.
                assert b'libvglfaker.so' in loaded_maps, 'GPU interposer absent from game JVM'
            found.append(int(entry.name))
    return found

def window():
    if xvfb.poll() is not None:
        raise RuntimeError('Owned Xvfb server exited; input operation stopped')
    if not owned_java():
        raise RuntimeError('Game JVM exited before input; inspect native startup or crash evidence')
    r=run(['xdotool','search','--onlyvisible','--name','^Minecraft'])
    ids=r.stdout.strip().splitlines()
    if not ids: raise RuntimeError('No Minecraft window on owned display')
    wid=ids[-1]
    run(['xdotool','windowfocus','--sync',wid],check=True)
    return wid

def key(*keys):
    window();run(['xdotool','key',*keys],check=True);time.sleep(.2)
def command(text, expected):
    if a.lighting_qa and a.minecraft_version!='1.7.10' and 'USEFUL_TOOLS_LIGHTING_NATIVE_COMMAND_CONTROLLER_READY' in game_log():
        token=uuid.uuid4().hex;request=work/'lighting-native-command.request';temporary=request.with_suffix('.tmp');temporary.write_text(token+'\n'+text+'\n');temporary.replace(request)
        marker_text='USEFUL_TOOLS_LIGHTING_NATIVE_COMMAND_ACK token='+token;failure='USEFUL_TOOLS_LIGHTING_NATIVE_COMMAND_FAIL token='+token;deadline=time.monotonic()+120
        while time.monotonic()<deadline:
            log=game_log()
            if failure in log:
                if text=='kill @e[type=!minecraft:player]' and re.search(r'No entit(?:y|ies)',log[log.index(failure):],re.I):
                    report['commands'].append(dict(command=text,accepted=True,native_dispatcher_outcome='No non-player entities matched the cleanup selector'));return
                raise RuntimeError('Native scene command failed: '+text)
            if marker_text in log:
                result=re.search(re.escape(marker_text)+r' result=(-?\d+)',log)
                if not result:raise RuntimeError('Native command result missing: '+text)
                value=int(result.group(1));report['commands'].append(dict(command=text,accepted=True,native_dispatcher_acknowledgement=marker_text,native_result=value));return value
            time.sleep(.2)
        raise RuntimeError('Native scene command not acknowledged: '+text)
    before=Counter(game_log().splitlines())
    token=None
    if a.native_spectral_commands:
        window()  # Verify owned display, route and live game before dispatch.
        token=uuid.uuid4().hex
        request=work/'spectral-state.log.command'
        temporary=request.with_suffix('.tmp')
        temporary.write_text(token+'\n'+text+'\n');temporary.replace(request)
    else:
        key('t'); time.sleep(.7)
        window();run(['xdotool','type','--clearmodifiers','--delay','1','/'+text],check=True);time.sleep(.3)
        key('Return')
    deadline=time.monotonic()+15
    while time.monotonic()<deadline:
        if token:
            acknowledgement=work/'spectral-state.log.command-ack'
            transport=native_command_status(acknowledgement.read_text() if acknowledgement.exists() else '',token)
            if transport=='FAILED':
                report['commands'].append(dict(command=text,accepted=False,native_transport='failed'))
                raise RuntimeError('Native client command transport failed: '+text)
            if transport!='SENT':time.sleep(.1);continue
        feedback=command_feedback(before,game_log(),expected)
        if feedback=='rejected':
            report['commands'].append(dict(command=text,accepted=False));shot('rejected-command-'+str(len(report['commands'])));raise RuntimeError('Command rejected: '+text)
        if feedback=='accepted':
            evidence=dict(command=text,accepted=True)
            if token:
                evidence.update(native_transport='client connection',request_token=token,
                                native_method=acknowledgement.read_text().splitlines()[2])
            report['commands'].append(evidence); return
        time.sleep(.1 if token else .3)
    report['commands'].append(dict(command=text,accepted=False))
    shot('command-failed-'+str(len(report['commands'])))
    raise RuntimeError('Command not acknowledged: '+text)
def shot(name, transient=False):
    before=set((game/'screenshots').glob('*.png'))
    if transient:
        # HurtTime lasts only ten ticks. Do not spend that interval in the
        # normal input settling delay after the acknowledged damage packet.
        window();run(['xdotool','key','F2'],check=True)
    else:
        key('F2')
    deadline=time.monotonic()+30;retry_at=time.monotonic()+8
    while time.monotonic()<deadline:
        if time.monotonic()>retry_at:key('F2');retry_at=time.monotonic()+8
        new=set((game/'screenshots').glob('*.png'))-before
        if new:
            f=max(new,key=lambda x:x.stat().st_mtime_ns)
            pixels=f.read_bytes()
            if complete_png(pixels):
                (out/(name+'.png')).write_bytes(pixels);report['captures'].append(name+'.png')
                report.setdefault('capture_sha256', {})[name+'.png'] = digest(out/(name+'.png'))
                # Explicit owned DISPLAY; preserve the full virtual framebuffer too.
                framebuffer = out/(name+'-framebuffer.png')
                run(['import','-display',env['DISPLAY'],'-window','root',str(framebuffer)],check=True)
                report.setdefault('framebuffer_sha256', {})[framebuffer.name] = digest(framebuffer)
                return
        time.sleep(.3)
    raise RuntimeError('No fresh framebuffer capture: '+name)
def click(x,y):
    wid=window();run(['xdotool','mousemove','--window',wid,str(x),str(y),'mousedown','1'],check=True)
    # Classic GuiSlot polls Mouse.isButtonDown during drawScreen instead of
    # consuming queued button events. A zero-duration click can be missed.
    time.sleep(.6 if a.minecraft_version=='1.7.10' else .15)
    run(['xdotool','mouseup','1'],check=True)

def menu_words(name):
    result=run([str(a.ocr_python),str(OCR_SCRIPT),str(out/(name+'.png'))])
    if result.returncode:
        (out/(name+'-ocr-error.log')).write_text(result.stderr)
        raise RuntimeError('Menu OCR failed; diagnostics saved for '+name)
    words=json.loads(result.stdout)
    if not words:
        # Some OptiFine builds omit open menus from F2, although shot() also
        # retained the fresh owned Xvfb root with those controls visible.
        framebuffer=out/(name+'-framebuffer.png')
        if framebuffer.is_file():
            pixels=framebuffer.read_bytes()
            if not complete_png(pixels):raise RuntimeError('Incomplete owned menu framebuffer: '+name)
            frame_size=struct.unpack('>II',pixels[16:24])
            wid=window()
            shell=run(['xdotool','getwindowgeometry','--shell',wid],check=True).stdout
            geometry=dict(line.split('=',1) for line in shell.splitlines() if '=' in line)
            native=run([str(a.ocr_python),str(OCR_SCRIPT),str(framebuffer)],check=True)
            root_words=json.loads(native.stdout)
            words=map_root_ocr_to_window(root_words,geometry,frame_size)
            report.setdefault('menu_ocr_fallbacks',[]).append(dict(
                frame=framebuffer.name,framebuffer_sha256=digest(framebuffer),
                window_id=wid,window_geometry={key:int(geometry[key]) for key in ('X','Y','WIDTH','HEIGHT')},
                root_words=len(root_words),owned_window_words=len(words)))
    native=work/'spectral-state.log.menu'
    if (a.classic_qa or a.lighting_qa) and native.exists():
        lines=native.read_text().splitlines()
        if lines and 0 <= time.time()*1000-int(lines[0]) < 2000:
            for line in lines[1:]:
                label,x,y=line.split('\t')
                label=base64.b64decode(label).decode('utf-8')
                words=[w for w in words if abs(w['x']-float(x))>12 or abs(w['y']-float(y))>12]
                words.append(dict(text=label,x=float(x),y=float(y),confidence=1,source='native_acceptance_gui'))
            (out/(name+'-native-menu.txt')).write_text('\n'.join(lines)+'\n')
    (out/(name+'-ocr.json')).write_text(json.dumps(words,indent=2)+'\n')
    return words

def navigate_menu(world, index):
    # Hover colors obscure the classic pixel-font labels for OCR.
    run(['xdotool','mousemove','--window',window(),'10','10'],check=True)
    name='world-entry-'+str(index)
    try:
        shot(name)
    except RuntimeError as error:
        if str(error).startswith('No fresh framebuffer capture:'):
            report.setdefault('menu_capture_retries',[]).append({'frame':name,'error':str(error)})
            return
        raise
    words=menu_words(name)
    from spectral_validation import menu_failure
    failure=menu_failure(words)
    if failure:
        report['world_entry_failure']={'frame':name+'.png','native_screen':failure}
        raise RuntimeError(failure)
    action=menu_action(words,world,single_world=report.get('copied_world_selection',{}).get('visible_acceptance_worlds')==1)
    if action:
        kind, word=action
        if not a.menu_world and kind not in ('confirm_experimental','acknowledge_loading_warnings'):return
        click(round(word['x']),round(word['y']))
        if kind=='open_world':
            # Use the named play control after selection instead of relying on
            # version-specific double-click timing (held classic clicks are longer).
            time.sleep(.5)
            try:click_control(r'^[PF]lay Sele[co]ted [WHM]orld$',name+'-play')
            except RuntimeError as error:
                if str(error).endswith('found 0') and re.search(r'joined the game|logged in with entity id',game_log(),re.I):
                    report.setdefault('menu_transition_retries',[]).append(dict(frame=name+'-play.png',error=str(error),native_result='World joined while the selected-world control was being reverified; no additional click sent'))
                    return
                raise
        report.setdefault('menu_actions',[]).append(dict(action=kind,control=word,frame=name+'.png'))

def verify_shader_state(expected):
    if expected:
        from lighting_shader_diagnostics import compiler_failures
        failures = compiler_failures(game_log())
        if failures:
            report['failure_layer'] = 'shader'
            raise RuntimeError('Shader compilation failed during active-pack validation: ' + failures[0])
    if not (work/'runtime-probe.jar').is_file():
        report['live_shader_state']='unverified: runtime probe absent'
        return
    deadline=time.monotonic()+5
    while time.monotonic()<deadline:
        probe=work/'spectral-state.log'
        if probe.exists():
            lines=probe.read_text().splitlines()
            if lines:
                match=re.search(r'time_ms=(\d+) active=(true|false) wraith_floor=(\d+|unavailable)',lines[-1])
                if match and time.time()*1000-int(match.group(1)) < 2000 and (match.group(2)=='true') == expected:
                    floor = requested_wraith_floor(a.jvm_arg)
                    if floor is not None and match.group(3) != str(floor):
                        raise RuntimeError('Renderer Wraith floor does not match requested JVM override')
                    report.setdefault('live_shader_states',[]).append(dict(time_ms=int(match.group(1)),active=expected,wraith_floor=None if match.group(3)=='unavailable' else int(match.group(3))))
                    return
        time.sleep(.2)
    raise RuntimeError('Actual renderer shader state did not match requested control')

def capture_scene(entity, scene):
    verify_shader_state(mode=='active')
    shot(entity+'-'+scene)
    if a.mask_trials:
        for strength in (1,2,3):
            command('spectralqa mask '+str(strength), r'SPECTRAL_QA_MASK '+str(strength))
            time.sleep(2);shot(entity+'-'+scene+'-mask'+str(strength))
        command('spectralqa mask 0', r'SPECTRAL_QA_MASK 0')

def native_frame_trials(entity, state='ordinary'):
    if not a.native_frame_trials:return
    for repeat in range(3):
        time.sleep(10)
        token=uuid.uuid4().hex
        request=out/'native-frame.request';temporary=request.with_suffix('.tmp')
        temporary.write_text(token+'\n1800\n');temporary.replace(request)
        deadline=time.time()+185
        while time.time()<deadline:
            response=out/'native-frame.response'
            if response.is_file():
                samples=native_frame_observation(response.read_text(),token)
                if samples is not None:
                    prefix=entity+('-outline' if state=='outline' else '')
                    raw=out/(prefix+'-native-frames-'+str(repeat)+'.json');raw.write_text(json.dumps(dict(token=token,samples_ns=samples),indent=2)+'\n')
                    ordered=sorted(samples)
                    report.setdefault('native_frame_trials',[]).append(dict(entity=entity,state=state,repeat=repeat,samples=1800,warmup_seconds=10,median_ns=(ordered[899]+ordered[900])/2,p95_ns=ordered[1709],raw_file=raw.name,sha256=digest(raw)))
                    break
            time.sleep(.1)
        else:raise RuntimeError('Native frame observer timed out')

def frame_trials(entity):
    if not a.frame_trials: return
    for repeat in range(3):
        for strength in (0,1,0,2,0,3):
            command('spectralqa mask '+str(strength),r'SPECTRAL_QA_MASK '+str(strength))
            time.sleep(10)
            before=len(game_log())
            command('spectralqa frames',r'SPECTRAL_QA_FRAMES_STARTED')
            deadline=time.monotonic()+120
            while time.monotonic()<deadline:
                feedback=game_log()[before:]
                match=re.search(r'SPECTRAL_QA_FRAMES_COMPLETE mask=(\d+) samples=(\d+) median_ns=(\d+) p95_ns=(\d+)',feedback)
                if match:
                    values=list(map(int,match.groups()))
                    if values[0] != strength or values[1] != 1800: raise RuntimeError('Frame trial sample/mask mismatch')
                    report.setdefault('frame_trials',[]).append(dict(entity=entity,repeat=repeat,mask=strength,samples=values[1],median_ns=values[2],p95_ns=values[3]))
                    break
                time.sleep(.2)
            else:raise RuntimeError('Frame trial timed out')
    command('spectralqa mask 0',r'SPECTRAL_QA_MASK 0')

def click_control(pattern, name, retry_from=None):
    # Retry a missed native transition only when its previous control remains
    # uniquely visible. Each attempt has its own fresh, retained framebuffer.
    for attempt in range(3 if retry_from else 1):
        frame = name if attempt == 0 else name + '-transition-retry-' + str(attempt)
        run(['xdotool','mousemove','--window',window(),'10','100'],check=True)
        time.sleep(.8)
        shot(frame)
        words=menu_words(frame)
        candidates=[w for w in words if w['confidence']>=.65 and re.search(pattern,w['text'],re.I)]
        if len(candidates)==1:
            word=candidates[0];click(round(word['x']),round(word['y']))
            report.setdefault('shader_menu_actions',[]).append(dict(control=word,frame=frame+'.png'))
            time.sleep(1)
            return
        previous=[w for w in words if w['confidence']>=.65 and retry_from and re.search(retry_from,w['text'],re.I)]
        if len(candidates) != 0 or len(previous) != 1 or attempt == 2:
            raise RuntimeError('Expected one verified control '+pattern+'; found '+str(len(candidates)))
        word=previous[0];click(round(word['x']),round(word['y']))
        report.setdefault('shader_menu_actions',[]).append(dict(control=word,frame=frame+'.png',retry_transition=True))
        time.sleep(2)

def capture_owned_launcher_tail():
    """Retain early Java failures that Prism keeps only in its console widget."""
    if proc is None or proc.poll() is not None:return
    route=(Path('/proc')/str(proc.pid)/'environ').read_bytes().split(b'\0')
    assert f'{marker}={run_id}'.encode() in route
    assert f'DISPLAY={env["DISPLAY"]}'.encode() in route
    owned_windows=run(['xdotool','search','--onlyvisible','--pid',str(proc.pid)])
    if not owned_windows.stdout.strip():return
    before=out/'failure-launcher-console-before.png'
    run(['import','-display',env['DISPLAY'],'-window','root',str(before)],check=True)
    words=json.loads(run([str(a.ocr_python),str(OCR_SCRIPT),str(before)],check=True).stdout)
    if not any(re.search(r'Minecraft Log',w['text'],re.I) for w in words):return
    buttons=[w for w in words if re.fullmatch(r'Bottom',w['text'],re.I) and w['confidence']>=.65]
    if len(buttons)!=1:return
    button=buttons[0]
    bounded=False
    for wid in owned_windows.stdout.split():
        geometry=run(['xdotool','getwindowgeometry','--shell',wid],check=True).stdout
        values=dict(line.split('=',1) for line in geometry.splitlines() if '=' in line)
        x,y,width,height=(int(values[k]) for k in ('X','Y','WIDTH','HEIGHT'))
        bounded |= x<=button['x']<x+width and y<=button['y']<y+height
    if not bounded:return
    run(['xdotool','mousemove','--sync',str(round(button['x'])),str(round(button['y']))],check=True)
    run(['xdotool','click','1'],check=True);time.sleep(1)
    tail=out/'failure-launcher-console-tail.png'
    run(['import','-display',env['DISPLAY'],'-window','root',str(tail)],check=True)
    report['launcher_console_tail']={'capture':tail.name,'sha256':digest(tail),'owned_launcher_pid':proc.pid,'verified_control':button}
    # This clipboard belongs to the owned Xvfb server. Extract only native
    # exception/stack lines; never retain Prism's full argument/authentication log.
    copies=[w for w in words if re.fullmatch(r'Copy',w['text'],re.I) and w['confidence']>=.65]
    if len(copies)==1 and shutil.which('xclip'):
        copy=copies[0]
        if x<=copy['x']<x+width and y<=copy['y']<y+height:
            run(['xdotool','mousemove','--sync',str(round(copy['x'])),str(round(copy['y']))],check=True)
            run(['xdotool','click','1'],check=True);time.sleep(.3)
            copied=run(['xclip','-display',env['DISPLAY'],'-selection','clipboard','-o'],timeout=3,check=True)
            errors=native_console_errors(copied.stdout)
            if errors:
                native_errors=out/'launcher-native-exceptions.txt';native_errors.write_text(errors)
                report['launcher_native_exceptions']={'file':native_errors.name,'sha256':digest(native_errors),'isolated_clipboard_display':env['DISPLAY']}

def enter_pause_options(prefix):
    for attempt in range(4):
        key('Escape');time.sleep(1)
        frame=prefix+'options'+('-pause-retry-'+str(attempt) if attempt else '')
        run(['xdotool','mousemove','--window',window(),'10','100'],check=True)
        shot(frame)
        words=menu_words(frame)
        controls=[w for w in words if w['confidence']>=.65 and re.search(r'^Optio(?:n|ni|i)s',w['text'],re.I)]
        if len(controls)>1:raise RuntimeError('Ambiguous native pause Options control')
        if len(controls)==1:
            word=controls[0];click(round(word['x']),round(word['y']))
            report.setdefault('shader_menu_actions',[]).append(dict(control=word,frame=frame+'.png'))
            time.sleep(1)
            return
        report.setdefault('pause_menu_retries',[]).append(dict(frame=frame+'.png',reason='Options not visible in fresh owned frame'))
    raise RuntimeError('Native pause Options did not become ready')

def optifine_toggle(enabled, entity):
    prefix=entity+('-enable-menu-' if enabled else '-disable-menu-')
    enter_pause_options(prefix)
    # The native pixel-font V is sometimes recognized as W. Preserve the
    # confidence and uniqueness checks and retain the actual OCR label.
    click_control(r'^[VW]ideo Settings',prefix+'video')
    click_control(SHADERS_MENU_CONTROL,prefix+'shaders')
    if enabled:
        # OptiFine displays the exact ZIP filename in its pack list.
        click_control(pack_control_pattern(a.shader_pack.name),prefix+'pack',retry_from=SHADERS_MENU_CONTROL)
    else:click_control(r'^[OQ]FF$',prefix+'off',retry_from=SHADERS_MENU_CONTROL)
    time.sleep(8);verify_shader_state(enabled)
    click_control(r'^Done$',prefix+'done')
    key('Escape');key('Escape');key('Escape')
    time.sleep(2)

def community_toggle(enabled, entity):
    prefix=entity+('-community-enable-' if enabled else '-community-disable-')
    key('Escape');time.sleep(1)
    click_control(r'^Mods$',prefix+'mods')
    # Forge clips this list entry at the panel edge at 1280x720.
    # Require its unique visible versioned prefix; the following Config
    # screen and live service state establish the integration identity.
    click_control(COMMUNITY_MOD_CONTROL,prefix+'oculus')
    click_control(r'^Config$',prefix+'config')
    # The published screen exposes a cycle button; a fresh OCR match proves
    # its current value before changing it, and the live service proves Save.
    click_control(r'^Shaders\s*:\s*'+('OFF' if enabled else 'ON')+'$',prefix+'state')
    click_control(r'^Save$',prefix+'save')
    time.sleep(8);verify_shader_state(enabled)
    click_control(r'^Done$',prefix+'done')
    key('Escape');key('Escape');time.sleep(2)

def classic_scenes():
    command('spectralqa prepare',r'SPECTRAL_QA_ACCEPTED')
    if 'SPECTRAL_QA_PRECIPITATION_BIOME plains acknowledged' not in game_log():
        raise RuntimeError('Classic native precipitation biome not acknowledged')
    report['precipitation_fixture']={'biome':'minecraft:plains','acknowledged':True,'mechanism':'native chunk biome arrays and S21 full chunk packets'}
    key('F1')
    for entity in a.entities:
        for state in a.scenes:
            command('spectralqa scene '+entity+' '+state,r'SPECTRAL_QA_ACCEPTED')
            time.sleep(4);capture_scene(entity,state)
        if a.toggle_shaders:
            optifine_toggle(False,entity);shot(entity+'-shaders-off')
            optifine_toggle(True,entity);shot(entity+'-shaders-restored')
        if a.entity_states:
            for state in ('movement','damage','invisible'):
                command('spectralqa scene '+entity+' '+state,r'SPECTRAL_QA_ACCEPTED')
                if state!='damage':time.sleep(1)
                shot(entity+'-'+state)
            report['classic_native_state_dispositions']={
                'outline':'No native glowing/outline entity state in Minecraft 1.7.10; retained for explicit disposition.',
                'spectator':'No native spectator game mode in Minecraft 1.7.10; retained for explicit disposition.'}
            api_evidence=ROOT/'validation/shader-support-2.3.2/recovery/implementation/research/classic-native-states/provenance.json'
            if api_evidence.is_file():report['classic_native_state_api_evidence']={'path':str(api_evidence),'sha256':digest(api_evidence)}
        command('spectralqa remove',r'SPECTRAL_QA_ACCEPTED')
    if a.surfaces:
        for state in ('night','lit','day'):
            command('spectralqa surfaces '+state,r'SPECTRAL_QA_ACCEPTED')
            if 'SPECTRAL_QA_LANTERNS ectoplasm=usefultoolsmod:ectoplasm_lantern soul=usefultoolsmod:soul_lantern' not in game_log():
                raise RuntimeError('Classic native lantern registry fixtures not acknowledged')
            report['surface_registry_fixture']=['ectoplasm_lantern','soul_lantern','mining_charge','spectral_infuser','ectoplasm_block','refined_ectoplasm_block','hglow_block']
            time.sleep(2)
            if state=='lit':command('spectralqa verify-lit',r'SPECTRAL_QA_ACCEPTED')
            shot('surfaces-blocks-'+state)
        command('spectralqa surfaces armor',r'SPECTRAL_QA_ACCEPTED')
        key('F5');key('F5');time.sleep(1);shot('surfaces-worn-armor-and-glint');key('F5')
        command('spectralqa surfaces inventory',r'SPECTRAL_QA_ACCEPTED')
        key('F1');key('e');time.sleep(5);shot('surfaces-inventory-previews')
        inventory_words=menu_words('surfaces-inventory-previews')
        if not any(re.search(r'Crafting',w['text'],re.I) for w in inventory_words):
            raise RuntimeError('Classic native survival inventory preview not acknowledged')
        report['inventory_preview_fixture']={'native_survival_inventory':True,'armor_slots':4}
        key('Escape');key('F1')
        command('spectralqa surfaces creative',r'SPECTRAL_QA_ACCEPTED')
        command('spectralqa surfaces projectiles',r'SPECTRAL_QA_ACCEPTED')
        time.sleep(1);shot('surfaces-projectiles')
        command('spectralqa surfaces gui',r'SPECTRAL_QA_ACCEPTED')
        if 'SPECTRAL_QA_INFUSER_GUI input=iron_sword fuel=ectoplasm slots_acknowledged' not in game_log():
            raise RuntimeError('Classic Infuser GUI inventory fixture not acknowledged')
        report['infuser_inventory_fixture']={'native_slots_acknowledged':True,'input':'minecraft:iron_sword','fuel':'usefultoolsmod:ectoplasm'}
        time.sleep(1);shot('surfaces-infuser-gui')
        words=menu_words('surfaces-infuser-gui')
        if not any(re.search(r'Spectral.*Infus',w['text'],re.I) for w in words):
            raise RuntimeError('Native classic Infuser GUI not visually acknowledged')
        key('Escape');command('spectralqa remove',r'SPECTRAL_QA_ACCEPTED')

try:
    # Let the existing evidence-preserving finally block handle cancellation,
    # including SIGTERM from a stopped batch rather than orphaning the JVM.
    def cancel_run(signum, frame):
        raise KeyboardInterrupt('Client test cancelled by signal '+str(signum))
    signal.signal(signal.SIGTERM, cancel_run)
    shutil.copytree(a.template, instance, ignore=shutil.ignore_patterns('screenshots','logs','crash-reports','natives','.cache'))
    if a.fml_config_source:
        if a.loader_uid not in ('net.minecraftforge', 'net.neoforged'):
            raise ValueError('FML configuration requires a Forge or NeoForge runtime')
        source = a.fml_config_source.resolve(strict=True)
        destination = game/'config'/'fml.toml'
        destination.parent.mkdir(parents=True, exist_ok=True)
        shutil.copy2(source, destination)
        report['fml_configuration'] = {'source':str(source), 'sha256':digest(source),
                                       'private_destination':str(destination)}
    for name in ('assets','java'): (work/name).symlink_to(launcher/name,target_is_directory=True)
    if a.loader_uid in ('net.minecraftforge','net.neoforged') and (a.minecraft_version or '').startswith(('1.20.','1.21.')):
        # ForgeWrapper canonicalizes its module path. A directory symlink
        # gives BootstrapLauncher two different names for the same module.
        subprocess.run(['cp','-a','--reflink=auto',str(launcher/'libraries'),str(work/'libraries')],check=True)
        report['private_library_paths']='private copy with reflinks where supported; no shared symlink alias on the module path'
    else:
        (work/'libraries').symlink_to(launcher/'libraries',target_is_directory=True)
    # Metadata resolution must not race another private launcher or alter the
    # user's cached component records. Freeze a private copy for every run.
    shutil.copytree(launcher/'meta',work/'meta')
    report['private_component_metadata']=True
    # Private launcher root avoids IPC routing into the user's running launcher.
    # Credentials stay private and are never included in evidence.
    for name in ('accounts.json','prismlauncher.cfg'):
        if name=='accounts.json' and a.lighting_direct_client:
            # Use an isolated native Offline account after checking the owner's
            # existing entitlement. Repeated QA launches must not refresh their
            # Microsoft tokens or change their real launcher accounts.
            original_accounts=json.loads((launcher/name).read_text())
            entitlement=next((account['entitlement'] for account in original_accounts.get('accounts',[])
                if account.get('type')=='MSA' and account.get('entitlement',{}).get('ownsMinecraft')
                and account.get('entitlement',{}).get('canPlayMinecraft')),None)
            if entitlement is None:raise RuntimeError('Existing licensed Minecraft account entitlement unavailable for isolated offline QA')
            offline_uuid=bytearray(hashlib.md5(('OfflinePlayer:'+a.offline_name).encode()).digest())
            offline_uuid[6]=(offline_uuid[6]&15)|48;offline_uuid[8]=(offline_uuid[8]&63)|128
            account=dict(active=True,type='Offline',entitlement=entitlement,
                profile=dict(id=bytes(offline_uuid).hex(),name=a.offline_name,capes=[],skin=dict(id='',url='',variant='')),
                ygg=dict(token='0',iat=int(time.time()),extra=dict(userName=a.offline_name,clientToken=uuid.uuid4().hex)))
            (work/name).write_text(json.dumps(dict(formatVersion=3,accounts=[account])))
            report['account_route']='native local test client; existing Minecraft owner entitlement checked; no Microsoft credentials copied or refreshed'
        else:shutil.copy2(launcher/name,work/name)
        (work/name).chmod(0o600)
    pack=json.loads((instance/'mmc-pack.json').read_text())
    pack['components']=[c for c in pack['components'] if c['uid'] not in ('net.minecraftforge','net.neoforged','net.fabricmc.fabric-loader','org.quiltmc.quilt-loader','net.optifine')]
    if a.loader_uid in ('net.minecraftforge','net.neoforged'):
        pack['components']=[c for c in pack['components'] if c['uid'] != 'net.fabricmc.intermediary']
    if a.minecraft_version:
        for c in pack['components']:
            if c['uid'] in ('net.minecraft','net.fabricmc.intermediary'):
                c['version']=a.minecraft_version
                for cache_key in ('cachedVersion','cachedRequires'):c.pop(cache_key,None)
    if a.minecraft_version:
        lwjgl_version='3.4.1' if a.minecraft_version.startswith('26.') else ('3.3.3' if a.minecraft_version=='1.21.1' else '3.3.2' if a.minecraft_version=='1.20.2' else '3.3.1')
        for c in pack['components']:
            if c['uid']=='org.lwjgl3': c['version']=lwjgl_version;c.pop('cachedVersion',None)
    prism_loader_version=a.loader_version
    if a.loader_uid=='net.minecraftforge' and a.minecraft_version and prism_loader_version.startswith(a.minecraft_version+'-'):
        prism_loader_version=prism_loader_version[len(a.minecraft_version)+1:]
    pack['components'].append(dict(uid=a.loader_uid,version=prism_loader_version))
    if a.lighting_qa:
        metadata=[]
        for component in pack['components']:
            uid,version=component['uid'],component['version']
            directory=work/'meta'/uid
            index=directory/'index.json'
            if not index.is_file():
                directory.mkdir(parents=True,exist_ok=True)
                index.write_bytes(urllib.request.urlopen('https://meta.prismlauncher.org/v1/'+uid+'/index.json',timeout=45).read())
            entries=json.loads(index.read_text())['versions']
            entry=next((v for v in entries if v['version']==version),None)
            if entry is None:raise RuntimeError('Pinned launcher metadata absent from index: '+uid+' '+version)
            destination=directory/(version+'.json')
            expected=entry['sha256']
            restored=not destination.is_file() or digest(destination)!=expected
            if restored:
                data=urllib.request.urlopen('https://meta.prismlauncher.org/v1/'+uid+'/'+version+'.json',timeout=45).read()
                if hashlib.sha256(data).hexdigest()!=expected:raise RuntimeError('Downloaded launcher metadata checksum mismatch: '+uid+' '+version)
                destination.write_bytes(data)
            metadata.append(dict(uid=uid,version=version,sha256=expected,restored=restored))
        report['private_verified_metadata']=metadata
    report['prism_loader_version']=prism_loader_version
    (instance/'mmc-pack.json').write_text(json.dumps(pack,indent=2));report['components']=pack
    cfg=(instance/'instance.cfg').read_text()
    for name,value in dict(name='Isolated spectral QA',OverrideWindow='true',MinecraftWinWidth='854',MinecraftWinHeight='480',LaunchMaximized='false',OverrideEnv='false',OverrideMemory='true',MaxMemAlloc='2048').items():
        cfg=set_instance_option(cfg,name,value)
    if a.java_path:
        cfg=re.sub(r'^JavaPath=.*$', 'JavaPath='+str(a.java_path), cfg, flags=re.M)
        cfg=re.sub(r'^OverrideJavaLocation=.*$', 'OverrideJavaLocation=true', cfg, flags=re.M)
    if a.log_jvm_errors:
        cfg=re.sub(r'^OverrideJavaArgs=.*$', 'OverrideJavaArgs=true', cfg, flags=re.M)
        cfg=re.sub(r'^JvmArgs=.*$', 'JvmArgs=-Xlog:exceptions=info:file='+str(work/'jvm-exceptions.log'), cfg, flags=re.M)
    if a.probe_agent.is_file():
        shutil.copy2(a.probe_agent,work/'runtime-probe.jar')
        a.jvm_arg.append('-javaagent:'+str(work/'runtime-probe.jar')+'='+str(work/'spectral-state.log'))
        report['probe_agent_sha256']=digest(work/'runtime-probe.jar')
    if a.native_spectral_commands:
        if a.minecraft_version=='1.7.10' or a.classic_qa or a.lighting_qa or not a.probe_agent.is_file():
            raise RuntimeError('Native spectral transport requires the modern isolated probe and spectral fixture')
        a.jvm_arg.append('-DusefulToolsSpectralCommands=true')
    if a.native_damage_probe:
        if not a.native_spectral_commands:raise RuntimeError('Native damage proof requires native spectral transport')
        a.jvm_arg.append('-DusefulToolsSpectralHurtProbe=true')
    if a.lighting_qa and a.minecraft_version!='1.7.10':
        a.jvm_arg.append('-DusefulToolsLightingQaMenu='+str(work/'spectral-state.log.menu'))
        a.jvm_arg.append('-DusefulToolsLightingQaEquipmentScene='+str(work/'lighting-equipment-scene.request'))
        a.jvm_arg.append('-DusefulToolsLightingQaNativeCommands='+str(work/'lighting-native-command.request'))
    if a.lighting_material_only:
        shutil.copy2(a.lighting_material_agent,work/'lighting-material-control-agent.jar')
        a.jvm_arg.append('-javaagent:'+str(work/'lighting-material-control-agent.jar')+'='+str(work/('lighting-material-control-'+a.shader_integration+'.flag')))
        report['lighting_material_control']={'agent_sha256':digest(work/'lighting-material-control-agent.jar'),'production_artifact_changed':False,'activation':'pending normal native acceptance','render_chunk_reload':'pending'}
    if a.lighting_multiplayer:
        a.jvm_arg.append('-DusefulToolsLightingQaMultiplayer='+str(a.lighting_multiplayer.resolve()))
    if a.jvm_arg:
        cfg=set_instance_option(cfg, 'OverrideJavaArgs', 'true')
        inherited=re.search(r'^JvmArgs=(.*)$', cfg, re.M)
        cfg=set_instance_option(cfg, 'JvmArgs', ((inherited.group(1)+' ') if inherited else '')+' '.join(a.jvm_arg))
    (instance/'instance.cfg').write_text(cfg)
    shutil.rmtree(game/'mods',ignore_errors=True);(game/'mods').mkdir()
    artifacts=[a.artifact,*a.client_mod]
    if a.lighting_material_only:
        import zipfile
        for artifact in artifacts:
            with zipfile.ZipFile(artifact) as jar:
                if 'fabric.mod.json' in jar.namelist() and json.loads(jar.read('fabric.mod.json')).get('id') in {'lambdynlights','lambdynlights_api'}:
                    raise RuntimeError('Remove LambDynamicLights from the separate material-only profile so it cannot add held emission')
    for f in artifacts: shutil.copy2(f,game/'mods'/f.name)
    report['artifacts']={str(f.resolve()):digest(f) for f in artifacts}
    # Temporary launch roots may disappear on reboot. Preserve exact inputs
    # separately from disposable worlds so later evidence audits remain possible.
    input_store=Path.home()/'.cache/useful-tools-lighting-inputs'
    preserved={}
    inputs=[*artifacts]
    if a.probe_agent.is_file():inputs.append(a.probe_agent)
    if a.shader_pack:inputs.append(a.shader_pack)
    if a.artifact_provenance:inputs.append(a.artifact_provenance)
    if a.lighting_material_only:
        inputs.append(a.lighting_material_agent)
    for source in inputs:
        expected=digest(source);destination=input_store/expected/source.name
        destination.parent.mkdir(parents=True,exist_ok=True)
        if not destination.exists():
            temporary=destination.with_name('.'+destination.name+'-'+run_id)
            shutil.copy2(source,temporary)
            if digest(temporary)!=expected:
                temporary.unlink();raise RuntimeError('Input changed while preserving '+str(source))
            temporary.replace(destination)
        if digest(destination)!=expected:raise RuntimeError('Preserved input hash mismatch: '+str(destination))
        preserved[str(source.resolve())]={'path':str(destination),'sha256':expected}
    report['preserved_inputs']=preserved
    options=(game/'options.txt').read_text() if (game/'options.txt').exists() else ''
    # Classic uses "clouds"; modern codecs accept a BOOL (26.x requires it).
    # Clouds can intersect this height on every runtime.
    cloud_key='clouds' if a.minecraft_version=='1.7.10' else 'renderClouds'
    options=set_option(options,cloud_key,'false',separator=':')
    report['native_cloud_control']={cloud_key:False,'reason':'Prevent native clouds occluding the entity fixture at height 200'}
    for name,value in dict(fullscreen='false',guiScale='2',renderDistance='4',simulationDistance='5',maxFps='260' if a.uncapped else '30',pauseOnLostFocus='false',onboardAccessibility='false',resourcePacks='[]',enableVsync='false').items():
        options=re.sub(r'^'+name+r':.*\n?', '',options,flags=re.M)+name+':'+value+'\n'
    if a.lighting_emission_map_control:
        resources=game/'resourcepacks';resources.mkdir(exist_ok=True)
        import io,zipfile
        from PIL import Image
        with zipfile.ZipFile(a.artifact) as base,zipfile.ZipFile(a.lighting_emission_map_control) as override:
            expected={name for name in base.namelist() if name.startswith('assets/usefultoolsmod/textures/') and name.endswith('_s.png') and 'hglow' in name}
            actual={name for name in override.namelist() if not name.endswith('/')}
            if len(expected)!=13 or actual!=expected|{'pack.mcmeta'}:raise RuntimeError('Emission control must contain exactly 13 native Hardened Glowstone specular maps')
            for name in sorted(expected):
                before=Image.open(io.BytesIO(base.read(name))).convert('RGBA');after=Image.open(io.BytesIO(override.read(name))).convert('RGBA')
                if before.size!=after.size or before.convert('RGB').tobytes()!=after.convert('RGB').tobytes() or after.getchannel('A').getextrema()!=(0,0):
                    raise RuntimeError('Emission control modified specular RGB, dimensions or failed to clear alpha: '+name)
        control=resources/'lighting-emission-control.zip';shutil.copy2(a.lighting_emission_map_control,control)
        options=set_option(options,'resourcePacks',json.dumps(['vanilla','file/'+control.name],separators=(',',':')),separator=':')
        report['lighting_material_control']['emission_map_override']={'source':str(a.lighting_emission_map_control.resolve()),'sha256':digest(control),'only_emission_alpha_changed':True}
    (game/'options.txt').write_text(options)
    shutil.rmtree(game/'shaderpacks', ignore_errors=True)
    (game/'shaderpacks').mkdir()
    if a.shader_pack:
        shutil.copy2(a.shader_pack,game/'shaderpacks'/a.shader_pack.name)
        report['shader_pack']=dict(name=a.shader_pack.name,sha256=digest(a.shader_pack))
        if a.shader_setting:
            (game/'shaderpacks'/(a.shader_pack.name+'.txt')).write_text('\n'.join(a.shader_setting)+'\n')
            report['shader_settings']=a.shader_setting
    configure_shaders(game, a.shader_integration, mode, a.shader_pack.name if a.shader_pack else None)
    if a.lighting_material_only and a.shader_integration=='optifine':
        settings=game/'optionsof.txt';text=settings.read_text() if settings.exists() else ''
        text=re.sub(r'^ofDynamicLights:.*\n?', '',text,flags=re.M)+'ofDynamicLights:3\n';settings.write_text(text)
        report['lighting_material_control']['optifine_dynamic_lighting']='Off; native Config.isDynamicLights must acknowledge false'

    report['shaders_disabled_control'] = mode == 'disabled'
    if a.toggle_shaders:
        keys={'key_iris.keybind.toggleShaders','key.iris.keybind.toggleShaders','key.iris.toggleShaders'}
        options_path=game/'options.txt';options_path.write_text('\n'.join(line for line in options_path.read_text().splitlines() if line.split(':',1)[0] not in keys)+'\n'+''.join(key+':key.keyboard.k\n' for key in sorted(keys)))
    # One renderer at a time across CPU and GPU profiles. Separate backend
    # locks alone let independent batches contend for the same host resources.
    if not a.lighting_multiplayer:
        queued=time.monotonic()
        software_slot_lock=open('/tmp/useful-tools-spectral-render.lock','a+')
        fcntl.flock(software_slot_lock.fileno(),fcntl.LOCK_EX)
        report['serial_render_lock']={'path':software_slot_lock.name,
                                      'wait_seconds':time.monotonic()-queued}
    if a.gpu_zink or a.gpu_virtualgl:
        lock_path='/tmp/useful-tools-spectral-gpu-egl0.lock' if a.gpu_virtualgl and a.egl_device=='egl0' else '/tmp/useful-tools-spectral-gpu.lock'
        gpu_lock = open(lock_path, 'a+')
        fcntl.flock(gpu_lock.fileno(), fcntl.LOCK_EX)
        # Older harness processes do not own this lock. Let them finish before
        # launching; later callers use the shared lock and cannot race this run.
        wait_started=time.monotonic()
        while True:
            other=[]
            for entry in Path('/proc').iterdir():
                if not entry.name.isdigit():continue
                try:
                    cmd=(entry/'cmdline').read_bytes().split(b'\0')
                    route=(entry/'environ').read_bytes().split(b'\0')
                    if not (a.gpu_virtualgl and a.egl_device=='egl0') and cmd[0].endswith(b'java') and b'org.prismlauncher.EntryPoint' in cmd and b'GALLIUM_DRIVER=zink' in route:
                        other.append(int(entry.name))
                except OSError:pass
            if not other:break
            report.setdefault('prior_gpu_clients',[]).append({'time_ms':int(time.time()*1000),'pids':other})
            time.sleep(2)
        report['serial_gpu_lock']={'wait_seconds':time.monotonic()-wait_started,'path':lock_path}
    for display_number in range(90,120):
        reservation=Path(f'/tmp/useful-tools-spectral-display-{display_number}.lock')
        if not reserve_display(reservation,run_id):continue
        if Path(f'/tmp/.X{display_number}-lock').exists() or Path(f'/tmp/.X11-unix/X{display_number}').exists():
            reservation.unlink();continue
        display_reservation=reservation;break
    else:raise RuntimeError('No free isolated display')
    env['DISPLAY']=f':{display_number}';report['display']=env['DISPLAY']
    xvfb=subprocess.Popen(['Xvfb',env['DISPLAY'],'-screen','0','1280x720x24','-nolisten','tcp'],env=env,stdout=(out/'xvfb.log').open('w'),stderr=subprocess.STDOUT)
    for _ in range(100):
        if xvfb.poll() is not None:raise RuntimeError('Xvfb startup failed')
        lock=Path(f'/tmp/.X{display_number}-lock')
        if lock.exists() and lock.read_text().strip()==str(xvfb.pid) and run(['xdotool','getdisplaygeometry']).stdout.strip()=='1280 720':break
        time.sleep(.1)
    else: raise RuntimeError('Xvfb readiness timeout')
    report['xvfb_pid']=xvfb.pid
    (out/'glxinfo.txt').write_text(run(gpu_prefix+['glxinfo','-B']).stdout)
    if a.world_source:
        shutil.rmtree(game/'saves');shutil.copytree(a.world_source,game/'saves')
    world, unselected_worlds = isolate_world_selection(game/'saves', work/'unselected-copied-worlds')
    report['copied_world_selection']={'selected_directory':world,'unselected_directories_archived':unselected_worlds,'visible_acceptance_worlds':1}
    level=game/'saves'/world/'level.dat'
    level_data=gzip.decompress(level.read_bytes())
    title_key=b'\x08\x00\x09LevelName'
    if level_data.count(title_key)!=1:raise RuntimeError('Cannot establish copied world display title')
    title_offset=level_data.index(title_key)+len(title_key)
    title_length=struct.unpack('>H',level_data[title_offset:title_offset+2])[0]
    world_title=level_data[title_offset+2:title_offset+2+title_length].decode('utf-8')
    report['world_directory']=world;report['world_title']=world_title
    weather_version=a.minecraft_version or next(c['version'] for c in pack['components'] if c['uid']=='net.minecraft')
    if weather_version != '1.7.10':
        report['weather_control']=install_weather_control(game/'saves'/world,weather_version)
        shutil.copytree(game/'saves'/world/'datapacks/spectral_recovery_weather',out/'weather-control',dirs_exist_ok=True)
    tag=b'\x01\x00\x1dconfirmedExperimentalSettings\x00'
    if level_data.count(tag)==1: level_data=level_data.replace(tag,tag[:-1]+b'\x01')
    if a.lighting_qa:
        command_tag=b'\x01\x00\x0dallowCommands\x00'
        if level_data.count(command_tag)==1:level_data=level_data.replace(command_tag,command_tag[:-1]+b'\x01');report['copied_world_command_setup']='Enabled commands in the disposable copied acceptance world'
    level.write_bytes(gzip.compress(level_data))
    launch=['prismlauncher','--dir',str(work),'--launch','spectral','--offline',a.offline_name]
    if a.server_address:launch.extend(['--server',a.server_address])
    elif not a.menu_world:launch.extend(['--world',world])
    report['world_entry']='normal menu (fresh-frame verified isolated input)' if a.menu_world else 'Quick Play'
    if a.lighting_direct_client:
        if not a.lighting_qa or a.minecraft_version=='1.7.10':
            raise RuntimeError('Direct local acceptance route requires a native modern lighting target')
        selected={};minecraft_meta=None;loader_meta=None
        for component in sorted(pack['components'],key=lambda c:json.loads((work/'meta'/c['uid']/(c['version']+'.json')).read_text()).get('order',0)):
            metadata=json.loads((work/'meta'/component['uid']/(component['version']+'.json')).read_text())
            if component['uid']=='net.minecraft':minecraft_meta=metadata
            if component['uid']==a.loader_uid:loader_meta=metadata
            libraries=[(library,True) for library in metadata.get('libraries',[])+([metadata['mainJar']] if metadata.get('mainJar') else [])]+[(library,False) for library in metadata.get('mavenFiles',[])]
            for library,on_classpath in libraries:
                rules=library.get('rules',[])
                allowed=not rules
                for rule in rules:
                    os_rule=rule.get('os',{})
                    if os_rule.get('name','linux')=='linux' and os_rule.get('arch','x86_64') in ('x86_64','amd64'):
                        allowed=rule['action']=='allow'
                if not allowed:continue
                coordinate,_,extension=library['name'].partition('@');group,name,version,*classifier=coordinate.split(':')
                download=library.get('downloads',{}).get('artifact',{})
                filename=name+'-'+version+('-'+classifier[0] if classifier else '')+'.'+(extension or 'jar')
                path=download.get('path') or '/'.join([group.replace('.','/'),name,version,filename])
                destination=work/'libraries'/path
                assert not Path(path).is_absolute() and '..' not in Path(path).parts
                url=download.get('url') or library['url'].rstrip('/')+'/'+path
                expected=download.get('sha1')
                if not expected:expected=urllib.request.urlopen(url+'.sha1',timeout=45).read().decode().strip().split()[0]
                if not destination.exists():
                    data=urllib.request.urlopen(url,timeout=45).read()
                    if hashlib.sha1(data).hexdigest()!=expected:raise RuntimeError('Native dependency checksum mismatch: '+path)
                    destination.parent.mkdir(parents=True,exist_ok=True);temporary=destination.with_name(destination.name+'.lighting-'+run_id);temporary.write_bytes(data)
                    if destination.exists():temporary.unlink()
                    else:temporary.replace(destination)
                if hashlib.sha1(destination.read_bytes()).hexdigest()!=expected:raise RuntimeError('Native cached dependency checksum mismatch: '+path)
                if on_classpath:selected[(group,name,tuple(classifier))]=(destination,expected)
        assert minecraft_meta and loader_meta
        classpath=os.pathsep.join(str(value[0]) for value in selected.values())
        game_args=['--username',a.offline_name,'--version',a.minecraft_version,'--gameDir',str(game),'--assetsDir',str(work/'assets'),'--assetIndex',minecraft_meta['assetIndex']['id'],'--uuid',bytes(offline_uuid).hex(),'--accessToken','0','--userType','legacy','--versionType','release','--width','854','--height','480']
        if a.loader_uid in ('net.minecraftforge','net.neoforged'):
            tokens=shlex.split(loader_meta['minecraftArguments'])
            mod_index=next(i for i,value in enumerate(tokens) if value in ('--launchTarget','--fml.neoForgeVersion'))
            game_args+=tokens[mod_index:]
        if a.server_address:game_args+=['--quickPlayMultiplayer',a.server_address]
        elif not a.menu_world:game_args+=['--quickPlaySingleplayer',world]
        inherited_args=re.search(r'^JvmArgs=(.*)$',cfg,re.M)
        launch=[str(a.java_path),'-Xms512M','-Xmx2048M',*shlex.split(inherited_args.group(1) if inherited_args else ''),'-Dforgewrapper.librariesDir='+str(work/'libraries'),'-cp',classpath,loader_meta['mainClass'],*game_args]
        report['native_local_client']=dict(main_class=loader_meta['mainClass'],libraries=[dict(path=str(path),sha1=sha1) for path,sha1 in selected.values()],sources=['https://github.com/PrismLauncher/PrismLauncher/blob/11.0.3/libraries/launcher/org/prismlauncher/launcher/impl/StandardLauncher.java'])
    proc=subprocess.Popen(gpu_prefix+launch,env=env,cwd=game,stdout=(work/'launcher-private.log').open('w'),stderr=subprocess.STDOUT)
    deadline=time.monotonic()+a.timeout
    last_menu=0; menu_index=0; observed_game_process=False
    while time.monotonic()<deadline:
        log=game_log()
        if re.search(r'joined the game|logged in with entity id|USEFUL_TOOLS_LIGHTING_QA_NETWORK_JOINED',log,re.I):break
        if any((game/'crash-reports').glob('*.txt')):raise RuntimeError('Client generated a fresh crash report before world entry')
        if re.search(r'(?:saved|writing|preparing) crash report|Crash report saved|Mod loading has failed|Mixin apply.*failed',log,re.I):raise RuntimeError('Client startup failed; see game log')
        game_processes=[]
        for pid in owned_java():
            try:cmd=(Path('/proc')/str(pid)/'cmdline').read_bytes()
            except OSError:continue
            if str(work/'runtime-probe.jar').encode() in cmd:game_processes.append(pid)
        if game_processes:observed_game_process=True
        elif observed_game_process:raise RuntimeError('Game JVM exited before world entry; see native logs and owned launcher console')
        if proc.poll() is not None:raise RuntimeError('Launcher exited before world entry; private launcher log at '+str(work))
        if (a.menu_world or a.lighting_qa) and time.monotonic()-last_menu>6 and owned_java():
            visible=run(['xdotool','search','--onlyvisible','--name','^Minecraft'])
            if visible.stdout.strip():
                menu_index+=1;navigate_menu(world_title,menu_index);last_menu=time.monotonic()
        time.sleep(1)
    else:raise RuntimeError('World entry timeout; private launcher log at '+str(work))
    report['client_pids']=owned_java();time.sleep(8)
    if any((game/'crash-reports').glob('*.txt')):
        raise RuntimeError('Client generated a fresh crash report while entering the world')
    if not owned_java():
        raise RuntimeError('Client exited while entering the world; see saved native log')
    if a.gpu_virtualgl:
        report['gpu_route_verified']={'game_pids':owned_java(),'egl_device':a.egl_device,'interposer_loaded':True}
    (work/'spectral-state.log.ready').touch()
    if a.jvm_arg:
        for requested in a.jvm_arg:
            if not any(requested.encode() in (Path('/proc')/str(pid)/'cmdline').read_bytes().split(b'\0')
                       for pid in owned_java()):
                raise RuntimeError('Requested JVM argument absent from actual client: '+requested)
        report['jvm_arguments_verified']=a.jvm_arg
    runtime_pin=a.loader_version
    if a.loader_uid=='net.minecraftforge' and a.minecraft_version and runtime_pin.startswith(a.minecraft_version+'-'):
        runtime_pin=runtime_pin[len(a.minecraft_version)+1:]
    runtime_evidence=game_log()
    classic_fml=game/'logs/fml-client-latest.log'
    if a.classic_qa and classic_fml.exists():
        runtime_evidence+='\n'+classic_fml.read_text(errors='replace')
        # Save only the native version acknowledgement, not launch arguments.
        (out/'classic-loader-acknowledgement.log').write_text('\n'.join(
            line for line in runtime_evidence.splitlines() if re.search(r'MinecraftForge v[\d.]+ Initialized',line))+'\n')
    if (work/'spectral-state.log').exists():runtime_evidence+='\n'+(work/'spectral-state.log').read_text()
    if not re.search(r'(?<![\w.+-])'+re.escape(runtime_pin)+r'(?![\w.+-])',runtime_evidence):
        raise RuntimeError('Exact loader version absent from runtime log: '+runtime_pin)
    report['runtime_loader_pin_acknowledged']=runtime_pin
    if a.shader_pack:
        report['shader_activation_logged']=activation_reported(game_log(), a.shader_integration, a.shader_pack.name)
        if not report['shader_activation_logged']:raise RuntimeError('Requested shader activation not reported')
    if a.lighting_qa and not a.lighting_startup_only:
        if a.minecraft_version == '1.7.10':
            audio_marker = 'USEFUL_TOOLS_LIGHTING_QA_AUDIO silent Paulscode Library; ALSOFT_DRIVERS=null retained'
            if audio_marker not in game_log():
                raise RuntimeError('Native classic silent audio fixture was not loaded; repair the acceptance-package bootstrap before testing')
            report['classic_silent_audio_hook'] = audio_marker
        deadline=time.monotonic()+180
        while time.monotonic()<deadline:
            log=game_log()
            if 'USEFUL_TOOLS_LIGHTING_QA_FAIL' in log:raise RuntimeError('Native lighting acceptance failed')
            if ('USEFUL_TOOLS_LIGHTING_QA_NETWORK_PASS' if a.lighting_multiplayer else 'USEFUL_TOOLS_LIGHTING_QA_PASS') in log:break
            if not owned_java():raise RuntimeError('Client exited during lighting acceptance')
            time.sleep(.5)
        else:raise RuntimeError('Native lighting acceptance timed out')
        report['lighting_equipment_cases']=[line for line in log.splitlines() if 'USEFUL_TOOLS_LIGHTING_QA_CASE_PASS' in line]
        report['lighting_advanced_cases']=[line for line in log.splitlines() if 'USEFUL_TOOLS_LIGHTING_QA_ADVANCED_PASS' in line]
        if a.lighting_classic_beddium:
            report['lighting_classic_beddium_cache_cases']=classic_beddium_observation(log)
        if a.lighting_multiplayer:
            report['lighting_network_cases']=[line for line in log.splitlines() if 'USEFUL_TOOLS_LIGHTING_QA_NETWORK_CASE_PASS' in line]
        shot('lighting-native-acceptance')
        if a.lighting_material_only:
            flag=work/('lighting-material-control-'+a.shader_integration+'.flag');flag.write_text('normal native acceptance passed; enable shader-only control\n')
            ack=Path(str(flag)+'.ack');failure=Path(str(flag)+'.fail');deadline=time.monotonic()+30
            while time.monotonic()<deadline:
                if failure.is_file():raise RuntimeError('Material-only native control failed: '+failure.read_text())
                if ack.is_file():break
                if not owned_java():raise RuntimeError('Client exited before material control acknowledgement')
                time.sleep(.1)
            else:raise RuntimeError('Material-only native control timed out')
            acknowledgement=ack.read_text()
            if 'USEFUL_TOOLS_LIGHTING_MATERIAL_CONTROL_PASS' not in acknowledgement:raise RuntimeError('Incomplete material-control acknowledgement')
            (out/'lighting-material-control-acknowledgement.log').write_text(acknowledgement)
            report['lighting_material_control'].update(activation='after full native acceptance',acknowledgement=acknowledgement,acknowledgement_sha256=digest(ack))
            key('F3+a');time.sleep(8)
            report['lighting_material_control']['render_chunk_reload']='F3+A on verified owned client window after activation'
        if a.lighting_scenes:
            new_mc=(a.minecraft_version or '').startswith('26.')
            classic_lighting=a.minecraft_version=='1.7.10'
            if classic_lighting:
                command('gamerule doDaylightCycle false',r'(updated|false)')
                command('lightingqa prepare',r'USEFUL_TOOLS_LIGHTING_SCENE_ACCEPTED')
                time.sleep(8)
                key('F1')
                for label in ['ordinary','hardened']:
                    command('lightingqa '+label,r'USEFUL_TOOLS_LIGHTING_SCENE_ACCEPTED')
                    for distance in [3,10,15,20,29]:
                        command('lightingqa view '+str(distance),r'USEFUL_TOOLS_LIGHTING_SCENE_ACCEPTED')
                        time.sleep(4);shot('lighting-'+label+'-distance-'+str(distance))
                if a.lighting_classic_beddium:
                    # Keep the camera fixed and let normal client block packets
                    # invalidate the renderer. No manual chunk reload is used.
                    dirty_scenes = []
                    for scene, scene_command in [
                            ('source-removed', 'setblock 500 200 0 minecraft:air 0 replace'),
                            ('source-moved', 'setblock 510 200 0 usefultoolsmod:hglow_block 0 replace'),
                            ('moved-source-removed', 'setblock 510 200 0 minecraft:air 0 replace')]:
                        command(scene_command, r'(block|placed|changed)')
                        time.sleep(8)
                        name = 'lighting-beddium-' + scene
                        shot(name)
                        dirty_scenes.append(dict(scene=scene, command=scene_command, capture=name+'.png'))
                    report['lighting_classic_beddium_dirty_scenes'] = dirty_scenes
                    report['lighting_classic_beddium_dirty_scene_method'] = (
                        'Fixed camera at source distance29; original source removed, new source placed ten blocks closer, '
                        'and new source removed. Native server command feedback required. No F3+A or manual renderer '
                        'invalidation; compare fresh frames and native cache assertions before recording a pass.')
                if a.toggle_shaders:
                    optifine_toggle(False,'lighting');shot('lighting-shaders-disabled')
                    optifine_toggle(True,'lighting');shot('lighting-shaders-restored')
                report['lighting_comparison_distances']=[10,15,20,29]
            else:
                command('gamerule '+('minecraft:send_command_feedback' if new_mc else 'sendCommandFeedback')+' true',r'(feedback|sendCommandFeedback)')
                command('gamemode survival',r'(survival|already|Nothing changed)')
                command('gamemode creative',r'(creative|already|Nothing changed)')
                command('gamerule '+('minecraft:spawn_mobs' if new_mc else 'doMobSpawning')+' false',r'(updated|false)')
                command('gamerule '+('minecraft:mob_drops' if new_mc else 'doMobLoot')+' false',r'(updated|false)')
                command('gamerule '+('minecraft:block_drops' if new_mc else 'doTileDrops')+' false',r'(updated|false)')
                command('kill @e[type=!minecraft:player]',r'(Killed|No entity|No entities)')
                command('give @s minecraft:dirt',r'(Gave|given)')
                command('clear @s',r'(Removed|No items|cleared)')
                command('gamerule '+('minecraft:advance_time' if new_mc else 'doDaylightCycle')+' false',r'(updated|false)')
                command('gamerule '+('minecraft:advance_weather' if new_mc else 'doWeatherCycle')+' false',r'(updated|false)')
                command('time set midnight',r'(time|Set)')
                command('weather clear',r'(weather|clear)')
                command('tp @s 500 201 0 90 15',r'(Teleported|201)')
                time.sleep(8)
                # Teleport acknowledgement precedes asynchronous chunk loading.
                # Prove all eight corridor chunks are ready before the first fill.
                loaded='execute '+ ' '.join('if loaded '+str(x)+' 199 '+str(z) for x in [480,496,512,528] for z in [-16,0])+' run time query gametime'
                deadline=time.monotonic()+120;attempts=0
                while time.monotonic()<deadline:
                    attempts+=1
                    result=command(loaded,r'(time|query)')
                    if result is not None and result>0:
                        report['lighting_corridor_chunks']={'all_eight_loaded':True,'attempts':attempts,'native_result':result};break
                    time.sleep(1)
                else:raise RuntimeError('Native corridor chunks did not load before fill')
                command('fill 495 199 -5 535 205 5 minecraft:stone',r'(filled|No blocks)')
                command('fill 496 200 -4 534 204 4 minecraft:air',r'(filled|No blocks)')
                key('F3+a');time.sleep(8)
                report['lighting_corridor_render_reload']='F3+A on verified owned client after both accepted fills'
                key('F1')
                for block,label in [('minecraft:glowstone','ordinary'),('usefultoolsmod:hglow_block','hardened')]:
                    command('setblock 500 200 0 '+block,r'(Changed|block|Nothing changed)')
                    for distance in [3,10,15,20,29]:
                        command('tp @s '+str(500+distance+.5)+' 201 0.5 90 18',r'(Teleported|201)')
                        time.sleep(4);shot('lighting-'+label+'-distance-'+str(distance))
                if a.toggle_shaders:
                    if a.shader_integration=='optifine':optifine_toggle(False,'lighting')
                    elif a.shader_integration=='community':community_toggle(False,'lighting')
                    else:key('k');time.sleep(6);verify_shader_state(False)
                    shot('lighting-shaders-disabled')
                    if a.shader_integration=='optifine':optifine_toggle(True,'lighting')
                    elif a.shader_integration=='community':community_toggle(True,'lighting')
                    else:key('k');time.sleep(6);verify_shader_state(True)
                    shot('lighting-shaders-restored')
                report['lighting_comparison_distances']=[10,15,20,29]
            if a.lighting_equipment_scenes:
                sources=json.loads((ROOT/'lighting/sources.json').read_text())['sources']
                cases=[('held','minecraft:glowstone')]+[('held','usefultoolsmod:'+source['id']) for source in sources]
                cases += [(slot,'usefultoolsmod:hglow_'+piece) for slot,piece in [('head','helmet'),('chest','chestplate'),('legs','leggings'),('feet','boots')]]
                if not classic_lighting:
                    command('setblock 500 200 0 minecraft:air',r'(Changed|block|Nothing changed)')
                    command('tp @s 503.5 201 0.5 90 0',r'(Teleported|201)')
                key('F5');key('F5')
                report['lighting_equipment_shader_captures']=[]
                for slot,source in cases:
                    if classic_lighting:
                        command('lightingqa equip '+slot+' '+source,r'USEFUL_TOOLS_LIGHTING_SCENE_ACCEPTED')
                        deadline=time.monotonic()+10
                        marker_text='USEFUL_TOOLS_LIGHTING_SCENE_EQUIPMENT_ACK slot='+slot+' source='+source
                        while marker_text not in game_log() and time.monotonic()<deadline:time.sleep(.2)
                        if marker_text not in game_log():raise RuntimeError('Native equipped-source replication and visual light not acknowledged: '+slot+' '+source)
                    else:
                        key('1');time.sleep(.3)
                        token=uuid.uuid4().hex
                        request=work/'lighting-equipment-scene.request';temporary=request.with_suffix('.tmp');temporary.write_text(token+'\n'+slot+'\n'+source+'\n');temporary.replace(request)
                        marker_text='USEFUL_TOOLS_LIGHTING_SCENE_EQUIPMENT_ACK token='+token
                        deadline=time.monotonic()+10
                        while marker_text not in game_log() and time.monotonic()<deadline:time.sleep(.2)
                        if marker_text not in game_log():raise RuntimeError('Native equipped-source replication and visual light not acknowledged: '+slot+' '+source)
                    time.sleep(4);verify_shader_state(mode=='active')
                    frame='lighting-equipment-'+slot+'-'+source.split(':')[-1]
                    shot(frame)
                    report['lighting_equipment_shader_captures'].append(dict(slot=slot,source=source,capture=frame+'.png',native_acknowledgement=marker_text))
                key('F5')
    elif a.lighting_startup_only:
        time.sleep(25);shot('lighting-base-native-startup')
    elif a.classic_qa:
        classic_scenes()
    else:
        minecraft_version=a.minecraft_version or next(c['version'] for c in pack['components'] if c['uid']=='net.minecraft')
        modern_game=minecraft_version.startswith('26.')
        feedback_rule='minecraft:send_command_feedback' if modern_game else 'sendCommandFeedback'
        spawn_rule='minecraft:spawn_mobs' if modern_game else 'doMobSpawning'
        command('gamerule '+feedback_rule+' true',r'(feedback|sendCommandFeedback)')
        # Forge 1.21.1 emits no command feedback for a no-op gamemode change.
        # The validated world fixture starts in survival, so force a real
        # transition before proving both survival and the final creative mode.
        if minecraft_version=='1.21.1':
            command('gamemode creative',r'(creative|already|Nothing changed)')
        command('gamemode survival',r'(survival|already|Nothing changed)')
        command('gamemode creative',r'(creative|already|Nothing changed)')
        command('gamerule '+spawn_rule+' false',r'(spawn_mobs|doMobSpawning)')
        command('gamerule '+('minecraft:mob_drops' if modern_game else 'doMobLoot')+' false',r'(mob_drops|doMobLoot)')
        command('weather clear',r'(weather|clear)')
        command('kill @e[type=!minecraft:player]',r'(Killed|No entity)')
        command('tp @s 0 205 3 180 8',r'(Teleported|205)')
        command('fill -8 199 -8 8 199 8 minecraft:smooth_stone',r'(filled|No blocks)')
        command('fill -8 200 -8 8 210 8 minecraft:air',r'(filled|No blocks)')
        # A copied world's spawn can be desert even when /weather succeeds.
        # Use a precipitation-capable biome throughout the camera's rain area.
        command('fillbiome -12 192 -12 12 216 12 minecraft:plains',r'(biomes|biome|No changes)')
        command('execute if biome 0 201 -3 minecraft:plains run say SPECTRAL_PRECIPITATION_BIOME',r'SPECTRAL_PRECIPITATION_BIOME')
        report['precipitation_fixture']={'biome':'minecraft:plains','bounds':[-12,192,-12,12,216,12],'acknowledged':True}
        command('tp @s 0 200 3 180 8',r'(Teleported|200)')
        key('F1')
        for entity in a.entities:
            command(f'summon usefultoolsmod:{entity} 0 201 -3 {{NoAI:1b,NoGravity:1b,Silent:1b,PersistenceRequired:1b,Invulnerable:1b,Rotation:[0f,0f]}}',r'Summoned')
            for state in [scene for scene in a.scenes if scene != 'indoor']:
                if state in ('day','night'): command('time set '+('noon' if state=='day' else 'midnight'),r'(Set the time|time marker)')
                elif state=='rain': command('time set noon',r'(Set the time|time marker)');command('weather rain 100000',r'Set the weather')
                else:
                    command('weather clear',r'Set the weather')
                    command('fill -4 201 -5 4 205 -5 minecraft:'+('deepslate_tiles' if state=='dark-background' else 'white_concrete'),r'(filled|No blocks)')
                time.sleep(10 if state=='rain' else 4)
                if state=='rain':
                    command('execute if predicate spectral_recovery:rain run say SPECTRAL_NATIVE_RAIN',r'SPECTRAL_NATIVE_RAIN')
                    report.setdefault('native_weather_checks',[]).append({'entity':entity,'raining':True,'acknowledged':True})
                capture_scene(entity,state)
                if state=='rain' and a.rain_diagnostics:
                    if mode!='active':raise RuntimeError('Rain shader toggle diagnostics require active mode')
                    for active,label in [(False,'off'),(True,'restored')]:
                        if a.shader_integration=='optifine':optifine_toggle(active,entity+'-rain')
                        elif a.shader_integration=='community':community_toggle(active,entity+'-rain')
                        else:
                            start=len(game_log());key('k');time.sleep(8 if active else 5)
                            if not active and not re.search(r'(Shaders are disabled|Shaders disabled)',game_log()[start:],re.I):
                                raise RuntimeError('Rain shader-off toggle not acknowledged')
                            if active and not activation_reported(game_log()[start:],a.shader_integration,a.shader_pack.name):
                                raise RuntimeError('Rain shader restoration pack not acknowledged')
                            verify_shader_state(active)
                        command('execute if predicate spectral_recovery:rain run say SPECTRAL_NATIVE_RAIN',r'SPECTRAL_NATIVE_RAIN')
                        shot(entity+'-rain-shaders-'+label)
                        report.setdefault('rain_toggle_diagnostics',[]).append({'entity':entity,'shader_active':active,'raining':True,'acknowledged':True})
            command('fill -4 201 -5 4 205 -5 minecraft:air',r'(filled|No blocks)')
            command(f'kill @e[type=usefultoolsmod:{entity}]',r'Killed')
        command('fill -5 199 -8 5 207 4 minecraft:deepslate_bricks hollow',r'(filled|No blocks)')
        command('fill -5 200 4 5 206 4 minecraft:air',r'(filled|No blocks)')
        command('setblock 0 205 -4 minecraft:sea_lantern',r'(Changed|No blocks)')
        for entity in (a.entities if 'indoor' in a.scenes else []):
            command(f'summon usefultoolsmod:{entity} 0 201 -3 {{NoAI:1b,NoGravity:1b,Silent:1b,PersistenceRequired:1b,Invulnerable:1b,Rotation:[0f,0f]}}',r'Summoned')
            time.sleep(4);capture_scene(entity,'indoor')
            native_frame_trials(entity)
            if a.outline_frame_trials:
                target=f'@e[type=usefultoolsmod:{entity},limit=1]'
                command(f'data merge entity {target} {{Glowing:1b}}',r'Modified')
                command(f'data get entity {target} Glowing',r'entity data.*1b')
                verify_shader_state(mode=='active')
                shot(entity+'-outline-performance')
                report.setdefault('native_outline_performance_controls',[]).append(
                    dict(entity=entity,glowing_native_acknowledged=True,
                         shader_active=mode=='active',capture=entity+'-outline-performance.png'))
                native_frame_trials(entity, state='outline')
                command(f'data merge entity {target} {{Glowing:0b}}',r'Modified')
                # A false Glowing tag is omitted from the entity's saved NBT on
                # this native target; /data get then reports no matching path.
                command(f'data get entity {target} Glowing',
                        r'(entity data.*0b|Found no elements matching Glowing)')
            frame_trials(entity)
            if a.toggle_shaders:
                if a.shader_integration == 'optifine':
                    optifine_toggle(False,entity);shot(entity+'-shaders-off')
                    optifine_toggle(True,entity);shot(entity+'-shaders-restored')
                elif a.shader_integration == 'community':
                    community_toggle(False,entity);shot(entity+'-shaders-off')
                    community_toggle(True,entity);shot(entity+'-shaders-restored')
                else:
                    start=len(game_log());key('k');time.sleep(5)
                    if not re.search(r'(Shaders are disabled|Shaders disabled)',game_log()[start:],re.I):raise RuntimeError('Shader off toggle not acknowledged')
                    verify_shader_state(False)
                    shot(entity+'-shaders-off')
                    start=len(game_log());key('k');time.sleep(8)
                    if not re.search(r'(Using shaderpack|Loaded shaderpack|Creating pipeline)',game_log()[start:],re.I):raise RuntimeError('Shader on toggle not acknowledged')
                    verify_shader_state(True)
                    shot(entity+'-shaders-restored')
            command(f'kill @e[type=usefultoolsmod:{entity}]',r'Killed')
        if a.entity_states:
            for entity in a.entities:
                target=f'@e[type=usefultoolsmod:{entity},limit=1]'
                # Let the preceding /kill finish its native death animation
                # before recording this entity's movement reference frame.
                time.sleep(5)
                command(f'summon usefultoolsmod:{entity} 0 201 -3 {{NoAI:1b,NoGravity:1b,PersistenceRequired:1b,Rotation:[0f,0f]}}',r'Summoned')
                command(f'data merge entity {target} {{Motion:[0.05d,0d,0d]}}',r'Modified')
                shot(entity+'-movement-before');time.sleep(2);shot(entity+'-movement-after')
                command(f'tp {target} 0 201 -3 0 0',r'Teleported')
                if a.native_damage_probe:
                    token=uuid.uuid4().hex
                    request=work/'spectral-state.log.hurt-request'
                    temporary=request.with_suffix('.tmp');temporary.write_text(token+'\n'+entity+'\n');temporary.replace(request)
                command(f'damage {target} 1 minecraft:generic_kill',r'(damage|Damaged)')
                if a.native_damage_probe:
                    deadline=time.monotonic()+2
                    while time.monotonic()<deadline:
                        ack=work/'spectral-state.log.hurt-ack'
                        proof=native_hurt_observation(ack.read_text() if ack.exists() else '',token,int(time.time()*1000))
                        if proof:
                            report.setdefault('native_damage_checks',[]).append(dict(entity=entity,request_token=token,**proof))
                            break
                        time.sleep(.01)
                    else:raise RuntimeError('Native client damage flash not observed: '+entity)
                    request.unlink()
                # Observe the actual client timer; never extend or fake hurt state.
                shot(entity+'-damage', transient=True)
                command(f'effect give {target} minecraft:invisibility 99999 0 true',r'(Applied|invisibility)')
                time.sleep(1);shot(entity+'-invisible')
                command('gamemode spectator',r'(spectator|Nothing changed)')
                time.sleep(1);shot(entity+'-spectator-invisible')
                # Native spectator alpha is deliberately faint. Preserve the
                # original dark-wall shot and add an acknowledged white-wall
                # control for every modern mode, without changing entity state.
                command('fill -4 200 -6 4 205 -6 minecraft:white_concrete',r'(filled|No blocks)')
                command('execute if block 0 203 -6 minecraft:white_concrete run say SPECTRAL_SPECTATOR_WHITE_BACKDROP',r'SPECTRAL_SPECTATOR_WHITE_BACKDROP')
                time.sleep(2);shot(entity+'-spectator-white-invisible')
                if a.spectator_diagnostics:
                    key('k');time.sleep(6);verify_shader_state(False)
                    shot(entity+'-spectator-native-invisible')
                    key('k');time.sleep(8);verify_shader_state(True)
                    shot(entity+'-spectator-restored-invisible')
                command('fill -4 200 -6 4 205 -6 minecraft:black_concrete',r'(filled|No blocks)')
                command('gamemode creative',r'(creative|Nothing changed)')
                command(f'effect clear {target}',r'(Removed|effect)')
                if a.outline_interior_controls:
                    command(f'data merge entity {target} {{Glowing:0b}}',r'Modified')
                    time.sleep(1);shot(entity+'-outline-interior-before')
                command(f'data merge entity {target} {{Glowing:1b}}',r'Modified')
                time.sleep(1);shot(entity+'-outline')
                command(f'data merge entity {target} {{Glowing:0b}}',r'Modified')
                command(f'kill {target}',r'Killed')
        if a.vanilla_outline_control:
            command('kill @e[type=minecraft:sheep]',r'(Killed|No entity|No entities)')
            # The previous spectral target has a native death animation. Wait
            # in the unpaused world before introducing this brightness control
            # so its body cannot overlap the sheep in the first capture.
            time.sleep(5)
            command('summon minecraft:sheep 0 201 -3 {NoAI:1b,NoGravity:1b,Silent:1b,Invulnerable:1b,Glowing:0b,Rotation:[0f,0f]}',r'Summoned')
            command('data get entity @e[type=minecraft:sheep,limit=1] HurtTime',r'entity data.*0s')
            time.sleep(2);shot('vanilla-sheep-outline-interior-before')
            command('data merge entity @e[type=minecraft:sheep,limit=1] {Glowing:1b}',r'Modified')
            command('data get entity @e[type=minecraft:sheep,limit=1] HurtTime',r'entity data.*0s')
            time.sleep(2);shot('vanilla-sheep-outline-active')
            optifine_toggle(False,'vanilla-sheep')
            time.sleep(2);shot('vanilla-sheep-outline-disabled')
            optifine_toggle(True,'vanilla-sheep')
            command('kill @e[type=minecraft:sheep]',r'Killed')
            report['vanilla_outline_control'] = {'native_entity':'minecraft:sheep','glowing_nbt':True,
                                                'active_and_disabled_captured':True,
                                                'same_active_scene_interior_before_captured':True,
                                                'native_hurt_time_zero_acknowledged':True}
        if a.surfaces:
            command('weather clear',r'(weather|clear)')
            command('time set midnight',r'(Set the time|time marker)')
            command('fill -5 200 -7 5 207 4 minecraft:air',r'(filled|No blocks)')
            command('tp @s 0 200 3 180 8',r'Teleported')
            for index,block in enumerate(('ectoplasm_lantern','mining_charge','spectral_infuser',
                                          'ectoplasm_block','refined_ectoplasm_block','hglow_block')):
                x=(index%3-1)*3;z=-2 if index<3 else -5
                state='[facing=south]' if block=='spectral_infuser' else ''
                command(f'setblock {x} 200 {z} usefultoolsmod:{block}{state}',r'(Changed|No blocks)')
            command('setblock -4 200 -2 usefultoolsmod:soul_lantern',r'(Changed|No blocks)')
            report['surface_registry_fixture']=['ectoplasm_lantern','soul_lantern','mining_charge','spectral_infuser','ectoplasm_block','refined_ectoplasm_block','hglow_block']
            time.sleep(3);shot('surfaces-blocks-night-unlit')
            fixture=infuser_fixture_nbt(a.minecraft_version or '',a.loader_uid)
            report['infuser_inventory_fixture']=fixture
            command('data merge block 3 200 -2 '+fixture,r'Modified')
            if (a.minecraft_version or '').startswith('26.'):
                command('item replace block 3 200 -2 container.0 with minecraft:iron_sword',r'Replaced')
                command('item replace block 3 200 -2 container.1 with usefultoolsmod:ectoplasm',r'Replaced')
                command('data get block 3 200 -2 Items',r'iron_sword')
            command('execute if block 3 200 -2 usefultoolsmod:spectral_infuser[lit=true] run say SPECTRAL_INFUSER_LIT',r'SPECTRAL_INFUSER_LIT')
            command('setblock 0 200 -2 usefultoolsmod:mining_charge[lit=true]',r'(Changed|No blocks)')
            time.sleep(1);shot('surfaces-blocks-night-lit')
            command('time set noon',r'(Set the time|time marker)')
            time.sleep(2);shot('surfaces-blocks-day')
            command('clear @s',r'(Removed|No items|cleared)')
            for item in ('dynamite','grenade','sticky_dynamite','mining_charge','ectoplasm_lantern',
                         'spectral_resonator','remote_detonator','hglowstone_sword','hglow_helmet'):
                command('give @s usefultoolsmod:'+item,r'(Gave|given)')
            command('give @s usefultoolsmod:soul_lantern',r'(Gave|given)')
            for slot, item in (('head','hglow_helmet'),('chest','hglow_chestplate'),('legs','hglow_leggings'),('feet','hglow_boots')):
                command('item replace entity @s armor.'+slot+' with usefultoolsmod:'+item,r'(Replaced|replaced)')
            command('item replace entity @s weapon.mainhand with usefultoolsmod:hglowstone_sword',r'Replaced')
            command('item replace entity @s weapon.mainhand with '+enchanted_sword_fixture(a.minecraft_version),r'Replaced')
            command('data get entity @s SelectedItem',r'unbreaking')
            report['glint_fixture']={'item':'usefultoolsmod:hglowstone_sword','enchantment':'minecraft:unbreaking','native_serialization_acknowledged':True}
            key('F5');key('F5');time.sleep(1);shot('surfaces-worn-armor-and-glint');key('F5')
            command('gamemode survival',r'(survival|Nothing changed)')
            key('F1');key('e')
            # Toast timers do not advance while the HUD is hidden. Let them
            # finish in the visible native inventory, which also shows all four
            # worn armor slots and the equipped player preview.
            time.sleep(20)
            run(['xdotool','mousemove','--window',window(),'10','10'],check=True)
            shot('surfaces-inventory-previews')
            inventory_words=menu_words('surfaces-inventory-previews')
            if any(re.search(r'Advancement|Goal Reached|New Recipes|Challenge Complete',w['text'],re.I) for w in inventory_words):
                raise RuntimeError('Acquisition toasts obscure native inventory preview')
            if not any(re.search(r'Crafting',w['text'],re.I) for w in inventory_words):
                raise RuntimeError('Native survival inventory preview not acknowledged')
            report['inventory_preview_fixture']={'native_survival_inventory':True,'toasts_absent':True,'armor_slots':4}
            key('Escape');key('F1')
            command('gamemode creative',r'(creative|Nothing changed)')
            for index, projectile in enumerate(('grenade','sticky_dynamite')):
                command(f'summon usefultoolsmod:{projectile} {index*2-1} 201 -3 {{NoGravity:1b,Fuse:10000,Motion:[0d,0d,0d]}}',r'Summoned')
            time.sleep(1);shot('surfaces-projectiles')
            command('kill @e[type=usefultoolsmod:grenade]',r'Killed')
            command('kill @e[type=usefultoolsmod:sticky_dynamite]',r'Killed')
            # Repopulate immediately before the GUI check and acknowledge native
            # container contents; a title alone cannot prove populated slots.
            command('data merge block 3 200 -2 '+fixture,r'Modified')
            if (a.minecraft_version or '').startswith('26.'):
                command('item replace block 3 200 -2 container.0 with minecraft:iron_sword',r'Replaced')
                command('item replace block 3 200 -2 container.1 with usefultoolsmod:ectoplasm',r'Replaced')
                command('data get block 3 200 -2 Items',r'iron_sword')
            command('tp @s 3 200 1 180 20',r'Teleported')
            key('F1');window();run(['xdotool','click','3'],check=True);time.sleep(1)
            shot('surfaces-infuser-gui')
            gui_words=json.loads(run([str(a.ocr_python),str(OCR_SCRIPT),str(out/'surfaces-infuser-gui.png')],check=True).stdout)
            (out/'surfaces-infuser-gui-ocr.json').write_text(json.dumps(gui_words,indent=2)+'\n')
            if not any(re.search(r'Spectral Infuser',word['text'],re.I) and word['confidence']>=.65 for word in gui_words):
                raise RuntimeError('Infuser GUI title not verified in fresh capture')
            if a.loader_uid=='net.minecraftforge' and (a.minecraft_version or '').startswith('26.'):
                slot_file=work/'spectral-state.log.menu-slots'
                if not slot_file.is_file():raise RuntimeError('Native client Infuser slot evidence missing')
                slot_text=slot_file.read_text()
                if not populated_infuser_client_slots(slot_text,time.time()*1000):
                    raise RuntimeError('Native client Infuser input/fuel slots missing, stale or incorrect')
                report['infuser_client_inventory_fixture']={'input':'minecraft:iron_sword','fuel':'usefultoolsmod:ectoplasm',
                    'native_slot_probe_sha256':digest(slot_file),'fresh_client_slots_acknowledged':True}

            key('Escape');key('F1')
    key('F1')
    # XDestroyWindow bypasses both LWJGL2 and GLFW's normal close request.
    # Native menus acknowledge world saving before the graphics context ends.
    if a.lighting_multiplayer and not a.server_address:
        deadline=time.monotonic()+90
        while not (a.lighting_multiplayer/'guest_finished').exists() and time.monotonic()<deadline:time.sleep(.5)
        if not (a.lighting_multiplayer/'guest_finished').exists():raise RuntimeError('Guest did not finish its owned client shutdown')
    from spectral_validation import shutdown_control
    for attempt in range(8):
        key('Escape');time.sleep(1)
        frame=('lighting-save-quit' if a.lighting_qa else 'shutdown-save-quit')+('-retry-'+str(attempt) if attempt else '')
        run(['xdotool','mousemove','--window',window(),'10','100'],check=True)
        time.sleep(.8);shot(frame)
        control=shutdown_control(menu_words(frame),bool(a.server_address))
        if control:
            click(round(control['x']),round(control['y']))
            report.setdefault('shader_menu_actions',[]).append(dict(control=control,frame=frame+'.png'))
            break
        report.setdefault('shutdown_menu_retries',[]).append({'frame':frame+'.png','reason':'Native pause-menu shutdown control not visible'})
    else:raise RuntimeError('Native pause-menu shutdown control did not become ready')
    time.sleep(12)
    if a.lighting_qa and not a.lighting_startup_only:
        deadline=time.monotonic()+120
        while time.monotonic()<deadline:
            native=work/'spectral-state.log.menu'
            if native.exists():
                lines=native.read_text().splitlines()
                if lines and 0 <= time.time()*1000-int(lines[0]) < 2000:
                    labels=[base64.b64decode(line.split('\t')[0]).decode('utf-8') for line in lines[1:]]
                    if any(re.search(r'^Quit\s*Game$',label,re.I) for label in labels):break
                    if a.server_address and any(re.search(r'^(Back|Cancel)$',label,re.I) for label in labels):
                        click_control(r'^(Back|Cancel)$','multiplayer-return-title');time.sleep(1);continue
            time.sleep(.5)
        else:raise RuntimeError('Native title menu did not become ready after disconnect or world save')
    elif a.server_address and a.minecraft_version!='1.7.10':click_control(r'^(Back|Cancel)$', 'multiplayer-return-title')
    if not a.lighting_qa:
        # Saving can exceed the old fixed twelve-second delay. Observe a
        # fresh title screen before selecting Quit; never click through a
        # still-saving screen or reinterpret it as an integration failure.
        deadline=time.monotonic()+120
        index=0
        while time.monotonic()<deadline:
            index+=1
            frame='shutdown-title-wait-'+str(index)
            # The title menu may appear beneath the old pointer position.
            # Its language tooltip can cover Quit Game and defeat OCR.
            run(['xdotool','mousemove','--window',window(),'10','100'],check=True)
            time.sleep(.8)
            shot(frame)
            words=menu_words(frame)
            controls=[word for word in words if re.fullmatch(r'Quit\s*Game',word['text'],re.I) and word['confidence']>=.65]
            if len(controls)==1:
                report['title_after_save_verified']={'frame':frame+'.png','control':controls[0]}
                title_quit_control=controls[0]
                break
            time.sleep(3)
        else:raise RuntimeError('Fresh title menu did not become ready after world save')
    if a.lighting_qa:
        click_control(r'^Quit\s*Game$', 'lighting-quit-game')
    else:
        # Click the control from the fresh title-screen frame verified above.
        # A redundant recapture can land while button glyphs are transiently
        # absent even though the title screen and control were just verified.
        click(round(title_quit_control['x']),round(title_quit_control['y']))
        report.setdefault('shader_menu_actions',[]).append(
            dict(control=title_quit_control,frame=frame+'.png'))
    deadline=time.monotonic()+30
    while owned_java() and time.monotonic()<deadline:time.sleep(.5)
    shutdown_log=game_log()
    report['shutdown_acknowledgement']=[line for line in shutdown_log.splitlines()
        if re.search(r'Stopping server|Saving worlds|Saving chunks for level|All chunks are saved',line)]
    report['clean_client_exit']=(not owned_java()
        and (a.server_address or bool(re.search(r'Stopping server',shutdown_log)))
        and (a.server_address or bool(re.search(r'Saving worlds|Saving chunks for level|All chunks are saved',shutdown_log)))
        and not any((game/'crash-reports').glob('*.txt')))
    if a.lighting_multiplayer and a.server_address and report['clean_client_exit']:
        (a.lighting_multiplayer/'guest_finished').write_text('clean client exit\n')
    report['status']='captures_complete' if report['clean_client_exit'] else 'shutdown_failed'
except KeyboardInterrupt as error:
    report.update(status='cancelled', error=str(error) or 'Client test interrupted',
                  clean_client_exit=False)
    # A second interrupt must not abandon owned-process cleanup.
    signal.signal(signal.SIGINT, signal.SIG_IGN)
    signal.signal(signal.SIGTERM, signal.SIG_IGN)
except Exception as error:
    report.update(status='failed',error=str(error))
    # Save native thread state before owned JVM cleanup; distinguish a stalled
    # renderer/server from a shader compiler, scene transport, or API failure.
    for game_pid in owned_java():
        try:
            executable=Path('/proc')/str(game_pid)/'exe'
            jcmd=executable.resolve().with_name('jcmd')
            if jcmd.is_file():
                diagnostic=subprocess.run([str(jcmd),str(game_pid),'Thread.print'],env=env,capture_output=True,text=True,timeout=8)
                (out/('failure-native-threads-'+str(game_pid)+'.txt')).write_text(diagnostic.stdout+diagnostic.stderr)
        except (OSError,subprocess.SubprocessError) as diagnostic_error:
            report.setdefault('failure_thread_diagnostic_errors',[]).append(str(diagnostic_error))
    if xvfb and xvfb.poll() is None and display_reservation and display_reservation.exists() and display_reservation.read_text()==run_id:
        failure_frame=out/'failure-framebuffer.png'
        result=run(['import','-display',env['DISPLAY'],'-window','root',str(failure_frame)])
        if result.returncode==0 and failure_frame.is_file():report['failure_framebuffer_sha256']=digest(failure_frame)
        try:capture_owned_launcher_tail()
        except (OSError,RuntimeError,AssertionError,ValueError,subprocess.SubprocessError) as diagnostic_error:
            report['launcher_console_capture_error']=str(diagnostic_error)
finally:
    if a.lighting_qa:(out/'lighting-native-acknowledgements.log').write_text('\n'.join(line for line in game_log().splitlines() if 'USEFUL_TOOLS_LIGHTING' in line)+'\n')
    from lighting_shader_diagnostics import compiler_failures
    report['shader_compile_errors']=compiler_failures(game_log())
    if report['shader_compile_errors'] and report.get('status') == 'captures_complete':
        report.update(status='shader_failed', failure_layer='shader',
                      error='Shader compiler failures recorded; completed captures do not certify this pack')
    report['shader_backend_diagnostics']=[line for line in game_log().splitlines() if re.search(r'OpenGL.*error|Invalid argument type',line,re.I)]
    if (work/'spectral-state.log').exists():shutil.copy2(work/'spectral-state.log',out/'spectral-state.log')
    if (work/'spectral-state.log.menu-slots').exists():shutil.copy2(work/'spectral-state.log.menu-slots',out/'infuser-client-slots.log')
    if (work/'spectral-state.log.menu-slots.server').exists():shutil.copy2(work/'spectral-state.log.menu-slots.server',out/'infuser-server-slots.log')
    if (work/'spectral-state.log.hurt-ack').exists():shutil.copy2(work/'spectral-state.log.hurt-ack',out/'native-hurt-final-ack.log')
    if (work/'spectral-state.log.command-ack').exists():shutil.copy2(work/'spectral-state.log.command-ack',out/'native-command-final-ack.log')
    if (game/'logs/latest.log').exists():shutil.copy2(game/'logs/latest.log',out/'client.log')
    if (game/'logs/debug.log').exists():shutil.copy2(game/'logs/debug.log',out/'client-debug.log')
    if (work/'jvm-exceptions.log').exists():
        rows=[line for line in (work/'jvm-exceptions.log').read_text(errors='replace').splitlines() if re.search(r'NoSuchMethodError|NoSuchFieldError|AbstractMethodError|IncompatibleClassChangeError',line)]
        (out/'jvm-linkage-errors.txt').write_text('\n'.join(rows)+'\n')
    if (game/'crash-reports').exists():shutil.copytree(game/'crash-reports',out/'crash-reports',dirs_exist_ok=True)
    # Kill only descendants carrying this unique run marker; never shared services.
    for entry in Path('/proc').iterdir():
        if not entry.name.isdigit() or int(entry.name)==os.getpid():continue
        try:
            if f'{marker}={run_id}'.encode() in (entry/'environ').read_bytes().split(b'\0'):os.kill(int(entry.name),15)
        except OSError:pass
    if xvfb:
        try:xvfb.wait(timeout=5)
        except subprocess.TimeoutExpired:xvfb.kill();xvfb.wait()
    remaining=[]
    for entry in Path('/proc').iterdir():
        if not entry.name.isdigit() or int(entry.name)==os.getpid():continue
        try:
            if f'{marker}={run_id}'.encode() in (entry/'environ').read_bytes().split(b'\0'):
                os.kill(int(entry.name),9);remaining.append(int(entry.name))
        except OSError:pass
    report['cleanup']={'sigkill_after_sigterm':remaining, 'private_launcher_root_retained':True}
    # A failed copy also leaves owned partial payloads; clean those after
    # preserving the failure, without touching shared library symlinks.
    if (work/'libraries').is_dir() and not (work/'libraries').is_symlink():
        shutil.rmtree(work/'libraries')
        report['cleanup']['private_library_copy_removed']=True
    # Evidence is copied above; discard only this run's reproducible game
    # payloads. Retain launcher logs and metadata for failure investigation.
    removed_payloads=[]
    for payload_name in ('saves','screenshots','mods','shaderpacks','natives','resourcepacks'):
        payload=game/payload_name
        if payload.is_dir() and not payload.is_symlink():
            shutil.rmtree(payload);removed_payloads.append(payload_name)
    report['cleanup']['private_game_payloads_removed']=removed_payloads
    archived_worlds=work/'unselected-copied-worlds'
    if archived_worlds.is_dir() and not archived_worlds.is_symlink():
        shutil.rmtree(archived_worlds)
        report['cleanup']['unselected_private_world_copies_removed']=True
    if software_slot_lock:
        fcntl.flock(software_slot_lock.fileno(),fcntl.LOCK_UN);software_slot_lock.close()
    if gpu_lock:
        fcntl.flock(gpu_lock.fileno(),fcntl.LOCK_UN);gpu_lock.close()
    if display_reservation and display_reservation.exists() and display_reservation.read_text()==run_id:display_reservation.unlink()
    if client_admission:client_admission.close()
    (out/'report.json').write_text(json.dumps(report,indent=2)+'\n')
    print(json.dumps({k:report.get(k) for k in ('status','error','display','clean_client_exit','captures')}),flush=True)
raise SystemExit(0 if report['status']=='captures_complete' else 1)
