package me.cocos.savestarlings.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Hud extends Stage {

    private final Table table;
    private final Label fpsLabel;

    public Hud() {
        super(new ExtendViewport(1600, 900));
        this.table = new Table();
        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        this.fpsLabel = new Label("FPS: " + Gdx.graphics.getFramesPerSecond(), labelStyle);
        fpsLabel.setSize(5f, 10f);
        fpsLabel.setAlignment(Align.left, Align.top);
        fpsLabel.setPosition(50f, Gdx.graphics.getHeight() - 10f);
        this.addActor(fpsLabel);
    }

    public void update() {
        fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
        this.act(Math.min(Gdx.graphics.getDeltaTime(), 1f / 30f));
        this.draw();
    }
}
