package me.cocos.savestarlings.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import me.cocos.savestarlings.entity.Entity;
import me.cocos.savestarlings.entity.building.Building;
import me.cocos.savestarlings.entity.building.base.StarBase;
import me.cocos.savestarlings.entity.building.other.Laboratory;
import me.cocos.savestarlings.entity.building.tower.impl.SniperTower;
import me.cocos.savestarlings.map.Chunk;
import me.cocos.savestarlings.util.SoundUtil;

import java.util.concurrent.CompletableFuture;

public class EntityService {

    private final Array<Entity> entites;
    private final Array<Building> buildings;
    private final EnvironmentService environmentService;

    public EntityService(EnvironmentService environmentService) {
        this.entites = new Array<>();
        this.buildings = new Array<>();
        this.environmentService = environmentService;
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
        environmentService.addScene(building.getScene());
    }

    public void removeBuilding(Building building) {
        this.buildings.removeValue(building, false);
        environmentService.removeScene(building.getScene());
    }

    public Array<Building> getBuildings() {
        return this.buildings;
    }

    public void update(float delta) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.MIDDLE)) {

            ChunkService chunkService = environmentService.getChunkService();

            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();

            Ray ray = this.environmentService.getSceneService().camera.getPickRay(mouseX, mouseY);

            Vector3 intersection = new Vector3();
            boolean hit = Intersector.intersectRayPlane(ray, new Plane(Vector3.Y, 0), intersection);

            if (hit) {
                Building building = new Laboratory(intersection);

                for (Chunk chunk : chunkService.getChunksIntersectingWithBuilding(building)) {
                    chunk.addEntity(building);
                }
                this.addBuilding(building);
            }
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {

            ChunkService chunkService = environmentService.getChunkService();

            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();

            Ray ray = this.environmentService.getSceneService().camera.getPickRay(mouseX, mouseY);

            Vector3 intersection = new Vector3();
            boolean hit = Intersector.intersectRayPlane(ray, new Plane(Vector3.Y, 0), intersection);

            if (hit) {
                Building building = new StarBase(intersection);

                for (Chunk chunk : chunkService.getChunksIntersectingWithBuilding(building)) {
                    chunk.addEntity(building);
                }
                this.addBuilding(building);
            }
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            CompletableFuture.runAsync(() -> {
                ChunkService chunkService = environmentService.getChunkService();

                int mouseX = Gdx.input.getX();
                int mouseY = Gdx.input.getY();

                Ray ray = this.environmentService.getSceneService().camera.getPickRay(mouseX, mouseY);

                Vector3 intersection = new Vector3();
                boolean hit = Intersector.intersectRayPlane(ray, new Plane(Vector3.Y, 0), intersection);

                if (hit) {
                    Building building = new SniperTower(intersection);

                    for (Chunk chunk : chunkService.getChunksIntersectingWithBuilding(building)) {
                        chunk.addEntity(building);
                    }
                    SoundUtil.play("build.mp3");
                    this.addBuilding(building);
                }
            });
        }
        for (Building building : this.buildings) {
            building.update(delta);
        }
    }
}
