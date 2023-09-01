package me.cocos.savestarlings.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.DepthShader;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import me.cocos.savestarlings.entity.Entity;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.lights.DirectionalShadowLight;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.scene.SceneSkybox;
import net.mgsx.gltf.scene3d.shaders.PBRShaderConfig;
import net.mgsx.gltf.scene3d.shaders.PBRShaderProvider;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;

public class EnvironmentService {

    private final SceneManager sceneManager;
    private final Cubemap diffuseCubemap;
    private final Cubemap environmentCubemap;
    private final Cubemap specularCubemap;
    private final Texture brdfLUT;
    private final SceneSkybox skybox;
    private final DirectionalShadowLight directionalShadowLight;
    private EntityService entityService;

    private static final float GRID_MIN = -100f;
    private static final float GRID_MAX = 100f;
    private static final float GRID_STEP = 2.5f;

    private final ChunkService chunkService;

    public EnvironmentService() {
        PBRShaderConfig config = PBRShaderProvider.createDefaultConfig();
        config.numBones = 16;
        config.numDirectionalLights = 2;
        config.numPointLights = 0;
        DepthShader.Config depthConfig = new DepthShader.Config();
        depthConfig.numBones = config.numBones;
        this.sceneManager = new SceneManager(PBRShaderProvider.createDefault(config), PBRShaderProvider.createDefaultDepth(depthConfig));
        this.chunkService = new ChunkService();

        DirectionalLightEx light = new DirectionalLightEx();
        light.direction.set(0, -1, 0);
        light.intensity = 1f;
        light.color.set(Color.WHITE);
        sceneManager.environment.add(light);

        IBLBuilder iblBuilder = IBLBuilder.createOutdoor(light);
        this.environmentCubemap = iblBuilder.buildEnvMap(1);
        this.diffuseCubemap = iblBuilder.buildIrradianceMap(256);
        this.specularCubemap = iblBuilder.buildRadianceMap(10);
        iblBuilder.dispose();

        this.brdfLUT = new Texture(Gdx.files.classpath("net/mgsx/gltf/shaders/brdfLUT.png"));

        sceneManager.setAmbientLight(1f);
        sceneManager.environment.set(new PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, brdfLUT));
        sceneManager.environment.set(PBRCubemapAttribute.createSpecularEnv(specularCubemap));
        sceneManager.environment.set(PBRCubemapAttribute.createDiffuseEnv(diffuseCubemap));
        sceneManager.environment.set(PBRColorAttribute.createDiffuse(Color.WHITE));
        this.directionalShadowLight = new DirectionalShadowLight(2048, 2048, 30f, 30f, 1f, 100f);
        sceneManager.environment.add(directionalShadowLight.set(Color.WHITE, new Vector3(0f, -1f, 0f), 2f));
        this.skybox = new SceneSkybox(environmentCubemap);
        sceneManager.setSkyBox(skybox);

        ModelBuilder modelBuilder = new ModelBuilder();

        Texture texture = new Texture("grass.jpg");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        PBRTextureAttribute textureAttribute = PBRTextureAttribute.createBaseColorTexture(texture);
        textureAttribute.scaleU = 16f;
        textureAttribute.scaleV = 16f;
        Model greenBoxModel = modelBuilder.createBox(
                200f, 1f, 200f,
                new Material(textureAttribute),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates
        );
        ModelInstance greenBoxInstance = new ModelInstance(greenBoxModel);
        greenBoxInstance.transform.setFromEulerAngles(90f, 0f, 0f);
        greenBoxInstance.transform.setToTranslation(0f, -1f, 0f);
        this.sceneManager.addScene(new Scene(greenBoxInstance));
        this.createGrid();
    }

    private void createGrid() {
        Gdx.gl.glLineWidth(2f);
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        BlendingAttribute blendingAttribute = new BlendingAttribute();
        blendingAttribute.opacity = 1f;
        MeshPartBuilder builder = modelBuilder.part("grid", GL20.GL_LINES, VertexAttributes.Usage.Position | VertexAttributes.Usage.ColorUnpacked, new Material(blendingAttribute));
        Color color = Color.valueOf("#175717");
        color.a = 0.75f;
        builder.setColor(color);
        for (float t = GRID_MIN; t <= GRID_MAX; t += GRID_STEP) {
            builder.line(t, 1, GRID_MIN, t, 1, GRID_MAX);
            builder.line(GRID_MIN, 1, t, GRID_MAX, 1, t);
        }
        modelBuilder.part("axes", GL20.GL_LINES, VertexAttributes.Usage.Position | VertexAttributes.Usage.ColorUnpacked, new Material());

        Model axesModel = modelBuilder.end();

        ModelInstance axesInstance = new ModelInstance(axesModel);

        axesInstance.transform.setToTranslation(0f, -1f, 0f);

        this.sceneManager.addScene(new Scene(axesInstance), false);
    }

    public void dispose() {
        sceneManager.dispose();
        environmentCubemap.dispose();
        diffuseCubemap.dispose();
        specularCubemap.dispose();
        brdfLUT.dispose();
        skybox.dispose();
    }

    public void addScene(Scene scene) {
        this.sceneManager.addScene(scene);
    }

    public void removeScene(Scene scene) {
        this.sceneManager.removeScene(scene);
    }

    public SceneManager getSceneManager() {
        return this.sceneManager;
    }

    public void update(float delta) {
        for (Entity entity : entityService.getBuildings()) {
            Scene scene = entity.getScene();
            boolean isVisible = this.isVisible(sceneManager.camera, scene.modelInstance);
            if (!sceneManager.getRenderableProviders().contains(entity.getScene(), false) && isVisible) {
                sceneManager.addScene(scene);
            } else if (sceneManager.getRenderableProviders().contains(entity.getScene(), false) && !isVisible) {
                sceneManager.removeScene(scene);
            }
        }
        directionalShadowLight.setCenter(sceneManager.camera.position);
        sceneManager.update(delta);
        sceneManager.render();
    }

    private final Vector3 position = new Vector3();

    private boolean isVisible(Camera cam, ModelInstance instance) {
        instance.transform.getTranslation(position);
        return cam.frustum.pointInFrustum(position);
    }

    public ChunkService getChunkService() {
        return this.chunkService;
    }

    public void setEntityService(EntityService entityService) {
        this.entityService = entityService;
    }
}
