package com.stonytark.usefultoolsmod.fabric.gametest;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.block.entity.SpectralInfuserBlockEntity;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.event.DamageModifier;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmArmorHelper;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
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

public final class UsefulToolsGameTests implements FabricGameTest {
    private static final String TEMPLATE = "usefultoolsmod:empty";

    @GameTest(template = TEMPLATE, timeoutTicks = 100)
    public void registrationsAndRecipeData(GameTestHelper helper) {
        long blocks = BuiltInRegistries.BLOCK.keySet().stream()
                .filter(id -> UsefultoolsMod.MOD_ID.equals(id.getNamespace())).count();
        long items = BuiltInRegistries.ITEM.keySet().stream()
                .filter(id -> UsefultoolsMod.MOD_ID.equals(id.getNamespace())).count();
        long entities = BuiltInRegistries.ENTITY_TYPE.keySet().stream()
                .filter(id -> UsefultoolsMod.MOD_ID.equals(id.getNamespace())).count();
        helper.assertValueEqual(blocks, 21L, "registered block count");
        helper.assertValueEqual(items, 654L, "registered item count including block items");
        helper.assertValueEqual(entities, 2L, "registered entity count");
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

    @GameTest(template = TEMPLATE, timeoutTicks = 100)
    public void spectralInfuserCreatesBlockEntity(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.SPECTRAL_INFUSER.get());
        helper.assertBlockPresent(ModBlocks.SPECTRAL_INFUSER.get(), pos);
        helper.assertTrue(helper.getBlockEntity(pos) instanceof SpectralInfuserBlockEntity,
                "spectral infuser block entity must be created");
        helper.succeed();
    }

    @GameTest(template = TEMPLATE, timeoutTicks = 100)
    public void entitiesInstantiate(GameTestHelper helper) {
        helper.spawnWithNoFreeWill(ModEntities.GHOST.get(), new BlockPos(1, 1, 1));
        helper.spawn(ModEntities.GRENADE.get(), new BlockPos(2, 1, 1));
        helper.assertEntityPresent(ModEntities.GHOST.get());
        helper.assertEntityPresent(ModEntities.GRENADE.get());
        helper.succeed();
    }

    @GameTest(template = TEMPLATE, timeoutTicks = 260)
    public void spectralInfuserProcesses(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.SPECTRAL_INFUSER.get());
        SpectralInfuserBlockEntity infuser = (SpectralInfuserBlockEntity) helper.getBlockEntity(pos);
        infuser.setItem(0, new ItemStack(Items.IRON_PICKAXE));
        infuser.setItem(1, new ItemStack(ModItems.ECTOPLASM.get()));
        helper.runAfterDelay(205, () -> {
            helper.assertTrue(infuser.getItem(0).isEmpty(), "input must be consumed");
            helper.assertTrue(infuser.getItem(1).isEmpty(), "one ectoplasm must be consumed");
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

    @GameTest(template = TEMPLATE, timeoutTicks = 100)
    public void ghostAndDamageContracts(GameTestHelper helper) {
        GhostEntity ghost = (GhostEntity) helper.spawnWithNoFreeWill(ModEntities.GHOST.get(), new BlockPos(1, 1, 1));
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        var source = helper.getLevel().damageSources().playerAttack(player);
        helper.assertTrue(ghost.isInvulnerableTo(source), "ordinary weapons must not damage ghosts");
        ItemStack infused = new ItemStack(Items.IRON_SWORD);
        EctoplasmInfusionHelper.setInfused(infused, true);
        player.setItemInHand(InteractionHand.MAIN_HAND, infused);
        helper.assertFalse(ghost.isInvulnerableTo(source), "infused weapons must damage ghosts");
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
        float modified;
        boolean spawnAllowed;
        try {
            Config.sweetBerryEnabled = true;
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.SWEET_BERRY_SWORD.get()));
            modified = DamageModifier.compute(ghost, source, 4.0F);
            Config.ghostEnabled = false;
            spawnAllowed = GhostEntity.checkGhostSpawnRules(ModEntities.GHOST.get(), helper.getLevel(),
                    MobSpawnType.NATURAL, new BlockPos(1, 1, 1), RandomSource.create(1L));
        } finally {
            Config.sweetBerryEnabled = oldSweetBerry;
            Config.ghostEnabled = oldGhost;
        }
        helper.assertValueEqual(modified, 5.0F, "damage mutation must run exactly once");
        helper.assertFalse(spawnAllowed, "ghost spawn kill switch must be authoritative");
        helper.succeed();
    }

    @GameTest(template = TEMPLATE, timeoutTicks = 100)
    public void explosivesKillSwitch(GameTestHelper helper) {
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
