package me.cocos.savestarlings.hud.progressbar.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.cocos.savestarlings.hud.progressbar.GLProgressBar;

public class StatsProgressBar extends Table {

    private final GLProgressBar progressBar;

    public StatsProgressBar(String texturePath, float max, float value, int width, int height) {
        Texture texture = new Texture(texturePath);
        Image image = new Image(texture);
        this.progressBar = new GLProgressBar(max, value, width, height);
        this.add(image).size(height, height);
        this.add(progressBar).size(width, height);
    }
}
