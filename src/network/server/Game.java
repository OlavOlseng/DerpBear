package network.server;

import java.util.ArrayList;
import java.util.HashMap;

import network.server.Connection;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import rendering.MatrixUtil;
import rendering.Pipeline;
import rendering.Tile;
import rendering.TileGrid;
import rendering.TileType;
import util.GameConstants;
import world.gameobject.RenderPropertyName;
import world.gameobject.RenderingMethod;
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
	private ArrayList<Connection> connections;
	
	
	public Game(ArrayList<Connection> connections) {
		super(60);
		this.connections = connections;
		init(false);
	}

	@Override
	public void setup() {
		manager = new EntityManager();
		factory = new EntityFactory(manager);

		Entity groundE = factory.createEmptyEntity();
		
		
		TileGrid grid =  new TileGrid(50, 50);
		
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
		
		TransformComponent comp = new TransformComponent();
		manager.addComponentToEntity(comp, groundE);
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(RenderPropertyName.TEXTURE.getName(), "testAtlas.png");
		params.put(RenderPropertyName.TILES_X.getName(), 2);
		params.put(RenderPropertyName.TILES_Y.getName(), 2);
		params.put(RenderPropertyName.TILE_GRID.getName(), grid);
		
		RenderComponent renderComp = new RenderComponent(RenderingMethod.TILE_GRID_RENDERER, params);
		
		manager.addComponentToEntity(renderComp, groundE);
		manager.addComponentToEntity(new NetworkComponent(comp,renderComp), groundE);
		networkSystem = new NetworkHostSystem(manager,factory,this.connections,100);
		
	}

	@Override
	public void onTick(float dt) {
		//Stop gameworld while no clients are connected
		if(connections.size() > 0){
			
			
		}
		
	}

}
