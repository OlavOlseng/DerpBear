package entitysystem;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import entitysystem.component.Component;

import network.Syncable;
import network.server.Connection;

public class NetworkReadSystem extends BaseSystem{

	private ArrayList<Connection> connections;
	private long updateFrequency;
	
	/**
	 * The network system will start its own private thread for listening.
	 * @param connection a connection to the server
	 * @param updateFrequency how often the socket will be read
	 */
	public NetworkReadSystem(EntityManager entityManager, EntityFactory entityFactory, final ArrayList<Connection> connections, final long updateFrequency){
		super(entityManager, entityFactory);
		this.connections = connections;
		this.updateFrequency = updateFrequency;
		new Timer(true).scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				
				update(updateFrequency);
			}
		}, 0, updateFrequency);
	}

	/**
	 * 
	 * Syncs game state. Used internaly. Should not be called unless you know what you are doing
	 */
	@Override
	public void update(float dt) {
	
		for(Connection connection: connections){
			ArrayList<Syncable> components  = null;
			try {
				components = connection.readObjects();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			
			
			System.out.println(components);
			for(Syncable syncable :components){
				Component component = (Component)syncable;
				Entity entity = component.getOwnerEntity();
				EntityManager manager = getEntityManager();
				entity.setEntityManager(manager);
				//Check if any of the components belong to a new entity
				if(!manager.hasEntity(entity)){
					//if so add the entity to the entitymanager
					getEntityManager().addEntity(entity);
				}
				//Check if the entity has this component
				if(manager.getComponentOfClassForEntity(component.getClass(), entity) == null){
					//if true, add the component to the entity
					((Syncable)component).onRead(component);
					manager.addComponentToEntity(component, entity);
					
				}
				
				((Syncable)manager.getComponentOfClassForEntity(component.getClass(), entity)).onRead(component);
				
				
				
				
				
				
			}
			
			
			
		}
	}
}
