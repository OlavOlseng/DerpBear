package entitysystem;

import java.util.ArrayList;

import org.jbox2d.dynamics.contacts.Contact;

import entitysystem.component.DamageComponent;
import entitysystem.component.StatusComponent;
import entitysystem.component.PhysicsComponent;

import world.ContactHandler;

public class CollisionSystem extends BaseSystem{

	ContactHandler ch;
	Class damageComp = DamageComponent.class;
	Class statusComp = StatusComponent.class;

	public CollisionSystem(EntityManager entityManager, EntityFactory entityFactory, ContactHandler ch) {
		super(entityManager, entityFactory);
		this.ch = ch;
	}

	@Override
	public void update(float dt) {

		ArrayList<Contact> contacts = ch.getContacts();
		ArrayList<Entity> toBeRemoved = new ArrayList<Entity>();

		for (Contact c : contacts) {
			if(!c.isTouching()){
				continue;
			}
			
			//This section takes damage collisions into consideration.
			
			PhysicsComponent a = (PhysicsComponent)(c.getFixtureA().getBody().getUserData());
			PhysicsComponent b = (PhysicsComponent)(c.getFixtureB().getBody().getUserData());

			Entity entA = a.getOwnerEntity();
			Entity entB = b.getOwnerEntity();

			DamageComponent dca = (DamageComponent)(getEntityManager().getComponentOfClassForEntity(damageComp, entA));
			DamageComponent dcb = (DamageComponent)(getEntityManager().getComponentOfClassForEntity(damageComp, entB));

			StatusComponent sca = (StatusComponent)(getEntityManager().getComponentOfClassForEntity(statusComp, entA));
			StatusComponent scb = (StatusComponent)(getEntityManager().getComponentOfClassForEntity(statusComp, entB));

			if(dca != null && scb != null) {
				scb.takeDamage(dca.getDamage());
				if(!scb.isAlive()) {
					toBeRemoved.add(entB);
				}
			}
			if(dcb != null && sca != null) {
				sca.takeDamage(dcb.getDamage());
				if(!sca.isAlive()) {
					toBeRemoved.add(entA);
				}
			}
		}
		for (Entity e : toBeRemoved) {
			getEntityManager().removeEntity(e);
		}
		toBeRemoved.clear();
	}
}
