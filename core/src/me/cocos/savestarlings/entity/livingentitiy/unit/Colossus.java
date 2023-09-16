package me.cocos.savestarlings.entity.livingentitiy.unit;

import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.entity.livingentitiy.LivingEntity;
import me.cocos.savestarlings.service.AssetService;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class Colossus implements LivingEntity {

    private final Scene scene;
    private final BoundingBox boundingBox;
    private final Vector3 position;
    private static final SceneAsset sceneAsset;
    private final Vector3 targetPosition;
    private final Vector3 rotationDirection;

    static {
        sceneAsset = AssetService.getAsset("entities/units/colossus.glb");
    }

    public Colossus(Vector3 position) {
        this.position = position;
        this.scene = new Scene(sceneAsset.scene);
        this.boundingBox = new BoundingBox();
        this.rotationDirection = new Vector3();
        scene.modelInstance.calculateBoundingBox(boundingBox);

        float scaleX = 5f / boundingBox.getWidth();
        float scaleY = 5f / boundingBox.getHeight();
        float scaleZ = 5f / boundingBox.getDepth();

        this.scene.modelInstance.transform.scale(scaleX, scaleY, scaleZ);

        this.boundingBox.mul(scene.modelInstance.transform);

        scene.modelInstance.transform.setTranslation(position.x, position.y, position.z);

        this.targetPosition = new Vector3(position);
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
        this.position.add(0.1f, 0f, 0f);
        this.scene.modelInstance.transform.setTranslation(position);
    }
}
