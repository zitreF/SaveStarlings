package me.cocos.savestarlings.hud.progressbar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.util.zip.Deflater;

public class GLProgressBar extends Stack {

    public GLProgressBar(String color, float max, float value, int width, int height) {
        Image backgroundImage = new Image(new Texture("ui/progressbar/progressbar.png"));

        Pixmap oldTexture = new Pixmap(Gdx.files.internal("ui/progressbar/color/"+color+".png"));
        Pixmap newTexture = new Pixmap(width, height, oldTexture.getFormat());
        newTexture.drawPixmap(oldTexture,
                0, 0, oldTexture.getWidth(), oldTexture.getHeight(),
                0, 0, newTexture.getWidth(), newTexture.getHeight()
        );
        Texture texture = new Texture(newTexture);
        oldTexture.dispose();
        newTexture.dispose();

        TextureRegion progressTexture = new TextureRegion(texture, 0, 0, (int) ((value / max) * width), height);
        Image progressImage = new Image(progressTexture);
        Container<Image> background = new Container<>(backgroundImage);
        background.width(width).height(height);
        Container<Image> progress = new Container<>(progressImage);
        progress.width((value / max) * width).height(height).padRight(width - ((value / max) * width));
        this.addActor(background);
        this.addActor(progress);
    }
}
