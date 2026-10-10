import java.nio.file.*;
import java.lang.instrument.*;
import java.lang.reflect.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;
import com.stonytark.usefultoolsmod.spectralqa.OptifineOutlineBridge;
public final class OutlineBytecodeCheck {
 public static void main(String[] args) throws Exception {
  ClassFileTransformer[] hook=new ClassFileTransformer[1];
  Instrumentation instrumentation=(Instrumentation)Proxy.newProxyInstance(OutlineBytecodeCheck.class.getClassLoader(),new Class<?>[]{Instrumentation.class},(proxy,method,values)->{if(method.getName().equals("addTransformer"))hook[0]=(ClassFileTransformer)values[0];return null;});
  OptifineOutlineBridge.premain(args[1],instrumentation);
  byte[] source=Files.readAllBytes(Path.of(args[0]));byte[] result=hook[0].transform(null,"net/minecraft/client/renderer/ShaderInstance",null,null,source);
  ClassNode original=new ClassNode();new ClassReader(source).accept(original,0);
  ClassNode transformed=new ClassNode();new ClassReader(result).accept(transformed,0);
  if(original.methods.size()!=transformed.methods.size()||original.fields.size()!=transformed.fields.size())throw new AssertionError("Native members changed");
  int changed=0;
  for(int i=0;i<original.methods.size();i++) {
   MethodNode a=original.methods.get(i),b=transformed.methods.get(i);
   if(!a.name.equals(b.name)||!a.desc.equals(b.desc))throw new AssertionError("Native signatures changed");
   if(a.instructions.size()!=b.instructions.size()) {new Analyzer<BasicValue>(new BasicVerifier()).analyze(transformed.name,b);changed++;}
  }
  if(changed!=1)throw new AssertionError("Expected only actual shader apply to change: "+changed);
  Files.write(Path.of(args[2]),result);
  System.out.println("Actual publisher shader bytecode verified: one apply method changed; native members retained; transformed stack verified");
 }
}
