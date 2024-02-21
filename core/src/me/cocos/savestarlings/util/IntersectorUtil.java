package me.cocos.savestarlings.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
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

    public static boolean isColliding(Rectangle rectangle1, Rectangle rectangle2) {
        float radiusX1 = rectangle1.width / 2f;
        float radiusZ1 = rectangle1.height / 2f;

        float radiusX2 = rectangle2.width / 2f;
        float radiusZ2 = rectangle2.height / 2f;

        float distanceX = rectangle1.x - rectangle2.x;
        float distanceZ = rectangle1.y - rectangle2.y;

        float minDistanceX = radiusX1 + radiusX2;
        float minDistanceZ = radiusZ1 + radiusZ2;

        return (Math.abs(distanceX) < minDistanceX) && (Math.abs(distanceZ) < minDistanceZ);
    }
}
