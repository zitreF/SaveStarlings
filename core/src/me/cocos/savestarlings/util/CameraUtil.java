package me.cocos.savestarlings.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import me.cocos.savestarlings.service.GameService;

public class CameraUtil {

    private static Vector3 initialPosition;
    private static float shakeIntensity;
    private static float shakeDuration;
    private static float shakeTimer;

    public static void shake(float intensity, float duration) {
        initialPosition = new Vector3(GameService.getInstance().getCamera().position);
        shakeIntensity = intensity;
        shakeDuration = duration;
        shakeTimer = duration;
    }

    public static void update(PerspectiveCamera camera, float deltaTime) {
        if (shakeTimer <= 0) return;

        float shakeTime = MathUtils.clamp(shakeTimer / shakeDuration, 0, 1);
        float currentIntensity = shakeIntensity * shakeTime;
        float offsetX = (MathUtils.random() - 0.5f) * 2 * currentIntensity;
        float offsetY = (MathUtils.random() - 0.5f) * 2 * currentIntensity;
        float offsetZ = (MathUtils.random() - 0.5f) * 2 * currentIntensity;

        camera.position.set(initialPosition.x + offsetX, initialPosition.y + offsetY, initialPosition.z + offsetZ);
        shakeTimer -= deltaTime;
        camera.update();
    }


    public static boolean needUpdate() {
        return shakeTimer > 0;
    }

    public static void reset(PerspectiveCamera camera) {
        camera.position.set(initialPosition);
        camera.update();
    }
}
