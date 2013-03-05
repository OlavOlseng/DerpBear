package entitysystem;

public class Entity {

	private long eid;
	private EntityManager entityManager;
	
	public Entity(long eid,EntityManager entityManager){
		this.eid = eid;
		this.entityManager = entityManager;
	}
	
	public long getEID(){
		return this.eid;
		
	}
	
	
}
