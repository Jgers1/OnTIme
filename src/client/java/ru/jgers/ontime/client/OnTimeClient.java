package ru.jgers.ontime.client;

import net.fabricmc.api.ClientModInitializer;
import ru.jgers.ontime.client.render.ScreenTimeRenderer;

public class OnTimeClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ScreenTimeRenderer.HANDLER.load();
        ScreenTimeRenderer.renderRegister();
	}
}