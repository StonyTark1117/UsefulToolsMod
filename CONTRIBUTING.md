# Contributing

Useful Tools is a multi-version, multi-loader project. Preserve registry IDs and user configuration keys; compatibility changes need an explicit migration entry.

Before opening a change:

1. Run `scripts/audit_catalog.py` after changing registrations or config fields.
2. Build the affected target with `scripts/build-matrix.sh <minecraft>-<loader>`. All three 1.21.1 loaders are independent native projects.
3. Check the binary and source jars in the target's `build/libs` directory.
4. Run `scripts/audit-datagen.py <minecraft>-<loader>` for modern data/provider changes; generated resources must remain stable.
5. Exercise the affected dedicated server with `scripts/smoke-server.py <minecraft>-<loader>` and inspect its JSON result; a readiness line alone is not a clean-shutdown pass.
6. For release work, run the complete matrix and then `scripts/assemble-release.py`.

Do not commit Gradle caches, run directories, IDE state, generated crash reports, or release binaries. Do not change a published identifier merely to make naming more consistent.
