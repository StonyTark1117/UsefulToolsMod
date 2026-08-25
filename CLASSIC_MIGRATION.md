# Minecraft 1.7.10 v1.6 registry migration

The published v1.6 implementation passed each unlocalized name to Forge as its registry path. Useful Tools 2.3.0 therefore retains every exact mixed-case `item.` and `tile.` path below. It also handles prefix-free variants through `FMLMissingMappingsEvent`; these aliases remap to the retained object and never discard world data.

| Kind | Published/retained registry path | Accepted prefix-free alias | 2.3.0 status |
|---|---|---|---|
| Item | `usefultoolsmod:item.rGold` | `usefultoolsmod:rGold` | retained legacy object |
| Item | `usefultoolsmod:item.rGPick` | `usefultoolsmod:rGPick` | retained legacy object |
| Item | `usefultoolsmod:item.rGAxe` | `usefultoolsmod:rGAxe` | retained legacy object |
| Item | `usefultoolsmod:item.rGShovel` | `usefultoolsmod:rGShovel` | retained legacy object |
| Item | `usefultoolsmod:item.rGSword` | `usefultoolsmod:rGSword` | retained legacy object |
| Item | `usefultoolsmod:item.rGHoe` | `usefultoolsmod:rGHoe` | retained legacy object |
| Item | `usefultoolsmod:item.INugget` | `usefultoolsmod:INugget` | retained legacy object |
| Item | `usefultoolsmod:item.emPick` | `usefultoolsmod:emPick` | retained legacy object |
| Item | `usefultoolsmod:item.emAxe` | `usefultoolsmod:emAxe` | retained legacy object |
| Item | `usefultoolsmod:item.emShovel` | `usefultoolsmod:emShovel` | retained legacy object |
| Item | `usefultoolsmod:item.emSword` | `usefultoolsmod:emSword` | retained legacy object |
| Item | `usefultoolsmod:item.emHoe` | `usefultoolsmod:emHoe` | retained legacy object |
| Item | `usefultoolsmod:item.ObShard` | `usefultoolsmod:ObShard` | retained legacy object |
| Item | `usefultoolsmod:item.sEm` | `usefultoolsmod:sEm` | retained legacy object |
| Item | `usefultoolsmod:item.semPick` | `usefultoolsmod:semPick` | retained legacy object |
| Item | `usefultoolsmod:item.semAxe` | `usefultoolsmod:semAxe` | retained legacy object |
| Item | `usefultoolsmod:item.semShovel` | `usefultoolsmod:semShovel` | retained legacy object |
| Item | `usefultoolsmod:item.semSword` | `usefultoolsmod:semSword` | retained legacy object |
| Item | `usefultoolsmod:item.semHoe` | `usefultoolsmod:semHoe` | retained legacy object |
| Item | `usefultoolsmod:item.oPick` | `usefultoolsmod:oPick` | retained legacy object |
| Item | `usefultoolsmod:item.oAxe` | `usefultoolsmod:oAxe` | retained legacy object |
| Item | `usefultoolsmod:item.oShovel` | `usefultoolsmod:oShovel` | retained legacy object |
| Item | `usefultoolsmod:item.oSword` | `usefultoolsmod:oSword` | retained legacy object |
| Item | `usefultoolsmod:item.oHoe` | `usefultoolsmod:oHoe` | retained legacy object |
| Item | `usefultoolsmod:item.joPick` | `usefultoolsmod:joPick` | retained legacy object |
| Item | `usefultoolsmod:item.joAxe` | `usefultoolsmod:joAxe` | retained legacy object |
| Item | `usefultoolsmod:item.joShovel` | `usefultoolsmod:joShovel` | retained legacy object |
| Item | `usefultoolsmod:item.joSword` | `usefultoolsmod:joSword` | retained legacy object |
| Item | `usefultoolsmod:item.joHoe` | `usefultoolsmod:joHoe` | retained legacy object |
| Item | `usefultoolsmod:item.ObIngot` | `usefultoolsmod:ObIngot` | retained legacy object |
| Item | `usefultoolsmod:item.opsword` | `usefultoolsmod:opsword` | retained legacy object |
| Item | `usefultoolsmod:item.oppick` | `usefultoolsmod:oppick` | retained legacy object |
| Item | `usefultoolsmod:item.opaxe` | `usefultoolsmod:opaxe` | retained legacy object |
| Item | `usefultoolsmod:item.opshovel` | `usefultoolsmod:opshovel` | retained legacy object |
| Item | `usefultoolsmod:item.Dynamite` | `usefultoolsmod:Dynamite` | retained legacy object |
| Item | `usefultoolsmod:item.rPick` | `usefultoolsmod:rPick` | retained legacy object |
| Item | `usefultoolsmod:item.rAxe` | `usefultoolsmod:rAxe` | retained legacy object |
| Item | `usefultoolsmod:item.rShovel` | `usefultoolsmod:rShovel` | retained legacy object |
| Item | `usefultoolsmod:item.rSword` | `usefultoolsmod:rSword` | retained legacy object |
| Item | `usefultoolsmod:item.rHoe` | `usefultoolsmod:rHoe` | retained legacy object |
| Item | `usefultoolsmod:item.hred` | `usefultoolsmod:hred` | retained legacy object |
| Item | `usefultoolsmod:item.rghelm` | `usefultoolsmod:rghelm` | retained legacy object |
| Item | `usefultoolsmod:item.rgchest` | `usefultoolsmod:rgchest` | retained legacy object |
| Item | `usefultoolsmod:item.rgpants` | `usefultoolsmod:rgpants` | retained legacy object |
| Item | `usefultoolsmod:item.rgboots` | `usefultoolsmod:rgboots` | retained legacy object |
| Item | `usefultoolsmod:item.obhelm` | `usefultoolsmod:obhelm` | retained legacy object |
| Item | `usefultoolsmod:item.obchest` | `usefultoolsmod:obchest` | retained legacy object |
| Item | `usefultoolsmod:item.obpants` | `usefultoolsmod:obpants` | retained legacy object |
| Item | `usefultoolsmod:item.obboots` | `usefultoolsmod:obboots` | retained legacy object |
| Item | `usefultoolsmod:item.emhelm` | `usefultoolsmod:emhelm` | retained legacy object |
| Item | `usefultoolsmod:item.emchest` | `usefultoolsmod:emchest` | retained legacy object |
| Item | `usefultoolsmod:item.empants` | `usefultoolsmod:empants` | retained legacy object |
| Item | `usefultoolsmod:item.emboots` | `usefultoolsmod:emboots` | retained legacy object |
| Item | `usefultoolsmod:item.ophelm` | `usefultoolsmod:ophelm` | retained legacy object |
| Item | `usefultoolsmod:item.opchest` | `usefultoolsmod:opchest` | retained legacy object |
| Item | `usefultoolsmod:item.oppants` | `usefultoolsmod:oppants` | retained legacy object |
| Item | `usefultoolsmod:item.opboots` | `usefultoolsmod:opboots` | retained legacy object |
| Item | `usefultoolsmod:item.hrhelm` | `usefultoolsmod:hrhelm` | retained legacy object |
| Item | `usefultoolsmod:item.hrchest` | `usefultoolsmod:hrchest` | retained legacy object |
| Item | `usefultoolsmod:item.hrpants` | `usefultoolsmod:hrpants` | retained legacy object |
| Item | `usefultoolsmod:item.hrboots` | `usefultoolsmod:hrboots` | retained legacy object |
| Item | `usefultoolsmod:item.Grenade` | `usefultoolsmod:Grenade` | retained legacy object |
| Block | `usefultoolsmod:tile.rGoldBlock` | `usefultoolsmod:rGoldBlock` | retained legacy object |
| Block | `usefultoolsmod:tile.semBlock` | `usefultoolsmod:semBlock` | retained legacy object |
| Block | `usefultoolsmod:tile.sOblock` | `usefultoolsmod:sOblock` | retained legacy object |
| Block | `usefultoolsmod:tile.hrblock` | `usefultoolsmod:hrblock` | retained legacy object |
| Block | `usefultoolsmod:tile.rGoldore` | `usefultoolsmod:rGoldore` | retained legacy object |

Lowercase modern IDs are additional registrations generated from the canonical catalog; they do not replace the entries above. Release acceptance still requires loading an independently captured published-v1.6 world, not merely a world produced by this checkout.
