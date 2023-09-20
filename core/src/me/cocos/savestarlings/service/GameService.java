package me.cocos.savestarlings.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import me.cocos.savestarlings.hud.Hud;
import me.cocos.savestarlings.map.Map;
import me.cocos.savestarlings.util.SoundUtil;

public class GameService {

    private static GameService instance;

    private final EnvironmentService environmentService;
    private final EntityService entityService;
    private final BuildingService buildingService;
    private final Hud hud;

    public GameService() {
        instance = this;
        this.hud = new Hud();
        this.environmentService = new EnvironmentService();
        this.entityService = new EntityService(environmentService);
        environmentService.setEntityService(entityService);
        this.buildingService = new BuildingService(entityService, environmentService, hud);
        Map map = new Map(entityService);
        map.generate();
        map.populate();
        SoundUtil.playMusic("music_main.mp3");
    }

    public void setInputProcessors(InputProcessor cameraController) {
        InputMultiplexer inputMultiplexer = new InputMultiplexer(cameraController, hud);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public Hud getHud() {
        return this.hud;
    }

    public EnvironmentService getEnvironmentService() {
        return this.environmentService;
    }

    public EntityService getEntityService() {
        return this.entityService;
    }

    public BuildingService getBuildingService() {
        return this.buildingService;
    }

    public void update(float delta) {
        this.entityService.update(delta);
        this.environmentService.update(delta);
        this.buildingService.update();
        this.hud.update(delta);
    }

    public void dispose() {
        this.environmentService.dispose();
    }

    public static GameService getInstance() {
        return instance;
    }
}
