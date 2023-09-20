package me.cocos.savestarlings.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import me.cocos.savestarlings.hud.node.BuilderHud;
import me.cocos.savestarlings.service.BuildingService;

public class Hud extends Stage {

    private final Label fpsLabel;
    private BuildingService buildingService;

    public Hud() {
        super(new ExtendViewport(1600, 900));
        BuilderHud builderHud = new BuilderHud(this);
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/font/glfont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 10;
        parameter.color = new Color(1f, 1f, 1f, 1f);
        parameter.borderColor = new Color(0f, 0f, 0f, 1f);
        parameter.borderWidth = 1f;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.Linear;
        BitmapFont bitmapFont = freeTypeFontGenerator.generateFont(parameter);

        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, new Color(1f, 1f, 1f, 1f));
        this.fpsLabel = new Label("FPS: " + Gdx.graphics.getFramesPerSecond(), labelStyle);
        fpsLabel.setAlignment(Align.left, Align.top);
        fpsLabel.setPosition(50f, Gdx.graphics.getHeight() - fpsLabel.getHeight());
        this.addActor(builderHud);
        this.addActor(fpsLabel);
    }

    public void update(float delta) {
        fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
        fpsLabel.setPosition(fpsLabel.getWidth() / 2f, Gdx.graphics.getHeight() - fpsLabel.getHeight());
        this.act(delta);
        this.draw();
    }

    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    public BuildingService getBuildingService() {
        return this.buildingService;
    }
}
