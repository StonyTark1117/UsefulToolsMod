import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
public final class NativeFieldRelocation {
 public static void main(String[] args) throws Exception {
  int changed=0;
  try(ZipFile src=new ZipFile(args[0]);ZipOutputStream dst=new ZipOutputStream(Files.newOutputStream(Path.of(args[1])))) {
   for(ZipEntry e:Collections.list(src.entries())) {
    if(e.isDirectory())continue;
    byte[] data;try(InputStream in=src.getInputStream(e)){data=in.readAllBytes();}
    if(e.getName().endsWith(".class")) {
     ClassNode n=new ClassNode();new ClassReader(data).accept(n,0);boolean edit=false;
     for(MethodNode m:n.methods)for(AbstractInsnNode insn:m.instructions) {
      if(insn instanceof FieldInsnNode f && f.name.equals("INFINITE_EXTENT_AABB") && f.desc.equals("Lnet/minecraft/world/phys/AABB;") && f.getOpcode()==Opcodes.GETSTATIC && f.owner.equals("net/neoforged/neoforge/common/extensions/IBlockEntityExtension")) {
       f.owner="net/neoforged/neoforge/client/extensions/IBlockEntityRendererExtension";edit=true;changed++;
      }
     }
     if(edit){ClassWriter w=new ClassWriter(0);n.accept(w);data=w.toByteArray();}
    }
    ZipEntry out=new ZipEntry(e.getName());out.setTime(0);dst.putNextEntry(out);dst.write(data);dst.closeEntry();
   }
  }
  if(changed!=1)throw new IllegalStateException("Expected exactly one native relocated bounding-box field, found "+changed);
  System.out.println("Relocated actual native bounding-box constant: "+changed);
 }
}
