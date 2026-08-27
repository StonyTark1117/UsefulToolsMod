package com.stonytark.usefultoolsmod.gametest;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.block.entity.SpectralInfuserBlockEntity;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.event.DamageModifier;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public final class UsefulToolsGameTests implements FabricGameTest {
    private static final String TEMPLATE = FabricGameTest.EMPTY_STRUCTURE;

    @GameTest(templateName = TEMPLATE, tickLimit = 100)
    public void registrationsAndRecipeData(TestContext context) {
        long blocks = Registries.BLOCK.getIds().stream().filter(UsefulToolsGameTests::isOurs).count();
        long items = Registries.ITEM.getIds().stream().filter(UsefulToolsGameTests::isOurs).count();
        long entities = Registries.ENTITY_TYPE.getIds().stream().filter(UsefulToolsGameTests::isOurs).count();
        context.assertTrue(blocks == 24L, "registered block count");
        context.assertTrue(items == 662L, "registered item count including block items");
        context.assertTrue(entities == 4L, "registered entity count");
        context.assertTrue(context.getWorld().getServer().getRecipeManager()
                .get(new Identifier(UsefultoolsMod.MOD_ID, "spectral_infuser")).isPresent(),
                "spectral_infuser recipe must load");
        context.complete();
    }

    @GameTest(templateName = TEMPLATE, tickLimit = 100)
    public void spectralInfuserCreatesBlockEntity(TestContext context) {
        BlockPos pos = new BlockPos(1, 1, 1);
        context.setBlockState(pos, ModBlocks.SPECTRAL_INFUSER);
        context.expectBlock(ModBlocks.SPECTRAL_INFUSER, pos);
        context.assertTrue(context.getBlockEntity(pos) instanceof SpectralInfuserBlockEntity,
                "spectral infuser block entity must be created");
        context.complete();
    }

    @GameTest(templateName = TEMPLATE, tickLimit = 100)
    public void entitiesInstantiate(TestContext context) {
        context.spawnMob(ModEntities.GHOST, new BlockPos(1, 1, 1));
        context.spawnMob(ModEntities.WRAITH, new BlockPos(3, 1, 1));
        context.spawnEntity(ModEntities.GRENADE, new BlockPos(2, 1, 1));
        context.expectEntity(ModEntities.GHOST);
        context.expectEntity(ModEntities.WRAITH);
        context.expectEntity(ModEntities.GRENADE);
        context.complete();
    }

    @GameTest(templateName = TEMPLATE, tickLimit = 100)
    public void spectralBlocksAndChargeContracts(TestContext context) {
        BlockPos pos = new BlockPos(1, 1, 1);
        context.setBlockState(pos, ModBlocks.ECTOPLASM_LANTERN.getDefaultState());
        var absolute = context.getAbsolutePos(pos);
        var lanternState = context.getWorld().getBlockState(absolute);
        context.assertTrue(lanternState.getLuminance() == 12, "Ectoplasm Lantern light level");
        context.assertFalse(lanternState.getCollisionShape(context.getWorld(), absolute).isEmpty(),
                "Ectoplasm Lantern must retain bounded collision");
        var warded = context.spawnMob(ModEntities.WRAITH, new BlockPos(2, 1, 1));
        context.assertTrue(com.stonytark.usefultoolsmod.block.custom.SoulLanternBlock.isStabilized(warded),
                "active Ectoplasm Lantern must suppress a nearby Wraith");
        context.setBlockState(new BlockPos(1, 1, 2), net.minecraft.block.Blocks.REDSTONE_BLOCK.getDefaultState());
        context.assertFalse(com.stonytark.usefultoolsmod.block.custom.SoulLanternBlock.isStabilized(warded),
                "redstone-powered Ectoplasm Lantern must not suppress Wraiths");
        warded.discard();
        for (net.minecraft.util.math.Direction direction : net.minecraft.util.math.Direction.values()) {
            var state = ModBlocks.MINING_CHARGE.getDefaultState()
                    .with(com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock.FACING, direction)
                    .with(com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock.LIT, false);
            context.setBlockState(pos, state);
            var placed = context.getWorld().getBlockState(absolute);
            context.assertTrue(placed.get(com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock.FACING)
                    == direction, "Mining Charge facing");
            context.assertFalse(placed.getCollisionShape(context.getWorld(), absolute).isEmpty(),
                    "Mining Charge must retain directional collision");
            context.assertTrue(context.getBlockEntity(pos) instanceof
                    com.stonytark.usefultoolsmod.block.entity.MiningChargeBlockEntity,
                    "Mining Charge block entity must be created");
        }
        context.setBlockState(pos, ModBlocks.MINING_CHARGE.getDefaultState());
        var charge = (com.stonytark.usefultoolsmod.block.entity.MiningChargeBlockEntity)
                context.getBlockEntity(pos);
        var owner = java.util.UUID.randomUUID();
        context.assertTrue(charge.linkOwner(owner, 3), "owner must be able to link Mining Charge");
        context.assertFalse(charge.detonateOwner(owner, 2), "wrong remote channel must not detonate Mining Charge");
        charge.primeOwner(owner);
        context.assertTrue(context.getWorld().getBlockState(absolute).get(
                com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock.LIT),
                "primed Mining Charge must enter its lit state");
        context.assertTrue(owner.equals(charge.owner()), "Mining Charge owner must persist in memory");
        context.assertTrue(charge.channel() == 3, "Mining Charge channel must persist in memory");
        context.assertTrue(charge.detonateOwner(owner, 3), "matching owner/channel must arm remote detonation");
        context.complete();
    }

    @GameTest(templateName = TEMPLATE, tickLimit = 260)
    public void spectralInfuserProcesses(TestContext context) {
        BlockPos pos = new BlockPos(1, 1, 1);
        context.setBlockState(pos, ModBlocks.SPECTRAL_INFUSER);
        SpectralInfuserBlockEntity infuser = (SpectralInfuserBlockEntity) context.getBlockEntity(pos);
        infuser.getInventory().set(0, new ItemStack(Items.IRON_PICKAXE));
        infuser.getInventory().set(1, new ItemStack(ModItems.CONDENSED_ECTOPLASM));
        context.runAtTick(230, () -> {
            context.assertTrue(infuser.getInventory().get(0).isEmpty(), "input must be consumed");
            context.assertTrue(infuser.getInventory().get(1).isEmpty(), "one condensed ectoplasm must be consumed");
            context.assertTrue(infuser.getPropertyDelegate().get(2) == 7,
                    "condensed ectoplasm must retain seven fuel uses");
            context.assertTrue(infuser.getInventory().get(2).isOf(Items.IRON_PICKAXE), "tool must reach output");
            context.assertTrue(EctoplasmInfusionHelper.isInfused(infuser.getInventory().get(2)), "output must be infused");
            infuser.getInventory().set(2, ItemStack.EMPTY);
            infuser.getInventory().set(0, new ItemStack(Items.IRON_PICKAXE));
            infuser.getInventory().set(1, new ItemStack(ModItems.ECTOPLASM));
            boolean old = Config.spectralInfuserEnabled;
            try {
                Config.spectralInfuserEnabled = false;
                BlockPos absolute = context.getAbsolutePos(pos);
                SpectralInfuserBlockEntity.tick(context.getWorld(), absolute,
                        context.getWorld().getBlockState(absolute), infuser);
            } finally {
                Config.spectralInfuserEnabled = old;
            }
            context.assertTrue(infuser.getPropertyDelegate().get(0) == 0, "disabled Infuser must not progress");
            context.assertTrue(infuser.getInventory().get(0).isOf(Items.IRON_PICKAXE), "disabled Infuser must retain input");
            context.assertTrue(infuser.getInventory().get(1).isOf(ModItems.ECTOPLASM), "disabled Infuser must retain fuel");
            context.complete();
        });
    }

    @GameTest(templateName = TEMPLATE, tickLimit = 100)
    public void ghostAndDamageContracts(TestContext context) {
        GhostEntity ghost = context.spawnMob(ModEntities.GHOST, new BlockPos(1, 1, 1));
        var wraith = context.spawnMob(ModEntities.WRAITH, new BlockPos(3, 1, 1));
        PlayerEntity player = context.createMockSurvivalPlayer();
        DamageSource source = context.getWorld().getDamageSources().playerAttack(player);
        context.assertTrue(ghost.isInvulnerableTo(source), "ordinary weapons must not damage ghosts");
        context.assertTrue(wraith.isInvulnerableTo(source), "ordinary weapons must not damage wraiths");
        ItemStack infused = new ItemStack(Items.IRON_SWORD);
        EctoplasmInfusionHelper.setInfused(infused, true);
        player.setStackInHand(Hand.MAIN_HAND, infused);
        context.assertFalse(ghost.isInvulnerableTo(source), "infused weapons must damage ghosts");
        context.assertFalse(wraith.isInvulnerableTo(source), "infused weapons must damage wraiths");
        context.assertTrue(ghost.isBreedingItem(new ItemStack(ModItems.ECTOPLASM)), "ectoplasm must breed ghosts");
        context.assertTrue(ghost.createChild(context.getWorld(), ghost) instanceof GhostEntity,
                "ghost breeding must create a ghost");
        boolean oldSweetBerry = Config.sweetBerryEnabled;
        boolean oldGhost = Config.ghostEnabled;
        boolean oldWraith = Config.wraithEnabled;
        float modified;
        boolean spawnAllowed;
        boolean wraithSpawnAllowed;
        try {
            Config.sweetBerryEnabled = true;
            player.setStackInHand(Hand.MAIN_HAND, new ItemStack(ModItems.SWEET_BERRY_SWORD));
            modified = DamageModifier.compute(ghost, source, 4.0F);
            Config.ghostEnabled = false;
            spawnAllowed = GhostEntity.checkGhostSpawnRules(ModEntities.GHOST, context.getWorld(),
                    SpawnReason.NATURAL, new BlockPos(1, 1, 1), Random.create(1L));
            Config.wraithEnabled = false;
            wraithSpawnAllowed = com.stonytark.usefultoolsmod.entity.custom.WraithEntity.checkSpawnRules(
                    ModEntities.WRAITH, context.getWorld(), SpawnReason.NATURAL,
                    new BlockPos(1, 1, 1), Random.create(1L));
        } finally {
            Config.sweetBerryEnabled = oldSweetBerry;
            Config.ghostEnabled = oldGhost;
            Config.wraithEnabled = oldWraith;
        }
        context.assertTrue(modified == 5.0F, "damage mutation must run exactly once");
        context.assertFalse(spawnAllowed, "ghost spawn kill switch must be authoritative");
        context.assertFalse(wraithSpawnAllowed, "wraith spawn kill switch must be authoritative");
        assertWraithRuntimeContracts(context);
    }


    @SuppressWarnings("removal")
    private static void assertWraithRuntimeContracts(TestContext context) {
        for (int y = 1; y <= 6; y++)
            context.setBlockState(new BlockPos(1, y, 1), net.minecraft.block.Blocks.AIR.getDefaultState());
        var wraith = context.spawnMob(ModEntities.WRAITH, new BlockPos(1, 1, 1));
        context.assertTrue(wraith.hasNoGravity(), "Wraith must use flight rather than Ghost gravity");
        wraith.setFireTicks(100);
        wraith.tick();
        context.assertFalse(wraith.isOnFire(), "Wraith must clear fire every tick");
        wraith.discard();
        wraith = context.spawnMob(ModEntities.WRAITH, new BlockPos(1, 1, 1));

        var lungeTarget = context.spawnMob(ModEntities.GHOST, new BlockPos(1, 5, 1));
        lungeTarget.setNoGravity(true);
        wraith.setTarget(lungeTarget);
        wraith.getVisibilityCache().clear();
        context.assertTrue(wraith.getTarget() == lungeTarget, "Wraith must retain an explicit hostile target");
        context.assertTrue(wraith.squaredDistanceTo(lungeTarget) > 9.0D
                        && wraith.squaredDistanceTo(lungeTarget) < 144.0D,
                "Wraith test target must be inside the lunge distance window");
        context.assertTrue(wraith.canSee(lungeTarget), "Wraith test target must be visible");
        wraith.setVelocity(net.minecraft.util.math.Vec3d.ZERO);
        context.assertTrue(wraith.tryLungeAtTarget(),
                "Wraith lunge contract must activate for a visible supported target");
        context.assertTrue(wraith.getVelocity().lengthSquared() > 0.1D,
                "Wraith must lunge toward a visible mid-range target");

        var player = context.createMockCreativeServerPlayerInWorld();
        var encounter = player.server.getAdvancementLoader().get(
                new Identifier(UsefultoolsMod.MOD_ID, "spectral/encounter_wraith"));
        context.assertTrue(encounter != null, "Wraith encounter advancement must load");
        try {
            com.stonytark.usefultoolsmod.util.ModAdvancements.award(
                    player, "spectral/encounter_wraith");
        } catch (NullPointerException mockConnectionWithoutChannel) {
            // GameTest mock players can lack a Netty channel. Progress is mutated before sync.
        }
        context.assertTrue(player.getAdvancementTracker().getProgress(encounter).isDone(),
                "Wraith encounter award path must complete its advancement");

        var lootWraith = context.spawnMob(ModEntities.WRAITH, new BlockPos(2, 1, 2));
        ItemStack infused = new ItemStack(Items.IRON_SWORD);
        EctoplasmInfusionHelper.setInfused(infused, true);
        player.setStackInHand(Hand.MAIN_HAND, infused);
        lootWraith.setHealth(1.0F);
        context.assertTrue(lootWraith.damage(context.getWorld().getDamageSources().playerAttack(player), 5.0F),
                "infused player attack must kill a Wraith");
        context.runAtTick(1, () -> {
            context.expectItemAt(ModItems.CONDENSED_ECTOPLASM, new BlockPos(2, 1, 2), 4.0D);
            context.expectItemAt(ModItems.ECTOPLASM, new BlockPos(2, 1, 2), 4.0D);
            context.complete();
        });
    }

    @GameTest(templateName = TEMPLATE, tickLimit = 100)
    public void explosivesKillSwitch(TestContext context) {
        PlayerEntity player = context.createMockSurvivalPlayer();
        boolean old = Config.explosivesEnabled;
        try {
            Config.explosivesEnabled = false;
            player.setStackInHand(Hand.MAIN_HAND, new ItemStack(ModItems.GRENADE));
            ModItems.GRENADE.use(context.getWorld(), player, Hand.MAIN_HAND);
            context.assertTrue(player.getMainHandStack().getCount() == 1, "disabled grenade must not be consumed");
            context.dontExpectEntity(ModEntities.GRENADE);
            player.setStackInHand(Hand.MAIN_HAND, new ItemStack(ModItems.DYNAMITE));
            ModItems.DYNAMITE.use(context.getWorld(), player, Hand.MAIN_HAND);
            context.assertTrue(player.getMainHandStack().getCount() == 1, "disabled dynamite must not be consumed");
        } finally {
            Config.explosivesEnabled = old;
        }
        context.complete();
    }

    private static boolean isOurs(Identifier id) {
        return UsefultoolsMod.MOD_ID.equals(id.getNamespace());
    }
}
