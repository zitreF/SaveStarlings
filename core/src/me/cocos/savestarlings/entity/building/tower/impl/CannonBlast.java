package me.cocos.savestarlings.entity.building.tower.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.entity.building.tower.Tower;
import me.cocos.savestarlings.entity.livingentitiy.Enemy;
import me.cocos.savestarlings.entity.livingentitiy.LivingEntity;
import me.cocos.savestarlings.service.AssetService;
import me.cocos.savestarlings.service.EntityService;
import me.cocos.savestarlings.service.GameService;
import me.cocos.savestarlings.util.AsyncUtil;
import me.cocos.savestarlings.util.IntersectorUtil;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

import java.util.Comparator;
import java.util.stream.StreamSupport;

public class CannonBlast implements Tower {

    private final Scene scene;
    private final BoundingBox boundingBox;
    private final Rectangle rectangle;
    private final Vector3 position;
    private LivingEntity target;
    private float health;
    private float delay;
    private float attackDelay;
    private final Vector3 rotationDirection;
    private static final SceneAsset sceneAsset;

    static {
        sceneAsset = AssetService.getAsset("buildings/turrets/cannon_blast.glb");
    }

    public CannonBlast(Vector3 position) {
        this.position = new Vector3(position);
        this.scene = new Scene(sceneAsset.scene);
        this.boundingBox = new BoundingBox();
        this.rotationDirection = new Vector3();
        scene.modelInstance.calculateBoundingBox(boundingBox);

        float scaleX = 4f / boundingBox.getWidth();
        float scaleY = 3.25f / boundingBox.getHeight();
        float scaleZ = 6f / boundingBox.getDepth();

        this.scene.modelInstance.transform.scale(scaleX, scaleY, scaleZ);

        float x = MathUtils.round(position.x / 2.5f) * 2.5f;
        float z = MathUtils.round(position.z / 2.5f) * 2.5f;

        position.set(x, position.y, z);

        scene.modelInstance.transform.setTranslation(position.x, position.y, position.z);

        scene.modelInstance.materials.clear();

        this.rectangle = new Rectangle(x - 2.5f, z - 2.5f, 5f, 5f);
    }

    @Override
    public void update(float delta) {
        AsyncUtil.runAsync(() -> {
            if (delay < 1f) {
                this.delay += delta;
            }
            if (attackDelay < 2f) {
                this.attackDelay += delta;
            }
            if (delay >= 1f) {
                this.delay = 0f;
                EntityService entityService = GameService.getInstance().getEntityService();
                StreamSupport.stream(entityService.getEntities().spliterator(), false)
                        .filter(entity -> entity instanceof Enemy && this.position.dst2(entity.getPosition()) < (15*15))
                        .min(Comparator.comparing(enemy -> this.position.dst2(enemy.getPosition())))
                        .ifPresent(nearestBuilding -> this.target = nearestBuilding);
                if (target != null) {
                    this.rotationDirection.set(target.getPosition()).sub(position).nor();

                    float rotationAngleDeg = MathUtils.atan2(rotationDirection.x, rotationDirection.z) * MathUtils.radiansToDegrees;
                    Gdx.app.postRunnable(() -> {
                        scene.modelInstance.transform.setToRotation(Vector3.Y, rotationAngleDeg);
                        scene.modelInstance.transform.setTranslation(this.position);
                        scene.modelInstance.transform.scale(4f / boundingBox.getWidth(), 3.25f / boundingBox.getHeight(), 6f / boundingBox.getDepth());
                    });
                }
            }
            if (target != null) {
                if (position.epsilonEquals(target.getPosition(), 15f)) {
                    if (attackDelay >= 2f) {
                        this.attackDelay = 0f;
                    }
                }
            }
        });
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
