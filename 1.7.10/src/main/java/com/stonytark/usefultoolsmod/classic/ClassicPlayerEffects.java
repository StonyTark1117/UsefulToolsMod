package com.stonytark.usefultoolsmod.classic;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

/** Player-tick equivalents for effects that exist in Minecraft 1.7.10. */
public class ClassicPlayerEffects {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.worldObj.isRemote) return;
        EntityPlayer player = event.player;
        String held = ClassicCombatEvents.id(player.getHeldItem());
        String heldFamily = held == null ? "" : ClassicCombatEvents.toolFamily(held);

        applyHeldEffects(player, held, heldFamily);
        applyArmorEffects(player);
        applyAuras(player);
        applyMaterialLifecycle(player, heldFamily);
        applyWallPhasing(player);

        if (isFallSafeFamily(heldFamily) || ClassicCombatEvents.armor(player, 0, "honey")
                || ClassicCombatEvents.armor(player, 0, "chorus_fruit")
                || fullAny(player, "rabbit_hide", "slime", "purpur", "shulker")) {
            player.fallDistance = 0.0F;
            if (player.motionY < -0.22D) player.motionY = -0.22D;
        }
        if (ClassicGeneratedConfig.booleanValue("foodHungerDrain") && player.ticksExisted % 40 == 0
                && (isFoodFamily(heldFamily) || wearingFoodArmor(player))) {
            player.addExhaustion(0.5F);
            damageHeld(player, 1);
        }
    }

    private static void applyMaterialLifecycle(EntityPlayer player, String heldFamily) {
        ItemStack held = player.getHeldItem();
        if (ClassicGeneratedConfig.booleanValue("snowEnabled")
                && ClassicGeneratedConfig.booleanValue("snowMeltEffects") && "snow".equals(heldFamily)) {
            if (player.ticksExisted % 40 == 0) damageHeld(player, 1);
            if (player.isBurning()) { player.extinguish(); if (player.ticksExisted % 20 == 0) damageHeld(player, 5); }
        }
        if (ClassicGeneratedConfig.booleanValue("iceEnabled")
                && ClassicGeneratedConfig.booleanValue("iceEffects")) {
            if (player.ticksExisted % 60 == 0) {
                if ("ice".equals(heldFamily)) damageHeld(player, 1);
                damageArmorFamily(player, "ice", 1);
            }
            if (player.isBurning() && ("ice".equals(heldFamily) || armorPieces(player, "ice") > 0)) {
                player.extinguish();
                if (player.ticksExisted % 20 == 0) {
                    if ("ice".equals(heldFamily)) damageHeld(player, 5);
                    damageArmorFamily(player, "ice", 5);
                }
            }
        }
        if (ClassicGeneratedConfig.booleanValue("coalEnabled")
                && ClassicGeneratedConfig.booleanValue("coalFireEffects")) {
            boolean coalHeld = "coal".equals(heldFamily);
            if (coalHeld && held != null) {
                if (player.isInWater()) setBurning(held, false);
                else if (player.isBurning()) setBurning(held, true);
                if (isBurning(held) && player.ticksExisted % 20 == 0) {
                    player.attackEntityFrom(DamageSource.inFire, 0.5F);
                    damageHeld(player, 2);
                }
            }
            for (ItemStack armor : player.inventory.armorInventory) {
                String armorId = ClassicCombatEvents.id(armor);
                if (armorId != null && "coal".equals(ClassicPlayerEffects.armorFamily(armorId))) {
                    if (player.isInWater()) setBurning(armor, false);
                    else if (player.isBurning()) setBurning(armor, true);
                    if (isBurning(armor) && player.ticksExisted % 20 == 0) armor.damageItem(1, player);
                }
            }
        }
        if (ClassicGeneratedConfig.booleanValue("pprismEnabled")
                && ClassicGeneratedConfig.booleanValue("pprismWaterEffects") && player.isInWater()) {
            if ("pprism".equals(heldFamily)) effect(player, Potion.digSpeed, 40, 0);
            if (ClassicCombatEvents.armor(player, 3, "pprism")) effect(player, Potion.waterBreathing, 40, 0);
            if (ClassicCombatEvents.armor(player, 2, "pprism")) effect(player, Potion.resistance, 40, 0);
            if (ClassicCombatEvents.armor(player, 1, "pprism")) effect(player, Potion.digSpeed, 40, 0);
        }
    }

    private static void applyWallPhasing(EntityPlayer player) {
        if (!ClassicGeneratedConfig.booleanValue("ectoplasmSetEnabled")
                || !ClassicGeneratedConfig.booleanValue("ectoplasmWallPhasing")
                || !full(player, "ecto") || !player.isSneaking()) return;
        int cooldown = player.getEntityData().getInteger("UsefulToolsPhaseCooldown");
        if (cooldown > 0) {
            player.getEntityData().setInteger("UsefulToolsPhaseCooldown", cooldown - 1);
            return;
        }
        int direction = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int dx = direction == 1 ? -1 : direction == 3 ? 1 : 0;
        int dz = direction == 0 ? 1 : direction == 2 ? -1 : 0;
        int x = MathHelper.floor_double(player.posX);
        int y = MathHelper.floor_double(player.posY);
        int z = MathHelper.floor_double(player.posZ);
        boolean[] blocked = new boolean[3];
        for (int i = 1; i <= 3; i++) {
            blocked[i - 1] = solid(player, x + dx * i, y, z + dz * i)
                    || solid(player, x + dx * i, y + 1, z + dz * i);
        }
        int exit = phaseExit(blocked);
        if (exit > 0) {
            player.setPositionAndUpdate(x + dx * exit + 0.5D, y, z + dz * exit + 0.5D);
            effect(player, Potion.moveSlowdown, 20, 0);
            player.getEntityData().setInteger("UsefulToolsPhaseCooldown", 10);
        }
    }

    static int phaseExit(boolean[] blocked) {
        if (blocked.length < 1 || !blocked[0]) return -1;
        for (int i = 1; i < blocked.length; i++) if (!blocked[i]) return i + 1;
        return -1;
    }

    private static boolean solid(EntityPlayer player, int x, int y, int z) {
        return player.worldObj.getBlock(x, y, z).getMaterial().blocksMovement();
    }

    private static void damageArmorFamily(EntityPlayer player, String family, int amount) {
        for (ItemStack armor : player.inventory.armorInventory) {
            String id = ClassicCombatEvents.id(armor);
            if (id != null && family.equals(armorFamily(id))) armor.damageItem(amount, player);
        }
    }

    private static int armorPieces(EntityPlayer player, String family) {
        int count = 0;
        for (int slot = 0; slot < 4; slot++) if (ClassicCombatEvents.armor(player, slot, family)) count++;
        return count;
    }

    private static boolean isBurning(ItemStack stack) {
        return stack != null && stack.hasTagCompound() && stack.getTagCompound().getBoolean("UsefulToolsCoalBurning");
    }

    private static void setBurning(ItemStack stack, boolean burning) {
        if (stack == null) return;
        if (!stack.hasTagCompound()) stack.setTagCompound(new net.minecraft.nbt.NBTTagCompound());
        stack.getTagCompound().setBoolean("UsefulToolsCoalBurning", burning);
    }

    private static void applyAuras(EntityPlayer player) {
        if (player.ticksExisted % 40 != 0) return;
        boolean spores = ClassicGeneratedConfig.booleanValue("mushroomEnabled")
                && ClassicGeneratedConfig.booleanValue("mushroomSporeCloud") && full(player, "mushroom");
        boolean poison = ClassicGeneratedConfig.booleanValue("pufferfishEnabled")
                && ClassicGeneratedConfig.booleanValue("pufferfishPoisonAura") && full(player, "pufferfish");
        if (!spores && !poison) return;
        double radius = spores ? 4.0D : 3.0D;
        @SuppressWarnings("unchecked")
        java.util.List<net.minecraft.entity.EntityLiving> mobs = player.worldObj.getEntitiesWithinAABB(
                net.minecraft.entity.EntityLiving.class, player.boundingBox.expand(radius, radius, radius));
        for (net.minecraft.entity.EntityLiving mob : mobs) {
            if (mob.getAttackTarget() == null) continue;
            if (spores) effect(mob, Potion.confusion, 100, 0);
            if (poison) effect(mob, Potion.poison, 100, 0);
        }
    }

    private static void applyHeldEffects(EntityPlayer player, String held, String family) {
        if (held == null) return;
        if (enabled("paper", "paperEnabled", "paperEffects") && "paper".equals(family)) effect(player, Potion.weakness, 40, 0);
        if (enabled("nether_wart", "netherWartEnabled", "netherWartEffects") && "nether_wart".equals(family)) effect(player, Potion.hunger, 40, 0);
        if (enabled("ghast_tear", "ghastTearEnabled", "ghastTearEffects") && "ghast_tear".equals(family)) effect(player, Potion.regeneration, 40, 0);
        if (enabled("eye_of_ender", "eyeOfEnderEnabled", "eyeOfEnderEffects") && "eye_of_ender".equals(family)) effect(player, Potion.nightVision, 220, 0);
        if (enabled("dragon_breath", "dragonBreathEnabled", "dragonBreathEffects") && "dragon_breath".equals(family)) {
            effect(player, Potion.damageBoost, 40, 0);
            effect(player, Potion.fireResistance, 40, 0);
        }
        if (ClassicGeneratedConfig.booleanValue("overpowerEnabled")
                && ClassicGeneratedConfig.booleanValue("opToolEffectsEnabled")
                && "overpower".equals(family)) {
            if (held.endsWith("_sword")) {
                effect(player, Potion.damageBoost, 20, 3); effect(player, Potion.moveSpeed, 20, 3);
                effect(player, Potion.jump, 20, 3); effect(player, Potion.resistance, 20, 3);
            } else if (held.endsWith("_pickaxe")) {
                effect(player, Potion.digSpeed, 20, 3); effect(player, Potion.damageBoost, 20, 3);
                effect(player, Potion.jump, 20, 10);
            } else if (held.endsWith("_shovel")) {
                effect(player, Potion.digSpeed, 20, 3); effect(player, Potion.damageBoost, 20, 1);
                effect(player, Potion.jump, 20, 5);
            } else if (held.endsWith("_axe")) {
                effect(player, Potion.regeneration, 20, 3); effect(player, Potion.damageBoost, 20, 3);
                effect(player, Potion.jump, 20, 10);
            }
        }
        if (ClassicInfusion.isInfused(player.getHeldItem())
                && ClassicGeneratedConfig.booleanValue("spectralInfuserEnabled")
                && ClassicGeneratedConfig.booleanValue("infusedToolEffects")) {
            effect(player, Potion.nightVision, 220, 0);
        }
    }

    private static void applyArmorEffects(EntityPlayer player) {
        for (int slot = 0; slot < 4; slot++) {
            String id = ClassicCombatEvents.id(player.inventory.armorInventory[slot]);
            if (id == null) continue;
            String family = armorFamily(id);
            if (!armorEffectsEnabled(family)) continue;
            applyArmorSlot(player, family, slot);
        }
        applyFullSetEffects(player);
    }

    static String armorFamily(String id) {
        for (String suffix : new String[] {"_boots", "_leggings", "_chestplate", "_helmet"})
            if (id.endsWith(suffix)) return id.substring(0, id.length() - suffix.length());
        return id;
    }

    private static void applyArmorSlot(EntityPlayer p, String family, int slot) {
        switch (family) {
            case "bread":
                if (slot == 0) effect(p, Potion.moveSpeed, 40, 0);
                if (slot == 1) effect(p, Potion.jump, 40, 0);
                if (slot == 2 && p.ticksExisted % 60 == 0) p.getFoodStats().addStats(1, 0.1F);
                break;
            case "dried_kelp":
                if (slot == 0 && p.isInWater()) effect(p, Potion.moveSpeed, 40, 1);
                if (slot == 1) effect(p, Potion.digSpeed, 40, 0);
                if (slot == 2) effect(p, Potion.waterBreathing, 40, 0);
                if (slot == 3) effect(p, Potion.nightVision, 220, 0);
                break;
            case "rotten_flesh":
                if (slot == 1) effect(p, Potion.fireResistance, 40, 0);
                if (slot == 2) effect(p, Potion.resistance, 40, 0);
                if (slot == 3) effect(p, Potion.hunger, 40, 0);
                break;
            case "melon": case "sweet_berry":
                if (slot == 0) effect(p, Potion.moveSpeed, 40, 0);
                if (slot == 1) effect(p, Potion.jump, 40, 0);
                if (slot == 2) effect(p, Potion.regeneration, 40, 0);
                if (slot == 3 && "melon".equals(family)) effect(p, Potion.waterBreathing, 40, 0);
                break;
            case "pumpkin_pie":
                if (slot == 0) effect(p, Potion.moveSpeed, 40, 0);
                if (slot == 1) effect(p, Potion.jump, 40, 0);
                if (slot == 2) effect(p, Potion.field_76444_x, 40, 0);
                break;
            case "mushroom":
                if (slot == 0) effect(p, Potion.digSpeed, 40, 0);
                if (slot == 1) effect(p, Potion.jump, 40, 0);
                if (slot == 2) effect(p, Potion.resistance, 40, 0);
                if (slot == 3) effect(p, Potion.nightVision, 220, 0);
                break;
            case "pufferfish":
                if (slot == 0) effect(p, Potion.waterBreathing, 40, 0);
                if (slot == 1) effect(p, Potion.resistance, 40, 0);
                if (slot == 2) p.removePotionEffect(Potion.poison.id);
                if (slot == 3) effect(p, Potion.waterBreathing, 40, 1);
                break;
            case "honey":
                if (slot == 1) effect(p, Potion.resistance, 40, 0);
                if (slot == 2) effect(p, Potion.fireResistance, 40, 0);
                if (slot == 3) p.removePotionEffect(Potion.poison.id);
                break;
            case "chorus_fruit":
                if (slot == 1) effect(p, Potion.moveSpeed, 40, 0);
                if (slot == 2) effect(p, Potion.resistance, 40, 0);
                if (slot == 3) effect(p, Potion.nightVision, 220, 0);
                break;
            case "golden_apple":
                if (slot == 0) effect(p, Potion.moveSpeed, 40, 0);
                if (slot == 1) effect(p, Potion.resistance, 40, 0);
                if (slot == 2) effect(p, Potion.regeneration, 40, 0);
                if (slot == 3) effect(p, Potion.fireResistance, 40, 0);
                break;
            case "rabbit_hide":
                if (slot == 0) effect(p, Potion.jump, 40, 0);
                if (slot == 1) effect(p, Potion.moveSpeed, 40, 0);
                break;
            case "clay":
                if (slot == 0) effect(p, Potion.moveSpeed, 40, 0);
                if (slot == 1) effect(p, Potion.jump, 40, 0);
                if (slot == 2) effect(p, Potion.resistance, 40, 0);
                break;
            case "nether_brick": case "blaze":
                effect(p, Potion.fireResistance, 40, 0); break;
            case "copper":
                if (p.worldObj.isRaining()) {
                    if (slot == 0) effect(p, Potion.moveSpeed, 40, 0);
                    if (slot == 3) effect(p, Potion.waterBreathing, 40, 0);
                }
                break;
            case "phantom":
                if (slot == 1 && !p.worldObj.isDaytime()) effect(p, Potion.moveSpeed, 40, 0);
                if (slot == 2 && !p.worldObj.isDaytime()) effect(p, Potion.resistance, 40, 0);
                if (slot == 3) effect(p, Potion.nightVision, 220, 0);
                break;
            case "magma_cream":
                if (slot == 0 || slot == 2) effect(p, Potion.fireResistance, 40, 0);
                if (slot == 1) effect(p, Potion.resistance, 40, 0);
                break;
            case "slime":
                if (slot == 0) effect(p, Potion.jump, 40, 1);
                if (slot == 1) effect(p, Potion.moveSpeed, 40, 0);
                if (slot == 2) effect(p, Potion.resistance, 40, 0);
                break;
            case "nautilus": case "turtle_scute":
                if (slot == 3) effect(p, Potion.waterBreathing, 220, 0);
                if (p.isInWater()) {
                    if (slot == 0) effect(p, Potion.moveSpeed, 40, 1);
                    if (slot == 1 || slot == 2) effect(p, Potion.resistance, 40, 0);
                }
                break;
            case "purpur": case "shulker":
                if (slot == 1) effect(p, Potion.jump, 40, "shulker".equals(family) ? 1 : 0);
                if (slot == 2) effect(p, Potion.resistance, 40, 0);
                if (slot == 3) effect(p, Potion.nightVision, 220, 0);
                break;
            case "ghast_tear":
                if (slot == 0) effect(p, Potion.moveSpeed, 40, 0);
                if (slot == 1) effect(p, Potion.regeneration, 40, 0);
                if (slot == 2) effect(p, Potion.resistance, 40, 0);
                if (slot == 3) effect(p, Potion.field_76444_x, 40, 0);
                break;
            case "eye_of_ender":
                if (slot == 0) effect(p, Potion.moveSpeed, 40, 0);
                if (slot == 1) effect(p, Potion.jump, 40, 0);
                if (slot == 2) effect(p, Potion.resistance, 40, 0);
                if (slot == 3) effect(p, Potion.nightVision, 220, 0);
                break;
            case "echo_shard":
                if (slot == 0) effect(p, Potion.moveSpeed, 40, 0);
                if (slot == 1) effect(p, Potion.resistance, 40, 0);
                if (slot == 2) effect(p, Potion.resistance, 40, 1);
                if (slot == 3) effect(p, Potion.nightVision, 220, 0);
                break;
            case "dragon_breath":
                if (slot == 0) effect(p, Potion.moveSpeed, 40, 0);
                if (slot == 1) effect(p, Potion.damageBoost, 40, 0);
                if (slot == 2) effect(p, Potion.resistance, 40, 1);
                if (slot == 3) effect(p, Potion.fireResistance, 40, 0);
                break;
            case "overpower":
                if (ClassicGeneratedConfig.booleanValue("opArmorEffectsEnabled")) {
                    effect(p, Potion.damageBoost, 20, 2); effect(p, Potion.resistance, 20, 2);
                }
                break;
        }
    }

    private static void applyFullSetEffects(EntityPlayer p) {
        if (full(p, "bread")) p.removePotionEffect(Potion.hunger.id);
        if (full(p, "melon") && p.ticksExisted % 60 == 0) p.getFoodStats().addStats(1, 0.1F);
        if (full(p, "golden_apple")) effect(p, Potion.field_76444_x, 40, 1);
        if (full(p, "blaze")) effect(p, Potion.damageBoost, 40, 0);
        if (full(p, "ghast_tear")) { effect(p, Potion.regeneration, 40, 1); effect(p, Potion.field_76444_x, 40, 1); }
        if (full(p, "dragon_breath")) { effect(p, Potion.damageBoost, 40, 1); effect(p, Potion.resistance, 40, 1); }
        if (full(p, "rabbit_hide")) { effect(p, Potion.jump, 40, 2); effect(p, Potion.moveSpeed, 40, 0); }
    }

    private static boolean armorEffectsEnabled(String family) {
        String[] mapping = configKeys(family);
        return mapping != null && ClassicGeneratedConfig.booleanValue(mapping[0])
                && ClassicGeneratedConfig.booleanValue(mapping[1]);
    }

    static String[] configKeys(String family) {
        String[][] entries = {
                {"bread","breadEnabled","breadArmorEffects"}, {"dried_kelp","driedKelpEnabled","driedKelpArmorEffects"},
                {"rotten_flesh","rottenFleshEnabled","rottenFleshArmorEffects"}, {"melon","melonEnabled","melonArmorEffects"},
                {"sweet_berry","sweetBerryEnabled","sweetBerryArmorEffects"}, {"pumpkin_pie","pumpkinPieEnabled","pumpkinPieArmorEffects"},
                {"mushroom","mushroomEnabled","mushroomArmorEffects"}, {"pufferfish","pufferfishEnabled","pufferfishArmorEffects"},
                {"honey","honeyEnabled","honeyArmorEffects"}, {"chorus_fruit","chorusFruitEnabled","chorusFruitArmorEffects"},
                {"golden_apple","goldenAppleEnabled","goldenAppleArmorEffects"}, {"rabbit_hide","rabbitHideEnabled","rabbitHideEffects"},
                {"clay","clayEnabled","clayEffects"}, {"nether_brick","netherBrickEnabled","netherBrickEffects"},
                {"copper","copperEnabled","copperEffects"}, {"phantom","phantomEnabled","phantomEffects"},
                {"magma_cream","magmaCreamEnabled","magmaCreamEffects"}, {"slime","slimeEnabled","slimeEffects"},
                {"blaze","blazeEnabled","blazeEffects"}, {"nautilus","nautilusEnabled","nautilusEffects"},
                {"purpur","purpurEnabled","purpurEffects"}, {"ghast_tear","ghastTearEnabled","ghastTearEffects"},
                {"eye_of_ender","eyeOfEnderEnabled","eyeOfEnderEffects"}, {"shulker","shulkerEnabled","shulkerEffects"},
                {"turtle_scute","turtleScuteEnabled","turtleScuteEffects"}, {"echo_shard","echoShardEnabled","echoShardEffects"},
                {"dragon_breath","dragonBreathEnabled","dragonBreathEffects"}, {"overpower","overpowerEnabled","opArmorEffectsEnabled"}
        };
        for (String[] entry : entries) if (entry[0].equals(family)) return new String[] {entry[1], entry[2]};
        return null;
    }

    private static boolean enabled(String family, String enabled, String effects) {
        return ClassicGeneratedConfig.booleanValue(enabled) && ClassicGeneratedConfig.booleanValue(effects);
    }
    private static void effect(EntityLivingBase p, Potion potion, int duration, int amplifier) {
        p.addPotionEffect(new PotionEffect(potion.id, duration, amplifier, true));
    }
    private static boolean full(EntityPlayer p, String family) { return ClassicCombatEvents.fullSet(p, family); }
    private static boolean fullAny(EntityPlayer p, String... families) {
        for (String family : families) if (full(p, family)) return true;
        return false;
    }
    private static boolean isFallSafeFamily(String family) {
        return "feather".equals(family) || "phantom".equals(family) || "purpur".equals(family)
                || "shulker".equals(family) || "chorus_fruit".equals(family);
    }
    private static boolean isFoodFamily(String family) {
        return family.equals("cake") || family.equals("bread") || family.equals("dried_kelp")
                || family.equals("rotten_flesh") || family.equals("melon") || family.equals("sweet_berry")
                || family.equals("pumpkin_pie") || family.equals("mushroom") || family.equals("pufferfish")
                || family.equals("honey") || family.equals("chorus_fruit") || family.equals("golden_apple");
    }
    private static boolean wearingFoodArmor(EntityPlayer p) {
        for (ItemStack stack : p.inventory.armorInventory) {
            String id = ClassicCombatEvents.id(stack);
            if (id != null && isFoodFamily(armorFamily(id))) return true;
        }
        return false;
    }
    private static void damageHeld(EntityPlayer p, int amount) {
        ItemStack stack = p.getHeldItem();
        if (stack != null && stack.isItemStackDamageable()) stack.damageItem(amount, p);
    }
}
