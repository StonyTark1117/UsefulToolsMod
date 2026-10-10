package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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

/** Private 1.20.2 trial: remove an unused synthetic HTTP texture no-op. */
public final class OptifineHttpDefaultPrune {
 private OptifineHttpDefaultPrune() { }
 private static final String OWNER="net/minecraft/client/renderer/texture/HttpTexture";
 private static final String DESC="(Lnet/minecraft/server/packs/resources/ResourceManager;)V";

 private static ClassNode read(Path path) throws Exception {
  ClassNode result=new ClassNode();new ClassReader(Files.readAllBytes(path)).accept(result,0);return result;
 }

 private static boolean references(ClassNode type,String name) {
  for(MethodNode method:type.methods)for(AbstractInsnNode instruction:method.instructions) {
   if(instruction instanceof MethodInsnNode call&&call.owner.equals(OWNER)
    &&call.name.equals(name)&&call.desc.equals(DESC))return true;
   if(instruction instanceof InvokeDynamicInsnNode dynamic)
    for(Object argument:dynamic.bsmArgs)if(argument instanceof Handle handle
     &&handle.getOwner().equals(OWNER)&&handle.getName().equals(name)
     &&handle.getDesc().equals(DESC))return true;
  }
  return false;
 }

 public static void main(String[] args) throws Exception {
  if(args.length!=3)throw new IllegalArgumentException("donor publisher-patched output-class");
  ClassNode donor=read(Path.of(args[0]));ClassNode publisher=read(Path.of(args[1]));
  if(!donor.name.equals(OWNER)||!publisher.name.equals(OWNER))
   throw new IllegalStateException("Exact HTTP texture classes required");
  MethodNode defaultBody=donor.methods.stream()
   .filter(m->m.name.equals("lambda$load$2")&&m.desc.equals(DESC)).findFirst().orElseThrow();
  var code=new java.util.ArrayList<Integer>();
  for(AbstractInsnNode instruction:defaultBody.instructions)
   if(instruction.getOpcode()>=0)code.add(instruction.getOpcode());
  if(!code.equals(List.of(Opcodes.RETURN)))
   throw new IllegalStateException("Refusing non-placeholder HTTP texture callback");
  MethodNode publisherBody=publisher.methods.stream()
   .filter(m->m.name.equals("lambda$loadTexture$2")&&m.desc.equals(DESC)).findFirst().orElseThrow();
  if(publisherBody.instructions.size()<3)
   throw new IllegalStateException("Publisher has no real HTTP texture load callback");
  if(references(donor,defaultBody.name)||references(publisher,defaultBody.name))
   throw new IllegalStateException("HTTP texture placeholder is still referenced");
  donor.methods.remove(defaultBody);
  ClassWriter writer=new ClassWriter(0);donor.accept(writer);byte[] bytes=writer.toByteArray();
  ClassNode verified=new ClassNode();new ClassReader(bytes).accept(verified,0);
  for(MethodNode method:verified.methods)if(method.instructions.size()>0)
   new Analyzer<>(new BasicVerifier()).analyze(verified.name,method);
  Path target=Path.of(args[2]);Files.createDirectories(target.getParent());Files.write(target,bytes);
  System.out.println("Removed unreferenced default HTTP callback; publisher real load callback retained");
 }
}
