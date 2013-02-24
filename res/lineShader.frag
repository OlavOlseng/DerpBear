#version 330

in vec3 f_color;
void main(){

	gl_FragColor = vec4(f_color.xyz,1.0);

}