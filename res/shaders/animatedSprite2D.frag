#version 330

in vec2 f_texcoord;
uniform sampler2D tex;
uniform float offset;
void main()
{
	
	vec4 color = texture2D(tex,vec2(f_texcoord.x + offset, f_texcoord.y));
	if(color.x == 1.0 && color.y == 1.0 && color.z == 1.0)
		discard;
	gl_FragColor = color;
}
