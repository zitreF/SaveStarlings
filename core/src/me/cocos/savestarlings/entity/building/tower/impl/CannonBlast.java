package me.cocos.savestarlings.entity.building.tower.impl;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.entity.building.tower.Tower;
import me.cocos.savestarlings.service.AssetService;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class CannonBlast implements Tower {

    private final Scene scene;
    private final Vector3 position;
    private final float dimension;

    private static final SceneAsset sceneAsset;

    static {
        sceneAsset = AssetService.getAsset("buildings/turrets/cannon_blast.glb");
    }

    public CannonBlast(Vector3 position) {
        this.position = position;
        this.scene = new Scene(sceneAsset.scene);
        this.dimension = 2.5f;

        float x = MathUtils.round(position.x / 2.5f) * 2.5f;
        float z = MathUtils.round(position.z / 2.5f) * 2.5f;

        position.set(x, position.y, z);

        scene.modelInstance.transform.setTranslation(position.x, position.y, position.z);

        Material material = new Material();
        material.set(new BlendingAttribute(GL20.GL_DST_COLOR, GL20.GL_ZERO));

        scene.modelInstance.materials.clear();
        scene.modelInstance.materials.add(material);
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

    @Override
    public int getRange() {
        return 300;
    }

    public static SceneAsset getSceneAsset() {
        return sceneAsset;
    }
}