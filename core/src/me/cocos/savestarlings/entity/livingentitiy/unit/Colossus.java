package me.cocos.savestarlings.entity.livingentitiy.unit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.entity.building.Building;
import me.cocos.savestarlings.entity.livingentitiy.Enemy;
import me.cocos.savestarlings.entity.livingentitiy.LivingEntity;
import me.cocos.savestarlings.entity.livingentitiy.projectiles.Bullet;
import me.cocos.savestarlings.asset.AssetService;
import me.cocos.savestarlings.service.EntityService;
import me.cocos.savestarlings.service.GameService;
import me.cocos.savestarlings.util.AsyncUtil;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

import java.util.Comparator;

public class Colossus implements LivingEntity, Enemy {

    private static final SceneAsset sceneAsset;

    private final Scene scene;
    private final BoundingBox boundingBox;
    private final Vector3 position;
    private float delay;
    private float attackDelay;
    private Building target;
    private final Vector3 rotationDirection;
    private final Vector3 direction;

    static {
        sceneAsset = AssetService.getAsset("entities/units/colossus.glb");
    }

    public Colossus(Vector3 position) {
        this.position = new Vector3(position);
        this.scene = new Scene(sceneAsset.scene);
        this.rotationDirection = new Vector3();
        this.direction = new Vector3();
        this.boundingBox = new BoundingBox();
        this.delay = 5f;
        this.attackDelay = 0f;
        scene.modelInstance.calculateBoundingBox(boundingBox);

        float scaleX = 4f / boundingBox.getWidth();
        float scaleY = 4f / boundingBox.getHeight();
        float scaleZ = 4.5f / boundingBox.getDepth();

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
                entityService.getBuildings().stream()
                        .min(Comparator.comparing(building -> this.position.dst2(building.getPosition())))
                        .ifPresent(nearestBuilding -> this.target = nearestBuilding);
                this.rotationDirection.set(target.getPosition()).sub(position).nor();

                float rotationAngleDeg = MathUtils.atan2(rotationDirection.x, rotationDirection.z) * MathUtils.radiansToDegrees;
                Gdx.app.postRunnable(() -> {
                    scene.modelInstance.transform.setToRotation(Vector3.Y, rotationAngleDeg);
                    scene.modelInstance.transform.setTranslation(this.position);
                    scene.modelInstance.transform.scale(4f / boundingBox.getWidth(), 4f / boundingBox.getHeight(), 4.5f / boundingBox.getDepth());
                });
            }
            if (target != null) {
                if (position.epsilonEquals(target.getPosition(), 15f)) {
                    if (attackDelay >= 2f) {
                        this.attackDelay = 0f;
                        Gdx.app.postRunnable(() -> {
                            EntityService entityService = GameService.getInstance().getEntityService();
                            Bullet bullet = new Bullet(this.position, rotationDirection);
                            entityService.addEntity(bullet);
                        });
                    }
                    return;
                }
                this.direction.set(target.getPosition()).sub(position).nor();
                position.add(direction.scl(1.5f * delta));
                scene.modelInstance.transform.setTranslation(this.position);
            }
        });
    }
}
