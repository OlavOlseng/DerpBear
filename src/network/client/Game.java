package network.client;

import java.util.ArrayList;

import network.server.Connection;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import rendering.MatrixUtil;
import rendering.Pipeline;
import util.GLWorkerManager;
import util.GameConstants;
import core.BaseGame;
import entitysystem.EntityFactory;
import entitysystem.EntityManager;
import entitysystem.InputSystem;
import entitysystem.NetworkReadSystem;
import entitysystem.NetworkWriteSystem;
import entitysystem.RenderSystem;

public class Game extends BaseGame{

	private EntityManager manager;
	private EntityFactory factory;
	private Pipeline pipeline;
	private RenderSystem renderSystem;
	private NetworkReadSystem networkReadSystem;
	private NetworkWriteSystem networkWriteSystem;
	private Connection connection;
	private InputSystem inputSystem;
	
	
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
		ArrayList<Connection> conn = new ArrayList<Connection>();
		conn.add(connection);
		renderSystem = new RenderSystem(manager, factory, pipeline);
		networkReadSystem = new NetworkReadSystem(manager,factory,conn,1);
		networkWriteSystem = new NetworkWriteSystem(manager, factory, conn, 16, false);
		inputSystem = new InputSystem(manager, factory);
	}

	@Override
	public void onTick(float dt) {
		
		renderSystem.update(dt);
		inputSystem.update(dt);
		GLWorkerManager.invokeAllJobs();
		pipeline.clear();
		
	}

}
