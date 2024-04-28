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
import com.badlogic.gdx.math.MathUtils;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;

public class HeightMapTerrain extends Terrain {

    private final HeightField field;

    public HeightMapTerrain(Pixmap data, int size, float magnitude) {
        this.size = size;
        this.width = data.getWidth();
        this.heightMagnitude = magnitude;

        this.field = new HeightField(true, data, true, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        data.dispose();
        float halfSize = size / 2.0f;
        field.corner00.set(-halfSize, 0, -halfSize);
        field.corner10.set(halfSize, 0, -halfSize);
        field.corner01.set(-halfSize, 0, halfSize);
        field.corner11.set(halfSize, 0, halfSize);
        field.magnitude.set(0f, magnitude, 0f);
        field.normalizeHeightField();
        field.update();

        Texture texture = new Texture(Gdx.files.internal("map/terrain/textures/meadow.png"), true);
        texture.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.MipMapLinearLinear);
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        PBRTextureAttribute textureAttribute = PBRTextureAttribute.createBaseColorTexture(texture);
        textureAttribute.scaleU = 10f;
        textureAttribute.scaleV = 10f;
        textureAttribute.rotationUV = MathUtils.random(360f);

        Material material = new Material();
        material.set(textureAttribute);

        ModelBuilder mb = new ModelBuilder();
        mb.begin();
        mb.part("terrain", field.mesh, GL20.GL_TRIANGLES, material);
        this.modelInstance = new ModelInstance(mb.end());

        int vertices = 0;
        for (Mesh mesh : modelInstance.model.meshes) {
            vertices += mesh.getNumVertices();
        }
        System.out.println(vertices);
    }

    public HeightField getField() {
        return this.field;
    }

    @Override
    public float getHeightAt(float x, float y) {
        float dx = (x - field.corner00.x) / (field.corner10.x - field.corner00.x) * (field.width - 1);
        float dy = (y - field.corner00.z) / (field.corner01.z - field.corner00.z) * (field.height - 1);

        int x0 = Math.max(0, Math.min((int) Math.floor(dx), field.width - 2));
        int y0 = Math.max(0, Math.min((int) Math.floor(dy), field.height - 2));

        float sx = dx - x0;
        float sy = dy - y0;

        float h00 = field.data[y0 * width + x0];
        float h10 = field.data[y0 * width + x0 + 1];
        float h01 = field.data[(y0 + 1) * width + x0];
        float h11 = field.data[(y0 + 1) * width + x0 + 1];

        float h0 = h00 + sx * (h10 - h00);
        float h1 = h01 + sx * (h11 - h01);
        float height = h0 + sy * (h1 - h0);

        height *= field.magnitude.len();

        return height;
    }

    @Override
    public void dispose() {
        field.dispose();
    }
}
