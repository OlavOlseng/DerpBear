package entitysystem.event;

import entitysystem.Entity;
import entitysystem.component.PhysicsComponent;

public class RemoveBodyEvent extends Event {

	public RemoveBodyEvent() {
		super(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onFire() {
		Entity ent = owner.getOwnerEntity();
		PhysicsComponent pc = (PhysicsComponent)(ent.getEntityManager().getComponentOfClassForEntity(PhysicsComponent.class, ent));
		if(pc != null) {
			pc.delete();
		}
	}
}
