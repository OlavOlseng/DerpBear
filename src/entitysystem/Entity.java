package entitysystem;

public class Entity {

	private Long eid;
	private EntityManager entityManager;
	
	public Entity(long eid,EntityManager entityManager){
		this.eid = eid;
		this.entityManager = entityManager;
	}
	
	public Long getEID(){
		return this.eid;
		
	}
	
	
}
