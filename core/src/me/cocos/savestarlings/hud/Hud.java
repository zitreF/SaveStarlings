package me.cocos.savestarlings.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import me.cocos.savestarlings.builder.FontBuilder;
import me.cocos.savestarlings.hud.impl.BuilderHud;
import me.cocos.savestarlings.hud.impl.BattleHud;
import me.cocos.savestarlings.hud.impl.DebugHud;
import me.cocos.savestarlings.hud.impl.ResourcesHud;
import me.cocos.savestarlings.service.BuildingService;
import me.cocos.savestarlings.util.BinaryUtil;

public class Hud extends Stage {

    private static Hud instance;
    private DebugHud debugHud;
    private BuildingService buildingService;

    public Hud() {
        super(new StretchViewport(1600, 900));
        instance = this;
        BuilderHud builderHud = new BuilderHud(this);
        BattleHud battleHud = new BattleHud();
        ResourcesHud resourcesHud = new ResourcesHud();
        this.debugHud = new DebugHud();
        this.wrapTableAndAddToStage(debugHud).top().left().expand();
        this.wrapTableAndAddToStage(resourcesHud).size(400f, 50f).top().expand();
        this.wrapTableAndAddToStage(battleHud).size(300f, 600f).top().right().expand();
        this.wrapTableAndAddToStage(builderHud).size(800f, 230f).bottom().expand();

        this.setDebugAll(true);
    }

    private Cell<Table> wrapTableAndAddToStage(Table table) {
        Table root = new Table();
        root.setFillParent(true);
        root.setTouchable(Touchable.childrenOnly);

        this.addActor(root);

        return root.add(table);
    }


    public void update(float delta) {
        this.debugHud.update();
        this.act(delta);
    }

    public void render() {
        this.draw();
    }

    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    public BuildingService getBuildingService() {
        return this.buildingService;
    }

    public static Hud getInstance() {
        return instance;
    }
}
