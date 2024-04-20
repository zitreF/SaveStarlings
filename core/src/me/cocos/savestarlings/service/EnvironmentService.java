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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.cocos.savestarlings.entity.building.Building;
import me.cocos.savestarlings.entity.environment.Environment;
import me.cocos.savestarlings.entity.livingentitiy.LivingEntity;
import me.cocos.savestarlings.map.noise.PerlinNoise;
import me.cocos.savestarlings.scene.SceneService;
import me.cocos.savestarlings.asset.AssetService;
import me.cocos.savestarlings.util.GridUtil;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalShadowLight;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneSkybox;
import net.mgsx.gltf.scene3d.shaders.PBRShaderConfig;
import net.mgsx.gltf.scene3d.shaders.PBRShaderProvider;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;
import net.mgsx.gltf.scene3d.utils.ShaderParser;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EnvironmentService {

    private final SceneService sceneService;
    private final Cubemap diffuseCubemap;
    private final Cubemap environmentCubemap;
    private final Cubemap specularCubemap;
    private final Texture brdfLUT;
    private final SceneSkybox skybox;
    private final DirectionalShadowLight directionalShadowLight;
    private EntityService entityService;
    private final ScheduledExecutorService executorService;
    private final ParticleService particleService;

    private final PerlinNoise perlinNoise;

    public EnvironmentService(ParticleService particleService, Camera camera) {
        this.perlinNoise = new PerlinNoise();
        PBRShaderConfig config = PBRShaderProvider.createDefaultConfig();
        config.fragmentShader = AssetService.getAsset("shaders/pbr/pbr.fs.glsl");
        config.vertexShader = AssetService.getAsset("shaders/pbr/pbr.vs.glsl");
        config.numBones = 0;
        config.numDirectionalLights = 2;
        config.numPointLights = 0;
        DepthShader.Config depthConfig = new DepthShader.Config();
        depthConfig.numBones = config.numBones;
        this.sceneService = new SceneService(PBRShaderProvider.createDefault(config), PBRShaderProvider.createDefaultDepth(depthConfig));
        this.directionalShadowLight = new DirectionalShadowLight(4048, 4048, 256f, 256f, 1f, 256f);
        sceneService.environment.add(directionalShadowLight.set(Color.WHITE, new Vector3(-0.5f, -1f, 0.5f).nor(), 5f));

        IBLBuilder iblBuilder = IBLBuilder.createOutdoor(directionalShadowLight);
        this.environmentCubemap = iblBuilder.buildEnvMap(1);
        this.diffuseCubemap = iblBuilder.buildIrradianceMap(16);
        this.specularCubemap = iblBuilder.buildRadianceMap(1);
        iblBuilder.dispose();

        this.brdfLUT = new Texture(Gdx.files.classpath("net/mgsx/gltf/shaders/brdfLUT.png"));

        sceneService.setCamera(camera);
        sceneService.setAmbientLight(1f);
        sceneService.environment.set(new PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, brdfLUT));
        sceneService.environment.set(PBRCubemapAttribute.createSpecularEnv(specularCubemap));
        sceneService.environment.set(PBRCubemapAttribute.createDiffuseEnv(diffuseCubemap));
        sceneService.environment.set(PBRColorAttribute.createDiffuse(Color.WHITE));
        sceneService.environment.set(new PBRFloatAttribute(PBRFloatAttribute.ShadowBias, 1f / 256f));
        this.skybox = new SceneSkybox(environmentCubemap);
        sceneService.setSkyBox(skybox);

        this.executorService = Executors.newScheduledThreadPool(2);

        Thread gdxThread = Thread.currentThread();

        executorService.scheduleAtFixedRate(() -> {
            if (!gdxThread.isAlive()) {
                executorService.shutdown();
                return;
            }
            for (Building building : entityService.getBuildings()) {
                Scene scene = building.getScene();
                boolean isVisible = this.isVisible(sceneService.camera, scene.modelInstance);
                boolean contains = sceneService.getRenderableProviders().contains(scene, false);
                if (!contains && isVisible) {
                    sceneService.addScene(scene);
                } else if (contains && !isVisible) {
                    sceneService.removeScene(scene);
                }
            }
            for (LivingEntity livingEntity : this.entityService.getEntities()) {
                Scene scene = livingEntity.getScene();
                boolean isVisible = this.isVisible(sceneService.camera, scene.modelInstance);
                boolean contains = sceneService.getRenderableProviders().contains(scene, false);
                if (!contains && isVisible) {
                    sceneService.addScene(scene);
                } else if (contains && !isVisible) {
                    sceneService.removeScene(scene);
                }
            }
            for (Environment environment : this.entityService.getEnvironments()) {
                Scene scene = environment.getScene();
                boolean isVisible = this.isVisible(sceneService.camera, scene.modelInstance);
                boolean contains = sceneService.getRenderableProviders().contains(scene, false);
                if (!contains && isVisible) {
                    sceneService.addScene(scene);
                } else if (contains && !isVisible) {
                    sceneService.removeScene(scene);
                }
            }
        }, 150, 150, TimeUnit.MILLISECONDS);
        this.particleService = particleService;

    }

    public void dispose() {
        sceneService.dispose();
        environmentCubemap.dispose();
        diffuseCubemap.dispose();
        specularCubemap.dispose();
        brdfLUT.dispose();
        skybox.dispose();
    }

    public void addScene(Scene scene) {
        this.sceneService.addScene(scene);
    }

    public void addSceneWithoutShadows(Scene scene) {
        this.sceneService.addSceneWithoutShadows(scene, false);
    }

    public void removeScene(Scene scene) {
        this.sceneService.removeScene(scene);
    }

    public SceneService getSceneService() {
        return this.sceneService;
    }

    public void update(float delta) {
        sceneService.update(delta);
        directionalShadowLight.setCenter(sceneService.camera.position);
        sceneService.update(delta);
    }

    public void render() {
        sceneService.renderShadows();
        sceneService.renderColors();
        particleService.render(sceneService.getBatch());
    }

    private final Vector3 position = new Vector3();

    private boolean isVisible(Camera cam, ModelInstance instance) {
        instance.transform.getTranslation(position);
        return cam.frustum.pointInFrustum(position);
    }

    public void setEntityService(EntityService entityService) {
        this.entityService = entityService;
    }

    public void removeSceneWithoutShadows(Scene scene) {
        this.sceneService.removeSceneWithoutShadows(scene);
    }
}
