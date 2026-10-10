package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.BasicVerifier;

/** Private pinned-NeoForge experiment: retain actual block-entity culling bounds. */
public final class OptifineNativeBoundingBoxes {
 private OptifineNativeBoundingBoxes() { }

 private static ClassNode read(ZipFile jar, String path) throws Exception {
  ZipEntry entry=jar.getEntry(path);
  if(entry==null)throw new IllegalStateException("Exact class absent: "+path);
  ClassNode node=new ClassNode();
  try(var input=jar.getInputStream(entry)) {new ClassReader(input.readAllBytes()).accept(node,0);}
  return node;
 }

 public static void main(String[] args) throws Exception {
  if(args.length!=4)throw new IllegalArgumentException("loader native-client publisher-patched output-dir");
  try(ZipFile loader=new ZipFile(args[0]);ZipFile nativeJar=new ZipFile(args[1]);ZipFile publisher=new ZipFile(args[2])) {
   Path output=Path.of(args[3]);
   for(String owner:List.of("net/minecraft/client/renderer/blockentity/SignRenderer",
                             "net/minecraft/client/renderer/blockentity/SkullBlockRenderer")) {
    ClassNode donor=read(loader,"optifineoforge/donors/"+owner+".class");
    ClassNode nativeClass=read(nativeJar,owner+".class");
    ClassNode publisherClass=read(publisher,"srg/"+owner+".class");
    int replaced=0;
    for(int i=0;i<donor.methods.size();i++) {
     MethodNode old=donor.methods.get(i);
     if(!old.name.equals("getRenderBoundingBox")||!old.desc.startsWith("(Lnet/minecraft/world/level/block/entity/"))continue;
     if(old.desc.equals("(Lnet/minecraft/world/level/block/entity/BlockEntity;)Lnet/minecraft/world/phys/AABB;"))continue;
     var opcodes=new java.util.ArrayList<Integer>();
     for(AbstractInsnNode insn:old.instructions)if(insn.getOpcode()>=0)opcodes.add(insn.getOpcode());
     if(!opcodes.equals(List.of(Opcodes.ACONST_NULL,Opcodes.ARETURN)))
      throw new IllegalStateException("Refusing to replace non-null bounding-box body: "+owner+old.desc);
     MethodNode source=nativeClass.methods.stream()
      .filter(m->m.name.equals(old.name)&&m.desc.equals(old.desc)).findFirst().orElseThrow();
     MethodNode copy=new MethodNode(old.access,old.name,old.desc,old.signature,null);
     source.accept(copy);copy.access=old.access;
     for(AbstractInsnNode insn:copy.instructions) {
      if(insn instanceof FieldInsnNode field&&field.owner.equals(owner)
       &&publisherClass.fields.stream().noneMatch(f->f.name.equals(field.name)&&f.desc.equals(field.desc)))
       throw new IllegalStateException("Native bounding box uses absent publisher field: "+field.name);
      if(insn instanceof MethodInsnNode method&&method.owner.equals(owner)
       &&publisherClass.methods.stream().noneMatch(m->m.name.equals(method.name)&&m.desc.equals(method.desc)))
       throw new IllegalStateException("Native bounding box uses absent publisher method: "+method.name);
     }
     donor.methods.set(i,copy);replaced++;
    }
    if(replaced!=1)throw new IllegalStateException("Expected one exact native bounding box for "+owner+", found "+replaced);
    ClassWriter writer=new ClassWriter(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);
    donor.accept(writer);byte[] bytes=writer.toByteArray();
    ClassNode verified=new ClassNode();new ClassReader(bytes).accept(verified,0);
    for(MethodNode method:verified.methods)if(method.instructions.size()>0)
     new Analyzer<>(new BasicVerifier()).analyze(verified.name,method);
    Path path=output.resolve(owner+".class");Files.createDirectories(path.getParent());Files.write(path,bytes);
    System.out.println("Restored pinned native block-entity culling bounds: "+owner);
   }
  }
 }
}
