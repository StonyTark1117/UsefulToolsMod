#!/usr/bin/env bash
set -euo pipefail

repo_root="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
only_target="${1:-}"

run_build() {
    local target="$1" directory="$2" java_home="$3"
    if [[ -n "$only_target" && "$only_target" != "$target" ]]; then return; fi
    echo "==> $target"
    # ForgeGradle 7's nonstandard build/sourceSets output is not restored
    # correctly by Gradle's shared build cache: compileJava can report
    # FROM-CACHE while producing a resource-only jar. Force real compilation so
    # a green matrix always proves that the packaged classes exist.
    local gradle_args=(--no-daemon --max-workers=1 --no-build-cache clean build)
    # RFG compiles patched upstream 1.7.10 sources that emit warnings outside
    # this project. Every modern compilation unit is held to the strict gate.
    if [[ "$target" != "1.7.10-forge" ]]; then
        gradle_args+=(-I "$repo_root/scripts/strict-warnings.gradle")
    fi
    (cd "$repo_root/$directory" && JAVA_HOME="$java_home" ./gradlew "${gradle_args[@]}")
}

run_build "1.7.10-forge"   "1.7.10"                 "/usr/lib/jvm/java-21-openjdk"
run_build "1.20.1-forge"   "1.20.1/forge"           "/usr/lib/jvm/java-17-openjdk"
run_build "1.20.1-fabric"  "1.20.1/fabric"          "/usr/lib/jvm/java-21-openjdk"
run_build "1.20.1-neoforge" "1.20.1/neoforge"        "/usr/lib/jvm/java-17-openjdk"
run_build "1.20.2-forge"   "1.20.2/forge"           "/usr/lib/jvm/java-17-openjdk"
run_build "1.20.2-fabric"  "1.20.2/fabric"          "/usr/lib/jvm/java-21-openjdk"
run_build "1.20.2-neoforge" "1.20.2/neoforge"        "/usr/lib/jvm/java-17-openjdk"
run_build "1.21.1-common"  "1.21.1"                 "/usr/lib/jvm/java-21-openjdk"
run_build "1.21.1-forge"   "1.21.1/forge-standalone" "/usr/lib/jvm/java-21-openjdk"
run_build "26.1.2-forge"   "26.1.2/forge"           "/usr/lib/jvm/java-21-openjdk"
run_build "26.1.2-fabric"  "26.1.2/fabric"          "/usr/lib/jvm/java-26-openjdk"
run_build "26.1.2-neoforge" "26.1.2/neoforge"        "/usr/lib/jvm/java-21-openjdk"
run_build "26.2-forge"     "26.2/forge"             "/usr/lib/jvm/java-21-openjdk"
run_build "26.2-fabric"    "26.2/fabric"            "/usr/lib/jvm/java-26-openjdk"
run_build "26.2-neoforge"  "26.2/neoforge"          "/usr/lib/jvm/java-21-openjdk"

"$repo_root/scripts/audit_catalog.py"
