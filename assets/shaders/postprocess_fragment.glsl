#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_position;
varying vec4 v_lightSpacePos;
uniform sampler2D u_shadowMap;  // Texture containing the depth values from the light's perspective
uniform vec3 u_lightPosition;   // Position of the light source

void main() {
    // Convert the position to shadow map coordinates
    vec3 projCoords = v_lightSpacePos.xyz / v_lightSpacePos.w;
    projCoords = projCoords * 0.5 + 0.5; // Map to [0,1] range

    float closestDepth = texture2D(u_shadowMap, projCoords.xy).r;
    float currentDepth = projCoords.z;

    // Compare the depth of the current fragment with the depth in the shadow map
    float shadow = currentDepth > closestDepth ? 0.0 : 1.0;

    // You can use 'shadow' to adjust the fragment's color or lighting
    // For example, you can darken the fragment if it's in shadow:
    // vec3 finalColor = texture2D(u_texture, v_texCoord0).rgb;
    // finalColor *= shadow;

    gl_FragColor = vec4(vec3(shadow), 1.0);
}
