#version 330

in vec3 f_texcoord;
uniform sampler2D tex;
uniform float stepSizeX;
uniform float stepSizeY;
uniform float tilesY;
void main()
{

	vec2 texCoord = vec2((fract(f_texcoord.x) + f_texcoord.z)*stepSizeX,-(fract(f_texcoord.y) + floor(f_texcoord.z/tilesY))*stepSizeY);
	
	gl_FragColor = texture2D(tex, texCoord);
	
	
	
}
