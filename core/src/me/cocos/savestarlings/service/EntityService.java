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

public class EntityService {

    private final Array<LivingEntity> entities;
    private final Array<Building> buildings;
    private final EnvironmentService environmentService;

    public EntityService(EnvironmentService environmentService) {
        this.entities = new Array<>();
        this.buildings = new Array<>();
        this.environmentService = environmentService;
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
        environmentService.addScene(building.getScene());
    }

    public void removeBuilding(Building building) {
        this.buildings.removeValue(building, false);
        environmentService.removeScene(building.getScene());
    }

    public void addEntity(LivingEntity entity) {
        this.entities.add(entity);
        environmentService.addScene(entity.getScene());
    }

    public void removeEntity(LivingEntity entity) {
        this.entities.removeValue(entity, false);
        environmentService.removeScene(entity.getScene());
    }

    public Array<Building> getBuildings() {
        return this.buildings;
    }

    public Array<LivingEntity> getEntities() {
        return this.entities;
    }

    private boolean found = false;

    public void update(float delta) {
        for (Building building : this.buildings) {
            building.update(delta);
            if (!this.found && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && building.isClicked()) {
                building.onClick();
                this.found = true;
            }
        }
        for (LivingEntity livingEntity : this.entities) {
            livingEntity.update(delta);
            if (!this.found && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && livingEntity instanceof Clickable clickable) {
                if (clickable.isClicked()) {
                    clickable.onClick();
                    this.found = true;
                }
            }
        }
        this.found = false;
    }
}
