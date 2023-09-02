package me.cocos.savestarlings.service;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import net.mgsx.gltf.loaders.glb.GLBAssetLoader;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class AssetService {

    private static final AssetManager ASSET_MANAGER;

    static {
        ASSET_MANAGER = new AssetManager();
        ASSET_MANAGER.setLoader(SceneAsset.class, ".glb", new GLBAssetLoader(new InternalFileHandleResolver()));
        ASSET_MANAGER.setLoader(Sound.class, ".mp3", new SoundLoader(new InternalFileHandleResolver()));
    }

    public static void load() {

        // MODELS
        ASSET_MANAGER.load("buildings/turrets/sniper_tower.glb", SceneAsset.class);
        ASSET_MANAGER.load("buildings/starbase/sb-7.glb", SceneAsset.class);
        ASSET_MANAGER.load("buildings/other/laboratory.glb", SceneAsset.class);

        // SOUNDS
        ASSET_MANAGER.load("sounds/Body_1.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/Body_2.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/Body_3.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/Body_4.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/click.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/repair.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/build.mp3", Sound.class);
    }

    public static <T> T getAsset(String name) {
        return ASSET_MANAGER.get(name);
    }

    public static AssetManager getAssetManager() {
        return ASSET_MANAGER;
    }
}
