package com.custardgames.worldtrotter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.custardgames.worldtrotter.handlers.InputHandler;
import com.custardgames.worldtrotter.states.Play;

import java.util.ArrayList;
import java.util.List;

public class Core extends Game {
    public static final String TITLE = "Worldtrotter";
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int SCALE = 1;

    private List<Screen> screens;


    @Override
    public void create() {
        Gdx.input.setInputProcessor(new InputHandler());
        screens = new ArrayList<Screen>();
        screens.add(new Play());
        setScreen(screens.get(0));
    }

}
