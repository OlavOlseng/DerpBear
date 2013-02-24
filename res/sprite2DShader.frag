#version 330

in vec2 f_texcoord;
uniform sampler2D tex;
void main()
{
	
	gl_FragColor = texture2D(tex,f_texcoord.xy);
}
