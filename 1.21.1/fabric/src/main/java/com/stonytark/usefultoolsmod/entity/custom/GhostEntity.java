package com.stonytark.usefultoolsmod.entity.custom;

import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.ai.goal.FollowActiveGhostGoal;
import com.stonytark.usefultoolsmod.entity.ai.goal.FollowPlayerGoal;
import com.stonytark.usefultoolsmod.entity.ai.goal.HideBehindAdultGoal;
import com.stonytark.usefultoolsmod.entity.ai.goal.ObserveFriendlyMobGoal;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GhostEntity extends AnimalEntity {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int lifetime = 0;
    private static final int MAX_LIFETIME = 5 * 60 * 20;
    private static final int MAX_SOLID_DEPTH = 3;

    public GhostEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
        this.moveControl = new FlightMoveControl(this, 10, true);
        this.setNoGravity(true);
        this.noClip = true;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new HideBehindAdultGoal(this, 1.3D));
        this.goalSelector.add(1, new FollowParentGoal(this, 1.1D));

        this.goalSelector.add(0, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(0, new FollowPlayerGoal(this, 1.2D, 10.0F, 1.0));
        this.goalSelector.add(1, new ObserveFriendlyMobGoal(this, 0.9D, 10.0F, GhostEntity.class));
        this.goalSelector.add(2, new FollowActiveGhostGoal(this, 1.0D, 12.0F));

        this.goalSelector.add(3, new FlyGoal(this, 1.0D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation nav = new BirdNavigation(this, world);
        nav.setCanSwim(true);
        nav.setCanEnterOpenDoors(true);
        return nav;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D);
    }

    public static boolean checkGhostSpawnRules(EntityType<? extends AnimalEntity> type,
                                               net.minecraft.world.WorldAccess world,
                                               net.minecraft.entity.SpawnReason reason,
                                               BlockPos pos,
                                               net.minecraft.util.math.random.Random random) {
        if (!com.stonytark.usefultoolsmod.Config.ghostEnabled) return false;
        if (random.nextDouble() > com.stonytark.usefultoolsmod.Config.ghostSpawnChance) return false;

        int skyLight = world.getBaseLightLevel(pos, 0);
        if (skyLight > 3) {
            return random.nextInt(3) == 0;
        }
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient) {
            this.extinguish();
            constrainPosition();

            lifetime++;
            if (lifetime > MAX_LIFETIME) {
                this.discard();
            }
        }

        if (this.random.nextFloat() < 0.02F) {
            this.setVelocity(this.getVelocity().add(
                    (this.random.nextDouble() - 0.5D) * 0.05D,
                    (this.random.nextDouble() - 0.5D) * 0.05D,
                    (this.random.nextDouble() - 0.5D) * 0.05D
            ));
        }

        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }
    }

    private void constrainPosition() {
        World world = this.getWorld();
        BlockPos pos = this.getBlockPos();

        if (world.getBlockState(pos).isAir()) return;

        Direction bestDir = null;
        int bestDist = MAX_SOLID_DEPTH + 1;

        for (Direction dir : Direction.values()) {
            for (int dist = 1; dist <= MAX_SOLID_DEPTH; dist++) {
                BlockPos check = pos.offset(dir, dist);
                if (world.getBlockState(check).isAir()) {
                    if (dist < bestDist) {
                        bestDist = dist;
                        bestDir = dir;
                    }
                    break;
                }
            }
        }

        if (bestDir != null) {
            Vec3d push = Vec3d.of(bestDir.getVector()).normalize().multiply(0.15 / bestDist);
            Vec3d current = this.getVelocity();
            this.setVelocity(
                    current.x * 0.6 + push.x,
                    current.y * 0.6 + push.y,
                    current.z * 0.6 + push.z
            );
        } else {
            teleportToNearestAir(pos);
        }
    }

    private void teleportToNearestAir(BlockPos origin) {
        World world = this.getWorld();
        for (int r = MAX_SOLID_DEPTH + 1; r <= 16; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dy = -r; dy <= r; dy++) {
                    for (int dz = -r; dz <= r; dz++) {
                        if (Math.abs(dx) != r && Math.abs(dy) != r && Math.abs(dz) != r) continue;
                        BlockPos check = origin.add(dx, dy, dz);
                        if (world.getBlockState(check).isAir()
                                && world.getBlockState(check.up()).isAir()) {
                            this.setPosition(check.getX() + 0.5, check.getY() + 0.5, check.getZ() + 0.5);
                            this.setVelocity(Vec3d.ZERO);
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override
    public Box getVisibilityBoundingBox() {
        return this.getBoundingBox().expand(1.5);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) return false;

        Entity attacker = source.getAttacker();
        if (attacker instanceof LivingEntity living) {
            ItemStack weapon = living.getMainHandStack();
            if (EctoplasmInfusionHelper.isInfused(weapon)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        Entity attacker = source.getAttacker();
        if (attacker instanceof LivingEntity living) {
            ItemStack weapon = living.getMainHandStack();
            if (EctoplasmInfusionHelper.isInfused(weapon)
                    && !(weapon.getItem() instanceof SwordItem)
                    && !(weapon.getItem() instanceof AxeItem)) {
                amount = 1.0f;
            }
        }
        return super.damage(source, amount);
    }

    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        super.dropLoot(source, causedByPlayer);
        int count;
        if (isBaby()) {
            count = this.random.nextInt(2);
        } else {
            count = 1 + this.random.nextInt(3);
        }
        if (count > 0) {
            this.dropStack(new ItemStack(ModItems.ECTOPLASM, count));
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity other) {
        return (PassiveEntity) ModEntities.GHOST.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(ModItems.ECTOPLASM);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 110;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_GHAST_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_GHAST_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GHAST_DEATH;
    }
}
