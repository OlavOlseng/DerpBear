package entitysystem.component;

import java.io.Serializable;

import entitysystem.Entity;

public class Component implements Serializable {
	protected Entity owner;
	
	public void setOwnerEntity(Entity ent) {
		this.owner = ent;
	}
	
	public Entity getOwnerEntity() {
		return this.owner;
	}
	
	public void delete(){};
}
