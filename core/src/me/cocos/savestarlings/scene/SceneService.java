package me.cocos.savestarlings.scene;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneManager;

public class SceneService extends SceneManager {

    private final Array<RenderableProvider> nonShadowRenderableProviders = new Array<>();

    public SceneService(ShaderProvider shaderProvider, DepthShaderProvider depthShaderProvider) {
        super(shaderProvider, depthShaderProvider);
    }

    @Override
    public void renderColors() {
        ModelBatch batch = this.getBatch();
        batch.begin(camera);
        batch.render(this.nonShadowRenderableProviders, computedEnvironement);
        batch.render(this.getRenderableProviders(), computedEnvironement);
        if (this.getSkyBox() != null) batch.render(this.getSkyBox());
        batch.end();
    }

    public void addSceneWithoutShadows(Scene scene, boolean appendLights) {
        this.nonShadowRenderableProviders.add(scene);
        if (appendLights) {
            for (ObjectMap.Entry<Node, BaseLight> e : scene.lights) {
                environment.add(e.value);
            }
        }
    }

    public void removeSceneWithoutShadows(Scene scene) {
        this.nonShadowRenderableProviders.removeValue(scene, false);
        this.removeScene(scene);
    }
}
