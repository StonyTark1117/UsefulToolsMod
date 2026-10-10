package com.stonytark.usefultools.lighting.qa;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

/** Acceptance-only, read-only snapshot of visible native OptiFine pack rows. */
public final class OptifineLightingQaMenuSnapshot {
    private OptifineLightingQaMenuSnapshot() {}

    public static void append(Object screen, double scale, StringBuilder text) {
        append(screen, scale, scale, text);
    }

    public static void append(Object screen, double scaleX, double scaleY, StringBuilder text) {
        String screenClass = screen.getClass().getName();
        boolean classic = screenClass.equals("shadersmod.client.GuiShaders");
        if (!classic && !screenClass.equals("net.optifine.shaders.gui.GuiShaders")) return;
        try {
            Object list = field(screen, "shaderList");
            if (!classic && !Boolean.TRUE.equals(field(list, "visible"))) return;
            List<?> labels = (List<?>) field(list, "shaderslist");
            int top = classic ? integer(list, "top", "field_148153_b") : integer(list, "y0");
            int bottom = classic ? integer(list, "bottom", "field_148154_c") : integer(list, "y1");
            int height = classic ? integer(list, "slotHeight", "field_148149_f") : integer(list, "itemHeight");
            double x = classic ? integer(list, "left", "field_148152_e")
                    + integer(list, "width", "field_148155_a") / 2.0
                    : integer(list, "x0") + integer(list, "width") / 2.0;
            int header = classic ? integer(list, "headerPadding", "field_148160_j") : integer(list, "headerHeight");
            int scroll = ((Number) (classic ? field(list, "amountScrolled", "field_148169_q") : field(list, "yo"))).intValue();
            int origin = top + 4 + header - scroll;
            Method hit = classic ? list.getClass().getMethod("func_148124_c", int.class, int.class)
                    : list.getClass().getMethod("getItemAtPosition", double.class, double.class);
            hit.setAccessible(true);
            for (int index = 0; index < labels.size(); index++) {
                double y = origin + index * height + height / 2.0;
                if (y <= top || y >= bottom) continue;
                Object indexAtPoint = classic ? hit.invoke(list, (int) Math.round(x), (int) Math.round(y))
                        : hit.invoke(list, x, y);
                if (((Number) indexAtPoint).intValue() != index) continue;
                String label = Base64.getEncoder().encodeToString(
                        String.valueOf(labels.get(index)).getBytes(StandardCharsets.UTF_8));
                text.append(label).append('\t').append(x * scaleX).append('\t')
                        .append(y * scaleY).append('\n');
            }
        } catch (ReflectiveOperationException error) {
            throw new IllegalStateException("Cannot observe native OptiFine shader rows", error);
        }
    }

    private static int integer(Object object, String... names) throws ReflectiveOperationException {
        return ((Number) field(object, names)).intValue();
    }

    private static Object field(Object object, String... names) throws ReflectiveOperationException {
        for (Class<?> type = object.getClass(); type != null; type = type.getSuperclass()) {
            for (String name : names) {
                try {
                    Field field = type.getDeclaredField(name);
                    field.setAccessible(true);
                    return field.get(object);
                } catch (NoSuchFieldException absent) {
                    // The pinned SlotGui defines inherited bounds and scrolling fields.
                }
            }
        }
        throw new NoSuchFieldException(names[0]);
    }
}
