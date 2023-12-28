package me.cocos.savestarlings.service;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import me.cocos.savestarlings.particle.IParticle;
import me.cocos.savestarlings.particle.Particle;
import me.cocos.savestarlings.particle.impl.ExplosionParticle;

public class ParticleService {

    private final Array<ParticleController> emitters;

    private final BillboardParticleBatch billboardParticleBatch;


    public ParticleService(Camera camera) {
        this.emitters = new Array<>();
        this.billboardParticleBatch = new BillboardParticleBatch();
        billboardParticleBatch.setUseGpu(true);
        billboardParticleBatch.setCamera(camera);
        billboardParticleBatch.setTexture(AssetService.getAsset("particles/explosion_2.png", Texture.class));
    }

    public void playParticle(Particle particle, Vector3 position) {
        ParticleController controller = particle.getParticleSupplier().get().getController(billboardParticleBatch);
        controller.init();
        controller.start();
        emitters.add(controller);
        controller.translate(position);
    }

    public void render(ModelBatch modelBatch) {
        if (emitters.size > 0) {
            billboardParticleBatch.begin();
            for (ParticleController controller : emitters) {
                if (controller.isComplete()) {
                    emitters.removeValue(controller, false);
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
