package me.cocos.savestarlings.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import me.cocos.savestarlings.entity.Clickable;
import me.cocos.savestarlings.entity.building.Building;
import me.cocos.savestarlings.entity.environment.Environment;
import me.cocos.savestarlings.entity.livingentitiy.LivingEntity;
import me.cocos.savestarlings.util.AsyncUtil;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityService {

    private final List<LivingEntity> entities;
    private final List<Building> buildings;
    private final List<Environment> environments;
    private final EnvironmentService environmentService;

    public EntityService(EnvironmentService environmentService) {
        this.entities = new CopyOnWriteArrayList<>();
        this.buildings = new CopyOnWriteArrayList<>();
        this.environments = new CopyOnWriteArrayList<>();
        this.environmentService = environmentService;
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
        environmentService.addScene(building.getScene());
    }

    public void removeBuilding(Building building) {
        this.buildings.remove(building);
        environmentService.removeScene(building.getScene());
    }

    public void addEntity(LivingEntity entity) {
        this.entities.add(entity);
        environmentService.addScene(entity.getScene());
    }

    public void addEntityWithoutShadows(LivingEntity entity) {
        this.entities.add(entity);
        environmentService.addSceneWithoutShadows(entity.getScene());
    }

    public void removeEntity(LivingEntity entity) {
        this.entities.remove(entity);
        environmentService.removeScene(entity.getScene());
    }

    public void removeEntityWithoutShadows(LivingEntity entity) {
        this.entities.remove(entity);
        environmentService.removeSceneWithoutShadows(entity.getScene());
    }

    public void addEnvironment(Environment environment) {
        this.environments.add(environment);
        environmentService.addScene(environment.getScene());
    }

    public void removeEnvironment(Environment environment) {
        this.environments.remove(environment);
        environmentService.removeScene(environment.getScene());
    }

    public List<Building> getBuildings() {
        return this.buildings;
    }

    public List<LivingEntity> getEntities() {
        return this.entities;
    }

    public List<Environment> getEnvironments() {
        return this.environments;
    }

    private boolean found = false;

    public void update(float delta) {
        AsyncUtil.runAsync(() -> {
            for (Building building : this.buildings) {
                building.update(delta);
                if (!this.found
                        && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)
                        && building.isClicked()
                        && !GameService.getInstance().getBuildingService().isMouseOverHudElement()) {
                    building.onClick();
                    this.found = true;
                }
            }
            for (LivingEntity livingEntity : this.entities) {
                livingEntity.update(delta);
                if (!this.found
                        && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)
                        && livingEntity instanceof Clickable clickable
                        && !GameService.getInstance().getBuildingService().isMouseOverHudElement()) {
                    if (clickable.isClicked()) {
                        clickable.onClick();
                        this.found = true;
                    }
                }
            }
            for (Environment environment : this.environments) {
                if (!this.found
                        && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)
                        && !GameService.getInstance().getBuildingService().isMouseOverHudElement()) {
                    if (environment.isClicked()) {
                        environment.onClick();
                        this.found = true;
                    }
                }
            }
            this.found = false;
        });
    }
}
