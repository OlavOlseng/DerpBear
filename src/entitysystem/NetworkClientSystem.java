package entitysystem;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import entitysystem.component.Component;

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
				ArrayList<Component> components  = null;
				try {
					components = connection.readObjects();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
				System.out.println(components);
			}
		}, 0, updateFrequency);
	}

	/**
	 * 
	 * Not used. The class uses a private thread for updates
	 */
	@Override
	public void update(float dt) {
		System.out.println(dt);
		
	}
}
