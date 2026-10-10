import java.nio.file.*;
import java.util.*;
import java.util.zip.*;
import java.io.*;
import kynarain.cn.optifineoforge.optifine.OptifineJarFixer;
public final class PrepareNativeRuntime {
 public static void main(String[] args) throws Exception {
  int repaired=0;
  try(ZipFile source=new ZipFile(args[0]);ZipOutputStream target=new ZipOutputStream(Files.newOutputStream(Path.of(args[1])))) {
   for(ZipEntry entry:Collections.list(source.entries())) {
    if(entry.isDirectory())continue;
    byte[] data;try(InputStream input=source.getInputStream(entry)){data=input.readAllBytes();}
    if(OptifineJarFixer.handles(entry.getName())){data=OptifineJarFixer.fix(entry.getName(),data,true);repaired++;}
    ZipEntry out=new ZipEntry(entry.getName());out.setTime(0);target.putNextEntry(out);target.write(data);target.closeEntry();
   }
  }
  if(repaired!=2)throw new IllegalStateException("Exact publisher service/metadata repair pair absent: "+repaired);
  System.out.println("Actual publisher union-path and SecureJarHandler supplier adapters applied: "+repaired);
 }
}
