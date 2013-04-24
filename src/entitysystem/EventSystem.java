package entitysystem;

import java.util.ArrayList;

import entitysystem.component.DeathEventComponent;
import entitysystem.component.StatusComponent;

public class EventSystem extends BaseSystem{

	public EventSystem(EntityManager entityManager, EntityFactory entityFactory) {
		super(entityManager, entityFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(float dt) {
		
		//Find all dead entities and fire deathevents
		ArrayList<Entity> deathEnts = getEntityManager().getAllEntitiesPossesingComponentsOfClass(DeathEventComponent.class, StatusComponent.class);
		for(Entity e : deathEnts) {
			StatusComponent sc =(StatusComponent)getEntityManager().getComponentOfClassForEntity(StatusComponent.class, e);
			if(sc != null && !sc.isAlive()) {
				DeathEventComponent dec = (DeathEventComponent)getEntityManager().getComponentOfClassForEntity(DeathEventComponent.class, e);
				dec.fireEvents(dt);
			}
		}
	}
}
