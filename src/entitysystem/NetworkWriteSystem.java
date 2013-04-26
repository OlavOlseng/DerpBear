package entitysystem;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import entitysystem.component.Component;
import entitysystem.component.NetworkComponent;

import network.Syncable;
import network.server.Connection;

public class NetworkWriteSystem extends BaseSystem {

	private Long updateFrequency;

	// Used to keep track of how many connections the system is aware of

	private boolean isHost;
	private ExecutorService threadPool;
	private List<Connection> connections;
	private Object lock = new Object();

	/**
	 * @param connection
	 *            - A list of initial client connections
	 * @param updateFrequency
	 *            - How often updates will be queried and sent
	 * @param isHost
	 *            - Is the system running as client or host?(Ugly solution)
	 */
	public NetworkWriteSystem(EntityManager entityManager,
			EntityFactory entityFactory, final long updateFrequency,
			boolean isHost) {
		super(entityManager, entityFactory);

		threadPool = Executors.newCachedThreadPool();
		this.updateFrequency = updateFrequency;
		this.isHost = isHost;
		this.connections = Collections
				.synchronizedList(new ArrayList<Connection>());

		new Timer().scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				// TODO Auto-generated method stub
				update(0);
			}
		}, 0, updateFrequency);

	}

	/**
	 * Syncs game state. Used internaly. Should not be called unless you know
	 * what you are doing
	 */
	@Override
	public void update(float dt) {
		// True if there are new connections since last tick

		sendChangedComponents();

	}

	public void addConnection(Connection connection) {
		connections.add(connection);
		sendAllComponents(connection);
	}

	private List<Future<?>> tasks = new ArrayList<Future<?>>();

	private void sendChangedComponents() {

		ArrayList<Entity> ents = getEntityManager()
				.getAllEntitiesPossesingComponentOfClass(NetworkComponent.class);
		final List<Syncable> didChangeList = new ArrayList<Syncable>();
		for (Entity entity : ents) {
			NetworkComponent networkComponent = (NetworkComponent) entity
					.getComponentOfType(NetworkComponent.class);
			if (isHost) {
				didChangeList.addAll(networkComponent
						.getReadyHostWriteSyncables());

			} else {
				didChangeList.addAll(networkComponent
						.getReadyClientWriteSyncables());

			}
			networkComponent.clear();
		}

		synchronized (lock) {

			if (didChangeList.size() > 0) {
				System.out.println(didChangeList);
				for (final Connection connection : connections) {
					tasks.add(threadPool.submit(new Runnable() {

						@Override
						public void run() {
							try {

								connection.sendObjects(didChangeList);
								for (Syncable syncable : didChangeList) {
									syncable.onWrite(syncable);
								}

							} catch (IOException e) {
								System.out.println("Client disconected");

							}

						}
					}));

				}

				for (Future<?> task : tasks) {
					try {
						task.get();
					} catch (InterruptedException e) {

						e.printStackTrace();
						System.exit(-1);
					} catch (ExecutionException e) {
						System.exit(-1);
						e.printStackTrace();
					}
				}

			}

		}
	}

	private void sendAllComponents(Connection target) {

		synchronized (lock) {

			ArrayList<Entity> ents = getEntityManager()
					.getAllEntitiesPossesingComponentOfClass(
							NetworkComponent.class);
			for (Entity entity : ents) {
				NetworkComponent networkComponent = (NetworkComponent) entity
						.getComponentOfType(NetworkComponent.class);
				List<Syncable> components;
				if (isHost) {
					components = networkComponent.getAllHostWriteSyncables();
				} else {
					components = networkComponent.getAllClientWriteSyncables();

				}

				try {

					target.sendObjects(components);
					for (Syncable syncable : components) {
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
}
