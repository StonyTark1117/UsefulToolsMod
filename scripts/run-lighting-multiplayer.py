#!/usr/bin/env python3
"""Coordinate two independently isolated Minecraft processes over loopback TCP."""
import argparse,json,subprocess,time,signal
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
p=argparse.ArgumentParser();p.add_argument('profile',type=Path,help='Version-native packaged profile providing the exact loader and artifacts');p.add_argument('--output',type=Path,required=True);a=p.parse_args()
out=a.output.resolve();out.mkdir(parents=True,exist_ok=False);coord=out/'coordination';coord.mkdir()
r=json.loads(a.profile.read_text());base=['python',*r['invocation']]
for option in ['--offline-name','--lighting-multiplayer','--server-address']:
 if option in base:i=base.index(option);del base[i:i+2]
base+=['--lighting-multiplayer',str(coord)]
clients=[];results={}
def launch(role,address=None):
 args=base.copy();args[args.index('--output')+1]=str(out/role);args+=['--offline-name','Lighting'+role.title()]
 if address:args+=['--server-address',address]
 log=(out/(role+'-harness.log')).open('w');process=subprocess.Popen(args,cwd=ROOT,stdout=log,stderr=subprocess.STDOUT);clients.append((role,process,log));return process
try:
 host=launch('host');deadline=time.monotonic()+360
 while not (coord/'port').exists():
  if host.poll() is not None:raise RuntimeError('Host exited before loopback endpoint readiness')
  if time.monotonic()>deadline:raise RuntimeError('Host endpoint timeout')
  time.sleep(.5)
 guest=launch('guest','127.0.0.1:'+str(int((coord/'port').read_text())))
 deadline=time.monotonic()+420;last=-1;last_update=0
 while any(process.poll() is None for _,process,_ in clients):
  if any(process.poll() not in (None,0) for _,process,_ in clients):raise RuntimeError('A native client acceptance harness failed; see its retained report')
  request=coord/'request'
  if request.exists():
   stage=int(request.read_text().splitlines()[0])
   if stage!=last and time.monotonic()-last_update>10:print('Network stage',stage,flush=True);last=stage;last_update=time.monotonic()
  if (coord/'failure').exists():raise RuntimeError((coord/'failure').read_text())
  if time.monotonic()>deadline:raise RuntimeError('Two-client acceptance timeout')
  time.sleep(.5)
 for role,process,_ in clients:
  report=json.loads((out/role/'report.json').read_text());results[role]=dict(exit_code=process.returncode,status=report['status'],clean_client_exit=report.get('clean_client_exit'),network_cases=len(report.get('lighting_network_cases',[])),evidence=str(out/role/'report.json'))
 minimum=21 if any(x.get('uid')=='net.minecraft' and x.get('version')=='1.7.10' for x in r.get('components',{}).get('components',[])) else 34
 assert (coord/'complete').exists() and all(x['exit_code']==0 and x['clean_client_exit'] and x['network_cases']>=minimum for x in results.values()), 'Incomplete network acceptance'
 results['status']='runtime verified';print('Two actual clients: PASS',flush=True)
except BaseException as error:
 results.update(status='incomplete',error=str(error));print('Two actual clients:',str(error),flush=True)
finally:
 for role,process,log in clients:
  if process.poll() is None:
   process.send_signal(signal.SIGINT)
   try:process.wait(30)
   except subprocess.TimeoutExpired:process.terminate();process.wait(10)
  log.close()
 (out/'result.json').write_text(json.dumps(results,indent=2)+'\n')
raise SystemExit(0 if results['status']=='runtime verified' else 1)
