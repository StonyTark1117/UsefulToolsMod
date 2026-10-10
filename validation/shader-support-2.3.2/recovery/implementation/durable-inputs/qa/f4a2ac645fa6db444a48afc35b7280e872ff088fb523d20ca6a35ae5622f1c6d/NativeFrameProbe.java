package com.stonytark.usefultoolsmod.spectralqa;
import java.io.IOException;
import java.lang.instrument.*;
import java.lang.reflect.Field;
import java.nio.file.*;
import java.security.ProtectionDomain;
import java.util.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
/** Private observer of the actual native end-of-frame duration; identical in paired runs. */
public final class NativeFrameProbe {
 private NativeFrameProbe() { }
 public static void premain(final String output, final Instrumentation instrumentation) {
  instrumentation.addTransformer(new ClassFileTransformer() {
   @Override public byte[] transform(ClassLoader loader,String name,Class<?> redefining,ProtectionDomain domain,byte[] original) {
    if(!name.equals("net/minecraft/client/gui/components/DebugScreenOverlay"))return null;
    ClassNode type=new ClassNode();new ClassReader(original).accept(type,0);
    type.fields.add(new FieldNode(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC,"spectralQaFrameSamples","[J",null,null));
    type.fields.add(new FieldNode(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC,"spectralQaFrameIndex","I",null,null));
    type.fields.add(new FieldNode(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC|Opcodes.ACC_VOLATILE,"spectralQaFramesRemaining","I",null,null));
    int changed=0;
    for(MethodNode method:type.methods)if(method.desc.equals("(J)V")&&(method.name.equals("logFrameDuration")||method.name.equals("m_294107_"))) {
     LabelNode done=new LabelNode();InsnList sample=new InsnList();
     sample.add(new FieldInsnNode(Opcodes.GETSTATIC,type.name,"spectralQaFramesRemaining","I"));sample.add(new JumpInsnNode(Opcodes.IFEQ,done));
     sample.add(new FieldInsnNode(Opcodes.GETSTATIC,type.name,"spectralQaFrameSamples","[J"));
     sample.add(new FieldInsnNode(Opcodes.GETSTATIC,type.name,"spectralQaFrameIndex","I"));sample.add(new VarInsnNode(Opcodes.LLOAD,1));sample.add(new InsnNode(Opcodes.LASTORE));
     sample.add(new FieldInsnNode(Opcodes.GETSTATIC,type.name,"spectralQaFrameIndex","I"));sample.add(new InsnNode(Opcodes.ICONST_1));sample.add(new InsnNode(Opcodes.IADD));sample.add(new FieldInsnNode(Opcodes.PUTSTATIC,type.name,"spectralQaFrameIndex","I"));
     sample.add(new FieldInsnNode(Opcodes.GETSTATIC,type.name,"spectralQaFramesRemaining","I"));sample.add(new InsnNode(Opcodes.ICONST_1));sample.add(new InsnNode(Opcodes.ISUB));sample.add(new FieldInsnNode(Opcodes.PUTSTATIC,type.name,"spectralQaFramesRemaining","I"));
     sample.add(done);sample.add(new FrameNode(Opcodes.F_SAME,0,null,0,null));method.instructions.insert(sample);changed++;
    }
    if(changed!=1)throw new IllegalStateException("Exact native end-frame logger absent");
    ClassWriter writer=new ClassWriter(ClassWriter.COMPUTE_MAXS);type.accept(writer);return writer.toByteArray();
   }
  },false);
  Thread controller=new Thread(() -> {
   String previous="";
   while(true)try {
    Path request=Paths.get(output+".request");
    if(!Files.exists(request)){Thread.sleep(20);continue;}
    List<String> lines=Files.readAllLines(request);
    if(lines.size()!=2||!lines.get(0).matches("[a-f0-9]{32}")||!lines.get(1).equals("1800"))throw new IllegalArgumentException("Malformed frame request");
    String token=lines.get(0);if(token.equals(previous)){Thread.sleep(20);continue;}previous=token;
    Class<?> nativeType=null;
    for(Class<?> loaded:instrumentation.getAllLoadedClasses())if(loaded.getName().equals("net.minecraft.client.gui.components.DebugScreenOverlay")){nativeType=loaded;break;}
    if(nativeType==null)throw new IllegalStateException("Native frame logger not yet loaded");
    Field samples=nativeType.getField("spectralQaFrameSamples"),index=nativeType.getField("spectralQaFrameIndex"),remaining=nativeType.getField("spectralQaFramesRemaining");
    if(remaining.getInt(null)!=0)throw new IllegalStateException("Overlapping frame trial");
    long[] values=new long[1800];samples.set(null,values);index.setInt(null,0);remaining.setInt(null,values.length);
    long deadline=System.nanoTime()+180_000_000_000L;
    while(remaining.getInt(null)!=0&&System.nanoTime()<deadline)Thread.sleep(20);
    if(remaining.getInt(null)!=0||index.getInt(null)!=1800)throw new IllegalStateException("Native frame sample timeout");
    for(long value:values)if(value<=0)throw new IllegalStateException("Invalid native frame duration");
    Path temporary=Paths.get(output+".response.tmp");Files.writeString(temporary,token+"\nCOMPLETE\n"+Arrays.toString(values)+"\n");Files.move(temporary,Paths.get(output+".response"),StandardCopyOption.REPLACE_EXISTING);
   }catch(InterruptedException stopped){return;}catch(Exception error){
    try{Files.writeString(Paths.get(output+".response"),previous+"\nFAILED\n"+error+"\n");Thread.sleep(1000);}catch(IOException|InterruptedException stopped){return;}
   }
  },"spectral-qa-native-frame-observer");controller.setDaemon(true);controller.start();
 }
}
