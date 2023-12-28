package me.cocos.savestarlings;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {

	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(0);
		config.setIdleFPS(144);
		config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 4);
		config.useVsync(false);
		config.setWindowedMode(1600, 900);
		config.setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.GL32, 4, 6);
		config.setTitle("Save Starlings");
		config.setWindowIcon("icon.png");
		new Lwjgl3Application(new SaveStarlings(), config);
	}
}
