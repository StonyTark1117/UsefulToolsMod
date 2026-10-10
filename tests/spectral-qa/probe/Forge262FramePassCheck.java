package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.BasicVerifier;

/** Verify the private 26.2 adapter against both exact Forge signatures. */
public final class Forge262FramePassCheck {
 private Forge262FramePassCheck() { }
 private static ClassNode read(byte[] bytes) {
  ClassNode node=new ClassNode();new ClassReader(bytes).accept(node,0);return node;
 }
 private static byte[] entry(Path jar,String name) throws Exception {
  try(ZipFile archive=new ZipFile(jar.toFile())) {
   var found=archive.getEntry(name);
   if(found==null)throw new IllegalStateException("Exact entry absent: "+name);
   try(var input=archive.getInputStream(found)) {return input.readAllBytes();}
  }
 }
 private static AbstractInsnNode next(AbstractInsnNode node) {
  node=node.getNext();while(node!=null&&node.getOpcode()<0)node=node.getNext();return node;
 }
 private static void expectForgeSignature(Path jar,int argumentCount) throws Exception {
  ClassNode node=read(entry(jar,"net/minecraftforge/client/FramePassManager.class"));
  long matches=node.methods.stream().filter(m->m.name.equals("insertForgePasses")
   &&m.desc.startsWith("(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lnet/minecraft/client/renderer/LevelTargetBundle;Lnet/minecraft/client/renderer/state/level/LevelRenderState;")
   &&m.desc.endsWith(argumentCount==4?"Lnet/minecraft/client/DeltaTracker;)V":")V")).count();
  if(matches!=1)throw new IllegalStateException("Wrong pinned Forge frame-pass signature: "+jar);
 }
 private static List<Integer> opcodes(MethodNode method) {
  var result=new ArrayList<Integer>();for(AbstractInsnNode node:method.instructions)if(node.getOpcode()>=0)result.add(node.getOpcode());return result;
 }
 public static void main(String[] args) throws Exception {
  if(args.length!=5)throw new IllegalArgumentException("patched-publisher forge-65-universal forge-64-universal optifine-2612-preview forge-65-client");
  expectForgeSignature(Path.of(args[1]),4);expectForgeSignature(Path.of(args[2]),3);
  var selection=OptifineGuiBridge.class.getDeclaredMethod("selected",String.class);
  selection.setAccessible(true);
  String flag="usefulToolsSpectralAllowPack262",prior=System.getProperty(flag);
  try {
   System.clearProperty(flag);
   if((boolean)selection.invoke(null,"net/optifine/shaders/Shaders"))
    throw new IllegalStateException("Publisher shader replacement enabled without 26.2 opt-in");
   System.setProperty(flag,"true");
   if(!(boolean)selection.invoke(null,"net/optifine/shaders/Shaders"))
    throw new IllegalStateException("26.2 shader opt-in did not select publisher class");
  } finally {if(prior==null)System.clearProperty(flag);else System.setProperty(flag,prior);}
  ClassNode original=read(entry(Path.of(args[0]),"srg/net/minecraft/client/renderer/LevelRenderer.class"));
  var adapter=OptifineGuiBridge.class.getDeclaredMethod("forge262FramePass",byte[].class);
  adapter.setAccessible(true);
  ClassNode patched=read((byte[])adapter.invoke(null,(Object)entry(Path.of(args[0]),"srg/net/minecraft/client/renderer/LevelRenderer.class")));
  if(original.methods.size()!=patched.methods.size()||original.fields.size()!=patched.fields.size())
   throw new IllegalStateException("Unrelated class members changed");
  for(int i=0;i<original.methods.size();i++) {
   MethodNode before=original.methods.get(i),after=patched.methods.get(i);
   if(!before.name.equals(after.name)||!before.desc.equals(after.desc))throw new IllegalStateException("Method identity changed");
   if(!before.name.equals("render")&&!opcodes(before).equals(opcodes(after)))
    throw new IllegalStateException("Unrelated method body changed: "+before.name);
   if(after.instructions.size()>0)new Analyzer<>(new BasicVerifier()).analyze(patched.name,after);
  }
  MethodNode render=patched.methods.stream().filter(m->m.name.equals("render")
   &&m.desc.startsWith("(Lcom/mojang/blaze3d/resource/GraphicsResourceAllocator;Lnet/minecraft/client/DeltaTracker;"))
   .findFirst().orElseThrow();
  int calls=0;
  for(AbstractInsnNode node:render.instructions) {
   if(!(node instanceof FieldInsnNode field)||!field.name.equals("FramePassManager_insertForgePasses"))continue;
   AbstractInsnNode size=next(node),array=size==null?null:next(size);
   if(size==null||size.getOpcode()!=Opcodes.ICONST_4||!(array instanceof TypeInsnNode type)
    ||type.getOpcode()!=Opcodes.ANEWARRAY||!type.desc.equals("java/lang/Object"))continue;
   int stores=0;AbstractInsnNode cursor=next(array),last=null;
   while(cursor!=null&&!(cursor instanceof org.objectweb.asm.tree.MethodInsnNode method
    &&method.owner.equals("net/optifine/reflect/ReflectorMethod")&&method.name.equals("call"))) {
    if(cursor.getOpcode()==Opcodes.AASTORE){stores++;last=cursor;}cursor=next(cursor);
   }
   if(stores!=4||last==null||!(last.getPrevious() instanceof VarInsnNode load)
    ||load.getOpcode()!=Opcodes.ALOAD||load.var!=2)throw new IllegalStateException("DeltaTracker not fourth argument");
   calls++;
  }
  if(calls!=1)throw new IllegalStateException("Expected one exact four-argument OptiFine frame-pass call");
  byte[] shaderBytes=entry(Path.of(args[0]),"srg/net/optifine/shaders/Shaders.class");
  ClassNode shaderOriginal=read(shaderBytes);
  var enable=OptifineGuiBridge.class.getDeclaredMethod("enablePublisherPack262",byte[].class);
  enable.setAccessible(true);
  ClassNode shaderPatched=read((byte[])enable.invoke(null,(Object)shaderBytes));
  if(shaderOriginal.methods.size()!=shaderPatched.methods.size())throw new IllegalStateException("Unrelated shader methods changed");
  for(int i=0;i<shaderOriginal.methods.size();i++) {
   MethodNode before=shaderOriginal.methods.get(i),after=shaderPatched.methods.get(i);
   if(!before.name.equals(after.name)||!before.desc.equals(after.desc))throw new IllegalStateException("Shader method identity changed");
   int delta=opcodes(before).size()-opcodes(after).size();
   if(delta!=(before.name.equals("loadShaderPack")&&before.desc.equals("()V")?2:0))
    throw new IllegalStateException("Unexpected shader opcode change: "+before.name+" delta="+delta);
   if(after.instructions.size()>0)new Analyzer<>(new BasicVerifier()).analyze(shaderPatched.name,after);
  }
  MethodNode shaderLoad=shaderPatched.methods.stream().filter(m->m.name.equals("loadShaderPack")&&m.desc.equals("()V"))
   .findFirst().orElseThrow();
  boolean antialias=false,fabulous=false;
  for(AbstractInsnNode instruction:shaderLoad.instructions)if(instruction instanceof org.objectweb.asm.tree.MethodInsnNode call) {
   if(call.owner.equals("net/optifine/Config")&&call.name.equals("isAntialiasing"))antialias=true;
   if(call.owner.equals("net/optifine/Config")&&call.name.equals("isGraphicsFabulous"))fabulous=true;
  }
  if(!antialias||!fabulous)throw new IllegalStateException("Publisher shader guards removed");
  ClassNode older=read(entry(Path.of(args[3]),"srg/net/optifine/shaders/Shaders.class"));
  MethodNode oldLoad=older.methods.stream().filter(m->m.name.equals("loadShaderPack")&&m.desc.equals("()V"))
   .findFirst().orElseThrow();
  if(opcodes(oldLoad).size()!=opcodes(shaderLoad).size())throw new IllegalStateException("K2 repaired shader load differs from K1 opcode count");
  byte[] nativeTexture=entry(Path.of(args[4]),"com/mojang/blaze3d/opengl/GlTexture.class");
  byte[] publisherTexture=entry(Path.of(args[0]),"srg/com/mojang/blaze3d/opengl/GlTexture.class");
  var graft=OptifineGuiBridge.class.getDeclaredMethod("nativeGlTexture",byte[].class,byte[].class);
  graft.setAccessible(true);
  ClassNode originalTexture=read(nativeTexture);
  ClassNode graftedTexture=read((byte[])graft.invoke(null,nativeTexture,publisherTexture));
  if(graftedTexture.fields.size()!=originalTexture.fields.size()+2
   ||graftedTexture.methods.size()!=originalTexture.methods.size()+8)
   throw new IllegalStateException("Native GlTexture member counts changed unexpectedly");
  for(int i=0;i<originalTexture.methods.size();i++) {
   MethodNode before=originalTexture.methods.get(i),after=graftedTexture.methods.get(i);
   if(!before.name.equals(after.name)||!before.desc.equals(after.desc))
    throw new IllegalStateException("Pinned native texture method changed: "+before.name);
   int delta=opcodes(after).size()-opcodes(before).size();
   if(delta!=(before.name.equals("destroyImmediately")?4:0))
    throw new IllegalStateException("Unexpected native texture opcode change: "+before.name);
  }
  for(MethodNode method:graftedTexture.methods)
   if(method.instructions.size()>0)new Analyzer<>(new BasicVerifier()).analyze(graftedTexture.name,method);
  MethodNode textureId=graftedTexture.methods.stream().filter(m->m.name.equals("getMultiTexID")
   &&m.desc.equals("()Lnet/optifine/shaders/MultiTexID;")).findFirst().orElseThrow();
  if(!textureId.instructions.iterator().hasNext())throw new IllegalStateException("Shader texture ID method empty");
  boolean delegates=false;
  for(AbstractInsnNode node:textureId.instructions)
   if(node instanceof org.objectweb.asm.tree.MethodInsnNode call
    &&call.owner.equals("net/optifine/shaders/ShadersTex")&&call.name.equals("getMultiTexID"))delegates=true;
  if(!delegates)throw new IllegalStateException("Native texture does not request publisher multi-texture ID");
  MethodNode cleanup=graftedTexture.methods.stream().filter(m->m.name.equals("destroyImmediately")
   &&m.desc.equals("()V")).findFirst().orElseThrow();
  int releases=0;
  for(AbstractInsnNode node:cleanup.instructions)
   if(node instanceof org.objectweb.asm.tree.MethodInsnNode call
    &&call.owner.equals("net/optifine/shaders/ShadersTex")&&call.name.equals("deleteTextures"))releases++;
  if(releases!=1)throw new IllegalStateException("Publisher texture cleanup hook missing");
  System.out.println("Verified Forge 65 four-argument adapter, K2 pack enable, native GlTexture shader-ID and cleanup graft; Forge 64/K1 guards preserved; all method stacks valid.");
 }
}
