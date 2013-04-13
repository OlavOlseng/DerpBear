package network.client;

import network.server.Connection;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import rendering.MatrixUtil;
import rendering.Pipeline;
import util.GameConstants;
import core.BaseGame;
import entitysystem.EntityFactory;
import entitysystem.EntityManager;
import entitysystem.NetworkClientSystem;
import entitysystem.RenderSystem;

public class Game extends BaseGame{

	private EntityManager manager;
	private EntityFactory factory;
	private Pipeline pipeline;
	private RenderSystem renderSystem;
	private NetworkClientSystem networkSystem;
	private Connection connection;
	
	
	public Game(Connection connection) {
		super(60, GameConstants.CLIENT_SCREEN_WIDTH, GameConstants.CLIENT_SCREEN_HEIGHT);
		this.connection = connection;
		init();
	}

	@Override
	public void setup() {
		manager = new EntityManager();
		factory = new EntityFactory(manager);
		
		pipeline = new Pipeline();
		pipeline.setProjectionMatrix(MatrixUtil.getOrthographicProjection(0, this.getScreenWidth(), 0, this.getScreenHeight()));
		Matrix4f view = new Matrix4f();
		view.translate(new Vector2f(0.0f,0.0f));
		pipeline.setViewMatrix(view);
		
		renderSystem = new RenderSystem(manager, factory, pipeline);
		networkSystem = new NetworkClientSystem(manager,factory,connection,100);
		
	}

	@Override
	public void onTick(float dt) {
		renderSystem.update(dt);
		
	}

}
