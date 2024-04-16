package me.cocos.savestarlings.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import me.cocos.savestarlings.service.GameService;

public class CameraUtil {

    private static final Vector3 initialPosition = new Vector3();
    private static float shakeIntensity;
    private static float shakeDuration;
    private static float shakeTimer;

    public static void shake(float intensity, float duration) {
        initialPosition.set(GameService.getInstance().getCamera().position);
        shakeIntensity = intensity;
        shakeDuration = duration;
        shakeTimer = duration;
    }

    public static void update(PerspectiveCamera camera, Vector3 movement, float deltaTime) {
        if (shakeTimer > 0f) {
            float shakeTime = MathUtils.clamp(shakeTimer / shakeDuration, 0, 1);
            float currentIntensity = shakeIntensity * shakeTime;
            float offsetX = (MathUtils.random() - 0.5f) * 2 * currentIntensity;
            float offsetY = (MathUtils.random() - 0.5f) * 2 * currentIntensity;
            float offsetZ = (MathUtils.random() - 0.5f) * 2 * currentIntensity;

            initialPosition.add(movement.x, 0f, movement.z);

            camera.position.set(initialPosition.x + offsetX, initialPosition.y + offsetY, initialPosition.z + offsetZ);
            shakeTimer -= deltaTime;
        }
    }

    public static float getShakeTimer() {
        return shakeTimer;
    }
}
