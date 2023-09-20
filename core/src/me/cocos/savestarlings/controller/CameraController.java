package me.cocos.savestarlings.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraController implements InputProcessor {

    private static final float CAMERA_SPEED = 30f;
    private final Camera camera;
    public static boolean isPressed;

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
        if (button == Input.Buttons.LEFT) {
            isPressed = true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            isPressed = false;
        }
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
            this.camera.translate(0, camera.direction.nor().y, 0);
        } else {
            this.camera.translate(0, -camera.direction.nor().y, 0);
        }
        return false;
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.position.x = MathUtils.lerp(camera.position.x, camera.position.x + 1f, delta * -CAMERA_SPEED);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.position.x = MathUtils.lerp(camera.position.x, camera.position.x - 1f, delta * -CAMERA_SPEED);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.position.z = MathUtils.lerp(camera.position.z, camera.position.z - 1f, delta * -CAMERA_SPEED);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.position.z = MathUtils.lerp(camera.position.z, camera.position.z + 1f, delta * -CAMERA_SPEED);
        }
        camera.update(true);
    }
}
