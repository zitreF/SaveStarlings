package me.cocos.savestarlings.map.generator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.math.MathUtils;
import me.cocos.savestarlings.map.noise.PerlinNoise;
import me.cocos.savestarlings.map.terrain.HeightMapTerrain;
import me.cocos.savestarlings.map.terrain.Terrain;
import me.cocos.savestarlings.scene.SceneService;
import me.cocos.savestarlings.service.GameService;
import net.mgsx.gltf.scene3d.scene.Scene;

public class MapGenerator {

    private static MapGenerator instance;
    private final PerlinNoise perlinNoise;
    private final SceneService sceneService;
    private Terrain terrain;
    private Scene terrainScene;

    public MapGenerator() {
        instance = this;
        this.perlinNoise = new PerlinNoise();
        this.sceneService = GameService.getInstance().getEnvironmentService().getSceneService();
    }

    public void createTerrain() {
        if (terrain != null) {
            terrain.dispose();
            sceneService.removeScene(terrainScene);
        }
        this.terrain = new HeightMapTerrain(this.generateHeightmapPixmap(), 200, 0f);
        this.terrainScene = new Scene(terrain.getModelInstance());
        terrainScene.modelInstance.transform.setToTranslation(0f, 0f, 0f);
        sceneService.addScene(terrainScene);
    }

    private Pixmap generateHeightmapPixmap() {
        Pixmap pixmap = new Pixmap(200, 200, Pixmap.Format.RGB888);

        for (int y = 0; y < 200; y++) {
            for (int x = 0; x < 200; x++) {
                float noiseValue = perlinNoise.noise(x * 0.02f, y * 0.02f, 0);
                int color = this.interpolateColors(noiseValue);
                pixmap.setColor(color);
                pixmap.drawPixel(x, y);
            }
        }
        PixmapIO.writePNG(Gdx.files.local("noise.png"), pixmap);
        return pixmap;
    }

    private int interpolateColors(float noiseValue) {
        float r = MathUtils.lerp(0f, 0.8f, noiseValue);
        float g = MathUtils.lerp(0f, 0.8f, noiseValue);
        float b = MathUtils.lerp(0f, 0.8f, noiseValue);

        return ((int) (r * 255) << 24) | ((int) (g * 255) << 16) | ((int) (b * 255) << 8) | 255;
    }

    public Terrain getTerrain() {
        return this.terrain;
    }

    public static MapGenerator getInstance() {
        return instance;
    }
}
