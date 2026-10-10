#!/usr/bin/env python3
"""Record reproducible screen-space comparison metrics for completed profiles."""
import argparse, hashlib, json
from pathlib import Path
from PIL import Image, ImageStat
ROOT=Path(__file__).resolve().parents[1]
p=argparse.ArgumentParser();p.add_argument('profile',type=Path);a=p.parse_args()
f=a.profile.resolve();r=json.loads((f/'report.json').read_text())
assert r['status']=='captures_complete' and r.get('clean_client_exit'), 'Incomplete profile cannot certify captures'
roi=(250,355,600,420)
rows=[]
for distance in [3,10,15,20,29]:
 row=dict(distance=distance,captures={})
 for source in ['ordinary','hardened']:
  path=f/f'lighting-{source}-distance-{distance}.png'
  im=Image.open(path).convert('RGB');assert im.size==(854,480), 'ROI requires this exact camera framebuffer'
  rgb=ImageStat.Stat(im.crop(roi)).mean
  row[source]=sum(v*w for v,w in zip(rgb,[.2126,.7152,.0722]))/255
  row['captures'][source]=dict(path=str(path.relative_to(ROOT)),sha256=hashlib.sha256(path.read_bytes()).hexdigest())
 row['hardened_to_ordinary_ratio']=row['hardened']/row['ordinary'];rows.append(row)
result=dict(target=next(x['version'] for x in r['components']['components'] if x['uid']=='net.minecraft'),shader=r['shader_pack'],settings=r['shader_settings'],artifact_hashes=r['artifacts'],roi=list(roi),method='Mean Rec.709-weighted RGB code values in the matched lower-floor screen region. This compares rendered illumination; it is not physical radiance or a measurement isolating shader emission from companion lighting.',distances=rows,all_requested_distances_brighter=all(x['hardened']>x['ordinary'] for x in rows if x['distance']!=3),shader_toggle_states=r.get('live_shader_states',[]))
(f/'lighting-comparison.json').write_text(json.dumps(result,indent=2)+'\n');print(f.name,result['all_requested_distances_brighter'])
