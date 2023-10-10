package me.cocos.savestarlings.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import me.cocos.savestarlings.service.GameService;

public class IntersectorUtil {

    private IntersectorUtil() {}

    public static boolean isPressed(Vector3 position, float size) {
        Ray ray = GameService.getInstance().getEnvironmentService().getSceneService().camera.getPickRay(Gdx.input.getX(), Gdx.input.getY());

        return Intersector.intersectRaySphere(ray, position, size, null);
    }

    public static boolean isPressedBounding(Vector3 center, Vector3 dimensions) {
        Ray ray = GameService.getInstance().getEnvironmentService().getSceneService().camera.getPickRay(Gdx.input.getX(), Gdx.input.getY());

        return Intersector.intersectRayBoundsFast(ray, center, dimensions);
    }
}
