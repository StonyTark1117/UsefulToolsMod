package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.*;
import java.util.*;
import java.util.zip.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;

/** Private actual-state experiment; retains real native backup and restore bodies. */
public final class OptifineNativeGlBackup {
 private OptifineNativeGlBackup() { }
 private static ClassNode read(ZipFile jar,String name,boolean donor) throws Exception {
  String key=(donor?"optifineoforge/donors/":"")+name+".class";
  ZipEntry entry=jar.getEntry(key);
  if(entry==null)entry=jar.getEntry("srg/"+name+".class");
  if(entry==null)throw new IllegalStateException("Exact class absent: "+key);
  ClassNode node=new ClassNode();
  try(var input=jar.getInputStream(entry)) {new ClassReader(input.readAllBytes()).accept(node,0);}
  return node;
 }
 private static void earlyContextScope(MethodNode method,String owner) {
  if(!method.desc.equals("(Lnet/neoforged/neoforge/client/GlStateBackup;)V")
     ||(method.access&Opcodes.ACC_STATIC)==0)throw new IllegalStateException("Unexpected native backup signature");
  LabelNode context=new LabelNode(),start=new LabelNode(),end=new LabelNode(),handler=new LabelNode();
  InsnList prefix=new InsnList();
  prefix.add(new FieldInsnNode(Opcodes.GETSTATIC,owner,"renderThread","Ljava/lang/Thread;"));
  prefix.add(new VarInsnNode(Opcodes.ASTORE,1));
  prefix.add(new VarInsnNode(Opcodes.ALOAD,1));prefix.add(new JumpInsnNode(Opcodes.IFNONNULL,start));
  prefix.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"org/lwjgl/glfw/GLFW","glfwGetCurrentContext","()J",false));
  prefix.add(new InsnNode(Opcodes.LCONST_0));prefix.add(new InsnNode(Opcodes.LCMP));prefix.add(new JumpInsnNode(Opcodes.IFNE,context));
  prefix.add(new TypeInsnNode(Opcodes.NEW,"java/lang/IllegalStateException"));prefix.add(new InsnNode(Opcodes.DUP));
  prefix.add(new LdcInsnNode("Native GL backup before render initialization requires a current GLFW context"));
  prefix.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,"java/lang/IllegalStateException","<init>","(Ljava/lang/String;)V",false));prefix.add(new InsnNode(Opcodes.ATHROW));
  prefix.add(context);prefix.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"java/lang/Thread","currentThread","()Ljava/lang/Thread;",false));
  prefix.add(new FieldInsnNode(Opcodes.PUTSTATIC,owner,"renderThread","Ljava/lang/Thread;"));prefix.add(start);
  method.instructions.insert(prefix);
  int returns=0;
  for(AbstractInsnNode instruction:method.instructions.toArray())if(instruction.getOpcode()==Opcodes.RETURN) {
   InsnList restore=new InsnList();restore.add(new VarInsnNode(Opcodes.ALOAD,1));
   restore.add(new FieldInsnNode(Opcodes.PUTSTATIC,owner,"renderThread","Ljava/lang/Thread;"));
   method.instructions.insertBefore(instruction,restore);returns++;
  }
  if(returns!=1)throw new IllegalStateException("Expected one native backup wrapper return");
  method.instructions.add(end);method.instructions.add(handler);
  method.instructions.add(new VarInsnNode(Opcodes.ASTORE,2));method.instructions.add(new VarInsnNode(Opcodes.ALOAD,1));
  method.instructions.add(new FieldInsnNode(Opcodes.PUTSTATIC,owner,"renderThread","Ljava/lang/Thread;"));
  method.instructions.add(new VarInsnNode(Opcodes.ALOAD,2));method.instructions.add(new InsnNode(Opcodes.ATHROW));
  method.tryCatchBlocks.add(new TryCatchBlockNode(start,end,handler,null));
  method.maxLocals=Math.max(3,method.maxLocals);
  method.localVariables=null;
 }
 public static void main(String[] args) throws Exception {
  try(ZipFile loader=new ZipFile(args[0]);ZipFile nativeJar=new ZipFile(args[1]);ZipFile runtime=new ZipFile(args[2])) {
   Path output=Path.of(args[3]);Files.createDirectories(output);
   for(String owner:List.of("com/mojang/blaze3d/platform/GlStateManager","com/mojang/blaze3d/systems/RenderSystem")) {
    ClassNode donor=read(loader,owner,true),actual=read(nativeJar,owner,false),patched=read(runtime,owner,false);
    if(owner.endsWith("RenderSystem")&&!patched.fields.stream().anyMatch(f->f.name.equals("renderThread")&&f.desc.equals("Ljava/lang/Thread;")))throw new IllegalStateException("Exact publisher renderThread field absent");
    int count=0;
    for(int i=0;i<donor.methods.size();i++) {
     MethodNode old=donor.methods.get(i);
     if(!Set.of("_backupGlState","_restoreGlState","backupGlState","restoreGlState").contains(old.name))continue;
     List<Integer> body=new ArrayList<>();for(AbstractInsnNode insn:old.instructions)if(insn.getOpcode()>=0)body.add(insn.getOpcode());
     if(!body.equals(List.of(Opcodes.RETURN)))throw new IllegalStateException("Refusing to replace non-empty backup: "+old.name);
     MethodNode source=actual.methods.stream().filter(m->m.name.equals(old.name)&&m.desc.equals(old.desc)).findFirst().orElseThrow();
     MethodNode replacement=new MethodNode(old.access,old.name,old.desc,old.signature,null);source.accept(replacement);replacement.access=old.access;
     // Check real publisher field/method ABI for same-class native cache accesses.
     for(AbstractInsnNode insn:replacement.instructions) {
      if(insn instanceof FieldInsnNode field&&field.owner.equals(owner)
         &&!patched.fields.stream().anyMatch(f->f.name.equals(field.name)&&f.desc.equals(field.desc)))throw new IllegalStateException("Native cache field absent from publisher: "+field.name);
      if(insn instanceof MethodInsnNode call&&call.owner.equals(owner)
         &&!patched.methods.stream().anyMatch(m->m.name.equals(call.name)&&m.desc.equals(call.desc)))throw new IllegalStateException("Native cache method absent from publisher: "+call.name);
     }
     if(owner.endsWith("RenderSystem"))earlyContextScope(replacement,owner);
     donor.methods.set(i,replacement);count++;
    }
    if(count!=2)throw new IllegalStateException("Exact backup/restore pair absent: "+owner);
    // Compute frames only after preserving every native state operation and guard.
    ClassWriter writer=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);donor.accept(writer);
    byte[] bytes=writer.toByteArray();ClassNode verified=new ClassNode();new ClassReader(bytes).accept(verified,0);
    for(MethodNode method:verified.methods)if(method.instructions.size()>0)new Analyzer<>(new BasicVerifier()).analyze(verified.name,method);
    Path path=output.resolve(owner+".class");Files.createDirectories(path.getParent());Files.write(path,bytes);
    System.out.println("Restored actual native state pair: "+owner+"; method stacks verified; publisher cache ABI checked");
   }
  }
 }
}
