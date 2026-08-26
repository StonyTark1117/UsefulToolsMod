package com.stonytark.usefultoolsmod.entity.custom;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.block.custom.SoulLanternBlock;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.FlyGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class WraithEntity extends HostileEntity {
    private int lungeCooldown;
    public WraithEntity(EntityType<? extends HostileEntity> type, World world) {
        super(type, world); moveControl = new FlightMoveControl(this, 12, true); setNoGravity(true); noClip = true;
    }

    @Override protected void initGoals() {
        goalSelector.add(1, new MeleeAttackGoal(this, 1.15D, true));
        goalSelector.add(4, new FlyGoal(this, 0.9D));
        goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 16.0F));
        goalSelector.add(6, new LookAroundGoal(this));
        targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        targetSelector.add(2, new ActiveTargetGoal<>(this, GhostEntity.class, true));
    }

    @Override protected EntityNavigation createNavigation(World world) {
        BirdNavigation navigation = new BirdNavigation(this, world);
        navigation.setCanSwim(true); navigation.setCanEnterOpenDoors(true); return navigation;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.7D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.32D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D);
    }

    public static boolean checkSpawnRules(EntityType<? extends HostileEntity> type, WorldAccess world,
                                          SpawnReason reason, BlockPos pos, Random random) {
        return Config.ghostEnabled && Config.wraithEnabled && random.nextDouble() <= Config.wraithSpawnChance
                && world.getBaseLightLevel(pos, 0) <= 3 && !hasActiveLantern(world, pos);
    }

    private static boolean hasActiveLantern(WorldAccess world, BlockPos origin) {
        int r = 24;
        for (BlockPos pos : BlockPos.iterate(origin.add(-r, -r, -r), origin.add(r, r, r))) {
            if (world.getBlockState(pos).getBlock() instanceof SoulLanternBlock
                    && !world.isReceivingRedstonePower(pos)) return true;
        }
        return false;
    }

    @Override public void tick() {
        super.tick(); extinguish(); if (lungeCooldown > 0) lungeCooldown--;
        if (getWorld().isClient && com.stonytark.usefultoolsmod.client.SpectralClientConfig.particlesEnabled
                && random.nextInt(4) == 0) getWorld().addParticle(net.minecraft.particle.ParticleTypes.REVERSE_PORTAL,
                getParticleX(0.8D), getRandomBodyY(), getParticleZ(0.8D), 0, 0.01D, 0);
        if (!getWorld().isClient && age % 20 == 0) {
            for (net.minecraft.server.network.ServerPlayerEntity player : getWorld().getEntitiesByClass(
                    net.minecraft.server.network.ServerPlayerEntity.class, getBoundingBox().expand(16.0D), entity -> true))
                com.stonytark.usefultoolsmod.util.ModAdvancements.award(player, "spectral/encounter_wraith");
        }
        LivingEntity target = getTarget();
        if (!getWorld().isClient && target != null && lungeCooldown == 0 && squaredDistanceTo(target) > 9.0D
                && squaredDistanceTo(target) < 144.0D && canSee(target)) {
            Vec3d lunge = target.getEyePos().subtract(getPos()).normalize().multiply(0.85D);
            setVelocity(lunge); lungeCooldown = 40;
        }
    }

    @Override public boolean tryAttack(Entity target) {
        boolean hit = super.tryAttack(target);
        if (hit && target instanceof LivingEntity living) living.addStatusEffect(
                new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 0));
        return hit;
    }

    @Override public boolean isInvulnerableTo(DamageSource source) {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) || source.isIn(DamageTypeTags.IS_EXPLOSION)) return false;
        Entity attacker = source.getAttacker();
        return !(attacker instanceof LivingEntity living && EctoplasmInfusionHelper.isInfused(living.getMainHandStack()));
    }

    @Override protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        super.dropLoot(source, causedByPlayer);
        int looting = source.getAttacker() instanceof PlayerEntity player
                ? net.minecraft.enchantment.EnchantmentHelper.getLevel(net.minecraft.enchantment.Enchantments.LOOTING,
                player.getMainHandStack()) : 0;
        int condensed = 1 + (looting > 0 && random.nextInt(4) < looting ? 1 : 0);
        dropStack(new ItemStack(ModItems.CONDENSED_ECTOPLASM, condensed));
        dropStack(new ItemStack(ModItems.ECTOPLASM, 1 + random.nextInt(3)));
    }
    @Override protected net.minecraft.sound.SoundEvent getAmbientSound() { return com.stonytark.usefultoolsmod.sound.ModSounds.WRAITH_AMBIENT; }
    @Override protected net.minecraft.sound.SoundEvent getHurtSound(net.minecraft.entity.damage.DamageSource source) { return com.stonytark.usefultoolsmod.sound.ModSounds.WRAITH_HURT; }
    @Override protected net.minecraft.sound.SoundEvent getDeathSound() { return com.stonytark.usefultoolsmod.sound.ModSounds.WRAITH_DEATH; }
}
