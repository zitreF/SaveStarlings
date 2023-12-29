package me.cocos.savestarlings.entity.environment;

import com.badlogic.gdx.math.Rectangle;
import me.cocos.savestarlings.entity.Clickable;
import me.cocos.savestarlings.entity.Entity;

public interface Environment extends Entity, Clickable {

    Rectangle getBounding();
}
