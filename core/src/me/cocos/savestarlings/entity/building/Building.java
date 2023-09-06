package me.cocos.savestarlings.entity.building;

import com.badlogic.gdx.math.Vector2;
import me.cocos.savestarlings.entity.Entity;

public interface Building extends Entity {

    void update(float delta);

    float getDimension();
}
