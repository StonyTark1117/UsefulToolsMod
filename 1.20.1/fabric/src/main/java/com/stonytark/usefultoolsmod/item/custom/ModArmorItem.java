package com.stonytark.usefultoolsmod.item.custom;

import com.google.common.collect.ImmutableMap;
import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.item.ModArmorMaterials;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class ModArmorItem extends ArmorItem {
    private static final Map<ArmorMaterial, List<StatusEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, List<StatusEffectInstance>>())
                    .put(ModArmorMaterials.OVERPOWER,
                            List.of(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 3, false, false),
                                    new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 5, false, false),
                                    new StatusEffectInstance(StatusEffects.ABSORPTION, 200, 2, false, false),
                                    new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 5, false, false),
                                    new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 200, 3, false, false),
                                    new StatusEffectInstance(StatusEffects.SPEED, 200, 4, false, false),
                                    new StatusEffectInstance(StatusEffects.REGENERATION, 200, 4, false, false)))
                    .build();

    public ModArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player && !world.isClient() && hasFullSuitOfArmorOn(player)
                && Config.overpowerEnabled && Config.opArmorEffectsEnabled) {
            evaluateArmorEffects(player);
        }
    }

    private void evaluateArmorEffects(PlayerEntity player) {
        for (Map.Entry<ArmorMaterial, List<StatusEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            List<StatusEffectInstance> mapEffect = entry.getValue();

            if (hasPlayerCorrectArmorOn(mapArmorMaterial, player)) {
                addEffectToPlayer(player, mapEffect);
            }
        }
    }

    private void addEffectToPlayer(PlayerEntity player, List<StatusEffectInstance> mapEffect) {
        boolean hasPlayerEffect = mapEffect.stream().allMatch(effect -> player.hasStatusEffect(effect.getEffectType()));

        if (!hasPlayerEffect) {
            for (StatusEffectInstance effect : mapEffect) {
                player.addStatusEffect(new StatusEffectInstance(effect.getEffectType(),
                        effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), effect.shouldShowParticles()));
            }
        }
    }

    private boolean hasPlayerCorrectArmorOn(ArmorMaterial mapArmorMaterial, PlayerEntity player) {
        for (ItemStack armorStack : player.getArmorItems()) {
            if (!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem) player.getInventory().getArmorStack(0).getItem());
        ArmorItem leggings = ((ArmorItem) player.getInventory().getArmorStack(1).getItem());
        ArmorItem chestplate = ((ArmorItem) player.getInventory().getArmorStack(2).getItem());
        ArmorItem helmet = ((ArmorItem) player.getInventory().getArmorStack(3).getItem());

        return boots.getMaterial() == mapArmorMaterial && leggings.getMaterial() == mapArmorMaterial
                && chestplate.getMaterial() == mapArmorMaterial && helmet.getMaterial() == mapArmorMaterial;
    }

    private boolean hasFullSuitOfArmorOn(PlayerEntity player) {
        ItemStack boots = player.getInventory().getArmorStack(0);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack chestplate = player.getInventory().getArmorStack(2);
        ItemStack helmet = player.getInventory().getArmorStack(3);

        return !boots.isEmpty() && !leggings.isEmpty() && !chestplate.isEmpty() && !helmet.isEmpty();
    }
}
