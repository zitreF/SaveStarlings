package me.cocos.savestarlings.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import me.cocos.savestarlings.controller.CameraController;
import me.cocos.savestarlings.service.GameService;

public class GameScreen implements Screen {


    private final PerspectiveCamera camera;

    private final CameraController cameraController;

    private final GameService gameService;

    public GameScreen() {
        this.gameService = new GameService();

        this.camera = new PerspectiveCamera(60, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = 0.1f;
        camera.far = 1000f;
        camera.position.set(10f, 40f, 0f);
        camera.lookAt(0f, 0f, 0f);

        gameService.getEnvironmentService().getSceneService().setCamera(camera);

        this.cameraController = new CameraController(camera);

        gameService.setInputProcessors(cameraController);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        cameraController.update(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        gameService.update(delta);
    }


    @Override
    public void resize(int width, int height) {
        this.gameService.getEnvironmentService().getSceneService().updateViewport(width, height);
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
