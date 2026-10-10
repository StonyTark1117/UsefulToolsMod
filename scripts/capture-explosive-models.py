#!/usr/bin/env python3
"""Capture real held and thrown explosive geometry in an isolated 1.21.1 client."""

from __future__ import annotations

import argparse
import importlib.util
from pathlib import Path
import subprocess
import sys
import time

ROOT = Path(__file__).resolve().parent.parent
spec = importlib.util.spec_from_file_location('explosive_smoke', ROOT / 'scripts/smoke-client.py')
assert spec and spec.loader
smoke = importlib.util.module_from_spec(spec)
sys.modules[spec.name] = smoke
spec.loader.exec_module(smoke)


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--target', default='1.21.1-neoforge', choices=('1.21.1-neoforge', '1.21.1-fabric', '26.2-fabric', '1.7.10-forge'))
    parser.add_argument('--offline', action='store_true')
    args = parser.parse_args()
    out = ROOT / 'validation/explosive-models-2.3.2' / args.target
    out.mkdir(parents=True, exist_ok=True)
    classic = args.target == '1.7.10-forge'
    display_base = {'1.7.10-forge': 180, '1.21.1-neoforge': 190, '1.21.1-fabric': 200, '26.2-fabric': 210}[args.target]
    smoke.free_display = lambda: next(n for n in range(display_base, display_base + 10) if not Path(f'/tmp/.X11-unix/X{n}').exists())
    run_args = argparse.Namespace(
        output_dir=out, client_mod=[], shader_pack=None, offline=args.offline,
        display=None, disable_shaders=False,
        skip_asset_download=args.target == '1.21.1-neoforge',
        asset_cache_source=Path('/home/braydon/.gradle/caches/neoformruntime/assets/objects') if args.target == '1.21.1-neoforge' else None,
        gradle_property=[], timeout=420, verbose=False, visual_showcase=True,
        inventory_probe=False, inventory_search=None, spectral_render_check=False,
    )
    original_xdo = smoke.xdo

    def xdo(display: str, *parts: str, **kwargs):
        # This scene uses the original 854x480 UI coordinates. Shader QA in the
        # shared smoke harness uses a larger framebuffer; keep our scene local.
        if parts and parts[0] == 'windowsize':
            parts = (*parts[:-2], '854', '480')
        return original_xdo(display, *parts, **kwargs)

    smoke.xdo = xdo
    original_click = smoke.click
    title_clicks = 0

    def click(display: str, window: str, x: int, y: int):
        nonlocal title_clicks
        if args.target == '26.2-fabric' and (x, y) == (427, 237):
            title_clicks += 1
            if title_clicks > 1:
                return
        return original_click(display, window, x, y)

    smoke.click = click

    def original(display: str, path: Path, window: str, game_dir: Path) -> None:
        subprocess.run(['magick', 'import', '-window', window, str(path)],
                       env={**smoke.os.environ, 'DISPLAY': display}, check=True)

    captured = []
    if classic:
        # The legacy window can keep its XID after closing the config screen.
        # Re-resolve it instead of waiting for an unrelated splash replacement.
        smoke.wait_for_window_replacement = lambda display, previous, timeout: smoke.window_id(display, timeout)
        original_quick_click = smoke.quick_click

        def quick_click(display: str, x: int, y: int) -> None:
            if (x, y) == (206, 443):
                # Classic's world screen cycles Survival -> Hardcore -> Creative.
                # Creative enables commands in this disposable world.
                original_quick_click(display, 427, 250)
                time.sleep(0.3)
                original_quick_click(display, 427, 250)
                time.sleep(0.3)
            original_quick_click(display, x, y)

        smoke.quick_click = quick_click

    def scene(display: str) -> None:
        window = smoke.window_id(display, 3)

        def command(value: str, delay: float = 0.35) -> None:
            if classic and value.startswith('tp '):
                # 1.7.10 only accepts coordinates; yaw/pitch arrived later.
                value = ' '.join(value.split()[:5])
            if classic and value == 'time set noon':
                value = 'time set 6000'
            smoke.xdo(display, 'key', '--window', window, 't')
            time.sleep(0.2)
            smoke.xdo(display, 'type', '--window', window, '--delay', '3', '/' + value)
            smoke.xdo(display, 'key', '--window', window, 'Return')
            time.sleep(delay)

        def capture(name: str) -> None:
            # Keep the HUD enabled: F1 also hides first-person held items.
            hide_hud = 'firstperson' not in name
            if hide_hud:
                smoke.xdo(display, 'key', '--window', window, 'F1')
            elif not classic:
                smoke.xdo(display, 'key', '--window', window, 'F3+d')
            time.sleep(0.35)
            path = out / (name + '.png')
            subprocess.run(['magick', 'import', '-window', window, str(path)],
                           env={**smoke.os.environ, 'DISPLAY': display}, check=True)
            if hide_hud:
                smoke.xdo(display, 'key', '--window', window, 'F1')
            captured.append(path)
            print('Explosive capture: ' + str(path), flush=True)

        command('gamemode creative')
        if classic:
            # Classic /give drops an item for pickup rather than inserting it.
            # Receive the items on the ground before moving the scene into air.
            command('clear @p')
            for item in ('dynamite', 'grenade'):
                command('give @p usefultoolsmod:' + item, 1.2)
            for _ in range(2):
                smoke.xdo(display, 'keydown', '--window', window, 'space')
                time.sleep(0.12)
                smoke.xdo(display, 'keyup', '--window', window, 'space')
                time.sleep(0.12)
        command('time set noon')
        command('weather clear')
        command('gamerule doMobSpawning false')
        command('tp @p 0 201 5 180 10', 2)
        if not classic:
            command('fill -12 199 -12 12 199 12 minecraft:smooth_stone')
            command('fill -12 200 -12 12 211 12 minecraft:air')
            command('tp @p 0 200 5 180 10', 2)
        if not classic:
            command('clear @p')
        if classic:
            smoke.xdo(display, 'key', '--window', window, '1')
            time.sleep(0.6)
            capture('dynamite-firstperson-right')
            smoke.xdo(display, 'key', '--window', window, '2')
            time.sleep(0.6)
            capture('grenade-firstperson-right')
            smoke.xdo(display, 'key', '--window', window, 'F5', 'F5')
            time.sleep(0.6)
            capture('grenade-thirdperson')
            smoke.xdo(display, 'key', '--window', window, 'F5')
            # Capture an actual thrown grenade before impact.
            command('tp @p 0 210 5 180 -15')
            smoke.xdo(display, 'click', '--window', window, '3')
            time.sleep(0.12)
            capture('grenade-thrown')
        else:
            for item in ('dynamite', 'grenade'):
                command('item replace entity @s weapon.mainhand with usefultoolsmod:' + item)
                time.sleep(0.5)
                capture(item + '-firstperson-right')
                command('item replace entity @s weapon.offhand with usefultoolsmod:' + item)
                command('item replace entity @s weapon.mainhand with minecraft:air')
                time.sleep(0.5)
                capture(item + '-firstperson-left')
                command('item replace entity @s weapon.mainhand with usefultoolsmod:' + item)
                command('item replace entity @s weapon.offhand with minecraft:air')
                smoke.xdo(display, 'key', '--window', window, 'F5', 'F5')
                time.sleep(0.5)
                capture(item + '-thirdperson')
                smoke.xdo(display, 'key', '--window', window, 'F5')
            command('tick freeze')
            command('tp @s 0 201 5 180 0')
            # Launch through the item's normal use path, then advance two ticks.
            smoke.xdo(display, 'click', '--window', window, '3')
            time.sleep(0.3)
            command('tick step 2', 0.8)
            capture('grenade-thrown')
            command('data get entity @e[type=usefultoolsmod:grenade,limit=1] Pos')
            command('kill @e[type=usefultoolsmod:grenade]')
            command('tick unfreeze')
            # Larger fixed displays make the six-sided geometry easy to inspect.
            for x, item in ((-1, 'dynamite'), (1, 'grenade')):
                count_key = 'count:1'
                command(f'summon minecraft:item_display {x} 202 0 '
                        '{item:{id:"usefultoolsmod:' + item + '",' + count_key + '},'
                        'transformation:{scale:[3f,3f,3f]}}')
            command('tp @s 0 201 5 180 0')
            command('item replace entity @s weapon.mainhand with minecraft:air')
            capture('geometry-front')
            command('tp @s 4 201 3 130 0')
            capture('geometry-side')
        # The stock harness now finishes its ordinary exit/cleanup path.
        run_args.visual_showcase = False

    def screenshot(display: str, path: Path, window: str, game_dir: Path) -> None:
        original(display, path, window, game_dir)
        if path.name.endswith('-world.png'):
            scene(display)

    smoke.screenshot = screenshot
    success = smoke.run_target(args.target, smoke.TARGETS[args.target], run_args)
    substantial = all(smoke.image_is_substantial(path) for path in captured)
    return 0 if success and captured and substantial else 1


if __name__ == '__main__':
    raise SystemExit(main())
