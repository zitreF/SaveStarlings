package me.cocos.savestarlings.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import me.cocos.savestarlings.entity.Clickable;
import me.cocos.savestarlings.entity.building.Building;
import me.cocos.savestarlings.entity.livingentitiy.LivingEntity;
import me.cocos.savestarlings.entity.livingentitiy.starling.Citizen;
import me.cocos.savestarlings.util.IntersectorUtil;
import me.cocos.savestarlings.util.SoundUtil;

import java.util.HashSet;
import java.util.Set;

public class EntityService {

    private final Set<LivingEntity> entities;
    private final Set<Building> buildings;
    private final EnvironmentService environmentService;

    public EntityService(EnvironmentService environmentService) {
        this.entities = new HashSet<>();
        this.buildings = new HashSet<>();
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

    public Set<Building> getBuildings() {
        return this.buildings;
    }

    public Set<LivingEntity> getEntities() {
        return this.entities;
    }

    private boolean found = false;

    public void update(float delta) {
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
        this.found = false;
    }
}
