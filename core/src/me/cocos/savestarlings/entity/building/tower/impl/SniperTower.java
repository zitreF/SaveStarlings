package me.cocos.savestarlings.entity.building.tower.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.asset.AssetService;
import me.cocos.savestarlings.entity.building.tower.Tower;
import me.cocos.savestarlings.entity.livingentitiy.Enemy;
import me.cocos.savestarlings.entity.livingentitiy.LivingEntity;
import me.cocos.savestarlings.service.BuildingService;
import me.cocos.savestarlings.service.EntityService;
import me.cocos.savestarlings.service.GameService;
import me.cocos.savestarlings.util.AsyncUtil;
import me.cocos.savestarlings.util.GridUtil;
import me.cocos.savestarlings.util.IntersectorUtil;
import me.cocos.savestarlings.util.SoundUtil;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

import java.util.Comparator;

public class SniperTower extends Tower {

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
        sceneAsset = AssetService.getAsset("buildings/turrets/sniper_tower.glb");
    }

    public SniperTower(Vector3 position) {
        this.position = new Vector3(position);
        this.scene = new Scene(sceneAsset.scene);
        this.rotationDirection = new Vector3();
        this.boundingBox = new BoundingBox();
        scene.modelInstance.calculateBoundingBox(boundingBox);

        float scaleX = 4f / boundingBox.getWidth();
        float scaleY = 3.25f / boundingBox.getHeight();
        float scaleZ = 6f / boundingBox.getDepth();

        this.scene.modelInstance.transform.scale(scaleX, scaleY, scaleZ);

        float x = MathUtils.round(this.position.x / 2.5f) * 2.5f;
        float z = MathUtils.round(this.position.z / 2.5f) * 2.5f;

        this.position.set(x, this.position.y, z);

        scene.modelInstance.transform.setTranslation(this.position.x, this.position.y, this.position.z);

        this.delay = 1f;

        this.rectangle = new Rectangle(x, z, 5f, 5f);

        GameService.getInstance().getEnvironmentService().getSceneService().addSceneWithoutShadows(GridUtil.createGrid(-0f, 0f, new Vector2(this.position.x, this.position.z)), false);

        ModelBuilder modelBuilder = new ModelBuilder();
        Model greenSquareModel = modelBuilder.createBox(
                rectangle.getWidth(), 0.1f, rectangle.getHeight(),
                new Material(BuildingService.GREEN_COLOR_ATTRIBUTE, BuildingService.OPACITY_ATTRIBUTE),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        Scene greenSquareScene = new Scene(greenSquareModel);

        greenSquareScene.modelInstance.transform.setTranslation(x, this.position.y, z);

        GameService.getInstance().getEnvironmentService().getSceneService().addSceneWithoutShadows(greenSquareScene, false);
        Model blueCapsuleModel = modelBuilder.createSphere(
                this.getRange(), 10f, this.getRange(), 45, 45,
                new Material(
                        PBRColorAttribute.createBaseColorFactor(Color.valueOf("#0193bf")),
                        new BlendingAttribute(GL32.GL_SRC_ALPHA, GL32.GL_ONE_MINUS_SRC_ALPHA, 0.5f)
                ),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        this.rangeCapsule = new Scene(blueCapsuleModel);
        rangeCapsule.modelInstance.transform.setTranslation(x, this.position.y, z);

        Model test = modelBuilder.createBox(
                rectangle.width, 0.5f, rectangle.height,
                new Material(),
                VertexAttributes.Usage.Normal | VertexAttributes.Usage.Position);


        Scene tess = new Scene(test);
        tess.modelInstance.transform.setTranslation(rectangle.x, 1f, rectangle.y);

        GameService.getInstance().getEnvironmentService().getSceneService().addSceneWithoutShadows(tess, false);
    }

    @Override
    public void update(float delta) {
        AsyncUtil.runAsync(() -> {
            this.handleHover(delta);
            this.updateDelays(delta);
            this.updateRotation();
            this.checkAttack();
        });
    }

    private void updateDelays(float delta) {
        if (delay < 1f) {
            this.delay += delta;
        }
        if (attackDelay < 2f) {
            this.attackDelay += delta;
        }
    }

    private void updateRotation() {
        if (delay >= 1f) {
            this.delay = 0f;
            EntityService entityService = GameService.getInstance().getEntityService();
            entityService.getEntities().stream()
                    .filter(entity -> entity instanceof Enemy && position.dst2(entity.getPosition()) < Math.pow(getRange() / 2f, 2))
                    .min(Comparator.comparing(enemy -> this.position.dst2(enemy.getPosition())))
                    .ifPresent(this::updateRotationForTarget);
        }
    }

    private void updateRotationForTarget(LivingEntity nearestBuilding) {
        this.target = nearestBuilding;
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

    private void checkAttack() {
        if (target != null) {
            if (position.epsilonEquals(target.getPosition(), this.getRange())) {
                if (attackDelay >= 2f) {
                    this.attackDelay = 0f;
                    SoundUtil.playSound("other/laser.mp3");
                }
            }
        }
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
        return 40;
    }

    public static SceneAsset getSceneAsset() {
        return sceneAsset;
    }

    @Override
    public boolean isHovered() {
        return IntersectorUtil.isPressed(this.position, 1.25f);
    }
}
