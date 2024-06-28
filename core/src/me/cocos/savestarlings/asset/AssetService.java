package me.cocos.savestarlings.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import me.cocos.savestarlings.asset.loader.ShaderLoader;
import net.mgsx.gltf.loaders.glb.GLBAssetLoader;
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
        ASSET_MANAGER.setLoader(String.class, new ShaderLoader(internalFileHandleResolver));
    }

    public static void load() {

        // MODELS
        ASSET_MANAGER.load("buildings/turrets/mortar.glb", SceneAsset.class);
        ASSET_MANAGER.load("buildings/turrets/sniper_tower.glb", SceneAsset.class);
        ASSET_MANAGER.load("buildings/turrets/cannon_blast.glb", SceneAsset.class);
        ASSET_MANAGER.load("buildings/turrets/wall.glb", SceneAsset.class);
        ASSET_MANAGER.load("buildings/starbase/sb-7.glb", SceneAsset.class);
        ASSET_MANAGER.load("buildings/resources/compacthouse.glb", SceneAsset.class);
        ASSET_MANAGER.load("buildings/resources/bank.glb", SceneAsset.class);
        ASSET_MANAGER.load("buildings/other/laboratory.glb", SceneAsset.class);
        ASSET_MANAGER.load("entities/starlings/starling.glb", SceneAsset.class);
        ASSET_MANAGER.load("entities/starlings/nick.glb", SceneAsset.class);
        ASSET_MANAGER.load("entities/units/colossus.glb", SceneAsset.class);
        ASSET_MANAGER.load("entities/projectiles/rocket.glb", SceneAsset.class);
        ASSET_MANAGER.load("environment/palm_tree.glb", SceneAsset.class);
        ASSET_MANAGER.load("environment/rock.glb", SceneAsset.class);
        // SOUNDS
        ASSET_MANAGER.load("sounds/starling/Body_1.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/starling/Body_2.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/starling/Body_3.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/starling/Body_4.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/other/click.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/other/explode.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/other/explosion2.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/other/explosion3.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/other/explosion4.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/other/explosion5.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/other/laser.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/building/repair.mp3", Sound.class);
        ASSET_MANAGER.load("sounds/building/build.mp3", Sound.class);

        // MUSIC
        ASSET_MANAGER.load("sounds/music/music_main.mp3", Music.class);

        // TEXTURES
        TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
        textureParameter.minFilter = Texture.TextureFilter.Linear;
        textureParameter.magFilter = Texture.TextureFilter.Linear;

        ASSET_MANAGER.load("map/terrain/textures/meadow.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("map/terrain/textures/swamp.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("map/terrain/textures/winter.png", Texture.class, textureParameter);

        ASSET_MANAGER.load("ui/popup/turrets/blast_cannon_popup.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/popup/turrets/sniper_tower_popup.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/buildings/turrets/btn_close.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/icons/minerals.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/icons/coins.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/background/resources_background.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/icons/decorations.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/icons/defenses.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/icons/turrets.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/icons/army.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/icons/resources.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/icons/decorations_hover.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/icons/defenses_hover.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/icons/turrets_hover.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/icons/army_hover.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/icons/resources_hover.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/buildings/turrets/btn_info.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/buildings/turrets/cannon_blast.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/buildings/turrets/cannon_blast_pressed.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/buildings/turrets/sniper.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/buildings/turrets/sniper_pressed.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/popup/turret_popup.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/popup/default_popup.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/popup/icons/health_icon.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/popup/icons/damage_icon.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/popup/icons/range_icon.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/popup/icons/accelerate_icon.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/popup/dialog/dialog_background.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/progressbar/progressbar.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("ui/starlings/starling_pointing.png", Texture.class, textureParameter);

        // PARTICLES
        ASSET_MANAGER.load("particles/explosion.png", Texture.class, textureParameter);
        ASSET_MANAGER.load("particles/explosion_2.png", Texture.class, textureParameter);

        // SHADERS

        ASSET_MANAGER.load("shaders/pbr/pbr.fs.glsl", String.class, new ShaderLoader.StringParameter());
        ASSET_MANAGER.load("shaders/pbr/pbr.vs.glsl", String.class, new ShaderLoader.StringParameter());
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
