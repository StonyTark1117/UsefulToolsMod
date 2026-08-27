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

    @GameTest(templateName = TEMPLATE, tickLimit = 260)
    public void spectralInfuserProcesses(TestContext context) {
        BlockPos pos = new BlockPos(1, 1, 1);
        context.setBlockState(pos, ModBlocks.SPECTRAL_INFUSER);
        SpectralInfuserBlockEntity infuser = (SpectralInfuserBlockEntity) context.getBlockEntity(pos);
        infuser.getInventory().set(0, new ItemStack(Items.IRON_PICKAXE));
        infuser.getInventory().set(1, new ItemStack(ModItems.CONDENSED_ECTOPLASM));
        context.runAtTick(205, () -> {
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
        float modified;
        boolean spawnAllowed;
        try {
            Config.sweetBerryEnabled = true;
            player.setStackInHand(Hand.MAIN_HAND, new ItemStack(ModItems.SWEET_BERRY_SWORD));
            modified = DamageModifier.compute(ghost, source, 4.0F);
            Config.ghostEnabled = false;
            spawnAllowed = GhostEntity.checkGhostSpawnRules(ModEntities.GHOST, context.getWorld(),
                    SpawnReason.NATURAL, new BlockPos(1, 1, 1), Random.create(1L));
        } finally {
            Config.sweetBerryEnabled = oldSweetBerry;
            Config.ghostEnabled = oldGhost;
        }
        context.assertTrue(modified == 5.0F, "damage mutation must run exactly once");
        context.assertFalse(spawnAllowed, "ghost spawn kill switch must be authoritative");
        context.complete();
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
