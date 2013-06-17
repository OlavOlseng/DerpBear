package rendering;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;

import static org.lwjgl.opengl.GL20.glUniformMatrix4;

import java.io.Serializable;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

import rendering.models.TextureModel;

public class Animation extends Node implements Serializable{
	private transient Shader shader;
	private Texture spriteSheet;
	
	private transient Buffer vertexBuffer;
	private transient Buffer texCoordBuffer;
	private transient FloatBuffer mvpBuffer;
	private transient Matrix4f mvp;
	private int frameCount;
	private int fps;
	private transient int frame;
	private transient long lastTime;
	private transient float stepSize;
	private int frameWidth;
	public Animation(Texture spriteSheet, int frameCount, int frameWidth, int fps){
		this.frameCount = frameCount;
		this.fps = fps;
		this.spriteSheet = spriteSheet;
		this.frameWidth = frameWidth;
		this.width = 2;
		this.height = 2;
	}
	
	
	private Matrix4f tempModelMatrix = new Matrix4f();
	@Override
	public void render(Pipeline pipeline){
		
		super.render(pipeline);
	
		if(System.currentTimeMillis() - lastTime > 1000.0f/fps){
			lastTime = System.currentTimeMillis();
			if(frame == frameCount-1){
				frame = 0;
			}else{
				frame++;
			}
			
		}
		
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
		
		glUniform1f(shader.getUniform(Uniform.OFFSET), stepSize * frame);
		glUniformMatrix4(shader.getUniform(Uniform.MVP), false,mvpBuffer);
		glActiveTexture(GL_TEXTURE0);
		spriteSheet.bind();
		glDrawArrays(GL_TRIANGLES, 0, 12);
		Matrix4f.load(tempModelMatrix, modelMatrix);
		
		
		
		
	}
	
	
	
	@Override
	public void init() {
		System.out.println("WIDHT:" + spriteSheet.getWidth());
		stepSize = (float)frameWidth/(float)spriteSheet.getWidth();
		
		System.out.println(stepSize);
		lastTime = System.currentTimeMillis();
		spriteSheet.bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		
		mvp = new Matrix4f();
		mvpBuffer = BufferUtils.createFloatBuffer(16);
		
		
		shader = ResourceManager.getShader("animatedSprite2D");
		shader.bindAttribute(Attribute.COORD2D);
		shader.bindAttribute(Attribute.TEXCOOR2D);
		shader.bindUniform(Uniform.MVP);
		shader.bindUniform(Uniform.DEPTH);
		shader.bindUniform(Uniform.OFFSET);
		
		spriteSheet.bind();
		FloatBuffer vertices = BufferUtils.createFloatBuffer(12);
		vertices.put(-1).put(-1);
		vertices.put(1).put(-1);
		vertices.put(-1).put(1);
		vertices.put(-1).put(1);
		vertices.put(1).put(-1);
		vertices.put(1).put(1);
		
		FloatBuffer texCoords = BufferUtils.createFloatBuffer(12);
		texCoords.put(0).put(0);
		texCoords.put(stepSize).put(0);
		texCoords.put(0).put(1.0f);
		texCoords.put(0).put(1.0f);
		texCoords.put(stepSize).put(0);
		texCoords.put(stepSize).put(1.0f);
		
	
		//this.setScale(2.0f, 2.0f);
		
		vertices.flip();
		texCoords.flip();
		
		for(int i = 0;i <12;i++){
			System.out.println(texCoords.get());
		}
		texCoords.flip();
		vertexBuffer = new Buffer(GL_ARRAY_BUFFER, GL_STATIC_DRAW, GL_FLOAT, 2);
		texCoordBuffer = new Buffer(GL_ARRAY_BUFFER, GL_STATIC_DRAW, GL_FLOAT, 2);
		vertexBuffer.setData(vertices);
		texCoordBuffer.setData(texCoords);
		
		for(Node child: children){
			child.init();
		}
		
	}
	

}
