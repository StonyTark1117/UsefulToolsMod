package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.BasicVerifier;

/** Private 1.20.2 NeoForge trial: restore exact native chunk task callbacks. */
public final class OptifineNativeChunkCallbacks {
 private OptifineNativeChunkCallbacks() { }
 private static final String PREFIX="net/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection$";
 private static final List<String> OWNERS=List.of(PREFIX+"RebuildTask",PREFIX+"ResortTransparencyTask");

 private static ClassNode read(ZipFile jar,String path) throws Exception {
  var entry=jar.getEntry(path);
  if(entry==null)throw new IllegalStateException("Exact class absent: "+path);
  ClassNode result=new ClassNode();
  try(var input=jar.getInputStream(entry)) {new ClassReader(input.readAllBytes()).accept(result,0);}
  return result;
 }

 private static void checkReference(ClassNode publisher,ClassNode nativeClass,
                                    String owner,String name,String desc,boolean field,Set<String> missing) {
  if(!publisher.name.equals(owner))return;
  boolean found=field?publisher.fields.stream().anyMatch(f->f.name.equals(name)&&f.desc.equals(desc)):
                      publisher.methods.stream().anyMatch(m->m.name.equals(name)&&m.desc.equals(desc));
  if(found)return;
  boolean declaredNative=field?nativeClass.fields.stream().anyMatch(f->f.name.equals(name)&&f.desc.equals(desc)):
                               nativeClass.methods.stream().anyMatch(m->m.name.equals(name)&&m.desc.equals(desc));
  if(!declaredNative&&publisher.superName.equals(nativeClass.superName))return;
  missing.add(owner+"."+name+desc);
 }

 public static void main(String[] args) throws Exception {
  if(args.length!=4)throw new IllegalArgumentException("loader native-client publisher-patched output-dir");
  try(ZipFile loader=new ZipFile(args[0]);ZipFile nativeJar=new ZipFile(args[1]);ZipFile publisher=new ZipFile(args[2])) {
   int restored=0;
   for(String owner:OWNERS) {
    ClassNode donor=read(loader,"optifineoforge/donors/"+owner+".class");
    ClassNode nativeClass=read(nativeJar,owner+".class");
    ClassNode publisherClass=read(publisher,"srg/"+owner+".class");
    int found=0;
    for(int i=0;i<donor.methods.size();i++) {
     MethodNode old=donor.methods.get(i);
     if(!old.name.equals("lambda$doTask$1"))continue;
     found++;
     var opcodes=new java.util.ArrayList<Integer>();
     for(AbstractInsnNode insn:old.instructions)if(insn.getOpcode()>=0)opcodes.add(insn.getOpcode());
     if(!opcodes.equals(List.of(Opcodes.ACONST_NULL,Opcodes.ARETURN)))
      throw new IllegalStateException("Refusing to replace non-placeholder callback: "+owner+old.desc);
     MethodNode source=nativeClass.methods.stream()
      .filter(m->m.name.equals(old.name)&&m.desc.equals(old.desc)).findFirst().orElseThrow();
     MethodNode copy=new MethodNode(old.access,old.name,old.desc,old.signature,null);
     source.accept(copy);copy.access=old.access;
     Set<String> missing=new java.util.LinkedHashSet<>();
     for(AbstractInsnNode insn:copy.instructions) {
      if(insn instanceof FieldInsnNode field)
       checkReference(publisherClass,nativeClass,field.owner,field.name,field.desc,true,missing);
      if(insn instanceof MethodInsnNode method)
       checkReference(publisherClass,nativeClass,method.owner,method.name,method.desc,false,missing);
      if(insn instanceof InvokeDynamicInsnNode dynamic)
       for(Object argument:dynamic.bsmArgs)if(argument instanceof Handle handle)
        checkReference(publisherClass,nativeClass,handle.getOwner(),handle.getName(),handle.getDesc(),
         handle.getTag()<=Opcodes.H_PUTSTATIC,missing);
     }
     if(!missing.isEmpty())throw new IllegalStateException("Pinned chunk callback needs absent publisher members: "+missing);
     donor.methods.set(i,copy);restored++;
    }
    if(found!=1)throw new IllegalStateException("Expected one native chunk callback in "+owner+", found "+found);
    ClassWriter writer=new ClassWriter(ClassWriter.COMPUTE_MAXS);
    donor.accept(writer);byte[] bytes=writer.toByteArray();
    ClassNode verified=new ClassNode();new ClassReader(bytes).accept(verified,0);
    for(MethodNode method:verified.methods)if(method.instructions.size()>0)
     new Analyzer<>(new BasicVerifier()).analyze(verified.name,method);
    Path target=Path.of(args[3]).resolve(owner+".class");
    Files.createDirectories(target.getParent());Files.write(target,bytes);
   }
   System.out.println("Restored exact pinned NeoForge chunk callbacks: "+restored);
  }
 }
}
