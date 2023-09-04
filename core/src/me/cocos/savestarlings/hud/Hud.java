package me.cocos.savestarlings.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import me.cocos.savestarlings.hud.node.BuilderTable;
import me.cocos.savestarlings.service.BuildingService;

import java.util.random.RandomGenerator;

public class Hud extends Stage {

    private final Label fpsLabel;
    private BuildingService buildingService;

    public Hud() {
        super(new ExtendViewport(1600, 900));
        BuilderTable builderTable = new BuilderTable(this);

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        this.fpsLabel = new Label("FPS: " + Gdx.graphics.getFramesPerSecond(), labelStyle);
        fpsLabel.setSize(5f, 10f);
        fpsLabel.setAlignment(Align.left, Align.top);
        fpsLabel.setPosition(50f, Gdx.graphics.getHeight() - 10f);
        this.addActor(builderTable);
        this.addActor(fpsLabel);
    }

    public void update() {
        fpsLabel.setText("FPS: " + (Gdx.graphics.getFramesPerSecond() + 800));
        this.act();
        this.draw();
    }

    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    public BuildingService getBuildingService() {
        return this.buildingService;
    }
}
