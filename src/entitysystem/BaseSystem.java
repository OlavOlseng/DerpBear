package entitysystem;

public abstract class BaseSystem {

	private EntityManager entityManager;
	private EntityFactory entityFactory;
	
	public BaseSystem(EntityManager entityManager,EntityFactory entityFactory){
		this.entityManager = entityManager;
		this.entityFactory = entityFactory;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public EntityFactory getEntityFactory() {
		return entityFactory;
	}
	
	public abstract void update(float dt);
}
