package ru.jgers.ontemp.client.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import ru.jgers.ontemp.client.OnTempClient;
import ru.jgers.ontemp.client.render.ScreenTimeRenderer;

import java.awt.*;

public class OnTempOptions {

    /**
     * Method for creating GUI configs using YACL. It contains only three groups:
     * hint (various additional information),
     * texture (controlling the display of textures, indicators, etc.),
     * other (anything that doesn't fit into the previous categories).
     * <p>
     * Uses {@link ScreenTimeRenderer#HANDLER} to serialize and then deserialize information.
     * Serialization occurs when changes are applied.
     * Deserialization occurs when the client is initialized {@link OnTempClient#onInitializeClient()}
     *
     * @return OnTime config Screen
     */

    public static Screen createGui(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.literal("OnTemp"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("ontemp.config.category"))
                        .tooltip(Text.translatable("ontemp.config.category.tip"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("ontemp.config.group.hint"))
                                .description(OptionDescription.of(Text.translatable("ontemp.config.group.hint.tip")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("ontemp.config.option.weather"))
                                        .description(OptionDescription.of(Text.translatable("ontemp.config.tip.weather")))
                                        .binding(false, () -> ScreenTimeRenderer.SHOW_WEATHER, newVal -> ScreenTimeRenderer.SHOW_WEATHER = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("ontemp.config.option.show_moon_phase"))
                                        .description(OptionDescription.of(Text.translatable("ontemp.config.tip.show_moon_phase")))
                                        .binding(false, () -> ScreenTimeRenderer.SHOW_MOON_PHASE, newVal -> ScreenTimeRenderer.SHOW_MOON_PHASE = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("ontemp.config.option.total_ticks"))
                                        .description(OptionDescription.of(Text.translatable("ontemp.config.tip.total_ticks")))
                                        .binding(false, () -> ScreenTimeRenderer.SHOW_TOTAL_TICKS, newVal -> ScreenTimeRenderer.SHOW_TOTAL_TICKS = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("ontemp.config.option.day_ticks"))
                                        .description(OptionDescription.of(Text.translatable("ontemp.config.tip.day_ticks")))
                                        .binding(false, () -> ScreenTimeRenderer.SHOW_DAY_TICKS, newVal -> ScreenTimeRenderer.SHOW_DAY_TICKS = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("ontemp.config.option.day_count"))
                                        .description(OptionDescription.of(Text.translatable("ontemp.config.tip.day_count")))
                                        .binding(false, () -> ScreenTimeRenderer.SHOW_DAY_COUNT, newVal -> ScreenTimeRenderer.SHOW_DAY_COUNT = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("ontemp.config.group.texture"))
                                .description(OptionDescription.of(Text.translatable("ontemp.config.group.texture.tip")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("ontemp.config.option.indicator_on_right"))
                                        .description(OptionDescription.of(Text.translatable("ontemp.config.tip.indicator_on_right")))
                                        .binding(false, () -> ScreenTimeRenderer.INDICATOR_ON_RIGHT, newVal -> ScreenTimeRenderer.INDICATOR_ON_RIGHT = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.translatable("ontemp.config.option.texture_multiplier"))
                                        .description(OptionDescription.of(Text.translatable("ontemp.config.tip.texture_multiplier")))
                                        .binding(1.0, () -> ScreenTimeRenderer.TEXTURE_MULTIPLIER, newVal -> ScreenTimeRenderer.TEXTURE_MULTIPLIER = newVal)
                                        .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                                                .range(0.25d, 2d)
                                                .step(0.25d)
                                                .valueFormatter(val -> Text.literal(val + " X")))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("ontemp.config.option.text_color"))
                                        .description(OptionDescription.of(Text.translatable("ontemp.config.tip.text_color")))
                                        .binding(Color.WHITE, () -> ScreenTimeRenderer.TEXT_COLOR, newVal -> ScreenTimeRenderer.TEXT_COLOR = newVal)
                                        .controller(opt -> ColorControllerBuilder.create(opt).allowAlpha(false))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("ontemp.config.option.text_shadow"))
                                        .description(OptionDescription.of(Text.translatable("ontemp.config.tip.text_shadow")))
                                        .binding(true, () -> ScreenTimeRenderer.TEXT_SHADOW, newVal -> ScreenTimeRenderer.TEXT_SHADOW = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("ontemp.config.option.show_indicator"))
                                        .description(OptionDescription.of(Text.translatable("ontemp.config.tip.show_indicator")))
                                        .binding(true, () -> ScreenTimeRenderer.SHOW_INDICATOR, newVal -> ScreenTimeRenderer.SHOW_INDICATOR = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("ontemp.config.group.other"))
                                .description(OptionDescription.of(Text.translatable("ontemp.config.group.other.tip")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("ontemp.config.option.require_clock"))
                                        .description(OptionDescription.of(Text.translatable("ontemp.config.tip.require_clock")))
                                        .binding(false, () -> ScreenTimeRenderer.REQUIRE_CLOCK, newVal -> ScreenTimeRenderer.REQUIRE_CLOCK = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build())
                        .build())
                .save(() -> ScreenTimeRenderer.HANDLER.save())
                .build()
                .generateScreen(parent);
    }
}
