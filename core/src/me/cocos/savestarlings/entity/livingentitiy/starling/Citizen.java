package me.cocos.savestarlings.entity.livingentitiy.starling;

import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.entity.Clickable;
import me.cocos.savestarlings.entity.livingentitiy.LivingEntity;
import me.cocos.savestarlings.service.AssetService;
import me.cocos.savestarlings.util.IntersectorUtil;
import me.cocos.savestarlings.util.SoundUtil;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

import java.util.Random;

public class Citizen implements LivingEntity, Clickable {

    private static final float JUMP_HEIGHT = 1.0f;
    private static final float JUMP_DURATION = 0.5f;
    private static final float SPEED = 2.0f;
    private static final float ARRIVAL_THRESHOLD = 0.1f;
    private final Scene scene;
    private final BoundingBox boundingBox;
    private final Vector3 position;
    private static final SceneAsset sceneAsset;
    private final Vector3 targetPosition;
    private final Vector3 rotationDirection;

    static {
        sceneAsset = AssetService.getAsset("entities/starlings/starling.glb");
    }
    
    public Citizen(Vector3 position) {
        this.position = position;
        this.scene = new Scene(sceneAsset.scene);
        this.boundingBox = new BoundingBox();
        this.rotationDirection = new Vector3();
        scene.modelInstance.calculateBoundingBox(boundingBox);

        float scaleX = 1.5f / boundingBox.getWidth();
        float scaleY = 1.5f / boundingBox.getHeight();
        float scaleZ = 1f / boundingBox.getDepth();

        this.scene.modelInstance.transform.scale(scaleX, scaleY, scaleZ);

        scene.modelInstance.transform.setTranslation(position.x, position.y - 0.5f, position.z);

        scene.modelInstance.materials.clear();

        this.targetPosition = new Vector3(position);
    }

    private final Vector3 direction = new Vector3();

    @Override
    public void update(float delta) {
        if (isJumping) {
            this.jumpTimer += delta;

            if (jumpTimer <= JUMP_DURATION / 2) {
                position.y = initialPosition.y + MathUtils.lerp(0, JUMP_HEIGHT, jumpTimer / (JUMP_DURATION / 2));
            } else if (jumpTimer <= JUMP_DURATION) {
                position.y = initialPosition.y + MathUtils.lerp(JUMP_HEIGHT, 0, (jumpTimer - JUMP_DURATION / 2) / (JUMP_DURATION / 2));
            } else {
                this.isJumping = false;
                position.y = initialPosition.y;
                float rotationAngleDeg = MathUtils.atan2(rotationDirection.x, rotationDirection.z) * MathUtils.radiansToDegrees;
                scene.modelInstance.transform.setToRotation(Vector3.Y, rotationAngleDeg);
                this.scene.modelInstance.transform.scale(1.5f / boundingBox.getWidth(), 1.5f / boundingBox.getHeight(), 1.25f / boundingBox.getDepth());
            }
            this.scene.modelInstance.transform.setTranslation(position.x, position.y - 0.5f, position.z);
            return;
        }
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
    public BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public Vector3 getPosition() {
        return this.position;
    }

    private void generateRandomTargetPosition() {
        float randomX = MathUtils.random() * 20 - 10; // -10 & 10.
        float randomZ = MathUtils.random() * 20 - 10; // -10 & 10.
        targetPosition.set(position.x + randomX, position.y, position.z + randomZ);
        this.rotationDirection.set(targetPosition).sub(position).nor();

        float rotationAngleDeg = MathUtils.atan2(rotationDirection.x, rotationDirection.z) * MathUtils.radiansToDegrees;

        scene.modelInstance.transform.setToRotation(Vector3.Y, rotationAngleDeg);
        this.scene.modelInstance.transform.scale(1.5f / boundingBox.getWidth(), 1.5f / boundingBox.getHeight(), 1f / boundingBox.getDepth());
    }

    public static SceneAsset getSceneAsset() {
        return sceneAsset;
    }

    private boolean isJumping = false;
    private float jumpTimer = 0.0f;
    private final Vector3 initialPosition = new Vector3();

    @Override
    public void onClick() {
        if (!isJumping) {
            this.isJumping = true;
            this.jumpTimer = 0.0f;
            this.initialPosition.set(position);
            this.scene.modelInstance.transform.setToRotation(Vector3.Y, 90f);
            this.scene.modelInstance.transform.scale(1.5f / boundingBox.getWidth(), 1.5f / boundingBox.getHeight(), 1f / boundingBox.getDepth());
            SoundUtil.playSound(MathUtils.randomBoolean() ? "starling/Body_2.mp3" : "starling/Body_3.mp3");
        }
    }

    @Override
    public boolean isClicked() {
        return IntersectorUtil.isPressed(this.position, 1.25f);
    }
}
