package me.cocos.savestarlings.particle;

import me.cocos.savestarlings.particle.impl.ExplosionParticle;

import java.util.function.Supplier;

public enum Particle {

    EXPLOSION(ExplosionParticle::new);

    private final Supplier<IParticle> particleSupplier;

    Particle(Supplier<IParticle> particleSupplier) {
        this.particleSupplier = particleSupplier;
    }

    public Supplier<IParticle> getParticleSupplier() {
        return this.particleSupplier;
    }
}
