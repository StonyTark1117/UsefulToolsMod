#!/usr/bin/env python3
"""Generate numeric LabPBR 1.3 material data without modifying the color textures."""
from pathlib import Path
from PIL import Image
import json,hashlib
ROOT=Path(__file__).resolve().parents[1]
reference=ROOT/'1.21.1/neoforge/src/main/resources/assets/usefultoolsmod/textures'
results=[]
roots=[ROOT/'1.7.10']+[ROOT/v/l for v in ['1.20.1','1.20.2','1.21.1','26.1.2','26.2'] for l in ['forge','fabric','neoforge']]
for root in roots:
 folder=root/'src/main/resources/assets/usefultoolsmod/textures'
 for path in sorted(folder.rglob('hglow*.png')):
  if path.stem.endswith(('_e','_s','_n')):continue
  color=Image.open(path).convert('RGBA');original=hashlib.sha256(path.read_bytes()).hexdigest()
  maskPath=path.with_name(path.stem+'_e.png')
  if not maskPath.exists():
   # Classic item diffuse assets share the canonical item emissive masks.
   maskPath=(reference/'models/armor'/('hglow_layer_2_e.png' if 'humanoid_leggings' in str(path) else 'hglow_layer_1_e.png')) if 'equipment' in str(path) else reference/'item'/(path.stem+'_e.png')
  mask=Image.open(maskPath).convert('RGBA') if maskPath.exists() else None
  if mask is not None and mask.size!=color.size:raise ValueError(f'Mask size mismatch: {path}')
  active_luma=[]
  for y in range(color.height):
   for x in range(color.width):
    r,g,b,a=color.getpixel((x,y))
    if a>0 and (mask is None or mask.getpixel((x,y))[3]>0):active_luma.append((r*54+g*183+b*19)/256)
  low=min(active_luma) if active_luma else 0;high=max(active_luma) if active_luma else 1
  spec=Image.new('RGBA',color.size)
  for y in range(color.height):
   for x in range(color.width):
    r,g,b,a=color.getpixel((x,y));active=a>0 and (mask.getpixel((x,y))[3]>0 if mask is not None else True)
    luminance=(r*54+g*183+b*19)/256
    # Stretch the original pixel contrast instead of making every lit pixel
    # nearly white. Peak emission remains strong; darker texels retain detail.
    contrast=(luminance-low)/(high-low) if high>low else .5
    emission=round(32+160*contrast*contrast) if active else 0
    spec.putpixel((x,y),(48,10,0,emission))
  dest=path.with_name(path.stem+'_s.png');spec.save(dest)
  assert hashlib.sha256(path.read_bytes()).hexdigest()==original
  values=[p[3] for p in spec.get_flattened_data()]
  results.append({'diffuse':str(path.relative_to(ROOT)),'diffuse_sha256':original,'specular':str(dest.relative_to(ROOT)),'specular_sha256':hashlib.sha256(dest.read_bytes()).hexdigest(),'alpha_range':[min(values),max(values)],'mask':str(maskPath.relative_to(ROOT)) if mask is not None else 'opaque block surface'})
report=ROOT/'validation/dynamic-lighting-2.3.2/labpbr-assets.json';report.write_text(json.dumps({'format':'LabPBR 1.3','standard':'https://shaderlabs.org/wiki/LabPBR_Material_Standard','assets':results,'shader_certification_record':'validation/dynamic-lighting-2.3.2/shader-certification.json','shader_certification':'See the separate per-profile runtime record; generation alone does not certify a shader.'},indent=2)+'\n')
print(f'Generated {len(results)} LabPBR material maps; color textures unchanged.')
