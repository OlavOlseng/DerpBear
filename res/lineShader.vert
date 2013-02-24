#version 330
in vec2 coord2d;
in vec3 color3d;

uniform mat4 mvp;
uniform float depth;
out vec3 f_color;
void main(){

	f_color =color3d;
	gl_Position = mvp*vec4(coord2d.xy,0.0,1.0);
	gl_position.z = depth;

}