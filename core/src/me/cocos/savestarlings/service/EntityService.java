package me.cocos.savestarlings.service;

import com.badlogic.gdx.utils.Array;
import me.cocos.savestarlings.entity.Entity;
import me.cocos.savestarlings.entity.building.Building;

public class EntityService {

    private final Array<Entity> entites;
    private final Array<Building> buildings;
    private final EnvironmentService environmentService;

    public EntityService(EnvironmentService environmentService) {
        this.entites = new Array<>();
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

    public Array<Building> getBuildings() {
        return this.buildings;
    }

    public void update(float delta) {
        for (Building building : this.buildings) {
            building.update(delta);
        }
    }
}
