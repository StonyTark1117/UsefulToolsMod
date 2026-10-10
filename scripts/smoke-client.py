#!/usr/bin/env python3
"""Run a Useful Tools client, config-screen, world-join, and shutdown smoke."""

from __future__ import annotations

import argparse
from dataclasses import dataclass
import json
import os
from pathlib import Path
import queue
import re
import shutil
import signal
import subprocess
import sys
import tempfile
import threading
import time
import uuid


ROOT = Path(__file__).resolve().parent.parent
RUN_ID_ENV = "USEFUL_TOOLS_CLIENT_RUN_ID"
ANSI_RE = re.compile(r"\x1b\[[0-?]*[ -/]*[@-~]")
JOIN_RE = re.compile(
    r"\b(?:joined the game|logged in with entity id|client side modded connection established)\b",
    re.I,
)
CRASH_RE = re.compile(
    r"Unknown config option|critical issue trying to build the config GUI|"
    r"Failed to start|Failed to create mod instance|Minecraft has crashed|"
    r"Mixin apply.*failed|InvalidMixinException|Mod loading has failed|Warning while loading mods|"
    r"Failed to resolve uniform|Unknown variable:"
    r"deprecated [`']logoFile",
    re.I,
)
LOG_BYTE_LIMIT = 8 * 1024 * 1024


@dataclass(frozen=True)
class Target:
    directory: str
    task: str
    java_home: str
    loader: str
    runtime_java_home: str | None = None


TARGETS = {
    "1.7.10-forge": Target(
        "1.7.10", "runClient", "/usr/lib/jvm/java-17-openjdk", "classic",
        "/usr/lib/jvm/java-8-openjdk",
    ),
    "1.20.1-forge": Target("1.20.1/forge", "runClient", "/usr/lib/jvm/java-17-openjdk", "forge"),
    "1.20.1-fabric": Target("1.20.1/fabric", "runClient", "/usr/lib/jvm/java-21-openjdk", "fabric"),
    "1.20.1-neoforge": Target("1.20.1/neoforge", "runClient", "/usr/lib/jvm/java-17-openjdk", "neoforge"),
    "1.20.2-forge": Target("1.20.2/forge", "runClient", "/usr/lib/jvm/java-17-openjdk", "forge"),
    "1.20.2-fabric": Target("1.20.2/fabric", "runClient", "/usr/lib/jvm/java-21-openjdk", "fabric"),
    "1.20.2-neoforge": Target("1.20.2/neoforge", "runClient", "/usr/lib/jvm/java-17-openjdk", "neoforge"),
    "1.21.1-forge": Target("1.21.1/forge", "runClient", "/usr/lib/jvm/java-21-openjdk", "forge"),
    "1.21.1-fabric": Target("1.21.1/fabric", "runClient", "/usr/lib/jvm/java-21-openjdk", "fabric"),
    "1.21.1-neoforge": Target("1.21.1/neoforge", "runClient", "/usr/lib/jvm/java-21-openjdk", "neoforge"),
    "26.1.2-forge": Target("26.1.2/forge", "runClient", "/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2", "forge"),
    "26.1.2-fabric": Target("26.1.2/fabric", "runClient", "/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2", "fabric"),
    "26.1.2-neoforge": Target("26.1.2/neoforge", "runClient", "/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2", "neoforge"),
    "26.2-forge": Target("26.2/forge", "runClient", "/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2", "forge"),
    "26.2-fabric": Target("26.2/fabric", "runClient", "/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2", "fabric"),
    "26.2-neoforge": Target("26.2/neoforge", "runClient", "/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2", "neoforge"),
}


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("target", choices=(*TARGETS, "all", "verify-existing"))
    parser.add_argument("--timeout", type=int, default=300)
    parser.add_argument(
        "--offline", action="store_true",
        help="use cached Gradle and Minecraft assets without network checks",
    )
    parser.add_argument(
        "--skip-asset-download", action="store_true",
        help="use asset objects supplied from --asset-cache-source for NeoGradle clients",
    )
    parser.add_argument(
        "--asset-cache-source", type=Path, metavar="OBJECTS_DIR",
        help="restore cached Minecraft asset objects immediately before client launch",
    )
    parser.add_argument("--output-dir", type=Path, default=ROOT / "validation/client-smoke")
    parser.add_argument(
        "--gradle-property", action="append", default=[], metavar="NAME=VALUE",
        help="repeatable non-secret Gradle property used to select a runtime profile",
    )
    parser.add_argument(
        "--inventory-probe", action="store_true",
        help="capture the in-world inventory, including any loaded recipe viewer",
    )
    parser.add_argument(
        "--inventory-search", metavar="TEXT",
        help="type a recipe-viewer search before the inventory probe capture",
    )
    parser.add_argument(
        "--visual-showcase", action="store_true",
        help="stage and capture the 2.3.1 block and WTHIT showcase (1.21.1 NeoForge)",
    )
    parser.add_argument(
        "--client-mod", action="append", type=Path, default=[], metavar="JAR",
        help="copy a client-only mod into the disposable game directory",
    )
    parser.add_argument(
        "--shader-pack", type=Path, metavar="ZIP",
        help="enable this shader pack in the disposable game directory (requires a shader loader)",
    )
    parser.add_argument(
        "--disable-shaders", action="store_true",
        help="install Iris/Sodium but explicitly disable shader packs in iris.properties",
    )
    parser.add_argument(
        "--retain-spectral-entities", action="store_true",
        help="move spectral entities out of view instead of removing them after capture",
    )
    parser.add_argument(
        "--ignore-sodium-lwjgl-check", action="store_true",
        help=("skip Sodium's LWJGL version check in a dev run where Loom substitutes "
              "a snapshot; use only when the target Minecraft metadata pins Sodium's required version"),
    )
    parser.add_argument(
        "--display", metavar="DISPLAY",
        help="use an existing X11 display instead of a software-rendered Xvfb display",
    )
    parser.add_argument(
        "--spectral-render-check", action="store_true",
        help="capture Ghost and Wraith across a fixed lighting scene on a modern client target",
    )
    parser.add_argument("--verbose", action="store_true")
    args = parser.parse_args()
    if args.shader_pack and args.disable_shaders:
        parser.error("--shader-pack and --disable-shaders are mutually exclusive")
    return args


def read_proc(pid: int, name: str) -> bytes:
    try:
        return (Path("/proc") / str(pid) / name).read_bytes()
    except (FileNotFoundError, PermissionError, ProcessLookupError):
        return b""


def owned_processes(run_id: str) -> list[int]:
    marker = f"{RUN_ID_ENV}={run_id}".encode()
    return sorted(
        int(entry.name)
        for entry in Path("/proc").iterdir()
        if entry.name.isdigit()
        and int(entry.name) != os.getpid()
        and marker in read_proc(int(entry.name), "environ").split(b"\0")
    )


def cleanup(run_id: str) -> list[int]:
    for sig in (signal.SIGTERM, signal.SIGKILL):
        for pid in reversed(owned_processes(run_id)):
            try:
                os.kill(pid, sig)
            except ProcessLookupError:
                pass
        deadline = time.monotonic() + 5
        while time.monotonic() < deadline and owned_processes(run_id):
            time.sleep(0.2)
        if not owned_processes(run_id):
            break
    return owned_processes(run_id)


def free_display() -> int:
    for number in range(90, 120):
        if not Path(f"/tmp/.X{number}-lock").exists():
            return number
    raise RuntimeError("no free Xvfb display in :90-:119")


def xdo(display: str, *args: str, check: bool = True) -> subprocess.CompletedProcess[str]:
    return subprocess.run(
        ["xdotool", *args], env={**os.environ, "DISPLAY": display},
        text=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, check=check,
    )


def window_id(display: str, timeout: float) -> str:
    deadline = time.monotonic() + timeout
    while time.monotonic() < deadline:
        result = xdo(display, "search", "--onlyvisible", "--name", "Minecraft", check=False)
        ids = [line for line in result.stdout.splitlines() if line.isdigit()]
        if ids:
            return ids[-1]
        # Minecraft can briefly replace its titled window while changing from
        # the title screen to an integrated world. During that transition the
        # focused top-level window remains the game but its name may not yet
        # match the search expression.
        active = xdo(display, "getactivewindow", check=False).stdout.strip()
        if active.isdigit():
            title = xdo(display, "getwindowname", active, check=False).stdout
            if "minecraft" in title.lower():
                return active
        time.sleep(0.5)
    raise RuntimeError("Minecraft window did not appear or regain its title")


def scaled_window_point(display: str, window: str, x: int, y: int) -> tuple[int, int]:
    """Map the harness's 854x480 GUI coordinates to the current X11 window."""
    result = xdo(display, "getwindowgeometry", "--shell", window)
    values = dict(
        line.split("=", 1)
        for line in result.stdout.splitlines()
        if "=" in line
    )
    width = int(values["WIDTH"])
    offset_x, offset_y = int(values.get("X", 0)), int(values.get("Y", 0))
    # Minecraft's GUI scale follows the framebuffer width. Xwayland may report
    # a much taller framebuffer, but scaling y independently stretches clicks
    # away from the button the screenshot shows.
    scale = width / 854
    return offset_x + round(x * scale), offset_y + round(y * scale)


def click(display: str, window: str, x: int, y: int) -> None:
    # Loader splash windows may be replaced without invalidating the old XID.
    # Resolve the currently visible Minecraft window at the moment of input.
    current_window = window_id(display, 2)
    # Xwayland can place the window on a secondary monitor in the host's wide
    # desktop. scaled_window_point includes that root offset.
    x, y = scaled_window_point(display, current_window, x, y)
    xdo(display, "windowfocus", "--sync", current_window)
    xdo(display, "mousemove", "--sync", str(x), str(y))
    xdo(display, "click", "1")


def focus_and_enter(display: str, window: str, x: int, y: int) -> None:
    """Activate a menu button after mapping it to the current X11 window."""
    click(display, window, x, y)


def quick_click(display: str, x: int, y: int) -> None:
    """Use the LWJGL 2-compatible press/release event pair."""
    current_window = window_id(display, 2)
    x, y = scaled_window_point(display, current_window, x, y)
    xdo(display, "windowfocus", "--sync", current_window)
    time.sleep(0.5)
    xdo(display, "mousemove", str(x), str(y))
    xdo(display, "click", "1")


def screenshot(display: str, path: Path, window: str, game_dir: Path) -> None:
    """Capture Minecraft's framebuffer, including on accelerated Xwayland."""
    current_window = window_id(display, 5)
    screenshots_dir = game_dir / "screenshots"
    before = set(screenshots_dir.glob("*.png")) if screenshots_dir.exists() else set()
    xdo(display, "key", "--window", current_window, "F2")
    deadline = time.monotonic() + 5
    while time.monotonic() < deadline:
        candidates = set(screenshots_dir.glob("*.png")) - before
        if candidates:
            capture = max(candidates, key=lambda item: item.stat().st_mtime_ns)
            if capture.stat().st_size > 5_000:
                shutil.copy2(capture, path)
                return
        time.sleep(0.1)
    raise RuntimeError(f"Minecraft did not save its framebuffer screenshot: {path.name}")


def image_is_substantial(path: Path) -> bool:
    if not path.exists() or path.stat().st_size < 5_000:
        return False
    result = subprocess.run(
        ["identify", "-format", "%k", str(path)], text=True,
        stdout=subprocess.PIPE, stderr=subprocess.PIPE, check=False,
    )
    return result.returncode == 0 and int(result.stdout or "0") > 32


def wait_for_button(display: str, x: int, y: int, timeout: float) -> bool:
    """Wait until a gray Minecraft button occupies the requested local point."""
    deadline = time.monotonic() + timeout
    probe = Path("/dev/shm") / f"usefultools-button-{uuid.uuid4().hex}.png"
    try:
        while time.monotonic() < deadline:
            current_window = window_id(display, 2)
            captured = subprocess.run(
                ["magick", "import", "-window", current_window, str(probe)],
                env={**os.environ, "DISPLAY": display},
                stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL,
            )
            if captured.returncode == 0:
                left, top = max(0, x - 40), max(0, y - 10)
                mean = subprocess.run(
                    [
                        "magick", str(probe), "-crop", f"80x20+{left}+{top}",
                        "-colorspace", "Gray", "-format", "%[fx:mean]", "info:",
                    ],
                    text=True, stdout=subprocess.PIPE, stderr=subprocess.DEVNULL,
                )
                try:
                    if mean.returncode == 0 and float(mean.stdout) >= 0.4:
                        return True
                except ValueError:
                    pass
            time.sleep(0.5)
        return False
    finally:
        probe.unlink(missing_ok=True)


def wait_for_window_replacement(display: str, previous: str, timeout: float) -> str:
    """Return the post-splash GLFW window, waiting for its XID to replace the launcher."""
    deadline = time.monotonic() + timeout
    current = previous
    while time.monotonic() < deadline:
        current = window_id(display, 2)
        if current != previous:
            return current
        time.sleep(0.5)
    return current


def run_target(name: str, target: Target, args: argparse.Namespace) -> bool:
    run_id = uuid.uuid4().hex
    output_dir = args.output_dir.resolve()
    output_dir.mkdir(parents=True, exist_ok=True)
    log_path = output_dir / f"{name}.log"
    config_image = output_dir / f"{name}-config.png"
    title_image = output_dir / f"{name}-title.png"
    creation_image = output_dir / f"{name}-world-creation.png"
    cheats_image = output_dir / f"{name}-world-cheats.png"
    world_image = output_dir / f"{name}-world.png"
    pause_image = output_dir / f"{name}-pause.png"
    inventory_image = output_dir / f"{name}-inventory.png"
    blocks_image = output_dir / f"{name}-blocks.png"
    lantern_south_image = output_dir / f"{name}-ectoplasm-lantern-south.png"
    lantern_north_image = output_dir / f"{name}-ectoplasm-lantern-north.png"
    lantern_east_image = output_dir / f"{name}-ectoplasm-lantern-east.png"
    lantern_west_image = output_dir / f"{name}-ectoplasm-lantern-west.png"
    lantern_top_image = output_dir / f"{name}-ectoplasm-lantern-top.png"
    lantern_bottom_image = output_dir / f"{name}-ectoplasm-lantern-bottom.png"
    mining_unlit_image = output_dir / f"{name}-mining-charge-unlit.png"
    mining_lit_image = output_dir / f"{name}-mining-charge-lit.png"
    mining_glass_image = output_dir / f"{name}-mining-charge-glass.png"
    wraith_image = output_dir / f"{name}-wraith-wthit.png"
    wraith_hunting_ordinary_image = output_dir / f"{name}-wraith-hunting-ordinary-wthit.png"
    wraith_hunting_infused_image = output_dir / f"{name}-wraith-hunting-infused-wthit.png"
    wraith_warded_ordinary_image = output_dir / f"{name}-wraith-warded-ordinary-wthit.png"
    wraith_warded_infused_image = output_dir / f"{name}-wraith-warded-infused-wthit.png"
    ghost_image = output_dir / f"{name}-ghost-wthit.png"
    spectral_images = {
        (entity, lighting): output_dir / f"{name}-{entity}-{lighting}.png"
        for entity in ("ghost", "wraith") for lighting in ("day", "night")
    }
    spectral_environment_images = {
        (entity, environment): output_dir / f"{name}-{entity}-{environment}.png"
        for entity in ("ghost", "wraith")
        for environment in ("rain", "dark-background", "bright-background", "indoor")
    }
    display = args.display or f":{free_display()}"
    game_temp = tempfile.TemporaryDirectory(prefix="usefultools-client-", dir="/dev/shm")
    game_dir = Path(game_temp.name)
    if args.client_mod:
        mods_dir = game_dir / "mods"
        mods_dir.mkdir()
        for mod in args.client_mod:
            shutil.copy2(mod, mods_dir / mod.name)
    if args.shader_pack or args.disable_shaders:
        packs_dir = game_dir / "shaderpacks"
        if args.shader_pack:
            packs_dir.mkdir()
            shutil.copy2(args.shader_pack, packs_dir / args.shader_pack.name)
        config_dir = game_dir / "config"
        config_dir.mkdir(exist_ok=True)
        (config_dir / "iris.properties").write_text(
            f"enableShaders={'false' if args.disable_shaders else 'true'}\n"
            f"shaderPack={args.shader_pack.name if args.shader_pack else ''}\n",
            encoding="utf-8",
        )
    if args.shader_pack and args.disable_shaders:
        raise SystemExit("--shader-pack and --disable-shaders are mutually exclusive")
    if target.loader != "classic":
        # Accessibility onboarding is Minecraft UI, not part of the mod under
        # test, and its first-run button can be temporarily disabled while a
        # loader splash hands off to GLFW. Seed the vanilla completion flag so
        # automation begins deterministically on the title screen.
        (game_dir / "options.txt").write_text(
            "onboardAccessibility:false\ntutorialStep:none\nrenderClouds:false\n"
            "fullscreen:false\n", encoding="utf-8"
        )
    env = os.environ.copy()
    # Route every game launch exclusively through its agent-owned Xvfb display.
    env.pop("WAYLAND_DISPLAY", None)
    env.pop("WAYLAND_SOCKET", None)
    env.update(
        {
            "DISPLAY": display,
            "JAVA_HOME": target.java_home,
            "PATH": f"{target.java_home}/bin:{env.get('PATH', '')}",
            RUN_ID_ENV: run_id,
            "ALSOFT_DRIVERS": "null",
        }
    )
    command = [
        "./gradlew", "--no-daemon", "--max-workers=1",
        "--init-script", str(ROOT / "scripts/smoke-isolated.gradle"),
        f"-PusefulToolsSmokeGameDir={game_dir}",
    ]
    if args.offline:
        command.append("--offline")
    if args.skip_asset_download:
        asset_download_tasks = {
            "1.20.2-neoforge": "neoFormJoined1.20.2-20231019.002635DownloadAssets",
            "1.21.1-neoforge": "neoFormJoined1.21.1-20240808.144430DownloadAssets",
            "26.1.2-neoforge": "neoFormJoined26.1.2-1DownloadAssets",
            "26.2-neoforge": "neoFormJoined26.2-2DownloadAssets",
        }
        asset_task = asset_download_tasks.get(args.target)
        if asset_task is None:
            parser.error("--skip-asset-download is supported only for NeoForge targets with a known asset task")
        command.extend(("-x", asset_task))
    if args.asset_cache_source:
        command.append(f"-PusefulToolsSmokeAssetSource={args.asset_cache_source.resolve()}")
    if args.ignore_sodium_lwjgl_check:
        command.append("-PusefulToolsSmokeJvmArgs=-Dsodium.checks.issue2561=false")
    command.extend(f"-P{value}" for value in args.gradle_property)
    if target.loader == "fabric":
        command.append("-PusefulToolsModMenuRuntime=true")
    command.append(target.task)
    xvfb = None if args.display else subprocess.Popen(
        ["Xvfb", display, "-screen", "0", "1280x720x24", "-nolisten", "tcp"],
        env={**env, RUN_ID_ENV: run_id}, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL,
    )
    process: subprocess.Popen[str] | None = None
    lines: queue.Queue[str | None] = queue.Queue()
    all_output: list[str] = []
    failure: str | None = None
    joined = False
    config_opened = False
    clean_exit = False
    showcase_commands_succeeded = False
    spectral_commands_succeeded = False
    try:
        time.sleep(0.5)
        print(f"==> {name}: client smoke", flush=True)
        with log_path.open("w", encoding="utf-8") as log:
            logged_bytes = 0
            log_truncated = False
            process = subprocess.Popen(
                command, cwd=ROOT / target.directory, env=env,
                stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True,
                bufsize=1, start_new_session=True,
            )

            def reader() -> None:
                assert process is not None and process.stdout is not None
                for line in process.stdout:
                    lines.put(line)
                lines.put(None)

            threading.Thread(target=reader, daemon=True).start()

            def drain() -> None:
                nonlocal logged_bytes, log_truncated
                while True:
                    try:
                        line = lines.get_nowait()
                    except queue.Empty:
                        break
                    if line is None:
                        break
                    clean = ANSI_RE.sub("", line)
                    all_output.append(clean)
                    line_bytes = len(clean.encode("utf-8"))
                    if logged_bytes + line_bytes <= LOG_BYTE_LIMIT:
                        log.write(clean)
                        logged_bytes += line_bytes
                        log.flush()
                    elif not log_truncated:
                        log.write("\\n[smoke-client] Log truncated at 8 MiB; live output monitoring continues.\\n")
                        log.flush()
                        log_truncated = True
                    if args.verbose or JOIN_RE.search(clean) or CRASH_RE.search(clean):
                        sys.stdout.write(clean)
                        sys.stdout.flush()

            # Gradle may spend several minutes resolving a fresh optional
            # shader loader before GLFW creates its first window. Drain logs
            # throughout that wait so a failed launch leaves diagnostics.
            window = None
            window_deadline = time.monotonic() + min(300, args.timeout)
            while time.monotonic() < window_deadline:
                drain()
                result = xdo(display, "search", "--onlyvisible", "--name", "Minecraft", check=False)
                ids = [line for line in result.stdout.splitlines() if line.isdigit()]
                if ids:
                    window = ids[-1]
                    break
                if process.poll() is not None:
                    break
                time.sleep(0.5)
            if window is None:
                drain()
                raise RuntimeError("Minecraft window did not appear")
            # Wait for the resource reload/title screen, then harmlessly dismiss
            # first-run accessibility onboarding if it exists.
            deadline = time.monotonic() + 90
            while time.monotonic() < deadline:
                drain()
                title_markers = (
                    ("Forge Mod Loader has successfully loaded",)
                    if target.loader == "classic"
                    else ("mob_effects.png-atlas", "gui.png-atlas")
                )
                if any(marker in line for line in all_output for marker in title_markers):
                    break
                if process.poll() is not None:
                    break
                time.sleep(0.25)
            time.sleep(10)
            # NeoForge's early-display window can be replaced by the actual
            # GLFW game window during startup, so refresh the id immediately
            # before input instead of before the post-atlas transition.
            window = window_id(display, 10)
            # Desktop window managers may launch Minecraft maximized even
            # when the disposable options request windowed mode. Clear those
            # states before resizing so click coordinates share the harness's
            # landscape 854x480 reference aspect ratio.
            xdo(display, "windowstate", "--remove", "MAXIMIZED_HORZ", window, check=False)
            xdo(display, "windowstate", "--remove", "MAXIMIZED_VERT", window, check=False)
            xdo(display, "windowsize", "--sync", window, "1280", "720")
            # Keep the entire test window inside the bounded Xvfb framebuffer.
            xdo(display, "windowmove", "--sync", window, "0", "0")
            window_geometry = xdo(display, "getwindowgeometry", "--shell", window).stdout
            print(f"{name}: game window geometry after normalization: {window_geometry.strip().replace(chr(10), ', ')}", flush=True)
            time.sleep(2)
            if name == "26.2-neoforge" and any(
                "iris" in mod_path.name.lower() for mod_path in args.client_mod
            ):
                # Iris/Sodium's current 26.2 mod metadata triggers NeoForge's
                # one-time deprecated-metadata screen before the title menu.
                # Traverse to its third button, Proceed to Main Menu, without
                # relying on a pointer event during Iris' initial compilation.
                for _ in range(5):
                    xdo(display, "key", "--window", window, "Tab")
                xdo(display, "key", "--window", window, "Return")
                time.sleep(3)
                window = window_id(display, 10)
            print(f"{name}: title screen ready", flush=True)
            is_26 = name.startswith("26.")
            if args.spectral_render_check:
                # Shader QA is concerned with the rendered entity path, so
                # bypass the loader-specific config screens and use keyboard
                # focus on the stable title-screen Singleplayer action.
                config_opened = True
                print(f"{name}: config screen skipped for spectral render QA", flush=True)
            elif target.loader == "fabric":
                mod_menu_x, mod_menu_y = (
                    (498, 380) if name == "26.2-fabric" else (427, 356)
                )
                click(display, window, mod_menu_x, mod_menu_y)
                time.sleep(2)
                click(display, window, 180, 64)
                xdo(display, "type", "--window", window, "--delay", "15", "Useful Tools")
                time.sleep(1)
                click(display, window, 205, 167)
                time.sleep(1)
                click(display, window, 825, 115)
            elif target.loader == "classic":
                click(display, window, 527, 332)
                time.sleep(2)
                click(display, window, 130, 310)
                time.sleep(1)
                click(display, window, 137, 380)
            else:
                # Minecraft 26 moved the loader-provided Mods button into the
                # right half of the Create Test World row.
                mods_x, mods_y = (
                    (355, 348)
                    if name == "26.2-neoforge"
                    else (527, 348)
                    if is_26 and target.loader == "neoforge"
                    else (323, 332)
                )
                click(display, window, mods_x, mods_y)
                time.sleep(2)
                if name == "26.2-neoforge":
                    click(display, window, 130, 207)
                    time.sleep(1)
                    click(display, window, 746, 384)
                else:
                    click(display, window, 130, 350)
                    xdo(display, "type", "--window", window, "--delay", "15", "Useful Tools")
                    time.sleep(1)
                    click(display, window, 130, 90)
                    time.sleep(2)
                    click(display, window, 130, 397)
            if not args.spectral_render_check:
                time.sleep(3)
                drain()
                xdo(display, "mousemove", "--sync", "--window", window, "10", "10")
                time.sleep(1)
                screenshot(display, config_image, window, game_dir)
                config_opened = image_is_substantial(config_image) and not any(
                    CRASH_RE.search(line) for line in all_output
                )
                if not config_opened:
                    failure = "config screen did not open cleanly"
                print(f"{name}: config screen {'OK' if config_opened else 'FAILED'}", flush=True)

            # Cancel back through the config and mod-list screens.
            if not args.spectral_render_check:
                xdo(display, "key", "--window", window, "Escape")
                time.sleep(1)
                xdo(display, "key", "--window", window, "Escape")
            if not args.spectral_render_check and target.loader == "fabric":
                # Mod Menu has a separate selected-mod detail screen between
                # the config screen and its list, so Fabric needs one more
                # level of cancellation before returning to the title screen.
                time.sleep(1)
                xdo(display, "key", "--window", window, "Escape")
            if args.spectral_render_check:
                pass
            elif name == "26.2-neoforge":
                wait_for_button(display, 427, 204, 60)
            elif target.loader == "classic":
                window = wait_for_window_replacement(display, window, 45)
                wait_for_button(display, 427, 237, 15)
            elif not args.spectral_render_check:
                time.sleep(2)
            # Create the first disposable singleplayer world.
            singleplayer_y = (
                237 if name == "26.2-fabric"
                else 204 if (is_26 and target.loader == "neoforge") or name == "1.21.1-neoforge"
                else (211 if target.loader == "fabric" else 237)
            )
            if args.spectral_render_check:
                geometry = xdo(display, "getwindowgeometry", "--shell", window).stdout
                dimensions = dict(
                    line.split("=", 1) for line in geometry.splitlines() if "=" in line
                )
                # The UI is scaled from the window width, while this host can
                # expose a much taller Xwayland surface than the game's normal
                # 16:9 reference. Center the title-button stack in that extra
                # vertical space instead of choosing an arbitrary portrait y.
                logical_height = int(dimensions["HEIGHT"]) * 854 / int(dimensions["WIDTH"])
                singleplayer_y += round((logical_height - 480) * 0.276)
                world_action_y = 443 + round((logical_height - 480) * 0.98)
                # Keep the cheats click on the center of the toggle; placing it
                # too low can land on the create action on tall Xwayland windows.
                cheats_y = 284 + round((logical_height - 480) * 0.23)
            else:
                world_action_y, cheats_y = 443, 284
            print(f"{name}: creating disposable world", flush=True)
            if args.spectral_render_check:
                # Advance one screen at a time. Repeating the title-screen
                # coordinate after opening the world list can leave the click
                # on an inert row, especially on high-DPI portrait surfaces.
                focus_and_enter(display, window, 427, singleplayer_y)
                time.sleep(2)
                # The world-list screen's Create New World button is on the
                # left; the right-hand button returns to the title screen.
                focus_and_enter(display, window, 267, world_action_y)
                time.sleep(2)
                screenshot(display, creation_image, window, game_dir)
                # Allow Cheats toggle on the Game tab; keep a capture after
                # the toggle so a failed click is visible in the QA artifact.
                click(display, window, 427, cheats_y)
                time.sleep(0.5)
                screenshot(display, cheats_image, window, game_dir)
                create_world_x = 727 if target.loader == "classic" else 267
                click(display, window, create_world_x, world_action_y)
            elif name == "26.2-neoforge":
                # This beta intermittently drops synthetic mouse clicks on
                # the animated title screen, while keyboard focus remains
                # deterministic: Tab selects Singleplayer and Return accepts
                # both it and the default Create New World action.
                xdo(display, "key", "--window", window, "Tab")
                time.sleep(0.5)
                xdo(display, "key", "--window", window, "Return")
                time.sleep(3)
                xdo(display, "key", "--window", window, "Return")
            elif target.loader == "classic":
                screenshot(display,  title_image, window, game_dir)
                quick_click(display, 427, singleplayer_y)
                time.sleep(3)
                quick_click(display, 584, 397)
                time.sleep(2)
                quick_click(display, 206, 443)
            else:
                # Loader screens can remain visible for a few frames after the
                # final Escape. Re-resolve and retry the title-screen action;
                # the coordinate is inert on the empty-world creation screen.
                for _ in range(3):
                    click(display, window, 427, singleplayer_y)
                    time.sleep(2)
                if args.visual_showcase or args.spectral_render_check:
                    screenshot(display,  creation_image, window, game_dir)
                    # Vanilla 1.21.1's Allow Commands toggle on the Game tab.
                    click(display, window, 427, 284)
                    time.sleep(1)
                create_world_x = 727 if target.loader == "classic" else 267
                click(display, window, create_world_x, 443)
            if target.loader == "forge" and (
                name.startswith("1.21.1") or name.startswith("26.")
            ):
                # The 1.21.1 worldgen registries display Mojang's experimental
                # settings confirmation before generating a fresh world.
                time.sleep(2)
                click(display, window, 267, 296)
            join_deadline = time.monotonic() + args.timeout
            while time.monotonic() < join_deadline and process.poll() is None:
                drain()
                if any(JOIN_RE.search(line) for line in all_output):
                    joined = True
                    break
                if any(CRASH_RE.search(line) for line in all_output):
                    break
                time.sleep(0.25)
            if joined:
                # Some Fabric versions replace the top-level window when the
                # integrated server opens. Do not keep using the title-screen
                # XID for in-world captures or command entry.
                window = window_id(display, 30)
                # Iris compiles its first world pipeline during the initial
                # frame on 26.2; give that work time to finish before F2.
                time.sleep(30 if name == "26.2-neoforge" and args.shader_pack else 2)
                screenshot(display,  world_image, window, game_dir)
                if args.spectral_render_check:
                    def spectral_command(command: str, expected: tuple[str, ...]) -> None:
                        for _ in range(3):
                            start = len(all_output)
                            xdo(display, "key", "--window", window, "t")
                            time.sleep(0.8)
                            xdo(display, "type", "--window", window, "--delay", "8", f"/{command}")
                            xdo(display, "key", "--window", window, "Return")
                            deadline = time.monotonic() + 12
                            while time.monotonic() < deadline:
                                drain()
                                if any(
                                    marker in line for line in all_output[start:]
                                    for marker in expected
                                ):
                                    return
                                time.sleep(0.3)
                        raise RuntimeError(f"scene command was not accepted: {command}")

                    time.sleep(8)  # Let shader compilation and the first world frames settle.
                    spawn_rule = (
                        "minecraft:spawn_mobs"
                        if args.target.startswith(("26.1.2-", "26.2-"))
                        else "doMobSpawning"
                    )
                    for command, expected in (
                        ("gamemode creative", ("Set own game mode to Creative Mode",)),
                        (
                            f"gamerule {spawn_rule} false",
                            (
                                f"Gamerule {spawn_rule.rsplit(':', 1)[-1]}",
                                f"Game rule {spawn_rule.rsplit(':', 1)[-1]}",
                            ),
                        ),
                        ("weather clear", ("Set the weather to clear",)),
                        ("effect clear @s", ("Target has no effects", "Removed every effect")),
                        ("kill @e[type=!minecraft:player]", ("Killed ", "No entity was found")),
                        ("tp @s 0 205 3 180 8", ("205.000000",)),
                    ):
                        spectral_command(command, expected)
                    time.sleep(2)  # Let the destination chunks load before /fill.
                    for command, expected in (
                        ("fill -8 199 -8 8 199 8 minecraft:smooth_stone",
                         ("Successfully filled", "No blocks were filled")),
                        ("fill -8 200 -8 8 210 8 minecraft:air",
                         ("Successfully filled", "No blocks were filled")),
                        ("tp @s 0 200 3 180 8", ("200.000000",)),
                    ):
                        spectral_command(command, expected)
                    xdo(display, "key", "--window", window, "F1")
                    for entity in ("ghost", "wraith"):
                        spectral_command(
                            f"summon usefultoolsmod:{entity} 0 201 -3 "
                            "{NoAI:1b,NoGravity:1b,Silent:1b,PersistenceRequired:1b,Invulnerable:1b}",
                            (f"Summoned new {entity.title()}",),
                        )
                        for lighting, clock_time, expected_time in (
                            ("day", "noon", "6000"), ("night", "midnight", "18000")
                        ):
                            time_message = (
                                f"Set minecraft:overworld to time marker minecraft:{clock_time}"
                                if args.target.startswith(("26.1.2-", "26.2-"))
                                else f"Set the time to {expected_time}"
                            )
                            spectral_command(
                                f"time set {clock_time}", (time_message,)
                            )
                            time.sleep(2)
                            screenshot(display,  spectral_images[entity, lighting], window, game_dir)
                        noon_message = (
                            "Set minecraft:overworld to time marker minecraft:noon"
                            if args.target.startswith(("26.1.2-", "26.2-"))
                            else "Set the time to 6000"
                        )
                        spectral_command("time set noon", (noon_message,))
                        spectral_command("weather rain 100000", ("Set the weather to rain",))
                        time.sleep(3)
                        screenshot(display,  spectral_environment_images[entity, "rain"], window, game_dir)
                        spectral_command("weather clear", ("Set the weather to clear",))
                        spectral_command(
                            "fill -4 201 -5 4 205 -5 minecraft:deepslate_tiles",
                            ("Successfully filled", "No blocks were filled"),
                        )
                        time.sleep(1)
                        screenshot(display,  spectral_environment_images[entity, "dark-background"], window, game_dir)
                        spectral_command(
                            "fill -4 201 -5 4 205 -5 minecraft:white_concrete",
                            ("Successfully filled", "No blocks were filled"),
                        )
                        time.sleep(1)
                        screenshot(display,  spectral_environment_images[entity, "bright-background"], window, game_dir)
                        spectral_command("fill -4 201 -5 4 205 -5 minecraft:air",
                                         ("Successfully filled", "No blocks were filled"))
                        if args.retain_spectral_entities:
                            spectral_command(
                                f"tp @e[type=usefultoolsmod:{entity},distance=..16] 1000 200 1000",
                                ("Teleported",),
                            )
                        else:
                            spectral_command(
                                f"kill @e[type=usefultoolsmod:{entity},distance=..16]",
                                (f"Killed {entity.title()}",),
                            )
                    xdo(display, "key", "--window", window, "F1")
                    spectral_command(
                        "fill -5 199 -8 5 207 4 minecraft:deepslate_bricks hollow",
                        ("Successfully filled", "No blocks were filled"),
                    )
                    spectral_command("fill -5 200 4 5 206 4 minecraft:air",
                                     ("Successfully filled", "No blocks were filled"))
                    spectral_command(
                        "setblock 0 205 -4 minecraft:sea_lantern",
                        ("Block placed", "Changed the block at"),
                    )
                    spectral_command("tp @s 0 200 3 180 8", ("200.000000",))
                    xdo(display, "key", "--window", window, "F1")
                    for entity in ("ghost", "wraith"):
                        spectral_command(
                            f"summon usefultoolsmod:{entity} 0 201 -3 "
                            "{NoAI:1b,NoGravity:1b,Silent:1b,PersistenceRequired:1b,Invulnerable:1b}",
                            (f"Summoned new {entity.title()}",),
                        )
                        time.sleep(1)
                        screenshot(display,  spectral_environment_images[entity, "indoor"], window, game_dir)
                        if args.retain_spectral_entities:
                            spectral_command(
                                f"tp @e[type=usefultoolsmod:{entity},distance=..16] 1000 200 1000",
                                ("Teleported",),
                            )
                        else:
                            spectral_command(
                                f"kill @e[type=usefultoolsmod:{entity},distance=..16]",
                                (f"Killed {entity.title()}",),
                            )
                    xdo(display, "key", "--window", window, "F1")
                    spectral_commands_succeeded = True
                if args.visual_showcase:
                    def chat_command(command: str, delay: float = 0.6) -> None:
                        xdo(display, "key", "--window", window, "t")
                        time.sleep(0.3)
                        xdo(display, "type", "--window", window, "--delay", "2", f"/{command}")
                        xdo(display, "key", "--window", window, "Return")
                        time.sleep(delay)
                        drain()

                    def text_display_command(
                        x: int, y: int, z: int, label: str, color: str = "white"
                    ) -> str:
                        # Text-display entity data stores a serialized component
                        # string, not an inline SNBT compound. A JSON object here
                        # is accepted by /summon but produces an empty display and
                        # logs "Display entity Not a string" on 1.21.1.
                        text = json.dumps(label)
                        return (
                            f"summon minecraft:text_display {x} {y} {z} "
                            "{Tags:[\"usefultools_visual_label\"],billboard:\"center\","
                            f"background:1073741824,shadow:1b,text:'{text}'}}"
                        )

                    def ensure_world_view() -> None:
                        """Close any screen without assuming its current state.

                        Escape closes an inventory but opens pause from the
                        world. The subsequent Back to Game coordinate is inert
                        in-world and closes pause when it is present.
                        """
                        xdo(display, "key", "--window", window, "Escape")
                        time.sleep(0.5)
                        click(display, window, 427, 144)
                        time.sleep(0.8)

                    commands = [
                        "gamemode creative",
                        "gamerule sendCommandFeedback false",
                        "gamerule doMobSpawning false",
                        "kill @e[type=!minecraft:player]",
                        "effect give @s minecraft:night_vision infinite 0 true",
                        "weather clear",
                        "time set midnight",
                        "tp @s 0 205 -1 180 25",
                        "fill -14 199 -14 14 199 14 minecraft:stone",
                        "fill -14 200 -14 14 210 14 minecraft:air",
                        "fill -7 199 6 7 199 6 minecraft:glass",
                        "fill -7 199 -6 7 199 -6 minecraft:glass",
                        "setblock 0 200 2 usefultoolsmod:ectoplasm_lantern",
                        "setblock 0 200 -2 usefultoolsmod:ectoplasm_lantern",
                        "setblock 1 200 2 minecraft:redstone_block",
                        "setblock 1 200 -2 minecraft:redstone_block",
                        # Trigger the inventory advancement and recipe toast well
                        # before the Wraith screenshots, then restore the ordinary
                        # weapon used by the first acceptance state.
                        "item replace entity @s weapon.mainhand with usefultoolsmod:ecto_sword",
                        "item replace entity @s weapon.mainhand with minecraft:iron_sword",
                    ]
                    directions = ("down", "up", "north", "south", "west", "east")
                    for index, direction in enumerate(directions):
                        x = -5 + index * 2
                        commands.append(
                            f"setblock {x} 200 4 usefultoolsmod:mining_charge"
                            f"[facing={direction},lit=false]"
                        )
                        commands.append(
                            f"setblock {x} 200 -4 usefultoolsmod:mining_charge"
                            f"[facing={direction},lit=false]"
                        )
                        commands.append(
                            f"setblock {x} 200 6 usefultoolsmod:mining_charge"
                            f"[facing={direction},lit=true]"
                        )
                        commands.append(
                            f"setblock {x} 200 -6 usefultoolsmod:mining_charge"
                            f"[facing={direction},lit=true]"
                        )
                    for showcase_command in commands:
                        chat_command(showcase_command)
                    showcase_commands_succeeded = any(
                        "Set own game mode to Creative Mode" in line for line in all_output
                    )
                    time.sleep(8)
                    chat_command("tp @s 0 203 0 180 20", 0.2)
                    time.sleep(1)
                    screenshot(display,  blocks_image, window, game_dir)

                    # Dedicated Ectoplasm Lantern proof from every face. The
                    # invisible barrier support makes the lower cage and core
                    # visible without hiding alpha leaks behind an opaque floor.
                    # Spectator mode prevents the camera from falling between
                    # teleports; creative mode alone does not enable flight.
                    chat_command("gamemode spectator")
                    chat_command("fill -14 199 -14 14 210 14 minecraft:air")
                    chat_command("setblock 0 199 0 minecraft:barrier")
                    chat_command("setblock 0 200 0 usefultoolsmod:ectoplasm_lantern")
                    lantern_views = (
                        ("tp @s 0.5 199.5 3 180 14", lantern_south_image),
                        ("tp @s 0.5 199.5 -2 0 14", lantern_north_image),
                        ("tp @s 3 199.5 0.5 90 14", lantern_east_image),
                        ("tp @s -2 199.5 0.5 -90 14", lantern_west_image),
                        ("tp @s 0.5 201.5 0.5 0 90", lantern_top_image),
                        ("tp @s 0.5 197.5 0.5 0 -90", lantern_bottom_image),
                    )
                    for teleport, lantern_image in lantern_views:
                        chat_command(teleport, 0.2)
                        time.sleep(1)
                        screenshot(display,  lantern_image, window, game_dir)

                    # Dedicated Mining Charge proof. Each capture uses a clean
                    # scene, labels all six facing states, and keeps vanilla
                    # blocks out of the foreground so the subject is
                    # unambiguous. The final close-up uses a glass support to
                    # expose any unbounded transparency or floor-view leak.
                    chat_command("kill @e[type=minecraft:text_display,tag=usefultools_visual_label]")
                    chat_command("fill -14 200 -14 14 210 14 minecraft:air")
                    chat_command("fill -7 199 3 7 199 7 minecraft:smooth_stone")
                    for index, direction in enumerate(directions):
                        x = -5 + index * 2
                        chat_command(
                            f"setblock {x} 200 5 usefultoolsmod:mining_charge"
                            f"[facing={direction},lit=false]",
                            0.1,
                        )
                        chat_command(text_display_command(x, 202, 5, direction.upper()), 0.1)
                    chat_command("tp @s 0 200 11 180 11", 0.2)
                    time.sleep(2)
                    screenshot(display,  mining_unlit_image, window, game_dir)

                    for index, direction in enumerate(directions):
                        x = -5 + index * 2
                        chat_command(
                            f"setblock {x} 200 5 usefultoolsmod:mining_charge"
                            f"[facing={direction},lit=true]",
                            0.1,
                        )
                    time.sleep(2)
                    screenshot(display,  mining_lit_image, window, game_dir)

                    chat_command("kill @e[type=minecraft:text_display,tag=usefultools_visual_label]")
                    chat_command("fill -7 199 3 7 203 7 minecraft:air")
                    chat_command("fill -2 199 4 2 199 6 minecraft:glass")
                    chat_command("setblock -1 200 5 usefultoolsmod:mining_charge[facing=down,lit=false]")
                    chat_command("setblock 1 200 5 usefultoolsmod:mining_charge[facing=down,lit=true]")
                    chat_command(text_display_command(-1, 202, 5, "UNLIT"), 0.1)
                    chat_command(text_display_command(1, 202, 5, "LIT", "red"), 0.1)
                    chat_command("tp @s 0 200 9 180 15", 0.2)
                    time.sleep(2)
                    screenshot(display,  mining_glass_image, window, game_dir)
                    chat_command("kill @e[type=minecraft:text_display,tag=usefultools_visual_label]")
                    chat_command("fill -7 199 3 7 203 7 minecraft:air")

                    # Capture every advertised Wraith overlay state separately:
                    # hunting/warded crossed with ordinary/infused weapon.
                    chat_command("gamemode creative")
                    chat_command("fill -4 199 -6 4 199 2 minecraft:smooth_stone")
                    chat_command("tp @s 0 200 0 180 0")
                    chat_command("item replace entity @s weapon.mainhand with minecraft:iron_sword")
                    chat_command(
                        'summon usefultoolsmod:wraith 0 201 -3 '
                        '{NoAI:1b,NoGravity:1b,Silent:1b,PersistenceRequired:1b,Invulnerable:1b}'
                    , 0.3)
                    ensure_world_view()
                    time.sleep(2)
                    screenshot(display,  wraith_hunting_ordinary_image, window, game_dir)
                    chat_command("item replace entity @s weapon.mainhand with usefultoolsmod:ecto_sword")
                    ensure_world_view()
                    time.sleep(1)
                    screenshot(display,  wraith_hunting_infused_image, window, game_dir)
                    chat_command("setblock 2 200 -3 usefultoolsmod:ectoplasm_lantern")
                    chat_command("item replace entity @s weapon.mainhand with minecraft:iron_sword")
                    ensure_world_view()
                    time.sleep(1)
                    screenshot(display,  wraith_warded_ordinary_image, window, game_dir)
                    chat_command("item replace entity @s weapon.mainhand with usefultoolsmod:ecto_sword")
                    ensure_world_view()
                    time.sleep(1)
                    screenshot(display,  wraith_warded_infused_image, window, game_dir)
                    # Retain the original report field as a compatibility alias
                    # for the strongest warded+infused acceptance state.
                    screenshot(display,  wraith_image, window, game_dir)
                    chat_command("kill @e[type=usefultoolsmod:wraith,distance=..16]")
                    chat_command("kill @e[type=minecraft:item,distance=..16]")
                    chat_command("setblock 2 200 -3 minecraft:air")
                    chat_command(
                        'summon usefultoolsmod:ghost 0 201 -3 '
                        '{NoAI:1b,NoGravity:1b,Silent:1b,PersistenceRequired:1b,Invulnerable:1b}'
                    , 0.3)
                    ensure_world_view()
                    time.sleep(2)
                    screenshot(display,  ghost_image, window, game_dir)
                    chat_command("kill @e[type=usefultoolsmod:ghost,distance=..16]")
                if args.inventory_probe:
                    xdo(display, "key", "--window", window, "e")
                    time.sleep(5)
                    if args.inventory_search:
                        click(display, window, 710, 447)
                        xdo(display, "key", "--window", window, "ctrl+a")
                        xdo(display, "type", "--window", window, "--delay", "25", args.inventory_search)
                        time.sleep(3)
                    screenshot(display,  inventory_image, window, game_dir)
                    if args.inventory_search:
                        # JEI consumes the first Escape while its search box
                        # owns focus; a second one closes the inventory.
                        xdo(display, "key", "--window", window, "Escape")
                        time.sleep(0.5)
                    xdo(display, "key", "--window", window, "Escape")
                    time.sleep(1)
                xdo(display, "key", "--window", window, "Escape")
                time.sleep(1)
                screenshot(display,  pause_image, window, game_dir)
                # Forge and NeoForge add a full-width Mods button above the
                # vanilla Save and Quit button. Fabric keeps the vanilla
                # pause-menu layout.
                save_quit_y = (
                    348
                    if target.loader in ("fabric", "classic") or name == "26.2-neoforge"
                    else 383
                )
                if args.spectral_render_check:
                    focus_and_enter(display, window, 427, save_quit_y)
                else:
                    click(display, window, 427, save_quit_y)
                time.sleep(20)
                quit_y = (
                    428 if name == "26.2-forge"
                    else 396 if name == "26.2-neoforge"
                    else 420 if is_26 and target.loader == "neoforge"
                    else (429 if target.loader == "fabric" else 404)
                )
                if args.spectral_render_check:
                    focus_and_enter(display, window, 527, quit_y)
                else:
                    click(display, window, 527, quit_y)
                try:
                    process.wait(timeout=30)
                except subprocess.TimeoutExpired:
                    if args.spectral_render_check:
                        focus_and_enter(display, window, 527, quit_y)
                    else:
                        click(display, window, 527, quit_y)
                    process.wait(timeout=30)
                drain()
                clean_exit = process.returncode == 0
            elif failure is None:
                failure = "singleplayer world did not reach player join"
        if joined and not image_is_substantial(world_image):
            failure = "world screenshot was empty"
    except (OSError, RuntimeError, subprocess.SubprocessError) as exc:
        failure = failure or str(exc)
    finally:
        if process is not None and process.poll() is None:
            cleanup(run_id)
        remaining = cleanup(run_id)
        if xvfb is not None and xvfb.poll() is None:
            xvfb.terminate()
            try:
                xvfb.wait(timeout=3)
            except subprocess.TimeoutExpired:
                xvfb.kill()
        game_temp.cleanup()

    shader_pack_loaded = (
        any(f"Using shaderpack: {args.shader_pack.name}" in line for line in all_output)
        if args.shader_pack else None
    )
    shader_loader_disabled = (
        any("Shaders are disabled because enableShaders is set to false" in line
            for line in all_output)
        if args.disable_shaders else None
    )
    success = config_opened and joined and clean_exit and not remaining and failure is None
    if args.shader_pack and not shader_pack_loaded:
        success = False
        failure = failure or "shader pack was not reported as loaded"
    if args.disable_shaders and not shader_loader_disabled:
        success = False
        failure = failure or "Iris did not report that shaders were explicitly disabled"
    if args.inventory_probe and not image_is_substantial(inventory_image):
        success = False
        failure = failure or "inventory probe screenshot was empty"
    if args.visual_showcase:
        showcase_images = (
            blocks_image, lantern_south_image, lantern_north_image,
            lantern_east_image, lantern_west_image, lantern_top_image,
            lantern_bottom_image, mining_unlit_image, mining_lit_image,
            mining_glass_image, wraith_hunting_ordinary_image,
            wraith_hunting_infused_image, wraith_warded_ordinary_image,
            wraith_warded_infused_image, wraith_image, ghost_image,
        )
        if name != "1.21.1-neoforge":
            success = False
            failure = failure or "visual showcase is supported only on 1.21.1-neoforge"
        elif not all(image_is_substantial(path) for path in showcase_images):
            success = False
            failure = failure or "visual showcase screenshot was empty"
        elif not showcase_commands_succeeded:
            success = False
            failure = failure or "visual showcase commands were not accepted by the server"
    if args.spectral_render_check:
        if not all(image_is_substantial(path) for path in (
            *spectral_images.values(), *spectral_environment_images.values()
        )):
            success = False
            failure = failure or "spectral lighting/context screenshot was empty"
        elif not spectral_commands_succeeded:
            success = False
            failure = failure or "spectral render scene commands were not accepted"
    report = {
        "target": name,
        "command": command,
        "gradle_java_home": target.java_home,
        "runtime_java_home": target.runtime_java_home or target.java_home,
        "config_opened": config_opened,
        "joined_world": joined,
        "clean_exit": clean_exit,
        "remaining_owned_pids": remaining,
        "failure": failure,
        "client_mods": [mod.name for mod in args.client_mod],
        "shader_pack": args.shader_pack.name if args.shader_pack else None,
        "shader_pack_loaded": shader_pack_loaded,
        "shaders_explicitly_disabled": args.disable_shaders,
        "shader_loader_disabled_reported": shader_loader_disabled,
        "sodium_lwjgl_check_ignored": args.ignore_sodium_lwjgl_check,
        "display": display,
        "spectral_render_check_requested": args.spectral_render_check,
        "spectral_entities_retained_after_capture": args.retain_spectral_entities,
        "spectral_render_commands_succeeded": spectral_commands_succeeded,
        "spectral_render_screenshots": (
            [str(path.relative_to(ROOT)) for path in (
                *spectral_images.values(), *spectral_environment_images.values()
            ) if path.exists()]
            if args.spectral_render_check else []
        ),
        "log": str(log_path.relative_to(ROOT)),
        "config_screenshot": str(config_image.relative_to(ROOT)),
        "title_screenshot": str(title_image.relative_to(ROOT)),
        "world_creation_screenshot": (
            str(creation_image.relative_to(ROOT))
            if args.visual_showcase or args.spectral_render_check else None
        ),
        "world_cheats_screenshot": (
            str(cheats_image.relative_to(ROOT))
            if args.spectral_render_check else None
        ),
        "world_screenshot": str(world_image.relative_to(ROOT)),
        "pause_screenshot": str(pause_image.relative_to(ROOT)),
        "inventory_probe_requested": args.inventory_probe,
        "inventory_search": args.inventory_search,
        "inventory_screenshot": (
            str(inventory_image.relative_to(ROOT)) if args.inventory_probe else None
        ),
        "visual_showcase_requested": args.visual_showcase,
        "visual_showcase_commands_succeeded": showcase_commands_succeeded,
        "blocks_screenshot": str(blocks_image.relative_to(ROOT)) if args.visual_showcase else None,
        "ectoplasm_lantern_screenshots": (
            [str(path.relative_to(ROOT)) for path in (
                lantern_south_image, lantern_north_image, lantern_east_image,
                lantern_west_image, lantern_top_image, lantern_bottom_image,
            )] if args.visual_showcase else []
        ),
        "mining_charge_unlit_screenshot": (
            str(mining_unlit_image.relative_to(ROOT)) if args.visual_showcase else None
        ),
        "mining_charge_lit_screenshot": (
            str(mining_lit_image.relative_to(ROOT)) if args.visual_showcase else None
        ),
        "mining_charge_glass_screenshot": (
            str(mining_glass_image.relative_to(ROOT)) if args.visual_showcase else None
        ),
        "wraith_wthit_screenshot": str(wraith_image.relative_to(ROOT)) if args.visual_showcase else None,
        "wraith_wthit_state_screenshots": (
            {
                "hunting_ordinary": str(wraith_hunting_ordinary_image.relative_to(ROOT)),
                "hunting_infused": str(wraith_hunting_infused_image.relative_to(ROOT)),
                "warded_ordinary": str(wraith_warded_ordinary_image.relative_to(ROOT)),
                "warded_infused": str(wraith_warded_infused_image.relative_to(ROOT)),
            } if args.visual_showcase else {}
        ),
        "ghost_wthit_screenshot": str(ghost_image.relative_to(ROOT)) if args.visual_showcase else None,
        "success": success,
    }
    (output_dir / f"{name}.json").write_text(json.dumps(report, indent=2) + "\n", encoding="utf-8")
    print(f"client smoke: {'OK' if success else 'FAILED'} ({name})")
    return success


def main() -> int:
    args = parse_args()
    property_re = re.compile(r"[A-Za-z][A-Za-z0-9_.-]*=[A-Za-z0-9_.-]+")
    invalid_properties = [value for value in args.gradle_property if not property_re.fullmatch(value)]
    if invalid_properties:
        print(f"invalid Gradle properties: {invalid_properties}", file=sys.stderr)
        return 4
    if args.inventory_search and not args.inventory_probe:
        print("--inventory-search requires --inventory-probe", file=sys.stderr)
        return 4
    if args.target == "verify-existing":
        args.output_dir.mkdir(parents=True, exist_ok=True)
        results: dict[str, bool] = {}
        for name in TARGETS:
            report_path = args.output_dir / f"{name}.json"
            try:
                report = json.loads(report_path.read_text(encoding="utf-8"))
                results[name] = report.get("target") == name and report.get("success") is True
            except (OSError, json.JSONDecodeError):
                results[name] = False
        summary = {
            "target_count": len(results),
            "passed_targets": sum(results.values()),
            "failed_targets": [name for name, passed in results.items() if not passed],
            "success": all(results.values()),
        }
        (args.output_dir / "matrix-summary.json").write_text(
            json.dumps(summary, indent=2) + "\n", encoding="utf-8"
        )
        print(
            f"client matrix: {summary['passed_targets']}/{summary['target_count']} passed"
        )
        return 0 if summary["success"] else 1
    names = list(TARGETS) if args.target == "all" else [args.target]
    results = {name: run_target(name, TARGETS[name], args) for name in names}
    if args.target == "all":
        summary = {
            "target_count": len(names),
            "passed_targets": sum(results.values()),
            "failed_targets": [name for name, passed in results.items() if not passed],
            "success": all(results.values()),
        }
        args.output_dir.mkdir(parents=True, exist_ok=True)
        (args.output_dir / "matrix-summary.json").write_text(
            json.dumps(summary, indent=2) + "\n", encoding="utf-8"
        )
    return 0 if all(results.values()) else 1


if __name__ == "__main__":
    raise SystemExit(main())
