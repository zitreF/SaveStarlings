varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform float u_amount;
uniform float u_speed;
uniform float u_time;

void main() {
    vec2 uv = v_texCoords;

    float randomX = sin((uv.y * 10.0 + u_time) * 0.1) * 0.02;
    float randomY = cos((uv.x * 10.0 + u_time) * 0.1) * 0.02;

    uv.x += randomX + sin((uv.y + (u_time * 0.07 * u_speed)) * 15.0) * 0.0029 * u_amount;
    uv.y += randomY + sin((uv.x + (u_time * 0.1 * u_speed)) * 15.0) * 0.002 * u_amount;

    vec4 tex_color = texture2D(u_texture, uv);

    gl_FragColor = tex_color;
}
