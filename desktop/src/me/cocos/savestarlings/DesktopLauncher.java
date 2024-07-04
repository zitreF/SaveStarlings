package me.cocos.savestarlings;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.NumberUtils;

import javax.print.attribute.standard.NumberUp;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.ComponentOrientation;
import java.awt.TrayIcon;

public class DesktopLauncher {

	public static void main(String[] arg) {
//		JFrame frame = new JFrame("FPS Limit");
//		frame.setIconImage(null);
//		frame.setUndecorated(true);
//		frame.setVisible(true);
//		frame.setLocationRelativeTo(null);
//		String fps = JOptionPane.showInputDialog(
//				frame,
//				"Select max FPS (0 = unlimited)",
//				"FPS Limit",
//                JOptionPane.PLAIN_MESSAGE);
//		frame.dispose();
//		if (fps == null || fps.isBlank() || !fps.chars().allMatch(Character::isDigit)) {
//			System.exit(0);
//			return;
//		}
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
