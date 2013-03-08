import java.io.IOException;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Vec2;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import core.BaseGame;

import rendering.LineDrawer;
import rendering.MatrixUtil;
import rendering.Node;
import rendering.Pipeline;
import rendering.ResourceManager;
import rendering.Sprite;
import rendering.Tile;
import rendering.TileAtlas;
import rendering.TileGrid;
import rendering.TileGridRenderer;
import rendering.TileType;
import util.DepthLevel;
import util.GameConstants;
import world.BodyFactory;
import world.GameWorld;
import world.dbDebugDraw;
import entitysystem.Entity;
import entitysystem.EntityFactory;
import entitysystem.EntityManager;
import entitysystem.RenderSystem;
import entitysystem.ScenerySystem;
import entitysystem.component.PhysicsComponent;
import entitysystem.component.RenderComponent;
import entitysystem.component.TransformComponent;


public class MothertruckerTest extends BaseGame{
	

	LineDrawer lineDrawer;
	Pipeline pipeline;
	Node rootNode;
	ScenerySystem ss;
	RenderSystem renderSystem;
	GameWorld world;
	LineDrawer ldr;
	dbDebugDraw dbgDraw;
	
	public MothertruckerTest() {
		super(60,1280,720);
		init();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
		world  = new GameWorld();
		ldr = new LineDrawer(100000);
		ldr.setDepth(DepthLevel.TOP_LVL.getDepth());
		
		dbgDraw = new dbDebugDraw(ldr);
		
		dbgDraw.setFlags(DebugDraw.e_shapeBit);
		world.getPhysWorld().setDebugDraw(dbgDraw);
		
		rootNode = new Node();
		pipeline = new Pipeline();
		pipeline.setProjectionMatrix(MatrixUtil.getOrthographicProjection(0, this.getScreenWidth(), 0, this.getScreenHeight()));
		Matrix4f view = new Matrix4f();
		view.translate(new Vector2f(-10.0f,0.0f));
		pipeline.setViewMatrix(view);
		
		lineDrawer  = new LineDrawer(200);
		lineDrawer.addLine(0.0f, 0.0f, 500.0f, 500.0f, 1.0f, 0.0f, 0.0f);
		
		//rootNode.addChild(lineDrawer);
		rootNode.addChild(ldr);
		

		test();
	}
	
	public void test() {
		//ADDING ZE COMPONENT
		
		EntityManager manager = new EntityManager();
		EntityFactory factory = new EntityFactory(manager);
		ss = new ScenerySystem(manager, factory);
		renderSystem = new RenderSystem(manager, factory, pipeline);
		
		
		Texture playerTex = ResourceManager.getTexture("PNG", "tileAtlas.png");
		Sprite playerSprite = new Sprite(playerTex);
		playerSprite.setSize(32, 32);
		
		
		float ps = GameConstants.PIXELSCALE;
		int boxes = 200;
		for(int x = -boxes/2; x < boxes/2; x++) {
			Entity empty = factory.createEmptyEntity();
			manager.addComponentToEntity(new TransformComponent(), empty);
			manager.addComponentToEntity(new PhysicsComponent(world, BodyFactory.createDynamicBodyDef(new Vec2(12*x/ps, 6*x/ps), 0f), BodyFactory.createBox(10.0f, 16/ps, 16/ps)), empty);
		//	manager.addComponentToEntity(new RenderComponent(playerSprite), empty);
			
		}
	}
	
	@Override
	public void onTick(float dt) {
			
			world.update(dt);
			
			renderSystem.update(dt);
			ss.update(dt);
			
			world.render(pipeline);
			ldr.render(pipeline);
			ldr.clear();
			
			pipeline.clear();
	}

	
	public static void main(String[] args) throws LWJGLException, InterruptedException {
		new MothertruckerTest();
	}
}
