package com.stonytark.usefultools.lighting.mixin;

import com.stonytark.usefultools.lighting.CommunityPortShaderHeaders;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Optional compatibility for the 26.2 Forge community port's final-pass adapter. */
@Mixin(targets = "dev.oculusforge.render.program.LegacyFinalShaderAdapter", remap = false)
public abstract class CommunityPortFinalAdapterMixin {
    private static final Logger USEFUL_TOOLS_LOGGER = LoggerFactory.getLogger("usefultools_lighting");

    @Inject(
        method = "adapt(Ljava/lang/String;Ljava/util/Map;)Ldev/oculusforge/render/program/LegacyFinalShaderAdapter$AdaptedShader;",
        at = @At("RETURN"), cancellable = true, remap = false
    )
    private void usefulToolsLighting$extensions(String input, Map<String, String> vertexOutputs,
                                                CallbackInfoReturnable<Object> cir) {
        Object adapted = cir.getReturnValue();
        if (adapted == null) return;
        try {
            Class<?> type = adapted.getClass();
            Method sourceMethod = type.getMethod("source");
            String original = (String) sourceMethod.invoke(adapted);
            String reordered = CommunityPortShaderHeaders.reorderExtensions(original);
            if (reordered.equals(original)) return;
            Constructor<?> constructor = type.getConstructor(String.class, List.class, List.class);
            Object replacement = constructor.newInstance(reordered, type.getMethod("samplers").invoke(adapted),
                type.getMethod("uniforms").invoke(adapted));
            cir.setReturnValue(replacement);
            USEFUL_TOOLS_LOGGER.info("Reordered community-port fragment shader extension directives");
        } catch (ReflectiveOperationException error) {
            USEFUL_TOOLS_LOGGER.error("Could not reorder community-port shader extensions", error);
        }
    }
}
