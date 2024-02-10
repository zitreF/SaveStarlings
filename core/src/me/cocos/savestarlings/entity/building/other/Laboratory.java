package me.cocos.savestarlings.entity.building.other;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.entity.building.Building;
import me.cocos.savestarlings.asset.AssetService;
import me.cocos.savestarlings.util.IntersectorUtil;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class Laboratory implements Building {

    private final Scene scene;
    private final Vector3 position;
    private final Rectangle rectangle;
    private float health;
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
        scene.modelInstance.transform.scale(1.5f, 1.5f, 1.5f);
        float x = MathUtils.floor(position.x / 2.5f) * 2.5f + 1.25f;
        float z = MathUtils.floor(position.z / 2.5f) * 2.5f + 1.25f;
        position.set(x, position.y-0.5f, z);

        scene.modelInstance.transform.setTranslation(position.x, position.y, position.z);

        this.rectangle = new Rectangle(x - 3.75f, z - 3.75f, 7.5f, 7.5f);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(float amount) {
        this.health = amount;
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
        return null;
    }

    @Override
    public Vector3 getPosition() {
        return scene.modelInstance.transform.getTranslation(this.position);
    }

    @Override
    public boolean isHovered() {
        return IntersectorUtil.isPressed(this.position, 1.25f);
    }

    public static Scene getSceneAsset() {
        return staticScene;
    }
}
