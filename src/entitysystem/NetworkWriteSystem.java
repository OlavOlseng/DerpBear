package entitysystem;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import entitysystem.component.Component;
import entitysystem.component.NetworkComponent;

import network.Syncable;
import network.server.Connection;

public class NetworkWriteSystem extends BaseSystem {
	
	private ArrayList<Connection> connections;
	private Long updateFrequency;
	//Used to keep track of how many connections the system is aware of
	private int numberOfConnections;
	private boolean isHost;
	/**
	 * @param  connection - A list of  initial client connections
	 * @param  updateFrequency - How often updates will be queried and sent
	 * @param  isHost - Is the system running as client or host?(Ugly solution)
	 */
	public NetworkWriteSystem(EntityManager entityManager, EntityFactory entityFactory, final ArrayList<Connection> connections, final long updateFrequency, boolean isHost) {
		super(entityManager, entityFactory);
		this.connections = connections;
		this.numberOfConnections = connections.size();
		this.updateFrequency = updateFrequency;
		this.isHost = isHost;
		new Timer(true).scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				update(updateFrequency);
			}
		}, 0, updateFrequency);
	}

	

	
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
	private ArrayList<Connection> disconectedClients = new ArrayList<Connection>();
	private void sendChangedComponents(){
		ArrayList<Entity> ents = getEntityManager().getAllEntitiesPossesingComponentOfClass(NetworkComponent.class);
		List<Syncable> didChangeList = new ArrayList<Syncable>();
		for (Entity entity: ents){
				NetworkComponent networkComponent =  (NetworkComponent) entity.getComponentOfType(NetworkComponent.class);
				if(isHost){
					didChangeList.addAll(networkComponent.getReadyHostWriteSyncables());
					
				}else{
					didChangeList.addAll(networkComponent.getReadyClientWriteSyncables());
					
				}
				networkComponent.clear();
		}
		
				if(didChangeList.size() > 0){
					System.out.println(didChangeList);
					for (Connection connection: connections){
						try {
							
							connection.sendObjects(didChangeList);
							for(Syncable syncable: didChangeList){
								syncable.onWrite(syncable);
							}
							
						} catch (IOException e) {
							System.out.println("Client disconected");
							disconectedClients.add(connection);
						}
					}
					
				
				
					if(disconectedClients.size() > 0){
						for (Connection connection: disconectedClients){
							connections.remove(connection);
						}
						disconectedClients.clear();
					}
				}
		
	}
	private void sendAllComponents(Connection target){
		ArrayList<Entity> ents = getEntityManager().getAllEntitiesPossesingComponentOfClass(NetworkComponent.class);
		for (Entity entity: ents){
				NetworkComponent networkComponent =  (NetworkComponent) entity.getComponentOfType(NetworkComponent.class);
				List<Syncable> components;
				if(isHost){
					components = networkComponent.getAllHostWriteSyncables();
				}else{
					components = networkComponent.getAllClientWriteSyncables();
					
				}
				
				try {
					
					target.sendObjects(components);
					for(Syncable syncable: components){
						syncable.onWrite(syncable);
					}
					networkComponent.clear();
					
				} catch (IOException e) {
					System.out.println("Client disconected");
					connections.remove(target);
				}
		
		}
		
	}

}
