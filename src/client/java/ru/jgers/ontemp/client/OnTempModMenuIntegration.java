package ru.jgers.ontemp.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import ru.jgers.ontemp.client.config.OnTempOptions;

public class OnTempModMenuIntegration implements ModMenuApi {

    /**
     * Opens the config screen via ModMenu
     */

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return OnTempOptions::createGui;
    }
}
