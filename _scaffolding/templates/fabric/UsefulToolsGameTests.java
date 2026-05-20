package com.stonytark.usefultoolsmod.gametest;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;

public final class UsefulToolsGameTests implements FabricGameTest {

    @GameTest(templateName = "usefultoolsmod:empty_3x3", timeoutTicks = 200)
    public void smoke(GameTestHelper helper) {
        helper.succeed();
    }

    @GameTest(templateName = "usefultoolsmod:empty_3x3", timeoutTicks = 200)
    public void grenadeEntitySpawns(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 2, 1);
        helper.setBlock(pos.below(), Blocks.STONE);
        // TODO: spawn ModEntities.GRENADE at pos, tick to landing, assert state.
        helper.succeed();
    }

    // Candidate tests to add as you touch the relevant code paths:
    //   - dynamiteEntitySpawns       (parallel to grenade, the other throwable)
    //   - remeraldPickaxeMineSpeed   (verify reinforced tier dig speed > vanilla iron)
    //   - overpowerSwordDamage       (assert configured damage on a passive mob)
    //   - spectralInfuserRecipeRuns  (place block, set inputs, advance ticks, assert output)
    //   - configReadAtRuntime        (Config.SOME_VALUE.get() returns expected default)
}
