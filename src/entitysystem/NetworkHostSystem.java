package entitysystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import entitysystem.component.Component;

import network.server.Connection;

public class NetworkHostSystem extends BaseSystem {
	
	private ArrayList<Connection> connection;
	private Long updateFrequency;
	
	/**
	 * @param connection - A list of client connections
	 * @param updateFrequency - How often updates will be queried and sent
	 */
	public NetworkHostSystem(EntityManager entityManager, EntityFactory entityFactory, final ArrayList<Connection> connection, final long updateFrequency) {
		super(entityManager, entityFactory);
		this.connection = connection;
		this.updateFrequency = updateFrequency;
		new Timer(true).scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				
				
			}
		}, 0, updateFrequency);
	}

	/**
	 * Not used. The class uses a private thread for updates
	 */
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}

}
