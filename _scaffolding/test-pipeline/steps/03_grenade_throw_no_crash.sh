#!/usr/bin/env bash
# Give grenade, throw 5x, wait for ticks. visual.sh's `alive` check after this
# step is what actually asserts "no crash" — this script just produces the
# events. A diff is recorded but not strict (gunpowder smoke is non-deterministic).
set -euo pipefail

# Close any open inventory from the previous step
mc_key 1:1 1:0     # Esc
sleep 0.5

mc_chat "/give @s usefultoolsmod:grenade 16"
sleep 0.5

# Hotbar slot 1
mc_key 2:1 2:0
sleep 0.3

# Look slightly up so projectiles arc into the air, not the ground
ydotool mousemove -- 0 -120
sleep 0.2

# Throw 5 grenades (right-click). On most Useful Tools impls this is `use` -> spawn entity.
for _ in 1 2 3 4 5; do
    mc_click 0xC1
    sleep 0.6
done

# Let entities tick + explode + remove. If they crash the client, alive() in
# visual.sh will catch it after this script returns.
sleep 4
