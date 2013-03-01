#version 330

in vec2 f_texcoord;
uniform sampler2D tex;
void main()
{
	
	vec4 color = texture2D(tex,f_texcoord.xy);
	if(color.x == 1.0 && color.y == 1.0 && color.z == 1.0)
		discard;
	gl_FragColor = texture2D(tex,f_texcoord.xy);
}
