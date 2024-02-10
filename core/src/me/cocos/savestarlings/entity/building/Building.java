package me.cocos.savestarlings.entity.building;

import com.badlogic.gdx.math.Rectangle;
import me.cocos.savestarlings.entity.Entity;

public interface Building extends Entity {

    void update(float delta);

    float getHealth();

    void setHealth(float amount);

    Rectangle getBounding();

    boolean isHovered();
}
