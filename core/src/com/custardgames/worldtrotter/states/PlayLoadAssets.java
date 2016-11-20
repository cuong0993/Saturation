package com.custardgames.worldtrotter.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class PlayLoadAssets {

    public AssetManager loadAssets(AssetManager assets) {
        assets.load("images/title.png", Texture.class);
        assets.load("images/title2.png", Texture.class);
        assets.load("images/title3.png", Texture.class);
        assets.load("images/title4.png", Texture.class);
        assets.load("images/player.png", Texture.class);
        assets.load("images/portal.png", Texture.class);
        assets.load("images/backgroundr.png", Texture.class);
        assets.load("images/backgroundy.png", Texture.class);
        assets.load("images/backgroundb.png", Texture.class);
        assets.load("images/colourwheelr.png", Texture.class);
        assets.load("images/colourwheely.png", Texture.class);
        assets.load("images/colourwheelb.png", Texture.class);
        assets.load("images/dialogue/dialogue1.png", Texture.class);
        assets.load("images/dialogue/dialogue2.png", Texture.class);
        assets.load("images/dialogue/dialogue3.png", Texture.class);
        assets.load("images/dialogue/dialogue4.png", Texture.class);
        assets.load("images/dialogue/dialogue5.png", Texture.class);
        assets.load("images/dialogue/dialogue6.png", Texture.class);
        assets.load("images/dialogue/dialogue7.png", Texture.class);
        assets.load("images/dialogue/dialogue8.png", Texture.class);
        assets.load("images/dialogue/dialogue9.png", Texture.class);
        assets.load("images/dialogue/dialogue10.png", Texture.class);
        assets.load("images/dialogue/dialogue11.png", Texture.class);
        assets.load("music/music.mp3", Music.class);
        assets.finishLoading();
        return assets;
    }
}
