package me.cocos.savestarlings.particle;

import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;

public interface IParticle {

    ParticleController getController(BillboardParticleBatch batch);
}
