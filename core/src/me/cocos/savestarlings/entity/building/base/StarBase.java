package me.cocos.savestarlings.entity.building.base;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.entity.building.Building;
import me.cocos.savestarlings.service.AssetService;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class StarBase implements Building {

    private final Scene scene;
    private final Vector3 position;
    private final float dimension;

    private static final SceneAsset sceneAsset;

    static {
        sceneAsset = AssetService.getAsset("buildings/starbase/sb-7.glb");
    }

    public StarBase(Vector3 position) {
        this.position = position;
        this.scene = new Scene(sceneAsset.scene);
        this.dimension = 5f;
        scene.modelInstance.transform.scale(0.4f, 0.4f, 0.4f);
        float x = MathUtils.floor(position.x / 2.5f) * 2.5f + 1.75f;
        float z = MathUtils.floor(position.z / 2.5f) * 2.5f + 1.25f;
        position.set(x, position.y, z);

        scene.modelInstance.transform.setTranslation(position.x, position.y, position.z);

        scene.modelInstance.materials.clear();
    }

    @Override
    public void update(float delta) {
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

}
