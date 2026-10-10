"""Pure configuration/evidence helpers shared by packaged spectral validation."""
from pathlib import Path
import fcntl
import hashlib
import json
import os
import re
import struct
import shutil
import signal
import zlib
from collections import Counter

# The owned 1280x720 Forge mods list clips the community port name. RapidOCR
# can additionally drop or confuse the leading pixel-font O while retaining the
# unique integration name, exact game version and clipped publisher suffix.
COMMUNITY_MOD_CONTROL = r'^(?:[O0]?culus) Forge 26\.2 Commun'


def shader_state_control_pattern(enabled):
    """Match only the exact native Oculus state expected before a toggle."""
    return r'^Shaders\s*:\s*' + ('ON' if enabled else 'OFF') + r'$'


def shader_menu_control_candidates(words, pattern):
    """Accept a slightly faint Save only with an unambiguous Oculus screen."""
    candidates = [word for word in words if word['confidence'] >= .65
                  and re.search(pattern, word['text'], re.I)]
    if candidates or pattern != r'^Save$':
        return candidates
    saves = [word for word in words if word['confidence'] >= .60
             and re.fullmatch(r'Save', word['text'], re.I)]
    states = [word for word in words if word['confidence'] >= .65
              and re.fullmatch(r'Shaders\s*:\s*(?:ON|OFF)', word['text'], re.I)]
    packs = [word for word in words if word['confidence'] >= .65
             and re.match(r'Shader\s*Pack\s*:', word['text'], re.I)]
    done = [word for word in words if word['confidence'] >= .65
            and re.fullmatch(r'Done', word['text'], re.I)]
    if (len(saves) == len(states) == len(packs) == len(done) == 1
            and saves[0]['y'] > max(states[0]['y'], packs[0]['y'])
            and abs(saves[0]['y'] - done[0]['y']) <= 12):
        return saves
    return []


def map_root_ocr_to_window(words, geometry, framebuffer_size):
    """Map fresh owned-root OCR coordinates into the verified game window."""
    x, y, width, height = (int(geometry[key]) for key in ('X', 'Y', 'WIDTH', 'HEIGHT'))
    frame_width, frame_height = framebuffer_size
    if (x < 0 or y < 0 or width <= 0 or height <= 0
            or x + width > frame_width or y + height > frame_height):
        raise ValueError('Game window lies outside the owned framebuffer')
    mapped = []
    for word in words:
        root_x, root_y = float(word['x']), float(word['y'])
        if not (x <= root_x < x + width and y <= root_y < y + height):
            continue
        mapped.append(dict(word, x=root_x - x, y=root_y - y,
                           source='owned_xvfb_framebuffer', root_x=root_x,
                           root_y=root_y))
    return mapped


def validate_manifest_agent_logs(command, output):
    """Reject a private agent log path whose parent cannot be opened at premain.

    Native bridge agents use ``agent.jar=donor.jar!/absolute/log``. The harness
    creates its own output directory before launching the JVM. Existing external
    diagnostic directories are also valid. A stale parent path makes premain
    fail before Minecraft starts.
    """
    output=Path(output).resolve()
    for argument in command:
        if argument.startswith('--jvm-arg=-javaagent:'):
            payload=argument.removeprefix('--jvm-arg=-javaagent:')
        elif argument.startswith('-javaagent:'):
            payload=argument.removeprefix('-javaagent:')
        else:
            continue
        if '=' not in payload:
            raise ValueError('Diagnostic agent donor archive is missing: '+argument)
        agent,donor_and_log=payload.split('=',1)
        if '!' in donor_and_log:
            donor,log=donor_and_log.rsplit('!',1)
            if not Path(agent).is_file() or not Path(donor).is_file():
                raise ValueError('Diagnostic agent or donor archive is unavailable: '
                                 +agent+' ; '+donor)
        else:
            log=donor_and_log
            if not Path(agent).is_file():
                raise ValueError('Diagnostic agent is unavailable: '+agent)
        destination=Path(log)
        if not destination.is_absolute() or (destination.parent.resolve()!=output
                                              and not destination.parent.is_dir()):
            raise ValueError('Diagnostic agent log parent is unavailable: '+str(destination))


def extract_community_gbuffer_proof(jvm_arguments, run_id, publisher_sha256, shader_pack_sha256):
    """Bind exact bridge/event logs to the harness run without accepting claims alone."""
    for argument in jvm_arguments:
        payload=argument.removeprefix('-javaagent:') if argument.startswith('-javaagent:') else None
        if payload is None or '=' not in payload or '!' in payload:continue
        bridge_name,transform_name=payload.split('=',1)
        bridge=Path(bridge_name);transform=Path(transform_name)
        if not bridge.is_file() or not transform.is_file():continue
        text=transform.read_text(errors='replace')
        ready=re.search(r'^OCULUS_262_GBUFFER_BRIDGE ready targets=5 run_token=([0-9a-f]{32}) event_log=(/[^\r\n]+)$',text,re.M)
        if not ready:continue
        if ready.group(1)!=run_id:raise ValueError('Oculus gbuffer proof belongs to a stale harness run')
        event_log=Path(ready.group(2))
        if not event_log.is_file():raise ValueError('Oculus gbuffer event log is missing')
        events=[]
        for line in event_log.read_text().splitlines():
            if line.strip():events.append(json.loads(line))
        return {
            'schema':1,'run_token':run_id,'publisher_sha256':publisher_sha256,
            'shader_pack_sha256':shader_pack_sha256,'events':events,
            'bridge':{'path':str(bridge.resolve()),'sha256':hashlib.sha256(bridge.read_bytes()).hexdigest()},
            'transform_log':{'path':str(transform.resolve()),'sha256':hashlib.sha256(transform.read_bytes()).hexdigest()},
            'event_log':{'path':str(event_log.resolve()),'sha256':hashlib.sha256(event_log.read_bytes()).hexdigest()}
        }
    return None


def acquire_client_admission(path, wait_seconds=0):
    """Fail before copying profiles when another ordinary client test owns the slot."""
    path=Path(path)
    path.parent.mkdir(mode=0o700,parents=True,exist_ok=True)
    descriptor=os.open(path,os.O_CREAT|os.O_RDWR,0o600)
    handle=os.fdopen(descriptor,'a+')
    try:
        fcntl.flock(handle.fileno(),fcntl.LOCK_EX|fcntl.LOCK_NB)
    except BlockingIOError:
        if not wait_seconds:
            handle.close()
            raise
        if not 0 < wait_seconds <= 3600:
            handle.close()
            raise ValueError('Client admission wait must be at most 3600 seconds')
        def expired(signum, frame):
            raise BlockingIOError('Admission wait expired')
        previous = signal.signal(signal.SIGALRM, expired)
        signal.setitimer(signal.ITIMER_REAL, wait_seconds)
        try:
            fcntl.flock(handle.fileno(),fcntl.LOCK_EX)
        except BaseException:
            handle.close()
            raise
        finally:
            signal.setitimer(signal.ITIMER_REAL,0)
            signal.signal(signal.SIGALRM,previous)
    except BaseException:
        handle.close()
        raise
    return handle


def isolate_world_selection(saves, archive):
    """Leave one copied test world visible so duplicate menu titles cannot misroute."""
    saves, archive = Path(saves), Path(archive)
    worlds = [path for path in saves.iterdir() if path.is_dir() and (path / 'level.dat').is_file()]
    if not worlds:
        raise ValueError('No copied acceptance world contains level.dat')
    if archive == saves or saves in archive.parents:
        raise ValueError('Unselected world archive must be outside the visible saves directory')
    selected = worlds[0]
    moved = []
    for path in worlds[1:]:
        archive.mkdir(parents=True, exist_ok=True)
        destination = archive / path.name
        if destination.exists():
            raise ValueError('Unselected world archive already exists: ' + str(destination))
        shutil.move(str(path), str(destination))
        moved.append(path.name)
    return selected.name, moved


def native_command_status(text, token):
    """Ignore another request's acknowledgement; a sent packet is not acceptance."""
    lines = text.splitlines()
    if not lines or lines[0] != token:
        return None
    if len(lines) < 3 or lines[1] not in ('SENT', 'FAILED'):
        raise ValueError('Malformed native command acknowledgement')
    return lines[1]


def native_hurt_observation(text, token, now_ms):
    """A command acknowledgement alone does not prove the client's hurt flash."""
    lines = text.splitlines()
    if not lines or lines[0] != token:
        return None
    if len(lines) >= 2 and lines[1] == 'FAILED':
        raise ValueError('Native hurt observer failed: ' + '\n'.join(lines[2:]))
    if len(lines) != 5 or lines[1] != 'HURT':
        raise ValueError('Malformed native hurt observation')
    stamp, ticks = int(lines[2]), int(lines[3])
    if not 0 <= now_ms - stamp <= 150 or not 5 <= ticks <= 10:
        return None
    return {'time_ms': stamp, 'hurt_ticks': ticks, 'entity_class': lines[4]}


def native_frame_observation(text, token):
    """A frame trial requires this request's complete, positive native sample array."""
    lines=text.splitlines()
    if not lines or lines[0]!=token:return None
    if len(lines)!=3 or lines[1]!='COMPLETE':raise ValueError('Native frame observer failed: '+text)
    samples=json.loads(lines[2])
    if not isinstance(samples,list) or len(samples)!=1800 or any(type(value)!=int or value<=0 for value in samples):
        raise ValueError('Invalid native frame trial')
    return samples


def command_feedback(before, text, expected):
    """Only new native feedback may certify a command, including repeated lines."""
    previous = Counter(before)
    fresh = []
    for line in text.splitlines():
        if previous[line]:
            previous[line] -= 1
        else:
            fresh.append(line)
    feedback = '\n'.join(fresh)
    if re.search(r'Unknown or incomplete command|Incorrect argument|Unknown entity|Unknown item|No permission|unexpected error occurred|cannot support that enchantment', feedback, re.I):
        return 'rejected'
    return 'accepted' if re.search(expected, feedback, re.I) else 'pending'


def reserve_display(reservation, run_id):
    """Claim a display without leaking an empty reservation after quota failure."""
    try:
        descriptor = os.open(reservation, os.O_CREAT | os.O_EXCL | os.O_WRONLY, 0o600)
    except FileExistsError:
        return False
    try:
        identity = run_id.encode()
        if os.write(descriptor, identity) != len(identity):
            raise OSError('Incomplete virtual display reservation')
    except OSError:
        reservation.unlink()
        raise
    finally:
        os.close(descriptor)
    return True


def complete_png(data):
    """Reject a screenshot while the game's asynchronous writer is still working."""
    if not data.startswith(b'\x89PNG\r\n\x1a\n'):
        return False
    offset = 8
    while offset + 12 <= len(data):
        size = struct.unpack('>I', data[offset:offset+4])[0]
        end = offset + size + 12
        if end > len(data):
            return False
        chunk = data[offset+4:end-4]
        if zlib.crc32(chunk) & 0xffffffff != struct.unpack('>I', data[end-4:end])[0]:
            return False
        if chunk[:4] == b'IEND':
            return size == 0 and end == len(data)
        offset = end
    return False


SHADER_MOD_NAMES = ("iris", "oculus", "optifine", "shaderloader")
SHADERS_MENU_CONTROL = r'^(?:Shaders\s*\.{0,3}|of\.options\.shaders)$'
OPTIFINE_OFF_CONTROL = r'^(?:[OQ]FF|of\.options\.shaders\.packNone)$'


def classic_beddium_observation(log):
    """All source stages must exercise the active native renderer/cache pair."""
    pattern = (r'USEFUL_TOOLS_LIGHTING_QA_BACKEND_PASS beddium_cache stage=(\d+) '
               r'packed=(\d+) required=(\d+) vanilla_cache_unchanged=true')
    stages = {}
    for match in re.finditer(pattern, log):
        stage, packed, required = map(int, match.groups())
        row = {'stage':stage, 'packed':packed, 'required':required}
        if stage in stages and stages[stage] != row:
            raise ValueError('Conflicting native Beddium cache observations')
        if packed & 255 < required:
            raise ValueError('Native Beddium sample omitted required visual light')
        stages[stage] = row
    if set(stages) != set(range(22)) or not any(row['required'] > 0 for row in stages.values()):
        raise ValueError('Native Beddium cache acceptance requires all 22 source stages and positive visual light')
    return [stages[stage] for stage in range(22)]


def validation_mode(mode, shader_pack, disabled, client_mods):
    selected = mode or ("disabled" if disabled else "active" if shader_pack else "baseline")
    shader_mods = [p for p in client_mods if any(n in p.name.lower() for n in SHADER_MOD_NAMES)]
    if selected == "baseline" and (shader_mods or shader_pack or disabled):
        raise ValueError("Baseline must not contain a shader loader or shader pack")
    if selected == "active" and (not shader_pack or disabled):
        raise ValueError("Active mode requires a pack and must not disable shaders")
    if selected == "disabled" and (not shader_mods or shader_pack):
        raise ValueError("Disabled control requires an installed shader loader and no active pack")
    return selected


def set_option(text, name, value, separator="="):
    return re.sub(r"^" + re.escape(name + separator) + r".*\n?", "", text,
                  flags=re.M) + name + separator + str(value) + "\n"


def set_instance_option(text, name, value):
    """Prism reads launch overrides from [General], not the final UI section."""
    section = re.search(r'^\[General\]\n(.*?)(?=^\[|\Z)', text, re.M | re.S)
    if section is None:
        raise ValueError('Prism instance is missing its General section')
    return text[:section.start(1)] + set_option(section.group(1), name, value) + text[section.end(1):]


def configure_shaders(game: Path, integration, mode, pack_name=None):
    """Replace inherited pack settings, including OptiFine's OFF control."""
    active = mode == "active"
    if integration == "optifine":
        path = game / "optionsshaders.txt"
        text = path.read_text() if path.exists() else ""
        path.write_text(set_option(text, "shaderPack", pack_name if active else "OFF"))
    elif integration == "community":
        path = game / "config/oculus.properties"
        path.parent.mkdir(exist_ok=True)
        path.write_text("shaders.enabled=" + str(active).lower() + "\nshaderpack="
                        + (pack_name if active else "") + "\n")
    else:
        for path in (game / "config/oculus.properties", game / "config/iris.properties",
                     game / "iris.properties"):
            path.parent.mkdir(exist_ok=True)
            path.write_text("enableShaders=" + str(active).lower() + "\nshaderPack="
                            + (pack_name if active else "") + "\nmaxShadowRenderDistance=4\n")


def activation_reported(log, integration, pack_name):
    if integration == "community":
        label = re.escape(pack_name[:-4]) + r"(?:\.zip)?" if pack_name.endswith(".zip") else re.escape(pack_name)
        pattern = r"Activated shader pack '" + label + r"' with \d+ Forge"
    elif integration == "optifine":
        pattern = r"(?:Loaded|Using) shader(?:\s*pack)?[: ]+" + re.escape(pack_name)
    else:
        pattern = r"(?:Using|Loaded) shaderpack: ?" + re.escape(pack_name)
    return bool(re.search(pattern, log, re.I))


def pack_control_pattern(filename):
    """Match one exact pack label while tolerating OCR's omitted spaces."""
    pattern = r'\s*'.join(re.escape(word) for word in filename.split())
    # Inspected native OptiFine captures recognize the pixel-font capital R
    # as Fi. Keep the entire actual pack version and ZIP suffix exact.
    # Fresh native shader-list contrast OCR can read BSL's pixel-font B as 6
    # and its version-prefix v as 4 or w. The following SL marker, complete
    # numeric version, and ZIP suffix stay exact.
    if filename.startswith('BSL_v'):
        pattern = pattern.replace('BSL_v', r'[B6]SL_[v4w]', 1)
    if filename.startswith('ComplementaryReimagined_'):
        pattern = pattern.replace('ComplementaryReimagined_', r'Complementary(?:R|Fi)eimagined_', 1)
    return r'^\s*' + pattern + r'\s*$'


def acknowledged(feedback, expected):
    failure = re.search(r"Unknown or incomplete command|Incorrect argument|Unknown entity|Unknown item|"
                        r"No permission|unexpected error occurred|cannot support that enchantment|"
                        r"commands\..*\.failed", feedback, re.I)
    return not failure and bool(re.search(expected, feedback, re.I))


def menu_failure(words):
    """Recognize a native disconnect from its title and return control together."""
    reliable = [word for word in words if word['confidence'] >= .65]
    title = next((w for w in reliable if re.fullmatch(
        r'Failed\s*to\s*connect\s*to\s*the\s*server', w['text'], re.I)), None)
    back = next((w for w in reliable if re.fullmatch(
        r'Back\s*to\s*(?:Server\s*List|Title\s*Screen)', w['text'], re.I)), None)
    if title and back:
        return 'Native world-entry disconnect: ' + ' | '.join(w['text'] for w in reliable)
    return None


def shutdown_control(words, multiplayer=False):
    pattern = r'^Disconnect$' if multiplayer else r'^Save.*(?:Quit|Ouit).*Title$'
    matches = [word for word in words if word['confidence'] >= .65
               and re.search(pattern, word['text'], re.I)]
    if len(matches) > 1:
        raise ValueError('Ambiguous native shutdown control')
    return matches[0] if matches else None


def menu_action(words, world, single_world=False):
    """Choose only controls identified in the current owned framebuffer."""
    reliable = [word for word in words if word['confidence'] >= .65]
    if any(re.search(r'Warnings while loading mods', w['text'], re.I) for w in reliable):
        proceed = next((w for w in reliable if re.search(r'^[PF]roceed to main menu$', w['text'], re.I)), None)
        if proceed:
            return 'acknowledge_loading_warnings', proceed
    # Observed 26.2 pixel-font OCR alias. Require both native confirmation
    # controls before accepting the exact misread button; never infer position.
    experimental_title = any(re.fullmatch(
        r'Worlds using Exper(?:imental|inental) Settings are not supported',
        word['text'], re.I) for word in reliable)
    backup_control = any(re.fullmatch(r'Create Backup and Load', word['text'], re.I)
                         for word in reliable)
    if experimental_title and backup_control:
        aliases = [word for word in reliable if re.fullmatch(
            r"I\s*Kinow\s*WhatI'm\s*Coing!", word['text'], re.I)]
        if len(aliases) == 1:
            return 'confirm_experimental', aliases[0]
    for pattern, action in ((r'^Single\s*player$', 'singleplayer'),
                            (r'^I\s*know\s*what.*doing!?$', 'confirm_experimental')):
        match = next((w for w in reliable if re.search(pattern, w['text'], re.I)), None)
        if match:
            return action, match
    if any(re.search(r'(Select World|[PF]lay Selected [WHM]orld)', w['text'], re.I) for w in reliable):
        selected = next((w for w in reliable if w.get('source') == 'native_classic_gui'
                         and re.fullmatch(r'Play Selected World', w['text'], re.I)), None)
        if selected:
            return 'play_selected_world', selected
        # Minecraft's pixel font makes N/W look like H to OCR. Match only the
        # known copied world title, with a tight distance bound; never buttons.
        def distance(text):
            expected_title = re.sub(r'\s+', '', world.casefold())
            previous = list(range(len(expected_title)+1))
            for i, character in enumerate(re.sub(r'\s+', '', text.casefold()), 1):
                current = [i]
                for j, expected in enumerate(expected_title, 1):
                    confusion = frozenset((character, expected)) in (
                        frozenset(('n', 'h')), frozenset(('w', 'h')), frozenset(('w', 'u')))
                    cost = 0 if character == expected else .5 if confusion else 1
                    current.append(min(current[-1]+1, previous[j]+1,
                                       previous[j-1]+cost))
                previous = current
            return previous[-1]
        matches = [w for w in reliable if distance(w['text'].strip()) <= min(2, len(world)//4)]
        if matches:
            return 'open_world', min(matches, key=lambda w: w['y'])
        # A private copied save is reduced to one world before this menu opens.
        # Its readable mode/version line remains inside the same selectable row
        # even when OCR cannot read the title. Never use this for an unisolated
        # list, or when more than one row is observed.
        metadata=[w for w in reliable if re.search(
            r'^(?:Sur(?:v|w|y)?ival|Creative|Hardcore)\s+Mode[,.].*Version:',w['text'],re.I)]
        if single_world and len(metadata)==1:
            return 'open_world', metadata[0]
    return None
def infuser_fixture_nbt(version, loader_uid):
    """Native inventory layouts from each target's Infuser save/load methods."""
    count='Count:1b' if version.startswith('1.20.') else 'count:1'
    items='Items:[{Slot:0b,id:"minecraft:iron_sword",'+count+'},{Slot:1b,id:"usefultoolsmod:ectoplasm",'+count+'}]'
    if version.startswith('1.') and loader_uid in ('net.minecraftforge','net.neoforged'):
        items='inventory:{Size:3,'+items+'}'
    return '{progress:-10000,'+items+'}'

def populated_infuser_client_slots(text, now_ms):
    """Require a fresh native client snapshot, not just populated server NBT."""
    try:
        age = now_ms - int(text.split()[0])
    except (ValueError, IndexError):
        return False
    return (0 <= age <= 5000
            and 'SpectralInfuserMenu' in text.splitlines()[0]
            and re.search(r'^36 1 minecraft:iron_sword$', text, re.M) is not None
            and re.search(r'^37 1 usefultoolsmod:ectoplasm$', text, re.M) is not None)


def enchanted_sword_fixture(version):
    """Use native serialized enchantments to exercise glints independently of item tags."""
    if version.startswith('1.20.'):
        return 'usefultoolsmod:hglowstone_sword{Enchantments:[{id:"minecraft:unbreaking",lvl:1s}]}'
    # 1.21.1 accepts this map via its LEVELS_CODEC alternative; 26.x uses
    # the same map directly. No obsolete tooltip/levels wrapper is required.
    return 'usefultoolsmod:hglowstone_sword[minecraft:enchantments={"minecraft:unbreaking":1}]'
def native_console_errors(text):
    """Keep native Java exception frames without retaining launcher arguments."""
    import re
    result = []
    for line in text.splitlines():
        stripped = line.strip()
        if re.search(r'auth|token|credential|password|session', stripped, re.I):
            continue
        if (re.match(r'^(?:Caused by:\s*)?(?:java|javax|net|org|com|cpw|optifine)\.[\w.$]+(?:Exception|Error)(?::|$)', stripped)
                or re.match(r'^Exception in thread "[\w -]+" (?:java|net|org|com|cpw|optifine)\.', stripped)
                or re.match(r'^at [\w.$/@-]+\([^\r\n]*\)$', stripped)):
            result.append(stripped)
    return '\n'.join(result) + ('\n' if result else '')


def requested_wraith_floor(arguments):
    prefix = "-Dusefultools.wraithShaderLight="
    for argument in reversed(arguments):
        if argument.startswith(prefix):
            return max(10, min(15, int(argument[len(prefix):])))
    return None


def install_weather_control(world, version):
    """Install a private predicate that checks native server weather state."""
    formats = {'1.20.1': 15, '1.20.2': 18, '1.21.1': 48,
               '26.1.2': [101, 1], '26.2': [107, 1]}
    value = formats[version]
    metadata = {'description': 'Private spectral recovery weather assertions'}
    if isinstance(value, list):
        metadata.update(min_format=value, max_format=value)
    else:
        metadata['pack_format'] = value
    directory = Path(world) / 'datapacks' / 'spectral_recovery_weather'
    folder = 'predicates' if version.startswith('1.20.') else 'predicate'
    files = {'pack.mcmeta': {'pack': metadata},
             'data/spectral_recovery/' + folder + '/rain.json':
                 {'condition': 'minecraft:weather_check', 'raining': True}}
    hashes = {}
    for name, content in files.items():
        destination = directory / name
        destination.parent.mkdir(parents=True, exist_ok=True)
        destination.write_text(json.dumps(content, indent=2) + '\n')
        hashes[name] = hashlib.sha256(destination.read_bytes()).hexdigest()
    return {'version': version, 'files_sha256': hashes,
            'predicate': 'spectral_recovery:rain'}
