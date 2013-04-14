import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
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
import world.gameobject.Damage;
import world.gameobject.DamageType;
import entitysystem.CollisionSystem;
import entitysystem.Entity;
import entitysystem.EntityFactory;
import entitysystem.EntityManager;
import entitysystem.EventSystem;
import entitysystem.LookAtSystem;
import entitysystem.MoveToSystem;
import entitysystem.RenderSystem;
import entitysystem.PhysicsSystem;
import entitysystem.component.DamageComponent;
import entitysystem.component.DeathEventComponent;
import entitysystem.component.LookAtComponent;
import entitysystem.component.MoveToComponent;
import entitysystem.component.PhysicsComponent;
import entitysystem.component.RenderComponent;
import entitysystem.component.StatusComponent;
import entitysystem.component.TransformComponent;
import entitysystem.event.RemoveBodyEvent;


public class MothertruckerTest extends BaseGame{

	EntityManager manager = new EntityManager();
	EntityFactory factory = new EntityFactory(manager);

	LineDrawer lineDrawer;
	Pipeline pipeline;
	Node rootNode;

	PhysicsSystem ss;
	RenderSystem renderSystem;
	MoveToSystem ms;
	LookAtSystem ls;
	CollisionSystem cs;
	EventSystem es;
	
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
		
		ss = new PhysicsSystem(manager, factory);
		renderSystem = new RenderSystem(manager, factory, pipeline);
		ms = new MoveToSystem(manager, factory);
		ls = new LookAtSystem(manager, factory);
		cs = new CollisionSystem(manager, factory, world.getContactHandler());
		es = new EventSystem(manager, factory);
		
		Texture playerTex = ResourceManager.getTexture("PNG", "tileAtlas.png");
		Sprite playerSprite = new Sprite(playerTex);
		playerSprite.setSize(32, 32);
		
		float ps = GameConstants.PIXELSCALE;
		
		int boxes = 1000;
		int bullets = 3;
		
		//		ArrayList<MoveToComponent> moveList = new ArrayList<MoveToComponent>();
		
		//Create bullets
		for(int x = 0; x < 1280; x += 1280/bullets) {
			Random rand = new Random();
			Entity empty = factory.createEmptyEntity();

			Texture tex = ResourceManager.getTexture("PNG", "player.png");
			Sprite spr = new Sprite(tex);
			spr.setSize(32, 32);
			
			manager.addComponentToEntity(new TransformComponent(), empty);
			manager.addComponentToEntity(new PhysicsComponent(world, BodyFactory.createDynamicBodyDef(new Vec2(rand.nextFloat()*1280/ps, rand.nextFloat()*720/ps), 0f), BodyFactory.createCircle(10.f + rand.nextFloat()*49, 20/ps)), empty);
			manager.addComponentToEntity(new RenderComponent(spr), empty);

			MoveToComponent k = new MoveToComponent(1000, 10, 100/ps);
			LookAtComponent l = new LookAtComponent();
			ArrayList<Vec2> ts = new ArrayList<Vec2>();

			float xx = rand.nextFloat()*1280;
			float yy = rand.nextFloat()*720;

			ts.add(new Vec2(xx/ps, yy/ps));
			l.lookAt(xx, yy);
			k.setTargets(ts);

			manager.addComponentToEntity(l, empty);
			manager.addComponentToEntity(k, empty);

			DamageComponent dc = new DamageComponent(new Damage(DamageType.NORMAL, 100));
			
			manager.addComponentToEntity(dc, empty);
		}
		
		//Create boxes
		for(int x = 0; x < boxes; x += 1) {
//		for(int x = 0; x < 1280; x += 1280/boxes) {
			Random rand = new Random();
			Entity empty = factory.createEmptyEntity();
			manager.addComponentToEntity(new TransformComponent(), empty);
			manager.addComponentToEntity(new PhysicsComponent(world, BodyFactory.createDynamicBodyDef(new Vec2(rand.nextFloat()*1280/ps, rand.nextFloat()*720/ps), 0f), BodyFactory.createCircle(10.f + rand.nextFloat()*49, 20/ps)), empty);
			manager.addComponentToEntity(new RenderComponent(playerSprite), empty);

			MoveToComponent k = new MoveToComponent(1000, 10, 100/ps);
			LookAtComponent l = new LookAtComponent();
			ArrayList<Vec2> ts = new ArrayList<Vec2>();

			float xx = rand.nextFloat()*1280;
			float yy = rand.nextFloat()*720;

			ts.add(new Vec2(xx/ps, yy/ps));
			l.lookAt(xx, yy);
			k.setTargets(ts);

			manager.addComponentToEntity(l, empty);
			manager.addComponentToEntity(k, empty);
			
			DeathEventComponent dec = new DeathEventComponent();
			dec.addEvent(new RemoveBodyEvent());
			StatusComponent sc = new StatusComponent(100, 50, 0, 0, 0, 0);

			manager.addComponentToEntity(sc, empty);
			manager.addComponentToEntity(dec, empty);
		}
	}
	
	@Override
	public void onTick(float dt) {

		world.update(dt);
		ss.update(dt);

		ms.update(dt);
		ls.update(dt);
		cs.update(dt);
		es.update(dt);
		
		renderSystem.update(dt);
//---------DEGUB DRAW-------
//		world.render(pipeline);
//		ldr.render(pipeline);
//		ldr.clear();
//---------------------------
		pipeline.clear();
	}


	public static void main(String[] args) throws LWJGLException, InterruptedException {
		new MothertruckerTest();
	}
}
