import java.util.*;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
/** Compare constant-return donor methods with the exact native declarations. */
public final class NativeDefaultBodyAudit {
 private NativeDefaultBodyAudit() { }
 private static List<Integer> code(MethodNode method) {
  List<Integer> result=new ArrayList<>();
  for(AbstractInsnNode node:method.instructions) if(node.getOpcode()>=0)result.add(node.getOpcode());
  return result;
 }
 private static boolean constant(List<Integer> code) {
  return code.equals(List.of(Opcodes.RETURN))
   || code.equals(List.of(Opcodes.ICONST_0,Opcodes.IRETURN))
   || code.equals(List.of(Opcodes.ACONST_NULL,Opcodes.ARETURN))
   || code.equals(List.of(Opcodes.LCONST_0,Opcodes.LRETURN))
   || code.equals(List.of(Opcodes.FCONST_0,Opcodes.FRETURN))
   || code.equals(List.of(Opcodes.DCONST_0,Opcodes.DRETURN));
 }
 public static void main(String[] args) throws Exception {
  try(ZipFile loader=new ZipFile(args[0]);ZipFile nativeJar=new ZipFile(args[1])) {
   var entries=loader.entries();int suspect=0;int legitimate=0;
   while(entries.hasMoreElements()) {
    var entry=entries.nextElement();
    if(!entry.getName().startsWith("optifineoforge/donors/")||!entry.getName().endsWith(".class"))continue;
    ClassNode donor=new ClassNode();new ClassReader(loader.getInputStream(entry).readAllBytes()).accept(donor,0);
    ClassNode actual=new ClassNode();var nativeEntry=nativeJar.getEntry(donor.name+".class");
    if(nativeEntry!=null)new ClassReader(nativeJar.getInputStream(nativeEntry).readAllBytes()).accept(actual,0);
    for(MethodNode method:donor.methods) {
     List<Integer> body=code(method);if(!constant(body))continue;
     MethodNode same=null;for(MethodNode original:actual.methods)if(original.name.equals(method.name)&&original.desc.equals(method.desc))same=original;
     if(same!=null&&body.equals(code(same))) {legitimate++;continue;}
     suspect++;
     System.out.println(donor.name+"\t"+method.name+method.desc+"\t"+(same==null?"absent_native_declaration":"different_native_body")+"\tparent="+donor.superName);
    }
   }
   System.err.println("Constant-return donors requiring semantic investigation="+suspect+"; same actual native constant body="+legitimate);
  }
 }
}
