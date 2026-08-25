package com.stonytark.usefultoolsmod.client;

import com.stonytark.usefultoolsmod.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Dependency-free, reflection-backed editor for every public Config option. */
public final class UsefulToolsConfigScreen extends Screen {
    private static final int ROWS_PER_PAGE = 9;

    private final Screen parent;
    private final Map<Config.Option, Object> originalValues = new LinkedHashMap<>();
    private int page;
    private boolean saved;

    public UsefulToolsConfigScreen(Screen parent) {
        super(Component.translatable("config.usefultoolsmod.title"));
        this.parent = parent;
        for (Config.Option option : Config.options()) originalValues.put(option, option.get());
    }

    public static Screen create(Screen parent) {
        return new UsefulToolsConfigScreen(parent);
    }

    @Override
    protected void init() {
        clearWidgets();
        List<Config.Option> options = Config.options();
        int pages = Math.max(1, (options.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        page = Math.max(0, Math.min(page, pages - 1));
        int start = page * ROWS_PER_PAGE;
        int end = Math.min(start + ROWS_PER_PAGE, options.size());
        for (int index = start; index < end; index++) {
            Config.Option option = options.get(index);
            ConfigDescriptors.Descriptor descriptor = ConfigDescriptors.get(option.field().getName());
            int y = 48 + (index - start) * 22;
            if (option.field().getType() == boolean.class) {
                Button button = Button.builder(toggleLabel(option), pressed -> {
                    option.set(!(Boolean) option.get());
                    pressed.setMessage(toggleLabel(option));
                }).tooltip(Tooltip.create(Component.literal(descriptor.tooltip())))
                        .bounds(this.width / 2 - 150, y, 300, 20).build();
                addRenderableWidget(button);
            } else if (option.field().getType() == double.class) {
                EditBox box = new EditBox(this.font, this.width / 2 + 40, y, 110, 20,
                        Component.literal(option.label()));
                box.setValue(String.valueOf(option.get()));
                box.setTooltip(Tooltip.create(Component.literal(descriptor.tooltip())));
                box.setResponder(value -> {
                    try {
                        option.set(Double.parseDouble(value));
                        box.setTextColor(0xE0E0E0);
                    } catch (NumberFormatException ignored) {
                        box.setTextColor(0xFF5555);
                    }
                });
                addRenderableWidget(box);
            }
        }

        addRenderableWidget(Button.builder(Component.literal("<"), b -> changePage(-1))
                .bounds(this.width / 2 - 150, this.height - 28, 30, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("gui.done"), b -> saveAndClose())
                .bounds(this.width / 2 - 82, this.height - 28, 80, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("gui.cancel"), b -> onClose())
                .bounds(this.width / 2 + 2, this.height - 28, 80, 20).build());
        addRenderableWidget(Button.builder(Component.literal(">"), b -> changePage(1))
                .bounds(this.width / 2 + 120, this.height - 28, 30, 20).build());
    }

    private Component toggleLabel(Config.Option option) {
        ConfigDescriptors.Descriptor descriptor = ConfigDescriptors.get(option.field().getName());
        String reload = descriptor.reloadRequired() ? " (reload required)" : "";
        return Component.literal(descriptor.label() + reload + ": " + ((Boolean) option.get() ? "ON" : "OFF"));
    }

    private void changePage(int delta) {
        page += delta;
        rebuildWidgets();
    }

    private void saveAndClose() {
        saved = true;
        Config.save();
        Minecraft.getInstance().setScreen(parent);
    }

    @Override
    public void onClose() {
        if (!saved) originalValues.forEach(Config.Option::set);
        Minecraft.getInstance().setScreen(parent);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(font, title, width / 2, 14, 0xFFFFFF);
        String canonicalCategory = Config.options().isEmpty() ? "" : ConfigDescriptors.get(
                Config.options().get(Math.min(page * ROWS_PER_PAGE, Config.options().size() - 1)).field().getName()
        ).category();
        graphics.drawCenteredString(font,
                Component.literal(canonicalCategory + "  (" + (page + 1) + "/" +
                        Math.max(1, (Config.options().size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE) + ")"),
                width / 2, 30, 0xAAAAAA);

        int start = page * ROWS_PER_PAGE;
        int end = Math.min(start + ROWS_PER_PAGE, Config.options().size());
        for (int index = start; index < end; index++) {
            Config.Option option = Config.options().get(index);
            if (option.field().getType() == double.class) {
                ConfigDescriptors.Descriptor descriptor = ConfigDescriptors.get(option.field().getName());
                graphics.drawString(font, descriptor.label(), width / 2 - 150,
                        54 + (index - start) * 22, 0xFFFFFF, false);
            }
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }
}
