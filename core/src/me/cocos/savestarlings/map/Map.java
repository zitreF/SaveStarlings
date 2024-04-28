package me.cocos.savestarlings.map;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import me.cocos.savestarlings.entity.building.base.StarBase;
import me.cocos.savestarlings.entity.environment.EnvironmentType;
import me.cocos.savestarlings.entity.livingentitiy.starling.Citizen;
import me.cocos.savestarlings.map.generator.MapGenerator;
import me.cocos.savestarlings.map.noise.PerlinNoise;
import me.cocos.savestarlings.scene.SceneService;
import me.cocos.savestarlings.service.EntityService;
import me.cocos.savestarlings.service.EnvironmentService;
import me.cocos.savestarlings.service.GameService;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.scene.Scene;

public class Map {

    private static Map instance;
    private final EntityService entityService;
    private final MapGenerator mapGenerator;

    public Map(EntityService entityService) {
        instance = this;
        this.entityService = entityService;
        this.mapGenerator = new MapGenerator();
    }

    public void generate() {
        this.mapGenerator.createTerrain();
        StarBase starBase = new StarBase(new Vector3(0f, 0f, 0f));
        this.entityService.addBuilding(starBase);
        this.populate(15);
        this.plants(50);
    }

    private void populate(int amount) {
        for (int i = 0; i < amount; i++) {
            Vector3 position = new Vector3();
            position.x = MathUtils.random(-50f, 50f);
            position.y = 0f;
            position.z = MathUtils.random(-50f, 50f);
            Citizen citizen = new Citizen(position);
            this.entityService.addEntity(citizen);
        }
    }

    private void plants(int amount) {
        for (int i = 0; i < amount; i++) {
            Vector3 position = new Vector3();
            position.x = MathUtils.random(-50f, 50f);
            position.y = 0f;
            position.z = MathUtils.random(-50f, 50f);
            EnvironmentType randomType = EnvironmentType.values()[MathUtils.random(EnvironmentType.values().length - 1)];
            this.entityService.addEnvironment(randomType.getEnvironmentResult().result(position));
        }
    }

    public MapGenerator getMapGenerator() {
        return this.mapGenerator;
    }

    public static Map getInstance() {
        return instance;
    }
}
