package me.cocos.savestarlings.entity.building.base;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.entity.building.Building;
import me.cocos.savestarlings.asset.AssetService;
import me.cocos.savestarlings.util.IntersectorUtil;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class StarBase implements Building {

    private final Scene scene;
    private final BoundingBox boundingBox;
    private final Rectangle rectangle;
    private final Vector3 position;
    private float health;

    private static final SceneAsset sceneAsset;

    static {
        sceneAsset = AssetService.getAsset("buildings/starbase/sb-7.glb");
    }

    public StarBase(Vector3 position) {
        this.position = position;
        this.scene = new Scene(sceneAsset.scene);
        this.boundingBox = new BoundingBox();
        scene.modelInstance.calculateBoundingBox(boundingBox);

        float scaleX = 20f / boundingBox.getWidth();
        float scaleY = 20f / boundingBox.getHeight();
        float scaleZ = 20f / boundingBox.getDepth();

        this.scene.modelInstance.transform.scale(scaleX, scaleY, scaleZ);

        float x = MathUtils.round(position.x / 2.5f) * 2.5f;
        float z = MathUtils.round(position.z / 2.5f) * 2.5f;
        this.position.set(x, position.y, z);

        scene.modelInstance.transform.setTranslation(this.position.x + 1f, this.position.y, this.position.z);

        this.rectangle = new Rectangle(x - 7.5f, z - 7.5f, 15f, 15f);
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
        return this.boundingBox;
    }

    @Override
    public Vector3 getPosition() {
        return scene.modelInstance.transform.getTranslation(this.position);
    }

    @Override
    public void onClick() {

    }

    @Override
    public boolean isClicked() {
        return IntersectorUtil.isPressed(this.position, 1.25f);
    }
}
