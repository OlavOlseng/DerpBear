import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Vec2;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
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
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.ResourceLoader;

import core.BaseGame;

import rendering.Attribute;
import rendering.LineDrawer;
import rendering.MatrixUtil;
import rendering.Node;
import rendering.Pipeline;

import rendering.ResourceManager;
import rendering.Shader;
import rendering.Sprite;
import rendering.SpriteBatch;
import rendering.Tile;
import rendering.TileAtlas;
import rendering.TileGrid;
import rendering.TileGridRenderer;
import rendering.TileType;
import world.GameWorld;
import world.dbDebugDraw;
import world.entity.Box;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Eiriktest extends BaseGame {

	LineDrawer lineDrawer;
	Pipeline pipeline;
	Node rootNode;
	Sprite sprite;
	
	public static final float PIXELSCALE = 32/2;
	
	GameWorld world;
	LineDrawer ldr;
	dbDebugDraw dbgDraw;
	public Eiriktest() {
		super(60,1280,720);
		init();
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void setup() {
		// TODO Auto-generated method stub
		rootNode = new Node();
		
		Texture tex = null;
		try {
			tex = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("res/tileAtlas.png"),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 sprite = new Sprite(tex);
		sprite.setWidth(100);
		sprite.setHeight(150);
		pipeline = new Pipeline();
		
		pipeline.setProjectionMatrix(MatrixUtil.getOrthographicProjection(0, this.getScreenWidth(), 0, this.getScreenHeight()));
		Matrix4f view = new Matrix4f();
		view.translate(new Vector2f(-10.0f,0.0f));
		pipeline.setViewMatrix(view);
		
		
		sprite.setPosition(1000, 200);
		
		
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
		
		
		
		
		lineDrawer  = new LineDrawer(200);
		lineDrawer.addLine(0.0f, 0.0f, 500.0f, 500.0f, 1.0f, 0.0f, 0.0f);
		
		//rootNode.addChild(lineDrawer);
		rootNode.addChild(sprite);
		ldr = new LineDrawer(100000);
		
		dbgDraw = new dbDebugDraw(ldr);
		world  = new GameWorld();
		world.getPhysWorld().setDebugDraw(dbgDraw);
		dbgDraw.setFlags(DebugDraw.e_shapeBit);
		rootNode.addChild(ldr);
		world.add(new Box(world, new Vec2(200, 200), 20, 20, 0, 1));
		
		//lineDrawer.move(100, 100);
	}

	
	@Override
	public void onTick(float dt) {
		
	
		
		world.update(dt);
		world.render(pipeline);
		rootNode.render(pipeline);
		ldr.clear();
		
		
		
		// TODO Auto-generated method stub
		//glEnable(GL_DEPTH_TEST);
			
		
			
			
			
			//lineDrawer.render(pipeline);
			
			//rootNode.rotate(0.1f);
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				sprite.move(-1, 0);
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				sprite.move(1, 0);
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
				sprite.move(0, 1);
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				sprite.move(0, -1);
			}
			
			float dx = Mouse.getX()- sprite.getPosition().x;
			
			float dy = Mouse.getY() - sprite.getPosition().y;
			
			if (dx<0) {
				sprite.setOrientation((float)Math.atan(dy/dx) + 3.14f/2);
		    }else{
		    	sprite.setOrientation((float)Math.atan(dy/dx) - 3.14f/2);
		        

		    }
			
			if(Mouse.isButtonDown(0)){
				

				Sprite square = new Sprite(ResourceManager.getTexture("PNG", "grass.png"));
				square.setSize(5, 5);
				rootNode.addChild(square);
				Box box = new Box(world,square, new Vec2(sprite.getPosition().x,sprite.getPosition().y), 5, 5, 0, 1);
				Vec2 dir = new Vec2(dx,dy);
				dir.normalize();
				dir.x *=50;
				dir.y *=50;
				box.getBody().applyLinearImpulse(dir, new Vec2(0,0));
				
				
				
			}
			
			rootNode.render(pipeline);
			//System.out.println(1000.0/(deltaTime));
			pipeline.clear();
			
		
		
		
	}

	
	public static void main(String[] args) throws LWJGLException, InterruptedException {
		
		new Eiriktest();
		
		
	}

	
}

