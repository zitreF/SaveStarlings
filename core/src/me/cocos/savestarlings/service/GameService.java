package me.cocos.savestarlings.service;

import me.cocos.savestarlings.hud.Hud;

public class GameService {

    private static GameService instance;

    private final EnvironmentService environmentService;
    private final EntityService entityService;
    private final Hud hud;

    public GameService() {
        instance = this;
        this.environmentService = new EnvironmentService();
        this.entityService = new EntityService(environmentService);
        environmentService.setEntityService(entityService);
        this.hud = new Hud();
    }

    public EnvironmentService getEnvironmentService() {
        return this.environmentService;
    }

    public EntityService getEntityService() {
        return this.entityService;
    }

    public void update(float delta) {
        this.entityService.update(delta);
        this.environmentService.update(delta);
        this.hud.update();
    }

    public void dispose() {
        this.environmentService.dispose();
    }

    public static GameService getInstance() {
        return instance;
    }
}
