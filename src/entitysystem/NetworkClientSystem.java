package entitysystem;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import entitysystem.component.Component;

import network.Syncable;
import network.server.Connection;

public class NetworkClientSystem extends BaseSystem{

	private Connection connection;
	private long updateFrequency;
	
	/**
	 * The network system will start its own private thread for listening.
	 * @param connection a connection to the server
	 * @param updateFrequency how often the socket will be read
	 */
	public NetworkClientSystem(EntityManager entityManager, EntityFactory entityFactory, final Connection connection, final long updateFrequency){
		super(entityManager, entityFactory);
		this.connection = connection;
		this.updateFrequency = updateFrequency;
		new Timer(true).scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("client");
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
		System.out.println(dt);
		ArrayList<Syncable> components  = null;
		try {
			components = connection.readObjects();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		System.out.println(components);
		
	}
}
