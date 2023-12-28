package me.cocos.savestarlings.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import me.cocos.savestarlings.builder.FontBuilder;
import me.cocos.savestarlings.hud.impl.BuilderHud;
import me.cocos.savestarlings.hud.impl.ResourcesHud;
import me.cocos.savestarlings.service.BuildingService;

import java.awt.Graphics2D;

public class Hud extends Stage {

    private final Label fpsLabel;
    private BuildingService buildingService;

    public Hud() {
        super(new StretchViewport(1600, 900));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = FontBuilder.from("ui/font/glfont.ttf")
                .color(Color.WHITE)
                .size(10)
                .borderWidth(1f)
                .borderColor(Color.BLACK)
                .filter(Texture.TextureFilter.Linear)
                .build();
        this.fpsLabel = new Label("FPS: " + Gdx.graphics.getFramesPerSecond(), labelStyle);
        fpsLabel.setAlignment(Align.left, Align.top);
        Table root = new Table();
        root.setFillParent(true);
        root.setTouchable(Touchable.childrenOnly);

        BuilderHud builderHud = new BuilderHud(this);
        ResourcesHud resourcesHud = new ResourcesHud();
        root.add(resourcesHud).size(400f, 50f).top().expandX().row();
        root.add(builderHud).size(800f, 230f).expandY().bottom().row();
        this.addActor(root);
    }

    public void update(float delta) {
        fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
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
