package entitysystem;

public class EntityFactory {

	private EntityManager entityManager;
	public EntityFactory(EntityManager entityManager){
		this.entityManager = entityManager;
		
	}
	public Entity createEmptyEntity(){
		
		return entityManager.createEntity();
	}
}
