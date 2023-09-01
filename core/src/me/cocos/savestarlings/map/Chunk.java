package me.cocos.savestarlings.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.cocos.savestarlings.entity.Entity;

public class Chunk {

    private final Array<Entity> entities;
    private final Vector2 position;

    public Chunk(float x, float y) {
        this.position = new Vector2(x, y);
        this.entities = new Array<>();
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        this.entities.removeValue(entity, false);
    }

    public Array<Entity> getEntities() {
        return this.entities;
    }
}
