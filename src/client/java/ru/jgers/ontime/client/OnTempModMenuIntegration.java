package ru.jgers.ontime.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import ru.jgers.ontime.client.config.OnTempOptions;

public class OnTempModMenuIntegration implements ModMenuApi {

    /**
     * Opens the config screen via ModMenu
     */

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return OnTempOptions::createGui;
    }
}
