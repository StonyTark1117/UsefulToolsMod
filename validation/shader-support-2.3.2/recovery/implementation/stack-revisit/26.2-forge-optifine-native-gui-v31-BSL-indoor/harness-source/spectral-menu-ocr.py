#!/usr/bin/env python3
"""Read text/coordinates from a fresh framebuffer of an owned QA client."""
import json
import sys
import re
from rapidocr_onnxruntime import RapidOCR

engine = RapidOCR(intra_op_num_threads=1, inter_op_num_threads=1)
# Native game menus are upright. The angle classifier can mistake Minecraft's
# pixel font for upside-down text and rotate individual button labels.
result, _ = engine(sys.argv[1], use_cls=False)
words = [{'text': text, 'confidence': float(confidence),
                   'x': sum(point[0] for point in box) / 4,
                   'y': sum(point[1] for point in box) / 4}
                  for box, text, confidence in (result or [])]
def readable(pattern):
    return any(word['confidence'] >= .65 and re.fullmatch(pattern, word['text'], re.I)
               for word in words)
# Retain the same fresh image and confidence gate. Read isolated menu tiles
# when nearby text prevents detection, then preserve pixel-font edges with a
# nearest-neighbor pass. Never derive input coordinates from expected layout.
fallback_pattern=None
if not readable(r'Single\s*player') and all(readable(pattern) for pattern in (
        r'Multiplayer', r'Options\s*\.{0,3}')):
    fallback_pattern=r'Single\s*player'
elif not readable(r'Save.*(?:Quit|Ouit).*Title') and all(readable(pattern) for pattern in (
        r'Game\s*Menu', r'Back\s*to\s*Game', r'Options\s*\.{0,3}')):
    fallback_pattern=r'Save.*(?:Quit|Ouit).*Title'
elif not readable(r'Shaders\s*\.{0,3}') and all(readable(pattern) for pattern in (
        r'[VW]ideo\s*Settings', r'Quality\s*\.{0,3}', r'Done')):
    fallback_pattern=r'Shaders\s*\.{0,3}'
if fallback_pattern:
    import cv2
    pixels=cv2.imread(sys.argv[1])
    if pixels is None:
        raise ValueError('Fresh OCR image could not be read')
    if fallback_pattern.startswith('Shaders'):
        # The recognized Quality control anchors an OCR row tile. Input still
        # requires the actual Shaders label, never an assumed button position.
        quality=next(word for word in words if word['confidence']>=.65
                     and re.fullmatch(r'Quality\s*\.{0,3}',word['text'],re.I))
        height,width=pixels.shape[:2]
        offset_x,offset_y=width//8,max(0,round(quality['y'])-height//24)
        scale=1
        source=pixels[offset_y:min(height,round(quality['y'])+height//24),offset_x:width//2]
    elif fallback_pattern.startswith('Save'):
        # Read the lower menu tile without adjacent chat text merging into the
        # button. Coordinates still come from the recognized exact label.
        height,width=pixels.shape[:2]
        offset_x,offset_y=width//4,3*height//4
        scale=1
        source=pixels[offset_y:height,offset_x:3*width//4]
    else:
        height,width=pixels.shape[:2]
        offset_x,offset_y=width//4,height//3
        scale=1
        source=pixels[offset_y:2*height//3,offset_x:3*width//4]
    scaled, _ = engine(source,use_cls=False)
    controls=[(box,text,confidence) for box,text,confidence in (scaled or [])
              if float(confidence)>=.65 and re.fullmatch(fallback_pattern,text,re.I)]
    if not controls and fallback_pattern.startswith('Single'):
        offset_x=offset_y=0
        scale=3
        scaled,_=engine(cv2.resize(pixels,None,fx=scale,fy=scale,interpolation=cv2.INTER_NEAREST),use_cls=False)
        controls=[(box,text,confidence) for box,text,confidence in (scaled or [])
                  if float(confidence)>=.65 and re.fullmatch(fallback_pattern,text,re.I)]
    if len(controls)==1:
        box,text,confidence=controls[0]
        words.append({'text':text,'confidence':float(confidence),
                      'x':sum(point[0] for point in box)/(4*scale)+offset_x,
                      'y':sum(point[1] for point in box)/(4*scale)+offset_y,
                      'ocr_scale':scale,'ocr_offset':[offset_x,offset_y]})
# Full title frames can lose the small Quit Game label against the panorama.
# A contrast pass keeps the same full fresh pixels and detected text bounds;
# never derive input coordinates from an assumed button location.
if not readable(r'Quit\s*Game') and all(readable(pattern) for pattern in (
        r'Single\s*player', r'Multiplayer', r'Options\s*\.{0,3}')):
    import cv2
    pixels=cv2.imread(sys.argv[1])
    if pixels is None:raise ValueError('Fresh title OCR image could not be read')
    gray=cv2.cvtColor(pixels,cv2.COLOR_BGR2GRAY)
    _,contrast=cv2.threshold(gray,160,255,cv2.THRESH_BINARY)
    contrast=cv2.cvtColor(contrast,cv2.COLOR_GRAY2BGR)
    scale=3
    source=cv2.resize(contrast,None,fx=scale,fy=scale,interpolation=cv2.INTER_NEAREST)
    detected,_=engine(source,use_cls=False)
    controls=[(box,text,confidence) for box,text,confidence in (detected or [])
              if float(confidence)>=.65 and re.fullmatch(r'Quit\s*Game',text,re.I)]
    if len(controls)==1:
        box,text,confidence=controls[0]
        words.append({'text':text,'confidence':float(confidence),
                      'x':sum(point[0] for point in box)/(4*scale),
                      'y':sum(point[1] for point in box)/(4*scale),
                      'ocr_scale':scale,'ocr_threshold':160})
# Shader list text uses the same pixel font over a noisy dirt background.
# Retain exact ZIP labels read from a contrast pass, with actual detected bounds.
if readable(r'Shaders\s*Folder') and readable(r'OFF') and not readable(r'.*\.zip'):
    import cv2
    pixels=cv2.imread(sys.argv[1])
    if pixels is None:raise ValueError('Fresh shader-list OCR image could not be read')
    gray=cv2.cvtColor(pixels,cv2.COLOR_BGR2GRAY)
    _,contrast=cv2.threshold(gray,160,255,cv2.THRESH_BINARY)
    contrast=cv2.cvtColor(contrast,cv2.COLOR_GRAY2BGR)
    scale=3
    detected,_=engine(cv2.resize(contrast,None,fx=scale,fy=scale,
                                interpolation=cv2.INTER_NEAREST),use_cls=False)
    for box,text,confidence in detected or []:
        if float(confidence)>=.65 and re.fullmatch(r'[A-Za-z0-9_. -]+\.zip',text,re.I):
            words.append({'text':text,'confidence':float(confidence),
                          'x':sum(point[0] for point in box)/(4*scale),
                          'y':sum(point[1] for point in box)/(4*scale),
                          'ocr_scale':scale,'ocr_threshold':160})
print(json.dumps(words))
