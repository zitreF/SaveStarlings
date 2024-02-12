package me.cocos.savestarlings.entity.building.tower.impl;

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
import me.cocos.savestarlings.entity.building.tower.Defense;
import me.cocos.savestarlings.service.GameService;
import me.cocos.savestarlings.util.GridUtil;
import me.cocos.savestarlings.util.IntersectorUtil;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class Wall implements Defense {

    private final Scene scene;
    private final BoundingBox boundingBox;
    private final Rectangle rectangle;
    private final Vector3 position;
    private float health;

    private static final SceneAsset sceneAsset;

    static {
        sceneAsset = AssetService.getAsset("buildings/turrets/wall.glb");
    }

    public Wall(Vector3 position) {
        this.position = new Vector3(position);
        this.scene = new Scene(sceneAsset.scene);
        this.boundingBox = new BoundingBox();
        scene.modelInstance.calculateBoundingBox(boundingBox);

        float scaleX = 2.5f / boundingBox.getWidth();
        float scaleY = 2.5f / boundingBox.getHeight();
        float scaleZ = 2.5f / boundingBox.getDepth();

        this.scene.modelInstance.transform.scale(scaleX, scaleY, scaleZ);

        float x = MathUtils.floor(this.position.x / 2.5f) * 2.5f + 1.25f;
        float z = MathUtils.floor(this.position.z / 2.5f) * 2.5f + 1.25f;

        this.position.set(x, this.position.y, z);

        scene.modelInstance.transform.setTranslation(this.position.x, this.position.y, this.position.z);

        this.rectangle = new Rectangle(x, z, 2.5f, 2.5f);

        ModelBuilder modelBuilder = new ModelBuilder();

        Model blueCapsuleModel = modelBuilder.createBox(
                rectangle.width, 0.5f, rectangle.height,
                new Material(),
                VertexAttributes.Usage.Normal | VertexAttributes.Usage.Position);


        Scene rangeCapsule = new Scene(blueCapsuleModel);
        rangeCapsule.modelInstance.transform.setTranslation(rectangle.x, this.position.y, rectangle.y);

        GameService.getInstance().getEnvironmentService().getSceneService().addSceneWithoutShadows(rangeCapsule, false);
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
        return this.position;
    }

    public static SceneAsset getSceneAsset() {
        return sceneAsset;
    }

    @Override
    public boolean isHovered() {
        return IntersectorUtil.isPressed(this.position, 1.25f);
    }
}
