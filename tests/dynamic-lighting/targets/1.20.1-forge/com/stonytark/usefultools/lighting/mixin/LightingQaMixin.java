package com.stonytark.usefultools.lighting.mixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraftforge.registries.ForgeRegistries;
import com.stonytark.usefultools.lighting.ClientLighting;
import com.stonytark.usefultools.lighting.SourcePolicy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(Minecraft.class)
public abstract class LightingQaMixin {
    private static final EquipmentSlot[] SLOTS = {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    private static final String[] ITEMS = {"usefultoolsmod:ectoplasm_lantern","usefultoolsmod:ectoplasm_lantern","usefultoolsmod:soul_lantern","usefultoolsmod:soul_lantern","usefultoolsmod:hglow_block","usefultoolsmod:hglow_block","usefultoolsmod:hglow","usefultoolsmod:hglow","usefultoolsmod:hglowstone_axe","usefultoolsmod:hglowstone_axe","usefultoolsmod:hglowstone_hoe","usefultoolsmod:hglowstone_hoe","usefultoolsmod:hglowstone_pickaxe","usefultoolsmod:hglowstone_pickaxe","usefultoolsmod:hglowstone_shovel","usefultoolsmod:hglowstone_shovel","usefultoolsmod:hglowstone_sword","usefultoolsmod:hglowstone_sword","usefultoolsmod:hglow_helmet","usefultoolsmod:hglow_helmet","usefultoolsmod:hglow_helmet","usefultoolsmod:hglow_chestplate","usefultoolsmod:hglow_chestplate","usefultoolsmod:hglow_chestplate","usefultoolsmod:hglow_leggings","usefultoolsmod:hglow_leggings","usefultoolsmod:hglow_leggings","usefultoolsmod:hglow_boots","usefultoolsmod:hglow_boots","usefultoolsmod:hglow_boots","minecraft:glowstone","minecraft:glowstone","usefultoolsmod:calcified_amethyst","usefultoolsmod:ectoplasm"};
    private static final EquipmentSlot[] CASE_SLOTS = {EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.HEAD,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.CHEST,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.LEGS,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.FEET,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND,EquipmentSlot.MAINHAND,EquipmentSlot.MAINHAND};
    private static int stage = -1, ticks;
    private static ItemStack[] original;
    private static boolean done, announced, disconnected;
    private static void clear(Minecraft mc) { for (EquipmentSlot slot : SLOTS) mc.player.setItemSlot(slot, ItemStack.EMPTY); }
    private static ItemStack item(String name) { ItemStack result=new ItemStack(ForgeRegistries.ITEMS.getValue(new net.minecraft.resources.ResourceLocation(name))); if(result.isEmpty())throw new AssertionError("Missing registered fixture item "+name);return result; }
    private static void backends(Minecraft mc, BlockPos pos, String source) throws Exception {
        boolean sodiumChecked=false;
        for(String prefix:new String[]{"net.caffeinemc.mods.sodium","me.jellysquid.mods.sodium","org.embeddedt.embeddium.impl"}) {
            Class<?> cache;String dataPackage=prefix+(prefix.startsWith("org.embeddedt.")?".model.light.data":".client.model.light.data");
            try { cache=Class.forName(dataPackage+".HashLightDataCache"); } catch(ClassNotFoundException absent) {
                try {cache=Class.forName(dataPackage+".SingleBlockLightDataCache");} catch(ClassNotFoundException missing) {
                    try {Class.forName(dataPackage+".LightDataAccess");throw new AssertionError("Installed backend has no exercised native cache "+prefix);} catch(ClassNotFoundException absentBackend) {continue;}
                }
            }
            Object instance;
            if(cache.getSimpleName().equals("SingleBlockLightDataCache")) {
                instance=cache.getConstructor().newInstance();
                var reset=java.util.Arrays.stream(cache.getMethods()).filter(m->m.getName().equals("reset")&&m.getParameterCount()==2).findFirst().orElseThrow();
                reset.invoke(instance,pos,mc.level);
            } else instance=cache.getConstructors()[0].newInstance(mc.level);
            sodiumChecked=true;
            int sampled=((Number)cache.getMethod("get",int.class,int.class,int.class).invoke(instance,pos.getX(),pos.getY(),pos.getZ())).intValue();
            int expected=(int)Math.ceil(ClientLighting.FIELD.light(pos.getX()+.5,pos.getY()+.5,pos.getZ()+.5)-1e-9);
            if((sampled&15)<expected)throw new AssertionError("Sodium's actual mesh cache missed visual light");
            com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_BACKEND_PASS Sodium cache source="+source);
        }
        try {Class.forName("net.caffeinemc.mods.sodium.client.model.light.data.LightDataAccess"); if(!sodiumChecked)throw new AssertionError("Installed Sodium has no exercised native light cache");} catch(ClassNotFoundException absent) {}
        if(source.startsWith("usefultoolsmod:")&&SourcePolicy.level(source,false)>0) {
            Class<?> lamb;
            try {lamb=Class.forName("dev.lambdaurora.lambdynlights.LambDynLights");} catch(ClassNotFoundException absent) {return;}
            Object receiver=null;
            var method=lamb.getMethod("getLuminanceFromItemStack",ItemStack.class,boolean.class);
            for(boolean water:new boolean[]{false,true}) if(((Number)method.invoke(receiver,item(source),water)).intValue()!=0)throw new AssertionError("Lamb duplicated companion-owned source "+source);
            com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_BACKEND_PASS Lamb yields owned source="+source);
        }
    }
    @Inject(method="tick", at=@At("TAIL"))
    private void usefulToolsLighting$qa(CallbackInfo ci) {
        if (!announced) { announced=true; com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_LOADED"); }
        Minecraft mc = Minecraft.getInstance();
        com.stonytark.usefultools.lighting.LightingQaMenu.capture(mc);
        com.stonytark.usefultools.lighting.LightingQaEquipmentScene.tick(mc);
        if(System.getProperty("usefulToolsLightingQaMultiplayer")!=null) { com.stonytark.usefultools.lighting.MultiplayerLightingQa.tick(mc); return; }
        if (done) {
            if(!disconnected&&mc.level==null) {
                disconnected=true;
                if(ClientLighting.FIELD.size()!=0)throw new AssertionError("Disconnect retained sources");
                com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_ADVANCED_PASS disconnect clears field");
            }
            if(disconnected&&mc.level!=null&&mc.player!=null) com.stonytark.usefultools.lighting.AdvancedLightingQa.reopened(mc);
            return;
        }
        if (mc.level == null || mc.player == null || mc.player.getY() < 0) return;
        try {
            if (stage == -1) {
                if (++ticks < 100) return;
                if (!com.stonytark.usefultools.lighting.AdvancedLightingQa.prepare(mc)) return;
                original = new ItemStack[SLOTS.length]; for(int i=0;i<SLOTS.length;i++) original[i]=mc.player.getItemBySlot(SLOTS[i]).copy();
                stage=0; clear(mc); mc.player.setItemSlot(CASE_SLOTS[0],item(ITEMS[0])); ticks=0; return;
            }
            if(stage>ITEMS.length+1) {
                if(com.stonytark.usefultools.lighting.AdvancedLightingQa.tick(mc)) {
                    for(int i=0;i<SLOTS.length;i++)mc.player.setItemSlot(SLOTS[i],original[i]);
                    done=true;com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_PASS cases="+stage+" advanced=10");
                }
                return;
            }
            if (++ticks < 12) return;
            double x=Math.rint(mc.player.getX()*8)/8, y=Math.rint((mc.player.getY()+mc.player.getEyeHeight()*.5)*8)/8, z=Math.rint(mc.player.getZ()*8)/8;
            int expected=stage<ITEMS.length ? SourcePolicy.level(ITEMS[stage], CASE_SLOTS[stage]!=EquipmentSlot.MAINHAND && CASE_SLOTS[stage]!=EquipmentSlot.OFFHAND) : stage==ITEMS.length ? 15 : 0;
            if(expected==15&&!com.stonytark.usefultools.lighting.AdvancedLightingQa.serverInvariant(mc))return;
            double value=ClientLighting.FIELD.light(x,y,z);
            if (Math.abs(value-expected)>1e-6) throw new AssertionError("stage="+stage+" source="+(stage<ITEMS.length?ITEMS[stage]:"mixed/removal")+" expected="+expected+" actual="+value+" held="+mc.player.getItemBySlot(EquipmentSlot.MAINHAND)+" entities="+ClientLighting.FIELD.size());
            if (expected>0) {
                int radius=stage<ITEMS.length?SourcePolicy.radius(ITEMS[stage],false):30;
                for(int distance:new int[]{10,15,20,29}) {
                    double want=Math.max(0,expected*(1-distance/(double)radius));
                    if(Math.abs(ClientLighting.FIELD.light(x+distance,y,z)-want)>1e-6) throw new AssertionError("radius "+distance);
                }
                BlockPos pos=new BlockPos((int)Math.floor(x+20),(int)Math.floor(y),(int)Math.floor(z));
                int sampled=net.minecraft.client.renderer.LevelRenderer.getLightColor(mc.level,pos);
                int required=(int)(ClientLighting.FIELD.light(pos.getX()+.5,pos.getY()+.5,pos.getZ()+.5)*16);
                if((sampled&255)<required) throw new AssertionError("Native renderer hook did not sample companion light");
                backends(mc,pos,stage<ITEMS.length?ITEMS[stage]:"mixed");
            }
            com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_CASE_PASS stage="+stage+" source="+(stage<ITEMS.length?ITEMS[stage]:"mixed/removal")+" slot="+(stage<ITEMS.length?CASE_SLOTS[stage]:"mixed/removal")+" level="+value);
            stage++; ticks=0; clear(mc);
            if(stage<ITEMS.length) mc.player.setItemSlot(CASE_SLOTS[stage],item(ITEMS[stage]));
            else if(stage==ITEMS.length) { mc.player.setItemSlot(EquipmentSlot.MAINHAND,item("usefultoolsmod:ectoplasm_lantern")); mc.player.setItemSlot(EquipmentSlot.OFFHAND,item("usefultoolsmod:hglow")); for(EquipmentSlot slot:new EquipmentSlot[]{EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET}) mc.player.setItemSlot(slot,item("usefultoolsmod:hglow_"+(slot==EquipmentSlot.HEAD?"helmet":slot==EquipmentSlot.CHEST?"chestplate":slot==EquipmentSlot.LEGS?"leggings":"boots"))); }
            else if(stage>ITEMS.length+1) com.mojang.logging.LogUtils.getLogger().info("USEFUL_TOOLS_LIGHTING_QA_EQUIPMENT_PASS cases="+stage);
        } catch(Throwable error) { done=true; com.mojang.logging.LogUtils.getLogger().error("USEFUL_TOOLS_LIGHTING_QA_FAIL "+error); error.printStackTrace(); }
    }
}
