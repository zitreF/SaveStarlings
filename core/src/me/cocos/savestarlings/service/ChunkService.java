package me.cocos.savestarlings.service;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import me.cocos.savestarlings.entity.building.Building;
import me.cocos.savestarlings.entity.building.tower.impl.SniperTower;
import me.cocos.savestarlings.map.Cell;
import me.cocos.savestarlings.map.Chunk;

import java.util.ArrayList;
import java.util.List;

public class ChunkService {
    private static final int CHUNK_SIZE = 20;
    private static final int CHUNKS_X = 10;
    private static final int CHUNKS_Y = 10;

    private final Array<Chunk> chunks;

    public ChunkService() {
        this.chunks = new Array<>();
        this.createChunks();
    }

    private void createChunks() {
        for (int chunkX = -4; chunkX < -4 + CHUNKS_X; chunkX++) {
            for (int chunkY = -4; chunkY < -4 + CHUNKS_Y; chunkY++) {
                Chunk chunk = new Chunk(chunkX * CHUNK_SIZE, chunkY * CHUNK_SIZE);
                chunks.add(chunk);
            }
        }
    }

    public Array<Chunk> getChunks() {
        return chunks;
    }

    public Chunk[] getChunksIntersectingWithBuilding(Building building) {
        List<Chunk> intersectingChunks = new ArrayList<>();

        for (Chunk chunk : chunks) {
            if (this.buildingIntersectsChunk(building, chunk)) {
                intersectingChunks.add(chunk);
            }
        }
        return intersectingChunks.toArray(Chunk[]::new);
    }

    private boolean buildingIntersectsChunk(Building building, Chunk chunk) {
        float buildingX = building.getPosition().x;
        float buildingZ = building.getPosition().z;
        float chunkX = chunk.getPosition().x;
        float chunkY = chunk.getPosition().y;

        return buildingX >= chunkX && buildingX < chunkX + CHUNK_SIZE &&
                buildingZ >= chunkY && buildingZ < chunkY + CHUNK_SIZE;
    }

}
