package com.horse.pokemon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.horse.pokemon.Engine;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title     = "Pokemon Game";
		config.resizable = true;
		config.width     = Engine.getvWidth() * 2;
		config.height    = Engine.getvHeight() * 2;
		config.useGL30   = false;
		new LwjglApplication(new Engine(), config);
	}
}
