import java.lang.instrument.*;
import java.security.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
public final class NativeClassProbe {
 public static void premain(String arg,Instrumentation instrumentation){
  instrumentation.addTransformer(new ClassFileTransformer(){
   public byte[] transform(ClassLoader loader,String name,Class<?> redef,ProtectionDomain domain,byte[] bytes){
    if(name!=null&&(name.equals("net/minecraft/server/ReloadableServerResources")||name.equals("net/minecraftforge/common/ForgeInternalHandler")||name.equals("net/minecraftforge/event/ForgeEventFactory"))){
     try{Path out=Paths.get(arg);Files.createDirectories(out);Files.write(out.resolve(name.replace('/','_')+".class"),bytes);
      String constants=new String(bytes,StandardCharsets.ISO_8859_1);
      System.out.println("USEFUL_TOOLS_NATIVE_CLASS name="+name+" loader="+loader+" origin="+(domain==null?null:domain.getCodeSource())+" reloadHook="+constants.contains("onResourceReload"));
     }catch(Exception e){System.out.println("USEFUL_TOOLS_NATIVE_CLASS_ERROR "+e);}
    }return null;
   }
  });
 }
}
