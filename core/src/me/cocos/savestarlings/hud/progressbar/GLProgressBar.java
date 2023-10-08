package me.cocos.savestarlings.hud.progressbar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class GLProgressBar extends Stack {

    public GLProgressBar(float max, float value) {
        this.setSize(300f, 50f);
        Image backgroundImage = new Image(new Texture("ui/progressbar/progressbar.png"));
        TextureRegion progressTexture = new TextureRegion(new Texture("ui/progressbar/progress.png"), 0, 0, (int) value, 50);
        Image progressImage = new Image(progressTexture);
        Container<Image> background = new Container<>(backgroundImage);
        background.width(max).height(50f);
        Container<Image> progress = new Container<>(progressImage);
        progress.width(value).height(50f).padRight(max - value);
        this.addActor(background);
        this.addActor(progress);
    }
}
