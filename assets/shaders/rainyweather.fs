// Fragment Shader (shaders/rainyweather.fs)
#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform float u_time;
uniform sampler2D u_texture; // Assuming you're using a texture, adjust as needed

void main() {
    // Add raindrop effect based on texture coordinates and time
    float rainIntensity = 0.05; // Adjust the intensity of the raindrops

    // Add distortion based on time and texture coordinates
    vec2 distortion = vec2(sin(u_time * 0.1), cos(u_time * 0.07));
    vec2 distortedCoords = v_texCoords + distortion * rainIntensity;

    // Add splash effect
    float splashIntensity = 0.02; // Adjust the intensity of the splash effect
    vec2 splashCoords = v_texCoords + vec2(0.0, sin(u_time * 0.3)) * splashIntensity;

    // Mix the distorted and splash coordinates
    vec4 distortedColor = texture2D(u_texture, distortedCoords);
    vec4 splashColor = texture2D(u_texture, splashCoords);
    vec4 finalColor = mix(distortedColor, splashColor, 0.5);

    gl_FragColor = v_color * finalColor;
}
