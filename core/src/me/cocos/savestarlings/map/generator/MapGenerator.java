package me.cocos.savestarlings.map.generator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import me.cocos.savestarlings.asset.AssetService;
import me.cocos.savestarlings.map.noise.PerlinNoise;
import me.cocos.savestarlings.scene.SceneService;
import me.cocos.savestarlings.service.GameService;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.scene.Scene;

public class MapGenerator {

    private final PerlinNoise perlinNoise;
    private final SceneService sceneService;

    public MapGenerator() {
        this.perlinNoise = new PerlinNoise();
        this.sceneService = GameService.getInstance().getEnvironmentService().getSceneService();
    }

    public void generateMap() {
        ModelBuilder modelBuilder = new ModelBuilder();

        Texture texture = AssetService.getAsset("map/terrain/textures/meadow.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        PBRTextureAttribute textureAttribute = PBRTextureAttribute.createBaseColorTexture(texture);
        textureAttribute.scaleV = 7.5f;
        textureAttribute.scaleU = 7.5f;
        textureAttribute.rotationUV = MathUtils.random(360f);

        Model greenBoxModel = modelBuilder.createBox(
                200f, 0f, 200f,
                new Material(textureAttribute),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates
        );
        ModelInstance greenBoxInstance = new ModelInstance(greenBoxModel);
        greenBoxInstance.transform.setFromEulerAngles(90f, 0f, 0f);
        greenBoxInstance.transform.setToTranslation(0f, 0f, 0f);
        this.sceneService.addScene(new Scene(greenBoxInstance));
    }

    private Texture generateHeightmapTexture() {
        Pixmap pixmap = new Pixmap(200, 200, Pixmap.Format.RGB888);

        for (int y = 0; y < 200; y++) {
            for (int x = 0; x < 200; x++) {
                float noiseValue = perlinNoise.noise(x * 0.025f, y * 0.025f, 0);
                int color = this.interpolateColors(noiseValue);
                pixmap.setColor(color);
                pixmap.drawPixel(x, y);
            }
        }
        PixmapIO.writePNG(Gdx.files.local("noise.png"), pixmap);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private int interpolateColors(float noiseValue) {
        float r = MathUtils.lerp(0f, 1f, noiseValue);
        float g = MathUtils.lerp(0f, 1f, noiseValue);
        float b = MathUtils.lerp(0f, 1f, noiseValue);

        return ((int) (r * 255) << 24) | ((int) (g * 255) << 16) | ((int) (b * 255) << 8) | 255;
    }
}
