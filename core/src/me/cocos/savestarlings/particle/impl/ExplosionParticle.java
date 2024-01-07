package me.cocos.savestarlings.particle.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.badlogic.gdx.graphics.g3d.particles.influencers.*;
import com.badlogic.gdx.graphics.g3d.particles.renderers.BillboardRenderer;
import com.badlogic.gdx.graphics.g3d.particles.values.PointSpawnShapeValue;
import me.cocos.savestarlings.particle.IParticle;
import me.cocos.savestarlings.asset.AssetService;

public class ExplosionParticle implements IParticle {

    private final RegularEmitter emitter;
    private final PointSpawnShapeValue pointSpawnShapeValue;
    private final SpawnInfluencer spawnSource;
    private final ScaleInfluencer scaleInfluencer;
    private final ColorInfluencer.Single colorInfluencer;

    private static final Texture TEXTURE = AssetService.getAsset("particles/explosion_2.png");

    public ExplosionParticle() {
        this.emitter = new RegularEmitter();
        emitter.getEmission().setHigh(2000);
        emitter.getLife().setHigh(1000);
        emitter.setMaxParticleCount(50);
        emitter.setMinParticleCount(30);
        emitter.durationValue.setLow(1000, 1000);
        emitter.setContinuous(false);
        //Spawn
        this.pointSpawnShapeValue = new PointSpawnShapeValue();
        pointSpawnShapeValue.xOffsetValue.setLow(0, 1f);
        pointSpawnShapeValue.xOffsetValue.setActive(true);
        pointSpawnShapeValue.yOffsetValue.setLow(0, 1f);
        pointSpawnShapeValue.yOffsetValue.setActive(true);
        pointSpawnShapeValue.zOffsetValue.setLow(0, 1f);
        pointSpawnShapeValue.zOffsetValue.setActive(true);
        this.spawnSource = new SpawnInfluencer(pointSpawnShapeValue);

        //Scale
        this.scaleInfluencer = new ScaleInfluencer();
        scaleInfluencer.value.setTimeline(new float[]{0, 1});
        scaleInfluencer.value.setScaling(new float[]{1, 0});
        scaleInfluencer.value.setLow(1);
        scaleInfluencer.value.setHigh(2);

        //Color
        this.colorInfluencer = new ColorInfluencer.Single();
        colorInfluencer.colorValue.setColors(new float[] {
                0.81f, 0.21f, 0.21f,
                0.8f, 0.37f, 0.1f,
                0.92f, 0.73f, 0.1f,
                0f, 0f, 0f});
        colorInfluencer.colorValue.setTimeline(new float[] {0f, 0.4f, 0.2f, 1f});
        colorInfluencer.alphaValue.setHigh(1);
        colorInfluencer.alphaValue.setTimeline(new float[] {0, 0.5f, 0.8f, 1});
        colorInfluencer.alphaValue.setScaling(new float[] {0, 0.15f, 0.5f, 0});
    }

    @Override
    public ParticleController getController(BillboardParticleBatch batch) {
        return new ParticleController("Explosion Controller", emitter, new BillboardRenderer(batch),
                new RegionInfluencer.Single(TEXTURE),
                spawnSource,
                scaleInfluencer,
                colorInfluencer
        );
    }
}
