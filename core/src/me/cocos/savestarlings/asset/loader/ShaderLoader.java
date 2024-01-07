package me.cocos.savestarlings.asset.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.scene3d.utils.ShaderParser;

public class ShaderLoader extends AsynchronousAssetLoader<String, ShaderLoader.StringParameter> {

    public ShaderLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, StringParameter parameter) {

    }

    @Override
    public String loadSync(AssetManager manager, String fileName, FileHandle file, StringParameter parameter) {
        return ShaderParser.parse(file);
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, StringParameter parameter) {
        return null;
    }

    public static class StringParameter extends AssetLoaderParameters<String> {

    }

}