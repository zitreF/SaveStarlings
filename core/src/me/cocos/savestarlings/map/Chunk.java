package me.cocos.savestarlings.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import me.cocos.savestarlings.entity.Entity;

import java.util.List;

public class Chunk {

    private final Array<Entity> entities;
    private final Vector3 position;

    public Chunk(float x, float z) {
        this.position = new Vector3(x, 1f, z);
        this.entities = new Array<>();
    }

    public Vector3 getPosition() {
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
