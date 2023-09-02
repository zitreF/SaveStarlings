attribute vec4 a_position;
uniform mat4 u_projTrans;
uniform mat4 u_modelViewMatrix;
varying vec4 v_position;
varying vec4 v_lightSpacePos;

void main() {
    v_position = u_modelViewMatrix * a_position;
    v_lightSpacePos = u_projTrans * v_position;
    gl_Position = u_projTrans * a_position;
}
