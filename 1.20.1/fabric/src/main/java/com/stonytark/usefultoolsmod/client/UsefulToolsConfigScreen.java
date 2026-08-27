package com.stonytark.usefultoolsmod.client;

import com.stonytark.usefultoolsmod.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Vanilla-widget editor for all persistent Useful Tools options. */
public final class UsefulToolsConfigScreen extends Screen {
    private static final int ROWS_PER_PAGE = 7;
    private final Screen parent;
    private final Map<Config.Option, Object> originals = new LinkedHashMap<>();
    private int page;
    private boolean saved;

    public UsefulToolsConfigScreen(Screen parent) {
        super(Text.literal("The Useful Tools Mod"));
        this.parent = parent;
        Config.options().forEach(option -> originals.put(option, option.get()));
    }

    @Override
    protected void init() {
        clearChildren();
        List<Config.Option> options = Config.options();
        int pages = Math.max(1, (options.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        page = Math.max(0, Math.min(page, pages - 1));
        int start = page * ROWS_PER_PAGE;
        int end = Math.min(start + ROWS_PER_PAGE, options.size());
        for (int index = start; index < end; index++) {
            Config.Option option = options.get(index);
            ConfigDescriptors.Descriptor descriptor = ConfigDescriptors.get(option.field().getName());
            int y = 42 + (index - start) * 22;
            if (option.field().getType() == boolean.class) {
                ButtonWidget button = ButtonWidget.builder(toggleLabel(option), pressed -> {
                    option.set(!(Boolean) option.get());
                    pressed.setMessage(toggleLabel(option));
                }).tooltip(Tooltip.of(Text.literal(descriptor.tooltip())))
                        .dimensions(width / 2 - 150, y, 300, 20).build();
                addDrawableChild(button);
            } else if (option.field().getType() == double.class) {
                TextFieldWidget box = new TextFieldWidget(textRenderer, width / 2 + 40, y, 110, 20,
                        Text.literal(option.label()));
                box.setText(String.valueOf(option.get()));
                box.setTooltip(Tooltip.of(Text.literal(descriptor.tooltip())));
                box.setChangedListener(value -> updateNumber(option, value, box));
                addDrawableChild(box);
            }
        }
        addDrawableChild(ButtonWidget.builder(Text.literal("<"), b -> changePage(-1))
                .dimensions(width / 2 - 150, height - 28, 30, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), b -> saveAndClose())
                .dimensions(width / 2 - 82, height - 28, 80, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.translatable("gui.cancel"), b -> close())
                .dimensions(width / 2 + 2, height - 28, 80, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal(">"), b -> changePage(1))
                .dimensions(width / 2 + 120, height - 28, 30, 20).build());
    }

    private Text toggleLabel(Config.Option option) {
        ConfigDescriptors.Descriptor descriptor = ConfigDescriptors.get(option.field().getName());
        String reload = descriptor.reloadRequired() ? " (reload required)" : "";
        return Text.literal(descriptor.label() + reload + ": " + ((Boolean) option.get() ? "ON" : "OFF"));
    }

    private void updateNumber(Config.Option option, String value, TextFieldWidget box) {
        try {
            option.set(Double.parseDouble(value));
            box.setEditableColor(0xE0E0E0);
        } catch (NumberFormatException ignored) {
            box.setEditableColor(0xFF5555);
        }
    }

    private void changePage(int delta) {
        page += delta;
        init(MinecraftClient.getInstance(), width, height);
    }

    private void saveAndClose() {
        saved = true;
        Config.save();
        close();
    }

    @Override
    public void close() {
        if (!saved) originals.forEach(Config.Option::set);
        MinecraftClient.getInstance().setScreen(parent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 12, 0xFFFFFF);
        int pages = Math.max(1, (Config.options().size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        String category = Config.options().isEmpty() ? "" : ConfigDescriptors.get(
                Config.options().get(Math.min(page * ROWS_PER_PAGE, Config.options().size() - 1)).field().getName()
        ).category() + "  -  ";
        context.drawCenteredTextWithShadow(textRenderer, Text.literal(category + "Page " + (page + 1) + " / " + pages),
                width / 2, 27, 0xAAAAAA);
        int start = page * ROWS_PER_PAGE;
        int end = Math.min(start + ROWS_PER_PAGE, Config.options().size());
        for (int index = start; index < end; index++) {
            Config.Option option = Config.options().get(index);
            if (option.field().getType() == double.class) {
                ConfigDescriptors.Descriptor descriptor = ConfigDescriptors.get(option.field().getName());
                context.drawTextWithShadow(textRenderer, descriptor.label(), width / 2 - 150,
                        48 + (index - start) * 22, 0xFFFFFF);
            }
        }
        super.render(context, mouseX, mouseY, delta);
    }
}
