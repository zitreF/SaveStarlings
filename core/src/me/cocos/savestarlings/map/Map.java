package me.cocos.savestarlings.map;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import me.cocos.savestarlings.entity.building.base.StarBase;
import me.cocos.savestarlings.entity.livingentitiy.starling.Citizen;
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

    public void populate() {
        for (int i = 0; i < 50; i++) {
            Vector3 position = new Vector3();
            position.x = MathUtils.random(-50f, 50f);
            position.y = 0f;
            position.z = MathUtils.random(-50f, 50f);
            Citizen citizen = new Citizen(position);
            this.entityService.addEntity(citizen);
        }
    }
}
