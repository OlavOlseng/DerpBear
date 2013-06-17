package network.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import network.server.Connection;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import rendering.Animation;
import rendering.LineDrawer;
import rendering.MatrixUtil;
import rendering.Pipeline;
import rendering.Sprite;
import rendering.Texture;
import rendering.Tile;
import rendering.TileAtlas;
import rendering.TileGridRenderer;
import rendering.TileType;
import rendering.models.TextureModel;
import rendering.models.TileGridModel;
import util.DepthLevel;
import util.DungeonGenerator;
import util.GameConstants;
import world.BodyFactory;
import world.GameWorld;
import world.dbDebugDraw;
import world.gameobject.RenderPropertyName;
import world.gameobject.RenderingMethod;
import core.BaseGame;
import entitysystem.Entity;
import entitysystem.EntityFactory;
import entitysystem.EntityManager;
import entitysystem.NetworkReadSystem;
import entitysystem.NetworkWriteSystem;
import entitysystem.PhysicsSystem;
import entitysystem.PlayerMoveSystem;
import entitysystem.RenderSystem;
import entitysystem.component.InputComponent;
import entitysystem.component.NetworkComponent;
import entitysystem.component.PhysicsComponent;
import entitysystem.component.PlayerComponent;
import entitysystem.component.RenderComponent;
import entitysystem.component.TransformComponent;

public class Game extends BaseGame {

	private EntityManager manager;
	private EntityFactory factory;
	private Pipeline pipeline;

	private NetworkWriteSystem networkWriteSystem;
	private NetworkReadSystem networkReadSystem;
	private PlayerMoveSystem playerMoveSystem;
	private GameWorld world;
	private List<Connection> connections;
	private PhysicsSystem physicsSystem;

	public Game() {
		super(60);
		this.connections = new ArrayList<Connection>();
		init(false);

	}

	@Override
	public void setup() {
		manager = new EntityManager();
		factory = new EntityFactory(manager);
		world = new GameWorld();
		physicsSystem = new PhysicsSystem(manager, factory);

		// begin ground entity
		{
			Entity groundE = factory.createEmptyEntity();

			Tile[][] testRoom = new DungeonGenerator().generateRoom(20, 5, 5, 0);

			TileGridModel grid = new TileGridModel(testRoom.length + 5, testRoom.length + 5);

			for (int i = 0; i < grid.getNumTilesX(); i++)
				for (int j = 0; j < grid.getNumTilesY(); j++)
					grid.setTile(i, j, new Tile(TileType.DIRT_WALL));

			for (int x = 0; x < testRoom.length; x++) {
				for (int y = 0; y < testRoom.length; y++) {
					if (testRoom[y][x].getType() != TileType.NONE)
						grid.setTile(x + 2, y + 2, testRoom[y][x]);
				}

			}

			for (int i = 0; i < 10; i++)
				grid.setTile(5, i, new Tile(TileType.DIRT));
			// test wall
			{
				for (int x = 0; x < grid.getNumTilesX(); x++) {
					for (int y = 0; y < grid.getNumTilesY(); y++) {

						if (grid.getTile(x, y).getType().isWall()) {

							Entity wallE = factory.createEmptyEntity();
							PhysicsComponent physics = new PhysicsComponent(world, BodyFactory.createStaticBodyDef(new Vec2(16.0f
									/ GameConstants.PIXELSCALE + ((float) x) * 32.0f / GameConstants.PIXELSCALE, 16.0f / GameConstants.PIXELSCALE
									+ ((float) y) * 32.0f / GameConstants.PIXELSCALE), 0), BodyFactory.createBox(10.0f,
									16.0f / GameConstants.PIXELSCALE, 16.0f / GameConstants.PIXELSCALE));
							
							float side = 16.0f/GameConstants.PIXELSCALE;
							LineDrawer debug = new LineDrawer(10);
							debug.addLine(-side, -side, side, -side, 1.0f, 0.0f, 0.0f);
							debug.addLine(side, -side, side, side, 1.0f, 0.0f, 0.0f);
							debug.addLine(side, side, -side, side, 1.0f, 0.0f, 0.0f);
							debug.addLine(-side, side, -side, -side, 1.0f, 0.0f, 0.0f);
							debug.setDepth(1.0f);
							
							RenderComponent sprite = new RenderComponent(debug);
							sprite.getNodes().get(0).setScale(32, 32);
							manager.addComponentToEntity(physics, wallE);
							manager.addComponentToEntity(sprite, wallE);
							TransformComponent trans = new TransformComponent();
							trans.setDepth(DepthLevel.BACKGROUND_LVL);
							manager.addComponentToEntity(trans, wallE);
							NetworkComponent network = new NetworkComponent();
							network.addSyncable(sprite, true, false);
							network.addSyncable(trans, true, false);
							manager.addComponentToEntity(network, wallE);
						}
					}
				}

			}

			TileAtlas atlas = new TileAtlas(new rendering.Texture(new TextureModel("testAtlas.png")), 2, 2);
			TileGridRenderer groundRenderer = new TileGridRenderer(atlas, grid);

			// groundRenderer.setScale(32, 32);
			// groundRenderer.setSize(20.0f*32,20.0f*32);

			TransformComponent transform = new TransformComponent();
			transform.setDepth(DepthLevel.BACKGROUND_LVL);
			RenderComponent renderComp = new RenderComponent(groundRenderer);
			manager.addComponentToEntity(transform, groundE);
			manager.addComponentToEntity(renderComp, groundE);

			NetworkComponent network = new NetworkComponent();
			network.addSyncable(renderComp, true, false);
			network.addSyncable(transform, true, false);
			manager.addComponentToEntity(network, groundE);
		}

		// end ground entity

		// begin player entity
		{
			Entity playerE = manager.createEntity();

			TransformComponent trans = new TransformComponent();
			trans.setX(100);
			trans.setY(100);
			trans.setDepth(DepthLevel.ACTOR_LVL);
			LineDrawer debug = new LineDrawer(10);
			dbDebugDraw dbDraw = new dbDebugDraw(debug);
			dbDraw.drawCircle(new Vec2(), 16.0f/GameConstants.PIXELSCALE, new Color3f(1.0f, 0, 0));
			debug.setDepth(1.0f);
			
			RenderComponent sprite = new RenderComponent(
					new Animation(new Texture(new TextureModel("Animations//Dude4.png")),12, 32, 25),
					debug);
			sprite.getNodes().get(0).setSize(32, 32);
			InputComponent input = new InputComponent();
			PlayerComponent player = new PlayerComponent();
			final PhysicsComponent physics = new PhysicsComponent(world, BodyFactory.createDynamicBodyDef(new Vec2(100.0f / GameConstants.PIXELSCALE,
					100.0f / GameConstants.PIXELSCALE), 0f), BodyFactory.createCircle(10.0f, 15.0f / GameConstants.PIXELSCALE));
			manager.addComponentToEntity(trans, playerE);
			manager.addComponentToEntity(sprite, playerE);
			manager.addComponentToEntity(input, playerE);
			manager.addComponentToEntity(player, playerE);
			manager.addComponentToEntity(physics, playerE);
			physics.getBody().setTransform(new Vec2(-1, -1), 0);
			NetworkComponent network = new NetworkComponent();
			network.addSyncable(trans, true, false);
			network.addSyncable(sprite, true, false);
			network.addSyncable(input, false, true);
			network.addSyncable(network, true, false);
			network.addSyncable(player, true, false);

			manager.addComponentToEntity(network, playerE);

			networkWriteSystem = new NetworkWriteSystem(manager, factory, 16, true);

		}

		

		// end player entity
		
//		
//		{
//			
//			LineDrawer lineDrawer = new LineDrawer(10);
//			lineDrawer.addLine(-1, -1, -10, -1, 0.0f, 0.0f, 1.0f);
//			Entity line = factory.createEmptyEntity();
//			RenderComponent render = new RenderComponent(lineDrawer);
//			TransformComponent trans = new TransformComponent();
//			trans.setX(-1);
//			trans.setY(-1);
//			NetworkComponent network = new NetworkComponent();
//			network.addSyncable(render, true, false);
//			network.addSyncable(trans, true, false);
//			manager.addComponentToEntity(network, line);
//			manager.addComponentToEntity(trans, line);
//			manager.addComponentToEntity(render, line);
//			
//			
//		}
		playerMoveSystem = new PlayerMoveSystem(manager, factory);
	}

	public void addClient(Connection connection) {

		new NetworkReadSystem(manager, factory, connection);
		networkWriteSystem.addConnection(connection);
		this.connections.add(connection);
	}

	@Override
	public void onTick(float dt) {
		// Stop gameworld while no clients are connected

		if (connections.size() > 0) {

			playerMoveSystem.update(dt);
			world.update(dt);
			physicsSystem.update(dt);

		}

	}

}
