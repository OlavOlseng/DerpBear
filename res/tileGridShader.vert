#version 330

in vec3 coord3d;

uniform mat4 mvp;
uniform float depth;

out vec3 f_texcoord;
void main()
{

	f_texcoord = coord3d;
	gl_Position    = mvp*vec4(coord3d.xy,0.0,1.0);
	gl_Position.z = depth;
	
}
