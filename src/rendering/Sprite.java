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
	private Shader shader;
	private Texture texture;
	
	private Buffer vertexBuffer;
	private Buffer texCoordBuffer;
	private FloatBuffer mvpBuffer;
	private Matrix4f mvp;
	
	public Sprite(Texture texture){
		
		setWidth(2);
		setHeight(2);
		
		mvp = new Matrix4f();
		mvpBuffer = BufferUtils.createFloatBuffer(16);
		
		
		shader = ProgramManager.getShader("sprite2DShader");
		shader.bindAttribute(Attribute.COORD2D);
		shader.bindAttribute(Attribute.TEXCOOR2D);
		shader.bindUniform(Uniform.MVP);
		shader.bindUniform(Uniform.DEPTH);
		this.texture = texture;
		texture.bind();
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
	}
	
	
	private Matrix4f tempModelMatrix = new Matrix4f();
	@Override
	public void render(Pipeline pipeline){
		mvpBuffer.clear();		
		shader.bind();
		vertexBuffer.bindTo(shader.getAttribute(Attribute.COORD2D));
		texCoordBuffer.bindTo(shader.getAttribute(Attribute.TEXCOOR2D));
		
		
		Matrix4f modelMatrix = getModelMatrix();
		Matrix4f.load(modelMatrix, tempModelMatrix);
		Matrix4f.mul(pipeline.getWorldTransForm(), modelMatrix, modelMatrix);
		Matrix4f.mul(pipeline.getViewXProjectionMatrix(), modelMatrix, mvp);
		mvp.store(mvpBuffer);
		
		mvpBuffer.flip();
		glUniform1f(shader.getUniform(Uniform.DEPTH), getDepth());
		glUniformMatrix4(shader.getUniform(Uniform.MVP), false,mvpBuffer);
		glActiveTexture(GL_TEXTURE0);
		texture.bind();
		glDrawArrays(GL_TRIANGLES, 0, 12);
		Matrix4f.load(tempModelMatrix, modelMatrix);
		
		
		
	}
}
