package com.stonytark.usefultoolsmod.spectralqa;

import com.stonytark.usefultoolsmod.classic.EntityGhost;
import com.stonytark.usefultoolsmod.classic.EntityWraith;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import com.krazykid1117.entity.EntityGrenade;
import com.stonytark.usefultoolsmod.classic.EntityStickyDynamite;
import com.stonytark.usefultoolsmod.classic.ClassicSpectralInfuserBlock;
import com.krazykid1117.Main.Mainregistry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.enchantment.Enchantment;
import com.stonytark.usefultoolsmod.classic.ClassicGeneratedCatalog;
import com.stonytark.usefultoolsmod.classic.TileSpectralInfuser;
import com.stonytark.usefultoolsmod.classic.TileMiningCharge;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Native classic scene control; only the separately packaged acceptance mod contains it. */
@Mod(modid="usefultools_spectral_qa", name="Classic spectral acceptance fixture", version="1.0.0",
     dependencies="required-after:usefultoolsmod")
public final class ClassicSpectralQa {
    private static final Logger LOG = LogManager.getLogger("ClassicSpectralQa");
    private static EntityLiving held;
    private static boolean moving;
    private static final java.util.List<Entity> projectiles = new java.util.ArrayList<Entity>();

    @Mod.EventHandler public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(this);
    }
    @Mod.EventHandler public void server(FMLServerStartingEvent event) {
        event.registerServerCommand(new SceneCommand());
    }
    @SubscribeEvent public void tick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        for (int i=0;i<projectiles.size();i++) {
            Entity projectile=projectiles.get(i);
            projectile.setPosition(i*2-1,201,-3);
            projectile.motionX=0;projectile.motionY=0;projectile.motionZ=0;
        }
        if (held == null || held.isDead) return;
        if (!moving) {
            held.setPositionAndRotation(0,201,-3,0,0);
            held.rotationYawHead=0; held.renderYawOffset=0;
            held.motionX=0; held.motionY=0; held.motionZ=0;
            held.getNavigator().clearPathEntity();
        }
    }

    private static final class SceneCommand extends CommandBase {
        @Override public String getCommandName() { return "spectralqa"; }
        @Override public String getCommandUsage(ICommandSender sender) { return "/spectralqa prepare|scene <ghost|wraith> <state>|remove"; }
        @Override public boolean canCommandSenderUseCommand(ICommandSender sender) { return true; }
        @Override public void processCommand(ICommandSender sender, String[] args) {
            EntityPlayerMP player=getCommandSenderAsPlayer(sender);
            World world=player.worldObj;
            if (args.length==1 && "prepare".equals(args[0])) {
                remove(world);
                world.getGameRules().setOrCreateGameRule("doMobSpawning", "false");
                world.getGameRules().setOrCreateGameRule("doMobLoot", "false");
                world.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
                player.setPositionAndUpdate(0,200,3);player.rotationYaw=180;player.rotationPitch=8;
                player.playerNetServerHandler.setPlayerLocation(0,200,3,180,8);
                for (int cx=-1;cx<=0;cx++) for(int cz=-1;cz<=0;cz++) {
                    Chunk chunk=world.getChunkFromChunkCoords(cx,cz);
                    java.util.Arrays.fill(chunk.getBiomeArray(),(byte)BiomeGenBase.plains.biomeID);
                    chunk.setChunkModified();
                    player.playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S21PacketChunkData(chunk,true,65535));
                }
                if(!world.getBiomeGenForCoords(0,-3).canSpawnLightningBolt())
                    throw new IllegalStateException("Native fixture biome does not permit rain");
                LOG.info("SPECTRAL_QA_PRECIPITATION_BIOME plains acknowledged");
                for (int x=-8;x<=8;x++) for(int z=-8;z<=8;z++) {
                    world.setBlock(x,199,z,Blocks.stone,0,3);
                    for(int y=200;y<=210;y++) world.setBlock(x,y,z,Blocks.air,0,3);
                }
            } else if (args.length==1 && "remove".equals(args[0])) {
                remove(world);
            } else if (args.length==1 && "verify-lit".equals(args[0])) {
                if ((world.getBlockMetadata(3,200,-2) & 8)==0) throw new IllegalStateException("Native Infuser is not lit");
                if (((TileMiningCharge)world.getTileEntity(0,200,-2)).fuse<=0) throw new IllegalStateException("Native charge not primed");
            } else if (args.length==2 && "surfaces".equals(args[0])) {
                String state=args[1];
                world.setWorldTime("day".equals(state)?6000:18000);
                if ("armor".equals(state)) {
                    String[] armor={"hglow_boots","hglow_leggings","hglow_chestplate","hglow_helmet"};
                    for(int i=0;i<armor.length;i++) player.setCurrentItemOrArmor(i+1,
                            new ItemStack(ClassicGeneratedCatalog.ITEMS.get(armor[i])));
                    player.inventory.currentItem=7;
                    player.inventoryContainer.detectAndSendChanges();
                } else if ("inventory".equals(state)) {
                    player.setGameType(net.minecraft.world.WorldSettings.GameType.SURVIVAL);
                } else if ("creative".equals(state)) {
                    player.setGameType(net.minecraft.world.WorldSettings.GameType.CREATIVE);
                } else if ("projectiles".equals(state)) {
                    for(Entity projectile:projectiles) projectile.setDead();
                    projectiles.clear();
                    projectiles.add(new EntityGrenade(world));
                    EntityStickyDynamite sticky=new EntityStickyDynamite(world);
                    NBTTagCompound tag=new NBTTagCompound();
                    sticky.writeToNBT(tag);tag.setInteger("Fuse",10000);tag.setBoolean("Stuck",true);
                    sticky.readFromNBT(tag);projectiles.add(sticky);
                    for(int i=0;i<projectiles.size();i++) {
                        Entity projectile=projectiles.get(i);projectile.setPosition(i*2-1,201,-3);
                        if(!world.spawnEntityInWorld(projectile)) throw new IllegalStateException("Native projectile spawn rejected");
                        LOG.info("SPECTRAL_QA_PROJECTILE id={} type={}",projectile.getEntityId(),projectile.getClass().getName());
                    }
                } else if ("gui".equals(state)) {
                    TileSpectralInfuser infuser=(TileSpectralInfuser)world.getTileEntity(3,200,-2);
                    infuser.setInventorySlotContents(0,new ItemStack(Items.iron_sword));
                    infuser.setInventorySlotContents(1,new ItemStack(ClassicGeneratedCatalog.ITEMS.get("ectoplasm")));
                    infuser.setInventorySlotContents(2,null);
                    // Native redstone holds the populated slots without a negative
                    // progress value drawing outside the GUI's progress bounds.
                    world.setBlock(3,199,-2,Blocks.redstone_block,0,3);
                    infuser.setProgress(0);
                    LOG.info("SPECTRAL_QA_INFUSER_GUI input=iron_sword fuel=ectoplasm slots_acknowledged redstone_paused");
                    player.openGui(Mainregistry.modInstance,ClassicSpectralInfuserBlock.GUI_ID,world,3,200,-2);
                } else if ("lit".equals(state)) {
                    IInventory infuser=(IInventory)world.getTileEntity(3,200,-2);
                    infuser.setInventorySlotContents(0,new ItemStack(Items.iron_sword));
                    infuser.setInventorySlotContents(1,new ItemStack(ClassicGeneratedCatalog.ITEMS.get("ectoplasm")));
                    ((TileSpectralInfuser)infuser).setProgress(-10000);
                    TileMiningCharge charge=(TileMiningCharge)world.getTileEntity(0,200,-2);
                    charge.ignite();charge.fuse=10000;charge.markDirty();
                } else {
                    for(int x=-5;x<=5;x++) for(int y=200;y<=207;y++) for(int z=-8;z<=4;z++)
                        world.setBlock(x,y,z,Blocks.air,0,3);
                    String[] blocks={"ectoplasm_lantern","mining_charge","spectral_infuser",
                                     "ectoplasm_block","refined_ectoplasm_block","hglow_block"};
                    for(int i=0;i<blocks.length;i++) {
                        Object value=Block.blockRegistry.getObject("usefultoolsmod:"+blocks[i]);
                        if(!(value instanceof Block)||value==Blocks.air) throw new IllegalStateException("Missing native block "+blocks[i]);
                        world.setBlock((i%3-1)*3,200,-2-(i/3)*3,(Block)value,0,3);
                    }
                    Block legacyLantern=(Block)Block.blockRegistry.getObject("usefultoolsmod:soul_lantern");
                    if(legacyLantern==null||legacyLantern==Blocks.air) throw new IllegalStateException("Missing native Soul Lantern");
                    world.setBlock(-4,200,-2,legacyLantern,0,3);
                    String[] items={"dynamite","grenade","sticky_dynamite","mining_charge","ectoplasm_lantern",
                                    "spectral_resonator","remote_detonator","hglowstone_sword","hglow_helmet"};
                    for(int i=0;i<items.length;i++) {
                        Object value=Item.itemRegistry.getObject("usefultoolsmod:"+items[i]);
                        if(!(value instanceof Item)) throw new IllegalStateException("Missing native item "+items[i]);
                        ItemStack stack=new ItemStack((Item)value);
                        if(i==7)stack.addEnchantment(Enchantment.unbreaking,1);
                        player.inventory.setInventorySlotContents(i,stack);
                    }
                    Item legacyLanternItem=(Item)Item.itemRegistry.getObject("usefultoolsmod:soul_lantern");
                    if(legacyLanternItem==null) throw new IllegalStateException("Missing native Soul Lantern item");
                    player.inventory.setInventorySlotContents(9,new ItemStack(legacyLanternItem));
                    player.inventoryContainer.detectAndSendChanges();
                }
                LOG.info("SPECTRAL_QA_SURFACES state={} infuser_meta={} charge_fuse={}",state,
                         world.getBlockMetadata(3,200,-2),((TileMiningCharge)world.getTileEntity(0,200,-2)).fuse);
                LOG.info("SPECTRAL_QA_LANTERNS ectoplasm={} soul={}",
                         Block.blockRegistry.getNameForObject(world.getBlock(-3,200,-2)),
                         Block.blockRegistry.getNameForObject(world.getBlock(-4,200,-2)));
            } else if (args.length==3 && "scene".equals(args[0])) {
                String entity=args[1],state=args[2];
                if (!("ghost".equals(entity)||"wraith".equals(entity))) throw new IllegalArgumentException("Unknown fixture entity");
                if (held==null || held.isDead || ("ghost".equals(entity) != (held instanceof EntityGhost))) {
                    remove(world);
                    held="ghost".equals(entity)?new EntityGhost(world):new EntityWraith(world);
                    held.setPositionAndRotation(0,201,-3,0,0);
                    held.func_110163_bv();
                    if (!world.spawnEntityInWorld(held)) throw new IllegalStateException("Native spawn rejected");
                }
                moving=false;
                held.removePotionEffect(Potion.invisibility.id);
                boolean night="night".equals(state)||"indoor".equals(state);
                world.setWorldTime(night?18000:6000);
                world.getWorldInfo().setRaining("rain".equals(state));
                world.getWorldInfo().setRainTime(100000);
                world.getWorldInfo().setThundering(false);
                float rain="rain".equals(state)?1F:0F;
                world.setRainStrength(rain);world.setThunderStrength(0F);
                player.playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S2BPacketChangeGameState(rain>0F?1:2,0F));
                player.playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S2BPacketChangeGameState(7,rain));
                player.playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S2BPacketChangeGameState(8,0F));
                LOG.info("SPECTRAL_QA_WEATHER raining={} rain_strength={}",rain>0F,rain);
                for(int x=-5;x<=5;x++) for(int y=200;y<=207;y++) for(int z=-8;z<=4;z++)
                    world.setBlock(x,y,z,Blocks.air,0,3);
                if ("dark-background".equals(state)||"bright-background".equals(state)) {
                    for(int x=-4;x<=4;x++) for(int y=201;y<=205;y++)
                        world.setBlock(x,y,-5,Blocks.wool,"dark-background".equals(state)?15:0,3);
                } else if ("indoor".equals(state)) {
                    for(int x=-5;x<=5;x++) for(int y=200;y<=207;y++) for(int z=-8;z<=3;z++)
                        if(x==-5||x==5||y==207||z==-8) world.setBlock(x,y,z,Blocks.stonebrick,0,3);
                    world.setBlock(0,205,-4,Blocks.glowstone,0,3);
                } else if ("invisible".equals(state)) {
                    held.addPotionEffect(new PotionEffect(Potion.invisibility.id,20000,0));
                    // A dark wall distinguishes an absent model from the bright shader sun.
                    for(int x=-4;x<=4;x++) for(int y=201;y<=205;y++)
                        world.setBlock(x,y,-5,Blocks.wool,15,3);
                } else if ("damage".equals(state)) {
                    if(!held.attackEntityFrom(DamageSource.outOfWorld,1)) throw new IllegalStateException("Native damage rejected");
                } else if ("movement".equals(state)) {
                    moving=true;held.motionX=.15;
                } else if (!("day".equals(state)||"night".equals(state)||"rain".equals(state))) {
                    throw new IllegalArgumentException("Unknown scene state");
                }
                LOG.info("SPECTRAL_QA_ENTITY id={} type={} state={} alive={} health={}",
                        held.getEntityId(),entity,state,!held.isDead,held.getHealth());
            } else throw new IllegalArgumentException("Invalid fixture command");
            String message="SPECTRAL_QA_ACCEPTED "+java.util.Arrays.toString(args);
            LOG.info(message); sender.addChatMessage(new ChatComponentText(message));
        }
        private static void remove(World world) {
            if(held!=null) { held.setDead();world.removeEntity(held);held=null; }
            for(Entity projectile:projectiles) { projectile.setDead();world.removeEntity(projectile); }
            projectiles.clear();
            moving=false;
        }
    }
}
