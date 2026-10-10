#!/usr/bin/env python3
"""Register companion ownership through LambDynamicLights' documented optional API."""
from pathlib import Path
import json
ROOT=Path(__file__).resolve().parents[1];pkg='com.stonytark.usefultools.lighting'
rows=json.loads((ROOT/'lighting/sources.json').read_text())['sources']
for v in ['1.20.1','1.21.1','26.1.2','26.2']:
 for loader in ['fabric','neoforge']:
  if loader=='neoforge' and v=='1.20.1':continue
  root=ROOT/v/loader;yarn=loader=='fabric' and not v.startswith('26.');old=v=='1.20.1';legacy=v=='1.21.1'
  registry='net.minecraft.registry.Registries.ITEM' if yarn else 'net.minecraft.core.registries.BuiltInRegistries.ITEM'
  ident='net.minecraft.util.Identifier.of' if yarn and not old else ('new net.minecraft.util.Identifier' if yarn else ('net.minecraft.resources.Identifier.parse' if v.startswith('26.') else 'net.minecraft.resources.ResourceLocation.parse'))
  declarations=''
  if old:declarations='    @SuppressWarnings("removal")\n    @Override public void onInitializeDynamicLights() {}\n'
  if legacy:declarations='    @SuppressWarnings("removal")\n    @Override public void onInitializeDynamicLights(dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager manager) { register(manager); }\n'
  code=f'''package {pkg};
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import dev.lambdaurora.lambdynlights.api.DynamicLightsContext;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager;
/** The companion owns these enhanced sources; suppress their parallel Lamb item processing. */
public final class LambLightingIntegration implements DynamicLightsInitializer {{
{declarations}    @Override public void onInitializeDynamicLights(DynamicLightsContext context) {{ register(context.itemLightSourceManager()); }}
    private static void register(ItemLightSourceManager manager) {{
        manager.onRegisterEvent().register(registration -> {{
'''
  for row in rows:code+=f'            registration.register({registry}.{'getValue' if v.startswith('26.') else 'get'}({ident}("usefultoolsmod:{row["id"]}")), 0);\n'
  code+='        });\n    }\n}\n'
  p=root/'src/lighting/java'/pkg.replace('.','/')/'LambLightingIntegration.java';p.write_text(code)
  if loader=='fabric':
   p=root/'src/lighting/resources/fabric.mod.json';j=json.loads(p.read_text());j['entrypoints']={'lambdynlights:initializer':[pkg+'.LambLightingIntegration']};p.write_text(json.dumps(j,indent=2)+'\n')
  else:
   p=root/'src/lighting/resources/META-INF/neoforge.mods.toml';s=p.read_text();s+='\n[modproperties."usefultools_lighting"."yumi:entrypoints"]\n"lambdynlights:initializer"="'+pkg+'.LambLightingIntegration"\n';p.write_text(s)
print('Generated optional v4 ownership integration for seven supported Lamb targets.')
# Version 2 has a different item registration API, kept version-native.
root=ROOT/'1.20.2/fabric'
code=f'''package {pkg};
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSource;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSources;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
public final class LambLightingIntegration implements DynamicLightsInitializer {{
    @Override public void onInitializeDynamicLights() {{
'''
for row in rows:
 code+=f'        ItemLightSources.registerItemLightSource(new ItemLightSource.StaticItemLightSource(new Identifier("usefultools_lighting", "{row["id"]}"), Registries.ITEM.get(new Identifier("usefultoolsmod", "{row["id"]}")), 0, false));\n'
code+='    }\n}\n'
(root/'src/lighting/java'/pkg.replace('.','/')/'LambLightingIntegration.java').write_text(code)
p=root/'src/lighting/resources/fabric.mod.json';j=json.loads(p.read_text());j['entrypoints']={'dynamiclights':[pkg+'.LambLightingIntegration']};p.write_text(json.dumps(j,indent=2)+'\n')
