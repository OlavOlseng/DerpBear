#version 330

in vec3 f_texcoord;
uniform sampler2D tex;
uniform float stepSizeX;
uniform float stepSizeY;
void main()
{
	
	gl_FragColor = texture2D(tex,vec2((fract(f_texcoord.x)  + f_texcoord.z)*stepSizeX ,(fract(f_texcoord.y) + f_texcoord.z))*stepSizeY);
}
