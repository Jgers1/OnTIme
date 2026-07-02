package ru.jgers.ontemp.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import ru.jgers.ontemp.OnTemp;
import ru.jgers.ontemp.client.utils.WorldInfo;

import java.awt.*;

public class ScreenTimeRenderer {

        /**Handler for serializing and deserializing information about included configs {@link ru.jgers.ontemp.client.config.OnTempOptions}
         *
         */

        public static ConfigClassHandler<ScreenTimeRenderer> HANDLER = ConfigClassHandler.createBuilder(ScreenTimeRenderer.class)
                .id(Identifier.of(OnTemp.MOD_ID, "ontemp_config"))
                .serializer(config -> GsonConfigSerializerBuilder.create(config)
                        .setPath(FabricLoader.getInstance().getConfigDir().resolve("ontemp.json5"))
                        .setJson5(true)
                        .build())
                .build();

        /**
         * Additional information configs
         **/

        @SerialEntry
        public static boolean SHOW_WEATHER = false;

        @SerialEntry
        public static boolean SHOW_TOTAL_TICKS = false;

        @SerialEntry
        public static boolean SHOW_DAY_TICKS = false;

        @SerialEntry
        public static boolean SHOW_DAY_COUNT = false;

        @SerialEntry
        public static boolean SHOW_MOON_PHASE = false;

        /**
         * Texture configs
         */

        @SerialEntry
        public static boolean INDICATOR_ON_RIGHT = false;


        @SerialEntry
        public static boolean SHOW_INDICATOR = true;

        @SerialEntry
        public static double TEXTURE_MULTIPLIER = 1;

        @SerialEntry
        public static Color TEXT_COLOR = Color.WHITE;

        @SerialEntry
        public static boolean TEXT_SHADOW = true;

        /**
         * Other configs
         */

        @SerialEntry
        public static boolean REQUIRE_CLOCK = false;

        public static void renderRegister() {
                HudRenderCallback.EVENT.register((context, tickDeltaManager) -> {

                        if (!isPlayerContainsClock() && REQUIRE_CLOCK) {
                                return;
                        }

                        int bar_height = (int) (16 * TEXTURE_MULTIPLIER);
                        int bar_width = (int) (88 * TEXTURE_MULTIPLIER);
                        Identifier bar_texture = Identifier.of(OnTemp.MOD_ID, "textures/gui/classic_time_bar_v2.png");
                        int xRight = INDICATOR_ON_RIGHT ? context.getScaledWindowWidth() : 0;

                        int indicatorSize = (int) (8 * TEXTURE_MULTIPLIER);
                        int u = 12;
                        int v = 12;
                        int textureSunSize = 32;
                        int textureMoonWidth = 128;
                        int textureMoonHeight = 64;
                        int yText = SHOW_INDICATOR ? (int) (18 * TEXTURE_MULTIPLIER) : 2;

                        Identifier moonTexture = Identifier.ofVanilla("textures/environment/moon_phases.png");
                        Identifier sunTexture = Identifier.ofVanilla("textures/environment/sun.png");

                        int moonPhase = 1;
                        long ticks = 0;
                        ClientWorld world = MinecraftClient.getInstance().world;
                        int fontHeight = MinecraftClient.getInstance().textRenderer.fontHeight;

                        if (world != null) {
                                ticks = WorldInfo.getThisDayTime(world);
                                moonPhase = world.getMoonPhase();

                                /*Draw additional information under time bar in order:
                                (Weather, moon phase, total ticks, day ticks, day count
                                 */

                                if (SHOW_WEATHER) {
                                        drawTip(context, MinecraftClient.getInstance().textRenderer,
                                                WorldInfo.getWeather(world), yText);
                                        yText = yText + fontHeight;
                                }

                                if (SHOW_MOON_PHASE) {
                                        String moon = switch (moonPhase) {
                                                case 1 -> "waning_gibbous";
                                                case 2 -> "third_quarter";
                                                case 3 -> "waning_crescent";
                                                case 4 -> "new_moon";
                                                case 5 -> "waxing_crescent";
                                                case 6 -> "first_quarter";
                                                case 7 -> "waxing_gibbous";
                                                default -> "full_moon";
                                        };
                                        drawTip(context, MinecraftClient.getInstance().textRenderer,
                                                Text.translatable("tip.ontemp.show_moon_phase." + moon),
                                                yText);
                                        yText = yText + fontHeight;
                                }

                                if (SHOW_TOTAL_TICKS) {
                                    drawTip(context, MinecraftClient.getInstance().textRenderer,
                                                Text.translatable("tip.ontemp.total_ticks", world.getTimeOfDay()),
                                                yText);
                                        yText = yText + fontHeight;
                                }

                                if (SHOW_DAY_TICKS) {
                                        drawTip(context, MinecraftClient.getInstance().textRenderer,
                                                Text.translatable("tip.ontemp.day_ticks", WorldInfo.getThisDayTime(world)),
                                                yText);
                                        yText = yText + fontHeight;
                                }

                                if (SHOW_DAY_COUNT) {
                                        drawTip(context, MinecraftClient.getInstance().textRenderer,
                                                Text.translatable("tip.ontemp.day_count", WorldInfo.getTotalDays(world)),
                                                yText);
                                }
                        }

                        if (!SHOW_INDICATOR) {
                                return;
                        }

                        double xIndicator = 0.006 * TEXTURE_MULTIPLIER;
                        int y = (int) (4 * TEXTURE_MULTIPLIER);
                        int xSun = (int) (4 * TEXTURE_MULTIPLIER + xIndicator * ticks);
                        int xMoon = (int) (4 * TEXTURE_MULTIPLIER + xIndicator * (ticks - 12000));

                        //Enable blend for correct bar texture drawing

                        RenderSystem.enableBlend();

                        //Draw time bar

                        context.drawTexture(bar_texture, Math.max(0, xRight - bar_width), 0, 0, 0, 0, bar_width, bar_height,
                                bar_width, bar_height);

                        //Draw sun if day otherwise draw moon instead

                        if (ticks <= 12000) {
                                drawIndicator(context, sunTexture, xSun, y, u, v, indicatorSize, textureSunSize,
                                        textureSunSize);
                        }
                        else {
                                drawIndicator(context, moonTexture, xMoon, y,
                                        u + (2 * u + 8) * (moonPhase % 4),
                                        Math.max(v, (3 * v + 8) * (int) (moonPhase / 4)), indicatorSize, textureMoonWidth, textureMoonHeight);
                        }
                        RenderSystem.disableBlend();
                });
        }

        /**
         * Method for rendering indicators (sun and moon icons).
         */


        public static void drawIndicator(DrawContext context, Identifier texture, int x, int y, int u, int v, int size,
                                         int textureWidth, int textureHeight) {
                int bar_width = (int) (88 * TEXTURE_MULTIPLIER);
                int xRight = INDICATOR_ON_RIGHT ? context.getScaledWindowWidth() : 0;

                context.drawTexture(texture, Math.max(xRight - bar_width + x, x), y, size, size, u, v, 8,
                        8, textureWidth, textureHeight);
        }

        /**
         * Method for rendering additional information.
         */

        public static void drawTip(DrawContext context, TextRenderer textRenderer, Text text, int y) {
                int xRight = INDICATOR_ON_RIGHT ? context.getScaledWindowWidth() : 0;

                context.drawText(textRenderer, text,
                        Math.max(4, xRight - textRenderer.getWidth(text.asOrderedText()) - 4), y,
                        TEXT_COLOR.getRGB(), TEXT_SHADOW);
        }

        /**
         *
         * @return Boolean is the player have a clock {@link net.minecraft.item.ItemStack} in their inventory. If player is null return False.
         *
         */

        public static boolean isPlayerContainsClock() {
                ClientPlayerEntity player = MinecraftClient.getInstance().player;

                if (player == null) {
                        return false;
                }

                return player.getInventory().contains(Items.CLOCK.getDefaultStack());
        }
}