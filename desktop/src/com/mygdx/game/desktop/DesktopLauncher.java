package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.AsteroidsGame;
import com.mygdx.game.Window;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Wallis Asteroider";
		config.width = AsteroidsGame.GAME_WIDTH;
		config.height = AsteroidsGame.GAME_HEIGHT;
		new LwjglApplication(new Window(), config);
	}
}
