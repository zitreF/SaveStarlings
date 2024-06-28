package me.cocos.savestarlings.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FillViewport;
import me.cocos.savestarlings.controller.CameraController;
import me.cocos.savestarlings.hud.impl.DebugHud;
import me.cocos.savestarlings.service.GameService;

public class GameScreen implements Screen {

    private final PerspectiveCamera camera;
    private final FillViewport gameViewport;
    private final CameraController cameraController;
    private final GameService gameService;

    public GameScreen() {
        this.camera = new PerspectiveCamera(60f, 16f, 9f);
        this.gameViewport = new FillViewport(16f, 9f, camera);
        camera.near = 1f;
        camera.far = 1000f;
        camera.position.set(10f, 40f, 0f);
        camera.lookAt(0f, 0f, 0f);

        gameViewport.apply();

        this.gameService = new GameService(camera);

        this.cameraController = new CameraController(camera);
    }

    @Override
    public void show() {
        gameService.setInputProcessors(cameraController);
    }

    @Override
    public void render(float delta) {
        cameraController.update(delta);
        gameViewport.apply();
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL32.GL_COLOR_BUFFER_BIT | GL32.GL_DEPTH_BUFFER_BIT);

        gameService.update(delta);

        gameService.render();

    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
        gameService.resize(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.gameService.dispose();
    }
}
