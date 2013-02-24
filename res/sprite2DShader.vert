#version 330
in vec3 coord2d;
in vec2 texcoord2d;
uniform mat4 mvp;

out vec2 f_texcoord;
void main()
{

	f_texcoord = texcoord2d;
	gl_Position    = mvp*vec4(coord2d.xy,0.0,1.0);
	
}
