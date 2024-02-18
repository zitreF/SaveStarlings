package me.cocos.savestarlings.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2; // Import Vector2 class
import net.mgsx.gltf.scene3d.scene.Scene;

public class GridUtil {

    public static final float GRID_STEP = 2.5f;

    public static Scene createGrid(float min, float max, Vector2 position) {
        Gdx.gl.glLineWidth(2f);
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder builder = modelBuilder.part("grid",
                GL32.GL_LINES,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.ColorUnpacked, new Material(new BlendingAttribute()));

        float center = (max + min) / 2f;

        for (float x = min; x <= max; x += GRID_STEP) {
            for (float z = min; z <= max; z += GRID_STEP) {
                float distanceToCenter = Math.max(Math.abs(x - center), Math.abs(z - center));
                float alpha = 1f - Math.min(1f, distanceToCenter / (max / 2f));

                Color color = new Color(1f, 1f, 1f, alpha * 0.25f);
                builder.setColor(color);

                builder.line(x, 0, z, x + GRID_STEP, 0, z);
                builder.line(x, 0, z, x, 0, z + GRID_STEP);
            }
        }

        Model axesModel = modelBuilder.end();
        ModelInstance axesInstance = new ModelInstance(axesModel);

        axesInstance.transform.setToTranslation(position.x, 0f, position.y);

        return new Scene(axesInstance);
    }
}