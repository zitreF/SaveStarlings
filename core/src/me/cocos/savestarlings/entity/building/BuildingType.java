package me.cocos.savestarlings.entity.building;

import me.cocos.savestarlings.callback.Result;
import me.cocos.savestarlings.entity.building.tower.impl.CannonBlast;
import me.cocos.savestarlings.entity.building.tower.impl.SniperTower;
import net.mgsx.gltf.scene3d.scene.Scene;

public enum BuildingType {

    SNIPER(SniperTower::new, new Scene(SniperTower.getSceneAsset().scene)),
    CANNON_BLAST(CannonBlast::new, new Scene(CannonBlast.getSceneAsset().scene));

    private final Result<Building> buildingResult;
    private final Scene scene;

    BuildingType(Result<Building> buildingResult, Scene scene) {
        this.buildingResult = buildingResult;
        this.scene = scene;
    }

    public Result<Building> getBuildingResult() {
        return this.buildingResult;
    }

    public Scene getScene() {
        return this.scene;
    }
}
