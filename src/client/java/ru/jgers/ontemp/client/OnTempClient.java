package ru.jgers.ontemp.client;

import net.fabricmc.api.ClientModInitializer;
import ru.jgers.ontemp.client.render.ScreenTimeRenderer;

public class OnTempClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ScreenTimeRenderer.HANDLER.load();
        ScreenTimeRenderer.renderRegister();
	}
}