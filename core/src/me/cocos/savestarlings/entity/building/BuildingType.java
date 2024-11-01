package me.cocos.savestarlings.entity.building;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.cocos.savestarlings.callback.Result;
import me.cocos.savestarlings.entity.building.other.Laboratory;
import me.cocos.savestarlings.entity.building.resources.Bank;
import me.cocos.savestarlings.entity.building.tower.impl.CannonBlast;
import me.cocos.savestarlings.entity.building.tower.impl.Mortar;
import me.cocos.savestarlings.entity.building.tower.impl.SniperTower;
import me.cocos.savestarlings.entity.building.tower.impl.Wall;
import net.mgsx.gltf.scene3d.scene.Scene;

import java.util.function.Consumer;
import java.util.function.Function;

public enum BuildingType {

    MORTAR(Mortar::new, new Scene(Mortar.getSceneAsset().scene), (before) -> MathUtils.round(before / 2.5f) * 2.5f, 5f),
    SNIPER_TOWER(SniperTower::new, new Scene(SniperTower.getSceneAsset().scene), (before) -> MathUtils.round(before / 2.5f) * 2.5f, 5f),
    CANNON_BLAST(CannonBlast::new, new Scene(CannonBlast.getSceneAsset().scene), (before) -> MathUtils.round(before / 2.5f) * 2.5f, 5f),
    WALL(Wall::new, new Scene(Wall.getSceneAsset().scene), (before) -> MathUtils.floor(before / 2.5f) * 2.5f + 1.25f, 2.5f),
    BANK(Bank::new, new Scene(Bank.getSceneAsset().scene), (before) -> MathUtils.floor(before / 2.5f) * 2.5f, 7.5f),
    LABORATORY(Laboratory::new, Laboratory.getSceneAsset(), (before) -> MathUtils.floor(before / 2.5f) * 2.5f + 1.25f, 7.5f);

    private final Result<Building> buildingResult;
    private final Scene scene;
    private final Function<Float, Float> positionFunction;
    private final float size;

    BuildingType(Result<Building> buildingResult, Scene scene, Function<Float, Float> positionFunction, float size) {
        this.buildingResult = buildingResult;
        this.scene = scene;
        this.positionFunction = positionFunction;
        this.size = size;
    }

    public Result<Building> getBuildingResult() {
        return this.buildingResult;
    }

    public Scene getScene() {
        return this.scene;
    }

    public Function<Float, Float> getPositionFunction() {
        return this.positionFunction;
    }

    public float getSize() {
        return this.size;
    }
}
