package entitysystem;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import entitysystem.component.Component;
import entitysystem.component.NetworkComponent;

import network.Syncable;
import network.server.Connection;

public class NetworkHostSystem extends BaseSystem {
	
	private ArrayList<Connection> connections;
	private Long updateFrequency;
	//Used to keep track of how many connections the system is aware of
	private int numberOfConnections;
	/**
	 * @param connection - A list of  initial client connections
	 * @param updateFrequency - How often updates will be queried and sent
	 */
	public NetworkHostSystem(EntityManager entityManager, EntityFactory entityFactory, final ArrayList<Connection> connections, final long updateFrequency) {
		super(entityManager, entityFactory);
		this.connections = connections;
		this.numberOfConnections = connections.size();
		this.updateFrequency = updateFrequency;
		new Timer(true).scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				update(updateFrequency);
			}
		}, 0, updateFrequency);
	}

	
	//Only used to avoid unnecessary allocs
	private ArrayList<Syncable> listCache = new ArrayList<Syncable>();
	
	/**
	 * Syncs game state. Used internaly. Should not be called unless you know what you are doing
	 */
	@Override
	public void update(float dt) {
		//True if there are new connections since last tick
		if(connections.size() >  numberOfConnections){
			int newConnections =  connections.size() - numberOfConnections;
			for(int i = numberOfConnections; i < connections.size(); i++){
				sendAllComponents(connections.get(i));
			}
			numberOfConnections = connections.size();
			
		}
		sendChangedComponents();
		
	}
	
	private void sendChangedComponents(){
		ArrayList<Entity> ents = getEntityManager().getAllEntitiesPossesingComponentOfClass(NetworkComponent.class);
		for (Entity entity: ents){
				NetworkComponent networkComponent =  (NetworkComponent) entity.getComponentOfType(NetworkComponent.class);
				ArrayList<Syncable> components = networkComponent.getSyncableComponents();
				ArrayList<Syncable> didChangeList = listCache;
				didChangeList.clear();
				for(Syncable comp: components){
					if(comp.didChange()){
						didChangeList.add(comp);
					}
				}
				if(didChangeList.size() > 0){
					for (Connection connection: connections){
						try {
							connection.sendObjects(didChangeList);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
		}
	}
	private void sendAllComponents(Connection target){
		ArrayList<Entity> ents = getEntityManager().getAllEntitiesPossesingComponentOfClass(NetworkComponent.class);
		for (Entity entity: ents){
				NetworkComponent networkComponent =  (NetworkComponent) entity.getComponentOfType(NetworkComponent.class);
				ArrayList<Syncable> components = networkComponent.getSyncableComponents();
				
				try {
					target.sendObjects(components);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
		
		}
		
	}

}
