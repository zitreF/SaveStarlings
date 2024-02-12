package me.cocos.savestarlings.entity.building;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.cocos.savestarlings.entity.Clickable;
import me.cocos.savestarlings.entity.Entity;

public interface Building extends Entity, Clickable {

    void update(float delta);

    float getHealth();

    void setHealth(float amount);

    Rectangle getBounding();
}
