#!/usr/bin/env python3
"""Authenticate shader-only A/B captures and prepare visual review sheets."""
import argparse,hashlib,io,json,zipfile
from pathlib import Path
from PIL import Image,ImageDraw,ImageStat
from lighting_shader_diagnostics import compiler_failures
from lighting_pins import pinned_loader
ROOT=Path(__file__).resolve().parents[1];OUT=ROOT/'validation/dynamic-lighting-2.3.2'
p=argparse.ArgumentParser(description=__doc__);p.add_argument('native',type=Path);p.add_argument('control',type=Path);p.add_argument('--input-recovery',type=Path,help='Recovery manifest of exact original input hashes; never changes the native reports');p.add_argument('--record-review',help='Observed findings, only after actually inspecting both sheets and the close-up comparison');a=p.parse_args()
recovered_inputs=json.loads(a.input_recovery.read_text())['inputs'] if a.input_recovery else {}
def sha(path):return hashlib.sha256(path.read_bytes()).hexdigest()
def mean(path,roi):
 image=Image.open(path).convert('RGB');assert image.size==(854,480)
 return sum(v*w for v,w in zip(ImageStat.Stat(image.crop(roi)).mean,[.2126,.7152,.0722]))/255
folders=[a.native.resolve(),a.control.resolve()];reports=[json.loads((folder/'report.json').read_text()) for folder in folders]
for folder,report in zip(folders,reports):
 assert report['status']=='captures_complete' and report['clean_client_exit'],'Incomplete native client run'
 assert not compiler_failures('\n'.join(report.get('shader_compile_errors',[]))),'Shader compiler failure'
 control=report['lighting_material_control'];assert 'USEFUL_TOOLS_LIGHTING_MATERIAL_CONTROL_PASS' in control.get('acknowledgement','') and 'transformed=2' in control['acknowledgement']
 assert len(report.get('live_shader_states',[]))>=2,'Shader toggling not acknowledged'
 assert all(c['accepted'] for c in report['commands']),'Scene command rejected'
 assert any('DaylightCycle false' in c['command'] or 'advance_time false' in c['command'] for c in report['commands']),'Comparison clock not frozen'
 cases=report['lighting_equipment_shader_captures'];assert len(cases)==18
 logs='\n'.join(path.read_text(errors='replace') for path in [folder/'client.log',folder/'lighting-native-acknowledgements.log'] if path.exists())
 for case in cases:
  assert case['native_acknowledgement'] in logs
  assert sha(folder/case['capture'])==report['capture_sha256'][case['capture']]
assert reports[0]['shader_pack']==reports[1]['shader_pack'] and reports[0]['shader_settings']==reports[1]['shader_settings']
assert reports[0]['loader_uid']==reports[1]['loader_uid'] and reports[0]['loader_version']==reports[1]['loader_version']
assert reports[0]['lighting_material_control']['agent_sha256']==reports[1]['lighting_material_control']['agent_sha256']
assert [(c['slot'],c['source']) for c in reports[0]['lighting_equipment_shader_captures']]==[(c['slot'],c['source']) for c in reports[1]['lighting_equipment_shader_captures']]
def base(report):
 artifact=next(Path(v) for index,v in enumerate(report['invocation']) if index and report['invocation'][index-1]=='--artifact')
 return artifact if artifact.is_absolute() else ROOT/artifact
def preserved_base(report):
 original=base(report);expected=report['artifacts'][str(original)]
 if original.is_file():assert sha(original)==expected;return original
 saved=report.get('preserved_inputs',{}).get(str(original),{}) or recovered_inputs.get(expected,{})
 assert saved.get('sha256')==expected,'Missing original input and hash-linked preserved copy'
 path=Path(saved['path']);assert sha(path)==expected;return path
original,zero=[preserved_base(report) for report in reports]
assert sorted(v for k,v in reports[0]['artifacts'].items() if k!=str(base(reports[0])))==sorted(v for k,v in reports[1]['artifacts'].items() if k!=str(base(reports[1]))),'Optional backend or QA code differs'
with zipfile.ZipFile(original) as native,zipfile.ZipFile(zero) as control:
 assert set(native.namelist())==set(control.namelist())
 changed={name for name in native.namelist() if native.read(name)!=control.read(name)}
 expected={name for name in native.namelist() if name.startswith('assets/usefultoolsmod/textures/') and name.endswith('_s.png') and 'hglow' in name};assert changed==expected and len(changed)==13
 for name in changed:
  first=Image.open(io.BytesIO(native.read(name))).convert('RGBA');second=Image.open(io.BytesIO(control.read(name))).convert('RGBA')
  assert first.size==second.size and first.convert('RGB').tobytes()==second.convert('RGB').tobytes() and second.getchannel('A').getextrema()==(0,0)
metrics=[];floor=(250,355,600,420);face=(374,229,480,316)
for distance in [3,10,15,20,29]:
 row={'distance':distance,'roi':list(floor)}
 for name,folder,source in [('ordinary',folders[0],'ordinary'),('native_hardened',folders[0],'hardened'),('zero_alpha_hardened',folders[1],'hardened')]:row[name]=mean(folder/f'lighting-{source}-distance-{distance}.png',floor)
 row['native_to_ordinary_ratio']=row['native_hardened']/row['ordinary'];row['native_to_zero_alpha_floor_ratio']=row['native_hardened']/row['zero_alpha_hardened'];metrics.append(row)
block={name:mean(folder/f'lighting-{source}-distance-3.png',face) for name,folder,source in [('ordinary',folders[0],'ordinary'),('native_hardened',folders[0],'hardened'),('zero_alpha_hardened',folders[1],'hardened')]};block['roi']=list(face)
block['native_to_ordinary_ratio']=block['native_hardened']/block['ordinary'];block['native_to_zero_alpha_ratio']=block['native_hardened']/block['zero_alpha_hardened']
equipment=[]
for case in reports[0]['lighting_equipment_shader_captures']:
 if 'hglow' not in case['source']:continue
 roi=(365,265,428,346) if case['slot']=='held' else (320,100,535,465)
 values=[mean(folder/case['capture'],roi) for folder in folders]
 equipment.append(dict(slot=case['slot'],source=case['source'],roi=list(roi),native=values[0],zero_alpha=values[1],ratio=values[0]/values[1]))
assert len(equipment)==15
sheets=[]
for folder,report in zip(folders,reports):
 sheet=Image.new('RGB',(1200,1000),'#202020');draw=ImageDraw.Draw(sheet)
 for index,case in enumerate(report['lighting_equipment_shader_captures']):
  x=(index%4)*300;y=(index//4)*200;draw.text((x+4,y+3),case['slot']+': '+case['source'].split(':')[-1],fill='white');sheet.paste(Image.open(folder/case['capture']).convert('RGB').resize((300,169)),(x,y+23))
 for index,source in enumerate(['ordinary','hardened'],18):
  x=(index%4)*300;y=(index//4)*200;draw.text((x+4,y+3),source+' placed, distance 3',fill='white');sheet.paste(Image.open(folder/f'lighting-{source}-distance-3.png').convert('RGB').resize((300,169)),(x,y+23))
 path=folder/'material-control-contact-sheet.png';sheet.save(path);sheets.append({'path':str(path.relative_to(ROOT)),'sha256':sha(path)})
closeup=Image.new('RGB',(1281,266),'#202020');draw=ImageDraw.Draw(closeup)
for index,(folder,source,label) in enumerate([(folders[0],'ordinary','Ordinary Glowstone'),(folders[0],'hardened','Hardened Glowstone: native emission'),(folders[1],'hardened','Hardened Glowstone: emission alpha zero')]):
 draw.text((index*427+5,5),label,fill='white');closeup.paste(Image.open(folder/f'lighting-{source}-distance-3.png').convert('RGB').resize((427,240)),(index*427,26))
path=folders[0]/'material-closeup-comparison.png';closeup.save(path);sheets.append({'path':str(path.relative_to(ROOT)),'sha256':sha(path)})
passed=block['native_hardened']>block['ordinary'] and block['native_hardened']>block['zero_alpha_hardened'] and all(row['native']>row['zero_alpha'] for row in equipment)
mc=next(c['version'] for c in reports[0]['components']['components'] if c['uid']=='net.minecraft');loader={'net.fabricmc.fabric-loader':'fabric','net.minecraftforge':'forge','net.neoforged':'neoforge'}[reports[0]['loader_uid']];shader='bsl' if folders[0].name.startswith('bsl') else 'complementary'
assert reports[0]['loader_version']==pinned_loader(mc+'-'+loader),'Alternate loader runtime cannot certify the maintained pin'
# The specified 30-block radius is a companion renderer requirement. Keep every
# distance assertion in a separately authenticated, matching companion-on run.
# Shader-only controls test the emission maps with that renderer boost bypassed.
def backend_hashes(report):
 return sorted(value for path,value in report['artifacts'].items()
               if path!=str(base(report)) and not Path(path).name.lower().startswith('lambdynamiclights') and not Path(path).name.endswith('-acceptance-only.jar'))
matching=[]
for row in json.loads((OUT/'shader-certification.json').read_text())['coverage']:
 if row['target']!=mc+'-'+loader or not row['profile'].startswith(shader) or not row.get('equipment_review'):continue
 folder=OUT/'packaged'/row['profile'];candidate=json.loads((folder/'report.json').read_text())
 if candidate.get('lighting_material_control') or candidate['status']!='captures_complete' or not candidate.get('clean_client_exit'):continue
 if candidate['loader_uid']!=reports[0]['loader_uid'] or candidate['loader_version']!=pinned_loader(mc+'-'+loader):continue
 if candidate['shader_pack']!=reports[0]['shader_pack'] or candidate['shader_settings']!=reports[0]['shader_settings']:continue
 if candidate['artifacts'][str(base(candidate))]!=sha(original) or backend_hashes(candidate)!=backend_hashes(reports[0]):continue
 # Acceptance fixture recompilation must not substitute changed production code.
 class_rows=json.loads((OUT/'reviewed-runtime-class-equivalence.json').read_text())
 proofs=[next((proof for proof in class_rows if proof['profile']==profile),None) for profile in [folder.name,folders[0].name]]
 if not all(proofs):continue
 assert all(not proof['different_production_classes'] for proof in proofs)
 assert proofs[0]['current_release_sha256']==proofs[1]['current_release_sha256']
 assert proofs[0]['report_sha256']==sha(folder/'report.json') and proofs[1]['report_sha256']==sha(folders[0]/'report.json')
 review=ROOT/row['equipment_review'];review_data=json.loads(review.read_text())
 assert review_data['status']=='reviewed' and any(path.is_file() and review_data['contact_sheet_sha256']==sha(path) for path in [folder/'equipment-contact-sheet.png',folder/'equipment-review-contact-sheet.png'])
 assert len(candidate['lighting_equipment_shader_captures'])==18 and len(candidate['live_shader_states'])>=2
 comparison=[]
 for distance in [10,15,20,29]:
  values={source:mean(folder/f'lighting-{source}-distance-{distance}.png',floor) for source in ['ordinary','hardened']}
  for source in values:
   capture=f'lighting-{source}-distance-{distance}.png'
   assert sha(folder/capture)==candidate['capture_sha256'][capture]
  comparison.append(dict(distance=distance,**values,brighter=values['hardened']>values['ordinary']))
 assert all(value['brighter'] for value in comparison),'Companion-on required distance failed'
 matching.append(dict(profile=folder.name,report_sha256=sha(folder/'report.json'),equipment_review_sha256=sha(review),distance_metrics=comparison))
assert matching,'A matching reviewed companion-on run at all four required distances is mandatory'
linked=max(matching,key=lambda row:(OUT/'packaged'/row['profile']/'report.json').stat().st_mtime)
result={'target':mc+'-'+loader,'shader':shader,'native_profile':folders[0].name,'zero_alpha_profile':folders[1].name,'status':'material metrics passed; visual review pending' if passed else 'material metric failed; investigate before certification','report_sha256':[sha(folder/'report.json') for folder in folders],'native_artifact_sha256':sha(original),'zero_alpha_artifact_sha256':sha(zero),'only_13_emission_alphas_changed':True,'shader_pack':reports[0]['shader_pack'],'settings':reports[0]['shader_settings'],'method':'Mean Rec.709-weighted RGB code values. Companion packed/Sodium light boosts bypassed, source policy retained, Lamb absent, native world light retained, OptiFine dynamic lighting off where applicable. Matched clock/exposure/bloom. Lower-floor distance comparison, close-up face and fixed camera foreground regions measure visible brightness, not physical radiance. Distant lower-floor alpha-control differences are retained without treating all floor pixels as material radiance.','distance_metrics':metrics,'required_companion_distance_evidence':linked,'shader_only_distance_scope':'Diagnostic floor measurements with the specified companion radius intentionally disabled; all four required range assertions are retained in the authenticated matching companion-on profile.','closeup_face_metrics':block,'equipment_metrics':equipment,'visual_review_sheets':sheets,'all_required_metrics_passed':passed,'capture_sha256':[{capture:sha(folder/capture) for capture in report['captures'] if capture.startswith('lighting-')} for folder,report in zip(folders,reports)]}
previous_path=folders[0]/'material-emission-comparison.json'
if a.input_recovery:
 result['input_recovery']={'manifest':str(a.input_recovery.resolve()),'sha256':sha(a.input_recovery),'meaning':'Recovered base and control bytes match the original native report hashes exactly; original reports and captures were not changed or rerun.'}
if not a.record_review and previous_path.is_file():
 previous=json.loads(previous_path.read_text())
 if passed and previous.get('status')=='isolated material emission and held/worn visuals verified' and previous.get('report_sha256')==result['report_sha256'] and previous.get('capture_sha256')==result['capture_sha256'] and previous.get('visual_review_sheets')==result['visual_review_sheets']:
  result.update(status=previous['status'],visual_review=previous['visual_review'])
if a.record_review:
 assert passed,'A failed material comparison cannot be certified'
 result.update(status='isolated material emission and held/worn visuals verified',visual_review=a.record_review)
 cert=OUT/'material-emission-certification.json';entries=json.loads(cert.read_text()) if cert.exists() else {'coverage':[]};entries['coverage']=[row for row in entries['coverage'] if (row['target'],row['shader'])!=(result['target'],shader)];entries['coverage'].append({'target':result['target'],'shader':shader,'native_profile':folders[0].name,'zero_alpha_profile':folders[1].name,'status':result['status'],'evidence':str((folders[0]/'material-emission-comparison.json').relative_to(ROOT))});cert.write_text(json.dumps(entries,indent=2)+'\n')
(folders[0]/'material-emission-comparison.json').write_text(json.dumps(result,indent=2)+'\n');print(result['target'],shader,result['status']);print('\n'.join(row['path'] for row in sheets))
