package me.cocos.savestarlings.entity.livingentitiy.starling;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.entity.livingentitiy.LivingEntity;
import me.cocos.savestarlings.service.AssetService;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

import java.util.Random;

public class Citizen implements LivingEntity {

    private static final float SPEED = 2.0f;
    private static final float ARRIVAL_THRESHOLD = 0.1f;
    private final Scene scene;
    private final Vector3 position;
    private static final SceneAsset sceneAsset;
    private final Vector3 targetPosition;

    private final Random random = new Random();

    static {
        sceneAsset = AssetService.getAsset("entities/starlings/starling.glb");
    }

    public Citizen(Vector3 position) {
        this.position = position;
        this.scene = new Scene(sceneAsset.scene);

        scene.modelInstance.transform.scale(2.5f, 2.5f, 2.5f);

        BoundingBox bounds = new BoundingBox();
        scene.modelInstance.calculateBoundingBox(bounds);

        scene.modelInstance.transform.setTranslation(position.x, position.y - 0.5f, position.z);

        Material material = new Material();
        material.set(new BlendingAttribute(GL20.GL_DST_COLOR, GL20.GL_ZERO));

        scene.modelInstance.materials.clear();
        scene.modelInstance.materials.add(material);

        this.targetPosition = new Vector3(position);
    }

    private final Vector3 direction = new Vector3();

    @Override
    public void update(float delta) {
        if (position.epsilonEquals(targetPosition, ARRIVAL_THRESHOLD)) {
            this.generateRandomTargetPosition();
        }

        this.direction.set(targetPosition).sub(position).nor();
        position.add(direction.scl(SPEED * delta));

        scene.modelInstance.transform.setTranslation(position.x, position.y - 0.5f, position.z);
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public Vector3 getPosition() {
        return this.position;
    }

    private void generateRandomTargetPosition() {
        float randomX = random.nextFloat() * 20 - 10; // -10 & 10.
        float randomZ = random.nextFloat() * 20 - 10; // -10 & 10.
        targetPosition.set(position.x + randomX, position.y, position.z + randomZ);
        Vector3 direction = new Vector3(targetPosition).sub(position).nor();

        float rotationAngleDeg = MathUtils.atan2(direction.x, direction.z) * MathUtils.radiansToDegrees;

        scene.modelInstance.transform.setToRotation(Vector3.Y, rotationAngleDeg);

        scene.modelInstance.transform.scale(2.5f, 2.5f, 2.5f);
    }

    public static SceneAsset getSceneAsset() {
        return sceneAsset;
    }
}
