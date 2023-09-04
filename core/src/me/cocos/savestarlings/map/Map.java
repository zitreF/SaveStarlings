package me.cocos.savestarlings.map;

import com.badlogic.gdx.math.Vector3;
import me.cocos.savestarlings.entity.building.base.StarBase;
import me.cocos.savestarlings.service.EntityService;

public class Map {

    private final EntityService entityService;

    public Map(EntityService entityService) {
        this.entityService = entityService;
    }

    public void generate() {
        StarBase starBase = new StarBase(new Vector3(0f, 1f, 0f));
        this.entityService.addBuilding(starBase);
    }
}
