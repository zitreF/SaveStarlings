package me.cocos.savestarlings.entity.building.tower.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.entity.building.tower.Tower;
import me.cocos.savestarlings.service.AssetService;
import me.cocos.savestarlings.service.GameService;
import me.cocos.savestarlings.util.IntersectorUtil;
import me.cocos.savestarlings.util.SoundUtil;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

import java.util.HashMap;
import java.util.Map;

public class SniperTower implements Tower {

    private final Scene scene;
    private final BoundingBox boundingBox;
    private final Rectangle rectangle;
    private final Vector3 position;

    private static final SceneAsset sceneAsset;

    static {
        sceneAsset = AssetService.getAsset("buildings/turrets/sniper_tower.glb");
    }

    public SniperTower(Vector3 position) {
        this.position = position;
        this.scene = new Scene(sceneAsset.scene);
        this.boundingBox = new BoundingBox();
        scene.modelInstance.calculateBoundingBox(boundingBox);

        float scaleX = 4f / boundingBox.getWidth();
        float scaleY = 3.25f / boundingBox.getHeight();
        float scaleZ = 6f / boundingBox.getDepth();

        this.scene.modelInstance.transform.scale(scaleX, scaleY, scaleZ);

        this.boundingBox.mul(scene.modelInstance.transform);

        float x = MathUtils.round(this.position.x / 2.5f) * 2.5f;
        float z = MathUtils.round(this.position.z / 2.5f) * 2.5f;

        this.position.set(x, this.position.y, z);

        scene.modelInstance.transform.setTranslation(this.position.x, this.position.y, this.position.z);

        Material material = new Material();
        material.set(new BlendingAttribute(GL20.GL_DST_COLOR, GL20.GL_ZERO));

        scene.modelInstance.materials.clear();
        scene.modelInstance.materials.add(material);

        this.rectangle = new Rectangle(x, z, 5f, 5f);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public Rectangle getBounding() {
        return this.rectangle;
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return this.boundingBox;
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

    @Override
    public void onClick() {

    }

    @Override
    public boolean isClicked() {
        return IntersectorUtil.isPressed(this.position, 1.25f);
    }
}
