package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.BasicVerifier;

/** Private 1.20.2 trial: let the actual OptiFine video screen inherit defaults. */
public final class OptifineVideoDefaultPrune {
 private OptifineVideoDefaultPrune() { }
 private static final String OWNER="net/minecraft/client/gui/screens/VideoSettingsScreen";
 private static final Map<String,List<Integer>> DEFAULTS=Map.of(
  "mouseScrolled(DDDD)Z",List.of(Opcodes.ICONST_0,Opcodes.IRETURN),
  "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",List.of(Opcodes.RETURN),
  "lambda$mouseClicked$4(Lnet/minecraft/client/gui/components/Button;)V",List.of(Opcodes.RETURN),
  "lambda$mouseClicked$3(Lnet/minecraft/client/gui/components/Button;)V",List.of(Opcodes.RETURN),
  "lambda$init$2(Lcom/mojang/blaze3d/platform/Window;Lnet/minecraft/client/gui/components/Button;)V",List.of(Opcodes.RETURN));

 private static ClassNode read(Path path) throws Exception {
  ClassNode result=new ClassNode();new ClassReader(Files.readAllBytes(path)).accept(result,0);return result;
 }

 private static boolean references(ClassNode type,String name,String desc) {
  for(MethodNode method:type.methods)for(AbstractInsnNode instruction:method.instructions) {
   if(instruction instanceof MethodInsnNode call&&call.owner.equals(OWNER)
    &&call.name.equals(name)&&call.desc.equals(desc))return true;
   if(instruction instanceof InvokeDynamicInsnNode dynamic)
    for(Object argument:dynamic.bsmArgs)if(argument instanceof Handle handle
     &&handle.getOwner().equals(OWNER)&&handle.getName().equals(name)
     &&handle.getDesc().equals(desc))return true;
  }
  return false;
 }

 public static void main(String[] args) throws Exception {
  if(args.length!=5)throw new IllegalArgumentException("donor publisher-video publisher-parent native-screen output-class");
  ClassNode donor=read(Path.of(args[0]));
  ClassNode publisher=read(Path.of(args[1]));
  ClassNode parent=read(Path.of(args[2]));
  ClassNode nativeScreen=read(Path.of(args[3]));
  if(!donor.name.equals(OWNER)||!publisher.name.equals(OWNER)
   ||!publisher.superName.equals(parent.name)
   ||!parent.superName.equals(nativeScreen.name)
   ||!nativeScreen.name.equals("net/minecraft/client/gui/screens/Screen"))
   throw new IllegalStateException("Exact pinned video/OptiFine/Screen inheritance mismatch");
  MethodNode inheritedBackground=nativeScreen.methods.stream()
   .filter(m->m.name.equals("renderBackground")
    &&m.desc.equals("(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"))
   .findFirst().orElseThrow();
  if(inheritedBackground.instructions.size()<3)
   throw new IllegalStateException("Native Screen background has no useful body");
  int removed=0;
  for(MethodNode method:List.copyOf(donor.methods)) {
   String key=method.name+method.desc;
   List<Integer> expected=DEFAULTS.get(key);
   if(expected==null)continue;
   var actual=new java.util.ArrayList<Integer>();
   for(AbstractInsnNode instruction:method.instructions)
    if(instruction.getOpcode()>=0)actual.add(instruction.getOpcode());
   if(!actual.equals(expected))throw new IllegalStateException("Refusing non-default video method: "+key);
   if(references(publisher,method.name,method.desc))
    throw new IllegalStateException("Publisher references video override: "+key);
   donor.methods.remove(method);removed++;
  }
  if(removed!=DEFAULTS.size())throw new IllegalStateException("Expected five default overrides, removed "+removed);
  for(String key:DEFAULTS.keySet()) {
   int index=key.indexOf('(');String name=key.substring(0,index),desc=key.substring(index);
   if(references(donor,name,desc))throw new IllegalStateException("Remaining donor references pruned method: "+key);
  }
  ClassWriter writer=new ClassWriter(0);donor.accept(writer);byte[] bytes=writer.toByteArray();
  ClassNode verified=new ClassNode();new ClassReader(bytes).accept(verified,0);
  for(MethodNode method:verified.methods)if(method.instructions.size()>0)
   new Analyzer<>(new BasicVerifier()).analyze(verified.name,method);
  Path target=Path.of(args[4]);Files.createDirectories(target.getParent());Files.write(target,bytes);
  System.out.println("Pruned five verified placeholder overrides; native/publisher inheritance retained");
 }
}
