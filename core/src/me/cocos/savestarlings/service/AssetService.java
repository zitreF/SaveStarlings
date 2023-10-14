package me.cocos.savestarlings.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import net.mgsx.gltf.loaders.glb.GLBAssetLoader;
import net.mgsx.gltf.loaders.gltf.GLTFAssetLoader;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class AssetService {

    private static final AssetManager ASSET_MANAGER;

    static {
        ASSET_MANAGER = new AssetManager();
        InternalFileHandleResolver internalFileHandleResolver = new InternalFileHandleResolver();
        ASSET_MANAGER.setLoader(SceneAsset.class, ".glb", new GLBAssetLoader(internalFileHandleResolver));
        ASSET_MANAGER.setLoader(Sound.class, ".mp3", new SoundLoader(internalFileHandleResolver));
        ASSET_MANAGER.setLoader(ParticleEffect.class, new ParticleEffectLoader(internalFileHandleResolver));
        ASSET_MANAGER.setLoader(Texture.class, new TextureLoader(internalFileHandleResolver));
    }

    public static void load() {

        // MODELS
        ASSET_MANAGER.load("buildings/turrets/sniper_tower.glb", SceneAsset.class);
        ASSET_MANAGER.load("buildings/turrets/cannon_blast.glb", SceneAsset.class);
        ASSET_MANAGER.load("buildings/starbase/sb-7.glb", SceneAsset.class);
        ASSET_MANAGER.load("buildings/resources/compacthouse.glb", SceneAsset.class);
        ASSET_MANAGER.load("buildings/resources/bank.glb", SceneAsset.class);
        ASSET_MANAGER.load("buildings/other/laboratory.glb", SceneAsset.class);
        ASSET_MANAGER.load("entities/starlings/starling.glb", SceneAsset.class);
        ASSET_MANAGER.load("entities/units/colossus.glb", SceneAsset.class);
        ASSET_MANAGER.load("entities/projectiles/rocket.glb", SceneAsset.class);

        // SOUNDS
        ASSET_MANAGER.load("sounds/starling/Body_1.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/starling/Body_2.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/starling/Body_3.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/starling/Body_4.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/other/click.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/building/repair.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/building/build.mp3", Sound.class);

        // MUSIC
        ASSET_MANAGER.load("sounds/music/music_main.mp3", Music.class);

        // PARTICLES
        ASSET_MANAGER.load("particles/laser_smoke", ParticleEffect.class);

        // TEXTURES
        TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
        textureParameter.minFilter = Texture.TextureFilter.Linear;
        textureParameter.magFilter = Texture.TextureFilter.Linear;
        ASSET_MANAGER.load("ui/popup/turrets/blast_cannon_popup.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/popup/turrets/sniper_tower_popup.png", Texture.class, textureParameter);
    }

    public static <T> T getAsset(String name) {
        return ASSET_MANAGER.get(name);
    }

    public static <T> T getAsset(String name, Class<T> clazz) {
        return ASSET_MANAGER.get(name, clazz);
    }

    public static AssetManager getAssetManager() {
        return ASSET_MANAGER;
    }
}
