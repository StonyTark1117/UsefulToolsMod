#!/usr/bin/env bash
# Open inventory, search JEI for "spectral", click the result, screenshot the
# Spectral Infuser recipe page. visual.sh diffs against expected/.
set -euo pipefail

# Open inventory
mc_key 18:1 18:0   # E
sleep 1

# JEI search field is focused by default in dev. Clear then type.
mc_key 29:1 30:1 30:0 29:0   # Ctrl+A
mc_key 14:1 14:0             # Backspace
mc_type "spectral"
sleep 1

# screenshot taken by visual.sh after this script returns
