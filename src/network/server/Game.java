package network.server;

import java.util.ArrayList;

import network.server.Connection;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import rendering.MatrixUtil;
import rendering.Pipeline;
import util.GameConstants;
import core.BaseGame;
import entitysystem.Entity;
import entitysystem.EntityFactory;
import entitysystem.EntityManager;
import entitysystem.NetworkClientSystem;
import entitysystem.NetworkHostSystem;
import entitysystem.RenderSystem;
import entitysystem.component.NetworkComponent;
import entitysystem.component.RenderComponent;
import entitysystem.component.TransformComponent;

public class Game extends BaseGame{

	private EntityManager manager;
	private EntityFactory factory;
	private Pipeline pipeline;
	private NetworkHostSystem networkSystem;
	
	public Game() {
		super(60);

		init(false);
	}

	@Override
	public void setup() {
		manager = new EntityManager();
		factory = new EntityFactory(manager);

		Entity groundE = factory.createEmptyEntity();
		TransformComponent comp = new TransformComponent();
		manager.addComponentToEntity(comp, groundE);
		manager.addComponentToEntity(new NetworkComponent(comp), groundE);
		networkSystem = new NetworkHostSystem(manager,factory,100);
		
		
		
	}

	@Override
	public void onTick(float dt) {
		
		
	}

}
