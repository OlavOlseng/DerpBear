package entitysystem.component;

import entitysystem.Entity;

public class Component {
	protected Entity owner;
	
	public void setOwnerEntity(Entity ent) {
		this.owner = ent;
	}
	
	public Entity getOwnerEntity() {
		return this.owner;
	}
	
	public void delete(){};
}
