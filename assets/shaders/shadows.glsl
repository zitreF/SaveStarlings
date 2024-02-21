#ifdef shadowMapFlag
uniform float u_shadowBias;
uniform sampler2D u_shadowTexture;
uniform float u_shadowPCFOffset;
varying vec3 v_shadowMapUv;

#ifdef numCSM

uniform sampler2D u_csmSamplers[numCSM];
uniform vec2 u_csmPCFClip[numCSM];
varying vec3 v_csmUVs[numCSM];

#else

float getShadowness(vec2 offset)
{
    const vec4 bitShifts = vec4(1.0, 1.0 / 255.0, 1.0 / 65025.0, 1.0 / 16581375.0);
    return step(v_shadowMapUv.z, dot(texture2D(u_shadowTexture, v_shadowMapUv.xy + offset), bitShifts) + u_shadowBias); // (1.0/255.0)
}

float getShadow()
{
    float totalShadowness = 1.0;
    int numSamples = 8;

    for (int i = 0; i < numSamples; i++) {
        float angle = float(i) * 6.283185 / float(numSamples);
        vec2 offset = u_shadowPCFOffset * 5.0 * vec2(cos(angle), sin(angle));
        totalShadowness += getShadowness(offset);
    }

    return totalShadowness / float(numSamples);
}

#endif

#endif
