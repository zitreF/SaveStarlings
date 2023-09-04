package me.cocos.savestarlings.callback;

import com.badlogic.gdx.math.Vector3;

@FunctionalInterface
public interface Result<T> {

    T result(Vector3 position);
}
