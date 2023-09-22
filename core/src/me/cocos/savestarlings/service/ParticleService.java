package me.cocos.savestarlings.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.utils.Array;

public class ParticleService {

    private final Array<ParticleController> emmiters;
    private final ParticleSystem particleSystem;

    public ParticleService(Camera camera) {
        this.emmiters = new Array<>();
        this.particleSystem = new ParticleSystem();
        BillboardParticleBatch pointSpriteBatch = new BillboardParticleBatch();
        pointSpriteBatch.setUseGpu(true);
        System.out.println(camera == null);
        pointSpriteBatch.setCamera(camera);
        particleSystem.add(pointSpriteBatch);
    }

    public void playParticle(String path) {
        ParticleEffect originalEffect = AssetService.getAsset("particles/laser_smoke");
        ParticleEffect effect = originalEffect.copy();
        effect.init();
        effect.start();
        particleSystem.add(effect);
    }

    public void render(ModelBatch modelBatch, Camera camera) {
        particleSystem.update();
        particleSystem.begin();
        particleSystem.draw();
        particleSystem.end();
        modelBatch.begin(camera);
        modelBatch.render(particleSystem);
        modelBatch.end();
    }
}
