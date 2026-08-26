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
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;

@GameTestHolder(UsefultoolsMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class UsefulToolsGameTests {
    private UsefulToolsGameTests() {}

    @GameTest(template = "gametest_platform", timeoutTicks = 100)
    public static void registrationsAndRecipeData(GameTestHelper helper) {
        long blocks = helper.getLevel().registryAccess().registryOrThrow(Registries.BLOCK).keySet().stream()
                .filter(id -> UsefultoolsMod.MOD_ID.equals(id.getNamespace())).count();
        long items = helper.getLevel().registryAccess().registryOrThrow(Registries.ITEM).keySet().stream()
                .filter(id -> UsefultoolsMod.MOD_ID.equals(id.getNamespace())).count();
        long entities = helper.getLevel().registryAccess().registryOrThrow(Registries.ENTITY_TYPE).keySet().stream()
                .filter(id -> UsefultoolsMod.MOD_ID.equals(id.getNamespace())).count();
        helper.assertTrue(blocks == 23L, "registered block count");
        helper.assertTrue(items == 661L, "registered item count including block items");
        helper.assertTrue(entities == 4L, "registered entity count");
        helper.assertTrue(helper.getLevel().getServer().getRecipeManager().byKey(
                new ResourceLocation(UsefultoolsMod.MOD_ID, "spectral_infuser")).isPresent(),
                "spectral_infuser recipe must load");
        helper.succeed();
    }

    @GameTest(template = "gametest_platform", timeoutTicks = 100)
    public static void spectralInfuserCreatesBlockEntity(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.SPECTRAL_INFUSER.get());
        helper.assertBlockPresent(ModBlocks.SPECTRAL_INFUSER.get(), pos);
        helper.assertTrue(helper.getBlockEntity(pos) instanceof SpectralInfuserBlockEntity,
                "spectral infuser block entity must be created");
        helper.succeed();
    }

    @GameTest(template = "gametest_platform", timeoutTicks = 100)
    public static void entitiesInstantiate(GameTestHelper helper) {
        helper.spawnWithNoFreeWill(ModEntities.GHOST.get(), new BlockPos(1, 1, 1));
        helper.spawn(ModEntities.GRENADE.get(), new BlockPos(2, 1, 1));
        helper.assertEntityPresent(ModEntities.GHOST.get());
        helper.assertEntityPresent(ModEntities.GRENADE.get());
        helper.succeed();
    }

    @GameTest(template = "gametest_platform", timeoutTicks = 260)
    public static void spectralInfuserProcesses(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.SPECTRAL_INFUSER.get());
        SpectralInfuserBlockEntity infuser = (SpectralInfuserBlockEntity) helper.getBlockEntity(pos);
        infuser.setItem(0, new ItemStack(Items.IRON_PICKAXE));
        infuser.setItem(1, new ItemStack(ModItems.CONDENSED_ECTOPLASM.get()));
        helper.runAfterDelay(205, () -> {
            helper.assertTrue(infuser.getItem(0).isEmpty(), "input must be consumed");
            helper.assertTrue(infuser.getItem(1).isEmpty(), "one condensed ectoplasm must be consumed");
            helper.assertTrue(infuser.getData().get(2) == 7, "condensed ectoplasm must retain seven fuel uses");
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
            helper.assertTrue(infuser.getData().get(0) == 0, "disabled Infuser must not progress");
            helper.assertTrue(infuser.getItem(0).is(Items.IRON_PICKAXE), "disabled Infuser must retain input");
            helper.assertTrue(infuser.getItem(1).is(ModItems.ECTOPLASM.get()), "disabled Infuser must retain fuel");
            helper.succeed();
        });
    }

    @GameTest(template = "gametest_platform", timeoutTicks = 100)
    public static void ghostAndDamageContracts(GameTestHelper helper) {
        GhostEntity ghost = (GhostEntity) helper.spawnWithNoFreeWill(ModEntities.GHOST.get(), new BlockPos(1, 1, 1));
        Player player = helper.makeMockPlayer();
        var source = helper.getLevel().damageSources().playerAttack(player);
        helper.assertTrue(ghost.isInvulnerableTo(source), "ordinary weapons must not damage ghosts");
        ItemStack infused = new ItemStack(Items.IRON_SWORD);
        EctoplasmInfusionHelper.setInfused(infused, true);
        player.setItemInHand(InteractionHand.MAIN_HAND, infused);
        helper.assertFalse(ghost.isInvulnerableTo(source), "infused weapons must damage ghosts");
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
            LivingHurtEvent event = new LivingHurtEvent(ghost, source, 4.0F);
            ModEvents.onLivingHurt(event);
            modified = event.getAmount();
            Config.ghostEnabled = false;
            spawnAllowed = GhostEntity.checkGhostSpawnRules(ModEntities.GHOST.get(), helper.getLevel(),
                    MobSpawnType.NATURAL, new BlockPos(1, 1, 1), RandomSource.create(1L));
        } finally {
            Config.sweetBerryEnabled = oldSweetBerry;
            Config.ghostEnabled = oldGhost;
        }
        helper.assertTrue(modified == 5.0F, "damage mutation must run exactly once");
        helper.assertFalse(spawnAllowed, "ghost spawn kill switch must be authoritative");
        helper.succeed();
    }

    @GameTest(template = "gametest_platform", timeoutTicks = 100)
    public static void explosivesKillSwitch(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        boolean old = Config.explosivesEnabled;
        try {
            Config.explosivesEnabled = false;
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.GRENADE.get()));
            ModItems.GRENADE.get().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
            helper.assertTrue(player.getMainHandItem().getCount() == 1, "disabled grenade must not be consumed");
            helper.assertEntityNotPresent(ModEntities.GRENADE.get());
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.DYNAMITE.get()));
            ModItems.DYNAMITE.get().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
            helper.assertTrue(player.getMainHandItem().getCount() == 1, "disabled dynamite must not be consumed");
        } finally {
            Config.explosivesEnabled = old;
        }
        helper.succeed();
    }
}
