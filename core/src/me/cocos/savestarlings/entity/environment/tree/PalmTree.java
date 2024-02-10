package me.cocos.savestarlings.entity.environment.tree;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import me.cocos.savestarlings.entity.environment.Environment;
import me.cocos.savestarlings.asset.AssetService;
import me.cocos.savestarlings.util.IntersectorUtil;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.utils.MaterialConverter;

public class PalmTree implements Environment {

    private final Scene scene;
    private final BoundingBox boundingBox;
    private final Rectangle rectangle;
    private final Vector3 position;

    private static final SceneAsset sceneAsset;

    static {
        sceneAsset = AssetService.getAsset("environment/palm_tree.glb");
    }

    public PalmTree(Vector3 position) {
        this.position = position;
        this.scene = new Scene(sceneAsset.scene);
        this.boundingBox = new BoundingBox();
        scene.modelInstance.calculateBoundingBox(boundingBox);

        float scaleX = 6.5f / boundingBox.getWidth();
        float scaleY = MathUtils.random(6.5f, 8f) / boundingBox.getHeight();
        float scaleZ = 6.5f / boundingBox.getDepth();

        this.scene.modelInstance.transform.scale(scaleX, scaleY, scaleZ);

        this.boundingBox.mul(scene.modelInstance.transform);
        float x = MathUtils.floor(position.x / 2.5f) * 2.5f + 1.25f;
        float z = MathUtils.floor(position.z / 2.5f) * 2.5f + 1.25f;
        this.position.set(x, position.y, z);

        scene.modelInstance.transform.setTranslation(this.position.x, this.position.y, this.position.z);

        scene.modelInstance.transform.rotate(Vector3.Y, MathUtils.random(360f));

        this.rectangle = new Rectangle(x - 1.25f, z - 1.25f, 2.5f, 2.5f);
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
    public void onClick() {

    }

    @Override
    public boolean isClicked() {
        return IntersectorUtil.isPressed(this.position, 1.25f);
    }

    public static SceneAsset getSceneAsset() {
        return sceneAsset;
    }
}

