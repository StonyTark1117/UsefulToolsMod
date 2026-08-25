package com.stonytark.usefultoolsmod.classic;

import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassicSemanticTest {
    @Test
    public void infusionCopiesRepairsAndMarksEquipment() {
        ItemStack original = new ItemStack(new Item().setMaxDamage(250));
        original.setItemDamage(100);

        ItemStack result = TileSpectralInfuser.createResult(original, new Item(), new Item());

        assertNotSame(original, result);
        assertEquals(100, original.getItemDamage());
        assertEquals(0, result.getItemDamage());
        assertFalse(ClassicInfusion.isInfused(original));
        assertTrue(ClassicInfusion.isInfused(result));
    }

    @Test
    public void eggBecomesTheProvidedGhostSpawnEgg() {
        Item spawnEgg = new ClassicGhostSpawnEgg();
        Item egg = new Item();
        ItemStack result = TileSpectralInfuser.createResult(new ItemStack(egg), spawnEgg, egg);
        assertSame(spawnEgg, result.getItem());
        assertEquals(1, result.stackSize);
    }

    @Test
    public void onlyEquipmentAndEggsAreInfusable() {
        Item egg = new Item();
        assertTrue(TileSpectralInfuser.isInfusable(
                new ItemStack(new ItemSword(Item.ToolMaterial.IRON)), egg));
        assertTrue(TileSpectralInfuser.isInfusable(new ItemStack(egg), egg));
        assertFalse(TileSpectralInfuser.isInfusable(new ItemStack(new Item()), egg));
        assertFalse(TileSpectralInfuser.isInfusable(null, egg));
    }

    @Test
    public void classicDamageAdapterMatchesModernRuleOrder() {
        assertEquals(7.0F, ClassicCombatEvents.computeDamageForFamily(
                "glass", 5.0F, false, false, false, 0, false), 0.0001F);
        assertEquals(8.0F, ClassicCombatEvents.computeDamageForFamily(
                "sponge", 5.0F, true, false, false, 0, false), 0.0001F);
        assertEquals(9.75F, ClassicCombatEvents.computeDamageForFamily(
                "pointed_dripstone", 5.0F, false, false, false, 2.0F, true), 0.0001F);
        assertEquals(7.5F, ClassicCombatEvents.computeDamageForFamily(
                "bone", 5.0F, false, true, false, 0, false), 0.0001F);
        assertEquals(5.0F, ClassicCombatEvents.computeDamageForFamily(
                "phantom", 5.0F, false, false, false, 0, false), 0.0001F);
    }

    @Test
    public void classicEffectFamiliesMapToStableConfigKeys() {
        assertArrayEquals(new String[] {"driedKelpEnabled", "driedKelpArmorEffects"},
                ClassicPlayerEffects.configKeys("dried_kelp"));
        assertArrayEquals(new String[] {"dragonBreathEnabled", "dragonBreathEffects"},
                ClassicPlayerEffects.configKeys("dragon_breath"));
        assertEquals("golden_apple", ClassicPlayerEffects.armorFamily("golden_apple_helmet"));
        assertNull(ClassicPlayerEffects.configKeys("not_a_material"));
    }

    @Test
    public void classicAchievementClassifierCoversCanonicalEquipment() {
        assertTrue(ClassicAchievements.isTool("dragon_breath_pickaxe"));
        assertFalse(ClassicAchievements.isTool("dragon_breath_helmet"));
        assertTrue(ClassicAchievements.isArmor("dragon_breath_helmet"));
        assertFalse(ClassicAchievements.isArmor("ectoplasm"));
    }

    @Test
    public void classicWorldgenCoversEveryCanonicalOreInItsNativeDimension() {
        assertEquals(4, ClassicOreGeneration.RULES.length);
        assertOreRule(0, "rgold_deepslate_ore", Blocks.stone, 1, 17);
        assertOreRule(0, "rgoldore", Blocks.stone, 17, 80);
        assertOreRule(-1, "rgold_nether_ore", Blocks.netherrack, 8, 120);
        assertOreRule(1, "rgold_end_ore", Blocks.end_stone, 8, 120);
    }

    @Test
    public void ectoplasmPhasingOnlyCrossesAThinWallWithAnOpenExit() {
        assertEquals(2, ClassicPlayerEffects.phaseExit(new boolean[] {true, false, false}));
        assertEquals(3, ClassicPlayerEffects.phaseExit(new boolean[] {true, true, false}));
        assertEquals(-1, ClassicPlayerEffects.phaseExit(new boolean[] {true, true, true}));
        assertEquals(-1, ClassicPlayerEffects.phaseExit(new boolean[] {false, false, false}));
    }

    private static void assertOreRule(int dimension, String id, Object replacement,
                                      int minY, int maxY) {
        for (ClassicOreGeneration.Rule rule : ClassicOreGeneration.RULES) {
            if (!id.equals(rule.oreId)) continue;
            assertEquals(dimension, rule.dimension);
            assertSame(replacement, rule.replacement);
            assertEquals(minY, rule.minY);
            assertEquals(maxY, rule.maxY);
            assertEquals(9, rule.veinSize);
            assertEquals(12, rule.attempts);
            return;
        }
        fail("Missing ore generation rule " + id);
    }
}
