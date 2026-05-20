package com.stonytark.usefultoolsmod.event;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.item.ModArmorMaterials;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.item.custom.CoalArmorItem;
import com.stonytark.usefultoolsmod.item.custom.CoalBurningHelper;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmArmorHelper;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import com.stonytark.usefultoolsmod.trigger.ModTriggers;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.*;
import java.util.function.Predicate;

public class ModEvents {

    // -----------------------------------------------------------------------
    // Coal burning state
    // -----------------------------------------------------------------------

    /** Players currently holding at least one burning coal item (tool or armor in hand). */
    private static final Set<UUID> COAL_TOOL_BURNING = new HashSet<>();
    /** Players whose worn coal armor is burning. */
    private static final Set<UUID> COAL_ARMOR_BURNING = new HashSet<>();

    /**
     * Burning coal items (tools OR armor) that are lying on the ground as item entities.
     * Key = entity UUID, Value = dimension registry key.
     */
    private static final Map<UUID, net.minecraft.registry.RegistryKey<World>> BURNING_DROPPED_ITEMS = new HashMap<>();

    // -----------------------------------------------------------------------
    // Registration — all Fabric event callbacks
    // -----------------------------------------------------------------------

    public static void register() {
        // Per-player tick via server tick
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (player.isSpectator()) continue;
                onPlayerTick(player);
            }
        });

        // World tick — processes burning coal items on the ground
        ServerTickEvents.END_WORLD_TICK.register(ModEvents::onWorldTick);

        // Entity load — detect burning coal items entering the world
        ServerEntityEvents.ENTITY_LOAD.register(ModEvents::onEntityLoad);

        // Player death — handle burning coal armor/tool on death
        ServerLivingEntityEvents.AFTER_DEATH.register(ModEvents::onLivingDeath);

        // Furnace fuel values for coal items
        registerFuelValues();

        // FNI right-click fire
        UseBlockCallback.EVENT.register(ModEvents::onUseBlock);

        // Item tooltips (client-only)
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            registerClientTooltips();
        }

        // Damage events — offensive tool hits + defensive armor effects
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(ModEvents::onAllowDamage);
        AttackEntityCallback.EVENT.register(ModEvents::onAttackEntity);
    }

    // -----------------------------------------------------------------------
    // Per-player tick
    // -----------------------------------------------------------------------

    private static void onPlayerTick(PlayerEntity player) {
        if (player == null || player.isSpectator()) return;

        if (Config.overpowerEnabled && Config.opToolEffectsEnabled) {
            handleOpToolEffects(player, player.getMainHandStack());
            handleOpToolEffects(player, player.getOffHandStack());
        }
        // Note: OP armor aura particles are handled client-side in UsefultoolsModClient

        if (!player.getWorld().isClient()) {
            if (Config.coalEnabled && Config.coalFireEffects) {
                handleCoalToolBurning(player);
                handleCoalArmorBurning(player);
            }
            if (Config.snowEnabled && Config.snowMeltEffects) {
                handleSnowMelt(player);
                handleSnowFireProtection(player);
            }
            if (Config.iceEnabled && Config.iceEffects) {
                handleIceMelt(player);
                handleIceFireProtection(player);
            }
            if (Config.pprismEnabled && Config.pprismWaterEffects) {
                handlePprismWaterEffects(player);
            }
            if (Config.fniEnabled && Config.fniFireEffects) {
                handleFniBootsFire(player);
                handleFniArmorBonus(player);
            }
            if (Config.ectoplasmSetEnabled && Config.ectoplasmWallPhasing) {
                handleEctoWallPhasing(player);
            }
            if (Config.spectralInfuserEnabled && Config.infusedToolEffects) {
                handleInfusedToolEffects(player);
            }
            if (Config.foodHungerDrain) {
                handleFoodHungerDrain(player);
            }
            if (Config.cakeEnabled && Config.cakeArmorEffects) {
                handleCakeArmorEffects(player);
            }
            // Food set armor effects
            if (Config.breadEnabled && Config.breadArmorEffects) handleBreadArmorEffects(player);
            if (Config.driedKelpEnabled && Config.driedKelpArmorEffects) handleDriedKelpArmorEffects(player);
            if (Config.rottenFleshEnabled && Config.rottenFleshArmorEffects) handleRottenFleshArmorEffects(player);
            if (Config.melonEnabled && Config.melonArmorEffects) handleMelonArmorEffects(player);
            if (Config.sweetBerryEnabled && Config.sweetBerryArmorEffects) handleSweetBerryArmorEffects(player);
            if (Config.pumpkinPieEnabled && Config.pumpkinPieArmorEffects) handlePumpkinPieArmorEffects(player);
            if (Config.mushroomEnabled && Config.mushroomArmorEffects) handleMushroomArmorEffects(player);
            if (Config.pufferfishEnabled && Config.pufferfishArmorEffects) handlePufferfishArmorEffects(player);
            if (Config.honeyEnabled && Config.honeyArmorEffects) handleHoneyArmorEffects(player);
            if (Config.chorusFruitEnabled && Config.chorusFruitArmorEffects) handleChorusFruitArmorEffects(player);
            if (Config.goldenAppleEnabled && Config.goldenAppleArmorEffects) handleGoldenAppleArmorEffects(player);
            // Vanilla material set effects
            if (Config.paperEnabled && Config.paperEffects) handlePaperPassive(player);
            if (Config.featherEnabled && Config.featherEffects) handleFeatherPassive(player);
            if (Config.spongeEnabled && Config.spongeEffects) handleSpongePassive(player);
            if (Config.netherWartEnabled && Config.netherWartEffects) handleNetherWartPassive(player);
            if (Config.rabbitHideEnabled && Config.rabbitHideEffects) handleRabbitHideArmor(player);
            if (Config.cactusEnabled && Config.cactusEffects) handleCactusAura(player);
            if (Config.boneEnabled && Config.boneEffects) handleBoneArmor(player);
            if (Config.clayEnabled && Config.clayEffects) handleClayArmor(player);
            if (Config.netherBrickEnabled && Config.netherBrickEffects) handleNetherBrickArmor(player);
            if (Config.copperEnabled && Config.copperEffects) handleCopperArmor(player);
            if (Config.phantomEnabled && Config.phantomEffects) handlePhantomEffects(player);
            if (Config.magmaCreamEnabled && Config.magmaCreamEffects) handleMagmaCreamArmor(player);
            if (Config.slimeEnabled && Config.slimeEffects) handleSlimeEffects(player);
            if (Config.blazeEnabled && Config.blazeEffects) handleBlazeArmor(player);
            if (Config.nautilusEnabled && Config.nautilusEffects) handleNautilusEffects(player);
            if (Config.purpurEnabled && Config.purpurEffects) handlePurpurEffects(player);
            if (Config.ghastTearEnabled && Config.ghastTearEffects) handleGhastTearEffects(player);
            if (Config.eyeOfEnderEnabled && Config.eyeOfEnderEffects) handleEyeOfEnderEffects(player);
            if (Config.shulkerEnabled && Config.shulkerEffects) handleShulkerEffects(player);
            if (Config.turtleScuteEnabled && Config.turtleScuteEffects) handleTurtleScuteArmor(player);
            if (Config.echoShardEnabled && Config.echoShardEffects) handleEchoShardEffects(player);
            if (Config.dragonBreathEnabled && Config.dragonBreathEffects) handleDragonBreathEffects(player);
        }
    }

    // -----------------------------------------------------------------------
    // World tick — processes burning coal items lying on the ground
    // -----------------------------------------------------------------------

    private static void onWorldTick(ServerWorld serverWorld) {
        if (BURNING_DROPPED_ITEMS.isEmpty()) return;

        net.minecraft.registry.RegistryKey<World> thisDim = serverWorld.getRegistryKey();

        BURNING_DROPPED_ITEMS.entrySet().removeIf(entry -> {
            // Only process entries that belong to this dimension this tick
            if (!entry.getValue().equals(thisDim)) return false;

            Entity entity = serverWorld.getEntity(entry.getKey());
            if (entity == null) return false;          // chunk unloaded — keep tracking
            if (!(entity instanceof ItemEntity itemEntity) || !itemEntity.isAlive()) return true;

            ItemStack item = itemEntity.getStack();
            if (!(isCoalTool(item) || isCoalArmor(item)) || !CoalBurningHelper.isBurning(item)) return true;

            // Water extinguishes the item
            if (itemEntity.isTouchingWater()) {
                CoalBurningHelper.setBurning(item, false);
                return true;
            }

            // Durability drain once per second
            if (serverWorld.getTime() % 20 == 0) {
                int newDmg = item.getDamage() + 2;
                if (newDmg >= item.getMaxDamage()) {
                    itemEntity.discard();
                    return true;
                }
                item.setDamage(newDmg);
            }

            // Smoke particles from the burning item on the ground
            if (serverWorld.getTime() % 5 == 0) {
                serverWorld.spawnParticles(ParticleTypes.SMOKE,
                        itemEntity.getX(), itemEntity.getY() + 0.3, itemEntity.getZ(),
                        1, 0.1, 0.1, 0.1, 0.01);
            }

            return false;
        });
    }

    // -----------------------------------------------------------------------
    // Detect burning coal items entering the world (drops, throws, chunk loads)
    // + Ghost spawn gating
    // -----------------------------------------------------------------------

    private static void onEntityLoad(Entity entity, ServerWorld serverWorld) {
        // Burning coal items on the ground
        if (entity instanceof ItemEntity itemEntity) {
            ItemStack item = itemEntity.getStack();
            if ((isCoalTool(item) || isCoalArmor(item)) && CoalBurningHelper.isBurning(item)) {
                BURNING_DROPPED_ITEMS.put(itemEntity.getUuid(), serverWorld.getRegistryKey());
            }
        }

        // Ghost kill-switch: if config has ghosts disabled, drop any that load
        // (natural spawn rate is enforced by GhostEntity.checkGhostSpawnRules,
        // which only runs for natural spawning paths — not spawn eggs / commands / breeding).
        if (entity instanceof GhostEntity && !Config.ghostEnabled) {
            entity.discard();
        }
    }

    // -----------------------------------------------------------------------
    // Player death — handle burning coal armor/tool on death
    // -----------------------------------------------------------------------

    private static void onLivingDeath(LivingEntity entity, DamageSource source) {
        if (!(entity instanceof PlayerEntity player)) return;
        if (player.getWorld().isClient()) return;

        UUID uuid = player.getUuid();
        boolean keepInventory = player.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY);

        // --- Burning armor ---
        if (COAL_ARMOR_BURNING.contains(uuid)) {
            COAL_ARMOR_BURNING.remove(uuid);
            if (keepInventory) {
                clearArmorBurning(player);
            }
        }

        // --- Burning tool ---
        if (COAL_TOOL_BURNING.contains(uuid)) {
            COAL_TOOL_BURNING.remove(uuid);
            if (keepInventory) {
                clearBurningIfCoalTool(player.getMainHandStack());
                clearBurningIfCoalTool(player.getOffHandStack());
            }
        }
    }

    // -----------------------------------------------------------------------
    // OP Tool effects
    // -----------------------------------------------------------------------

    private static void handleOpToolEffects(PlayerEntity player, ItemStack held) {
        if (held.isEmpty()) return;

        if (held.isOf(ModItems.OVERPOWER_SWORD)) {
            applyEffects(player,
                    new StatusEffectInstance(StatusEffects.STRENGTH, 10, 3, false, false, false),
                    new StatusEffectInstance(StatusEffects.SPEED, 10, 3, false, false, false),
                    new StatusEffectInstance(StatusEffects.JUMP_BOOST, 10, 3, false, false, false),
                    new StatusEffectInstance(StatusEffects.RESISTANCE, 10, 3, false, false, false));
        } else if (held.isOf(ModItems.OVERPOWER_PICKAXE)) {
            applyEffects(player,
                    new StatusEffectInstance(StatusEffects.HASTE, 10, 3, false, false, false),
                    new StatusEffectInstance(StatusEffects.STRENGTH, 10, 3, false, false, false),
                    new StatusEffectInstance(StatusEffects.JUMP_BOOST, 10, 10, false, false, false));
        } else if (held.isOf(ModItems.OVERPOWER_SHOVEL)) {
            applyEffects(player,
                    new StatusEffectInstance(StatusEffects.HASTE, 10, 3, false, false, false),
                    new StatusEffectInstance(StatusEffects.STRENGTH, 10, 1, false, false, false),
                    new StatusEffectInstance(StatusEffects.JUMP_BOOST, 10, 5, false, false, false));
        } else if (held.isOf(ModItems.OVERPOWER_AXE)) {
            applyEffects(player,
                    new StatusEffectInstance(StatusEffects.REGENERATION, 10, 3, false, false, false),
                    new StatusEffectInstance(StatusEffects.STRENGTH, 10, 3, false, false, false),
                    new StatusEffectInstance(StatusEffects.JUMP_BOOST, 10, 10, false, false, false));
        }
    }

    // -----------------------------------------------------------------------
    // Coal tool burning (held by a player)
    // -----------------------------------------------------------------------

    private static void handleCoalToolBurning(PlayerEntity player) {

        UUID uuid = player.getUuid();
        ItemStack main = player.getMainHandStack();
        ItemStack off = player.getOffHandStack();

        // Coal armor held in hand behaves identically to a burning coal tool
        boolean mainIsCoal = isCoalTool(main) || isCoalArmor(main);
        boolean offIsCoal = isCoalTool(off) || isCoalArmor(off);
        boolean mainBurning = mainIsCoal && CoalBurningHelper.isBurning(main);
        boolean offBurning = offIsCoal && CoalBurningHelper.isBurning(off);

        // Water extinguishes any burning coal item in hand
        if (player.isTouchingWater()) {
            if (mainIsCoal) CoalBurningHelper.setBurning(main, false);
            if (offIsCoal) CoalBurningHelper.setBurning(off, false);
            COAL_TOOL_BURNING.remove(uuid);
            return;
        }

        // Ignite: player catches fire while holding an unlit coal item
        if (player.isOnFire()) {
            boolean justIgnitedTool = false;
            if (mainIsCoal && !CoalBurningHelper.isBurning(main)) {
                CoalBurningHelper.setBurning(main, true);
                mainBurning = true;
                if (isCoalTool(main)) justIgnitedTool = true;
            }
            if (offIsCoal && !CoalBurningHelper.isBurning(off)) {
                CoalBurningHelper.setBurning(off, true);
                offBurning = true;
                if (isCoalTool(off)) justIgnitedTool = true;
            }
            if (mainIsCoal || offIsCoal) COAL_TOOL_BURNING.add(uuid);
            // Advancement trigger fires only when a coal tool (not armor) first ignites
            if (justIgnitedTool && player instanceof ServerPlayerEntity sp) {
                ModTriggers.COAL_TOOL_IGNITED.trigger(sp);
            }
        }

        // Re-register if player picked up an already-burning coal item
        if ((mainBurning || offBurning) && !COAL_TOOL_BURNING.contains(uuid)) {
            COAL_TOOL_BURNING.add(uuid);
        }

        // No burning coal item in hand — clear held effects
        if (!mainBurning && !offBurning) {
            COAL_TOOL_BURNING.remove(uuid);
            return;
        }

        if (!COAL_TOOL_BURNING.contains(uuid)) return;

        // Once per second: damage player and drain item durability
        if (player.age % 20 == 0) {
            player.damage(player.getDamageSources().inFire(), 0.5f);

            if (mainBurning) {
                main.damage(2, player, EquipmentSlot.MAINHAND);
                if (main.isEmpty() && !offBurning) {
                    COAL_TOOL_BURNING.remove(uuid);
                    return;
                }
            }
            if (offBurning) {
                off.damage(2, player, EquipmentSlot.OFFHAND);
                if (off.isEmpty() && !mainBurning) {
                    COAL_TOOL_BURNING.remove(uuid);
                    return;
                }
            }
        }

        // Smoke particles from the held burning item
        if (player.age % 4 == 0 && player.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.SMOKE,
                    player.getX(), player.getY() + 1.2, player.getZ(),
                    1, 0.15, 0.1, 0.15, 0.01);
        }
    }

    // -----------------------------------------------------------------------
    // Coal armor burning (worn by a player)
    // -----------------------------------------------------------------------

    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
    };

    private static void handleCoalArmorBurning(PlayerEntity player) {
        UUID uuid = player.getUuid();

        // Water extinguishes worn armor burning
        if (player.isTouchingWater()) {
            if (COAL_ARMOR_BURNING.contains(uuid)) {
                COAL_ARMOR_BURNING.remove(uuid);
                clearArmorBurning(player);
            }
            return;
        }

        // Ignite: player catches fire while wearing coal armor
        if (player.isOnFire() && isWearingAnyCoalArmor(player) && !COAL_ARMOR_BURNING.contains(uuid)) {
            COAL_ARMOR_BURNING.add(uuid);
            for (EquipmentSlot slot : ARMOR_SLOTS) {
                ItemStack piece = player.getEquippedStack(slot);
                if (isCoalArmor(piece)) CoalBurningHelper.setBurning(piece, true);
            }
        }

        if (!COAL_ARMOR_BURNING.contains(uuid)) return;

        // Stop if no burning coal armor is worn anymore
        if (!isWearingAnyBurningCoalArmor(player)) {
            COAL_ARMOR_BURNING.remove(uuid);
            return;
        }

        // Keep player on fire while wearing burning coal armor
        if (player.getFireTicks() < 40) {
            player.setFireTicks(100);
        }

        // Damage player and drain durability once per second
        if (player.age % 20 == 0) {
            player.damage(player.getDamageSources().inFire(), 0.5f);
            for (EquipmentSlot slot : ARMOR_SLOTS) {
                ItemStack piece = player.getEquippedStack(slot);
                if (isCoalArmor(piece) && CoalBurningHelper.isBurning(piece)) {
                    piece.damage(1, player, slot);
                }
            }
        }

        // Smoke particles
        if (player.age % 3 == 0 && player.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.SMOKE,
                    player.getX(), player.getY() + 1.5, player.getZ(),
                    3, 0.3, 0.3, 0.3, 0.01);
        }
    }

    // -----------------------------------------------------------------------
    // Furnace fuel values for coal items
    // -----------------------------------------------------------------------

    private static void registerFuelValues() {
        FuelRegistry.INSTANCE.add(ModItems.COAL_DUST, 200);
        FuelRegistry.INSTANCE.add(ModItems.HARDENED_COAL, 400);
        FuelRegistry.INSTANCE.add(ModItems.COAL_SWORD, 800);
        FuelRegistry.INSTANCE.add(ModItems.COAL_PICKAXE, 1200);
        FuelRegistry.INSTANCE.add(ModItems.COAL_SHOVEL, 400);
        FuelRegistry.INSTANCE.add(ModItems.COAL_AXE, 1200);
        FuelRegistry.INSTANCE.add(ModItems.COAL_HOE, 800);
        FuelRegistry.INSTANCE.add(ModItems.COAL_HELMET, 2000);
        FuelRegistry.INSTANCE.add(ModItems.COAL_CHESTPLATE, 3200);
        FuelRegistry.INSTANCE.add(ModItems.COAL_LEGGINGS, 2800);
        FuelRegistry.INSTANCE.add(ModItems.COAL_BOOTS, 1600);
    }

    // -----------------------------------------------------------------------
    // Snow melt mechanics
    // -----------------------------------------------------------------------

    private static void handleSnowMelt(PlayerEntity player) {
        if (!(player.getWorld() instanceof ServerWorld serverWorld)) return;

        // Snow tools melt every 40 ticks when held
        if (player.age % 40 == 0) {
            ItemStack main = player.getMainHandStack();
            ItemStack off = player.getOffHandStack();
            boolean melted = false;
            if (isSnowTool(main)) { main.damage(1, player, EquipmentSlot.MAINHAND); melted = true; }
            if (isSnowTool(off)) { off.damage(1, player, EquipmentSlot.OFFHAND); melted = true; }
            if (melted) spawnMeltParticles(player, serverWorld);
        }
    }

    // -----------------------------------------------------------------------
    // Snow fire protection — absorbs fire at heavy durability cost
    // -----------------------------------------------------------------------

    private static void handleSnowFireProtection(PlayerEntity player) {
        if (!(player.getWorld() instanceof ServerWorld serverWorld)) return;
        if (!player.isOnFire()) return;

        ItemStack main = player.getMainHandStack();
        ItemStack off = player.getOffHandStack();

        boolean hasProtector = isSnowTool(main) || isSnowTool(off);
        if (!hasProtector) return;

        player.extinguish();

        if (player.age % 20 == 0) {
            if (isSnowTool(main)) main.damage(5, player, EquipmentSlot.MAINHAND);
            if (isSnowTool(off)) off.damage(5, player, EquipmentSlot.OFFHAND);
        }

        if (player.age % 8 == 0) {
            serverWorld.spawnParticles(ParticleTypes.FALLING_WATER,
                    player.getX(), player.getY() + 1.0, player.getZ(),
                    6, 0.4, 0.4, 0.4, 0.05);
        }
    }

    // -----------------------------------------------------------------------
    // Ice melt mechanics
    // -----------------------------------------------------------------------

    private static void handleIceMelt(PlayerEntity player) {
        if (!(player.getWorld() instanceof ServerWorld serverWorld)) return;

        // Ice tools + armor melt every 60 ticks
        if (player.age % 60 == 0) {
            ItemStack main = player.getMainHandStack();
            ItemStack off = player.getOffHandStack();
            boolean melted = false;
            if (isIceTool(main)) { main.damage(1, player, EquipmentSlot.MAINHAND); melted = true; }
            if (isIceTool(off)) { off.damage(1, player, EquipmentSlot.OFFHAND); melted = true; }

            for (EquipmentSlot slot : ARMOR_SLOTS) {
                ItemStack piece = player.getEquippedStack(slot);
                if (isIceArmor(piece)) {
                    piece.damage(1, player, slot);
                    melted = true;
                    // On break: 10% chance to place a water source at the player's feet
                    if (piece.isEmpty() && serverWorld.random.nextFloat() < 0.1f) {
                        BlockPos feet = player.getBlockPos();
                        if (serverWorld.getBlockState(feet).isAir()) {
                            serverWorld.setBlockState(feet, Blocks.WATER.getDefaultState(), 3);
                        }
                    }
                }
            }
            if (melted) spawnMeltParticles(player, serverWorld);
        }
    }

    private static void spawnMeltParticles(PlayerEntity player, ServerWorld world) {
        world.spawnParticles(ParticleTypes.FALLING_WATER,
                player.getX(), player.getY() + 1.2, player.getZ(),
                4, 0.3, 0.3, 0.3, 0.02);
    }

    // -----------------------------------------------------------------------
    // Ice fire protection — absorbs fire at heavy durability cost
    // -----------------------------------------------------------------------

    private static void handleIceFireProtection(PlayerEntity player) {
        if (!(player.getWorld() instanceof ServerWorld serverWorld)) return;
        if (!player.isOnFire()) return;

        ItemStack main = player.getMainHandStack();
        ItemStack off = player.getOffHandStack();

        boolean hasProtector = isIceTool(main) || isIceTool(off);
        if (!hasProtector) {
            for (EquipmentSlot slot : ARMOR_SLOTS) {
                if (isIceArmor(player.getEquippedStack(slot))) { hasProtector = true; break; }
            }
        }
        if (!hasProtector) return;

        player.extinguish();

        if (player.age % 20 == 0) {
            if (isIceTool(main)) main.damage(5, player, EquipmentSlot.MAINHAND);
            if (isIceTool(off)) off.damage(5, player, EquipmentSlot.OFFHAND);
            for (EquipmentSlot slot : ARMOR_SLOTS) {
                ItemStack piece = player.getEquippedStack(slot);
                if (isIceArmor(piece)) piece.damage(5, player, slot);
            }
        }

        if (player.age % 8 == 0) {
            serverWorld.spawnParticles(ParticleTypes.FALLING_WATER,
                    player.getX(), player.getY() + 1.0, player.getZ(),
                    6, 0.4, 0.4, 0.4, 0.05);
        }
    }

    // -----------------------------------------------------------------------
    // Polished Prismarine water benefits
    // -----------------------------------------------------------------------

    private static void handlePprismWaterEffects(PlayerEntity player) {
        if (!player.isTouchingWater()) return;

        // Pprism tool in hand -> Haste I (cancels underwater mining penalty)
        if (isPprismTool(player.getMainHandStack()) || isPprismTool(player.getOffHandStack())) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 60, 0, false, false, false));
        }

        // Per-slot armor benefits while in water
        // Helmet -> Water Breathing
        if (isPprismArmor(player.getEquippedStack(EquipmentSlot.HEAD))) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 60, 0, false, false, false));
        }
        // Chestplate -> Resistance I (ocean's protective pressure)
        if (isPprismArmor(player.getEquippedStack(EquipmentSlot.CHEST))) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, false));
        }
        // Leggings -> Haste I (removes underwater mining penalty)
        if (isPprismArmor(player.getEquippedStack(EquipmentSlot.LEGS))) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 60, 0, false, false, false));
        }
        // Boots -> Slow Falling (buoyancy — rise through water effortlessly)
        if (isPprismArmor(player.getEquippedStack(EquipmentSlot.FEET))) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 0, false, false, false));
        }

        // Full 4-piece -> Dolphins Grace
        boolean fullSet = isPprismArmor(player.getEquippedStack(EquipmentSlot.HEAD))
                && isPprismArmor(player.getEquippedStack(EquipmentSlot.CHEST))
                && isPprismArmor(player.getEquippedStack(EquipmentSlot.LEGS))
                && isPprismArmor(player.getEquippedStack(EquipmentSlot.FEET));
        if (fullSet) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 60, 0, false, false, false));
        }
    }

    // -----------------------------------------------------------------------
    // FNI fire mechanics
    // -----------------------------------------------------------------------

    /**
     * Sneak + right-click a block while holding any Flint-Iron tool in the main hand
     * to start a fire on the clicked face (identical behaviour to vanilla Flint and Steel).
     * Consumes 1 durability per use.
     */
    private static ActionResult onUseBlock(PlayerEntity player, World world, Hand hand,
                                            net.minecraft.util.hit.BlockHitResult hitResult) {
        if (!Config.fniEnabled || !Config.fniFireEffects) return ActionResult.PASS;
        if (!player.isSneaking()) return ActionResult.PASS;
        if (hand != Hand.MAIN_HAND) return ActionResult.PASS;

        ItemStack held = player.getMainHandStack();
        if (!isFniTool(held)) return ActionResult.PASS;

        if (world.isClient()) return ActionResult.PASS;

        BlockPos clickedPos = hitResult.getBlockPos();
        Direction face = hitResult.getSide();

        BlockPos firePos = clickedPos.offset(face);
        BlockState target = world.getBlockState(firePos);
        if (target.isAir() || target.isReplaceable()) {
            world.setBlockState(firePos, AbstractFireBlock.getState(world, firePos), 11);
            world.playSound(null, firePos, SoundEvents.ITEM_FLINTANDSTEEL_USE,
                    SoundCategory.BLOCKS, 1.0f, world.random.nextFloat() * 0.4f + 0.8f);
            held.damage(1, player, EquipmentSlot.MAINHAND);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    /**
     * FNI boots: small chance (4% per 10 ticks) to ignite flammable blocks underfoot.
     * Fire appears beside the player to avoid immediately burning them.
     */
    private static void handleFniBootsFire(PlayerEntity player) {
        if (!player.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.FNI_BOOTS)) return;
        if (player.age % 10 != 0) return;
        if (!player.isOnGround()) return;

        World world = player.getWorld();
        BlockPos feetPos = player.getBlockPos();
        BlockState floorState = world.getBlockState(feetPos.down());

        if (((FireBlock) Blocks.FIRE).getBurnChance(floorState) > 0
                && world.random.nextFloat() < 0.04f) {
            Direction[] dirs = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
            Direction randDir = dirs[world.random.nextInt(4)];
            BlockPos firePos = feetPos.offset(randDir);
            if (world.getBlockState(firePos).isAir()) {
                world.setBlockState(firePos, AbstractFireBlock.getState(world, firePos), 11);
            }
        }
    }

    /**
     * Full FNI set bonus: drains fire ticks one extra per tick on top of vanilla's own
     * decrement, cutting effective burn duration roughly in half.
     */
    private static void handleFniArmorBonus(PlayerEntity player) {
        if (!isWearingFullFniArmor(player)) return;
        int fireTicks = player.getFireTicks();
        if (fireTicks > 0) {
            player.setFireTicks(fireTicks - 1);
        }
    }

    private static boolean isWearingFullFniArmor(PlayerEntity player) {
        return player.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.FNI_HELMET)
            && player.getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.FNI_CHESTPLATE)
            && player.getEquippedStack(EquipmentSlot.LEGS).isOf(ModItems.FNI_LEGGINGS)
            && player.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.FNI_BOOTS);
    }

    // -----------------------------------------------------------------------
    // Item tooltips (client-only registration)
    // -----------------------------------------------------------------------

    private static void registerClientTooltips() {
        net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback.EVENT.register(ModEvents::onItemTooltip);
    }

    private static void onItemTooltip(ItemStack stack, Item.TooltipContext context,
                                       TooltipType tooltipType, List<Text> tips) {
        // --- Ectoplasm-infused items ---
        if (EctoplasmInfusionHelper.isInfused(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.ectoplasm_infused")
                    .formatted(Formatting.DARK_AQUA));
            if (stack.getItem() instanceof ArmorItem) {
                tips.add(Text.translatable("tooltip.usefultoolsmod.ecto_armor_invisibility")
                        .formatted(Formatting.DARK_GREEN));
            } else {
                tips.add(Text.translatable("tooltip.usefultoolsmod.ecto_can_damage_ghosts")
                        .formatted(Formatting.GRAY));
            }
            if (stack.getItem() instanceof PickaxeItem) {
                tips.add(Text.translatable("tooltip.usefultoolsmod.spectral_sight")
                        .formatted(Formatting.GRAY));
            } else if (stack.getItem() instanceof ShovelItem) {
                tips.add(Text.translatable("tooltip.usefultoolsmod.spectral_efficiency")
                        .formatted(Formatting.GRAY));
            } else if (stack.getItem() instanceof HoeItem) {
                tips.add(Text.translatable("tooltip.usefultoolsmod.spectral_fortune")
                        .formatted(Formatting.GRAY));
            }
        }

        // --- Coal tools / armor ---
        if (isCoalTool(stack) || isCoalArmor(stack)) {
            if (CoalBurningHelper.isBurning(stack)) {
                tips.add(Text.translatable("tooltip.usefultoolsmod.burning")
                        .formatted(Formatting.RED));
                double drainPerSecond = isCoalTool(stack) ? 2.0 : 1.0;
                addTimeRemaining(tips, stack, drainPerSecond);
            } else {
                tips.add(Text.translatable("tooltip.usefultoolsmod.flammable")
                        .formatted(Formatting.GOLD));
            }
        }

        // --- Snow tools ---
        if (isSnowTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.melts_when_held")
                    .formatted(Formatting.AQUA));
            tips.add(Text.translatable("tooltip.usefultoolsmod.fire_protection")
                    .formatted(Formatting.GRAY));
            addTimeRemaining(tips, stack, 0.5);
        }

        // --- Ice tools ---
        if (isIceTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.melts_when_held")
                    .formatted(Formatting.AQUA));
            tips.add(Text.translatable("tooltip.usefultoolsmod.fire_protection")
                    .formatted(Formatting.GRAY));
            addTimeRemaining(tips, stack, 1.0 / 3.0);
        }

        // --- Ice armor ---
        if (isIceArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.melts_when_worn")
                    .formatted(Formatting.AQUA));
            tips.add(Text.translatable("tooltip.usefultoolsmod.ice_armor_fire_prot")
                    .formatted(Formatting.GRAY));
            tips.add(Text.translatable("tooltip.usefultoolsmod.ice_armor_water")
                    .formatted(Formatting.GRAY));
            addTimeRemaining(tips, stack, 1.0 / 3.0);
        }

        // --- OP tools ---
        if (isOpTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.op_header")
                    .formatted(Formatting.LIGHT_PURPLE));
            String key = stack.isOf(ModItems.OVERPOWER_SWORD) ? "op_sword"
                    : stack.isOf(ModItems.OVERPOWER_PICKAXE) ? "op_pickaxe"
                    : stack.isOf(ModItems.OVERPOWER_SHOVEL) ? "op_shovel" : "op_axe";
            tips.add(Text.translatable("tooltip.usefultoolsmod." + key)
                    .formatted(Formatting.GRAY));
        }

        // --- OP armor ---
        if (isOpArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.op_header")
                    .formatted(Formatting.LIGHT_PURPLE));
            tips.add(Text.translatable("tooltip.usefultoolsmod.op_armor")
                    .formatted(Formatting.GRAY));
        }

        // --- Polished Prismarine tools ---
        if (isPprismTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.pprism_header")
                    .formatted(Formatting.DARK_AQUA));
            tips.add(Text.translatable("tooltip.usefultoolsmod.pprism_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Polished Prismarine armor ---
        if (isPprismArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.pprism_header")
                    .formatted(Formatting.DARK_AQUA));
            addArmorSlotTooltip(tips, stack, "pprism");
            tips.add(Text.translatable("tooltip.usefultoolsmod.pprism_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- FNI tools ---
        if (isFniTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.fni_header")
                    .formatted(Formatting.GOLD));
            tips.add(Text.translatable("tooltip.usefultoolsmod.fni_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- FNI armor ---
        if (isFniArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.fni_header")
                    .formatted(Formatting.GOLD));
            if (stack.getItem() instanceof ArmorItem a) {
                if (a.getSlotType() == EquipmentSlot.FEET) {
                    tips.add(Text.translatable("tooltip.usefultoolsmod.fni_boots")
                            .formatted(Formatting.GRAY));
                }
            }
            tips.add(Text.translatable("tooltip.usefultoolsmod.fni_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Ecto armor ---
        if (isEctoArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.ecto_header")
                    .formatted(Formatting.DARK_AQUA));
            tips.add(Text.translatable("tooltip.usefultoolsmod.ecto_ghost_avoid")
                    .formatted(Formatting.DARK_GREEN));
            tips.add(Text.translatable("tooltip.usefultoolsmod.ecto_wall_phase")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Cake armor ---
        if (isCakeArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.cake_header")
                    .formatted(Formatting.LIGHT_PURPLE));
            addArmorSlotTooltip(tips, stack, "cake");
            tips.add(Text.translatable("tooltip.usefultoolsmod.cake_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Food tools (on-hit effects) ---
        addFoodToolTooltip(tips, stack);

        // --- Food armor (per-slot + full set) ---
        addFoodArmorTooltip(tips, stack);

        // --- All food tools/armor: edible + hunger drain ---
        if ((isFoodTool(stack) || isFoodArmor(stack)) && stack.isDamageable()) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.food_edible")
                    .formatted(Formatting.GREEN));
            tips.add(Text.translatable("tooltip.usefultoolsmod.food_hunger_drain")
                    .formatted(Formatting.GRAY));
        }

        // --- Grenade ---
        if (stack.isOf(ModItems.GRENADE)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.grenade_throw")
                    .formatted(Formatting.RED));
            tips.add(Text.translatable("tooltip.usefultoolsmod.grenade_radius")
                    .formatted(Formatting.GRAY));
        }

        // --- Dynamite ---
        if (stack.isOf(ModItems.DYNAMITE)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.dynamite_use")
                    .formatted(Formatting.DARK_RED));
            tips.add(Text.translatable("tooltip.usefultoolsmod.dynamite_radius")
                    .formatted(Formatting.GRAY));
            tips.add(Text.translatable("tooltip.usefultoolsmod.dynamite_warning")
                    .formatted(Formatting.RED));
        }

        // --- Paper tools ---
        if (isPaperTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.paper_header")
                    .formatted(Formatting.WHITE));
            tips.add(Text.translatable("tooltip.usefultoolsmod.paper_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Feather tools ---
        if (isFeatherTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.feather_header")
                    .formatted(Formatting.WHITE));
            tips.add(Text.translatable("tooltip.usefultoolsmod.feather_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Glass tools ---
        if (isGlassTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.glass_header")
                    .formatted(Formatting.AQUA));
            tips.add(Text.translatable("tooltip.usefultoolsmod.glass_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Sponge tools ---
        if (isSpongeTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.sponge_header")
                    .formatted(Formatting.YELLOW));
            tips.add(Text.translatable("tooltip.usefultoolsmod.sponge_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Nether Wart tools ---
        if (isNetherWartTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.nether_wart_header")
                    .formatted(Formatting.DARK_RED));
            tips.add(Text.translatable("tooltip.usefultoolsmod.nether_wart_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Pointed Dripstone tools ---
        if (isDripstoneTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.dripstone_header")
                    .formatted(Formatting.GOLD));
            tips.add(Text.translatable("tooltip.usefultoolsmod.dripstone_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Rabbit Hide armor ---
        if (isRabbitHideArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.rabbit_hide_header")
                    .formatted(Formatting.GOLD));
            addArmorSlotTooltip(tips, stack, "rabbit_hide");
            tips.add(Text.translatable("tooltip.usefultoolsmod.rabbit_hide_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Cactus tools ---
        if (isCactusTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.cactus_header")
                    .formatted(Formatting.GREEN));
            tips.add(Text.translatable("tooltip.usefultoolsmod.cactus_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Cactus armor ---
        if (isCactusArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.cactus_header")
                    .formatted(Formatting.GREEN));
            tips.add(Text.translatable("tooltip.usefultoolsmod.cactus_armor")
                    .formatted(Formatting.GRAY));
            tips.add(Text.translatable("tooltip.usefultoolsmod.cactus_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Bone tools ---
        if (isBoneTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.bone_header")
                    .formatted(Formatting.WHITE));
            tips.add(Text.translatable("tooltip.usefultoolsmod.bone_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Bone armor ---
        if (isBoneArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.bone_header")
                    .formatted(Formatting.WHITE));
            addArmorSlotTooltip(tips, stack, "bone");
            tips.add(Text.translatable("tooltip.usefultoolsmod.bone_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Clay armor ---
        if (isClayArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.clay_header")
                    .formatted(Formatting.GOLD));
            addArmorSlotTooltip(tips, stack, "clay");
            tips.add(Text.translatable("tooltip.usefultoolsmod.clay_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Nether Brick tools ---
        if (isNetherBrickTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.nether_brick_header")
                    .formatted(Formatting.DARK_RED));
            tips.add(Text.translatable("tooltip.usefultoolsmod.nether_brick_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Nether Brick armor ---
        if (isNetherBrickArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.nether_brick_header")
                    .formatted(Formatting.DARK_RED));
            tips.add(Text.translatable("tooltip.usefultoolsmod.nether_brick_armor")
                    .formatted(Formatting.GRAY));
            tips.add(Text.translatable("tooltip.usefultoolsmod.nether_brick_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Copper armor ---
        if (isCopperArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.copper_header")
                    .formatted(Formatting.GOLD));
            addArmorSlotTooltip(tips, stack, "copper");
            tips.add(Text.translatable("tooltip.usefultoolsmod.copper_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Phantom tools ---
        if (isPhantomTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.phantom_header")
                    .formatted(Formatting.GRAY));
            tips.add(Text.translatable("tooltip.usefultoolsmod.phantom_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Phantom armor ---
        if (isPhantomArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.phantom_header")
                    .formatted(Formatting.GRAY));
            addArmorSlotTooltip(tips, stack, "phantom");
            tips.add(Text.translatable("tooltip.usefultoolsmod.phantom_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Magma Cream tools ---
        if (isMagmaCreamTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.magma_cream_header")
                    .formatted(Formatting.RED));
            tips.add(Text.translatable("tooltip.usefultoolsmod.magma_cream_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Magma Cream armor ---
        if (isMagmaCreamArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.magma_cream_header")
                    .formatted(Formatting.RED));
            tips.add(Text.translatable("tooltip.usefultoolsmod.magma_cream_armor")
                    .formatted(Formatting.GRAY));
            tips.add(Text.translatable("tooltip.usefultoolsmod.magma_cream_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Slime tools ---
        if (isSlimeTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.slime_header")
                    .formatted(Formatting.GREEN));
            tips.add(Text.translatable("tooltip.usefultoolsmod.slime_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Slime armor ---
        if (isSlimeArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.slime_header")
                    .formatted(Formatting.GREEN));
            addArmorSlotTooltip(tips, stack, "slime");
            tips.add(Text.translatable("tooltip.usefultoolsmod.slime_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Blaze tools ---
        if (isBlazeTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.blaze_header")
                    .formatted(Formatting.GOLD));
            tips.add(Text.translatable("tooltip.usefultoolsmod.blaze_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Blaze armor ---
        if (isBlazeArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.blaze_header")
                    .formatted(Formatting.GOLD));
            tips.add(Text.translatable("tooltip.usefultoolsmod.blaze_armor")
                    .formatted(Formatting.GRAY));
            tips.add(Text.translatable("tooltip.usefultoolsmod.blaze_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Nautilus tools ---
        if (isNautilusTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.nautilus_header")
                    .formatted(Formatting.DARK_AQUA));
            tips.add(Text.translatable("tooltip.usefultoolsmod.nautilus_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Nautilus armor ---
        if (isNautilusArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.nautilus_header")
                    .formatted(Formatting.DARK_AQUA));
            addArmorSlotTooltip(tips, stack, "nautilus");
            tips.add(Text.translatable("tooltip.usefultoolsmod.nautilus_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Purpur tools ---
        if (isPurpurTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.purpur_header")
                    .formatted(Formatting.LIGHT_PURPLE));
            tips.add(Text.translatable("tooltip.usefultoolsmod.purpur_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Purpur armor ---
        if (isPurpurArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.purpur_header")
                    .formatted(Formatting.LIGHT_PURPLE));
            addArmorSlotTooltip(tips, stack, "purpur");
            tips.add(Text.translatable("tooltip.usefultoolsmod.purpur_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Ghast Tear tools ---
        if (isGhastTearTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.ghast_tear_header")
                    .formatted(Formatting.WHITE));
            tips.add(Text.translatable("tooltip.usefultoolsmod.ghast_tear_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Ghast Tear armor ---
        if (isGhastTearArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.ghast_tear_header")
                    .formatted(Formatting.WHITE));
            addArmorSlotTooltip(tips, stack, "ghast_tear");
            tips.add(Text.translatable("tooltip.usefultoolsmod.ghast_tear_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Eye of Ender tools ---
        if (isEyeOfEnderTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.eye_of_ender_header")
                    .formatted(Formatting.GREEN));
            tips.add(Text.translatable("tooltip.usefultoolsmod.eye_of_ender_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Eye of Ender armor ---
        if (isEyeOfEnderArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.eye_of_ender_header")
                    .formatted(Formatting.GREEN));
            addArmorSlotTooltip(tips, stack, "eye_of_ender");
            tips.add(Text.translatable("tooltip.usefultoolsmod.eye_of_ender_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Shulker tools ---
        if (isShulkerTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.shulker_header")
                    .formatted(Formatting.LIGHT_PURPLE));
            tips.add(Text.translatable("tooltip.usefultoolsmod.shulker_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Shulker armor ---
        if (isShulkerArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.shulker_header")
                    .formatted(Formatting.LIGHT_PURPLE));
            addArmorSlotTooltip(tips, stack, "shulker");
            tips.add(Text.translatable("tooltip.usefultoolsmod.shulker_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Turtle Scute armor ---
        if (isTurtleScuteArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.turtle_scute_header")
                    .formatted(Formatting.GREEN));
            addArmorSlotTooltip(tips, stack, "turtle_scute");
            tips.add(Text.translatable("tooltip.usefultoolsmod.turtle_scute_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Echo Shard tools ---
        if (isEchoShardTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.echo_shard_header")
                    .formatted(Formatting.DARK_AQUA));
            tips.add(Text.translatable("tooltip.usefultoolsmod.echo_shard_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Echo Shard armor ---
        if (isEchoShardArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.echo_shard_header")
                    .formatted(Formatting.DARK_AQUA));
            addArmorSlotTooltip(tips, stack, "echo_shard");
            tips.add(Text.translatable("tooltip.usefultoolsmod.echo_shard_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }

        // --- Dragon's Breath tools ---
        if (isDragonBreathTool(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.dragon_breath_header")
                    .formatted(Formatting.DARK_PURPLE));
            tips.add(Text.translatable("tooltip.usefultoolsmod.dragon_breath_tool")
                    .formatted(Formatting.GRAY));
        }

        // --- Dragon's Breath armor ---
        if (isDragonBreathArmor(stack)) {
            tips.add(Text.translatable("tooltip.usefultoolsmod.dragon_breath_header")
                    .formatted(Formatting.DARK_PURPLE));
            addArmorSlotTooltip(tips, stack, "dragon_breath");
            tips.add(Text.translatable("tooltip.usefultoolsmod.dragon_breath_full_set")
                    .formatted(Formatting.DARK_GREEN));
        }
    }

    private static void addArmorSlotTooltip(List<Text> tips, ItemStack stack, String prefix) {
        if (!(stack.getItem() instanceof ArmorItem armor)) return;
        String slot = switch (armor.getSlotType()) {
            case FEET -> "boots";
            case LEGS -> "leggings";
            case CHEST -> "chestplate";
            case HEAD -> "helmet";
            default -> null;
        };
        if (slot != null) {
            tips.add(Text.translatable("tooltip.usefultoolsmod." + prefix + "_" + slot)
                    .formatted(Formatting.GRAY));
        }
    }

    private static void addFoodToolTooltip(List<Text> tips, ItemStack stack) {
        String key = null;
        if (isHoneyTool(stack)) key = "honey_tool";
        else if (isPufferfishTool(stack)) key = "pufferfish_tool";
        else if (isSweetBerryTool(stack)) key = "sweet_berry_tool";
        else if (isRottenFleshTool(stack)) key = "rotten_flesh_tool";
        else if (isMushroomTool(stack)) key = "mushroom_tool";
        else if (isChorusFruitTool(stack)) key = "chorus_fruit_tool";
        if (key != null) {
            tips.add(Text.translatable("tooltip.usefultoolsmod." + key)
                    .formatted(Formatting.GRAY));
        }
    }

    private static void addFoodArmorTooltip(List<Text> tips, ItemStack stack) {
        String prefix = null;
        Formatting color = Formatting.YELLOW;
        if (isBreadArmor(stack)) prefix = "bread";
        else if (isDriedKelpArmor(stack)) { prefix = "dried_kelp"; color = Formatting.GREEN; }
        else if (isRottenFleshArmor(stack)) { prefix = "rotten_flesh"; color = Formatting.DARK_RED; }
        else if (isMelonArmor(stack)) { prefix = "melon"; color = Formatting.GREEN; }
        else if (isSweetBerryArmor(stack)) { prefix = "sweet_berry"; color = Formatting.RED; }
        else if (isPumpkinPieArmor(stack)) { prefix = "pumpkin_pie"; color = Formatting.GOLD; }
        else if (isMushroomArmor(stack)) { prefix = "mushroom"; color = Formatting.RED; }
        else if (isPufferfishArmor(stack)) { prefix = "pufferfish"; color = Formatting.YELLOW; }
        else if (isHoneyArmor(stack)) { prefix = "honey"; color = Formatting.GOLD; }
        else if (isChorusFruitArmor(stack)) { prefix = "chorus_fruit"; color = Formatting.LIGHT_PURPLE; }
        else if (isGoldenAppleArmor(stack)) { prefix = "golden_apple"; color = Formatting.GOLD; }
        if (prefix == null) return;

        addArmorSlotTooltip(tips, stack, prefix);
        tips.add(Text.translatable("tooltip.usefultoolsmod." + prefix + "_full_set")
                .formatted(Formatting.DARK_GREEN));
    }

    private static void addTimeRemaining(List<Text> tips, ItemStack stack, double drainPerSecond) {
        if (!stack.isDamageable()) return;
        int remaining = stack.getMaxDamage() - stack.getDamage();
        int totalSeconds = (int) Math.ceil(remaining / drainPerSecond);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        String time;
        if (minutes > 0) {
            time = minutes + "m " + seconds + "s";
        } else {
            time = seconds + "s";
        }

        tips.add(Text.translatable("tooltip.usefultoolsmod.time_remaining", time)
                .formatted(Formatting.GRAY));
    }

    // -----------------------------------------------------------------------
    // Ectoplasm wall phasing
    // -----------------------------------------------------------------------

    /** Tracks which players are currently phasing through walls. */
    private static final Set<UUID> PHASING_PLAYERS = new HashSet<>();

    /** Cooldown to prevent spamming the phase teleport (ticks). */
    private static final Map<UUID, Integer> PHASE_COOLDOWNS = new HashMap<>();

    /**
     * Full ecto armor set allows the player to phase through walls <= 3 blocks thick.
     * Sneak while walking into a wall to teleport to the other side.
     */
    private static void handleEctoWallPhasing(PlayerEntity player) {
        boolean hasFullSet = hasFullEctoArmorSet(player);
        if (!hasFullSet || !player.isSneaking()) return;

        UUID uuid = player.getUuid();
        int cooldown = PHASE_COOLDOWNS.getOrDefault(uuid, 0);
        if (cooldown > 0) {
            PHASE_COOLDOWNS.put(uuid, cooldown - 1);
            return;
        }

        World world = player.getWorld();
        BlockPos feetPos = player.getBlockPos();
        Direction dir = getHorizontalLookDirection(player);
        BlockPos ahead = feetPos.offset(dir);

        // Check if the player is pushing into a wall (block directly ahead is solid)
        if (!hasCollision(world, ahead) && !hasCollision(world, ahead.up())) {
            return; // No wall ahead
        }

        // Measure wall thickness and find the exit position
        int thickness = 0;
        BlockPos exitPos = null;
        for (int i = 1; i <= 3; i++) {
            BlockPos check = feetPos.offset(dir, i);
            if (hasCollision(world, check) || hasCollision(world, check.up())) {
                thickness++;
            } else {
                // Found open air — this is the exit
                // Also verify head clearance
                if (!hasCollision(world, check.up())) {
                    exitPos = check;
                }
                break;
            }
        }

        if (exitPos == null || thickness == 0) return;

        // Teleport to the center of the exit block
        double tx = exitPos.getX() + 0.5;
        double ty = exitPos.getY();
        double tz = exitPos.getZ() + 0.5;
        player.requestTeleport(tx, ty, tz);

        // Effects
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 0, false, false, true));

        if (world instanceof ServerWorld serverWorld) {
            // Particles at entry
            serverWorld.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME,
                    player.prevX, player.prevY + 1.0, player.prevZ,
                    8, 0.3, 0.5, 0.3, 0.02);
            // Particles at exit
            serverWorld.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME,
                    tx, ty + 1.0, tz,
                    8, 0.3, 0.5, 0.3, 0.02);
        }

        // Cooldown: 10 ticks (half second) before next phase
        PHASE_COOLDOWNS.put(uuid, 10);
    }

    /** Check if player is wearing full ecto armor set. */
    private static boolean hasFullEctoArmorSet(PlayerEntity player) {
        return isEctoArmor(player.getEquippedStack(EquipmentSlot.HEAD))
            && isEctoArmor(player.getEquippedStack(EquipmentSlot.CHEST))
            && isEctoArmor(player.getEquippedStack(EquipmentSlot.LEGS))
            && isEctoArmor(player.getEquippedStack(EquipmentSlot.FEET));
    }

    private static Direction getHorizontalLookDirection(PlayerEntity player) {
        float yaw = player.getYaw();
        double dx = -Math.sin(Math.toRadians(yaw));
        double dz = Math.cos(Math.toRadians(yaw));
        if (Math.abs(dx) > Math.abs(dz)) {
            return dx > 0 ? Direction.EAST : Direction.WEST;
        } else {
            return dz > 0 ? Direction.SOUTH : Direction.NORTH;
        }
    }

    private static boolean hasCollision(World world, BlockPos pos) {
        return !world.getBlockState(pos).getCollisionShape(world, pos).isEmpty();
    }

    // -----------------------------------------------------------------------
    // Generalized food hunger drain (all food sets including cake)
    // -----------------------------------------------------------------------

    /** All food armor materials for hunger drain scanning. */
    private static List<RegistryEntry<ArmorMaterial>> FOOD_ARMOR_MATERIALS;
    private static List<RegistryEntry<ArmorMaterial>> getFoodArmorMaterials() {
        if (FOOD_ARMOR_MATERIALS == null) {
            FOOD_ARMOR_MATERIALS = List.of(
                    ModArmorMaterials.CAKE_ARMOR_MATERIAL,
                    ModArmorMaterials.BREAD_ARMOR_MATERIAL,
                    ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL,
                    ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL,
                    ModArmorMaterials.MELON_ARMOR_MATERIAL,
                    ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL,
                    ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL,
                    ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL,
                    ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL,
                    ModArmorMaterials.HONEY_ARMOR_MATERIAL,
                    ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL,
                    ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL
            );
        }
        return FOOD_ARMOR_MATERIALS;
    }

    private static boolean isFoodTool(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return isCakeTool(stack) || isBreadTool(stack) || isDriedKelpTool(stack)
            || isRottenFleshTool(stack) || isMelonTool(stack) || isSweetBerryTool(stack)
            || isPumpkinPieTool(stack) || isMushroomTool(stack) || isPufferfishTool(stack)
            || isHoneyTool(stack) || isChorusFruitTool(stack) || isGoldenAppleTool(stack);
    }

    private static boolean isFoodArmor(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ArmorItem armor)) return false;
        for (RegistryEntry<ArmorMaterial> mat : getFoodArmorMaterials()) {
            if (armor.getMaterial() == mat) return true;
        }
        return false;
    }

    private static boolean isFoodSetEnabled(ItemStack stack) {
        if (isCakeTool(stack) || isCakeArmor(stack)) return Config.cakeEnabled;
        if (isBreadTool(stack) || isBreadArmor(stack)) return Config.breadEnabled;
        if (isDriedKelpTool(stack) || isDriedKelpArmor(stack)) return Config.driedKelpEnabled;
        if (isRottenFleshTool(stack) || isRottenFleshArmor(stack)) return Config.rottenFleshEnabled;
        if (isMelonTool(stack) || isMelonArmor(stack)) return Config.melonEnabled;
        if (isSweetBerryTool(stack) || isSweetBerryArmor(stack)) return Config.sweetBerryEnabled;
        if (isPumpkinPieTool(stack) || isPumpkinPieArmor(stack)) return Config.pumpkinPieEnabled;
        if (isMushroomTool(stack) || isMushroomArmor(stack)) return Config.mushroomEnabled;
        if (isPufferfishTool(stack) || isPufferfishArmor(stack)) return Config.pufferfishEnabled;
        if (isHoneyTool(stack) || isHoneyArmor(stack)) return Config.honeyEnabled;
        if (isChorusFruitTool(stack) || isChorusFruitArmor(stack)) return Config.chorusFruitEnabled;
        if (isGoldenAppleTool(stack) || isGoldenAppleArmor(stack)) return Config.goldenAppleEnabled;
        return false;
    }

    private static void handleFoodHungerDrain(PlayerEntity player) {
        if (!(player.getWorld() instanceof ServerWorld serverWorld)) return;
        if (player.getHungerManager().getFoodLevel() > 6) return;
        if (player.age % 40 != 0) return;

        ItemStack main = player.getMainHandStack();
        ItemStack off = player.getOffHandStack();
        if (isFoodTool(main) && main.isDamageable() && isFoodSetEnabled(main)) {
            main.damage(1, player, EquipmentSlot.MAINHAND);
            player.getHungerManager().add(1, 0.1f);
            spawnFoodParticles(player, serverWorld);
        }
        if (isFoodTool(off) && off.isDamageable() && isFoodSetEnabled(off)) {
            off.damage(1, player, EquipmentSlot.OFFHAND);
            player.getHungerManager().add(1, 0.1f);
            spawnFoodParticles(player, serverWorld);
        }

        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack piece = player.getEquippedStack(slot);
            if (isFoodArmor(piece) && piece.isDamageable() && isFoodSetEnabled(piece)) {
                piece.damage(1, player, slot);
                player.getHungerManager().add(1, 0.1f);
                spawnFoodParticles(player, serverWorld);
            }
        }
    }

    private static void spawnFoodParticles(PlayerEntity player, ServerWorld world) {
        world.spawnParticles(ParticleTypes.HAPPY_VILLAGER,
                player.getX(), player.getY() + 1.0, player.getZ(),
                5, 0.3, 0.3, 0.3, 0.01);
    }

    private static void handleCakeArmorEffects(PlayerEntity player) {
        // Boots -> Speed I (sugar rush)
        if (isCakeArmor(player.getEquippedStack(EquipmentSlot.FEET))) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        }
        // Leggings -> Jump Boost I (light and fluffy)
        if (isCakeArmor(player.getEquippedStack(EquipmentSlot.LEGS))) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 0, false, false, true));
        }
        // Chestplate -> Regeneration I (comfort food healing)
        if (isCakeArmor(player.getEquippedStack(EquipmentSlot.CHEST))) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 0, false, false, true));
        }
        // Helmet -> Saturation (keeps hunger satisfied)
        if (isCakeArmor(player.getEquippedStack(EquipmentSlot.HEAD))) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 60, 0, false, false, true));
        }

        // Full set -> Absorption I (frosting shield — 2 extra hearts)
        boolean fullSet = isCakeArmor(player.getEquippedStack(EquipmentSlot.HEAD))
                && isCakeArmor(player.getEquippedStack(EquipmentSlot.CHEST))
                && isCakeArmor(player.getEquippedStack(EquipmentSlot.LEGS))
                && isCakeArmor(player.getEquippedStack(EquipmentSlot.FEET));
        if (fullSet) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 60, 0, false, false, true));
        }
    }

    // -----------------------------------------------------------------------
    // Ectoplasm-infused non-weapon tool effects
    // -----------------------------------------------------------------------

    private static void handleInfusedToolEffects(PlayerEntity player) {
        ItemStack main = player.getMainHandStack();
        ItemStack off = player.getOffHandStack();

        applyInfusedEffect(player, main);
        applyInfusedEffect(player, off);
    }

    private static void applyInfusedEffect(PlayerEntity player, ItemStack stack) {
        if (stack.isEmpty() || !EctoplasmInfusionHelper.isInfused(stack)) return;

        // Pickaxe -> Night Vision (spectral sight)
        if (stack.getItem() instanceof PickaxeItem) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 260, 0, false, false, true));
        }
        // Shovel -> Haste I (spectral efficiency)
        else if (stack.getItem() instanceof ShovelItem) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 60, 0, false, false, true));
        }
        // Hoe -> Luck I (spectral fortune)
        else if (stack.getItem() instanceof HoeItem) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 60, 0, false, false, true));
        }
    }

    /**
     * Teleports the player to the nearest safe (non-solid) position above them.
     */
    private static void teleportToSafety(PlayerEntity player, World world) {
        BlockPos pos = player.getBlockPos();

        // Try going up
        for (int y = 0; y < 256; y++) {
            BlockPos check = pos.up(y);
            BlockPos checkHead = check.up();

            boolean feetClear = world.getBlockState(check).getCollisionShape(world, check).isEmpty();
            boolean headClear = world.getBlockState(checkHead).getCollisionShape(world, checkHead).isEmpty();

            if (feetClear && headClear) {
                player.requestTeleport(check.getX() + 0.5, check.getY(), check.getZ() + 0.5);
                return;
            }
        }
    }

    // =======================================================================
    // Food set helpers
    // =======================================================================

    private static boolean isBreadTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.BREAD_SWORD) || s.isOf(ModItems.BREAD_PICKAXE)
            || s.isOf(ModItems.BREAD_SHOVEL) || s.isOf(ModItems.BREAD_AXE) || s.isOf(ModItems.BREAD_HOE));
    }
    private static boolean isBreadArmor(ItemStack s) {
        return !s.isEmpty() && s.getItem() instanceof ArmorItem a && a.getMaterial() == ModArmorMaterials.BREAD_ARMOR_MATERIAL;
    }
    private static boolean isDriedKelpTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.DRIED_KELP_SWORD) || s.isOf(ModItems.DRIED_KELP_PICKAXE)
            || s.isOf(ModItems.DRIED_KELP_SHOVEL) || s.isOf(ModItems.DRIED_KELP_AXE) || s.isOf(ModItems.DRIED_KELP_HOE));
    }
    private static boolean isDriedKelpArmor(ItemStack s) {
        return !s.isEmpty() && s.getItem() instanceof ArmorItem a && a.getMaterial() == ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL;
    }
    private static boolean isRottenFleshTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.ROTTEN_FLESH_SWORD) || s.isOf(ModItems.ROTTEN_FLESH_PICKAXE)
            || s.isOf(ModItems.ROTTEN_FLESH_SHOVEL) || s.isOf(ModItems.ROTTEN_FLESH_AXE) || s.isOf(ModItems.ROTTEN_FLESH_HOE));
    }
    private static boolean isRottenFleshArmor(ItemStack s) {
        return !s.isEmpty() && s.getItem() instanceof ArmorItem a && a.getMaterial() == ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL;
    }
    private static boolean isMelonTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.MELON_SWORD) || s.isOf(ModItems.MELON_PICKAXE)
            || s.isOf(ModItems.MELON_SHOVEL) || s.isOf(ModItems.MELON_AXE) || s.isOf(ModItems.MELON_HOE));
    }
    private static boolean isMelonArmor(ItemStack s) {
        return !s.isEmpty() && s.getItem() instanceof ArmorItem a && a.getMaterial() == ModArmorMaterials.MELON_ARMOR_MATERIAL;
    }
    private static boolean isSweetBerryTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.SWEET_BERRY_SWORD) || s.isOf(ModItems.SWEET_BERRY_PICKAXE)
            || s.isOf(ModItems.SWEET_BERRY_SHOVEL) || s.isOf(ModItems.SWEET_BERRY_AXE) || s.isOf(ModItems.SWEET_BERRY_HOE));
    }
    private static boolean isSweetBerryArmor(ItemStack s) {
        return !s.isEmpty() && s.getItem() instanceof ArmorItem a && a.getMaterial() == ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL;
    }
    private static boolean isPumpkinPieTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.PUMPKIN_PIE_SWORD) || s.isOf(ModItems.PUMPKIN_PIE_PICKAXE)
            || s.isOf(ModItems.PUMPKIN_PIE_SHOVEL) || s.isOf(ModItems.PUMPKIN_PIE_AXE) || s.isOf(ModItems.PUMPKIN_PIE_HOE));
    }
    private static boolean isPumpkinPieArmor(ItemStack s) {
        return !s.isEmpty() && s.getItem() instanceof ArmorItem a && a.getMaterial() == ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL;
    }
    private static boolean isMushroomTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.MUSHROOM_SWORD) || s.isOf(ModItems.MUSHROOM_PICKAXE)
            || s.isOf(ModItems.MUSHROOM_SHOVEL) || s.isOf(ModItems.MUSHROOM_AXE) || s.isOf(ModItems.MUSHROOM_HOE));
    }
    private static boolean isMushroomArmor(ItemStack s) {
        return !s.isEmpty() && s.getItem() instanceof ArmorItem a && a.getMaterial() == ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL;
    }
    private static boolean isPufferfishTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.PUFFERFISH_SWORD) || s.isOf(ModItems.PUFFERFISH_PICKAXE)
            || s.isOf(ModItems.PUFFERFISH_SHOVEL) || s.isOf(ModItems.PUFFERFISH_AXE) || s.isOf(ModItems.PUFFERFISH_HOE));
    }
    private static boolean isPufferfishArmor(ItemStack s) {
        return !s.isEmpty() && s.getItem() instanceof ArmorItem a && a.getMaterial() == ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL;
    }
    private static boolean isHoneyTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.HONEY_SWORD) || s.isOf(ModItems.HONEY_PICKAXE)
            || s.isOf(ModItems.HONEY_SHOVEL) || s.isOf(ModItems.HONEY_AXE) || s.isOf(ModItems.HONEY_HOE));
    }
    private static boolean isHoneyArmor(ItemStack s) {
        return !s.isEmpty() && s.getItem() instanceof ArmorItem a && a.getMaterial() == ModArmorMaterials.HONEY_ARMOR_MATERIAL;
    }
    private static boolean isChorusFruitTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.CHORUS_FRUIT_SWORD) || s.isOf(ModItems.CHORUS_FRUIT_PICKAXE)
            || s.isOf(ModItems.CHORUS_FRUIT_SHOVEL) || s.isOf(ModItems.CHORUS_FRUIT_AXE) || s.isOf(ModItems.CHORUS_FRUIT_HOE));
    }
    private static boolean isChorusFruitArmor(ItemStack s) {
        return !s.isEmpty() && s.getItem() instanceof ArmorItem a && a.getMaterial() == ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL;
    }
    private static boolean isGoldenAppleTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.GOLDEN_APPLE_SWORD) || s.isOf(ModItems.GOLDEN_APPLE_PICKAXE)
            || s.isOf(ModItems.GOLDEN_APPLE_SHOVEL) || s.isOf(ModItems.GOLDEN_APPLE_AXE) || s.isOf(ModItems.GOLDEN_APPLE_HOE));
    }
    private static boolean isGoldenAppleArmor(ItemStack s) {
        return !s.isEmpty() && s.getItem() instanceof ArmorItem a && a.getMaterial() == ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL;
    }

    private static boolean isWearingFullSet(PlayerEntity player, Predicate<ItemStack> check) {
        return check.test(player.getEquippedStack(EquipmentSlot.HEAD))
            && check.test(player.getEquippedStack(EquipmentSlot.CHEST))
            && check.test(player.getEquippedStack(EquipmentSlot.LEGS))
            && check.test(player.getEquippedStack(EquipmentSlot.FEET));
    }

    // =======================================================================
    // Food set armor effects
    // =======================================================================

    // --- Bread: Boots=Speed, Legs=Jump, Chest=Saturation, Helm=Luck, Full=Hunger immunity ---
    private static void handleBreadArmorEffects(PlayerEntity player) {
        if (isBreadArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isBreadArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 0, false, false, true));
        if (isBreadArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 60, 0, false, false, true));
        if (isBreadArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 60, 0, false, false, true));
        if (isWearingFullSet(player, ModEvents::isBreadArmor)) {
            player.removeStatusEffect(StatusEffects.HUNGER);
        }
    }

    // --- Dried Kelp: Boots=Dolphins Grace, Legs=Haste, Chest=Water Breathing, Helm=Night Vision, Full=Conduit Power ---
    private static void handleDriedKelpArmorEffects(PlayerEntity player) {
        if (isDriedKelpArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 60, 0, false, false, true));
        if (isDriedKelpArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 60, 0, false, false, true));
        if (isDriedKelpArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 60, 0, false, false, true));
        if (isDriedKelpArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 260, 0, false, false, true));
        if (isWearingFullSet(player, ModEvents::isDriedKelpArmor))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 60, 0, false, false, true));
    }

    // --- Rotten Flesh: Boots=Slow Falling, Legs=Fire Resist, Chest=Resistance, Helm=Hunger, Full=Undead neutral ---
    private static void handleRottenFleshArmorEffects(PlayerEntity player) {
        if (isRottenFleshArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 0, false, false, true));
        if (isRottenFleshArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 60, 0, false, false, true));
        if (isRottenFleshArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        if (isRottenFleshArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 60, 0, false, false, true));
        // Full set undead neutral via proximity-based check
        if (Config.rottenFleshUndeadNeutral && isWearingFullSet(player, ModEvents::isRottenFleshArmor)
                && player.age % 20 == 0 && player.getWorld() instanceof ServerWorld serverWorld) {
            Box area = player.getBoundingBox().expand(16.0);
            for (MobEntity mob : serverWorld.getEntitiesByClass(MobEntity.class, area, m -> m.getTarget() == player)) {
                if (mob instanceof ZombieEntity || mob instanceof AbstractSkeletonEntity || mob instanceof PhantomEntity) {
                    mob.setTarget(null);
                }
            }
        }
    }

    // --- Melon: Boots=Speed, Legs=Jump, Chest=Regen, Helm=Water Breathing, Full=Passive hunger restore ---
    private static void handleMelonArmorEffects(PlayerEntity player) {
        if (isMelonArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isMelonArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 0, false, false, true));
        if (isMelonArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 0, false, false, true));
        if (isMelonArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 60, 0, false, false, true));
        if (isWearingFullSet(player, ModEvents::isMelonArmor) && player.age % 60 == 0) {
            player.getHungerManager().add(1, 0.1f);
        }
    }

    // --- Sweet Berries: Boots=Speed, Legs=Jump Boost, Chest=Regen, Helm=Saturation, Full=Thorns (event) ---
    private static void handleSweetBerryArmorEffects(PlayerEntity player) {
        if (isSweetBerryArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isSweetBerryArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 0, false, false, true));
        if (isSweetBerryArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 0, false, false, true));
        if (isSweetBerryArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 60, 0, false, false, true));
    }

    // --- Pumpkin Pie: Boots=Speed, Legs=Jump, Chest=Absorption, Helm=Enderman avoid, Full=Luck ---
    private static void handlePumpkinPieArmorEffects(PlayerEntity player) {
        if (isPumpkinPieArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isPumpkinPieArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 0, false, false, true));
        if (isPumpkinPieArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 60, 0, false, false, true));
        if (isWearingFullSet(player, ModEvents::isPumpkinPieArmor))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 60, 0, false, false, true));
        // Pumpkin Pie helmet — endermen ignore player
        if (Config.pumpkinPieEndermanAvoidance && isPumpkinPieArmor(player.getEquippedStack(EquipmentSlot.HEAD))
                && player.age % 20 == 0 && player.getWorld() instanceof ServerWorld serverWorld) {
            Box area = player.getBoundingBox().expand(16.0);
            for (EndermanEntity enderman : serverWorld.getEntitiesByClass(EndermanEntity.class, area,
                    m -> m.getTarget() == player)) {
                enderman.setTarget(null);
            }
        }
    }

    // --- Mushroom: Boots=Haste, Legs=Jump, Chest=Resistance, Helm=Night Vision, Full=Nausea aura ---
    private static void handleMushroomArmorEffects(PlayerEntity player) {
        if (isMushroomArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 60, 0, false, false, true));
        if (isMushroomArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 0, false, false, true));
        if (isMushroomArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        if (isMushroomArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 260, 0, false, false, true));
        if (Config.mushroomSporeCloud && isWearingFullSet(player, ModEvents::isMushroomArmor)
                && player.age % 40 == 0 && player.getWorld() instanceof ServerWorld serverWorld) {
            Box area = player.getBoundingBox().expand(4.0);
            for (MobEntity mob : serverWorld.getEntitiesByClass(MobEntity.class, area, m -> m.getTarget() != null)) {
                mob.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 100, 0, false, false, false));
            }
        }
    }

    // --- Pufferfish: Boots=Water Breathing, Legs=Resistance, Chest=Poison immunity, Helm=Conduit Power, Full=Poison aura ---
    private static void handlePufferfishArmorEffects(PlayerEntity player) {
        if (isPufferfishArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 60, 0, false, false, true));
        if (isPufferfishArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        if (isPufferfishArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.removeStatusEffect(StatusEffects.POISON);
        if (isPufferfishArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 60, 0, false, false, true));
        if (Config.pufferfishPoisonAura && isWearingFullSet(player, ModEvents::isPufferfishArmor)
                && player.age % 40 == 0 && player.getWorld() instanceof ServerWorld serverWorld) {
            Box area = player.getBoundingBox().expand(3.0);
            for (MobEntity mob : serverWorld.getEntitiesByClass(MobEntity.class, area, m -> m.getTarget() != null)) {
                mob.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0, false, false, false));
            }
        }
    }

    // --- Honey: Boots=Slow Falling, Legs=Resistance, Chest=Fire Resist, Helm=Poison immunity, Full=Sticky (event) ---
    private static void handleHoneyArmorEffects(PlayerEntity player) {
        if (isHoneyArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 0, false, false, true));
        if (isHoneyArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        if (isHoneyArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 60, 0, false, false, true));
        if (isHoneyArmor(player.getEquippedStack(EquipmentSlot.HEAD))) {
            player.removeStatusEffect(StatusEffects.POISON);
        }
    }

    // --- Chorus Fruit: Boots=Slow Falling, Legs=Speed, Chest=Resistance, Helm=Night Vision, Full=Teleport dodge (event) ---
    private static void handleChorusFruitArmorEffects(PlayerEntity player) {
        if (isChorusFruitArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 0, false, false, true));
        if (isChorusFruitArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isChorusFruitArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        if (isChorusFruitArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 260, 0, false, false, true));
    }

    // --- Golden Apple: Boots=Speed, Legs=Resistance, Chest=Regen, Helm=Fire Resist, Full=Absorption II ---
    private static void handleGoldenAppleArmorEffects(PlayerEntity player) {
        if (isGoldenAppleArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isGoldenAppleArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        if (isGoldenAppleArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 0, false, false, true));
        if (isGoldenAppleArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 60, 0, false, false, true));
        if (isWearingFullSet(player, ModEvents::isGoldenAppleArmor))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 60, 1, false, false, true));
    }

    // =======================================================================
    // Damage events — offensive tool hits + defensive armor effects
    // =======================================================================

    /**
     * ALLOW_DAMAGE: handles defensive armor effects (chorus fruit teleport dodge).
     * Returns false to cancel damage, true to allow.
     */
    private static boolean onAllowDamage(LivingEntity entity, DamageSource source, float amount) {
        // --- Armor reactive effects (player is the target) ---
        if (entity instanceof PlayerEntity victim && source.getAttacker() instanceof LivingEntity attacker) {
            // Sweet Berry thorns
            if (Config.sweetBerryEnabled && Config.sweetBerryThorns
                    && isWearingFullSet(victim, ModEvents::isSweetBerryArmor)) {
                attacker.damage(victim.getDamageSources().thorns(victim), 1.0f);
            }
            // Honey sticky — attacker gets Slowness II
            if (Config.honeyEnabled && Config.honeySticky
                    && isWearingFullSet(victim, ModEvents::isHoneyArmor)) {
                attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1));
            }
            // Chorus Fruit teleport dodge — 15% chance
            if (Config.chorusFruitEnabled && Config.chorusFruitTeleport
                    && isWearingFullSet(victim, ModEvents::isChorusFruitArmor)) {
                if (victim.getWorld().random.nextFloat() < 0.15f) {
                    teleportRandomly(victim, 3, 8);
                    return false; // Cancel damage
                }
            }
            // Cactus armor — per-piece thorn damage
            if (Config.cactusEnabled && Config.cactusEffects) {
                int cactusPieces = 0;
                for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET})
                    if (isCactusArmor(victim.getEquippedStack(slot))) cactusPieces++;
                if (cactusPieces > 0)
                    attacker.damage(victim.getDamageSources().thorns(victim), cactusPieces * 0.5f);
            }
            // Nether Brick full set — attacker ignited
            if (Config.netherBrickEnabled && Config.netherBrickEffects
                    && isWearingFullSet(victim, ModEvents::isNetherBrickArmor)) {
                attacker.setOnFireFor(2);
            }
            // Magma Cream full set — attacker ignited + Slowness
            if (Config.magmaCreamEnabled && Config.magmaCreamEffects
                    && isWearingFullSet(victim, ModEvents::isMagmaCreamArmor)) {
                attacker.setOnFireFor(3);
                attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 0));
            }
            // Slime full set — massive knockback on attacker
            if (Config.slimeEnabled && Config.slimeEffects
                    && isWearingFullSet(victim, ModEvents::isSlimeArmor)) {
                var dir = attacker.getPos().subtract(victim.getPos()).normalize();
                attacker.setVelocity(attacker.getVelocity().add(dir.x * 2.0, 0.5, dir.z * 2.0));
                attacker.velocityModified = true;
            }
            // Blaze full set — attacker ignited
            if (Config.blazeEnabled && Config.blazeEffects
                    && isWearingFullSet(victim, ModEvents::isBlazeArmor)) {
                attacker.setOnFireFor(4);
            }
            // Purpur full set — 20% teleport dodge
            if (Config.purpurEnabled && Config.purpurEffects
                    && isWearingFullSet(victim, ModEvents::isPurpurArmor)) {
                if (victim.getWorld().random.nextFloat() < 0.20f) {
                    teleportRandomly(victim, 3, 8);
                    return false; // Cancel damage
                }
            }
            // Shulker full set — attacker levitated/launched
            if (Config.shulkerEnabled && Config.shulkerEffects
                    && isWearingFullSet(victim, ModEvents::isShulkerArmor)) {
                if (attacker instanceof PlayerEntity) {
                    attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 40, 0));
                } else {
                    attacker.setVelocity(attacker.getVelocity().add(0, 1.2, 0));
                    attacker.velocityModified = true;
                }
            }
            // Echo Shard full set — attacker gets Darkness
            if (Config.echoShardEnabled && Config.echoShardEffects
                    && isWearingFullSet(victim, ModEvents::isEchoShardArmor)) {
                attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 60, 0));
            }
            // Dragon Breath full set — attacker gets Wither + ignited
            if (Config.dragonBreathEnabled && Config.dragonBreathEffects
                    && isWearingFullSet(victim, ModEvents::isDragonBreathArmor)) {
                attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 60, 0));
                attacker.setOnFireFor(3);
            }
        }
        return true;
    }

    /**
     * AttackEntityCallback: handles offensive food tool on-hit effects.
     * Note: This fires when the player attacks, before damage is dealt.
     */
    private static ActionResult onAttackEntity(PlayerEntity player, World world, Hand hand,
                                                Entity entity, net.minecraft.util.hit.EntityHitResult hitResult) {
        if (world.isClient()) return ActionResult.PASS;
        if (!(entity instanceof LivingEntity target)) return ActionResult.PASS;

        ItemStack held = player.getMainHandStack();

        if (Config.honeyEnabled && isHoneyTool(held)) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 0));
        }
        if (Config.pufferfishEnabled && isPufferfishTool(held)) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0));
        }
        if (Config.rottenFleshEnabled && isRottenFleshTool(held)) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 100, 0));
        }
        if (Config.mushroomEnabled && isMushroomTool(held)) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 60, 0));
        }
        if (Config.chorusFruitEnabled && Config.chorusFruitTeleport && isChorusFruitTool(held)) {
            if (world.random.nextFloat() < 0.1f) {
                teleportRandomly(target, 3, 8);
            }
        }

        // Vanilla material tool on-hit effects
        if (Config.paperEnabled && Config.paperEffects && isPaperTool(held)) {
            if (world.random.nextFloat() < 0.05f)
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 20, 0));
            if (world.random.nextFloat() < 0.25f)
                held.damage(held.getMaxDamage() - held.getDamage(), player, EquipmentSlot.MAINHAND);
        }
        if (Config.featherEnabled && Config.featherEffects && isFeatherTool(held)) {
            var dir = target.getPos().subtract(player.getPos()).normalize();
            target.setVelocity(target.getVelocity().add(dir.x * 0.3, 0.8, dir.z * 0.3));
            target.velocityModified = true;
        }
        if (Config.glassEnabled && Config.glassEffects && isGlassTool(held)) {
            target.damage(player.getDamageSources().playerAttack(player), 2.0f);
            if (held.getDamage() >= held.getMaxDamage() - 1 && world instanceof ServerWorld sl) {
                for (LivingEntity nearby : sl.getEntitiesByClass(LivingEntity.class,
                        player.getBoundingBox().expand(3.0), e -> e != player)) {
                    nearby.damage(player.getDamageSources().playerAttack(player), 3.0f);
                }
                sl.spawnParticles(ParticleTypes.CRIT, player.getX(), player.getY() + 1, player.getZ(), 20, 1, 1, 1, 0.2);
            }
        }
        if (Config.spongeEnabled && Config.spongeEffects && isSpongeTool(held)) {
            if (target.isTouchingWater()) target.damage(player.getDamageSources().playerAttack(player), 3.0f);
        }
        if (Config.netherWartEnabled && Config.netherWartEffects && isNetherWartTool(held)) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 60, 0));
        }
        if (Config.pointedDripstoneEnabled && Config.pointedDripstoneEffects && isDripstoneTool(held)) {
            float bonus = 0.3f;
            if (player.fallDistance > 0 && !player.isOnGround()) bonus += 0.5f;
            target.damage(player.getDamageSources().playerAttack(player), target.getMaxHealth() * 0.01f * bonus > 1 ? 1 : bonus);
        }
        if (Config.cactusEnabled && Config.cactusEffects && isCactusTool(held)) {
            target.damage(player.getDamageSources().playerAttack(player), 1.0f);
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, 0));
        }
        if (Config.boneEnabled && Config.boneEffects && isBoneTool(held)) {
            if (target instanceof MobEntity mob && mob.getType().isIn(EntityTypeTags.UNDEAD))
                target.damage(player.getDamageSources().playerAttack(player), 2.0f);
        }
        if (Config.netherBrickEnabled && Config.netherBrickEffects && isNetherBrickTool(held)) {
            target.setOnFireFor(4);
        }
        if (Config.magmaCreamEnabled && Config.magmaCreamEffects && isMagmaCreamTool(held)) {
            target.setOnFireFor(5);
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 0));
        }
        if (Config.slimeEnabled && Config.slimeEffects && isSlimeTool(held)) {
            var dir = target.getPos().subtract(player.getPos()).normalize();
            target.setVelocity(target.getVelocity().add(dir.x * 1.5, 0.4, dir.z * 1.5));
            target.velocityModified = true;
        }
        if (Config.blazeEnabled && Config.blazeEffects && isBlazeTool(held)) {
            target.setOnFireFor(6);
        }
        if (Config.purpurEnabled && Config.purpurEffects && isPurpurTool(held)) {
            if (world.random.nextFloat() < 0.1f)
                teleportRandomly(target, 5, 10);
        }
        if (Config.ghastTearEnabled && Config.ghastTearEffects && isGhastTearTool(held)) {
            player.heal(2.0f);
        }
        if (Config.eyeOfEnderEnabled && Config.eyeOfEnderEffects && isEyeOfEnderTool(held)) {
            if (world.random.nextFloat() < 0.15f)
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, 0));
        }
        if (Config.shulkerEnabled && Config.shulkerEffects && isShulkerTool(held)) {
            target.setVelocity(target.getVelocity().add(0, 1.2, 0));
            target.velocityModified = true;
        }
        if (Config.echoShardEnabled && Config.echoShardEffects && isEchoShardTool(held)) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 100, 0));
        }
        if (Config.dragonBreathEnabled && Config.dragonBreathEffects && isDragonBreathTool(held)) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 60, 1));
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 0));
            if (world instanceof ServerWorld sl && world.random.nextFloat() < 0.3f) {
                AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(sl, target.getX(), target.getY(), target.getZ());
                cloud.setRadius(1.5f);
                cloud.setDuration(40);
                cloud.setRadiusGrowth(-1.5f / 40f);
                cloud.addEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 0));
                cloud.setOwner(player);
                sl.spawnEntity(cloud);
            }
        }
        if (Config.phantomEnabled && Config.phantomEffects && isPhantomTool(held)) {
            if (!world.isDay()) target.damage(player.getDamageSources().playerAttack(player), 2.0f);
        }

        return ActionResult.PASS;
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private static boolean isSnowTool(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.isOf(ModItems.SNOW_SWORD)
            || stack.isOf(ModItems.SNOW_PICKAXE)
            || stack.isOf(ModItems.SNOW_SHOVEL)
            || stack.isOf(ModItems.SNOW_AXE)
            || stack.isOf(ModItems.SNOW_HOE);
    }

    private static boolean isIceTool(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.isOf(ModItems.ICE_SWORD)
            || stack.isOf(ModItems.ICE_PICKAXE)
            || stack.isOf(ModItems.ICE_SHOVEL)
            || stack.isOf(ModItems.ICE_AXE)
            || stack.isOf(ModItems.ICE_HOE);
    }

    private static boolean isIceArmor(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ArmorItem armor)) return false;
        return armor.getMaterial() == ModArmorMaterials.ICE_ARMOR_MATERIAL;
    }

    private static boolean isPprismTool(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.isOf(ModItems.PPRISM_SWORD)
            || stack.isOf(ModItems.PPRISM_PICKAXE)
            || stack.isOf(ModItems.PPRISM_SHOVEL)
            || stack.isOf(ModItems.PPRISM_AXE)
            || stack.isOf(ModItems.PPRISM_HOE);
    }

    private static boolean isPprismArmor(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ArmorItem armor)) return false;
        return armor.getMaterial() == ModArmorMaterials.PPRISM_ARMOR_MATERIAL;
    }

    private static boolean isCoalTool(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.isOf(ModItems.COAL_SWORD)
            || stack.isOf(ModItems.COAL_PICKAXE)
            || stack.isOf(ModItems.COAL_SHOVEL)
            || stack.isOf(ModItems.COAL_AXE)
            || stack.isOf(ModItems.COAL_HOE);
    }

    private static boolean isCoalArmor(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof CoalArmorItem;
    }

    private static boolean isOpTool(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.isOf(ModItems.OVERPOWER_SWORD)
            || stack.isOf(ModItems.OVERPOWER_PICKAXE)
            || stack.isOf(ModItems.OVERPOWER_SHOVEL)
            || stack.isOf(ModItems.OVERPOWER_AXE);
    }

    private static boolean isOpArmor(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ArmorItem armor)) return false;
        return armor.getMaterial() == ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL;
    }

    private static boolean isFniTool(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.isOf(ModItems.FNI_SWORD)
            || stack.isOf(ModItems.FNI_PICKAXE)
            || stack.isOf(ModItems.FNI_SHOVEL)
            || stack.isOf(ModItems.FNI_AXE)
            || stack.isOf(ModItems.FNI_HOE);
    }

    private static boolean isFniArmor(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ArmorItem armor)) return false;
        return armor.getMaterial() == ModArmorMaterials.FNI_ARMOR_MATERIAL;
    }

    private static boolean isEctoArmor(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ArmorItem armor)) return false;
        return armor.getMaterial() == ModArmorMaterials.ECTO_ARMOR_MATERIAL;
    }

    private static boolean isCakeTool(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.isOf(ModItems.CAKE_SWORD)
            || stack.isOf(ModItems.CAKE_PICKAXE)
            || stack.isOf(ModItems.CAKE_SHOVEL)
            || stack.isOf(ModItems.CAKE_AXE)
            || stack.isOf(ModItems.CAKE_HOE);
    }

    private static boolean isCakeArmor(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ArmorItem armor)) return false;
        return armor.getMaterial() == ModArmorMaterials.CAKE_ARMOR_MATERIAL;
    }

    private static boolean isWearingAnyCoalArmor(PlayerEntity player) {
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            if (isCoalArmor(player.getEquippedStack(slot))) return true;
        }
        return false;
    }

    private static boolean isWearingAnyBurningCoalArmor(PlayerEntity player) {
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack piece = player.getEquippedStack(slot);
            if (isCoalArmor(piece) && CoalBurningHelper.isBurning(piece)) return true;
        }
        return false;
    }

    private static void clearArmorBurning(PlayerEntity player) {
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack piece = player.getEquippedStack(slot);
            if (isCoalArmor(piece)) CoalBurningHelper.setBurning(piece, false);
        }
    }

    private static void clearBurningIfCoalTool(ItemStack stack) {
        if (isCoalTool(stack)) CoalBurningHelper.setBurning(stack, false);
    }

    private static void applyEffects(PlayerEntity player, StatusEffectInstance... effects) {
        for (StatusEffectInstance effect : effects) {
            player.addStatusEffect(effect);
        }
    }

    // =======================================================================
    // Utility — random teleport
    // =======================================================================

    private static void teleportRandomly(LivingEntity entity, int minDist, int maxDist) {
        World world = entity.getWorld();
        if (world.isClient()) return;

        for (int attempt = 0; attempt < 16; attempt++) {
            double angle = world.random.nextDouble() * Math.PI * 2;
            double dist = minDist + world.random.nextDouble() * (maxDist - minDist);
            double tx = entity.getX() + Math.cos(angle) * dist;
            double tz = entity.getZ() + Math.sin(angle) * dist;
            double ty = entity.getY();

            BlockPos target = BlockPos.ofFloored(tx, ty, tz);
            // Try to find a safe y
            for (int dy = -2; dy <= 2; dy++) {
                BlockPos check = target.up(dy);
                BlockPos checkHead = check.up();
                if (world.getBlockState(check).getCollisionShape(world, check).isEmpty()
                        && world.getBlockState(checkHead).getCollisionShape(world, checkHead).isEmpty()
                        && !world.getBlockState(check.down()).getCollisionShape(world, check.down()).isEmpty()) {
                    entity.requestTeleport(check.getX() + 0.5, check.getY(), check.getZ() + 0.5);
                    if (world instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(ParticleTypes.PORTAL,
                                entity.getX(), entity.getY() + 1.0, entity.getZ(),
                                32, 0.5, 0.5, 0.5, 0.5);
                    }
                    return;
                }
            }
        }
    }

    // =======================================================================
    // Vanilla Material Set helpers
    // =======================================================================

    private static boolean isPaperTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.PAPER_SWORD) || s.isOf(ModItems.PAPER_PICKAXE) || s.isOf(ModItems.PAPER_SHOVEL) || s.isOf(ModItems.PAPER_AXE) || s.isOf(ModItems.PAPER_HOE));
    }
    private static boolean isFeatherTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.FEATHER_SWORD) || s.isOf(ModItems.FEATHER_PICKAXE) || s.isOf(ModItems.FEATHER_SHOVEL) || s.isOf(ModItems.FEATHER_AXE) || s.isOf(ModItems.FEATHER_HOE));
    }
    private static boolean isGlassTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.GLASS_SWORD) || s.isOf(ModItems.GLASS_PICKAXE) || s.isOf(ModItems.GLASS_SHOVEL) || s.isOf(ModItems.GLASS_AXE) || s.isOf(ModItems.GLASS_HOE));
    }
    private static boolean isSpongeTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.SPONGE_SWORD) || s.isOf(ModItems.SPONGE_PICKAXE) || s.isOf(ModItems.SPONGE_SHOVEL) || s.isOf(ModItems.SPONGE_AXE) || s.isOf(ModItems.SPONGE_HOE));
    }
    private static boolean isNetherWartTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.NETHER_WART_SWORD) || s.isOf(ModItems.NETHER_WART_PICKAXE) || s.isOf(ModItems.NETHER_WART_SHOVEL) || s.isOf(ModItems.NETHER_WART_AXE) || s.isOf(ModItems.NETHER_WART_HOE));
    }
    private static boolean isDripstoneTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.POINTED_DRIPSTONE_SWORD) || s.isOf(ModItems.POINTED_DRIPSTONE_PICKAXE) || s.isOf(ModItems.POINTED_DRIPSTONE_SHOVEL) || s.isOf(ModItems.POINTED_DRIPSTONE_AXE) || s.isOf(ModItems.POINTED_DRIPSTONE_HOE));
    }
    private static boolean isCactusTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.CACTUS_SWORD) || s.isOf(ModItems.CACTUS_PICKAXE) || s.isOf(ModItems.CACTUS_SHOVEL) || s.isOf(ModItems.CACTUS_AXE) || s.isOf(ModItems.CACTUS_HOE));
    }
    private static boolean isCactusArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.CACTUS_ARMOR_MATERIAL;
    }
    private static boolean isBoneTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.BONE_SWORD) || s.isOf(ModItems.BONE_PICKAXE) || s.isOf(ModItems.BONE_SHOVEL) || s.isOf(ModItems.BONE_AXE) || s.isOf(ModItems.BONE_HOE));
    }
    private static boolean isBoneArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.BONE_ARMOR_MATERIAL;
    }
    private static boolean isClayArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.CLAY_ARMOR_MATERIAL;
    }
    private static boolean isNetherBrickTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.NETHER_BRICK_SWORD) || s.isOf(ModItems.NETHER_BRICK_PICKAXE) || s.isOf(ModItems.NETHER_BRICK_SHOVEL) || s.isOf(ModItems.NETHER_BRICK_AXE) || s.isOf(ModItems.NETHER_BRICK_HOE));
    }
    private static boolean isNetherBrickArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL;
    }
    private static boolean isCopperArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.COPPER_ARMOR_MATERIAL;
    }
    private static boolean isPhantomTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.PHANTOM_SWORD) || s.isOf(ModItems.PHANTOM_PICKAXE) || s.isOf(ModItems.PHANTOM_SHOVEL) || s.isOf(ModItems.PHANTOM_AXE) || s.isOf(ModItems.PHANTOM_HOE));
    }
    private static boolean isPhantomArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.PHANTOM_ARMOR_MATERIAL;
    }
    private static boolean isMagmaCreamTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.MAGMA_CREAM_SWORD) || s.isOf(ModItems.MAGMA_CREAM_PICKAXE) || s.isOf(ModItems.MAGMA_CREAM_SHOVEL) || s.isOf(ModItems.MAGMA_CREAM_AXE) || s.isOf(ModItems.MAGMA_CREAM_HOE));
    }
    private static boolean isMagmaCreamArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL;
    }
    private static boolean isSlimeTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.SLIME_SWORD) || s.isOf(ModItems.SLIME_PICKAXE) || s.isOf(ModItems.SLIME_SHOVEL) || s.isOf(ModItems.SLIME_AXE) || s.isOf(ModItems.SLIME_HOE));
    }
    private static boolean isSlimeArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.SLIME_ARMOR_MATERIAL;
    }
    private static boolean isBlazeTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.BLAZE_SWORD) || s.isOf(ModItems.BLAZE_PICKAXE) || s.isOf(ModItems.BLAZE_SHOVEL) || s.isOf(ModItems.BLAZE_AXE) || s.isOf(ModItems.BLAZE_HOE));
    }
    private static boolean isBlazeArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.BLAZE_ARMOR_MATERIAL;
    }
    private static boolean isNautilusTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.NAUTILUS_SWORD) || s.isOf(ModItems.NAUTILUS_PICKAXE) || s.isOf(ModItems.NAUTILUS_SHOVEL) || s.isOf(ModItems.NAUTILUS_AXE) || s.isOf(ModItems.NAUTILUS_HOE));
    }
    private static boolean isNautilusArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL;
    }
    private static boolean isPurpurTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.PURPUR_SWORD) || s.isOf(ModItems.PURPUR_PICKAXE) || s.isOf(ModItems.PURPUR_SHOVEL) || s.isOf(ModItems.PURPUR_AXE) || s.isOf(ModItems.PURPUR_HOE));
    }
    private static boolean isPurpurArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.PURPUR_ARMOR_MATERIAL;
    }
    private static boolean isGhastTearTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.GHAST_TEAR_SWORD) || s.isOf(ModItems.GHAST_TEAR_PICKAXE) || s.isOf(ModItems.GHAST_TEAR_SHOVEL) || s.isOf(ModItems.GHAST_TEAR_AXE) || s.isOf(ModItems.GHAST_TEAR_HOE));
    }
    private static boolean isGhastTearArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL;
    }
    private static boolean isEyeOfEnderTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.EYE_OF_ENDER_SWORD) || s.isOf(ModItems.EYE_OF_ENDER_PICKAXE) || s.isOf(ModItems.EYE_OF_ENDER_SHOVEL) || s.isOf(ModItems.EYE_OF_ENDER_AXE) || s.isOf(ModItems.EYE_OF_ENDER_HOE));
    }
    private static boolean isEyeOfEnderArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL;
    }
    private static boolean isShulkerTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.SHULKER_SWORD) || s.isOf(ModItems.SHULKER_PICKAXE) || s.isOf(ModItems.SHULKER_SHOVEL) || s.isOf(ModItems.SHULKER_AXE) || s.isOf(ModItems.SHULKER_HOE));
    }
    private static boolean isShulkerArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.SHULKER_ARMOR_MATERIAL;
    }
    private static boolean isTurtleScuteArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL;
    }
    private static boolean isEchoShardTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.ECHO_SHARD_SWORD) || s.isOf(ModItems.ECHO_SHARD_PICKAXE) || s.isOf(ModItems.ECHO_SHARD_SHOVEL) || s.isOf(ModItems.ECHO_SHARD_AXE) || s.isOf(ModItems.ECHO_SHARD_HOE));
    }
    private static boolean isEchoShardArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL;
    }
    private static boolean isDragonBreathTool(ItemStack s) {
        return !s.isEmpty() && (s.isOf(ModItems.DRAGON_BREATH_SWORD) || s.isOf(ModItems.DRAGON_BREATH_PICKAXE) || s.isOf(ModItems.DRAGON_BREATH_SHOVEL) || s.isOf(ModItems.DRAGON_BREATH_AXE) || s.isOf(ModItems.DRAGON_BREATH_HOE));
    }
    private static boolean isDragonBreathArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL;
    }
    private static boolean isRabbitHideArmor(ItemStack s) {
        if (s.isEmpty() || !(s.getItem() instanceof ArmorItem a)) return false;
        return a.getMaterial() == ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL;
    }

    // =======================================================================
    // Vanilla Material Set tick handlers
    // =======================================================================

    private static void handlePaperPassive(PlayerEntity player) {
        if (isPaperTool(player.getMainHandStack()) || isPaperTool(player.getOffHandStack()))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 60, 0, false, false, true));
    }

    private static void handleFeatherPassive(PlayerEntity player) {
        if (isFeatherTool(player.getMainHandStack()) || isFeatherTool(player.getOffHandStack()))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 0, false, false, true));
    }

    private static void handleSpongePassive(PlayerEntity player) {
        if (!isSpongeTool(player.getMainHandStack())) return;
        if (player.age % 20 != 0) return;
        if (!player.isTouchingWater() && !player.getWorld().hasRain(player.getBlockPos())) return;
        ItemStack held = player.getMainHandStack();
        BlockPos center = player.getBlockPos();
        int repaired = 0;
        for (BlockPos pos : BlockPos.iterate(center.add(-3, -1, -3), center.add(3, 1, 3))) {
            if (repaired >= 5) break;
            if (player.getWorld().getBlockState(pos).isOf(Blocks.WATER)) {
                player.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                repaired++;
            }
        }
        if (repaired > 0 && held.isDamaged())
            held.setDamage(Math.max(0, held.getDamage() - repaired));
    }

    private static void handleNetherWartPassive(PlayerEntity player) {
        if (isNetherWartTool(player.getMainHandStack()) || isNetherWartTool(player.getOffHandStack()))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 60, 0, false, false, true));
    }

    private static void handleRabbitHideArmor(PlayerEntity player) {
        if (isRabbitHideArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 0, false, false, true));
        if (isRabbitHideArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isWearingFullSet(player, ModEvents::isRabbitHideArmor)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 2, false, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
            player.fallDistance = 0;
        }
    }

    private static void handleCactusAura(PlayerEntity player) {
        if (!isWearingFullSet(player, ModEvents::isCactusArmor)) return;
        if (player.age % 40 != 0) return;
        if (!(player.getWorld() instanceof ServerWorld sl)) return;
        for (LivingEntity mob : sl.getEntitiesByClass(LivingEntity.class,
                player.getBoundingBox().expand(2.0), e -> e != player && e instanceof MobEntity)) {
            mob.damage(player.getDamageSources().thorns(player), 1.0f);
        }
    }

    private static void handleBoneArmor(PlayerEntity player) {
        if (isWearingFullSet(player, ModEvents::isBoneArmor) && player.age % 20 == 0) {
            if (!(player.getWorld() instanceof ServerWorld sl)) return;
            for (LivingEntity mob : sl.getEntitiesByClass(LivingEntity.class,
                    player.getBoundingBox().expand(8.0), e -> e instanceof MobEntity m && m.getType().isIn(EntityTypeTags.UNDEAD))) {
                mob.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 40, 0, false, false, false));
            }
        }
        // Bone helmet — undead reduced detection range (>16 blocks cancel target)
        if (isBoneArmor(player.getEquippedStack(EquipmentSlot.HEAD))
                && player.age % 20 == 0 && player.getWorld() instanceof ServerWorld sl) {
            Box area = player.getBoundingBox().expand(48.0);
            for (MobEntity mob : sl.getEntitiesByClass(MobEntity.class, area,
                    m -> m.getType().isIn(EntityTypeTags.UNDEAD) && m.getTarget() == player && m.distanceTo(player) > 16)) {
                mob.setTarget(null);
            }
        }
    }

    private static void handleClayArmor(PlayerEntity player) {
        if (isClayArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isClayArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 0, false, false, true));
        if (isClayArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        if (isClayArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 60, 0, false, false, true));
        if (isWearingFullSet(player, ModEvents::isClayArmor))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 60, 0, false, false, true));
    }

    private static void handleNetherBrickArmor(PlayerEntity player) {
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            if (isNetherBrickArmor(player.getEquippedStack(slot))) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 60, 0, false, false, true));
                break;
            }
        }
    }

    private static void handleCopperArmor(PlayerEntity player) {
        boolean inRain = player.getWorld().hasRain(player.getBlockPos());
        if (!inRain) return;
        if (isCopperArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isCopperArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 60, 0, false, false, true));
        if (isWearingFullSet(player, ModEvents::isCopperArmor) && player.getWorld().isThundering())
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 60, 0, false, false, true));
    }

    private static void handlePhantomEffects(PlayerEntity player) {
        ItemStack held = player.getMainHandStack();
        if (isPhantomTool(held))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 0, false, false, true));
        boolean night = !player.getWorld().isDay();
        if (isPhantomArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 0, false, false, true));
        if (night && isPhantomArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (night && isPhantomArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        if (isPhantomArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 220, 0, false, false, true));
        // Phantom membrane full set — phantoms ignore player
        if (isWearingFullSet(player, ModEvents::isPhantomArmor)
                && player.age % 20 == 0 && player.getWorld() instanceof ServerWorld sl) {
            for (PhantomEntity phantom : sl.getEntitiesByClass(PhantomEntity.class,
                    player.getBoundingBox().expand(48.0), m -> m.getTarget() == player)) {
                phantom.setTarget(null);
            }
        }
    }

    private static void handleMagmaCreamArmor(PlayerEntity player) {
        if (isMagmaCreamArmor(player.getEquippedStack(EquipmentSlot.FEET)) || isMagmaCreamArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 60, 0, false, false, true));
        if (isMagmaCreamArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
    }

    private static void handleSlimeEffects(PlayerEntity player) {
        if (isSlimeArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 1, false, false, true));
        if (isSlimeArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isSlimeArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        if (isSlimeArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 0, false, false, true));
        if (isWearingFullSet(player, ModEvents::isSlimeArmor))
            player.fallDistance = 0;
    }

    private static void handleBlazeArmor(PlayerEntity player) {
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            if (isBlazeArmor(player.getEquippedStack(slot))) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 60, 0, false, false, true));
                break;
            }
        }
        if (isWearingFullSet(player, ModEvents::isBlazeArmor))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 60, 0, false, false, true));
    }

    private static void handleNautilusEffects(PlayerEntity player) {
        if (isNautilusTool(player.getMainHandStack()) && player.isTouchingWater())
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 60, 0, false, false, true));
        if (!player.isTouchingWater()) return;
        if (isNautilusArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isNautilusArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 60, 0, false, false, true));
        if (isNautilusArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        if (isNautilusArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 220, 0, false, false, true));
        if (isWearingFullSet(player, ModEvents::isNautilusArmor))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 60, 0, false, false, true));
        // Nautilus full set — guardians and drowned ignore player
        if (isWearingFullSet(player, ModEvents::isNautilusArmor)
                && player.age % 20 == 0 && player.getWorld() instanceof ServerWorld sl) {
            Box area = player.getBoundingBox().expand(48.0);
            for (MobEntity mob : sl.getEntitiesByClass(MobEntity.class, area,
                    m -> (m instanceof GuardianEntity || m instanceof DrownedEntity) && m.getTarget() == player)) {
                mob.setTarget(null);
            }
        }
    }

    private static void handlePurpurEffects(PlayerEntity player) {
        if (isPurpurTool(player.getMainHandStack()))
            player.fallDistance = 0;
        if (isPurpurArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 0, false, false, true));
        if (isPurpurArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isPurpurArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        if (isPurpurArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 220, 0, false, false, true));
    }

    private static void handleGhastTearEffects(PlayerEntity player) {
        if (isGhastTearTool(player.getMainHandStack()))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 0, false, false, true));
        if (isGhastTearArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isGhastTearArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 0, false, false, true));
        if (isGhastTearArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        if (isGhastTearArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 60, 0, false, false, true));
        if (isWearingFullSet(player, ModEvents::isGhastTearArmor)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 1, false, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 60, 1, false, false, true));
        }
    }

    private static void handleEyeOfEnderEffects(PlayerEntity player) {
        if (isEyeOfEnderTool(player.getMainHandStack()))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 220, 0, false, false, true));
        if (isEyeOfEnderArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isEyeOfEnderArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 0, false, false, true));
        if (isEyeOfEnderArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        if (isEyeOfEnderArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 220, 0, false, false, true));
        if (isWearingFullSet(player, ModEvents::isEyeOfEnderArmor) && player.age % 20 == 0) {
            if (player.getWorld() instanceof ServerWorld sl) {
                for (LivingEntity mob : sl.getEntitiesByClass(LivingEntity.class,
                        player.getBoundingBox().expand(16.0), e -> e != player))
                    mob.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 40, 0, false, false, false));
                // Eye of Ender full set — endermen neutral
                for (EndermanEntity enderman : sl.getEntitiesByClass(EndermanEntity.class,
                        player.getBoundingBox().expand(48.0), m -> m.getTarget() == player)) {
                    enderman.setTarget(null);
                }
            }
        }
    }

    private static void handleShulkerEffects(PlayerEntity player) {
        if (isShulkerTool(player.getMainHandStack()) && !player.isOnGround())
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 60, 0, false, false, true));
        if (isShulkerArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 0, false, false, true));
        if (isShulkerArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 1, false, false, true));
        if (isShulkerArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        if (isShulkerArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 220, 0, false, false, true));
        if (isWearingFullSet(player, ModEvents::isShulkerArmor)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 2, false, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 0, false, false, true));
            player.fallDistance = 0;
        }
    }

    private static void handleTurtleScuteArmor(PlayerEntity player) {
        if (isTurtleScuteArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 220, 0, false, false, true));
        if (player.isTouchingWater()) {
            if (isTurtleScuteArmor(player.getEquippedStack(EquipmentSlot.FEET)))
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
            if (isTurtleScuteArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
            if (isTurtleScuteArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 60, 0, false, false, true));
            if (isWearingFullSet(player, ModEvents::isTurtleScuteArmor)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 60, 1, false, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 60, 0, false, false, true));
            }
        } else if (isWearingFullSet(player, ModEvents::isTurtleScuteArmor)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 0, false, false, true));
        }
        // Turtle Scute full set — guardians ignore player
        if (isWearingFullSet(player, ModEvents::isTurtleScuteArmor)
                && player.age % 20 == 0 && player.getWorld() instanceof ServerWorld sl) {
            for (GuardianEntity guardian : sl.getEntitiesByClass(GuardianEntity.class,
                    player.getBoundingBox().expand(48.0), m -> m.getTarget() == player)) {
                guardian.setTarget(null);
            }
        }
    }

    private static void handleEchoShardEffects(PlayerEntity player) {
        if (isEchoShardArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isEchoShardArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        if (isEchoShardArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 1, false, false, true));
        if (isEchoShardArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 220, 0, false, false, true));
        boolean toolHeld = isEchoShardTool(player.getMainHandStack());
        boolean fullSet = isWearingFullSet(player, ModEvents::isEchoShardArmor);
        if ((toolHeld || fullSet) && player.age % 20 == 0) {
            if (player.getWorld() instanceof ServerWorld sl) {
                for (LivingEntity mob : sl.getEntitiesByClass(LivingEntity.class,
                        player.getBoundingBox().expand(16.0), e -> e != player))
                    mob.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 40, 0, false, false, false));
                // Echo Shard full set — warden neutral
                if (fullSet) {
                    for (WardenEntity warden : sl.getEntitiesByClass(WardenEntity.class,
                            player.getBoundingBox().expand(48.0), m -> m.getTarget() == player)) {
                        warden.setTarget(null);
                    }
                }
            }
        }
    }

    private static void handleDragonBreathEffects(PlayerEntity player) {
        if (isDragonBreathTool(player.getMainHandStack())) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 60, 0, false, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 60, 0, false, false, true));
        }
        if (isDragonBreathArmor(player.getEquippedStack(EquipmentSlot.FEET)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        if (isDragonBreathArmor(player.getEquippedStack(EquipmentSlot.LEGS)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 60, 0, false, false, true));
        if (isDragonBreathArmor(player.getEquippedStack(EquipmentSlot.CHEST)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 1, false, false, true));
        if (isDragonBreathArmor(player.getEquippedStack(EquipmentSlot.HEAD)))
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 60, 0, false, false, true));
        if (isWearingFullSet(player, ModEvents::isDragonBreathArmor)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 60, 1, false, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 1, false, false, true));
        }
    }
}
