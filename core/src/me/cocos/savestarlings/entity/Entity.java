package me.cocos.savestarlings.entity;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import net.mgsx.gltf.scene3d.scene.Scene;

public interface Entity {

    Scene getScene();

    BoundingBox getBoundingBox();

    Vector3 getPosition();


}
