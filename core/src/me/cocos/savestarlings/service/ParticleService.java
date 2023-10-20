package me.cocos.savestarlings.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.badlogic.gdx.graphics.g3d.particles.influencers.*;
import com.badlogic.gdx.graphics.g3d.particles.renderers.BillboardRenderer;
import com.badlogic.gdx.graphics.g3d.particles.values.PointSpawnShapeValue;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class ParticleService {

    private final Array<ParticleController> emitters;

    private final BillboardParticleBatch billboardParticleBatch;

    private final ParticleSystem particleSystem;

    public ParticleService(Camera camera) {
        this.emitters = new Array<>();
        this.particleSystem = new ParticleSystem();
        this.billboardParticleBatch = new BillboardParticleBatch();
        billboardParticleBatch.setUseGpu(true);
        billboardParticleBatch.setCamera(camera);

        Texture particleTexture = AssetService.getAsset("pp.png");
        billboardParticleBatch.setTexture(AssetService.getAsset("pp.png", Texture.class));

        Vector3 tmpVector = new Vector3();

        //X
        addEmitter(new float[] {1, 0.12156863f, 0.047058824f}, particleTexture,
                tmpVector.set(5,5,0), Vector3.X, 360);

        //Y
        addEmitter(new float[] {0.12156863f, 1, 0.047058824f}, particleTexture,
                tmpVector.set(0,5,-5), Vector3.Y, -360);

        //Z
        addEmitter(new float[] {0.12156863f, 0.047058824f, 1}, particleTexture,
                tmpVector.set(0,5,5), Vector3.Z, -360);

    }

    private void addEmitter(float[] colors, Texture particleTexture,
                                Vector3 translation,
                                Vector3 actionAxis, float actionRotation){
        ParticleController controller = createBillboardController(colors, particleTexture);
        controller.init();
        controller.start();
        emitters.add(controller);
        controller.translate(translation);
    }

    private ParticleController createBillboardController (float[] colors, Texture particleTexture) {
        //Emission
        RegularEmitter emitter = new RegularEmitter();
        emitter.getDuration().setLow(3000);
        emitter.getEmission().setHigh(2900);
        emitter.getLife().setHigh(1000);
        emitter.setMaxParticleCount(3000);
        emitter.durationValue.setLow(3000, 5000);
        emitter.setContinuous(false);
        //Spawn
        PointSpawnShapeValue pointSpawnShapeValue = new PointSpawnShapeValue();
        pointSpawnShapeValue.xOffsetValue.setLow(0, 1f);
        pointSpawnShapeValue.xOffsetValue.setActive(true);
        pointSpawnShapeValue.yOffsetValue.setLow(0, 1f);
        pointSpawnShapeValue.yOffsetValue.setActive(true);
        pointSpawnShapeValue.zOffsetValue.setLow(0, 1f);
        pointSpawnShapeValue.zOffsetValue.setActive(true);
        SpawnInfluencer spawnSource = new SpawnInfluencer(pointSpawnShapeValue);

        //Scale
        ScaleInfluencer scaleInfluencer = new ScaleInfluencer();
        scaleInfluencer.value.setTimeline(new float[]{0, 1});
        scaleInfluencer.value.setScaling(new float[]{1, 0});
        scaleInfluencer.value.setLow(0);
        scaleInfluencer.value.setHigh(1);

        //Color
        ColorInfluencer.Single colorInfluencer = new ColorInfluencer.Single();
        colorInfluencer.colorValue.setColors(new float[] {colors[0], colors[1], colors[2], 0,0,0});
        colorInfluencer.colorValue.setTimeline(new float[] {0, 1});
        colorInfluencer.alphaValue.setHigh(1);
        colorInfluencer.alphaValue.setTimeline(new float[] {0, 0.5f, 0.8f, 1});
        colorInfluencer.alphaValue.setScaling(new float[] {0, 0.15f, 0.5f, 0});

        //Dynamics
        DynamicsInfluencer dynamicsInfluencer = new DynamicsInfluencer();
        DynamicsModifier.BrownianAcceleration modifier = new DynamicsModifier.BrownianAcceleration();
        modifier.strengthValue.setTimeline(new float[]{0,1});
        modifier.strengthValue.setScaling(new float[]{0,1});
        modifier.strengthValue.setHigh(80);
        modifier.strengthValue.setLow(1, 5);
        dynamicsInfluencer.velocities.add(modifier);

        return new ParticleController("Billboard Controller", emitter, new BillboardRenderer(billboardParticleBatch),
                new RegionInfluencer.Single(particleTexture),
                spawnSource,
                scaleInfluencer,
                colorInfluencer,
                dynamicsInfluencer
        );
    }

    public void render(ModelBatch modelBatch) {
        if (emitters.size > 0) {
            billboardParticleBatch.begin();
            System.out.println(emitters.size);
            for (ParticleController controller : emitters) {
                if (controller.isComplete()) {
                    controller.particles.clear();
                    controller.dispose();
                    continue;
                }
                controller.update();
                controller.draw();
            }
            billboardParticleBatch.end();
            modelBatch.render(billboardParticleBatch);
        }
    }
}
