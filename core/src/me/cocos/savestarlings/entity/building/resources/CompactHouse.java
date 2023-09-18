package me.cocos.savestarlings.entity.building.resources;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.entity.building.Building;
import me.cocos.savestarlings.service.AssetService;
import me.cocos.savestarlings.util.IntersectorUtil;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class CompactHouse implements Building {
    private final Scene scene;
    private final BoundingBox boundingBox;
    private final Vector3 position;

    private static final SceneAsset sceneAsset;

    static {
        sceneAsset = AssetService.getAsset("buildings/resources/compacthouse.glb");
    }

    public CompactHouse(Vector3 position) {
        this.position = position;
        this.scene = new Scene(sceneAsset.scene);
        this.boundingBox = new BoundingBox();
        scene.modelInstance.calculateBoundingBox(boundingBox);

        float scaleX = 7.5f / boundingBox.getWidth();
        float scaleY = 7.5f / boundingBox.getHeight();
        float scaleZ = 7.5f / boundingBox.getDepth();

        this.scene.modelInstance.transform.scale(scaleX, scaleY, scaleZ);

        this.boundingBox.mul(scene.modelInstance.transform);
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
    public Rectangle getBounding() {
        return null;
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
