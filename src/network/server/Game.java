package network.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import network.server.Connection;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;


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
import util.GameConstants;
import world.gameobject.RenderPropertyName;
import world.gameobject.RenderingMethod;
import core.BaseGame;
import entitysystem.Entity;
import entitysystem.EntityFactory;
import entitysystem.EntityManager;
import entitysystem.NetworkReadSystem;
import entitysystem.NetworkWriteSystem;
import entitysystem.RenderSystem;
import entitysystem.component.InputComponent;
import entitysystem.component.NetworkComponent;
import entitysystem.component.RenderComponent;
import entitysystem.component.TransformComponent;

public class Game extends BaseGame{

	private EntityManager manager;
	private EntityFactory factory;
	private Pipeline pipeline;
	private NetworkWriteSystem networkWriteSystem;
	private NetworkReadSystem networkReadSystem;
	
	private ArrayList<Connection> connections;
	TransformComponent trans;
	
	public Game(ArrayList<Connection> connections) {
		super(60);
		this.connections = connections;
		init(false);
	}

	@Override
	public void setup() {
		manager = new EntityManager();
		factory = new EntityFactory(manager);
		//begin ground entity
		{
			Entity groundE = factory.createEmptyEntity();
			
			
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
			
			TileAtlas atlas = new TileAtlas(new rendering.Texture(new TextureModel("testAtlas.png")), 2, 2);
			TileGridRenderer groundRenderer = new TileGridRenderer(atlas, grid);
			groundRenderer.setScale(50, 50);
			
			TransformComponent transform = new TransformComponent();
			transform.getTransform().setDepth(DepthLevel.BACKGROUND_LVL);
			RenderComponent renderComp = new RenderComponent(groundRenderer);
			manager.addComponentToEntity(transform, groundE);
			manager.addComponentToEntity(renderComp, groundE);
			
			NetworkComponent network = new NetworkComponent();
			network.addSyncable(renderComp, true, false);
			network.addSyncable(transform, true, false);
			

			
			
			manager.addComponentToEntity(network, groundE);
		}
		//end ground entity
		
		//begin player entity
		{
			Entity playerE = manager.createEntity();
	
			
		    trans = new TransformComponent();
		    trans.getTransform().setX(100);
			trans.getTransform().setY(100);
			RenderComponent sprite = new RenderComponent(new Sprite(new Texture(new TextureModel("player.png"))));
			sprite.getNode().setScale(20, 20);
			InputComponent input = new InputComponent();
			manager.addComponentToEntity(trans, playerE);
			manager.addComponentToEntity(sprite, playerE);
			manager.addComponentToEntity(input, playerE);
			NetworkComponent network = new NetworkComponent();
			network.addSyncable(trans, true, false);
			network.addSyncable(sprite, true, false);
			network.addSyncable(input, false, true);
			network.addSyncable(network, true, false);
			manager.addComponentToEntity(network, playerE);
			
			

		}
		//end player entity
		networkWriteSystem = new NetworkWriteSystem(manager,factory,this.connections,100, true);
		networkReadSystem = new NetworkReadSystem(manager, factory, this.connections, 100);
	}

	@Override
	public void onTick(float dt) {
		//Stop gameworld while no clients are connected
		
		if(connections.size() > 0){
		//	trans.getTransform().setX(trans.getTransform().getX() +10*dt);
			
		}
		
	}

}
