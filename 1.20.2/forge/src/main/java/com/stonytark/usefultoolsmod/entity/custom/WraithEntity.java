package com.stonytark.usefultoolsmod.entity.custom;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
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
        super(type, level);
        moveControl = new FlyingMoveControl(this, 12, true);
        setNoGravity(true);
        noPhysics = true;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.15D, true));
        goalSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this, 0.9D));
        goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 16.0F));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, GhostEntity.class, true));
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FLYING_SPEED, 0.7D)
                .add(Attributes.MOVEMENT_SPEED, 0.32D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    public static boolean checkSpawnRules(EntityType<? extends Monster> type, LevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        return Config.ghostEnabled && Config.wraithEnabled && random.nextDouble() <= Config.wraithSpawnChance
                && level.getMaxLocalRawBrightness(pos) <= 3
                && level.getEntitiesOfClass(WraithEntity.class, new net.minecraft.world.phys.AABB(pos).inflate(1)).isEmpty()
                && !hasActiveLantern(level, pos);
    }

    private static boolean hasActiveLantern(LevelAccessor level, BlockPos origin) {
        int radius = 24;
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-radius, -radius, -radius),
                origin.offset(radius, radius, radius))) {
            if (level.getBlockState(pos).getBlock() instanceof com.stonytark.usefultoolsmod.block.custom.SoulLanternBlock
                    && !level.hasNeighborSignal(pos)) return true;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        clearFire();
        if (level().isClientSide && com.stonytark.usefultoolsmod.client.SpectralClientConfig.particlesEnabled
                && random.nextInt(4) == 0) {
            level().addParticle(net.minecraft.core.particles.ParticleTypes.REVERSE_PORTAL,
                    getRandomX(0.8D), getRandomY(), getRandomZ(0.8D), 0, 0.01D, 0);
        }
        if (lungeCooldown > 0) lungeCooldown--;
        if (!level().isClientSide && tickCount % 20 == 0) {
            for (net.minecraft.server.level.ServerPlayer player : level().getEntitiesOfClass(
                    net.minecraft.server.level.ServerPlayer.class, getBoundingBox().inflate(16.0D)))
                com.stonytark.usefultoolsmod.util.ModAdvancements.award(player, "spectral/encounter_wraith");
        }
        LivingEntity target = getTarget();
        if (!level().isClientSide && target != null && lungeCooldown == 0
                && distanceToSqr(target) > 9.0D && distanceToSqr(target) < 144.0D && hasLineOfSight(target)) {
            Vec3 lunge = target.getEyePosition().subtract(position()).normalize().scale(0.85D);
            setDeltaMovement(lunge);
            lungeCooldown = 40;
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0));
        }
        return hit;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || source.is(DamageTypeTags.IS_EXPLOSION)) return false;
        Entity attacker = source.getEntity();
        if (attacker instanceof LivingEntity living && EctoplasmInfusionHelper.isInfused(living.getMainHandItem())) return false;
        return true;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        int condensed = 1 + (looting > 0 && random.nextBoolean() ? 1 : 0);
        spawnAtLocation(new ItemStack(ModItems.CONDENSED_ECTOPLASM.get(), condensed));
        spawnAtLocation(new ItemStack(ModItems.ECTOPLASM.get(), 1 + random.nextInt(3)));
    }
    @Override protected net.minecraft.sounds.SoundEvent getAmbientSound() { return com.stonytark.usefultoolsmod.sound.ModSounds.WRAITH_AMBIENT.get(); }
    @Override protected net.minecraft.sounds.SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource source) { return com.stonytark.usefultoolsmod.sound.ModSounds.WRAITH_HURT.get(); }
    @Override protected net.minecraft.sounds.SoundEvent getDeathSound() { return com.stonytark.usefultoolsmod.sound.ModSounds.WRAITH_DEATH.get(); }
}
