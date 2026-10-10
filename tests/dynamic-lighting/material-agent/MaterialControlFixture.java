package com.stonytark.lightingqa;
import com.stonytark.usefultools.lighting.LightField;
import java.nio.file.*;
import java.util.*;
/** Execute the actual premain transformation; verify both branches, sky bits and source retention. */
public final class MaterialControlFixture {
    public static void main(String[] arguments) throws Exception {
        Path flag=Paths.get(arguments[0]);
        LightField field=new LightField();
        Map<String,LightField.Source> sources=new HashMap<String,LightField.Source>();
        sources.put("hardened",new LightField.Source(0,0,0,15,30));field.update(sources);
        if(field.packed(0,0,0,0)!=240||field.sodium(0,0,0,0)!=15)throw new AssertionError("normal sample");
        Files.write(flag,new byte[]{1});
        long deadline=System.nanoTime()+10_000_000_000L;
        while(!Files.isRegularFile(Paths.get(flag+".ack"))){
            if(Files.isRegularFile(Paths.get(flag+".fail")))throw new AssertionError(new String(Files.readAllBytes(Paths.get(flag+".fail")),"UTF-8"));
            if(System.nanoTime()>deadline)throw new AssertionError("agent control timed out");Thread.sleep(10);
        }
        Random random=new Random(232);
        for(int i=0;i<1000;i++){
            int original=random.nextInt();
            if(field.packed(original,0,0,0)!=original||field.sodium(original,0,0,0)!=original)throw new AssertionError("metadata changed");
        }
        if(field.light(0,0,0)!=15||field.light(20,0,0)<=0)throw new AssertionError("source policy removed");
        System.setProperty("usefulToolsLightingMaterialOnly.active","false");
        if(field.packed(0,0,0,0)!=240||field.sodium(0,0,0,0)!=15)throw new AssertionError("normal branch not restored");
        System.out.println("PASS actual agent bytecode, 1000 packed/Sodium metadata samples, unchanged field and restored normal branch");
    }
}
