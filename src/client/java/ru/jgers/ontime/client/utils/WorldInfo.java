package ru.jgers.ontime.client.utils;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;

public class WorldInfo {

    /**
     * @return Text depending on the weather.
     **/

    public static Text getWeather(ClientWorld world) {
        if(world.isThundering()) {
            return Text.translatable("tip.ontemp.thunderstorm");
        }

        if(world.isRaining()) {
            return Text.translatable("tip.ontemp.rain");
        }

        else {
            return Text.translatable("tip.ontemp.clear");
        }
    }

    /**
     * @return the number of days that have passed since the world was created.
     **/

    public static int getTotalDays(ClientWorld world) {
        return (int) (world.getTimeOfDay() / 24000);
    }

    /**
     * @return ticks for this day
     **/

    public static int getThisDayTime(ClientWorld world) {
        return (int) (world.getTimeOfDay() % 24000);
    }
}
