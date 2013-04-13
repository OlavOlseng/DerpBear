package entitysystem;

import java.io.Serializable;

import entitysystem.component.Component;
import entitysystem.component.RenderComponent;

public class Entity implements Serializable  {

	private Long eid;
	private transient EntityManager entityManager;
	
	

	public Entity(long eid, EntityManager entityManager){
		this.eid = eid;
		this.entityManager = entityManager;
	}
	
	public Long getEID(){
		return this.eid;
		
	}
	
	public Component getComponentOfType(Class type){
		
		return entityManager.getComponentOfClassForEntity(type, this);
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public String toString(){
		return "" + this.eid;
	}
	
}
