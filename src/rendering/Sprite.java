package rendering;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL13.*;

import org.newdawn.slick.opengl.Texture;

public class Sprite extends Node {
	Shader shader;
	Texture texture;
	
	private Buffer vertexBuffer;
	private Buffer texCoordBuffer;
	private FloatBuffer mvpBuffer;
	private Matrix4f mvp;
	private int height;
	private int width;
	public Sprite(Texture texture){
		
		height =2;
		width = 2;
		mvp = new Matrix4f();
		mvpBuffer = BufferUtils.createFloatBuffer(16);
		
		
		shader = ProgramManager.getShader("sprite2DShader");
		shader.bindAttribute(Attribute.COORD2D);
		shader.bindAttribute(Attribute.TEXCOOR2D);
		shader.bindUniform(Uniform.MVP);
		this.texture = texture;
		texture.bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		
		FloatBuffer vertices = BufferUtils.createFloatBuffer(12);
		vertices.put(-1).put(-1);
		vertices.put(1).put(-1);
		vertices.put(-1).put(1);
		vertices.put(-1).put(1);
		vertices.put(1).put(-1);
		vertices.put(1).put(1);
		
		FloatBuffer texCoords = BufferUtils.createFloatBuffer(12);
		texCoords.put(0).put(0);
		texCoords.put(1).put(0);
		texCoords.put(0).put(1);
		texCoords.put(0).put(1);
		texCoords.put(1).put(0);
		texCoords.put(1).put(1);
		
		
		vertices.flip();
		texCoords.flip();
		
		vertexBuffer = new Buffer(GL_ARRAY_BUFFER, GL_STATIC_DRAW, GL_FLOAT, 2);
		texCoordBuffer = new Buffer(GL_ARRAY_BUFFER, GL_STATIC_DRAW, GL_FLOAT, 2);
		vertexBuffer.setData(vertices);
		texCoordBuffer.setData(texCoords);
		
		vertexBuffer.bind();
		int size = glGetBufferParameteri(GL_ARRAY_BUFFER, GL_BUFFER_SIZE);
		System.out.println("size:" + size);
		texCoordBuffer.bind();
		size = glGetBufferParameteri(GL_ARRAY_BUFFER, GL_BUFFER_SIZE);
		System.out.println("size:" + size);
	}
	
	
	public void setWidth(float width){
		
		setScaleX(width/(float)this.width);
		
	}
	public void setHeight(float height){
		setScaleY(height/(float)this.height);
	}
	@Override
	public void render(Pipeline pipeline){
		mvpBuffer.clear();		
		shader.bind();
		vertexBuffer.bindTo(shader.getAttribute(Attribute.COORD2D));
		texCoordBuffer.bindTo(shader.getAttribute(Attribute.TEXCOOR2D));
		
		Matrix4f projection = pipeline.getProjectionMatrix();
		Matrix4f modelMatrix = getModelMatrix();
		Matrix4f test = new Matrix4f();
		Matrix4f.setIdentity(test);
		Matrix4f.mul(projection, modelMatrix, mvp);
		mvp.store(mvpBuffer);
		
		mvpBuffer.flip();
		
		glUniformMatrix4(shader.getUniform(Uniform.MVP), false,mvpBuffer);
		glActiveTexture(GL_TEXTURE0);
		texture.bind();
		glDrawArrays(GL_TRIANGLES, 0, 12);
		
		
		
	}
}
