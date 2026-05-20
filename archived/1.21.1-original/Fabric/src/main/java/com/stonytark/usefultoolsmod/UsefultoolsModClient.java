package com.stonytark.usefultoolsmod;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.block.entity.ModMenuTypes;
import com.stonytark.usefultoolsmod.client.SpectralInfuserScreen;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.client.GhostModel;
import com.stonytark.usefultoolsmod.entity.client.GhostRenderer;
import com.stonytark.usefultoolsmod.item.ModArmorMaterials;
import com.stonytark.usefultoolsmod.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;

public class UsefultoolsModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register entity model layers (defines geometry for baking)
        EntityModelLayerRegistry.registerModelLayer(GhostModel.LAYER_LOCATION, GhostModel::createBodyLayer);

        // Register entity renderers
        EntityRendererRegistry.register(ModEntities.GHOST, GhostRenderer::new);
        EntityRendererRegistry.register(ModEntities.GRENADE, FlyingItemEntityRenderer::new);

        // Register screen handlers
        HandledScreens.register(ModMenuTypes.SPECTRAL_INFUSER_MENU, SpectralInfuserScreen::new);

        // Register client-side tick for particle aura effects
        ClientTickEvents.END_CLIENT_TICK.register(UsefultoolsModClient::onClientTick);
    }

    private static void onClientTick(MinecraftClient client) {
        if (client.player == null || client.world == null) return;
        PlayerEntity player = client.player;

        ItemStack mainHand = player.getMainHandStack();
        ItemStack offHand = player.getOffHandStack();

        // Spawn enchant glint particles for OVERPOWER tools
        if (isOverpowerTool(mainHand) || isOverpowerTool(offHand)) {
            spawnAuraParticles(player);
        }

        // Spawn aura particles per OVERPOWER armor piece worn
        if (Config.overpowerEnabled && Config.opArmorEffectsEnabled) {
            spawnArmorAuraIfOp(player);
        }
    }

    private static boolean isOverpowerTool(ItemStack stack) {
        return stack.isOf(ModItems.OVERPOWER_SWORD)
                || stack.isOf(ModItems.OVERPOWER_PICKAXE)
                || stack.isOf(ModItems.OVERPOWER_SHOVEL)
                || stack.isOf(ModItems.OVERPOWER_AXE);
    }

    private static void spawnArmorAuraIfOp(PlayerEntity player) {
        for (ItemStack armorStack : player.getArmorItems()) {
            if (armorStack.getItem() instanceof ArmorItem armor
                    && armor.getMaterial() == ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL) {
                spawnOpAuraParticles(player);
            }
        }
    }

    private static void spawnAuraParticles(PlayerEntity player) {
        double x = player.getX() + (player.getRandom().nextDouble() - 0.5) * 1.2;
        double y = player.getY() + player.getRandom().nextDouble() * player.getHeight();
        double z = player.getZ() + (player.getRandom().nextDouble() - 0.5) * 1.2;
        player.getWorld().addParticle(ParticleTypes.ENCHANT, x, y, z, 0.0, 0.1, 0.0);
    }

    private static void spawnOpAuraParticles(PlayerEntity player) {
        double radius = 0.5 + player.getRandom().nextDouble() * 0.5;
        double angle  = player.getRandom().nextDouble() * Math.PI * 2;
        double x = player.getX() + Math.cos(angle) * radius;
        double y = player.getY() + 1.0 + (player.getRandom().nextDouble() - 0.5) * 0.3;
        double z = player.getZ() + Math.sin(angle) * radius;

        if (player.getRandom().nextBoolean()) {
            player.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 0, 0.02, 0);
        } else {
            player.getWorld().addParticle(ParticleTypes.ENCHANT, x, y, z, 0, 0.01, 0);
        }
    }
}
