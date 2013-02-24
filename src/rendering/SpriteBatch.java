package rendering;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.newdawn.slick.opengl.Texture;

public class SpriteBatch {
	private Shader shader;
	private Texture texture;
	private ArrayList<Node> sprites;
	private Buffer vertexBuffer;
	private Buffer texCoordBuffer;
	private FloatBuffer mvpBuffer;
	private Matrix4f mvp;
	public SpriteBatch(Texture texture,int initalCapacity){
		sprites = new ArrayList<Node>(initalCapacity);
		
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
	public void addSprite(Node node){
		
		sprites.add(node);
	}
	
	public void render(Pipeline pipeline){
				
		shader.bind();
		
		glActiveTexture(GL_TEXTURE0);
		texture.bind();
		vertexBuffer.bindTo(shader.getAttribute(Attribute.COORD2D));
		texCoordBuffer.bindTo(shader.getAttribute(Attribute.TEXCOOR2D));
		
		for (Node  sprite :sprites){
			mvpBuffer.clear();
			Matrix4f projection = pipeline.getProjectionMatrix();
			Matrix4f modelMatrix = sprite.getModelMatrix();
			Matrix4f.mul(projection, modelMatrix, mvp);
			mvp.store(mvpBuffer);
			mvpBuffer.flip();
			glUniform1f(shader.getUniform(Uniform.DEPTH), sprite.getDepth());
			glUniformMatrix4(shader.getUniform(Uniform.MVP), false,mvpBuffer);
			glDrawArrays(GL_TRIANGLES, 0, 12);
		}
		
		
		
		
		
	}
	
	
}
