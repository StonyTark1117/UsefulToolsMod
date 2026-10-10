package com.stonytark.lightingqa;
import java.lang.instrument.*;
import java.security.ProtectionDomain;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import com.stonytark.lightingqa.asm.*;
/** Private native-loader diagnosis: record actual post-loader bytecode; add diagnostic entry logging only; never change world or event results. */
public final class ForgeReloadProbe {
 public static void premain(String argument,Instrumentation instrumentation){
  final Path output=Paths.get(argument);
  instrumentation.addTransformer(new ClassFileTransformer(){
   public byte[] transform(ClassLoader loader,String name,Class<?> cls,ProtectionDomain domain,byte[] bytes){
    if(name==null||!(name.equals("net/minecraft/server/ReloadableServerResources")||name.equals("net/minecraftforge/common/ForgeInternalHandler")||name.equals("net/minecraftforge/common/MinecraftForge")||name.equals("net/minecraftforge/event/ForgeEventFactory")))return null;
    try{Files.createDirectories(output);Files.write(output.resolve(name.replace('/','_')+".class"),bytes);
     Files.write(output.resolve(name.replace('/','_')+".origin"),("loader="+loader.getClass().getName()+"\nsource="+(domain==null||domain.getCodeSource()==null?"unknown":domain.getCodeSource().getLocation().toString())+"\n").getBytes(StandardCharsets.UTF_8));
    }catch(Exception error){throw new IllegalStateException(error);}
    ClassReader reader=new ClassReader(bytes);ClassWriter writer=new ClassWriter(reader,ClassWriter.COMPUTE_MAXS);
    final String owner=name;
    reader.accept(new ClassVisitor(Opcodes.ASM9,writer){
      public MethodVisitor visitMethod(int access,String method,String descriptor,String signature,String[] exceptions){
        MethodVisitor visitor=super.visitMethod(access,method,descriptor,signature,exceptions);
        final boolean observed=(owner.endsWith("ForgeInternalHandler")&&(method.equals("register")||method.equals("onResourceReload"))) || (owner.endsWith("ForgeEventFactory")&&method.equals("onResourceReload")) || (owner.endsWith("ReloadableServerResources")&&method.equals("lambda$loadResources$2"));
        if(!observed)return visitor;
        final String message="USEFUL_TOOLS_FORGE_RELOAD_PROBE "+owner+"::"+method;
        return new MethodVisitor(Opcodes.ASM9,visitor){
          public void visitCode(){super.visitCode();super.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");super.visitLdcInsn(message);super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V",false);}
        };
      }
    },0);
    return writer.toByteArray();
   }
  });
 }
}
