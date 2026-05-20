#!/usr/bin/env bash
# Orchestrator: phase 1 = headless GameTest, phase 2 = visual (ydotool+grim).
# Phase 2 only runs if phase 1 exits zero.
set -euo pipefail

cd "$(dirname "$(readlink -f "$0")")/.."   # project root

OUT=test-pipeline/out
mkdir -p "$OUT"

if grep -q 'fabric-loom' build.gradle; then
    GAMETEST_TASK="runGametest"
    LOADER="fabric"
else
    GAMETEST_TASK="runGameTestServer"
    LOADER="neoforge"
fi

echo "==> [1/2] GameTest ($LOADER)"
if ! ./gradlew --no-daemon "$GAMETEST_TASK" 2>&1 | tee "$OUT/gametest.log"; then
    echo "GAMETEST FAILED — skipping visual phase"
    exit 1
fi

# NeoForge writes build/gametest-report.xml; Fabric same when configured.
if [[ -f build/gametest-report.xml ]]; then
    cp build/gametest-report.xml "$OUT/gametest-report.xml"
    if grep -q '<failure\|<error' "$OUT/gametest-report.xml"; then
        echo "GAMETEST REPORT contains failures"
        exit 1
    fi
fi

echo "==> [2/2] Visual"
exec test-pipeline/visual.sh
