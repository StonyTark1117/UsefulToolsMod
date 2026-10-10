#!/usr/bin/env python3
"""Inventory packed render assets and target-native renderer sources for recovery."""
import argparse
import hashlib
from io import BytesIO
import json
from pathlib import Path
import zipfile
from PIL import Image

root=Path(__file__).resolve().parents[1]
p=argparse.ArgumentParser(description=__doc__)
p.add_argument('--output',type=Path,required=True)
a=p.parse_args()
rows=[]
for version in ('1.7.10','1.20.1','1.20.2','1.21.1','26.1.2','26.2'):
 for loader in (('forge',) if version=='1.7.10' else ('forge','fabric','neoforge')):
  source=root/version if version=='1.7.10' else root/version/loader
  artifact=next(f for f in (source/'build/libs').glob('usefultoolsmod-*.jar') if not any(n in f.name for n in ('sources','dev')))
  assets=[]
  with zipfile.ZipFile(artifact) as jar:
   for name in sorted(jar.namelist()):
    if not name.startswith('assets/usefultoolsmod/') or not name.endswith('.png'):continue
    data=jar.read(name);image=Image.open(BytesIO(data)).convert('RGBA');alpha=image.getchannel('A').histogram()
    assets.append({'resource':name,'size':list(image.size),'sha256':hashlib.sha256(data).hexdigest(),
                   'transparent_pixels':alpha[0],'opaque_pixels':alpha[255],'partial_alpha_pixels':sum(alpha[1:255]),
                   'category':'armor' if '/armor/' in name else 'entity' if '/entity/' in name else 'block' if '/block' in name else 'item' if '/item' in name else 'gui' if '/gui/' in name else 'other'})
  render_sources=[]
  for f in source.rglob('*.java'):
   if 'build' in f.relative_to(source).parts:continue
   text=f.read_text()
   if any(token in text for token in ('RenderLayer','RenderType','getRenderBlockPass','getArmorTexture','ParticleTypes','EnumParticleTypes','spawnParticle','getRenderType','RenderBlocks','.luminance','.lightLevel','.setLightLevel','isOpaqueCube','noOcclusion','nonOpaque','getLightValue','setBlockBounds')):
    render_sources.append({'path':str(f.relative_to(root)),'sha256':hashlib.sha256(f.read_bytes()).hexdigest(),
                           'render_lines':[line.strip() for line in text.splitlines() if any(token in line for token in ('RenderLayer','RenderType','getRenderBlockPass','getArmorTexture','ParticleTypes','EnumParticleTypes','spawnParticle','getRenderType','RenderBlocks','.luminance','.lightLevel','.setLightLevel','isOpaqueCube','noOcclusion','nonOpaque','getLightValue','setBlockBounds'))]})
  rows.append({'target':version+'-'+loader,'artifact':str(artifact.relative_to(root)),'artifact_sha256':hashlib.sha256(artifact.read_bytes()).hexdigest(),
               'packed_textures':assets,'render_sources':render_sources,'runtime_status':'requires scenario captures and visual review; inventory alone is not a pass'})
a.output.parent.mkdir(parents=True,exist_ok=True)
a.output.write_text(json.dumps({'targets':rows},indent=2)+'\n')
print(json.dumps({'targets':len(rows),'textures':sum(len(r['packed_textures']) for r in rows),'render_sources':sum(len(r['render_sources']) for r in rows)}))
