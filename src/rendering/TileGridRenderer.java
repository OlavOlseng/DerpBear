package rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;

import static org.lwjgl.opengl.GL15.*;


import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.newdawn.slick.opengl.Texture;

public class TileGridRenderer extends Node {

	private TileAtlas tileAtlas;
	private TileGrid grid;
	private Shader shader;
	private Buffer vertexBuffer;
	private Buffer texCoordBuffer;
	private FloatBuffer mvpBuffer;
	private Matrix4f mvp;
	private int[] vertices;
	private IntBuffer internalVertexBuffer;
	
	
	public TileGridRenderer(TileAtlas tileAtlas, TileGrid grid){
		this.tileAtlas = tileAtlas;
		this.grid = grid;
		
		tileAtlas.bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		
		mvp = new Matrix4f();
		mvpBuffer = BufferUtils.createFloatBuffer(16);
		shader = ResourceManager.getShader("tileGridShader");
		
		shader.bindUniform(Uniform.MVP);
		shader.bindUniform(Uniform.DEPTH);
		shader.bindUniform(Uniform.STEP_SIZE_X);
		shader.bindUniform(Uniform.STEP_SIZE_Y);
		shader.bindUniform(Uniform.TILES_Y);
		shader.bindAttribute(Attribute.COORD3D);
		
		internalVertexBuffer = BufferUtils.createIntBuffer(grid.getNumTiles()*3*6);
		vertices = new int[grid.getNumTiles()*3*6];
		vertexBuffer = new Buffer(GL_ARRAY_BUFFER, GL_STATIC_DRAW, GL_INT, 3);
		updateGridMesh();
		this.width = grid.getNumTilesX();
		this.height = grid.getNumTilesY();
		
			
	}
	
	public void updateGridMesh(){
		int i = 0;
		internalVertexBuffer.clear();
		for(int x = 0; x<grid.getNumTilesX();x++){
			for(int y = 0; y<grid.getNumTilesY();y++){
				int type = grid.getTypeAt(x, y).getType();
				if(type == 0)
					continue;
				
				vertices[i++] = x;vertices[i++] = y;vertices[i++] = type-1;
				vertices[i++] = x+1;vertices[i++] = y;vertices[i++] = type-1;
				vertices[i++] = x;vertices[i++] = y+1;vertices[i++] = type-1;
				
				vertices[i++] = x;vertices[i++] = y+1;vertices[i++] = type-1;
				vertices[i++] = x+1;vertices[i++] = y;vertices[i++] = type-1;
				vertices[i++] = x+1;vertices[i++] = y+1;vertices[i++] = type-1;
				
				
				
			}
		}
		
		
		internalVertexBuffer.put(vertices);
		internalVertexBuffer.flip();
		vertexBuffer.setData(internalVertexBuffer);
		
		
	}
	
	public void render(Pipeline pipeline){
		
		shader.bind();
		
		glActiveTexture(GL_TEXTURE0);
		tileAtlas.bind();
		
		vertexBuffer.bindTo(shader.getAttribute(Attribute.COORD3D));	
		
		mvpBuffer.clear();
		Matrix4f projection = pipeline.getProjectionMatrix();
		Matrix4f modelMatrix = getModelMatrix();
		Matrix4f.mul(projection, modelMatrix, mvp);
		mvp.store(mvpBuffer);
		mvpBuffer.flip();
		glUniform1f(shader.getUniform(Uniform.DEPTH), getDepth());
		glUniformMatrix4(shader.getUniform(Uniform.MVP), false,mvpBuffer);
		glUniform1f(shader.getUniform(Uniform.STEP_SIZE_X), 1.0f/tileAtlas.getTilesX());
		glUniform1f(shader.getUniform(Uniform.STEP_SIZE_Y), 1.0f/tileAtlas.getTilesY());
		glUniform1f(shader.getUniform(Uniform.TILES_Y), tileAtlas.getTilesY());
		glDrawArrays(GL_TRIANGLES, 0, vertexBuffer.getSize());

		
		
		
		
		
		
	}
	
	
}
