package me.cocos.savestarlings.entity.livingentitiy.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.entity.building.Building;
import me.cocos.savestarlings.entity.livingentitiy.LivingEntity;
import me.cocos.savestarlings.particle.Particle;
import me.cocos.savestarlings.particle.impl.ExplosionParticle;
import me.cocos.savestarlings.service.AssetService;
import me.cocos.savestarlings.service.GameService;
import me.cocos.savestarlings.util.AsyncUtil;
import me.cocos.savestarlings.util.SoundUtil;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

import java.util.Iterator;

public final class Bullet implements LivingEntity {
    private static final float SPEED = 20f;
    private static final SceneAsset sceneAsset;
    private final Scene scene;
    private final BoundingBox boundingBox;
    private final Vector3 position;
    private final Vector3 direction;
    private final Vector3 scaling;
    private final Rectangle bounding;

    static {
        sceneAsset = AssetService.getAsset("entities/projectiles/rocket.glb");
    }

    public Bullet(Vector3 position, Vector3 direction) {
        this.position = new Vector3(position).add(0f, 1.5f, 0f);
        this.direction = new Vector3(direction);
        this.scene = new Scene(sceneAsset.scene);
        this.boundingBox = new BoundingBox();
        scene.modelInstance.calculateBoundingBox(boundingBox);

        float scaleX = 1f / boundingBox.getWidth();
        float scaleY = 2f / boundingBox.getHeight();
        float scaleZ = 1f / boundingBox.getDepth();

        this.scaling = new Vector3(scaleX, scaleY, scaleZ);

        scene.modelInstance.transform.setTranslation(this.position.x, this.position.y, this.position.z);

        this.scene.modelInstance.transform.scale(scaling.x, scaling.y, scaling.z);

        scene.modelInstance.materials.clear();

        this.bounding = new Rectangle(this.position.x - 0.5f, this.position.y - 0.5f, 1f, 1f);

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

    private final Vector3 velocity = new Vector3();

    @Override
    public void update(float delta) {
        AsyncUtil.runAsync(() -> {
            velocity.set(direction).scl(SPEED * delta);
            position.add(velocity);
            float rotationAngleDeg = MathUtils.atan2(direction.x, direction.z) * MathUtils.radiansToDegrees;
            scene.modelInstance.transform.setToRotation(Vector3.Y, rotationAngleDeg);
            scene.modelInstance.transform.rotate(Vector3.X, 90f);
            scene.modelInstance.transform.setTranslation(position);
            scene.modelInstance.transform.scale(this.scaling.x, this.scaling.y, this.scaling.z);
            this.bounding.setPosition(position.x - 0.5f, position.z - 0.5f);
            for (Building building : GameService.getInstance().getEntityService().getBuildings()) {
                if (this.bounding.overlaps(building.getBounding())) {
                    SoundUtil.playSound("other/explode.mp3");
                    Gdx.app.postRunnable(() -> {
                        if (direction.x > 0f) {
                            GameService.getInstance().getParticleService().playParticle(Particle.EXPLOSION, this.position);
                        } else {
                            GameService.getInstance().getParticleService().playParticle(Particle.EXPLOSION, this.position.add(direction.scl(building.getBounding().height / 4f)));
                        }
                        GameService.getInstance().getEntityService().removeEntity(this);
                    });
                    return;
                }
            }
        });

    }
}
