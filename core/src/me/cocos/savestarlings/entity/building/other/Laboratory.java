package me.cocos.savestarlings.entity.building.other;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.entity.building.Building;
import me.cocos.savestarlings.service.AssetService;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class Laboratory implements Building {

    private final Scene scene;
    private final Vector3 position;
    private final float dimension;

    private static final SceneAsset sceneAsset;
    private static final Scene staticScene;

    static {
        sceneAsset = AssetService.getAsset("buildings/other/laboratory.glb");
        staticScene = new Scene(sceneAsset.scene);
        staticScene.modelInstance.transform.scale(1.5f, 1.5f, 1.5f);
    }

    public Laboratory(Vector3 position) {
        this.position = position;
        this.scene = new Scene(sceneAsset.scene);
        this.dimension = 5f;
        scene.modelInstance.transform.scale(1.5f, 1.5f, 1.5f);
        BoundingBox bounds = new BoundingBox();
        scene.modelInstance.calculateBoundingBox(bounds);

        float x = MathUtils.floor(position.x / 2.5f) * 2.5f + 1.25f;
        float z = MathUtils.floor(position.z / 2.5f) * 2.5f + 1.25f;
        position.set(x, position.y-0.75f, z);

        scene.modelInstance.transform.setTranslation(position.x, position.y, position.z);

    }

    @Override
    public void update(float delta) {
        //scene.modelInstance.transform.rotate(Vector3.Y, 10f * delta);
    }

    @Override
    public float getDimension() {
        return this.dimension;
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public Vector3 getPosition() {
        return this.position;
    }

    public static Scene getSceneAsset() {
        return staticScene;
    }
}
