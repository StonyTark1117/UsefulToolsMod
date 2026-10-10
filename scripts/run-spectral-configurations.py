#!/usr/bin/env python3
"""Run an explicit configuration manifest serially, preserving individual failures."""
import argparse
import hashlib
import json
from pathlib import Path
import subprocess
import signal
from lighting_client_lifecycle import wait_client
from spectral_validation import validate_manifest_agent_logs


def stop_batch(signum, frame):
    raise KeyboardInterrupt()


signal.signal(signal.SIGTERM, stop_batch)

parser=argparse.ArgumentParser(description=__doc__)
parser.add_argument('manifest',type=Path)
parser.add_argument('--results',type=Path,required=True)
parser.add_argument('--resume',action='store_true',help='Retain completed attempts and continue configurations not yet run')
args=parser.parse_args()
results=json.loads(args.results.read_text()) if args.resume and args.results.is_file() else []
completed_names={r['name'] for r in results if r.get('status')!='not_started_client_busy'}
results=[r for r in results if r.get('status')!='not_started_client_busy']
while True:
    # Read the next explicit configuration afresh so corrected, immutable QA
    # fixtures can be selected before an attempt starts. Retain attempted rows.
    manifest=args.manifest.read_bytes()
    configurations=json.loads(manifest)
    names=[r['name'] for r in configurations]
    if len(names)!=len(set(names)):raise SystemExit('Duplicate configuration names')
    configuration=next((r for r in configurations if r['name'] not in completed_names),None)
    if configuration is None:break
    completed_names.add(configuration['name'])
    print('Testing '+configuration['name'],flush=True)
    if 'command' not in configuration:
        results.append(configuration)
        args.results.write_text(json.dumps(results,indent=2)+'\n')
        print(json.dumps(configuration),flush=True)
        continue
    external_manifest=configuration.get('external_execution_manifest')
    if external_manifest:
        external=json.loads(Path(external_manifest).read_text())
        if not any(r.get('command')==configuration['command'] for r in external):
            raise SystemExit('External execution command mismatch: '+configuration['name'])
        command=configuration['command']
        output=Path(command[command.index('--output')+1])
        results.append({'name':configuration['name'],'target':configuration['target'],
                        'status':'external_execution','exit_code':None,
                        'execution_manifest':external_manifest,
                        'report':str(output/'report.json'),
                        'note':'Execution is owned by the named queue; audit the fresh report to determine acceptance.'})
        args.results.write_text(json.dumps(results,indent=2)+'\n')
        print(json.dumps(results[-1]),flush=True)
        continue
    command=configuration['command']
    output=Path(command[command.index('--output')+1])
    try:
        validate_manifest_agent_logs(command,output)
    except ValueError as error:
        results.append({'name':configuration['name'],'target':configuration['target'],
                        'status':'invalid_configuration','exit_code':2,
                        'manifest_sha256':hashlib.sha256(manifest).hexdigest(),
                        'error':str(error),
                        'note':'Rejected before client launch or profile preparation'})
        args.results.write_text(json.dumps(results,indent=2)+'\n')
        print(json.dumps(results[-1]),flush=True)
        raise SystemExit(2)
    client=subprocess.Popen(command,start_new_session=True)
    try:
        exit_code=wait_client(client)
    except KeyboardInterrupt:
        results.append({'name':configuration['name'],'target':configuration['target'],
                        'status':'cancelled','exit_code':130,
                        'report':str(output/'report.json'),
                        'manifest_sha256':hashlib.sha256(manifest).hexdigest(),
                        'error':'Interrupted; private client cleanup finished; remaining rows were not dispatched'})
        args.results.write_text(json.dumps(results,indent=2)+'\n')
        raise SystemExit(130)
    if exit_code==75:
        results.append({'name':configuration['name'],'exit_code':75,'target':configuration['target'],
                        'status':'not_started_client_busy','error':'Client admission occupied; no profile prepared or test launched',
                        'manifest_sha256':hashlib.sha256(manifest).hexdigest()})
        args.results.write_text(json.dumps(results,indent=2)+'\n')
        print(json.dumps(results[-1]),flush=True)
        raise SystemExit(75)
    report=json.loads((output/'report.json').read_text()) if (output/'report.json').exists() else {}
    results.append({'name':configuration['name'],'exit_code':exit_code,
                    'target':configuration['target'],'manifest_sha256':hashlib.sha256(manifest).hexdigest(),
                    'report':str(output/'report.json'),'status':report.get('status'),
                    'error':report.get('error'),'captures':len(report.get('captures',[]))})
    args.results.write_text(json.dumps(results,indent=2)+'\n')
    print(json.dumps(results[-1]),flush=True)

# Retain every attempted row, but do not return success when a child failed.
# Busy admission already exits above without preparing another configuration.
if any(row.get('exit_code') not in (None, 0) for row in results):
    raise SystemExit(1)
