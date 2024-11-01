package me.cocos.savestarlings.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import me.cocos.savestarlings.hud.Hud;
import me.cocos.savestarlings.map.Map;
import me.cocos.savestarlings.shader.ShaderProvider;
import me.cocos.savestarlings.util.CameraUtil;
import me.cocos.savestarlings.util.SoundUtil;

public class GameService {

    private static GameService instance;

    private final PerspectiveCamera camera;
    private final ParticleService particleService;
    private final EnvironmentService environmentService;
    private final EntityService entityService;
    private final BuildingService buildingService;
    private final BattleService battleService;
    private final ShaderProvider shaderProvider;
    private final Hud hud;

    public GameService(PerspectiveCamera camera) {
        instance = this;
        this.camera = camera;
        this.hud = new Hud();
        this.particleService = new ParticleService(camera);
        this.environmentService = new EnvironmentService(particleService, camera);
        this.entityService = new EntityService(environmentService);
        environmentService.setEntityService(entityService);
        this.buildingService = new BuildingService(entityService, environmentService, hud);
        this.battleService = new BattleService();
        this.shaderProvider = new ShaderProvider();
        Map map = new Map(entityService);
        map.generate();
        SoundUtil.playMusic("music_main.mp3");
    }

    public void setInputProcessors(InputProcessor cameraController) {
        InputMultiplexer inputMultiplexer = new InputMultiplexer(cameraController, hud);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public PerspectiveCamera getCamera() {
        return this.camera;
    }

    public Hud getHud() {
        return this.hud;
    }

    public ParticleService getParticleService() {
        return this.particleService;
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
        this.hud.update(delta);
        this.buildingService.update();
        this.battleService.update(delta);
    }


    public void render() {
        this.environmentService.render();
        this.shaderProvider.render(Gdx.graphics.getDeltaTime());
        this.hud.update(Gdx.graphics.getDeltaTime());
        this.hud.render();
    }

    public void dispose() {
        this.environmentService.dispose();
    }

    public static GameService getInstance() {
        return instance;
    }

    public void resize(int width, int height) {
        environmentService.getSceneService().updateViewport(width, height);
        shaderProvider.updateViewport(width, height);
        hud.getViewport().update(width, height);
    }
}
