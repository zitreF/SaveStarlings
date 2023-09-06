package me.cocos.savestarlings.entity.building;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.cocos.savestarlings.callback.Result;
import me.cocos.savestarlings.entity.building.tower.impl.CannonBlast;
import me.cocos.savestarlings.entity.building.tower.impl.SniperTower;
import net.mgsx.gltf.scene3d.scene.Scene;

import java.util.function.Consumer;

public enum BuildingType {

    SNIPER(SniperTower::new, new Scene(SniperTower.getSceneAsset().scene), 2.5f),
    CANNON_BLAST(CannonBlast::new, new Scene(CannonBlast.getSceneAsset().scene), 2.5f);

    private final Result<Building> buildingResult;
    private final Scene scene;
    private final float positionOffset;

    BuildingType(Result<Building> buildingResult, Scene scene, float positionOffset) {
        this.buildingResult = buildingResult;
        this.scene = scene;
        this.positionOffset = positionOffset;
    }

    public Result<Building> getBuildingResult() {
        return this.buildingResult;
    }

    public Scene getScene() {
        return this.scene;
    }

    public float getPositionOffset() {
        return this.positionOffset;
    }
}
