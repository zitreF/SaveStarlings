package me.cocos.savestarlings.service;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g3d.Model;
import net.mgsx.gltf.loaders.glb.GLBAssetLoader;
import net.mgsx.gltf.loaders.gltf.GLTFAssetLoader;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class AssetService {

    private static final AssetManager ASSET_MANAGER;

    static {
        ASSET_MANAGER = new AssetManager();
        ASSET_MANAGER.setLoader(SceneAsset.class, ".glb", new GLBAssetLoader(new InternalFileHandleResolver()));
    }

    public static void load() {
        ASSET_MANAGER.load("buildings/turrets/sniper_tower.glb", SceneAsset.class);
        ASSET_MANAGER.load("buildings/sb-7.glb", SceneAsset.class);
    }

    public static <T> T getAsset(String name) {
        return ASSET_MANAGER.get(name);
    }

    public static AssetManager getAssetManager() {
        return ASSET_MANAGER;
    }
}
