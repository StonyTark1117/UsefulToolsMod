package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
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

/** Private 1.20.2 NeoForge trial: preserve the integrated-server halt callback. */
public final class OptifineNativeServerHalt {
 private OptifineNativeServerHalt() { }
 private static final String OWNER="net/minecraft/client/server/IntegratedServer";

 private static ClassNode read(ZipFile jar,String path) throws Exception {
  var entry=jar.getEntry(path);
  if(entry==null)throw new IllegalStateException("Exact class absent: "+path);
  ClassNode result=new ClassNode();
  try(var input=jar.getInputStream(entry)) {new ClassReader(input.readAllBytes()).accept(result,0);}
  return result;
 }

 private static Map<String,String> memberAliases(Path mappings) throws Exception {
  Map<String,String> aliases=new java.util.HashMap<>();
  boolean selected=false;
  try(var reader=Files.newBufferedReader(mappings)) {
   String line;
   while((line=reader.readLine())!=null) {
    if(!line.startsWith("\t")) {
     if(selected)break;
     selected=line.startsWith(OWNER+" ");
     continue;
    }
    if(!selected)continue;
    String[] tokens=line.trim().split("\\s+");
    if(tokens.length==2)aliases.put(tokens[0],tokens[1]);
    else if(tokens.length==3)aliases.put(tokens[0]+tokens[1],tokens[2]);
   }
  }
  if(aliases.isEmpty())throw new IllegalStateException("Pinned member mappings absent: "+OWNER);
  return aliases;
 }

 public static void main(String[] args) throws Exception {
  if(args.length!=5)throw new IllegalArgumentException("loader native-client publisher-patched official-to-srg output-dir");
  try(ZipFile loader=new ZipFile(args[0]);ZipFile nativeJar=new ZipFile(args[1]);ZipFile publisher=new ZipFile(args[2])) {
   ClassNode donor=read(loader,"optifineoforge/donors/"+OWNER+".class");
   ClassNode nativeClass=read(nativeJar,OWNER+".class");
   ClassNode publisherClass=read(publisher,"srg/"+OWNER+".class");
   Map<String,String> aliases=memberAliases(Path.of(args[3]));
   int replaced=0;
   for(int i=0;i<donor.methods.size();i++) {
    MethodNode old=donor.methods.get(i);
    if(!old.name.equals("lambda$halt$3")||!old.desc.equals("()V"))continue;
    var code=new java.util.ArrayList<Integer>();
    for(AbstractInsnNode insn:old.instructions)if(insn.getOpcode()>=0)code.add(insn.getOpcode());
    if(!code.equals(List.of(Opcodes.RETURN)))
     throw new IllegalStateException("Refusing to replace non-placeholder server halt callback");
    MethodNode source=nativeClass.methods.stream()
     .filter(m->m.name.equals(old.name)&&m.desc.equals(old.desc)).findFirst().orElseThrow();
    MethodNode copy=new MethodNode(old.access,old.name,old.desc,old.signature,null);
    source.accept(copy);copy.access=old.access;
    for(AbstractInsnNode insn:copy.instructions) {
     if(insn instanceof FieldInsnNode field&&field.owner.equals(OWNER)) {
      String alias=aliases.get(field.name);
      if(publisherClass.fields.stream().noneMatch(f->f.desc.equals(field.desc)
        &&(f.name.equals(field.name)||f.name.equals(alias))))
       throw new IllegalStateException("Halt callback needs absent publisher field: "+field.name+field.desc);
     }
     if(insn instanceof MethodInsnNode method&&method.owner.equals(OWNER)
      &&publisherClass.methods.stream().noneMatch(m->m.desc.equals(method.desc)
       &&(m.name.equals(method.name)||m.name.equals(aliases.get(method.name+method.desc))))) {
      boolean inherited=nativeClass.methods.stream().noneMatch(m->m.name.equals(method.name)&&m.desc.equals(method.desc))
       &&publisherClass.superName.equals(nativeClass.superName);
      if(!inherited)throw new IllegalStateException("Halt callback needs absent publisher method: "+method.name+method.desc);
     }
    }
    donor.methods.set(i,copy);replaced++;
   }
   if(replaced!=1)throw new IllegalStateException("Expected one pinned halt callback, found "+replaced);
   ClassWriter writer=new ClassWriter(ClassWriter.COMPUTE_MAXS);
   donor.accept(writer);byte[] bytes=writer.toByteArray();
   ClassNode verified=new ClassNode();new ClassReader(bytes).accept(verified,0);
   for(MethodNode method:verified.methods)if(method.instructions.size()>0)
    new Analyzer<>(new BasicVerifier()).analyze(verified.name,method);
   Path target=Path.of(args[4]).resolve(OWNER+".class");
   Files.createDirectories(target.getParent());Files.write(target,bytes);
   System.out.println("Restored exact pinned NeoForge integrated-server halt callback");
  }
 }
}
