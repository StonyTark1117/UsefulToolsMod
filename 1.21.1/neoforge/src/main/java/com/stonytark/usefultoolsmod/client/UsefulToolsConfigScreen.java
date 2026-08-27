package com.stonytark.usefultoolsmod.client;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.stonytark.usefultoolsmod.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Vanilla-widget editor for every value in the Forge config spec. */
@OnlyIn(Dist.CLIENT)
@SuppressWarnings("deprecation")
public final class UsefulToolsConfigScreen extends Screen {
    private static final int ROWS_PER_PAGE = 9;
    private final Screen parent;
    private final List<Option> options = new ArrayList<>();
    private final Map<ModConfigSpec.ConfigValue<?>, Object> originals = new LinkedHashMap<>();
    private int page;
    private boolean saved;

    private record Option(ConfigDescriptors.Descriptor descriptor, ModConfigSpec.ConfigValue<?> value) {
        String label() { return descriptor.label() + (descriptor.reloadRequired() ? " (reload required)" : ""); }
    }

    private UsefulToolsConfigScreen(Screen parent) {
        super(Component.literal("The Useful Tools Mod"));
        this.parent = parent;
        flatten("", Config.SPEC.getValues());
        for (Option option : options) originals.put(option.value(), option.value().get());
    }

    public static Screen build(Minecraft minecraft, Screen parent) {
        return new UsefulToolsConfigScreen(parent);
    }

    private void flatten(String section, UnmodifiableConfig config) {
        for (Map.Entry<String, Object> entry : config.valueMap().entrySet()) {
            String key = section.isEmpty() ? entry.getKey()
                    : section + Character.toUpperCase(entry.getKey().charAt(0)) + entry.getKey().substring(1);
            if (entry.getValue() instanceof UnmodifiableConfig nested) flatten(key, nested);
            else if (entry.getValue() instanceof ModConfigSpec.ConfigValue<?> value) {
                if (isHiddenMigrationKey(key)) continue;
                options.add(new Option(ConfigDescriptors.get(canonicalKey(key)), value));
            }
        }
    }

    private static boolean isHiddenMigrationKey(String key) {
        return key.equals("spectralUtilitiesSoulLanternEnabled");
    }

    private static String canonicalKey(String key) {
        if (ConfigDescriptors.values().containsKey(key)) return key;
        String normalized = key
                .replaceFirst("^spectralUtilitiesWraithEnabled", "wraithEnabled")
                .replaceFirst("^spectralUtilitiesWraithSpawnChance", "wraithSpawnChance")
                .replaceFirst("^spectralUtilitiesEctoplasmLanternEnabled", "ectoplasmLanternEnabled")
                .replaceFirst("^spectralUtilitiesSoulLanternEnabled", "ectoplasmLanternEnabled")
                .replaceFirst("^spectralUtilitiesSpectralResonatorEnabled", "spectralResonatorEnabled")
                .replaceFirst("^controlledExplosivesMiningChargeEnabled", "miningChargeEnabled")
                .replaceFirst("^controlledExplosivesStickyDynamiteEnabled", "stickyDynamiteEnabled")
                .replaceFirst("^controlledExplosivesRemoteDetonationEnabled", "remoteDetonationEnabled")
                .replaceFirst("^controlledExplosivesPreserveDrops", "controlledDrops")
                .replaceFirst("^controlledExplosivesEntityDamageCap", "controlledEntityDamage")
                .replaceFirst("^controlledExplosivesRemoteRange", "remoteRange")
                .replaceFirst("^spectralInfuserInfused", "infused")
                .replaceFirst("^pointedDripstone", "dripstone")
                .replaceFirst("^foodSetsHunger", "foodHunger")
                .replaceFirst("^sweetBerries", "sweetBerry")
                .replaceFirst("^overpower", "op")
                .replaceFirst("^ectoplasmSet", "ectoplasm");
        if (ConfigDescriptors.values().containsKey(normalized)) return normalized;
        if (normalized.endsWith("Enabled")) {
            String withoutEnabled = normalized.substring(0, normalized.length() - 7);
            if (ConfigDescriptors.values().containsKey(withoutEnabled)) return withoutEnabled;
        }
        return normalized;
    }

    @Override
    protected void init() {
        clearWidgets();
        int pages = Math.max(1, (options.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        page = Math.max(0, Math.min(page, pages - 1));
        int start = page * ROWS_PER_PAGE;
        int end = Math.min(start + ROWS_PER_PAGE, options.size());
        for (int index = start; index < end; index++) {
            Option option = options.get(index);
            int y = 42 + (index - start) * 22;
            Object value = option.value().get();
            Tooltip tooltip = Tooltip.create(Component.literal(option.descriptor().tooltip()));
            if (value instanceof Boolean enabled) {
                addRenderableWidget(Button.builder(toggleLabel(option, enabled), button -> {
                    boolean next = !((Boolean) option.value().get());
                    setValue(option.value(), next);
                    button.setMessage(toggleLabel(option, next));
                }).tooltip(tooltip).bounds(width / 2 - 150, y, 300, 20).build());
            } else if (value instanceof Number) {
                EditBox box = new EditBox(font, width / 2 + 40, y, 110, 20,
                        Component.literal(option.label()));
                box.setValue(String.valueOf(value));
                box.setTooltip(tooltip);
                box.setResponder(text -> setNumber(option.value(), value, text, box));
                addRenderableWidget(box);
            }
        }
        addRenderableWidget(Button.builder(Component.literal("<"), button -> changePage(-1))
                .bounds(width / 2 - 150, height - 28, 30, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("gui.done"), button -> saveAndClose())
                .bounds(width / 2 - 82, height - 28, 80, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("gui.cancel"), button -> onClose())
                .bounds(width / 2 + 2, height - 28, 80, 20).build());
        addRenderableWidget(Button.builder(Component.literal(">"), button -> changePage(1))
                .bounds(width / 2 + 120, height - 28, 30, 20).build());
    }

    private Component toggleLabel(Option option, boolean enabled) {
        return Component.literal(option.label() + ": " + (enabled ? "ON" : "OFF"));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void setValue(ModConfigSpec.ConfigValue value, Object newValue) {
        value.set(newValue);
    }

    private void setNumber(ModConfigSpec.ConfigValue<?> value, Object original, String text, EditBox box) {
        try {
            Object parsed = original instanceof Integer ? Integer.parseInt(text.trim())
                    : original instanceof Long ? Long.parseLong(text.trim()) : Double.parseDouble(text.trim());
            setValue(value, parsed);
            box.setTextColor(0xE0E0E0);
        } catch (NumberFormatException ignored) {
            box.setTextColor(0xFF5555);
        }
    }

    private void changePage(int delta) {
        page += delta;
        rebuildWidgets();
    }

    private void saveAndClose() {
        saved = true;
        Config.SPEC.save();
        Minecraft.getInstance().setScreen(parent);
    }

    @Override
    public void onClose() {
        if (!saved) originals.forEach(UsefulToolsConfigScreen::setValue);
        Minecraft.getInstance().setScreen(parent);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(font, title, width / 2, 12, 0xFFFFFF);
        String category = options.isEmpty() ? "" : options.get(
                Math.min(page * ROWS_PER_PAGE, options.size() - 1)).descriptor().category() + "  -  ";
        graphics.drawCenteredString(font, Component.literal(category + "Page " + (page + 1) + " / "
                + Math.max(1, (options.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE)),
                width / 2, 27, 0xAAAAAA);
        int start = page * ROWS_PER_PAGE;
        int end = Math.min(start + ROWS_PER_PAGE, options.size());
        for (int index = start; index < end; index++) {
            Option option = options.get(index);
            if (option.value().get() instanceof Number) {
                graphics.drawString(font, option.label(), width / 2 - 150,
                        48 + (index - start) * 22, 0xFFFFFF, false);
            }
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }
}
