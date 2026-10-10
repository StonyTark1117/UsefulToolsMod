package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
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

/** Private 1.20.2 NeoForge trial: restore exact pinned title actions in OptiFine donors. */
public final class OptifineNativeTitleActions {
 private OptifineNativeTitleActions() { }
 private static final Set<String> MISSING_REFERENCES=new java.util.LinkedHashSet<>();

 private static ClassNode read(ZipFile jar,String path) throws Exception {
  ZipEntry entry=jar.getEntry(path);
  if(entry==null)throw new IllegalStateException("Exact class absent: "+path);
  ClassNode result=new ClassNode();
  try(var input=jar.getInputStream(entry)) {new ClassReader(input.readAllBytes()).accept(result,0);}
  return result;
 }

 private static Map<String,String> memberAliases(Path mappings,String owner) throws Exception {
  Map<String,String> aliases=new java.util.HashMap<>();
  boolean selected=false;
  try(var reader=Files.newBufferedReader(mappings)) {
   String line;
   while((line=reader.readLine())!=null) {
    if(!line.startsWith("\t")) {
     if(selected)break;
     selected=line.startsWith(owner+" ");
     continue;
    }
    if(!selected)continue;
    String[] tokens=line.trim().split("\\s+");
    if(tokens.length==2)aliases.put(tokens[0],tokens[1]);
    else if(tokens.length==3)aliases.put(tokens[0]+tokens[1],tokens[2]);
   }
  }
  if(aliases.isEmpty())throw new IllegalStateException("Pinned member mappings absent: "+owner);
  return aliases;
 }

 private static void checkReference(ClassNode publisher,ClassNode nativeClass,Map<String,String> aliases,
                                    String owner,String name,String desc,boolean field) {
  if(!publisher.name.equals(owner))return;
  boolean found=field?publisher.fields.stream().anyMatch(f->f.name.equals(name)&&f.desc.equals(desc)):
                      publisher.methods.stream().anyMatch(m->m.name.equals(name)&&m.desc.equals(desc));
  String alias=aliases.get(field?name:name+desc);
  if(!found&&alias!=null)
   found=field?publisher.fields.stream().anyMatch(f->f.name.equals(alias)&&f.desc.equals(desc)):
               publisher.methods.stream().anyMatch(m->m.name.equals(alias)&&m.desc.equals(desc));
  if(found)return;
  boolean declaredNative=field?nativeClass.fields.stream().anyMatch(f->f.name.equals(name)&&f.desc.equals(desc)):
                               nativeClass.methods.stream().anyMatch(m->m.name.equals(name)&&m.desc.equals(desc));
  if(!declaredNative&&publisher.superName.equals(nativeClass.superName))return;
  MISSING_REFERENCES.add(owner+"."+name+desc);
 }

 public static void main(String[] args) throws Exception {
  if(args.length!=5)throw new IllegalArgumentException("loader native-client publisher-patched official-to-srg output-dir");
  String owner="net/minecraft/client/gui/screens/TitleScreen";
  Set<String> names=Set.of("lambda$render$13","lambda$createDemoMenuOptions$11",
   "lambda$createDemoMenuOptions$10","lambda$createNormalMenuOptions$8",
   "lambda$createNormalMenuOptions$7","lambda$init$6");
  try(ZipFile loader=new ZipFile(args[0]);ZipFile nativeJar=new ZipFile(args[1]);ZipFile publisher=new ZipFile(args[2])) {
   ClassNode donor=read(loader,"optifineoforge/donors/"+owner+".class");
   ClassNode nativeClass=read(nativeJar,owner+".class");
   ClassNode publisherClass=read(publisher,"srg/"+owner+".class");
   Map<String,String> aliases=memberAliases(Path.of(args[3]),owner);
   int matched=0,replaced=0;
   var unresolved=new java.util.LinkedHashMap<String,Set<String>>();
   for(int i=0;i<donor.methods.size();i++) {
    MethodNode old=donor.methods.get(i);
    if(!names.contains(old.name))continue;
    var body=new java.util.ArrayList<Integer>();
    for(AbstractInsnNode insn:old.instructions)if(insn.getOpcode()>=0)body.add(insn.getOpcode());
    if(!body.equals(List.of(Opcodes.RETURN)))
     throw new IllegalStateException("Refusing to replace non-placeholder title action: "+old.name+old.desc);
    matched++;
    MISSING_REFERENCES.clear();
    MethodNode source=nativeClass.methods.stream()
     .filter(m->m.name.equals(old.name)&&m.desc.equals(old.desc)).findFirst().orElseThrow();
    MethodNode copy=new MethodNode(old.access,old.name,old.desc,old.signature,null);
    source.accept(copy);copy.access=old.access;
    for(AbstractInsnNode insn:copy.instructions) {
     if(insn instanceof FieldInsnNode field)
      checkReference(publisherClass,nativeClass,aliases,field.owner,field.name,field.desc,true);
     if(insn instanceof MethodInsnNode method)
      checkReference(publisherClass,nativeClass,aliases,method.owner,method.name,method.desc,false);
     if(insn instanceof InvokeDynamicInsnNode dynamic)
      for(Object argument:dynamic.bsmArgs)if(argument instanceof Handle handle)
       checkReference(publisherClass,nativeClass,aliases,handle.getOwner(),handle.getName(),handle.getDesc(),
        handle.getTag()<=Opcodes.H_PUTSTATIC);
    }
    if(MISSING_REFERENCES.isEmpty()) {donor.methods.set(i,copy);replaced++;}
    else unresolved.put(old.name,Set.copyOf(MISSING_REFERENCES));
   }
   if(matched!=names.size())throw new IllegalStateException("Expected six title actions, found "+matched);
   if(replaced!=names.size())throw new IllegalStateException("Native title actions still unresolved: "+unresolved);
   // The native method carries its own stack maps; frame recomputation would
   // require loading Minecraft classes into this offline checker JVM.
   ClassWriter writer=new ClassWriter(ClassWriter.COMPUTE_MAXS);
   donor.accept(writer);byte[] bytes=writer.toByteArray();
   ClassNode verified=new ClassNode();new ClassReader(bytes).accept(verified,0);
   for(MethodNode method:verified.methods)if(method.instructions.size()>0)
    new Analyzer<>(new BasicVerifier()).analyze(verified.name,method);
   Path target=Path.of(args[4]).resolve(owner+".class");
   Files.createDirectories(target.getParent());Files.write(target,bytes);
   System.out.println("Restored exact pinned NeoForge TitleScreen actions: "+replaced);
   System.out.println("Actions requiring absent publisher members: "+unresolved);
  }
 }
}
