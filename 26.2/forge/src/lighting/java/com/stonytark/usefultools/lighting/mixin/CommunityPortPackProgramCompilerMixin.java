package com.stonytark.usefultools.lighting.mixin;

import com.stonytark.usefultools.lighting.CommunityPortShaderInterfaces;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Optional final-pass interpolation repair for the unmodified 26.2 community port. */
@Mixin(targets = "dev.oculusforge.render.program.PackProgramCompiler", remap = false)
public abstract class CommunityPortPackProgramCompilerMixin {
    private static final Logger USEFUL_TOOLS_LOGGER = LoggerFactory.getLogger("usefultools_lighting");

    @Inject(
        method = "prepare(Ldev/oculusforge/shaderpack/ShaderPackDescriptor;)Ldev/oculusforge/render/program/PreparedPackPipeline;",
        at = @At("RETURN"), cancellable = true, remap = false
    )
    private void usefulToolsLighting$matchFinalInterpolation(CallbackInfoReturnable<Object> cir) {
        Object pipeline = cir.getReturnValue();
        if (pipeline == null) return;
        try {
            Class<?> pipelineType = pipeline.getClass();
            List<?> programs = (List<?>) pipelineType.getMethod("programs").invoke(pipeline);
            List<Object> corrected = new ArrayList<>(programs.size());
            int changed = 0;
            for (Object program : programs) {
                Class<?> programType = program.getClass();
                String vertex = (String) programType.getMethod("vertexSource").invoke(program);
                Object fragment = programType.getMethod("fragment").invoke(program);
                String fragmentSource = (String) fragment.getClass().getMethod("source").invoke(fragment);
                String matched = CommunityPortShaderInterfaces.matchInterpolation(vertex, fragmentSource);
                if (matched.equals(vertex)) {
                    corrected.add(program);
                    continue;
                }
                Constructor<?> constructor = recordConstructor(programType);
                corrected.add(constructor.newInstance(
                    programType.getMethod("pack").invoke(program),
                    programType.getMethod("stageName").invoke(program), matched, fragment
                ));
                changed++;
            }
            if (changed == 0) return;
            Constructor<?> constructor = recordConstructor(pipelineType);
            cir.setReturnValue(constructor.newInstance(pipelineType.getMethod("pack").invoke(pipeline), corrected));
            USEFUL_TOOLS_LOGGER.info("Matched community-port final shader interpolation in {} program(s)", changed);
        } catch (ReflectiveOperationException | ClassCastException error) {
            USEFUL_TOOLS_LOGGER.error("Could not match community-port final shader interpolation", error);
        }
    }

    private static Constructor<?> recordConstructor(Class<?> type) throws ReflectiveOperationException {
        RecordComponent[] components = type.getRecordComponents();
        if (components == null) throw new NoSuchMethodException(type.getName() + " is not a record");
        Class<?>[] parameterTypes = new Class<?>[components.length];
        for (int i = 0; i < components.length; i++) parameterTypes[i] = components[i].getType();
        return type.getConstructor(parameterTypes);
    }
}
