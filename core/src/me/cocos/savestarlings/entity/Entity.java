package me.cocos.savestarlings.entity;

import com.badlogic.gdx.math.Vector3;
import net.mgsx.gltf.scene3d.scene.Scene;

public interface Entity {

    Scene getScene();

    Vector3 getPosition();

}
