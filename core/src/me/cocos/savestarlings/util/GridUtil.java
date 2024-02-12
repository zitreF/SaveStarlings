package me.cocos.savestarlings.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import net.mgsx.gltf.scene3d.scene.Scene;

public class GridUtil {

    public static final float GRID_STEP = 2.5f;

    public static Scene createGrid(float min, float max, Vector2 position) {
        Gdx.gl.glLineWidth(2f);
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder builder = modelBuilder.part("grid",
                GL32.GL_LINES,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
                new Material());

        float gridMin = min - GRID_STEP;
        float gridMax = max + GRID_STEP;

        Color color = new Color(1f, 1f, 1f, 1f);

        for (float x = gridMin; x <= gridMax; x += GRID_STEP) {
            for (float z = gridMin; z <= gridMax; z += GRID_STEP) {
                builder.setColor(color);

                builder.line(x, 0, z, Math.min(x + GRID_STEP, gridMax), 0, z);
                builder.line(x, 0, z, x, 0, Math.min(z + GRID_STEP, gridMax));
            }
        }
        Model axesModel = modelBuilder.end();
        ModelInstance axesInstance = new ModelInstance(axesModel);

        axesInstance.transform.setToTranslation(position.x, 0.1f, position.y);

        return new Scene(axesInstance);
    }
}
