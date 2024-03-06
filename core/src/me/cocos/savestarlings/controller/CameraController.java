package me.cocos.savestarlings.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraController implements InputProcessor {

    private static final float CAMERA_SPEED = 5f;
    private static final float ZOOM_SPEED = 1000f;
    private final Camera camera;

    public CameraController(Camera camera) {
        this.camera = camera;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.F11) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(1600, 900);
            } else {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (amountY == -1) {
            this.camera.translate(0, Gdx.graphics.getDeltaTime() * -ZOOM_SPEED, 0);
            if (camera.position.y < 25f) {
                camera.position.y = 25f;
            }
        } else {
            this.camera.translate(0, Gdx.graphics.getDeltaTime() * ZOOM_SPEED, 0);
            if (camera.position.y > 50f) {
                camera.position.y = 50f;
            }
        }
        return false;
    }

    private final Vector3 velocity = new Vector3();

    public void update(float delta) {
        velocity.x += (Gdx.input.isKeyPressed(Input.Keys.W) ? -1 : Gdx.input.isKeyPressed(Input.Keys.S) ? 1 : 0) * delta * CAMERA_SPEED;
        velocity.z += (Gdx.input.isKeyPressed(Input.Keys.D) ? -1 : Gdx.input.isKeyPressed(Input.Keys.A) ? 1 : 0) * delta * CAMERA_SPEED;
        velocity.scl(0.8f);

        float clampedX = MathUtils.clamp(camera.position.x + velocity.x, -50f, 50f);
        float clampedZ = MathUtils.clamp(camera.position.z + velocity.z, -50f, 50f);

        camera.position.set(clampedX, camera.position.y, clampedZ);
        camera.update(true);
    }
}
