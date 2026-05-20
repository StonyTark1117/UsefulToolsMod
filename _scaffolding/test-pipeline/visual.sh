#!/usr/bin/env bash
# Visual harness. Launches the dev client, runs each step script, screenshots,
# diffs against expected/, and treats a dead JVM as failure.
#
# Requires (on host):  ydotool ydotoold grim imagemagick jq hyprctl
# ydotoold must be running:  systemctl --user enable --now ydotool.service
set -euo pipefail

cd "$(dirname "$(readlink -f "$0")")"   # test-pipeline/

OUT=out
mkdir -p "$OUT"
: > "$OUT/visual.log"
exec > >(tee -a "$OUT/visual.log") 2>&1

# ---- preflight ----------------------------------------------------------
for bin in ydotool grim jq hyprctl magick; do
    command -v "$bin" >/dev/null || { echo "missing: $bin"; exit 2; }
done
pgrep -x ydotoold >/dev/null || { echo "ydotoold not running (systemctl --user start ydotool)"; exit 2; }
[[ -n "${HYPRLAND_INSTANCE_SIGNATURE:-}" ]] || { echo "not in a Hyprland session"; exit 2; }

# Per-scenario diff threshold (pixels different at 5% fuzz). Tunable.
: "${DIFF_THRESHOLD:=8000}"

# ---- clean state --------------------------------------------------------
# Step 01 assumes a fresh main menu (no last-played world). Wipe both possible
# loom/neogradle save dirs so the bootstrap is deterministic every run.
rm -rf ../runs/client/saves ../run/saves 2>/dev/null || true

# ---- launch client ------------------------------------------------------
echo "launching dev client…"
( cd .. && ./gradlew --no-daemon runClient ) >"$OUT/client.log" 2>&1 &
GRADLE_PID=$!
trap 'echo "stopping client"; pkill -P $GRADLE_PID 2>/dev/null || true; kill $GRADLE_PID 2>/dev/null || true' EXIT

# Wait for the Minecraft window. Match by title since class is "Minecraft*".
MC_ADDR=""
for i in $(seq 1 180); do
    MC_ADDR=$(hyprctl clients -j 2>/dev/null \
        | jq -r '.[] | select(.title|test("Minecraft"; "i")) | .address' \
        | head -1)
    [[ -n "$MC_ADDR" ]] && break
    sleep 1
done
[[ -n "$MC_ADDR" ]] || { echo "client window never appeared"; exit 1; }
echo "client window: $MC_ADDR"
hyprctl dispatch focuswindow "address:$MC_ADDR" >/dev/null
sleep 4

# ---- helpers exported to step scripts -----------------------------------
export MC_ADDR
export YDOTOOL_SOCKET="${YDOTOOL_SOCKET:-/tmp/.ydotool_socket}"

# Wrap key bursts so we don't have to repeat the press/release pattern
mc_key()   { ydotool key --key-delay 30 "$@"; }
mc_type()  { ydotool type --key-delay 15 -- "$1"; }
mc_click() { ydotool click "${1:-0xC0}"; }   # 0xC0 left, 0xC1 right
mc_chat()  { mc_key 20:1 20:0; sleep 0.3; mc_type "$1"; sleep 0.2; mc_key 28:1 28:0; sleep 0.3; }
export -f mc_key mc_type mc_click mc_chat

alive() {
    kill -0 "$GRADLE_PID" 2>/dev/null \
        && hyprctl clients -j | jq -e --arg a "$MC_ADDR" '.[]|select(.address==$a)' >/dev/null
}

# ---- run scenarios ------------------------------------------------------
FAILED=()
shopt -s nullglob
for step in steps/*.sh; do
    name=$(basename "$step" .sh)
    echo "---- $name ----"
    if ! bash "$step"; then
        echo "  step exited non-zero"
        FAILED+=("$name:script")
        continue
    fi
    sleep 1

    if ! alive; then
        echo "  CLIENT CRASHED during $name"
        FAILED+=("$name:crash")
        break   # nothing more we can run
    fi

    grim -t png "$OUT/${name}.png"

    if [[ -f "expected/${name}.png" ]]; then
        diff_px=$(magick compare -metric AE -fuzz 5% \
            "expected/${name}.png" "$OUT/${name}.png" \
            "$OUT/${name}.diff.png" 2>&1 || true)
        diff_px=${diff_px%%[!0-9]*}; diff_px=${diff_px:-99999999}
        echo "  diff: ${diff_px}px (threshold $DIFF_THRESHOLD)"
        if (( diff_px > DIFF_THRESHOLD )); then
            FAILED+=("$name:visual")
        fi
    else
        echo "  no baseline; saved $OUT/${name}.png — review and copy to expected/ if good"
    fi
done

# ---- shutdown -----------------------------------------------------------
if alive; then
    mc_key 1:1 1:0   # ESC
    sleep 0.5
fi

if (( ${#FAILED[@]} > 0 )); then
    printf 'VISUAL FAIL: %s\n' "${FAILED[@]}"
    exit 1
fi
echo "VISUAL PASS"
