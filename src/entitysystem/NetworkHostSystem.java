package entitysystem;

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
	/**
	 * @param connection - A list of client connections
	 * @param updateFrequency - How often updates will be queried and sent
	 */
	public NetworkHostSystem(EntityManager entityManager, EntityFactory entityFactory, final long updateFrequency) {
		super(entityManager, entityFactory);
		this.updateFrequency = updateFrequency;
		this.connections = new ArrayList<Connection>();
		new Timer(true).scheduleAtFixedRate(new TimerTask() {
			
			
			@Override
			public void run() {
				update(updateFrequency);
			}
		}, 0, updateFrequency);
	}

	public void addConnection(Connection connection){
		sendAllComponents(connection);
		connections.add(connection);
	}
	//Only used to avoid unnecessary allocs
	private ArrayList<Syncable> listCache = new ArrayList<Syncable>();
	/**
	 * Syncs game state. Used internaly. Should not be called unless you know what you are doing
	 */
	@Override
	public void update(float dt) {
		
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
						System.out.println("didChange");
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
					System.out.println("sending all!");
					target.sendObjects(components);
				} catch (IOException e) {
					e.printStackTrace();
				}		
		}
		
	}

}
