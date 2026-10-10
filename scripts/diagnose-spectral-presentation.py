#!/usr/bin/env python3
"""Bounded owned-Xvfb experiment comparing Zink presentation routes."""
import fcntl
import argparse
import json
import os
from pathlib import Path
import subprocess
import time
import uuid

root=Path(__file__).resolve().parents[1]
parser=argparse.ArgumentParser(description=__doc__)
parser.add_argument('--virtualgl',type=Path,help='Extracted publisher package root; test EGL devices without a host X display')
args=parser.parse_args()
out=root/'validation/shader-support-2.3.2/recovery/implementation/presentation-experiment'
if args.virtualgl:out=out/'virtualgl'
out.mkdir(parents=True,exist_ok=True)
env=os.environ.copy()
for name in ('DISPLAY','WAYLAND_DISPLAY','WAYLAND_SOCKET','GALLIUM_DRIVER','LIBGL_KOPPER_DRI2','LIBGL_KOPPER_DISABLE','LIBGL_ALWAYS_SOFTWARE'):
    env.pop(name,None)
env['ALSOFT_DRIVERS']='null'
run_id=uuid.uuid4().hex
xvfb=None; reservation=None;results=[]
def run(cmd,route=env):return subprocess.run(cmd,env=route,capture_output=True,text=True,timeout=15)
with open('/tmp/useful-tools-spectral-gpu.lock','a+') as gpu:
    fcntl.flock(gpu.fileno(),fcntl.LOCK_EX)
    try:
        # Respect still-running clients from harness versions predating the lock.
        while True:
            clients=[]
            for p in Path('/proc').iterdir():
                if not p.name.isdigit():continue
                try:
                    cmd=(p/'cmdline').read_bytes().split(b'\0');route=(p/'environ').read_bytes().split(b'\0')
                    if cmd[0].endswith(b'java') and b'org.prismlauncher.EntryPoint' in cmd and b'GALLIUM_DRIVER=zink' in route:clients.append(p.name)
                except OSError:pass
            if not clients:break
            time.sleep(2)
        for number in range(120,150):
            reservation=Path(f'/tmp/useful-tools-spectral-display-{number}.lock')
            try:fd=os.open(reservation,os.O_CREAT|os.O_EXCL|os.O_WRONLY,0o600)
            except FileExistsError:continue
            os.write(fd,run_id.encode());os.close(fd)
            if Path(f'/tmp/.X{number}-lock').exists() or Path(f'/tmp/.X11-unix/X{number}').exists():reservation.unlink();continue
            env['DISPLAY']=f':{number}';break
        else:raise RuntimeError('No free owned display')
        xvfb=subprocess.Popen(['Xvfb',env['DISPLAY'],'-screen','0','1280x720x24','-nolisten','tcp'],env=env,stdout=(out/'xvfb.log').open('w'),stderr=subprocess.STDOUT)
        for _ in range(50):
            lock=Path(f'/tmp/.X{number}-lock')
            if xvfb.poll() is not None:raise RuntimeError('Xvfb exited')
            if lock.exists() and lock.read_text().strip()==str(xvfb.pid) and run(['xdotool','getdisplaygeometry']).stdout.strip()=='1280 720':break
            time.sleep(.1)
        else:raise RuntimeError('Owned display readiness failed')
        cases=[('software',{'LIBGL_ALWAYS_SOFTWARE':'true'},[]),('zink-disabled',{'GALLIUM_DRIVER':'zink','LIBGL_KOPPER_DRI2':'true','LIBGL_KOPPER_DISABLE':'true'},[]),('zink-dri2',{'GALLIUM_DRIVER':'zink','LIBGL_KOPPER_DRI2':'true'},[]),('zink-default',{'GALLIUM_DRIVER':'zink'},[])]
        if args.virtualgl:
            cases=[('virtualgl-'+device,{},[str(args.virtualgl/'opt/VirtualGL/bin/vglrun'),'-ld',str(args.virtualgl/'usr/lib'),'-d',device,'-c','proxy']) for device in ('egl0','egl1')]
        for name,options,prefix in cases:
            route=dict(env,**options);info=run(prefix+['glxinfo','-B'],route);(out/(name+'-glxinfo.log')).write_text(info.stdout+info.stderr)
            gears=subprocess.Popen(prefix+['glxgears','-geometry','640x480+0+0'],env=route,stdout=(out/(name+'-gears.log')).open('w'),stderr=subprocess.STDOUT)
            try:
                time.sleep(3)
                image=out/(name+'.png');capture=run(['import','-display',env['DISPLAY'],'-window','root',str(image)],route)
                stats=run(['identify','-format','%[fx:maxima]',str(image)],route) if image.exists() else None
                results.append({'mode':name,'display':env['DISPLAY'],'framebuffer':'1280x720x24','audio_backend':'ALSOFT_DRIVERS=null','wayland_unset':True,'options':options,'gears_exit':gears.poll(),'glxinfo_exit':info.returncode,'capture_exit':capture.returncode,'pixel_maximum':stats.stdout if stats else None})
            finally:
                if gears.poll() is None:
                    gears.terminate()
                    try:gears.wait(timeout=5)
                    except subprocess.TimeoutExpired:gears.kill();gears.wait()
    finally:
        if xvfb and xvfb.poll() is None:
            xvfb.terminate()
            try:xvfb.wait(timeout=5)
            except subprocess.TimeoutExpired:xvfb.kill();xvfb.wait()
        if reservation and reservation.exists() and reservation.read_text()==run_id:reservation.unlink()
        (out/'results.json').write_text(json.dumps(results,indent=2)+'\n')
        fcntl.flock(gpu.fileno(),fcntl.LOCK_UN)
print(json.dumps(results),flush=True)
