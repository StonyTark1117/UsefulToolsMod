package com.stonytark.usefultoolsmod.gametest;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.block.entity.SpectralInfuserBlockEntity;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.event.ModEvents;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;

public final class UsefulToolsGameTests {
    private static final String TEMPLATE = "fabric-gametest-api-v1:empty";

    @GameTest(structure = TEMPLATE, maxTicks = 100)
    public void registrationsAndRecipeData(GameTestHelper helper) {
        long blocks = BuiltInRegistries.BLOCK.keySet().stream()
                .filter(id -> UsefultoolsMod.MOD_ID.equals(id.getNamespace())).count();
        long items = BuiltInRegistries.ITEM.keySet().stream()
                .filter(id -> UsefultoolsMod.MOD_ID.equals(id.getNamespace())).count();
        long entities = BuiltInRegistries.ENTITY_TYPE.keySet().stream()
                .filter(id -> UsefultoolsMod.MOD_ID.equals(id.getNamespace())).count();
        helper.assertValueEqual(blocks, 24L, "registered block count");
        helper.assertValueEqual(items, 662L, "registered item count including block items");
        helper.assertValueEqual(entities, 4L, "registered entity count");
        helper.assertTrue(helper.getLevel().getServer().getRecipeManager().byKey(
                ResourceKey.create(Registries.RECIPE,
                        Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, "spectral_infuser"))).isPresent(),
                "spectral_infuser recipe must load");
        helper.succeed();
    }

    @GameTest(structure = TEMPLATE, maxTicks = 100)
    public void spectralInfuserCreatesBlockEntity(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.SPECTRAL_INFUSER);
        helper.assertBlockPresent(ModBlocks.SPECTRAL_INFUSER, pos);
        helper.getBlockEntity(pos, SpectralInfuserBlockEntity.class);
        helper.succeed();
    }

    @GameTest(structure = TEMPLATE, maxTicks = 100)
    public void entitiesInstantiate(GameTestHelper helper) {
        helper.spawnWithNoFreeWill(ModEntities.GHOST, new BlockPos(1, 1, 1));
        helper.spawnWithNoFreeWill(ModEntities.WRAITH, new BlockPos(3, 1, 1));
        helper.spawn(ModEntities.GRENADE, new BlockPos(2, 1, 1));
        helper.assertEntityPresent(ModEntities.GHOST);
        helper.assertEntityPresent(ModEntities.WRAITH);
        helper.assertEntityPresent(ModEntities.GRENADE);
        helper.succeed();
    }

    @SuppressWarnings("deprecation")
    @GameTest(structure = TEMPLATE, maxTicks = 100)
    public void spectralBlocksAndChargeContracts(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.ECTOPLASM_LANTERN);
        var lanternState = helper.getLevel().getBlockState(helper.absolutePos(pos));
        helper.assertValueEqual(lanternState.getLightEmission(), 12, "Ectoplasm Lantern light level");
        helper.assertFalse(lanternState.getCollisionShape(helper.getLevel(), helper.absolutePos(pos)).isEmpty(),
                "Ectoplasm Lantern must retain bounded collision");
        var warded = (com.stonytark.usefultoolsmod.entity.custom.WraithEntity)
                helper.spawnWithNoFreeWill(ModEntities.WRAITH, new BlockPos(2, 1, 1));
        helper.assertTrue(com.stonytark.usefultoolsmod.block.custom.SoulLanternWard.activeWithin(warded, 12),
                "active Ectoplasm Lantern must suppress a nearby Wraith");
        helper.setBlock(new BlockPos(1, 1, 2), net.minecraft.world.level.block.Blocks.REDSTONE_BLOCK);
        helper.assertFalse(com.stonytark.usefultoolsmod.block.custom.SoulLanternWard.activeWithin(warded, 12),
                "redstone-powered Ectoplasm Lantern must not suppress Wraiths");
        warded.discard();
        for (net.minecraft.core.Direction direction : net.minecraft.core.Direction.values()) {
            var state = ModBlocks.MINING_CHARGE.defaultBlockState()
                    .setValue(com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock.FACING, direction)
                    .setValue(com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock.LIT, false);
            helper.setBlock(pos, state);
            var placed = helper.getLevel().getBlockState(helper.absolutePos(pos));
            helper.assertValueEqual(placed.getValue(
                    com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock.FACING), direction,
                    "Mining Charge facing");
            helper.assertFalse(placed.getCollisionShape(helper.getLevel(), helper.absolutePos(pos)).isEmpty(),
                    "Mining Charge must retain directional collision");
            helper.getBlockEntity(pos,
                    com.stonytark.usefultoolsmod.block.entity.MiningChargeBlockEntity.class);
        }
        helper.setBlock(pos, ModBlocks.MINING_CHARGE.defaultBlockState());
        var charge = helper.getBlockEntity(pos,
                com.stonytark.usefultoolsmod.block.entity.MiningChargeBlockEntity.class);
        var owner = java.util.UUID.randomUUID();
        helper.assertTrue(charge.linkOwner(owner, 3), "owner must be able to link Mining Charge");
        helper.assertFalse(charge.detonateOwner(owner, 2), "wrong remote channel must not detonate Mining Charge");
        charge.primeOwner(owner);
        helper.assertTrue(helper.getLevel().getBlockState(helper.absolutePos(pos)).getValue(
                com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock.LIT),
                "primed Mining Charge must enter its lit state");
        helper.assertValueEqual(charge.owner(), owner, "Mining Charge owner must persist in memory");
        helper.assertValueEqual(charge.channel(), 3, "Mining Charge channel must persist in memory");
        helper.assertTrue(charge.detonateOwner(owner, 3), "matching owner/channel must arm remote detonation");
        helper.succeed();
    }

    @GameTest(structure = TEMPLATE, maxTicks = 260)
    public void spectralInfuserProcesses(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.SPECTRAL_INFUSER);
        SpectralInfuserBlockEntity infuser = helper.getBlockEntity(pos, SpectralInfuserBlockEntity.class);
        infuser.setItem(0, new ItemStack(Items.IRON_PICKAXE));
        infuser.setItem(1, new ItemStack(ModItems.CONDENSED_ECTOPLASM));
        helper.runAfterDelay(230, () -> {
            helper.assertTrue(infuser.getItem(0).isEmpty(), "input must be consumed");
            helper.assertTrue(infuser.getItem(1).isEmpty(), "one condensed ectoplasm must be consumed");
            helper.assertValueEqual(infuser.getData().get(2), 7,
                    "condensed ectoplasm must retain seven fuel uses");
            helper.assertTrue(infuser.getItem(2).is(Items.IRON_PICKAXE), "tool must reach output");
            helper.assertTrue(EctoplasmInfusionHelper.isInfused(infuser.getItem(2)), "output must be infused");
            infuser.setItem(2, ItemStack.EMPTY);
            infuser.setItem(0, new ItemStack(Items.IRON_PICKAXE));
            infuser.setItem(1, new ItemStack(ModItems.ECTOPLASM));
            boolean old = Config.spectralInfuserEnabled;
            try {
                Config.spectralInfuserEnabled = false;
                SpectralInfuserBlockEntity.tick(helper.getLevel(), helper.absolutePos(pos),
                        helper.getLevel().getBlockState(helper.absolutePos(pos)), infuser);
            } finally {
                Config.spectralInfuserEnabled = old;
            }
            helper.assertValueEqual(infuser.getData().get(0), 0, "disabled Infuser must not progress");
            helper.assertTrue(infuser.getItem(0).is(Items.IRON_PICKAXE), "disabled Infuser must retain input");
            helper.assertTrue(infuser.getItem(1).is(ModItems.ECTOPLASM), "disabled Infuser must retain fuel");
            helper.succeed();
        });
    }

    @GameTest(structure = TEMPLATE, maxTicks = 100)
    public void ghostAndDamageContracts(GameTestHelper helper) {
        GhostEntity ghost = (GhostEntity) helper.spawnWithNoFreeWill(ModEntities.GHOST, new BlockPos(1, 1, 1));
        var wraith = (com.stonytark.usefultoolsmod.entity.custom.WraithEntity) helper.spawnWithNoFreeWill(ModEntities.WRAITH, new BlockPos(3, 1, 1));
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        var source = helper.getLevel().damageSources().playerAttack(player);
        helper.assertTrue(ghost.isInvulnerableTo(helper.getLevel(), source), "ordinary weapons must not damage ghosts");
        helper.assertTrue(wraith.isInvulnerableTo(helper.getLevel(), source), "ordinary weapons must not damage wraiths");
        ItemStack infused = new ItemStack(Items.IRON_SWORD);
        EctoplasmInfusionHelper.setInfused(infused, true);
        player.setItemInHand(InteractionHand.MAIN_HAND, infused);
        helper.assertFalse(ghost.isInvulnerableTo(helper.getLevel(), source), "infused weapons must damage ghosts");
        helper.assertFalse(wraith.isInvulnerableTo(helper.getLevel(), source), "infused weapons must damage wraiths");
        helper.assertTrue(ghost.isFood(new ItemStack(ModItems.ECTOPLASM)), "ectoplasm must breed ghosts");
        helper.assertTrue(ghost.getBreedOffspring(helper.getLevel(), ghost) instanceof GhostEntity,
                "ghost breeding must create a ghost");

        boolean oldSweetBerry = Config.sweetBerryEnabled;
        boolean oldGhost = Config.ghostEnabled;
        boolean oldWraith = Config.wraithEnabled;
        float modified;
        boolean spawnAllowed;
        boolean wraithSpawnAllowed;
        try {
            Config.sweetBerryEnabled = true;
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.SWEET_BERRY_SWORD));
            modified = ModEvents.processIncomingDamage(ghost, source, 4.0F).amount();
            Config.ghostEnabled = false;
            spawnAllowed = GhostEntity.checkGhostSpawnRules(ModEntities.GHOST, helper.getLevel(),
                    EntitySpawnReason.NATURAL, new BlockPos(1, 1, 1), RandomSource.create(1L));
            Config.wraithEnabled = false;
            wraithSpawnAllowed = com.stonytark.usefultoolsmod.entity.custom.WraithEntity.checkSpawnRules(
                    ModEntities.WRAITH, helper.getLevel(), EntitySpawnReason.NATURAL,
                    new BlockPos(1, 1, 1), RandomSource.create(1L));
        } finally {
            Config.sweetBerryEnabled = oldSweetBerry;
            Config.ghostEnabled = oldGhost;
            Config.wraithEnabled = oldWraith;
        }
        helper.assertValueEqual(modified, 5.0F, "damage mutation must run exactly once");
        helper.assertFalse(spawnAllowed, "ghost spawn kill switch must be authoritative");
        helper.assertFalse(wraithSpawnAllowed, "wraith spawn kill switch must be authoritative");
        assertWraithRuntimeContracts(helper);
    }


    @SuppressWarnings("removal")
    private static void assertWraithRuntimeContracts(GameTestHelper helper) {
        for (int y = 1; y <= 6; y++)
            helper.setBlock(new BlockPos(1, y, 1), net.minecraft.world.level.block.Blocks.AIR);
        var wraith = (com.stonytark.usefultoolsmod.entity.custom.WraithEntity)
                helper.spawn(ModEntities.WRAITH, new BlockPos(1, 1, 1));
        helper.assertTrue(wraith.isNoGravity(), "Wraith must use flight rather than Ghost gravity");
        wraith.setRemainingFireTicks(100);
        wraith.tick();
        helper.assertFalse(wraith.isOnFire(), "Wraith must clear fire every tick");
        wraith.discard();
        wraith = (com.stonytark.usefultoolsmod.entity.custom.WraithEntity)
                helper.spawn(ModEntities.WRAITH, new BlockPos(1, 1, 1));

        var lungeTarget = helper.spawn(ModEntities.GHOST, new BlockPos(1, 5, 1));
        lungeTarget.setNoGravity(true);
        wraith.setTarget(lungeTarget);
        wraith.getSensing().tick();
        helper.assertTrue(wraith.getTarget() == lungeTarget, "Wraith must retain an explicit hostile target");
        helper.assertTrue(wraith.distanceToSqr(lungeTarget) > 9.0D
                        && wraith.distanceToSqr(lungeTarget) < 144.0D,
                "Wraith test target must be inside the lunge distance window");
        helper.assertTrue(wraith.hasLineOfSight(lungeTarget), "Wraith test target must be visible");
        wraith.setDeltaMovement(net.minecraft.world.phys.Vec3.ZERO);
        helper.assertTrue(wraith.tryLungeAtTarget(),
                "Wraith lunge contract must activate for a visible supported target");
        helper.assertTrue(wraith.getDeltaMovement().lengthSqr() > 0.1D,
                "Wraith must lunge toward a visible mid-range target");

        var player = helper.makeMockServerPlayerInLevel();
        var encounter = helper.getLevel().getServer().getAdvancements().get(
                Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, "spectral/encounter_wraith"));
        helper.assertTrue(encounter != null, "Wraith encounter advancement must load");
        try {
            com.stonytark.usefultoolsmod.util.ModAdvancements.award(
                    player, "spectral/encounter_wraith");
        } catch (NullPointerException mockConnectionWithoutChannel) {
            // GameTest mock players can lack a Netty channel. Progress is mutated before sync.
        }
        helper.assertTrue(player.getAdvancements().getOrStartProgress(encounter).isDone(),
                "Wraith encounter award path must complete its advancement");

        var lootWraith = (com.stonytark.usefultoolsmod.entity.custom.WraithEntity)
                helper.spawnWithNoFreeWill(ModEntities.WRAITH, new BlockPos(2, 1, 2));
        ItemStack infused = new ItemStack(Items.IRON_SWORD);
        EctoplasmInfusionHelper.setInfused(infused, true);
        player.setItemInHand(InteractionHand.MAIN_HAND, infused);
        lootWraith.setHealth(1.0F);
        helper.assertTrue(lootWraith.hurtServer(helper.getLevel(),
                        helper.getLevel().damageSources().playerAttack(player), 5.0F),
                "infused player attack must kill a Wraith");
        helper.runAfterDelay(1, () -> {
            helper.assertItemEntityPresent(ModItems.CONDENSED_ECTOPLASM, new BlockPos(2, 1, 2), 4.0D);
            helper.assertItemEntityPresent(ModItems.ECTOPLASM, new BlockPos(2, 1, 2), 4.0D);
            helper.succeed();
        });
    }

    @GameTest(structure = TEMPLATE, maxTicks = 100)
    public void explosivesKillSwitch(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        boolean old = Config.explosivesEnabled;
        try {
            Config.explosivesEnabled = false;
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.GRENADE));
            ModItems.GRENADE.use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
            helper.assertValueEqual(player.getMainHandItem().getCount(), 1, "disabled grenade must not be consumed");
            helper.assertEntityNotPresent(ModEntities.GRENADE);
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.DYNAMITE));
            ModItems.DYNAMITE.use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
            helper.assertValueEqual(player.getMainHandItem().getCount(), 1, "disabled dynamite must not be consumed");
        } finally {
            Config.explosivesEnabled = old;
        }
        helper.succeed();
    }
}
