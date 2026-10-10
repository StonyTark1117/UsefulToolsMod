package com.stonytark.usefultools.lighting.classic;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import java.util.Map;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
/** Test-only silent Paulscode Library avoids classic asynchronous OpenAL context races. */
@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions({"com.stonytark.usefultools.lighting.classic.ClassicQaAudioPlugin"})
public final class ClassicQaAudioPlugin implements IFMLLoadingPlugin {
    public String[] getASMTransformerClass(){return new String[]{ClassicQaAudioPlugin.Transformer.class.getName()};}
    public String getModContainerClass(){return null;}
    public String getSetupClass(){return null;}
    public void injectData(Map<String,Object> data){}
    public String getAccessTransformerClass(){return null;}
    public static final class Transformer implements IClassTransformer {
        public byte[] transform(String name,String transformedName,byte[] original){
            if(original==null||!"net.minecraft.client.audio.SoundManager".equals(transformedName))return original;
            ClassNode node=new ClassNode();new ClassReader(original).accept(node,0);int changed=0;
            for(Object value:node.methods)for(AbstractInsnNode insn:((MethodNode)value).instructions.toArray())
                if(insn instanceof LdcInsnNode){LdcInsnNode literal=(LdcInsnNode)insn;
                    if(literal.cst instanceof Type&&"paulscode/sound/libraries/LibraryLWJGLOpenAL".equals(((Type)literal.cst).getInternalName())){literal.cst=Type.getObjectType("paulscode/sound/Library");changed++;}
                }
            if(changed!=1)throw new IllegalStateException("Native silent audio hook expected one library registration; got "+changed);
            ClassWriter writer=new ClassWriter(0);node.accept(writer);System.out.println("USEFUL_TOOLS_LIGHTING_QA_AUDIO silent Paulscode Library; ALSOFT_DRIVERS=null retained");return writer.toByteArray();
        }
    }
}
