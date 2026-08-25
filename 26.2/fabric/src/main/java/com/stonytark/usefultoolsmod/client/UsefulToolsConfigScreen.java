package com.stonytark.usefultoolsmod.client;

import com.stonytark.usefultoolsmod.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Vanilla-only config screen for the mod.
 *
 * <p>This dependency-free editor uses vanilla MC widgets only
 * — a {@link Screen} hosting a {@link ContainerObjectSelectionList} of toggle
 * / number-field rows, with grey {@link StringWidget} headers between
 * categories. Booleans get a {@link Checkbox}; integer / long / double values
 * get an {@link EditBox} with parse-time validation. The actual write to the
 * underlying config option happens when the user clicks "Done"; "Cancel"
 * discards the staged edits.
 *
 * <p>Registered as a Forge config screen via
 * {@code ConfigScreenHandler.ConfigScreenFactory} in
 * {@link ClientConfigRegistration}.
 */
public class UsefulToolsConfigScreen extends Screen {

    private static final int LIST_TOP_INSET = 32;
    private static final int LIST_BOTTOM_INSET = 36;
    private static final int ITEM_HEIGHT = 24;
    private static final int LABEL_WIDTH_BUDGET = 250;
    private static final int CONTROL_AREA_WIDTH = 100;

    private final Screen parent;

    /** Staged edits — applied to the spec only on Done, discarded on Cancel. */
    private final List<Runnable> pendingWrites = new ArrayList<>();

    private OptionList list;

    public UsefulToolsConfigScreen(Screen parent) {
        super(Component.literal("The Useful Tools Mod"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.list = new OptionList(this.minecraft,
                this.width,
                this.height - LIST_TOP_INSET - LIST_BOTTOM_INSET,
                LIST_TOP_INSET,
                ITEM_HEIGHT);
        populate(this.list);
        this.addRenderableWidget(this.list);

        int btnW = 100;
        int btnY = this.height - 27;
        int gap = 8;
        int totalW = btnW * 2 + gap;
        int startX = (this.width - totalW) / 2;

        this.addRenderableWidget(Button.builder(Component.literal("Done"), b -> {
            for (Runnable w : pendingWrites) w.run();
            Config.save();
            this.onClose();
        }).bounds(startX, btnY, btnW, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Cancel"),
                b -> this.onClose())
                .bounds(startX + btnW + gap, btnY, btnW, 20).build());
    }

    private void populate(OptionList list) {
        if (Config.options().isEmpty()) {
            list.addHeader(Component.literal("No config options."));
            return;
        }
        String category = null;
        for (Config.Option option : Config.options()) {
            ConfigDescriptors.Descriptor descriptor = ConfigDescriptors.get(option.field().getName());
            if (!descriptor.category().equals(category)) {
                category = descriptor.category();
                list.addHeader(Component.literal("§e" + category));
            }
            list.addRow(makeRow(option));
        }
    }

    private RowEntry makeRow(Config.Option option) {
        ConfigDescriptors.Descriptor descriptor = ConfigDescriptors.get(option.field().getName());
        String reload = descriptor.reloadRequired() ? " (reload required)" : "";
        Component name = Component.literal(descriptor.label() + reload);
        Component tooltip = Component.literal(descriptor.tooltip());
        Object value = option.get();
        if (value instanceof Boolean b) {
            return new BoolRow(this, name, tooltip, b, newVal -> stageWrite(option, newVal));
        } else if (value instanceof Number) {
            return new NumberRow(this, name, tooltip, String.valueOf(value), text -> stageNumberWrite(option, value, text));
        } else {
            return new NumberRow(this, name, tooltip, String.valueOf(value), text -> {});
        }
    }

    private void stageWrite(Config.Option option, boolean value) {
        pendingWrites.add(() -> option.set(value));
    }

    private void stageNumberWrite(Config.Option option, Object original, String text) {
        try {
            Object parsed;
            if (original instanceof Integer) parsed = Integer.parseInt(text.trim());
            else if (original instanceof Long) parsed = Long.parseLong(text.trim());
            else if (original instanceof Double) parsed = Double.parseDouble(text.trim());
            else return;
            pendingWrites.add(() -> option.set(parsed));
        } catch (NumberFormatException ignored) {
            // Invalid input — leave the staged write off the queue so the spec keeps the old value.
        }
    }

    @Override
    public void onClose() {
        this.minecraft.setScreenAndShow(parent);
    }

    // ─── ContainerObjectSelectionList wiring ──────────────────────────────

    public static final class OptionList extends ContainerObjectSelectionList<RowEntry> {
        OptionList(Minecraft mc, int width, int height, int y, int itemHeight) {
            super(mc, width, height, y, itemHeight);
        }

        @Override
        public int getRowWidth() {
            return Math.min(this.width - 40, 380);
        }

        public void addRow(RowEntry entry) {
            this.addEntry(entry);
        }

        public void addHeader(Component text) {
            this.addEntry(new HeaderRow(text, Minecraft.getInstance()));
        }
    }

    public abstract static class RowEntry extends ContainerObjectSelectionList.Entry<RowEntry> {}

    private static final class HeaderRow extends RowEntry {
        private final StringWidget widget;

        HeaderRow(Component text, Minecraft mc) {
            this.widget = new StringWidget(text, mc.font);
        }

        @Override
        public List<? extends GuiEventListener> children() { return List.of(widget); }

        @Override
        public List<? extends NarratableEntry> narratables() { return List.of(widget); }

        @Override
        public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float partialTick) {
            widget.setPosition(this.getContentX(), this.getContentY() + 4);
            widget.extractRenderState(graphics, mouseX, mouseY, partialTick);
        }
    }

    private static final class BoolRow extends RowEntry {
        private final Screen screen;
        private final StringWidget label;
        private final Checkbox checkbox;

        BoolRow(Screen screen, Component label, Component tooltip, boolean initial, Consumer<Boolean> onChange) {
            this.screen = screen;
            Minecraft mc = Minecraft.getInstance();
            this.label = new StringWidget(label, mc.font);
            this.label.setMaxWidth(LABEL_WIDTH_BUDGET);
            this.checkbox = Checkbox.builder(Component.empty(), mc.font)
                    .selected(initial)
                    .onValueChange((cb, sel) -> onChange.accept(sel))
                    .build();
            this.checkbox.setTooltip(Tooltip.create(tooltip));
        }

        @Override
        public List<? extends GuiEventListener> children() { return List.of(checkbox); }

        @Override
        public List<? extends NarratableEntry> narratables() { return List.of(checkbox); }

        @Override
        public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float partialTick) {
            int contentX = this.getContentX();
            int contentY = this.getContentY();
            int contentRight = this.getContentRight();

            label.setPosition(contentX, contentY + 5);
            label.extractRenderState(graphics, mouseX, mouseY, partialTick);

            int boxSize = Checkbox.getBoxSize(Minecraft.getInstance().font);
            checkbox.setPosition(contentRight - boxSize, contentY + 2);
            checkbox.extractRenderState(graphics, mouseX, mouseY, partialTick);
        }
    }

    private static final class NumberRow extends RowEntry {
        private final Screen screen;
        private final StringWidget label;
        private final EditBox editBox;

        NumberRow(Screen screen, Component label, Component tooltip, String initial, Consumer<String> onChange) {
            this.screen = screen;
            Minecraft mc = Minecraft.getInstance();
            this.label = new StringWidget(label, mc.font);
            this.label.setMaxWidth(LABEL_WIDTH_BUDGET);
            this.editBox = new EditBox(mc.font, 0, 0, 80, 18, Component.empty());
            this.editBox.setValue(initial);
            this.editBox.setResponder(onChange);
            this.editBox.setTooltip(Tooltip.create(tooltip));
        }

        @Override
        public List<? extends GuiEventListener> children() { return List.of(editBox); }

        @Override
        public List<? extends NarratableEntry> narratables() { return List.of(editBox); }

        @Override
        public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float partialTick) {
            int contentX = this.getContentX();
            int contentY = this.getContentY();
            int contentRight = this.getContentRight();

            label.setPosition(contentX, contentY + 5);
            label.extractRenderState(graphics, mouseX, mouseY, partialTick);

            editBox.setPosition(contentRight - 80, contentY + 2);
            editBox.extractRenderState(graphics, mouseX, mouseY, partialTick);
        }
    }

    // ─── prettify (label formatter, same logic as the Cloth-Config version) ──────────

    private static String prettify(String raw) {
        StringBuilder out = new StringBuilder(raw.length() + 4);
        boolean capNext = true;
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (c == '_' || c == '-') {
                out.append(' ');
                capNext = true;
            } else if (Character.isUpperCase(c) && i > 0 && Character.isLowerCase(raw.charAt(i - 1))) {
                out.append(' ').append(c);
                capNext = false;
            } else if (capNext) {
                out.append(Character.toUpperCase(c));
                capNext = false;
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }
}
