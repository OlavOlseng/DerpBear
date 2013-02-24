package rendering;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Scanner;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;


import static org.lwjgl.opengl.GL20.*;

public class Shader {
	private int program;
	private int[] attributes;
	private int[] uniforms;
	public Shader(String fileName){
		
		String vsSource = readFile(fileName + ".vert");
		String fsSource = readFile(fileName + ".frag");
		IntBuffer shaders = BufferUtils.createIntBuffer(2);
		program = glCreateProgram();
		if(!attachShader(program, vsSource, GL_VERTEX_SHADER))
			System.out.println("Error on compiling vertex shader");
		glGetAttachedShaders(program, BufferUtils.createIntBuffer(1), shaders);
		System.out.println("Vertex shader log:\n" + getShaderLog(shaders.get(0)));
		
		if(!attachShader(program, fsSource, GL_FRAGMENT_SHADER))
			System.out.println("Error on compiling fragment shader");
		glGetAttachedShaders(program, BufferUtils.createIntBuffer(1), shaders);
		System.out.println("Fragment shader log:\n" + getShaderLog(shaders.get(1)));
		glLinkProgram(program);	
		
		attributes = new int[Attribute.values().length];
		uniforms = new int[Uniform.values().length];
		
	}
	
	public void bindAttribute(Attribute attribute){
		int attrib = glGetAttribLocation(program, attribute.toString());
		if(attrib == -1)
			throw new RuntimeException("No such attribute: " + attribute);
		attributes[attribute.ordinal()] = attrib;
	}
	public void bindUniform(Uniform uniform){
		int uni = glGetUniformLocation(program, uniform.toString());
		if(uni == -1)
			throw new RuntimeException("No such uniform: " + uniform);
		uniforms[uniform.ordinal()] = uni;
		
	}
	public void bind(){
		
		glUseProgram(program);
	}
	
	public int getProgramHandle(){
		return program;
	}
	public int getUniform(Uniform uniform){
		return uniforms[uniform.ordinal()];
	}
	public int getAttribute(Attribute attribute){
		return attributes[attribute.ordinal()];
	}
	private String getShaderLog(int shader){
		String log = glGetShaderInfoLog(shader, 512);
		return log;
		
	}
	private boolean attachShader(int program,String shaderSource,int type){
		int shader = glCreateShader(type);
		glShaderSource(shader, shaderSource);
		glCompileShader(shader);
		int status = glGetShaderi(shader, GL_COMPILE_STATUS);
		glAttachShader(program, shader);
		
		return status == GL_TRUE;
	}
	private String readFile(String fileName){
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			return "";
		}
		String line;
		try {
			while(( line = reader.readLine()) != null){
					builder.append(line);
					builder.append("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return builder.toString();
	}
}
