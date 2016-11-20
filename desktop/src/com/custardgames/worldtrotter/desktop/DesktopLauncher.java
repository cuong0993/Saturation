package com.custardgames.worldtrotter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.custardgames.worldtrotter.Core;

public class DesktopLauncher {
    public static void main(String[] arg) {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");

        config.title = Core.TITLE;
        config.width = Core.WIDTH * Core.SCALE;
        config.height = Core.HEIGHT * Core.SCALE;
        config.backgroundFPS = 0;
        config.foregroundFPS = 0;
        config.vSyncEnabled = true;
        config.fullscreen = false;
        new LwjglApplication(new Core(), config);
    }
}
