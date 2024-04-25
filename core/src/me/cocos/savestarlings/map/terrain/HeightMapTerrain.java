package me.cocos.savestarlings.map.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;

public class HeightMapTerrain extends Terrain {

    private final HeightField field;

    public HeightMapTerrain(Pixmap data, int size, float magnitude) {
        this.size = size;
        this.width = data.getWidth();
        this.heightMagnitude = magnitude;

        field = new HeightField(true, data, true, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        data.dispose();
        float halfSize = size / 2.0f;
        field.corner00.set(-halfSize, 0, -halfSize);
        field.corner10.set(halfSize, 0, -halfSize);
        field.corner01.set(-halfSize, 0, halfSize);
        field.corner11.set(halfSize, 0, halfSize);
        field.update();

        Texture texture = new Texture(Gdx.files.internal("map/terrain/textures/meadow.png"), true);
        texture.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.MipMapLinearLinear);
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        PBRTextureAttribute textureAttribute = PBRTextureAttribute.createBaseColorTexture(texture);
        textureAttribute.scaleU = 40f;
        textureAttribute.scaleV = 40f;

        Material material = new Material();
        material.set(textureAttribute);

        ModelBuilder mb = new ModelBuilder();
        mb.begin();
        mb.part("terrain", field.mesh, GL20.GL_TRIANGLES, material);
        modelInstance = new ModelInstance(mb.end());

        int vertices = 0;
        for (Mesh mesh : modelInstance.model.meshes) {
            vertices += mesh.getNumVertices();
        }
        System.out.println(vertices);
    }

    @Override
    public void dispose() {
        field.dispose();
    }
}
