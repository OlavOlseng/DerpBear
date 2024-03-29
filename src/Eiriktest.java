import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
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

import org.newdawn.slick.Game;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.ResourceLoader;



import core.BaseGame;
import entitysystem.ChaseSystem;
import entitysystem.Entity;
import entitysystem.EntityFactory;
import entitysystem.EntityManager;
import entitysystem.LookAtSystem;
import entitysystem.PlayerMoveSystem;
import entitysystem.RenderSystem;
import entitysystem.PhysicsSystem;
import entitysystem.component.ChaseComponent;
import entitysystem.component.LookAtComponent;
import entitysystem.component.MoveToComponent;
import entitysystem.component.PhysicsComponent;
import entitysystem.component.PlayerComponent;
import entitysystem.component.RenderComponent;
import entitysystem.component.TransformComponent;

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
import rendering.TileGridRenderer;
import rendering.TileType;
import rendering.models.TileGridModel;
import util.DepthLevel;
import util.GameConstants;
import world.BodyFactory;
import world.GameWorld;
import world.dbDebugDraw;

import world.gameobject.Transform;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Eiriktest extends BaseGame {

	LineDrawer lineDrawer;
	Pipeline pipeline;
	Node rootNode;
	Sprite sprite;
	TileGridRenderer ground;
	RenderSystem renderSystem;
	PlayerMoveSystem playerMoveSystem;
	PhysicsSystem physicsSystem;
	LookAtSystem lookAtSystem;
	ChaseSystem chaseSystem;
	public static final float PIXELSCALE = 32/2;
	
	GameWorld world;
	LineDrawer ldr;
	dbDebugDraw dbgDraw;
	
	public Eiriktest() {
		super(100,1280,720);
		init();
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void setup() {
		// TODO Auto-generated method stub
		Long a = (long) 2;
		Long b = (long) 2;
		System.out.println(a == b);
				
		world  = new GameWorld();
		ldr = new LineDrawer(100000);
		ldr.setDepth(DepthLevel.TOP_LVL.getDepth());
		
		dbgDraw = new dbDebugDraw(ldr);
		
		dbgDraw.setFlags(DebugDraw.e_shapeBit);
		world.getPhysWorld().setDebugDraw(dbgDraw);
		
		rootNode = new Node();
		
		Texture tex = null;
		try {
			tex = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("res/tileAtlas.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 sprite = new Sprite(tex);
		sprite.setPosition(200, 200);
		sprite.setWidth(40);
		sprite.setHeight(40);
		pipeline = new Pipeline();
//		boxActor = new TestActor(world, new Vec2(sprite.getPosition().x,sprite.getPosition().y), 0.0f);
//		boxActor.setNode(sprite);
		
		pipeline.setProjectionMatrix(MatrixUtil.getOrthographicProjection(0, this.getScreenWidth(), 0, this.getScreenHeight()));
		Matrix4f view = new Matrix4f();
		view.translate(new Vector2f(-10.0f,0.0f));
		pipeline.setViewMatrix(view);
		
		
		sprite.setPosition(1000, 200);
		
		
		
		
		Texture atlas = null;
		try {
			atlas = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("res/testAtlas.png"),true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		TileAtlas tileAtlas = new TileAtlas(atlas, 2, 2);
		
		TileGridModel grid =  new TileGridModel(50, 50);
		
		for(int x = 0; x<50; x++){
			for (int y = 0; y<50;y++){
				grid.setTile(x, y, new Tile(TileType.DIRT));
			}
			
		}
		
	
		grid.setTile(5, 5, new Tile(TileType.GRASS));
		grid.setTile(5, 6, new Tile(TileType.GRASS));
		grid.setTile(4, 5, new Tile(TileType.GRASS));
		grid.setTile(5, 4, new Tile(TileType.GRASS));
		grid.setTile(6, 5, new Tile(TileType.GRASS));
		ground= new TileGridRenderer(tileAtlas, grid);
		ground.setSize(50*64, 50*64);
		ground.setPosition(0, 0);
		
		ground.setDepth(DepthLevel.BACKGROUND_LVL.getDepth());
		
		
		lineDrawer  = new LineDrawer(200);
		lineDrawer.addLine(0.0f, 0.0f, 500.0f, 500.0f, 1.0f, 0.0f, 0.0f);
		
		//rootNode.addChild(lineDrawer);
		sprite.setDepth(DepthLevel.ACTOR_LVL.getDepth());
		rootNode.addChild(sprite);
		rootNode.addChild(ldr);
//		world.add(new Box(world, new Vec2(200, 200), 20, 20, 0, 1));
		Texture playerTex = ResourceManager.getTexture("PNG", "square.png");
		Sprite playerSprite = new Sprite(playerTex);
		
		
		playerSprite.setSize(32, 32);
		BodyDef def = new BodyDef();
		def.type = BodyType.DYNAMIC;
		
		EntityManager manager = new EntityManager();
		EntityFactory factory = new EntityFactory(manager);
		
		
		
		Entity empty = factory.createEmptyEntity();
		RenderComponent renderComponent = new RenderComponent(playerSprite);
		//manager.addComponentToEntity(renderComponent, empty);
		manager.addComponentToEntity(new TransformComponent(), empty);
		manager.addComponentToEntity(new PlayerComponent(), empty);
		manager.addComponentToEntity(new LookAtComponent(), empty);
		manager.addComponentToEntity(new RenderComponent(playerSprite), empty);
		manager.addComponentToEntity(new PhysicsComponent(world, BodyFactory.createDynamicBodyDef(new Vec2(100.0f/GameConstants.PIXELSCALE, 100.0f/GameConstants.PIXELSCALE), 0f), BodyFactory.createBox(10.0f, 16.0f/GameConstants.PIXELSCALE, 16.0f/GameConstants.PIXELSCALE)), empty);
		
		
		Entity groundE = factory.createEmptyEntity();
		manager.addComponentToEntity(new TransformComponent(), groundE);
		manager.addComponentToEntity(new RenderComponent(ground), groundE);
		
		float ps = GameConstants.PIXELSCALE;
		
		
		for(int i = 0; i< 10; i++){
		Entity chaser = factory.createEmptyEntity();
		manager.addComponentToEntity(new TransformComponent(), chaser);
		manager.addComponentToEntity(new PhysicsComponent(world, BodyFactory.createDynamicBodyDef(new Vec2((float)i*10, 360.0f/ps), 0f), BodyFactory.createCircle(10.0f, 16/ps)), chaser);
		manager.addComponentToEntity(new RenderComponent(playerSprite), chaser);
		ChaseComponent chase = new ChaseComponent();
		chase.setTarget(empty);
		manager.addComponentToEntity(chase, chaser);
		
		
		}
		
		
		
		
		
		
		renderSystem = new RenderSystem(manager, factory, pipeline);
		playerMoveSystem = new PlayerMoveSystem(manager, factory);
		physicsSystem = new PhysicsSystem(manager,factory);
		lookAtSystem = new LookAtSystem(manager, factory);
		chaseSystem = new ChaseSystem(manager, factory);
		
		
		//player = new Entity(graphicsComponent, physicsComponent)
//		world.add(player);
		//lineDrawer.move(100, 100);
	}

	
	@Override
	public void onTick(float dt) {
		
	
		
		
		
			
			

			
			
		
			
			world.update(dt);
			playerMoveSystem.update(dt);
			chaseSystem.update(dt);
			lookAtSystem.update(dt);
			physicsSystem.update(dt);
			world.render(pipeline);
			renderSystem.update(dt);
			ldr.render(pipeline);
			
			pipeline.clear();
			
		
		
			ldr.clear();
	}

	
	public static void main(String[] args) throws LWJGLException, InterruptedException {
		
		new Eiriktest();
		
		
	}

	
}

