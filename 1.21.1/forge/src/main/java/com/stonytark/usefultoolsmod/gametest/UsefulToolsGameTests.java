package com.stonytark.usefultoolsmod.gametest;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.block.entity.SpectralInfuserBlockEntity;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.event.ModEvents;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmArmorHelper;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

@GameTestHolder(value = UsefultoolsMod.MOD_ID, namespace = UsefultoolsMod.MOD_ID)
public final class UsefulToolsGameTests {
    private UsefulToolsGameTests() {}

    @GameTest(template = "usefultoolsmod:gametest_platform", timeoutTicks = 100)
    public static void registrationsAndRecipeData(GameTestHelper helper) {
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
                ResourceLocation.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, "spectral_infuser")).isPresent(),
                "spectral_infuser recipe must load");
        helper.assertTrue(ModItems.RGOLD_PICKAXE.get().getDefaultInstance().getMaxDamage()
                        > Items.IRON_PICKAXE.getDefaultInstance().getMaxDamage(),
                "ferrous-gold tier must outlast iron");
        helper.assertTrue(helper.getLevel().registryAccess().registryOrThrow(Registries.PLACED_FEATURE)
                        .containsKey(ResourceLocation.fromNamespaceAndPath(UsefultoolsMod.MOD_ID,
                                "rgold_ore_placed")),
                "overworld ferrous-gold placed feature must load");
        helper.succeed();
    }

    @GameTest(template = "usefultoolsmod:gametest_platform", timeoutTicks = 100)
    public static void spectralInfuserCreatesBlockEntity(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.SPECTRAL_INFUSER.get());
        helper.assertBlockPresent(ModBlocks.SPECTRAL_INFUSER.get(), pos);
        helper.assertTrue(helper.getBlockEntity(pos) instanceof SpectralInfuserBlockEntity,
                "spectral infuser block entity must be created");
        helper.succeed();
    }

    @GameTest(template = "usefultoolsmod:gametest_platform", timeoutTicks = 100)
    public static void entitiesInstantiate(GameTestHelper helper) {
        helper.spawnWithNoFreeWill(ModEntities.GHOST.get(), new BlockPos(1, 1, 1));
        helper.spawnWithNoFreeWill(ModEntities.WRAITH.get(), new BlockPos(3, 1, 1));
        helper.spawn(ModEntities.GRENADE.get(), new BlockPos(2, 1, 1));
        helper.assertEntityPresent(ModEntities.GHOST.get());
        helper.assertEntityPresent(ModEntities.WRAITH.get());
        helper.assertEntityPresent(ModEntities.GRENADE.get());
        helper.succeed();
    }

    @GameTest(template = "usefultoolsmod:gametest_platform", timeoutTicks = 100)
    public static void spectralBlocksAndChargeContracts(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.ECTOPLASM_LANTERN.get());
        var lanternState = helper.getLevel().getBlockState(helper.absolutePos(pos));
        helper.assertValueEqual(lanternState.getLightEmission(helper.getLevel(), helper.absolutePos(pos)), 12,
                "Ectoplasm Lantern light level");
        helper.assertFalse(lanternState.getCollisionShape(helper.getLevel(), helper.absolutePos(pos)).isEmpty(),
                "Ectoplasm Lantern must retain bounded collision");
        var warded = (com.stonytark.usefultoolsmod.entity.custom.WraithEntity)
                helper.spawnWithNoFreeWill(ModEntities.WRAITH.get(), new BlockPos(2, 1, 1));
        helper.assertTrue(com.stonytark.usefultoolsmod.block.custom.SoulLanternBlock.isStabilized(warded),
                "active Ectoplasm Lantern must suppress a nearby Wraith");
        helper.setBlock(new BlockPos(1, 1, 2), net.minecraft.world.level.block.Blocks.REDSTONE_BLOCK);
        helper.assertFalse(com.stonytark.usefultoolsmod.block.custom.SoulLanternBlock.isStabilized(warded),
                "redstone-powered Ectoplasm Lantern must not suppress Wraiths");
        warded.discard();
        for (net.minecraft.core.Direction direction : net.minecraft.core.Direction.values()) {
            var state = ModBlocks.MINING_CHARGE.get().defaultBlockState()
                    .setValue(com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock.FACING, direction)
                    .setValue(com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock.LIT, false);
            helper.setBlock(pos, state);
            var placed = helper.getLevel().getBlockState(helper.absolutePos(pos));
            helper.assertValueEqual(placed.getValue(
                    com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock.FACING), direction,
                    "Mining Charge facing");
            helper.assertFalse(placed.getCollisionShape(helper.getLevel(), helper.absolutePos(pos)).isEmpty(),
                    "Mining Charge must retain directional collision");
            helper.assertTrue(helper.getBlockEntity(pos) instanceof
                    com.stonytark.usefultoolsmod.block.entity.MiningChargeBlockEntity,
                    "Mining Charge block entity must be created");
        }
        helper.setBlock(pos, ModBlocks.MINING_CHARGE.get().defaultBlockState());
        var charge = (com.stonytark.usefultoolsmod.block.entity.MiningChargeBlockEntity)
                helper.getBlockEntity(pos);
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

    @GameTest(template = "usefultoolsmod:gametest_platform", timeoutTicks = 260)
    public static void spectralInfuserProcesses(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.SPECTRAL_INFUSER.get());
        SpectralInfuserBlockEntity infuser = (SpectralInfuserBlockEntity) helper.getBlockEntity(pos);
        infuser.setItem(0, new ItemStack(Items.IRON_PICKAXE));
        infuser.setItem(1, new ItemStack(ModItems.CONDENSED_ECTOPLASM.get()));
        helper.runAfterDelay(230, () -> {
            helper.assertTrue(infuser.getItem(0).isEmpty(), "input must be consumed");
            helper.assertTrue(infuser.getItem(1).isEmpty(), "one condensed ectoplasm must be consumed");
            helper.assertValueEqual(infuser.getData().get(2), 7, "condensed ectoplasm must retain seven fuel uses");
            helper.assertTrue(infuser.getItem(2).is(Items.IRON_PICKAXE), "tool must reach output");
            helper.assertTrue(EctoplasmInfusionHelper.isInfused(infuser.getItem(2)), "output must be infused");
            infuser.setItem(2, ItemStack.EMPTY);
            infuser.setItem(0, new ItemStack(Items.IRON_PICKAXE));
            infuser.setItem(1, new ItemStack(ModItems.ECTOPLASM.get()));
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
            helper.assertTrue(infuser.getItem(1).is(ModItems.ECTOPLASM.get()), "disabled Infuser must retain fuel");
            helper.succeed();
        });
    }

    @GameTest(template = "usefultoolsmod:gametest_platform", timeoutTicks = 100)
    public static void ghostAndDamageContracts(GameTestHelper helper) {
        GhostEntity ghost = (GhostEntity) helper.spawnWithNoFreeWill(ModEntities.GHOST.get(), new BlockPos(1, 1, 1));
        var wraith = (com.stonytark.usefultoolsmod.entity.custom.WraithEntity) helper.spawnWithNoFreeWill(ModEntities.WRAITH.get(), new BlockPos(3, 1, 1));
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        var source = helper.getLevel().damageSources().playerAttack(player);
        helper.assertTrue(ghost.isInvulnerableTo(source), "ordinary weapons must not damage ghosts");
        helper.assertTrue(wraith.isInvulnerableTo(source), "ordinary weapons must not damage wraiths");
        ItemStack infused = new ItemStack(Items.IRON_SWORD);
        EctoplasmInfusionHelper.setInfused(infused, true);
        player.setItemInHand(InteractionHand.MAIN_HAND, infused);
        helper.assertFalse(ghost.isInvulnerableTo(source), "infused weapons must damage ghosts");
        helper.assertFalse(wraith.isInvulnerableTo(source), "infused weapons must damage wraiths");
        helper.assertTrue(ghost.isFood(new ItemStack(ModItems.ECTOPLASM.get())), "ectoplasm must breed ghosts");
        helper.assertTrue(ghost.getBreedOffspring(helper.getLevel(), ghost) instanceof GhostEntity,
                "ghost breeding must create a ghost");
        equipInfusedArmor(player, EquipmentSlot.HEAD, Items.IRON_HELMET);
        equipInfusedArmor(player, EquipmentSlot.CHEST, Items.IRON_CHESTPLATE);
        equipInfusedArmor(player, EquipmentSlot.LEGS, Items.IRON_LEGGINGS);
        equipInfusedArmor(player, EquipmentSlot.FEET, Items.IRON_BOOTS);
        helper.assertTrue(EctoplasmArmorHelper.isGhostInvisible(player),
                "a full infused armor set must hide its wearer from ghosts");

        boolean oldSweetBerry = Config.sweetBerryEnabled;
        boolean oldGhost = Config.ghostEnabled;
        boolean oldWraith = Config.wraithEnabled;
        float modified;
        boolean spawnAllowed;
        boolean wraithSpawnAllowed;
        try {
            Config.sweetBerryEnabled = true;
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.SWEET_BERRY_SWORD.get()));
            LivingHurtEvent event = new LivingHurtEvent(ghost, source, 4.0F);
            ModEvents.onLivingHurt(event);
            modified = event.getAmount();
            Config.ghostEnabled = false;
            spawnAllowed = GhostEntity.checkGhostSpawnRules(ModEntities.GHOST.get(), helper.getLevel(),
                    MobSpawnType.NATURAL, new BlockPos(1, 1, 1), RandomSource.create(1L));
            Config.wraithEnabled = false;
            wraithSpawnAllowed = com.stonytark.usefultoolsmod.entity.custom.WraithEntity.checkSpawnRules(
                    ModEntities.WRAITH.get(), helper.getLevel(), MobSpawnType.NATURAL,
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
                helper.spawn(ModEntities.WRAITH.get(), new BlockPos(1, 1, 1));
        helper.assertTrue(wraith.isNoGravity(), "Wraith must use flight rather than Ghost gravity");
        wraith.setRemainingFireTicks(100);
        wraith.tick();
        helper.assertFalse(wraith.isOnFire(), "Wraith must clear fire every tick");
        wraith.discard();
        wraith = (com.stonytark.usefultoolsmod.entity.custom.WraithEntity)
                helper.spawn(ModEntities.WRAITH.get(), new BlockPos(1, 1, 1));

        var lungeTarget = helper.spawn(ModEntities.GHOST.get(), new BlockPos(1, 5, 1));
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
        wraith.setTarget(null);
        wraith.setDeltaMovement(net.minecraft.world.phys.Vec3.ZERO);
        var encounter = player.server.getAdvancements().get(ResourceLocation.fromNamespaceAndPath(
                UsefultoolsMod.MOD_ID, "spectral/encounter_wraith"));
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
                helper.spawnWithNoFreeWill(ModEntities.WRAITH.get(), new BlockPos(2, 1, 2));
        ItemStack infused = new ItemStack(Items.IRON_SWORD);
        EctoplasmInfusionHelper.setInfused(infused, true);
        player.setItemInHand(InteractionHand.MAIN_HAND, infused);
        lootWraith.setHealth(1.0F);
        helper.assertTrue(lootWraith.hurt(helper.getLevel().damageSources().playerAttack(player), 5.0F),
                "infused player attack must kill a Wraith");
        helper.runAfterDelay(1, () -> {
            helper.assertItemEntityPresent(ModItems.CONDENSED_ECTOPLASM.get());
            helper.assertItemEntityPresent(ModItems.ECTOPLASM.get());
            helper.succeed();
        });
    }

    @GameTest(template = "usefultoolsmod:gametest_platform", timeoutTicks = 100)
    public static void explosivesKillSwitch(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        boolean old = Config.explosivesEnabled;
        try {
            Config.explosivesEnabled = false;
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.GRENADE.get()));
            ModItems.GRENADE.get().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
            helper.assertValueEqual(player.getMainHandItem().getCount(), 1, "disabled grenade must not be consumed");
            helper.assertEntityNotPresent(ModEntities.GRENADE.get());
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.DYNAMITE.get()));
            ModItems.DYNAMITE.get().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
            helper.assertValueEqual(player.getMainHandItem().getCount(), 1, "disabled dynamite must not be consumed");
        } finally {
            Config.explosivesEnabled = old;
        }
        helper.succeed();
    }

    private static void equipInfusedArmor(Player player, EquipmentSlot slot, net.minecraft.world.item.Item item) {
        ItemStack stack = new ItemStack(item);
        EctoplasmInfusionHelper.setInfused(stack, true);
        player.setItemSlot(slot, stack);
    }
}
