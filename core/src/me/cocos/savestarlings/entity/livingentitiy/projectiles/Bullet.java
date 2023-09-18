package me.cocos.savestarlings.entity.livingentitiy.projectiles;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.entity.livingentitiy.LivingEntity;
import me.cocos.savestarlings.service.AssetService;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public final class Bullet implements LivingEntity {

    private static final SceneAsset sceneAsset;
    private final Scene scene;
    private final BoundingBox boundingBox;
    private final Vector3 position;

    static {
        sceneAsset = AssetService.getAsset("");
    }

    public Bullet(Vector3 position) {
        this.position = new Vector3(position);
        this.scene = new Scene(sceneAsset.scene);
        this.boundingBox = new BoundingBox();
        scene.modelInstance.calculateBoundingBox(boundingBox);

        float scaleX = 1f / boundingBox.getWidth();
        float scaleY = 1f / boundingBox.getHeight();
        float scaleZ = 1f / boundingBox.getDepth();

        this.scene.modelInstance.transform.scale(scaleX, scaleY, scaleZ);

        scene.modelInstance.transform.setTranslation(this.position.x, this.position.y, this.position.z);

        scene.modelInstance.materials.clear();

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
    public void update(float delta) {

    }
}
