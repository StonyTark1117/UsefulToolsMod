#!/usr/bin/env python3
"""Run native companion equipment checks on isolated Xvfb and save captures/logs."""
import importlib.util,json,subprocess,sys,time,argparse,os,uuid,atexit,re
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
spec=importlib.util.spec_from_file_location('dynamic_lighting_smoke',ROOT/'scripts/smoke-client.py');smoke=importlib.util.module_from_spec(spec);sys.modules[spec.name]=smoke;spec.loader.exec_module(smoke)
p=argparse.ArgumentParser();p.add_argument('target',choices=smoke.TARGETS);p.add_argument('--offline',action='store_true');p.add_argument('--client-mod',type=Path,action='append',default=[]);p.add_argument('--profile',default='vanilla');opts=p.parse_args()
out=ROOT/'validation/dynamic-lighting-2.3.2'/('runtime' if opts.profile=='vanilla' else 'backend-runtime/'+opts.profile)/opts.target;out.mkdir(parents=True,exist_ok=True)
if (out/'native-client.log').exists():
 archive=out/('previous-'+str(time.time_ns()));archive.mkdir()
 for f in list(out.glob('*.log'))+list(out.glob('*.json')):
  f.rename(archive/f.name)
original_popen=subprocess.Popen

def popen(command,*args,**kwargs):
 if command and command[0]=='./gradlew': command=[command[0],'-I',str(ROOT/'tests/dynamic-lighting/qa.gradle'),*command[1:]]
 return original_popen(command,*args,**kwargs)
subprocess.Popen=popen
reservation_token=uuid.uuid4().hex
reserved_display=None
def free_display():
 global reserved_display
 for number in range(240,271):
  lock=Path('/tmp/useful-tools-lighting-display-'+str(number)+'.lock')
  try:fd=os.open(lock,os.O_CREAT|os.O_EXCL|os.O_WRONLY,0o600)
  except FileExistsError:continue
  os.write(fd,reservation_token.encode());os.close(fd)
  if Path('/tmp/.X11-unix/X'+str(number)).exists() or Path('/tmp/.X'+str(number)+'-lock').exists():
   lock.unlink();continue
  reserved_display=lock;return number
 raise RuntimeError('No free isolated lighting display')
def release_display():
 if reserved_display and reserved_display.exists() and reserved_display.read_text()==reservation_token:reserved_display.unlink()
atexit.register(release_display)
smoke.free_display=free_display
if opts.target=='1.7.10-forge':
 original_xdo=smoke.xdo
 def xdo(display,*parts,**kwargs):
  if parts and parts[0]=='windowsize':parts=(*parts[:-2],'854','480')
  return original_xdo(display,*parts,**kwargs)
 smoke.xdo=xdo
 smoke.wait_for_window_replacement=lambda display,previous,timeout:smoke.window_id(display,timeout)
 original_quick_click=smoke.quick_click
 def quick_click(display,x,y):
  if (x,y)==(206,443):
   original_quick_click(display,427,250);time.sleep(.3);original_quick_click(display,427,250);time.sleep(.3)
  return original_quick_click(display,x,y)
 smoke.quick_click=quick_click
def capture(display,path,window,game_dir):
 global live_game_dir
 live_game_dir=game_dir
 if path.name.endswith('-world.png'):
  deadline=time.monotonic()+180
  diagnostic_saved=False
  while time.monotonic()<deadline:
   logs=list((game_dir/'logs').glob('*latest.log'));text='\n'.join(p.read_text(errors='replace') for p in logs)
   (out/'native-client.log').write_text(text)
   if 'Game crashed!' in text or '#@!@#' in text:raise RuntimeError('native client crashed; see saved native-client.log')
   if 'USEFUL_TOOLS_LIGHTING_QA_FAIL' in text:raise RuntimeError('native dynamic lighting fixture failed')
   if 'USEFUL_TOOLS_LIGHTING_QA_PASS' in text:break
   if not diagnostic_saved and deadline-time.monotonic()<60:
    diagnostic_saved=True
    subprocess.run(['magick','import','-window',window,str(out/'waiting-world.png')],env={**smoke.os.environ,'DISPLAY':display,'WAYLAND_DISPLAY':''},check=True,timeout=20)
    for proc in Path('/proc').iterdir():
     if not proc.name.isdigit():continue
     try:
      envbytes=(proc/'environ').read_bytes().split(b'\0');cmd=(proc/'cmdline').read_bytes()
     except OSError:continue
     if ('DISPLAY='+display).encode() in envbytes and b'ALSOFT_DRIVERS=null' in envbytes and b'BootstrapLauncher' in cmd:
      with (out/'waiting-client-threads.txt').open('w') as report:subprocess.run(['/usr/lib/jvm/java-21-openjdk/bin/jcmd',proc.name,'Thread.print'],stdout=report,stderr=subprocess.STDOUT)
   time.sleep(.5)
  else:raise RuntimeError('native dynamic lighting fixture did not complete')
 subprocess.run(['magick','import','-window',window,str(path)],env={**smoke.os.environ,'DISPLAY':display,'WAYLAND_DISPLAY':''},check=True,timeout=20)
original_click=smoke.click
def click(display,window,x,y):
 global reopened
 if x==527 and (out/'native-client.log').exists():
  # Every path belongs to the isolated launch; the live game directory is captured below.
  if live_game_dir is not None:
   logs=list((live_game_dir/'logs').glob('*latest.log'))
   (out/'native-client.log').write_text('\n'.join(p.read_text(errors='replace') for p in logs))
  if live_game_dir is not None and not reopened and 'USEFUL_TOOLS_LIGHTING_QA_PASS' in (out/'native-client.log').read_text():
   reopened=True
   single_y=(237 if opts.target=='26.2-fabric' else 204 if opts.target in {'1.21.1-neoforge','26.1.2-neoforge','26.2-neoforge'} else 211 if 'fabric' in opts.target else 237)
   original_click(display,window,427,single_y);time.sleep(8)
   subprocess.run(['magick','import','-window',smoke.window_id(display,5),str(out/'saved-world-list.png')],env={**smoke.os.environ,'DISPLAY':display,'WAYLAND_DISPLAY':''},check=True,timeout=20)
   # The first row is the disposable world just saved. Double-click its actual list region.
   row_y=210 if opts.target=='1.7.10-forge' else 130
   def open_saved():
    for gui_x,gui_y in [(427,row_y),(267,396)]:
     px,py=smoke.scaled_window_point(display,smoke.window_id(display,5),gui_x,gui_y)
     smoke.xdo(display,'mousemove','--sync',str(px),str(py))
     smoke.xdo(display,'mousedown','1');time.sleep(.1);smoke.xdo(display,'mouseup','1');time.sleep(.2)
   open_saved()
   deadline=time.monotonic()+120
   last_menu=time.monotonic()
   menu_count=0
   while time.monotonic()<deadline:
    native='\n'.join(p.read_text(errors='replace') for p in (live_game_dir/'logs').glob('*latest.log'))
    (out/'native-client.log').write_text(native)
    if 'USEFUL_TOOLS_LIGHTING_QA_FAIL' in native:raise RuntimeError('Saved-world fixture failed')
    if 'USEFUL_TOOLS_LIGHTING_QA_REOPEN_PASS' in native:break
    if 'USEFUL_TOOLS_LIGHTING_QA_REOPEN_READY' not in native and time.monotonic()-last_menu>6:
     menu_count+=1;last_menu=time.monotonic()
     current=smoke.window_id(display,5);frame=out/('reopen-menu-'+str(menu_count)+'.png')
     subprocess.run(['magick','import','-window',current,str(frame)],env={**smoke.os.environ,'DISPLAY':display,'WAYLAND_DISPLAY':''},check=True,timeout=20)
     words=json.loads(subprocess.check_output(['/tmp/usefultools-spectral-ocr/bin/python',str(ROOT/'scripts/spectral-menu-ocr.py'),str(frame)],text=True))
     (out/('reopen-menu-'+str(menu_count)+'.json')).write_text(json.dumps(words,indent=2)+'\n')
     import spectral_validation
     action=spectral_validation.menu_action(words,'New World')
     if action is None and any('experimental' in word['text'].lower() for word in words):
      # Pixel-font OCR can omit spaces and read the final g as a. Restrict the
      # fallback to the captured experimental-settings confirmation screen.
      confirm=next((word for word in words if word['confidence']>=.65 and re.search('know.*doi',re.sub('[^a-z]','',word['text'].lower()))),None)
      if confirm:action=('confirm_experimental',confirm)
     if action:
      kind,word=action
      smoke.xdo(display,'mousemove','--window',current,str(round(word['x'])),str(round(word['y'])))
      for _ in range(2 if kind=='open_world' else 1):
       smoke.xdo(display,'mousedown','1');time.sleep(.1);smoke.xdo(display,'mouseup','1');time.sleep(.1)
    time.sleep(.5)
   else:raise RuntimeError('Saved-world reopen acceptance did not complete')
   window=smoke.window_id(display,5)
   subprocess.run(['magick','import','-window',window,str(out/'reopened-world.png')],env={**smoke.os.environ,'DISPLAY':display,'WAYLAND_DISPLAY':''},check=True,timeout=20)
   smoke.xdo(display,'key','--window',window,'Escape');time.sleep(1)
   quit_save=348 if 'fabric' in opts.target or opts.target in {'1.7.10-forge','26.2-neoforge'} else 383
   original_click(display,window,427,quit_save);time.sleep(20)
   (out/'native-client.log').write_text('\n'.join(p.read_text(errors='replace') for p in (live_game_dir/'logs').glob('*latest.log')))
 return original_click(display,window,x,y)
live_game_dir=None
reopened=False
smoke.click=click
smoke.screenshot=capture
argv=[sys.argv[0],opts.target,'--gradle-property','usefulToolsLighting=true','--output-dir',str(out),'--timeout','600']
if opts.target in {'1.20.2-neoforge','26.1.2-neoforge','26.2-neoforge'}:
 # NeoGradle can delete other versions' shared objects while downloading.
 # Skip its downloader only after checking every object in the exact native index.
 import hashlib
 version=opts.target.rsplit('-',1)[0]
 indexes=list((Path.home()/'.gradle/caches/fabric-loom/assets/indexes').glob(version+'-*.json'))
 if len(indexes)!=1:raise RuntimeError('Exact Minecraft asset index unavailable')
 objects=json.loads(indexes[0].read_text())['objects']
 assetroot=Path.home()/'.gradle/caches/minecraft/assets'
 def valid(entry):
  obj=assetroot/'objects'/entry['hash'][:2]/entry['hash']
  return obj.is_file() and hashlib.sha1(obj.read_bytes()).hexdigest()==entry['hash']
 if not all(valid(entry) for entry in objects.values()):
  # Restore only hash-verified objects from this workspace's other native
  # loader caches, then repeat the exact-version check before launching.
  restored=subprocess.check_output([sys.executable,str(ROOT/'scripts/restore-lighting-assets.py')],text=True)
  (out/'asset-cache-restoration.json').write_text(restored)
 for entry in objects.values():
  if not valid(entry):
   raise RuntimeError('Missing or invalid indexed Minecraft asset '+entry['hash'])
 (out/'verified-assets.json').write_text(json.dumps({'index':str(indexes[0]),'sha256':hashlib.sha256(indexes[0].read_bytes()).hexdigest(),'objects':len(objects),'all_sha1_verified':True},indent=2)+'\n')
 argv+=['--skip-asset-download']
if opts.offline:argv+=['--offline']
for mod in opts.client_mod:argv+=['--client-mod',str(mod.resolve())]
sys.argv=argv;args=smoke.parse_args();result=smoke.run_target(opts.target,smoke.TARGETS[opts.target],args)
log=(out/(opts.target+'.log')).read_text(errors='replace');native=(out/'native-client.log').read_text(errors='replace') if (out/'native-client.log').exists() else ''
passes=[line for line in native.splitlines() if 'USEFUL_TOOLS_LIGHTING_QA_CASE_PASS' in line]
success=result and 'USEFUL_TOOLS_LIGHTING_QA_PASS' in native and 'USEFUL_TOOLS_LIGHTING_QA_REOPEN_PASS' in native
performance=[]
for line in native.splitlines():
 match=re.search(r'QA_PERF phase=(\S+) adapter_us=(\S+) mean_fps=(\S+) tick_samples=(\d+) fps_samples=(\d+) fps_cap=(\d+) vsync=(\S+)',line)
 if match:
  phase,adapter,fps,ticks,samples,cap,vsync=match.groups()
  performance.append({'phase':phase,'adapter_us':float(adapter),'mean_fps':float(fps),'tick_samples':int(ticks),'fps_samples':int(samples),'fps_cap':int(cap),'vsync':vsync=='true'})
(out/'performance.json').write_text(json.dumps({'observations':performance,'method':'One extra native adapter tick timed per client tick; four sequential phases, 40 warm-up and 100 measured ticks each. FPS is a sampled client counter, not a frame-time percentile or a GPU benchmark.','environment':'Isolated Xvfb; concurrent matrix activity may affect FPS and CPU timing.'},indent=2)+'\n')
evidence_spec=importlib.util.spec_from_file_location('lighting_evidence',ROOT/'scripts/lighting-evidence.py');evidence=importlib.util.module_from_spec(evidence_spec);evidence_spec.loader.exec_module(evidence)
(out/'tested-files.json').write_text(json.dumps(evidence.identity(opts.target),indent=2)+'\n')
(out/'equipment-checks.json').write_text(json.dumps({'success':success,'cases':passes,'isolation':{'framebuffer':'1280x720x24','audio':'ALSOFT_DRIVERS=null','wayland':'unset'},'coverage':'Native equipment, advanced entity/world checks, and saved-world reopen/removal; shader/backend combinations and performance comparisons remain separately tracked.', 'advanced_cases':[line for line in native.splitlines() if 'USEFUL_TOOLS_LIGHTING_QA_ADVANCED_PASS' in line]},indent=2)+'\n')
raise SystemExit(0 if success else 1)
