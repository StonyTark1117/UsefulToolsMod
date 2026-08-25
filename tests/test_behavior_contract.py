import json
import re
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
LOADER_ROOTS = tuple(
    ROOT / version / loader
    for version in ("1.20.1", "1.20.2", "26.1.2", "26.2")
    for loader in ("forge", "fabric", "neoforge")
)
BEHAVIOR_ROOTS = LOADER_ROOTS + (
    ROOT / "1.21.1/forge",
    ROOT / "1.21.1/fabric",
    ROOT / "1.21.1/neoforge",
)


def java_sources(root: Path, exclude=()):
    return "\n".join(
        path.read_text(encoding="utf-8")
        for path in (root / "src/main/java").rglob("*.java")
        if path.name not in exclude
    )


class BehaviorContractTests(unittest.TestCase):
    def test_every_effect_control_reaches_behavior_code(self):
        for root in BEHAVIOR_ROOTS:
            with self.subTest(root=root.relative_to(ROOT)):
                config = (root / "src/main/java/com/stonytark/usefultoolsmod/Config.java").read_text(
                    encoding="utf-8"
                )
                controls = [
                    key
                    for key in re.findall(r"public static (?:boolean|double)\s+(\w+)\s*=", config)
                    if key.endswith(
                        ("Effects", "Avoidance", "Phasing", "Drain", "Sticky", "Thorns", "Teleport")
                    )
                ]
                behavior = java_sources(root, {"Config.java", "ConfigDescriptors.java"})
                missing = [key for key in controls if f"Config.{key}" not in behavior]
                self.assertEqual(50, len(controls))
                self.assertEqual([], missing)

    def test_120_fabric_damage_is_mutated_once(self):
        forbidden_extra_hits = (
            "target.damage(player.getDamageSources().playerAttack(player), 2.0f)",
            "target.damage(player.getDamageSources().playerAttack(player), 3.0f)",
            "target.damage(player.getDamageSources().playerAttack(player), 1.0f)",
            "target.getMaxHealth() * 0.01f",
        )
        for version in ("1.20.1", "1.20.2"):
            root = ROOT / version / "fabric"
            with self.subTest(version=version):
                event_source = (
                    root / "src/main/java/com/stonytark/usefultoolsmod/event/ModEvents.java"
                ).read_text(encoding="utf-8")
                modifier = (
                    root / "src/main/java/com/stonytark/usefultoolsmod/event/DamageModifier.java"
                ).read_text(encoding="utf-8")
                mixin = (
                    root / "src/main/java/com/stonytark/usefultoolsmod/mixin/LivingEntityMixin.java"
                ).read_text(encoding="utf-8")
                metadata = json.loads((root / "src/main/resources/fabric.mod.json").read_text())
                mixin_config = json.loads(
                    (root / "src/main/resources/usefultoolsmod.mixins.json").read_text()
                )

                for snippet in forbidden_extra_hits:
                    self.assertNotIn(snippet, event_source)
                for expected in ("modified += 1.0f", "modified += 2.0f", "modified += 3.0f",
                                 "modified *= 1.3f", "modified *= 1.5f"):
                    self.assertIn(expected, modifier)
                self.assertEqual(1, mixin.count("DamageModifier.compute"))
                self.assertEqual(["usefultoolsmod.mixins.json"], metadata["mixins"])
                self.assertEqual(["LivingEntityMixin", "MobMixin"], mixin_config["mixins"])

    def test_every_modern_line_has_damage_target_spawn_and_burn_bridges(self):
        for root in BEHAVIOR_ROOTS:
            with self.subTest(root=root.relative_to(ROOT)):
                source = java_sources(root)
                self.assertRegex(source, r"DamageModifier|Living(?:Hurt|IncomingDamage)|onAllowDamage")
                self.assertRegex(source, r"LivingChangeTarget|TargetPredicate|setTarget")
                self.assertIn("ghostSpawnChance", source)
                self.assertRegex(source, r"getBurnOdds|getBurnChance|getFlammability")

    def test_active_event_sources_have_no_stale_damage_placeholder(self):
        stale = re.compile(r"For now|Actually, we handle this|cannot modify damage amount directly|no-op damage", re.I)
        offenders = []
        for root in BEHAVIOR_ROOTS:
            for path in (root / "src/main/java").rglob("*.java"):
                if stale.search(path.read_text(encoding="utf-8")):
                    offenders.append(str(path.relative_to(ROOT)))
        self.assertEqual([], offenders)

    def test_high_tier_incorrect_tag_remains_comparable_for_viewers(self):
        resources = (
            *(ROOT / "1.21.1" / loader / relative
              for loader, relative in (
                  ("fabric", "src/main/generated/data/usefultoolsmod/tags/block/incorrect_sob_tool.json"),
                  ("forge", "src/generated/resources/data/usefultoolsmod/tags/block/incorrect_sob_tool.json"),
                  ("neoforge", "src/generated/resources/data/usefultoolsmod/tags/block/incorrect_sob_tool.json"),
              )),
            *(ROOT / version / loader / relative
              for version in ("26.1.2", "26.2")
              for loader, relative in (
                  ("fabric", "src/main/generated/data/usefultoolsmod/tags/block/incorrect_sob_tool.json"),
                  ("forge", "src/generated/resources/server/data/usefultoolsmod/tags/block/incorrect_sob_tool.json"),
                  ("neoforge", "src/generated/resources/server/data/usefultoolsmod/tags/block/incorrect_sob_tool.json"),
              )),
        )
        for path in resources:
            with self.subTest(path=path.relative_to(ROOT)):
                values = json.loads(path.read_text(encoding="utf-8"))["values"]
                ids = [value["id"] if isinstance(value, dict) else value for value in values]
                self.assertIn("#minecraft:incorrect_for_diamond_tool", ids)
                self.assertNotIn("minecraft:bedrock", ids)


if __name__ == "__main__":
    unittest.main()
