package com.stonytark.usefultoolsmod.entity.custom;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.block.custom.SoulLanternWard;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class WraithEntity extends Monster {
    private int lungeCooldown;
    public WraithEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level); moveControl = new FlyingMoveControl<>(this, 12, true); setNoGravity(true); noPhysics = true;
    }
    @Override protected void registerGoals() {
        goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.15D, true));
        goalSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this, 0.9D));
        goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 16.0F));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, GhostEntity.class, true));
    }
    @Override protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level); nav.setCanFloat(true);
        nav.getNodeEvaluator().setCanPassDoors(true); return nav;
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 30).add(Attributes.ATTACK_DAMAGE, 6)
                .add(Attributes.FLYING_SPEED, 0.7).add(Attributes.MOVEMENT_SPEED, 0.32).add(Attributes.FOLLOW_RANGE, 32);
    }
    public static boolean checkSpawnRules(EntityType<? extends Monster> type, LevelAccessor level,
                                          EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return Config.ghostEnabled && Config.wraithEnabled && random.nextDouble() <= Config.wraithSpawnChance
                && level.getMaxLocalRawBrightness(pos) <= 3 && !SoulLanternWard.activeWithin(level, pos, 24);
    }
    @Override public void tick() {
        super.tick(); clearFire(); if (lungeCooldown > 0) lungeCooldown--;
        if (!level().isClientSide() && SoulLanternWard.activeWithin(this, SoulLanternWard.RADIUS)) {
            BlockPos lantern = SoulLanternWard.nearest(this);
            if (lantern != null) { Vec3 away = position().subtract(Vec3.atCenterOf(lantern));
                if (away.lengthSqr() < 0.01) away = new Vec3(0, .2, 0); setDeltaMovement(getDeltaMovement().scale(.5).add(away.normalize().scale(.18)).add(0,.04,0)); setTarget(null); }
        }
        if (!level().isClientSide() && tickCount % 20 == 0) {
            for (net.minecraft.server.level.ServerPlayer player : level().getEntitiesOfClass(
                    net.minecraft.server.level.ServerPlayer.class, getBoundingBox().inflate(16.0D)))
                com.stonytark.usefultoolsmod.util.ModAdvancements.award(player, "spectral/encounter_wraith");
        }
        LivingEntity target = getTarget();
        if (!level().isClientSide() && target != null && lungeCooldown == 0 && distanceToSqr(target) > 9
                && distanceToSqr(target) < 144 && hasLineOfSight(target)) {
            setDeltaMovement(target.getEyePosition().subtract(position()).normalize().scale(.85)); lungeCooldown = 40;
        }
    }
    @Override public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean hit = super.doHurtTarget(level, target);
        if (hit && target instanceof LivingEntity living) living.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60, 0));
        return hit;
    }
    @Override public boolean isInvulnerableTo(ServerLevel level, DamageSource source) {
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || source.is(DamageTypeTags.IS_EXPLOSION)) return false;
        return !(source.getEntity() instanceof LivingEntity living && EctoplasmInfusionHelper.isInfused(living.getMainHandItem()));
    }
    @Override protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        int looting = 0;
        if (source.getEntity() instanceof LivingEntity attacker) {
            var enchantment = level.registryAccess().lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT)
                    .getOrThrow(net.minecraft.world.item.enchantment.Enchantments.LOOTING);
            looting = net.minecraft.world.item.enchantment.EnchantmentHelper.getEnchantmentLevel(enchantment, attacker);
        }
        int condensed = 1 + (looting > 0 && random.nextInt(4) < looting ? 1 : 0);
        spawnAtLocation(level, new ItemStack(ModItems.CONDENSED_ECTOPLASM, condensed));
        spawnAtLocation(level, new ItemStack(ModItems.ECTOPLASM, 1 + random.nextInt(3)));
    }
    @Override protected net.minecraft.sounds.SoundEvent getAmbientSound() { return com.stonytark.usefultoolsmod.sound.ModSounds.wraithAmbient(); }
    @Override protected net.minecraft.sounds.SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource source) { return com.stonytark.usefultoolsmod.sound.ModSounds.wraithHurt(); }
    @Override protected net.minecraft.sounds.SoundEvent getDeathSound() { return com.stonytark.usefultoolsmod.sound.ModSounds.wraithDeath(); }
}
