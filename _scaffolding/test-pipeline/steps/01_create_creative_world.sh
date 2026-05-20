#!/usr/bin/env bash
# Bootstrap: from main menu -> create a creative world with cheats.
# Assumes a fresh dev run (no last-played world). If you already have a saved
# world named "gametest-visual", switch to a `selectWorld` flow instead.
set -euo pipefail

# main menu -> Singleplayer
sleep 2
mc_key 28:1 28:0   # Enter (Singleplayer is the first focused button)
sleep 2

# "Create New World"
mc_key 28:1 28:0
sleep 2

# World name field is focused. Clear, type, advance.
mc_key 29:1 30:1 30:0 29:0          # Ctrl+A
mc_key 14:1 14:0                    # Backspace
mc_type "gametest-visual"
sleep 0.5

# Tab to "Game Mode" cycler, set to Creative (3 clicks: Survival -> Hardcore -> Creative)
# Easier: tab to "Allow Cheats: ON" first, then game mode. Order is layout-dependent
# across MC builds; the safest path is to use the world-creation tabs by mouse.
# Switch to the More tab via keyboard: hold Ctrl+Tab? No — use Tab through buttons.
# Pragmatic shortcut: just create with defaults (survival) then /gamemode creative + cheats
# after world load (cheats can be enabled mid-world via Open to LAN in singleplayer).
mc_key 15:1 15:0   # Tab to "Create New World" button (cycles past everything once)
sleep 0.2
mc_key 28:1 28:0   # Enter
sleep 8            # world generation

# Open to LAN -> enable cheats -> creative
mc_key 1:1 1:0     # Esc to pause
sleep 0.5
# "Open to LAN" is below "Save and Quit". Use Tab navigation or click via known coords.
# Simpler: use chat-side-effects only available with cheats. If cheats aren't on,
# fall back to opening to LAN via mouse — encoded in step 02 as a precondition check.
# For now, just exit pause; we'll attempt /gamemode and detect failure in later steps.
mc_key 1:1 1:0     # Esc back into world
sleep 1

# Try to enable creative + cheats via the dev-only cheat shortcut: in singleplayer
# Forge/NeoForge dev clients usually load with cheats enabled by default. Fabric too.
mc_chat "/gamemode creative"
sleep 0.5
