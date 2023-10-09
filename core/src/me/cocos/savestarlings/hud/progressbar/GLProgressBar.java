package me.cocos.savestarlings.hud.progressbar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class GLProgressBar extends Stack {

    public GLProgressBar(float max, float value, int width, int height) {
        Image backgroundImage = new Image(new Texture("ui/progressbar/progressbar.png"));
        TextureRegion progressTexture = new TextureRegion(new Texture("ui/progressbar/progress.png"), 0, 0, (int) ((value / max) * width), 50);
        Image progressImage = new Image(progressTexture);
        Container<Image> background = new Container<>(backgroundImage);
        background.width(width).height(height);
        Container<Image> progress = new Container<>(progressImage);
        System.out.println(width - ((value / max) * width));
        progress.width((value / max) * width).height(height).padRight(width - ((value / max) * width));
        this.addActor(background);
        this.addActor(progress);
    }
}
