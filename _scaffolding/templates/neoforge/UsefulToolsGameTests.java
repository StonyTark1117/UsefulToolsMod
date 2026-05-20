package com.stonytark.usefultoolsmod.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.gametest.GameTestHolder;

@GameTestHolder("usefultoolsmod")
public final class UsefulToolsGameTests {

    @GameTest(template = "usefultoolsmod:empty_3x3", timeoutTicks = 200)
    public static void smoke(GameTestHelper helper) {
        helper.succeed();
    }

    @GameTest(template = "usefultoolsmod:empty_3x3", timeoutTicks = 200)
    public static void grenadeEntitySpawns(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 2, 1);
        helper.setBlock(pos.below(), Blocks.STONE);
        // TODO: spawn ModEntities.GRENADE.get() at pos, advance ticks,
        // assert entity removed and surrounding blocks unchanged (no terrain damage by default).
        helper.succeed();
    }

    // Candidate tests to add as you touch the relevant code paths:
    //   - dynamiteEntitySpawns       (parallel to grenade, the other throwable)
    //   - remeraldPickaxeMineSpeed   (verify reinforced tier dig speed > vanilla iron)
    //   - overpowerSwordDamage       (assert configured damage on a passive mob)
    //   - spectralInfuserRecipeRuns  (place block, set inputs, advance ticks, assert output)
    //   - configReadAtRuntime        (Config.SOME_VALUE.get() returns expected default)
}
