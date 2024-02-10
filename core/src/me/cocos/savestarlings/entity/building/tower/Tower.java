package me.cocos.savestarlings.entity.building.tower;

import me.cocos.savestarlings.entity.building.Building;

public abstract class Tower implements Building {

    public abstract int getRange();

    public abstract boolean isHovered();
}
