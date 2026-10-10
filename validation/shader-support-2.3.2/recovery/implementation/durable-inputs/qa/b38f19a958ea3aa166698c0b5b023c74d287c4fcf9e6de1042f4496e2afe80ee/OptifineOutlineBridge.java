package com.stonytark.usefultoolsmod.spectralqa;
import java.io.*;
import java.lang.instrument.*;
import java.security.ProtectionDomain;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
/** Private native-outline experiment. No entity draw or mask is added. */
public final class OptifineOutlineBridge {
 private OptifineOutlineBridge() { }
 public static void premain(String evidencePath, Instrumentation instrumentation) throws IOException {
  final FileOutputStream evidence=new FileOutputStream(evidencePath);
  instrumentation.addTransformer(new ClassFileTransformer() {
   @Override public byte[] transform(ClassLoader loader,String name,Class<?> redefining,ProtectionDomain domain,byte[] original) {
    if(name.equals("net/minecraft/client/renderer/ShaderInstance")) {
     ClassNode type=new ClassNode();new ClassReader(original).accept(type,0);String shaderName=null;
     for(FieldNode field:type.fields)if(field.desc.equals("Ljava/lang/String;")&&(field.access&Opcodes.ACC_STATIC)==0&&(field.access&Opcodes.ACC_FINAL)!=0) {
      if(shaderName!=null)throw new IllegalStateException("Native shader name field ambiguous");shaderName=field.name;
     }
     if(shaderName==null)throw new IllegalStateException("Native shader name field absent");
     int changed=0;
     for(MethodNode method:type.methods) {
      boolean packProgram=false;
      for(AbstractInsnNode instruction:method.instructions)if(instruction instanceof FieldInsnNode field&&field.owner.equals("net/optifine/shaders/Shaders")&&field.name.equals("activeProgramID"))packProgram=true;
      if(!packProgram||!method.desc.equals("()V"))continue;
      for(AbstractInsnNode instruction:method.instructions.toArray())if(instruction instanceof MethodInsnNode call&&call.owner.equals("net/optifine/Config")&&call.name.equals("isShaders")&&call.desc.equals("()Z")) {
       // Native outline-mask RGBA must use its own shader, not the entity gbuffer program.
       // Keep every other shader and the shadow pass on the publisher's original path.
       InsnList nativeOutline=new InsnList();nativeOutline.add(new LdcInsnNode("rendertype_outline"));
       nativeOutline.add(new VarInsnNode(Opcodes.ALOAD,0));nativeOutline.add(new FieldInsnNode(Opcodes.GETFIELD,type.name,shaderName,"Ljava/lang/String;"));
       nativeOutline.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,"java/lang/String","equals","(Ljava/lang/Object;)Z",false));
       nativeOutline.add(new InsnNode(Opcodes.ICONST_1));nativeOutline.add(new InsnNode(Opcodes.IXOR));
       nativeOutline.add(new FieldInsnNode(Opcodes.GETSTATIC,"net/optifine/shaders/Shaders","isShadowPass","Z"));nativeOutline.add(new InsnNode(Opcodes.IOR));nativeOutline.add(new InsnNode(Opcodes.IAND));
       method.instructions.insert(call,nativeOutline);changed++;
      }
     }
     if(changed!=2)throw new IllegalStateException("Exact publisher native shader apply override pair absent: "+changed);
     ClassWriter writer=new ClassWriter(ClassWriter.COMPUTE_MAXS);type.accept(writer);
     try{evidence.write("NATIVE_OUTLINE_SHADER_INSTANCE overrides=2 only=rendertype_outline shadow_path_retained\n".getBytes("UTF-8"));evidence.flush();}catch(IOException error){throw new IllegalStateException(error);}
     return writer.toByteArray();
    }
    if(name.equals("net/optifine/shaders/Shaders")) {
     ClassNode shaders=new ClassNode();new ClassReader(original).accept(shaders,0);
     boolean found=false;
     for(FieldNode field:shaders.fields)if(field.name.equals("dfb")&&field.desc.equals("Lnet/optifine/shaders/ShadersFramebuffer;")) {field.access|=Opcodes.ACC_PUBLIC;found=true;}
     if(!found)throw new IllegalStateException("Native OptiFine draw framebuffer field absent");
     ClassWriter output=new ClassWriter(0);shaders.accept(output);return output.toByteArray();
    }
    if(name.equals("net/minecraft/client/renderer/RenderStateShard")) {
     ClassNode type=new ClassNode();new ClassReader(original).accept(type,0);String cleanup=null;
     for(MethodNode method:type.methods)if(method.name.equals("<clinit>"))for(AbstractInsnNode instruction:method.instructions) {
      if(instruction instanceof FieldInsnNode field&&field.getOpcode()==Opcodes.PUTSTATIC&&field.owner.equals(type.name)&&field.name.equals("OUTLINE_TARGET")) {
       for(AbstractInsnNode previous=instruction.getPrevious();previous!=null;previous=previous.getPrevious())if(previous instanceof InvokeDynamicInsnNode dynamic) {
        for(Object argument:dynamic.bsmArgs)if(argument instanceof Handle handle&&handle.getOwner().equals(type.name)&&handle.getDesc().equals("()V"))cleanup=handle.getName();
        break;
       }
      }
     }
     if(cleanup==null)throw new IllegalStateException("Exact native outline cleanup handle absent");
     int changed=0;
     for(MethodNode method:type.methods)if(method.name.equals(cleanup)&&method.desc.equals("()V"))for(AbstractInsnNode instruction:method.instructions.toArray())if(instruction.getOpcode()==Opcodes.RETURN) {
      LabelNode done=new LabelNode();InsnList restore=new InsnList();
      restore.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"net/optifine/Config","isShaders","()Z",false));restore.add(new JumpInsnNode(Opcodes.IFEQ,done));
      restore.add(new FieldInsnNode(Opcodes.GETSTATIC,"net/optifine/shaders/Shaders","isShadowPass","Z"));restore.add(new JumpInsnNode(Opcodes.IFNE,done));
      restore.add(new FieldInsnNode(Opcodes.GETSTATIC,"net/optifine/shaders/Shaders","dfb","Lnet/optifine/shaders/ShadersFramebuffer;"));
      restore.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,"net/optifine/shaders/ShadersFramebuffer","bindFramebuffer","()V",false));
      restore.add(done);restore.add(new FrameNode(Opcodes.F_SAME,0,null,0,null));method.instructions.insertBefore(instruction,restore);changed++;
     }
     if(changed!=1)throw new IllegalStateException("Exact native outline output cleanup absent");
     ClassWriter writer=new ClassWriter(ClassWriter.COMPUTE_MAXS);type.accept(writer);
     try{evidence.write(("NATIVE_OUTLINE_OUTPUT_CLEANUP restored=1 method="+cleanup+"\n").getBytes("UTF-8"));evidence.flush();}catch(IOException error){throw new IllegalStateException(error);}
     return writer.toByteArray();
    }
    if(!name.equals("net/minecraft/client/renderer/LevelRenderer"))return null;
    ClassNode type=new ClassNode();new ClassReader(original).accept(type,0);int changed=0;String outlineMethod=null;
    for(MethodNode method:type.methods) {
     if(!method.desc.equals("()Z"))continue;
     MethodInsnNode shader=null;boolean antialiasing=false;
     for(AbstractInsnNode insn:method.instructions)if(insn instanceof MethodInsnNode) {
      MethodInsnNode call=(MethodInsnNode)insn;
      if(call.owner.equals("net/optifine/Config")&&call.desc.equals("()Z")&&call.getOpcode()==Opcodes.INVOKESTATIC) {
       if(call.name.equals("isShaders"))shader=call;
       if(call.name.equals("isAntialiasing"))antialiasing=true;
      }
     }
     if(shader!=null&&antialiasing) {method.instructions.set(shader,new FieldInsnNode(Opcodes.GETSTATIC,"net/optifine/shaders/Shaders","isShadowPass","Z"));changed++;outlineMethod=method.name;}
    }
    if(changed!=1)throw new IllegalStateException("Expected exact publisher native-outline shader guard, found "+changed);
    int rebound=0;
    for(MethodNode method:type.methods) {
     for(AbstractInsnNode insn:method.instructions.toArray()) {
      if(!(insn instanceof MethodInsnNode))continue;
      MethodInsnNode call=(MethodInsnNode)insn;
      if(!call.owner.equals("com/mojang/blaze3d/pipeline/RenderTarget")||!call.desc.equals("(Z)V"))continue;
      AbstractInsnNode nearest=call.getPrevious();
      while(nearest!=null&&!(nearest instanceof MethodInsnNode))nearest=nearest.getPrevious();
      if(!(nearest instanceof MethodInsnNode))continue;
      MethodInsnNode main=(MethodInsnNode)nearest;
      if(!main.owner.equals("net/minecraft/client/Minecraft")||!main.desc.equals("()Lcom/mojang/blaze3d/pipeline/RenderTarget;"))continue;
      boolean guarded=false, mainTarget=false;int previous=0;
      for(AbstractInsnNode before=call.getPrevious();before!=null&&previous++<18;before=before.getPrevious())if(before instanceof MethodInsnNode) {
       MethodInsnNode prior=(MethodInsnNode)before;
       if(prior.owner.equals(type.name)&&prior.name.equals(outlineMethod)&&prior.desc.equals("()Z"))guarded=true;
       if(prior.owner.equals("net/minecraft/client/Minecraft")&&prior.desc.equals("()Lcom/mojang/blaze3d/pipeline/RenderTarget;"))mainTarget=true;
      }
      if(!guarded||!mainTarget)continue;
      LabelNode done=new LabelNode();InsnList restore=new InsnList();
      restore.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"net/optifine/Config","isShaders","()Z",false));
      restore.add(new JumpInsnNode(Opcodes.IFEQ,done));
      restore.add(new FieldInsnNode(Opcodes.GETSTATIC,"net/optifine/shaders/Shaders","dfb","Lnet/optifine/shaders/ShadersFramebuffer;"));
      restore.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,"net/optifine/shaders/ShadersFramebuffer","bindFramebuffer","()V",false));
      restore.add(done);restore.add(new FrameNode(Opcodes.F_SAME,0,null,0,null));
      method.instructions.insert(call,restore);rebound++;
     }
    }
    if(rebound!=1)throw new IllegalStateException("Expected exact native outline-clear main-target rebind, found "+rebound);
    ClassWriter writer=new ClassWriter(ClassWriter.COMPUTE_MAXS);type.accept(writer);
    try {evidence.write("NATIVE_OUTLINE_SHADER_GUARD removed=1 shadow_guard_retained native_draw_framebuffer_restored=1 all_other_native_conditions_retained\n".getBytes("UTF-8"));evidence.flush();}catch(IOException e){throw new IllegalStateException(e);}
    return writer.toByteArray();
   }
  },false);
 }
}
