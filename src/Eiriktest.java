import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.ResourceLoader;

import rendering.Attribute;
import rendering.LineDrawer;
import rendering.MatrixUtil;
import rendering.Node;
import rendering.Pipeline;
import rendering.ProgramManager;
import rendering.Shader;
import rendering.Sprite;
import rendering.SpriteBatch;
import rendering.Tile;
import rendering.TileAtlas;
import rendering.TileGrid;
import rendering.TileGridRenderer;
import rendering.TileType;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Eiriktest {

	
	public static void main(String[] args) throws LWJGLException, InterruptedException {
		
		int SCREEN_WIDTH = 1280;
		int SCREEN_HEIGHT = 720;
		Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
		Display.create(new PixelFormat());
		Matrix4f mat = new Matrix4f();
		Matrix4f.setIdentity(mat);
		Matrix4f.translate(new Vector3f(10.0f,10f,10.0f), mat, mat);
		Matrix4f test = new Matrix4f();
		Matrix4f.setIdentity(test);
		Vector4f translated = new Vector4f(0.0f,0.0f,0.0f,1.0f);
		
		
		
		
		
		glViewport(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		Shader shader = ProgramManager.getShader("sprite2DShader");
		Texture tex = null;
		try {
			tex = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("res/tileAtlas.png"),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Sprite sprite = new Sprite(tex);
		sprite.setWidth(100);
		sprite.setHeight(150);
		Pipeline pipeline = new Pipeline();
		pipeline.setProjectionMatrix(MatrixUtil.getOrthographicProjection(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT));
		
		
		
		sprite.setPosition(200, 200);
		Keyboard.create();
		
		SpriteBatch batch = new SpriteBatch(tex,5000);
		
		for (int i =0 ;i <100 ; i++){
			
			Node node = new Node();
			
			node.setPosition(i % 200, i % 200);
			node.setSize(2000, 2000);
			
			batch.addSprite(node);
			
		}
		
		
		Texture atlas = null;
		try {
			atlas = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("res/tileAtlas.png"),true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		TileAtlas tileAtlas = new TileAtlas(atlas, 2, 2);
		
		TileGrid grid =  new TileGrid(10, 10);
		
		for(int x = 0; x<10; x++){
			for (int y = 0; y<10;y++){
				grid.setTile(x, y, new Tile(TileType.GRASS));
			}
			
		}
		
		grid.setTile(5, 5, new Tile(TileType.NONE));
		grid.setTile(2, 2, new Tile(TileType.DIRT));
		grid.setTile(2, 3, new Tile(TileType.GRAVEL));
		grid.setTile(2, 4, new Tile(TileType.PLANKS));
		TileGridRenderer tileGridRenderer = new TileGridRenderer(tileAtlas, grid);
		tileGridRenderer.setSize(1000, 1000);
		tileGridRenderer.setPosition(0, 0);
		
		
		long lasTime = System.currentTimeMillis();
		long deltaTime;
		
		LineDrawer lineDrawer  = new LineDrawer(200);
		lineDrawer.addLine(0.0f, 0.0f, 500.0f, 500.0f, 1.0f, 0.0f, 0.0f);
		//glEnable(GL_DEPTH_TEST);
		
		while(!Display.isCloseRequested()){
			
			glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT);
			Keyboard.getEventKeyState();
		
			lineDrawer.Render(pipeline);
			
			
			Keyboard.poll();
			deltaTime = System.currentTimeMillis() - lasTime;
			lasTime = System.currentTimeMillis();
			
			
			//System.out.println(1000.0/(deltaTime));
			Display.swapBuffers();
			Display.sync(60);
			
		}
		
	
		
		
	
		
		
	}
}

