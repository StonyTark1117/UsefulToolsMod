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
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.BuiltinTestFunctions;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class UsefulToolsGameTests {
    public UsefulToolsGameTests() {}

    public static void register(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(
                Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, "default"),
                new TestEnvironmentDefinition.AllOf());
        register(event, environment, "registrations_and_recipe_data", 100,
                UsefulToolsGameTests::registrationsAndRecipeData);
        register(event, environment, "spectral_infuser_creates_block_entity", 100,
                UsefulToolsGameTests::spectralInfuserCreatesBlockEntity);
        register(event, environment, "entities_instantiate", 100,
                UsefulToolsGameTests::entitiesInstantiate);
        register(event, environment, "spectral_infuser_processes", 260,
                UsefulToolsGameTests::spectralInfuserProcesses);
        register(event, environment, "ghost_and_damage_contracts", 100,
                UsefulToolsGameTests::ghostAndDamageContracts);
        register(event, environment, "explosives_kill_switch", 100,
                UsefulToolsGameTests::explosivesKillSwitch);
    }

    private static void register(RegisterGameTestsEvent event,
                                 net.minecraft.core.Holder<TestEnvironmentDefinition<?>> environment,
                                 String name, int maxTicks,
                                 java.util.function.Consumer<GameTestHelper> test) {
        var data = new TestData<>(environment,
                Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, "gametest_platform"),
                maxTicks, 0, true);
        event.registerTest(Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, name),
                new FunctionGameTestInstance(BuiltinTestFunctions.ALWAYS_PASS, data) {
                    @Override
                    public void run(GameTestHelper helper) {
                        test.accept(helper);
                    }
                });
    }

    public static void registrationsAndRecipeData(GameTestHelper helper) {
        long blocks = BuiltInRegistries.BLOCK.keySet().stream()
                .filter(id -> UsefultoolsMod.MOD_ID.equals(id.getNamespace())).count();
        long items = BuiltInRegistries.ITEM.keySet().stream()
                .filter(id -> UsefultoolsMod.MOD_ID.equals(id.getNamespace())).count();
        long entities = BuiltInRegistries.ENTITY_TYPE.keySet().stream()
                .filter(id -> UsefultoolsMod.MOD_ID.equals(id.getNamespace())).count();
        helper.assertValueEqual(blocks, 23L, "registered block count");
        helper.assertValueEqual(items, 661L, "registered item count including block items");
        helper.assertValueEqual(entities, 4L, "registered entity count");
        helper.assertTrue(helper.getLevel().getServer().getRecipeManager().byKey(
                ResourceKey.create(Registries.RECIPE,
                        Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, "spectral_infuser"))).isPresent(),
                "spectral_infuser recipe must load");
        helper.succeed();
    }

    public static void spectralInfuserCreatesBlockEntity(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.SPECTRAL_INFUSER.get());
        helper.assertBlockPresent(ModBlocks.SPECTRAL_INFUSER.get(), pos);
        helper.getBlockEntity(pos, SpectralInfuserBlockEntity.class);
        helper.succeed();
    }

    public static void entitiesInstantiate(GameTestHelper helper) {
        helper.spawnWithNoFreeWill(ModEntities.GHOST.get(), new BlockPos(1, 1, 1));
        helper.spawn(ModEntities.GRENADE.get(), new BlockPos(2, 1, 1));
        helper.assertEntityPresent(ModEntities.GHOST.get());
        helper.assertEntityPresent(ModEntities.GRENADE.get());
        helper.succeed();
    }

    public static void spectralInfuserProcesses(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.SPECTRAL_INFUSER.get());
        SpectralInfuserBlockEntity infuser = helper.getBlockEntity(pos, SpectralInfuserBlockEntity.class);
        infuser.setItem(0, new ItemStack(Items.IRON_PICKAXE));
        infuser.setItem(1, new ItemStack(ModItems.CONDENSED_ECTOPLASM.get()));
        helper.runAfterDelay(205, () -> {
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

    public static void ghostAndDamageContracts(GameTestHelper helper) {
        GhostEntity ghost = (GhostEntity) helper.spawnWithNoFreeWill(ModEntities.GHOST.get(), new BlockPos(1, 1, 1));
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        var source = helper.getLevel().damageSources().playerAttack(player);
        helper.assertTrue(ghost.isInvulnerableTo(helper.getLevel(), source), "ordinary weapons must not damage ghosts");
        ItemStack infused = new ItemStack(Items.IRON_SWORD);
        EctoplasmInfusionHelper.setInfused(infused, true);
        player.setItemInHand(InteractionHand.MAIN_HAND, infused);
        helper.assertFalse(ghost.isInvulnerableTo(helper.getLevel(), source), "infused weapons must damage ghosts");
        helper.assertTrue(ghost.isFood(new ItemStack(ModItems.ECTOPLASM.get())), "ectoplasm must breed ghosts");
        helper.assertTrue(ghost.getBreedOffspring(helper.getLevel(), ghost) instanceof GhostEntity,
                "ghost breeding must create a ghost");

        boolean oldSweetBerry = Config.sweetBerryEnabled;
        boolean oldGhost = Config.ghostEnabled;
        float modified;
        boolean spawnAllowed;
        try {
            Config.sweetBerryEnabled = true;
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.SWEET_BERRY_SWORD.get()));
            LivingIncomingDamageEvent event = new LivingIncomingDamageEvent(
                    ghost, new DamageContainer(source, 4.0F));
            ModEvents.onLivingHurt(event);
            modified = event.getAmount();
            Config.ghostEnabled = false;
            spawnAllowed = GhostEntity.checkGhostSpawnRules(ModEntities.GHOST.get(), helper.getLevel(),
                    EntitySpawnReason.NATURAL, new BlockPos(1, 1, 1), RandomSource.create(1L));
        } finally {
            Config.sweetBerryEnabled = oldSweetBerry;
            Config.ghostEnabled = oldGhost;
        }
        helper.assertValueEqual(modified, 5.0F, "damage mutation must run exactly once");
        helper.assertFalse(spawnAllowed, "ghost spawn kill switch must be authoritative");
        helper.succeed();
    }

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
}
